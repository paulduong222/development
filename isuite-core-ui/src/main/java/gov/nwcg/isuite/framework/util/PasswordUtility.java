package gov.nwcg.isuite.framework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordUtility {

	/**
	 * 
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public static Boolean isValidLength(String pwd) throws Exception {
		if(pwd.length() < 12) 
			return false;
		else
			return true;
	}

	public static Boolean containsUpper(String pwd) throws Exception {
	    Pattern pattern = Pattern.compile("[A-Z]");
	    Matcher matcher = pattern.matcher(pwd);
	    if(!matcher.find()) 
	    	return false;
	    else
	    	return true;
	}

	public static Boolean containsLower(String pwd) throws Exception {
		
	    Pattern pattern = Pattern.compile("[a-z]");
	    Matcher matcher = pattern.matcher(pwd);
	    if(!matcher.find()) 
	    	return false;
	    else
	    	return true;
	}
	
	public static Boolean containsNumber(String pwd) throws Exception {
		
	    Pattern pattern = Pattern.compile("[\\d]");
	    Matcher matcher = pattern.matcher(pwd);
	    if(!matcher.find()) 
	    	return false;
	    else
	    	return true;
	}

	public static Boolean containsSpecial(String pwd) throws Exception {
		
	    Pattern pattern = Pattern.compile("[!#%&^_*.]");
	    Matcher matcher = pattern.matcher(pwd);
	    if(!matcher.find()) 
	    	return false;
	    else
	    	return true;
	}
	
}
