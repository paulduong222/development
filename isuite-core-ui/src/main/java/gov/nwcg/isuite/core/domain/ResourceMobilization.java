package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.util.Collection;
import java.util.Date;

public interface ResourceMobilization extends Persistable {

	/**
	 * Returns the resource id.
	 * 
	 * @return
	 * 		the resource id to return
	 */
	public Long getResourceId();
	
	/**
	 * Sets the resource id.
	 * 
	 * @param id
	 * 		the resource id to set
	 */
	public void setResourceId(Long id);

	/**
	 * Returns the resource.
	 * 
	 * @return
	 * 		the resource to return
	 */
	public Resource getResource();
	
	/**
	 * Sets the resource id.
	 * 
	 * @param resource
	 * 		the resource to set
	 */
	public void setResource(Resource resource);
	
	/**
	 * Returns the start date.
	 * 
	 * @return
	 * 		the start date to return
	 */
	public Date getStartDate();
	
	/**
	 * Sets the start date.
	 * 
	 * @param dt
	 * 		the start date to set
	 */
	public void setStartDate(Date dt);

	/**
	 * Returns the workPeriods.
	 *
	 * @return 
	 *		the workPeriods to return
	 */
	public Collection<WorkPeriod> getWorkPeriods();

	/**
	 * Sets the workPeriods.
	 *
	 * @param workPeriods 
	 *			the workPeriods to set
	 */
	public void setWorkPeriods(Collection<WorkPeriod> workPeriods); 
	
}