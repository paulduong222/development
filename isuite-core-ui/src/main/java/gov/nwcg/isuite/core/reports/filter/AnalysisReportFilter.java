package gov.nwcg.isuite.core.reports.filter;

import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class AnalysisReportFilter extends TimeReportFilter{
	private Long incidentId = 0L;
	private Long incidentGroupId = 0L;	
	private boolean isIncidentGroup = false;
	private String analysisReport = "resourceCost"; //exception, byCost, byOverhead
	private String itemCodeOrResource = "itemCode"; //resource
	private String analysisReportFilter = "noActualTimePosted"; //exceeds10000, threeOrMoreDays, noAgencyAssigned, noCostRecords
	private String exceeds10000 = "10000";
	private String threeOrMoreDays = "3";
	private String startDateChar = "";
	private String endDateChar = "";
	private Date startDate;
	private Date endDate;
	private boolean dateRangeIncluded = false;
	private Collection<String> noDailyCostSelections;
	
	private boolean allExceptionsIncluded = true;
	private boolean missingAgencyIncluded = false;
	private boolean missingDatesIncluded = false;
	private boolean statusIsFIncluded = false;
	
	private DateTransferVo startDateVo=new DateTransferVo();
	private DateTransferVo endDateVo=new DateTransferVo();

	
	public DateTransferVo getStartDateVo() {
		return startDateVo;
	}

	public void setStartDateVo(DateTransferVo startDateVo) {
		this.startDateVo = startDateVo;
	}

	public DateTransferVo getEndDateVo() {
		return endDateVo;
	}

	public void setEndDateVo(DateTransferVo endDateVo) {
		this.endDateVo = endDateVo;
	}
	
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
	public boolean isIncidentGroup() {
		return isIncidentGroup;
	}
	public void setIncidentGroup(boolean isIncidentGroup) {
		this.isIncidentGroup = isIncidentGroup;
	}
	public String getItemCodeOrResource() {
		return itemCodeOrResource;
	}
	public void setItemCodeOrResource(String itemCodeOrResource) {
		this.itemCodeOrResource = itemCodeOrResource;
	}
	public String getStartDateChar() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		return df.format(this.getStartDate());
	}
	public void setStartDateChar(String startDateChar) {
		this.startDateChar = startDateChar;
	}
	public String getEndDateChar() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		return df.format(this.getEndDate());
	}
	public void setEndDateChar(String endDateChar) {
		this.endDateChar = endDateChar;
	}
	public String getAnalysisReport() {
		return analysisReport;
	}
	public void setAnalysisReport(String analysisReport) {
		this.analysisReport = analysisReport;
	}
	public String getAnalysisReportFilter() {
		return analysisReportFilter;
	}
	public void setAnalysisReportFilter(String analysisReportFilter) {
		this.analysisReportFilter = analysisReportFilter;
	}
	public String getExceeds10000() {
		return exceeds10000;
	}
	public void setExceeds10000(String exceeds10000) {
		this.exceeds10000 = exceeds10000;
	}
	public String getThreeOrMoreDays() {
		return threeOrMoreDays;
	}
	public void setThreeOrMoreDays(String threeOrMoreDays) {
		this.threeOrMoreDays = threeOrMoreDays;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public boolean isDateRangeIncluded() {
		return dateRangeIncluded;
	}
	public void setDateRangeIncluded(boolean dateRangeIncluded) {
		this.dateRangeIncluded = dateRangeIncluded;
	}
	public Collection<String> getNoDailyCostSelections() {
		return noDailyCostSelections;
	}
	public void setNoDailyCostSelections(Collection<String> noDailyCostSelections) {
		this.noDailyCostSelections = noDailyCostSelections;
	}
	public boolean isAllExceptionsIncluded() {
		return allExceptionsIncluded;
	}
	public void setAllExceptionsIncluded(boolean allExceptionsIncluded) {
		this.allExceptionsIncluded = allExceptionsIncluded;
	}
	public boolean isMissingAgencyIncluded() {
		return missingAgencyIncluded;
	}
	public void setMissingAgencyIncluded(boolean missingAgencyIncluded) {
		this.missingAgencyIncluded = missingAgencyIncluded;
	}
	public boolean isMissingDatesIncluded() {
		return missingDatesIncluded;
	}
	public void setMissingDatesIncluded(boolean missingDatesIncluded) {
		this.missingDatesIncluded = missingDatesIncluded;
	}
	public boolean isStatusIsFIncluded() {
		return statusIsFIncluded;
	}
	public void setStatusIsFIncluded(boolean statusIsFIncluded) {
		this.statusIsFIncluded = statusIsFIncluded;
	}
	
	
}
