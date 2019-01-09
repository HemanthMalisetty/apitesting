package com.edr.businesscomponents;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.edr.testnglisteners.TestListener;
import com.edr.utils.Config;
import com.edr.utils.Excel;

public class BaseClass extends TestListener{
	
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
	

}
