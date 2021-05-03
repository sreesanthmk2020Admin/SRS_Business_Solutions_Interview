package bigbasket.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import bigbasket.components.DriverManager;

public final class BB_CategoryPage extends BB_BasePage {
	
	@FindBy(how=How.XPATH, xpath = "//div[contains(@class,'custom-search')]/input")
	private WebElement txt_search;
	
	@FindBy(how=How.XPATH, xpath = "//h4[span[text()='Brand']]/following-sibling::section/div/label")
	private WebElement chk_firstBrand;
	
	@FindBy(how=How.XPATH, xpath = "//h4[span[text()='Brand']]/following-sibling::section/div[last()]/label")
	private WebElement chk_lasttBrand;
	
	@FindBy(xpath="//li/a[text()='Clear all']")
	private WebElement btn_ClearAll;
	
	@FindBy(how=How.CSS, css = "div.items>div[qa='product']")
	private List<WebElement> productList;
	
	@FindBy(how=How.XPATH, xpath="//div[@class='items']/div[@qa='product']//div[@class='delivery-opt'][.//button[normalize-space(text())='Add']]/parent::div/parent::div/preceding-sibling::div/a")
	private WebElement lbl_name;
	
	@FindBy(how=How.XPATH, xpath = "//div[@class='items']/div[@qa='product']//div[@class='delivery-opt'][.//button[normalize-space(text())='Add']]/preceding-sibling::div/h4/span[last()]/span")
	private WebElement lbl_Price;
	
	@FindBy(how = How.XPATH, xpath = "//div[@class='items']/div[@qa='product']//span[text()='Qty']/following-sibling::input")
	private WebElement txt_Qty;
	
	@FindBy(how=How.XPATH, xpath = "//div[@class='items']/div[@qa='product']//button[normalize-space(text())='Add']")
	private WebElement btn_Add;
	
	@FindBy(how=How.XPATH, xpath="//a[@qa='myBasket']")
	private WebElement btn_myBasket;
	
	@FindBy(how=How.XPATH, xpath="//a[@qa='prodNameMB']")
	private WebElement lbl_cartProd;
	
	@FindBy(how=How.XPATH, xpath="//span[@qa='subTotalMB']")
	private WebElement lbl_subTotal;
	
	public BB_CategoryPage() {
		PageFactory.initElements(DriverManager.driver, this);
	}
	
	public final boolean searchBrand(String searchKey) {
		actualResults = false;
		try {
			obj_GenericComponents.SyncElement(txt_search, "visibility");
			txt_search.sendKeys(searchKey);
			actualResults = txt_search.getAttribute("value").equalsIgnoreCase(searchKey);
		} catch (Exception e) {
			obj_GenericComponents.exceptionHandler(e);
		}
		return actualResults;
	}
	
	public final boolean selectFirstLast() {
		actualResults = false;
		try {
			chk_firstBrand.click();
			obj_GenericComponents.SyncElement(btn_ClearAll, "enable");
			Thread.sleep(10000);
			
			chk_lasttBrand.click();
			obj_GenericComponents.SyncElement(btn_ClearAll, "enable");
			Thread.sleep(10000);
			
			actualResults = chk_firstBrand.findElement(By.xpath("child::input")).isSelected() && chk_lasttBrand.findElement(By.xpath("child::input")).isSelected();
			if(actualResults)
				placeHolder = String.valueOf(productList.size());
		} catch (Exception e) {
			obj_GenericComponents.exceptionHandler(e);
		}
		return actualResults;
	}
	
	public final boolean addfirstItemInCart(int quantity) {
		actualResults = false;
		try {
			placeHolder = String.valueOf(Float.valueOf(lbl_Price.getText()) * quantity);
			txt_Qty.clear();
			txt_Qty.sendKeys(String.valueOf(quantity));
			btn_Add.click();
			
			actualResults = DriverManager.driver.findElement(By.xpath("//div[starts-with(text(),'Successfully added " + lbl_name.getText() + "')]")).isDisplayed();
		} catch (Exception e) {
			obj_GenericComponents.exceptionHandler(e);
		}
		return actualResults;
	}
	
	public final boolean verifyproductAddedtoCart() {
		actualResults = false;
		try {
			DriverManager.driver.findElement(By.xpath("//div[starts-with(text(),'Successfully added')]")).click();
			//obj_GenericComponents.SyncElement(DriverManager.driver.findElement(By.xpath("//div[starts-with(text(),'Successfully added')]")), "not visible");
		} catch (Exception e) {}
		try {
			btn_myBasket.click();
			Thread.sleep(10000);
			System.out.println(lbl_cartProd.getText().contains(lbl_name.getText()));
			System.out.println(Float.valueOf(placeHolder).compareTo(Float.valueOf(lbl_subTotal.getText())));
			System.out.println(Float.valueOf(placeHolder).compareTo(Float.valueOf(lbl_subTotal.getText())) == 0);
			actualResults = Boolean.compare(lbl_cartProd.getText().contains(lbl_name.getText()), Float.valueOf(placeHolder).compareTo(Float.valueOf(lbl_subTotal.getText())) == 0) == 0;
		} catch (Exception e) {
			obj_GenericComponents.exceptionHandler(e);
		}
		return actualResults;
	}
	
}








