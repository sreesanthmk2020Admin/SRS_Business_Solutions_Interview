package bigbasket.tests;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import bigbasket.components.DriverManager;
import bigbasket.components.GenericComponents;
import bigbasket.pages.BB_HomePage;
import bigbasket.pages.BB_CategoryPage;

public abstract class BaseInterviewAssessment {
	
	protected String testResultPlaceHolder = null;
	
	private final DriverManager obj_WebDriverManager = new DriverManager();
	private GenericComponents obj_GenericComponents = null;
	protected BB_HomePage obj_BB_HomePage = null;
	protected BB_CategoryPage obj_BB_CategoryPage = null;
	
	@BeforeSuite
	protected final void suiteStup() {
		obj_GenericComponents = new GenericComponents();
		
		obj_GenericComponents.initTestReport();
		GenericComponents.dateTime = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss").format(LocalDateTime.now());
	}

	@BeforeTest
	protected final void testSetup(ITestContext obj_ITestContext) {
		obj_WebDriverManager.initChrome();
		obj_WebDriverManager.launchurl(obj_GenericComponents.getPropertyValue("TEST.url").trim());
		
		obj_BB_HomePage = new BB_HomePage();
		obj_BB_CategoryPage = new BB_CategoryPage();
		
		obj_GenericComponents.startTestReport(obj_ITestContext.getName());
	}
	
	@AfterMethod
	protected final void methodTearDown(ITestResult testResult) {
		obj_GenericComponents.reportLogging(testResult, testResultPlaceHolder);
	}
	
	@AfterTest
	protected final void testTearDown() {
		DriverManager.driver.quit();
		obj_GenericComponents.endTestReport();
	}
	
	@AfterSuite
	protected final void suiteTearDown() {
		DriverManager.driver.quit();
		obj_GenericComponents.tearDownTestReport();
	}
	
}
