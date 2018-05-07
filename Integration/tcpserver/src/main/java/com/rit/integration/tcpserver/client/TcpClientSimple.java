package com.rit.integration.tcpserver.client;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;

/**
 * Created by nirbo on 11/18/2015.
 */
@Deprecated
public class TcpClientSimple {

    private static final Logger _logger = Logger.getLogger(TcpClientSimple.class);

    public static void main(String[] args) throws Exception {
        _logger.info("Client Started...");

        Socket clientSocket = new Socket("localhost", 5679);
      //  BufferedReader is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      //  DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());

        // Create Client Request
      //  String request = "CLIENT_REQUEST:SEND_SYSTEM_TIME\n";

        // Send Client Request to Server
       // os.writeBytes(request);
        // send(os, request.getBytes());
        //_logger.info("Data sent to Server ; Message = " + request);

        try {
            for (int i = 0; i <1000; i++) {
                ClientHandler stam = new ClientHandler(clientSocket, null);
                stam.start();
            }
                // Receive Server Response
               // String byteData = receive(is);

                //_logger.info("Server Response = " + byteData.trim());

        } catch (Exception e) {
            _logger.info("Exception: " + e.getMessage());
        } /*finally {
            clientSocket.close();
        }*/
        System.in.read();
    }

    /**
     * Method receives the Server Response
     *
     * @param is
     */
   /* public static String receive(BufferedReader is) throws Exception {

        return is.readLine();

    }*/

    /**
     * Method used to Send Request to Server
     */
  /*  public static void send(DataOutputStream os, byte[] byteData) throws Exception {
        try {
            os.write(byteData);
            os.flush();
        } catch (Exception exception) {
            throw exception;
        }
    }*/


    public static class ClientHandler extends Thread {

        private Socket clientSocket;
        private String messege;

        public ClientHandler(Socket clientSocket,String message) {
            this.clientSocket = clientSocket;
            this.messege = message;
        }

        public void run() {
            DataOutputStream os = null;
            BufferedReader is = null;
            String serverResponse = null;
            try {
                synchronized (this) {
                  //  os = new DataOutputStream(clientSocket.getOutputStream());
                    is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


                    Writer out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    out.write("1001HELLOWORLD");
                    out.flush();

                    //sending request to server
                   // os.writeBytes(messege);
                    //getting response from server
                    _logger.info("client is waiting to msg from server...");
                  while((serverResponse = is.readLine()) != null){
                      _logger.info("Server Response = " + serverResponse);
                  }



                }


            } catch (IOException e) {
                e.printStackTrace();
            }/*finally {

                try {
                    is.close();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/


        }
    }
}
