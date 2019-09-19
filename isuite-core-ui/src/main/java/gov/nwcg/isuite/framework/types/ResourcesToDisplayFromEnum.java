package gov.nwcg.isuite.framework.types;

public enum ResourcesToDisplayFromEnum {
	SELECTED_INCIDENT
	,ALL_INCIDENTS;
	
	public static Short toShortValue(String val) {
		Short resourcesToDisplayFrom = 0;
		
		if(null != val) {
			resourcesToDisplayFrom = (short)(ResourcesToDisplayFromEnum.valueOf(val)).ordinal();
		}
		
		return resourcesToDisplayFrom;
	}
	
	public static String toStringValue(Short val) {
		String resourcesToDisplayFrom = "";
		
		if(null != val) {
			resourcesToDisplayFrom = ResourcesToDisplayFromEnum.values()[val].name();
		}
		
		return resourcesToDisplayFrom;
	}

}
