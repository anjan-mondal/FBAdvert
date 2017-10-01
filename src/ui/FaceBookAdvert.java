package ui;

import java.io.File;
import config.PropertiesValue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FaceBookAdvert {
	
	public static WebDriver driver;

	public static void main(String[] args) {
		PropertiesValue pv = new PropertiesValue();
		String driverPath = System.getProperty("user.dir");
		driverPath+=File.separator+"drivers"+File.separator;
		System.out.println(driverPath);
		
		System.setProperty("webdriver.chrome.driver", driverPath+"chromedriver.exe");
		driver = new ChromeDriver();
		
		String URL = pv.readProperties("config.properties", "URL");
		
		driver.navigate().to(URL);
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class=\"privacy__info\"]")));
		String privacyinfo = element.getText();
		
		System.out.println(privacyinfo);
		
		driver.quit();
	}

}
