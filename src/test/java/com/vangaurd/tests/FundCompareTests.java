package com.vangaurd.tests;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.basic.uiframework.HybridDriver;
import com.ui.vanguard.pageobjects.CompareProductsPage;
import com.ui.vanguard.pageobjects.VanguardHomePage;
import com.utilities.ExcelUtils;

public class FundCompareTests extends HybridDriver {
	
	
	String excelFilePath = "TestDataProvider/TestDataSheet_FundCompare.xlsx";
	String sheetName = "FundCompare";

	@DataProvider(name = "default")
	public Object[][] testData() throws IOException {
		ExcelUtils excelObj = new ExcelUtils();
		Object[][] arrayObject = excelObj.getExcelData(excelFilePath, sheetName);
		return arrayObject;
	}

	@BeforeMethod
	public void beforeMethod() {
		try {
			driver = setDriver();
		} catch (Exception e) {
			System.out.println("Error - - - - - - - - - - - - -> " + e.getMessage());
		}
	}

	/**
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	@Test(dataProvider = "default")
	public void verifyFundComparisonRetailProducts(String URL, String mainMenuLabel, String fundCompare, String fund1,
			String fund2, String fund3, String fund4, String entities, String productName1, String productName2,
			String productName3, String productName4) throws InterruptedException, IOException {

		// Initialize Pages
		new VanguardHomePage(driver);
		new CompareProductsPage(driver);

		launchURL(URL);
		waitForPageLoad();
		linkTextActionClick(mainMenuLabel);
		waitForPageLoad();
		linkTextActionClick(fundCompare);
		waitForPageLoad();

		switchToNextTab(driver);
		waitForPageLoad();
		
		clickWithJavaScript(CompareProductsPage.addFundButton);
 
		waitForPageLoad();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement radio = new WebDriverWait(driver, 10).until(
		ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='radio' and @value='RETAIL']")));
		js.executeScript("arguments[0].checked=true", radio);

		waitForPageLoad();
		CompareProductsPage.retailRadioBtn.isSelected();
				
/**
 * Dynamic xpath generation based on Fund name from data provider excel sheet
 */
		String fund_xpath1 = "//*[contains(text(),'" + fund1
				+ "')]/preceding::input[contains(@onclick, 'onSelectFund')][1]";
		String fund_xpath2 = "//*[contains(text(),'" + fund2
				+ "')]/preceding::input[contains(@onclick, 'onSelectFund')][1]";
		String fund_xpath3 = "//*[contains(text(),'" + fund3
				+ "')]/preceding::input[contains(@onclick, 'onSelectFund')][1]";
		String fund_xpath4 = "//*[contains(text(),'" + fund4
				+ "')]/preceding::input[contains(@onclick, 'onSelectFund')][1]";

		// clickElement(CompareProductsPage.addFund);
		waitForPageLoad();
		clickElementWithXpath(fund_xpath1);
		waitForPageLoad();
		clickElementWithXpath(fund_xpath2);
		waitForPageLoad();
		clickElementWithXpath(fund_xpath3);
		waitForPageLoad();
		clickElementWithXpath(fund_xpath4);

		waitForPageLoad();

		clickWithJavaScript(CompareProductsPage.compareFundsButton);
		
		/**
		 * Get values from Comparison table toList
		 * entities list will contain all the attributes of table
		 * Product 1 will contain the values related to attribute with respect to product 1
		 * Product 2 will contain the values related to attribute with respect to product 2
		 * Product 3 will contain the values related to attribute with respect to product 3
		 * Product 4 will contain the values related to attribute with respect to product 4
		 */
		
		ArrayList<String> entities1 = new ArrayList<String>();
		ArrayList<String> product1 = new ArrayList<String>();
		ArrayList<String> product2 = new ArrayList<String>();
		ArrayList<String> product3 = new ArrayList<String>();
		ArrayList<String> product4 = new ArrayList<String>();

		String xpath = "//strong[@id='fundManagerDataPnt']/following::tr";
		System.out.println("-------------------------------"+driver.findElements(By.xpath(xpath)).size());
		
		for (int count = 1; count < 18; count++) {
			entities1.add(driver
					.findElement(By.xpath("//strong[@id='fundManagerDataPnt']/following::tr[" + count + "]/td[1]"))
					.getText());
			product1.add(driver
					.findElement(By.xpath("//strong[@id='fundManagerDataPnt']/following::tr[" + count + "]/td[2]"))
					.getText());
			product2.add(driver
					.findElement(By.xpath("//strong[@id='fundManagerDataPnt']/following::tr[" + count + "]/td[3]"))
					.getText());
			product3.add(driver
					.findElement(By.xpath("//strong[@id='fundManagerDataPnt']/following::tr[" + count + "]/td[4]"))
					.getText());
			product4.add(driver
					.findElement(By.xpath("//strong[@id='fundManagerDataPnt']/following::tr[" + count + "]/td[5]"))
					.getText());

		}

	 
		/**
		 * Assert with list of values from Product List and  data provider values from Excel sheet
		 */
		boolean entity = entities1.toString().contains(entities);
		boolean productResult1 = product1.toString().contains(productName1);
		boolean productResult2 = product2.toString().contains(productName2);
		boolean productResult3 = product3.toString().contains(productName3);
		boolean productResult4 = product4.toString().contains(productName4);

		
		infoToReportWithBoolean(entity,"Entity comparison result is :"+entity +"\n : Actual Entities ->"+ entities1.toString()+": Comparison Entities -> "+entities);
		infoToReportWithBoolean(productResult1,"productResult1  is : "+productResult1 +"\n : Actual Product 1 ->"+ product1.toString()+": Comparison Product 1  -> "+productName1);
		infoToReportWithBoolean(productResult2,"productResult2  is : "+productResult2 +"\n : Actual Product 2->"+ product2.toString()+": Comparison Product 2 -> "+productName2);
		infoToReportWithBoolean(productResult3,"productResult3 is : "+productResult3 +"\n : Actual Product3 ->"+ product3.toString()+": Comparison Product3 -> "+productName3);
		infoToReportWithBoolean(productResult4,"productResult4 is : "+productResult4 +"\n : Actual product 4 ->"+ product4.toString()+": Comparison product 4 -> "+productName4);
 

	}

	@AfterMethod
	public void afterMethod() {
		 closeWindow();
	}

}
