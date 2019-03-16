package com.eci.nir.targil2.config;


import com.eci.nir.targil2.model.PaymentType;
import com.eci.nir.targil2.service.MessageConsumer;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {


    public static final String topicExchangeName = "main-exchange";

    public  static final String cashQueueName = PaymentType.CASH.name()+"-queue";

    @Bean
    Queue queue() {
        return new Queue(cashQueueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(PaymentType.CASH.name());
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
