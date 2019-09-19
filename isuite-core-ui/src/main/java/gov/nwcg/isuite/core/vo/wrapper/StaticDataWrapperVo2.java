package gov.nwcg.isuite.core.vo.wrapper;

import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.TrainingSystemVo;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Calendar;

import com.google.gson.Gson;

public class StaticDataWrapperVo2 {
	private String serverDate = "";
	private String serverVersion="1.2.4";
	private TrainingSystemVo trainingVo=null;
	
	private String systemRoles;
	private String agencies;
	private String agencyGroups;
	private String kinds;
	private String eventTypes;
	private String organizations;
	private String nonPdcOrgs;
	private String pdcOrgs;
	private String jetpors;
	private String countryCodeSubs;
	private String countryCodes;
	private String sit209s;
	private String requestCategories;
	private String regionCodes;
	private String specialPays;
	private String adjustCategories;
	private String rateClasses;
	private String rateAreas;
	private String rateClassRates;
	private String accrualCodes;
	private String adjustmentTypes;
	private String assignmentStatusess;
	private String travelMethodss;
	private String ratesTypess;
	private String unitsOfMeasures;
	private String sysParams;
	private String departments;
	private String dailyForms;
	private String groupCategories;
	private String subGroupCategories;
	private String rateGroups;
	private String systemTypes;
	private String fonts;
	private String sections;
	private String complexities;
	private String recommendations;
		
	public StaticDataWrapperVo2() {
	}

	public static StaticDataWrapperVo2 getInstance(GlobalCacheVo gcvo) throws Exception{
		StaticDataWrapperVo2 vo = new StaticDataWrapperVo2();
		Gson gson = new Gson();
		
		 
		//vo.setServerDate(DateUtil.toDateString(Calendar.getInstance().getTime(),DateUtil.MM_SLASH_DD_SLASH_YYYY) + " (Patch 3c to 1.1.0 Applied)");
		vo.setServerDate(DateUtil.toDateString(Calendar.getInstance().getTime(),DateUtil.MM_SLASH_DD_SLASH_YYYY) + " (Patch 2 to 1.2.4 Applied)");
		//vo.setServerDate(DateUtil.toDateString(Calendar.getInstance().getTime(),DateUtil.MM_SLASH_DD_SLASH_YYYY) + " (2017 AD Rates Applied)");
		//vo.setServerDate(DateUtil.toDateString(Calendar.getInstance().getTime(),DateUtil.MM_SLASH_DD_SLASH_YYYY));

		vo.setAccrualCodes(gson.toJson(gcvo.getAccrualCodeVos()));
		vo.setAdjustCategories(gson.toJson(gcvo.getAdjustCategoryVos()));
		vo.setAgencies(gson.toJson(gcvo.getAgencyVos()));

		/*
		vo.setCountryCodeSubdivisionVos(gcvo.getCountryCodeSubdivisionVos());
		vo.setCountryCodeVos(gcvo.getCountryCodeVos());
		vo.setEventTypeVos(gcvo.getEventTypeVos());
		vo.setJetPortVos(gcvo.getJetPortVos());
		vo.setKindVos(gcvo.getKindVos());
		vo.setNonPDCOrgs(gcvo.getNonPDCOrgs());
		vo.setOrganizationVos(gcvo.getOrganizationVos());
		vo.setRateAreaVos(gcvo.getRateAreaVos());
		vo.setRateClassVos(gcvo.getRateClassVos());
		vo.setRateClassRateVos(gcvo.getRateClassRateVos());
		vo.setRegionCodeVos(gcvo.getRegionCodeVos());
		vo.setRequestCategoryVos(gcvo.getRequestCategoryVos());
		vo.setComplexityVos(gcvo.getComplexityVos());
		vo.setRecommendationVos(gcvo.getRecommendationVos());
		vo.setSit209CodeVos(gcvo.getSit209CodeVos());
		vo.setSpecialPayVos(gcvo.getSpecialPayVos());
		vo.setSystemRoleVos(gcvo.getSystemRoleVos());
		vo.setAdjustmentTypeVos(AdjustmentTypeEnum.getAdjustmentTypeList());
		vo.setAssignmentStatuses(AssignmentStatusTypeEnum.getAssignmentVoList(false));
		vo.setTravelMethods(TravelMethodTypeEnum.getTravelMethodVoList());
		vo.setAgencyGroupVos(gcvo.getAgencyGroupVos());
		vo.setDepartmentVos(gcvo.getDepartmentVos());
		vo.setDailyFormVos(gcvo.getDailyFormVos());
		vo.setGroupCategoryVos(gcvo.getGroupCategoryVos());
		vo.setSubGroupCategoryVos(gcvo.getSubGroupCategoryVos());
		vo.setRateGroupVos(gcvo.getRateGroupVos());
		vo.setFontVos(gcvo.getFontVos());
		*/
		return vo;
	}

	public String getServerDate() {
		return serverDate;
	}

	public void setServerDate(String serverDate) {
		this.serverDate = serverDate;
	}

	public String getServerVersion() {
		return serverVersion;
	}

	public void setServerVersion(String serverVersion) {
		this.serverVersion = serverVersion;
	}

	public TrainingSystemVo getTrainingVo() {
		return trainingVo;
	}

	public void setTrainingVo(TrainingSystemVo trainingVo) {
		this.trainingVo = trainingVo;
	}

	public String getSystemRoles() {
		return systemRoles;
	}

	public void setSystemRoles(String systemRoles) {
		this.systemRoles = systemRoles;
	}

	public String getAgencies() {
		return agencies;
	}

	public void setAgencies(String agencies) {
		this.agencies = agencies;
	}

	public String getAgencyGroups() {
		return agencyGroups;
	}

	public void setAgencyGroups(String agencyGroups) {
		this.agencyGroups = agencyGroups;
	}

	public String getKinds() {
		return kinds;
	}

	public void setKinds(String kinds) {
		this.kinds = kinds;
	}

	public String getEventTypes() {
		return eventTypes;
	}

	public void setEventTypes(String eventTypes) {
		this.eventTypes = eventTypes;
	}

	public String getOrganizations() {
		return organizations;
	}

	public void setOrganizations(String organizations) {
		this.organizations = organizations;
	}

	public String getNonPdcOrgs() {
		return nonPdcOrgs;
	}

	public void setNonPdcOrgs(String nonPdcOrgs) {
		this.nonPdcOrgs = nonPdcOrgs;
	}

	public String getPdcOrgs() {
		return pdcOrgs;
	}

	public void setPdcOrgs(String pdcOrgs) {
		this.pdcOrgs = pdcOrgs;
	}

	public String getJetpors() {
		return jetpors;
	}

	public void setJetpors(String jetpors) {
		this.jetpors = jetpors;
	}

	public String getCountryCodeSubs() {
		return countryCodeSubs;
	}

	public void setCountryCodeSubs(String countryCodeSubs) {
		this.countryCodeSubs = countryCodeSubs;
	}

	public String getCountryCodes() {
		return countryCodes;
	}

	public void setCountryCodes(String countryCodes) {
		this.countryCodes = countryCodes;
	}

	public String getSit209s() {
		return sit209s;
	}

	public void setSit209s(String sit209s) {
		this.sit209s = sit209s;
	}

	public String getRequestCategories() {
		return requestCategories;
	}

	public void setRequestCategories(String requestCategories) {
		this.requestCategories = requestCategories;
	}

	public String getRegionCodes() {
		return regionCodes;
	}

	public void setRegionCodes(String regionCodes) {
		this.regionCodes = regionCodes;
	}

	public String getSpecialPays() {
		return specialPays;
	}

	public void setSpecialPays(String specialPays) {
		this.specialPays = specialPays;
	}

	public String getAdjustCategories() {
		return adjustCategories;
	}

	public void setAdjustCategories(String adjustCategories) {
		this.adjustCategories = adjustCategories;
	}

	public String getRateClasses() {
		return rateClasses;
	}

	public void setRateClasses(String rateClasses) {
		this.rateClasses = rateClasses;
	}

	public String getRateAreas() {
		return rateAreas;
	}

	public void setRateAreas(String rateAreas) {
		this.rateAreas = rateAreas;
	}

	public String getRateClassRates() {
		return rateClassRates;
	}

	public void setRateClassRates(String rateClassRates) {
		this.rateClassRates = rateClassRates;
	}

	public String getAccrualCodes() {
		return accrualCodes;
	}

	public void setAccrualCodes(String accrualCodes) {
		this.accrualCodes = accrualCodes;
	}

	public String getAdjustmentTypes() {
		return adjustmentTypes;
	}

	public void setAdjustmentTypes(String adjustmentTypes) {
		this.adjustmentTypes = adjustmentTypes;
	}

	public String getAssignmentStatusess() {
		return assignmentStatusess;
	}

	public void setAssignmentStatusess(String assignmentStatusess) {
		this.assignmentStatusess = assignmentStatusess;
	}

	public String getTravelMethodss() {
		return travelMethodss;
	}

	public void setTravelMethodss(String travelMethodss) {
		this.travelMethodss = travelMethodss;
	}

	public String getRatesTypess() {
		return ratesTypess;
	}

	public void setRatesTypess(String ratesTypess) {
		this.ratesTypess = ratesTypess;
	}

	public String getUnitsOfMeasures() {
		return unitsOfMeasures;
	}

	public void setUnitsOfMeasures(String unitsOfMeasures) {
		this.unitsOfMeasures = unitsOfMeasures;
	}

	public String getSysParams() {
		return sysParams;
	}

	public void setSysParams(String sysParams) {
		this.sysParams = sysParams;
	}

	public String getDepartments() {
		return departments;
	}

	public void setDepartments(String departments) {
		this.departments = departments;
	}

	public String getDailyForms() {
		return dailyForms;
	}

	public void setDailyForms(String dailyForms) {
		this.dailyForms = dailyForms;
	}

	public String getGroupCategories() {
		return groupCategories;
	}

	public void setGroupCategories(String groupCategories) {
		this.groupCategories = groupCategories;
	}

	public String getSubGroupCategories() {
		return subGroupCategories;
	}

	public void setSubGroupCategories(String subGroupCategories) {
		this.subGroupCategories = subGroupCategories;
	}

	public String getRateGroups() {
		return rateGroups;
	}

	public void setRateGroups(String rateGroups) {
		this.rateGroups = rateGroups;
	}

	public String getSystemTypes() {
		return systemTypes;
	}

	public void setSystemTypes(String systemTypes) {
		this.systemTypes = systemTypes;
	}

	public String getFonts() {
		return fonts;
	}

	public void setFonts(String fonts) {
		this.fonts = fonts;
	}

	public String getSections() {
		return sections;
	}

	public void setSections(String sections) {
		this.sections = sections;
	}

	public String getComplexities() {
		return complexities;
	}

	public void setComplexities(String complexities) {
		this.complexities = complexities;
	}

	public String getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(String recommendations) {
		this.recommendations = recommendations;
	}
	


}
