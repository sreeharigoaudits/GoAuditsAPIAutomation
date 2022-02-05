package com.goaudits.automation.framework.api.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class DateIterator implements Iterator<Date>, Iterable<Date> {
    private Calendar end = Calendar.getInstance();
    private Calendar current = Calendar.getInstance();

    public DateIterator(Date start, Date end)
    {
        this.end.setTime(end);
        this.end.add(Calendar.DATE, -1);
        this.current.setTime(start);
        this.current.add(Calendar.DATE, -1);
    }

    @Override
    public boolean hasNext()
    {
        return !current.after(end);
    }

    @Override
    public Date next()
    {
        current.add(Calendar.DATE, 1);
        return current.getTime();
    }

    public Date current()
    {
        return current.getTime();
    }

    @Override
    public void remove()
    {
        throw new UnsupportedOperationException(
                "Cannot remove");
    }

    @Override
    public Iterator<Date> iterator()
    {
        return this;
    }
}
