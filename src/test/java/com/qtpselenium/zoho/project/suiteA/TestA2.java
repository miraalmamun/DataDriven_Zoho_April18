package com.qtpselenium.zoho.project.suiteA;

import org.testng.annotations.Test;

import com.qtpselenium.zoho.project.base2.BaseTest2;

public class TestA2 extends BaseTest2
{
	@Test(priority = 1)
	public void testA2() throws InterruptedException {
		System.out.println("Starting A2");
		openBrowser("chrome");
		Thread.sleep(5000);
		System.out.println("Ending A2");
	}
	
	@Test(priority = 2)
	public void testAA2()
	{
		driver.get("http://yahoo.com");
	}
	
	
}
