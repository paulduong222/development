package gov.nwcg.isuite.core.reports.data;

import java.util.Date;

public class PersonnelTimeSubReportData implements Comparable<PersonnelTimeSubReportData>{

	private String resourceName;
	private String accountingCode;
	private Date postStartDate;
	private Date postStartTime;
	private Date postStopDate;
	private Double totalHours;
	private String premiumCode;
   
	public PersonnelTimeSubReportData() {}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getAccountingCode() {
		return accountingCode;
	}

	public void setAccountingCode(String accountingCode) {
		this.accountingCode = accountingCode;
	}

	public Date getPostStartDate() {
		return postStartDate;
	}

	public void setPostStartDate(Date postStartDate) {
		this.postStartDate = postStartDate;
	}

	public Date getPostStopDate() {
		return postStopDate;
	}

	public void setPostStopDate(Date postStopDate) {
		this.postStopDate = postStopDate;
	}

	public Date getPostStartTime() {
		return postStartTime;
	}

	public void setPostStartTime(Date postStartTime) {
		this.postStartTime = postStartTime;
	}

	public Double getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(Double totalHours) {
		this.totalHours = totalHours;
	}

	public String getPremiumCode() {
		return premiumCode;
	}

	public void setPremiumCode(String premiumCode) {
		this.premiumCode = premiumCode;
	}

	@Override
	public int compareTo(PersonnelTimeSubReportData o) {
		if(o == null) return 1;
		int compare = 0;
		
		if(this.getResourceName()==null) return -1;
		if(o.getResourceName()==null) return 1;
		compare = this.getResourceName().compareToIgnoreCase(o.getResourceName());
		if(compare != 0) return compare;
		
		if(this.getPostStartDate()==null) return -1;
		if(o.getPostStartDate()==null) return 1;
		compare = this.getPostStartDate().compareTo(o.getPostStartDate());
		if(compare != 0) return compare;
		
		if(this.getAccountingCode()==null) return -1;
		if(o.getAccountingCode()==null) return 1;
		compare = this.getAccountingCode().compareTo(o.getAccountingCode());
		return compare;
	}

}
