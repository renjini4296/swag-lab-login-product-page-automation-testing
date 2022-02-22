package com.qa.swag.test;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.swag.base.TestBase;
import com.qa.swag.pages.LoginPage;
import com.qa.swag.parameter.DataProviderClass;

public class LoginPageTest extends TestBase {

	LoginPage loginPage;

	public LoginPageTest() {
		super();
	}

	@BeforeMethod
	public void openBrowser() {

		initialization();
	}

	@Test(priority = 1)
	public void verifyTitle() {
		loginPage = new LoginPage();
		String title = loginPage.getTitle();
		Assert.assertEquals(title, "Swag Labs");
		Reporter.log("Verify tile succeed", true);

	}

	@Test(dataProvider = "validdata", dataProviderClass = DataProviderClass.class, priority = 2)
	public void validLogin(String username, String password) {
		loginPage = new LoginPage();
		loginPage.enterData(username, password);
		loginPage.clickLoginButton();
		String heading = loginPage.getProductPageHeading();
		Assert.assertEquals(heading, "PRODUCTS");
		Reporter.log("Logged in successfully");

	}

	@Test(priority = 3)
	public void emptyLogin() {
		loginPage = new LoginPage();
		loginPage.clickLoginButton();
		String errorMessage = loginPage.getFieldEmptyErrormessage();
		Assert.assertEquals(errorMessage, "Epic sadface: Username is required");
	}

	@Test(dataProvider = "invaliddata", dataProviderClass = DataProviderClass.class, priority = 4)
	public void invalidLogin(String username, String password) {
		loginPage = new LoginPage();
		loginPage.enterData(username, password);
		loginPage.clickLoginButton();
		String message = loginPage.getInvalidLoginErrormessage();
		Assert.assertEquals(message, "Epic sadface: Username and password do not match any user in this service");
	}

	@AfterMethod
	public void close() {

		driver.close();
	}

}
