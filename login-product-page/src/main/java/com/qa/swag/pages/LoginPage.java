package com.qa.swag.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.swag.base.TestBase;

public class LoginPage extends TestBase {

	@FindBy(name="user-name") WebElement userName;
	@FindBy(name="password") WebElement password;
	@FindBy(name="login-button") WebElement submit;
	@FindBy(xpath="//div[@id='login_button_container']//h3[contains(text(),'Epic sadface: Username is required')]") WebElement empty_error;
	     
	      @FindBy(xpath="//div[@id='login_button_container']//h3[contains(text(),'Epic')]") WebElement invalid_error;
	      @FindBy(xpath="//div[@id='header_container']//span[@class='title']") WebElement heading;
	public LoginPage() {
		
		PageFactory.initElements(driver,this);
	}
public String getTitle() {
		
	String title=driver.getTitle();
	System.out.print(title);
	return title;	
	}
	
	public void enterData(String uname,String pwd) {
		userName.sendKeys(uname);
		password.sendKeys(pwd);
	}
	public void clickLoginButton() {
		
		submit.click();
	}
	
	public String getProductPageHeading() {
		
		String heading_Name=heading.getText();
		return heading_Name;
		
	}
	public String getFieldEmptyErrormessage() {
		
		String message=empty_error.getText();
		return message;
	}

public String getInvalidLoginErrormessage() {
		
		String message=invalid_error.getText();
		return message;
	}
}
