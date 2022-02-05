package com.goaudits.automation.framework.api.resolver;


import com.goaudits.automation.framework.api.v1.Common_Actions;
import org.apache.commons.lang3.RandomStringUtils;

public class AddressResolver {

    public static String resolve(Common_Actions actions, String address_type) throws Exception {
        String address = null;
        switch (address_type) {
            case "VALID":
                address = "GoAudits, 2nd floor, Akshay tech park, Plot 72 & 73, Rd Number 3, EPIP Zone, Vijayanagar, KIADB Export Promotion Industrial Area, Whitefield, Bengaluru, Karnataka 560066";
                break;
            case "MIN_VALUE":
                address = RandomStringUtils.randomAlphabetic(1);
                break;
            case "MAX_VALUE":
                address = RandomStringUtils.randomAlphanumeric(500);
                break;
            case "MORE_THAN_MAX":
                address = RandomStringUtils.randomAlphanumeric(501);
                break;
            case "WITH_SPECIAL_CHARS":
                address = "2/22#, test street1 & test street2, @bangalore, &GoAudits";
                break;
            case "":
            case "<NULL>":
                address = address_type;
                break;
            default:
                throw new Exception("Unknown value in data provider : " + address);
        }
        return address;
    }
}
