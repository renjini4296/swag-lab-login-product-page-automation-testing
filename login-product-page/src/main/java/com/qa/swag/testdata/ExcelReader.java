package com.qa.swag.testdata;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.qa.swag.base.TestBase;

public class ExcelReader extends TestBase {

	public Object[][] readData(String excelpath,String sheetName) throws IOException {

		FileInputStream fis = new FileInputStream(excelpath);
		XSSFWorkbook wb = new XSSFWorkbook(fis);

		int rowCount = wb.getSheet(sheetName).getLastRowNum();
		int columnCount = wb.getSheet(sheetName).getRow(0).getLastCellNum();

		Object[][] data = new Object[rowCount][columnCount];
		// Getting data from Excel sheet
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {

				XSSFCell cell = wb.getSheet(sheetName).getRow(i + 1).getCell(j);
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
