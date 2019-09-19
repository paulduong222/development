package gov.nwcg.isuite.core.domain.views.impl;

import gov.nwcg.isuite.core.domain.views.CRPersonPostView;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "iswv_cr_person_post")
public class CRPersonPostViewImpl implements CRPersonPostView {
	
	@Id
	@Column(name = "ACCOUNTING_CODE", insertable = false, updatable = false)
	private String accountCode;

	@Column(name = "INCIDENT_ID", insertable = false, updatable = false)
	private Long incidentId;
	
	@Column(name = "ITEM_CODE", insertable = false, updatable = false)
	private String itemCode;

	@Column(name = "ITEM_CODE_DESCRIPTION", insertable = false, updatable = false)
	private String itemCodeDescription;

	@Column(name = "NUMBER_OF_HOURS", insertable = false, updatable = false)
	private BigDecimal numberOfHours;

	@Column(name = "RATE_AMOUNT", insertable = false, updatable = false)
	private BigDecimal rateAmount;

	@Column(name = "RATE_CLASS", insertable = false, updatable = false)
	private String rateClass;

	@Column(name = "REQUEST_NUMBER", insertable = false, updatable = false)
	private String requestNumber;

	@Column(name = "RESOURCE_NAME", insertable = false, updatable = false)
	private String resourceName;

	@Column(name = "SECTION_CODE", insertable = false, updatable = false)
	private String sectionCode;

	@Column(name = "SECTION_CODE_DESCRIPTION", insertable = false, updatable = false)
	private String sectionCodeDescription;

	@Column(name = "START_DATE", insertable = false, updatable = false)
	private Date startDate;

	@Column(name = "START_TIME", insertable = false, updatable = false)
	private Date startTime;

	@Column(name = "STOP_DATE", insertable = false, updatable = false)
	private Date stopDate;

	@Column(name = "STOP_TIME", insertable = false, updatable = false)
	private Date stopTime;
	
	public CRPersonPostViewImpl() {
	}
	
	public Long getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemCodeDescription() {
		return itemCodeDescription;
	}

	public void setItemCodeDescription(String itemCodeDescription) {
		this.itemCodeDescription = itemCodeDescription;
	}

	public BigDecimal getNumberOfHours() {
		return numberOfHours;
	}

	public void setNumberOfHours(BigDecimal numberOfHours) {
		this.numberOfHours = numberOfHours;
	}

	public BigDecimal getRateAmount() {
		return rateAmount;
	}

	public void setRateAmount(BigDecimal rateAmount) {
		this.rateAmount = rateAmount;
	}

	public String getRateClass() {
		return rateClass;
	}

	public void setRateClass(String rateClass) {
		this.rateClass = rateClass;
	}

	public String getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}

	public String getSectionCodeDescription() {
		return sectionCodeDescription;
	}

	public void setSectionCodeDescription(String sectionCodeDescription) {
		this.sectionCodeDescription = sectionCodeDescription;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStopDate() {
		return stopDate;
	}

	public void setStopDate(Date stopDate) {
		this.stopDate = stopDate;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}
	
}
