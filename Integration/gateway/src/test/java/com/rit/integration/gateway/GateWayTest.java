package com.rit.integration.gateway;

import com.rit.integration.gateway.config.GateWaySpringConfig;
import com.rit.integration.gateway.convertors.CenterMindMailTransformer;
import com.rit.integration.gateway.model.CenterMindSystemEvent;
import com.rit.integration.gateway.model.EventTime;
import com.rit.integration.gateway.model.external.CAPAlarm;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.core.convert.ConversionService;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by nirbo on 12/6/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:com/"})
@SpringApplicationConfiguration(classes = {GateWaySpringConfig.class})
public class GateWayTest {

    private static final Logger LOGGER = Logger.getLogger(GateWayTest.class);
    public static final String EXPECTED_HEX_MSG_FORMAT = "7E 7E 7E 01 01 02 03 0A 00 14 FF 01 00 05 01 00 05 14 0A 05 14 0A 22 10 00 00 1D";

    @Autowired
    private ConversionService conversionService;



    @Resource(name = "cacheManager")
    private CacheManager cacheManager;

    @Resource(name = "mailTransformerBean")
    private CenterMindMailTransformer mailTransformer;

    @Test
    public void parseEventTime() {
        EventTime time = new EventTime(16, 34, 10, 20, 5, 2010);

        org.junit.Assert.assertEquals("10", time.secondToHex(16));
        org.junit.Assert.assertEquals("22", time.minuteToHex(34));
        org.junit.Assert.assertEquals("0A", time.hourToHex(10));
        org.junit.Assert.assertEquals("14", time.dayToHex(20));
        org.junit.Assert.assertEquals("05", time.monthToHex(5));
        org.junit.Assert.assertEquals("14", time.yearFirstPartToHex());
        org.junit.Assert.assertEquals("0A", time.yearSecondPartToHex());

    }


    @Test
    public void convertCmEventToCapAlarmTest() {
        CenterMindSystemEvent centerMindSystemEvent = buildCenterMindEvent();
        CAPAlarm convert = conversionService.convert(centerMindSystemEvent, CAPAlarm.class);
        String s = convert.getHexdecimalMessageString();
        LOGGER.info("convert result " + s);
        org.junit.Assert.assertEquals(EXPECTED_HEX_MSG_FORMAT, s);

        centerMindSystemEvent.setId("1255");
        convert = conversionService.convert(centerMindSystemEvent, CAPAlarm.class);
         /*hexFormatWithSpacesWithOutEndBit = convert.getHexFormatWithSpacesWithOutEndBit();
        LOGGER.info("HexString with spaces with out endBit " + hexFormatWithSpacesWithOutEndBit);*/
        s = convert.getHexdecimalMessageString();
        LOGGER.info("convert result " + s);
        org.junit.Assert.assertEquals("7E 7E 7E 01 01 02 03 0A 00 14 FF 01 00 05 01 04 E7 14 0A 05 14 0A 22 10 00 00 03", s);


    }

    @Test
    public void eventTimeDateTest() {
        EventTime t = new EventTime(new Date());
        LOGGER.info(t.toString());
        LOGGER.info(t.toHexString());
    }



    private CenterMindSystemEvent buildCenterMindEvent() {
        CenterMindSystemEvent cme = new CenterMindSystemEvent();
        //cme.setContent("port is unpluged");
        cme.setLocation("01 02 03 0A");
        cme.setDeviceCode("20");
        cme.setId("5");
        EventTime time = new EventTime(16, 34, 10, 20, 5, 2010);
        cme.setEventTime(time);
        cme.setEventType("01");
        cme.setEventCode("05");
        cme.setEventLevel("01");
        cme.setEventSign("FF");
        return cme;
    }


    @Test
    public void cacheTest(){
        Cache eventsCache = cacheManager.getCache("eventsCache");
        eventsCache.put("key","dfasdfasdf");
        String value = (String) eventsCache.get("key").get();
        org.junit.Assert.assertEquals("dfasdfasdf",value);
        eventsCache.evict("key");
        org.junit.Assert.assertEquals(eventsCache.get("key",String.class),null);
        //eventsCache.putIfAbsent("adfsd",null);
    }


    @Test
    public void parseInput(){
        String input = "0103B77E7E7E0103B87E7E7E0103B97E7E7E0103BA7E7E7E0103BB";
        String[] split = input.split("7E7E7E01");
    }

    @Test
    public void parseMailBodyTest() {
        String txt = "Event Code : unscheduled port connect\r\nDate/Time  :  12/21/2015 17:13\r\nSource : \r\n " +
                "User : Administrator\r\nComputer : fe80::bda2:dde3:9c09:4022%10\r\nDescription :  Work Order Modified\r\n"+
                "WorkOrderId : 1000003\r\nWorkOrderName : WO-21/12/2015-00007\r\nWorkOrderPriority : 1\r\n\r\n";
        //MessageBuilder<byte[]> capMessageBuilder = MessageBuilder.withPayload(s).copyHeadersIfAbsent(headers);

        MessageBuilder<String> testMail = MessageBuilder.withPayload(txt);
        CenterMindSystemEvent centerMindSystemEvent = mailTransformer.transformMail(testMail.build());

        Assert.assertNotNull(centerMindSystemEvent);
        Assert.assertNotNull(centerMindSystemEvent.getEventCode());
        Assert.assertNotNull(centerMindSystemEvent.getEventLevel());
        Assert.assertNotNull(centerMindSystemEvent.getEventSign());
        Assert.assertNotNull(centerMindSystemEvent.getContent());
        Assert.assertNotNull(centerMindSystemEvent.getEventTime());
        Assert.assertNotNull(centerMindSystemEvent.getEventType());
        Assert.assertNotNull(centerMindSystemEvent.getId());
        Assert.assertNotNull(centerMindSystemEvent.getLocation());
        Assert.assertNotNull(centerMindSystemEvent.getDeviceCode());

        CAPAlarm capAlarm = conversionService.convert(centerMindSystemEvent, CAPAlarm.class);

        Assert.assertNotNull(capAlarm);
        Assert.assertNotNull(capAlarm.getStartBit());
        Assert.assertNotNull(capAlarm.getLocation());
        Assert.assertNotNull(capAlarm.getEventTime());
        Assert.assertNotNull(capAlarm.getAlarmCode());
        Assert.assertNotNull(capAlarm.getDeviceCode());
        Assert.assertNotNull(capAlarm.getLevel());
        Assert.assertNotNull(capAlarm.getSequenceNumber());
        Assert.assertNotNull(capAlarm.getSign());
        Assert.assertNotNull(capAlarm.getEndBit());
        Assert.assertNotNull(capAlarm.getSubSystemCode());
        Assert.assertNotNull(capAlarm.getReservedBytes());
        Assert.assertNotNull(capAlarm.getType());

        String s = capAlarm.getHexdecimalMessageString();
        Assert.assertTrue(s.split(" ").length ==27);
        LOGGER.info("Mail Hexdecimal String "+s);


    }






}
