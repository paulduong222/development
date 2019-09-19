package gov.nwcg.isuite.core.domain;

import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public interface IapBranchRscAssign extends Persistable {
	
	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

	/**
	 * @param iapBranch the iapBranch to set
	 */
	public void setIapBranch(IapBranch iapBranch);

	/**
	 * @return the iapBranch
	 */
	public IapBranch getIapBranch();

	/**
	 * @param iapBranchId the iapBranchId to set
	 */
	public void setIapBranchId(Long iapBranchId);

	/**
	 * @return the iapBranchId
	 */
	public Long getIapBranchId();

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName);

	/**
	 * @return the resourceName
	 */
	public String getResourceName();

	/**
	 * @param leaderName the leaderName to set
	 */
	public void setLeaderName(String leaderName);

	/**
	 * @return the leaderName
	 */
	public String getLeaderName();

	/**
	 * @param nbrOfPersonnel the nbrOfPersonnel to set
	 */
	public void setNbrOfPersonnel(Integer nbrOfPersonnel);

	/**
	 * @return the nbrOfPersonnel
	 */
	public Integer getNbrOfPersonnel();

	/**
	 * @param transportation the transportation to set
	 */
	public void setTransportation(StringBooleanEnum transportation);

	/**
	 * @return the transportation
	 */
	public StringBooleanEnum getTransportation();

	/**
	 * @param dropOffPoint the dropOffPoint to set
	 */
	public void setDropOffPoint(String dropOffPoint);

	/**
	 * @return the dropOffPoint
	 */
	public String getDropOffPoint();

	/**
	 * @param dropOffTime the dropOffTime to set
	 */
	public void setDropOffTime(String dropOffTime);

	/**
	 * @return the dropOffTime
	 */
	public String getDropOffTime();

	/**
	 * @param pickUpPoint the pickUpPoint to set
	 */
	public void setPickUpPoint(String pickUpPoint);

	/**
	 * @return the pickUpPoint
	 */
	public String getPickUpPoint();

	/**
	 * @param pickUpTime the pickUpTime to set
	 */
	public void setPickUpTime(String pickUpTime);

	/**
	 * @return the pickUpTime
	 */
	public String getPickUpTime();

	/**
	 * @param contactInfo the contactInfo to set
	 */
	public void setContactInfo(String contactInfo);

	/**
	 * @return the contactInfo
	 */
	public String getContactInfo();

	/**
	 * @param additionalInfo the additionalInfo to set
	 */
	public void setAdditionalInfo(String additionalInfo);

	/**
	 * @return the additionalInfo
	 */
	public String getAdditionalInfo();
	
	/**
	 * @return the lastDayToWorkDate
	 */
	public Date getLastDayToWorkDate();

	/**
	 * @param lastDayToWorkDate the lastDayToWorkDate to set
	 */
	public void setLastDayToWorkDate(Date lastDayToWorkDate);
	
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

	/**
	 * @return the resourceId
	 */
	public Long getResourceId();

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Long resourceId);
	
}
