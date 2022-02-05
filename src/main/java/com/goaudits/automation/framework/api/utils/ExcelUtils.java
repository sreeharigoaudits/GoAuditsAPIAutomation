package com.goaudits.automation.framework.api.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ExcelUtils {
    public static List<String> readUserIds(File file) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        List<String> userIds = new ArrayList<>();

        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() != 0) {
                String user_id = row.getCell(0).getStringCellValue();
                userIds.add(user_id);
            }
        }

        workbook.close();
        return userIds;
    }

    public static List<String> readExcel(File file, Integer column) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        List<String> values = new ArrayList<>();

        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row currentRow = rowIterator.next();
            if (currentRow.getRowNum() != 0) {
                DataFormatter formatter = new DataFormatter();
                String value = formatter.formatCellValue(sheet.getRow(currentRow.getRowNum()).getCell(column));
                if(!value.equalsIgnoreCase("") && value!=null)
                values.add(value);
            }
        }
        workbook.close();
        return values;
    }

    public static void feedExcel(File file, HashMap<String, List<String>> excelInput, List<String> mapKeys) throws IOException {
        if (file.exists())
            file.delete();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("TestData");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Create cells
        for (int i = 0; i < mapKeys.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(mapKeys.get(i));
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1;
        for (int i = 0; i < excelInput.get(mapKeys.get(0)).size(); i++) {
            Row row = sheet.createRow(rowNum++);

            int columnNum = 0;
            for (int j = 0; j < mapKeys.size(); j++) {
                row.createCell(columnNum).setCellValue(excelInput.get(mapKeys.get(j)).get(i));
                columnNum++;
            }
        }

        FileOutputStream fileOut = new FileOutputStream(file);
        workbook.write(fileOut);
        fileOut.close();
    }

    public static void feedExcel(File file, HashMap<String, List<String>> excelInput, HashMap<String, List<String>> userSpecificLoans, List<String> mapKeys) throws IOException {
        if (file.exists())
            file.delete();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("TestData");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Create cells
        for (int i = 0; i < mapKeys.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(mapKeys.get(i));
            cell.setCellStyle(headerCellStyle);
        }

        Cell cell = headerRow.createCell(mapKeys.size());
        cell.setCellValue("loans");
        cell.setCellStyle(headerCellStyle);

        int rowNum = 1;
        for (int i = 0; i < excelInput.get(mapKeys.get(0)).size(); i++) {
            Row row = sheet.createRow(rowNum++);

            int columnNum = 0;
            for (int j = 0; j < mapKeys.size(); j++) {
                row.createCell(columnNum).setCellValue(excelInput.get(mapKeys.get(j)).get(i));
                columnNum++;
            }

            System.out.println(excelInput.get(mapKeys.get(0)).get(i));
            List<String> loanIds = userSpecificLoans.get(excelInput.get(mapKeys.get(0)).get(i));
            for (String loanId : loanIds) {
                System.out.println(loanId);
                row.createCell(mapKeys.size()).setCellValue(loanId);
                row = sheet.createRow(rowNum++);
            }
        }

        FileOutputStream fileOut = new FileOutputStream(file);
        workbook.write(fileOut);
        fileOut.close();
    }
}
