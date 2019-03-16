package com.eci.nir.targil2.service;

import com.eci.nir.targil2.config.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);

    private final RabbitTemplate rabbitTemplate;
    private final MessageConsumer receiver;


    public MessageProducer(RabbitTemplate rabbitTemplate, MessageConsumer receiver) {
        this.rabbitTemplate = rabbitTemplate;
        this.receiver = receiver;
    }


    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Sending message...");
        rabbitTemplate.convertAndSend(ApplicationConfig.topicExchangeName, "payment", "Hello from RabbitMQ!");
       // receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}
