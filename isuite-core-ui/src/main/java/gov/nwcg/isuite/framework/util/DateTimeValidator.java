package gov.nwcg.isuite.framework.util;

import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.ErrorEnum;

import java.util.Calendar;
import java.util.Date;

public class DateTimeValidator {

	public static ErrorObject validateTimeField(String fieldName, String fieldValue) {
		ErrorObject error = null;
		
		if(StringUtility.hasValue(fieldValue)){
			if(!DateUtil.isMilitaryTime(fieldValue)){
				error = new ErrorObject("error.invalidTimeFormat",fieldName);
			}
		}
		
		return null;
	}

	public static ErrorObject validateTimeField2(String fieldName, String fieldValue,Boolean required) {
		ErrorObject error = null;

		if( (required) && (!StringUtility.hasValue(fieldValue)) ){
			error = new ErrorObject("text.requiredFieldError",fieldName);
		}
		
		if(StringUtility.hasValue(fieldValue)){
			if(!DateUtil.isMilitaryTime(fieldValue)){
				error = new ErrorObject("error.invalidTimeFormat",fieldName);
			}
		}
		
		return error;
	}

	public static ErrorObject validateDate1NotBeforeDate2(Date dt1, Date dt2, String field1, String field2) {
		ErrorObject error = null;

		if(DateUtil.hasValue(dt1) && DateUtil.hasValue(dt2)){
			Calendar cal1 = Calendar.getInstance();
			String sdt1=DateUtil.toDateString(dt1, DateUtil.MM_SLASH_DD_SLASH_YYYY);
			try{
				cal1.setTime(DateUtil.toDate(sdt1, DateUtil.MM_SLASH_DD_SLASH_YYYY));
			}catch(Exception e){}
			cal1.set(Calendar.HOUR, 1);
			cal1.set(Calendar.MINUTE, 1);
			cal1.set(Calendar.SECOND, 1);
			
			Calendar cal2 = Calendar.getInstance();
			String sdt2=DateUtil.toDateString(dt2, DateUtil.MM_SLASH_DD_SLASH_YYYY);
			try{
				cal2.setTime(DateUtil.toDate(sdt2, DateUtil.MM_SLASH_DD_SLASH_YYYY));
			}catch(Exception e){}
			cal2.set(Calendar.HOUR, 1);
			cal2.set(Calendar.MINUTE, 1);
			cal2.set(Calendar.SECOND, 1);
			
			Date dte1=cal1.getTime();
			Date dte2=cal2.getTime();
			
			if(dte1.before(dte2)){
				String msg = "The " + field1 + " cannot be before the " + field2 + ".";
				error = new ErrorObject("info.generic",msg);
				return error;
			}
		}
		return error;
	}
	
	public static ErrorObject validateDate1NotAfterDate2(Date dt1, Date dt2, String field1, String field2) {
		ErrorObject error = null;
	
		if(DateUtil.hasValue(dt1) && DateUtil.hasValue(dt2)){
			if(dt1.after(dt2)){
				String msg = "The " + field1 + " cannot be after the " + field2 + ".";
				error = new ErrorObject("info.generic",msg);
				return error;
			}
		}
		return error;
	}

	public static void main(String args[]) {
		DateTimeValidator.validateTimeField("time", "1248");
	}
}
