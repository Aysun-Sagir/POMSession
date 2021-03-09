 package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 
 *This method is used to init the driver on the basis of given browser value
 * parameter browserName
 * @return this return webDriver
 *
 */

public class DriverFactory {
	
	//WebDriver driver;
	Properties prop;
	public static String highlight;
	OptionsManager optionsManager;
	
	//driver yerine tlDriver kullandik
	public static ThreadLocal<WebDriver> tlDriver=new ThreadLocal<>();
	
	public WebDriver int_driver(Properties prop) {
		String browserName = prop.getProperty("browser").trim();
		highlight = prop.getProperty("highlight").trim();
		optionsManager =new OptionsManager(prop);
		
		System.out.println("browser name is:" +browserName);
		
		if(browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
		//	driver=new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
		}
		else if(browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
		//	driver=new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
		}
		else if(browserName.equalsIgnoreCase("safari")) {
			//driver=new SafariDriver();
			tlDriver.set(new SafariDriver());
		}
		else {
			System.out.println("browser is not found " +browserName);
		}
		
		getDriver().manage().window().fullscreen();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url").trim());
		
		return getDriver();
	}
	/**
	 * 
	 * @return
	 */
	public static synchronized WebDriver getDriver() {
		return tlDriver.get();
	}
	/**
	 * This method is init the properties from .properties file
	 * @return Properties (prop)
	 */
	public Properties init_prop() {
		prop=new Properties();
		FileInputStream ip =null;
		
		String env = System.getProperty("env");
		if(env==null) {
			try {
				ip =new FileInputStream("./src/test/resources/config/config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
			switch(env) {
			case "qa":
				ip=new FileInputStream("./src/test/resources/config/qa.config.properties");
				break;
			case "stage":
				ip=new FileInputStream("./src/test/resources/config/stage.config.properties");
				break;
			case "dev":
				ip=new FileInputStream("./src/test/resources/config/dev.config.properties");
				break;	
			default: 
				System.out.println("Please pass the rigth env value...");
				break;
			}
			
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			}
		}
		
		try {	
			prop.load(ip);	
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	//mvn clean install -Denv =qa
	
	/**
	 * take screenshot
	 */
		
		public String getScreenshot() {
			File srcFile =((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
			//File srcFile=new File(src);
			
			String path = System.getProperty("user.dir")+"/screenshots/"+System.currentTimeMillis()+".png";
			File destination =new File(path);
			
			try {
				FileUtils.copyFile(srcFile, destination);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return path;
		}
}
