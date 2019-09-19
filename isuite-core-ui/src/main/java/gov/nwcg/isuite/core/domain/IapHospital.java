package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;


public interface IapHospital extends Persistable {
	
	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

	/**
	 * @param name the name to set
	 */
	public void setName(String name);

	/**
	 * @return the name
	 */
	public String getName();

	/**
	 * @param airTravelTime the airTravelTime to set
	 */
	public void setAirTravelTime(String airTravelTime);

	/**
	 * @return the airTravelTime
	 */
	public String getAirTravelTime();

	/**
	 * @param groundTravelTime the groundTravelTime to set
	 */
	public void setGroundTravelTime(String groundTravelTime);

	/**
	 * @return the groundTravelTime
	 */
	public String getGroundTravelTime();

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone);

	/**
	 * @return the phone
	 */
	public String getPhone();

	/**
	 * @param trauma the trauma to set
	 */
	public void setTrauma(StringBooleanEnum trauma);

	/**
	 * @return the trauma
	 */
	public StringBooleanEnum getTrauma();

	/**
	 * @param helipad the helipad to set
	 */
	public void setHelipad(StringBooleanEnum helipad);

	/**
	 * @return the helipad
	 */
	public StringBooleanEnum getHelipad();

	/**
	 * @param burnCenter the burnCenter to set
	 */
	public void setBurnCenter(StringBooleanEnum burnCenter);

	/**
	 * @return the burnCenter
	 */
	public StringBooleanEnum getBurnCenter();

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
	 * @return the address
	 */
	public Address getAddress();

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address);

	/**
	 * @return the latitude
	 */
	public String getLatitude();

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude);

	/**
	 * @return the longitude
	 */
	public String getLongitude();

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude);

	/**
	 * @return the vhf
	 */
	public String getVhf();

	/**
	 * @param vhf the vhf to set
	 */
	public void setVhf(String vhf) ;

	/**
	 * @return the levelOfCare
	 */
	public String getLevelOfCare();
	/**
	 * @param levelOfCare the levelOfCare to set
	 */
	public void setLevelOfCare(String levelOfCare);

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
