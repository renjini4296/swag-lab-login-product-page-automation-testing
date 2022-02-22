package com.qa.swag.parameter;

import java.io.IOException;

import org.testng.annotations.DataProvider;

import com.qa.swag.testdata.ExcelReader;

public class DataProviderClass {
	ExcelReader er;

	@DataProvider(name = "validdata")
	public Object readExcelValidData() throws IOException {
		er = new ExcelReader();
		String excelpath = "C:\\Users\\renji\\eclipse-workspace\\interview\\src\\main\\java\\com\\qa\\swag\\util\\Sauce.xlsx";
		Object[][] data = er.readData(excelpath);
		return data;
	}

	@DataProvider(name = "invaliddata")
	public Object readExcelInvaliddata() throws IOException {
		er = new ExcelReader();
		String excelpath = "C:\\Users\\renji\\eclipse-workspace\\interview\\src\\main\\java\\com\\qa\\swag\\util\\invaliddata.xlsx";
		Object[][] data = er.readData(excelpath);
		return data;
	}
}
