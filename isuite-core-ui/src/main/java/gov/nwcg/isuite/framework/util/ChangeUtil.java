package gov.nwcg.isuite.framework.util;

import java.math.BigDecimal;

public class ChangeUtil {

	public static Boolean isBigDecimalChanged(BigDecimal val1, BigDecimal val2){
		
		if(null==val1 && null!=val2)
			return true;
		
		if(null != val1 && null==val2)
			return true;
		
		if(null != val1 && null != val2){
			if(val1.doubleValue()!=val2.doubleValue())
				return true;
		}
		
		return false;
	}
	
	public static Boolean isIntegerChanged(Integer val1, Integer val2){
		
		if(null==val1 && null!=val2)
			return true;
		
		if(null != val1 && null==val2)
			return true;
		
		if(null != val1 && null != val2){
			if(val1.intValue()!=val2.intValue())
				return true;
		}
		
		return false;
	}

	public static Boolean isStringChanged(String val1, String val2){
		
		if(!StringUtility.hasValue(val1) && StringUtility.hasValue(val2))
			return true;
		
		if(StringUtility.hasValue(val1) && !StringUtility.hasValue(val2))
			return true;
		
		if(StringUtility.hasValue(val1) && StringUtility.hasValue(val2)){
			
			if(!val1.equals(val2))
				return true;
		}
		
		return false;
	}

	public static Boolean isLongChanged(Long val1, Long val2){
		if(!LongUtility.hasValue(val1) && LongUtility.hasValue(val2))
			return true;
		
		if(LongUtility.hasValue(val1) && !LongUtility.hasValue(val2))
			return true;
		
		if(LongUtility.hasValue(val1) && LongUtility.hasValue(val2)){
			if(val1.compareTo(val2)!=0)
				return true;
		}
		
		return false;
	}
	
}
