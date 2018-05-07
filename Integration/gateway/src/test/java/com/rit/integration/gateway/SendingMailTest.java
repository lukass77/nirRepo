package com.rit.integration.gateway;

import org.junit.Ignore;
import org.junit.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by nirbo on 12/14/2015.
 */
@Ignore
public class SendingMailTest {


    @Test
    public void sendmailTest() {

        String user = "test";
        String password = "test";

        String fromAddress = "test@cmdomain.com"; // newlycreateduser@localhost
        String toAddress = "test@cmdomain.com";


        // Create a mail session
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "127.0.0.1");
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.username", user);
        properties.put("mail.smtp.password", password);
        properties.put("mail.transport.protocol", "smtp");
        Session session = Session.getDefaultInstance(properties, null);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromAddress));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));

            message.setSubject("Email from our JAMES Server");
            //message.setText("Luke, I'm your father!!");

            String txt = "Event Code : PVPlus Unscheduled Ports Disconnected\r\nDate/Time  :  12/21/2015 17:13\r\nSource : \r\n " +
                    "User : Administrator\r\nComputer : fe80::bda2:dde3:9c09:4022%10\r\nDescription :  unscheduled port connect\r\n" +
                    "WorkOrderId : 1000003\r\nWorkOrderName : WO-21/12/2015-00007\r\nWorkOrderPriority : 1\r\n\r\n";
            message.setText(txt);
            Transport.send(message);

            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
