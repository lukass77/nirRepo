package com.rit.integration.gateway;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import javax.mail.*;
import java.util.Properties;

/**
 * Created by nirbo on 12/13/2015.
 */
@Ignore(value = "must have a running mail server")
public class TestMailServer {

    public static Logger LOGGER = Logger.getLogger(TestMailServer.class);


    @Test
    public void readMainTest(){

        Properties props = System.getProperties();

        /*props.setProperty("pop3.debug", "true");*/

        props.setProperty("mail.store.protocol", "pop3");
        props.setProperty( "mail.pop3.host", "127.0.0.1" );
        props.setProperty( "mail.pop3.port", "110");



        try {
            Session session = Session.getDefaultInstance(props);
            session.setDebug(true);
            String mailBox = "pop3://test:test@127.0.0.1/INBOX";
            URLName urlName = new URLName(mailBox);

            Store store = session.getStore(urlName);
            if(!store.isConnected()){//
                store.connect();
                //store.connect("127.0.0.1","test","test");
                LOGGER.info(store);
                Folder defaultFolder = store.getDefaultFolder();
                String fullName = defaultFolder.getFullName();
                Folder inbox = defaultFolder.getFolder("INBOX");
                inbox.open(Folder.READ_WRITE);
                Message[] messages = inbox.getMessages();
                for(Message message:messages) {
                    LOGGER.info("---------------------------------");
                    //LOGGER.info("Email Number " + (i + 1));
                    LOGGER.info("Subject: " + message.getSubject());
                    LOGGER.info("From: " + message.getFrom()[0]);
                    LOGGER.info("Text: " + message.getContent().toString());
                    

                }

            }
        }  catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }

    }
}
