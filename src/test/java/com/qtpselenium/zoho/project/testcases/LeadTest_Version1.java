package com.qtpselenium.zoho.project.testcases;

import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qtpselenium.zoho.project.base.BaseTest;
import com.qtpselenium.zoho.project.util.DataUtil;
import com.qtpselenium.zoho.project.util.ExcelReadWrite;
import com.relevantcodes.extentreports.LogStatus;

public class LeadTest_Version1 extends BaseTest {

	SoftAssert softAssert;
	ExcelReadWrite xls;
	String testCaseName = "CreateLeadTest";

	@Test(priority = 1, dataProvider = "getData")
	public void createLeadTest(Hashtable<String, String> data) {
		test = extent.startTest("createLeadTest");
		test.log(LogStatus.INFO, data.toString());
		if (!DataUtil.isRunnable(testCaseName, xls) || data.get("Runmode").equals("N")) {
			test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}

		openBrowser("Mozila");
		navigate("appurl");
		// I am reading userName and password from properties file
		// because it is global userName and password for login
		doLogin(pro.getProperty("userName"), pro.getProperty("password"));
		// After Login I am going to create the Lead here
		// Lets identify Object and its locator

		click("crmlink_xpath");
		click("leadsTab_xpath");
		wait(2);
		// To create a new lead we need to click
		click("plusButton_xpath");
		type("leadsCompany_xpath", "Samsung");
		type("leadsLastName_xpath", "Mossarrof");
		click("leadsSaveButton_xpath");
		click("leadsTab_xpath");
		// Validate whether leads created or not
	}

	@Test(priority = 2)
	public void convertLeadTest() {

	}

	@Test(priority = 3)
	public void deleteLeadAccountTest() {

	}

	@BeforeMethod
	public void initt() {
		softAssert = new SoftAssert();

	}

	@AfterMethod
	public void quit() {
		try {
			softAssert.assertAll();

		} catch (Error e) {
			test.log(LogStatus.FAIL, e.getMessage());
		}

		if (extent != null) {
			extent.endTest(test);
			extent.flush();
		}
		if (driver != null) {
			driver.quit();
		}

	}

	@DataProvider
	public Object getData() {
		init();
		xls = new ExcelReadWrite(pro.getProperty("xlsPath2"));

		return DataUtil.getTestData(xls, testCaseName);
	}

}
