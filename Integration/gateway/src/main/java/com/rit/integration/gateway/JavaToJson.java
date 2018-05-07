package com.rit.integration.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rit.integration.gateway.model.CenterMindSystemEvent;
import com.rit.integration.gateway.model.EventTime;
import org.apache.log4j.Logger;

import java.util.Calendar;

/**
 * Created by nirbo on 12/3/2015.
 */
public class JavaToJson {

    private static final Logger LOGGER = Logger.getLogger(JavaToJson.class);

    public static void main(String[] args) {
        ObjectMapper jacksonMapper = new ObjectMapper();
        CenterMindSystemEvent cme = new CenterMindSystemEvent();
        cme.setContent("port is unpluged");
        cme.setDeviceCode("Cisco switch");
        cme.setId("00 05");
        Calendar instance = Calendar.getInstance();

        EventTime time = new EventTime(16,34,10,20,5,2010);
        cme.setEventTime(time);

        cme.setEventType("01");
        cme.setEventCode("10 01");


        try {
            String s = jacksonMapper.writeValueAsString(cme);
            LOGGER.info(s);


        } catch (Exception e) {
            LOGGER.error(e);
        }

    }
}
