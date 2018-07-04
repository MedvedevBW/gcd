package com.bwsw.test.gcd.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class Producer<T extends Serializable> {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    public void sendMessage(RabbitTemplate rabbitTemplate, String exchange, String routingKey, T message) {
        logger.info("sendMessage(exchange: {}, routingKey: {}, message: {})", exchange, routingKey, message);
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        logger.info("The message has been sent to the queue.");
    }
}
