package com.qtpselenium.zoho.project.testcases;

import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qtpselenium.zoho.project.base.BaseTest;
import com.qtpselenium.zoho.project.util.DataUtil;
import com.qtpselenium.zoho.project.util.ExcelReadWrite;
import com.relevantcodes.extentreports.LogStatus;

public class LoginTest extends BaseTest {
    
	String testCaseName = "LoginTest";
	SoftAssert softAssert;
	ExcelReadWrite xls;
	
	@Test(dataProvider = "getData")
	public void doLoginTest(Hashtable<String, String> data)
	{
		
		if (!DataUtil.isRunnable(testCaseName, xls) || data.get("Runmode").equals("N")) {
			test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}
		
		test = extent.startTest("LoginTest");
		test.log(LogStatus.INFO, data.toString());
		
		openBrowser(data.get("Browser"));
		navigate("appurl");
		
		boolean actualResult = doLogin(data.get("Username"), data.get("Password"));
		boolean expectedResult = false;
		if(data.get("ExpectedResult").equals("Pass"))
		{
			expectedResult = true;
		}
		else
		{
			expectedResult = false;
		}
		
		if(expectedResult!=actualResult)
		{
			reportFailure("Login Test Failed");
		}
		else
		{
			reportPass("Login Test Passed");
		}
		
		
		
		
		
		
			
			
		
		
		
		//System.out.println(driver.findElements(By.tagName("iframe")).size());
	}
	
	
	@BeforeMethod
	public void init() {
		softAssert = new SoftAssert();

	}
	
	@BeforeTest
	public void load()
	{
		init();
	}

	@AfterMethod
	public void quit() {
		try {
			softAssert.assertAll();

		} catch (Error e) {
			test.log(LogStatus.FAIL, e.getMessage());
		}
		
		if(extent!=null){
			extent.endTest(test);
			extent.flush();
		}
		if (driver != null) {
			driver.quit();
		}

	}

	@DataProvider()
	public Object getData() {
	
		xls = new ExcelReadWrite(pro.getProperty("xlsPath2"));

		return DataUtil.getTestData(xls, testCaseName);
	}

	
	
}
