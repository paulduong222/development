package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswlJetPort", table = "iswl_jet_port")
public class IswlJetPort {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_JETPORT", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "Code", sqlname="CODE", type="STRING")
	private String code;

	@XmlTransferField(name = "Description", sqlname="DESCRIPTION", type="STRING")
	private String description;

	@XmlTransferField(name = "Standard", sqlname="IS_STANDARD", type="BOOLEAN")
	private Boolean standard;

	@XmlTransferField(name = "CountrySubdivisionTransferableIdentity", alias="csdti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="CountrySubdivisionId"
						, disjoined=true, disjoinedtable="iswl_country_subdivision", disjoinedfield="transferable_identity",disjoinedsource="COUNTRY_SUBDIVISION_ID")
	private String countrySubdivisionTransferableIdentity;

	@XmlTransferField(name = "CountrySubdivisionId", sqlname="COUNTRY_SUBDIVISION_ID", type="LONG"
						,derived=true, derivedfield="CountrySubdivisionTransferableIdentity")
	private Long countrySubdivisionId;

	@XmlTransferField(name = "IncidentId", sqlname="INCIDENT_ID", type="LONG",updateable=false)
	private Long incidentId;

	@XmlTransferField(name = "IncidentGroupId", sqlname = "INCIDENT_GROUP_ID", type="LONG",updateable=false)
	private Long incidentGroupId;

	@XmlTransferField(name = "Active", sqlname = "IS_ACTIVE", type="STRING")
	private String active;

	/**
	 * Default Constructor
	 */
	public IswlJetPort() {
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * @return the standard
	 */
	public Boolean getStandard() {
		return standard;
	}

	/**
	 * @param standard the standard to set
	 */
	public void setStandard(Boolean standard) {
		this.standard = standard;
	}

	/**
	 * @return the countrySubdivisionTransferableIdentity
	 */
	public String getCountrySubdivisionTransferableIdentity() {
		return countrySubdivisionTransferableIdentity;
	}

	/**
	 * @param countrySubdivisionTransferableIdentity the countrySubdivisionTransferableIdentity to set
	 */
	public void setCountrySubdivisionTransferableIdentity(
			String countrySubdivisionTransferableIdentity) {
		this.countrySubdivisionTransferableIdentity = countrySubdivisionTransferableIdentity;
	}

	/**
	 * @return the countrySubdivisionId
	 */
	public Long getCountrySubdivisionId() {
		return countrySubdivisionId;
	}

	/**
	 * @param countrySubdivisionId the countrySubdivisionId to set
	 */
	public void setCountrySubdivisionId(Long countrySubdivisionId) {
		this.countrySubdivisionId = countrySubdivisionId;
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
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}


}
