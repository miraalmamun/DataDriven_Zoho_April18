package com.qtpselenium.zoho.project.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

//import com.aventstack.extentreports.MediaEntityBuilder;
//import com.aventstack.extentreports.Status;
import com.qtpselenium.zoho.project.util.ExtentManager;
//import com.qtpselenium.zoho.project.util.ExtentManager2;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseTest {
	public WebDriver driver;
	public Properties pro;
	public Properties envPro;
	public ExtentReports extent = ExtentManager.getInstance();
	public ExtentTest test;
	boolean gridRun = false;

	public void init() {
		// initialize the pro file
		if (pro == null) {
			pro = new Properties();
			envPro = new Properties();
			try {
				FileInputStream fs = new FileInputStream(
						System.getProperty("user.dir") + "//src//test//resources//projectconfig.properties");
				pro.load(fs);
				// I am loading here main project configure(projectconfig) file
				// Retrieve data from main project file and hold it into
				// environ variable.
				String environ = pro.getProperty("environment");
				System.out.println("Environment for test:--> " + environ);
				fs = new FileInputStream(
						System.getProperty("user.dir") + "//src//test//resources//" + environ + ".properties");
				envPro.load(fs);

				System.out.println("User Name: " + envPro.get("userName"));
				System.out.println("Password : " + envPro.get("password"));

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

	}
	/*
	 * public void init1() { // initialize the pro file if (pro == null) { pro = new
	 * Properties(); envPro = new Properties(); try { FileInputStream fs = new
	 * FileInputStream( System.getProperty("user.dir") +
	 * "//src//test//resources//projectconfig.properties"); pro.load(fs); // I am
	 * loading here main project configure(projectconfig) file // Retrieve data from
	 * main project file and hold it into // environ variable. String environ =
	 * pro.getProperty("environment");
	 * System.out.println("Environment for test:--> " + environ); fs = new
	 * FileInputStream( System.getProperty("user.dir") + "//src//test//resources//"
	 * + environ + ".properties"); envPro.load(fs); System.out.println("User Name: "
	 * + envPro.get("userName")); System.out.println("Password : " +
	 * envPro.get("password"));
	 * 
	 * } catch (Exception e) {
	 * 
	 * e.printStackTrace(); } }
	 * 
	 * }
	 * 
	 */

	/************ Below methods Interact with UI ***************************/

	public void openBrowser(String bType) {
		// This method will open the given Browser
		test.log(LogStatus.INFO, "Opening browser " + bType);
		if (!gridRun) {

			if (bType.equalsIgnoreCase("Mozila")) {

				System.setProperty("webdriver.gecko.driver", pro.getProperty("geckodriver_exe"));

				System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,
						System.getProperty("user.dir") + "\\DriverLog\\mozila.log");
				// Turn off notification
				FirefoxProfile foxProfile = new FirefoxProfile();
				foxProfile.setPreference("dom.webnotifications.enabled", false);

				driver = new FirefoxDriver();

			} else if (bType.equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver", pro.getProperty("chromedriver_exe"));

				ChromeOptions ops = new ChromeOptions();
				ops.addArguments("--disable-notifications");
				ops.addArguments("disable-infobars");
				ops.addArguments("--start-maximized");
				System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY,
						System.getProperty("user.dir") + "\\DriverLog\\chrome.log");
				System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
				driver = new ChromeDriver(ops);
			} else if (bType.equalsIgnoreCase("IE")) {

			} else if (bType.equalsIgnoreCase("Edge")) {

			}
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			test.log(LogStatus.INFO, bType + " browser opened successfully");
		} else {
			DesiredCapabilities cap = null;

			if (bType.equalsIgnoreCase("Mozilla")) {
				cap = DesiredCapabilities.firefox();
				cap.setPlatform(Platform.WINDOWS);
				String hubUrl = "http://172.29.4.52:8090/wd/hub";
				try {
					driver = new RemoteWebDriver(new URL(hubUrl), cap);
					driver.manage().window().maximize();
					driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

				} catch (MalformedURLException e) {

					e.printStackTrace();
				}
			} else if (bType.equalsIgnoreCase("chrome")) {

				// 1. Define desire capability
				// In Grid we have to use 'ChromeOptions' and we have to merge
				// with DesiredCapabilities and we have to pass 'options' to RemoteWebDriver
				// options.merge(cap);
				cap = new DesiredCapabilities();
				cap.setBrowserName("chrome");
				cap.setPlatform(Platform.WINDOWS);

				ChromeOptions options = new ChromeOptions();
				options.addArguments("--disable-notifications");
				options.addArguments("disable-infobars");
				options.addArguments("--start-maximized");
				options.merge(cap);
				String hubUrl = "http://172.29.4.52:8090/wd/hub";
				try {
					driver = new RemoteWebDriver(new URL(hubUrl), options);
					driver.get("http://www.freecrm.com");
					System.out.println(driver.getTitle());
				} catch (MalformedURLException e) {

					e.printStackTrace();
				}

			}

		}
	}

	public void navigate(String urlKey) {
		// This method will navigate URL
		test.log(LogStatus.INFO, "Navigating to " + envPro.getProperty(urlKey));
		driver.get(envPro.getProperty(urlKey));

	}

	public void click(String locatorKey) {
		// This method will click anywhere on the page
		test.log(LogStatus.INFO, "Clicking on " + locatorKey);
		getElement(locatorKey).click();
		test.log(LogStatus.INFO, "Clicked successfully on " + locatorKey);

	}

	public void type(String locatorKey, String data) {
		// This method will type somewhere on the web page. Will take two parameter
		test.log(LogStatus.INFO, "Typing in " + locatorKey + ". Data - " + data);
		getElement(locatorKey).sendKeys(data);

	}

	public void type2(String locatorKey, String data) {
		// This method will type somewhere on the web page. Will take two parameter
		test.log(LogStatus.INFO, "Typing in " + locatorKey + ". Data - " + data);
		getElement(locatorKey).click();

	}

	public WebElement getElement(String locatorKey) {

		WebElement e = null;
		try {
			if (locatorKey.endsWith("_id")) {
				e = driver.findElement(By.id(pro.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("_name")) {
				e = driver.findElement(By.name(pro.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("_xpath")) {
				e = driver.findElement(By.xpath(pro.getProperty(locatorKey)));
			} else {

				reportFailure("locator not correct :--> " + locatorKey);
				Assert.fail("locator not correct :--> " + locatorKey);
			}
		} catch (Exception ex) {
			reportFailure(ex.getMessage());
			ex.getStackTrace();
			Assert.fail("Failed the test because of : " + ex.getMessage());
		}
		return e;

	}

	/*********** Below methods about validations ****************************/
	public boolean verifyTitle() {
		// If expected title and actual title same return true
		// otherwise return false
		return false;
	}

	public boolean isElementPresent(String locatorKey) {
		// If the given path of an element is present
		// then it will return true
		// otherwise return false
		List<WebElement> elementList = null;
		if (locatorKey.endsWith("_id"))
			elementList = driver.findElements(By.id(pro.getProperty(locatorKey)));
		else if (locatorKey.endsWith("_name"))
			elementList = driver.findElements(By.name(pro.getProperty(locatorKey)));
		else if (locatorKey.endsWith("_xpath"))
			elementList = driver.findElements(By.xpath(pro.getProperty(locatorKey)));
		else {
			reportFailure("Locator is not correct - " + locatorKey);
			Assert.fail("Locator is not correct - " + locatorKey);
		}

		if (elementList.size() == 0)
			return false;
		else
			return true;
	}

	public boolean verifyText(String locatorKey, String expectedTextKey) {
		// If expected text and actual text same then this method return true
		// otherwise return false
		String actualText = getElement(locatorKey).getText().trim();
		String expectedText = pro.getProperty(expectedTextKey);
		if (actualText.equals(expectedText))
			return true;
		else
			return false;

	}

	/************** Reporting **********************************/
	public void reportPass(String msg) {
		test.log(LogStatus.PASS, msg);
	}

	public void reportFailure(String msg) {
		test.log(LogStatus.FAIL, msg);
		takeScreenShot();
		Assert.fail(msg);
	}

	public void takeScreenShot() {

		// Take screenshot and put in the report
		// fileName of the screenshot

		Date d = new Date();
		String screenshotFile = d.toString().replace(":", "_").replace(" ", "_") + ".png";
		String imgPath = ".\\screenshots\\" + screenshotFile;

		// store screenshot in that file
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		// getScreenshotAs()-->Capture the screenshot and store it in the specified
		// location.

		try {
			// FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") +
			// "//screenshots//" + screenshotFile));
			// The png file located here---projectPath/screenshots/date.png

			File path = new File(imgPath);

			FileUtils.copyFile(scrFile, path);

		} catch (IOException e) {

			e.printStackTrace();
		}
		// put screenshot file in reports

		test.log(LogStatus.INFO, "Screenshot-> "
				+ test.addScreenCapture(System.getProperty("user.dir") + "\\screenshots\\" + screenshotFile));

		// test.log(Status.INFO,
		// "ScreenShot",MediaEntityBuilder.createScreenCaptureFromPath("."+imgPath).build());

//        try {
//			test.log(Status.INFO, "ScreenShot" , MediaEntityBuilder.createScreenCaptureFromPath("."+imgPath).build());
//		} catch (IOException e) {
//
//			e.printStackTrace();
//		}

	}

	public void clickAndWait(String locator_clicked, String locator_pres) {
		test.log(LogStatus.INFO, "Clicking and waiting on - " + locator_clicked);
		int count = 5;
		for (int i = 0; i < count; i++) {
			getElement(locator_clicked).click();
			wait(2);
			if (isElementPresent(locator_pres))
				break;
		}
	}

	/******************************************************************/

	public void acceptAlert() {
		WebDriverWait wait = new WebDriverWait(driver, 6);
		wait.until(ExpectedConditions.alertIsPresent());
		test.log(LogStatus.INFO, "Accepting alert");
		driver.switchTo().alert().accept();
		driver.switchTo().defaultContent();
	}

	public void acceptAlert2() {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.alertIsPresent());
		test.log(LogStatus.INFO, "Accepting alert");
		driver.switchTo().alert().accept();
		driver.switchTo().defaultContent();
	}

	public void waitForPageToLoad() {
		wait(1);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String state = (String) js.executeScript("return document.readyState");

		while (!state.equals("complete")) {
			wait(2);
			state = (String) js.executeScript("return document.readyState");
		}
	}

	public void wait(int timeToWaitInSec) {
		try {
			Thread.sleep(timeToWaitInSec * 1000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	/************** Application Functions ****************************/

	public boolean doLogin(String userName, String password) {
		test.log(LogStatus.INFO, "Trying to login with-> " + userName + " , " + password);
		click("loginLink_xpath");
		wait(1);
		waitForPageToLoad();
		type("loginid_xpath", userName);
		type("password_xpath", password);
		click("siginButton_xpath");
		if (isElementPresent("crmlink_xpath")) {
			test.log(LogStatus.INFO, "Login Success");
			return true;
		} else {
			test.log(LogStatus.INFO, "Login Failed");
			return false;
		}

	}

	// I will sand leads last name here to verify
	// lead is added to the table. This function will tell
	// me the row number on which the leads is in.. This function
	// will give me row number for the leads
	public int getLeadRowNum(String leadName) {
		/*
		 * If this method return '-1'-->The given name(leadName) is not exist in html
		 * table If this method return any positive number--> The given name is exist I
		 * will provide in this table xpath of the column. So this method will return
		 * all cells number. This method will return (i+1) because i start from 0. Thats
		 * why row number here # return (i+1); If name exist This function just me give
		 * the information. I do not need to fail the test here Failed or passed I will
		 * do when I get the result from it.
		 * 
		 */

		test.log(LogStatus.INFO, "Finding the lead " + leadName);
		// List<WebElement> leadNames =
		// driver.findElements(By.xpath(pro.getProperty("leadNamesCol_xpath")));
		// List<WebElement> leadNames =
		// driver.findElements(By.xpath("//*[@id='row1']/td[4]/span/a"));
		// table[@id='listViewTable']/tbody/tr/td[4]
		// I am holding all columns value in the List Variable(leadNames)
		List<WebElement> leadNames = driver.findElements(By.xpath(pro.getProperty("leadNamesCol_xpath")));

		for (int i = 0; i < leadNames.size(); i++) {
			System.out.println(leadNames.get(i).getText());
			if (leadNames.get(i).getText().trim().equals(leadName)) {
				test.log(LogStatus.INFO, "Lead found in row number " + (i + 1));
				return (i + 1);
			}
		}

		test.log(LogStatus.INFO, "Lead not found");
		return -1;

	}

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

	public void clickOnLead(String leadName) {
		test.log(LogStatus.INFO, "Clicking on the lead " + leadName);
		int rNum = getLeadRowNum(leadName);
		// I have to click on this row
		driver.findElement(By.xpath(
				pro.getProperty("leadNamesColPartOne_xpath") + rNum + pro.getProperty("leadNamesColPartTwo_xpath")))
				.click();
		test.log(LogStatus.INFO, "Clicked successfull on the lead--> " + leadName);

	}

	public int getAccountsRowNum(String LeadCompany) {
		/*
		 * If this method return '-1'-->The given name(leadName) is not exist in html
		 * table If this method return any positive number--> The given name is exist I
		 * will provide in this table xpath of the column. So this method will return
		 * all cells number. This method will return (i+1) because i start from 0. Thats
		 * why row number here # return (i+1); If name exist This function just me give
		 * the information. I do not need to fail the test here Failed or passed I will
		 * do when I get the result from it.
		 * 
		 */

		test.log(LogStatus.INFO, "Finding the lead Accounts number " + LeadCompany);
		// List<WebElement> leadNames =
		// driver.findElements(By.xpath(pro.getProperty("leadsAccounts_xpath")));
		// List<WebElement> leadNames =
		// I am holding all columns value in the List Variable(leadNames)
		List<WebElement> LeadCompanies = driver.findElements(By.xpath(pro.getProperty("leadsAccounts_xpath")));

		for (int i = 0; i < LeadCompanies.size(); i++) {
			System.out.println(LeadCompanies.get(i).getText());
			if (LeadCompanies.get(i).getText().trim().equals(LeadCompany)) {
				test.log(LogStatus.INFO, "Account found in row number " + (i + 1));
				return (i + 1);
			}
		}

		test.log(LogStatus.INFO, "Account not found");
		return -1;

	}

	public void drawBorder(WebElement e, WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='3px solid red'", e);
	}

	// drawBorder(getElement("loginid_xpath"), driver);

	public void selectDate(String d) {
		test.log(LogStatus.INFO, "This date coming from Excel--> " + d);

		// convert the string date(input) in date object
		click("dateTextField_xpath");

		test.log(LogStatus.INFO, "This date from app: " + getText("monthYearDisplayed_xpath"));

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
					click("back_xpath");
				} else if (currentDate.compareTo(dateTobeSelected) == -1) {
					// front
					click("forward_xpath");
				}

				if (monthYearToBeSelected.equals(getText("monthYearDisplayed_xpath"))) {
					test.log(LogStatus.INFO,
							"Get Month and Year from applicatin-->" + getText("monthYearDisplayed_xpath"));
					test.log(LogStatus.INFO, " Match Month and Year--> " + monthYearToBeSelected);
					break;
				}

			}

			driver.findElement(By.xpath("//td[text()='" + dayToBeSelected + "']")).click();

			test.log(LogStatus.INFO, "Date Selection Successful " + d);

		} catch (ParseException e) {

			e.printStackTrace();
		}
	}

	public String getText(String locatorKey) {
		test.log(LogStatus.INFO, "Getting text from " + locatorKey);
		return getElement(locatorKey).getText();

	}

	public int getDealsRowNum(String leadName) {

		test.log(LogStatus.INFO, "Finding deals by Deal Name " + leadName);

		List<WebElement> leadNames = driver.findElements(By.xpath(pro.getProperty("dealSaveTable_xpath")));

		for (int i = 0; i < leadNames.size(); i++) {
			if (leadNames.get(i).getText().trim().equals(leadName)) {
				test.log(LogStatus.INFO, "Deal Name found in row number " + (i + 1));
				return i + 1;
			}
		}

		test.log(LogStatus.INFO, "Lead not found");
		return -1;

	}

	public void selectDropDown(String value) {
		test.log(LogStatus.INFO, "Trying to select from DropDown--> " + value);

		List<WebElement> element = driver.findElements(By.xpath("//span[@class='select2-results']/ul/li"));

		System.out.println("DropDown------>" + element.size());

		for (WebElement ele : element) {
			String name = ele.getText();
			System.out.println(name);
			if (ele.getText().equals(value)) {
				ele.click();
				test.log(LogStatus.INFO, "Clicked successful on--->" + value);
				break;
			}
		}

		/*
		 * for(WebElement ele:element) { if(ele.getText().equals(value)) { ele.click();
		 * test.log(LogStatus.INFO, "Clicked successful on--->"+value); }
		 */

	}

}
