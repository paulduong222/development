package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswIapPositionItemCode", table="isw_iap_position_item_code")
public class IswIapPositionItemCode {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_POSITION_ITEM_CODE", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "IncidentId", sqlname="INCIDENT_ID", type="LONG",updateable=false)
	private Long incidentId;
	
	@XmlTransferField(name = "IncidentGroupId", sqlname="INCIDENT_GROUP_ID", type="LONG",updateable=false)
	private Long incidentGroupId;

	@XmlTransferField(name = "Position", sqlname="POSITION", type="STRING")
	private String position;

	@XmlTransferField(name = "KindTransferableIdentity", alias="kindti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="KindId"
						, disjoined=true, disjoinedtable="iswl_kind", disjoinedfield="transferable_identity",disjoinedsource="KIND_ID")
	private String kindTransferableIdentity;

	@XmlTransferField(name = "KindId", sqlname="KIND_ID", type="LONG"
				,derived=true, derivedfield="KindTransferableIdentity")
	private Long kindId;
	
	@XmlTransferField(name = "AgencyTransferableIdentity", alias="agti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="AgencyId"
						, disjoined=true, disjoinedtable="iswl_agency", disjoinedfield="transferable_identity",disjoinedsource="AGENCY_ID")
	private String agencyTransferableIdentity;

	@XmlTransferField(name = "AgencyId", sqlname="AGENCY_ID", type="LONG"
						,derived=true, derivedfield="AgencyTransferableIdentity")
	private Long agencyId;
	
	@XmlTransferField(name = "Form", sqlname="FORM", type="STRING")
	private String form;
	
	@XmlTransferField(name = "Section", sqlname="SECTION", type="STRING")
	private String section;

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
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the kindTransferableIdentity
	 */
	public String getKindTransferableIdentity() {
		return kindTransferableIdentity;
	}

	/**
	 * @param kindTransferableIdentity the kindTransferableIdentity to set
	 */
	public void setKindTransferableIdentity(String kindTransferableIdentity) {
		this.kindTransferableIdentity = kindTransferableIdentity;
	}

	/**
	 * @return the kindId
	 */
	public Long getKindId() {
		return kindId;
	}

	/**
	 * @param kindId the kindId to set
	 */
	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}

	/**
	 * @return the agencyTransferableIdentity
	 */
	public String getAgencyTransferableIdentity() {
		return agencyTransferableIdentity;
	}

	/**
	 * @param agencyTransferableIdentity the agencyTransferableIdentity to set
	 */
	public void setAgencyTransferableIdentity(String agencyTransferableIdentity) {
		this.agencyTransferableIdentity = agencyTransferableIdentity;
	}

	/**
	 * @return the agencyId
	 */
	public Long getAgencyId() {
		return agencyId;
	}

	/**
	 * @param agencyId the agencyId to set
	 */
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	/**
	 * @return the form
	 */
	public String getForm() {
		return form;
	}

	/**
	 * @param form the form to set
	 */
	public void setForm(String form) {
		this.form = form;
	}

	/**
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * @param section the section to set
	 */
	public void setSection(String section) {
		this.section = section;
	}

	
}
