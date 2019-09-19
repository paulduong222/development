package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Collection;

@XmlTransferTable(name = "IswIncidentResource", table="isw_incident_resource")
public class IswIncidentResource {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCIDENT_RESOURCE", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "IncidentId", sqlname="INCIDENT_ID", type="LONG",updateable=false)
	private Long incidentId;

	@XmlTransferField(name = "Resource", type="COMPLEX",target=IswResource.class
						,lookupname="Id", sourcename="ResourceId")
	private IswResource resource;

	@XmlTransferField(name = "ResourceId", sqlname="RESOURCE_ID", type="LONG"
						,derived=true, derivedfield="Resource")
	private Long resourceId;

	@XmlTransferField(name = "ResNumId", sqlname="RES_NUM_ID", type="LONG")
	private Long resNumId;
	
	@XmlTransferField(name = "NameAtIncident", sqlname="NAME_AT_INCIDENT", type="STRING")
	private String nameAtIncident;

	@XmlTransferField(name = "Active", sqlname="ACTIVE", type="BOOLEAN")
	private Boolean active=true; 

	@XmlTransferField(name = "CostData", type="COMPLEX",target=IswCostData.class
						,lookupname="Id", sourcename="CostDataId")
	private IswCostData costData;

	@XmlTransferField(name = "CostDataId", sqlname="COST_DATA_ID", type="LONG"
						,derived=true, derivedfield="CostData")
	private Long costDataId;

	@XmlTransferField(name = "RossResReqId", sqlname="ROSS_RES_REQ_ID", type="LONG")
	private Long rossResReqId;

	@XmlTransferField(name = "DailyCostException", sqlname="DAILY_COST_EXCEPTION", type="STRING")
	private String dailyCostException;

	@XmlTransferField(name = "WorkPeriod", type="COMPLEX", target=IswWorkPeriod.class
						,lookupname="IncidentResourceId", sourcename="Id"
						,cascade=true)
	private IswWorkPeriod workPeriod;

	@XmlTransferField(name = "IncidentResourceDailyCost", type="COMPLEX", target=IswIncidentResourceDailyCost.class
			,lookupname="IncidentResourceId", sourcename="Id"
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
	 * @return the resource
	 */
	public IswResource getResource() {
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResource(IswResource resource) {
		this.resource = resource;
	}

	/**
	 * @return the resourceId
	 */
	public Long getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @return the nameAtIncident
	 */
	public String getNameAtIncident() {
		return nameAtIncident;
	}

	/**
	 * @param nameAtIncident the nameAtIncident to set
	 */
	public void setNameAtIncident(String nameAtIncident) {
		this.nameAtIncident = nameAtIncident;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
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
	 * @return the rossResReqId
	 */
	public Long getRossResReqId() {
		return rossResReqId;
	}

	/**
	 * @param rossResReqId the rossResReqId to set
	 */
	public void setRossResReqId(Long rossResReqId) {
		this.rossResReqId = rossResReqId;
	}

	/**
	 * @return the dailyCostException
	 */
	public String getDailyCostException() {
		return dailyCostException;
	}

	/**
	 * @param dailyCostException the dailyCostException to set
	 */
	public void setDailyCostException(String dailyCostException) {
		this.dailyCostException = dailyCostException;
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
	 * @return the workPeriod
	 */
	public IswWorkPeriod getWorkPeriod() {
		return workPeriod;
	}

	/**
	 * @param workPeriod the workPeriod to set
	 */
	public void setWorkPeriod(IswWorkPeriod workPeriod) {
		this.workPeriod = workPeriod;
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

	/**
	 * @return the resNumId
	 */
	public Long getResNumId() {
		if(LongUtility.hasValue(resNumId))
			return resNumId;
		else
			return 0L;
	}

	/**
	 * @param resNumId the resNumId to set
	 */
	public void setResNumId(Long resNumId) {
		this.resNumId = resNumId;
	}


}

