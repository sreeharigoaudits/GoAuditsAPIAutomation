package com.goaudits.automation.framework.api.resolver;


import com.goaudits.automation.framework.api.v1.Common_Actions;
import org.apache.commons.lang3.RandomStringUtils;

public class DocTypeResolver {
    public static String resolve(Common_Actions actions, String doc_type) throws Exception {
        String docType = null;
        switch (doc_type) {
            case "VALID":
                docType = RandomStringUtils.randomAlphanumeric(10);
                break;
            case "MIN_VALUE":
                docType = RandomStringUtils.randomAlphabetic(1);
                break;
            case "MAX_VALUE":
                docType = RandomStringUtils.randomAlphanumeric(100);
                break;
            case "MORE_THAN_MAX":
                docType = RandomStringUtils.randomAlphanumeric(101);
                break;
            case "WITH_SPECIAL_CHARS":
                docType = RandomStringUtils.randomAlphanumeric(5) + "$&#@%#$*^%";
                break;
            default:
                docType = doc_type;
        }
        return docType;
    }
}
