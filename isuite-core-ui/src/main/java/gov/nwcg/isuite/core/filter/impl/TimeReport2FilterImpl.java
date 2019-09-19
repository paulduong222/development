package gov.nwcg.isuite.core.filter.impl;

import java.util.Date;

public class TimeReport2FilterImpl {
	private Long incidentId;
	private Long incidentGroupId;
	private Date startDate;
	private Date stopDate;
	private Long incidentResourceId;
	private Long resourceId;
	private Boolean excludeDemob=false;
	private Boolean sortByRequestNumber=false;
	private Boolean sortByLastName=false;
	private Boolean sortByTotal=false;
	private Boolean sortByShiftStartDate=false;
	
	private Boolean reportIsShiftsInExcess=false;
	private Boolean reportIsWorkRatio=false;
	private Boolean reportIsSummaryHours=false;

	private Boolean sectionIncludeAll=true;
	private String sectionFilter;
	
	public Long getIncidentId() {
		return incidentId;
	}
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getStopDate() {
		return stopDate;
	}
	public void setStopDate(Date stopDate) {
		this.stopDate = stopDate;
	}
	public Long getIncidentResourceId() {
		return incidentResourceId;
	}
	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
	}
	public Long getResourceId() {
		return resourceId;
	}
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	public Boolean getExcludeDemob() {
		return excludeDemob;
	}
	public void setExcludeDemob(Boolean excludeDemob) {
		this.excludeDemob = excludeDemob;
	}
	public Boolean getSortByRequestNumber() {
		return sortByRequestNumber;
	}
	public void setSortByRequestNumber(Boolean sortByRequestNumber) {
		this.sortByRequestNumber = sortByRequestNumber;
	}
	public Boolean getSortByLastName() {
		return sortByLastName;
	}
	public void setSortByLastName(Boolean sortByLastName) {
		this.sortByLastName = sortByLastName;
	}
	public Boolean getSortByTotal() {
		return sortByTotal;
	}
	public void setSortByTotal(Boolean sortByTotal) {
		this.sortByTotal = sortByTotal;
	}
	public Boolean getReportIsShiftsInExcess() {
		return reportIsShiftsInExcess;
	}
	public void setReportIsShiftsInExcess(Boolean reportIsShiftsInExcess) {
		this.reportIsShiftsInExcess = reportIsShiftsInExcess;
	}
	public Boolean getReportIsWorkRatio() {
		return reportIsWorkRatio;
	}
	public void setReportIsWorkRatio(Boolean reportIsWorkRatio) {
		this.reportIsWorkRatio = reportIsWorkRatio;
	}
	public Boolean getReportIsSummaryHours() {
		return reportIsSummaryHours;
	}
	public void setReportIsSummaryHours(Boolean reportIsSummaryHours) {
		this.reportIsSummaryHours = reportIsSummaryHours;
	}
	public Boolean getSectionIncludeAll() {
		return sectionIncludeAll;
	}
	public void setSectionIncludeAll(Boolean sectionIncludeAll) {
		this.sectionIncludeAll = sectionIncludeAll;
	}
	public String getSectionFilter() {
		return sectionFilter;
	}
	public void setSectionFilter(String sectionFilter) {
		this.sectionFilter = sectionFilter;
	}
	public Boolean getSortByShiftStartDate() {
		return sortByShiftStartDate;
	}
	public void setSortByShiftStartDate(Boolean sortByShiftStartDate) {
		this.sortByShiftStartDate = sortByShiftStartDate;
	}
}
