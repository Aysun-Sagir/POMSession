 package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
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
	
	public WebDriver int_driver(String browserName, String browserVersion) {
		//String browserName = prop.getProperty("browser").trim();
		highlight = prop.getProperty("highlight").trim();
		optionsManager =new OptionsManager(prop);
		
//		
//		String browserEnv = System.getProperty("browserName");
//		if(browserEnv!=null) {
//			browserName=browserEnv;
//		}

		System.out.println("browser name is:" +browserName);
		if(browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteDriver(browserName, browserVersion);
			}else {
		//	driver=new ChromeDriver(optionsManager.getChromeOptions()); PARALEL EXECUTION ICIN BU DRIVER YERINE tlDriver KULLANDIK
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}
		}
		else if(browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteDriver(browserName, browserVersion);
			}else {
		//	driver=new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			}
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
	 * this method will define the desired capabilities and it will initialize the
	 * driver with the given capabilities. This method will send request to the Grid Hub
	 * @param browserName
	 */
	private void init_remoteDriver(String browserName, String browserVersion) {
		if(browserName.equalsIgnoreCase("chrome")) {
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(ChromeOptions.CAPABILITY, optionsManager.getChromeOptions());
			cap.setCapability("browserName", browserName.toLowerCase());
			cap.setCapability("browserVersion", browserVersion);
			cap.setCapability("enableVNC", true);
			
			try {
			tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), cap));
			}catch(MalformedURLException e) {
				e.printStackTrace();
			}
		}
		else if(browserName.equalsIgnoreCase("firefox")) {
			DesiredCapabilities cap = DesiredCapabilities.firefox();
			cap.setCapability(FirefoxOptions.FIREFOX_OPTIONS, optionsManager.getFirefoxOptions());
			cap.setCapability("browserName", browserName.toLowerCase());
			cap.setCapability("browserVersion", browserVersion);
			cap.setCapability("enableVNC", true);
			try {
			tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), cap));
			}catch(MalformedURLException e) {
				e.printStackTrace();
			}
		}
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
