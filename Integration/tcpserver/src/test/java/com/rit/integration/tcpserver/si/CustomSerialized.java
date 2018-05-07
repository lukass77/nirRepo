package com.rit.integration.tcpserver.si;


import org.apache.log4j.Logger;
import org.springframework.integration.ip.tcp.serializer.ByteArrayLfSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by nirbo on 11/29/2015.
 * ByteArrayRawSerializer
 * ByteArrayLfSerializer
 */
@Deprecated
public class CustomSerialized extends ByteArrayLfSerializer {


    private static final Logger logger = Logger.getLogger(CustomSerialized.class);


    public byte[] deserialize(InputStream inputStream) throws IOException {
        logger.debug("start deserialize");

        /*java.util.Scanner s = new java.util.Scanner(inputStream).useDelimiter("\\A");
        String tmp =  s.hasNext() ? s.next() : "";*/

        //  String tmp = IOUtils.toString(inputStream, "UTF-8");

        //  tmp = tmp+'\n';
        /*byte[] srcByteArray = inputSteamToByteArray(inputStream);
        // byte[] srcByteArray = IOUtils.toByteArray(inputStream);
        byte[] modified = new byte[srcByteArray.length+1];
        System.arraycopy(modified, 0, srcByteArray, 0, srcByteArray.length);
        modified[srcByteArray.length+1] = '\n';
        InputStream is = new ByteArrayInputStream(modified);*/
        //  InputStream is = new ByteArrayInputStream(tmp.getBytes());
        byte[] result = super.deserialize(inputStream);


        // byte[] result = super.deserialize(inputStream);
        /*logger.debug("begin deserialize");
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[this.getMaxMessageSize()];

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }*/
        logger.debug("done deserialize");
        return result;
    }

    private byte[] inputSteamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[this.maxMessageSize]; // you can configure the buffer size
        int length;

        while ((length = inputStream.read(buffer)) != -1) {
            out.write(buffer, 0, length); //copy streams
        }
        byte[] result = out.toByteArray();
        out.close();
        return result;
    }


    public void serialize(byte[] bytes, OutputStream outputStream) throws IOException {
        logger.debug("begin serialize:" + bytes);
        super.serialize(bytes, outputStream);

    }


}