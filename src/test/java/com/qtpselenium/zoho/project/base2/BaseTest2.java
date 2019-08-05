package com.qtpselenium.zoho.project.base2;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;

public class BaseTest2 
{
    public WebDriver driver;
    
	public void openBrowser(String browser)
	{
		
		if(browser.equalsIgnoreCase("Mozilla"))
		{   DesiredCapabilities cap = null;
			cap = DesiredCapabilities.firefox();
			cap.setPlatform(Platform.WINDOWS);
			String hubUrl = "http://192.168.0.2:8090/wd/hub";
			try {
			    driver = new RemoteWebDriver(new URL(hubUrl), cap);
			    driver.manage().window().maximize();
			    driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
				
			} catch (MalformedURLException e) {

				e.printStackTrace();
			}
		}
		else if(browser.equalsIgnoreCase("chrome"))
		{
			DesiredCapabilities cap = null;
			// 1. Define desire capability
            // In Grid we have to use 'ChromeOptions' and we have to merge 
			//with DesiredCapabilities and we have to pass 'options' to RemoteWebDriver
			//options.merge(cap);
		    cap = new DesiredCapabilities();
			cap.setBrowserName("chrome");
			cap.setPlatform(Platform.WINDOWS);

			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			options.addArguments("disable-infobars");
			options.addArguments("--start-maximized");
			options.merge(cap);
			String hubUrl = "http://192.168.0.2:8090/wd/hub";
			try {
			    driver = new RemoteWebDriver(new URL(hubUrl), options);
				driver.get("http://www.freecrm.com");
				System.out.println(driver.getTitle());
			} catch (MalformedURLException e) {

				e.printStackTrace();
			}

		}
		
		
		
	}
	

	@AfterTest
	public void quit()
	{
		if(driver!=null)
			driver.quit();
	}

}
