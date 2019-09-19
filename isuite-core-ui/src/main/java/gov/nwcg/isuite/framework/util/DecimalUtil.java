package gov.nwcg.isuite.framework.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

public class DecimalUtil {

	public static Boolean hasValue(java.math.BigDecimal val){
		if((null != val) && (val.doubleValue() > 0))
			return true;
		else
			return false;
	}

	public static Boolean hasDoubleValue(Double val){
		if((null != val) && (val > 0))
			return true;
		else
			return false;
	}
	
	public static String formatAsCurrencyString(double val, int decimalPlaces) {
		return "$"+formatAsString(val,decimalPlaces);
	}

	public static double formatAsRoundedUp(double val, int decimalPlaces) {
		BigDecimal bd = new BigDecimal(val);
		bd=bd.setScale(decimalPlaces,RoundingMode.UP);
		
		double v=bd.doubleValue();
		return v;
	}
	
	public static double formatAsRoundedDown(double val, int decimalPlaces) {
		BigDecimal bd = new BigDecimal(val);
		bd=bd.setScale(decimalPlaces,RoundingMode.DOWN);
		
		double v=bd.doubleValue();
		return v;
	}

	public static String formatAsString(double val, int decimalPlaces) {
		NumberFormat df = DecimalFormat.getInstance();
		df.setMinimumFractionDigits(decimalPlaces);
		df.setRoundingMode(RoundingMode.DOWN);

		return df.format(val);
	}

	public static double formatAsDecimalRounded(double val) {
		String sval=String.valueOf(val);
		int idx=sval.indexOf(".");
		if(idx>0){
			String strDecimal=sval.substring(idx+1, sval.length());
			if(StringUtility.hasValue(strDecimal) && strDecimal.length()==1){
				Double d=Double.parseDouble(val+"0");
				return d;
			}
			if(StringUtility.hasValue(strDecimal) && strDecimal.length()==2){
				return val;
			}
			if(StringUtility.hasValue(strDecimal) && strDecimal.length()>2){
				int endswith=Integer.parseInt(strDecimal.substring(2,3));
				if(endswith<=4)
					return formatAsRoundedDown(val,2);
				else
					return formatAsRoundedUp(val,2);
			}else
				return formatAsRoundedDown(val,2);
		}else{
			Double d=Double.parseDouble(val+".00");
			return d;
		}
	}
	
	public static String addEnding0toString(String s) {
		try{
			if(StringUtility.hasValue(s)
					&& StringUtility.contains(s,".")
					&& (s.length()-(s.indexOf(".")+1)==1)){
				return s+"0";
			}else if(StringUtility.hasValue(s)
						&& !StringUtility.contains(s,".")){
				return s+".00";
			}else
				return s;
		}catch(Exception e){
			return s;
		}
	}

}
