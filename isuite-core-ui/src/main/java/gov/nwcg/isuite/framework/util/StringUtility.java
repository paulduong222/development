package gov.nwcg.isuite.framework.util;

import java.util.StringTokenizer;

public class StringUtility {

	public static Boolean hasValue(String val){
		
		if( (null != val) && (!val.trim().isEmpty()) )
			return true;
		
		return false;
	}

	public static String fixString(String val, int maxLength){
		if(null==val)
			return "";
		if(hasValue(val)){
			if(val.length()>maxLength){
				val=val.substring(0, (maxLength-1));
			}else
				return val;
		}
		return val;
	}
	
	public static String fixStringSpecialChars(String val, int maxLength){
		if(null==val)
			return "";
		
		if(hasValue(val)){
			String val2=val.replace("'", "''");
			if(val2.length()>maxLength){
				val2=val2.substring(0, (maxLength-1));
				return val2;
			}else
				return val2;
		}
		return val;
	}

	public static String toUpper(String val){
		if(null==val)
			return val;
		else
			return val.toUpperCase();
	}
	
	public static String removeNonNumeric(String val){
		if(null==val)
			return val;
		else{
			StringBuffer strBuff =new StringBuffer();
			char c;
		    
		    for (int i = 0; i < val.length() ; i++) {
		        c = val.charAt(i);
		        
		        if (Character.isDigit(c)) {
		            strBuff.append(c);
		        }			
		    }
		    return strBuff.toString();
		}
	}
	
	public static String removeChar(String val, char cval){
		if(null==val)
			return val;
		else{
			StringBuffer strBuff =new StringBuffer();
			char c;
		    
		    for (int i = 0; i < val.length() ; i++) {
		        c = val.charAt(i);
		        
		        if(c!=cval)
		        	strBuff.append(c);
		    }
		    
		    return strBuff.toString();
		}
	}

	public static String tenDigitPhoneNumberFormatter(String val){
		if(null==val)
			return val;
		else if(val.length() != 10)
			return null;
		else {
			StringBuffer strBuff = new StringBuffer();
			char c;
		    
		    for (int i = 0; i < 10 ; i++) {
		        c = val.charAt(i);
		        
		        if (i == 0) {
		            strBuff.append("(");
		        } else if ( i == 3) {
		            strBuff.append(") ");
		        } else if ( i == 6) {
		        	strBuff.append("-");
		        }
		        strBuff.append(c);
		    }
		    return strBuff.toString();
		}
	}
	
	public static String leftPad(String val, int step){
		String rtn="";
		
		for(int i=0;i<step;i++){
			rtn=rtn+" ";
		}
		rtn=rtn+ (val != null ? val : "" );
		return rtn;
	}
	
	public static String leftPad(String val, String padString, int lng){
		String rtn="";
	
		if(null == val || val.isEmpty()){
			for(int i=0;i<lng;i++){
				rtn=rtn+padString;
			}
		}else{
			int valLength=val.length();
			int loopCnt=lng-valLength;
			rtn=val;
			for(int i=0;i<loopCnt;i++){
				rtn=padString+rtn;
			}
		}
		return rtn;
	}

	public static String rightPad(String val, String padString, int lng){
		String rtn="";
	
		if(null == val || val.isEmpty()){
			for(int i=0;i<lng;i++){
				rtn=rtn+padString;
			}
		}else{
			int valLength=val.length();
			int loopCnt=lng-valLength;
			rtn=val;
			for(int i=0;i<loopCnt;i++){
				rtn=rtn+padString;
			}
		}
		return rtn;
	}
	
	public static String getTokenValue(String val, String delimeter, int idx) {
		StringTokenizer tokenizer = new StringTokenizer(val,delimeter);
		int cnt=1;
		
		while(tokenizer.hasMoreTokens()){
			String token = (String)tokenizer.nextToken();
			
			if(cnt==idx){
				return token;
			}
			
			cnt++;
		}
		
		return "";
	}

	/**
	 * @param val
	 * @param length
	 * @return
	 */
	public static String leftTrim(String val, int length){
		String rtn="";
		
		if(null != val && val.length() > length){
			rtn=val.substring(0, (length-1));
		}else
			rtn=val;
		
		return rtn;
	}
	
	public static String toCurrency(String val) {
		return "$ " +  val;
	}
	
	public static Boolean contains(String source, String val){
		if(null != source && source.indexOf(val)>-1)
			return true;
		else
			return false;
	}
}
