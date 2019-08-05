package com.qtpselenium.zoho.project.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {
	
	

	static WebDriver driver;
	static WebElement element;
	public static void main(String[] args) {
		
		
		
       drawBorder(element, driver);
		//Drawing border and screenshot
		
		
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File trg = new File(System.getProperty("user.dir")+"\\screenshot\\twolugs.png");
        try 
        {
			FileUtils.copyFile(src, trg);
		} 
        catch (IOException e) 
        {
			
			e.printStackTrace();
		}
	}
	
	//https://www.twolugs.com/
	public static void scrollIntoView(WebElement element, WebDriver driver)
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	//Vertical scroll down by 600  pixels		
    //js.executeScript("window.scrollBy(0,600)");
	
	
	public static void verticalScrollDown(WebDriver driver)
	{   
		//Launching the Site.		
        driver.get("http://moneyboats.com/");			
     
        //Maximize window		
        driver.manage().window().maximize();
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0,600)");
	}
	public static void scrollPageDown(WebDriver driver)
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}
/*********************************Flushing by changing Color*********************************************/	
	public static void flash(WebElement element, WebDriver driver)
	{
		
		//The method 'getCssValue("backgroundColor");' capture the color
		String bgcolor = element.getCssValue("backgroundColor");
		
		for(int i = 0; i < 50; i++)//Changing the color 50 times
		{
			changeColor("#000000", element, driver);//1-->Black-->#000000
			changeColor(bgcolor, element, driver);//2-->Going back bgcolor(backgroundColor
		}
	}
	
	public static void changeColor(String color,WebElement element,WebDriver driver)
	
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);
		try
		{
			Thread.sleep(2000);
		} catch (InterruptedException e) 
		{
			
			e.printStackTrace();
		}
	}
/******************************End Changing color*************************************************/	
	
	public static void drawBorder(WebElement element, WebDriver driver)
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}
	
	//Capture title of the page
	//driver.getTitle();
	public static String getTitle(WebDriver driver)
	{   // //Creating the JavascriptExecutor interface object by Type casting
		JavascriptExecutor js = (JavascriptExecutor)driver;
		String title = js.executeScript("return document.title;").toString();
		return title;
	}
	
	public static void clickElementByJS(WebElement element,WebDriver driver)
	{   // //Creating the JavascriptExecutor interface object by Type casting
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", element);
		
	}
	
	
	public static void generateAlert(WebDriver driver,String message)
	{   // //Creating the JavascriptExecutor interface object by Type casting
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("alert('" + message +"')");
		
	}
	
	//refreshing page
	//driver.navigate().refresh();
	
	public static void refreshBrowserByJS(WebDriver driver)
	{    //Creating the JavascriptExecutor interface object by Type casting
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("history.go(0)");
		
	}
	
//==================================================================================
	
//JavaScriptExecutor is an Interface that helps to execute JavaScript 
//through Selenium Webdriver. JavaScriptExecutor provides two methods 
//"executescript" & "executeAsyncScript" to run javascript on the selected 
//window or current page.
	
/*
Summary:

JavaScriptExecutor is used when Selenium Webdriver fails to click on any 
element due to some issue.

1.JavaScriptExecutor provides two methods "executescript" & "executeAsyncScript"	
to handle.
2.Executed the JavaScript using Selenium Webdriver.
3.Illustrated how to click on an element through JavaScriptExecutor, 
if selenium fails to click on element due to some issue.
4.Generated the 'Alert' window using JavaScriptExecutor.
5.Navigated to the different page using JavaScriptExecutor.
6.Scrolled down the window using JavaScriptExecutor.
7.Fetched URL, title, and domain name using JavaScriptExecutor.
	
	
*/
	
	


}
