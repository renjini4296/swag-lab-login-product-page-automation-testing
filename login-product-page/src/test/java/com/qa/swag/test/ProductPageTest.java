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

public class ProductPageTest extends TestBase {

	public ProductPageTest() {

		super();
	}

	@BeforeMethod
	public void openBrowser() {

		initialization();

		driver.findElement(By.name("user-name")).sendKeys("standard_user");
		driver.findElement(By.name("password")).sendKeys("secret_sauce");
		driver.findElement(By.name("login-button")).click();
	}

	@Test(priority = 1)
	public void get_ProductPageHeading() {

		String heading_Name = driver.findElement(By.xpath("//div[@id='header_container']//span[@class='title']"))
				.getText();

		Assert.assertEquals(heading_Name, "PRODUCTS");
		Reporter.log("Now in product page", true);

	}

	@Test(priority = 2)
	public void verify_ProductSortContainerLowToHighPrice() {

		Select product = new Select(driver.findElement(By.className("product_sort_container")));
		product.selectByValue("lohi");
		List<WebElement> list_of_product_price = driver.findElements(By.xpath(
				"//div[@id='inventory_container']//div[1]//div[@class='pricebar']//div[@class='inventory_item_price']"));
		List<WebElement> list_of_products = driver.findElements(By.xpath("//a/div[@class='inventory_item_name']"));
		String product_name;
		String product_price;
		int int_product_price;
		HashMap<Integer, String> products_map = new HashMap<Integer, String>();
		for (int i = 0; i < list_of_products.size(); i++) {
			product_name = list_of_products.get(i).getText();// get products name
			product_price = list_of_product_price.get(i).getText();// get products price

			product_price = product_price.replaceAll("[^0-9]", "");// Replace anything other than numbers
			int_product_price = Integer.parseInt(product_price);// Convert to Integer
			products_map.put(int_product_price, product_name);// Add product and price in HashMap

		}
		Reporter.log(
				"Product Name and price fetched from UI and saved in HashMap as:" + products_map.toString() + "<br>",
				true);
		// Get all the keys from Hash Map
		Set<Integer> allkeys = products_map.keySet();
		ArrayList<Integer> array_list_values_product_prices = new ArrayList<Integer>(allkeys);

		// Sort the Prices in Array List using Collections class
		// this will sort in ascending order lowest at the top and highest at the bottom
		Collections.sort(array_list_values_product_prices);
		int low_price = array_list_values_product_prices.get(0);
		String lowest_price = driver.findElement(By.xpath(
				"//div[@class='inventory_list']//div[1]//div[@class='inventory_item_description']//div[2][@class='pricebar']//div[1]"))
				.getText();
		String lp = lowest_price.replaceAll("[^0-9]", "");
		int lowest_displayed_price = Integer.parseInt(lp);
		Assert.assertEquals(lowest_displayed_price, low_price);
		Reporter.log("Products are displayed from low to high price: lowest price is-" + lowest_displayed_price, true);
	}

	@Test(priority = 3)
	public void addToCart() {
		int cart_item_count = 0;
		// Adding 4 items to cart
		for (int i = 0; i < 4; i++) {
			String name = driver.findElement(By.xpath("//a[@id='item_" + i + "_title_link']")).getText();

			String product_name = name.toLowerCase();
			product_name = product_name.replaceAll("\\s", "-");

			driver.findElement(By.id("add-to-cart-" + product_name + "")).click();
			cart_item_count++;
		}
		String item_number = driver.findElement(By.xpath("//a[@class='shopping_cart_link']//span")).getText();
		int number_of_cart_item = Integer.parseInt(item_number);
		// Validating the number of items in the cart.
		Assert.assertEquals(cart_item_count, number_of_cart_item);
		Reporter.log("Number of items added to cart is:  " + number_of_cart_item, true);
	}

	@Test(priority = 4)
	public void removeFromCart() {

		addToCart();//Adding 4 items to cart
		List<WebElement> list_of_products = driver.findElements(By
				.xpath("//div[@class='inventory_list']//div[@class='inventory_item']//div[@class='pricebar']//button"));
		for (int i = 0; i < list_of_products.size(); i++) {
			String button_name = list_of_products.get(i).getText();
			// Removing all added item from cart
			if (button_name.equalsIgnoreCase("Remove")) {
				list_of_products.get(i).click();
				String text = driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).getText();
				Reporter.log( "item is removed. Number of items in cart is:"+ text,true);
			}
		}
		String text = driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).getText();
		Assert.assertEquals(text, "");
		Reporter.log("Cart is empty", true);

	}

	@AfterMethod
	public void close() {
		driver.close();
	}

}
