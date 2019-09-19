package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswContractor", table = "isw_contractor")
public class IswContractor {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_CONTRACTOR", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;

	@XmlTransferField(name = "Name", sqlname = "NAME", type = "STRING")
	private String name;

	@XmlTransferField(name = "Tin", sqlname = "TIN", type = "STRING")
	private String tin;

	@XmlTransferField(name = "Duns", sqlname = "DUNS", type = "STRING")
	private String duns;

	@XmlTransferField(name = "Phone", sqlname = "PHONE", type = "STRING")
	private String phone;

	@XmlTransferField(name = "Fax", sqlname = "FAX", type = "STRING")
	private String fax;

	@XmlTransferField(name = "Address", type = "COMPLEX", target = IswAddress.class
						, lookupname = "Id", sourcename = "AddressId")
	private IswAddress address;

	@XmlTransferField(name = "AddressId", sqlname = "ADDRESS_ID", type = "LONG"
						, derived = true, derivedfield = "Address")
	private Long addressId;

	@XmlTransferField(name = "DeletedDate", sqlname = "DELETED_DATE", type = "DATE")
	private Date deletedDate;

	@XmlTransferField(name = "Enabled", sqlname = "IS_ENABLED", type = "BOOLEAN")
	private Boolean enabled;

	// @ManyToMany(targetEntity = ResourceImpl.class, cascade = CascadeType.ALL,
	// fetch = FetchType.LAZY, mappedBy = "contractors")
	//private Collection<Resource> resources;

	public IswContractor() {

	}

	/**
	 * Returns the id.
	 * 
	 * @return the id to return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the name.
	 * 
	 * @return the name to return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the tin.
	 * 
	 * @return the tin to return
	 */
	public String getTin() {
		return tin;
	}

	/**
	 * Sets the tin.
	 * 
	 * @param tin
	 *            the tin to set
	 */
	public void setTin(String tin) {
		this.tin = tin;
	}

	/**
	 * Returns the duns.
	 * 
	 * @return the duns to return
	 */
	public String getDuns() {
		return duns;
	}

	/**
	 * Sets the duns.
	 * 
	 * @param duns
	 *            the duns to set
	 */
	public void setDuns(String duns) {
		this.duns = duns;
	}

	/**
	 * Returns the phone.
	 * 
	 * @return the phone to return
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the phone.
	 * 
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Returns the fax.
	 * 
	 * @return the fax to return
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * Sets the fax.
	 * 
	 * @param fax
	 *            the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * Returns the address.
	 * 
	 * @return the address to return
	 */
	public IswAddress getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 * 
	 * @param address
	 *            the address to set
	 */
	public void setAddress(IswAddress address) {
		this.address = address;
	}

	/**
	 * Returns the addressId.
	 * 
	 * @return the addressId to return
	 */
	public Long getAddressId() {
		return addressId;
	}

	/**
	 * Sets the addressId.
	 * 
	 * @param addressId
	 *            the addressId to set
	 */
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	/**
	 * Returns the deletedDate.
	 * 
	 * @return the deletedDate to return
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/**
	 * Sets the deletedDate.
	 * 
	 * @param deletedDate
	 *            the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	/**
	 * @return
	 */
	public Boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
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
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}

}
