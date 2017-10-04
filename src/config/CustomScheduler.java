package config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ui.FaceBookAdvert;

public class CustomScheduler {

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean timeFlag =false;
		PropertiesValue pv = new PropertiesValue();
		int SchedulerCount = Integer.parseInt(pv.readProperties("config.properties", "SchedulerCount").trim());
		ReadExcelProperty rp =new ReadExcelProperty();
		for(int i=1;i<=SchedulerCount;i++)
		{		
			String TimeFromExcel = rp.getProperty("TIME"+i,"Scheduler");
			if(TimeFromExcel!=null){
				TimeFromExcel =TimeFromExcel.trim();
				timeFlag =true;
				SimpleDateFormat time_formatter = new SimpleDateFormat("HH:mm");
				String current_time_str = time_formatter.format(System.currentTimeMillis());
				System.out.println("current_time_str:" + current_time_str);
				if(current_time_str.equalsIgnoreCase(TimeFromExcel)){
					try{
						FaceBookAdvert.main(args);
						
						Thread.sleep(6000);
						break;
					}catch(Exception e){
						System.out.println(e);
					}
				}
			}	
		}
		if(!timeFlag){
			System.out.println("Time property not set.Please check the rule");
		}

	}
	

}
