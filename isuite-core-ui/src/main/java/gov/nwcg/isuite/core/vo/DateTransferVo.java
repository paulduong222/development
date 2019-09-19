package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Date;

public class DateTransferVo  implements Cloneable{
	public String dateString;
	public String timeString;
	
	public DateTransferVo(){
		
	}

	public static DateTransferVo getInstance(Date dt) {
		DateTransferVo vo = new DateTransferVo();
		
		populateDate(vo,dt);
		
		return vo;
	}
	
	public static void populateDate(DateTransferVo vo,Date dt) {
		
		if(DateUtil.hasValue(dt)){
			vo.setDateString(DateUtil.toDateString(dt, DateUtil.MM_SLASH_DD_SLASH_YYYY));
			vo.setTimeString(DateUtil.toMilitaryTime(dt));
		}
	}
	
	public static Boolean hasDateString(DateTransferVo vo){
		if(null != vo && StringUtility.hasValue(vo.getDateString()))
			return true;
		else
			return false;
	}
	
	public static Boolean hasTimeString(DateTransferVo vo){
		if(null != vo && StringUtility.hasValue(vo.getTimeString()))
			return true;
		else
			return false;
	}

	public static Date getTransferDate(DateTransferVo vo) throws Exception{
		Date rtnDate=null;
		
		if(null != vo && StringUtility.hasValue(vo.getDateString())){
			rtnDate=DateUtil.toDate(vo.getDateString(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
			if(StringUtility.hasValue(vo.getTimeString())){
				rtnDate=DateUtil.addMilitaryTimeToDate(rtnDate, vo.getTimeString());
			}
		}
		
		return rtnDate;
	}

	public static Date getDate(DateTransferVo vo) {
		Date rtnDate=null;

		try{
			if(null != vo && StringUtility.hasValue(vo.getDateString()))
				rtnDate=DateUtil.toDate(vo.getDateString(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
		}catch(Exception e){
			
		}
		
		return rtnDate;
	}

	public static String getTime(DateTransferVo vo) {
		Date rtnDate=null;
		String rtnTime="";
		
		try{
			if(null != vo && StringUtility.hasValue(vo.getDateString())){
				rtnDate=DateUtil.toDate(vo.getDateString(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
				if(null != vo && StringUtility.hasValue(vo.getTimeString()))
					rtnTime=DateUtil.toMilitaryTime(rtnDate);
			}
		}catch(Exception e){
			
		}
		
		return rtnTime;
	}
	
	/**
	 * @return the dateString
	 */
	public String getDateString() {
		return dateString;
	}

	/**
	 * @param dateString the dateString to set
	 */
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	/**
	 * @return the timeString
	 */
	public String getTimeString() {
		return timeString;
	}

	/**
	 * @param timeString the timeString to set
	 */
	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}
}
