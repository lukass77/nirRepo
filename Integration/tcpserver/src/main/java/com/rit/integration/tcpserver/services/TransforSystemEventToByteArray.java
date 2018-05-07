package com.rit.integration.tcpserver.services;

import com.rit.integration.gateway.ifc.CacheService;
import com.rit.integration.gateway.model.CenterMindSystemEvent;
import com.rit.integration.gateway.model.external.CAPAlarm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import javax.xml.bind.DatatypeConverter;
import java.util.List;

/**
 * Created by nirbo on 12/3/2015.
 */
public class TransforSystemEventToByteArray {

    public static final String MESSAGE_ID = "messageId";

    @Resource(name = "server")
    private TcpNetServerConnectionFactory tcpNetServerConnectionFactory;

    @Autowired
    private CacheService<String, Message<byte[]>> cacheService;

    private ConversionService conversionService;

    private static final Logger LOGGER = Logger.getLogger(TransforSystemEventToByteArray.class);


    public TransforSystemEventToByteArray() {
    }


    @Autowired
    @Qualifier(value = "conversionService")
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


    /**
     * This method get CenterMindSystemEvent from Queue and transform it to ByteArray
     * Transform --> output
     *
     * @param centerMindSystemEventMessage *
     * @return
     */
    public Message<byte[]> transform(Message<CenterMindSystemEvent> centerMindSystemEventMessage) {
        Message<byte[]> result = null;
        String connectionId = isSocketOpen();
        if (connectionId != null) {
            MessageHeaders headers = centerMindSystemEventMessage.getHeaders();
            CenterMindSystemEvent centerMindSystemEvent = centerMindSystemEventMessage.getPayload();
            CAPAlarm capEvent = conversionService.convert(centerMindSystemEvent, CAPAlarm.class);
            //convert capEvent to Hexdecimal String
            boolean isHexMessageValid = validateHexdecimalMsg(capEvent);

            if(isHexMessageValid) {
               //trim spaces before converting to byte array
                String messageHexString =  capEvent.getHexdecimalMessageString().replaceAll("\\s+", "").toUpperCase();
                LOGGER.debug("hex message to be sent "+messageHexString);
                //convert hexdecimal string to byte array
                byte[] s = DatatypeConverter.parseHexBinary(messageHexString);
                MessageBuilder<byte[]> capMessageBuilder = MessageBuilder.withPayload(s).copyHeadersIfAbsent(headers);

                String key = capEvent.getSequenceNumber().replaceAll("\\s+", "");
                capMessageBuilder.setHeader(MESSAGE_ID, key);
                capMessageBuilder.setHeader(IpHeaders.CONNECTION_ID, connectionId);
                result = capMessageBuilder.build();

            }else{
                throw new MessageHandlingException(result,"Hexdecimal message is not valid : "+capEvent.getHexdecimalMessageString());
            }

        } else {
            throw new MessageHandlingException(result, "Currently there is not open socket on server");
        }
        return result;
    }

    /**
     * Expect hexmessage is 27 byte by spec
     * -- all validate logic can be done here
     * @param capEvent
     * @return
     */
    private boolean validateHexdecimalMsg(CAPAlarm capEvent) {
        String hexdecimalMessageString = capEvent.getHexdecimalMessageString();
        String[] split = hexdecimalMessageString.split(" ");
        return split.length == 27;
    }


    private String isSocketOpen() {
        String result = null;
        List<String> openConnectionIds = tcpNetServerConnectionFactory.getOpenConnectionIds();
        if (openConnectionIds != null && !openConnectionIds.isEmpty()) {
            result = openConnectionIds.get(0);
            LOGGER.trace(IpHeaders.CONNECTION_ID + " is " + result);
        }
        return result;
    }


  /*  private String toTrimHexStringWithEndBit(CAPAlarm capEvent) {
       *//* StringBuffer buffer = new StringBuffer(capEvent.getHexFormatWithSpacesWithOutEndBit());
        buffer.append(" ");
        buffer.append(capEvent.getEndBit());
        String s = buffer.toString();*//*

        return

    }*/
}
