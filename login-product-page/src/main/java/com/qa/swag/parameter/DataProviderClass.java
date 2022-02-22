package com.qa.swag.parameter;

import java.io.IOException;

import org.testng.annotations.DataProvider;

import com.qa.swag.testdata.ExcelReader;

public class DataProviderClass {
	ExcelReader excelReader;

	@DataProvider(name = "validdata")
	public Object readExcelValidData() throws IOException {
		excelReader = new ExcelReader();
		String excelpath = "C:\\Users\\renji\\eclipse-workspace\\interview\\src\\main\\java\\com\\qa\\swag\\util\\validdata.xlsx";
		Object[][] data = excelReader.readData(excelpath);
		return data;
	}

	@DataProvider(name = "invaliddata")
	public Object readExcelInvaliddata() throws IOException {
		excelReader = new ExcelReader();
		String excelpath = "C:\\Users\\renji\\eclipse-workspace\\interview\\src\\main\\java\\com\\qa\\swag\\util\\invaliddata.xlsx";
		Object[][] data = excelReader.readData(excelpath);
		return data;
	}
}
