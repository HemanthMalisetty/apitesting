package com.edr.reporting;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.edr.utils.Config;

public class ExtentManager {
	private static ExtentReports extent;
 
    public synchronized static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }
 
    //Create an extent report instance
    public synchronized static ExtentReports createInstance() {
    	Config.load();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(Config.getProperty("REPORT_PATH"));
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(Config.getProperty("REPORT_FILENAME"));
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setDocumentTitle("EDR Regression Report");
 
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
 
        return extent;
    }

}
