package com.qtpselenium.zoho.project.practice;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

public class DropDown2 {

	public static void main(String[] args) {

		WebDriver driver;

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
		driver.navigate().to("https://www.facebook.com/");

		WebElement element1 = driver.findElement(By.id("day"));
		// WebElement element2 = driver.findElement(By.id("month"));
		// WebElement element3 = driver.findElement(By.id("year"));

		Select select = new Select(element1);

		List<WebElement> listDays = select.getOptions();
		System.out.println(listDays.size());
		int TotalDays = listDays.size() - 1;
		System.out.println("Total days are: " + TotalDays);

		for (int i = 0; i < listDays.size(); i++) {
			String dayVal = listDays.get(i).getText().trim();
			System.out.println(dayVal);
			if (dayVal.equals("15")) {
				listDays.get(i).click();
				break;
			}
		}

	}

}
