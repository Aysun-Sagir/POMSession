package com.qa.opencart.tests;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.util.Constants;
import com.qa.opencart.util.Error;
import com.qa.opencart.util.ExcelUtil;

public class RegisterPageTest extends BaseTest{
	
	@BeforeClass
	public void setupRegister() {
		registerPage = loginPage.navigateToRegisterPage();
	}
	
	@DataProvider
	public Object[][] getRegisterData() {
		Object data[][] = ExcelUtil.getTestData(Constants.REGISTER_SHEET_NAME);
		return data;
	}
	
	public String getRandomNumber() {
		Random randomGenerator=new Random();
		String email ="testautomation"+randomGenerator.nextInt(1000)+"@gmail.com";
		return email;
	}
	
	@Test(dataProvider = "getRegisterData")
	public void userRegisterTest(String firstName, String lastName, String telephone
			, String password, String subscribe) {
		
		Assert.assertTrue(registerPage.accountRegistration(firstName, lastName, 
				getRandomNumber(), telephone, password, subscribe), Error.REGISTER_FAILED_MESSG);		
	}
	

	

}
