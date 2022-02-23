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

	public static FileInputStream fis;
	public static Properties pro;
	public static WebDriver driver;

	public TestBase() {
		try {
			pro = new Properties();
			fis = new FileInputStream(
					"C:\\Users\\renji\\eclipse-workspace\\interview\\src\\main\\java\\com\\qa\\swag\\base\\config.properties");
			pro.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initialization() {
		String browserName = pro.getProperty("browser");
		String urlName = pro.getProperty("url");
		String chromeDriver = pro.getProperty("chromeDriverPath");
		if (browserName.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", chromeDriver);
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("FireFox")) {
			System.setProperty("webdriver.gecko.driver", pro.getProperty("fireFoxDriverPath"));
			driver = new FirefoxDriver();
		}
		driver.get(urlName);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
	}
}
