package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.CostReportFilter;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

import java.util.Collection;
import java.util.Date;

public class CostReportFilterImpl extends FilterImpl implements CostReportFilter {
	private Long incidentId;
	private Long incidentGroupId;
	private boolean isIncidentGroup = false;

	// date filter
	private Boolean isIncludeAllDates;
	private Date startDate;
	private Date endDate;
	
	// grouping
	private String groupingField;
	private Collection<String> groupingSelections;
	
	//Additional filters
	private String additionalFilterType;

	// UnitId local?
	private Boolean localUnitId;
	
	// analysis report type
	private String analysisReportType;
	
	// costshare report type
	private String costshareReportType;
	
	//Graphs
	private String graphType;
	
	//Report type
	private String reportType;
	
	private DateTransferVo startDateVo=new DateTransferVo();
	private DateTransferVo endDateVo=new DateTransferVo();

    public CostReportFilterImpl(){
		
	}
    
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
	
	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}


	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}


	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}


	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}


	/**
	 * @return the isIncludeAllDates
	 */
	public Boolean getIsIncludeAllDates() {
		return isIncludeAllDates;
	}


	/**
	 * @param isIncludeAllDates the isIncludeAllDates to set
	 */
	public void setIsIncludeAllDates(Boolean isIncludeAllDates) {
		this.isIncludeAllDates = isIncludeAllDates;
	}


	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}


	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}


	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	/**
	 * @return the groupingField
	 */
	public String getGroupingField() {
		return groupingField;
	}


	/**
	 * @param groupingField the groupingField to set
	 */
	public void setGroupingField(String groupingField) {
		this.groupingField = groupingField;
	}


	/**
	 * @return the groupingSelections
	 */
	public Collection<String> getGroupingSelections() {
		return groupingSelections;
	}


	/**
	 * @param groupingSelections the groupingSelections to set
	 */
	public void setGroupingSelections(Collection<String> groupingSelections) {
		this.groupingSelections = groupingSelections;
	}


	/**
	 * @return the additionalFilterType
	 */
	public String getAdditionalFilterType() {
		return additionalFilterType;
	}


	/**
	 * @param additionalFilterType the additionalFilterType to set
	 */
	public void setAdditionalFilterType(String additionalFilterType) {
		this.additionalFilterType = additionalFilterType;
	}


	/**
	 * @return the graphType
	 */
	public String getGraphType() {
		return graphType;
	}


	/**
	 * @param graphType the graphType to set
	 */
	public void setGraphType(String graphType) {
		this.graphType = graphType;
	}


	/**
	 * @return the localUnitId
	 */
	public Boolean getLocalUnitId() {
		return localUnitId;
	}


	/**
	 * @param localUnitId the localUnitId to set
	 */
	public void setLocalUnitId(Boolean localUnitId) {
		this.localUnitId = localUnitId;
	}


	/**
	 * @return the analysisReportType
	 */
	public String getAnalysisReportType() {
		return analysisReportType;
	}


	/**
	 * @param analysisReportType the analysisReportType to set
	 */
	public void setAnalysisReportType(String analysisReportType) {
		this.analysisReportType = analysisReportType;
	}


	/**
	 * @return the costshareReportType
	 */
	public String getCostshareReportType() {
		return costshareReportType;
	}


	/**
	 * @param costshareReportType the costshareReportType to set
	 */
	public void setCostshareReportType(String costshareReportType) {
		this.costshareReportType = costshareReportType;
	}

	public boolean isIncidentGroup() {
		return this.isIncidentGroup;
	}

	public void setIncidentGroup(boolean isIncidentGroup) {
		this.isIncidentGroup = isIncidentGroup;
	}
	
	/**
	 * @return the reportType
	 */
	public String getReportType() {
		return reportType;
	}


	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
}
