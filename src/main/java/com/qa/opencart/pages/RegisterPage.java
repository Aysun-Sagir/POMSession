package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.util.Constants;
import com.qa.opencart.util.ElementUtil;

public class RegisterPage {
	
	ElementUtil elementUtil;
	private WebDriver driver;
	
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPwd = By.id("input-confirm");
	private By subscribeYes = By.xpath("(//label[@class='radio-inline'])[position()=1]/input");
	private By subscribeNo = By.xpath("(//label[@class='radio-inline'])[position()=2]/input");
	private By agreeCheckBox = By.name("agree");
	private By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");
	private By successMessg = By.cssSelector("div#content h1");
	private By logoutLink = By.linkText("Logout");
	private By registerLink =By.linkText("Register");
	
	public RegisterPage(WebDriver driver) {
		this.driver=driver;
		elementUtil=new ElementUtil(driver); 
	}
	
//	By privacyPol = By.name("agree");
//	By continueBut = By.xpath("//*[@id='content']/form/div/div/input[2]");
//	By successMessg = By.xpath("//*[@id='common-success']//..//*[@id='content']/h1");
//	By unsuccessMessg =By.xpath("//div/i[@class='fa fa-exclamation-circle']");
	
	public boolean accountRegistration(String firstName, String lastName, String email, String telephone, String password, String subcribe) {
		
		elementUtil.doSendKeys(this.firstName, firstName);
		elementUtil.doSendKeys(this.lastName, lastName);
		elementUtil.doSendKeys(this.email, email);
		elementUtil.doSendKeys(this.telephone, telephone);
		elementUtil.doSendKeys(this.password, password);
		elementUtil.doSendKeys(this.confirmPwd, password);
		
		if(subcribe.equals("yes")) {
			elementUtil.doClick(this.subscribeYes);
		}else {
			elementUtil.doClick(this.subscribeNo);
		}
		elementUtil.doClick(this.agreeCheckBox);
		elementUtil.doClick(this.continueButton);
		elementUtil.waitForVisiblityOfElement(this.successMessg, 5);
		String mesg = elementUtil.doGetElementText(this.successMessg);
		System.out.println("account creation: "+mesg);
		if(mesg.contains(Constants.ACC_CREATION_SUCCESS_MESSG)) {
			
			elementUtil.doClick(logoutLink);
			elementUtil.doClick(registerLink);
			
			return true;
		}else {
			return false;
		}
	}
}
