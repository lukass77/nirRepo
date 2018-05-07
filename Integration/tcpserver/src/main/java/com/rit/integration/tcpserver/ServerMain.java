package com.rit.integration.tcpserver;


import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * Created by nirbo on 11/18/2015.
 */

@SpringBootApplication
//LOADING gateway context configuration
@Import(com.rit.integration.gateway.config.GateWaySpringConfig.class)
@ImportResource(locations = {"classpath:com/rit/integration/tcpserver/tcpServer-context.xml"})

public class ServerMain {

    private static final Logger _logger = Logger.getLogger(ServerMain.class);

    public static void main(String[] args) {
        SpringApplication.run(ServerMain.class, args);
        _logger.info("Start TCP server ...");
        //ServerMain.setupContext();

    }


   /* public static GenericXmlApplicationContext setupContext() {
        final GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.load("classpath:com/rit/integration/tcpserver/tcpServer-context.xml");
        context.registerShutdownHook();
        context.refresh();
        _logger.info("Start TCP server ...");


        return context;
    }*/
}