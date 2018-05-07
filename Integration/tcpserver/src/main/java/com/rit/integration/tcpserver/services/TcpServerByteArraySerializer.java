package com.rit.integration.tcpserver.services;


import org.apache.log4j.Logger;
import org.springframework.integration.ip.tcp.serializer.AbstractByteArraySerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by nirbo on 11/29/2015.
 * ByteArrayRawSerializer
 * ByteArrayLfSerializer
 */
public class TcpServerByteArraySerializer extends AbstractByteArraySerializer {


    private static final Logger _Logger = Logger.getLogger(TcpServerByteArraySerializer.class);


    public TcpServerByteArraySerializer() {
        setMaxMessageSize(27);
    }


    /**
     * For payload received in inbound channel - sent from client to server
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public byte[] deserialize(InputStream inputStream) throws IOException {
        _Logger.trace("start deserialize");
        byte[] result;
        try {
            byte[] buffer = new byte[getMaxMessageSize()];
                int numOfBytes = inputStream.read(buffer, 0, buffer.length);
            _Logger.trace("numOfBytes " + numOfBytes + " bytes in to buffer");
            result = copyToSizedArray(buffer, numOfBytes);
        } catch (IOException e) {
            _Logger.error("Exception on deserialize tcp inbound stream ", e);
            //publishEvent(e, , n);
            throw e;
        }
        return result;

    }


    /**
     * For payload going to outbond - sent from server to client
     *
     * @param bytes
     * @param outputStream
     * @throws IOException
     */
    public void serialize(byte[] bytes, OutputStream outputStream) throws IOException {
        _Logger.trace("begin serialize:" + bytes);
        outputStream.write(bytes);
    }





}