package gov.nwcg.isuite.framework.util;

public class BooleanUtility {

	public static Boolean hasValue(Boolean val){
		
		if( (null != val) )
			return true;
		
		return false;
	}
	
	public static Boolean getValue(Boolean val){
		return (null==val ? false : val);
	}

	public static Boolean isTrue(Boolean val){
		if(hasValue(val)){
			return (val==true);
		}else
			return false;
	}

	public static Boolean isFalse(Boolean val){
		if(hasValue(val)){
			return (val==false);
		}else
			return true;
	}


}
