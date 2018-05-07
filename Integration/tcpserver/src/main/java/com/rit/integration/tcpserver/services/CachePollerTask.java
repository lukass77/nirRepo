package com.rit.integration.tcpserver.services;

import com.rit.integration.gateway.ifc.CacheService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.integration.util.CollectionFilter;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by nirbo on 12/10/2015.
 */
public class CachePollerTask {

    private static final Logger LOGGER = Logger.getLogger(CachePollerTask.class);

    @Resource(name = "server")
    private TcpNetServerConnectionFactory tcpNetServerConnectionFactory;

    @Autowired
    private CacheService<String, Message<byte[]>> cacheService;

    @Resource(name = "outChannel")
    private DirectChannel outPutChannel;


    @Value("${tcpserver.cachePuller.fixed-delay}")
    private String cachePullerInterval;


    /**
     * Resend push messges in case we did not get ACK from external system
     */
    public void reSendPushEventMessages() {

        Cache eventsCache = cacheService.getEventsCache();
        Map nativeCache = (ConcurrentMap) eventsCache.getNativeCache();

        Collection<Message<byte[]>> values = nativeCache.values();
        LOGGER.debug("cache size is " + values.size());
        if (!cacheService.isEmpty()) {
            String currentTcpConnectionId = getCurrentTcpConnectionId();
            if (currentTcpConnectionId != null) {
                Long now = Calendar.getInstance().getTimeInMillis();
                for (Message<byte[]> value : values) {
                    Long msgTimeStamp = value.getHeaders().getTimestamp();
                    //re-send just when message time in cache is bigger then pullerInterval
                    if(now - msgTimeStamp > Long.valueOf(cachePullerInterval)+1000) {
                        //TODO - can be done as a callable / thread task
                        MessageBuilder<byte[]> dateMessageBuilder = MessageBuilder.withPayload(value.getPayload()).copyHeadersIfAbsent(value.getHeaders());
                        Message<byte[]> result = dateMessageBuilder.setHeader(IpHeaders.CONNECTION_ID, currentTcpConnectionId).build();
                        //resend push message
                        outPutChannel.send(result);
                    }
                }
            }


        }
    }


    public String getCurrentTcpConnectionId() {
        String result = null;
        List<String> openConnectionIds = tcpNetServerConnectionFactory.getOpenConnectionIds();
        if (openConnectionIds != null && !openConnectionIds.isEmpty()) {
            result = openConnectionIds.get(0);
            LOGGER.debug(IpHeaders.CONNECTION_ID + " is " + result);
        }
        return result;
    }
}
