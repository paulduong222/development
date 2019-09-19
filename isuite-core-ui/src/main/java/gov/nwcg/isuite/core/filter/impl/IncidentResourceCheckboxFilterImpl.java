package gov.nwcg.isuite.core.filter.impl;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.filter.IncidentResourceCheckboxFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.ResourceRequestCategoryEnum;

public class IncidentResourceCheckboxFilterImpl extends FilterImpl implements IncidentResourceCheckboxFilter {

	private static final long serialVersionUID = -2729552930817864670L;

	private boolean excludeFilled=false;
	private boolean excludeDemob=false;
	private Object enabledObject;
	private Boolean enabled=true;	
	private boolean overhead=false;
	private boolean aircraft=false;
	private boolean personnel=false;
	private boolean nonpersonnel=false;
	private boolean crews=false;
	private boolean equipment=false;
	
	public IncidentResourceCheckboxFilterImpl() {
		
	}
	
	/**
	 * @return the excludeFilled
	 */
	public boolean isExcludeFilled() {
		return excludeFilled;
	}
	/**
	 * @param excludeFilled the excludeFilled to set
	 */
	public void setExcludeFilled(boolean excludeFilled) {
		this.excludeFilled = excludeFilled;
	}
	/**
	 * @return the excludeDemob
	 */
	public boolean isExcludeDemob() {
		return excludeDemob;
	}
	/**
	 * @param excludeDemob the excludeDemob to set
	 */
	public void setExcludeDemob(boolean excludeDemob) {
		this.excludeDemob = excludeDemob;
	}
	
	public boolean getExcludeDemob(){
		return this.excludeDemob;
	}
	
	/**
	 * @return the enabledObject
	 */
	public Object getEnabledObject() {
		return enabledObject;
	}
	/**
	 * @param enabledObject the enabledObject to set
	 */
	public void setEnabledObject(Object enabledObject) {
		
		this.enabledObject = enabledObject;
		
		if(this.enabledObject == Boolean.TRUE) {
			this.setEnabled(true);
		} else if(this.enabledObject == Boolean.FALSE) {
			this.setEnabled(false);
		} else {
			this.setEnabled(null);
		}
	}
	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	/**
	 * @return the overhead
	 */
	public boolean isOverhead() {
		return overhead;
	}
	/**
	 * @param overhead the overhead to set
	 */
	public void setOverhead(boolean overhead) {
		this.overhead = overhead;
	}
	/**
	 * @return the aircraft
	 */
	public boolean isAircraft() {
		return aircraft;
	}
	/**
	 * @param aircraft the aircraft to set
	 */
	public void setAircraft(boolean aircraft) {
		this.aircraft = aircraft;
	}
	/**
	 * @return the personnel
	 */
	public boolean isPersonnel() {
		return personnel;
	}
	/**
	 * @param personnel the personnel to set
	 */
	public void setPersonnel(boolean personnel) {
		this.personnel = personnel;
	}
	/**
	 * @return the crews
	 */
	public boolean isCrews() {
		return crews;
	}
	/**
	 * @param crews the crews to set
	 */
	public void setCrews(boolean crews) {
		this.crews = crews;
	}
	/**
	 * @return the equipment
	 */
	public boolean isEquipment() {
		return equipment;
	}
	/**
	 * @param equipment the equipment to set
	 */
	public void setEquipment(boolean equipment) {
		this.equipment = equipment;
	}

	/**
	 * @return the serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	public Collection<String> getReqCatList(IncidentResourceCheckboxFilter filter) {
		Collection<String> reqCatList = new ArrayList<String>();
		
		if(filter.isPersonnel())reqCatList.add(ResourceRequestCategoryEnum.O.name());
		if(filter.isOverhead())reqCatList.add(ResourceRequestCategoryEnum.O.name());
		if(filter.isAircraft())reqCatList.add(ResourceRequestCategoryEnum.A.name());
		if(filter.isCrews())reqCatList.add(ResourceRequestCategoryEnum.C.name());
		if(filter.isEquipment())reqCatList.add(ResourceRequestCategoryEnum.E.name());
		
		return reqCatList;
	}
	
	public Collection<String> getAssignmentStatusList(IncidentResourceCheckboxFilter filter) {
		Collection<String> statusList = new ArrayList<String>();
		
		if(filter.isExcludeFilled())statusList.add(AssignmentStatusTypeEnum.F.name());
		if(filter.isExcludeDemob())statusList.add(AssignmentStatusTypeEnum.D.name());
		
		return statusList;
	}

	/**
	 * @return the nonpersonnel
	 */
	public boolean isNonpersonnel() {
		return nonpersonnel;
	}

	/**
	 * @param nonpersonnel the nonpersonnel to set
	 */
	public void setNonpersonnel(boolean nonpersonnel) {
		this.nonpersonnel = nonpersonnel;
	}
	
}
