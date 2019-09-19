package gov.nwcg.isuite.framework.util;

import gov.nwcg.isuite.core.vo.AssignmentVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestNumberUtil {

	/*
	 * Rule is we remove any leading 0's from the request number
	 * after the hyphen and . 
	 * 
	 * Make sure hyphen exists after first char
	 * 
	 * Example: A-0009 --> A-9
	 *          E-1.009 --> E-1.9
	 *          O-002.003 --> O-2.3
	 */
	public static void formatRequestNumber(IncidentResourceVo vo) throws Exception {
		if(null != vo){
			if(null != vo.getWorkPeriodVo().getCurrentAssignmentVo()){
				String rq=vo.getWorkPeriodVo().getCurrentAssignmentVo().getRequestNumber();

				if(StringUtility.hasValue(rq)){
					rq = RequestNumberUtil.formatRequestNumberString(rq);
					vo.getWorkPeriodVo().getCurrentAssignmentVo().setRequestNumber(rq);
				}
			}
		}
	}
	
	/**
	 * Public static utility method to return a formatted value of a request number
	 * @param strRequestNumber The incoming, possibly unformatted request number
	 * @return A formatted request number String.
	 */
	public static String formatRequestNumberString(String strRequestNumber) {
		
		if(StringUtility.hasValue(strRequestNumber)){

			if(strRequestNumber.length() > 1){
				if(strRequestNumber.charAt(1) != '-'){
					String newstrRequestNumber=String.valueOf(strRequestNumber.charAt(0)) + "-" + strRequestNumber.substring(1, strRequestNumber.length());
					strRequestNumber=newstrRequestNumber;
				}
			}
		
			Boolean b = true;
			while(b){
				Pattern p = Pattern.compile("(\\-0)");
				Matcher m = p.matcher(strRequestNumber);

				b=m.find();
				
				if(b)
					strRequestNumber=strRequestNumber.replaceAll("(\\-0)", "-");
			}
			
			b=true;
			while(b){
				Pattern p = Pattern.compile("(\\.0)");
				Matcher m = p.matcher(strRequestNumber);

				b=m.find();
				
				if(b)
					strRequestNumber=strRequestNumber.replaceAll("(\\.0)", ".");
			}

			String strRequestNumberChar=parseRequestNumberChar(strRequestNumber);
			String strRequestNumberValue=parseRequestNumberValue(strRequestNumber);
			
			strRequestNumber=(StringUtility.hasValue(strRequestNumberChar) ? strRequestNumberChar.toUpperCase() : " ");
			strRequestNumber=strRequestNumber+"-"+(StringUtility.hasValue(strRequestNumberValue) ? strRequestNumberValue: " ");
		}
		
		return strRequestNumber;
	}
		

	public static void formatRequestNumber(AssignmentVo vo) throws Exception {
		if(null != vo){
			if(null != vo){
				String rq=vo.getRequestNumber();

				if(StringUtility.hasValue(rq)){
					Boolean b = true;
					while(b){
						Pattern p = Pattern.compile("(\\-0)");
						Matcher m = p.matcher(rq);

						b=m.find();
						
						if(b)
							rq=rq.replaceAll("(\\-0)", "-");
					}
					
					b=true;
					while(b){
						Pattern p = Pattern.compile("(\\.0)");
						Matcher m = p.matcher(rq);

						b=m.find();
						
						if(b)
							rq=rq.replaceAll("(\\.0)", ".");
					}
				
					vo.setRequestNumber(rq);
				}
				
				
			}
		}
	}
	
	public static String parseRequestNumberChar(String requestNumber) {
		
		if(null != requestNumber){
			StringTokenizer tokens = new StringTokenizer(requestNumber,"-");
			int cnt=0;
			
			while(tokens.hasMoreTokens()){
				String val = (String)tokens.nextToken();
				
				if(cnt==0)
					return val.trim();
				
				cnt++;
			}
		}
		
		return "";
	}

	public static String parseRequestNumberValue(String requestNumber) {
		if(null != requestNumber){
			StringTokenizer tokens = new StringTokenizer(requestNumber,"-");
			int cnt=0;
			
			while(tokens.hasMoreTokens()){
				String val = (String)tokens.nextToken();
				
				if(cnt==1)
					return val.trim();
				
				cnt++;
			}
		}
		
		return "";
	}
	
/*	public static void main(String[] args){
//		String rq = "A-000900.001.000034800.04";
		String rq = "A-01980";
		
		Boolean b = true;
		while(b){
			Pattern p = Pattern.compile("(\\-0)");
			Matcher m = p.matcher(rq);

			b=m.find();
			
			if(b)
				rq=rq.replaceAll("(\\-0)", "-");
		}
		
		System.out.println(rq);
		
		b=true;
		while(b){
			Pattern p = Pattern.compile("(\\.0)");
			Matcher m = p.matcher(rq);

			b=m.find();
			
			if(b)
				rq=rq.replaceAll("(\\.0)", ".");
		}
		

		System.out.println(rq);
		
	}*/
}
