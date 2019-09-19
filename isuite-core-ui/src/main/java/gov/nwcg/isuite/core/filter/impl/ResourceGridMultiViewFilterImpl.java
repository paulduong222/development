package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.ResourceGridMultiViewFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.framework.types.ResourceRequestCategoryEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This is the filter implementation for filtering out how the data is pulled for
 * the resource grid.
 * 
 * @author dprice
 */
public class ResourceGridMultiViewFilterImpl extends FilterImpl implements ResourceGridMultiViewFilter {

	private boolean assignedToIncident=false;
	private boolean permanent=false;
	private boolean overhead=false;
	private boolean aircraft=false;
	private boolean personnel=false;
	private boolean crews=false;
	private boolean equipment=false;
	
	public ResourceGridMultiViewFilterImpl(){
		
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.ResourceFilter#getAssignedToIncident()
	 */
	public boolean getAssignedToIncident() {
		return assignedToIncident;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.ResourceFilter#setAssignedToIncident(boolean)
	 */
	public void setAssignedToIncident(boolean assignedToIncident) {
		this.assignedToIncident = assignedToIncident;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.ResourceGridFilter#getPermanent()
	 */
	public boolean getPermanent() {
		return permanent;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.ResourceGridFilter#setPermanent(boolean)
	 */
	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.ResourceGridFilter#getOverhead()
	 */
	public boolean getOverhead() {
		return overhead;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.ResourceGridFilter#setOverhead(boolean)
	 */
	public void setOverhead(boolean overhead) {
		this.overhead = overhead;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.ResourceGridMultiViewFilter#getAircraft()
	 */
	public boolean getAircraft() {
		return aircraft;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.ResourceGridMultiViewFilter#setAircraft(boolean)
	 */
	public void setAircraft(boolean aircraft) {
		this.aircraft = aircraft;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.ResourceGridMultiViewFilter#getPersonnel()
	 */
	public boolean getPersonnel() {
		return personnel;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.ResourceGridMultiViewFilter#setPersonnel(boolean)
	 */
	public void setPersonnel(boolean personnel) {
		this.personnel = personnel;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.ResourceGridMultiViewFilter#getCrews()
	 */
	public boolean getCrews() {
		return crews;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.ResourceGridMultiViewFilter#setCrews(boolean)
	 */
	public void setCrews(boolean crews) {
		this.crews = crews;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.ResourceGridMultiViewFilter#getEquipment()
	 */
	public boolean getEquipment() {
		return equipment;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.ResourceGridMultiViewFilter#setEquipment(boolean)
	 */
	public void setEquipment(boolean equipment) {
		this.equipment = equipment;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.resource.ResourceGridMultiViewFilter#getResourceTypeFilterList()
	 */
	public Collection<ResourceRequestCategoryEnum> getResourceTypeFilterList(){
		List<ResourceRequestCategoryEnum> list = new ArrayList<ResourceRequestCategoryEnum>();
		
		if(getOverhead())
				list.add(ResourceRequestCategoryEnum.O);
		if(getAircraft())
				list.add(ResourceRequestCategoryEnum.A);
//		if(getPersonnel())
//			list.add(ResourceRequestCategoryEnum.P);
		if(getCrews())
			list.add(ResourceRequestCategoryEnum.C);
		if(getEquipment())
			list.add(ResourceRequestCategoryEnum.E);

		return list;
	}

	
}