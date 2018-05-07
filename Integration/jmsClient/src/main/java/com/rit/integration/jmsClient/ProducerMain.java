package com.rit.integration.jmsClient;

import com.rit.integration.jmsClient.services.EventProducer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by nirbo on 11/24/2015.
 */
public class ProducerMain {

    public static void main(String[] args) {


        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:com/rit/integration/jmsclient/spring/rabbitmq-producer-context.xml");
        EventProducer producer = applicationContext.getBean(EventProducer.class);

        for (int i = 1; i < 10; i++) {
            String payLoad = producer.createJsonMssage(String.valueOf(i));
            Message message = ProducerMain.buildMessage(payLoad);
            producer.sendEvent(message);
       }
    }


    private static Message buildMessage(String payLoad) {
        MessageProperties props = MessagePropertiesBuilder.newInstance()
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
        Message message = MessageBuilder.withBody(payLoad.getBytes()).
                andProperties(props).build();
        return message;

    }
}
