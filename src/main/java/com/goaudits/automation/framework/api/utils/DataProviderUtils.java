package com.goaudits.automation.framework.api.utils;



import com.goaudits.automation.framework.api.fileHandlers.ExcelInputReader;

import java.util.ArrayList;
import java.util.List;

public class DataProviderUtils {
    private final ExcelInputReader excelInputReader;

    public DataProviderUtils() {
        this.excelInputReader = new ExcelInputReader();
    }

    public Object[][] getDataProvider(String className, String methodName, String currentWorkingDirectory) throws Exception {
        String[][] entries = excelInputReader.getTestCaseFromGroup(className, methodName, currentWorkingDirectory, DataHolder.getGroupName(), DataHolder.isExcludeBrokenCases());

        List<String[]> finalEntries = new ArrayList<>();
        for(String[] entry : entries) {
            String description = entry[1];
            finalEntries.add(entry);
        }

        String[][] array = new String[finalEntries.size()][];
        int i = 0;
        for (String[] nestedList : finalEntries) {
            array[i++] = nestedList;
        }

        return array;
    }

    public Object[][] getSimpleDataProvider(String className, String methodName, String currentWorkingDirectory) throws Exception {
        return excelInputReader.getTestCases(className, methodName, currentWorkingDirectory);
    }
}