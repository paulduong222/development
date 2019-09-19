package gov.nwcg.isuite.framework.core.persistence.datefilter;


public class CustomDateFilterFactory {

	/*
	 * Factory method to return the appropriate customdatefilter.
	 * 
	 */
	public static ICustomDateFilter getCustomDateFilterInstance(String filterFormat) throws Exception {
		
		ICustomDateFilter dateFilter = null;

		if(null != filterFormat && filterFormat.length()>0){
			
			CustomDateFilterEnum dfEnum = null;
			
			// need to find the enum that matches the pattern
			// may need to use regex to find the enum based on pattern matching
			for(CustomDateFilterEnum cdfEnum : CustomDateFilterEnum.values()){
				if(cdfEnum.getCrypticDateFilterCode().equals(filterFormat)){
					dfEnum = cdfEnum;
				}
			}
			
			if(null != dfEnum){
				// create new instantiated instance of the customdatefilter
				Class<?> filterClass = Class.forName(dfEnum.getFilterClass());
				dateFilter = (ICustomDateFilter)filterClass.newInstance();
			} else {
				Class<?> filterClass = 
					Class.forName(CustomDateFilterEnum.CustomDateFilterTypeDefault.getFilterClass());
				dateFilter = (ICustomDateFilter)filterClass.newInstance();
			}
			
		}
		
		return dateFilter;
	}
	
}
