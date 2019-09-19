package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswIncidentGroupIncident", table="isw_incident_group_incident")
public class IswIncidentGroupIncident {

	@XmlTransferField(name = "IncidentTransferableIdentity", alias="iti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IncidentId"
		, disjoined=true, disjoinedtable="isw_incident", disjoinedfield="transferable_identity",disjoinedsource="INCIDENT_ID")
	private String incidentTransferableIdentity;

	@XmlTransferField(name = "IncidentId", sqlname="INCIDENT_ID", type="LONG"
		,derived=true, derivedfield="incidentTransferableIdentity")
	private Long incidentId;
	
	@XmlTransferField(name = "IncidentGroupTransferableIdentity", alias="igti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="IncidentGroupId"
						, disjoined=true, disjoinedtable="isw_incident_group", disjoinedfield="transferable_identity",disjoinedsource="incident_group_id")
    private String incidentGroupTransferableIdentity;
	
	@XmlTransferField(name = "IncidentGroupId", sqlname="INCIDENT_GROUP_ID", type="LONG"
						,joinkeyprimary=true)
	private Long incidentGroupId;
	

    public IswIncidentGroupIncident(){
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




   
}
