package com.rit.integration.gateway;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import javax.mail.*;
import java.util.Properties;

/**
 * Created by nirbo on 12/13/2015.
 * Need to start pop3 server before running this test
 */
@Ignore(value = "must have a running mail server")
public class TestMailPop3Server {

    public static final Logger LOGGER = Logger.getLogger(TestMailPop3Server.class);

    @Test
    public void readMainPop3Test() throws Exception {


        Properties props = System.getProperties();

        /*props.setProperty("pop3.debug", "true");*/
        props.setProperty("mail.store.protocol", "pop3");
        props.setProperty("mail.pop3.host", "127.0.0.1");
        props.setProperty("mail.pop3.user", "test");
        //props.setProperty("mail.user", "coa-web");
        props.setProperty("mail.pop3.password", "test");


        try {
            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(true);
            String mailBox = "pop3://test:test@127.0.0.1/INBOX";
            URLName urlName = new URLName(mailBox);

            Store store = session.getStore(urlName);
            LOGGER.info(store);
            if (!store.isConnected()) {
                store.connect();
                Folder inbox = store.getFolder("INBOX");
                inbox.open(Folder.READ_WRITE);
                Message[] messages = inbox.getMessages();
                for (Message message : messages) {
                    LOGGER.info(message);
                    LOGGER.info(message.getSubject());
                    String content = (String) message.getContent();
                    LOGGER.info(content);

                }

            }

        } catch (javax.mail.MessagingException e) {
            LOGGER.error("Check mail server is running !!", e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error on "+this.getClass().getName(), e);
            throw e;
        }


    }
}
