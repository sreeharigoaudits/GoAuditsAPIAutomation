package com.goaudits.automation.framework.api.utils;


import com.goaudits.automation.framework.api.fileHandlers.PropertyFile;
import com.goaudits.automation.framework.api.reporter.NotificationDataHolder;
import com.goaudits.automation.framework.api.v1.Common_Actions;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataHolder {
    private static PropertyFile configFile;
    private static boolean excludeBrokenCases;
    private static String masterServerURL_v1, execution_mode;
    private static String groupName;

    private static Common_Actions actions;

    static {
        log.info("Initializing GoAudits Data Holder");
        init();
    }

    public static void init() {
        try {
            execution_mode = System.getenv("EXECUTION_MODE");
            configFile = new PropertyFile(Utils.getTestResourcesPath() + "goaudits_config.properties");

            groupName = System.getenv("GROUP_NAME");
            Utils.setUpUrlConfigs(execution_mode, configFile, groupName);

            String excludeBrokenCasesStr;
            excludeBrokenCasesStr = configFile.getProperty("exclude.broken.cases");
            excludeBrokenCases = Boolean.parseBoolean(excludeBrokenCasesStr);
            //initializations
            actions = new Common_Actions();
            NotificationDataHolder.init();
            AllureUtils.createAllureEnvironmentFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getGroupName() {
        return groupName;
    }

    public static void setGroupName(String groupNm) {
        groupName = groupNm;
    }

    public static boolean isExcludeBrokenCases() {
        return excludeBrokenCases;
    }

    public static String getMasterServerURL_v1() {
        return masterServerURL_v1;
    }


    public static void setMasterServerURL_v1(String url) {
        masterServerURL_v1 = url;
    }

    public static String getExecution_mode() {
        return execution_mode;
    }



    public static Common_Actions getActions() {
        return actions;
    }

    public static void setActions(Common_Actions actions) {
        DataHolder.actions = actions;
    }
}
