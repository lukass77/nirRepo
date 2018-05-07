package com.rit.integration.gateway.model.external;

import com.rit.integration.gateway.model.EventTime;
import com.rit.integration.gateway.model.external.enums.AlarmLevel;
import com.rit.integration.gateway.model.external.enums.AlarmType;

/**
 * Created by nirbo on 12/6/2015.
 * This class hold all needed info by CAP as a java model
 */
public class CAPAlarm {

    private String startBit;
    private String subSystemCode;
    private String location;
    private String deviceCode;
    private String sign;
    private AlarmType type;
    //alarm alarmCode depending on subsystem
    private String alarmCode;
    private AlarmLevel level;
    private String sequenceNumber;
    private EventTime eventTime;

    private String hexFormatWithSpacesWithOutEndBit;
    private String reservedBytes;
    private String endBit;
    private String hexdecimalMessageString;


    public CAPAlarm() {
    }

    public String getStartBit() {
        return startBit;
    }

    public void setStartBit(String startBit) {
        this.startBit = startBit;
    }

    public String getSubSystemCode() {
        return subSystemCode;
    }

    public void setSubSystemCode(String subSystemCode) {
        this.subSystemCode = subSystemCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public AlarmType getType() {
        return type;
    }

    public void setType(AlarmType type) {
        this.type = type;
    }

    public String getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(String alarmCode) {
        this.alarmCode = alarmCode;
    }

    public AlarmLevel getLevel() {
        return level;
    }

    public void setLevel(AlarmLevel level) {
        this.level = level;
    }

    public String getSequenceNumber() {
        return sequenceNumber.toUpperCase();
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public EventTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(EventTime eventTime) {
        this.eventTime = eventTime;
    }

    public String getReservedBytes() {
        return reservedBytes;
    }

    public void setReservedBytes(String reservedBytes) {
        this.reservedBytes = reservedBytes;
    }

    public String getEndBit() {
        return endBit;
    }

    public void setEndBit(String endBit) {
        this.endBit = endBit;
    }





    /**
     * build HexDecimal string format for Alarm message
     * build with spaces between each value inroder to calculate CAPAlarm.endBit value
     */
    public void calcHexStringWithTokenWithOutEndBit() {
        StringBuffer result = new StringBuffer();
        //TODO - SPLIT TO CHENK FO 2 DIGITS
        result.append(this.getStartBit());
        result.append(" ");
        result.append(this.getSubSystemCode());
        result.append(" ");
        result.append(this.getLocation());
        result.append(" ");
        result.append(getDeviceCode());
        result.append(" ");

        result.append(getSign());
        result.append(" ");

        result.append(getType().getTypeHexValue());
        result.append(" ");

        result.append(getAlarmCode());
        result.append(" ");

        result.append(getLevel().getHexCode());
        result.append(" ");
        result.append(getSequenceNumber());
        result.append(" ");


        EventTime eventTime = getEventTime();

        result.append(eventTime.yearFirstPartToHex());
        result.append(" ");
        result.append(eventTime.yearSecondPartToHex());
        result.append(" ");
        result.append(eventTime.monthToHex(eventTime.getMonth()));
        result.append(" ");
        result.append(eventTime.dayToHex(eventTime.getDay()));
        result.append(" ");
        result.append(eventTime.hourToHex(eventTime.getHour()));
        result.append(" ");
        result.append(eventTime.minuteToHex(eventTime.getMinute()));
        result.append(" ");
        result.append(eventTime.secondToHex(eventTime.getSecond()));
        result.append(" ");
        result.append(getReservedBytes());
        hexFormatWithSpacesWithOutEndBit = result.toString();
    }

    public String getHexFormatWithSpacesWithOutEndBit() {
        return hexFormatWithSpacesWithOutEndBit;
    }

    public void setHexdecimalMessageString(String hexdecimalMessageString) {
        this.hexdecimalMessageString = hexdecimalMessageString;
    }

    public String getHexdecimalMessageString() {
        return hexdecimalMessageString;
    }


    @Override
    public String toString() {
        return "CAPAlarm{" +
                "startBit='" + startBit + '\'' +
                ", subSystemCode='" + subSystemCode + '\'' +
                ", location='" + location + '\'' +
                ", deviceCode='" + deviceCode + '\'' +
                ", sign='" + sign + '\'' +
                ", type='" + type + '\'' +
                ", alarmCode='" + alarmCode + '\'' +
                ", level='" + level + '\'' +
                ", sequenceNumber='" + sequenceNumber + '\'' +
                ", eventTime=" + eventTime +
                ", reservedBytes='" + reservedBytes + '\'' +
                ", endBit='" + endBit + '\'' +
                '}';
    }


}
