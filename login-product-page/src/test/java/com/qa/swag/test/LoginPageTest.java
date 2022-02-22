package com.qa.swag.test;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.swag.base.TestBase;

import com.qa.swag.pages.LoginPage;

import com.qa.swag.parameter.DataProviderClass;
import com.qa.swag.testdata.ExcelReader;

public class LoginPageTest extends TestBase {

	ExcelReader er;
	LoginPage lp;

	public LoginPageTest() {

		super();

	}

	@BeforeMethod
	public void openBrowser() {

		initialization();
	}

	@Test(priority = 1)
	public void verifyTitle() {
		lp = new LoginPage();
		String title = lp.getTitle();
		Assert.assertEquals(title, "Swag Labs");
		Reporter.log("Verify tile succeed", true);

	}

	@Test(dataProvider = "validdata", dataProviderClass = DataProviderClass.class, priority = 2)
	public void validLogin(String username, String password) {
		lp = new LoginPage();
		lp.enterData(username, password);
		lp.clickLoginButton();
		String heading = lp.getProductPageHeading();
		Assert.assertEquals(heading, "PRODUCTS");
		Reporter.log("Logged in successfully");

	}

	@Test(priority = 3)
	public void emptyLogin() {
		lp = new LoginPage();
		lp.clickLoginButton();
		String error_message = lp.getFieldEmptyErrormessage();
		Assert.assertEquals(error_message, "Epic sadface: Username is required");
	}

	@Test(dataProvider = "invaliddata", dataProviderClass = DataProviderClass.class, priority = 4)
	public void invalidLogin(String username, String password) {
		lp = new LoginPage();
		lp.enterData(username, password);
		lp.clickLoginButton();
		String message = lp.getInvalidLoginErrormessage();
		Assert.assertEquals(message, "Epic sadface: Username and password do not match any user in this service");
	}

	@AfterMethod
	public void close() {

		driver.close();
	}

}
