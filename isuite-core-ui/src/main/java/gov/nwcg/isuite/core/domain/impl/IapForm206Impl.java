package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.IapAreaLocationCapability;
import gov.nwcg.isuite.core.domain.IapForm206;
import gov.nwcg.isuite.core.domain.IapHospital;
import gov.nwcg.isuite.core.domain.IapMedicalAid;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.core.domain.IapRemoteCampLocations;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

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
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name="SEQ_IAP_FORM_206", sequenceName="SEQ_IAP_FORM_206")
@Table(name = "isw_iap_form_206")
public class IapForm206Impl extends PersistableImpl implements IapForm206 {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_IAP_FORM_206")
	private Long id = 0L;

	@ManyToOne(targetEntity=IapPlanImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IAP_PLAN_ID", nullable = false)
	private IapPlan iapPlan;

	@Column(name = "IAP_PLAN_ID", insertable = false, updatable = false, unique=false, nullable = false)
	private Long iapPlanId;

	@Column(name = "PREPARED_BY", length = 50)
	private String preparedBy;

	@Column(name = "PREPARED_BY_POS", length = 50)
	private String preparedByPosition;

	@Column(name = "PREPARED_DATE")
	private Date preparedDate;

	@Column(name = "REVIEWED_BY", length = 50)
	private String reviewedBy;

	@Column(name = "REVIEWED_DATE")
	private Date reviewedDate;

	@Column(name = "APPROVED_BY", length = 50)
	private String approvedBy;
	
	@Column(name = "IS_FORM_LOCKED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isFormLocked;
	
	@Column(name = "IS_AVIATION_UTILIZED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isAviationUtilized;

	@Column(name = "MEDICAL_EMERGENCY_PROC", length = 4000)
	private String medicalEmergencyProcedures;

	@OneToMany(targetEntity=IapHospitalImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iapForm206")
    @OrderBy("positionNum")
	private Collection<IapHospital> iapHospitals;

	@OneToMany(targetEntity=IapMedicalAidImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iapForm206")
    @OrderBy("positionNum")
	private Collection<IapMedicalAid> iapMedicalAids;

	@OneToMany(targetEntity=IapAreaLocationCapabilityImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iapForm206")
    @OrderBy("positionNum")
	private Collection<IapAreaLocationCapability> iapAreaLocationCapabilities = new ArrayList<IapAreaLocationCapability>();
	
	@OneToMany(targetEntity=IapRemoteCampLocationsImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iapForm206")
    @OrderBy("positionNum")
	private Collection<IapRemoteCampLocations> iapRemoteCampLocations = new ArrayList<IapRemoteCampLocations>();

	public IapForm206Impl() {
		super();
	}

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

	/**
	 * @return the iapPlan
	 */
	public IapPlan getIapPlan() {
		return iapPlan;
	}

	/**
	 * @param iapPlan the iapPlan to set
	 */
	public void setIapPlan(IapPlan iapPlan) {
		this.iapPlan = iapPlan;
	}

	/**
	 * @return the iapPlanId
	 */
	public Long getIapPlanId() {
		return iapPlanId;
	}

	/**
	 * @param iapPlanId the iapPlanId to set
	 */
	public void setIapPlanId(Long iapPlanId) {
		this.iapPlanId = iapPlanId;
	}

	/**
	 * @return the preparedBy
	 */
	public String getPreparedBy() {
		return preparedBy;
	}

	/**
	 * @param preparedBy the preparedBy to set
	 */
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	/**
	 * @return the preparedByPosition
	 */
	public String getPreparedByPosition() {
		return preparedByPosition;
	}

	/**
	 * @param preparedByPosition the preparedByPosition to set
	 */
	public void setPreparedByPosition(String preparedByPosition) {
		this.preparedByPosition = preparedByPosition;
	}

	/**
	 * @return the preparedDate
	 */
	public Date getPreparedDate() {
		return preparedDate;
	}

	/**
	 * @param preparedDate the preparedDate to set
	 */
	public void setPreparedDate(Date preparedDate) {
		this.preparedDate = preparedDate;
	}

	/**
	 * @return the approvedBy
	 */
	public String getApprovedBy() {
		return approvedBy;
	}

	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * @return the isFormLocked
	 */
	public StringBooleanEnum getIsFormLocked() {
		return isFormLocked;
	}

	/**
	 * @param isFormLocked the isFormLocked to set
	 */
	public void setIsFormLocked(StringBooleanEnum isFormLocked) {
		this.isFormLocked = isFormLocked;
	}

	/**
	 * @return the isAviationUtilized
	 */
	public StringBooleanEnum getIsAviationUtilized() {
		return isAviationUtilized;
	}

	/**
	 * @param isAviationUtilized the isAviationUtilized to set
	 */
	public void setIsAviationUtilized(StringBooleanEnum isAviationUtilized) {
		this.isAviationUtilized = isAviationUtilized;
	}

	/**
	 * @return the medicalEmergencyProcedures
	 */
	public String getMedicalEmergencyProcedures() {
		return medicalEmergencyProcedures;
	}

	/**
	 * @param medicalEmergencyProcedures the medicalEmergencyProcedures to set
	 */
	public void setMedicalEmergencyProcedures(String medicalEmergencyProcedures) {
		this.medicalEmergencyProcedures = medicalEmergencyProcedures;
	}

	/**
	 * @return the iapHospitals
	 */
	public Collection<IapHospital> getIapHospitals() {
		return iapHospitals;
	}

	/**
	 * @param iapHospitals the iapHospitals to set
	 */
	public void setIapHospitals(Collection<IapHospital> iapHospitals) {
		this.iapHospitals = iapHospitals;
	}

	/**
	 * @return the iapMedicalAids
	 */
	public Collection<IapMedicalAid> getIapMedicalAids() {
		return iapMedicalAids;
	}

	/**
	 * @param iapMedicalAids the iapMedicalAids to set
	 */
	public void setIapMedicalAids(Collection<IapMedicalAid> iapMedicalAids) {
		this.iapMedicalAids = iapMedicalAids;
	}

	/**
	 * @return the iapAreaLocationCapabilities
	 */
	public Collection<IapAreaLocationCapability> getIapAreaLocationCapabilities() {
		return iapAreaLocationCapabilities;
	}

	/**
	 * @param iapAreaLocationCapabilities the iapAreaLocationCapabilities to set
	 */
	public void setIapAreaLocationCapabilities(
			Collection<IapAreaLocationCapability> iapAreaLocationCapabilities) {
		this.iapAreaLocationCapabilities = iapAreaLocationCapabilities;
	}

	/**
	 * @return the iapRemoteCampLocations
	 */
	public Collection<IapRemoteCampLocations> getIapRemoteCampLocations() {
		return iapRemoteCampLocations;
	}

	/**
	 * @param iapRemoteCampLocations the iapRemoteCampLocations to set
	 */
	public void setIapRemoteCampLocations(
			Collection<IapRemoteCampLocations> iapRemoteCampLocations) {
		this.iapRemoteCampLocations = iapRemoteCampLocations;
	}

	/**
	 * @return the reviewedBy
	 */
	public String getReviewedBy() {
		return reviewedBy;
	}

	/**
	 * @param reviewedBy the reviewedBy to set
	 */
	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}

	/**
	 * @return the reviewedDate
	 */
	public Date getReviewedDate() {
		return reviewedDate;
	}

	/**
	 * @param reviewedDate the reviewedDate to set
	 */
	public void setReviewedDate(Date reviewedDate) {
		this.reviewedDate = reviewedDate;
	} 

}
