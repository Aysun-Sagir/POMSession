 package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchResultPage;

public class BaseTest {
	private WebDriver driver;
	public Properties prop;
	DriverFactory df;
	public LoginPage loginPage;
	public AccountsPage accPage;
	public SearchResultPage searchResultPage;
	public ProductInfoPage productInfoPage;
	public RegisterPage registerPage;
	
	@Parameters({"browser","version"})
	@BeforeTest
	public void setUp(String browserName, String browserVersion) {
		df=new DriverFactory();
		prop = df.init_prop();
		
		String browser =prop.getProperty("browser");
		String browserEnv =System.getProperty("browser");
		if(browserName!=null) {
			browser=browserName;
		}
		if(browserEnv!=null) {
			browser=browserEnv;
		}
		driver= df.int_driver(browser, browserVersion);
		loginPage=new LoginPage(driver);
		
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
