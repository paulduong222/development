package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.Contractor;
import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.domain.ContractorPaymentInfo;
import gov.nwcg.isuite.core.domain.ContractorRate;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_CONTR_PAYMENT_INFO", sequenceName="SEQ_CONTR_PAYMENT_INFO")
@Table(name="isw_contr_payment_info")
public class ContractorPaymentInfoImpl extends PersistableImpl implements ContractorPaymentInfo{

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_CONTR_PAYMENT_INFO")
	private Long id = 0L;

	@ManyToOne(targetEntity=AssignmentTimeImpl.class,fetch = FetchType.LAZY)
    @JoinColumn(name="ASSIGNMENT_TIME_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private AssignmentTime assignmentTime;
	
	@Column(name="ASSIGNMENT_TIME_ID", insertable=false, updatable=false, nullable=true, unique=false)
	private Long assignmentTimeId;
	
	@ManyToOne(targetEntity=ContractorImpl.class,fetch = FetchType.LAZY)
    @JoinColumn(name="CONTRACTOR_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private Contractor contractor;
	
	@Column(name="CONTRACTOR_ID", insertable=false, updatable=false, nullable=true, unique=false)
	private Long contractorId;
	
	@Column(name = "VIN_NAME", length = 90)
	private String vinName;
	
	@Column(name = "DESC1", length = 90)
	private String desc1;

	@Column(name = "DESC2", length = 90)
	private String desc2;
	
	@ManyToOne(targetEntity=ContractorAgreementImpl.class,fetch = FetchType.LAZY)
    @JoinColumn(name="CONTRACTOR_AGREEMENT_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private ContractorAgreement contractorAgreement;
	
	@Column(name="CONTRACTOR_AGREEMENT_ID", insertable=false, updatable=false, nullable=true, unique=false)
	private Long contractorAgreementId;
	
    @Column(name="HIRED_DATE", nullable=true)
	private Date hiredDate;
    
    @Column(name = "POINT_OF_HIRE", length = 30)
	private String pointOfHire;
	
	@Column(name = "IS_OPERATION")
	private Boolean operation;
	
	@Column(name = "IS_SUPPLIES")
	private Boolean supplies;
	
	@Column(name = "IS_WITHDRAWN")
	private Boolean withdrawn;

    @Column(name="DELETED_DATE", nullable=true)
	private Date deletedDate;

	@OneToMany(targetEntity=ContractorRateImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "isw_contr_payinfo_rate", 
			   joinColumns = 
			   		{@JoinColumn(name = "contractor_pay_info_id", unique = true, updatable = false) },
			   inverseJoinColumns = 
			   		{ @JoinColumn(name = "contractor_rate_id", nullable = false, updatable = false) }
	)
    @OrderBy("rateType")
	private Collection<ContractorRate> contractorRates;
    
	public ContractorPaymentInfoImpl(){
		
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
	 * @return the contractor
	 */
	public Contractor getContractor() {
		return contractor;
	}

	/**
	 * @param contractor the contractor to set
	 */
	public void setContractor(Contractor contractor) {
		this.contractor = contractor;
	}

	/**
	 * @return the contractorId
	 */
	public Long getContractorId() {
		return contractorId;
	}

	/**
	 * @param contractorId the contractorId to set
	 */
	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}

	/**
	 * @return the vinName
	 */
	public String getVinName() {
		return vinName;
	}

	/**
	 * @param vinName the vinName to set
	 */
	public void setVinName(String vinName) {
		this.vinName = vinName;
	}

	/**
	 * Returns the pointOfHire.
	 *
	 * @return 
	 *		the pointOfHire to return
	 */
	public String getPointOfHire() {
		return pointOfHire;
	}

	/**
	 * Sets the pointOfHire.
	 *
	 * @param pointOfHire 
	 *			the pointOfHire to set
	 */
	public void setPointOfHire(String pointOfHire) {
		this.pointOfHire = pointOfHire;
	}

	/**
	 * @return the desc2
	 */
	public String getDesc2() {
		return desc2;
	}

	/**
	 * @param desc2 the desc2 to set
	 */
	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}
	/**
	 * @return the desc1
	 */
	public String getDesc1() {
		return desc1;
	}

	/**
	 * @param desc1 the desc1 to set
	 */
	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}
	/**
	 * @return the contractorAgreement
	 */
	public ContractorAgreement getContractorAgreement() {
		return contractorAgreement;
	}

	/**
	 * @param contractorAgreement the contractorAgreement to set
	 */
	public void setContractorAgreement(ContractorAgreement contractorAgreement) {
		this.contractorAgreement = contractorAgreement;
	}

	/**
	 * @return the contractorAgreementId
	 */
	public Long getContractorAgreementId() {
		return contractorAgreementId;
	}

	/**
	 * @param contractorAgreementId the contractorAgreementId to set
	 */
	public void setContractorAgreementId(Long contractorAgreementId) {
		this.contractorAgreementId = contractorAgreementId;
	}

	/**
	 * @return the hiredDate
	 */
	public Date getHiredDate() {
		return hiredDate;
	}

	/**
	 * @param hiredDate the hiredDate to set
	 */
	public void setHiredDate(Date hiredDate) {
		this.hiredDate = hiredDate;
	}

	/**
	 * @return the operation
	 */
	public Boolean getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(Boolean operation) {
		this.operation = operation;
	}

	/**
	 * @return the supplies
	 */
	public Boolean getSupplies() {
		return supplies;
	}

	/**
	 * @param supplies the supplies to set
	 */
	public void setSupplies(Boolean supplies) {
		this.supplies = supplies;
	}

	/**
	 * @return the withdrawn
	 */
	public Boolean getWithdrawn() {
		return withdrawn;
	}

	/**
	 * @param withdrawn the withdrawn to set
	 */
	public void setWithdrawn(Boolean withdrawn) {
		this.withdrawn = withdrawn;
	}

	/**
	 * @return the deletedDate
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/**
	 * @param deletedDate the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	/**
	 * @return the contractorRates
	 */
	public Collection<ContractorRate> getContractorRates() {
		return contractorRates;
	}

	/**
	 * @param contractorRates the contractorRates to set
	 */
	public void setContractorRates(Collection<ContractorRate> contractorRates) {
		this.contractorRates = contractorRates;
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
		ContractorPaymentInfoImpl o = (ContractorPaymentInfoImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id,assignmentTimeId,contractorId,vinName,desc1,desc2,contractorAgreementId,hiredDate,operation,supplies,withdrawn,deletedDate},
				new Object[]{o.id,o.assignmentTimeId,o.contractorId,o.vinName,o.desc1,o.desc2,o.contractorAgreementId,o.hiredDate,o.operation,o.supplies,o.withdrawn,o.deletedDate})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id,assignmentTimeId,contractorId,vinName,desc1,desc2,contractorAgreementId
							,hiredDate,operation,supplies,withdrawn,deletedDate})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("assignmentTimeId", assignmentTimeId)
		.append("contractorId", contractorId)
		.append("vinName", vinName)
		.append("desc1", desc1)
		.append("desc2", desc2)
		.append("contractorAgreementId", contractorAgreementId)
		.append("hiredDate", hiredDate)
		.append("operation", operation)
		.append("supplies", supplies)
		.append("withdrawn", withdrawn)
		.append("deletedDate", deletedDate)
		.appendSuper(super.toString())
		.toString();
	}

}
