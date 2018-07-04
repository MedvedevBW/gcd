package com.bwsw.test.gcd.rabbitmq;

import com.bwsw.test.gcd.configuration.AppConfig;
import com.bwsw.test.gcd.entities.*;
import com.bwsw.test.gcd.repositories.GcdResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcdArgumentsConsumer {
    private static final Logger logger = LoggerFactory.getLogger(GcdArgumentsConsumer.class);

    private AppConfig config;
    private GcdResultRepository resultRepository;

    @Autowired
    public GcdArgumentsConsumer(GcdResultRepository resultRepository, AppConfig config) {
        this.resultRepository = resultRepository;
        this.config = config;
    }

    @RabbitListener(queues="${app.rabbitmq.answerQueue}")
    public void recieveMessage(GcdCalculationResponse response) {
        logger.info("recieveMessage(GcdCalculationResponse {})", response);
        GcdResult result;

        if (response.getErrorMessage() == null) {
            result = new GcdResult(response.getRequestId(), response.getResult());
        } else {
            result = new GcdResult(response.getRequestId(), response.getErrorMessage());
        }

        resultRepository.save(result);
    }
}
