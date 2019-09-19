package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class GlobalCacheVo {
	private Boolean loaded=false;
	private SystemVo systemVo = null;
	private Collection<String> dataSourceConnectionsUsed=new ArrayList<String>();
	private Collection<SystemRoleVo> systemRoleVos = new ArrayList<SystemRoleVo>();
	private Collection<AgencyVo> agencyVos = new ArrayList<AgencyVo>();
	private Collection<AgencyGroupVo> agencyGroupVos = new ArrayList<AgencyGroupVo>();
	private Collection<KindVo> kindVos = new ArrayList<KindVo>();
	private Collection<EventTypeVo> eventTypeVos = new ArrayList<EventTypeVo>();
	private Collection<OrganizationVo> organizationVos = new ArrayList<OrganizationVo>();
	private Collection<OrganizationVo> nonPDCOrgs = new ArrayList<OrganizationVo>();
	private Collection<OrganizationVo> pdcVos = new ArrayList<OrganizationVo>();
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
	private Collection<AccrualCodeVo> accrualCodeVos = new ArrayList<AccrualCodeVo>();
	private Collection<RateClassRateVo> rateClassRateVos = new ArrayList<RateClassRateVo>();
	private Collection<DepartmentVo> departmentVos = new ArrayList<DepartmentVo>();
	private Collection<DailyFormVo> dailyFormVos = new ArrayList<DailyFormVo>();
	private Collection<GroupCategoryVo> groupCategoryVos = new ArrayList<GroupCategoryVo>();
	private Collection<SubGroupCategoryVo> subGroupCategoryVos = new ArrayList<SubGroupCategoryVo>();
	private Collection<RateGroupVo> rateGroupVos = new ArrayList<RateGroupVo>();
	private Collection<FontVo> fontVos = new ArrayList<FontVo>();
	private Collection<ComplexityVo> complexityVos = new ArrayList<ComplexityVo>();
	private Collection<RecommendationVo> recommendationVos = new ArrayList<RecommendationVo>();
	
	public GlobalCacheVo(){

	}

	/**
	 * Returns the loaded.
	 *
	 * @return 
	 *		the loaded to return
	 */
	public Boolean getLoaded() {
		return loaded;
	}

	/**
	 * Returns the loaded.
	 *
	 * @return 
	 *		the loaded to return
	 */
	@JsonIgnore
	public Boolean isLoaded() {
		return loaded;
	}

	/**
	 * Sets the loaded.
	 *
	 * @param loaded 
	 *			the loaded to set
	 */
	public void setLoaded(Boolean loaded) {
		this.loaded = loaded;
	}

	/**
	 * Returns the jetPortVos.
	 *
	 * @return 
	 *		the jetPortVos to return
	 */
	public Collection<JetPortVo> getJetPortVos() {
		return jetPortVos;
	}

	/**
	 * Sets the jetPortVos.
	 *
	 * @param jetPortVos 
	 *			the jetPortVos to set
	 */
	public void setJetPortVos(Collection<JetPortVo> jetPortVos) {
		this.jetPortVos = jetPortVos;
	}

	/**
	 * Returns the agencyVos.
	 *
	 * @return 
	 *		the agencyVos to return
	 */
	public Collection<AgencyVo> getAgencyVos() {
		return agencyVos;
	}

	/**
	 * Sets the agencyVos.
	 *
	 * @param agencyVos 
	 *			the agencyVos to set
	 */
	public void setAgencyVos(Collection<AgencyVo> agencyVos) {
		this.agencyVos = agencyVos;
	}

	/**
	 * Returns the kindVos.
	 *
	 * @return 
	 *		the kindVos to return
	 */
	public Collection<KindVo> getKindVos() {
		return kindVos;
	}

	/**
	 * Sets the kindVos.
	 *
	 * @param kindVos 
	 *			the kindVos to set
	 */
	public void setKindVos(Collection<KindVo> kindVos) {
		this.kindVos = kindVos;
	}

	/**
	 * Returns the eventTypeVos.
	 *
	 * @return 
	 *		the eventTypeVos to return
	 */
	public Collection<EventTypeVo> getEventTypeVos() {
		return eventTypeVos;
	}

	/**
	 * Sets the eventTypeVos.
	 *
	 * @param eventTypeVos 
	 *			the eventTypeVos to set
	 */
	public void setEventTypeVos(Collection<EventTypeVo> eventTypeVos) {
		this.eventTypeVos = eventTypeVos;
	}

	/**
	 * Returns the organizationVos.
	 *
	 * @return 
	 *		the organizationVos to return
	 */
	public Collection<OrganizationVo> getOrganizationVos() {
		return organizationVos;
	}

	/**
	 * Sets the organizationVos.
	 *
	 * @param organizationVos 
	 *			the organizationVos to set
	 */
	public void setOrganizationVos(Collection<OrganizationVo> organizationVos) {
		this.organizationVos = organizationVos;
	}

	/**
	 * Returns the countryCodeSubdivisionVos.
	 *
	 * @return 
	 *		the countryCodeSubdivisionVos to return
	 */
	public Collection<CountryCodeSubdivisionVo> getCountryCodeSubdivisionVos() {
		return countryCodeSubdivisionVos;
	}

	/**
	 * Sets the countryCodeSubdivisionVos.
	 *
	 * @param countryCodeSubdivisionVos 
	 *			the countryCodeSubdivisionVos to set
	 */
	public void setCountryCodeSubdivisionVos(
			Collection<CountryCodeSubdivisionVo> countryCodeSubdivisionVos) {
		this.countryCodeSubdivisionVos = countryCodeSubdivisionVos;
	}

	/**
	 * Returns the countryCodeVos.
	 *
	 * @return 
	 *		the countryCodeVos to return
	 */
	public Collection<CountryCodeVo> getCountryCodeVos() {
		return countryCodeVos;
	}

	/**
	 * Sets the countryCodeVos.
	 *
	 * @param countryCodeVos 
	 *			the countryCodeVos to set
	 */
	public void setCountryCodeVos(Collection<CountryCodeVo> countryCodeVos) {
		this.countryCodeVos = countryCodeVos;
	}

	/**
	 * Returns the sit209CodeVos.
	 *
	 * @return 
	 *		the sit209CodeVos to return
	 */
	public Collection<Sit209Vo> getSit209CodeVos() {
		return sit209CodeVos;
	}

	/**
	 * Sets the sit209CodeVos.
	 *
	 * @param sit209CodeVos 
	 *			the sit209CodeVos to set
	 */
	public void setSit209CodeVos(Collection<Sit209Vo> sit209CodeVos) {
		this.sit209CodeVos = sit209CodeVos;
	}

	/**
	 * Returns the requestCategoryVos.
	 *
	 * @return 
	 *		the requestCategoryVos to return
	 */
	public Collection<RequestCategoryVo> getRequestCategoryVos() {
		return requestCategoryVos;
	}

	/**
	 * Sets the requestCategoryVos.
	 *
	 * @param requestCategoryVos 
	 *			the requestCategoryVos to set
	 */
	public void setRequestCategoryVos(
			Collection<RequestCategoryVo> requestCategoryVos) {
		this.requestCategoryVos = requestCategoryVos;
	}

	/**
	 * Returns the systemRoleVos.
	 *
	 * @return 
	 *		the systemRoleVos to return
	 */
	public Collection<SystemRoleVo> getSystemRoleVos() {
		return systemRoleVos;
	}

	/**
	 * Sets the systemRoleVos.
	 *
	 * @param systemRoleVos 
	 *			the systemRoleVos to set
	 */
	public void setSystemRoleVos(Collection<SystemRoleVo> systemRoleVos) {
		this.systemRoleVos = systemRoleVos;
	}

	/**
	 * Returns the systemVo.
	 *
	 * @return 
	 *		the systemVo to return
	 */
	public SystemVo getSystemVo() {
		return systemVo;
	}

	/**
	 * Sets the systemVo.
	 *
	 * @param systemVo 
	 *			the systemVo to set
	 */
	public void setSystemVo(SystemVo systemVo) {
		this.systemVo = systemVo;
	}

	/**
	 * @return the regionCodeVos
	 */
	public Collection<RegionCodeVo> getRegionCodeVos() {
		return regionCodeVos;
	}

	/**
	 * @param regionCodeVos the regionCodeVos to set
	 */
	public void setRegionCodeVos(Collection<RegionCodeVo> regionCodeVos) {
		this.regionCodeVos = regionCodeVos;
	}

	/**
	 * Returns the nonPDCOrgs.
	 *
	 * @return 
	 *		the nonPDCOrgs to return
	 */
	public Collection<OrganizationVo> getNonPDCOrgs() {
		return nonPDCOrgs;
	}

	/**
	 * Sets the nonPDCOrgs.
	 *
	 * @param nonPDCOrgs 
	 *			the nonPDCOrgs to set
	 */
	public void setNonPDCOrgs(Collection<OrganizationVo> nonPDCOrgs) {
		this.nonPDCOrgs = nonPDCOrgs;
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

	/**
	 * @return the accrualCodeVos
	 */
	public Collection<AccrualCodeVo> getAccrualCodeVos() {
		return accrualCodeVos;
	}

	/**
	 * @param accrualCodeVos the accrualCodeVos to set
	 */
	public void setAccrualCodeVos(Collection<AccrualCodeVo> accrualCodeVos) {
		this.accrualCodeVos = accrualCodeVos;
	}

	public Collection<RateClassRateVo> getRateClassRateVos() {
		return rateClassRateVos;
	}

	public void setRateClassRateVos(Collection<RateClassRateVo> rateClassRateVos) {
		this.rateClassRateVos = rateClassRateVos;
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
	 * @param groupCategoryVos the groupCategoryVos to set
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
	 * @return
	 */
	public Collection<OrganizationVo> getPdcVos() {
		return pdcVos;
	}

	/**
	 * 
	 * @param pdcVos
	 */
	public void setPdcVos(Collection<OrganizationVo> pdcVos) {
		this.pdcVos = pdcVos;
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
	 * @return the dataSourceConnectionsUsed
	 */
	public Collection<String> getDataSourceConnectionsUsed() {
		return dataSourceConnectionsUsed;
	}

	/**
	 * @param dataSourceConnectionsUsed the dataSourceConnectionsUsed to set
	 */
	public void setDataSourceConnectionsUsed(
			Collection<String> dataSourceConnectionsUsed) {
		this.dataSourceConnectionsUsed = dataSourceConnectionsUsed;
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

}
