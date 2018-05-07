package com.rit.integration.tcpserver.services;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;
import org.springframework.stereotype.Component;

/**
 * Created by nirbo on 11/26/2015.
 */
@Component
public class TcpConnectionCloseListener implements ApplicationListener<TcpConnectionCloseEvent> {

    private final static Logger _logger = Logger.getLogger(TcpConnectionCloseListener.class);


    public void onApplicationEvent(TcpConnectionCloseEvent tcpConnectionCloseEvent) {
        _logger.info(tcpConnectionCloseEvent.toString());
    }


}
