package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.types.ResourceRequestCategoryEnum;

import java.util.Collection;

/**
 * This is the filter interface defining the filtering fields.
 * 
 * @author dprice
 */
public interface ResourceGridMultiViewFilter extends Filter {

	/**
	 * Returns whether or not to filter only resources assigned to an incident.
	 * 
	 * @return
	 * 		the filter value on whether to return only resources assigned to an incident
	 */
	public boolean getAssignedToIncident();

	/**
	 * Sets whether or not to filter only resources assigned to an incident.
	 * 
	 * @param val
	 * 		whether to return only resources assigned to an incident
	 */
	public void setAssignedToIncident(boolean val);

	/**
	 * Returns whether or not to filter only resources that are marked as permanent.
	 * 
	 * @return
	 * 		the filter value on whether to return only resources marked as permanent
	 */
	public boolean getPermanent();

	/**
	 * Sets whether or not to filter only resources marked as permanent.
	 * 
	 * @param val
	 * 		whether to return only resources marked as permanent
	 */
	public void setPermanent(boolean val);
	
	/**
	 * Returns whether or not to filter only resources that are marked as overhead.
	 * 
	 * @return
	 * 		the filter value on whether to return only resources marked as overhead
	 */
	public boolean getOverhead();

	/**
	 * Sets whether or not to filter only resources marked as overhead.
	 * 
	 * @param val
	 * 		whether to return only resources marked as overhead
	 */
	public void setOverhead(boolean val);

	/**
	 * Returns whether or not to filter only resources that are marked as aircraft.
	 * 
	 * @return
	 * 		the filter value on whether to return only resources marked as aircraft
	 */
	public boolean getAircraft();

	/**
	 * Sets whether or not to filter only resources marked as aircraft.
	 * 
	 * @param val
	 * 		whether to return only resources marked as aircraft
	 */
	public void setAircraft(boolean val);

	/**
	 * Returns whether or not to filter only resources that are marked as crews.
	 * 
	 * @return
	 * 		the filter value on whether to return only resources marked as crews
	 */
	public boolean getCrews();

	/**
	 * Sets whether or not to filter only resources marked as crews.
	 * 
	 * @param val
	 * 		whether to return only resources marked as crews
	 */
	public void setCrews(boolean val);

	/**
	 * Returns whether or not to filter only resources that are marked as equipment.
	 * 
	 * @return
	 * 		the filter value on whether to return only resources marked as equipment
	 */
	public boolean getEquipment();

	/**
	 * Sets whether or not to filter only resources marked as equipment.
	 * 
	 * @param val
	 * 		whether to return only resources marked as equipment
	 */
	public void setEquipment(boolean val);

	/**
	 * Returns whether or not to filter only resources that are marked as personnel.
	 * 
	 * @return
	 * 		the filter value on whether to return only resources marked as personnel
	 */
	public boolean getPersonnel();

	/**
	 * Sets whether or not to filter only resources marked as personnel.
	 * 
	 * @param val
	 * 		whether to return only resources marked as personnel
	 */
	public void setPersonnel(boolean val);
	
	/**
	 * Returns list of resource types to filter by.
	 * 
	 * @return
	 * 		collection of resource types to filter
	 */
	public Collection<ResourceRequestCategoryEnum> getResourceTypeFilterList();
}
