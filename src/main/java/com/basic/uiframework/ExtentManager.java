package com.basic.uiframework;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
	public static ExtentReports instance;
	
	public static synchronized ExtentReports getInstance() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
		 Date date = new Date();
		if (instance == null) {
			System.out.println(System.getProperty("user.dir"));
			instance = new ExtentReports(System.getProperty("user.dir") + "/HTMLReports/"+dateFormat.format(date)+"SprintestReport.html");
		}
		return instance;
	}
}
