package com.basic.uiframework;

import static org.testng.Assert.assertEquals;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.LogStatus;

public class HybridDriver {
	
	public static String getPageTitle(WebDriver driver) {
		String pageTitle = "";
		try {
			pageTitle = driver.getTitle();
			ExtentTestManager.getTest().log(LogStatus.PASS, "Successfully Obtained Page Title - " + pageTitle);
			ExtentTestManager.getTest().log(LogStatus.INFO,
					ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "getPageTitle")));
			logger.info("Successfully Obtained Page Title - " + pageTitle);
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Failed to Extract Page Title  -  " + e.getMessage());
			ExtentTestManager.getTest().log(LogStatus.INFO,
					ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "clickFailed")));
			logger.error("Failed to Obtained Page Title - " + pageTitle);
		}
		return pageTitle;
	}

	public boolean isVisible(WebElement ele) {
		try {
			if (ele.isDisplayed()) {
				ExtentTestManager.getTest().log(LogStatus.PASS, "Element Visible is Successful : " + elementName(ele));
				ExtentTestManager.getTest().log(LogStatus.INFO,
						ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "isVisible")));
				logger.info("Element Visible is Successful : " + elementName(ele));
				return true;
			} else {
				ExtentTestManager.getTest().log(LogStatus.FAIL,
						"Unable to Find the element : isVisible : " + elementName(ele));
				ExtentTestManager.getTest().log(LogStatus.INFO,
						ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "isVisibleFailed")));
				logger.error("Unable to Find the element : isVisible : " + elementName(ele));
				return false;
			}

		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,
					"Unable to Find the element : isVisible : " + e.getMessage());
			logger.error("Unable to Find the element : isVisible : " + elementName(ele));
		}
		return false;
	}

	public void switchWindow() {
		String parentWindow = driver.getWindowHandle();
		Set<String> windowHandles = driver.getWindowHandles();
		try {
			for (String windowHandle : windowHandles) {
				if (!windowHandle.equals(parentWindow)) {
					driver.switchTo().window(windowHandle);
				}
			}
			ExtentTestManager.getTest().log(LogStatus.PASS,
					"Switch to  window is successful : " + windowHandles.toString());
			logger.info("Switch to  window is successful : " + windowHandles.toString());
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Failed to Switch to Window  : " + e.getMessage());
			logger.error("Failed to Switch to Window  : " + e.getMessage());
		}
	}

	public String getParentWindow() {
		String parentWin = "";
		try {
			parentWin = driver.getWindowHandle();
			ExtentTestManager.getTest().log(LogStatus.PASS, "Switch to Parent window is successful : " + parentWin);
			logger.info("Switch to Parent window is successful : " + parentWin);
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Failed to Switch to Parent Window : " + e.getMessage());
			logger.error("Failed to Switch to Parent Window : " + e.getMessage());
		}
		return parentWin;
	}

	public static void closeWindow() {

		try {
			driver.close();
			ExtentTestManager.getTest().log(LogStatus.PASS, "Closed Webdriver instance Successfully");
			logger.info("Closed Webdriver instance Successfully");
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Failed to Close WebDriver Instance  " + e.getMessage());
			logger.error("Failed to Close WebDriver Instance  " + e.getMessage());
		}

		try {
			driver.quit();
			;
			ExtentTestManager.getTest().log(LogStatus.PASS, "Quitting Webdriver instance Successfully");
			logger.info("Quitting Webdriver instance Successfully");
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Failed to Close Webdriver Instance " + e.getMessage());
			logger.error("Failed to Close Webdriver Instance " + e.getMessage());
		}
	}

	public void clickElement(WebElement element) {
		try {
			element.click();
			ExtentTestManager.getTest().log(LogStatus.PASS,"Click on Element : " + elementName(element).toString() + " -  Successfull");
			ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "clickSuccess")));
			logger.info("Click on Element  -  Successfull");
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,"Click on Element : " + elementName(element) + " -  Failed : " + e.getMessage());
			ExtentTestManager.getTest().log(LogStatus.INFO,ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "clickFailed")));
			logger.error("Click on Element  -  Failed : " + e.getStackTrace());
		}
	}
	
	
	public void clickElementWithXpath(String element) {
		try {
			driver.findElement(By.xpath(element)).click();
			ExtentTestManager.getTest().log(LogStatus.PASS,"Click on Element : " +element + " -  Successfull");
			ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "clickSuccess")));
			logger.info("Click on Element  -  Successfull");
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,"Click on Element : " + element + " -  Failed : " + e.getMessage());
			ExtentTestManager.getTest().log(LogStatus.INFO,ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "clickFailed")));
			logger.error("Click on Element  -  Failed : " + e.getStackTrace());
		}
	}
	public void clickWithImage(String imagePath) {
		//Screen screen = new Screen();
		try {
			//screen.click(imagePath);
			ExtentTestManager.getTest().log(LogStatus.PASS,"Click on Image : "+imagePath+" -  Successfull");
			ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "clickSuccess")));
			logger.info("Click on Element  -  Successfull");
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,"Click on Element  : "+imagePath+"  -  Failed : " + e.getMessage());
			ExtentTestManager.getTest().log(LogStatus.INFO,ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "clickFailed")));
			logger.error("Click on Element  -  Failed : " + e.getStackTrace());
		}
	}
	
	
	
	public void clickWithJavaScript(WebElement id) {
		//Screen screen = new Screen();
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
//			WebElement element = driver.findElement(By.xpa(id));
			js.executeScript("arguments[0].click();", id);
			
			ExtentTestManager.getTest().log(LogStatus.PASS,"Click success with Java Script by ID: "+id+" -  Successfull");
			ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "clickSuccessJavaScriptID")));
			logger.info("Click on Element  -  Successfull");
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,"Click Failed with Java Script by ID: : "+id+"  -  Failed : " + e.getMessage());
			ExtentTestManager.getTest().log(LogStatus.INFO,ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "clickFailedJavaScriptID")));
			logger.error("Click on Element  -  Failed : " + e.getStackTrace());
		}
	}
	
	
	public void waitForPageLoad() {
		try {
			driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
			Thread.sleep(1000);
			ExtentTestManager.getTest().log(LogStatus.PASS,"Wait for the Page (" + driver.getTitle() + ") to Load  -  Successfull");
			logger.info("Wait for the Page ( " + driver.getTitle() + ") to Load  -  Successfull");
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Wait for the Page (" + driver.getTitle() + ") to Load  -  Failed");
			logger.error("Wait for the Page ( " + driver.getTitle() + ") to Load  -  Failed");
		}
	}


	private String elementName(WebElement element) {
		String result = " - - ";
		try {
			String[] a = element.toString().split("->");
			result = a[1].replace("]]", "]");
		} catch (Exception e) {
			logger.error("Error while extracting element name -> " + e.getMessage());
		}
		return result;
	}

	public void explicitWaitForDisplayed(WebElement ele, int time) throws InterruptedException {
		int i = 1;
		try {
			while (i <= time) {
				if (ele.isDisplayed()) {
					break;
				}
				Thread.sleep(1000);
				i = i + 1;
			}
			ExtentTestManager.getTest().log(LogStatus.PASS, "Explicit Wait for Displayed Element : " + elementName(ele) + " -  Success ");
			ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "clickSuccess")));
			logger.info("Explicit Wait for Displayed Element : " + elementName(ele) + " -  Success ");
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Click on Element - Failed : " + e.getMessage());
			ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "clickFailed")));
			logger.error("Click on Element - Failed : " + e.getMessage());
		}
	}

	

	public String linkTextActionClick(String text) {
		try {
			driver.findElement(By.linkText(text)).click();
			ExtentTestManager.getTest().log(LogStatus.PASS, "Click using Link Text  " + text +": is successful");
			ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "linkTextClicksuccess")));
			logger.info("Element Get Text : " + text + " : "+ ": Successful");
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Click with Linkable text is failed : " + text + "  : " + e.getMessage());
			ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "linkTextFailed")));
			logger.error("Clickable on Link Text : " + text + "  : " + e.getMessage() + " : Stack Trace : " + e.getStackTrace());
		}

		return text;
	}
	
	
	public String getText(WebElement element) {
		String text = "";
		try {
			text = element.getText();
			ExtentTestManager.getTest().log(LogStatus.PASS, "Element Get Text : " + text + " : " + elementName(element));
			ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "getTextSuccess")));
			logger.info("Element Get Text : " + text + " : " + elementName(element));
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Element Get Text Failed : " + text + "  : " + e.getMessage());
			ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "getTextFailed")));
			logger.error("Element Get Text Failed : " + text + "  : " + e.getMessage() + " : Stack Trace : " + e.getStackTrace());
		}

		return text;
	}

	public void launchURL(String URL) {
		try {
			driver.get(URL);
			ExtentTestManager.getTest().log(LogStatus.PASS, "URL Launch Successfull: " + URL);
			ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "urlLaunchSuccess")));
			logger.info("URL Launch Successfull: " + URL);
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Failed to Launch : " + URL + "  : " + e.getMessage());
			ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "urlLaunchFailed")));
			logger.error("Failed to Launch : " + URL + "  : " + e.getMessage() + " : Stack Trace : " + e.getStackTrace());
		}
	}

	public static void switchToNextTab(WebDriver driver) {
		try {
			ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs2.get(1));
			ExtentTestManager.getTest().log(LogStatus.PASS,
					"Switch to Next Tab Success: " + driver.switchTo().window(tabs2.get(1)).getTitle());
			ExtentTestManager.getTest().log(LogStatus.INFO,
					ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "switchTabsSuccess")));
			logger.info("Switch to Next Tab - - Success ");
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Failed to Switch Tabs :" + e.getMessage());
			ExtentTestManager.getTest().log(LogStatus.INFO,
					ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "switchTabsFailed")));
			logger.error("Failed to Switch Tabs :" + e.getMessage() + ": Stack Trace : " + e.getStackTrace());
		}
	}

	public void enterValue(WebElement element, String text) {
		try {
			element.sendKeys(text);
			ExtentTestManager.getTest().log(LogStatus.PASS,"Enter Text " + text + "  on Element : " + elementName(element) + " : Successfull");
			ExtentTestManager.getTest().log(LogStatus.INFO,ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "enterTextSuccess")));
			logger.info("Enter Text " + text + "  on Element : " + elementName(element) + " : Successfull");
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Failed to Enter Text : " + text + "  : on Element : "+ elementName(element) + " : Error : " + e.getMessage());
			ExtentTestManager.getTest().log(LogStatus.INFO,ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "enterTextFailed")));
			logger.error("Failed to Enter Text : " + text + "  : on Element : " + elementName(element) + " : Error : "+ e.getMessage() + ": Stack Trace : " + e.getStackTrace());
		}
	}
	

	public void enterPassKey(WebElement element, String passKey) {
		try {
			element.sendKeys(passKey);
			ExtentTestManager.getTest().log(LogStatus.PASS,"Enter Password " + passKey.replace(passKey, "************") + "  on Element : " + elementName(element) + " : Successfull");
			ExtentTestManager.getTest().log(LogStatus.INFO,ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "enterTextSuccess")));
			logger.info("Enter Passkey " + passKey.replace(passKey, "************") + "  on Element : " + elementName(element) + " : Successfull");
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Failed to Enter Text : " + passKey.replace(passKey, "************") + "  : on Element : "+ elementName(element) + " : Error : " + e.getMessage());
			ExtentTestManager.getTest().log(LogStatus.INFO,ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "enterTextFailed")));
			logger.error("Failed to Enter Text : " + passKey.replace(passKey, "************") + "  : on Element : " + elementName(element) + " : Error : "+ e.getMessage() + ": Stack Trace : " + e.getStackTrace());
		}
	}

	public void failed(String message) {
		ExtentTestManager.getTest().log(LogStatus.FAIL, message);
		logger.error("Failed - >" + message);
	}

	public void infoToReport(String message) {
		ExtentTestManager.getTest().log(LogStatus.INFO, message);
		logger.info("Message - > " + message);
	}

	
	
	public void infoToReportWithBoolean(boolean value, String message) {
		
		if(value){
		ExtentTestManager.getTest().log(LogStatus.INFO, message);
		logger.info("Message - > " + message);
		}else{
			ExtentTestManager.getTest().log(LogStatus.FAIL, message);
			logger.info("Failed in Info Assertion - - - > " + message);
			assertEquals(true, false);
		}
	}

	
	public void infoToReportWithScreenShot(String message) {
		ExtentTestManager.getTest().log(LogStatus.INFO, message);
		try {
			Thread.sleep(1000);
			//captureWindowScreenShot();
			ExtentTestManager.getTest().log(LogStatus.INFO,ExtentTestManager.getTest().addScreenCapture(captureWindowScreenShot()));
		} catch (Exception e) {
			System.out.println("Capture Windows screenshot Failed - > "+e.getMessage());
			logger.error("Capture Windows screenshot Failed - > "+e.getMessage());
			ExtentTestManager.getTest().log(LogStatus.FAIL,ExtentTestManager.getTest().addScreenCapture(captureWindowScreenShot()));
		}
		
	}

	public void waitMedium(int wait) {
		try {
			Thread.sleep(wait);
			ExtentTestManager.getTest().log(LogStatus.PASS, "Medium Wait for Seconds Success: " + wait);
			logger.info("Medium Wait for Seconds Success: " + wait);
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,
					"Medium Wait for Seconds Failed:" + wait + "  :" + e.getMessage());
			ExtentTestManager.getTest().log(LogStatus.INFO,
					ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "waitMedium")));
			logger.error("Medium Wait for Seconds Failed:" + wait + "  :" + e.getMessage() + ": Stack Trace : "
					+ e.getStackTrace());
		}
	}

	public static void switchToParentWin(String win) {
		try {
			driver.switchTo().window(win);
			ExtentTestManager.getTest().log(LogStatus.PASS, "Switch Control to Parent Window Success: " + win);
			ExtentTestManager.getTest().log(LogStatus.INFO,
					ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "switchParentWindow")));
			logger.info("Switch Control to Parent Window Success: " + win);
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,
					"Switch Control to Parent Window Failed: " + win + "  :" + e.getMessage());
			ExtentTestManager.getTest().log(LogStatus.INFO,
					ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "switchParentWindow")));
			logger.error("Switch Control to Parent Window Failed: " + win + "  :" + e.getMessage() + ": Stack Trace : "
					+ e.getStackTrace());
		}
	}

	public static void scrollToBottom(WebDriver driver) {
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			
			ExtentTestManager.getTest().log(LogStatus.PASS, "Scroll Window to bottom SuccessFull ");
			logger.info("Scroll Window to bottom SuccessFull ");
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Scroll Window to bottom Failed: " + e.getMessage());
			logger.error("Scroll Window to bottom Failed: " + e.getMessage() + ": Stack Trace : " + e.getStackTrace());
		}
	}
	
	
	public static void scrollToBottomInSlowMotion(WebDriver driver) {
		try {
			for (int second = 0;; second++) {
			    if(second >=60){
			        break;
			    }
			    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,400)", ""); //y value '400' can be altered
			    Thread.sleep(2000);
			}
			
			ExtentTestManager.getTest().log(LogStatus.PASS, "Scroll Window to bottom in Slow Motion SuccessFull ");
			logger.info("Scroll Window to bottom SuccessFull ");
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Scroll Window to bottom in Slow Motion Failed: " + e.getMessage());
			logger.error("Scroll Window to bottom Failed: " + e.getMessage() + ": Stack Trace : " + e.getStackTrace());
		}
	}
	
	
	public static void scrollToElement(WebElement xpath){
		try {
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", xpath);
			    Thread.sleep(2000);
			ExtentTestManager.getTest().log(LogStatus.PASS, "Scroll Window to bottom in DIV TAG SuccessFull ");
			logger.info("Scroll Window to bottom SuccessFull in DIV TAG");
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Scroll Window to bottom in DIV TAG Failed: " + e.getMessage());
			logger.error("Scroll Window to bottom Failed: " + e.getMessage() + ": Stack Trace : " + e.getStackTrace());
		}
	}
	
	public void selectOptionByVisibleText(WebElement element, String option) {
		try {
			Select dropDown = new Select(element);
			dropDown.selectByVisibleText(option);
			ExtentTestManager.getTest().log(LogStatus.PASS,
					"Select Option by Visible Text Success on Option : " + option + " : " + elementName(element));
			ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest()
					.addScreenCapture(captureHTMLScreenShot(driver, "selectByVisibleTextSuccess")));
			logger.info("Select Option by Visible Text Success on Option : " + option + " : " + elementName(element));
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,
					"Select Option by Visible Text Failed : " + option + "  : " + e.getMessage());
			ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest()
					.addScreenCapture(captureHTMLScreenShot(driver, "selectByVisibleTextFailed")));
			logger.error("Select Option by Visible Text Failed : " + option + "  : " + e.getMessage()
					+ ": StackTrace : " + e.getStackTrace());
		}
	}

	public void selectOptionByIndex(WebElement element, String option) {
		try {
			Select dropDown = new Select(element);
			dropDown.selectByIndex(Integer.parseInt(option));
			ExtentTestManager.getTest().log(LogStatus.PASS,
					"Select Option by Index Success : " + option + " : " + elementName(element));
			ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest()
					.addScreenCapture(captureHTMLScreenShot(driver, "selectByVisibleIndexSuccess")));
			logger.info("Select Option by Index Success : " + option + " : " + elementName(element));
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,
					"Select Option by Index Failed : " + option + "  : " + e.getMessage());
			ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest()
					.addScreenCapture(captureHTMLScreenShot(driver, "selectByVisibleIndexFailed")));
			logger.error("Select Option by Index Failed : " + option + "  : " + e.getMessage() + ": StackTrace : "
					+ e.getStackTrace());
		}
	}

	public void selectOptionByValue(WebElement element, String option) {
		try {
			Select dropDown = new Select(element);
			dropDown.selectByValue(option);
			ExtentTestManager.getTest().log(LogStatus.PASS, "Select Option by Value Success : " + option);
			ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest()
					.addScreenCapture(captureHTMLScreenShot(driver, "selectByValueSuccess")));
			logger.info("Select Option by Value Success : " + option);
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,"Select Option by Value Failed : " + option + "  : " + e.getMessage());
			ExtentTestManager.getTest().log(LogStatus.INFO,ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "selectByValueFailed")));
			logger.error("Select Option by Value Failed : " + option + "  : " + e.getMessage() + ": StackTrace : "+ e.getStackTrace());
		}
	}

	@SuppressWarnings("static-access")
	public HybridDriver(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver setDriver() throws InterruptedException {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
			System.setProperty("current.date.time", dateFormat.format(new Date()));
			prop.load(new FileInputStream("./config/config.properties"));
			switch (prop.getProperty("browserType")) {
			case "chrome":
				driver = initChromeDriver();
				break;
			case "firefox":
				driver = initFirefoxDriver();
				break;
			default:
				logger.info("Browser : " + prop.getProperty("browserType")
						+ " is invalid, Launching Firefox as browser of choice..");
				driver = initFirefoxDriver();
			}
		} catch (FileNotFoundException e) {
			logger.error("Exception while setting Driver - - > > " + e.getMessage() + ": StackTrace : " + e.getStackTrace());
		} catch (IOException e) {
			logger.error("Exception while setting Driver - - > > " + e.getMessage() + ": Stacktrace : " + e.getStackTrace());
		}
		return driver;
	}

	// below method is used to initiate properties file
	/*
	 * public void initPropFile() { config = new Properties(); String fileName =
	 * "config" + ".properties"; InputStream input = null; try { input = new
	 * FileInputStream( System.getProperty("user.dir") + File.separator +
	 * "config" + File.separator + fileName); // load properties file
	 * config.load(input); } catch (IOException e) { e.printStackTrace(); } }
	 */

	protected String getSreenShotSavePath() {
		String className = this.getClass().getName().toString().trim();
		File dir = new File(System.getProperty("user.dir") + fileSeperator + "screenshots" + fileSeperator + className
				+ fileSeperator);
		if (!dir.exists()) {
			// System.out.println("File created " + dir);
			dir.mkdir();
		}

		return dir.getAbsolutePath();
	}

	protected static String sprintScreenshots() {
		File dir = new File(System.getProperty("user.dir") + fileSeperator + "HTMLReports" + fileSeperator
				+ "ScreenShots" + fileSeperator);
		if (!dir.exists()) {
			dir.mkdir();
		}
		return dir.getAbsolutePath();
	}

	public void captureScreenShot() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String date = sdf.format(new Date());
		String ext = ".png";
		String path = getSreenShotSavePath() + fileSeperator + date + ext;

		try {
			if (driver instanceof TakesScreenshot) {
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(path));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String captureHTMLScreenShot(WebDriver driver, String name) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String date = sdf.format(new Date());
		String ext = ".png";
		String path = sprintScreenshots() + fileSeperator + date + ext;
		try {
			if (driver instanceof TakesScreenshot) {
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				File dest = new File(path);
				FileUtils.copyFile(scrFile, dest);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
/**
 * Take ScreenShot for PDF and Native Screens as well
 * @return
 */
	public static String captureWindowScreenShot() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String date = sdf.format(new Date());
		String ext = "-windows.jpg";
		String path = sprintScreenshots() + fileSeperator + date + ext;
		try {
			Thread.sleep(3000);
			File dest = new File(path);
			Robot robot = new Robot();
			String format = "jpg";
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
			ImageIO.write(screenFullImage, format, dest);
		} catch (AWTException | IOException | InterruptedException ex) {
			System.err.println(ex);
		}
		return path;
	}
	
	public static boolean checkTextPresence(String text){
	try {
		driver.getPageSource().contains(text);
		
		ExtentTestManager.getTest().log(LogStatus.PASS,"Text : " + text + "  : is Found Successfull in Page : "+driver.getTitle() );
		ExtentTestManager.getTest().log(LogStatus.INFO,ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "checkTextPresence")));
		logger.info("Text : " + text + "  : is Found Successfull in Page : "+driver.getTitle() );
	} catch (Exception e) {
		ExtentTestManager.getTest().log(LogStatus.FAIL,"Text : " + text + "  : is Not Found  in Page : "+driver.getTitle() );
		ExtentTestManager.getTest().log(LogStatus.INFO,ExtentTestManager.getTest().addScreenCapture(captureHTMLScreenShot(driver, "checkTextPresence")));
		logger.error("Text : " + text + "  : is Not Found  in Page : "+driver.getTitle() );
		return false;
	}
	return true;
	}	
	
	public static  boolean isTextOnPagePresent(String text) {
	    try {
			WebElement body = driver.findElement(By.tagName("body"));
			String bodyText = body.getText();
			  if(bodyText.contains(text)){
			  logger.info("Text : " + text + "  : is Found Successfull in Page : "+driver.getTitle() );
			  return true;
			  }
		} catch (Exception e) {
			logger.error("Text : " + text + "  : is Not Found  in Page : "+driver.getTitle()+ ":Error Message : "+e.getMessage() );
			return false;
		}
		return true;
	  
	}
	
	public static  boolean checkVerticalScrollBarPresence(WebDriver driver) {
		JavascriptExecutor javascript = (JavascriptExecutor) driver;
		try {
			Boolean b2 = (Boolean) javascript.executeScript("return document.documentElement.scrollHeight>document.documentElement.clientHeight;");
			  if (b2 == true) {
			   System.out.println("Vertical Scrollbar is present on page.");
			  } 
			ExtentTestManager.getTest().log(LogStatus.PASS,"Vertical Scroll Bar Found on the page" );
			logger.info("Vertical Scroll Bar Found on the page" );
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL," No presence of Scroll Bar in the page");
			logger.error(" Vertical Scroll bar is not available in the page " );
			return false;
		}
		return true;
	}

	private static WebDriver initChromeDriver() throws InterruptedException {
		// System.out.println("Launching Chrome browser");
		logger.info("Launching Chrome Browser...,");
		System.setProperty("webdriver.chrome.driver", driverPath);
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}

	private static WebDriver initFirefoxDriver() {
		// System.out.println("Launching Firefox browser..");
		logger.info("Launching Firefox Browser...,");
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		return driver;
	}

	@AfterMethod
	public static void afterMethod(ITestResult result) throws FileNotFoundException, IOException {
		if (result.isSuccess()) {
			ExtentTestManager.getTest().log(LogStatus.PASS, "TestCase Passed");
			logger.info("TestCase --> " + result.getTestName() + " Execution Completed : " + result.getStatus());
		} else if (result.getStatus() == ITestResult.FAILURE) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "TestCase Failed");
			logger.info("TestCase --> " + result.getTestName() + " Execution Completed : " + result.getStatus());
		} else if (result.getStatus() == ITestResult.SKIP) {
			ExtentTestManager.getTest().log(LogStatus.SKIP, "TestCase Skipped");
			logger.info("TestCase --> " + result.getTestName() + " Execution Completed : " + result.getStatus());
		}

		ExtentTestManager.endTest();
		ExtentManager.getInstance().flush();
	}

	@AfterClass
	public static void tearDown() {
		ExtentManager.getInstance().flush();
		logger.info("Execution Completed and Closing the driver");
	}

	public HybridDriver() {
		// initPropFile();
	}

	public static WebDriver getDriver() {
		return driver;
	}

	@BeforeMethod
	public static void beforeMethod(Method caller) throws FileNotFoundException, IOException {
		ExtentTestManager.startTest("" + caller.getName());
		logger.info("" + caller.getName() + ": Completed");
	}

	@BeforeClass
	public static void initializeTestBaseSetup() throws FileNotFoundException, IOException {
	}

	protected static WebDriver driver = null;
	static String driverPath = "Drivers/chromedriver.exe";
	static String screenShotPath = "/screenshots";
	static String propFile = "/config/config.properties";
	public static Properties config = null;
	private static String fileSeperator = System.getProperty("file.separator");
	static Properties prop = new Properties();
	final static Logger logger = Logger.getLogger(HybridDriver.class);
	static long timeoutSeconds;
}
