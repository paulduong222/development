package gov.nwcg.isuite.core.reports.filter;

import java.util.Collection;

public class ActualDemobReportFilter {
	private Collection<Long> resourceIds = null;
	private Long incidentId;
	private Long incidentGroupId;
	private Boolean includeSTTFComponents = false;
	private Boolean markDispatchNotified=false;
	
	/**
	 * Sets the resourceIds
	 * 
	 * @param resourceIds
	 * 		the resourceIds to set
	 */
	public void setResourceIds(Collection<Long> resourceIds) {
		this.resourceIds = resourceIds;
	}
	
	/**
	 * Returns the resourceIds
	 * 
	 * @return
	 * 		the resourceIds to return
	 */
	public Collection<Long> getResourceIds() {
		return resourceIds;
	}
	
	/**
	 * Sets the incidentId
	 * 
	 * @param incidentId
	 * 		the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}
	
	/**
	 * Returns the incidentId
	 * 
	 * @return
	 * 		the incidentId to return
	 */
	public Long getIncidentId() {
		return incidentId;
	}
	
	/**
	 * Sets include Strike Team Task Force Components
	 * 
	 * @param includeSTTFComponents
	 * 		the includeSTTFComponents to set
	 */
	public void setIncludeSTTFComponents(Boolean includeSTTFComponents) {
		this.includeSTTFComponents = includeSTTFComponents;
	}

	/**
	 * Returns include Strike Team Task Force Components
	 * 
	 * @return
	 * 		the includeSTTFComponent to return
	 */
	public Boolean getIncludeSTTFComponents() {
		return includeSTTFComponents;
	}

	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	public Boolean getMarkDispatchNotified() {
		return markDispatchNotified;
	}

	public void setMarkDispatchNotified(Boolean markDispatchNotified) {
		this.markDispatchNotified = markDispatchNotified;
	}

}
