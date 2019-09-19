package gov.nwcg.isuite.framework.util;

import gov.nwcg.isuite.core.vo.UserSessionVo;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class EISuiteCalendar {
	private static HashMap<String,Date> dbDates = new HashMap<String,Date>();
	
	private static Date calendarDate=Calendar.getInstance().getTime();
	public static Boolean isTrainingMode=false;
	public static Boolean hasTrainingDate=false;
	public static String runMode="SITE";
	
	public static void setCalendarDate(Date dt){
		calendarDate=dt;
	}
	
	public static Date getSystemDateForClient(UserSessionVo usvo){
		return null;
	}
	
	public static Date getCalendarDate(String dbName) {
		if(EISuiteCalendar.isTrainingMode==false){
			return Calendar.getInstance().getTime();
		}else{
			return getDbCalendarDate(dbName);
			/*
			if(null != calendarDate)
				return calendarDate;
			else
				return Calendar.getInstance().getTime();
			*/
		}
	}
	
	public static String getCalendarDateAsString(String dbName) {
		if(EISuiteCalendar.isTrainingMode==false){
			return DateUtil.toDateString(Calendar.getInstance().getTime(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
		}else{
			return DateUtil.toDateString(getDbCalendarDate(dbName), DateUtil.MM_SLASH_DD_SLASH_YYYY);
			/*
			if(null != calendarDate)
				return DateUtil.toDateString(calendarDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
			else
				return DateUtil.toDateString(Calendar.getInstance().getTime(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
			*/
		}
	}
	
	public static String getCalendarDateAsFullString(String dbName) {
		if(EISuiteCalendar.isTrainingMode==false){
			return DateUtil.toDateString(Calendar.getInstance().getTime(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS);
		}else{
			return DateUtil.toDateString(getDbCalendarDate(dbName), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS);
			/*
			if(null != calendarDate)
				return DateUtil.toDateString(calendarDate, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS);
			else
				return DateUtil.toDateString(Calendar.getInstance().getTime(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS);
			*/
		}
	}

	public static void setDbCalendarDate(String dbName, Date dt){
		if(StringUtility.hasValue(dbName)){
			dbDates.put(dbName, dt);
		}
	}
	
	public static Date getDbCalendarDate(String dbName){
		if(StringUtility.hasValue(dbName)){
			if(dbDates.containsKey(dbName)){
				Date dt=dbDates.get(dbName);
				if(null == dt)
					return Calendar.getInstance().getTime();
				else
					return dt;
			}else{
				if(dbDates.containsKey("isuite_site_master")){
					Date dt=dbDates.get("isuite_site_master");
					dbDates.put(dbName, dt);
					return dt;
				}
			}
		}else{
			if(runMode=="ENTERPRISE" && null != calendarDate)
				return calendarDate;
		}
		
		return Calendar.getInstance().getTime();
	}
	
	public static Boolean hasDbCalendarDate(String dbName){
		if(StringUtility.hasValue(dbName)){
			if(dbDates.containsKey(dbName)){
				return true;
			}
		}
		return false;
	}
}
