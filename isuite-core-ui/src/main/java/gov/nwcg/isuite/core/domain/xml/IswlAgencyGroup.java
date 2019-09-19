package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswlAgencyGroup", table = "iswl_agency_group")
public class IswlAgencyGroup {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_AGENCY_GROUP", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "Code", sqlname = "CODE", type="STRING")
	private String code;

	@XmlTransferField(name = "Description", sqlname = "DESCRIPTION", type="STRING")
	private String description;

	@XmlTransferField(name = "Standard", sqlname = "IS_STANDARD", type="BOOLEAN")
	private Boolean standard;

	@XmlTransferField(name = "IncidentId", sqlname = "INCIDENT_ID", type="LONG",updateable=false)
	private Long incidentId;

	@XmlTransferField(name = "IncidentGroupId", sqlname = "INCIDENT_GROUP_ID", type="LONG",updateable=false)
	private Long incidentGroupId;

	@XmlTransferField(name = "Active", sqlname = "IS_ACTIVE", type="STRING",updateable=false)
	private String active;

	public IswlAgencyGroup() {
	}

	/**
	 * @return
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @return
	 */
	public Boolean getStandard() {
		return this.standard;
	}

	/**
	 * @param isStandard
	 */
	public void setStandard(Boolean isStandard) {
		this.standard = isStandard;
	}

	/**
	 * @param incidentId
	 *            the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentGroupId
	 *            the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
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

}
