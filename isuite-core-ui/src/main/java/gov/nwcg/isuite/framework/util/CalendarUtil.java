package gov.nwcg.isuite.framework.util;

import java.util.Calendar;

public class CalendarUtil {

	public static double getTimeBetweenInQuarterHours(Calendar cal1, Calendar cal2) {
		double totalTime = 0.0;
		
		if(cal1 == null || cal2 == null) 
			return totalTime;
				
		while(cal1.get(Calendar.MINUTE) > cal2.get(Calendar.MINUTE)) {
			cal1.add(Calendar.MINUTE, 15);
			totalTime += .25;
		}
		while(cal1.get(Calendar.MINUTE) < cal2.get(Calendar.MINUTE)) {
			cal1.add(Calendar.MINUTE, 15);
			if(cal1.get(Calendar.MINUTE) <= cal2.get(Calendar.MINUTE)  )
				totalTime += .25;
		}
		
		double hours = 0.0;
		
		while(cal1.before(cal2)) {
			cal1.add(Calendar.HOUR, 1);
			hours += 1;
		}
				
		totalTime += hours;
		
		return totalTime;
	}
}
