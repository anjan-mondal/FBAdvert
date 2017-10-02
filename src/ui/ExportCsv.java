package ui;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import config.PropertiesValue;

public class ExportCsv {

	static String csvDownloadPath;
	
	/**
	 * Returns ChromeOptions  that can then be can be used in calling method. 
	 *
	 * <p>
	 * This method will set the Chrome options and Chrome preference and set the default download 
	 * path for chrome browser using chrome options. 
	 * @param  void  
	 * @return ChromeOptions
	 * 
	 */
	
	public static ChromeOptions csvExport() {
		PropertiesValue pv = new PropertiesValue();
	    csvDownloadPath = pv.readProperties("config.properties", "csvDownloadPath");
		// TODO Auto-generated method stub
		String downloadFilepath = createFloder();
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("--disable-notifications");
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		return options;

	}
	
	/**
	 * Returns string  which is actually a report path where the actual csv file will be stored.
	 * This method always return a string which is actually folder path. 
	 * <p>
	 *  
	 * @return String
	 * 
	 */
	public static String createFloder(){
		String filepath;
		
		//Fetching current date 
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String requiredDate = df.format(new Date()).toString();
		
		filepath = csvDownloadPath+File.separator+requiredDate;
		File file = new File(filepath);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }else{
        	 System.out.println("Directory Exists");
        }
		return filepath ;
	}

}
