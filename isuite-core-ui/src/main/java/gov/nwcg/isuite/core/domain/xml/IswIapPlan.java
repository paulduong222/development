package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@XmlTransferTable(name = "IswIapPlan", table="isw_iap_plan")
public class IswIapPlan {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_PLAN", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "IncidentGroupTransferableIdentity", alias="igti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IncidentGroupId"
		, disjoined=true, disjoinedtable="isw_incident_group", disjoinedfield="transferable_identity",disjoinedsource="incident_group_id")
	private String incidentGroupTransferableIdentity;

	@XmlTransferField(name = "IncidentGroupId", sqlname="INCIDENT_GROUP_ID", type="LONG"
		,derived=true,derivedfield="IncidentGroupTransferableIdentity")
	private Long incidentGroupId;

	@XmlTransferField(name = "IncidentTransferableIdentity", alias="iti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IncidentId"
		, disjoined=true, disjoinedtable="isw_incident", disjoinedfield="transferable_identity",disjoinedsource="incident_id")
	private String incidentTransferableIdentity;

	@XmlTransferField(name = "IncidentId", sqlname="INCIDENT_ID", type="LONG"
		,derived=true,derivedfield="IncidentTransferableIdentity")
	private Long incidentId;

	@XmlTransferField(name = "IncidentName", sqlname="INCIDENT_NAME", type="STRING")
	private String incidentName;

	@XmlTransferField(name = "OperationPeriod", sqlname="OPERATION_PERIOD", type="STRING")
	private String operationPeriod;

	@XmlTransferField(name = "FromDate", sqlname="FROM_DATE", type="DATE")
	private Date fromDate;

	@XmlTransferField(name = "ToDate", sqlname="TO_DATE", type="DATE")
	private Date toDate;

	@XmlTransferField(name = "IsPlanLocked", sqlname="IS_PLAN_LOCKED", type="STRING", defaultvalue="N")
	private String isPlanLocked;

	@XmlTransferField(name = "IapForm202", type="COMPLEX", target=IswIapForm202.class
						, lookupname="IapPlanId", sourcename="Id"
						, cascade=true)
	private Collection<IswIapForm202> iapForm202s = new ArrayList<IswIapForm202>();
	
	@XmlTransferField(name = "IapForm203", type="COMPLEX", target=IswIapForm203.class
			, lookupname="IapPlanId", sourcename="Id"
			, cascade=true)
	private Collection<IswIapForm203> iapForm203s = new ArrayList<IswIapForm203>();
	
	@XmlTransferField(name = "IapBranch", type="COMPLEX", target=IswIapBranch.class
			, lookupname="IapPlanId", sourcename="Id"
			, cascade=true)
	private Collection<IswIapBranch> iapBranchs = new ArrayList<IswIapBranch>();
	
	@XmlTransferField(name = "IapForm205", type="COMPLEX", target=IswIapForm205.class
			, lookupname="IapPlanId", sourcename="Id"
			, cascade=true)
	private Collection<IswIapForm205> iapForm205s = new ArrayList<IswIapForm205>();

	@XmlTransferField(name = "IapForm206", type="COMPLEX", target=IswIapForm206.class
			, lookupname="IapPlanId", sourcename="Id"
			, cascade=true)
	private Collection<IswIapForm206> iapForm206s = new ArrayList<IswIapForm206>();
	
	@XmlTransferField(name = "IapForm220", type="COMPLEX", target=IswIapForm220.class
			, lookupname="IapPlanId", sourcename="Id"
			, cascade=true)
	private Collection<IswIapForm220> iapForm220s = new ArrayList<IswIapForm220>();

	/*
	 * 
	@XmlTransferField(name = "IapAttachment", type="COMPLEX", target=IswIapAttachment.class
			, lookupname="IapPlanId", sourcename="Id"
			, cascade=true)
	private Collection<IswIapAttachment> iapAttachments = new ArrayList<IswIapAttachment>();
	
	@XmlTransferField(name = "IapPlanPrintOrder", type="COMPLEX", target=IswIapPlanPrintOrder.class
			, lookupname="IapPlanId", sourcename="Id"
			, cascade=true)
	private Collection<IswIapPlanPrintOrder> iapPlanPrintOrder = new ArrayList<IswIapPlanPrintOrder>();
	
	*/
	
	
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
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	/**
	 * @param incidentGroupId the incidentGroupId to set
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
	 * @param incidentId the incidentId to set
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
	 * @param incidentName the incidentName to set
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
	 * @param operationPeriod the operationPeriod to set
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
	 * @param fromDate the fromDate to set
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
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the isPlanLocked
	 */
	public String getIsPlanLocked() {
		return isPlanLocked;
	}

	/**
	 * @param isPlanLocked the isPlanLocked to set
	 */
	public void setIsPlanLocked(String isPlanLocked) {
		this.isPlanLocked = isPlanLocked;
	}

	/**
	 * @return the transferableIdentity
	 */
	public String getTransferableIdentity() {
		return transferableIdentity;
	}

	/**
	 * @param transferableIdentity the transferableIdentity to set
	 */
	public void setTransferableIdentity(String transferableIdentity) {
		this.transferableIdentity = transferableIdentity;
	}

	/**
	 * @return the incidentGroupTransferableIdentity
	 */
	public String getIncidentGroupTransferableIdentity() {
		return incidentGroupTransferableIdentity;
	}

	/**
	 * @param incidentGroupTransferableIdentity the incidentGroupTransferableIdentity to set
	 */
	public void setIncidentGroupTransferableIdentity(
			String incidentGroupTransferableIdentity) {
		this.incidentGroupTransferableIdentity = incidentGroupTransferableIdentity;
	}

	/**
	 * @return the incidentTransferableIdentity
	 */
	public String getIncidentTransferableIdentity() {
		return incidentTransferableIdentity;
	}

	/**
	 * @param incidentTransferableIdentity the incidentTransferableIdentity to set
	 */
	public void setIncidentTransferableIdentity(String incidentTransferableIdentity) {
		this.incidentTransferableIdentity = incidentTransferableIdentity;
	}

	/**
	 * @return the iapForm202s
	 */
	public Collection<IswIapForm202> getIapForm202s() {
		return iapForm202s;
	}

	/**
	 * @param iapForm202s the iapForm202s to set
	 */
	public void setIapForm202s(Collection<IswIapForm202> iapForm202s) {
		this.iapForm202s = iapForm202s;
	}

	/**
	 * @return the iapForm203s
	 */
	public Collection<IswIapForm203> getIapForm203s() {
		return iapForm203s;
	}

	/**
	 * @param iapForm203s the iapForm203s to set
	 */
	public void setIapForm203s(Collection<IswIapForm203> iapForm203s) {
		this.iapForm203s = iapForm203s;
	}

	/**
	 * @return the iapBranchs
	 */
	public Collection<IswIapBranch> getIapBranchs() {
		return iapBranchs;
	}

	/**
	 * @param iapBranchs the iapBranchs to set
	 */
	public void setIapBranchs(Collection<IswIapBranch> iapBranchs) {
		this.iapBranchs = iapBranchs;
	}

	/**
	 * @return the iapForm205s
	 */
	public Collection<IswIapForm205> getIapForm205s() {
		return iapForm205s;
	}

	/**
	 * @param iapForm205s the iapForm205s to set
	 */
	public void setIapForm205s(Collection<IswIapForm205> iapForm205s) {
		this.iapForm205s = iapForm205s;
	}

	/**
	 * @return the iapForm206s
	 */
	public Collection<IswIapForm206> getIapForm206s() {
		return iapForm206s;
	}

	/**
	 * @param iapForm206s the iapForm206s to set
	 */
	public void setIapForm206s(Collection<IswIapForm206> iapForm206s) {
		this.iapForm206s = iapForm206s;
	}

	/**
	 * @return the iapForm220s
	 */
	public Collection<IswIapForm220> getIapForm220s() {
		return iapForm220s;
	}

	/**
	 * @param iapForm220s the iapForm220s to set
	 */
	public void setIapForm220s(Collection<IswIapForm220> iapForm220s) {
		this.iapForm220s = iapForm220s;
	}

}
