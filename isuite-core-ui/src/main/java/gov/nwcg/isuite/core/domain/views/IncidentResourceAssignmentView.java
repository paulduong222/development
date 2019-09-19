package gov.nwcg.isuite.core.domain.views;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.util.Collection;


/**
 * This represents the view for resource assignments attached to an incident.
 * 
 */
public interface IncidentResourceAssignmentView extends Persistable{

	/**
	 * Returns the incident.
	 * 
	 * @return
	 * 		the incident to return
	 */
	public Incident getIncident();
	
	/**
	 * Sets the incident.
	 * 
	 * @param incident
	 * 			the incident to set
	 */
	public void setIncident(Incident incident);
	
	/**
	 * Returns the incident id.
	 * 
	 * @return
	 * 		the incident id to return
	 */
	public Long getIncidentId();
	
	/**
	 * Sets the incident id.
	 * 
	 * @param id
	 * 		the incident id to set
	 */
	public void setIncidentId(Long id);
	
	/**
	 * Returns the name at the incident.
	 * 
	 * @return
	 * 		the name to return
	 */
	public String getNameAtIncident();
	
	/**
	 * Sets the name at the incident.
	 * 
	 * @param name
	 * 			the name to set
	 */
	public void setNameAtIncident(String name);
	
	/**
	 * Returns the resource.
	 * 
	 * @return
	 * 		the resource to return
	 */
	public Resource getResource();
   
	/**
	 * Sets the resource.
	 * 
	 * @param resource
	 * 			the resource to set
	 */
	public void setResource(Resource resource);

	/**
	 * Sets the resource id.
	 * 
	 * @param resourceId
	 * 			the resource id to set
	 */
	public void setResourceId(Long resourceId);

	/**
	 * Returns the resource id.
	 * 
	 * @return
	 * 		the resource id to return
	 */
	public Long getResourceId();
   
	/**
	 * Returns the collection of work periods.
	 * 
	 * @return
	 * 		the collection of work periods to return
	 */
	public Collection<WorkPeriod> getWorkPeriods();
   
	/**
	 * Sets the collection of work periods.
	 * 
	 * @param collection
	 * 			the collection of work periods to set
	 */
	public void setWorkPeriods(Collection<WorkPeriod> collection);
}
