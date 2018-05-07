package com.rit.integration.tcpserver.services;

import com.rit.integration.gateway.ifc.CacheService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

/**
 * Created by nirbo on 12/8/2015.
 */

public class PushMessageInterceptor extends ChannelInterceptorAdapter {

    private static final Logger LOGGER = Logger.getLogger(PushMessageInterceptor.class);

    @Autowired
    private CacheService<String, Message<byte[]>> cacheService;

    /*@Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        LOGGER.info("in preSend Push Interceptor "+message.getClass().getName());
        return super.preSend(message, channel);
    }*/

   /* @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        LOGGER.info("in postSend Push Interceptor "+message.getClass().getName()+ " sent value is "+sent);
        super.postSend(message, channel, sent);
    }*/

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        String messageId = (String) message.getHeaders().get(TransforSystemEventToByteArray.MESSAGE_ID);
        if (sent && messageId != null) {
            //put in cache - just in case send to cap succeed
            LOGGER.info("put sent message in cache , message id =  " + messageId);
            cacheService.putEntity(messageId, (Message<byte[]>) message);
        }
    }


}
