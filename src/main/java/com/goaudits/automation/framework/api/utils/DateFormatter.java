package com.goaudits.automation.framework.api.utils;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class DateFormatter {
    private SimpleDateFormat formatter;

    public DateFormatter() {
        setFormatter(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));
        this.formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public DateFormatter(String format) {
        setFormatter(new SimpleDateFormat(format));
        this.formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public SimpleDateFormat getFormatter() {
        return formatter;
    }

    public void setFormatter(SimpleDateFormat formatter) {
        this.formatter = formatter;
    }
}