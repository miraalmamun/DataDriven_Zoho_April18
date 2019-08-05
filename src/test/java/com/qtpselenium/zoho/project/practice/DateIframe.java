package com.qtpselenium.zoho.project.practice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

public class DateIframe {
	static WebDriver driver;

	public static void main(String[] args) {
		
		
		
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//Driver//chromedriver.exe");

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
        //List<WebElement> elements = driver.findElements(By.tagName("iframe"));
        //System.out.println(elements.size());
        driver.switchTo().frame(0);
        driver.findElement(By.xpath("//*[@id= 'datepicker']")).click();
		
		String d = "15/9/2019";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date dateTobeSelected = sdf.parse(d);
			Date currentDate = new Date();
			sdf = new SimpleDateFormat("MMMM");
			String monthToBeSelected = sdf.format(dateTobeSelected);
			sdf = new SimpleDateFormat("yyyy");
			String yearToBeSelected = sdf.format(dateTobeSelected);
			sdf = new SimpleDateFormat("d");
			String dayToBeSelected = sdf.format(dateTobeSelected);
			// June 2016
			String monthYearToBeSelected = monthToBeSelected + " " + yearToBeSelected;

			while (true) {
				if (currentDate.compareTo(dateTobeSelected) == 1) {
					// back
					driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div/a[1]/span")).click();
				} else if (currentDate.compareTo(dateTobeSelected) == -1) {
					// front
					driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div/a[2]/span")).click();
				}

				if (monthYearToBeSelected.equals(
						driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div/div")).getText().trim())) {
					System.out.println("I am in break");
					break;
				}
                
			}
			System.out.println("Trying to click date");
			driver.findElement(By.xpath("//td[text()='" + dayToBeSelected + "']")).click();

		} catch (ParseException e) {

			e.printStackTrace();
			System.out.println("Helloo--->"+e.getMessage());
		}
		
		
	}

}
