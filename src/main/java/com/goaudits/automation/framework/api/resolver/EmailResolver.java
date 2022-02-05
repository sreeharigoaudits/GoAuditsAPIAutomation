package com.goaudits.automation.framework.api.resolver;


import com.goaudits.automation.framework.api.v1.Common_Actions;
import org.apache.commons.lang3.RandomStringUtils;

public class EmailResolver {
    public static String resolve(Common_Actions actions, String email_type) throws Exception {
        String email = null;
        switch (email_type) {
            case "VALID":
                email = RandomStringUtils.randomAlphanumeric(10) + "@test.com";
                break;
            case "MIN_VALUE":
                email = RandomStringUtils.randomAlphabetic(1) + "@f.s";
                break;
            case "MAX_VALUE":
                email = RandomStringUtils.randomAlphanumeric(200) + "@" + RandomStringUtils.randomAlphanumeric(52) + ".in";
                break;
            case "MORE_THAN_MAX":
                email = RandomStringUtils.randomAlphanumeric(255) + "@dkf.sfggdfg";
                break;
            case "WITH_SPECIAL_CHARS":
                email = RandomStringUtils.randomAlphanumeric(5) + "$&#@%#$*^%@#$&kjsdf.sdf";
                break;
            case "":
            case "<NULL>":
                email = email_type;
                break;
            default:
                throw new Exception("Unknown value in data provider : " + email);
        }
        return email;
    }
}
