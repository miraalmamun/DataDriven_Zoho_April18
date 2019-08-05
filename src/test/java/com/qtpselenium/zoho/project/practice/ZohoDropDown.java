package com.qtpselenium.zoho.project.practice;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

public class ZohoDropDown {

	public static void main(String[] args) {
		// *[@id='select2-Crm_Potentials_STAGE-results']//li

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
		driver.navigate().to("https://www.zoho.com/");
		driver.findElement(By.xpath("//a[@class='zh-login']")).click();
		driver.findElement(By.xpath("//*[@id='lid']")).sendKeys("protivaae@gmail.com");
		driver.findElement(By.xpath("//*[@id='pwd']")).sendKeys("Mimo949658");
		driver.findElement(By.xpath("//*[@id='signin_submit']")).click();
		// click on crm_link
		driver.findElement(By.xpath("//*[@id='zl-myapps']/div[1]/div[6]/div/a/span")).click();
		// click on deals_link
		driver.findElement(By.xpath("//a[contains(text(),'Deals')]")).click();
		driver.findElement(By.xpath("//*[@id='topRightIcons']/span[1]/link-to/button")).click();
		// click on Stage
		driver.findElement(By.xpath("//*[@id='Potentials_fldRow_STAGE']/div[2]/div/span/span[1]/span")).click();

		List<WebElement> element = driver.findElements(By.xpath("//span[@class='select2-results']/ul/li"));
		System.out.println("Drop------>" + element.size());

		for (WebElement ele : element) {
			   
			String name = ele.getText();
			System.out.println(name);
			
			if (ele.getText().equals("Qualification")) {
				
				ele.click();
				break;
			}
			
		}

	}

}
