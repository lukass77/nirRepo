package com.rit.integration.tcpserver.services;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.ip.tcp.connection.TcpConnectionOpenEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by nirbo on 11/26/2015.
 */
@Component
public class TcpConnectionListener implements ApplicationListener<TcpConnectionOpenEvent> {

    private final static Logger _logger = Logger.getLogger(TcpConnectionListener.class);

    public static String currentConnectinoId;


    @Resource(name = "outChannel")
    private DirectChannel outputchanel;


    public void onApplicationEvent(TcpConnectionOpenEvent applicationEvent) {

        currentConnectinoId = applicationEvent.getConnectionId();
        _logger.debug("client connect open new tcp connection connection id is"+currentConnectinoId);

       // pushMessagesToClient(connectionId);


    }

   /* private void pushMessagesToClient(String connectionId) {
        try {
            Thread.sleep(7000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Map<String,Object> header = new HashMap<String,Object>();
        header.put(IpHeaders.CONNECTION_ID,connectionId);
        outputchanel.send(new GenericMessage<String>("HI World from server",header));
        outputchanel.send(new GenericMessage<String>("HI World from server11",header));
        try {
            Thread.sleep(3000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String s = "FAFAFA03";

        outputchanel.send(new GenericMessage<String>(s,header));*/
        //outputchanel.send(new GenericMessage<String>("HI World from server13",header));
       // outputchanel.send(new GenericMessage<String>("HI World from server14",header));
      //  outputchanel.send(new GenericMessage<String>("HI World from server15",header));
       // outputchanel.send(new GenericMessage<String>("HI World from server16",header));
    //}
}
