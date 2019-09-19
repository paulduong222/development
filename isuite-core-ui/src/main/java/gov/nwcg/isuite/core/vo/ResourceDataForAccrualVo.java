package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.util.StringUtility;

public class ResourceDataForAccrualVo {
	private Long incidentResourceId;
	private Long resourceId;
	private Long costDataId;
	private String accrualLocked;
	private String accrualCode;
	private String incidentAgency;
	private String incidentState;
	private String resourcePaymentAgency;
	private String resourceAgencyCode;
	private String resourceAgencyState;
	private String resourceEmploymentType;
	private String resourceItemCode;
	private String resourceItemCodeCategory;
	private String resourceUnitCode;
	private String incidentUnitCode;
	private Integer resourceCostCount;
	
	/**
	 * @return the incidentResourceId
	 */
	public Long getIncidentResourceId() {
		return incidentResourceId;
	}
	/**
	 * @param incidentResourceId the incidentResourceId to set
	 */
	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
	}
	/**
	 * @return the costDataId
	 */
	public Long getCostDataId() {
		return costDataId;
	}
	/**
	 * @param costDataId the costDataId to set
	 */
	public void setCostDataId(Long costDataId) {
		this.costDataId = costDataId;
	}
	/**
	 * @return the accrualLocked
	 */
	public String getAccrualLocked() {
		return accrualLocked;
	}
	/**
	 * @param accrualLocked the accrualLocked to set
	 */
	public void setAccrualLocked(String accrualLocked) {
		this.accrualLocked = accrualLocked;
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
	 * @return the incidentAgency
	 */
	public String getIncidentAgency() {
		return incidentAgency;
	}
	/**
	 * @param incidentAgency the incidentAgency to set
	 */
	public void setIncidentAgency(String incidentAgency) {
		this.incidentAgency = incidentAgency;
	}
	/**
	 * @return the resourcePaymentAgency
	 */
	public String getResourcePaymentAgency() {
		return resourcePaymentAgency;
	}
	/**
	 * @param resourcePaymentAgency the resourcePaymentAgency to set
	 */
	public void setResourcePaymentAgency(String resourcePaymentAgency) {
		this.resourcePaymentAgency = resourcePaymentAgency;
	}
	/**
	 * @return the resourceAgencyCode
	 */
	public String getResourceAgencyCode() {
		return resourceAgencyCode;
	}
	/**
	 * @param resourceAgencyCode the resourceAgencyCode to set
	 */
	public void setResourceAgencyCode(String resourceAgencyCode) {
		this.resourceAgencyCode = resourceAgencyCode;
	}
	/**
	 * @return the resourceAgencyState
	 */
	public String getResourceAgencyState() {
		return resourceAgencyState;
	}
	/**
	 * @param resourceAgencyState the resourceAgencyState to set
	 */
	public void setResourceAgencyState(String resourceAgencyState) {
		this.resourceAgencyState = resourceAgencyState;
	}
	/**
	 * @return the resourceEmploymentType
	 */
	public String getResourceEmploymentType() {
		return resourceEmploymentType;
	}
	/**
	 * @param resourceEmploymentType the resourceEmploymentType to set
	 */
	public void setResourceEmploymentType(String resourceEmploymentType) {
		this.resourceEmploymentType = resourceEmploymentType;
	}
	/**
	 * @return the resourceItemCode
	 */
	public String getResourceItemCode() {
		return resourceItemCode;
	}
	/**
	 * @param resourceItemCode the resourceItemCode to set
	 */
	public void setResourceItemCode(String resourceItemCode) {
		this.resourceItemCode = resourceItemCode;
	}
	/**
	 * @return the resourceItemCodeCategory
	 */
	public String getResourceItemCodeCategory() {
		return resourceItemCodeCategory;
	}
	/**
	 * @param resourceItemCodeCategory the resourceItemCodeCategory to set
	 */
	public void setResourceItemCodeCategory(String resourceItemCodeCategory) {
		this.resourceItemCodeCategory = resourceItemCodeCategory;
	}
	/**
	 * @return the resourceUnitCode
	 */
	public String getResourceUnitCode() {
		return resourceUnitCode;
	}
	/**
	 * @param resourceUnitCode the resourceUnitCode to set
	 */
	public void setResourceUnitCode(String resourceUnitCode) {
		this.resourceUnitCode = resourceUnitCode;
	}
	/**
	 * @return the incidentUnitCode
	 */
	public String getIncidentUnitCode() {
		return incidentUnitCode;
	}
	/**
	 * @param incidentUnitCode the incidentUnitCode to set
	 */
	public void setIncidentUnitCode(String incidentUnitCode) {
		this.incidentUnitCode = incidentUnitCode;
	}

	public Boolean getIsAccrualLocked(){
		if(StringUtility.hasValue(this.accrualLocked)){
			if(this.accrualCode.equalsIgnoreCase("1") || this.accrualCode.equalsIgnoreCase("true")){
				return true;
			}else
				return false;
		}else
			return false;
	}

	public Boolean getIsResourceAgencyState(){
		if(StringUtility.hasValue(this.resourceAgencyState)){
			if(this.resourceAgencyState.equalsIgnoreCase("1") || this.resourceAgencyState.equalsIgnoreCase("true")){
				return true;
			}else
				return false;
		}else
			return false;
	}
	/**
	 * @return the incidentState
	 */
	public String getIncidentState() {
		return incidentState;
	}
	/**
	 * @param incidentState the incidentState to set
	 */
	public void setIncidentState(String incidentState) {
		this.incidentState = incidentState;
	}
	/**
	 * @return the resourceId
	 */
	public Long getResourceId() {
		return resourceId;
	}
	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	/**
	 * @return the resourceCostCount
	 */
	public Integer getResourceCostCount() {
		return resourceCostCount;
	}
	/**
	 * @param resourceCostCount the resourceCostCount to set
	 */
	public void setResourceCostCount(Integer resourceCostCount) {
		this.resourceCostCount = resourceCostCount;
	}
}
