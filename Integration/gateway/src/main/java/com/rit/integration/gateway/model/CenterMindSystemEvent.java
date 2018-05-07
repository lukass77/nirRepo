package com.rit.integration.gateway.model;

import java.io.Serializable;

/**
 * Created by nirbo on 11/18/2015.
 */
public class CenterMindSystemEvent implements Serializable {

    private String id;
    private String eventSign;
    private String eventType;
    private String eventCode;
    private String eventLevel;
    private EventTime eventTime;
    private String content;
    private String deviceCode;
    private String location;

    public CenterMindSystemEvent() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public EventTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(EventTime eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventLevel() {
        return eventLevel;
    }

    public void setEventLevel(String eventLevel) {
        this.eventLevel = eventLevel;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventSign() {
        return eventSign;
    }

    public void setEventSign(String eventSign) {
        this.eventSign = eventSign;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "CenterMindSystemEvent{" +
                "id='" + id + '\'' +
                ", eventSign='" + eventSign + '\'' +
                ", eventType='" + eventType + '\'' +
                ", eventCode='" + eventCode + '\'' +
                ", eventLevel='" + eventLevel + '\'' +
                ", eventTime=" + eventTime +
                ", content='" + content + '\'' +
                ", deviceCode='" + deviceCode + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
