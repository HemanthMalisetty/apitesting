package com.edr.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.edr.utils.Config;

public class HelperPage {
	
	public static final int appTimeoutInSecs = Integer.parseInt(Config.getProperty("APP_TIMEOUT_IN_SECS"));	
	
	protected WebElement getVisibleElement(WebDriver driver, WebElement element) throws Exception{
		return getVisibleElement(driver,element,appTimeoutInSecs);
	}	

	protected WebElement getVisibleElement(WebDriver driver, WebElement element, Integer timeoutInSecs) throws Exception{
		WebDriverWait wait = new WebDriverWait (driver, timeoutInSecs);
		wait.until(		
			ExpectedConditions.or(ExpectedConditions.visibilityOf(element))
			);
		if (element.isDisplayed ()){
			return element;
		}else {
			throw new Exception("Element Not found");
		}		
	}	
}
