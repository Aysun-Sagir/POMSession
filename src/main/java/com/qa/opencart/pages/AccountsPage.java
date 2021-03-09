package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.util.Constants;
import com.qa.opencart.util.ElementUtil;

public class AccountsPage {
	ElementUtil elementUtil;
	private WebDriver driver;
	
	private By logo = By.cssSelector("#logo");
	private By AccHeaders = By.cssSelector("#content h2");
	private By searchField =By.name("search");
	private By searchButton =By.cssSelector("div#search button");
	
	public AccountsPage (WebDriver driver) {
		this.driver=driver;
		elementUtil=new ElementUtil(driver);
	}
	
	public String getAccPageTitle() {
		return elementUtil.waitForTitleIs(5, Constants.ACCOUNT_PAGE_TITLE);
	}
	
	public boolean isLogoExist() {
		return elementUtil.doIsDisplayed(logo);
	}
		
	public int getAccountPageHeadersCount() {
		return elementUtil.getElements(AccHeaders).size();
	}
	
	public List<String> getAccountPageHeadersList() {
		List<WebElement> accList =elementUtil.getElements(AccHeaders);
		List<String> accSectionList =new ArrayList<String>();
		for(WebElement e: accList) {
			accSectionList.add(e.getText());
		}
		return accSectionList;
	}
	
	public SearchResultPage doSearch(String productName) {
		System.out.println("Search for the product : "+productName);
		elementUtil.doSendKeys(searchField, productName);
		elementUtil.doClick(searchButton);
		return new SearchResultPage(driver);
	}

}
