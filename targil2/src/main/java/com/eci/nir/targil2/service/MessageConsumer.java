package com.eci.nir.targil2.service;


import com.eci.nir.targil2.model.Payment;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    private final ObjectMapper objectMapper;

    public MessageConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void handleMessage(String message) {
        LOGGER.info("receive message -- message content:: "+message);
      /*  try {
            Payment payment = objectMapper.readValue(message, Payment.class);
            LOGGER.info(payment.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
}
