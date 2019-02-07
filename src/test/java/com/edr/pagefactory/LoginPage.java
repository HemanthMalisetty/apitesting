package com.edr.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	WebDriver driver;
	
	@FindBy(id="username")
	WebElement userNameTextBox;
	
	@FindBy(id="password")
	WebElement passWordTextBox;
	
	@FindBy(id="submit")
	WebElement loginButton;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void setUserName(String userName) {
		userNameTextBox.sendKeys(userName);
	}
	public void setPassword(String password) {
		passWordTextBox.sendKeys(password);
	}
	public void clickLogin() {
		loginButton.click();
	}

}
