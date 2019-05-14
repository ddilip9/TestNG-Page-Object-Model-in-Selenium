# vanguard-product-compare
Compare multiple retail funds in Product compare

Framework Approach and Setup process:

Technology stack: Junit/TestNG, Java, extent reporting libs for rich HTML preview, Eclipse/Intelli J IDE, Selenium libs, browser drivers, Maven, Jenkins for Continuous execution(Compatible)
 On very high level my framework contains KeywordClass, PageObjectClass(Object Repository), ReportingClass,  TestcaseDriverClass, and other utilities

KeywordClass(Hybrid Driver) – Contains all the keywords of Selenium functionality (For eg: click(), elementSendText(), waitForElementPresence(), dragAndDrop(), wait(), sleep() ..etc)

PageObjectClass – All the XPath and other elements are recorded in this class, for better standard created separate class for separate pages in the application – this is equivalent to page object model.

ReportingClass – reporting class get logs for every test step execution, since every keyword is surrounded with try-catch exception handling, Pass/Fail log status will be recorded as XML tags via reporting class.
 
TestcaseDriverClass – Test case driver class contain the test execution data, how many test cases to be executed, from which sheet to pickup for execution, which testing class to be executed, number of parallel executions..etc.
Test Case Creation Approach: 

We write test cases using our own keywords in TestNG class for e.g.: 

Click(HomePage.loginButton);
elementSendText(HomePage.userName, userName)

Click   - our custom keyword 
HomePage – Name of Page which Element is recorded
loginButton – elementName in HomePage

Test data can be directly provided from feature file with Examples via Scenario outline or we can store in any file formats like Excel, JSON or Database and write helper classes to supply test data.

For Execution: TestNG class with test cases is used to execute and generate the results via TestNG runner or Command line execution or Maven or ANT or Jenkins...Etc. Rich HTML reports can be generated via our custom report storage file (XML/JSON which are generated while execution) or third-party libraries like Extent reports can serve the purpose.

Justification:

•	This approach can be used for eliminating code duplications. If the test steps are repeated they can be created in Component class and reused across the application.
•	Object Repository – All Xpath, Id, Names, cssselectors can be stored with name in Page Object classes
•	Reports are created automatically – No code write is required from the tester to generate framework will handle.
•	Test data organization is simple. Excel can be used to maintain test data.
•	Execution is a cake walk. (Can be execute from Maven life cycle, Jenkins jobs, direct from Eclipse, or From command prompt  ...etc. has many options to execute the test scenario’s)
•	Can be easily integrated with CI/CD tools 
•	Can be integrated with other frameworks like Galen, Sikuli, Robot.etc.
•	No limitation for number of  browsers execution
•	Grid support is awesome
•	Defects can be directly logged to Defect management tool by extending utilities classes

Setup and Usage Process:


•	Download project from Github repo and clone
Please see word document guide for more detailed steps.
