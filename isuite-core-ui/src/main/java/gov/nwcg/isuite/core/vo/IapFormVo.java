package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Iap;
import gov.nwcg.isuite.core.domain.impl.IapImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;

import java.util.Date;

public class IapFormVo extends AbstractVo {
	private Long parentIapId;
	private String formType;
	private Boolean isLocked;
	
	// form fields
	private String preparedBy;  // 202
	private String preparedByTitle; // 202
	
	private String approvedBy; // 202 
	private Date approvedByDate; // 202 
	private String approvedByDateString; // helper field
	private String approvedByTimeString; // helper field
	
	private String objectives;  // 202
	private String commandEmphasis; // 202
	private String genSitAwareness; // 202
	
	private Boolean siteSafetyPlanRqrd; // 202
	private String siteSafetyPlanLoc; // 202
	
	private Boolean attachForm202;
	private Boolean attachForm203;
	private Boolean attachForm204;
	private Boolean attachForm205;
	private Boolean attachForm205a;
	private Boolean attachForm206;
	private Boolean attachForm207;
	private Boolean attachForm208;
	private Boolean attachForm220;
	private Boolean attachIncidentMap;
	private Boolean attachSafetyMsg;
	private Boolean attachTrafficPlan;
	private Boolean attachWeatherForecast;
	private Boolean attachOther1;
	private String attachOther1Name;
	private Boolean attachOther2;
	private String attachOther2Name;
	private Boolean attachOther3;
	private String attachOther3Name;
	private Boolean attachOther4;
	private String attachOther4Name;
	
	
	public IapFormVo(){
		
	}
	
	public static IapFormVo getInstance(Iap entity, Boolean cascadable) throws Exception {
		IapFormVo vo = new IapFormVo();
		
		vo.setId(entity.getId());
		vo.setParentIapId(entity.getIapPlanId());
		
		if(cascadable){
			if(BooleanUtility.isTrue(StringBooleanEnum.toBooleanValue(entity.getForm202Type())))
				load202Vo(vo,entity);
		}
		
		return vo;
	}
	
	/**
	 * Loads the iapformvo for a 202 form
	 * 
	 * @param entity
	 * @param vo
	 */
	private static void load202Vo(IapFormVo vo, Iap entity){
		vo.setFormType("ICS 202");
		vo.setObjectives(entity.getD202B3Objectives());
		vo.setCommandEmphasis(entity.getD202B4CommandEmphasis());
		vo.setGenSitAwareness(entity.getD202B4GenSitAwareness());
		vo.setSiteSafetyPlanRqrd(StringBooleanEnum.toBooleanValue(entity.getD202B5SiteSafetyPlanRqrd()));
		vo.setSiteSafetyPlanLoc(entity.getD202B5SiteSafetyPlanLoc());
		vo.setAttachForm203(StringBooleanEnum.toBooleanValue(entity.getAttachForm203()));
		vo.setAttachForm204(StringBooleanEnum.toBooleanValue(entity.getAttachForm204()));
		vo.setAttachForm205(StringBooleanEnum.toBooleanValue(entity.getAttachForm205()));
		vo.setAttachForm205a(StringBooleanEnum.toBooleanValue(entity.getAttachForm205a()));
		vo.setAttachForm206(StringBooleanEnum.toBooleanValue(entity.getAttachForm206()));
		vo.setAttachForm207(StringBooleanEnum.toBooleanValue(entity.getAttachForm207()));
		vo.setAttachForm208(StringBooleanEnum.toBooleanValue(entity.getAttachForm208()));
		vo.setAttachIncidentMap(StringBooleanEnum.toBooleanValue(entity.getAttachIncidentMap()));
		vo.setAttachWeatherForecast(StringBooleanEnum.toBooleanValue(entity.getAttachWeatherForecast()));
		vo.setPreparedBy(entity.getB202PreparedBy());
		// missing preparedByTitle
		vo.setApprovedBy(entity.getB202ApprovedBy());
		// missing approvedByDate
	}
	
	public static Iap toEntity(Iap entity, IapFormVo vo, Boolean cascadable, Persistable... persistables) throws Exception {
		if(null==entity) entity = new IapImpl();
		
		entity.setId(vo.getId());

		Iap iapPlan=(Iap)AbstractVo.getPersistableObject(persistables, IapImpl.class);
		if(null != iapPlan){
			entity.setIapPlan(iapPlan);
			entity.setIapPlanId(iapPlan.getId());
		}
		
		if(cascadable){
			if(vo.getFormType().equals("ICS 202"))
				load202Entity(entity,vo);
			if(vo.getFormType().equals("ICS 203"))
				load203Entity(entity,vo);
			if(vo.getFormType().equals("ICS 204"))
				load204Entity(entity,vo);
			if(vo.getFormType().equals("ICS 205"))
				load205Entity(entity,vo);
			if(vo.getFormType().equals("ICS 206"))
				load206Entity(entity,vo);
			if(vo.getFormType().equals("ICS 220"))
				load220Entity(entity,vo);
		}
		
		return entity;
	}

	/**
	 * Loads the iap entity for a 202 form
	 * 
	 * @param entity
	 * @param vo
	 */
	private static void load202Entity(Iap entity,IapFormVo vo){
		entity.setForm202Type(StringBooleanEnum.Y);
		
		// Block 3 
		entity.setD202B3Objectives(vo.getObjectives());
		
		// Block 4 
		entity.setD202B4CommandEmphasis(vo.getCommandEmphasis());
		entity.setD202B4GenSitAwareness(vo.getGenSitAwareness());
		
		// Block 5
		entity.setD202B5SiteSafetyPlanRqrd(StringBooleanEnum.toEnumValue(vo.getSiteSafetyPlanRqrd()));
		entity.setD202B5SiteSafetyPlanLoc(vo.getSiteSafetyPlanLoc());
		
		// Block 6
		// missing 202, other1 thru 4, other1-4 text
		entity.setAttachForm203(StringBooleanEnum.toEnumValue(vo.getAttachForm203()));
		entity.setAttachForm204(StringBooleanEnum.toEnumValue(vo.getAttachForm204()));
		entity.setAttachForm205(StringBooleanEnum.toEnumValue(vo.getAttachForm205()));
		entity.setAttachForm205a(StringBooleanEnum.toEnumValue(vo.getAttachForm205a()));
		entity.setAttachForm206(StringBooleanEnum.toEnumValue(vo.getAttachForm206()));
		entity.setAttachForm207(StringBooleanEnum.toEnumValue(vo.getAttachForm207()));
		entity.setAttachForm208(StringBooleanEnum.toEnumValue(vo.getAttachForm208()));
		entity.setAttachIncidentMap(StringBooleanEnum.toEnumValue(vo.getAttachIncidentMap()));
		entity.setAttachWeatherForecast(StringBooleanEnum.toEnumValue(vo.getAttachWeatherForecast()));

		// Block 7
		entity.setB202PreparedBy(vo.getPreparedBy());
		// missing preparedByTitle
		entity.setB202ApprovedBy(vo.getApprovedBy());
		// missing approvedByDate
		
	}

	/**
	 * Loads the iap entity for a 203 form
	 * 
	 * @param entity
	 * @param vo
	 */
	private static void load203Entity(Iap entity,IapFormVo vo){
		entity.setForm203Type(StringBooleanEnum.Y);
		
	}

	/**
	 * Loads the iap entity for a 204 form
	 * 
	 * @param entity
	 * @param vo
	 */
	private static void load204Entity(Iap entity,IapFormVo vo){
		entity.setForm204Type(StringBooleanEnum.Y);
		
	}

	/**
	 * Loads the iap entity for a 205 form
	 * 
	 * @param entity
	 * @param vo
	 */
	private static void load205Entity(Iap entity,IapFormVo vo){
		entity.setForm205Type(StringBooleanEnum.Y);
		
	}
	
	/**
	 * Loads the iap entity for a 206 form
	 * 
	 * @param entity
	 * @param vo
	 */
	private static void load206Entity(Iap entity,IapFormVo vo){
		entity.setForm206Type(StringBooleanEnum.Y);
		
	}

	/**
	 * Loads the iap entity for a 220 form
	 * 
	 * @param entity
	 * @param vo
	 */
	private static void load220Entity(Iap entity,IapFormVo vo){
		entity.setForm220Type(StringBooleanEnum.Y);
		
	}
	
	/**
	 * @return the parentIapId
	 */
	public Long getParentIapId() {
		return parentIapId;
	}


	/**
	 * @param parentIapId the parentIapId to set
	 */
	public void setParentIapId(Long parentIapId) {
		this.parentIapId = parentIapId;
	}


	/**
	 * @return the formType
	 */
	public String getFormType() {
		return formType;
	}


	/**
	 * @param formType the formType to set
	 */
	public void setFormType(String formType) {
		this.formType = formType;
	}


	/**
	 * @return the isLocked
	 */
	public Boolean getIsLocked() {
		return isLocked;
	}


	/**
	 * @param isLocked the isLocked to set
	 */
	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
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
	 * @return the preparedByTitle
	 */
	public String getPreparedByTitle() {
		return preparedByTitle;
	}


	/**
	 * @param preparedByTitle the preparedByTitle to set
	 */
	public void setPreparedByTitle(String preparedByTitle) {
		this.preparedByTitle = preparedByTitle;
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
	 * @return the approvedByDate
	 */
	public Date getApprovedByDate() {
		return approvedByDate;
	}


	/**
	 * @param approvedByDate the approvedByDate to set
	 */
	public void setApprovedByDate(Date approvedByDate) {
		this.approvedByDate = approvedByDate;
	}


	/**
	 * @return the approvedByDateString
	 */
	public String getApprovedByDateString() {
		return approvedByDateString;
	}


	/**
	 * @param approvedByDateString the approvedByDateString to set
	 */
	public void setApprovedByDateString(String approvedByDateString) {
		this.approvedByDateString = approvedByDateString;
	}


	/**
	 * @return the approvedByTimeString
	 */
	public String getApprovedByTimeString() {
		return approvedByTimeString;
	}


	/**
	 * @param approvedByTimeString the approvedByTimeString to set
	 */
	public void setApprovedByTimeString(String approvedByTimeString) {
		this.approvedByTimeString = approvedByTimeString;
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
	 * @return the genSitAwareness
	 */
	public String getGenSitAwareness() {
		return genSitAwareness;
	}


	/**
	 * @param genSitAwareness the genSitAwareness to set
	 */
	public void setGenSitAwareness(String genSitAwareness) {
		this.genSitAwareness = genSitAwareness;
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
	 * @return the attachForm202
	 */
	public Boolean getAttachForm202() {
		return attachForm202;
	}


	/**
	 * @param attachForm202 the attachForm202 to set
	 */
	public void setAttachForm202(Boolean attachForm202) {
		this.attachForm202 = attachForm202;
	}


	/**
	 * @return the attachForm203
	 */
	public Boolean getAttachForm203() {
		return attachForm203;
	}


	/**
	 * @param attachForm203 the attachForm203 to set
	 */
	public void setAttachForm203(Boolean attachForm203) {
		this.attachForm203 = attachForm203;
	}


	/**
	 * @return the attachForm204
	 */
	public Boolean getAttachForm204() {
		return attachForm204;
	}


	/**
	 * @param attachForm204 the attachForm204 to set
	 */
	public void setAttachForm204(Boolean attachForm204) {
		this.attachForm204 = attachForm204;
	}


	/**
	 * @return the attachForm205
	 */
	public Boolean getAttachForm205() {
		return attachForm205;
	}


	/**
	 * @param attachForm205 the attachForm205 to set
	 */
	public void setAttachForm205(Boolean attachForm205) {
		this.attachForm205 = attachForm205;
	}


	/**
	 * @return the attachForm205a
	 */
	public Boolean getAttachForm205a() {
		return attachForm205a;
	}


	/**
	 * @param attachForm205a the attachForm205a to set
	 */
	public void setAttachForm205a(Boolean attachForm205a) {
		this.attachForm205a = attachForm205a;
	}


	/**
	 * @return the attachForm206
	 */
	public Boolean getAttachForm206() {
		return attachForm206;
	}


	/**
	 * @param attachForm206 the attachForm206 to set
	 */
	public void setAttachForm206(Boolean attachForm206) {
		this.attachForm206 = attachForm206;
	}


	/**
	 * @return the attachForm207
	 */
	public Boolean getAttachForm207() {
		return attachForm207;
	}


	/**
	 * @param attachForm207 the attachForm207 to set
	 */
	public void setAttachForm207(Boolean attachForm207) {
		this.attachForm207 = attachForm207;
	}


	/**
	 * @return the attachForm208
	 */
	public Boolean getAttachForm208() {
		return attachForm208;
	}


	/**
	 * @param attachForm208 the attachForm208 to set
	 */
	public void setAttachForm208(Boolean attachForm208) {
		this.attachForm208 = attachForm208;
	}


	/**
	 * @return the attachForm220
	 */
	public Boolean getAttachForm220() {
		return attachForm220;
	}


	/**
	 * @param attachForm220 the attachForm220 to set
	 */
	public void setAttachForm220(Boolean attachForm220) {
		this.attachForm220 = attachForm220;
	}


	/**
	 * @return the attachIncidentMap
	 */
	public Boolean getAttachIncidentMap() {
		return attachIncidentMap;
	}


	/**
	 * @param attachIncidentMap the attachIncidentMap to set
	 */
	public void setAttachIncidentMap(Boolean attachIncidentMap) {
		this.attachIncidentMap = attachIncidentMap;
	}


	/**
	 * @return the attachSafetyMsg
	 */
	public Boolean getAttachSafetyMsg() {
		return attachSafetyMsg;
	}


	/**
	 * @param attachSafetyMsg the attachSafetyMsg to set
	 */
	public void setAttachSafetyMsg(Boolean attachSafetyMsg) {
		this.attachSafetyMsg = attachSafetyMsg;
	}


	/**
	 * @return the attachTrafficPlan
	 */
	public Boolean getAttachTrafficPlan() {
		return attachTrafficPlan;
	}


	/**
	 * @param attachTrafficPlan the attachTrafficPlan to set
	 */
	public void setAttachTrafficPlan(Boolean attachTrafficPlan) {
		this.attachTrafficPlan = attachTrafficPlan;
	}


	/**
	 * @return the attachWeatherForecast
	 */
	public Boolean getAttachWeatherForecast() {
		return attachWeatherForecast;
	}


	/**
	 * @param attachWeatherForecast the attachWeatherForecast to set
	 */
	public void setAttachWeatherForecast(Boolean attachWeatherForecast) {
		this.attachWeatherForecast = attachWeatherForecast;
	}


	/**
	 * @return the attachOther1
	 */
	public Boolean getAttachOther1() {
		return attachOther1;
	}


	/**
	 * @param attachOther1 the attachOther1 to set
	 */
	public void setAttachOther1(Boolean attachOther1) {
		this.attachOther1 = attachOther1;
	}


	/**
	 * @return the attachOther1Name
	 */
	public String getAttachOther1Name() {
		return attachOther1Name;
	}


	/**
	 * @param attachOther1Name the attachOther1Name to set
	 */
	public void setAttachOther1Name(String attachOther1Name) {
		this.attachOther1Name = attachOther1Name;
	}


	/**
	 * @return the attachOther2
	 */
	public Boolean getAttachOther2() {
		return attachOther2;
	}


	/**
	 * @param attachOther2 the attachOther2 to set
	 */
	public void setAttachOther2(Boolean attachOther2) {
		this.attachOther2 = attachOther2;
	}


	/**
	 * @return the attachOther2Name
	 */
	public String getAttachOther2Name() {
		return attachOther2Name;
	}


	/**
	 * @param attachOther2Name the attachOther2Name to set
	 */
	public void setAttachOther2Name(String attachOther2Name) {
		this.attachOther2Name = attachOther2Name;
	}


	/**
	 * @return the attachOther3
	 */
	public Boolean getAttachOther3() {
		return attachOther3;
	}


	/**
	 * @param attachOther3 the attachOther3 to set
	 */
	public void setAttachOther3(Boolean attachOther3) {
		this.attachOther3 = attachOther3;
	}


	/**
	 * @return the attachOther3Name
	 */
	public String getAttachOther3Name() {
		return attachOther3Name;
	}


	/**
	 * @param attachOther3Name the attachOther3Name to set
	 */
	public void setAttachOther3Name(String attachOther3Name) {
		this.attachOther3Name = attachOther3Name;
	}


	/**
	 * @return the attachOther4
	 */
	public Boolean getAttachOther4() {
		return attachOther4;
	}


	/**
	 * @param attachOther4 the attachOther4 to set
	 */
	public void setAttachOther4(Boolean attachOther4) {
		this.attachOther4 = attachOther4;
	}


	/**
	 * @return the attachOther4Name
	 */
	public String getAttachOther4Name() {
		return attachOther4Name;
	}


	/**
	 * @param attachOther4Name the attachOther4Name to set
	 */
	public void setAttachOther4Name(String attachOther4Name) {
		this.attachOther4Name = attachOther4Name;
	}
	
}
