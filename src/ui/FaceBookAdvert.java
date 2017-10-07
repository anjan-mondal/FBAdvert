package ui;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

import config.PropertiesValue;
import config.ReadExcelProperty;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

public class FaceBookAdvert {
	
	public static WebDriver driver;
	static PropertiesValue pv = new PropertiesValue();
	static ReadExcelProperty rp =new ReadExcelProperty();
	static String computername = null;
	
	public static boolean waitForJSandJQueryToLoad() {

	    WebDriverWait wait = new WebDriverWait(driver, 60);

	    // wait for jQuery to load
	    ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
	      @Override
	      public Boolean apply(WebDriver driver) {
	        try {
	          return ((Long)((JavascriptExecutor)driver).executeScript("return jQuery.active") == 0);
	        }
	        catch (Exception e) {
	          // no jQuery present
	          return true;
	        }
	      }
	    };

	    // wait for Javascript to load
	    ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
	      @Override
	      public Boolean apply(WebDriver driver) {
	        return ((JavascriptExecutor)driver).executeScript("return document.readyState")
	        .toString().equals("complete");
	      }
	    };

	  return wait.until(jQueryLoad) && wait.until(jsLoad);
	}
	
	public static void waitForPageToLoad()
	{
		String waitT = pv.readProperties("config.properties", "WaitTime");
		int waitTime = Integer.parseInt(waitT);
		System.out.print("Waiting for page to load: ");
		while(waitForJSandJQueryToLoad() && waitTime > 0)
		{
			System.out.print(".");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			waitTime--;
		}
		System.out.println("Page load complete");
	}
	
	public static void loginToFacebook()
	{
		waitForPageToLoad();
		WebDriverWait wait = new WebDriverWait(driver, 60);
		String username = pv.readProperties("xpath.properties", "EMAIL");
		String password = pv.readProperties("xpath.properties", "PASS");
		String loginbtn = pv.readProperties("xpath.properties", "LOGIN_BTN");
		
		WebElement username_element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(username)));
		username_element.sendKeys(rp.getProperty("Email",computername));
		WebElement password_element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(password)));
		password_element.sendKeys(rp.getProperty("Password",computername));
		WebElement loginbtn_element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(loginbtn)));
		loginbtn_element.click();
	}
	
	public static void navigateToManageAdverts()
	{
		waitForPageToLoad();
		WebDriverWait wait = new WebDriverWait(driver, 60);
		String usermenu = pv.readProperties("xpath.properties", "FB_USER_MENU");
		String manageadvertslink = pv.readProperties("xpath.properties", "MANAGE_ADVERTS");
		
		WebElement usermenu_element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(usermenu)));
		usermenu_element.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement manageadvertslink_element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(manageadvertslink)));
		manageadvertslink_element.click();
	}
	
	public static void selectAdvertAccount()
	{
		waitForPageToLoad();
		WebDriverWait wait = new WebDriverWait(driver, 60);
		String advertAccountName = pv.readProperties("xpath.properties", "MANAGE_ADVERT_ACC_Name");
		String advertAccountNameXPAth = advertAccountName.replace("#####", rp.getProperty("AdvertAccountName",computername));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement advertAccountName_element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(advertAccountNameXPAth)));
		advertAccountName_element.click();	
	}
	
	public static void processCSV() throws InterruptedException
	{
		waitForPageToLoad();
		WebDriverWait wait = new WebDriverWait(driver, 60);
		String ReportType =  rp.getProperty("ReportType",computername);
		String ReportTypeArr[] = ReportType.split(";");
		
		for(String i : ReportTypeArr)
		{
			String clickReportType = "//span[text()='" + i + "']";
			WebElement clickReportType_element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(clickReportType)));
			clickReportType_element.click();
			selectDataColumn();
			selectDateRange();
			exportCSV();
			Thread.sleep(15000);
		}
		
	}
	
	public static void selectDataColumn() throws InterruptedException
	{
		waitForPageToLoad();
		WebDriverWait wait = new WebDriverWait(driver, 60);
		String dataColumn = rp.getProperty("DataColumn",computername);
		String dataColumnXPath = pv.readProperties("xpath.properties", "CLICK_COLUMNS");
		WebElement dataColumnXPath_element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dataColumnXPath)));
		dataColumnXPath_element.click();

		String dataColumnSelect = "//span[text()='" + dataColumn + "']";

		WebElement dataColumnSelect_element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dataColumnSelect)));
		dataColumnSelect_element.click();
	}
	
	public static void selectDateRange()
	{
		waitForPageToLoad();
		WebDriverWait wait = new WebDriverWait(driver, 60);
		String dateRange = rp.getProperty("DateRange",computername);
		String dateRangeXPath = pv.readProperties("xpath.properties", "DATE_COLUMNS");
		String dateUpdateXPath = pv.readProperties("xpath.properties", "DATE_UPDATE");
		WebElement dateRangeXPath_element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dateRangeXPath)));
		dateRangeXPath_element.click();
		
		String dateSelect = "//li[text()='" + dateRange + "']";
		WebElement dateSelect_element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dateSelect)));
		dateSelect_element.click();
		
		WebElement dateUpdate_element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dateUpdateXPath)));
		dateUpdate_element.click();
	}
	
	public static void exportCSV() throws InterruptedException
	{
		waitForPageToLoad();
		WebDriverWait wait = new WebDriverWait(driver, 60);

		String exportXPath = pv.readProperties("xpath.properties", "EXPORT");
		WebElement exportXPath_element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(exportXPath)));
		exportXPath_element.click();
		
		String saveAsCSVXPath = pv.readProperties("xpath.properties", "SAVE_AS_CSV");
		Thread.sleep(5000);
		boolean clickRequired = driver.findElement(By.xpath(saveAsCSVXPath)).isSelected();
		if(!clickRequired) {
			WebElement saveAsCSVXPath_element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(saveAsCSVXPath)));
			saveAsCSVXPath_element.click();
		}
		
		
		String exportBtnXPath = pv.readProperties("xpath.properties", "EXPORT_BTN_CSV");
		WebElement exportBtnXPath_element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(exportBtnXPath)));
		exportBtnXPath_element.click();	
	}
	
	public static void main(String[] args) throws InterruptedException {
		try {
			computername = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String driverPath = System.getProperty("user.dir");
		driverPath+=File.separator+"drivers"+File.separator;
		System.out.println(driverPath);
		
		String browser = rp.getProperty("Browser",computername);
		
		switch(browser.toUpperCase()) {
		
		case "CHROME":
			System.setProperty("webdriver.chrome.driver", driverPath+"chromedriver.exe");
			driver = new ChromeDriver(ExportCsv.csvExport());
			break;
			
		case "FIREFOX":
			System.setProperty("webdriver.gecko.driver", driverPath+"geckodriver.exe");
			FirefoxProfile ffprofile = new FirefoxProfile();
			
			FirefoxOptions option = new FirefoxOptions();
			option.setProfile(ExportCsv.csvExportFireFox());
			driver = new FirefoxDriver(option);
			break;
		}
		
		//ChromeOptions options = new ChromeOptions();
		//options.addArguments("--disable-notifications");
		//System.setProperty("webdriver.chrome.driver", driverPath+"chromedriver.exe");
		
		//driver = new ChromeDriver(ExportCsv.csvExport());
		
		String URL = pv.readProperties("config.properties", "URL");
		try{
			driver.navigate().to(URL);
			driver.manage().window().maximize();
			loginToFacebook();
			navigateToManageAdverts();
			selectAdvertAccount();
			processCSV();
			driver.quit();
		}catch(Exception e){
			System.out.println(e.getMessage());
			driver.quit();
		}
	}

}
