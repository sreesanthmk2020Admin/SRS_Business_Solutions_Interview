package bigbasket.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import bigbasket.components.DriverManager;
import bigbasket.components.GenericComponents;

public abstract class BB_BasePage {
	
	protected boolean actualResults = false;
	public static String placeHolder = null;
	protected final GenericComponents obj_GenericComponents = new GenericComponents();
	
	protected WebElement pageHeader(String pageHeader) throws Exception {
		return DriverManager.driver.findElement(By.xpath("//h2[span[text()='" + pageHeader + "']]"));
	}
}
