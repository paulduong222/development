package gov.nwcg.isuite.core.filter.impl;

import java.util.Date;

import gov.nwcg.isuite.core.filter.OtherResourceCostFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

public class OtherResourceCostFilterImpl extends FilterImpl implements OtherResourceCostFilter {
	
	private static final long serialVersionUID = -1245778567733385090L;
	
	private Long incidentId = 0L;
	private Long incidentGroupId = 0L;
	private Long incidentResourceOtherId = 0L;
	private String requestNumber;
	private String costDescription;
	private String itemName;
	private String itemCode;
	private String agency;
	private String paymentAgency;
	private Date assignDate;
	private Date actualReleaseDate;
	private String status;
	private String remarks;
	private String accrualCode;
	private String incidentName;
	private String incidentNumber;
	private String crypticDateFilterAssignDate;
	private String crypticDateFilterActualReleaseDate;
	
	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}
	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}
	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}
	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}
	
	/**
	 * @return the requestNumber
	 */
	public String getRequestNumber() {
		return requestNumber;
	}
	/**
	 * @param requestNumber the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}
	/**
	 * @return the costDescription
	 */
	public String getCostDescription() {
		return costDescription;
	}
	/**
	 * @param costDescription the costDescription to set
	 */
	public void setCostDescription(String costDescription) {
		this.costDescription = costDescription;
	}
	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}
	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	/**
	 * @return the agency
	 */
	public String getAgency() {
		return agency;
	}
	/**
	 * @param agency the agency to set
	 */
	public void setAgency(String agency) {
		this.agency = agency;
	}
	/**
	 * @return the paymentAgency
	 */
	public String getPaymentAgency() {
		return paymentAgency;
	}
	/**
	 * @param paymentAgency the paymentAgency to set
	 */
	public void setPaymentAgency(String paymentAgency) {
		this.paymentAgency = paymentAgency;
	}
	/**
	 * @return the assignDate
	 */
	public Date getAssignDate() {
		return assignDate;
	}
	/**
	 * @param assignDate the assignDate to set
	 */
	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}
	/**
	 * @return the actualReleaseDate
	 */
	public Date getActualReleaseDate() {
		return actualReleaseDate;
	}
	/**
	 * @param actualReleaseDate the actualReleaseDate to set
	 */
	public void setActualReleaseDate(Date actualReleaseDate) {
		this.actualReleaseDate = actualReleaseDate;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the accrualCode
	 */
	public String getAccrualCode() {
		return accrualCode;
	}
	/**
	 * @param accrualCode the accrualCode to set
	 */
	public void setAccrualCode(String accrualCode) {
		this.accrualCode = accrualCode;
	}
	/**
	 * @return the incidentName
	 */
	public String getIncidentName() {
		return incidentName;
	}
	/**
	 * @param incidentName the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}
	/**
	 * @return the incidentNumber
	 */
	public String getIncidentNumber() {
		return incidentNumber;
	}
	/**
	 * @param incidentNumber the incidentNumber to set
	 */
	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}
	/**
	 * @return the crypticDateFilterAssignDate
	 */
	public String getCrypticDateFilterAssignDate() {
		return crypticDateFilterAssignDate;
	}
	/**
	 * @param crypticDateFilterAssignDate the crypticDateFilterAssignDate to set
	 */
	public void setCrypticDateFilterAssignDate(String crypticDateFilterAssignDate) {
		this.crypticDateFilterAssignDate = crypticDateFilterAssignDate;
	}
	/**
	 * @return the crypticDateFilterActualReleaseDate
	 */
	public String getCrypticDateFilterActualReleaseDate() {
		return crypticDateFilterActualReleaseDate;
	}
	/**
	 * @param crypticDateFilterActualReleaseDate the crypticDateFilterActualReleaseDate to set
	 */
	public void setCrypticDateFilterActualReleaseDate(
			String crypticDateFilterActualReleaseDate) {
		this.crypticDateFilterActualReleaseDate = crypticDateFilterActualReleaseDate;
	}
	/**
	 * @return the incidentResourceOtherId
	 */
	public Long getIncidentResourceOtherId() {
		return incidentResourceOtherId;
	}
	/**
	 * @param incidentResourceOtherId the incidentResourceOtherId to set
	 */
	public void setIncidentResourceOtherId(Long incidentResourceOtherId) {
		this.incidentResourceOtherId = incidentResourceOtherId;
	}
}
