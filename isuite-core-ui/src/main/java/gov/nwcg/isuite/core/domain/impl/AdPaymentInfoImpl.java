package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.AdPaymentInfo;
import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.RateArea;
import gov.nwcg.isuite.core.domain.RateClassRate;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.RateAreaEnum;

import java.util.Date;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_AD_PAYMENT_INFO", sequenceName="SEQ_AD_PAYMENT_INFO")
@Table(name="isw_ad_payment_info")
public class AdPaymentInfoImpl extends PersistableImpl implements AdPaymentInfo {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_AD_PAYMENT_INFO")
	private Long id = 0L;

	@ManyToOne(targetEntity=RateAreaImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name="RATE_AREA_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private RateArea rateArea;

	@Column(name="RATE_AREA_ID", insertable=false, updatable=false, nullable=true, unique=false)
	private Long rateAreaId;

	@Column(name="RATE_AREA_NAME", length=10, nullable=true)
	@Enumerated(EnumType.STRING)
	private RateAreaEnum rateAreaName;

	@ManyToOne(targetEntity=RateClassRateImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name="RATE_CLASS_RATE_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private RateClassRate rateClassRate;

	@Column(name="RATE_CLASS_RATE_ID", insertable=false, updatable=false, nullable=true, unique=false)
	private Long rateClassRateId;

	@ManyToOne(targetEntity=AssignmentTimeImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name="ASSIGNMENT_TIME_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private AssignmentTime assignmentTime;

	@Column(name="ASSIGNMENT_TIME_ID", insertable=false, updatable=false, nullable=true, unique=false)
	private Long assignmentTimeId;

	@Column(name = "IS_INITIAL_EMP")
	private Boolean initialEmp=false;

	@Column(name = "IS_RETURN_TRAVEL")
	private Boolean returnTravel=false;

	@ManyToOne(targetEntity=OrganizationImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name="POINT_OF_HIRE_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private Organization pointOfHireOrg;

	@Column(name="POINT_OF_HIRE_ID", insertable=false, updatable=false, nullable=true, unique=false)
	private Long pointOfHireId;

	@Column(name = "POINT_OF_HIRE", length = 90)
	private String pointOfHire;

	@Column(name = "SSN", length = 12)
	private String ssn;
	
	@Column(name = "ECI", length = 10)
	private String eci;

	@Column(name="DELETED_DATE", nullable=true)
	private Date deletedDate;

	@Column(name="RATE_YEAR")
	private Integer rateYear;
	
	public AdPaymentInfoImpl() {
		super();
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
	 * @return the rateArea
	 */
	public RateArea getRateArea() {
		return rateArea;
	}

	/**
	 * @param rateArea the rateArea to set
	 */
	public void setRateArea(RateArea rateArea) {
		this.rateArea = rateArea;
	}

	/**
	 * @return the rateAreaId
	 */
	public Long getRateAreaId() {
		return rateAreaId;
	}

	/**
	 * @param rateAreaId the rateAreaId to set
	 */
	public void setRateAreaId(Long rateAreaId) {
		this.rateAreaId = rateAreaId;
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
	 * @return the initialEmp
	 */
	public Boolean getInitialEmp() {
		return initialEmp;
	}

	/**
	 * @param initialEmp the initialEmp to set
	 */
	public void setInitialEmp(Boolean initialEmp) {
		this.initialEmp = initialEmp;
	}

	/**
	 * @return the returnTravel
	 */
	public Boolean getReturnTravel() {
		return returnTravel;
	}

	/**
	 * @param returnTravel the returnTravel to set
	 */
	public void setReturnTravel(Boolean returnTravel) {
		this.returnTravel = returnTravel;
	}

	/**
	 * @return the pointOfHire
	 */
	public String getPointOfHire() {
		return pointOfHire;
	}

	/**
	 * @param pointOfHire the pointOfHire to set
	 */
	public void setPointOfHire(String pointOfHire) {
		this.pointOfHire = pointOfHire;
	}

	/**
	 * @return the ssn
	 */
	public String getSsn() {
		return ssn;
	}

	/**
	 * @param ssn the ssn to set
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	/**
	 * @return the eci
	 */
	public String getEci() {
		return eci;
	}

	/**
	 * @param ssn the eci to set
	 */
	public void setEci(String eci) {
		this.eci = eci;
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

	public Organization getPointOfHireOrg() {
		return pointOfHireOrg;
	}

	public void setPointOfHireOrg(Organization pointOfHireOrg) {
		this.pointOfHireOrg = pointOfHireOrg;
	}

	public Long getPointOfHireId() {
		return pointOfHireId;
	}

	public void setPointOfHireId(Long pointOfHireId) {
		this.pointOfHireId = pointOfHireId;
	}

	public RateAreaEnum getRateAreaName() {
		return rateAreaName;
	}

	public void setRateAreaName(RateAreaEnum rateAreaName) {
		this.rateAreaName = rateAreaName;
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
		AdPaymentInfoImpl o = (AdPaymentInfoImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id,rateArea,rateAreaName,rateClassRate,assignmentTime,initialEmp,returnTravel,pointOfHireOrg,pointOfHire,ssn,eci,deletedDate},
				new Object[]{o.id,o.rateArea,o.rateAreaName,o.rateClassRate,o.assignmentTime,o.initialEmp,o.returnTravel,o.pointOfHireOrg,o.pointOfHire,o.ssn,o.eci,o.deletedDate})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id,rateArea,rateAreaName,rateClassRate,assignmentTime,initialEmp,returnTravel,pointOfHireOrg,pointOfHire,ssn,eci,deletedDate})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("rateArea", rateArea)
		.append("rateAreaName", rateAreaName)
		.append("rateClassRate",rateClassRate)
		.append("assignmentTime",assignmentTime)
		.append("initialEmp",initialEmp)
		.append("returnTravel",returnTravel)
		.append("pointOfHireOrg",pointOfHireOrg)
		.append("pointOfHire",pointOfHire)
		.append("ssn",ssn)
		.append("eci",eci)
		.append("deletedDate",deletedDate)
		.appendSuper(super.toString())
		.toString();
	}

	public Integer getRateYear() {
		return rateYear;
	}

	public void setRateYear(Integer rateYear) {
		this.rateYear = rateYear;
	}


}
