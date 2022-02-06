package com.qaweapon.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qaweapon.coreclasses.BasePage;

public class SearchResultPage extends BasePage {

	By searchResult = By.xpath("//div[@class='uEierd']//div[@role='heading']");

	public SearchResultPage(WebDriver driver) {
		super(driver);
	}

	public void verifySearchResult() {

		Assert.assertTrue(getText(searchResult).startsWith("COVID-19"));
	}

}
