package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@XmlTransferTable(name = "IswCostGroup", table = "isw_cost_group")
public class IswCostGroup {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_COST_GROUP", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "IncidentTransferableIdentity", alias="iti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IncidentId"
		, disjoined=true, disjoinedtable="isw_incident", disjoinedfield="transferable_identity",disjoinedsource="incident_id")
	private String incidentTransferableIdentity;

	@XmlTransferField(name = "IncidentId", sqlname="INCIDENT_ID", type="LONG"
		,derived=true,derivedfield="IncidentTransferableIdentity")
	private Long incidentId;

	@XmlTransferField(name = "CostGroupName", sqlname = "COST_GROUP_NAME", type="STRING")
	private String costGroupName;

	@XmlTransferField(name = "Description", sqlname = "DESCRIPTION", type="STRING")
	private String description;

	@XmlTransferField(name = "StartDate", sqlname = "START_DATE", type = "DATE")
	private Date startDate;
	
	@XmlTransferField(name = "IncidentShift", type="COMPLEX", target=IswIncidentShift.class
			, lookupname="Id", sourcename="IncidentShiftId")
	private IswIncidentShift incidentShift;
	
	@XmlTransferField(name = "IncidentShiftId", sqlname = "INCIDENT_SHIFT_ID", type = "LONG"
							, derived = true, derivedfield = "IncidentShift")
	private Long incidentShiftId;

	@XmlTransferField(name = "DeletedDate", sqlname = "DELETED_DATE", type = "DATE")
	private Date deletedDate;

	@XmlTransferField(name = "CostGroupAgencyDayShare", type = "COMPLEX", target=IswCostGroupAgencyDayShare.class
						,lookupname="CostGroupId", sourcename="Id"
						,cascade=true)
	private Collection<IswCostGroupAgencyDayShare> costGroupAgencyDayShares = new ArrayList<IswCostGroupAgencyDayShare>();

	@XmlTransferField(name = "CostGroupDfAgPct", type = "COMPLEX", target=IswCostGroupDefaultAgencyDaySharePercentage.class
			,lookupname="CostGroupId", sourcename="Id"
			,cascade=true)
	private Collection<IswCostGroupDefaultAgencyDaySharePercentage> costGroupDfAgPcts=new ArrayList<IswCostGroupDefaultAgencyDaySharePercentage>();
	
	public IswCostGroup() {
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
	 * @return the costGroupName
	 */
	public String getCostGroupName() {
		return costGroupName;
	}

	/**
	 * @param costGroupName the costGroupName to set
	 */
	public void setCostGroupName(String costGroupName) {
		this.costGroupName = costGroupName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the incidentShiftId
	 */
	public Long getIncidentShiftId() {
		return incidentShiftId;
	}

	/**
	 * @param incidentShiftId the incidentShiftId to set
	 */
	public void setIncidentShiftId(Long incidentShiftId) {
		this.incidentShiftId = incidentShiftId;
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
	 * @return the costGroupAgencyDayShares
	 */
	public Collection<IswCostGroupAgencyDayShare> getCostGroupAgencyDayShares() {
		return costGroupAgencyDayShares;
	}

	/**
	 * @param costGroupAgencyDayShares the costGroupAgencyDayShares to set
	 */
	public void setCostGroupAgencyDayShares(
			Collection<IswCostGroupAgencyDayShare> costGroupAgencyDayShares) {
		this.costGroupAgencyDayShares = costGroupAgencyDayShares;
	}

	/**
	 * @return the costGroupDfAgPcts
	 */
	public Collection<IswCostGroupDefaultAgencyDaySharePercentage> getCostGroupDfAgPcts() {
		return costGroupDfAgPcts;
	}

	/**
	 * @param costGroupDfAgPcts the costGroupDfAgPcts to set
	 */
	public void setCostGroupDfAgPcts(
			Collection<IswCostGroupDefaultAgencyDaySharePercentage> costGroupDfAgPcts) {
		this.costGroupDfAgPcts = costGroupDfAgPcts;
	}

	/**
	 * @return the incidentShift
	 */
	public IswIncidentShift getIncidentShift() {
		return incidentShift;
	}

	/**
	 * @param incidentShift the incidentShift to set
	 */
	public void setIncidentShift(IswIncidentShift incidentShift) {
		this.incidentShift = incidentShift;
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


}
