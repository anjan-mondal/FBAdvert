package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesValue {

	public String readProperties(String fileName, String value)
	{
		Properties prop = new Properties();
		InputStream input = null;

		String readvalue = "";

		try {
			input = new FileInputStream(fileName);
			prop.load(input);
			readvalue = prop.getProperty(value);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return readvalue;
	}

}
