package com.eci.nir.targil2.config;


import com.eci.nir.targil2.model.PaymentType;
import com.eci.nir.targil2.service.MessageConsumer;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {


    //move to application
    public static final String topicExchangeName = "main-exchange";

    public  static final String cashQueueName = PaymentType.CASH.name()+"-queue";
    public  static final String creditQueueName = PaymentType.CREDIT.name()+"-queue";

    @Bean
    Queue queue() {
        return new Queue(cashQueueName, false);
    }

    @Bean
    Queue queue1() {
        return new Queue(creditQueueName, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(topicExchangeName);
    }

    //cash binding
    @Bean
    Binding bindingCashQueue(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(PaymentType.CASH.name());
    }

    //credit binding
    @Bean
    Binding bindingCreditQueue(Queue queue1, DirectExchange exchange) {
        return BindingBuilder.bind(queue1).to(exchange).with(PaymentType.CREDIT.name());
    }

    //delegate
    @Bean
    MessageListenerAdapter listenerAdapter(MessageConsumer messageConsumer) {
        return new MessageListenerAdapter(messageConsumer, "handleMessage");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(cashQueueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }
}
