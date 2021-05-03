package bigbasket.components;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

	int counter = 1;
	int retryLimit = Integer.valueOf(new GenericComponents().getPropertyValue("retry.Counter"));

	public boolean retry(ITestResult result) {
		if(counter < retryLimit) {
			counter++;
			return true;
		}
		return false;
	}
}
