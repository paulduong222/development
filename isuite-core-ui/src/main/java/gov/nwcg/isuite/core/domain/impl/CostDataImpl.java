package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.AccrualCode;
import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.CostData;
import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.domain.IncidentShift;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_COST_DATA", sequenceName="SEQ_COST_DATA")
@Table(name = "isw_cost_data")
public class CostDataImpl extends PersistableImpl implements CostData {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_COST_DATA")
	private Long id = 0L;

	@ManyToOne(targetEntity=AccrualCodeImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCRUAL_CODE_ID")
	private AccrualCode accrualCode;

	@ManyToOne(targetEntity=AgencyImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "PAYMENT_AGENCY_ID")
	private Agency paymentAgency;

	@Column(name = "ASSIGN_DATE", nullable=true)
	private Date assignDate;

	@Column(name = "IS_ACCRUAL_LOCKED", precision = 1, scale = 0)
	private Boolean accrualLocked;

	@Column(name = "IS_USE_ACCRUALS_ONLY", precision = 1, scale = 0)
	private Boolean useAccrualsOnly;

	@Column(name = "IS_GENERATE_COSTS", precision = 1, scale = 0)
	private Boolean generateCosts;

	@Column(name = "IS_GENERATE_COSTS_SYS")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum generateCostsSys;
	
	@Column(name = "COST_REMARKS", length = 1000)
	private String costRemarks;

	@Column(name = "COST_OTHER_1", length = 15)
	private String costOther1;

	@Column(name = "COST_OTHER_2", length = 15)
	private String costOther2;

	@Column(name = "COST_OTHER_3", length = 15)
	private String costOther3;

    @ManyToOne(targetEntity=CostGroupImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "DEFAULT_COST_GROUP_ID", insertable=true, updatable=true)
	private CostGroup defaultCostGroup;
    
	@Column(name="DEFAULT_COST_GROUP_ID", insertable=false, updatable=false)
	private Long defaultCostGroupId;
	
    @OneToOne(targetEntity=IncidentShiftImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "DEFAULT_INC_SHIFT_ID", insertable=true, updatable=true)
	private IncidentShift defaultIncidentShift;
    
	@Column(name="DEFAULT_INC_SHIFT_ID", insertable=false, updatable=false)
	private Long defaultIncidentShiftId;

	@OneToOne(targetEntity=IncidentResourceImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "costData")
	private IncidentResource incidentResource = null;
	
//	@OneToMany(targetEntity=IncidentResourceOtherImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "costData")
//	private Collection<IncidentResourceOther> incidentResourceOthers = new ArrayList<IncidentResourceOther>();

	@OneToOne(targetEntity=IncidentResourceOtherImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "costData")
	private IncidentResourceOther incidentResourceOther = null; //TODO MANU REMOVE: new IncidentResourceOtherImpl();

	
	public CostDataImpl() {
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
	 * @return the accrualCode
	 */
	public AccrualCode getAccrualCode() {
		return accrualCode;
	}


	/**
	 * @param accrualCode the accrualCode to set
	 */
	public void setAccrualCode(AccrualCode accrualCode) {
		this.accrualCode = accrualCode;
	}


	/**
	 * @return the paymentAgency
	 */
	public Agency getPaymentAgency() {
		return paymentAgency;
	}


	/**
	 * @param paymentAgency the paymentAgency to set
	 */
	public void setPaymentAgency(Agency paymentAgency) {
		this.paymentAgency = paymentAgency;
	}


	/**
	 * @return the assignDate
	 */
	public Date getAssignDate() {
		return assignDate;
	}


	/**
	 * @param assignDate the assignDate to set
	 */
	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}


	/**
	 * @return the accrualLocked
	 */
	public Boolean getAccrualLocked() {
		return accrualLocked;
	}


	/**
	 * @param accrualLocked the accrualLocked to set
	 */
	public void setAccrualLocked(Boolean accrualLocked) {
		this.accrualLocked = accrualLocked;
	}


	/**
	 * @return the useAccrualsOnly
	 */
	public Boolean getUseAccrualsOnly() {
		return useAccrualsOnly;
	}


	/**
	 * @param useAccrualsOnly the useAccrualsOnly to set
	 */
	public void setUseAccrualsOnly(Boolean useAccrualsOnly) {
		this.useAccrualsOnly = useAccrualsOnly;
	}


	/**
	 * @return the generateCosts
	 */
	public Boolean getGenerateCosts() {
		return generateCosts;
	}


	/**
	 * @param generateCosts the generateCosts to set
	 */
	public void setGenerateCosts(Boolean generateCosts) {
		this.generateCosts = generateCosts;
	}


	/**
	 * @return the costRemarks
	 */
	public String getCostRemarks() {
		return costRemarks;
	}


	/**
	 * @param costRemarks the costRemarks to set
	 */
	public void setCostRemarks(String costRemarks) {
		this.costRemarks = costRemarks;
	}


	/**
	 * @return the costOther1
	 */
	public String getCostOther1() {
		return costOther1;
	}


	/**
	 * @param costOther1 the costOther1 to set
	 */
	public void setCostOther1(String costOther1) {
		this.costOther1 = costOther1;
	}


	/**
	 * @return the costOther2
	 */
	public String getCostOther2() {
		return costOther2;
	}


	/**
	 * @param costOther2 the costOther2 to set
	 */
	public void setCostOther2(String costOther2) {
		this.costOther2 = costOther2;
	}


	/**
	 * @return the costOther3
	 */
	public String getCostOther3() {
		return costOther3;
	}


	/**
	 * @param costOther3 the costOther3 to set
	 */
	public void setCostOther3(String costOther3) {
		this.costOther3 = costOther3;
	}


	/**
	 * @return the incidentResource
	 */
	public IncidentResource getIncidentResource() {
		return incidentResource;
	}


	/**
	 * @param incidentResource the incidentResource to set
	 */
	public void setIncidentResource(IncidentResource incidentResource) {
		this.incidentResource = incidentResource;
	}


//	/**
//	 * @return the incidentResourceOthers
//	 */
//	public Collection<IncidentResourceOther> getIncidentResourceOthers() {
//		return incidentResourceOthers;
//	}
//
//
//	/**
//	 * @param incidentResourceOthers the incidentResourceOthers to set
//	 */
//	public void setIncidentResourceOthers(
//			Collection<IncidentResourceOther> incidentResourceOthers) {
//		this.incidentResourceOthers = incidentResourceOthers;
//	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( this == obj ) return true;
		if ( getClass() != obj.getClass() ) return false;
		CostDataImpl o = (CostDataImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id},
				new Object[]{o.id})
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
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.appendSuper(super.toString())
		.toString();
	}


	/**
	 * @return the incidentResourceOther
	 */
	public IncidentResourceOther getIncidentResourceOther() {
		return incidentResourceOther;
	}


	/**
	 * @param incidentResourceOther the incidentResourceOther to set
	 */
	public void setIncidentResourceOther(IncidentResourceOther incidentResourceOther) {
		this.incidentResourceOther = incidentResourceOther;
	}


	/**
	 * @return the costGroup
	 */
	public CostGroup getDefaultCostGroup() {
		return defaultCostGroup;
	}


	/**
	 * @param costGroup the costGroup to set
	 */
	public void setDefaultCostGroup(CostGroup costGroup) {
		this.defaultCostGroup = costGroup;
	}


	/**
	 * @return the costGroupId
	 */
	public Long getDefaultCostGroupId() {
		return defaultCostGroupId;
	}


	/**
	 * @param costGroupId the costGroupId to set
	 */
	public void setDefaultCostGroupId(Long costGroupId) {
		this.defaultCostGroupId = costGroupId;
	}


	/**
	 * @return the defaultIncidentShift
	 */
	public IncidentShift getDefaultIncidentShift() {
		return defaultIncidentShift;
	}


	/**
	 * @param defaultIncidentShift the defaultIncidentShift to set
	 */
	public void setDefaultIncidentShift(IncidentShift defaultIncidentShift) {
		this.defaultIncidentShift = defaultIncidentShift;
	}


	/**
	 * @return the defaultIncidentShiftId
	 */
	public Long getDefaultIncidentShiftId() {
		return defaultIncidentShiftId;
	}


	/**
	 * @param defaultIncidentShiftId the defaultIncidentShiftId to set
	 */
	public void setDefaultIncidentShiftId(Long defaultIncidentShiftId) {
		this.defaultIncidentShiftId = defaultIncidentShiftId;
	}


	/**
	 * @return the generateCostsSys
	 */
	public StringBooleanEnum getGenerateCostsSys() {
		return generateCostsSys;
	}


	/**
	 * @param generateCostsSys the generateCostsSys to set
	 */
	public void setGenerateCostsSys(StringBooleanEnum generateCostsSys) {
		this.generateCostsSys = generateCostsSys;
	}


}
