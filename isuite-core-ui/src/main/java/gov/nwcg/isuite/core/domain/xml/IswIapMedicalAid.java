package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswIapMedicalAid", table = "isw_iap_medical_aid")
public class IswIapMedicalAid {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_MEDICAL_AID", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;

	@XmlTransferField(name = "IapForm206TransferableIdentity", alias="fti2", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IapForm206Id"
		, disjoined=true, disjoinedtable="isw_iap_form_206", disjoinedfield="transferable_identity",disjoinedsource="iap_form_206_id")
	private String iapForm206TransferableIdentity;

	@XmlTransferField(name = "IapForm206Id", sqlname="IAP_FORM_206_ID", type="LONG"
		,derived=true,derivedfield="IapForm206TransferableIdentity")
	private Long iapForm206Id;
	
	@XmlTransferField(name = "Type", sqlname="TYPE", type="STRING")
	private String type;
	
	@XmlTransferField(name = "Name", sqlname="NAME", type="STRING")
	private String name;
	
	@XmlTransferField(name = "Location", sqlname="LOCATION", type="STRING")
	private String location;
	
	@XmlTransferField(name = "Phone", sqlname="PHONE", type="STRING")
	private String phone;
	
	@XmlTransferField(name = "Paramedics", sqlname="PARAMEDICS", type="STRING")
	private String paramedics;
	
	@XmlTransferField(name = "ServiceLevel", sqlname="SERVICE_LEVEL", type="STRING")
	private String serviceLevel;

	@XmlTransferField(name = "LifeSupport", sqlname="LIFE_SUPPORT", type="STRING")
	private String lifeSupport;

	@XmlTransferField(name = "EmsFrequency", sqlname="EMS_FREQUENCY", type="STRING")
	private String emsFrequency;

	@XmlTransferField(name = "AirType", sqlname="AIR_TYPE", type="STRING")
	private String airType;
	
	@XmlTransferField(name = "Capability", sqlname="CAPABILITY", type="STRING")
	private String capability;

	@XmlTransferField(name = "Address", type="COMPLEX", target=IswAddress.class
			, lookupname="Id", sourcename="AddressId")
	private IswAddress address;

	@XmlTransferField(name = "AddressId", sqlname="ADDRESS_ID", type="LONG"
			,derived=true, derivedfield="Address")
	private Long addressId;
	
	@XmlTransferField(name = "PositionNum", sqlname="POSITION_NUM", type="INTEGER")
	private Integer positionNum;

	@XmlTransferField(name = "IsBlankLine", sqlname="IS_BLANK_LINE", type="STRING")
	private String isBlankLine;

	/**
	 * Default constructor.
	 * 
	 */
	public IswIapMedicalAid() {
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
	 * @return the iapForm206TransferableIdentity
	 */
	public String getIapForm206TransferableIdentity() {
		return iapForm206TransferableIdentity;
	}

	/**
	 * @param iapForm206TransferableIdentity the iapForm206TransferableIdentity to set
	 */
	public void setIapForm206TransferableIdentity(
			String iapForm206TransferableIdentity) {
		this.iapForm206TransferableIdentity = iapForm206TransferableIdentity;
	}

	/**
	 * @return the iapForm206Id
	 */
	public Long getIapForm206Id() {
		return iapForm206Id;
	}

	/**
	 * @param iapForm206Id the iapForm206Id to set
	 */
	public void setIapForm206Id(Long iapForm206Id) {
		this.iapForm206Id = iapForm206Id;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the paramedics
	 */
	public String getParamedics() {
		return paramedics;
	}

	/**
	 * @param paramedics the paramedics to set
	 */
	public void setParamedics(String paramedics) {
		this.paramedics = paramedics;
	}

	/**
	 * @return the serviceLevel
	 */
	public String getServiceLevel() {
		return serviceLevel;
	}

	/**
	 * @param serviceLevel the serviceLevel to set
	 */
	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	/**
	 * @return the lifeSupport
	 */
	public String getLifeSupport() {
		return lifeSupport;
	}

	/**
	 * @param lifeSupport the lifeSupport to set
	 */
	public void setLifeSupport(String lifeSupport) {
		this.lifeSupport = lifeSupport;
	}

	/**
	 * @return the emsFrequency
	 */
	public String getEmsFrequency() {
		return emsFrequency;
	}

	/**
	 * @param emsFrequency the emsFrequency to set
	 */
	public void setEmsFrequency(String emsFrequency) {
		this.emsFrequency = emsFrequency;
	}

	/**
	 * @return the airType
	 */
	public String getAirType() {
		return airType;
	}

	/**
	 * @param airType the airType to set
	 */
	public void setAirType(String airType) {
		this.airType = airType;
	}

	/**
	 * @return the capability
	 */
	public String getCapability() {
		return capability;
	}

	/**
	 * @param capability the capability to set
	 */
	public void setCapability(String capability) {
		this.capability = capability;
	}

	/**
	 * @return the address
	 */
	public IswAddress getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(IswAddress address) {
		this.address = address;
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
	 * @return the positionNum
	 */
	public Integer getPositionNum() {
		return positionNum;
	}

	/**
	 * @param positionNum the positionNum to set
	 */
	public void setPositionNum(Integer positionNum) {
		this.positionNum = positionNum;
	}

	/**
	 * @return the isBlankLine
	 */
	public String getIsBlankLine() {
		return isBlankLine;
	}

	/**
	 * @param isBlankLine the isBlankLine to set
	 */
	public void setIsBlankLine(String isBlankLine) {
		this.isBlankLine = isBlankLine;
	}


}
