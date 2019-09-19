package gov.nwcg.isuite.core.vo;

import java.math.BigDecimal;
import java.util.Date;

public class IncidentResourceTimePostDataVo {
	private Long incidentResourceId;
	private Long assignTimePostId;
	private Date postStartDate;
	private Date postStopDate;
	private String kindCode;
	private String rateType;
	private BigDecimal quantity;
	private BigDecimal rateAmount;
	private BigDecimal otherRate;
	private String employmentType;
	private String accountCode;
	private String rateClassName;
	private BigDecimal rateClassRate;
	private Boolean training;
	private String incidentName;
	private String incidentNumber;
	private String incidentUnitCode;
	private Boolean returnTravelStartOnly;
	private String specialPayCode;
	private Long specialPayId;
	private String regionCode;
	private Boolean halfRate;
	private BigDecimal invoicedAmount;
	
	public Long getIncidentResourceId() {
		return incidentResourceId;
	}
	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
	}
	public Long getAssignTimePostId() {
		return assignTimePostId;
	}
	public void setAssignTimePostId(Long assignTimePostId) {
		this.assignTimePostId = assignTimePostId;
	}
	public Date getPostStartDate() {
		return postStartDate;
	}
	public void setPostStartDate(Date postStartDate) {
		this.postStartDate = postStartDate;
	}
	public Date getPostStopDate() {
		return postStopDate;
	}
	public void setPostStopDate(Date postStopDate) {
		this.postStopDate = postStopDate;
	}
	public String getKindCode() {
		return kindCode;
	}
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getRateAmount() {
		return rateAmount;
	}
	public void setRateAmount(BigDecimal rateAmount) {
		this.rateAmount = rateAmount;
	}
	public BigDecimal getOtherRate() {
		return otherRate;
	}
	public void setOtherRate(BigDecimal otherRate) {
		this.otherRate = otherRate;
	}
	public String getEmploymentType() {
		return employmentType;
	}
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getRateClassName() {
		return rateClassName;
	}
	public void setRateClassName(String rateClassName) {
		this.rateClassName = rateClassName;
	}
	public BigDecimal getRateClassRate() {
		return rateClassRate;
	}
	public void setRateClassRate(BigDecimal rateClassRate) {
		this.rateClassRate = rateClassRate;
	}
	public Boolean getTraining() {
		return training;
	}
	public void setTraining(Boolean training) {
		this.training = training;
	}
	public String getIncidentName() {
		return incidentName;
	}
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}
	public String getIncidentNumber() {
		return incidentNumber;
	}
	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}
	public String getIncidentUnitCode() {
		return incidentUnitCode;
	}
	public void setIncidentUnitCode(String incidentUnitCode) {
		this.incidentUnitCode = incidentUnitCode;
	}
	public Boolean getReturnTravelStartOnly() {
		return returnTravelStartOnly;
	}
	public void setReturnTravelStartOnly(Boolean returnTravelStartOnly) {
		this.returnTravelStartOnly = returnTravelStartOnly;
	}
	public String getSpecialPayCode() {
		return specialPayCode;
	}
	public void setSpecialPayCode(String specialPayCode) {
		this.specialPayCode = specialPayCode;
	}
	public Long getSpecialPayId() {
		return specialPayId;
	}
	public void setSpecialPayId(Long specialPayId) {
		this.specialPayId = specialPayId;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public Boolean getHalfRate() {
		return halfRate;
	}
	public void setHalfRate(Boolean halfRate) {
		this.halfRate = halfRate;
	}
	public BigDecimal getInvoicedAmount() {
		return invoicedAmount;
	}
	public void setInvoicedAmount(BigDecimal invoicedAmount) {
		this.invoicedAmount = invoicedAmount;
	}
	

}
