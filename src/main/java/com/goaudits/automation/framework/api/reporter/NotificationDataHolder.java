package com.goaudits.automation.framework.api.reporter;


import com.goaudits.automation.framework.api.fileHandlers.PropertyFile;
import com.goaudits.automation.framework.api.utils.PathUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NotificationDataHolder {
    static boolean isSlackNotificationEnabled;
    static boolean isMailNotificationEnabled;
    static List<String> mailNotificationRecipients;
    static NOTIFICATION_LEVEL mailNotificationLevel;
    private static boolean executed = false;

    public static void init() throws IOException {
        if (executed) {
            return;
        }

        String executionMode = System.getenv("EXECUTION_MODE");
        String slackNotificationEnabled, slackNotificationLevelStr;
        String mailNotificationEnabled, mailNotificationRecipientsStr, mailNotificationLevelStr;

        if (executionMode != null && executionMode.equalsIgnoreCase("JENKINS")) {
            slackNotificationEnabled = System.getenv("SLACK_NOTIFICATION_ENABLED");
            slackNotificationLevelStr = System.getenv("SLACK_NOTIFICATION_LEVEL");

            mailNotificationEnabled = System.getenv("EMAIL_NOTIFICATION_ENABLED");
            mailNotificationLevelStr = System.getenv("EMAIL_NOTIFICATION_LEVEL");
            mailNotificationRecipientsStr = System.getenv("EMAIL_NOTIFICATION_RECIPIENTS");
        } else {
            PropertyFile configFile = new PropertyFile(PathUtils.getTestResourcesPath() + "notification.properties");
            mailNotificationEnabled = configFile.getProperty("email.notification");
            mailNotificationLevelStr = configFile.getProperty("email.notification.level");
            mailNotificationRecipientsStr = configFile.getProperty("email.notification.recipients");
        }

        if (mailNotificationEnabled == null) {
            mailNotificationEnabled = "false";
        }
        if (mailNotificationLevelStr == null) {
            mailNotificationLevelStr = "NONE";
        }
        if (mailNotificationRecipientsStr == null) {
            mailNotificationRecipientsStr = "";
        }

        isMailNotificationEnabled = mailNotificationEnabled.equalsIgnoreCase("true");
        mailNotificationLevel = NOTIFICATION_LEVEL.valueOf(mailNotificationLevelStr);

        String[] recipientsArr = mailNotificationRecipientsStr.split(",");
        mailNotificationRecipients = Arrays.stream(recipientsArr)
                .filter(recipient -> EmailValidator.getInstance().isValid(recipient))
                .collect(Collectors.toList());

        executed = true;
    }

    public enum NOTIFICATION_LEVEL {
        NONE("NONE"), BLOCKER("BLOCKER"), CRITICAL("CRITICAL"), NORMAL("NORMAL"), MINOR("MINOR"), TRIVIAL("TRIVIAL"), ANY("ANY");

        private final String value;

        NOTIFICATION_LEVEL(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}