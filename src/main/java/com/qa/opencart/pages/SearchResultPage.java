 package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.util.ElementUtil;

public class SearchResultPage {
	
	ElementUtil elementUtil;
	private WebDriver driver;
	
	By searchItemresult =By.cssSelector("div.product-layout div.product-thumb");
	By resultItems =By.cssSelector("div.product-thumb h4 a");
	
	public SearchResultPage(WebDriver driver) {
		this.driver=driver;
		elementUtil =new ElementUtil(driver);
	}
	
	public int getProductResultCounts() {
		return elementUtil.getElements(searchItemresult).size();
	}
	
	public ProductInfoPage selectProductFromResults(String productName) {
		List<WebElement> resultItemList = elementUtil.getElements(resultItems);
		System.out.println("total number of items displayed for " +productName+ " : " + resultItemList.size());
		for(WebElement e:resultItemList) {
			if(e.getText().equals(productName)) {
				e.click();
				break;
			}
		}
		return new ProductInfoPage(driver);
	}

}
