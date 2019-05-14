/**
 * 
 */
package com.ui.vanguard.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.basic.uiframework.BasePage;

public class CompareProductsPage extends BasePage {

	public CompareProductsPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "compareFundBtn")
	public static WebElement compareFundsButton;
	
	@FindBy(xpath = "//input[@type='radio' and @value='RETAIL']")
	public static WebElement retailRadioBtn;
	
	@FindBy(id="addButton0")
	public static WebElement addFundButton;
	
	 

	 
	

}