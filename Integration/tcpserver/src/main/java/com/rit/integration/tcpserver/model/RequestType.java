package com.rit.integration.tcpserver.model;

/**
 * Created by nirbo on 12/9/2015.
 */
public enum RequestType {

    REQUEST_PACKAGE("FAFAFA"),HANDSHAKE_PACKAGE("FFFFFF"),ACK_ALARM("7E7E7E");

    private final String requestPrefix;

    RequestType(String requestPrefix) {
        this.requestPrefix = requestPrefix;
    }

    public String getRequestPrefix() {
        return requestPrefix;
    }


}
