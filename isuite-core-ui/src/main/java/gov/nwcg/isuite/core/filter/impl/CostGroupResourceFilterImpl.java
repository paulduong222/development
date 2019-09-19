package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.CostGroupResourceFilter;
import gov.nwcg.isuite.core.filter.IncidentResourceCheckboxFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

public class CostGroupResourceFilterImpl extends FilterImpl implements CostGroupResourceFilter {

	private static final long serialVersionUID = 5969560626033811370L;
	private Long costGroupId = 0L;
	private String requestNumber;
	private String resourceName;
	private String itemCode;
	private String agency;
	private String unitId;
	private IncidentResourceCheckboxFilter checkboxFilter;
	
	/**
	 * Returns the costGroupId.
	 * 
	 * @return
	 * 		the costGroupId to return
	 */
	public Long getCostGroupId() {
		return costGroupId;
	}
	
	/**
	 * Sets the costGroupId.
	 * 
	 * @param costGroupId
	 * 		the costGroupId to set
	 */
	public void setCostGroupId(Long costGroupId) {
		this.costGroupId = costGroupId;
	}
	
	/**
	 * Returns the requestNumber.
	 * 
	 * @return
	 * 		the requestNumber to return
	 */
	public String getRequestNumber() {
		return requestNumber;
	}
	
	/**
	 * Sets the requestNumber.
	 * 
	 * @param requestNumber
	 * 		the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}
	
	/**
	 * Returns the resourceName.
	 * 
	 * @return
	 * 		the resourceName to return
	 */
	public String getResourceName() {
		return resourceName;
	}
	
	/**
	 * Sets the resourceName.
	 * 
	 * @param resourceName
	 * 		the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
	/**
	 * Returns the itemCode.
	 * 
	 * @return
	 * 		the itemCode to return
	 */
	public String getItemCode() {
		return itemCode;
	}
	
	/**
	 * Sets the itemCode.
	 * 
	 * @param itemCode
	 * 		the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	/**
	 * Returns the agency.
	 * 
	 * @return
	 * 		the agency to return
	 */
	public String getAgency() {
		return agency;
	}
	
	/**
	 * Sets the agency.
	 * 
	 * @param agency
	 * 		the agency to set
	 */
	public void setAgency(String agency) {
		this.agency = agency;
	}
	
	/**
	 * Returns the unitId.
	 * 
	 * @return
	 * 		the unitId to return
	 */
	public String getUnitId() {
		return unitId;
	}
	
	/**
	 * Sets the unitId.
	 * 
	 * @param unitId
	 * 		the unitId to set
	 */
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	/**
	 * Sets the checkboxFilter.
	 * 
	 * @param checkboxFilter
	 * 		the checkboxFilter to set
	 */
	public void setCheckboxFilter(IncidentResourceCheckboxFilter checkboxFilter) {
		this.checkboxFilter = checkboxFilter;
	}

	/**
	 * Returns the checkboxFilter
	 * 
	 * @return
	 * 		the checkboxFilter to return
	 */
	public IncidentResourceCheckboxFilter getCheckboxFilter() {
		return checkboxFilter;
	}
	
	
}
