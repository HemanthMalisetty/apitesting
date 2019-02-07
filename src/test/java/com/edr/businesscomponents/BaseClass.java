package com.edr.businesscomponents;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.edr.testnglisteners.TestListener;
import com.edr.utils.Config;
import com.edr.utils.Excel;
import com.edr.utils.OptionsManager;

public class BaseClass extends TestListener{
	private static OptionsManager optionsManager = new OptionsManager();
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
	@BeforeSuite
	public void init() {
		Config.load();
		try {
			Excel.loadExcelTestData();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@BeforeMethod
	public void beforeMethod() {
		System.out.println("BEFORE:" + Thread.currentThread().getId());
	}
	
	@BeforeTest
	public void setUpTest() throws MalformedURLException {
		System.out.println("Setting up Test..");
		System.out.println("Setting Capabilities..");
		driver.set(new RemoteWebDriver(new URL(Config.getProperty("HUB_URL")),optionsManager.getBrowserOptions(Config.getProperty("BROWSER"))));
	}
	
	public static synchronized WebDriverWait getWait (WebDriver driver) {
        return new WebDriverWait(driver,20);
    }
 
    public static synchronized WebDriver getDriver() {
        return driver.get();
    }
    @AfterTest
    public void tearDown() {
    	getDriver().quit();
    }
    @AfterClass
    public void terminate() {
    	driver.remove();
    }

}
