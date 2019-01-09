package com.edr.models;

public class TestFlowModel {
	
	private int TestId;
	private String TestName;
	private int StepId;
	private String StepName;
	private String TestDataSheetName;
	private String BusinessComponentName;
	private String MethodName;
	private String DependsOn;
	
	public void setTestFlow(int testId,String testName,int stepId,String stepName,
			String testDataSheetName,String businessComponentName,
			String methodName,String dependsOn) {
		TestId = testId;
		TestName = testName;
		StepId = stepId;
		StepName = stepName;
		TestDataSheetName = testDataSheetName;
		BusinessComponentName = businessComponentName;
		MethodName = methodName;
		DependsOn = dependsOn;
	}
	
	public int getTestId() {
		return TestId;
	}
	public void setTestId(int testId) {
		TestId = testId;
	}
	public String getTestName() {
		return TestName;
	}
	public void setTestName(String testName) {
		TestName = testName;
	}
	public int getStepId() {
		return StepId;
	}
	public void setStepId(int stepId) {
		StepId = stepId;
	}
	public String getStepName() {
		return StepName;
	}
	public void setStepName(String stepName) {
		StepName = stepName;
	}
	public String getTestDataSheetName() {
		return TestDataSheetName;
	}
	public void setTestDataSheetName(String testDataSheetName) {
		TestDataSheetName = testDataSheetName;
	}
	public String getBusinessComponentName() {
		return BusinessComponentName;
	}
	public void setBusinessComponentName(String businessComponentName) {
		BusinessComponentName = businessComponentName;
	}
	public String getMethodName() {
		return MethodName;
	}
	public void setMethodName(String methodName) {
		MethodName = methodName;
	}
	public String getDependsOn() {
		return DependsOn;
	}
	public void setDependsOn(String dependsOn) {
		DependsOn = dependsOn;
	}	

}
