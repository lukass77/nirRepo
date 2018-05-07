package com.rit.integration.gateway.service;


import com.rit.integration.gateway.ifc.GateWayMessageHandler;
import com.rit.integration.gateway.model.CenterMindSystemEvent;
import org.apache.log4j.Logger;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.support.GenericMessage;

import javax.annotation.Resource;

/**
 * Created by nirbo on 11/24/2015.
 * not in use - using spring integration amqe , do not delete this .
 */
@Deprecated
public class SystemEventConsumer implements GateWayMessageHandler<CenterMindSystemEvent> {

    private static final Logger LOGGER = Logger.getLogger(SystemEventConsumer.class);

    @Resource(name = "inTransformerChannel")
    private DirectChannel inTransformerChannel;


    /**
     * Sent message from queue to transformerChannel
     * @param systemEvent
     * TODO - maybe can be improved by rabbit mq + spring integration , integration , so channel 'transformerChannel' will get message directly
     */
    public void handleMessage(CenterMindSystemEvent systemEvent) {
        LOGGER.info("received systemEvent from queue :: " + systemEvent.toString());
         inTransformerChannel.send(new GenericMessage<CenterMindSystemEvent>(systemEvent));
    }




}
