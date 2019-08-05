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

public class PotentialTest extends BaseTest {

	SoftAssert softAssert;
	ExcelReadWrite xls;

	@Test(priority = 1, dataProvider = "getDataCreatePotentialTest")
	public void createPotentialTest(Hashtable<String, String> data) {

		test = extent.startTest("CreatePotentialTest");
		test.log(LogStatus.INFO, "Data coming from Exel-->" + data.toString());
		if (!DataUtil.isRunnable("CreatePotentialTest", xls) || data.get("Runmode").equals("N")) {
			test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}

		openBrowser(data.get("Browser"));
		navigate("appurl");
		doLogin(envPro.getProperty("userName"), envPro.getProperty("password"));
		click("crmlink_xpath");
		click("dealsLink_xpath");
		click("createDeals_xpath");
		type("dealName_xpath", data.get("DealName"));
		type("accountName_xpath", data.get("AccountName"));
		selectDate(data.get("ClosingDate"));
		click("stage_xpath");
		selectDropDown("Qualification");

		// type2("stageSearch_xpath", data.get("Stage"));
		// type("stage_xpath", data.get("Stage"));
		// type("stage_xpath",data.get("Stage"));

		// video#15
		click("saveDeal_xpath");
		wait(3);
		click("dealsLink_xpath");
		wait(3);
		if (getDealsRowNum(data.get("DealName")) != -1) {
			drawBorder(getElement("dealSaveTable_xpath"), driver);
			takeScreenShot();
			reportPass("Deals created successfully");
		} else {
			reportFailure("Deals did not create");
		}

	}

	@Test(priority = 2, dependsOnMethods = { "createPotentialTest" })
	public void deletePotentialAccountTest() {

	}

	@DataProvider()
	public Object getDataCreatePotentialTest() {

		xls = new ExcelReadWrite(pro.getProperty("xlsPath2"));

		return DataUtil.getTestData(xls, "CreatePotentialTest");
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
	
	@BeforeTest
	public void load()
	{
		init();
	}
	

}
