package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.ArrayList;
import java.util.Collection;

@XmlTransferTable(name = "IswIncidentCostRate", table="isw_inccost_rate")
public class IswIncidentCostRate {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCCOST_RATE", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "CostRateCategory", sqlname="COST_RATE_CATEGORY", type="STRING")
	private String costRateCategory;

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

	@XmlTransferField(name = "IncidentCostRateState", type="COMPLEX", target=IswIncidentCostRateState.class
			, lookupname="IncidentCostRateId", sourcename="Id"
			, cascade=true)
	private Collection<IswIncidentCostRateState> incidentCostRateStates = new ArrayList<IswIncidentCostRateState>();

	@XmlTransferField(name = "IncidentCostRateKind", type="COMPLEX", target=IswIncidentCostRateKind.class
			, lookupname="IncidentCostRateId", sourcename="Id"
			, cascade=true)
	private Collection<IswIncidentCostRateKind> incidentCostRateKinds = new ArrayList<IswIncidentCostRateKind>();
	
	@XmlTransferField(name = "IncidentCostRateOvhd", type="COMPLEX", target=IswIncidentCostRateOvhd.class
			, lookupname="IncidentCostRateId", sourcename="Id"
			, cascade=true)
	private Collection<IswIncidentCostRateOvhd> incidentCostRateOvhds = new ArrayList<IswIncidentCostRateOvhd>();

	
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
	 * @return the costRateCategory
	 */
	public String getCostRateCategory() {
		return costRateCategory;
	}

	/**
	 * @param costRateCategory the costRateCategory to set
	 */
	public void setCostRateCategory(String costRateCategory) {
		this.costRateCategory = costRateCategory;
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
	 * @return the incidentCostRateStates
	 */
	public Collection<IswIncidentCostRateState> getIncidentCostRateStates() {
		return incidentCostRateStates;
	}

	/**
	 * @param incidentCostRateStates the incidentCostRateStates to set
	 */
	public void setIncidentCostRateStates(
			Collection<IswIncidentCostRateState> incidentCostRateStates) {
		this.incidentCostRateStates = incidentCostRateStates;
	}

	/**
	 * @return the incidentCostRateKinds
	 */
	public Collection<IswIncidentCostRateKind> getIncidentCostRateKinds() {
		return incidentCostRateKinds;
	}

	/**
	 * @param incidentCostRateKinds the incidentCostRateKinds to set
	 */
	public void setIncidentCostRateKinds(
			Collection<IswIncidentCostRateKind> incidentCostRateKinds) {
		this.incidentCostRateKinds = incidentCostRateKinds;
	}

	/**
	 * @return the incidentCostRateOvhds
	 */
	public Collection<IswIncidentCostRateOvhd> getIncidentCostRateOvhds() {
		return incidentCostRateOvhds;
	}

	/**
	 * @param incidentCostRateOvhds the incidentCostRateOvhds to set
	 */
	public void setIncidentCostRateOvhds(
			Collection<IswIncidentCostRateOvhd> incidentCostRateOvhds) {
		this.incidentCostRateOvhds = incidentCostRateOvhds;
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
	
	/*
	@ManyToMany(targetEntity=IncidentCostRateStateImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentCostRate")
	private Collection<IncidentCostRateState> incidentCostRateStates = new ArrayList<IncidentCostRateState>();

	@ManyToMany(targetEntity=IncidentCostRateKindImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentCostRate")
	private Collection<IncidentCostRateKind> incidentCostRateKinds = new ArrayList<IncidentCostRateKind>();

	@ManyToMany(targetEntity=IncidentCostRateOvhdImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentCostRate")
	private Collection<IncidentCostRateOvhd> incidentCostRateOvhds = new ArrayList<IncidentCostRateOvhd>();
	*/

}
