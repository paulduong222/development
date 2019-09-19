package gov.nwcg.isuite.framework.customreports.criteriaformatters;

public class ToUpperCase {
	
	public ToUpperCase(){
	}
	
	public static String toFormat(String val) {
		String s = "";
		
		s = "upper('" + val + "')";
		
		return s;
	}

}
