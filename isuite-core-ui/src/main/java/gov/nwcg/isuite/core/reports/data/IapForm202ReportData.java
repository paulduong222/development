package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.core.domain.IapForm202;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.framework.report.data.BaseReportData;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.ReportTextUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Date;

public class IapForm202ReportData extends BaseReportData {
	private String field;
	private Date fromDate;
	private Date toDate;
	private String fromTime;
	private String toTime;
	private String fromDateTime;
	private String toDateTime;
	private String objectives;
	private String commandEmphasis;
	private String generalSitAwareness;
	private String siteSafetyPlanRqrdYes;
	private String siteSafetyPlanRqrdNo;
	private String siteSafetyPlanLoc;
	private String preparedBy;
	private String preparedByPosition;
	private String approvedBy;
	private String bottomDateTime;
	private String attach202;
	private String attach203;
	private String attach204;
	private String attach205;
	private String attach205a;
	private String attach206;
	private String attach207;
	private String attach208;
	private String attach220;
	private String attachMap;
	private String attachWeather;
	private String attachOther1;
	private String attachOther2;
	private String attachOther3;
	private String attachOther4;
	private String attachOther1Name;
	private String attachOther2Name;
	private String attachOther3Name;
	private String attachOther4Name;
	private String draftFinal;
	private String operationalPeriod;
	private String fromDayOfWeek;
	private String toDayOfWeek;
	
	public IapForm202ReportData() {
	}

	public static IapForm202ReportData getInstance(IapPlan iapPlan, IapForm202 entity) throws Exception {
		IapForm202ReportData reportData = new IapForm202ReportData();

		if(BooleanUtility.isTrue(entity.getIsFormLocked().getValue()))
			reportData.setDraftFinal("FINAL");
		else
			reportData.setDraftFinal("DRAFT");
		
		// Block 1
		reportData.setIncidentName(iapPlan.getIncidentName());
		reportData.setOperationalPeriod(iapPlan.getOperationPeriod());
		
		// Block 2
		if(DateUtil.hasValue(iapPlan.getFromDate())){
			String sdate = DateUtil.toDateString(iapPlan.getFromDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
			String stime = DateUtil.toMilitaryTime(iapPlan.getFromDate());
			reportData.setFromDateTime(sdate + " " + stime);
			reportData.setFromDayOfWeek(DateUtil.getDayOfWeekAbbrev(iapPlan.getFromDate()));
		}
		if(DateUtil.hasValue(iapPlan.getToDate())){
			String sdate = DateUtil.toDateString(iapPlan.getToDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
			String stime = DateUtil.toMilitaryTime(iapPlan.getToDate());
			
			if(stime.equals("2359"))
				stime = "2400";
			
			reportData.setToDateTime(sdate + " " + stime);
			reportData.setToDayOfWeek(DateUtil.getDayOfWeekAbbrev(iapPlan.getToDate()));
		}
		/*
		reportData.setFromDate(iapPlan.getFromDate());
		if(DateUtil.hasValue(iapPlan.getFromDate())){
			reportData.setFromTime(DateUtil.toMilitaryTime(iapPlan.getFromDate()));
			reportData.setFromDayOfWeek(DateUtil.getDayOfWeekAbbrev(iapPlan.getFromDate()));
		}
		reportData.setToDate(iapPlan.getToDate());
		if(DateUtil.hasValue(iapPlan.getToDate())){
			reportData.setToTime(DateUtil.toMilitaryTime(iapPlan.getToDate()));
			reportData.setToDayOfWeek(DateUtil.getDayOfWeekAbbrev(iapPlan.getToDate()));
		}
		*/
		
		// Block 3 
		if(StringUtility.hasValue(entity.getObjectives())){
			String str=ReportTextUtil.formatText(entity.getObjectives());
			reportData.setObjectives(str);
		}
		
		// Block 4
		if(StringUtility.hasValue(entity.getCommandEmphasis())){
			String str=ReportTextUtil.formatText(entity.getCommandEmphasis());
			reportData.setCommandEmphasis(str);
		}
		if(StringUtility.hasValue(entity.getGeneralSituationalAwareness())){
			String str=ReportTextUtil.formatText(entity.getGeneralSituationalAwareness());
			reportData.setGeneralSitAwareness(str);
		}
		
		// Block 5 
		reportData.setSiteSafetyPlanRqrdYes((StringBooleanEnum.toBooleanValue(entity.getSiteSafetyPlanRqrd())==true ? "X" : ""));
		reportData.setSiteSafetyPlanRqrdNo((StringBooleanEnum.toBooleanValue(entity.getSiteSafetyPlanRqrd())==false ? "X" : ""));
		reportData.setSiteSafetyPlanLoc(entity.getSiteSafetyPlanLoc());
		
		// Block 6
		reportData.setAttach202((StringBooleanEnum.toBooleanValue(entity.getIsForm202Attached())==true ? "X" : ""));
		reportData.setAttach203((StringBooleanEnum.toBooleanValue(entity.getIsForm203Attached())==true ? "X" : ""));
		reportData.setAttach204((StringBooleanEnum.toBooleanValue(entity.getIsForm204Attached())==true ? "X" : ""));
		reportData.setAttach205((StringBooleanEnum.toBooleanValue(entity.getIsForm205Attached())==true ? "X" : ""));
		reportData.setAttach205a((StringBooleanEnum.toBooleanValue(entity.getIsForm205aAttached())==true ? "X" : ""));
		reportData.setAttach206((StringBooleanEnum.toBooleanValue(entity.getIsForm206Attached())==true ? "X" : ""));
		reportData.setAttach207((StringBooleanEnum.toBooleanValue(entity.getIsForm207Attached())==true ? "X" : ""));
		reportData.setAttach208((StringBooleanEnum.toBooleanValue(entity.getIsForm208Attached())==true ? "X" : ""));
		reportData.setAttach220((StringBooleanEnum.toBooleanValue(entity.getIsForm220Attached())==true ? "X" : ""));
		reportData.setAttachMap((StringBooleanEnum.toBooleanValue(entity.getIsIncidentMapAttached())==true ? "X" : ""));
		reportData.setAttachWeather((StringBooleanEnum.toBooleanValue(entity.getIsWeatherForecastAttached())==true ? "X" : ""));
		reportData.setAttachOther1((StringBooleanEnum.toBooleanValue(entity.getIsOtherFormAttached1())==true ? "X" : ""));
		reportData.setAttachOther2((StringBooleanEnum.toBooleanValue(entity.getIsOtherFormAttached2())==true ? "X" : ""));
		reportData.setAttachOther3((StringBooleanEnum.toBooleanValue(entity.getIsOtherFormAttached3())==true ? "X" : ""));
		reportData.setAttachOther4((StringBooleanEnum.toBooleanValue(entity.getIsOtherFormAttached4())==true ? "X" : ""));
		reportData.setAttachOther1Name(entity.getOtherFormName1());
		reportData.setAttachOther2Name(entity.getOtherFormName2());
		reportData.setAttachOther3Name(entity.getOtherFormName3());
		reportData.setAttachOther4Name(entity.getOtherFormName4());
		
		// Block 7
		reportData.setPreparedBy(entity.getPreparedBy());
		reportData.setPreparedByPosition(entity.getPreparedByPosition());
		
		// Block 8
		reportData.setApprovedBy(entity.getApprovedBy());
		
		if(DateUtil.hasValue(entity.getApprovedDate())){
			String sdate=DateUtil.toDateString(entity.getApprovedDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
			String stime=DateUtil.toMilitaryTime(entity.getApprovedDate());
			reportData.setBottomDateTime(sdate+" " + stime);
		}
		

		return reportData;
	}
	
	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the fromTime
	 */
	public String getFromTime() {
		return fromTime;
	}

	/**
	 * @param fromTime the fromTime to set
	 */
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	/**
	 * @return the toTime
	 */
	public String getToTime() {
		return toTime;
	}

	/**
	 * @param toTime the toTime to set
	 */
	public void setToTime(String toTime) {
		this.toTime = toTime;
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
	 * @return the generalSitAwareness
	 */
	public String getGeneralSitAwareness() {
		return generalSitAwareness;
	}

	/**
	 * @param generalSitAwareness the generalSitAwareness to set
	 */
	public void setGeneralSitAwareness(String generalSitAwareness) {
		this.generalSitAwareness = generalSitAwareness;
	}

	/**
	 * @return the siteSafetyPlanRqrdYes
	 */
	public String getSiteSafetyPlanRqrdYes() {
		return siteSafetyPlanRqrdYes;
	}

	/**
	 * @param siteSafetyPlanRqrdYes the siteSafetyPlanRqrdYes to set
	 */
	public void setSiteSafetyPlanRqrdYes(String siteSafetyPlanRqrdYes) {
		this.siteSafetyPlanRqrdYes = siteSafetyPlanRqrdYes;
	}

	/**
	 * @return the siteSafetyPlanRqrdNo
	 */
	public String getSiteSafetyPlanRqrdNo() {
		return siteSafetyPlanRqrdNo;
	}

	/**
	 * @param siteSafetyPlanRqrdNo the siteSafetyPlanRqrdNo to set
	 */
	public void setSiteSafetyPlanRqrdNo(String siteSafetyPlanRqrdNo) {
		this.siteSafetyPlanRqrdNo = siteSafetyPlanRqrdNo;
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
	 * @return the bottomDateTime
	 */
	public String getBottomDateTime() {
		return bottomDateTime;
	}

	/**
	 * @param bottomDateTime the bottomDateTime to set
	 */
	public void setBottomDateTime(String bottomDateTime) {
		this.bottomDateTime = bottomDateTime;
	}

	/**
	 * @return the attach202
	 */
	public String getAttach202() {
		return attach202;
	}

	/**
	 * @param attach202 the attach202 to set
	 */
	public void setAttach202(String attach202) {
		this.attach202 = attach202;
	}

	/**
	 * @return the attach203
	 */
	public String getAttach203() {
		return attach203;
	}

	/**
	 * @param attach203 the attach203 to set
	 */
	public void setAttach203(String attach203) {
		this.attach203 = attach203;
	}

	/**
	 * @return the attach204
	 */
	public String getAttach204() {
		return attach204;
	}

	/**
	 * @param attach204 the attach204 to set
	 */
	public void setAttach204(String attach204) {
		this.attach204 = attach204;
	}

	/**
	 * @return the attach205
	 */
	public String getAttach205() {
		return attach205;
	}

	/**
	 * @param attach205 the attach205 to set
	 */
	public void setAttach205(String attach205) {
		this.attach205 = attach205;
	}

	/**
	 * @return the attach205a
	 */
	public String getAttach205a() {
		return attach205a;
	}

	/**
	 * @param attach205a the attach205a to set
	 */
	public void setAttach205a(String attach205a) {
		this.attach205a = attach205a;
	}

	/**
	 * @return the attach206
	 */
	public String getAttach206() {
		return attach206;
	}

	/**
	 * @param attach206 the attach206 to set
	 */
	public void setAttach206(String attach206) {
		this.attach206 = attach206;
	}

	/**
	 * @return the attach207
	 */
	public String getAttach207() {
		return attach207;
	}

	/**
	 * @param attach207 the attach207 to set
	 */
	public void setAttach207(String attach207) {
		this.attach207 = attach207;
	}

	/**
	 * @return the attach208
	 */
	public String getAttach208() {
		return attach208;
	}

	/**
	 * @param attach208 the attach208 to set
	 */
	public void setAttach208(String attach208) {
		this.attach208 = attach208;
	}

	/**
	 * @return the attachMap
	 */
	public String getAttachMap() {
		return attachMap;
	}

	/**
	 * @param attachMap the attachMap to set
	 */
	public void setAttachMap(String attachMap) {
		this.attachMap = attachMap;
	}

	/**
	 * @return the attachWeather
	 */
	public String getAttachWeather() {
		return attachWeather;
	}

	/**
	 * @param attachWeather the attachWeather to set
	 */
	public void setAttachWeather(String attachWeather) {
		this.attachWeather = attachWeather;
	}

	/**
	 * @return the attachOther1
	 */
	public String getAttachOther1() {
		return attachOther1;
	}

	/**
	 * @param attachOther1 the attachOther1 to set
	 */
	public void setAttachOther1(String attachOther1) {
		this.attachOther1 = attachOther1;
	}

	/**
	 * @return the attachOther2
	 */
	public String getAttachOther2() {
		return attachOther2;
	}

	/**
	 * @param attachOther2 the attachOther2 to set
	 */
	public void setAttachOther2(String attachOther2) {
		this.attachOther2 = attachOther2;
	}

	/**
	 * @return the attachOther3
	 */
	public String getAttachOther3() {
		return attachOther3;
	}

	/**
	 * @param attachOther3 the attachOther3 to set
	 */
	public void setAttachOther3(String attachOther3) {
		this.attachOther3 = attachOther3;
	}

	/**
	 * @return the attachOther4
	 */
	public String getAttachOther4() {
		return attachOther4;
	}

	/**
	 * @param attachOther4 the attachOther4 to set
	 */
	public void setAttachOther4(String attachOther4) {
		this.attachOther4 = attachOther4;
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

	/**
	 * @return the draftFinal
	 */
	public String getDraftFinal() {
		return draftFinal;
	}

	/**
	 * @param draftFinal the draftFinal to set
	 */
	public void setDraftFinal(String draftFinal) {
		this.draftFinal = draftFinal;
	}

	/**
	 * @return the operationalPeriod
	 */
	public String getOperationalPeriod() {
		return operationalPeriod;
	}

	/**
	 * @param operationalPeriod the operationalPeriod to set
	 */
	public void setOperationalPeriod(String operationalPeriod) {
		this.operationalPeriod = operationalPeriod;
	}

	/**
	 * @return the fromDayOfWeek
	 */
	public String getFromDayOfWeek() {
		return fromDayOfWeek;
	}

	/**
	 * @param fromDayOfWeek the fromDayOfWeek to set
	 */
	public void setFromDayOfWeek(String fromDayOfWeek) {
		this.fromDayOfWeek = fromDayOfWeek;
	}

	/**
	 * @return the toDayOfWeek
	 */
	public String getToDayOfWeek() {
		return toDayOfWeek;
	}

	/**
	 * @param toDayOfWeek the toDayOfWeek to set
	 */
	public void setToDayOfWeek(String toDayOfWeek) {
		this.toDayOfWeek = toDayOfWeek;
	}

	/**
	 * @return the attach220
	 */
	public String getAttach220() {
		return attach220;
	}

	/**
	 * @param attach220 the attach220 to set
	 */
	public void setAttach220(String attach220) {
		this.attach220 = attach220;
	}

	/**
	 * @return the fromDateTime
	 */
	public String getFromDateTime() {
		return fromDateTime;
	}

	/**
	 * @param fromDateTime the fromDateTime to set
	 */
	public void setFromDateTime(String fromDateTime) {
		this.fromDateTime = fromDateTime;
	}

	/**
	 * @return the toDateTime
	 */
	public String getToDateTime() {
		return toDateTime;
	}

	/**
	 * @param toDateTime the toDateTime to set
	 */
	public void setToDateTime(String toDateTime) {
		this.toDateTime = toDateTime;
	}
	
	
}
