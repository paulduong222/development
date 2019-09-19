
package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswIncidentPrefsOtherFields", table="isw_incident_prefs_otherfields")
public class IswIncidentPrefsOtherFields {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCIDENTPREFS_OTHERFIELDS", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "IncidentId", sqlname="INCIDENT_ID", type="LONG",updateable=false)
	private Long incidentId;

	@XmlTransferField(name = "IncidentGroupId", sqlname="INCIDENT_GROUP_ID", type="LONG",updateable=false)
	private Long incidentGroupId;

	@XmlTransferField(name = "Other1Label", sqlname="OTHER_1_LABEL", type="STRING")
	private String other1Label;

	@XmlTransferField(name = "Other2Label", sqlname="OTHER_2_LABEL", type="STRING")
	private String other2Label;

	@XmlTransferField(name = "Other3Label", sqlname="OTHER_3_LABEL", type="STRING")
	private String other3Label;

	public IswIncidentPrefsOtherFields() {
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
	 * @return the other1Label
	 */
	public String getOther1Label() {
		return other1Label;
	}

	/**
	 * @param other1Label the other1Label to set
	 */
	public void setOther1Label(String other1Label) {
		this.other1Label = other1Label;
	}

	/**
	 * @return the other2Label
	 */
	public String getOther2Label() {
		return other2Label;
	}

	/**
	 * @param other2Label the other2Label to set
	 */
	public void setOther2Label(String other2Label) {
		this.other2Label = other2Label;
	}

	/**
	 * @return the other3Label
	 */
	public String getOther3Label() {
		return other3Label;
	}

	/**
	 * @param other3Label the other3Label to set
	 */
	public void setOther3Label(String other3Label) {
		this.other3Label = other3Label;
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
