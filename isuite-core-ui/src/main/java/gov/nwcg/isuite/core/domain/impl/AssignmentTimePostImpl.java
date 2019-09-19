package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.ContractorRate;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.IncidentResourceDailyCost;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.RateClassRate;
import gov.nwcg.isuite.core.domain.SpecialPay;
import gov.nwcg.isuite.core.domain.TimeInvoice;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_ASSIGN_TIME_POST", sequenceName="SEQ_ASSIGN_TIME_POST")
@Table(name="isw_assign_time_post")
public class AssignmentTimePostImpl extends PersistableImpl implements AssignmentTimePost {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ASSIGN_TIME_POST")
	private Long id = 0L;

	@ManyToOne(targetEntity=AssignmentTimeImpl.class,fetch = FetchType.LAZY)
    @JoinColumn(name="ASSIGNMENT_TIME_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private AssignmentTime assignmentTime;

    @Column(name="ASSIGNMENT_TIME_ID", insertable=false, updatable=false, unique=false, nullable=true)
	private Long assignmentTimeId;

	@ManyToOne(targetEntity=RateClassRateImpl.class,fetch = FetchType.LAZY)
    @JoinColumn(name="RATE_CLASS_RATE_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private RateClassRate rateClassRate;
	
    @Column(name="RATE_CLASS_RATE_ID", insertable=false, updatable=false, unique=false, nullable=true)
	private Long rateClassRateId;
	
	@ManyToOne(targetEntity=IncidentAccountCodeImpl.class,fetch = FetchType.LAZY)
    @JoinColumn(name="INCIDENT_ACCOUNT_CODE_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private IncidentAccountCode incidentAccountCode;
	
    @Column(name="INCIDENT_ACCOUNT_CODE_ID", insertable=false, updatable=false, unique=false, nullable=true)
	private Long incidentAccountCodeId;
    
	@ManyToOne(targetEntity=KindImpl.class,fetch = FetchType.LAZY)
    @JoinColumn(name="KIND_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private Kind kind;
	
    @Column(name="KIND_ID", insertable=false, updatable=false, unique=false, nullable=true)
	private Long kindId;
	
	@ManyToOne(targetEntity=ContractorRateImpl.class,fetch = FetchType.LAZY)
    @JoinColumn(name="REF_CONTRACTOR_RATE_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private ContractorRate refContractorRate;
	
    @Column(name="REF_CONTRACTOR_RATE_ID", insertable=false, updatable=false, unique=false, nullable=true)
	private Long refContractorRateId;
	
	@ManyToOne(targetEntity=SpecialPayImpl.class,fetch = FetchType.LAZY)
    @JoinColumn(name="SPECIAL_PAY_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private SpecialPay specialPay;
	
    @Column(name="SPECIAL_PAY_ID", insertable=false, updatable=false, unique=false, nullable=true)
	private Long specialPayId;

    @Column(name = "EMPLOYMENT_TYPE", length = 20)
    @Enumerated(EnumType.STRING)
    private EmploymentTypeEnum employmentType;
    
	@Column(name = "POST_START_DATE")
	private Date postStartDate;
	
	@Column(name = "POST_STOP_DATE")
	private Date postStopDate;
	
	@Column(name = "OTHER_RATE", precision = 22, scale = 0)
	private BigDecimal otherRate;
	
	@Column(name = "RATE_TYPE", length = 20)
	private String rateType;
	
	@Column(name = "UNIT_OF_MEASURE", length = 20)
	private String unitOfMeasure;

	@Column(name = "RATE_AMOUNT", precision = 22, scale = 0)
	private BigDecimal rateAmount;
	
	@Column(name = "GUARANTEE_AMOUNT", precision = 22, scale = 0)
	private BigDecimal guaranteeAmount;
	
	@Column(name = "DESCRIPTION", length = 20)
	private String description;
	
	@Column(name = "IS_HALF_RATE")
	private Boolean isHalfRate;
	
	@Column(name = "QUANTITY", precision = 22, scale = 0)
	private BigDecimal quantity;
	
	@ManyToMany(targetEntity=TimeInvoiceImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "assignmentTimePosts")
	private Collection<TimeInvoice> timeInvoices;
	
	@Column(name="IS_TRAINING")
	private Boolean training;

	@Column(name="RTN_TRAVEL_START_ONLY")
	private Boolean returnTravelStartOnly;
	
	@OneToOne(targetEntity=AssignmentTimePostImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "specialRateAssignmentTimePost")
	private AssignmentTimePost assignmentTimePost;

	@OneToOne(targetEntity=AssignmentTimePostImpl.class,cascade=CascadeType.ALL)
	@JoinColumn(name="SPECIAL_ASSIGN_TIME_POST_ID")
	private AssignmentTimePost specialRateAssignmentTimePost;

	@Column(name="IS_PRIMARY_POSTING")
	private Boolean primaryPosting;

	@Column(name="IS_SPECIAL_POSTING")
	private Boolean specialPosting;

	@Column(name="IS_GUARANTEE_POSTING")
	private Boolean guaranteePosting;

	@Column(name = "INVOICED_AMOUNT", precision = 22, scale = 0)
	private BigDecimal invoicedAmount;
	
	@Column(name = "CONTRACTOR_POST_TYPE", length = 12)
	private String contractorPostType;
	
	@ManyToOne(targetEntity=IncidentResourceDailyCostImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "INC_RES_DAILY_COST_ID")
	private IncidentResourceDailyCost incidentResourceDailyCost;
	
	public AssignmentTimePostImpl(){
		
	}


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
	 * @return the assignmentTime
	 */
	public AssignmentTime getAssignmentTime() {
		return assignmentTime;
	}


	/**
	 * @param assignmentTime the assignmentTime to set
	 */
	public void setAssignmentTime(AssignmentTime assignmentTime) {
		this.assignmentTime = assignmentTime;
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
	 * @return the rateClassRate
	 */
	public RateClassRate getRateClassRate() {
		return rateClassRate;
	}


	/**
	 * @param rateClassRate the rateClassRate to set
	 */
	public void setRateClassRate(RateClassRate rateClassRate) {
		this.rateClassRate = rateClassRate;
	}


	/**
	 * @return the rateClassRateId
	 */
	public Long getRateClassRateId() {
		return rateClassRateId;
	}


	/**
	 * @param rateClassRateId the rateClassRateId to set
	 */
	public void setRateClassRateId(Long rateClassRateId) {
		this.rateClassRateId = rateClassRateId;
	}


	/**
	 * @return the incidentAccountCode
	 */
	public IncidentAccountCode getIncidentAccountCode() {
		return incidentAccountCode;
	}


	/**
	 * @param incidentAccountCode the incidentAccountCode to set
	 */
	public void setIncidentAccountCode(IncidentAccountCode incidentAccountCode) {
		this.incidentAccountCode = incidentAccountCode;
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
	 * @return the kind
	 */
	public Kind getKind() {
		return kind;
	}


	/**
	 * @param kind the kind to set
	 */
	public void setKind(Kind kind) {
		this.kind = kind;
	}


	/**
	 * @return the kindId
	 */
	public Long getKindId() {
		return kindId;
	}


	/**
	 * @param kindId the kindId to set
	 */
	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}


	/**
	 * @return the contractorRate
	 */
	public ContractorRate getRefContractorRate() {
		return refContractorRate;
	}


	/**
	 * @param contractorRate the contractorRate to set
	 */
	public void setRefContractorRate(ContractorRate contractorRate) {
		this.refContractorRate = contractorRate;
	}


	/**
	 * @return the contractorRateId
	 */
	public Long getRefContractorRateId() {
		return refContractorRateId;
	}


	/**
	 * @param contractorRateId the contractorRateId to set
	 */
	public void setRefContractorRateId(Long contractorRateId) {
		this.refContractorRateId = contractorRateId;
	}


	/**
	 * @return the specialPay
	 */
	public SpecialPay getSpecialPay() {
		return specialPay;
	}


	/**
	 * @param specialPay the specialPay to set
	 */
	public void setSpecialPay(SpecialPay specialPay) {
		this.specialPay = specialPay;
	}


	/**
	 * @return the specialPayId
	 */
	public Long getSpecialPayId() {
		return specialPayId;
	}


	/**
	 * @param specialPayId the specialPayId to set
	 */
	public void setSpecialPayId(Long specialPayId) {
		this.specialPayId = specialPayId;
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
	 * @return the postStopDate
	 */
	public Date getPostStopDate() {
		return postStopDate;
	}


	/**
	 * @param postStopDate the postStopDate to set
	 */
	public void setPostStopDate(Date postStopDate) {
		this.postStopDate = postStopDate;
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
	 * @return the rateAmmount
	 */
	public BigDecimal getRateAmount() {
		return rateAmount;
	}


	/**
	 * @param rateAmount the rateAmmount to set
	 */
	public void setRateAmount(BigDecimal rateAmount) {
		this.rateAmount = rateAmount;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the timeInvoices
	 */
	public Collection<TimeInvoice> getTimeInvoices() {
		return timeInvoices;
	}


	/**
	 * @param timeInvoices the timeInvoices to set
	 */
	public void setTimeInvoices(Collection<TimeInvoice> timeInvoices) {
		this.timeInvoices = timeInvoices;
	}


	/**
	 * @return the isHalfRate
	 */
	public Boolean getIsHalfRate() {
		return isHalfRate;
	}


	/**
	 * @param isHalfRate the isHalfRate to set
	 */
	public void setIsHalfRate(Boolean isHalfRate) {
		this.isHalfRate = isHalfRate;
	}


	/**
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}


	/**
	 * @param quantity the hours to set
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( this == obj ) return true;
		if ( getClass() != obj.getClass() ) return false;
		AssignmentTimePostImpl o = (AssignmentTimePostImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id,assignmentTimeId,rateClassRateId,incidentAccountCodeId,kindId,refContractorRateId,specialPayId,postStartDate
							,postStopDate,otherRate,rateType,unitOfMeasure,rateAmount,guaranteeAmount,description,isHalfRate,
							quantity},
				new Object[]{o.id,o.assignmentTimeId,o.rateClassRateId,o.incidentAccountCodeId,o.kindId,o.refContractorRateId,o.specialPayId,o.postStartDate
					,o.postStopDate,o.otherRate,o.rateType,o.unitOfMeasure,o.rateAmount,o.guaranteeAmount,o.description,o.isHalfRate,
					o.quantity})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id,assignmentTimeId,rateClassRateId,incidentAccountCodeId,kindId,refContractorRateId,specialPayId,postStartDate
				,postStopDate,otherRate,rateType,unitOfMeasure,rateAmount,guaranteeAmount,description,isHalfRate,
				quantity})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("assignmentTimeId", assignmentTimeId)
		.append("rateClassRateId", rateClassRateId)
		.append("incidentAccountCodeId",incidentAccountCodeId)
		.append("kindId",kindId)
		.append("refContractorRateId",refContractorRateId)
		.append("specialPayId",specialPayId)
		.append("postStartDate",postStartDate)
		.append("postStopDate",postStopDate)
		.append("otherRate",otherRate)
		.append("rateType",rateType)
		.append("unitOfMeasure",unitOfMeasure)
		.append("rateAmount",rateAmount)
		.append("guaranteeAmount",guaranteeAmount)
		.append("description",description)
		.append("isHalfRate",isHalfRate)
		.append("quantity",quantity)
		.appendSuper(super.toString())
		.toString();
	}


	/**
	 * @return the training
	 */
	public Boolean getTraining() {
		return training;
	}


	/**
	 * @param training the training to set
	 */
	public void setTraining(Boolean training) {
		this.training = training;
	}


	/**
	 * @return the returnTravelStartOnly
	 */
	public Boolean getReturnTravelStartOnly() {
		return returnTravelStartOnly;
	}


	/**
	 * @param returnTravelStartOnly the returnTravelStartOnly to set
	 */
	public void setReturnTravelStartOnly(Boolean returnTravelStartOnly) {
		this.returnTravelStartOnly = returnTravelStartOnly;
	}


	public AssignmentTimePost getAssignmentTimePost() {
		return assignmentTimePost;
	}


	public void setAssignmentTimePost(AssignmentTimePost assignmentTimePost) {
		this.assignmentTimePost = assignmentTimePost;
	}


	public AssignmentTimePost getSpecialRateAssignmentTimePost() {
		return specialRateAssignmentTimePost;
	}


	public void setSpecialRateAssignmentTimePost(AssignmentTimePost specialRateAssignmentTimePost) {
		this.specialRateAssignmentTimePost = specialRateAssignmentTimePost;
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
	 * @return the invoicedAmount
	 */
	public BigDecimal getInvoicedAmount() {
		return invoicedAmount;
	}


	/**
	 * @param invoicedAmount the invoicedAmount to set
	 */
	public void setInvoicedAmount(BigDecimal invoicedAmount) {
		this.invoicedAmount = invoicedAmount;
	}

	/**
	 * @return the employmentType
	 */
	public EmploymentTypeEnum getEmploymentType() {
		return employmentType;
	}
	
	/**
	 * @param employmentType the employmentType to set
	 */
	public void setEmploymentType(EmploymentTypeEnum employmentType) {
		this.employmentType = employmentType;
	}


	public String getContractorPostType() {
		return contractorPostType;
	}


	public void setContractorPostType(String contractorPostType) {
		this.contractorPostType = contractorPostType;
	}

	
	public IncidentResourceDailyCost getincidentResourceDailyCost() {
		return this.incidentResourceDailyCost;
	}

	public void setIncidentResourceDailyCost(IncidentResourceDailyCost incidentResourceDailyCost) {
		this.incidentResourceDailyCost = incidentResourceDailyCost;
	}

}
