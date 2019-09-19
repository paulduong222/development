package gov.nwcg.isuite.framework.util;

public class TimeUtility {

	public static String formatMilitaryTime(String time){
		String rtn="";
		
		if(time.length()==4)
			rtn=time.substring(0, 2)+":"+time.substring(2,4);
		if(time.length()==3)
			rtn="0"+time.substring(0, 1)+":"+time.substring(1,3);
		return rtn;
	}
	
	
	public static java.math.BigDecimal toTimeQuantity(long hours, long minutes) throws Exception {
		String val=String.valueOf(hours);
		
		/*
		 * Evaluate the minutes value
		 */
		if(minutes == 59 )
			val=String.valueOf((hours+1));
//		else if (minutes == 15)
		else if ((minutes > 0) && (minutes <= 15))
			val=String.valueOf(hours + ".25");
		else if (minutes == 30 || minutes == 29)
			val=String.valueOf(hours + ".50");
		else if (minutes == 45 || minutes==44)
			val=String.valueOf(hours + ".75");
		
		return TypeConverter.convertToBigDecimal(val);
	}
	
}
