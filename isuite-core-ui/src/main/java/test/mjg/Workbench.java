package test.mjg;

import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Calendar;
import java.util.Date;

import com.ibm.jsse2.Debug;

public class Workbench {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Workbench workbench = new Workbench();
	}

	public Workbench() {

		try {
			Workbench.addMilitaryTimeToDate(new Date(), "0000");	
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
	}
	
	public static Date addMilitaryTimeToDate(Date dt, String time) throws Exception {
		
		if(null == time || time.equals(""))
			return dt;
		
		System.out.println("param dt: " + dt.toString());
		System.out.println("param time: " + time);
		
		if(time.length() != 4) {
			//TODO: make sure this error message gets sent to the client
			throw new ServiceException("Invalid time format: expecting HHmm");
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.clear(Calendar.HOUR);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		//===============
//		System.out.println("before resetting hour, minutes: " + cal.getTime().toString());
//		cal.set(Calendar.HOUR, 0);
//		cal.set(Calendar.MINUTE, 0);
//		System.out.println("after resetting hour, minutes: " + cal.getTime().toString());
		//===============
		
		String sHour = time.substring(0, 2);
		String sMinutes = time.substring(2, 4);
		
		System.out.println("sHour: " + sHour);
		System.out.println("param sMinutes: " + sMinutes);
		
		int hour = Integer.parseInt(sHour);
		int minutes = Integer.parseInt(sMinutes);

		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minutes);
		
//		cal.clear();
		System.out.println("return value: " + cal.getTime().toString());
		System.out.println("");
		return cal.getTime();
	}
}
