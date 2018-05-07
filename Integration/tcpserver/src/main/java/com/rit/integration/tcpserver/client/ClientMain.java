package com.rit.integration.tcpserver.client;



import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * Created by nirbo on 11/18/2015.
 */
@Deprecated
public class ClientMain {


    /*public static void main(String[] args) {

        GenericXmlApplicationContext genericXmlApplicationContext = ClientMain.setupContext();
        SimpleGateway gw = (SimpleGateway) genericXmlApplicationContext.getBean("gw");
       // MessageBuilder.createMessage()
        String response = gw.send("akfjkdsfjklsdfjkldsjfasdf");
        System.out.println(response);
//
    }
*/

    public static GenericXmlApplicationContext setupContext() {
        final GenericXmlApplicationContext context = new GenericXmlApplicationContext();

             context.load("classpath:com/rit/integration/tcpClient-context.xml");
        context.registerShutdownHook();
        context.refresh();

        return context;
    }


}
