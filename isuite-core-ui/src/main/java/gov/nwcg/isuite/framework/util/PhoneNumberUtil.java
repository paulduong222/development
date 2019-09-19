package gov.nwcg.isuite.framework.util;

public class PhoneNumberUtil {

	
	/**
	 * Remove any non-digits from phone number
	 * 
	 * @param value
	 * @return
	 */
	public static String digitOnlyPhoneNumberFormat(String value) {
		value = value.replaceAll("\\D+", "");
		return value;
	}
		
	public static String formatNumber(String value) {
		if(StringUtility.hasValue(value)){
			if(value.length()==10){
				return "(" + value.substring( 0,3 ) + ") " + value.substring( 3,6 ) + "-" + value.substring( 6,10 );
			}else if(value.length()==7){
				return value.substring( 0,3 ) + "-" + value.substring( 3,6 );
			}else
				return value;
		}else{
			return "";
		}
	}
}
