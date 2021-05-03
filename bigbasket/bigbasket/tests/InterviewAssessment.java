package bigbasket.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import bigbasket.components.RetryAnalyzer;
import bigbasket.pages.BB_BasePage;

@Test(enabled = true, groups= {""})
public final class InterviewAssessment extends BaseInterviewAssessment {
	
	@Test(retryAnalyzer=RetryAnalyzer.class)
	private final void step01() {
		testResultPlaceHolder = "Did not select Green tea category";
		Assert.assertTrue(obj_BB_HomePage.selectCategory("Green tea"));
		testResultPlaceHolder = "Successfully selected, Green tea sub category";
	}

	@Test(retryAnalyzer=RetryAnalyzer.class)
	private final void step02() {
		testResultPlaceHolder = "Did not search for tea";
		Assert.assertTrue(obj_BB_CategoryPage.searchBrand("tea"));
		testResultPlaceHolder = "Successfully search tea in brand field";
	}
	
	@Test(retryAnalyzer=RetryAnalyzer.class)
	private final void step03() {
		testResultPlaceHolder = "Did not select first and last checkboxes";
		Assert.assertTrue(obj_BB_CategoryPage.selectFirstLast());
		testResultPlaceHolder = "Successfully Selected first and last boxes and count of items listed is " + BB_BasePage.placeHolder;
	}
	
	@Test(retryAnalyzer=RetryAnalyzer.class)
	private final void step04() {
		testResultPlaceHolder = "Did not add product to cart";
		Assert.assertTrue(obj_BB_CategoryPage.addfirstItemInCart(2));
		testResultPlaceHolder = "Successfully added first item of quantity 2 of and displayed success banner";
	}
	
	@Test(retryAnalyzer=RetryAnalyzer.class)
	private final void step05() {
		testResultPlaceHolder = "Did not show up in basket/cart";
		Assert.assertTrue(obj_BB_CategoryPage.verifyproductAddedtoCart());
		testResultPlaceHolder = "Successfully displayed in basket with price sumup to the quantity:" + BB_BasePage.placeHolder;
	}
	
}
