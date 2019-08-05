package com.qtpselenium.zoho.project.practice;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class SpiceJet {

	WebDriver driver;

	@Test
	public void depareDate() {

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//Driver//chromedriver.exe");

		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("--disable-notifications");
		ops.addArguments("disable-infobars");
		ops.addArguments("--start-maximized");
		System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY,
				System.getProperty("user.dir") + "\\DriverLog\\chrome.log");
		System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
		driver = new ChromeDriver(ops);

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.navigate().to("https://www.spicejet.com/");
		//https://www.cheapoair.com/
		//div[@class='col-6']//input[@placeholder='Pick a date']
		WebElement ele = driver.findElement(By.xpath("//input[@id='ctl00_mainContent_view_date1']"));
		String date = "05/09/2019";
		//Aug 20 2019
		selectDateByJS(driver, ele, date);

	}

	public  void selectDateByJS(WebDriver driver, WebElement element, String dateVal) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('value','" + dateVal + "');", element);
        //Injecting javaScript. I am changing the attribute of the value.
		//Lets say in the html dom-->value = "02/03/2019" and I am going to change it
		//05/09/2019 inside the html DOM. Through selenium we can not do it.
	}

}
