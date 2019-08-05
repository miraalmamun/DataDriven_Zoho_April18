package com.qtpselenium.zoho.project.practice;

import java.io.FileInputStream;
import java.util.Properties;

public class ProprtiesExam {

	static public Properties pro;
	static public Properties envPro;

	public static void main(String[] args) {

		init();
	}

	public static void init() {
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
                System.out.println(envPro.get("userName"));
				
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

	}

}
