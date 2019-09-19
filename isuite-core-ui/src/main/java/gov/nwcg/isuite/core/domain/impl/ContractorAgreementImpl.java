package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.AdminOffice;
import gov.nwcg.isuite.core.domain.Contractor;
import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.domain.ContractorAgreementNumberHistory;
import gov.nwcg.isuite.core.domain.ContractorPaymentInfo;
import gov.nwcg.isuite.core.domain.TimeInvoice;
import gov.nwcg.isuite.core.persistence.hibernate.query.ContractorAgreementQuery;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.util.ArrayList;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;		 
import org.apache.commons.lang.builder.HashCodeBuilder;		 
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_CONTRACTOR_AGREEMENT", sequenceName="SEQ_CONTRACTOR_AGREEMENT")
@NamedQueries({
	@NamedQuery(name=ContractorAgreementQuery.DISABLE_CONTRACTOR_AGREEMENTS,query=ContractorAgreementQuery.DISABLE_CONTRACTOR_AGREEMENTS_QUERY)
	,@NamedQuery(name=ContractorAgreementQuery.ENABLE_CONTRACTOR_AGREEMENTS,query=ContractorAgreementQuery.ENABLE_CONTRACTOR_AGREEMENTS_QUERY)
})
@Table(name = "isw_contractor_agreement")
public class ContractorAgreementImpl extends PersistableImpl implements ContractorAgreement {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_CONTRACTOR_AGREEMENT")
	private Long id = 0L;

	@ManyToOne(targetEntity=AdminOfficeImpl.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "ADMIN_OFFICE_ID", insertable = true, updatable = true, unique = false, nullable = true)
	private AdminOffice adminOffice;

    @Column(name="ADMIN_OFFICE_ID", insertable = false, updatable = false, nullable = true)
	private Long adminOfficeId;
	
	@Column(name = "agreement_number", nullable = false, length = 30)
	private String agreementNumber;
	
	@Column(name = "start_date", length = 29)
	private Date startDate;
	
	@Column(name = "end_date", length = 29)
	private Date endDate;
	
	@Column(name = "point_of_hire", length = 30)
	private String pointOfHire;
	
	@ManyToOne(targetEntity=ContractorImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTRACTOR_ID", insertable = true, updatable = true, nullable = false)
	private Contractor contractor;
	
	@Column(name = "CONTRACTOR_ID", length = 19, insertable=false, updatable=false, nullable=false)
	private Long contractorId;
	
	@Column(name="DELETED_DATE")
	private Date deletedDate;
	
	@Column(name="IS_ENABLED",nullable=false)
    private Boolean enabled;
	
	@OneToMany(targetEntity=ContractorAgreementNumberHistoryImpl.class, fetch=FetchType.LAZY,cascade=CascadeType.REMOVE,mappedBy="contractorAgreement")
	private Collection<ContractorAgreementNumberHistory> contractorAgreementNumberHistories = new ArrayList<ContractorAgreementNumberHistory>();
	
	@OneToMany(targetEntity=TimeInvoiceImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="contractorAgreement")
	private Collection<TimeInvoice> timeInvoices;
	
	@OneToMany(targetEntity=ContractorPaymentInfoImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="contractorAgreement")
	private Collection<ContractorPaymentInfo> contractorPaymentInfos;
	
	public ContractorAgreementImpl(){

	}

	/**
	 * Returns the id.
	 *
	 * @return 
	 *		the id to return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id 
	 *			the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the adminOffice.
	 *
	 * @return 
	 *		the adminOffice to return
	 */
	public AdminOffice getAdminOffice() {
		return adminOffice;
	}

	/**
	 * Sets the adminOffice.
	 *
	 * @param adminOffice 
	 *			the adminOffice to set
	 */
	public void setAdminOffice(AdminOffice adminOffice) {
		this.adminOffice = adminOffice;
	}

	/**
	 * Returns the adminOfficeId.
	 *
	 * @return 
	 *		the adminOfficeId to return
	 */
	public Long getAdminOfficeId() {
		return adminOfficeId;
	}

	/**
	 * Sets the adminOfficeId.
	 *
	 * @param adminOfficeId 
	 *			the adminOfficeId to set
	 */
	public void setAdminOfficeId(Long adminOfficeId) {
		this.adminOfficeId = adminOfficeId;
	}

	/**
	 * Returns the agreementNumber.
	 *
	 * @return 
	 *		the agreementNumber to return
	 */
	public String getAgreementNumber() {
		return agreementNumber;
	}

	/**
	 * Sets the agreementNumber.
	 *
	 * @param agreementNumber 
	 *			the agreementNumber to set
	 */
	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
	}

	/**
	 * Returns the startDate.
	 *
	 * @return 
	 *		the startDate to return
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Sets the startDate.
	 *
	 * @param startDate 
	 *			the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Returns the endDate.
	 *
	 * @return 
	 *		the endDate to return
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Sets the endDate.
	 *
	 * @param endDate 
	 *			the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	 * Returns the contractor.
	 *
	 * @return 
	 *		the contractor to return
	 */
	public Contractor getContractor() {
		return contractor;
	}

	/**
	 * Sets the contractor.
	 *
	 * @param contractor 
	 *			the contractor to set
	 */
	public void setContractor(Contractor contractor) {
		this.contractor = contractor;
	}

	/**
	 * Returns the contractorId.
	 *
	 * @return 
	 *		the contractorId to return
	 */
	public Long getContractorId() {
		return contractorId;
	}

	/**
	 * Sets the contractorId.
	 *
	 * @param contractorId 
	 *			the contractorId to set
	 */
	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}
	
	/**
	 * Returns the deletedDate.
	 *
	 * @return 
	 *		the deletedDate to return
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/**
	 * Sets the deletedDate.
	 *
	 * @param deletedDate 
	 *			the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.ContractorAgreement#setEnabled(java.lang.Boolean)
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.ContractorAgreement#isEnabled()
	 */
	public Boolean isEnabled() {
		return enabled;
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
		ContractorAgreementImpl o = (ContractorAgreementImpl)obj;		 
		return new EqualsBuilder()		 
			.append(new Object[]{id, adminOffice, agreementNumber, adminOfficeId, startDate, endDate, pointOfHire, contractor, contractorId, deletedDate},		 
			new Object[]{o.id, o.adminOffice, o.agreementNumber, o.adminOfficeId, o.startDate, o.endDate, o.pointOfHire, o.contractor, o.contractorId, o.deletedDate})		 
		.appendSuper(super.equals(o))		 
		.isEquals();		 
	} 		 
   		 
	/* (non-Javadoc)		 
	 * @see java.lang.Object#hashCode()		 
	*/		 
	public int hashCode() {		 
		return new HashCodeBuilder(31,33)		 
			.append(super.hashCode())		 
			.append(id)		 
			.append(adminOffice)		 
			.append(adminOfficeId)		 
			.append(agreementNumber)		 
			.append(startDate)		 
			.append(endDate)		 
			.append(pointOfHire)		 
			.append(contractor)		 
			.append(contractorId) 		 
			.append(deletedDate)		 
			.toHashCode();		 
	}		 
		 
	/* (non-Javadoc)		 
	 * @see java.lang.Object#toString()		 
	*/		 
	public String toString() {		 
		return new ToStringBuilder(this)		 
			.append("id", id)		 
			.append("adminOffice",adminOffice)		 
			.append("adminOfficeId",adminOfficeId)		 
			.append("agreementNumber",agreementNumber)		 
			.append("startDate",startDate)		 
			.append("endDate",endDate)		 
			.append("pointOfHire",pointOfHire)		 
			.append("contractor",contractor)		 
			.append("contractorId",contractorId) 		 
			.append("deletedDate",deletedDate)		 
			.appendSuper(super.toString())		 
			.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.ContractorAgreement#setTimeInvoices(java.util.Collection)
	 */
	public void setTimeInvoices(Collection<TimeInvoice> timeInvoices) {
		this.timeInvoices = timeInvoices;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.ContractorAgreement#getTimeInvoices()
	 */
	public Collection<TimeInvoice> getTimeInvoices() {
		return timeInvoices;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.ContractorAgreement#setContractorAgreementNumberHistories(java.util.Collection)
	 */
	public void setContractorAgreementNumberHistories(
			Collection<ContractorAgreementNumberHistory> contractorAgreementNumberHistories) {
		this.contractorAgreementNumberHistories = contractorAgreementNumberHistories;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.ContractorAgreement#getContractorAgreementNumberHistories()
	 */
	public Collection<ContractorAgreementNumberHistory> getContractorAgreementNumberHistories() {
		return contractorAgreementNumberHistories;
	}

	/**
	 * @param contractorPaymentInfos the contractorPaymentInfos to set
	 */
	public void setContractorPaymentInfos(Collection<ContractorPaymentInfo> contractorPaymentInfos) {
		this.contractorPaymentInfos = contractorPaymentInfos;
	}

	/**
	 * @return the contractorPaymentInfos
	 */
	public Collection<ContractorPaymentInfo> getContractorPaymentInfos() {
		return contractorPaymentInfos;
	}

}
