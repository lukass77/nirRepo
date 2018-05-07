package com.rit.integration.tcpserver.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created by nirbo on 11/19/2015.
 */
@Deprecated
public class TcpServerSimple {


    public static void main(String[] args) throws Exception {
        DataInputStream is;
        DataOutputStream os;
        int port = 5679;
        ServerSocket serverSocket = new ServerSocket(port);

        System.out.println("Server Socket is Running...");
        System.out.println("Server is waiting for Connections");

        Socket clientSocket = serverSocket.accept();

        is = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
        os = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));

        // Running continuously in-order to process Client Request
        while (true) {
            // Fetches the Client request
            byte[] byteData = receiveData(is);
            String clientRequestMessage = new String(byteData).trim();
            System.out.println("Received Message = " + clientRequestMessage);

            // Processing Client request
            String clientData = doProcess(clientRequestMessage);

            // Sending Response to Client
            sendData(os, clientData.getBytes());
            System.out.println("Response Message sent to Client = " + clientData);
        }
    }

    /**
     * Add you Custom Business Logic here
     *
     * @param message Client Request Message
     * @return Server Response Message
     */
    public static String doProcess(String message) {
        System.out.println("Processing the Client Request...");
        if (message.equals("CLIENT_REQUEST:SEND_SYSTEM_TIME")) {
            Date date = new Date(System.currentTimeMillis());
            return date.toString();
        } else {
            return "SERVER-ERROR:INVALID_REQUEST";
        }
    }

    /**
     * Method receives the Client Request
     */
    public static byte[] receiveData(DataInputStream is) throws Exception {
        try {
            byte[] inputData = new byte[1024];
            is.read(inputData);
            return inputData;
        } catch (Exception exception) {
            throw exception;
        }
    }

    /**
     * Method used to Send Response to Client
     */
    public static synchronized void sendData(DataOutputStream os, byte[] byteData) {
        if (byteData == null) {
            return;
        }
        try {
            os.write(byteData);
            os.flush();
        } catch (Exception exception) {
        }
    }
}



