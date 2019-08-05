package com.qtpselenium.zoho.project.practice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

public class IframeAndDate {
	public static WebDriver driver;

	public static void main(String[] args) throws Throwable {
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
		driver.navigate().to("https://jqueryui.com/datepicker/");
		// List<WebElement> elements = driver.findElements(By.tagName("iframe"));
		// System.out.println(elements.size());
		driver.switchTo().frame(0);
		driver.findElement(By.xpath("//*[@id= 'datepicker']")).click();

		// *[@id='ui-datepicker-div']/div/a[2]/span-->Forward
		// *[@id='ui-datepicker-div']/div/a[1]/span-->Back

		String date2 = "15/8/2019";

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = simpleDateFormat.parse(date2);
		simpleDateFormat = new SimpleDateFormat("MMMM");
		String monthToBeSelected = simpleDateFormat.format(date);
		simpleDateFormat = new SimpleDateFormat("yyyy");
		String yearToBeSelected = simpleDateFormat.format(date);
		String monthYearToBeSelected = monthToBeSelected + " " + yearToBeSelected;
		System.out.println(monthYearToBeSelected);
		String e = driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div/div")).getText().trim();
		System.out.println(e);
		simpleDateFormat = new SimpleDateFormat("d");
		String dayToBeSelected = simpleDateFormat.format(date);

		Date currentDate = new Date();
		System.out.println(currentDate.compareTo(date));

		while (true) {
			if (currentDate.compareTo(date) == 1) {
				driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div/a[1]/span")).click();
			} else if (currentDate.compareTo(date) == -1) {
				driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div/a[2]/span")).click();

			}

			if (monthYearToBeSelected
					.equals(driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div/div")).getText().trim())) {
				System.out.println("I am in break");
				break;
			}

			System.out.println("I am in while loop and after break");

		}
		System.out.println("I am trying to click date " + dayToBeSelected);

		

		List<WebElement> elements = driver.findElements(By.xpath("//*[@id='ui-datepicker-div']/table/tbody/tr/td"));

		for (int i = 0; i < elements.size(); i++) {

			if (elements.get(i).getText().trim().equals(dayToBeSelected)) {
				elements.get(i).click();
				break;
			}
		}

		Thread.sleep(3000);

		driver.switchTo().defaultContent();
		driver.quit();
	}

}
