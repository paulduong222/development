package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;

import java.util.Collection;

public interface IncidentResourceCheckboxFilter extends Filter {

	/**
	 * @return the excludeFilled
	 */
	public boolean isExcludeFilled();
	
	/**
	 * @param excludeFilled the excludeFilled to set
	 */
	public void setExcludeFilled(boolean excludeFilled);

	
	/**
	 * @return the excludeDemob
	 */
	public boolean isExcludeDemob();
	
	/**
	 * @param excludeDemob the excludeDemob to set
	 */
	public void setExcludeDemob(boolean excludeDemob);
	
	public boolean getExcludeDemob();

	/**
	 * @return the enabledObject
	 */
	public Object getEnabledObject();
	
	/**
	 * @param enabledObject the enabledObject to set
	 */
	public void setEnabledObject(Object enabledObject);
	
	/**
	 * @return the enabled
	 */
	public Boolean getEnabled();
	
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled);
	
	/**
	 * @return the overhead
	 */
	public boolean isOverhead();
	
	/**
	 * @param overhead the overhead to set
	 */
	public void setOverhead(boolean overhead);
	
	/**
	 * @return the aircraft
	 */
	public boolean isAircraft();
	
	/**
	 * @param aircraft the aircraft to set
	 */
	public void setAircraft(boolean aircraft);
	
	/**
	 * @return the personnel
	 */
	public boolean isPersonnel();
	
	/**
	 * @param personnel the personnel to set
	 */
	public void setPersonnel(boolean personnel);
	
	/**
	 * @return the crews
	 */
	public boolean isCrews();
	
	/**
	 * @param crews the crews to set
	 */
	public void setCrews(boolean crews);
	
	/**
	 * @return the equipment
	 */
	public boolean isEquipment();
	
	/**
	 * @param equipment the equipment to set
	 */
	public void setEquipment(boolean equipment);	
	
	/**
	 * @param filter
	 * @return
	 */
	public Collection<String> getReqCatList(IncidentResourceCheckboxFilter filter);
	
	/**
	 * @param filter
	 * @return
	 */
	public Collection<String> getAssignmentStatusList(IncidentResourceCheckboxFilter filter);

	/**
	 * @return the nonpersonnel
	 */
	public boolean isNonpersonnel() ;

	/**
	 * @param nonpersonnel the nonpersonnel to set
	 */
	public void setNonpersonnel(boolean nonpersonnel) ;
}
