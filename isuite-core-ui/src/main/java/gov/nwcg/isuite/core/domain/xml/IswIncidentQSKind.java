package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswIncidentQSKind", table="isw_incident_qs_kind")
public class IswIncidentQSKind {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCIDENT_QS_KIND", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;

	@XmlTransferField(name = "IncidentId", sqlname="INCIDENT_ID", type="LONG")
	private Long incidentId;

	@XmlTransferField(name = "IncidentKindTransferableIdentity", alias="ikti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="IncidentKindId"
						, disjoined=true, disjoinedtable="iswl_kind", disjoinedfield="transferable_identity",disjoinedsource="INCIDENT_KIND_ID")
	private String incidentKindTransferableIdentity;

	@XmlTransferField(name = "IncidentKindId", sqlname="INCIDENT_KIND_ID", type="LONG"
						,derived=true, derivedfield="IncidentKindTransferableIdentity")
	private Long incidentKindId;

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
	 * @return the incidentKindId
	 */
	public Long getIncidentKindId() {
		return incidentKindId;
	}

	/**
	 * @param incidentKindId the incidentKindId to set
	 */
	public void setIncidentKindId(Long incidentKindId) {
		this.incidentKindId = incidentKindId;
	}

	/**
	 * @return the incidentKindTransferableIdentity
	 */
	public String getIncidentKindTransferableIdentity() {
		return incidentKindTransferableIdentity;
	}

	/**
	 * @param incidentKindTransferableIdentity the incidentKindTransferableIdentity to set
	 */
	public void setIncidentKindTransferableIdentity(
			String incidentKindTransferableIdentity) {
		this.incidentKindTransferableIdentity = incidentKindTransferableIdentity;
	}


}
