package gov.nwcg.isuite.core.reports.filter;

import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Collection;
import java.util.Date;


/*
 * Report filter for GroundSupportReport (GroundSupportReport.jrxml)
 */
public class GroundSupportReportFilter {
	private Collection<Long> resourceIds = null;
	private Long incidentId;
	private Long incidentGroupId;
	private Boolean includeSTTFComponents = false;
	private Date startDate;
	private Date endDate;
	
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

	/**
	 * Returns the tentativeReleaseStartDate
	 * 
	 * @return
	 * 		the tentativeReleaseStartDate to return
	 */
	public String getTentativeReleaseDateStartRange() {
		if(null != startDate){
			return DateUtil.toDateString(startDate);
		}else{
			return "";
		}
	}

	/**
	 * Returns the tentativeReleaseEndDate
	 * 
	 * @return
	 * 		the tentativeReleaseEndDate to return
	 */
	public String getTentativeReleaseDateEndRange() {
		if(null != endDate){
			return DateUtil.toDateString(endDate);
		}else
			return "";
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
