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
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FaceBookAdvert {
	
	public static WebDriver driver;
	static PropertiesValue pv = new PropertiesValue();
	static ReadExcelProperty rp =new ReadExcelProperty();
	static String computername = null;
	
	public static void loginToFacebook()
	{
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
		WebDriverWait wait = new WebDriverWait(driver, 60);
		String usermenu = pv.readProperties("xpath.properties", "FB_USER_MENU");
		String manageadvertslink = pv.readProperties("xpath.properties", "MANAGE_ADVERTS");
		
		WebElement usermenu_element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(usermenu)));
		usermenu_element.click();
		
		WebElement manageadvertslink_element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(manageadvertslink)));
		manageadvertslink_element.click();
	}
	
	public static void selectAdvertAccount()
	{
		WebDriverWait wait = new WebDriverWait(driver, 60);
		String advertAccountName = pv.readProperties("xpath.properties", "MANAGE_ADVERT_ACC_Name");
		String advertAccountNameXPAth = advertAccountName.replaceAll("#####", advertAccountName);
		WebElement advertAccountName_element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(advertAccountNameXPAth)));
		advertAccountName_element.click();	
	}
	
	public static void main(String[] args) {
		try {
			computername = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String driverPath = System.getProperty("user.dir");
		driverPath+=File.separator+"drivers"+File.separator;
		System.out.println(driverPath);
		//ChromeOptions options = new ChromeOptions();
		//options.addArguments("--disable-notifications");
		System.setProperty("webdriver.chrome.driver", driverPath+"chromedriver.exe");
		
		driver = new ChromeDriver(ExportCsv.csvExport());
		
		String URL = pv.readProperties("config.properties", "URL");
		
		driver.navigate().to(URL);
		driver.manage().window().maximize();
		loginToFacebook();
		navigateToManageAdverts();
		selectAdvertAccount();
		driver.quit();
	}

}
