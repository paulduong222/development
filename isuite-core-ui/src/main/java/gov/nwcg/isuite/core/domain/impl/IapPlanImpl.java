package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.IapAttachment;
import gov.nwcg.isuite.core.domain.IapBranch;
import gov.nwcg.isuite.core.domain.IapForm202;
import gov.nwcg.isuite.core.domain.IapForm203;
import gov.nwcg.isuite.core.domain.IapForm205;
import gov.nwcg.isuite.core.domain.IapForm206;
import gov.nwcg.isuite.core.domain.IapForm220;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.core.domain.IapPlanPrintOrder;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@SequenceGenerator(name = "SEQ_IAP_PLAN", sequenceName = "SEQ_IAP_PLAN")
@Table(name = "isw_iap_plan")
public class IapPlanImpl extends PersistableImpl implements IapPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_IAP_PLAN")
	private Long id = 0L;

	@Column(name = "INCIDENT_GROUP_ID", unique = false, nullable = true)
	private Long incidentGroupId;

	@Column(name = "INCIDENT_ID", unique = false, nullable = true)
	private Long incidentId;

	@Column(name = "INCIDENT_NAME", length = 200)
	private String incidentName;

	@Column(name = "OPERATION_PERIOD", length = 200)
	private String operationPeriod;

	@Column(name = "FROM_DATE")
	private Date fromDate;

	@Column(name = "TO_DATE")
	private Date toDate;

	@Column(name = "IS_PLAN_LOCKED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isPlanLocked;

	@OneToMany(targetEntity = IapForm202Impl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iapPlan")
    @OrderBy("id")
	private Collection<IapForm202> iapForm202s;

	@OneToMany(targetEntity = IapForm203Impl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iapPlan")
    @OrderBy("id")
	private Collection<IapForm203> iapForm203s;

	@OneToMany(targetEntity = IapForm205Impl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iapPlan")
    @OrderBy("id")
	private Collection<IapForm205> iapForm205s;

	@OneToMany(targetEntity = IapForm206Impl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iapPlan")
    @OrderBy("id")
	private Collection<IapForm206> iapForm206s;

	@OneToMany(targetEntity = IapForm220Impl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iapPlan")
    @OrderBy("id")
	private Collection<IapForm220> iapForm220s;

	@OneToMany(targetEntity = IapBranchImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iapPlan")
    @OrderBy("branchName,divisionName")
	private Collection<IapBranch> iapBranchs;

	@OneToMany(targetEntity = IapAttachmentImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iapPlan")
    @OrderBy("id")
	private Collection<IapAttachment> iapAttachments;
	
	@OneToMany(targetEntity = IapPlanPrintOrderImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iapPlan")
    @OrderBy("id")
	private Collection<IapPlanPrintOrder> iapPlanPrintOrder;

	public IapPlanImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.Persistable#getId()
	 */
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.Persistable#setId(java.lang.Long)
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	/**
	 * @param incidentGroupId
	 *            the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentId
	 *            the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the incidentName
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/**
	 * @param incidentName
	 *            the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/**
	 * @return the operationPeriod
	 */
	public String getOperationPeriod() {
		return operationPeriod;
	}

	/**
	 * @param operationPeriod
	 *            the operationPeriod to set
	 */
	public void setOperationPeriod(String operationPeriod) {
		this.operationPeriod = operationPeriod;
	}

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate
	 *            the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the isPlanLocked
	 */
	public StringBooleanEnum getIsPlanLocked() {
		return isPlanLocked;
	}

	/**
	 * @param isPlanLocked
	 *            the isPlanLocked to set
	 */
	public void setIsPlanLocked(StringBooleanEnum isPlanLocked) {
		this.isPlanLocked = isPlanLocked;
	}

	/**
	 * @return the iapForm202s
	 */
	public Collection<IapForm202> getIapForm202s() {
		return iapForm202s;
	}

	/**
	 * @param iapForm202s
	 *            the iapForm202s to set
	 */
	public void setIapForm202s(Collection<IapForm202> iapForm202s) {
		this.iapForm202s = iapForm202s;
	}

	/**
	 * @return the iapForm203s
	 */
	public Collection<IapForm203> getIapForm203s() {
		return iapForm203s;
	}

	/**
	 * @param iapForm203s
	 *            the iapForm203s to set
	 */
	public void setIapForm203s(Collection<IapForm203> iapForm203s) {
		this.iapForm203s = iapForm203s;
	}

	/**
	 * @return the iapForm205s
	 */
	public Collection<IapForm205> getIapForm205s() {
		return iapForm205s;
	}

	/**
	 * @param iapForm205s
	 *            the iapForm205s to set
	 */
	public void setIapForm205s(Collection<IapForm205> iapForm205s) {
		this.iapForm205s = iapForm205s;
	}

	/**
	 * @return the iapForm220s
	 */
	public Collection<IapForm220> getIapForm220s() {
		return iapForm220s;
	}

	/**
	 * @param iapForm220s
	 *            the iapForm220s to set
	 */
	public void setIapForm220s(Collection<IapForm220> iapForm220s) {
		this.iapForm220s = iapForm220s;
	}

	/**
	 * @return the iapBranchs
	 */
	public Collection<IapBranch> getIapBranchs() {
		return iapBranchs;
	}

	/**
	 * @param iapBranchs
	 *            the iapBranchs to set
	 */
	public void setIapBranchs(Collection<IapBranch> iapBranchs) {
		this.iapBranchs = iapBranchs;
	}

	/**
	 * @return the iapAttachments
	 */
	public Collection<IapAttachment> getIapAttachments() {
		return iapAttachments;
	}

	/**
	 * @param iapAttachments
	 *            the iapAttachments to set
	 */
	public void setIapAttachments(Collection<IapAttachment> iapAttachments) {
		this.iapAttachments = iapAttachments;
	}
	/**
	 * @return the iapPlanPrintOrder
	 */
	public Collection<IapPlanPrintOrder> getIapPlanPrintOrder() {
		return iapPlanPrintOrder;
	}

	/**
	 * @param iapPlanPrintOrder
	 *            the iapPlanPrintOrder to set
	 */
	public void setIapPlanPrintOrder(Collection<IapPlanPrintOrder> iapPlanPrintOrder) {
		this.iapPlanPrintOrder = iapPlanPrintOrder;
	}
	/**
	 * @return the iapForm206s
	 */
	public Collection<IapForm206> getIapForm206s() {
		return iapForm206s;
	}

	/**
	 * @param iapForm206s the iapForm206s to set
	 */
	public void setIapForm206s(Collection<IapForm206> iapForm206s) {
		this.iapForm206s = iapForm206s;
	}
	
	@Transient
	public boolean isPlanEditable() {
		if(this.isPlanLocked.getValue())
			return false;
		
		Collection<IapForm202> iapForm202s = this.getIapForm202s();
		for(IapForm202 form202 : iapForm202s)
			if(form202.getIsFormLocked().getValue()) 
				return false;
		
		Collection<IapForm203> iapForm203s = this.getIapForm203s();
		for(IapForm203 form203 : iapForm203s)
			if(form203.getIsFormLocked().getValue()) 
				return false;
		
		Collection<IapForm205> iapForm205s = this.getIapForm205s();
		for(IapForm205 form205 : iapForm205s)
			if(form205.getIsFormLocked().getValue()) 
				return false;
		
		Collection<IapForm206> iapForm206s = this.getIapForm206s();
		for(IapForm206 form206 : iapForm206s)
			if(form206.getIsFormLocked().getValue()) 
				return false;
		
		Collection<IapForm220> iapForm220s = this.getIapForm220s();
		for(IapForm220 form220 : iapForm220s)
			if(form220.getIsFormLocked().getValue()) 
				return false;
		
		return true;
	}
}
