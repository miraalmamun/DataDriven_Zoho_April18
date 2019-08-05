package com.qtpselenium.zoho.project.practice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class DateFormt {

	WebDriver driver;

	@Test
	public void dateFormat() throws Throwable {

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

		// String getDateStr =
		// driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div/div")).getText().trim();

		String setDateStr = "2/6/2019";// It is better not to use '02'

		String currDateStr = driver.findElement(By.className("ui-datepicker-title")).getText().trim();
		System.out.println(currDateStr);
		Date setDate = new SimpleDateFormat("dd/MM/yyyy").parse(setDateStr);

		Date currDate = new SimpleDateFormat("MMMM yyyy").parse(currDateStr);

		String dayToBeSelected = new SimpleDateFormat("d").format(setDate);

		int monthDiff = Months
				.monthsBetween(new DateTime(currDate).withDayOfMonth(1), new DateTime(setDate).withDayOfMonth(1))
				.getMonths();
		System.out.println(monthDiff);

		boolean isFuture = true;

		if (monthDiff < 0) {
			isFuture = false;
			monthDiff = -1 * monthDiff;
			// I am making 'monthDiff' as positive
			// because I need to use it for 'far loop'

		}

		for (int i = 0; i < monthDiff; i++) {
			if (isFuture) {
				// driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div/a[1]/span")).click();
				// span[text()='Next']
				driver.findElement(By.xpath("//span[text()='Next']")).click();
			} else {
				driver.findElement(By.xpath("//span[text()='Prev']")).click();
				// span[text()='Prev']
				// driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div/a[2]/span")).click();
			}

		}

		driver.findElement(By.xpath("//a[text()='" + dayToBeSelected + "']")).click();

		Thread.sleep(9000);
		driver.quit();
	}

}
