package gov.nwcg.isuite.core.domain.impl;


import gov.nwcg.isuite.core.domain.ScratchReportTime;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@SequenceGenerator(name="SEQ_SCRATCH_REPORT_TIME", sequenceName="SEQ_SCRATCH_REPORT_TIME")
@Table(name="isw_scratch_report_time")

public class ScratchReportTimeImpl implements ScratchReportTime {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_SCRATCH_REPORT_TIME")
	private Long id = 0L;

	@Column(name="TRANSACTION_ID")
	private Long transactionId;
	
	@Column(name="INCIDENT_ID")
	private Long incidentId;
	
	@Column(name="INCIDENT_NAME", length=200) 
	private String incidentName;
	
	@Column(name="INCIDENT_NUMBER", length=200) 
	private String incidentNumber;
	
	@Column(name="INCIDENT_START_DATE") 
	private Date incidentStartDate;
	
	@Column(name="INCIDENT_GROUP_ID")
	private Long incidentGroupId;
	
	@Column(name="INCIDENT_GROUP_NAME", length=65)
	private String incidentGroupName;
	
	@Column(name="REQUEST_NUMBER", length=20)
	private String requestNumber;
	
	@Column(name="RESOURCE_ID")
	private Long resourceId;
	
	@Column(name="LAST_NAME", length=35)
	private String resourceLastName;
	              
	@Column(name="FIRST_NAME", length=35)
	private String resourceFirstName;

	@Column(name="IS_CONTRACTED")
	private Boolean contracted;

	@Column(name="ITEM_CODE", length=10)
	private String itemCode;
	
	@Column(name="SECTION_CODE", length=10)
	private String sectionCode;
	
	@Column(name="SECTION_NAME", length=75)
	private String sectionName;
	
	@Column(name="STATUS", length=10)
	private String status;
	
	@Column(name="SHIFT_START_DATE") 
	private Date shiftStartDate;
	
	@Column(name="SHIFT_END_DATE") 
	private Date shiftEndDate;

	@Column(name="HOURS_OF_WORK",precision=22,scale=2) 
	private BigDecimal hoursOfWork;

	@Column(name="HOURS_OF_REST",precision=22,scale=2) 
	private BigDecimal hoursOfRest;

	@Column(name="MITIGATION_NEEDED",precision=22,scale=2) 
	private BigDecimal mitigationNeeded;
	
	@Column(name="CREATED_DATE",insertable=false, updatable=false) 
	private Date createdDate;
	
	/* 
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.Persistable#getId()
	 */
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.Persistable#setId(java.lang.Long)
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.scratchReportTime.ScratchReportTime#getTransactionId()
	 */
	public Long getTransactionId() {
		return transactionId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.scratchReportTime.ScratchReportTime#setTransactionId()(java.lang.Long)
	 */
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
		
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.scratchReportTime.ScratchReportTime#getIncidentId()
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.scratchReportTime.ScratchReportTime#setIncidentId()(java.lang.Long)
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.scratchReportTime.ScratchReportTime#getIncidentName()
	 */
	public String getIncidentName() {
		return incidentName;
	}
	   
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.scratchReportTime.ScratchReportTime#setIncidentName()(java.lang.String)
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.scratchReportTime.ScratchReportTime#getIncidentStartDate()
	 */
	public Date getIncidentStartDate() {
		return incidentStartDate;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.scratchReportTime.ScratchReportTime#setIncidentStartDate()(java.util.Date)
	 */
	public void setIncidentStartDate(Date incidentStartDate) {
		this.incidentStartDate = incidentStartDate;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.scratchReportTime.ScratchReportTime#getIncidentGroupId()
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.scratchReportTime.ScratchReportTime#setIncidentGroupId()(java.lang.Long)
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.scratchReportTime.ScratchReportTime#getIncidentGroupName()
	 */
	public String getIncidentGroupName() {
		return incidentGroupName;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.scratchReportTime.ScratchReportTime#setIncidentName()(java.lang.String)
	 */
	public void setIncidentGroupName(String incidentGroupName) {
		this.incidentGroupName = incidentGroupName;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.scratchReportTime.ScratchReportTime#getRequestNumber()
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.scratchReportTime.ScratchReportTime#setRequestNumber()(java.lang.String)
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.scratchReportTime.ScratchReportTime#getResourceId()
	 */
	public Long getResourceId() {
		return resourceId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.scratchReportTime.ScratchReportTime#setResourceId()(java.lang.Long)
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.scratchReportTime.ScratchReportTime#getResourceLastName()
	 */
	public String getResourceLastName() {
		return resourceLastName;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.scratchReportTime.ScratchReportTime#setResourceLastName()(java.lang.String)
	 */
	public void setResourceLastName(String resourceLastName) {
		this.resourceLastName = resourceLastName;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.scratchReportTime.ScratchReportTime#getResourceFirstName()
	 */
	public String getResourceFirstName() {
		return resourceFirstName;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.scratchReportTime.ScratchReportTime#setResourceFirstName()(java.lang.String)
	 */
	public void setResourceFirstName(String resourceFirstname) {
		this.resourceFirstName = resourceFirstname;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.scratchReportTime.ScratchReportTime#getContracted()
	 */
	public Boolean getContracted() {
		return contracted;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.scratchReportTime.ScratchReportTime#setContracted()
	 */
	public void setContracted(Boolean contracted) {
		this.contracted = contracted;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.scratchReportTime.ScratchReportTime#getItemCode()
	 */
	public String getItemCode() {
		return itemCode;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.scratchReportTime.ScratchReportTime#setItemCode()(java.lang.String)
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.scratchReportTime.ScratchReportTime#getSectionCode()
	 */
	public String getSectionCode() {
		return sectionCode;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.scratchReportTime.ScratchReportTime#setSectionCode()(java.lang.String)
	 */
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.scratchReportTime.ScratchReportTime#getSectionName()
	 */
	public String getSectionName() {
		return sectionName;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.scratchReportTime.ScratchReportTime#setSectionName()(java.lang.String)
	 */
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.scratchReportTime.ScratchReportTime#getStatus()
	 */
	public String getStatus() {
		return status;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.scratchReportTime.ScratchReportTime#setStatus()(java.lang.String)
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.scratchReportTime.ScratchReportTime#getShiftStartDate()
	 */
	public Date getShiftStartDate(){
		return shiftStartDate;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.scratchReportTime.ScratchReportTime#setShiftStartDate(java.util.Date)
	 */
	public void setShiftStartDate(Date shiftStartDate) {
		this.shiftStartDate = shiftStartDate;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.scratchReportTime.ScratchReportTime#getShiftEndDate()
	 */
	public Date getShiftEndDate() {
		return shiftEndDate;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.scratchReportTime.ScratchReportTime#setShiftEndDate()
	 */
	public void setShiftEndDate(Date shiftEndDate) {
		this.shiftEndDate = shiftEndDate;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.scratchReportTime.ScratchReportTime#getgetHoursOfWork()
	 */
	public BigDecimal getHoursOfWork() {
		return hoursOfWork;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.scratchReportTime.ScratchReportTime#setHoursOfWork(java.lang.Double)
	 */
	public void setHoursOfWork(BigDecimal hoursOfWork) {
		this.hoursOfWork = hoursOfWork;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.scratchReportTime.ScratchReportTime#getHoursOfRest()
	 */
	public BigDecimal getHoursOfRest() {
		return hoursOfRest;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.scratchReportTime.ScratchReportTime#setHoursOfRest(java.lang.Double)
	 */
	public void setHoursOfRest(BigDecimal hoursOfRest) {
		this.hoursOfRest = hoursOfRest;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.scratchReportTime.ScratchReportTime#getMitigationNeeded()
	 */
	public BigDecimal getMitigationNeeded() {
		return mitigationNeeded;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.scratchReportTime.ScratchReportTime#setMitigationNeeded(java.lang.Double)
	 */
	public void setMitigationNeeded(BigDecimal mitigationNeeded) {
		this.mitigationNeeded = mitigationNeeded;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.scratchReportTime.ScratchReportTime#getCreatedDate()
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.scratchReportTime.ScratchReportTime#setCreatedDate(java.util.Date)
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getIncidentNumber() {
		return incidentNumber;
	}

	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}
}
