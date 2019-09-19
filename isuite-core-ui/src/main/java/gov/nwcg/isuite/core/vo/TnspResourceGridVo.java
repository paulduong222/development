package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;

public class TnspResourceGridVo extends AbstractVo {
	
	private String itemCode;
	private String itemDescription;
	private Long itemCodeId;
	private String status;
	private Boolean active;
	private String requestNumber;
	private String resourceName;
	private String sortRequestNumberField;
	private Long organizationId;
	
	
	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}
	/**
	 * @param itemDescription the itemDescription to set
	 */
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	/**
	 * @return the itemDescription
	 */
	public String getItemDescription() {
		return itemDescription;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}
	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}
	/**
	 * @param requestNumber the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}
	/**
	 * @return the requestNumber
	 */
	public String getRequestNumber() {
		return requestNumber;
	}
	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}
	/**
	 * @param sortRequestNumberField the sortRequestNumberField to set
	 */
	public void setSortRequestNumberField(String sortRequestNumberField) {
		this.sortRequestNumberField = sortRequestNumberField;
	}
	/**
	 * @return the sortRequestNumberField
	 */
	public String getSortRequestNumberField() {
		return sortRequestNumberField;
	}
	/**
	 * @param itemCodeId the itemCodeId to set
	 */
	public void setItemCodeId(Long itemCodeId) {
		this.itemCodeId = itemCodeId;
	}
	/**
	 * @return the itemCodeId
	 */
	public Long getItemCodeId() {
		return itemCodeId;
	}
	/**
	 * @param organizationId the organizationId to set
	 */
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	/**
	 * @return the organizationId
	 */
	public Long getOrganizationId() {
		return organizationId;
	}
	
}
