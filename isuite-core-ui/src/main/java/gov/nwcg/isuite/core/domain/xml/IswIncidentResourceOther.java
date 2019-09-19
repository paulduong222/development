package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.Collection;

@XmlTransferTable(name = "IswIncidentResourceOther", table="isw_incident_resource_other")
public class IswIncidentResourceOther {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCIDENT_RESOURCE_OTHER", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "IncidentId", sqlname="INCIDENT_ID", type="LONG",updateable=false)
	private Long incidentId;

	@XmlTransferField(name = "ResourceOther", type="COMPLEX",target=IswResourceOther.class
						,lookupname="Id", sourcename="ResourceOtherId")
	private IswResourceOther resourceOther;

	@XmlTransferField(name = "ResourceOtherId", sqlname="RESOURCE_OTHER_ID", type="LONG"
						,derived=true, derivedfield="ResourceOther")
	private Long resourceOtherId;

	@XmlTransferField(name = "CostData", type="COMPLEX",target=IswCostData.class
						,lookupname="Id", sourcename="CostDataId")
	private IswCostData costData;

	@XmlTransferField(name = "CostDataId", sqlname="COST_DATA_ID", type="LONG"
						,derived=true, derivedfield="CostData")
	private Long costDataId;

	@XmlTransferField(name = "AssignmentStatus", sqlname = "ASSIGNMENT_STATUS", type="STRING")
	private String assignmentStatus;
	
	@XmlTransferField(name = "IncidentResourceDailyCost", type="COMPLEX", target=IswIncidentResourceDailyCost.class
			,lookupname="IncidentResourceOtherId", sourcename="Id"
			,cascade=true)
	private Collection<IswIncidentResourceDailyCost> incidentResourceDailyCosts;

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
	 * @return the resourceOther
	 */
	public IswResourceOther getResourceOther() {
		return resourceOther;
	}

	/**
	 * @param resourceOther the resourceOther to set
	 */
	public void setResourceOther(IswResourceOther resourceOther) {
		this.resourceOther = resourceOther;
	}

	/**
	 * @return the resourceOtherId
	 */
	public Long getResourceOtherId() {
		return resourceOtherId;
	}

	/**
	 * @param resourceOtherId the resourceOtherId to set
	 */
	public void setResourceOtherId(Long resourceOtherId) {
		this.resourceOtherId = resourceOtherId;
	}

	/**
	 * @return the costData
	 */
	public IswCostData getCostData() {
		return costData;
	}

	/**
	 * @param costData the costData to set
	 */
	public void setCostData(IswCostData costData) {
		this.costData = costData;
	}

	/**
	 * @return the costDataId
	 */
	public Long getCostDataId() {
		return costDataId;
	}

	/**
	 * @param costDataId the costDataId to set
	 */
	public void setCostDataId(Long costDataId) {
		this.costDataId = costDataId;
	}

	/**
	 * @return the assignmentStatus
	 */
	public String getAssignmentStatus() {
		return assignmentStatus;
	}

	/**
	 * @param assignmentStatus the assignmentStatus to set
	 */
	public void setAssignmentStatus(String assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
	}

	/**
	 * @return the incidentResourceDailyCosts
	 */
	public Collection<IswIncidentResourceDailyCost> getIncidentResourceDailyCosts() {
		return incidentResourceDailyCosts;
	}

	/**
	 * @param incidentResourceDailyCosts the incidentResourceDailyCosts to set
	 */
	public void setIncidentResourceDailyCosts(
			Collection<IswIncidentResourceDailyCost> incidentResourceDailyCosts) {
		this.incidentResourceDailyCosts = incidentResourceDailyCosts;
	}
	


}

