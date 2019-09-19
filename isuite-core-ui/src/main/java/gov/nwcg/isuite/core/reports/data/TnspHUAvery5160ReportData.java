package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.framework.util.StringUtility;


public class TnspHUAvery5160ReportData {
	private String labelName;
	private String address1;
	private String city;
	private String state;
	private String cityState;
	private String zip;
	private String unitDescription;
	
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCityState() {
		if(StringUtility.hasValue(this.city)&&StringUtility.hasValue(this.state)){
			return this.city +", "+this.state;
		}else{
			if(StringUtility.hasValue(this.city))
				return this.city;
			else if(StringUtility.hasValue(this.state)){
				return this.state;
			}
		}
		return "";
	}
	public void setCityState(String cityState) {
		this.cityState = cityState;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public void setUnitDescription(String unitDescription) {
		this.unitDescription = unitDescription;
	}
	public String getUnitDescription() {
		return unitDescription;
	}
	
}
