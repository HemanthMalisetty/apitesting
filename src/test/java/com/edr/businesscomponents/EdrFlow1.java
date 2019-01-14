package com.edr.businesscomponents;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.edr.models.TestDataOneModel;
import com.edr.reporting.ExtentManager;
import com.edr.testnglisteners.TestListener;
import com.edr.utils.Excel;

import io.restassured.RestAssured;

//@Listeners({TestListener.class})
public class EdrFlow1 extends BaseClass {

	public static boolean canContinue = true;
	public EdrFlow1 edrFlow1;
	
	@DataProvider(name = "dataProvider",parallel = true)
	public Object[][] provideTestParam(ITestContext context) throws Exception {
		Object[][] obj = Excel.getTableArray(context.getCurrentXmlTest().getParameter("sheetName"),
	    		Integer.parseInt(context.getCurrentXmlTest().getParameter("testId")),
	    		Integer.parseInt(context.getCurrentXmlTest().getParameter("stepId")));
		return obj;
	}
	
	@Test (groups={"API_Method"},dataProvider="dataProvider")
	public void API_Method(TestDataOneModel testDataOne) {
		/*
		 * Call all your APIs here and handle dependencies between APIs by setting canContinue by end of each API
		 * Create as many static variables as needed to share data between methods
		 */
		edrFlow1 = new EdrFlow1();
		API_ONE(testDataOne);
		API_TWO(testDataOne);
		API_THREE(testDataOne);
		API_FOUR(testDataOne);		
		Assert.assertTrue(canContinue);
	}
	
	public void API_ONE(TestDataOneModel testDataOne) {	
			canContinue = true;
			//Hit the API-1
			System.out.println("API_ONE->INSTANCE-ID:" + testDataOne.getInstanceId() + "->THREAD-ID:" + Thread.currentThread().getId());
			RestAssured.when().
			request(testDataOne.getMethod(),testDataOne.getUrlTemplate())
			.then()
			.statusCode(200)
			.log().all();
			//edrFlow1.getTest().get().log(Status.PASS, json);
			
			//canContinue = false; //set this if API-1 didn't return as expected
	}
	public void API_TWO(TestDataOneModel testDataOne) {
		if(canContinue) {
			//Hit the API-2
			if(testDataOne.getUrlParmOne().equals("100")) {
				edrFlow1.getTest().get().log(Status.PASS, testDataOne.getUrlParmOne());	
			}else {
				edrFlow1.getTest().get().log(Status.FAIL, testDataOne.getUrlParmOne());
				canContinue = false; //set this if API-2 didn't return as expected
			}			
		}
		else {
			edrFlow1.getTest().get().log(Status.SKIP, "SKIPPED SINCE API-1 FAILED");
		}
	}
	public void API_THREE(TestDataOneModel testDataOne) {
		if(canContinue) {
			//Hit the API-3
			edrFlow1.getTest().get().log(Status.PASS, "API_THREE");
			//canContinue = false; //set this if API-3 didn't return as expected
		}
		else {
			edrFlow1.getTest().get().log(Status.SKIP, "SKIPPED SINCE API-2 FAILED");
		}
	}
	public void API_FOUR(TestDataOneModel testDataOne) {
		if(canContinue) {
			//Hit the API-4
			edrFlow1.getTest().get().log(Status.PASS, "API_FOUR");
			//canContinue = false; //set this if API-4 didn't return as expected
		}
		else {
			edrFlow1.getTest().get().log(Status.SKIP, "SKIPPED SINCE API-3 FAILED");
		}
	}
}
