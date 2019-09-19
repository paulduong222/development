package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswAddress", table = "isw_address")
public class IswAddress {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_ADDRESS", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "AddressLine1", sqlname = "ADDRESS_LINE_1", type = "STRING")
	private String addressLine1;

	@XmlTransferField(name = "AddressLine2", sqlname = "ADDRESS_LINE_2", type = "STRING")
	private String addressLine2;

	@XmlTransferField(name = "City", sqlname = "CITY", type = "STRING")
	private String city;

	@XmlTransferField(name = "PostalCode", sqlname = "POSTAL_CODE", type = "STRING")
	private String postalCode;

	@XmlTransferField(name = "CountrySubdivision", type="COMPLEX", target=IswlCountrySubdivision.class
						, lookupname="Id", sourcename="CountrySubdivisionId")
	private IswlCountrySubdivision countrySubdivision;

	@XmlTransferField(name = "CountrySubdivisionId", sqlname="COUNTRY_SUBDIVISION_ID", type="LONG"
						,derived=true, derivedfield="CountrySubdivision")
	private Long countrySubdivisionId;

	/**
	 * Default constructor.
	 */
	public IswAddress() {
	}

	/**
	 * @return
	 */
	public String getAddressLine1() {
		return this.addressLine1;
	}

	/**
	 * @param addressLine1
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	/**
	 * @return
	 */
	public String getAddressLine2() {
		return this.addressLine2;
	}

	/**
	 * @param addressLine2
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	/**
	 * @return
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
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
	 * @param countrySubdivisionId the countrySubdivisionId to set
	 */
	public void setCountrySubdivisionId(Long countrySubdivisionId) {
		this.countrySubdivisionId = countrySubdivisionId;
	}

	/**
	 * @return the countrySubdivisionId
	 */
	public Long getCountrySubdivisionId() {
		return countrySubdivisionId;
	}

	/**
	 * @return the iswlCountrySubdivision
	 */
	public IswlCountrySubdivision getCountrySubdivision() {
		return countrySubdivision;
	}

	/**
	 * @param iswlCountrySubdivision the iswlCountrySubdivision to set
	 */
	public void setCountrySubdivision(
			IswlCountrySubdivision iswlCountrySubdivision) {
		this.countrySubdivision = iswlCountrySubdivision;
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
