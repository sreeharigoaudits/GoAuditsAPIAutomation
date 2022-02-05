package com.goaudits.automation.framework.api.resolver;


import com.goaudits.automation.framework.api.v1.Common_Actions;
import org.apache.commons.lang3.RandomStringUtils;

public class ZipcodeResolver {
    public static String resolve(Common_Actions actions, String zipCode_type) throws Exception {
        String zipCode = null;
        switch (zipCode_type) {
            case "VALID":
            case "MAX_VALUE":
                zipCode = RandomStringUtils.random(6, "123456789");
                break;
            case "MIN_VALUE":
                zipCode = RandomStringUtils.random(1, "123456789");
                break;
            case "MORE_THAN_MAX":
                zipCode = RandomStringUtils.random(7, "123456789");
                break;
            case "INVALID":
                zipCode = RandomStringUtils.randomAlphanumeric(5) + "$&#@%#$*^%";
                break;
            case "":
            case "<NULL>":
                zipCode = zipCode_type;
                break;
            default:
                throw new Exception("Unknown value in data provider : " + zipCode);
        }
        return zipCode;
    }
}
