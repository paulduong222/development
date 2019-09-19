package gov.nwcg.isuite.core.domain;

import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public interface IapForm202 extends Persistable {
	/**
	 * @return the iapPlan
	 */
	public IapPlan getIapPlan();

	/**
	 * @param iapPlan the iapPlan to set
	 */
	public void setIapPlan(IapPlan iapPlan);

	/**
	 * @return the iapPlanId
	 */
	public Long getIapPlanId();

	/**
	 * @param iapPlanId the iapPlanId to set
	 */
	public void setIapPlanId(Long iapPlanId);

	/**
	 * @return the preparedBy
	 */
	public String getPreparedBy();

	/**
	 * @param preparedBy the preparedBy to set
	 */
	public void setPreparedBy(String preparedBy);

	/**
	 * @return the preparedByPosition
	 */
	public String getPreparedByPosition();

	/**
	 * @param preparedByPosition the preparedByPosition to set
	 */
	public void setPreparedByPosition(String preparedByPosition);

	/**
	 * @return the approvedBy
	 */
	public String getApprovedBy();

	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(String approvedBy);

	/**
	 * @return the approvedDate
	 */
	public Date getApprovedDate();

	/**
	 * @param approvedDate the approvedDate to set
	 */
	public void setApprovedDate(Date approvedDate);

	/**
	 * @return the objectives
	 */
	public String getObjectives();

	/**
	 * @param objectives the objectives to set
	 */
	public void setObjectives(String objectives);

	/**
	 * @return the commandEmphasis
	 */
	public String getCommandEmphasis();

	/**
	 * @param commandEmphasis the commandEmphasis to set
	 */
	public void setCommandEmphasis(String commandEmphasis);

	/**
	 * @return the generalSituationalAwareness
	 */
	public String getGeneralSituationalAwareness();

	/**
	 * @param generalSituationalAwareness the generalSituationalAwareness to set
	 */
	public void setGeneralSituationalAwareness(String generalSituationalAwareness);

	/**
	 * @return the siteSafetyPlanRqrd
	 */
	public StringBooleanEnum getSiteSafetyPlanRqrd();

	/**
	 * @param siteSafetyPlanRqrd the siteSafetyPlanRqrd to set
	 */
	public void setSiteSafetyPlanRqrd(StringBooleanEnum siteSafetyPlanRqrd);

	/**
	 * @return the siteSafetyPlanLoc
	 */
	public String getSiteSafetyPlanLoc();

	/**
	 * @param siteSafetyPlanLoc the siteSafetyPlanLoc to set
	 */
	public void setSiteSafetyPlanLoc(String siteSafetyPlanLoc);

	/**
	 * @return the isForm202Attached
	 */
	public StringBooleanEnum getIsForm202Attached();

	/**
	 * @param isForm202Attached the isForm202Attached to set
	 */
	public void setIsForm202Attached(StringBooleanEnum isForm202Attached);

	/**
	 * @return the isForm203Attached
	 */
	public StringBooleanEnum getIsForm203Attached();

	/**
	 * @param isForm203Attached the isForm203Attached to set
	 */
	public void setIsForm203Attached(StringBooleanEnum isForm203Attached);

	/**
	 * @return the isForm204Attached
	 */
	public StringBooleanEnum getIsForm204Attached();

	/**
	 * @param isForm204Attached the isForm204Attached to set
	 */
	public void setIsForm204Attached(StringBooleanEnum isForm204Attached);

	/**
	 * @return the isForm205Attached
	 */
	public StringBooleanEnum getIsForm205Attached();

	/**
	 * @param isForm205Attached the isForm205Attached to set
	 */
	public void setIsForm205Attached(StringBooleanEnum isForm205Attached);

	/**
	 * @return the isForm205aAttached
	 */
	public StringBooleanEnum getIsForm205aAttached();

	/**
	 * @param isForm205aAttached the isForm205aAttached to set
	 */
	public void setIsForm205aAttached(StringBooleanEnum isForm205aAttached);

	/**
	 * @return the isForm206Attached
	 */
	public StringBooleanEnum getIsForm206Attached();

	/**
	 * @param isForm206Attached the isForm206Attached to set
	 */
	public void setIsForm206Attached(StringBooleanEnum isForm206Attached);

	/**
	 * @return the isForm207Attached
	 */
	public StringBooleanEnum getIsForm207Attached();

	/**
	 * @param isForm207Attached the isForm207Attached to set
	 */
	public void setIsForm207Attached(StringBooleanEnum isForm207Attached);

	/**
	 * @return the isForm208Attached
	 */
	public StringBooleanEnum getIsForm208Attached();

	/**
	 * @param isForm208Attached the isForm208Attached to set
	 */
	public void setIsForm208Attached(StringBooleanEnum isForm208Attached);

	/**
	 * @return the isIncidentMapAttached
	 */
	public StringBooleanEnum getIsIncidentMapAttached();

	/**
	 * @param isIncidentMapAttached the isIncidentMapAttached to set
	 */
	public void setIsIncidentMapAttached(StringBooleanEnum isIncidentMapAttached);

	/**
	 * @return the isWeatherForecastAttached
	 */
	public StringBooleanEnum getIsWeatherForecastAttached();

	/**
	 * @param isWeatherForecastAttached the isWeatherForecastAttached to set
	 */
	public void setIsWeatherForecastAttached(StringBooleanEnum isWeatherForecastAttached);

	/**
	 * @return the isOtherFormAttached1
	 */
	public StringBooleanEnum getIsOtherFormAttached1();

	/**
	 * @param isOtherFormAttached1 the isOtherFormAttached1 to set
	 */
	public void setIsOtherFormAttached1(StringBooleanEnum isOtherFormAttached1);

	/**
	 * @return the isOtherFormAttached2
	 */
	public StringBooleanEnum getIsOtherFormAttached2();

	/**
	 * @param isOtherFormAttached2 the isOtherFormAttached2 to set
	 */
	public void setIsOtherFormAttached2(StringBooleanEnum isOtherFormAttached2);

	/**
	 * @return the isOtherFormAttached3
	 */
	public StringBooleanEnum getIsOtherFormAttached3();

	/**
	 * @param isOtherFormAttached3 the isOtherFormAttached3 to set
	 */
	public void setIsOtherFormAttached3(StringBooleanEnum isOtherFormAttached3);

	/**
	 * @return the isOtherFormAttached4
	 */
	public StringBooleanEnum getIsOtherFormAttached4();

	/**
	 * @param isOtherFormAttached4 the isOtherFormAttached4 to set
	 */
	public void setIsOtherFormAttached4(StringBooleanEnum isOtherFormAttached4);

	/**
	 * @return the otherFormName1
	 */
	public String getOtherFormName1();

	/**
	 * @param otherFormName1 the otherFormName1 to set
	 */
	public void setOtherFormName1(String otherFormName1);

	/**
	 * @return the otherFormName2
	 */
	public String getOtherFormName2();

	/**
	 * @param otherFormName2 the otherFormName2 to set
	 */
	public void setOtherFormName2(String otherFormName2);

	/**
	 * @return the otherFormName3
	 */
	public String getOtherFormName3();

	/**
	 * @param otherFormName3 the otherFormName3 to set
	 */
	public void setOtherFormName3(String otherFormName3);

	/**
	 * @return the otherFormName4
	 */
	public String getOtherFormName4();

	/**
	 * @param otherFormName4 the otherFormName4 to set
	 */
	public void setOtherFormName4(String otherFormName4);

	/**
	 * @return the isFormLocked
	 */
	public StringBooleanEnum getIsFormLocked();

	/**
	 * @param isFormLocked the isFormLocked to set
	 */
	public void setIsFormLocked(StringBooleanEnum isFormLocked);

	/**
	 * @return the isForm220Attached
	 */
	public StringBooleanEnum getIsForm220Attached();

	/**
	 * @param isForm220Attached the isForm220Attached to set
	 */
	public void setIsForm220Attached(StringBooleanEnum isForm220Attached);
	
	
}