package com.goaudits.automation.framework.api.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DateUtils {
    public static String randomDate(String startDateStr, String endDateStr) throws ParseException {
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);

        Date randomDate = new Date(ThreadLocalRandom.current().nextLong(startDate.getTime(), endDate.getTime()));
        return new SimpleDateFormat("yyyy-MM-dd").format(randomDate);
    }

    public static String getMinAgeDate() throws ParseException {
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.YEAR, -18); // to get previous 18 years add -18

        String minAgeDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        return minAgeDate;
    }

    public static String getMaxAgeDate() throws ParseException {
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.YEAR, -30); // to get previous 30 years add -30

        String maxAgeDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        return maxAgeDate;
    }

    public static String getCurrentDate() {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        return currentDate;
    }

    public static boolean validateLessThanCurrentDate(String date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        Date reqDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        Date today = cal.getTime();

        if(reqDate.before(today))
            return true;
        else
            return false;
    }

    public static String getCurrentDateTime() {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        return currentDate;
    }

    public static String getCurrentDateTimeInUTC() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String currentDate = simpleDateFormat.format(Calendar.getInstance().getTime());
        return currentDate;
    }

    public static String generateValidDate() {
        Date startDate = new GregorianCalendar(1990, Calendar.FEBRUARY, 11).getTime();
        Date endDate = new GregorianCalendar(2030, Calendar.FEBRUARY, 11).getTime();
        long random = ThreadLocalRandom.current().nextLong(startDate.getTime(), endDate.getTime());

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

        Date date = new Date(random);
        Instant instant = date.toInstant();
        ZonedDateTime randomDate = ZonedDateTime.ofInstant(instant, ZoneId.of("UTC-06:00"));
        System.out.println(randomDate.format(format));
        return randomDate.format(format);
    }

    public static Date addDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);

        return cal.getTime();
    }

    public  static int dateDiff(String startDateStr, String endDateStr) {
        Date startDate = java.sql.Date.valueOf(startDateStr);
        Date endDate = java.sql.Date.valueOf(endDateStr);

        Long diff = TimeUnit.MILLISECONDS.toDays(endDate.getTime() - startDate.getTime());
        return diff.intValue();
    }

    public  static int dateDiff(Date startDate, Date endDate) {
        Long diff = TimeUnit.MILLISECONDS.toDays(startDate.getTime() - endDate.getTime());
        return diff.intValue();
    }

    public static String getRequiredDate(int days){
        String date = null;
        try{
            date = DateUtils.getCurrentDateTime();
            final Calendar cal = Calendar.getInstance();
            cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
            cal.add(Calendar.DATE, days);
            date = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        }
        catch(Exception ex){
            log.error(ex.getMessage(), ex);
        }
        return date;
    }

    public static String getRequiredDateTime(int days){
        String date = null;
        try{
            date = DateUtils.getCurrentDateTime();
            final Calendar cal = Calendar.getInstance();
            cal.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
            cal.add(Calendar.DATE, days);
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
        }
        catch(Exception ex){
            log.error(ex.getMessage(), ex);
        }
        return date;
    }

    public static String getRequiredDate(int days, String format){
        String date = null;
        try{
            date = DateUtils.getCurrentDateTime();
            final Calendar cal = Calendar.getInstance();
            cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
            cal.add(Calendar.DATE, days);
            date = new SimpleDateFormat(format).format(cal.getTime());
        }
        catch(Exception ex){
            log.error(ex.getMessage(), ex);
        }
        return date;
    }

    public static List<String> getDateRangeFromCurrentDate(int startRange, int endRange) throws ParseException {
        List<String> dateRange = new ArrayList<>();
        String startDate, endDate;

        startDate = DateUtils.getRequiredDate(startRange);
        endDate = DateUtils.getRequiredDate(endRange);

        dateRange.add(startDate);
        dateRange.add(endDate);

        return dateRange;
    }

    public static String getToday() {
        String today = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        return today;
    }


}
