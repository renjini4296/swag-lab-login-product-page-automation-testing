package com.qa.swag.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.swag.base.TestBase;
import com.qa.swag.pages.LoginPage;

public class ProductPageTest extends TestBase {

	LoginPageTest loginpagetest;
	LoginPage loginPage;

	public ProductPageTest() {
		super();
	}

	@BeforeMethod
	public void openProductPage() {

		initialization();
		String validUserName = pro.getProperty("userName");
		String validPassword = pro.getProperty("password");
		loginpagetest = new LoginPageTest();
		loginpagetest.validLogin(validUserName, validPassword);
		Reporter.log("Product page opened successfully", true);
	}

	@Test(priority = 1)
	public void getProductPageHeading() {
		loginPage = new LoginPage();
		String headingName = loginPage.getProductPageHeading();
		Assert.assertEquals(headingName, "PRODUCTS");
		Reporter.log("Product Page heading verified", true);

	}

	@Test(priority = 2)
	public void verifyProductSortContainerLowToHighPrice() {

		Select product = new Select(driver.findElement(By.className("product_sort_container")));
		product.selectByValue("lohi");
		List<WebElement> listOfProductPrice = driver.findElements(By.xpath(
				"//div[@id='inventory_container']//div[1]//div[@class='pricebar']//div[@class='inventory_item_price']"));
		List<WebElement> listOfProducts = driver.findElements(By.xpath("//a/div[@class='inventory_item_name']"));
		String productName;
		String productPrice;
		int intProductPrice;
		HashMap<Integer, String> productsMap = new HashMap<Integer, String>();
		for (int i = 0; i < listOfProducts.size(); i++) {
			productName = listOfProducts.get(i).getText();// get products name
			productPrice = listOfProductPrice.get(i).getText();// get products price

			productPrice = productPrice.replaceAll("[^0-9]", "");// Replace anything other than numbers
			intProductPrice = Integer.parseInt(productPrice);// Convert to Integer
			productsMap.put(intProductPrice, productName);// Add product and price in HashMap

		}
		Reporter.log(
				"Product Name and price fetched from UI and saved in HashMap as:" + productsMap.toString() + "<br>",
				true);
		// Get all the keys from Hash Map
		Set<Integer> allkeys = productsMap.keySet();
		ArrayList<Integer> arrayListValuesProductPrices = new ArrayList<Integer>(allkeys);

		// Sort the Prices in Array List using Collections class
		// this will sort in ascending order lowest at the top and highest at the bottom
		Collections.sort(arrayListValuesProductPrices);
		int lowPrice = arrayListValuesProductPrices.get(0);
		String lowestPrice = driver.findElement(By.xpath(
				"//div[@class='inventory_list']//div[1]//div[@class='inventory_item_description']//div[2][@class='pricebar']//div[1]"))
				.getText();
		String lowerPrice = lowestPrice.replaceAll("[^0-9]", "");
		int lowestDisplayedPrice = Integer.parseInt(lowerPrice);
		Assert.assertEquals(lowestDisplayedPrice, lowPrice);
		Reporter.log("Products are displayed from low to high price: lowest price is-" + lowestDisplayedPrice, true);
	}

	@Test(priority = 3)
	public void addToCart() {
		int cartItemCount = 0;
		// Adding 4 items to cart
		for (int i = 0; i < 4; i++) {
			String name = driver.findElement(By.xpath("//a[@id='item_" + i + "_title_link']")).getText();

			String productName = name.toLowerCase();
			productName = productName.replaceAll("\\s", "-");

			driver.findElement(By.id("add-to-cart-" + productName + "")).click();
			cartItemCount++;
		}
		String itemNumber = driver.findElement(By.xpath("//a[@class='shopping_cart_link']//span")).getText();
		int numberOfCartItem = Integer.parseInt(itemNumber);
		// Validating the number of items in the cart.
		Assert.assertEquals(cartItemCount, numberOfCartItem);
		Reporter.log("Number of items added to cart is:  " + numberOfCartItem, true);
	}

	@Test(priority = 4)
	public void removeFromCart() {

		addToCart();// Adding 4 items to cart
		List<WebElement> listOfProducts = driver.findElements(By
				.xpath("//div[@class='inventory_list']//div[@class='inventory_item']//div[@class='pricebar']//button"));
		for (int i = 0; i < listOfProducts.size(); i++) {
			String buttonName = listOfProducts.get(i).getText();
			// Removing all added item from cart
			if (buttonName.equalsIgnoreCase("Remove")) {
				listOfProducts.get(i).click();
				String text = driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).getText();
				Reporter.log("item is removed. Number of items in cart is:" + text, true);
			}
		}
		String text = driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).getText();
		Assert.assertEquals(text, "");
		Reporter.log("All added items are removed and now Cart is empty", true);

	}

	@AfterMethod
	public void close() {
		driver.close();
	}

}
