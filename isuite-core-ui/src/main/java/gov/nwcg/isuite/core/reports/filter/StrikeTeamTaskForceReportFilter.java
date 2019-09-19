package gov.nwcg.isuite.core.reports.filter;

import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;


/*
 * Report filter for StrikeTeamTaskForceReport (StrikeTeamTaskForceReport.jrxml)
 */
public class StrikeTeamTaskForceReportFilter {
	private Long incidentId=null;
	private Long incidentGroupId;
	private Collection<Object> resourceIds = null;

	public StrikeTeamTaskForceReportFilter(){
		
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

	public Collection<Object> getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(Collection<Object> resourceIds) {
		this.resourceIds = resourceIds;
	}
	
	public Collection<Integer> getIntegerResourceIds() {
		Collection<Integer> list = new ArrayList<Integer>();
		
		for(Object val : resourceIds){
			try{
				list.add(TypeConverter.convertToInteger(val));
			}catch(Exception e){}
		}
		
		return list;
	}
	
	public Collection<Long> getLongResourceIds() {
		Collection<Long> list = new ArrayList<Long>();
		
		for(Object val : resourceIds){
			try{
				list.add(TypeConverter.convertToLong(val));
			}catch(Exception e){}
		}
		
		return list;
	}

}
