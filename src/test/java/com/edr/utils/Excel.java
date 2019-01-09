package com.edr.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.edr.models.RunManagerModel;
import com.edr.models.TestDataOneModel;
import com.edr.models.TestFlowModel;

public class Excel {
	private static String RUN_MANAGER_SHEET_NAME = "Run_Manager";
	private static String TEST_FLOW_SHEET_NAME = "Test_Flow";	
	private static XSSFWorkbook workbook;
	private static ArrayList<TestDataOneModel> testDataOneList;
	
	public static void loadExcelTestData() throws FileNotFoundException, IOException {
		workbook = new XSSFWorkbook(new FileInputStream(new File(Config.getProperty("RUN_MANAGER_PATH"))));
		testDataOneList = new ArrayList<TestDataOneModel>();
		loadTestDataOne();
	}
	
	public static void loadTestDataOne() {
		XSSFSheet sheet = workbook.getSheet("Test_Data1");
		int lastRow = sheet.getLastRowNum();
		for(int i=1;i<=lastRow;i++) {
			Row r = sheet.getRow(i);
			TestDataOneModel testDataOne = new TestDataOneModel();
			testDataOne.setTestId(Integer.parseInt(r.getCell(0,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()));
			testDataOne.setStepId(Integer.parseInt(r.getCell(1,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()));
			testDataOne.setInstanceId(Integer.parseInt(r.getCell(2,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()));
			testDataOne.setMethod(r.getCell(3,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
			testDataOne.setUrlTemplate(r.getCell(4,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
			testDataOne.setUrlParmOne(r.getCell(5,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
			testDataOne.setJsonTemplate(r.getCell(6,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
			testDataOne.setBodyParmOne(r.getCell(7,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
			testDataOne.setExpectedOutput(r.getCell(8,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
			testDataOneList.add(testDataOne);
		}
	}

	public static Object[][] getTableArray(String sheetName,int testId,int stepId) throws Exception {
		ArrayList<TestDataOneModel> matchingTestDataOneList = new ArrayList<TestDataOneModel>();		
		matchingTestDataOneList = testDataOneList.stream()
				.filter(x->x.getTestId()==testId)
				.filter(x->x.getStepId()==stepId)
				.collect(Collectors.toCollection(ArrayList::new));
		return matchingTestDataOneList.stream()
		        .map(x -> new Object[] { x })
		        .toArray(Object[][]::new);
	}
	
	public static ArrayList<RunManagerModel> loadRunManager(String path) {
		ArrayList<RunManagerModel> runManagerList = new ArrayList<RunManagerModel>();		
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(path)));
			XSSFSheet sheet = workbook.getSheet(RUN_MANAGER_SHEET_NAME);
			int lastRow = sheet.getLastRowNum();
			for(int i=1;i<=lastRow;i++) {
				RunManagerModel runManager = new RunManagerModel();
				XSSFRow r = sheet.getRow(i);
				runManager.setTestId(Integer.parseInt(r.getCell(0).toString()));
				runManager.setTestName(r.getCell(1).toString());
				runManager.setRunFlag(r.getCell(2).toString());		
				runManagerList.add(runManager);
			}
			workbook.close();
			return runManagerList;
		} catch (EncryptedDocumentException e) {			
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<TestFlowModel> loadTestFlow(String path) {
		ArrayList<TestFlowModel> testFlowList = new ArrayList<TestFlowModel>();		
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(path)));
			XSSFSheet sheet = workbook.getSheet(TEST_FLOW_SHEET_NAME);
			int lastRow = sheet.getLastRowNum();
			for(int i=1;i<=lastRow;i++) {
				TestFlowModel testFlow = new TestFlowModel();
				XSSFRow r = sheet.getRow(i);
				testFlow.setTestId(Integer.parseInt(r.getCell(0,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()));
				testFlow.setTestName(r.getCell(1,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
				testFlow.setStepId(Integer.parseInt(r.getCell(2,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()));
				testFlow.setStepName(r.getCell(3,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
				testFlow.setTestDataSheetName((r.getCell(4,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()));
				testFlow.setBusinessComponentName((r.getCell(5,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()));
				testFlow.setMethodName((r.getCell(6,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()));
				testFlow.setDependsOn((r.getCell(7,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()));
				testFlowList.add(testFlow);
			}
			workbook.close();
			return testFlowList;
		} catch (EncryptedDocumentException e) {			
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
