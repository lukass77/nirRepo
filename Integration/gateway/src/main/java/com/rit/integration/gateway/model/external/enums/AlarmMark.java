package com.rit.integration.gateway.model.external.enums;

/**
 * Created by nirbo on 12/7/2015.
 */
public enum AlarmMark {

    ELIMINATE("00"),HAPPEN("FF");

    private final String hexVal;

    AlarmMark(String hexVal) {
        this.hexVal = hexVal;
    }

    public String getHexVal(){
        return hexVal;
    }
}
