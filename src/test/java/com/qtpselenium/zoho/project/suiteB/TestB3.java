package com.qtpselenium.zoho.project.suiteB;

import org.testng.annotations.Test;

import com.qtpselenium.zoho.project.base2.BaseTest2;

public class TestB3 extends BaseTest2 
{
	@Test
	public void testB3() throws InterruptedException {
		System.out.println("Starting B3");
		openBrowser("chrome");
		Thread.sleep(5000);
		System.out.println("Ending B3");
	}
}
