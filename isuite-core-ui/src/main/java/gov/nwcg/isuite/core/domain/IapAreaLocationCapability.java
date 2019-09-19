package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public interface IapAreaLocationCapability extends Persistable {
	
	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

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
	 * @return the divisionName
	 */
	public String getDivisionName();

	/**
	 * @param divisionName the divisionName to set
	 */
	public void setDivisionName(String divisionName);

	/**
	 * @return the branchName
	 */
	public String getBranchName();

	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName);

	/**
	 * @return the groupName
	 */
	public String getGroupName();

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName);

	/**
	 * @return the emsResponders
	 */
	public String getEmsResponders();

	/**
	 * @param emsResponders the emsResponders to set
	 */
	public void setEmsResponders(String emsResponders);

	/**
	 * @return the capability
	 */
	public String getCapability();

	/**
	 * @param capability the capability to set
	 */
	public void setCapability(String capability);

	/**
	 * @return the ambAirEta
	 */
	public String getAmbAirEta();

	/**
	 * @param ambAirEta the ambAirEta to set
	 */
	public void setAmbAirEta(String ambAirEta);

	/**
	 * @return the ambGroundEta
	 */
	public String getAmbGroundEta();

	/**
	 * @param ambGroundEta the ambGroundEta to set
	 */
	public void setAmbGroundEta(String ambGroundEta);

	/**
	 * @return the approvedHelispot
	 */
	public String getApprovedHelispot();

	/**
	 * @param approvedHelispot the approvedHelispot to set
	 */
	public void setApprovedHelispot(String approvedHelispot);

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
	 * @return the emergencyChannel
	 */
	public String getEmergencyChannel();

	/**
	 * @param emergencyChannel the emergencyChannel to set
	 */
	public void setEmergencyChannel(String emergencyChannel);

	/**
	 * @return the availableEquipment
	 */
	public String getAvailableEquipment();

	/**
	 * @param availableEquipment the availableEquipment to set
	 */
	public void setAvailableEquipment(String availableEquipment);

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
