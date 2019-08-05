package extentReport.testcases;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import extentReport.util.Utility;

public class GoogleApp extends Utility {
	@Test
	public void enterText() {
		test = extent.createTest("Testing Google App").assignCategory("GoogleApp");
		app.findElement(By.xpath("//input[@name='q']")).sendKeys("Selenium");
		test.pass("Google Search Text is entered");
        AppScreenshots.passFailScreenshot("GoogleTesting");
      //input[@name='q']
	}
}
