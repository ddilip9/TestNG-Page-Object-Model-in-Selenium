package com.basic.uiframework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class BasePage extends HybridDriver {
	/** The Driver. */
	protected WebDriver driver = null;
	public int TimeoutValue = 50;
	
	/**
	 * Waiting for AjaxElement Locator to inspect and validate the web element
	 * @param driver
	 */
	public BasePage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, TimeoutValue), this);
	}
	
	 
}
