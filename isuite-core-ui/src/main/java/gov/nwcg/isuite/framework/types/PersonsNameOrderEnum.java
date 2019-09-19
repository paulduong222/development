package gov.nwcg.isuite.framework.types;

public enum PersonsNameOrderEnum {
	LAST_NAME_FIRST_NAME
	,FIRST_NAME_LAST_NAME;
	
	public static Short toShortValue(String val) {
		Short personsNameOrder = 0;
		
		if(null != val) {
			personsNameOrder = (short)(PersonsNameOrderEnum.valueOf(val)).ordinal();
		}
		
		return personsNameOrder;
	}
	
	public static String toStringValue(Short val) {
		String personsNameOrder = "";
		
		if(null != val) {
			personsNameOrder = PersonsNameOrderEnum.values()[val].name();
		}
		
		return personsNameOrder;
		
	}
}
