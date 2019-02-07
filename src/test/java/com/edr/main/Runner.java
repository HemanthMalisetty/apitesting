package com.edr.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.edr.models.RunManagerModel;
import com.edr.models.TestFlowModel;
import com.edr.models.TestSuiteModel;
import com.edr.models.TestSuiteModel.Suite;
import com.edr.utils.Config;
import com.edr.utils.Excel;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class Runner {
	
	public ArrayList<RunManagerModel> runManagerList;
	public ArrayList<TestFlowModel> testFlowList;
	public static Runner runner;
	final int[] lambdaIndex = new int[1];  
	public Suite suite;
	
	public static void main(String[] args) {
		runner=new Runner();
		initializer();
		
		if(runner.validateRunManager()) {
			runner.buildFlow();
			runner.generateTestNGXml();
		}		
	}
	
	public static void initializer() {
		Config.load();
		runner.loadRunDetails();		
	}
	
	private void buildFlow() {		
		TestSuiteModel suiteModel = new TestSuiteModel();
		suite = suiteModel.new Suite("RegressionPack");
		suite.addListeners(Arrays.asList("com.edr.testnglisteners.TestListener"));
		for(int i=0;i<runManagerList.size();i++) {
			if(runManagerList.get(i).getRunFlag().toUpperCase().equals("Y")) {							
				lambdaIndex[0]=i;
				ArrayList<TestFlowModel> tempTestFlowList = new ArrayList<TestFlowModel>(); 
				tempTestFlowList = testFlowList.stream()
						.filter(x->x.getTestId()==runManagerList.get(lambdaIndex[0]).getTestId())
						.filter(x->x.getTestName().equals(runManagerList.get(lambdaIndex[0]).getTestName()))
						.collect(Collectors.toCollection(ArrayList::new));
				List<String> uniqueClasses= new ArrayList<String>();
				uniqueClasses = tempTestFlowList.stream()
						.map(TestFlowModel::getBusinessComponentName)
						.collect(Collectors.toList());
				uniqueClasses = uniqueClasses.stream()
						.distinct()
						.collect(Collectors.toList());

				List<String> stepList= new ArrayList<String>();
				stepList = tempTestFlowList.stream()
						.map(TestFlowModel::getStepId)
						.map(Object::toString)
						.collect(Collectors.toList());
				List<String> sheetList= new ArrayList<String>();
				sheetList = tempTestFlowList.stream()
						.map(TestFlowModel::getTestDataSheetName)
						.collect(Collectors.toList());
				
				List<String> MethodList = new ArrayList<String>();
				
				for(int j=0;j<uniqueClasses.size();j++) {	
					List<String> classLevelMethodList = new ArrayList<String>();
					for(int k=0;k<tempTestFlowList.size();k++)
					{
						if(uniqueClasses.get(j).equals(tempTestFlowList.get(k).getBusinessComponentName())) {
							classLevelMethodList.add(tempTestFlowList.get(k).getMethodName());
						}
					}
					MethodList.add(classLevelMethodList.stream()
							.map(c->c.toString())
							.collect(Collectors.joining("#")));
				}
				
				 String className = uniqueClasses.stream()
					     .map(c -> Config.getProperty("BUSINESS_COMPONENTS_CLASS_PREFIX") + "." + c.toString())
					     .collect(Collectors.joining(","));
				 String methodName = MethodList.stream()
					     .map(c -> c.toString())
					     .collect(Collectors.joining(","));
				 String groupName = tempTestFlowList.stream()
						 .anyMatch(x->x.getDependsOn().length()>0)?tempTestFlowList.stream()
								 .filter(x->x.getDependsOn().length()>0)
								 .map(y->y.getMethodName())
								 .collect(Collectors.joining(",")):"";
								 
				 String dependsOn = tempTestFlowList.stream()
						 .anyMatch(x->x.getDependsOn().length()>0)?tempTestFlowList.stream()
								 .filter(x->x.getDependsOn().length()>0)
								 .map(y->y.getDependsOn())
								 .collect(Collectors.joining(",")):"";
				suite.addTest(runManagerList.get(i).getTestName(),
						"testId,stepId,sheetName",
						//To be updated later to handle parameters at step level
						//Current logic is specific to RBS - considering there will be only one step per test case
						tempTestFlowList.get(0).getTestId() + "," +
								tempTestFlowList.get(0).getStepId() + "," +
								tempTestFlowList.get(0).getTestDataSheetName(),
						className,
						methodName,
						groupName,
						dependsOn);								
			}
		}
		
	}

	
	
	public void loadRunDetails() {
		runManagerList = new ArrayList<RunManagerModel>();
		runManagerList = Excel.loadRunManager(Config.getProperty("RUN_MANAGER_PATH"));
		testFlowList = new ArrayList<TestFlowModel>();		
		testFlowList = Excel.loadTestFlow(Config.getProperty("RUN_MANAGER_PATH"));		
	}
	private boolean validateRunManager() {
		// To be developed - Low Priority
		/* 
		 * 1. Validate If all run manager line items have at least one test flow entry
		 * 2. Validate if all test flow entries have corresponding test data entries and that 
		 *    the template place-holders have data
		 * 3. Validate if all classes and methods mentioned in the testFlow sheet are available 
		 *    in the business components.
		 */
		System.out.println("Run Manager Validation Started.");
		System.out.println("Run Manager Validation Complete.");
		return true;	
	}
	
	private void generateTestNGXml() {		
		/*
		 * This method is to generate an artifact XML that can be saved and re-used to run a similar 
		 * suite any time in command line
		 * 
		 * This artifact can be be used for DevOps later on
		 */
		try {
			XmlMapper xmlMapper = new XmlMapper();
			xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
			xmlMapper.writeValue(new File("src/test/resources/testng-suite.xml"), suite);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
