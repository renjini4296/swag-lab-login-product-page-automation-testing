package com.qa.swag.parameter;

import java.io.IOException;
import org.testng.annotations.DataProvider;
import com.qa.swag.base.TestBase;
import com.qa.swag.testdata.ExcelReader;

public class DataProviderClass extends TestBase {
	ExcelReader excelReader;
	String excelpath = pro.getProperty("loginDataExcelPath") ;
	String sheetName;
	@DataProvider(name = "validData")
	public Object readExcelValidData() throws IOException {
		excelReader = new ExcelReader();
		sheetName=pro.getProperty("validDataExcelSheet");
		Object[][] data = excelReader.readData(excelpath,sheetName);
		return data;
	}

	@DataProvider(name = "invalidData")
	public Object readExcelInvaliddata() throws IOException {
		excelReader = new ExcelReader();
		sheetName=pro.getProperty("invalidDataExcelSheet");
		Object[][] data = excelReader.readData(excelpath,sheetName);
		return data;
	}
}
