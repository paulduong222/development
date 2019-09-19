package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public interface IapMedicalAid extends Persistable {
	
	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

	/**
	 * @param type the type to set
	 */
	public void setType(String type);

	/**
	 * @return the type
	 */
	public String getType();

	/**
	 * @param name the name to set
	 */
	public void setName(String name);

	/**
	 * @return the name
	 */
	public String getName();

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location);

	/**
	 * @return the location
	 */
	public String getLocation();

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone);

	/**
	 * @return the phone
	 */
	public String getPhone();

	/**
	 * @param paramedics the paramedics to set
	 */
	public void setParamedics(StringBooleanEnum paramedics);

	/**
	 * @return the paramedics
	 */
	public StringBooleanEnum getParamedics();

	/**
	 * @param serviceLevel the serviceLevel to set
	 */
	public void setServiceLevel(String serviceLevel);

	/**
	 * @return the serviceLevel
	 */
	public String getServiceLevel();

	/**
	 * @return the iapForm206
	 */
	public IapForm206 getIapForm206();

	/**
	 * @param iapForm206 the iapForm206 to set
	 */
	public void setIapForm206(IapForm206 iapForm206);

	/**
	 * @return the iapForm206Id
	 */
	public Long getIapForm206Id();

	/**
	 * @param iapForm206Id the iapForm206Id to set
	 */
	public void setIapForm206Id(Long iapForm206Id);

	/**
	 * @return the lifeSupport
	 */
	public StringBooleanEnum getLifeSupport();

	/**
	 * @param lifeSupport the lifeSupport to set
	 */
	public void setLifeSupport(StringBooleanEnum lifeSupport);

	/**
	 * @return the emsFrequency
	 */
	public String getEmsFrequency();

	/**
	 * @param emsFrequency the emsFrequency to set
	 */
	public void setEmsFrequency(String emsFrequency);

	/**
	 * @return the airType
	 */
	public String getAirType();

	/**
	 * @param airType the airType to set
	 */
	public void setAirType(String airType);

	/**
	 * @return the capability
	 */
	public String getCapability();

	/**
	 * @param capability the capability to set
	 */
	public void setCapability(String capability);
	
	/**
	 * @return the address
	 */
	public Address getAddress();

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address);

	/**
	 * @return the positionNum
	 */
	public Integer getPositionNum();

	/**
	 * @param positionNum the positionNum to set
	 */
	public void setPositionNum(Integer positionNum);

	/**
	 * @return the isBlankLine
	 */
	public StringBooleanEnum getIsBlankLine();

	/**
	 * @param isBlankLine the isBlankLine to set
	 */
	public void setIsBlankLine(StringBooleanEnum isBlankLine);
	
}
