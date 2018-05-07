package com.rit.integration.tcpserver.client;

import org.apache.log4j.Logger;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by nirbo on 11/22/2015.
 */
@Deprecated
public class TcpClient {

    private static final Logger _logger = Logger.getLogger(TcpClient.class);

    private String hostname;
    private int port;
    private Socket socketClient;

    public TcpClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void connect() throws IOException {
        _logger.info("Attempting to connect to " + hostname + ":" + port);
        socketClient = new Socket(hostname, port);
        _logger.info("Connection Established");
    }

    public byte[] readResponse() throws IOException {
        DataInputStream is = new DataInputStream(socketClient.getInputStream());
        byte[] inputData = null;
        try {
             inputData = new byte[1024];
            is.read(inputData);

        } catch (Exception e) {
            _logger.error(e);
        }
        return inputData;
    }





    public void keepAliveFromClient() throws IOException {

        byte[] s = DatatypeConverter.parseHexBinary("FAFAFA01");

        BufferedOutputStream dataOutputStream = new BufferedOutputStream(socketClient.getOutputStream());
        dataOutputStream.write(s);
        dataOutputStream.write('\n');
        dataOutputStream.flush();

      /*Writer out = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
        out.write("1001HELLOWORLD");
        out.flush();*/

        //dataOutputStream.write("keepAliveFromClient\r\n".getBytes());
        //dataOutputStream.close();
        /*BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
        writer.write("keepAliveFromClient");
        writer.newLine();
        writer.flush();*/
    }

    public static void main(String arg[]) {
        //Creating a SocketClient object
        TcpClient client = new TcpClient("localhost", 5679);
        try {
            //trying to establish connection to the server
            client.connect();
            client.keepAliveFromClient();

            while (true) {
                byte[] bytes = client.readResponse();
                _logger.info("Server response " + bytes);
            }

        } catch (UnknownHostException e) {
            _logger.error("Host unknown. Cannot establish connection");
        } catch (IOException e) {
            _logger.error("Cannot establish connection. Server may not be up." + e.getMessage());
        } catch (Exception e) {
            _logger.error(e);
        }
    }
}
