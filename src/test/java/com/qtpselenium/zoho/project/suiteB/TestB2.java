package com.qtpselenium.zoho.project.suiteB;

import org.testng.annotations.Test;

import com.qtpselenium.zoho.project.base2.BaseTest2;

public class TestB2 extends BaseTest2
{
	@Test
	public void testB2() throws InterruptedException {
		System.out.println("Starting B2");
		openBrowser("mozilla");
		Thread.sleep(5000);
		System.out.println("Ending B2");
	}
}
