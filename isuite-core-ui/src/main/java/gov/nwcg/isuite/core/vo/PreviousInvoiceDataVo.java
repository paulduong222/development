package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.util.DecimalUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

public class PreviousInvoiceDataVo {
	private String val1;
	private Double totalPostings;
	private Double totalAdditions;
	private Double totalDeductions;
	
	public static String getInvoiceTotal(PreviousInvoiceDataVo p){
		String sTotal="";
		Double total=0.0;
		
		if(null != p.getTotalPostings()){
			total=p.getTotalPostings();
		}
		if(null != p.getTotalAdditions()){
			total=total+p.getTotalAdditions();
		}
		if(null != p.getTotalDeductions()){
			total=total-p.getTotalDeductions();
		}
		if(DecimalUtil.hasDoubleValue(total)){
			total = DecimalUtil.formatAsDecimalRounded(total);
		}
		String s=String.valueOf(total);
		sTotal=DecimalUtil.addEnding0toString(s);
		return sTotal;
	}
	
	public String getVal1() {
		return val1;
	}
	public void setVal1(String val1) {
		this.val1 = val1;
	}
	public Double getTotalPostings() {
		return totalPostings;
	}
	public void setTotalPostings(Double totalPostings) {
		this.totalPostings = totalPostings;
	}
	public Double getTotalAdditions() {
		return totalAdditions;
	}
	public void setTotalAdditions(Double totalAdditions) {
		this.totalAdditions = totalAdditions;
	}
	public Double getTotalDeductions() {
		return totalDeductions;
	}
	public void setTotalDeductions(Double totalDeductions) {
		this.totalDeductions = totalDeductions;
	}

	public static void main(String[] args){
		String a = "";
		String b = "3";
		String c = "4.1";
		String d = "5.33";
		System.out.println(DecimalUtil.addEnding0toString(a));
		System.out.println(DecimalUtil.addEnding0toString(b));
		System.out.println(DecimalUtil.addEnding0toString(c));
		System.out.println(DecimalUtil.addEnding0toString(d));
		Double v= 9.45;
		String s = String.valueOf(v);
		
		if(StringUtility.contains(s,".")
				&& (s.length()-(s.indexOf(".")+1)==1)){
			System.out.println(s);
			System.out.println(s.length());
			System.out.println(s.indexOf("."));
		}
	}
	
}
