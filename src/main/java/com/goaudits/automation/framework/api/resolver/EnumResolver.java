package com.goaudits.automation.framework.api.resolver;


import com.goaudits.automation.framework.api.utils.Enumerations;
import com.goaudits.automation.framework.api.v1.Common_Actions;
import org.apache.commons.lang3.RandomStringUtils;

public class EnumResolver {
    public static String resolve(Common_Actions actions, String enum_type) throws Exception {
        String enums;
        switch (enum_type) {
            case "VALID_USER":

            case "VALID_NUMBER":
                enums = RandomStringUtils.random(7, "123456789");
                break;
            case "VALID_STRING":
                enums = RandomStringUtils.randomAlphabetic(10);
                break;
            case "VALID_BOOLEAN":
                enums = RandomStringUtils.random(1, "01");
                break;
            case "INVALID":
                enums = RandomStringUtils.randomAlphanumeric(10);
                break;

            case "INVALID_ID":
                enums = RandomStringUtils.random(15, "123456789");
                break;
            case "VALID_PAN":
                enums = "ATMPN8519Q";
                break;
            default:
                enums = enum_type;
        }
        return enums;
    }
}
