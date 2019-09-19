package gov.nwcg.isuite.core.filter;

import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.framework.core.filter.Filter;

public interface CostReportFilter extends Filter {

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId();


	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) ;


	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() ;


	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId);


	/**
	 * @return the isIncludeAllDates
	 */
	public Boolean getIsIncludeAllDates();

	/**
	 * @param isIncludeAllDates the isIncludeAllDates to set
	 */
	public void setIsIncludeAllDates(Boolean isIncludeAllDates) ;


	/**
	 * @return the startDate
	 */
	public Date getStartDate() ;


	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate);


	/**
	 * @return the endDate
	 */
	public Date getEndDate() ;


	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) ;


	/**
	 * @return the groupingField
	 */
	public String getGroupingField() ;


	/**
	 * @param groupingField the groupingField to set
	 */
	public void setGroupingField(String groupingField) ;


	/**
	 * @return the groupingSelections
	 */
	public Collection<String> getGroupingSelections() ;


	/**
	 * @param groupingSelections the groupingSelections to set
	 */
	public void setGroupingSelections(Collection<String> groupingSelections) ;


	/**
	 * @return the additionalFilterType
	 */
	public String getAdditionalFilterType();

	/**
	 * @param additionalFilterType the additionalFilterType to set
	 */
	public void setAdditionalFilterType(String additionalFilterType);


	/**
	 * @return the graphType
	 */
	public String getGraphType();


	/**
	 * @param graphType the graphType to set
	 */
	public void setGraphType(String graphType) ;

	/**
	 * @return the localUnitId
	 */
	public Boolean getLocalUnitId() ;


	/**
	 * @param localUnitId the localUnitId to set
	 */
	public void setLocalUnitId(Boolean localUnitId) ;

	/**
	 * @return the analysisReportType
	 */
	public String getAnalysisReportType() ;


	/**
	 * @param analysisReportType the analysisReportType to set
	 */
	public void setAnalysisReportType(String analysisReportType);


	/**
	 * @return the costshareReportType
	 */
	public String getCostshareReportType() ;


	/**
	 * @param costshareReportType the costshareReportType to set
	 */
	public void setCostshareReportType(String costshareReportType) ;

	public boolean isIncidentGroup();

	public void setIncidentGroup(boolean isIncidentGroup);
	
	/**
	 * @return the reportType
	 */
	public void setReportType(String reportType);
	
	/**
	 * @param reportType the reportType to set
	 */
	public String getReportType();
}
