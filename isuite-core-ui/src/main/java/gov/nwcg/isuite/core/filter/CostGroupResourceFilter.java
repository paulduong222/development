package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;

public interface CostGroupResourceFilter extends Filter {
	
	/**
	 * Returns the costGroupId.
	 * 
	 * @return
	 * 		the costGroupId to return
	 */
	public Long getCostGroupId();
	
	/**
	 * Sets the costGroupId.
	 * 
	 * @param costGroupId
	 * 		the costGroupId to set
	 */
	public void setCostGroupId(Long costGroupId);
	
	/**
	 * Returns the requestNumber.
	 * 
	 * @return
	 * 		the requestNumber to return
	 */
	public String getRequestNumber();
	
	/**
	 * Sets the requestNumber.
	 * 
	 * @param requestNumber
	 * 		the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber);
	
	/**
	 * Returns the resourceName.
	 * 
	 * @return
	 * 		the resourceName to return
	 */
	public String getResourceName();
	
	/**
	 * Sets the resourceName.
	 * 
	 * @param resourceName
	 * 		the resourceName to set
	 */
	public void setResourceName(String resourceName);
	
	/**
	 * Returns the itemCode.
	 * 
	 * @return
	 * 		the itemCode to return
	 */
	public String getItemCode();
	
	/**
	 * Sets the itemCode.
	 * 
	 * @param itemCode
	 * 		the itemCode to set
	 */
	public void setItemCode(String itemCode);
	
	/**
	 * Returns the agency.
	 * 
	 * @return
	 * 		the agency to return
	 */
	public String getAgency();
	
	/**
	 * Sets the agency.
	 * 
	 * @param agency
	 * 		the agency to set
	 */
	public void setAgency(String agency);
	
	/**
	 * Returns the unitId.
	 * 
	 * @return
	 * 		the unitId to return
	 */
	public String getUnitId();
	
	/**
	 * Sets the unitId.
	 * 
	 * @param unitId
	 * 		the unitId to set
	 */
	public void setUnitId(String unitId);
	
	/**
	 * Sets the checkboxFilter.
	 * 
	 * @param checkboxFilter
	 * 		the checkboxFilter to set
	 */
	public void setCheckboxFilter(IncidentResourceCheckboxFilter checkboxFilter);
	
	/**
	 * Returns the checkboxFilter
	 * 
	 * @return
	 * 		the checkboxFilter to return
	 */
	public IncidentResourceCheckboxFilter getCheckboxFilter();

}
