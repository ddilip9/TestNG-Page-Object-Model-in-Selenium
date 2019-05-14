/**
 * 
 */
package com.ui.vanguard.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.basic.uiframework.BasePage;


public class VanguardHomePage extends BasePage {

	public VanguardHomePage(WebDriver driver) {
		super(driver);

	}

	@FindBy(xpath = "//*[@id='primaryContact_name']")
	public static WebElement contactNameTextField;

	
	
}