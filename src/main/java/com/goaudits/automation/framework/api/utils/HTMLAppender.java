package com.goaudits.automation.framework.api.utils;


import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.testng.Reporter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class HTMLAppender extends AppenderBase {

    @Override
    protected void append(Object object) {
        if (object instanceof ILoggingEvent) {
            ILoggingEvent loggingEvent = (ILoggingEvent) object;
            Map<String, String> mdc = loggingEvent.getMDCPropertyMap();

            Timestamp timestamp = new Timestamp(loggingEvent.getTimeStamp());
            Date date = new Date(timestamp.getTime());
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");

            Reporter.log("<br/><b><font color=\"#ff0000\">" + sdfDate.format(date) + " " + loggingEvent.getFormattedMessage() + " - " + mdc.get("test.id") + "</font></b>");
        }
    }
}