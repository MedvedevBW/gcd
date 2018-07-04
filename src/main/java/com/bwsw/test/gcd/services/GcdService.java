package com.bwsw.test.gcd.services;

import com.bwsw.test.gcd.configuration.AppConfig;
import com.bwsw.test.gcd.entities.GcdArgumentsBase;
import com.bwsw.test.gcd.entities.GcdCalculationRequest;
import com.bwsw.test.gcd.entities.GcdCalculationStatus;
import com.bwsw.test.gcd.entities.GcdResult;
import com.bwsw.test.gcd.rabbitmq.Producer;
import com.bwsw.test.gcd.repositories.GcdArgumentsRepository;
import com.bwsw.test.gcd.repositories.GcdResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GcdService {
    private static final Logger logger = LoggerFactory.getLogger(GcdService.class);

    private final RabbitTemplate rabbitTemplate;
    private AppConfig config;
    private Producer<GcdCalculationRequest> producer;
    private GcdArgumentsRepository gcdArgumentsRepository;
    private GcdResultRepository gcdResultRepository;

    public GcdService(RabbitTemplate rabbitTemplate,
                      AppConfig config,
                      Producer<GcdCalculationRequest> producer,
                      GcdArgumentsRepository gcdArgumentsRepository,
                      GcdResultRepository gcdResultRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.config = config;
        this.producer = producer;
        this.gcdArgumentsRepository = gcdArgumentsRepository;
        this.gcdResultRepository = gcdResultRepository;
    }


    public GcdResult get(Long id) {
        logger.info("get GCD calculating result by id: {}", id);
        return gcdResultRepository.findById(id).orElse(
                new GcdResult(id, "Entity with specified id does not exist")
        );
    }

    public Long calculate(Long first, Long second) {
        logger.info("calculate GCD for numbers {} and {}", first, second);

        GcdArgumentsBase argumentsBase = new GcdArgumentsBase(first, second);
        List<GcdArgumentsBase> actualArgumentsList = gcdArgumentsRepository.findByFirstAndSecond(argumentsBase.getFirst(), argumentsBase.getSecond());
        if (!actualArgumentsList.isEmpty()) {
            logger.info("Specified arguments: {} and {} already exist in db", first, second);
            GcdArgumentsBase actualArguments = actualArgumentsList.get(0);

            boolean isNeedRecalculate = gcdResultRepository.findById(actualArguments.getId()).map( entity ->
                    GcdCalculationStatus.error.equalsName(entity.getStatus())
            ).orElse(true);

            if (isNeedRecalculate) {
                tryToSendArguments(actualArguments);
            }

            return actualArguments.getId();
        } else {
            logger.info("Specified arguments: {} and {} do not exist in db", first, second);
            GcdArgumentsBase newArgumentsBase = gcdArgumentsRepository.save(argumentsBase);
            GcdResult result = new GcdResult(newArgumentsBase.getId(), GcdCalculationStatus.notCompleted);

            gcdResultRepository.save(result);

            tryToSendArguments(newArgumentsBase);

            return newArgumentsBase.getId();
        }
    }

    private void tryToSendArguments(GcdArgumentsBase argumentsBase) {
        logger.info("Try to send arguments: {}", argumentsBase);

        String exchange = config.getAppExchange();
        String routingKey = config.getAppRoutingKey();
        try {
            producer.sendMessage(
                    rabbitTemplate,
                    exchange,
                    routingKey,
                    new GcdCalculationRequest(
                            argumentsBase.getFirst(),
                            argumentsBase.getSecond(),
                            argumentsBase.getId()
                    )
            );
        } catch (Throwable exception){
            logger.error("Arguments: {} was not sent, exception with message: {} was thrown", argumentsBase, exception.getMessage());
            gcdResultRepository.save(new GcdResult(argumentsBase.getId(), exception.getMessage()));
        }
    }
}
