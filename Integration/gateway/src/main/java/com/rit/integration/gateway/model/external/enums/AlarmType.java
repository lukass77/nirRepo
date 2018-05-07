package com.rit.integration.gateway.model.external.enums;

/**
 * Created by nirbo on 12/14/2015.
 */
public enum AlarmType {

    UNSCHEDULED_PORT_CONNECT("01", "unscheduled port connect","3"),
    UNSCHEDULED_PORT_DISCONNECT("02", "unscheduled port disconnect","4"),
    PVMAX_DEVICE_OFFLINE("03", "PVMax device offline","1"),
    PVMAX_DEVICE_ONLINE("04", "PVMax device online","2"),

    //EXPERIMENTAL
    PVPLUS_UNSCHEDULED_PORTS_DISCONNECTED("05", "PVPlus Unscheduled Ports Disconnected","5");

    private String typeHexValue;
    private String alarmTypeDes;
    private String alarmCode;


    AlarmType(String hexCode, String eventCode, String alarmCode) {
        this.typeHexValue = hexCode;
        this.alarmTypeDes = eventCode;
        this.alarmCode = alarmCode;
    }

    public String getTypeHexValue() {
        return typeHexValue;
    }

    public String getAlarmTypeDes() {
        return alarmTypeDes;
    }

    public String getAlarmCode() {
        return alarmCode;
    }

    public static AlarmType toEnum(String eventType) {
        AlarmType result = null;
        if (eventType.equals("01")) {
            result = UNSCHEDULED_PORT_CONNECT;
        } else if (eventType.equals("02")) {
            result = UNSCHEDULED_PORT_DISCONNECT;
        } else if (eventType.equals("03")) {
            result = PVMAX_DEVICE_OFFLINE;
        } else if (eventType.equals("04")) {
            result = PVMAX_DEVICE_ONLINE;
        }else if (eventType.equals("05")) {
            result = PVPLUS_UNSCHEDULED_PORTS_DISCONNECTED;
        }
        return result;
    }

    public static AlarmType enumByEventCode(String value) {
        AlarmType result = null;
        if (value.equals(UNSCHEDULED_PORT_CONNECT.getAlarmTypeDes())) {
            result = UNSCHEDULED_PORT_CONNECT;
        } else if (value.equals(UNSCHEDULED_PORT_DISCONNECT.getAlarmTypeDes())) {
            result = UNSCHEDULED_PORT_DISCONNECT;
        } else if (value.equals(PVMAX_DEVICE_OFFLINE.getAlarmTypeDes())) {
            result = PVMAX_DEVICE_OFFLINE;
        } else if (value.equals(PVMAX_DEVICE_ONLINE.getAlarmTypeDes())) {
            result = PVMAX_DEVICE_ONLINE;
        }else if (value.equals(PVPLUS_UNSCHEDULED_PORTS_DISCONNECTED.getAlarmTypeDes())) {
            result = PVPLUS_UNSCHEDULED_PORTS_DISCONNECTED;
        }
        return result;
    }
}
