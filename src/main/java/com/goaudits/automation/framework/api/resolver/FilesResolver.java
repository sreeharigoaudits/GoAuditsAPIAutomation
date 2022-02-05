package com.goaudits.automation.framework.api.resolver;


import com.goaudits.automation.framework.api.v1.Common_Actions;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class FilesResolver {
    public static List<String> resolve(Common_Actions actions, String file_type) throws Exception {
        List<String> filesList = new ArrayList<>();
        String files;
        switch (file_type) {
            case "VALID":
                files = RandomStringUtils.random(1, "123456789");
                for(int i = 0; i < Integer.valueOf(files); i++) {
                    filesList.add(RandomStringUtils.randomAlphabetic(10));
                }
                break;
            case "INVALID":
            case "" :
                files = file_type;
                filesList.add(files);
                break;
            case "BIG_NUMBERS":
                files = RandomStringUtils.random(15, "123456789");
                filesList.add(files);
                break;
            case "<NULL>":
                filesList = null;
                break;
            default:
                throw new Exception("Unknown value in data provider :" + file_type);
        }
        return filesList;
    }
}
