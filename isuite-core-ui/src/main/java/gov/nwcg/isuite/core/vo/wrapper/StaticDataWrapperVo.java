package gov.nwcg.isuite.core.vo.wrapper;

import gov.nwcg.isuite.core.vo.AccrualCodeVo;
import gov.nwcg.isuite.core.vo.AdjustCategoryVo;
import gov.nwcg.isuite.core.vo.AdjustmentTypeVo;
import gov.nwcg.isuite.core.vo.AgencyGroupVo;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.AssignmentStatusVo;
import gov.nwcg.isuite.core.vo.ComplexityVo;
import gov.nwcg.isuite.core.vo.CountryCodeSubdivisionVo;
import gov.nwcg.isuite.core.vo.CountryCodeVo;
import gov.nwcg.isuite.core.vo.DailyFormVo;
import gov.nwcg.isuite.core.vo.DepartmentVo;
import gov.nwcg.isuite.core.vo.EventTypeVo;
import gov.nwcg.isuite.core.vo.FontVo;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.GroupCategoryVo;
import gov.nwcg.isuite.core.vo.JetPortVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.RateAreaVo;
import gov.nwcg.isuite.core.vo.RateClassRateVo;
import gov.nwcg.isuite.core.vo.RateClassVo;
import gov.nwcg.isuite.core.vo.RateGroupVo;
import gov.nwcg.isuite.core.vo.RateTypeVo;
import gov.nwcg.isuite.core.vo.RecommendationVo;
import gov.nwcg.isuite.core.vo.RegionCodeVo;
import gov.nwcg.isuite.core.vo.RequestCategoryVo;
import gov.nwcg.isuite.core.vo.SectionVo;
import gov.nwcg.isuite.core.vo.Sit209Vo;
import gov.nwcg.isuite.core.vo.SpecialPayVo;
import gov.nwcg.isuite.core.vo.SubGroupCategoryVo;
import gov.nwcg.isuite.core.vo.SystemParameterVo;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.SystemTypeVo;
import gov.nwcg.isuite.core.vo.TrainingSystemVo;
import gov.nwcg.isuite.core.vo.TravelMethodVo;
import gov.nwcg.isuite.core.vo.UnitOfMeasureVo;
import gov.nwcg.isuite.framework.types.AdjustmentTypeEnum;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.TravelMethodTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

public class StaticDataWrapperVo {
	private String serverDate = "";
	private String serverVersion="2.0.0 QC.8";
	private TrainingSystemVo trainingVo=null;
	private Collection<SystemRoleVo> systemRoleVos = new ArrayList<SystemRoleVo>();
	private Collection<AgencyVo> agencyVos = new ArrayList<AgencyVo>();
	private Collection<AgencyGroupVo> agencyGroupVos = new ArrayList<AgencyGroupVo>();
	private Collection<KindVo> kindVos = new ArrayList<KindVo>();
	private Collection<KindVo> quickStatKindVos = new ArrayList<KindVo>();
	private Collection<EventTypeVo> eventTypeVos = new ArrayList<EventTypeVo>();
	private Collection<OrganizationVo> organizationVos = new ArrayList<OrganizationVo>();
	private Collection<OrganizationVo> nonPDCOrgs = new ArrayList<OrganizationVo>();
	private Collection<OrganizationVo> pdcOrgs = new ArrayList<OrganizationVo>();
	private Collection<OrganizationVo> dispatchCenters = new ArrayList<OrganizationVo>();
	private Collection<JetPortVo> jetPortVos = new ArrayList<JetPortVo>();
	private Collection<CountryCodeSubdivisionVo> countryCodeSubdivisionVos = new ArrayList<CountryCodeSubdivisionVo>();
	private Collection<CountryCodeVo> countryCodeVos = new ArrayList<CountryCodeVo>();
	private Collection<Sit209Vo> sit209CodeVos = new ArrayList<Sit209Vo>();
	private Collection<RequestCategoryVo> requestCategoryVos = new ArrayList<RequestCategoryVo>();
	private Collection<RegionCodeVo> regionCodeVos = new ArrayList<RegionCodeVo>();
	private Collection<SpecialPayVo> specialPayVos = new ArrayList<SpecialPayVo>();
	private Collection<AdjustCategoryVo> adjustCategoryVos = new ArrayList<AdjustCategoryVo>();
	private Collection<RateClassVo> rateClassVos = new ArrayList<RateClassVo>();
	private Collection<RateAreaVo> rateAreaVos = new ArrayList<RateAreaVo>();
	private Collection<RateClassRateVo> rateClassRateVos = new ArrayList<RateClassRateVo>();
	private Collection<AccrualCodeVo> accrualCodeVos = new ArrayList<AccrualCodeVo>();
	private Collection<AdjustmentTypeVo> adjustmentTypeVos = new ArrayList<AdjustmentTypeVo>();
	private Collection<AssignmentStatusVo> assignmentStatuses = new ArrayList<AssignmentStatusVo>();
	private Collection<TravelMethodVo> travelMethods = new ArrayList<TravelMethodVo>();
	private Collection<RateTypeVo> rateTypes = new ArrayList<RateTypeVo>();
	private Collection<UnitOfMeasureVo> unitsOfMeasure = new ArrayList<UnitOfMeasureVo>();
	private Collection<SystemParameterVo> sysParamVos = new ArrayList<SystemParameterVo>();
	private Collection<DepartmentVo> departmentVos = new ArrayList<DepartmentVo>();
	private Collection<DailyFormVo> dailyFormVos = new ArrayList<DailyFormVo>();
	private Collection<GroupCategoryVo> groupCategoryVos = new ArrayList<GroupCategoryVo>();
	private Collection<SubGroupCategoryVo> subGroupCategoryVos = new ArrayList<SubGroupCategoryVo>();
	private Collection<RateGroupVo> rateGroupVos = new ArrayList<RateGroupVo>();
	private Collection<SystemTypeVo> systemTypeVos = new ArrayList<SystemTypeVo>();
	private Collection<FontVo> fontVos = new ArrayList<FontVo>();
	private Collection<SectionVo> sectionVos = new ArrayList<SectionVo>();
	private Collection<ComplexityVo> complexityVos = new ArrayList<ComplexityVo>();
	private Collection<RecommendationVo> recommendationVos = new ArrayList<RecommendationVo>();
	
	public StaticDataWrapperVo() {
	}

	public static StaticDataWrapperVo getInstance(GlobalCacheVo gcvo){
		StaticDataWrapperVo vo = new StaticDataWrapperVo();
		//vo.setServerDate(DateUtil.toDateString(Calendar.getInstance().getTime(),DateUtil.MM_SLASH_DD_SLASH_YYYY) + " (Patch 3c to 1.1.0 Applied)");
		vo.setServerDate(DateUtil.toDateString(Calendar.getInstance().getTime(),DateUtil.MM_SLASH_DD_SLASH_YYYY) + "");
		//vo.setServerDate(DateUtil.toDateString(Calendar.getInstance().getTime(),DateUtil.MM_SLASH_DD_SLASH_YYYY) + " (2017 AD Rates Applied)");
		//vo.setServerDate(DateUtil.toDateString(Calendar.getInstance().getTime(),DateUtil.MM_SLASH_DD_SLASH_YYYY));
		vo.setAccrualCodeVos(gcvo.getAccrualCodeVos());
		vo.setAdjustCategoryVos(gcvo.getAdjustCategoryVos());
		vo.setAgencyVos(gcvo.getAgencyVos());
		vo.setCountryCodeSubdivisionVos(gcvo.getCountryCodeSubdivisionVos());
		vo.setCountryCodeVos(gcvo.getCountryCodeVos());
		vo.setEventTypeVos(gcvo.getEventTypeVos());
		vo.setJetPortVos(gcvo.getJetPortVos());
		vo.setKindVos(gcvo.getKindVos());
		for(KindVo kv : gcvo.getKindVos()){
			int idx="HC1 HC2 HCU HCS1 HCS2 HCSU".indexOf(kv.getCode());
			if(idx<0)
				vo.getQuickStatKindVos().add(kv);
		}
		vo.setNonPDCOrgs(gcvo.getNonPDCOrgs());
		//System.out.println(gcvo.getNonPDCOrgs().size());
		vo.setOrganizationVos(gcvo.getOrganizationVos());
		//System.out.println(gcvo.getOrganizationVos().size());
		
		for(OrganizationVo org : vo.getOrganizationVos()){
			if(BooleanUtility.isTrue(org.getDispatchCenter())){
				vo.getDispatchCenters().add(org);
			}
		}
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
		return vo;
	}
	
	public Collection<SystemRoleVo> getSystemRoleVos() {
		return systemRoleVos;
	}

	public void setSystemRoleVos(Collection<SystemRoleVo> systemRoleVos) {
		this.systemRoleVos = systemRoleVos;
	}

	public Collection<AgencyVo> getAgencyVos() {
		return agencyVos;
	}

	public void setAgencyVos(Collection<AgencyVo> agencyVos) {
		this.agencyVos = agencyVos;
	}

	public Collection<KindVo> getKindVos() {
		return kindVos;
	}

	public void setKindVos(Collection<KindVo> kindVos) {
		this.kindVos = kindVos;
	}

	public Collection<EventTypeVo> getEventTypeVos() {
		return eventTypeVos;
	}

	public void setEventTypeVos(Collection<EventTypeVo> eventTypeVos) {
		this.eventTypeVos = eventTypeVos;
	}

	public Collection<OrganizationVo> getOrganizationVos() {
		return organizationVos;
	}

	public void setOrganizationVos(Collection<OrganizationVo> organizationVos) {
		this.organizationVos = organizationVos;
	}

	public Collection<OrganizationVo> getNonPDCOrgs() {
		return nonPDCOrgs;
	}

	public void setNonPDCOrgs(Collection<OrganizationVo> nonPDCOrgs) {
		this.nonPDCOrgs = nonPDCOrgs;
	}

	public Collection<JetPortVo> getJetPortVos() {
		return jetPortVos;
	}

	public void setJetPortVos(Collection<JetPortVo> jetPortVos) {
		this.jetPortVos = jetPortVos;
	}

	public Collection<CountryCodeSubdivisionVo> getCountryCodeSubdivisionVos() {
		return countryCodeSubdivisionVos;
	}

	public void setCountryCodeSubdivisionVos(
			Collection<CountryCodeSubdivisionVo> countryCodeSubdivisionVos) {
		this.countryCodeSubdivisionVos = countryCodeSubdivisionVos;
	}

	public Collection<CountryCodeVo> getCountryCodeVos() {
		return countryCodeVos;
	}

	public void setCountryCodeVos(Collection<CountryCodeVo> countryCodeVos) {
		this.countryCodeVos = countryCodeVos;
	}

	public Collection<Sit209Vo> getSit209CodeVos() {
		return sit209CodeVos;
	}

	public void setSit209CodeVos(Collection<Sit209Vo> sit209CodeVos) {
		this.sit209CodeVos = sit209CodeVos;
	}

	public Collection<RequestCategoryVo> getRequestCategoryVos() {
		return requestCategoryVos;
	}

	public void setRequestCategoryVos(
			Collection<RequestCategoryVo> requestCategoryVos) {
		this.requestCategoryVos = requestCategoryVos;
	}

	public Collection<RegionCodeVo> getRegionCodeVos() {
		return regionCodeVos;
	}

	public void setRegionCodeVos(Collection<RegionCodeVo> regionCodeVos) {
		this.regionCodeVos = regionCodeVos;
	}

	public Collection<SpecialPayVo> getSpecialPayVos() {
		return specialPayVos;
	}

	public void setSpecialPayVos(Collection<SpecialPayVo> specialPayVos) {
		this.specialPayVos = specialPayVos;
	}

	public Collection<AdjustCategoryVo> getAdjustCategoryVos() {
		return adjustCategoryVos;
	}

	public void setAdjustCategoryVos(Collection<AdjustCategoryVo> adjustCategoryVos) {
		this.adjustCategoryVos = adjustCategoryVos;
	}

	public Collection<RateClassVo> getRateClassVos() {
		return rateClassVos;
	}

	public void setRateClassVos(Collection<RateClassVo> rateClassVos) {
		this.rateClassVos = rateClassVos;
	}

	public Collection<RateAreaVo> getRateAreaVos() {
		return rateAreaVos;
	}

	public void setRateAreaVos(Collection<RateAreaVo> rateAreaVos) {
		this.rateAreaVos = rateAreaVos;
	}

	public Collection<AccrualCodeVo> getAccrualCodeVos() {
		return accrualCodeVos;
	}

	public void setAccrualCodeVos(Collection<AccrualCodeVo> accrualCodeVos) {
		this.accrualCodeVos = accrualCodeVos;
	}

	public Collection<AdjustmentTypeVo> getAdjustmentTypeVos() {
		return adjustmentTypeVos;
	}

	public void setAdjustmentTypeVos(Collection<AdjustmentTypeVo> adjustmentTypeVos) {
		this.adjustmentTypeVos = adjustmentTypeVos;
	}

	public String getServerDate() {
		return serverDate;
	}

	public void setServerDate(String serverDate) {
		this.serverDate = serverDate;
	}

	public Collection<AssignmentStatusVo> getAssignmentStatuses() {
		return assignmentStatuses;
	}

	public void setAssignmentStatuses(
			Collection<AssignmentStatusVo> assignmentStatuses) {
		this.assignmentStatuses = assignmentStatuses;
	}

	public Collection<TravelMethodVo> getTravelMethods() {
		return travelMethods;
	}

	public void setTravelMethods(Collection<TravelMethodVo> travelMethods) {
		this.travelMethods = travelMethods;
	}

	public Collection<RateClassRateVo> getRateClassRateVos() {
		return rateClassRateVos;
	}

	public void setRateClassRateVos(Collection<RateClassRateVo> rateClassRateVos) {
		this.rateClassRateVos = rateClassRateVos;
	}

	public Collection<RateTypeVo> getRateTypes() {
		return rateTypes;
	}

	public void setRateTypes(Collection<RateTypeVo> rateTypes) {
		this.rateTypes = rateTypes;
	}

	public Collection<UnitOfMeasureVo> getUnitsOfMeasure() {
		return unitsOfMeasure;
	}

	public void setUnitsOfMeasure(Collection<UnitOfMeasureVo> unitsOfMeasure) {
		this.unitsOfMeasure = unitsOfMeasure;
	}

	public Collection<SystemParameterVo> getSysParamVos() {
		return sysParamVos;
	}

	public void setSysParamVos(Collection<SystemParameterVo> sysParamVos) {
		this.sysParamVos = sysParamVos;
	}

	public Collection<AgencyGroupVo> getAgencyGroupVos() {
		return agencyGroupVos;
	}

	public void setAgencyGroupVos(Collection<AgencyGroupVo> agencyGroupVos) {
		this.agencyGroupVos = agencyGroupVos;
	}

	/**
	 * @param departmentVos the departmentVos to set
	 */
	public void setDepartmentVos(Collection<DepartmentVo> departmentVos) {
		this.departmentVos = departmentVos;
	}

	/**
	 * @return the departmentVos
	 */
	public Collection<DepartmentVo> getDepartmentVos() {
		return departmentVos;
	}

	/**
	 * @param dailyFormVos the dailyFormVos to set
	 */
	public void setDailyFormVos(Collection<DailyFormVo> dailyFormVos) {
		this.dailyFormVos = dailyFormVos;
	}

	/**
	 * @return the dailyFormVos
	 */
	public Collection<DailyFormVo> getDailyFormVos() {
		return dailyFormVos;
	}

	/**
	 * @param groupCategoryVos
	 */
	public void setGroupCategoryVos(Collection<GroupCategoryVo> groupCategoryVos) {
		this.groupCategoryVos = groupCategoryVos;
	}
	
	/**
	 * @return the groupCategoryVos
	 */
	public Collection<GroupCategoryVo> getGroupCategoryVos() {
		return groupCategoryVos;
	}
	
	/**
	 * @param subGroupCategoryVos the subGroupCategoryVos to set
	 */
	public void setSubGroupCategoryVos(Collection<SubGroupCategoryVo> subGroupCategoryVos) {
		this.subGroupCategoryVos = subGroupCategoryVos;
	}

	/**
	 * @return the subGroupCategoryVos
	 */
	public Collection<SubGroupCategoryVo> getSubGroupCategoryVos() {
		return subGroupCategoryVos;
	}

	/**
	 * @param rateGroupVos the rateGroupVos to set
	 */
	public void setRateGroupVos(Collection<RateGroupVo> rateGroupVos) {
		this.rateGroupVos = rateGroupVos;
	}

	/**
	 * @return the rateGroupVos
	 */
	public Collection<RateGroupVo> getRateGroupVos() {
		return rateGroupVos;
	}

	/**
	 * @param systemTypeVos the systemTypeVos to set
	 */
	public void setSystemTypeVos(Collection<SystemTypeVo> systemTypeVos) {
		this.systemTypeVos = systemTypeVos;
	}

	/**
	 * @return the systemTypeVos
	 */
	public Collection<SystemTypeVo> getSystemTypeVos() {
		return systemTypeVos;
	}
	
	/**
	 * @param fontVos the fontVos to set
	 */
	public void setFontVos(Collection<FontVo> fontVos) {
		this.fontVos = fontVos;
	}

	/**
	 * @return the fontVos
	 */
	public Collection<FontVo> getFontVos() {
		return fontVos;
	}

	/**
	 * @param sectionVos the sectionVos to set
	 */
	public void setSectionVos(Collection<SectionVo> sectionVos) {
		this.sectionVos = sectionVos;
	}

	/**
	 * @return the sectionVos
	 */
	public Collection<SectionVo> getSectionVos() {
		return sectionVos;
	}

	/**
	 * @return the pdcOrgs
	 */
	public Collection<OrganizationVo> getPdcOrgs() {
		return pdcOrgs;
	}

	/**
	 * @param pdcOrgs the pdcOrgs to set
	 */
	public void setPdcOrgs(Collection<OrganizationVo> pdcOrgs) {
		this.pdcOrgs = pdcOrgs;
	}

	/**
	 * @return the trainingVo
	 */
	public TrainingSystemVo getTrainingVo() {
		return trainingVo;
	}

	/**
	 * @param trainingVo the trainingVo to set
	 */
	public void setTrainingVo(TrainingSystemVo trainingVo) {
		this.trainingVo = trainingVo;
	}

	public String getServerVersion() {
		return serverVersion;
	}

	public void setServerVersion(String serverVersion) {
		this.serverVersion = serverVersion;
	}

	/**
	 * @param complexityVos the complexityVos to set
	 */
	public void setComplexityVos(Collection<ComplexityVo> complexityVos) {
		this.complexityVos = complexityVos;
	}

	/**
	 * @return the complexityVos
	 */
	public Collection<ComplexityVo> getComplexityVos() {
		return complexityVos;
	}

	/**
	 * @param recommendationVos the recommendationVos to set
	 */
	public void setRecommendationVos(Collection<RecommendationVo> recommendationVos) {
		this.recommendationVos = recommendationVos;
	}

	/**
	 * @return the recommendationVos
	 */
	public Collection<RecommendationVo> getRecommendationVos() {
		return recommendationVos;
	}

	public Collection<OrganizationVo> getDispatchCenters() {
		return dispatchCenters;
	}

	public void setDispatchCenters(Collection<OrganizationVo> dispatchCenters) {
		this.dispatchCenters = dispatchCenters;
	}

	public Collection<KindVo> getQuickStatKindVos() {
		return quickStatKindVos;
	}

	public void setQuickStatKindVos(Collection<KindVo> quickStatKindVos) {
		this.quickStatKindVos = quickStatKindVos;
	}	



}
