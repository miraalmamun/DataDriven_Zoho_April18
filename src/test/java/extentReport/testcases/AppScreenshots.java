package extentReport.testcases;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import extentReport.util.Utility;



public class AppScreenshots extends Utility {
	public static String getScreenshotPath(String screenShotName) {
		File sourceFile = ((TakesScreenshot) app).getScreenshotAs(OutputType.FILE);
		String imgPath = "./reports/Screenshots/" + screenShotName + ".png";
		File path = new File(imgPath);
		try {
			FileUtils.copyFile(sourceFile, path);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return imgPath;
	}
	
	
	public static void passFailScreenshot(String name)
	{
		String screenshotname ="."+ AppScreenshots.getScreenshotPath(name);
		screenCapture("testing", screenshotname);
	}
	
	
	
	
	
	
	
}
