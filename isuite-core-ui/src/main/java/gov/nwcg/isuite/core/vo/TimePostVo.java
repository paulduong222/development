package gov.nwcg.isuite.core.vo;

import java.math.BigDecimal;
import java.util.Date;

public class TimePostVo {
	private Long id = 0L;
	private Long assignmentTimeId;
	private String employmentType;
	private Long incidentAccountCodeId;
	private Date postStartDate;
	private Date postStopDate;
	private BigDecimal otherRate;
	private String rateType;
	private String unitOfMeasure;
	private BigDecimal rateAmount;
	private BigDecimal quantity;
	private BigDecimal guaranteeAmount;
	private Boolean primaryPosting;
	private Boolean specialPosting;
	private Boolean guaranteePosting;
	private String contractorPostType;
	private Long contractorRateId;
	private Boolean halfRate;
	private String specialPayCode;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the assignmentTimeId
	 */
	public Long getAssignmentTimeId() {
		return assignmentTimeId;
	}
	/**
	 * @param assignmentTimeId the assignmentTimeId to set
	 */
	public void setAssignmentTimeId(Long assignmentTimeId) {
		this.assignmentTimeId = assignmentTimeId;
	}
	/**
	 * @return the employmentType
	 */
	public String getEmploymentType() {
		return employmentType;
	}
	/**
	 * @param employmentType the employmentType to set
	 */
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}
	/**
	 * @return the incidentAccountCodeId
	 */
	public Long getIncidentAccountCodeId() {
		return incidentAccountCodeId;
	}
	/**
	 * @param incidentAccountCodeId the incidentAccountCodeId to set
	 */
	public void setIncidentAccountCodeId(Long incidentAccountCodeId) {
		this.incidentAccountCodeId = incidentAccountCodeId;
	}
	/**
	 * @return the postStartDate
	 */
	public Date getPostStartDate() {
		return postStartDate;
	}
	/**
	 * @param postStartDate the postStartDate to set
	 */
	public void setPostStartDate(Date postStartDate) {
		this.postStartDate = postStartDate;
	}
	/**
	 * @return the otherRate
	 */
	public BigDecimal getOtherRate() {
		return otherRate;
	}
	/**
	 * @param otherRate the otherRate to set
	 */
	public void setOtherRate(BigDecimal otherRate) {
		this.otherRate = otherRate;
	}
	/**
	 * @return the rateType
	 */
	public String getRateType() {
		return rateType;
	}
	/**
	 * @param rateType the rateType to set
	 */
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	/**
	 * @return the unitOfMeasure
	 */
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}
	/**
	 * @param unitOfMeasure the unitOfMeasure to set
	 */
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	/**
	 * @return the rateAmount
	 */
	public BigDecimal getRateAmount() {
		return rateAmount;
	}
	/**
	 * @param rateAmount the rateAmount to set
	 */
	public void setRateAmount(BigDecimal rateAmount) {
		this.rateAmount = rateAmount;
	}
	/**
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the guaranteeAmount
	 */
	public BigDecimal getGuaranteeAmount() {
		return guaranteeAmount;
	}
	/**
	 * @param guaranteeAmount the guaranteeAmount to set
	 */
	public void setGuaranteeAmount(BigDecimal guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}
	/**
	 * @return the primaryPosting
	 */
	public Boolean getPrimaryPosting() {
		return primaryPosting;
	}
	/**
	 * @param primaryPosting the primaryPosting to set
	 */
	public void setPrimaryPosting(Boolean primaryPosting) {
		this.primaryPosting = primaryPosting;
	}
	/**
	 * @return the specialPosting
	 */
	public Boolean getSpecialPosting() {
		return specialPosting;
	}
	/**
	 * @param specialPosting the specialPosting to set
	 */
	public void setSpecialPosting(Boolean specialPosting) {
		this.specialPosting = specialPosting;
	}
	/**
	 * @return the guaranteePosting
	 */
	public Boolean getGuaranteePosting() {
		return guaranteePosting;
	}
	/**
	 * @param guaranteePosting the guaranteePosting to set
	 */
	public void setGuaranteePosting(Boolean guaranteePosting) {
		this.guaranteePosting = guaranteePosting;
	}
	/**
	 * @return the contractorPostType
	 */
	public String getContractorPostType() {
		return contractorPostType;
	}
	/**
	 * @param contractorPostType the contractorPostType to set
	 */
	public void setContractorPostType(String contractorPostType) {
		this.contractorPostType = contractorPostType;
	}
	/**
	 * @return the contractorRateId
	 */
	public Long getContractorRateId() {
		return contractorRateId;
	}
	/**
	 * @param contractorRateId the contractorRateId to set
	 */
	public void setContractorRateId(Long contractorRateId) {
		this.contractorRateId = contractorRateId;
	}
	/**
	 * @return the halfRate
	 */
	public Boolean getHalfRate() {
		return halfRate;
	}
	/**
	 * @param halfRate the halfRate to set
	 */
	public void setHalfRate(Boolean halfRate) {
		this.halfRate = halfRate;
	}
	public Date getPostStopDate() {
		return postStopDate;
	}
	public void setPostStopDate(Date postStopDate) {
		this.postStopDate = postStopDate;
	}
	public String getSpecialPayCode() {
		return specialPayCode;
	}
	public void setSpecialPayCode(String specialPayCode) {
		this.specialPayCode = specialPayCode;
	}
	
	

}
