package com.bwsw.test.gcd.configuration;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableRabbit
@PropertySource("classpath:application.yml")
public class AppConfig {

    @Value("${app.rabbitmq.exchange}")
    private String appExchange;

    @Value("${app.rabbitmq.questionQueue}")
    private String appQuestionQueue;

    @Value("${app.rabbitmq.answerQueue}")
    private String appAnswerQueue;

    @Value("${app.rabbitmq.routingkey}")
    private String appRoutingKey;

    public String getAppExchange() {
        return appExchange;
    }

    public void setAppExchange(String appExchange) {
        this.appExchange = appExchange;
    }

    public String getAppRoutingKey() {
        return appRoutingKey;
    }

    public void setAppRoutingKey(String appRoutingKey) {
        this.appRoutingKey = appRoutingKey;
    }

    public String getAppQuestionQueue() {
        return appQuestionQueue;
    }

    public void setAppQuestionQueue(String appQuestionQueue) {
        this.appQuestionQueue = appQuestionQueue;
    }

    public String getAppAnswerQueue() {
        return appAnswerQueue;
    }

    public void setAppAnswerQueue(String appAnswerQueue) {
        this.appAnswerQueue = appAnswerQueue;
    }
}
