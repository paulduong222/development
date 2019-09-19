package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswlCountrySubdivision", table="iswl_country_subdivision")
public class IswlCountrySubdivision {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "Name", sqlname = "CS_NAME", type="STRING")
	private String name;

	@XmlTransferField(name = "Abbreviation", sqlname = "CS_ABBREVIATION", type="STRING")
	private String abbreviation;

	@XmlTransferField(name = "Standard", sqlname = "CS_STANDARD", type="BOOLEAN")
	private Boolean standard;
	
	@XmlTransferField(name = "CountryTransferableIdentity", alias="countryti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="CountryCodeId"
						, disjoined=true, disjoinedtable="iswl_country", disjoinedfield="transferable_identity",disjoinedsource="COUNTRY_ID")
	private String countryTransferableIdentity;

	@XmlTransferField(name = "CountryCodeId", sqlname = "COUNTRY_ID", type="LONG"
			,derived=true,derivedfield="CountryTransferableIdentity")
	private Long countryCodeId;


	/**
	 * Default Constructor
	 */
	public IswlCountrySubdivision() {
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the abbreviation
	 */
	public String getAbbreviation() {
		return abbreviation;
	}


	/**
	 * @param abbreviation the abbreviation to set
	 */
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
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
	 * @return the countryTransferableIdentity
	 */
	public String getCountryTransferableIdentity() {
		return countryTransferableIdentity;
	}


	/**
	 * @param countryTransferableIdentity the countryTransferableIdentity to set
	 */
	public void setCountryTransferableIdentity(String countryTransferableIdentity) {
		this.countryTransferableIdentity = countryTransferableIdentity;
	}


	/**
	 * @return the countryCodeId
	 */
	public Long getCountryCodeId() {
		return countryCodeId;
	}


	/**
	 * @param countryCodeId the countryCodeId to set
	 */
	public void setCountryCodeId(Long countryCodeId) {
		this.countryCodeId = countryCodeId;
	}




}
