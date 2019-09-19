package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswIapRemoteCampLocation", table = "isw_iap_remote_camp_loc")
public class IswIapRemoteCampLocation {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_REMOTE_CAMP_LOC", type="LONG")
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
	
	@XmlTransferField(name = "Location", sqlname="LOCATION", type="STRING")
	private String location;

	@XmlTransferField(name = "PointOfContact", sqlname="POINT_OF_CONTACT", type="STRING")
	private String pointOfContact;

	@XmlTransferField(name = "EmsResponders", sqlname="EMS_RESPONDERS", type="STRING")
	private String emsResponders;

	@XmlTransferField(name = "Capability", sqlname="CAPABILITY", type="STRING")
	private String capability;
	
	@XmlTransferField(name = "AmbAirEta", sqlname="AMB_AIR_ETA", type="STRING")
	private String ambAirEta;

	@XmlTransferField(name = "AmbGroundEta", sqlname="AMB_GROUND_ETA", type="STRING")
	private String ambGroundEta;

	@XmlTransferField(name = "ApprovedHelispot", sqlname="APPROVED_HELISPOT", type="STRING")
	private String approvedHelispot;

	@XmlTransferField(name = "Latitude", sqlname="LATITUDE", type="STRING")
	private String latitude;

	@XmlTransferField(name = "Longitude", sqlname="LONGITUDE", type="STRING")
	private String longitude;

	@XmlTransferField(name = "EmergencyChannel", sqlname="EMERGENCY_CHANNEL", type="STRING")
	private String emergencyChannel;
	
	@XmlTransferField(name = "AvailableEquipment", sqlname="AVAIL_EQUIPMENT", type="STRING")
	private String availableEquipment;

	@XmlTransferField(name = "PositionNum", sqlname="POSITION_NUM", type="INTEGER")
	private Integer positionNum;

	@XmlTransferField(name = "IsBlankLine", sqlname="IS_BLANK_LINE", type="STRING")
	private String isBlankLine;

	/**
	 * Default constructor.
	 * 
	 */
	public IswIapRemoteCampLocation() {
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
	 * @return the pointOfContact
	 */
	public String getPointOfContact() {
		return pointOfContact;
	}

	/**
	 * @param pointOfContact the pointOfContact to set
	 */
	public void setPointOfContact(String pointOfContact) {
		this.pointOfContact = pointOfContact;
	}

	/**
	 * @return the emsResponders
	 */
	public String getEmsResponders() {
		return emsResponders;
	}

	/**
	 * @param emsResponders the emsResponders to set
	 */
	public void setEmsResponders(String emsResponders) {
		this.emsResponders = emsResponders;
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
	 * @return the ambAirEta
	 */
	public String getAmbAirEta() {
		return ambAirEta;
	}

	/**
	 * @param ambAirEta the ambAirEta to set
	 */
	public void setAmbAirEta(String ambAirEta) {
		this.ambAirEta = ambAirEta;
	}

	/**
	 * @return the ambGroundEta
	 */
	public String getAmbGroundEta() {
		return ambGroundEta;
	}

	/**
	 * @param ambGroundEta the ambGroundEta to set
	 */
	public void setAmbGroundEta(String ambGroundEta) {
		this.ambGroundEta = ambGroundEta;
	}

	/**
	 * @return the approvedHelispot
	 */
	public String getApprovedHelispot() {
		return approvedHelispot;
	}

	/**
	 * @param approvedHelispot the approvedHelispot to set
	 */
	public void setApprovedHelispot(String approvedHelispot) {
		this.approvedHelispot = approvedHelispot;
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
	 * @return the emergencyChannel
	 */
	public String getEmergencyChannel() {
		return emergencyChannel;
	}

	/**
	 * @param emergencyChannel the emergencyChannel to set
	 */
	public void setEmergencyChannel(String emergencyChannel) {
		this.emergencyChannel = emergencyChannel;
	}

	/**
	 * @return the availableEquipment
	 */
	public String getAvailableEquipment() {
		return availableEquipment;
	}

	/**
	 * @param availableEquipment the availableEquipment to set
	 */
	public void setAvailableEquipment(String availableEquipment) {
		this.availableEquipment = availableEquipment;
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
