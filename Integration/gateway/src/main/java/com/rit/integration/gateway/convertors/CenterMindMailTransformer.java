package com.rit.integration.gateway.convertors;

import com.rit.integration.gateway.model.CenterMindSystemEvent;
import com.rit.integration.gateway.model.EventTime;
import com.rit.integration.gateway.model.MailBodyAttributes;
import com.rit.integration.gateway.model.external.enums.AlarmLevel;
import com.rit.integration.gateway.model.external.enums.AlarmMark;
import com.rit.integration.gateway.model.external.enums.AlarmType;
import org.apache.log4j.Logger;
import org.springframework.messaging.Message;

import java.util.Set;

/**
 * Created by nirbo on 12/21/2015.
 */
public class CenterMindMailTransformer {

    public static final Logger LOGGER = Logger.getLogger(CenterMindMailTransformer.class);

    private Set<String> mailBodyAttributes;


    public void setMailBodyAttributes(Set<String> mailBodyAttributes) {
        this.mailBodyAttributes = mailBodyAttributes;
    }

    /**
     * This method tranform a mail string content to CenterMindSystemEvent
     *
     * @param message - mail string from pop3 server
     * @return CenterMindSystemEvent
     */
    //TODO - NEED TO COMPLETE THIS METHOD , BY USING A REAL MAIL FROM CLIENT ENV
    public CenterMindSystemEvent transformMail(Message<String> message) {
        CenterMindSystemEvent result = null;
        String body = message.getPayload();
        LOGGER.info("mail content " + body);
        try {
            result = new CenterMindSystemEvent();
            String[] mailLines = body.split("\\r?\\n");
            for (String mailLine : mailLines) {
                LOGGER.debug(mailLine.trim());
                int index = mailLine.indexOf(':');
                String attributenName = mailLine.substring(0, index).trim();
                String value = mailLine.substring(index + 1, mailLine.length()).trim();

                if (attributenName.equals(MailBodyAttributes.DATE_TIME)) {
                    EventTime eventTime = handleTime(value);
                    result.setEventTime(eventTime);

                }
                if (attributenName.equals(MailBodyAttributes.DESCRIPTION)) {
                    result.setContent(value);
                }
                if (attributenName.equals(MailBodyAttributes.EVENT_CODE)) {

                    AlarmType alarmType = AlarmType.enumByEventCode(value);

                    result.setEventType(alarmType.getTypeHexValue());
                    result.setEventCode(alarmType.getAlarmCode());

                    AlarmLevel alarmLevel = calcLevelByAlarmType(alarmType);
                    result.setEventLevel(alarmLevel.getHexCode());


                }
            }
            //TODO - ALL MISSING DATA FROM MAIL / need to check if can be calculated internally
            result.setEventSign(AlarmMark.HAPPEN.getHexVal());
            result.setId("2055");
            result.setLocation("01 02 03 0A");
            //need to be fetch from device code table - xls file
            result.setDeviceCode("15");


        } catch (Exception e) {
            LOGGER.error("Parsing mail error ", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * parse event time value - 12/21/2015 17:13
     *
     * @param value
     * @return
     */
    private EventTime handleTime(String value) {
        EventTime result = null;
        int i = value.indexOf(" ");
        String date = value.substring(0, i).trim();
        String[] split = date.split("/");
        int month = Integer.valueOf(split[0]);
        int day = Integer.valueOf(split[1]);
        int year = Integer.valueOf(split[2]);

        String time = value.substring(i, value.length()).trim();
        String[] timeArr = time.split(":");
        int hour = Integer.valueOf(timeArr[0]);
        int minute = Integer.valueOf(timeArr[1]);
        result = new EventTime(0, minute, hour, day, month, year);

        return result;
    }

    private AlarmLevel calcLevelByAlarmType(AlarmType alarmType) {
        AlarmLevel result = null;
        if (alarmType.equals(AlarmType.PVMAX_DEVICE_OFFLINE) || alarmType.equals(AlarmType.PVMAX_DEVICE_ONLINE)) {
            result = AlarmLevel.MINOR;

        } else {
            result = AlarmLevel.MAJOR;
        }
        return result;
    }


}
