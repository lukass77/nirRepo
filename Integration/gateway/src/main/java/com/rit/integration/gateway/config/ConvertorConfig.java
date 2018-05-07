package com.rit.integration.gateway.config;

import com.rit.integration.gateway.model.CenterMindSystemEvent;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by nirbo on 12/3/2015.
 * Setting to get CenterMindSystemEvent object from json message sent to the queue
 */
@Configuration
public class ConvertorConfig {

    @Bean()
    public DefaultClassMapper typeMapper() {
        DefaultClassMapper typeMapper = new DefaultClassMapper();
        typeMapper.setDefaultType(CenterMindSystemEvent.class);
        return typeMapper;
    }

    @Bean(name = "eventMessageConverter" )
    public MessageConverter messageConverter(){
        org.springframework.amqp.support.converter.Jackson2JsonMessageConverter jsonMessageConverter = new org.springframework.amqp.support.converter.Jackson2JsonMessageConverter();
        jsonMessageConverter.setClassMapper(typeMapper());
        return jsonMessageConverter;
    }
}
