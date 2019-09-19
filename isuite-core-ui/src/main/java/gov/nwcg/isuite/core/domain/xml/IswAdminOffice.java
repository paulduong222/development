package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswAdminOffice", table = "isw_admin_office")
public class IswAdminOffice {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_ADMIN_OFFICE", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "OfficeName", sqlname = "OFFICE_NAME", type="STRING")
	private String officeName;

	@XmlTransferField(name = "Phone", sqlname = "PHONE", type="STRING")
	private String phone;

	@XmlTransferField(name = "DeletedDate", sqlname = "DELETED_DATE", type = "DATE")
	private Date deletedDate;

	@XmlTransferField(name = "Standard", sqlname="IS_STANDARD", type="BOOLEAN")
	private Boolean standard;
	
	@XmlTransferField(name = "Address", type="COMPLEX", target=IswAddress.class
						, lookupname="Id", sourcename="AddressId")
	private IswAddress address;

	@XmlTransferField(name = "AddressId", sqlname="ADDRESS_ID", type="LONG"
						,derived=true, derivedfield="Address")
	private Long addressId;
	
	/**
	 * Default constructor.
	 */
	public IswAdminOffice() {
	}

	/**
	 * @return
	 */
	public String getOfficeName() {
		return this.officeName;
	}

	/**
	 * @param officeName
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	/**
	 * @return
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/**
	 * @param deletedDate
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
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
	 * @param standard
	 */
	public void setStandard(Boolean standard) {
		this.standard = standard;
	}

	/**
	 * @return
	 */
	public Boolean isStandard() {
		return standard;
	}

	/**
	 * @return the iswAddress
	 */
	public IswAddress getAddress() {
		return address;
	}

	/**
	 * @param iswAddress the iswAddress to set
	 */
	public void setAddress(IswAddress iswAddress) {
		this.address = iswAddress;
	}

	/**
	 * @return the addressId
	 */
	public Long getAddressId() {
		return addressId;
	}

	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	/**
	 * @return the standard
	 */
	public Boolean getStandard() {
		return standard;
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
