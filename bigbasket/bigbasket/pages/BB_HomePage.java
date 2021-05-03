package bigbasket.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import bigbasket.components.DriverManager;

public final class BB_HomePage extends BB_BasePage {
	
	@FindBy(how=How.XPATH, xpath = "//a[normalize-space(text())='Shop by Category']")
	private WebElement lnk_shopByCategory;
	
	@FindBy(how=How.XPATH, xpath = "//div[@id='navbar']//a[text()='Beverages']")
	private WebElement lnk_beverages;
	
	@FindBy(how=How.XPATH, xpath = "//div[@id='navbar']//a[text()='Tea']")
	private WebElement lnk_tea;
	
	@FindBy(how=How.XPATH, xpath = "//div[@id='navbar']//a[text()='Green Tea']")
	private WebElement lnk_greenTea;
	
	public BB_HomePage() {
		PageFactory.initElements(DriverManager.driver, this);
	}

	public final boolean selectCategory(String childSubCategory) {
		actualResults = false;
		try {
			if(btn_Close.isDisplayed())
				btn_Close.click();
		} catch (Exception e) {}
		
		try {
			obj_GenericComponents.SyncElement(lnk_shopByCategory, "visibility");
			obj_GenericComponents.hoverOnElement(lnk_shopByCategory);
			
			obj_GenericComponents.SyncElement(lnk_beverages, "visibility");
			obj_GenericComponents.hoverOnElement(lnk_beverages);
			
			obj_GenericComponents.SyncElement(lnk_tea, "visibility");
			obj_GenericComponents.hoverOnElement(lnk_tea);
			
			obj_GenericComponents.SyncElement(lnk_greenTea, "visibility");
			lnk_greenTea.click();
			
			obj_GenericComponents.SyncElement(pageHeader("Green Tea"), "visibility");
			actualResults = pageHeader("Green Tea").isDisplayed();
		} catch (Exception e) {
			obj_GenericComponents.exceptionHandler(e);
		}
		return actualResults;
	}

}
