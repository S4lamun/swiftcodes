package org.example.swiftapi.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.example.swiftapi.model.Branch;
import org.example.swiftapi.model.Headquarter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {
    public record BranchesAndHeadquarters<T,U>(T branches, U headquarters){}

    public static List<List<String>> readExcel(String excelFilePath) throws IOException {
        List<List<String>> data = new ArrayList<>();
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

        Workbook workbook = null;

        // Check Excel file type (.xls or .xlsx)
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("Unsupported file format. Supported formats: .xls, .xlsx");
        }

        // Get the first sheet
        Sheet sheet = workbook.getSheetAt(0);

        // Iterate through all rows
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            List<String> rowData = new ArrayList<>();

            // Iterate through all cells in the row
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                // Convert cell value to String based on cell type
                switch (cell.getCellType()) {
                    case STRING:
                        rowData.add(cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            rowData.add(cell.getLocalDateTimeCellValue().toString());
                        } else {
                            rowData.add(String.valueOf(cell.getNumericCellValue()));
                        }
                        break;
                    case BOOLEAN:
                        rowData.add(String.valueOf(cell.getBooleanCellValue()));
                        break;
                    case FORMULA:
                        rowData.add(cell.getCellFormula());
                        break;
                    default:
                        rowData.add("");
                }
            }

            data.add(rowData);
        }

        workbook.close();
        inputStream.close();

        return data;
    }

    public static BranchesAndHeadquarters<List<Branch>, List<Headquarter>> processData(List<List<String>> data) {
        List<Headquarter> headquarters = new ArrayList<>();
        List<Branch> branches = new ArrayList<>();
        String address = "";
        String bankName = "";
        String countryISO2 = "";
        String countryName = "";
        String swiftCode = "";

        for (List<String> row : data) {
            // Skip header row if it exists
            if (row.size() < 7 || row.get(0).equals("COUNTRY_ISO2")) {
                continue;
            }

            countryISO2 = row.get(0);
            swiftCode = row.get(1);
            bankName = row.get(3);
            address = row.get(4);
            countryName = row.get(6);

            if (swiftCode.endsWith("XXX"))
                headquarters.add(new Headquarter(address, bankName, countryISO2, countryName, swiftCode));
            else
                branches.add(new Branch(address, bankName, countryISO2, countryName, swiftCode));
        }

        // Associate branches with their headquarters
        for (Headquarter headquarter : headquarters) {
            String startCharacters = headquarter.getSwiftCode().substring(0, 8);
            for (Branch branch : branches) {
                if (branch.getSwiftCode().startsWith(startCharacters)) {
                    branch.setHeadquarter(headquarter);
                }
            }
        }

        return new BranchesAndHeadquarters<List<Branch>, List<Headquarter>>(branches, headquarters);
    }
}