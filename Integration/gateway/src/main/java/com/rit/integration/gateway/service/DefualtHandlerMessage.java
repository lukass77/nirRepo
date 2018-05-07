package com.rit.integration.gateway.service;

import com.rit.integration.gateway.ifc.GateWayMessageHandler;
import org.apache.log4j.Logger;

/**
 * Created by nirbo on 12/2/2015.
 */
@Deprecated
public class DefualtHandlerMessage implements GateWayMessageHandler<String> {

    private static final Logger _logger = Logger.getLogger(DefualtHandlerMessage.class);

    public void handleMessage(String message) {
        _logger.debug("received new message");

    }
}
