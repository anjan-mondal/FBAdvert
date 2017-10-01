package config;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelProperty {
	public File fileName;
	public String ExcelPath;
	// Read Excel from a specific  file path 
	public ReadExcelProperty(String ExcelPath){
		this.ExcelPath = ExcelPath;
	}
	// Read Excel from project Directory
	public ReadExcelProperty(){
		PropertiesValue pv = new PropertiesValue();
		String ExcelConfigFileName = pv.readProperties("config.properties", "ExcelConfigFileName");
		this.ExcelPath = ".\\"+ExcelConfigFileName;
	}

	
	
	public static void main(String[] args) {
		String computername = null;
		try {
			computername = InetAddress.getLocalHost().getHostName();
			System.out.println(computername);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ReadExcelProperty rp =new ReadExcelProperty("C:\\Users\\anjan\\Desktop\\newfile\\seleniumFramework\\bin\\testscripts\\datatables\\TC_01.xlsx");
		ReadExcelProperty rp =new ReadExcelProperty();
		String value1 = rp.getProperty("Numberofsessions",computername);
		String value2 = rp.getProperty("Email",computername);
		String value3 = rp.getProperty("Password",computername);
		String value4 = rp.getProperty("ReportType",computername);
		String value5 = rp.getProperty("AccountTimeZone",computername);
		String value6 = rp.getProperty("OS",computername);
		String value7 = rp.getProperty("FrequencyofDataGrab",computername);
		String value8 = rp.getProperty("Timeframeofreport",computername);
		String value9 = rp.getProperty("TimeofDataGrab",computername);
		String value10 = rp.getProperty("Browsertoopen",computername);
		String value11 = rp.getProperty("Numberofreports",computername);
		System.out.println("Number of sessions daily per machine:"+value1);
		System.out.println("Session login credentials Email:"+value2);
		System.out.println("Session login credentials password:"+value3);
		System.out.println("Number of reports daily"+value11);
		System.out.println("Account Time-Zone"+value5);
		System.out.println("OS:"+value6);
		System.out.println("Frequency of Data Grab:"+value7);
		System.out.println("Time-frame of report:"+value8);
		System.out.println("Time of Data Grab:"+value9);
		System.out.println("Browser to open:"+value10);
		
		
		
	}	
	
	
	
	/**
	 * Returns string  that can then be painted on the screen. 
	 *
	 * <p>
	 * This method always returns immediately, there is no matching key or sheet name available in the workbook
	 * it will return 'null'
	 * @param  Key 
	 * @param  Sheet name 
	 * @return Value
	 * 
	 */
	
	public String getProperty(String Key, String SheetName ) {
		
		FileInputStream fis;
		XSSFWorkbook myWorkBook;
		int numberOfSheet;
		String sheetName = null;
		XSSFSheet mySheet;
		boolean flagSheetName =false;
		int maxRowCount;
		String returnValue = null;
		try {
			// TODO Auto-generated method stub
			fileName = new File(ExcelPath);
			fis = new FileInputStream(fileName);

			// Finds the workbook instance for XLSX file

			myWorkBook = new XSSFWorkbook(fis);
			// Return number of sheet available in a workbook
			numberOfSheet = myWorkBook.getNumberOfSheets();
			// read property from a specific sheet
			for(int i=0;i<numberOfSheet;i++){
				sheetName = myWorkBook.getSheetName(i);
				if(sheetName.equalsIgnoreCase(SheetName)){
					flagSheetName =true;
					// need to write code in this area
					mySheet = myWorkBook.getSheetAt(i);
					// Store total rows available in a sheet
					maxRowCount = mySheet.getLastRowNum();
					//System.out.println("row count="+maxRowCount+1);
					for(Row row : mySheet) { 
						if(row.getCell(0).toString().equalsIgnoreCase(Key)){
							returnValue =row.getCell(1).toString();
					        //System.out.println(returnValue);
						}
					}  
					break;
				}
			}
			if(flagSheetName){
				//System.out.println(sheetName);
			}else{
				System.out.println("Sheet name not found. Check your input:" +SheetName);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		fis =null;
		myWorkBook =null;
		mySheet= null;
		return returnValue;
	}

}
