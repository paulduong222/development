package gov.nwcg.isuite.core.reports.filter;


/*
 * Report filter for ICS209Report (ICS209Report.jrxml)
 */
public class ICS209ReportFilter {
	private Long incidentId=null;
	private Long incidentGroupId=null;
	private Boolean stateGrouping=false;
	
	public ICS209ReportFilter(){
		
	}
	
	/**
	 * Returns the incidentId.
	 *
	 * @return 
	 *		the incidentId to return
	 */
	public Long getIncidentId() {
		return incidentId;
	}
	/**
	 * Sets the incidentId.
	 *
	 * @param incidentId 
	 *			the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	/**
	 * @return the stateGrouping
	 */
	public Boolean getStateGrouping() {
		return stateGrouping;
	}

	/**
	 * @param stateGrouping the stateGrouping to set
	 */
	public void setStateGrouping(Boolean stateGrouping) {
		this.stateGrouping = stateGrouping;
	}
	
}
