package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.util.Constants;
import com.qa.opencart.util.ElementUtil;

public class ProductInfoPage {

	ElementUtil elementUtil;
	
	private By productHeader = By.cssSelector("div#content h1");
	private By productImages =By.cssSelector("ul.thumbnails img");
	private By productMetaData =By.cssSelector("div#content ul.list-unstyled:nth-of-type(1) li");
	private By productPriceData =By.cssSelector("div#content ul.list-unstyled:nth-of-type(2) li");
	private By quantity =By.id("input-quantity");
	private By addToCartBtn =By.id("button-cart");
	private By successMessg =By.cssSelector("div.alert.alert-success.alert-dismissible");
	
	private By navigateCartBtn = By.cssSelector("span#cart-total");
	private By viewCartBtn = By.cssSelector("//strong[contains(text(), ' View Cart')]");

	
	public ProductInfoPage(WebDriver driver) {
		elementUtil =new ElementUtil(driver);
	}
	
	public String getProductHeaderText() {
		
		return elementUtil.doGetElementText(productHeader);
	}
	
	public int getProductImagesCount() {
		return elementUtil.getElements(productImages).size();
	}
	
	
	public Map<String,String> getProductInformation() {
		Map<String,String> productInfoMap = new HashMap<String,String>();
		productInfoMap.put("name", getProductHeaderText());
		
		List<WebElement> productMetaDataList = elementUtil.getElements(productMetaData);
		System.out.println("total metadata : "+productMetaDataList.size());
		for(WebElement e: productMetaDataList) {
			String meta[] = e.getText().split(":");
			String metaKey =meta[0].trim();
			String metaValue =meta[1].trim();
			productInfoMap.put(metaKey, metaValue);
		}
		
		//price
		List<WebElement> productPriceList = elementUtil.getElements(productPriceData);
		productInfoMap.put("price", productPriceList.get(0).getText().trim());
		productInfoMap.put("exTaxPrice", productPriceList.get(1).getText().split(":")[1].trim());
		
		return productInfoMap;
						
		}
	
	public void selectQuantity(String qty) {
		elementUtil.doSendKeys(quantity, qty);
	}
	
	public void addToCart() {
		elementUtil.doClick(addToCartBtn);
	}
	
	public boolean getSuccessMessage() {
		elementUtil.waitForPresenceOfElement(successMessg, 3);
		String succMesg = elementUtil.doGetElementText(successMessg);
		if(succMesg.contains(Constants.ADD_TO_CART_SUCCESS_MESSG)) {
		return true;
		}else {
			return false;
		}
	}
	
	public void navigateToAddToCart() {
		elementUtil.doClick(navigateCartBtn);
		elementUtil.doClick(viewCartBtn);
	}
	
}
		
	
