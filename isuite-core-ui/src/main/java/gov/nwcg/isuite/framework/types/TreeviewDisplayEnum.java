package gov.nwcg.isuite.framework.types;

public enum TreeviewDisplayEnum {
	SHOW_ALL
	,BY_DATE
	,NUMBER_OF_DAYS;
	
	public static Short toShortValue(String val) {
		Short treeviewDisplayEnum = 0;
		
		if(null != val) {
			treeviewDisplayEnum = (short)(TreeviewDisplayEnum.valueOf(val)).ordinal();
		}
		
		return treeviewDisplayEnum;
	}
	
	public static String toStringValue(Short val) {
		String treeviewDisplayEnum = "";
		
		if(null != val) {
			treeviewDisplayEnum = TreeviewDisplayEnum.values()[val].name();
		}
		
		return treeviewDisplayEnum;
	}

}
