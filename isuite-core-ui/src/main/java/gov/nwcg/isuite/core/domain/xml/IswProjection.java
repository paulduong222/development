package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@XmlTransferTable(name = "IswProjection", table = "isw_projection")
public class IswProjection {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_PROJECTION", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "ProjectionName", sqlname = "PROJECTION_NAME", type="STRING")
	private String projectionName;

	@XmlTransferField(name = "StartDate", sqlname = "START_DATE", type="DATE")
	private Date startDate;

	@XmlTransferField(name = "NumberOfDays", sqlname = "NUMBER_OF_DAYS", type="SHORT")
	private Short numberOfDays;

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

	@XmlTransferField(name = "ProjectionItem", type="COMPLEX", target=IswProjectionItem.class
						,lookupname="ProjectionId", sourcename="Id"
						,cascade=true)
	private Collection<IswProjectionItem> projectionItems=new ArrayList<IswProjectionItem>();

	/**
	 * Default constructor.
	 *
	 */
	public IswProjection() {
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
	 * @return the projectionName
	 */
	public String getProjectionName() {
		return projectionName;
	}


	/**
	 * @param projectionName the projectionName to set
	 */
	public void setProjectionName(String projectionName) {
		this.projectionName = projectionName;
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
	 * @return the numberOfDays
	 */
	public Short getNumberOfDays() {
		return numberOfDays;
	}


	/**
	 * @param numberOfDays the numberOfDays to set
	 */
	public void setNumberOfDays(Short numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	public Long getIncidentId() {
		return incidentId;
	}


	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}


	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
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
	 * @return the projectionItems
	 */
	public Collection<IswProjectionItem> getProjectionItems() {
		return projectionItems;
	}

	/**
	 * @param projectionItems the projectionItems to set
	 */
	public void setProjectionItems(Collection<IswProjectionItem> projectionItems) {
		this.projectionItems = projectionItems;
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

}
