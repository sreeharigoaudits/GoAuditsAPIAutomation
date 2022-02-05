package com.goaudits.automation.framework.api.utils;


import com.goaudits.automation.framework.api.fileHandlers.PropertyFile;


public class UserHolder {
    private static String superAdminEmail;

    private static String usersCount;
    private static PropertyFile configFile;

    static {
        init();
    }

    public static void init() {
        try {
            configFile = new PropertyFile(Utils.getWorkingDirectory() + "/test-classes/goaudits_config.properties");

            superAdminEmail = configFile.getProperty("super.admin.email");

            usersCount = configFile.getProperty("users.count");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
