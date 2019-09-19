package gov.nwcg.isuite.framework.util;

import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import com.ibm.jsse2.Debug;

public class DateUtil {
	public static final String MM_SLASH_DD_SLASH_YYYY_HH_MI_SS="MM/dd/yyyy HH:mm:ss";
	public static final String MM_SLASH_DD_SLASH_YYYY="MM/dd/yyyy";
	public static final String MM_SLASH_DD="MM/dd";
	public static final String DD="dd";
	public static final String MM="MM";
	public static final String MM_SLASH_DD_SLASH_YYYY_HH_MM="MM/dd/yyyy HH:mm";
	public static final String HH_MM="HH:mm";
	public static final String YYYYMMDD_HH_MI_SS="yyyy-MM-dd HH:mm:ss";
	public static final String YYYYMMDD_T_HH_MI_SS="yyyy-MM-dd'T'HH:mm:ss";
	public static final String YYYYMMDD_T_HH_MI_SS_z="yyyy-MM-dd'T'HH:mm:ssz";
	public static final String YYYYMMDD="yyyy-MM-dd ";
	public static final String YYYY_SLASH_MM_SLASH_DD="yyyy/MM/dd";
	public static final String YYYY="yyyy";
	

	public final static long SECOND_MILLIS = 1000;
	public final static long MINUTE_MILLIS = SECOND_MILLIS*60;
	public final static long HOUR_MILLIS = MINUTE_MILLIS*60;
	public final static long DAY_MILLIS = HOUR_MILLIS*24;
	public final static long YEAR_MILLIS = DAY_MILLIS*365;	

	private static enum DaysOfWeekEnum {
		SUNDAY(1,"SUN")
		,MONDAY(2,"MON")
		,TUESDAY(3,"TUE")		
		,WEDNESDAY(4,"WED")
		,THURSDAY(5,"THU")
		,FRIDAY(6,"FRI")
		,SATURDAY(7,"SAT")
		;

		public int index;
		public String abbreviation="";
		
		DaysOfWeekEnum(int idx,String abbrev){
			index=idx;
			abbreviation=abbrev;
		}
		
		static DaysOfWeekEnum getByIndex(int idx){
			for(DaysOfWeekEnum dow : DaysOfWeekEnum.values()){
				if(dow.index==idx)
					return dow;
			}
			
			return null;
		}
	}	
	
	
	public static Boolean hasValue(Date dt){
		return (null != dt ? true : false);
	}
	
	public static String toDateStringDash(Date dt){
		String strDate="";
		strDate=String.valueOf(DateUtil.getMonthAsInt(dt))+"-";
		strDate=strDate+String.valueOf(DateUtil.getDayAsInt(dt))+"-";
		strDate=strDate+String.valueOf(DateUtil.getYearAsInt(dt));
		
		return strDate;
	}
	
	public static String toTime(Date dt) {
		String time="";

		try{
			if(null!=dt){
				Calendar cal = Calendar.getInstance();
				cal.setTime(dt);

				time=cal.get(Calendar.HOUR) + ":" +
				cal.get(Calendar.MINUTE);
			}
		}catch(Exception e){
			throw new RuntimeException("Unable to return time from date ["+e.getMessage()+"]");
		}

		return time;
	}

	public static String toMilitaryTime(Date dt) {

		String time = "";

		Format formatter = new SimpleDateFormat("HHmm");
		time = formatter.format(dt);
		if(StringUtility.hasValue(time)){
			if(time.length()==3)
				time=time+"0";
		}
		return time;
	}

	public static String toMilitaryTimeColon(Date dt) {

		String time = "";

		Format formatter = new SimpleDateFormat("HHmm");
		time = formatter.format(dt);
		if(StringUtility.hasValue(time)){
			if(time.length()==3)
				time=time+"0";
			
			String cTime = time.substring(0,2) + ":" + time.substring(2,4);
			time=cTime;
		}
		
		return time;
	}
	
	public static Integer getYearAsInteger(Date dt){
		if(null != dt){
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			return new Integer(cal.get(Calendar.YEAR));
		}else{
			return 0;
		}
	}

	public static Boolean isSameDate(Date dt1, Date dt2) {
		/*
		System.out.println(DateUtil.getMonthAsInt(dt1));
		System.out.println(DateUtil.getMonthAsInt(dt2));
		
		System.out.println(DateUtil.getDayAsInt(dt1));
		System.out.println(DateUtil.getDayAsInt(dt2));
		
		System.out.println(DateUtil.getYearAsInt(dt1));
		System.out.println(DateUtil.getYearAsInt(dt2));
		*/
		if(null != dt1 && null != dt2){
			if( (DateUtil.getMonthAsInt(dt1)!=DateUtil.getMonthAsInt(dt2))
					||
				(DateUtil.getDayAsInt(dt1)!=DateUtil.getDayAsInt(dt2))				
				    ||
			    (DateUtil.getYearAsInt(dt1)!=DateUtil.getYearAsInt(dt2))){
				return false;
			}else
				return true;
		}else if(null==dt1 && null != dt2){
			return true;
		}else if(null != dt1 && null == dt2){
			return true;
		}else
			return false;
		
	}
	
	public static Boolean isMilitaryTime(String time) {
		
		int iTime;
		
		if(!StringUtility.hasValue(time)) return Boolean.FALSE;
		if(time.length() != 4) return Boolean.FALSE;
		try {
			iTime = Integer.parseInt(time);
		} catch (Exception e) {
			return Boolean.FALSE;
		}
		
		if(iTime == 2400) return Boolean.TRUE;
		if(iTime > 2400) return Boolean.FALSE;
		
		int hours = Integer.parseInt(time.substring(0, 1));
		Boolean isValidHour = ((hours >= 0) && (hours <= 23));
		if(!isValidHour) return Boolean.FALSE;
		
		int minutes = Integer.parseInt(time.substring(2));
		Boolean isValidMinutes = ((minutes >= 0) && (minutes <=59));
		if(!isValidMinutes) return Boolean.FALSE;
		
		return Boolean.TRUE;
	}
	
	public static int getMonthAsInt(Date dt){
		if(null != dt){
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			return new Integer(cal.get(Calendar.MONTH)).intValue();
		}else{
			return 0;
		}
	}

	public static String getMonthAsString(Date dt){
		if(null != dt){
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			int val=new Integer(cal.get(Calendar.MONTH)).intValue();
			String month=String.valueOf(val);
			if(month.length()==1)
				month="0"+month;
			return month;
		}else{
			return "";
		}
	}

	public static int getYearAsInt(Date dt){
		if(null != dt){
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			return new Integer(cal.get(Calendar.YEAR)).intValue();
		}else{
			return 0;
		}
	}

	public static String getYearAsString(Date dt){
		if(null != dt){
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			int val=new Integer(cal.get(Calendar.YEAR)).intValue();
			String year=String.valueOf(val);
			return year;
		}else{
			return "";
		}
	}

	public static int getDayAsInt(Date dt){
		if(null != dt){
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			return new Integer(cal.get(Calendar.DAY_OF_MONTH)).intValue();
		}else{
			return 0;
		}
	}
	
	public static String getDayAsString(Date dt){
		if(null != dt){
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			int val=new Integer(cal.get(Calendar.DAY_OF_MONTH)).intValue();
			String day=String.valueOf(val);
			if(day.length()==1)
				day="0"+day;
			return day;
		}else{
			return "";
		}
	}

	public static int getHourAsInt(Date dt){
		if(null != dt){
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			return new Integer(cal.get(Calendar.HOUR_OF_DAY)).intValue();
		}else{
			return 0;
		}
	}

	public static int getHourOffset(int hr1, Date dt2){
		int offset=0;
		
		int sysHour=getHourAsInt(dt2);
		
		if(hr1==sysHour)
			offset=0;
		else{
		}
		return 2;
	}
	
	public static String applyHourOffset(String sourceTime, int offset){
		
		return "";
	}
	
	/**
	 * Set the createdDate timestamp to 00:00:00
	 * @param filter {@link Date}
	 */
	public static Date setTimeStampToZero(Date date) {
		Date createdDate = date;
		if(createdDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			String stringDate = sdf.format(createdDate);
			String[] stringDateArray = stringDate.split("/");
			Integer month = Integer.valueOf(stringDateArray[0]);
			Integer day = Integer.valueOf(stringDateArray[1]);
			Integer year = Integer.valueOf(stringDateArray[2]);

			Calendar gCal = new GregorianCalendar(year, month - 1, day, 0, 0, 0);
			createdDate = new Date();
			createdDate = gCal.getTime();
		}
		return createdDate;
	}

	public static Date getDateZeroTimeStamp(String date) throws Exception{
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		Date dt = df.parse(date);

		return getDateZeroTimeStamp(dt);
	}

	/**
	 * Returns a java.util.Date with timestamp set to 00:00:00
	 * 
	 * @param filter {@link Date}
	 */
	public static Date getDateZeroTimeStamp(Date date){
		Date createdDate = date;
		if(createdDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			String stringDate = sdf.format(createdDate);
			String[] stringDateArray = stringDate.split("/");
			Integer month = Integer.valueOf(stringDateArray[0]);
			Integer day = Integer.valueOf(stringDateArray[1]);
			Integer year = Integer.valueOf(stringDateArray[2]);

			Calendar gCal = new GregorianCalendar(year, month - 1, day, 0, 0, 0);
			createdDate = new Date();
			createdDate = gCal.getTime();
		}
		return createdDate;
	}

	public static Date addDaysToDate(Date dt, int numDays) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.add(Calendar.DATE, numDays);
		return cal.getTime();
	}

	public static Date subtractDaysFromDate(Date dt, int numDays) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.add(Calendar.DATE, -numDays);
		return cal.getTime();
	}

	public static Date addHoursFromDate(Date dt, int hours) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int hourOfDay=cal.get(Calendar.HOUR_OF_DAY);
		if(hourOfDay+hours < 0){
			//crossing midnight
		}else{
			hourOfDay=hourOfDay+hours;
			cal.set(Calendar.HOUR, hourOfDay);
		}
		Date dt2=cal.getTime();
		//System.out.println(DateUtil.toDateString(dt2,DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM));
		return cal.getTime();
	}
	
	public static long diffDays(Date curDate, Date futureDate) throws Exception {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(curDate);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(futureDate);

		long daysBetween = 0;  
		while (c1.before(c2)) {  
			c1.add(Calendar.DAY_OF_MONTH, 1);  
			daysBetween++;  
		}  
		return daysBetween;  	
	}

	public static long diffHours(Date dt1, Date dt2) throws Exception {
		long duration=dt1.getTime() - dt2.getTime();
		
		long hours=TimeUnit.MILLISECONDS.toHours(duration);
		
		return hours;
	}
	
	public static String getHoursDifference(Date dt1, Date dt2){
		long duration=dt1.getTime() - dt2.getTime();
		
		long hours=TimeUnit.MILLISECONDS.toHours(duration);

		return String.valueOf(hours);
	}
	
	public static String toMilitaryTime(String v){
		String s=v;
		if(v.indexOf(":")>0)
			s=v.replaceAll(":", "");
		if(s.length()==3)
			s="0"+s;
		return s;
	}
	
	public static Date addTimeToDate(Date dt, String time) throws Exception {
		if(null == time || time.equals(""))
			return dt;
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int hour=0;
		int minutes=0;
		int i=0;
		StringTokenizer st = new StringTokenizer(time,":");
		while(st.hasMoreTokens()){
			String val=(String)st.nextToken();
			try{
				if(null != val && !val.equals("")){
					if(i==0)
						hour=Integer.parseInt(val);
					else
						minutes=Integer.parseInt(val);
				}
			}catch(Exception ee){/*smother*/}
			i++;
		}
		cal.set(Calendar.HOUR,hour);
		cal.set(Calendar.MINUTE, minutes);

		return cal.getTime();
	}

	public static Date addMilitaryTimeToDate(Date dt, String time) throws Exception {

		if(null == time || time.equals(""))
			return dt;

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

		String sHour = time.substring(0, 2);
		String sMinutes = time.substring(2, 4);

		int hour = Integer.parseInt(sHour);
		int minutes = Integer.parseInt(sMinutes);

		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minutes);

		return cal.getTime();
	}

	public static String toDateString(Date dt) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		String strDate = df.format(dt);

		return strDate;
	}

	public static String toDateString(Date dt, String format) {
		if(null==dt)
			return "";
		
		DateFormat df = new SimpleDateFormat(format);

		String strDate = df.format(dt);

		return strDate;
	}

	public static Date toMilitaryDate(Date dt, String militaryTime) throws Exception{

		if(null != dt && StringUtility.hasValue(militaryTime)){
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);


			int hour=0;
			int minute=0;

			StringTokenizer tokens = new StringTokenizer(militaryTime,":");
			int cnt=0;
			while(tokens.hasMoreTokens()){
				String val = (String)tokens.nextToken();
				if(StringUtility.hasValue(val)){
					if(cnt==0)
						hour=Integer.parseInt(val);
					else
						minute=Integer.parseInt(val);
				}
				cnt++;
			}

			cal.set(Calendar.HOUR_OF_DAY, hour);
			cal.set(Calendar.MINUTE, minute);

			return cal.getTime();
		}

		return null;
	}

	public static Date addDays(Date dt, int daysToAdd){
		if(null != dt){
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			cal.add(Calendar.DATE, daysToAdd);
			return cal.getTime();
		}

		return dt;
	}

	public static Date toDate(String val, String format) throws Exception{

		DateFormat df = new SimpleDateFormat(format);
		return (Date)df.parse(val);
	}

	public static int getMinutesDiff( Date earlierDate, Date laterDate ){
		if( earlierDate == null || laterDate == null ) return 0;

		return (int)((laterDate.getTime()/MINUTE_MILLIS) - (earlierDate.getTime()/MINUTE_MILLIS));
	}

	public static int getDaysSince(Date dt) throws Exception {

		int days = 0;


		return days;
	}

	
	public static String formatMilitaryTime(String time){
		String rtn="";
		
		if(time.length()==4)
			rtn=time.substring(0, 2)+":"+time.substring(2,4);
		if(time.length()==3)
			rtn="0"+time.substring(0, 1)+":"+time.substring(1,3);
		return rtn;
	}

	public static Date makeEndOfDay(Date d) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(d);
    cal.set(Calendar.HOUR, 23);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.SECOND, 59);
    cal.set(Calendar.AM_PM, 0);
    return cal.getTime();
  }

	
	public static void main(String[] args ) throws Exception{
		Date dt1=DateUtil.toDate("02/09/2016", DateUtil.MM_SLASH_DD_SLASH_YYYY);
		dt1=DateUtil.addMilitaryTimeToDate(dt1, "1230");
		Date dt2=DateUtil.toDate("02/09/2016", DateUtil.MM_SLASH_DD_SLASH_YYYY);
		dt2=DateUtil.addMilitaryTimeToDate(dt2, "1230");
		String d=DateUtil.getHoursDifference(dt2, dt1);
		System.out.println(d);
		
		String testTime="2359";
		System.out.println(DateUtil.formatMilitaryTime(testTime));
		String testTime2="359";
		System.out.println(DateUtil.formatMilitaryTime(testTime2));
		
		String testDate="2011-03-11T10:25:00";

		try{
			Date testDt = DateUtil.toDate(testDate, DateUtil.YYYYMMDD_T_HH_MI_SS);

			System.out.println(testDt);

		}catch(Exception e){
			System.out.println(e.getMessage());
		}

		String mtime="12:30";
		Date dt = Calendar.getInstance().getTime();
		try{
			Date newDt = DateUtil.toMilitaryDate(dt, mtime);
			System.out.println(newDt);

			Date dte = Calendar.getInstance().getTime();
			System.out.println(DateUtil.addDaysToDate(dte, 3));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}


	}


	public static Date toUTCDate(Date dt) {
		Date rtn = null;
		
		try{
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			
			long ms = dt.getTime();
			int offset = cal.getTimeZone().getOffset(ms);
			
			Calendar cal2 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			cal2.setTime(dt);
			cal2.add(Calendar.MILLISECOND, offset);
			
			rtn=cal2.getTime();
			
		}catch(Exception e){
			
		}
		
		return rtn;
	}

	public static String getDayOfWeekName(Date dt){
		String rtn="";
		
		if(hasValue(dt)){
			Calendar c = Calendar.getInstance();
			c.setTime(dt);
			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			DaysOfWeekEnum dowEnum = DaysOfWeekEnum.getByIndex(dayOfWeek);
			if(null != dowEnum)
				return dowEnum.name();
			else
				return "";
		}else
			return "";
	}
	
	public static String getDayOfWeekAbbrev(Date dt){
		String rtn="";
		
		if(hasValue(dt)){
			Calendar c = Calendar.getInstance();
			c.setTime(dt);
			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			DaysOfWeekEnum dowEnum = DaysOfWeekEnum.getByIndex(dayOfWeek);
			if(null != dowEnum)
				return dowEnum.abbreviation;
			else
				return "";
		}else
			return "";
	}
	
	/** 
	 * Public static method that returns the number of days between two java.util.Date objects. 
	 * Returns 0 if the dates are the same or if the startDate is after the endDate.
	 * @param startDate The starting date.
	 * @param endDate The ending date.
	 * @return Number of days between the start and end dates if the start date is before the end date; else 0.
	 */
	public static int daysBetween(Date startDate, Date endDate) {
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        startCal.setTime(startDate);
        endCal.setTime(endDate);
		
		Calendar currentCal = (Calendar) startCal.clone();
		int daysBetween = 0;
        while (currentCal.before(endCal)) {
        	currentCal.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }	
}
