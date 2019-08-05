package com.qtpselenium.zoho.project.practice;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.fluent.Request;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class MainMethod {
	static WebDriver driver;

	public static void main(String[] args) {
		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\Driver\\geckodriver.exe");

		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,
				System.getProperty("user.dir") + "\\DriverLog\\mozila.log");

		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.navigate().to("https://www.cnn.com/");

		String part1 = "//*[@id=\"homepage1-zone-1\"]/div[2]/div/div[1]/ul/li[";
		String part2 = "]/article/div/div/h3/a/span[1]";

		for (int i = 2; i <= 9; i++) {
			String text = driver.findElement(By.xpath(part1 + i + part2)).getText();
			System.out.println(text);
			driver.findElement(By.xpath(part1 + i + part2)).click();
			System.out.println(driver.getTitle());
			driver.navigate().back();
		}

		// Now there are various issue for above approached. Lets say
		// all link here are dynamic. The linked number showing here
		// max 9. If the link number goes up or down then above approached will
		// not work. The solution are:------->Below

		// I am going to create a method here that will give any given
		// locator present or not. If present then it will execute
		// else it will exit from loop--->Its mean does not exit

		// Re-write above loop
        //This loop dynamically will detect all the element on the web page with help of 
		//isElementPresent() method
		int i = 2;
		while (isElementPresent(part1 + i + part2, "xpath")) {
			String text = driver.findElement(By.xpath(part1 + i + part2)).getText();
			System.out.println(text);
			driver.findElement(By.xpath(part1 + i + part2)).click();
			System.out.println(driver.getTitle());
			driver.navigate().back();
			i++;
		}
		
		//Modify the above while loop
		
		/*
		
		int i=2;
		while(isElementPresent(part1+i+part2, "xpath")){
			String text = driver.findElement(By.xpath(part1+i+part2)).getText();
			System.out.println(text);
			// get response code
			String url=driver.findElement(By.xpath(part1+i+part2)).getAttribute("href");
			if(getResponseCode(url)){
				driver.findElement(By.xpath(part1+i+part2)).click();
				// response code
				System.out.println(driver.getTitle());
				driver.navigate().back();// back button
			}else{
				// report a failure
			}
			i++;
		}
     
		*/
	}

	// true - present
	// false - not present
	public static boolean isElementPresent(String locator, String locatorType) {
		List<WebElement> allElements = null;
		if (locatorType.equals("xpath"))
			allElements = driver.findElements(By.xpath(locator));
		else if (locatorType.equals("cssSelector"))
			allElements = driver.findElements(By.cssSelector(locator));
		else if (locatorType.equals("id"))
			allElements = driver.findElements(By.id(locator));
		else if (locatorType.equals("name"))
			allElements = driver.findElements(By.name(locator));

		if (allElements.size() == 0)
			return false;
		else
			return true;
	}
	
	//Some time we need to find out response code of certain URL
	//List of HTTP status codes
	//2xx Success-->3xx Redirection-->4xx Client errors
	//I will check response code of url before clicking on it
	
	
//===================================================================	
	
	//This method will return response code of the given URL
	public static boolean getResponseCode(String url){
		int resp_code=0;
		try {
			resp_code = Request.Get(url).execute().returnResponse().getStatusLine()
			        .getStatusCode();
	        System.out.println("Respose code for URL "+ url +" is -> "+ resp_code);
	        
		} catch (Exception e) {

			e.printStackTrace();
		} 
		
		if(resp_code==200)
        	return true;
        else 
        	return false;
	}

	
	
	
	
	
	

}
