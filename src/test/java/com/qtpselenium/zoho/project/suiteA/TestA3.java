package com.qtpselenium.zoho.project.suiteA;

import org.testng.annotations.Test;

import com.qtpselenium.zoho.project.base2.BaseTest2;

public class TestA3 extends BaseTest2 
{
	@Test
	public void testA3() throws InterruptedException {
		System.out.println("Starting A3");
		openBrowser("mozilla");
		Thread.sleep(5000);
		System.out.println("Ending A3");
	}
}
