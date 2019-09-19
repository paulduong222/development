package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.IapForm206;
import gov.nwcg.isuite.core.domain.IapRemoteCampLocations;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name = "SEQ_IAP_REMOTE_CAMP_LOC", sequenceName = "SEQ_IAP_REMOTE_CAMP_LOC")
@Table(name = "isw_iap_remote_camp_loc")
public class IapRemoteCampLocationsImpl extends PersistableImpl implements IapRemoteCampLocations {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_IAP_REMOTE_CAMP_LOC")
	private Long id = 0L;

	@ManyToOne(targetEntity=IapForm206Impl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IAP_FORM_206_ID", nullable = false)
	private IapForm206 iapForm206;
	
	@Column(name = "IAP_FORM_206_ID", insertable = false, updatable = false, unique=false, nullable = false)
	private Long iapForm206Id;
	
	@Column(name="NAME",length=200)
	private String name;
	
	@Column(name="LOCATION",length=200)
	private String location;

	@Column(name="POINT_OF_CONTACT",length=200)
	private String pointOfContact;

	@Column(name="EMS_RESPONDERS",length=200)
	private String emsResponders;

	@Column(name="CAPABILITY",length=200)
	private String capability;
	
	@Column(name="AMB_AIR_ETA",length=50)
	private String ambAirEta;

	@Column(name="AMB_GROUND_ETA",length=50)
	private String ambGroundEta;

	@Column(name="APPROVED_HELISPOT",length=200)
	private String approvedHelispot;

	@Column(name="LATITUDE",length=50)
	private String latitude;

	@Column(name="LONGITUDE",length=50)
	private String longitude;

	@Column(name="EMERGENCY_CHANNEL",length=100)
	private String emergencyChannel;
	
	@Column(name="AVAIL_EQUIPMENT",length=200)
	private String availableEquipment;

    @Column(name = "POSITION_NUM", nullable = true)
	private Integer positionNum;

	@Column(name = "IS_BLANK_LINE", nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isBlankLine;
	
	public IapRemoteCampLocationsImpl() {
		super();
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
	 * @return the iapForm206
	 */
	public IapForm206 getIapForm206() {
		return iapForm206;
	}

	/**
	 * @param iapForm206 the iapForm206 to set
	 */
	public void setIapForm206(IapForm206 iapForm206) {
		this.iapForm206 = iapForm206;
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
	public StringBooleanEnum getIsBlankLine() {
		return isBlankLine;
	}

	/**
	 * @param isBlankLine the isBlankLine to set
	 */
	public void setIsBlankLine(StringBooleanEnum isBlankLine) {
		this.isBlankLine = isBlankLine;
	}


}
