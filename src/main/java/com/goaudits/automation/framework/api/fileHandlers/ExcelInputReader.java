package com.goaudits.automation.framework.api.fileHandlers;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class ExcelInputReader {
    private final DataFormatter formatter = new DataFormatter();

    public String[][] getTestCaseFromGroup(String className, String sheetName, String currentWorkingDirectory, String groupName, boolean excludeBrokenCases) throws IOException, EncryptedDocumentException, InvalidFormatException {
        FileInputStream fis = new FileInputStream(getDataProviderPath(className, currentWorkingDirectory));
        Workbook workbook = WorkbookFactory.create(fis);
        Sheet sheet = workbook.getSheet(sheetName);
        Iterator<Row> iterator = sheet.iterator();
        iterator.next(); //To skip the headerRow

        int rowCount = 0;
        boolean exclusionFlag;
        String enabled;
        String groupName1;
        int enabledCol = 2;
        int groupNameCol = 3;
        while (iterator.hasNext()) {
            Row row = iterator.next();
            groupName1 = formatter.formatCellValue(row.getCell(groupNameCol)).toUpperCase();
            enabled = formatter.formatCellValue(row.getCell(enabledCol)).toUpperCase();
            exclusionFlag = !excludeBrokenCases || !groupName1.contains("BROKEN");

            if (groupName1.contains(groupName.toUpperCase()) && enabled.equalsIgnoreCase("YES") && exclusionFlag)
                rowCount++;
        }

        int startCol = 5;
        int columnCount = sheet.getRow(sheet.getFirstRowNum()).getLastCellNum() - startCol;
        String[][] tabArray = new String[rowCount][columnCount + 3]; // +3 for storing S.No, Test Case Name & Priority

        iterator = sheet.iterator();
        iterator.next(); //To skip the headerRow
        rowCount = 0;
        while (iterator.hasNext()) {
            Row row = iterator.next();
            groupName1 = formatter.formatCellValue(row.getCell(groupNameCol)).toUpperCase();
            enabled = formatter.formatCellValue(row.getCell(enabledCol)).toUpperCase();
            exclusionFlag = !excludeBrokenCases || !groupName1.contains("BROKEN");

            if (groupName1.contains(groupName.toUpperCase()) && enabled.equalsIgnoreCase("YES") && exclusionFlag) {
                int snoCol = 0;
                tabArray[rowCount][0] = formatter.formatCellValue(row.getCell(snoCol)); // Storing S.No
                int testCaseNameCol = 1;
                tabArray[rowCount][1] = formatter.formatCellValue(row.getCell(testCaseNameCol)) + " - " + formatter.formatCellValue(row.getCell(groupNameCol)); // Storing Test Case Name
                int priorityCol = 4;
                tabArray[rowCount][2] = formatter.formatCellValue(row.getCell(priorityCol)).toUpperCase(); // Storing Test Case Priority

                for (int j = startCol; j < sheet.getRow(sheet.getFirstRowNum()).getLastCellNum(); j++) {
                    tabArray[rowCount][j - startCol + 3] = formatter.formatCellValue(row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)); // +3 for storing Test Case Name & Priority
                }
                rowCount++;
            }
        }
        fis.close();
        workbook.close();
        return (tabArray);
    }

    public String[][] getTestCases(String className, String sheetName, String currentWorkingDirectory) throws IOException, EncryptedDocumentException, InvalidFormatException {
        FileInputStream fis = new FileInputStream(getDataProviderPath(className, currentWorkingDirectory));
        Workbook workbook = WorkbookFactory.create(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        Iterator<Row> rowIterator = sheet.iterator();
        Row header = rowIterator.next(); //To skip the headerRow
        int rowCount = sheet.getLastRowNum();
        int columnCount = header.getLastCellNum();
        String[][] data = new String[rowCount][columnCount];

        int tempRowCount = 0;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            int tempColumnCount = 0;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                data[tempRowCount][tempColumnCount] = formatter.formatCellValue(cell);
                tempColumnCount++;
            }
            tempRowCount++;
        }

        fis.close();
        workbook.close();
        return (data);
    }

    private String getWorkingDirectory() {
        String workingDir = System.getProperty("user.dir");
        if (!workingDir.contains("target"))
            workingDir = workingDir + System.getProperty("file.separator") + "target";
        return workingDir;
    }

    public String getDataProviderPath(String className, String currentWorkingDirectory) {
        String[] tempArr = className.split("\\.");
        StringBuilder excelFilePath = new StringBuilder();

        if(currentWorkingDirectory == null)
            excelFilePath.append(getWorkingDirectory());
        else
            excelFilePath.append(currentWorkingDirectory);

        excelFilePath.append(System.getProperty("file.separator"));
        excelFilePath.append("test-classes");
        excelFilePath.append(System.getProperty("file.separator"));
        excelFilePath.append("dataProviders");
        boolean flag = false;
        for (String tempStr : tempArr) {
            if (!flag) {
                if (tempStr.equalsIgnoreCase("tests")) {
                    flag = true;
                }
            } else {
                excelFilePath.append(System.getProperty("file.separator"));
                excelFilePath.append(tempStr);
            }
        }

        excelFilePath.append(".xls");

        return  excelFilePath.toString();
    }
}