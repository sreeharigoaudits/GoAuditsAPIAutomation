package com.goaudits.automation.framework.api.resolver;


import com.goaudits.automation.framework.api.v1.Common_Actions;
import org.apache.commons.lang3.RandomStringUtils;

public class NameResolver {
    public static String resolve(Common_Actions actions, String name_type) throws Exception {
        String name = null;
        switch (name_type) {
            case "VALID":
                name = RandomStringUtils.randomAlphabetic(10);
                break;
            case "MIN_VALUE":
                name = RandomStringUtils.randomAlphabetic(1);
                break;
            case "MAX_VALUE":
                name = RandomStringUtils.randomAlphabetic(255);
                break;
            case "MORE_THAN_MAX":
                name = RandomStringUtils.randomAlphabetic(256);
                break;
            case "WITH_SPECIAL_CHARS":
            case "INVALID":
                name = RandomStringUtils.randomAlphabetic(5) + "$&#@%#$*^%";
                break;
            case "":
            case "<NULL>":
                name = name_type;
                break;
            default:
                throw new Exception("Unknown value in data provider : " + name);
        }
        return name;
    }
}
