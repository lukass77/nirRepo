package com.rit.integration.tcpserver.server;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by nirbo on 11/19/2015.
 */
@Deprecated
public class TCPServer {

    private final static Logger _logger = Logger.getLogger(TCPServer.class);

    private static TCPServer tcpServer;

    private String newMsg;

    private static final int PORT = 5679;

    private TCPServer() {
    }

    public static final TCPServer getInstance() {
        if (tcpServer == null) {
            tcpServer = new TCPServer();
        }
        return tcpServer;
    }




    public void start() {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            _logger.info("Server is waiting to requests");
            clientSocket = serverSocket.accept();
            while (true) {

                if (clientSocket != null) {

                    if (tcpServer.getNewMsg() != null) {
                        new Thread(new ClientRequestHandlerThread(clientSocket, TCPServer.getInstance())).start();
                    }
                }
            }
        } catch (IOException e) {
            _logger.error("Server init error ", e);
            e.printStackTrace();

        }

    }

    public synchronized String getNewMsg() {
        return newMsg;
    }

    public synchronized void setNewMsg(String newMsg) {
        this.newMsg = newMsg;
    }


    public class ClientRequestHandlerThread implements Runnable {

        private Socket clientSocket;
        private TCPServer tcpServer;

        public ClientRequestHandlerThread(Socket socket, TCPServer tcpServer) {
            this.clientSocket = socket;
            this.tcpServer = tcpServer;
        }

        public void run() {
            BufferedReader in = null;
            DataOutputStream out = null;
            String serverResponse = null;
            try {
                synchronized (this) {
                    if (clientSocket.isConnected() && !clientSocket.isClosed()) {
                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        out = new DataOutputStream(clientSocket.getOutputStream());
                        if ((serverResponse = tcpServer.getNewMsg()) != null) {
                            serverResponse = serverResponse + "\n";
                            tcpServer.setNewMsg(null);

                            out.writeBytes(serverResponse);
                        }
                        out.flush();
                        //out.close();
                        //out.writeBytes("l);

                    }
                }


            } catch (Throwable e) {
                e.printStackTrace();
                _logger.error(e);
              /*  try {
                    clientSocket.close();
                } catch (IOException e1) {

                }*/
            }finally {
               /* try {
                    out.close();
                } catch (IOException e) {

                }*/
            }

        }
    }


    public static class Producer extends Thread {

        private TCPServer tcpServer;
        private String msg;

        public Producer(TCPServer tcpServer, String s) {
            this.tcpServer = tcpServer;
            this.msg = s;

        }

        @Override
        public void run() {
            int i = 0;
            while (true) {
                tcpServer.setNewMsg(msg+(i++));
                try {
                    Thread.sleep(1000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }


    }

    public static void main(String args[]) throws InterruptedException {
        final TCPServer instance = TCPServer.getInstance();
        new Producer(instance, "**** NEW MESSAGE ***").start();

        instance.start();



    }
}
