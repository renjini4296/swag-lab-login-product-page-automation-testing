package com.qa.swag.testdata;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	public Object[][] readData(String excelpath) throws IOException {

		FileInputStream fis = new FileInputStream(excelpath);
		XSSFWorkbook wb = new XSSFWorkbook(fis);

		int rowCount = wb.getSheet("Details").getLastRowNum();
		int columnCount = wb.getSheet("Details").getRow(0).getLastCellNum();

		String value = wb.getSheet("Details").getRow(1).getCell(0).getStringCellValue();

		Object[][] data = new Object[rowCount][columnCount];
        //Getting data from Excel sheet
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {

				XSSFCell cell = wb.getSheet("Details").getRow(i + 1).getCell(j);
				if (cell.getCellType() == CellType.STRING)
					data[i][j] = cell.getStringCellValue();
				else if (cell.getCellType() == CellType.NUMERIC)
					data[i][j] = Double.valueOf(cell.getNumericCellValue());
			}

		}
		wb.close();
		return data;
	}

}
