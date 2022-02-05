package com.goaudits.automation.framework.api.reporter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.*;
import org.testng.internal.Utils;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

public class EmailReporter implements ITestListener, IConfigurationListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailReporter.class);

    public void onTestStart(ITestResult result) {
    }

    public void onTestSuccess(ITestResult result) {
    }

    public void onTestSkipped(ITestResult result) {
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    public void onStart(ITestContext context) {
    }

    public void onFinish(ITestContext context) {
    }

    public void onConfigurationSkip(ITestResult result) {
    }

    public void onConfigurationSuccess(ITestResult result) {
    }

    @Override
    public void onConfigurationFailure(ITestResult result) {
        if (NotificationDataHolder.isMailNotificationEnabled) {
            if (ReporterHelper.validate(result, NotificationDataHolder.NOTIFICATION_LEVEL.ANY, ReporterHelper.MODE.CONFIG)) {
                sendEmail(result, ReporterHelper.MODE.CONFIG);
            }
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (NotificationDataHolder.isMailNotificationEnabled) {
            if (ReporterHelper.validate(result, NotificationDataHolder.mailNotificationLevel, ReporterHelper.MODE.TEST)) {
                if (!NotificationDataHolder.mailNotificationRecipients.isEmpty())
                    sendEmail(result, ReporterHelper.MODE.TEST);
            }
        }
    }

    private void sendEmail(ITestResult result, ReporterHelper.MODE mode) {
        int len = result.getParameters().length;
        String[] paramArray = new String[len];

        for (int i = 0; i < len; i++)
            paramArray[i] = result.getParameters()[i].toString();

        StringBuilder htmlMessage = new StringBuilder();
        String fromName = "GoAudits Automation";
        String from = "sreehari4a4@gmail.com";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("sreehari4a4@gmail.com", "prathap4A0@");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from, fromName));
            for (String mailNotificationRecipient : NotificationDataHolder.mailNotificationRecipients)
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailNotificationRecipient));
            message.setSubject("Instant E-Mail : Blocker Failure in GoAudits API Automation");

            htmlMessage.append("Hi,<br><br>");
            htmlMessage.append("This is an instant email for a critical failure in the GoAudits API Automation.<br><br>");
            htmlMessage.append("<b>Class Name :</b> ").append(result.getInstanceName()).append("<br>");
            htmlMessage.append("<b>Method Name :</b> ").append(result.getMethod().getMethodName()).append("<br>");
            if (mode == ReporterHelper.MODE.TEST && paramArray.length >= 1) {
                htmlMessage.append("<b>Test Case Name :</b> ").append(paramArray[0]).append("<br>");
            }
            htmlMessage.append("<b>Duration :</b> ").append((result.getEndMillis() - result.getStartMillis())).append(" ms<br><br>");

            htmlMessage.append("<b>Console Report :</b><br><br>");

            List<String> reporterMessages = Reporter.getOutput(result);
            htmlMessage.append(writeReporterMessages(reporterMessages));

            htmlMessage.append("<br><br><b>Failure Exception :</b><br><br>");
            htmlMessage.append(writeStackTrace(result));

            htmlMessage.append("<br><br>");
            htmlMessage.append("Best regards,<br>");
            htmlMessage.append("The GoAudits Team");

            message.setContent(htmlMessage, "text/html; charset=utf-8");
            Transport.send(message);
            LOGGER.error("Critical Failure - Instant EMail sent with exception details.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String writeReporterMessages(List<String> reporterMessages) {
        StringBuilder messages = new StringBuilder();
        for (String reporterMessage : reporterMessages) messages.append(reporterMessage).append("<br>");

        return messages.toString();
    }

    private String writeStackTrace(ITestResult result) {
        return Utils.escapeHtml(Utils.shortStackTrace(result.getThrowable(), true)).replaceAll("\n", "<br>");
    }
}