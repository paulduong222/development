package gov.nwcg.isuite.core.reports.filter;

import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Collection;
import java.util.Date;

public class TentativeReleasePosterReportFilter {
	private Collection<Long> resourceIds = null;
	private Long incidentId;
	private Long incidentGroupId;
	private Boolean includeSTTFComponents = false;
	private Date startDate = null;
	private Date endDate = null;
	
	/**
	 * @return the resourceIds
	 */
	public Collection<Long> getResourceIds() {
		return resourceIds;
	}
	
	/**
	 * @param resourceIds the resourceIds to set
	 */
	public void setResourceIds(Collection<Long> resourceIds) {
		this.resourceIds = resourceIds;
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
	 * @return the includeSTTFComponents
	 */
	public Boolean getIncludeSTTFComponents() {
		return includeSTTFComponents;
	}
	
	/**
	 * @param includeSTTFComponents the includeSTTFComponents to set
	 */
	public void setIncludeSTTFComponents(Boolean includeSTTFComponents) {
		this.includeSTTFComponents = includeSTTFComponents;
	}
	
	/**
	 * @return the reportStartDate
	 */
	public String getReportStartDate() {
		if(null==startDate)
			return "";
		else
			return DateUtil.toDateString(startDate);
	}
	
	
	/**
	 * @return the reportEndDate
	 */
	public String getReportEndDate() {
		if(null==endDate)
			return "";
		else
			return DateUtil.toDateString(endDate);
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

	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}
	

}
