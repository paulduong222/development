package gov.nwcg.isuite.framework.types;

import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.IntegerUtility;

public class ShortUtil {

	public static Boolean hasValue(Short val){
		
		if(null != val && val.intValue() > -1)
			return true;
		else
			return false;
	}

	public static Boolean hasValue(String val){
		
		if(null != val && val != "" && !val.equals("NaN"))
			return true;
		else
			return false;
	}

	public static Short toShort(Boolean val){
		if(BooleanUtility.isTrue(val))
			return Short.valueOf("1");
		else
			return Short.valueOf("0");
			
	}
	public static Boolean toBoolean(Short val){
		if(hasValue(val)){
			if(val==1)
				return true;
		}
		
		return false;
	}
	
	public static Integer toInteger(Short val){
		if(hasValue(val)){
			return Integer.valueOf(String.valueOf(val));
		}else
			return 0;
	}
	
	public static Short toShort(Integer val){
		if(IntegerUtility.hasValue(val))
			return Short.valueOf(String.valueOf(val));
		
		return Short.valueOf("0");
	}
}
