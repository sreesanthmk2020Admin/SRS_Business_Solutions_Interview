package bigbasket.components;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public final class DriverManager {

	public static WebDriver driver = null;

	public void initChrome() {
		try {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void launchurl(String url) {
		driver.manage().deleteAllCookies();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
		driver.manage().window().maximize();
	}

}
