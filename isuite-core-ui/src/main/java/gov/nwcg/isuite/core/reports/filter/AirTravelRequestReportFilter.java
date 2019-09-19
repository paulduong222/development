package gov.nwcg.isuite.core.reports.filter;

import java.util.Collection;

/*
 * Report filter for AirTravelRequestReport (AirTravelRequestReport.jrxml)
 */
public class AirTravelRequestReportFilter {
	private Collection<Long> resourceIds = null;
	private Long incidentId;
	private Long incidentGroupId;
	private Boolean includeSTTFComponents = false;
	private Boolean markDispatchNotified=false;
	
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

	public Boolean getMarkDispatchNotified() {
		return markDispatchNotified;
	}

	public void setMarkDispatchNotified(Boolean markDispatchNotified) {
		this.markDispatchNotified = markDispatchNotified;
	}
}
