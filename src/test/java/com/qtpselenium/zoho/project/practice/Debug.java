package com.qtpselenium.zoho.project.practice;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.qtpselenium.zoho.project.base.BaseTest;

public class Debug extends BaseTest
{  
	
	
	
    @Test
    public void test2()
    {
        init();
        
       test = extent.startTest("createLeadTest");
        
        /*
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Driver\\chromedriver.exe");

		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("--disable-notifications");
		ops.addArguments("disable-infobars");
		ops.addArguments("--start-maximized");
		System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY,
				System.getProperty("user.dir") + "\\DriverLog\\chrome.log");
		System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
		WebDriver driver = new ChromeDriver(ops);
		
		*/
        
        System.setProperty("webdriver.gecko.driver", pro.getProperty("geckodriver_exe"));

		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,
				System.getProperty("user.dir") + "\\DriverLog\\mozila.log");

		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		
		driver.get("https://www.zoho.com/");
		
		//click("loginLink_xpath");
		driver.findElement(By.xpath("//a[@class='zh-login']")).click();
		//type("loginid_xpath", userName);
		driver.findElement(By.xpath("//*[@id=\"lid\"]")).sendKeys("protivaae@gmail.com");
		//type("password_xpath", password);
		driver.findElement(By.xpath("//*[@id=\"pwd\"]")).sendKeys("Mimo949658");
		
		//click("siginButton_xpath");
		
		driver.findElement(By.xpath("//*[@id=\"signin_submit\"]")).click();
		
		wait(3);
		click("crmlink_xpath");
		wait(2);
		click("leadsTab_xpath");
		wait(2);
		click("plusButton_xpath");
		type("leadsCompany_xpath", "Bou");
		type("leadsLastName_xpath", "Mamun");
		click("leadsSaveButton_xpath");
		wait(5);
		click("leadsTab_xpath");
		wait(4);
		List<WebElement> name = driver.findElements(By.xpath("//*[@id='row1']/td[4]/span/a"));
		
		System.out.println("Name size--->"+name.size());
		
		System.out.println(name.get(1).getText());
		
		
		
}
  
    @AfterMethod
	public void quitt() {
		

		if (extent != null) {
			extent.endTest(test);
			extent.flush();
		}
		
		if (driver != null) {
			driver.quit();
		}

	}
    
    
    
    
}
