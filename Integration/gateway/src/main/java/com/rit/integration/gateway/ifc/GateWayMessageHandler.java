package com.rit.integration.gateway.ifc;

/**
 * Created by nirbo on 12/2/2015.
 */
@Deprecated
public interface GateWayMessageHandler<T> {

    void handleMessage(T message);

}
