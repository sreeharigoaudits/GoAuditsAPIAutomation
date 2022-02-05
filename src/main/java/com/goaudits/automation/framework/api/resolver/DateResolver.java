package com.goaudits.automation.framework.api.resolver;


import com.goaudits.automation.framework.api.utils.DateUtils;
import com.goaudits.automation.framework.api.v1.Common_Actions;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;

public class DateResolver {
    public static String resolve(Common_Actions actions, String date_type) throws Exception {
        String date = null;
        switch (date_type) {
            case "VALID":
                date = "1992-06-14";
                break;
            case "INVALID":
                date = "1200-12-32";
                break;
            case "DIFF_FORMAT":
                date = "14/06/19";
                break;
            case "MAX_AGE":
                date = "1900-01-01";
                break;
            case "RANDOM":
                date = RandomStringUtils.randomAlphanumeric(10);
                break;
            case "CURRENT":
                LocalDate now = LocalDate.now();
                date = now.toString();
                break;
            case "":
            case "<NULL>":
                date = date_type;
                break;
            default:
                throw new Exception("Unknown value in data provider : " + date);
        }
        return date;
    }

    public static String dateTimeResolve(Common_Actions actions, String date_type) throws Exception {
        String date = null;
        switch (date_type) {
            case "VALID":
                date = DateUtils.getCurrentDateTime();
                break;
            case "INVALID":
                date = "1200-12-32 101:000:12";
                break;
            case "DIFF_FORMAT":
                date = "14-06-19 24:60:222.000";
                break;
            case "MAX_AGE":
                date = "1960-01-01";
                break;
            case "RANDOM":
                date = RandomStringUtils.randomAlphanumeric(10);
                break;
            case "CURRENT":
                LocalDate now = LocalDate.now();
                date = now.toString() + " 00:00:00";
                break;
            case "":
            case "<NULL>":
                date = date_type;
                break;
            default:
                throw new Exception("Unknown value in data provider : " + date);
        }
        return date;
    }
}
