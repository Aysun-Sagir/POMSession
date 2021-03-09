package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.util.Constants;
import com.qa.opencart.util.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private ElementUtil elementUtil;
	private WebDriver driver;
	
	//Page Objects - By Locator -Object Repository
	
	private By username= By.id("input-email");
	private By password= By.id("input-password");
	private By login= By.xpath("//input[@value='Login']"); 
	private By forgotPwd =By.xpath("//div[@class='form-group']/a[text()='Forgotten Password']");
	private By registerLink =By.linkText("Register");
	
	//constructor:
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		elementUtil=new ElementUtil(driver);
	}
	
	//page actions:
	@Step("gettirn login page title...")
	public String getLoginPageTitle() {
		return elementUtil.waitForTitleIs(5, Constants.LOGIN_PAGE_TITLE);

	}
	
	@Step("checking forgot pwd link...")
	public boolean isForgotPwdLinkExist() {
		return elementUtil.doIsDisplayed(forgotPwd);
	}
	
	@Step("login with username: {0} and password: {1}")
	public AccountsPage doLogin(String un, String pwd) {
		System.out.println("login with: "+un+" : "+pwd);
		elementUtil.doSendKeys(username, un);
		elementUtil.doSendKeys(password, pwd);
		elementUtil.doClick(login);
		return new AccountsPage(driver);
	}
	
	@Step("Navigating to register page...")
	public RegisterPage navigateToRegisterPage() {
		elementUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}

}
