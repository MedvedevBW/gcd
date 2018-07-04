package com.bwsw.test.gcd;

import com.bwsw.test.gcd.configuration.AppConfig;
import com.bwsw.test.gcd.services.GcdService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@EnableRabbit
@SpringBootApplication
public class GcdApplication extends SpringBootServletInitializer /*implements RabbitListenerConfigurer */{

	@Autowired
	private AppConfig config;

	public static void main(String[] args) {
		SpringApplication.run(GcdApplication.class, args);
	}

	@Bean
	public TopicExchange getAppExchange() {
		return new TopicExchange(config.getAppExchange());
	}

	@Bean
	public Queue getAppQuestionQueue() {
		return new Queue(config.getAppQuestionQueue());
	}

	@Bean
	public Queue getAppAnswerQueue() {
		return new Queue(config.getAppAnswerQueue());
	}

	@Bean
	public Binding declareBindingApp() {
		return BindingBuilder.bind(getAppQuestionQueue()).to(getAppExchange()).with(config.getAppRoutingKey());
	}

	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
