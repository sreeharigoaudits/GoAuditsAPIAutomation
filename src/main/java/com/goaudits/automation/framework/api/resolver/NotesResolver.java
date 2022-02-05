package com.goaudits.automation.framework.api.resolver;


import com.goaudits.automation.framework.api.v1.Common_Actions;
import org.apache.commons.lang3.RandomStringUtils;

public class NotesResolver {
    public static String resolve(Common_Actions actions, String notes_type) throws Exception {
        String notes = null;
        switch (notes_type) {
            case "VALID":
                notes = RandomStringUtils.randomAlphanumeric(100);
                break;
            case "MIN_VALUE":
                notes = RandomStringUtils.randomAlphabetic(1);
                break;
            case "MAX_VALUE":
                notes = RandomStringUtils.randomAlphanumeric(1024);
                break;
            case "MORE_THAN_MAX":
                notes = RandomStringUtils.randomAlphanumeric(1025);
                break;
            case "WITH_SPECIAL_CHARS":
                notes = RandomStringUtils.randomAlphanumeric(500) + "$&#@%#$*^%";
                break;
            case "":
            case "<NULL>":
                notes = notes_type;
                break;
            default:
                throw new Exception("Unknown value in data provider : " + notes);
        }
        return notes;
    }
}
