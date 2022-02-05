package com.goaudits.automation.framework.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import javax.mail.*;
import java.util.Properties;

public class MailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);
    private Session session;
    private Store store;
    private Folder folder;
    private String host;

    public MailService(String host) {
        this.host = host;
    }

    public boolean isLoggedIn() {
        return store.isConnected();
    }

    public void login(String username, String password) throws MessagingException {
        String file = "INBOX";
        String protocol = "imaps";
        URLName url = new URLName(protocol, host, 993, file, username, password);

        if (session == null) {
            Properties props;
            try {
                props = System.getProperties();
            } catch (SecurityException sex) {
                props = new Properties();
            }
            session = Session.getInstance(props, null);
        }
        store = session.getStore(url);
        store.connect();
        folder = store.getFolder(url);
        folder.open(Folder.READ_WRITE);
    }

    public void logout() throws MessagingException {
        folder.close();
        store.close();
        store = null;
        session = null;
    }

    public int getMessageCount() throws MessagingException {
        return folder.getMessageCount();
    }

    public Message[] getMessages() throws MessagingException {
        return folder.getMessages();
    }

    public void clearMessages() throws MessagingException {
        Message[] messages = getMessages();
        for (Message message : messages) {
            message.setFlag(Flags.Flag.DELETED, true);
        }
        folder.close(true);
        folder.open(Folder.READ_WRITE);
    }

    private boolean checkForEmail(String subject, long maxWaitingTimeInSecs) throws MessagingException, InterruptedException {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + (maxWaitingTimeInSecs * 1000);
        boolean flag = false;

        do {
            Message[] messages = getMessages();
            for (Message message : messages) {
                if (message.getSubject().contains(subject)) {
                    flag = true;
                    break;
                }
            }

            if (flag) {
                LOGGER.info("Email found with subject = \t{}", subject);
            } else {
                LOGGER.info("Email not found with subject = \t{}", subject);
                Thread.sleep(5000);
            }
        } while (System.currentTimeMillis() < endTime && !flag);

        return flag;
    }

    public void assertContainsEmail(String subject, long maxWaitingTimeInSecs) throws MessagingException, InterruptedException {
        Assert.assertTrue(checkForEmail(subject, maxWaitingTimeInSecs), "Email not received with subject : " + subject);
    }

    public void assertNotContainsEmail(String subject, long maxWaitingTimeInSecs) throws MessagingException, InterruptedException {
        Assert.assertFalse(checkForEmail(subject, maxWaitingTimeInSecs), "Email received with subject : " + subject);
    }
}