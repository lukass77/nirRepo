package com.rit.integration.tcpserver.services;

import com.rit.integration.gateway.ifc.CacheService;
import com.rit.integration.tcpserver.model.RequestType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by nirbo on 11/18/2015.
 */

public class TcpFrontController {


    private static Logger _logger = Logger.getLogger(TcpFrontController.class);

    @Value("${upload.finish.package}")
    private String uploadFinishPackage;

    @Autowired
    private CacheService<String, Message<byte[]>> cacheService;


    // @ServiceActivator
    public Message<byte[]> handleKeepAlive(Message<byte[]> message) {
        byte[] payload = message.getPayload();
        String connectionId = (String) message.getHeaders().get(IpHeaders.CONNECTION_ID);
        String requestHexPayload = DatatypeConverter.printHexBinary(payload);
        _logger.debug("*** Received TCP Message from CAP: " + requestHexPayload);
        _logger.trace("*** Header ConnectionId: " + connectionId);
        Message<byte[]> response = handleRequest(message, requestHexPayload);
        return response;

    }


    private Message<byte[]> handleRequest(Message<byte[]> message, String requestHexPayload) {
        Message<byte[]> response = null;
        if (requestHexPayload.startsWith(RequestType.REQUEST_PACKAGE.getRequestPrefix()) || requestHexPayload.startsWith(RequestType.HANDSHAKE_PACKAGE.getRequestPrefix())) {
            response = message;
        } else if (requestHexPayload.startsWith(RequestType.ACK_ALARM.getRequestPrefix())) {
            //String[] split = requestHexPayload.split("7E7E7E01");
            String requestPrefix = RequestType.ACK_ALARM.getRequestPrefix();
            if (!cacheService.isEmpty()) {
                //getting message sequence number
                //TODO - SUBSTRING HERE IS BUGY
                String substring = requestHexPayload.substring(requestPrefix.length() + 2);
                Message<byte[]> s = cacheService.remvoeEntityByKey(substring);
                if(s != null){
                    _logger.debug("remove entity from cache with key " + substring);
                }
            }
            response = buildUplaodFinishMsg(message);
        }
        return response;
    }


    private Message<byte[]> buildUplaodFinishMsg(Message<byte[]> message) {
        String uploadFinishResponse = uploadFinishPackage.replaceAll("\\s+", "").toUpperCase();
        MessageBuilder<byte[]> dateMessageBuilder = MessageBuilder.withPayload(uploadFinishResponse.getBytes()).copyHeadersIfAbsent(message.getHeaders());
        return dateMessageBuilder.build();

    }


}
