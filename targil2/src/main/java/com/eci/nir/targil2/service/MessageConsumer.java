package com.eci.nir.targil2.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    public void handleMessage(String message) {
        LOGGER.info("recive message -- message content:: "+message);

    }
}
