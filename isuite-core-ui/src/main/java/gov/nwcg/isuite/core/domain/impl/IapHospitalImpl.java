package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Address;
import gov.nwcg.isuite.core.domain.IapForm206;
import gov.nwcg.isuite.core.domain.IapHospital;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * IapHospital entity.
 */
@Entity
@Table(name = "isw_iap_hospital")
@SequenceGenerator(name="SEQ_IAP_HOSPITAL", sequenceName="SEQ_IAP_HOSPITAL")
public class IapHospitalImpl extends PersistableImpl implements IapHospital {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_IAP_HOSPITAL")
	private Long id = 0L;

	@ManyToOne(targetEntity=IapForm206Impl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IAP_FORM_206_ID", nullable = false)
	private IapForm206 iapForm206;

	@Column(name = "IAP_FORM_206_ID", insertable = false, updatable = false, unique=false, nullable = false)
	private Long iapForm206Id;

	@Column(name = "NAME", length = 200)
	private String name;

	@OneToOne(targetEntity=AddressImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name = "ADDRESS_ID", insertable = true, updatable = true, unique = false, nullable = false)
	private Address address;

	@Column(name = "LATITUDE", length = 50)
	private String latitude;

	@Column(name = "LONGITUDE", length = 50)
	private String longitude;

	@Column(name = "VHF", length = 50)
	private String vhf;

	@Column(name = "AIR_TRAVEL_TIME", length = 20)
	private String airTravelTime;

	@Column(name = "GROUND_TRAVEL_TIME", length = 20)
	private String groundTravelTime;

	@Column(name = "PHONE", length = 20)
	private String phone;

	@Column(name = "LEVEL_OF_CARE", length = 100)
	private String levelOfCare;
	
	@Column(name = "TRAUMA")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum trauma;

	@Column(name = "HELIPAD")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum helipad;

	@Column(name = "BURN_CENTER")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum burnCenter;

    @Column(name = "POSITION_NUM", nullable = true)
	private Integer positionNum;

	@Column(name = "IS_BLANK_LINE", nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isBlankLine;
	
	/** 
	 * Default constructor 
	 */
	public IapHospitalImpl() {
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param airTravelTime the airTravelTime to set
	 */
	public void setAirTravelTime(String airTravelTime) {
		this.airTravelTime = airTravelTime;
	}

	/**
	 * @return the airTravelTime
	 */
	public String getAirTravelTime() {
		return airTravelTime;
	}

	/**
	 * @param groundTravelTime the groundTravelTime to set
	 */
	public void setGroundTravelTime(String groundTravelTime) {
		this.groundTravelTime = groundTravelTime;
	}

	/**
	 * @return the groundTravelTime
	 */
	public String getGroundTravelTime() {
		return groundTravelTime;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param trauma the trauma to set
	 */
	public void setTrauma(StringBooleanEnum trauma) {
		this.trauma = trauma;
	}

	/**
	 * @return the trauma
	 */
	public StringBooleanEnum getTrauma() {
		return trauma;
	}

	/**
	 * @param helipad the helipad to set
	 */
	public void setHelipad(StringBooleanEnum helipad) {
		this.helipad = helipad;
	}

	/**
	 * @return the helipad
	 */
	public StringBooleanEnum getHelipad() {
		return helipad;
	}

	/**
	 * @param burnCenter the burnCenter to set
	 */
	public void setBurnCenter(StringBooleanEnum burnCenter) {
		this.burnCenter = burnCenter;
	}

	/**
	 * @return the burnCenter
	 */
	public StringBooleanEnum getBurnCenter() {
		return burnCenter;
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
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
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
