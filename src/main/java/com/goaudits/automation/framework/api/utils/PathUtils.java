package com.goaudits.automation.framework.api.utils;

import java.io.File;

public class PathUtils {
    public static String getTestResourceFilesPath() {
        return getTestResourcesPath() + "resourceFiles" + File.separator;
    }

    public static String getTestSupportFilesPath() {
        return getTestResourcesPath() + "supportFiles" + File.separator;
    }

    public static String getAllureResourcesPath() {
        return getTargetResourcesPath() + "allure-results" + File.separator;
    }

    public static String getTestResourcesPath() {
        return getTargetResourcesPath() + "test-classes" + File.separator;
    }

    private static String getTargetResourcesPath() {
        String workingDir = System.getProperty("user.dir");
        if (!workingDir.contains("target"))
            workingDir = workingDir + File.separator + "target" + File.separator;
        if (!workingDir.endsWith(File.separator))
            workingDir = workingDir + File.separator;
        return workingDir;
    }

    public static String getDataProviderPath(String className, String currentWorkingDirectory) {
        String[] tempArr = className.split("\\.");
        StringBuilder excelFilePath = new StringBuilder();
        excelFilePath.append(currentWorkingDirectory);
        excelFilePath.append(System.getProperty("file.separator"));
        excelFilePath.append("test-classes");
        excelFilePath.append(System.getProperty("file.separator"));
        excelFilePath.append("dataProviders");
        excelFilePath.append(System.getProperty("file.separator"));
        excelFilePath.append(className);
        excelFilePath.append(System.getProperty("file.separator"));
        return  excelFilePath.toString();
    }

    public static String getWorkingDirectory() {
        String workingDir = System.getProperty("user.dir");
        if (!workingDir.contains("target"))
            workingDir = workingDir + System.getProperty("file.separator") + "target";
        return workingDir;
    }

    public static String getWorkingDirectory_withoutTarget() {
        String workingDir = System.getProperty("user.dir");
        return workingDir;
    }
}