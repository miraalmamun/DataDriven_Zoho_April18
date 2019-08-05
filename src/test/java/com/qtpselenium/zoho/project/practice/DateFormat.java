package com.qtpselenium.zoho.project.practice;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat 
{

	public static void main(String[] args) throws Throwable 
	{   
		Date d = new Date();
		System.out.println(d);
		System.out.println(new Date());
		
		String date = "12/6/2018";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date2 = simpleDateFormat.parse(date);
		System.out.println(date2);
		simpleDateFormat = new SimpleDateFormat("d");
		String daytoBeSelected = simpleDateFormat.format(date2);
		System.out.println(daytoBeSelected);
		simpleDateFormat = new SimpleDateFormat("MMMM");
		String monthToBeSelected = simpleDateFormat.format(date2);
		System.out.println(monthToBeSelected);
		simpleDateFormat = new SimpleDateFormat("yyyy");
		String yearToBeSelected = simpleDateFormat.format(date2);
		System.out.println(yearToBeSelected);
		String monthyearToBeSelected = monthToBeSelected+" "+yearToBeSelected;
		System.out.println(monthyearToBeSelected);
		
		
		
		
		
		
		

	}

}
