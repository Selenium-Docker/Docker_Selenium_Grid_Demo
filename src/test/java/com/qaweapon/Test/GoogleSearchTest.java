package com.qaweapon.Test;

import java.io.IOException;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.qaweapon.coreclasses.BaseTest;
import com.qaweapon.pageobjects.GoogleHomePage;
import com.qaweapon.pageobjects.SearchResultPage;

public class GoogleSearchTest extends BaseTest {

	GoogleHomePage googleHomePage;
	SearchResultPage searchResultPage;

	@Test
	public void googleSearchTest() throws IOException, InterruptedException {

		googleHomePage = webPageGenerator.GetInstance(GoogleHomePage.class);
		googleHomePage.enterSearchKeyword("test");
		test.log(Status.INFO, "Serach keyword entered.");
		googleHomePage.clickGoogleSearchButton();
		test.log(Status.INFO, "Clicked on search button.");

		searchResultPage = webPageGenerator.GetInstance(SearchResultPage.class);
		searchResultPage.verifySearchResult();
		test.log(Status.PASS, "Search Result showing as expected.");
	}

}