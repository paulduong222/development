package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswIncidentGroupQSKind", table="isw_incident_group_qs_kind")
public class IswIncidentGroupQSKind {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCIDENT_GROUP_QS_KIND", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "IncidentGroupId", sqlname="INCIDENT_GROUP_ID", type="LONG", updateable=false)
	private Long incidentGroupId;

	@XmlTransferField(name = "IgKindTransferableIdentity", alias="igkti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="IncidentGroupKindId"
						, disjoined=true, disjoinedtable="iswl_kind", disjoinedfield="transferable_identity",disjoinedsource="INCIDENT_GROUP_KIND_ID")
	private String igKindTransferableIdentity;

	@XmlTransferField(name = "IncidentGroupKindId", sqlname="INCIDENT_GROUP_KIND_ID", type="LONG"
						,derived=true, derivedfield="IgKindTransferableIdentity")
	private Long incidentGroupKindId;

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
	 * @return the igKindTransferableIdentity
	 */
	public String getIgKindTransferableIdentity() {
		return igKindTransferableIdentity;
	}

	/**
	 * @param igKindTransferableIdentity the igKindTransferableIdentity to set
	 */
	public void setIgKindTransferableIdentity(String igKindTransferableIdentity) {
		this.igKindTransferableIdentity = igKindTransferableIdentity;
	}

	/**
	 * @return the incidentGroupKindId
	 */
	public Long getIncidentGroupKindId() {
		return incidentGroupKindId;
	}

	/**
	 * @param incidentGroupKindId the incidentGroupKindId to set
	 */
	public void setIncidentGroupKindId(Long incidentGroupKindId) {
		this.incidentGroupKindId = incidentGroupKindId;
	}


	
}
