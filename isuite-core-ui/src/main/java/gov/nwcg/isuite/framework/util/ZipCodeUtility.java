package gov.nwcg.isuite.framework.util;

public class ZipCodeUtility {
	
	public static String formatZipCode(String zipCode) throws Exception {
			
		if(zipCode.length() == 9){
			zipCode = zipCode.substring(0, 5) + "-" + zipCode.substring(5);
		} 
		
		return zipCode;
		
	}

}
