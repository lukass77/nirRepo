package com.rit.integration.gateway.model.external.enums;

/**
 * Created by nirbo on 12/7/2015.
 */
public enum AlarmLevel {

    MINOR("01"),MAJOR("02"),FATAL("03");

    private final String hexCode;

    AlarmLevel(String hexCode) {
        this.hexCode = hexCode;
    }

    public String getHexCode() {
        return hexCode;
    }

    public static AlarmLevel toEnum(String eventLevel) {
        AlarmLevel result = null;
        if(eventLevel.equals("01")){
            result = MINOR;
        }else if(eventLevel.equals("02")){
            result = MAJOR;
        }else {
            result = FATAL;
        }
        return result;
    }
}
