package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@XmlTransferTable(name = "IswIapHospital", table = "isw_iap_hospital")
public class IswIapHospital {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_HOSPITAL", type="LONG")
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
	
	@XmlTransferField(name = "Name", sqlname="NAME", type="STRING")
	private String name;

	@XmlTransferField(name = "Address", type="COMPLEX", target=IswAddress.class
			, lookupname="Id", sourcename="AddressId")
	private IswAddress address;

	@XmlTransferField(name = "AddressId", sqlname="ADDRESS_ID", type="LONG"
			,derived=true, derivedfield="Address")
	private Long addressId;

	@XmlTransferField(name = "Latitude", sqlname="LATITUDE", type="STRING")
	private String latitude;

	@XmlTransferField(name = "Longitude", sqlname="LONGITUDE", type="STRING")
	private String longitude;

	@XmlTransferField(name = "Vhf", sqlname="VHF", type="STRING")
	private String vhf;

	@XmlTransferField(name = "AirTravelTime", sqlname="AIR_TRAVEL_TIME", type="STRING")
	private String airTravelTime;

	@XmlTransferField(name = "GroundTravelTime", sqlname="GROUND_TRAVEL_TIME", type="STRING")
	private String groundTravelTime;

	@XmlTransferField(name = "Phone", sqlname="PHONE", type="STRING")
	private String phone;

	@XmlTransferField(name = "LevelOfCare", sqlname="LEVEL_OF_CARE", type="STRING")
	private String levelOfCare;
	
	@XmlTransferField(name = "Trauma", sqlname="TRAUMA", type="STRING")
	private String trauma;

	@XmlTransferField(name = "Helipad", sqlname="HELIPAD", type="STRING")
	private String helipad;

	@XmlTransferField(name = "BurnCenter", sqlname="BURN_CENTER", type="STRING")
	private String burnCenter;

	@XmlTransferField(name = "PositionNum", sqlname="POSITION_NUM", type="INTEGER")
	private Integer positionNum;

	@XmlTransferField(name = "IsBlankLine", sqlname="IS_BLANK_LINE", type="STRING")
	private String isBlankLine;

	/**
	 * Default constructor.
	 * 
	 */
	public IswIapHospital() {
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
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the vhf
	 */
	public String getVhf() {
		return vhf;
	}

	/**
	 * @param vhf the vhf to set
	 */
	public void setVhf(String vhf) {
		this.vhf = vhf;
	}

	/**
	 * @return the airTravelTime
	 */
	public String getAirTravelTime() {
		return airTravelTime;
	}

	/**
	 * @param airTravelTime the airTravelTime to set
	 */
	public void setAirTravelTime(String airTravelTime) {
		this.airTravelTime = airTravelTime;
	}

	/**
	 * @return the groundTravelTime
	 */
	public String getGroundTravelTime() {
		return groundTravelTime;
	}

	/**
	 * @param groundTravelTime the groundTravelTime to set
	 */
	public void setGroundTravelTime(String groundTravelTime) {
		this.groundTravelTime = groundTravelTime;
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
	 * @return the levelOfCare
	 */
	public String getLevelOfCare() {
		return levelOfCare;
	}

	/**
	 * @param levelOfCare the levelOfCare to set
	 */
	public void setLevelOfCare(String levelOfCare) {
		this.levelOfCare = levelOfCare;
	}

	/**
	 * @return the trauma
	 */
	public String getTrauma() {
		return trauma;
	}

	/**
	 * @param trauma the trauma to set
	 */
	public void setTrauma(String trauma) {
		this.trauma = trauma;
	}

	/**
	 * @return the helipad
	 */
	public String getHelipad() {
		return helipad;
	}

	/**
	 * @param helipad the helipad to set
	 */
	public void setHelipad(String helipad) {
		this.helipad = helipad;
	}

	/**
	 * @return the burnCenter
	 */
	public String getBurnCenter() {
		return burnCenter;
	}

	/**
	 * @param burnCenter the burnCenter to set
	 */
	public void setBurnCenter(String burnCenter) {
		this.burnCenter = burnCenter;
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


}
