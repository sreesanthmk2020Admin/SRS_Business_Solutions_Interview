package bigbasket.components;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public final class GenericComponents {

	private String getPropertyValue = null;
	private String testCaseName = null;
	private static int incrementor = 0;
	public static String dateTime = null;
	private static String screenshotLocation = null;
	public static boolean screenshotTaken = false;

	private ExtentHtmlReporter obj_ExtentHtmlReporter = null;
	private static ExtentReports obj_ExtentReports = null;
	private ExtentTest obj_ExtentTest = null;
	private Properties obj_Properties = new Properties();


	public final void exceptionHandler(Exception e) {
		//e.printStackTrace();
		e.getLocalizedMessage();
	}

	public final void takeScreenshot() {
		try {
			if(!screenshotTaken) {
				screenshotLocation = System.getProperty("user.dir") + "/" + getPropertyValue("screenshot.Location") + "/" + dateTime + "/" + testCaseName + ++incrementor +".jpg";
				File screenshotFile = ((TakesScreenshot) DriverManager.driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(screenshotFile, new File(screenshotLocation));
			}
		} catch (Exception e) {
			exceptionHandler(e);
		}
	}

	public final String getPropertyValue(String propertykey) {
		try {
			obj_Properties.load(new FileInputStream("testConfig\\config.properties"));
			getPropertyValue = obj_Properties.getProperty(propertykey);
		} catch (Exception e) {
			exceptionHandler(e);
		}
		return getPropertyValue;
	}

	public final void SyncElement(WebElement element, String waitFor) throws Exception {
		WebDriverWait obj_WebDriverWait = new WebDriverWait(DriverManager.driver, Integer.valueOf((getPropertyValue("sync.timeout.SHORT"))));
		if (waitFor.equals("visibility"))
			obj_WebDriverWait.until(ExpectedConditions.visibilityOf(element));
		else if (waitFor.equals("enable"))
			obj_WebDriverWait.until(ExpectedConditions.elementToBeClickable(element));
		else if (waitFor.equals("not visible")) {
			Thread.sleep(Integer.valueOf(getPropertyValue("sync.timeout.SHORT"))/3 * 1000);
			obj_WebDriverWait.until(ExpectedConditions.invisibilityOf(element));
		}
	}

	public final void initTestReport() {
		try {
			obj_ExtentHtmlReporter = new ExtentHtmlReporter("Dashboard.html");
			obj_ExtentHtmlReporter.config().setDocumentTitle(getPropertyValue("report.DocumentTitle"));
			obj_ExtentHtmlReporter.config().setReportName(getPropertyValue("report.ReportName"));
			obj_ExtentHtmlReporter.config().setTheme(Theme.DARK);

			//obj_ExtentHtmlReporter.setSystemAttributeContext(systemAttributeContext);

			obj_ExtentReports = new ExtentReports();
			obj_ExtentReports.setSystemInfo("Host Name", getPropertyValue("report.HostName"));
			obj_ExtentReports.setSystemInfo("Environment", getPropertyValue("report.Environment"));
			obj_ExtentReports.setSystemInfo("User Name", getPropertyValue("TEST.Username.Admin"));
			obj_ExtentReports.attachReporter(obj_ExtentHtmlReporter);
		} catch (Exception e) {
			exceptionHandler(e);
		}
	}

	public final void startTestReport(String testName) {
		obj_ExtentTest = obj_ExtentReports.createTest(testName);
		testCaseName = testName;
	}

	public final void reportLogging(ITestResult testResult, String testLog) {
		try {
			if(testResult.getStatus() == ITestResult.SUCCESS) {
				if(Boolean.valueOf(getPropertyValue("screenshot.PASS").toLowerCase())) {
					takeScreenshot();
					obj_ExtentTest.log(Status.PASS, testLog, MediaEntityBuilder.createScreenCaptureFromPath(screenshotLocation).build());
				} else
					obj_ExtentTest.log(Status.PASS, testLog);
			}
			else if(testResult.getStatus() == ITestResult.FAILURE) {
				if(Boolean.valueOf(getPropertyValue("screenshot.FAIL").toLowerCase())) {
					takeScreenshot();
					obj_ExtentTest.log(Status.FAIL, testLog, MediaEntityBuilder.createScreenCaptureFromPath(screenshotLocation).build());
				} else 
					obj_ExtentTest.log(Status.FAIL, testLog);
			}
			//logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
		} catch (Exception e) {
			exceptionHandler(e);
		}
	}

	public final void endTestReport() {
		obj_ExtentReports.flush();
	}

	public final void tearDownTestReport() {
		try {
			obj_ExtentHtmlReporter.flush();

			if(!new File(getPropertyValue("report.Location")).exists())
				FileUtils.forceMkdir(new File(getPropertyValue("report.Location")));
			FileUtils.copyFile(new File("Dashboard.html"), new File(getPropertyValue("report.Location") + "/" + dateTime + ".html"));
		} catch (Exception e) {
			exceptionHandler(e);
		}
	}

	public final void hoverOnElement(WebElement element) {
		try {
			Actions obj_Actions = new Actions(DriverManager.driver);
			obj_Actions.moveToElement(element).build().perform();
		} catch (Exception e) {
			exceptionHandler(e);
		}
	}

}
