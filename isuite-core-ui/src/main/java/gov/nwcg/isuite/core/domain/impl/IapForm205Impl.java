package gov.nwcg.isuite.core.domain.impl;

import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.core.domain.IapForm205;
import gov.nwcg.isuite.core.domain.IapFrequency;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

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
import javax.persistence.Lob;

@Entity
@SequenceGenerator(name="SEQ_IAP_FORM_205", sequenceName="SEQ_IAP_FORM_205")
@Table(name = "isw_iap_form_205")
public class IapForm205Impl extends PersistableImpl implements IapForm205 {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_IAP_FORM_205")
	private Long id = 0L;

	@ManyToOne(targetEntity=IapPlanImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IAP_PLAN_ID", nullable = false)
	private IapPlan iapPlan;

	@Column(name = "IAP_PLAN_ID", insertable = false, updatable = false, unique=false, nullable = false)
	private Long iapPlanId;

	@Lob
	@Column(name = "SPECIAL_INSTRUCTION")
	private String specialInstruction;
	
	@Column(name = "PREPARED_BY", length = 50)
	private String preparedBy;

	@Column(name = "PREPARED_BY_POS", length = 50)
	private String preparedByPosition;
	
	@Column(name = "PREPARED_DATE")
	private Date preparedDate;

	@Column(name = "IS_FORM_LOCKED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isFormLocked;
	
	@OneToMany(targetEntity=IapFrequencyImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iapForm205")	
    @OrderBy("positionNum")
	private Collection<IapFrequency> iapFrequencies;
	
	public IapForm205Impl() {
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
	 * @return the specialInstruction
	 */
	public String getSpecialInstruction() {
		return specialInstruction;
	}

	/**
	 * @param specialInstruction the specialInstruction to set
	 */
	public void setSpecialInstruction(String specialInstruction) {
		this.specialInstruction = specialInstruction;
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
	 * @return the iapFrequencies
	 */
	public Collection<IapFrequency> getIapFrequencies() {
		return iapFrequencies;
	}

	/**
	 * @param iapFrequencies the iapFrequencies to set
	 */
	public void setIapFrequencies(Collection<IapFrequency> iapFrequencies) {
		this.iapFrequencies = iapFrequencies;
	} 

}
