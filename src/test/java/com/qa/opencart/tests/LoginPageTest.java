package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.util.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Story("US-101: design the login page for demo opencart app with login, title and pwd link")
@Epic("Epic-100: design login page feature")
public class LoginPageTest extends BaseTest  {

	@Description("Login page title test...")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority=1)
	public void loginPageTitle() {
		String title = loginPage.getLoginPageTitle();
		System.out.println("login page title: "+title);
		Assert.assertEquals(title, Constants.LOGIN_PAGE_TITLE);
	}
	
	@Description("forgot pwd link test...")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority=2)
	public void forgotPwdLinkTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	
	@Description("Login test with correct credentials test...")
	@Severity(SeverityLevel.BLOCKER)
	@Test (priority=3)
	public void loginTest() {
		accPage= loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(accPage.getAccPageTitle(), Constants.ACCOUNT_PAGE_TITLE);
	}
}
