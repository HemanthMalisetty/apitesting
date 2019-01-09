package com.edr.models;

import com.edr.utils.Config;

public class TestDataOneModel {
	private int testId;
	private int stepId;
	private int instanceId;
	private String method;
	private String urlTemplate;
	private String urlParmOne;
	private String jsonTemplate;
	private String bodyParmOne;
	private String expectedOutput;
	
	//Yet to Handle -> all non-Mandatory fields
	public int getTestId() {
		return testId;
	}
	public void setTestId(int testId) {
		this.testId = testId;
	}
	public int getStepId() {
		return stepId;
	}
	public void setStepId(int stepId) {
		this.stepId = stepId;
	}
	public int getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(int instanceId) {
		this.instanceId = instanceId;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getUrlTemplate() {
		//<<BASE_URL>>/{{url_parm_1}}
		return urlTemplate.replace("<<BASE_URL>>", Config.getProperty("DEV_EDR_URL")).replace("{{url_parm_1}}", urlParmOne);
	}
	public void setUrlTemplate(String urlTemplate) {
		this.urlTemplate = urlTemplate;
	}
	public String getUrlParmOne() {
		return urlParmOne;
	}
	public void setUrlParmOne(String urlParmOne) {
		this.urlParmOne = urlParmOne;
	}
	public String getJsonTemplate() {
		return jsonTemplate.replace("{{body_parm_1}}",bodyParmOne);
	}
	public void setJsonTemplate(String jsonTemplate) {
		this.jsonTemplate = jsonTemplate;
	}
	public String getBodyParmOne() {
		return bodyParmOne;
	}
	public void setBodyParmOne(String bodyParmOne) {
		this.bodyParmOne = bodyParmOne;
	}
	public String getExpectedOutput() {
		return expectedOutput;
	}
	public void setExpectedOutput(String expectedOutput) {
		this.expectedOutput = expectedOutput;
	}
}
