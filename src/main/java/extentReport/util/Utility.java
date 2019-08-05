package extentReport.util;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Utility {
	public static WebDriver app;
	public String url = "https://www.google.com/";
	public static ExtentReports extent;
	public static ExtentHtmlReporter reporter;
	public static ExtentTest test;
	public static ExtentTest parentTest;

	String concatenate = ".";

	@BeforeTest
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/Driver/chromedriver.exe");
		app = new ChromeDriver();
		app.manage().window().maximize();
		app.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		app.get(url);

		// Add reporter to extent report

		reporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "./reports/GoogleApp_TestExecution.html");
		extent = new ExtentReports();
		extent.attachReporter(reporter);

		reporter.config().setDocumentTitle("Google APP Automation Report");
		reporter.config().setReportName("Google APP Automation Execution");
		reporter.config().setTestViewChartLocation(ChartLocation.TOP);
		reporter.config().setTheme(Theme.STANDARD);

		// Add system info in extent
		extent.setSystemInfo("Environment", "Automation Testing");
		extent.setSystemInfo("HostAddress", "Google");
		extent.setSystemInfo("Browser", "Google Chrome");
		extent.setSystemInfo("Application_URL", "http://www.google.com");

	}

	@AfterMethod
	public void afterMethods() {
		
		if(app!=null)
		{
			app.quit();
		}
		extent.flush();
		
	}

	
	
	
	public static Object screenCapture(String logdetails, String imagpath) {
		try {
			test.log(Status.INFO, logdetails, MediaEntityBuilder.createScreenCaptureFromPath(imagpath).build());
		} catch (IOException e) {

			e.printStackTrace();
		}
		return test;
	}

}
