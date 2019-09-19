package gov.nwcg.isuite.framework.util;

import java.math.BigDecimal;

public class BigDecimalUtility {

	public static Boolean hasValue(BigDecimal val){
		if(null==val)
			return false;
		
		if(val.doubleValue() > 0.0)
			return true;
		else
			return false;
	}
}
