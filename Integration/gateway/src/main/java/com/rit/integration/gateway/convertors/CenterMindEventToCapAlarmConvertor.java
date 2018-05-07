package com.rit.integration.gateway.convertors;

import com.rit.integration.gateway.model.CenterMindSystemEvent;
import com.rit.integration.gateway.model.external.CAPAlarm;
import com.rit.integration.gateway.model.external.enums.AlarmLevel;
import com.rit.integration.gateway.model.external.enums.AlarmType;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by nirbo on 12/6/2015.
 */
public class CenterMindEventToCapAlarmConvertor implements Converter<CenterMindSystemEvent, CAPAlarm>{

    //HEXDECIMAL Msg from subsystem to CAP
    //private final static String mockMsg = "7E7E7E010102030A1001FF01100101000514 0A05140A2210 0000 26";

    private final String reservedBytes;
    private final String subSystemCode;
    private String startBit;


    public CenterMindEventToCapAlarmConvertor(String startBit,String reservedBytes,String subSystemCode) {
        this.startBit = startBit;
        this.reservedBytes= reservedBytes;
        this.subSystemCode = subSystemCode;
    }

    /**
     * Make sure all values are 2 digits and have space between them - like xx yy zz
     * This method convert CenterMindSystemEvent to CAPAlarm
     * @param centerMindSystemEvent
     * @return
     */
    public CAPAlarm convert(CenterMindSystemEvent centerMindSystemEvent) {
        CAPAlarm result = new CAPAlarm();
        //CONST
        result.setStartBit(this.startBit);
        result.setSubSystemCode(this.subSystemCode);

        //TODO - CALC  all those values from CenterMindSystemEvent
        result.setLocation(centerMindSystemEvent.getLocation());
        //result.setDeviceCode("10 01");
        result.setDeviceCode(calcHexTwoByteNumber(centerMindSystemEvent.getDeviceCode()));
        result.setSign(centerMindSystemEvent.getEventSign());
        //01
        result.setType(AlarmType.toEnum(centerMindSystemEvent.getEventType()));
        //10 01
        //TODO - need to transform this value to hexvalue //2 bytes
        result.setAlarmCode(calcHexTwoByteNumber(centerMindSystemEvent.getEventCode()));
        //01 - normal alarm - need to calc
        result.setLevel(AlarmLevel.toEnum(centerMindSystemEvent.getEventLevel()));
        result.setSequenceNumber(calcHexTwoByteNumber(centerMindSystemEvent.getId()));
        result.setEventTime(centerMindSystemEvent.getEventTime());

        result.setReservedBytes(this.reservedBytes);

        result.calcHexStringWithTokenWithOutEndBit();

        //calc endBit
        String hexFormatWithSpacesWithOutEndBit = result.getHexFormatWithSpacesWithOutEndBit();
        String endBit = calcEndBit(hexFormatWithSpacesWithOutEndBit);
        result.setEndBit(endBit);

        //calc string representation of hexdecimal message
        StringBuffer buffer = new StringBuffer(hexFormatWithSpacesWithOutEndBit);
        buffer.append(" ");
        buffer.append(result.getEndBit());

        result.setHexdecimalMessageString(buffer.toString());


        return result;


    }

    /**
     *
     * @param id - String represent a number
     * @return - Hexdecimal value - 2 bytes (xx yy)
     */
    private String calcHexTwoByteNumber(String id) {
        Integer integer = Integer.valueOf(id);
        String result = Integer.toHexString(integer);
        int length = result.length();
        if(length == 1){
            result = "000"+ result;
        }else if(length == 2){
            result = "00"+result;
        }else if(length == 3){
            result = "0"+result;
        }
        //add space in the middle
        String first = result.substring(0, 2).toUpperCase();
        String second = result.substring(2, 4).toUpperCase();
        return first +" "+second;

    }

    /**
     * This method CALC end bit - sum up all hexvlaues 2 bit for each attribute
     * discord reserved bytes
     * Note - in case endbit calculation is wrong CAP will not be able to confirm excepting .
     * @param hexString
     * @return
     */
    private String calcEndBit(String hexString) {
        String result;
        int decimalResult = 0;
        String[] split = hexString.split(" ");
        for (int i = 0; i < split.length - 2; i++) {
            String s = split[i];
            int current = Integer.parseInt(s.trim(), 16);
            decimalResult = decimalResult + current;

        }
        result = Integer.toHexString(decimalResult);
        if (result.length() == 3) {
            result = "0" + result;
        }
        String substring = result.substring(2, 4).toUpperCase();
        return substring;
    }

}
