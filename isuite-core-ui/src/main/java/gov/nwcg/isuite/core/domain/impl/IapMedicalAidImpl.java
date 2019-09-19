package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Address;
import gov.nwcg.isuite.core.domain.IapForm206;
import gov.nwcg.isuite.core.domain.IapMedicalAid;
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
 * IapMedicalAid entity.
 */
@Entity
@Table(name = "isw_iap_medical_aid")
@SequenceGenerator(name="SEQ_IAP_MEDICAL_AID", sequenceName="SEQ_IAP_MEDICAL_AID")
public class IapMedicalAidImpl extends PersistableImpl implements IapMedicalAid {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_IAP_MEDICAL_AID")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=IapForm206Impl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IAP_FORM_206_ID", nullable = false)
	private IapForm206 iapForm206;
	
	@Column(name = "IAP_FORM_206_ID", insertable = false, updatable = false, unique=false, nullable = false)
	private Long iapForm206Id;
	
	@Column(name = "TYPE", length = 50)
	private String type;
	
	@Column(name = "NAME", length = 200)
	private String name;
	
	@Column(name = "LOCATION", length = 200)
	private String location;
	
	@Column(name = "PHONE", length = 20)
	private String phone;
	
	@Column(name = "PARAMEDICS")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum paramedics;
	
	@Column(name = "SERVICE_LEVEL", length = 20)
	private String serviceLevel;

	@Column(name = "LIFE_SUPPORT")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum lifeSupport;

	@Column(name = "EMS_FREQUENCY", length = 100)
	private String emsFrequency;

	@Column(name = "AIR_TYPE", length = 75)
	private String airType;
	
	@Column(name = "CAPABILITY", length = 200)
	private String capability;

	@OneToOne(targetEntity=AddressImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name = "ADDRESS_ID", insertable = true, updatable = true, unique = false, nullable = false)
	private Address address;
	
    @Column(name = "POSITION_NUM", nullable = true)
	private Integer positionNum;

	@Column(name = "IS_BLANK_LINE", nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isBlankLine;
	
	/** 
	 * Default constructor 
	 */
	public IapMedicalAidImpl() {
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
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
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
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
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
	 * @param paramedics the paramedics to set
	 */
	public void setParamedics(StringBooleanEnum paramedics) {
		this.paramedics = paramedics;
	}

	/**
	 * @return the paramedics
	 */
	public StringBooleanEnum getParamedics() {
		return paramedics;
	}

	/**
	 * @param serviceLevel the serviceLevel to set
	 */
	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	/**
	 * @return the serviceLevel
	 */
	public String getServiceLevel() {
		return serviceLevel;
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
	 * @return the lifeSupport
	 */
	public StringBooleanEnum getLifeSupport() {
		return lifeSupport;
	}

	/**
	 * @param lifeSupport the lifeSupport to set
	 */
	public void setLifeSupport(StringBooleanEnum lifeSupport) {
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
