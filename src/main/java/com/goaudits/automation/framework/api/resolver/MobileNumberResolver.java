package com.goaudits.automation.framework.api.resolver;

import com.goaudits.automation.framework.api.utils.Enumerations;
import com.goaudits.automation.framework.api.v1.Common_Actions;
import org.apache.commons.lang3.RandomStringUtils;

public class MobileNumberResolver {
    public static String resolve(Common_Actions actions, String number_type, String mobile_number) throws Exception {
        String number = null;
        switch (number_type) {
            case "VALID":
                if(mobile_number == null)
                    number = "6161" + RandomStringUtils.random(6, "123456789");
                else
                    number = mobile_number;
                break;
            case "INVALID":
                number = RandomStringUtils.randomAlphanumeric(5) + "$&#@%#$*^%";
                break;
            case "RANDOM":
                number = RandomStringUtils.randomAlphanumeric(100);
                break;
            case "MIN_VALUE":
                number = RandomStringUtils.random(1, "123456789");
                break;
            case "MORE_THAN_MAX":
                number = RandomStringUtils.random(20, "123456789");
                break;
            case "":
            case "<NULL>":
                number = number_type;
                break;
            default:
                throw new Exception("Unknown value in data provider : " + number);
        }
        return number;
    }

    public static String resolve(Common_Actions actions, String number_type) throws Exception {
        return resolve(actions, number_type, null);
    }
}
