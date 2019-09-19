package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IapForm202;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.core.domain.impl.IapForm202Impl;
import gov.nwcg.isuite.core.domain.impl.IapPlanImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class IapForm202Vo extends AbstractVo {
	private Long iapPlanId;
	private String preparedBy;
	private String preparedByPosition;
	private String approvedBy;
	private Date approvedDate;
	private String approvedDateTime;
	private String approvedDateString; // helper
	private String objectives;
	private String commandEmphasis;
	private String generalSituationalAwareness;
	private Boolean siteSafetyPlanRqrd;
	private String siteSafetyPlanLoc;
	private Boolean isForm202Attached;
	private Boolean isForm203Attached;
	private Boolean isForm204Attached;
	private Boolean isForm205Attached;
	private Boolean isForm205aAttached;
	private Boolean isForm206Attached;
	private Boolean isForm207Attached;
	private Boolean isForm208Attached;
	private Boolean isForm220Attached;
	private Boolean isIncidentMapAttached;
	private Boolean isWeatherForecastAttached;
	private Boolean isOtherFormAttached1;
	private Boolean isOtherFormAttached2;
	private Boolean isOtherFormAttached3;
	private Boolean isOtherFormAttached4;
	private String otherFormName1;
	private String otherFormName2;
	private String otherFormName3;
	private String otherFormName4;
	private Boolean isFormLocked=false;

	public IapForm202Vo(){
		
	}
	
	public static IapForm202Vo getInstance(IapForm202 entity, Boolean cascadable) throws Exception {
		IapForm202Vo vo = new IapForm202Vo();
		
		if(null==entity)
			throw new Exception("Unable to create IapForm202Vo from null IapForm202 entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setApprovedBy(entity.getApprovedBy());
			vo.setApprovedDate(entity.getApprovedDate());
			if(DateUtil.hasValue(entity.getApprovedDate())){
				vo.setApprovedDateString(DateUtil.toDateString(entity.getApprovedDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
				vo.setApprovedDateTime(DateUtil.toMilitaryTime(entity.getApprovedDate()));
			}
			vo.setCommandEmphasis(entity.getCommandEmphasis());
			vo.setGeneralSituationalAwareness(entity.getGeneralSituationalAwareness());
			vo.setIapPlanId(entity.getIapPlanId());
			vo.setIsForm202Attached(StringBooleanEnum.toBooleanValue(entity.getIsForm202Attached()));
			vo.setIsForm203Attached(StringBooleanEnum.toBooleanValue(entity.getIsForm203Attached()));
			vo.setIsForm204Attached(StringBooleanEnum.toBooleanValue(entity.getIsForm204Attached()));
			vo.setIsForm205Attached(StringBooleanEnum.toBooleanValue(entity.getIsForm205Attached()));
			vo.setIsForm205aAttached(StringBooleanEnum.toBooleanValue(entity.getIsForm205aAttached()));
			vo.setIsForm206Attached(StringBooleanEnum.toBooleanValue(entity.getIsForm206Attached()));
			vo.setIsForm207Attached(StringBooleanEnum.toBooleanValue(entity.getIsForm207Attached()));
			vo.setIsForm208Attached(StringBooleanEnum.toBooleanValue(entity.getIsForm208Attached()));
			vo.setIsForm220Attached(StringBooleanEnum.toBooleanValue(entity.getIsForm220Attached()));
			vo.setIsFormLocked(StringBooleanEnum.toBooleanValue(entity.getIsFormLocked()));
			vo.setIsIncidentMapAttached(StringBooleanEnum.toBooleanValue(entity.getIsIncidentMapAttached()));
			vo.setIsOtherFormAttached1(StringBooleanEnum.toBooleanValue(entity.getIsOtherFormAttached1()));
			vo.setIsOtherFormAttached2(StringBooleanEnum.toBooleanValue(entity.getIsOtherFormAttached2()));
			vo.setIsOtherFormAttached3(StringBooleanEnum.toBooleanValue(entity.getIsOtherFormAttached3()));
			vo.setIsOtherFormAttached4(StringBooleanEnum.toBooleanValue(entity.getIsOtherFormAttached4()));
			vo.setIsWeatherForecastAttached(StringBooleanEnum.toBooleanValue(entity.getIsWeatherForecastAttached()));
			vo.setObjectives(entity.getObjectives());
			vo.setOtherFormName1(entity.getOtherFormName1());
			vo.setOtherFormName2(entity.getOtherFormName2());
			vo.setOtherFormName3(entity.getOtherFormName3());
			vo.setOtherFormName4(entity.getOtherFormName4());
			vo.setPreparedBy(entity.getPreparedBy());
			vo.setPreparedByPosition(entity.getPreparedByPosition());
			vo.setSiteSafetyPlanLoc(entity.getSiteSafetyPlanLoc());
			vo.setSiteSafetyPlanRqrd(StringBooleanEnum.toBooleanValue(entity.getSiteSafetyPlanRqrd()));
			
		}
		
		return vo;
	}
	
	public static IapForm202 toEntity(IapForm202 entity, IapForm202Vo vo, Boolean cascadable,Persistable...persistables  ) throws Exception {
		if(null == entity) entity = new IapForm202Impl();
		
		entity.setId(vo.getId());
		
		IapPlan iapPlan =(IapPlan)AbstractVo.getPersistableObject(persistables, IapPlanImpl.class);
		if(null != iapPlan)
			entity.setIapPlan(iapPlan);
		else if(LongUtility.hasValue(vo.getIapPlanId())){
			iapPlan = new IapPlanImpl();
			iapPlan.setId(vo.getIapPlanId());
			entity.setIapPlan(iapPlan);
		}
		
		if(cascadable){
			entity.setApprovedBy(StringUtility.toUpper(vo.getApprovedBy()));
			if(StringUtility.hasValue(vo.getApprovedDateString()))
				entity.setApprovedDate(DateUtil.toDate(vo.getApprovedDateString(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
			else
				entity.setApprovedDate(null);
			
			if(StringUtility.hasValue(vo.getApprovedDateTime()) && DateUtil.hasValue(entity.getApprovedDate())){
				Date datetime=DateUtil.addMilitaryTimeToDate(entity.getApprovedDate(), vo.getApprovedDateTime());
				entity.setApprovedDate(datetime);
			}
				
			entity.setCommandEmphasis(vo.getCommandEmphasis());
			entity.setGeneralSituationalAwareness(vo.getGeneralSituationalAwareness());
			entity.setIsForm202Attached(StringBooleanEnum.toEnumValue(vo.getIsForm202Attached()));
			entity.setIsForm203Attached(StringBooleanEnum.toEnumValue(vo.getIsForm203Attached()));
			entity.setIsForm204Attached(StringBooleanEnum.toEnumValue(vo.getIsForm204Attached()));
			entity.setIsForm205Attached(StringBooleanEnum.toEnumValue(vo.getIsForm205Attached()));
			entity.setIsForm205aAttached(StringBooleanEnum.toEnumValue(vo.getIsForm205aAttached()));
			entity.setIsForm206Attached(StringBooleanEnum.toEnumValue(vo.getIsForm206Attached()));
			entity.setIsForm207Attached(StringBooleanEnum.toEnumValue(vo.getIsForm207Attached()));
			entity.setIsForm208Attached(StringBooleanEnum.toEnumValue(vo.getIsForm208Attached()));
			entity.setIsForm220Attached(StringBooleanEnum.toEnumValue(vo.getIsForm220Attached()));
			entity.setIsFormLocked(StringBooleanEnum.toEnumValue(vo.getIsFormLocked()));
			entity.setIsIncidentMapAttached(StringBooleanEnum.toEnumValue(vo.getIsIncidentMapAttached()));
			entity.setIsOtherFormAttached1(StringBooleanEnum.toEnumValue(vo.getIsOtherFormAttached1()));
			entity.setIsOtherFormAttached2(StringBooleanEnum.toEnumValue(vo.getIsOtherFormAttached2()));
			entity.setIsOtherFormAttached3(StringBooleanEnum.toEnumValue(vo.getIsOtherFormAttached3()));
			entity.setIsOtherFormAttached4(StringBooleanEnum.toEnumValue(vo.getIsOtherFormAttached4()));
			entity.setIsWeatherForecastAttached(StringBooleanEnum.toEnumValue(vo.getIsWeatherForecastAttached()));
			entity.setObjectives(vo.getObjectives());
			entity.setOtherFormName1(StringUtility.hasValue(vo.getOtherFormName1()) ? vo.getOtherFormName1().toUpperCase() : "");
			entity.setOtherFormName2(StringUtility.hasValue(vo.getOtherFormName2()) ? vo.getOtherFormName2().toUpperCase() : "");
			entity.setOtherFormName3(StringUtility.hasValue(vo.getOtherFormName3()) ? vo.getOtherFormName3().toUpperCase() : "");
			entity.setOtherFormName4(StringUtility.hasValue(vo.getOtherFormName4()) ? vo.getOtherFormName4().toUpperCase() : "");
			entity.setPreparedBy(StringUtility.toUpper(vo.getPreparedBy()));
			entity.setPreparedByPosition(StringUtility.toUpper(vo.getPreparedByPosition()));
			entity.setSiteSafetyPlanLoc(StringUtility.toUpper(vo.getSiteSafetyPlanLoc()));
			entity.setSiteSafetyPlanRqrd(StringBooleanEnum.toEnumValue(vo.getSiteSafetyPlanRqrd()));
		}
		
		return entity;
	}
	
	public static Collection<IapForm202> toEntityList(Collection<IapForm202Vo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IapForm202> entities = new ArrayList<IapForm202>();

		for(IapForm202Vo vo : vos){
			entities.add(IapForm202Vo.toEntity(null, vo,cascadable, persistables));
		}

		return entities;
	}

	/**
	 * @return the iapPlanId
	 */
	public Long getIapPlanId() {
		return iapPlanId;
	}

	/**
	 * @param iapPlanId the iapPlanId to set
	 */
	public void setIapPlanId(Long iapPlanId) {
		this.iapPlanId = iapPlanId;
	}

	/**
	 * @return the preparedBy
	 */
	public String getPreparedBy() {
		return preparedBy;
	}

	/**
	 * @param preparedBy the preparedBy to set
	 */
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	/**
	 * @return the preparedByPosition
	 */
	public String getPreparedByPosition() {
		return preparedByPosition;
	}

	/**
	 * @param preparedByPosition the preparedByPosition to set
	 */
	public void setPreparedByPosition(String preparedByPosition) {
		this.preparedByPosition = preparedByPosition;
	}

	/**
	 * @return the approvedBy
	 */
	public String getApprovedBy() {
		return approvedBy;
	}

	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * @return the approvedDate
	 */
	public Date getApprovedDate() {
		return approvedDate;
	}

	/**
	 * @param approvedDate the approvedDate to set
	 */
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	/**
	 * @return the objectives
	 */
	public String getObjectives() {
		return objectives;
	}

	/**
	 * @param objectives the objectives to set
	 */
	public void setObjectives(String objectives) {
		this.objectives = objectives;
	}

	/**
	 * @return the commandEmphasis
	 */
	public String getCommandEmphasis() {
		return commandEmphasis;
	}

	/**
	 * @param commandEmphasis the commandEmphasis to set
	 */
	public void setCommandEmphasis(String commandEmphasis) {
		this.commandEmphasis = commandEmphasis;
	}

	/**
	 * @return the generalSituationalAwareness
	 */
	public String getGeneralSituationalAwareness() {
		return generalSituationalAwareness;
	}

	/**
	 * @param generalSituationalAwareness the generalSituationalAwareness to set
	 */
	public void setGeneralSituationalAwareness(String generalSituationalAwareness) {
		this.generalSituationalAwareness = generalSituationalAwareness;
	}

	/**
	 * @return the siteSafetyPlanRqrd
	 */
	public Boolean getSiteSafetyPlanRqrd() {
		return siteSafetyPlanRqrd;
	}

	/**
	 * @param siteSafetyPlanRqrd the siteSafetyPlanRqrd to set
	 */
	public void setSiteSafetyPlanRqrd(Boolean siteSafetyPlanRqrd) {
		this.siteSafetyPlanRqrd = siteSafetyPlanRqrd;
	}

	/**
	 * @return the siteSafetyPlanLoc
	 */
	public String getSiteSafetyPlanLoc() {
		return siteSafetyPlanLoc;
	}

	/**
	 * @param siteSafetyPlanLoc the siteSafetyPlanLoc to set
	 */
	public void setSiteSafetyPlanLoc(String siteSafetyPlanLoc) {
		this.siteSafetyPlanLoc = siteSafetyPlanLoc;
	}

	/**
	 * @return the isForm202Attached
	 */
	public Boolean getIsForm202Attached() {
		return isForm202Attached;
	}

	/**
	 * @param isForm202Attached the isForm202Attached to set
	 */
	public void setIsForm202Attached(Boolean isForm202Attached) {
		this.isForm202Attached = isForm202Attached;
	}

	/**
	 * @return the isForm203Attached
	 */
	public Boolean getIsForm203Attached() {
		return isForm203Attached;
	}

	/**
	 * @param isForm203Attached the isForm203Attached to set
	 */
	public void setIsForm203Attached(Boolean isForm203Attached) {
		this.isForm203Attached = isForm203Attached;
	}

	/**
	 * @return the isForm204Attached
	 */
	public Boolean getIsForm204Attached() {
		return isForm204Attached;
	}

	/**
	 * @param isForm204Attached the isForm204Attached to set
	 */
	public void setIsForm204Attached(Boolean isForm204Attached) {
		this.isForm204Attached = isForm204Attached;
	}

	/**
	 * @return the isForm205Attached
	 */
	public Boolean getIsForm205Attached() {
		return isForm205Attached;
	}

	/**
	 * @param isForm205Attached the isForm205Attached to set
	 */
	public void setIsForm205Attached(Boolean isForm205Attached) {
		this.isForm205Attached = isForm205Attached;
	}

	/**
	 * @return the isForm205aAttached
	 */
	public Boolean getIsForm205aAttached() {
		return isForm205aAttached;
	}

	/**
	 * @param isForm205aAttached the isForm205aAttached to set
	 */
	public void setIsForm205aAttached(Boolean isForm205aAttached) {
		this.isForm205aAttached = isForm205aAttached;
	}

	/**
	 * @return the isForm206Attached
	 */
	public Boolean getIsForm206Attached() {
		return isForm206Attached;
	}

	/**
	 * @param isForm206Attached the isForm206Attached to set
	 */
	public void setIsForm206Attached(Boolean isForm206Attached) {
		this.isForm206Attached = isForm206Attached;
	}

	/**
	 * @return the isForm207Attached
	 */
	public Boolean getIsForm207Attached() {
		return isForm207Attached;
	}

	/**
	 * @param isForm207Attached the isForm207Attached to set
	 */
	public void setIsForm207Attached(Boolean isForm207Attached) {
		this.isForm207Attached = isForm207Attached;
	}

	/**
	 * @return the isForm208Attached
	 */
	public Boolean getIsForm208Attached() {
		return isForm208Attached;
	}

	/**
	 * @param isForm208Attached the isForm208Attached to set
	 */
	public void setIsForm208Attached(Boolean isForm208Attached) {
		this.isForm208Attached = isForm208Attached;
	}

	/**
	 * @return the isIncidentMapAttached
	 */
	public Boolean getIsIncidentMapAttached() {
		return isIncidentMapAttached;
	}

	/**
	 * @param isIncidentMapAttached the isIncidentMapAttached to set
	 */
	public void setIsIncidentMapAttached(Boolean isIncidentMapAttached) {
		this.isIncidentMapAttached = isIncidentMapAttached;
	}

	/**
	 * @return the isWeatherForecastAttached
	 */
	public Boolean getIsWeatherForecastAttached() {
		return isWeatherForecastAttached;
	}

	/**
	 * @param isWeatherForecastAttached the isWeatherForecastAttached to set
	 */
	public void setIsWeatherForecastAttached(Boolean isWeatherForecastAttached) {
		this.isWeatherForecastAttached = isWeatherForecastAttached;
	}

	/**
	 * @return the isOtherFormAttached1
	 */
	public Boolean getIsOtherFormAttached1() {
		return isOtherFormAttached1;
	}

	/**
	 * @param isOtherFormAttached1 the isOtherFormAttached1 to set
	 */
	public void setIsOtherFormAttached1(Boolean isOtherFormAttached1) {
		this.isOtherFormAttached1 = isOtherFormAttached1;
	}

	/**
	 * @return the isOtherFormAttached2
	 */
	public Boolean getIsOtherFormAttached2() {
		return isOtherFormAttached2;
	}

	/**
	 * @param isOtherFormAttached2 the isOtherFormAttached2 to set
	 */
	public void setIsOtherFormAttached2(Boolean isOtherFormAttached2) {
		this.isOtherFormAttached2 = isOtherFormAttached2;
	}

	/**
	 * @return the isOtherFormAttached3
	 */
	public Boolean getIsOtherFormAttached3() {
		return isOtherFormAttached3;
	}

	/**
	 * @param isOtherFormAttached3 the isOtherFormAttached3 to set
	 */
	public void setIsOtherFormAttached3(Boolean isOtherFormAttached3) {
		this.isOtherFormAttached3 = isOtherFormAttached3;
	}

	/**
	 * @return the isOtherFormAttached4
	 */
	public Boolean getIsOtherFormAttached4() {
		return isOtherFormAttached4;
	}

	/**
	 * @param isOtherFormAttached4 the isOtherFormAttached4 to set
	 */
	public void setIsOtherFormAttached4(Boolean isOtherFormAttached4) {
		this.isOtherFormAttached4 = isOtherFormAttached4;
	}

	/**
	 * @return the otherFormName1
	 */
	public String getOtherFormName1() {
		return otherFormName1;
	}

	/**
	 * @param otherFormName1 the otherFormName1 to set
	 */
	public void setOtherFormName1(String otherFormName1) {
		this.otherFormName1 = otherFormName1;
	}

	/**
	 * @return the otherFormName2
	 */
	public String getOtherFormName2() {
		return otherFormName2;
	}

	/**
	 * @param otherFormName2 the otherFormName2 to set
	 */
	public void setOtherFormName2(String otherFormName2) {
		this.otherFormName2 = otherFormName2;
	}

	/**
	 * @return the otherFormName3
	 */
	public String getOtherFormName3() {
		return otherFormName3;
	}

	/**
	 * @param otherFormName3 the otherFormName3 to set
	 */
	public void setOtherFormName3(String otherFormName3) {
		this.otherFormName3 = otherFormName3;
	}

	/**
	 * @return the otherFormName4
	 */
	public String getOtherFormName4() {
		return otherFormName4;
	}

	/**
	 * @param otherFormName4 the otherFormName4 to set
	 */
	public void setOtherFormName4(String otherFormName4) {
		this.otherFormName4 = otherFormName4;
	}

	/**
	 * @return the isFormLocked
	 */
	public Boolean getIsFormLocked() {
		return isFormLocked;
	}

	/**
	 * @param isFormLocked the isFormLocked to set
	 */
	public void setIsFormLocked(Boolean isFormLocked) {
		this.isFormLocked = isFormLocked;
	}

	/**
	 * @return the approvedDateString
	 */
	public String getApprovedDateString() {
		return approvedDateString;
	}

	/**
	 * @param approvedDateString the approvedDateString to set
	 */
	public void setApprovedDateString(String approvedDateString) {
		this.approvedDateString = approvedDateString;
	}

	/**
	 * @return the approvedDateTime
	 */
	public String getApprovedDateTime() {
		return approvedDateTime;
	}

	/**
	 * @param approvedDateTime the approvedDateTime to set
	 */
	public void setApprovedDateTime(String approvedDateTime) {
		this.approvedDateTime = approvedDateTime;
	}

	/**
	 * @return the isForm220Attached
	 */
	public Boolean getIsForm220Attached() {
		return isForm220Attached;
	}

	/**
	 * @param isForm220Attached the isForm220Attached to set
	 */
	public void setIsForm220Attached(Boolean isForm220Attached) {
		this.isForm220Attached = isForm220Attached;
	}

	
}
