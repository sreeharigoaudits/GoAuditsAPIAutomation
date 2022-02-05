package com.goaudits.automation.framework.api.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import io.qameta.allure.Step;

import java.util.Map;

public class AllureAppender extends AppenderBase {

    @Step("Log - {0}")
    private static void logAllure(String message) {
    }

    @Override
    protected void append(Object object) {
        if (object instanceof ILoggingEvent) {
            ILoggingEvent loggingEvent = (ILoggingEvent) object;
            Map<String, String> mdc = loggingEvent.getMDCPropertyMap();
            logAllure(loggingEvent.getFormattedMessage() + " - " + mdc.get("test.id"));
        }
    }
}