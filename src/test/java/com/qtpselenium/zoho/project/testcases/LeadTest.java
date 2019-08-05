package com.qtpselenium.zoho.project.testcases;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qtpselenium.zoho.project.base.BaseTest;
import com.qtpselenium.zoho.project.util.DataUtil;
import com.qtpselenium.zoho.project.util.ExcelReadWrite;
import com.relevantcodes.extentreports.LogStatus;

public class LeadTest extends BaseTest {

	SoftAssert softAssert;
	ExcelReadWrite xls;

	@Test(priority = 1, dataProvider = "getData")
	public void createLeadTest(Hashtable<String, String> data) {
		test = extent.startTest("CreateLeadTest");
		test.log(LogStatus.INFO, data.toString());

		if (!DataUtil.isRunnable("CreateLeadTest", xls) || data.get("Runmode").equals("N")) {
			test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}

		openBrowser(data.get("Browser"));
		navigate("appurl");
		// I am reading userName and password from production.properties file
		// because it is global userName and password for login for production
		// environment
		doLogin(envPro.getProperty("userName"), envPro.getProperty("password"));
		// After Login I am going to create the Lead here
		// Lets identify Object and its locator

		click("crmlink_xpath");
		click("leadsTab_xpath");
		// To create a new lead we need to click
		click("plusButton_xpath");
		type("leadsCompany_xpath", data.get("LeadCompany"));
		type("leadsLastName_xpath", data.get("LeadLastName"));
		click("leadsSaveButton_xpath");
		wait(3);
		click("leadsTab3_xpath");
		// leadsTab_xpath
		// Validate whether leads created or not
		// Once the lead are created it should be in the table I need to
		// verify that this lead are in the html table
		// In order to do this I am going to make a method in the BaseTest class
		// This function all about for this application only
		// Its like doLogin()-->Method-->Application function

		int rNum = getLeadRowNum(data.get("LeadLastName"));

		if (rNum == -1) {
			reportFailure("Lead not found in lead table " + data.get("LeadLastName"));

		}
		reportPass("Lead created successfully and Lead name is " + data.get("LeadLastName"));
		test.log(LogStatus.PASS, "CreateLeadTest passed");
		wait(2);
		takeScreenShot();

	}

	@Test(priority = 2, dataProvider = "getData")
	public void convertLeadTest(Hashtable<String, String> data) {

		test = extent.startTest("ConvertLeadTest");
		test.log(LogStatus.INFO, data.toString());

		if (!DataUtil.isRunnable("ConvertLeadTest", xls) || data.get("Runmode").equals("N")) {
			test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}

		openBrowser(data.get("Browser"));
		navigate("appurl");
		doLogin(envPro.getProperty("userName"), envPro.getProperty("password"));
		click("crmlink_xpath");
		click("leadsTab_xpath");
		wait(2);
		// After clicking the lead tab. Now I need to click LeadName in order
		// to Convert it. So lets get the leads row number

		// int rNum = getLeadRowNum(data.get("LeadLastName"));
		// How do I pass the locator(xpath) for clicking on it.
		// The problem is here xpath is not final here. It is dynamic
		// in nature
		// I am getting the row number here. I need to click
		// on it. If the row number 3, I want to click on tr[3]
		// If the row number 2, I want to click on tr[2]---->
		// So I am going to create a custom method here. I will pass
		// on this method as a parameter 'LeadLastName'
		clickOnLead(data.get("LeadLastName"));
		click("convertButton_xpath");
		click("convertButtonSave_xpath");
		// If convert successful It should add to the Accounts Section LeadCompany
		// So I am going to validate it
		click("accountsLink_xpath");
		int rNum = getAccountsRowNum(data.get("LeadCompany"));

		if (rNum == -1) {
			reportFailure("Lead convert failed " + data.get("LeadCompany"));

		}
		reportPass("Lead converted successfully and Account Name is  " + data.get("LeadCompany"));
		test.log(LogStatus.PASS, "ConvertLeadTest passed ");
		wait(2);
		takeScreenShot();

	}

	@Test(priority = 3, dataProvider = "getDataDeleteLead")
	public void deleteLeadAccountTest(Hashtable<String, String> data) {

		test = extent.startTest("DeleteLeadAccountTest");
		test.log(LogStatus.INFO, data.toString());

		if (!DataUtil.isRunnable("DeleteLeadAccountTest", xls) || data.get("Runmode").equals("N")) {
			test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}

		openBrowser(data.get("Browser"));
		navigate("appurl");

		doLogin(envPro.getProperty("userName"), envPro.getProperty("password"));
		click("crmlink_xpath");
		click("leadsTab_xpath");
		clickOnLead(data.get("LeadLastName"));
		waitForPageToLoad();
		click("deleteLead_xpath");
		click("deleteLink_xpath");
		driver.findElement(By.xpath("//*[@id='deleteButton']")).click();
		// *[@id="deleteButton"]
		// driver.switchTo().alert().accept();
		// driver.switchTo().defaultContent();
		// acceptAlert();

		wait(2);
		click("leadsTab_xpath");
		int rNum = getLeadRowNum(data.get("LeadLastName"));
		if (rNum != -1) {
			reportFailure("Could not delete the lead");

		} else {
			reportPass("Lead deleted successfully");
			takeScreenShot();

		}

	}

	@BeforeMethod
	public void initt() {
		softAssert = new SoftAssert();

	}

	@BeforeClass
	public void load() {
		init();
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

	@DataProvider()
	public Object getData() {

		xls = new ExcelReadWrite(pro.getProperty("xlsPath2"));

		return DataUtil.getTestData(xls, "CreateLeadTest");
	}

	@DataProvider()
	public Object getDataDeleteLead() {

		xls = new ExcelReadWrite(pro.getProperty("xlsPath2"));

		return DataUtil.getTestData(xls, "DeleteLeadAccountTest");
	}

}
