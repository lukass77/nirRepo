package com.rit.integration.jmsClient.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rit.integration.gateway.model.CenterMindSystemEvent;
import com.rit.integration.gateway.model.EventTime;
import com.rit.integration.gateway.model.external.enums.AlarmLevel;
import com.rit.integration.gateway.model.external.enums.AlarmMark;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nirbo on 11/24/2015.
 */
@Service
public class EventProducer {

    private static final Logger _logger = Logger.getLogger(EventProducer.class);

    //public static final String cmJsonEvent = "{\"id\":1234,\"eventType\":\"UnPluged\",\"time\":1449127467902,\"content\":\"port is unpluged\",\"device\":\"Cisco switch\",\"portId\":\"16\"}";
    //public static final String cmJsonEvent = "{\"id\":\"0005\",\"eventSign\":null,\"eventType\":\"01\",\"eventCode\":\"1001\",\"eventLevel\":null,\"eventTime\":{\"second\":16,\"minute\":34,\"hour\":10,\"day\":20,\"month\":5,\"year\":2010},\"content\":\"port is unpluged\",\"device\":\"Cisco switch\",\"portId\":\"16\"}";


    private RabbitTemplate rabbitTemplate;

    public EventProducer() {
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEvent(String eventMessage){
        rabbitTemplate.convertAndSend("centerMindExchange","cm-events",eventMessage);

        _logger.info("post event to centerMindExchange "+eventMessage);

    }


    public void sendEvent(Message eventMessage){
        rabbitTemplate.convertAndSend("centerMindExchange","cm-events",eventMessage);
        _logger.info("post event to centerMindExchange "+eventMessage);

    }

    public void sendEventM(String eventMessage){
        MessageBuilder messageBuilder = MessageBuilder.withBody(eventMessage.getBytes());
        Message build = messageBuilder.build();
        rabbitTemplate.convertAndSend("centerMindExchange","cm-events",build);
        _logger.info("post event to centerMindExchange "+eventMessage);

    }

    public String createJsonMssage(String id){
        String result = null;
        ObjectMapper jacksonMapper = new ObjectMapper();
        CenterMindSystemEvent cme = new CenterMindSystemEvent();
        //cme.setContent("port is unpluged");
        cme.setLocation("01 02 03 0A");
        cme.setDeviceCode("15");

        cme.setId(id);

        EventTime time = new EventTime(16,34,10,20,5,2010);


        cme.setEventTime(time);
        cme.setEventType("01");
        cme.setEventCode("05");
        cme.setEventLevel(AlarmLevel.MINOR.getHexCode());
        cme.setEventSign(AlarmMark.HAPPEN.getHexVal());

        try {
            result = jacksonMapper.writeValueAsString(cme);
            _logger.info(result);


        } catch (Exception e) {
            _logger.error(e);
        }
        return  result;
    }
}
