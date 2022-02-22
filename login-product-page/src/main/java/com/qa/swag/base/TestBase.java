package com.qa.swag.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestBase {

	FileInputStream fis;
	Properties pro;
	public static WebDriver driver;
	public TestBase() {
		
		
		try {
			pro=new Properties();
			
			fis=new FileInputStream("C:\\Users\\renji\\eclipse-workspace\\interview\\src\\main\\java\\com\\qa\\swag\\base\\config.properties");
			pro.load(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initialization() {
		
		String browser_Name=pro.getProperty("browser");
		String url_Name=pro.getProperty("url");
		if(browser_Name.equalsIgnoreCase("Chrome")) {
			
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\chromedriver.exe");;
			driver=new ChromeDriver();
			
		}
		else if (browser_Name.equals("FireFox")) {

			System.setProperty("webdriver.gecko.driver", "C:\\Firefoxdriver.exe");
			driver=new FirefoxDriver();
		}
		driver.get(url_Name);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
	}
}
