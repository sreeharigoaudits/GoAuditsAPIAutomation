package com.goaudits.automation.framework.api.reporter;

import org.testng.ITestResult;

public class ReporterHelper {
    public static boolean validate(ITestResult result, NotificationDataHolder.NOTIFICATION_LEVEL notificationLevel, MODE mode) {
        int len = result.getParameters().length;
        String[] paramArray = new String[len];

        for (int i = 0; i < len; i++)
            paramArray[i] = result.getParameters()[i].toString();

        switch (mode) {
            case CONFIG:
                return true;
            case TEST:
                if (len < 3) {
                    if (notificationLevel != NotificationDataHolder.NOTIFICATION_LEVEL.ANY)
                        return false;
                    else
                        return true;
                } else {
                    NotificationDataHolder.NOTIFICATION_LEVEL level = NotificationDataHolder.NOTIFICATION_LEVEL.valueOf(paramArray[2]);
                    return notificationLevel.compareTo(level) >= 0;
                }
            default:
                return false;
        }
    }

    public enum MODE {
        CONFIG, TEST
    }
}