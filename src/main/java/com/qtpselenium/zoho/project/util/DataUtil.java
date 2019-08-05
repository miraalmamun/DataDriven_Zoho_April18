package com.qtpselenium.zoho.project.util;

import java.util.Hashtable;

public class DataUtil {

	// In this class I will create a method
	// which will return two dimensional Object
	// Array. This method two parameter as excel object
	// and testCase name.
	public static Object[][] getTestData(ExcelReadWrite xls, String testCaseName) {

		String sheetName = "Data";

		int testStartRowNum = 1;

		while (!xls.getCellData(sheetName, 0, testStartRowNum).equals(testCaseName)) {

			testStartRowNum++;
		}

		int colStartRowNum = testStartRowNum + 1;
		int dataStartRowNum = testStartRowNum + 2;

		int rows = 0;
		while (!xls.getCellData(sheetName, 0, dataStartRowNum + rows).equals("")) {

			rows++;
		}

		int col = 0;
		while (!xls.getCellData(sheetName, col, colStartRowNum).equals("")) {
			col++;
		}

		Object data[][] = new Object[rows][1];
		Hashtable<String, String> table = null;
		for (int i = 0; i < rows; i++) {
			table = new Hashtable<String, String>();

			for (int j = 0; j < col; j++) {
				String key = xls.getCellData(sheetName, j, colStartRowNum);
				String value = xls.getCellData(sheetName, j, dataStartRowNum + i);
				table.put(key, value);
			}

			data[i][0] = table;

		}

		return data;

	}
	
	/*This is excel file and sheet name is 'TestCases'
	 *  
	 *   
	 *   TCID	 Runmodes
        TestA	    Y
        TestB	    N
        TestC	    Y

	 * 
	 * */

	public static boolean isRunnable(String testName, ExcelReadWrite xls) {
		//'String testName' is the given test name by programmer.
		//This test name will provide by the programmer.
		//And this method 'isRunnable' going to check that is it match with the
		//Excel sheet. If excel sheet test name equal to provided test name
		//same then this method going to check 'Runmode'--> If Runmode
		//'Y' then this  method will return 'true'
		String sheet = "TestCases";
		int rows = xls.getRowCount(sheet);
		for (int r = 2; r <= rows; r++) {
			String tName = xls.getCellData(sheet, "TCID", r);
			if (tName.equals(testName)) {
				String runmode = xls.getCellData(sheet, "Runmode", r);
				if (runmode.equals("Y"))
					return true;
				else
					return false;

			}
		}
		return false;
	}
}
