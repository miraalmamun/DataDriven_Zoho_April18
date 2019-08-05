package com.qtpselenium.zoho.project.suiteA;

import org.testng.annotations.Test;

import com.qtpselenium.zoho.project.base2.BaseTest2;

public class TestA1 extends BaseTest2
{
	@Test
	public void testA1() throws InterruptedException {
		
		System.out.println("Starting A1");
		openBrowser("mozilla");
		driver.get("http://yahoo.com");
		driver.getTitle();
		Thread.sleep(5000);
		System.out.println("Ending A1");
		
	}
}
