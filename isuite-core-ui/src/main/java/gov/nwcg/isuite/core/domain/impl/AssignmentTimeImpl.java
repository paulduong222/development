package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.AdPaymentInfo;
import gov.nwcg.isuite.core.domain.Address;
import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.ContractorPaymentInfo;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_ASSIGNMENT_TIME", sequenceName="SEQ_ASSIGNMENT_TIME")
@Table(name="isw_assignment_time")
public class AssignmentTimeImpl extends PersistableImpl implements AssignmentTime {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ASSIGNMENT_TIME")
	private Long id = 0L;

	@ManyToOne(targetEntity=AssignmentImpl.class,fetch = FetchType.LAZY)
    @JoinColumn(name="ASSIGNMENT_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private Assignment assignment;

    @Column(name="ASSIGNMENT_ID", insertable=false, updatable=false, unique=false, nullable=true)
	private Long assignmentId;
	
	@Column(name = "EMPLOYMENT_TYPE", length = 20)
    @Enumerated(EnumType.STRING)
	private EmploymentTypeEnum employmentType;

	@ManyToOne(targetEntity=AddressImpl.class,fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="MAILING_ADDRESS_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private Address mailingAddress;
	
    @Column(name="MAILING_ADDRESS_ID", insertable=false, updatable=false, unique=false, nullable=true)
	private Long mailingAddressId;
	
	@Column(name = "PHONE", length = 30)
	private String phone;
	
	@Column(name = "FAX", length = 30)
	private String fax;
	
	@Column(name = "OF_REMARKS", length = 256)
	private String ofRemarks;
	
	@Column(name = "OTHER_DEFAULT_RATE", precision = 22, scale = 0)
	private BigDecimal otherDefaultRate;
	
    @Column(name="DELETED_DATE", nullable=true)
	private Date deletedDate;
	
	@OneToOne(targetEntity=AdPaymentInfoImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "assignmentTime")
	private AdPaymentInfo adPaymentInfo;
	
	@OneToOne(targetEntity=ContractorPaymentInfoImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "assignmentTime")
	private ContractorPaymentInfo contractorPaymentInfo;

	@Column(name = "HIRING_UNIT_NAME", length = 200)
	private String hiringUnitName;

	@Column(name = "HIRING_PHONE", length = 30)
	private String hiringPhone;
	
	@Column(name = "HIRING_FAX", length = 30)
	private String hiringFax;
	
	@OneToMany(targetEntity=AssignmentTimePostImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "assignmentTime")
	@OrderBy("postStartDate")
	private Collection<AssignmentTimePost> assignmentTimePosts = new ArrayList<AssignmentTimePost>();
	
	public AssignmentTimeImpl(){
		
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
	 * @return the assignment
	 */
	public Assignment getAssignment() {
		return assignment;
	}

	/**
	 * @param assignment the assignment to set
	 */
	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	/**
	 * @return the assignmentId
	 */
	public Long getAssignmentId() {
		return assignmentId;
	}

	/**
	 * @param assignmentId the assignmentId to set
	 */
	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
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

	/**
	 * @return the mailingAddress
	 */
	public Address getMailingAddress() {
		return mailingAddress;
	}

	/**
	 * @param mailingAddress the mailingAddress to set
	 */
	public void setMailingAddress(Address mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	/**
	 * @return the mailingAddressId
	 */
	public Long getMailingAddressId() {
		return mailingAddressId;
	}

	/**
	 * @param mailingAddressId the mailingAddressId to set
	 */
	public void setMailingAddressId(Long mailingAddressId) {
		this.mailingAddressId = mailingAddressId;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the ofRemarks
	 */
	public String getOfRemarks() {
		return ofRemarks;
	}

	/**
	 * @param ofRemarks the ofRemarks to set
	 */
	public void setOfRemarks(String ofRemarks) {
		this.ofRemarks = ofRemarks;
	}

	/**
	 * @return the otherDefaultRate
	 */
	public BigDecimal getOtherDefaultRate() {
		return otherDefaultRate;
	}

	/**
	 * @param otherDefaultRate the otherDefaultRate to set
	 */
	public void setOtherDefaultRate(BigDecimal otherDefaultRate) {
		this.otherDefaultRate = otherDefaultRate;
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
	 * @return the adPaymentInfo
	 */
	public AdPaymentInfo getAdPaymentInfo() {
		return adPaymentInfo;
	}

	/**
	 * @param adPaymentInfo the adPaymentInfo to set
	 */
	public void setAdPaymentInfo(AdPaymentInfo adPaymentInfo) {
		this.adPaymentInfo = adPaymentInfo;
	}

	/**
	 * @return the contractorPaymentInfos
	 */
	public ContractorPaymentInfo getContractorPaymentInfo() {
		return contractorPaymentInfo;
	}

	/**
	 * @param contractorPaymentInfos the contractorPaymentInfos to set
	 */
	public void setContractorPaymentInfo(
			ContractorPaymentInfo contractorPaymentInfo) {
		this.contractorPaymentInfo = contractorPaymentInfo;
	}

	/**
	 * @return the assignmentTimePosts
	 */
	public Collection<AssignmentTimePost> getAssignmentTimePosts() {
		return assignmentTimePosts;
	}

	/**
	 * @param assignmentTimePosts the assignmentTimePosts to set
	 */
	public void setAssignmentTimePosts(
			Collection<AssignmentTimePost> assignmentTimePosts) {
		this.assignmentTimePosts = assignmentTimePosts;
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
		AssignmentTimeImpl o = (AssignmentTimeImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id,assignment,employmentType,mailingAddress,phone,fax,ofRemarks,otherDefaultRate,deletedDate,adPaymentInfo,contractorPaymentInfo,assignmentTimePosts},
				new Object[]{o.id,o.assignment,o.employmentType,o.mailingAddress,o.phone,o.fax,o.ofRemarks,o.otherDefaultRate,o.deletedDate,o.adPaymentInfo,o.contractorPaymentInfo,o.assignmentTimePosts})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id,assignment,employmentType,mailingAddress,phone,fax,ofRemarks,otherDefaultRate,deletedDate,adPaymentInfo,contractorPaymentInfo,assignmentTimePosts})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("assignment", assignment)
		.append("employmentType", employmentType)
		.append("mailingAddress",mailingAddress)
		.append("phone",phone)
		.append("fax",fax)
		.append("ofRemarks",ofRemarks)
		.append("otherDefaultRate",otherDefaultRate)
		.append("deletedDate",deletedDate)
		.append("adPaymentInfo",adPaymentInfo)
		.append("contractorPaymentInfo",contractorPaymentInfo)
		.append("assignmentTimePosts",assignmentTimePosts)
		.appendSuper(super.toString())
		.toString();
	}

	public String getHiringUnitName() {
		return hiringUnitName;
	}

	public void setHiringUnitName(String hiringUnitName) {
		this.hiringUnitName = hiringUnitName;
	}

	public String getHiringPhone() {
		return hiringPhone;
	}

	public void setHiringPhone(String hiringPhone) {
		this.hiringPhone = hiringPhone;
	}

	public String getHiringFax() {
		return hiringFax;
	}

	public void setHiringFax(String hiringFax) {
		this.hiringFax = hiringFax;
	}
	
}
