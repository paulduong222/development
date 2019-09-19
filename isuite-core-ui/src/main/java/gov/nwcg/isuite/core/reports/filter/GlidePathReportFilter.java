package gov.nwcg.isuite.core.reports.filter;

import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.ReportSectionCategorySelectVo;

import java.util.ArrayList;
import java.util.Collection;

public class GlidePathReportFilter {
	private Long incidentId;
	private Long incidentGroupId;
	private String incidentTag;
	private String incidentNumber;
	private String incidentName;
	
	private Integer numberOfDays=0;
	
	private Boolean optionAll=false;
	private Boolean optionSections=false;
	private Boolean optionSummaryOnly=false;
	private Collection<ReportSectionCategorySelectVo> sectionCategories = new ArrayList<ReportSectionCategorySelectVo>();
	
	private Boolean optionIncludeSTComponents=false;
	
	private Boolean sortByItemCodeDemobDate=false;			// Default
	private Boolean sortByItemCodeRequestNumber=false;

	private Boolean sortByDemobDateRequestNumber=false;		// Default
	private Boolean sortByRequestNumberDemobDate=false;
	
	private Boolean sortByDemobDateResourceName=false;		// Default
	
	private Boolean sortByDemobDateRequestNumber2=false;
	private Boolean sortByDemobDateResourceName2=false;
	private Boolean sortByRequestNumberDemobDate2=false;
	
	private DateTransferVo startDateVo = new DateTransferVo();
	
	public GlidePathReportFilter() {
	}


	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}


	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}


	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}


	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}


	/**
	 * @return the numberOfDays
	 */
	public Integer getNumberOfDays() {
		return numberOfDays;
	}


	/**
	 * @param numberOfDays the numberOfDays to set
	 */
	public void setNumberOfDays(Integer numberOfDays) {
		this.numberOfDays = numberOfDays;
	}


	/**
	 * @return the optionAll
	 */
	public Boolean getOptionAll() {
		return optionAll;
	}


	/**
	 * @param optionAll the optionAll to set
	 */
	public void setOptionAll(Boolean optionAll) {
		this.optionAll = optionAll;
	}


	/**
	 * @return the optionSections
	 */
	public Boolean getOptionSections() {
		return optionSections;
	}


	/**
	 * @param optionSections the optionSections to set
	 */
	public void setOptionSections(Boolean optionSections) {
		this.optionSections = optionSections;
	}


	/**
	 * @return the optionSummaryOnly
	 */
	public Boolean getOptionSummaryOnly() {
		return optionSummaryOnly;
	}


	/**
	 * @param optionSummaryOnly the optionSummaryOnly to set
	 */
	public void setOptionSummaryOnly(Boolean optionSummaryOnly) {
		this.optionSummaryOnly = optionSummaryOnly;
	}

	/**
	 * @return the startDateVo
	 */
	public DateTransferVo getStartDateVo() {
		return startDateVo;
	}


	/**
	 * @param startDateVo the startDateVo to set
	 */
	public void setStartDateVo(DateTransferVo startDateVo) {
		this.startDateVo = startDateVo;
	}


	/**
	 * @return the sectionCategories
	 */
	public Collection<ReportSectionCategorySelectVo> getSectionCategories() {
		return sectionCategories;
	}


	/**
	 * @param sectionCategories the sectionCategories to set
	 */
	public void setSectionCategories(
			Collection<ReportSectionCategorySelectVo> sectionCategories) {
		this.sectionCategories = sectionCategories;
	}


	/**
	 * @return the optionIncludeSTComponents
	 */
	public Boolean getOptionIncludeSTComponents() {
		return optionIncludeSTComponents;
	}


	/**
	 * @param optionIncludeSTComponents the optionIncludeSTComponents to set
	 */
	public void setOptionIncludeSTComponents(Boolean optionIncludeSTComponents) {
		this.optionIncludeSTComponents = optionIncludeSTComponents;
	}


	/**
	 * @return the sortByItemCodeDemobDate
	 */
	public Boolean getSortByItemCodeDemobDate() {
		return sortByItemCodeDemobDate;
	}


	/**
	 * @param sortByItemCodeDemobDate the sortByItemCodeDemobDate to set
	 */
	public void setSortByItemCodeDemobDate(Boolean sortByItemCodeDemobDate) {
		this.sortByItemCodeDemobDate = sortByItemCodeDemobDate;
	}


	/**
	 * @return the sortByItemCodeRequestNumber
	 */
	public Boolean getSortByItemCodeRequestNumber() {
		return sortByItemCodeRequestNumber;
	}


	/**
	 * @param sortByItemCodeRequestNumber the sortByItemCodeRequestNumber to set
	 */
	public void setSortByItemCodeRequestNumber(Boolean sortByItemCodeRequestNumber) {
		this.sortByItemCodeRequestNumber = sortByItemCodeRequestNumber;
	}


	/**
	 * @return the sortByDemobDateRequestNumber
	 */
	public Boolean getSortByDemobDateRequestNumber() {
		return sortByDemobDateRequestNumber;
	}


	/**
	 * @param sortByDemobDateRequestNumber the sortByDemobDateRequestNumber to set
	 */
	public void setSortByDemobDateRequestNumber(Boolean sortByDemobDateRequestNumber) {
		this.sortByDemobDateRequestNumber = sortByDemobDateRequestNumber;
	}


	/**
	 * @return the sortByRequestNumberDemobDate
	 */
	public Boolean getSortByRequestNumberDemobDate() {
		return sortByRequestNumberDemobDate;
	}


	/**
	 * @param sortByRequestNumberDemobDate the sortByRequestNumberDemobDate to set
	 */
	public void setSortByRequestNumberDemobDate(Boolean sortByRequestNumberDemobDate) {
		this.sortByRequestNumberDemobDate = sortByRequestNumberDemobDate;
	}


	/**
	 * @return the incidentTag
	 */
	public String getIncidentTag() {
		return incidentTag;
	}


	/**
	 * @param incidentTag the incidentTag to set
	 */
	public void setIncidentTag(String incidentTag) {
		this.incidentTag = incidentTag;
	}


	/**
	 * @return the incidentNumber
	 */
	public String getIncidentNumber() {
		return incidentNumber;
	}


	/**
	 * @param incidentNumber the incidentNumber to set
	 */
	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}


	/**
	 * @return the incidentName
	 */
	public String getIncidentName() {
		return incidentName;
	}


	/**
	 * @param incidentName the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}


	public Boolean getSortByDemobDateResourceName() {
		return sortByDemobDateResourceName;
	}


	public void setSortByDemobDateResourceName(Boolean sortByDemobDateResourceName) {
		this.sortByDemobDateResourceName = sortByDemobDateResourceName;
	}


	public Boolean getSortByDemobDateRequestNumber2() {
		return sortByDemobDateRequestNumber2;
	}


	public void setSortByDemobDateRequestNumber2(
			Boolean sortByDemobDateRequestNumber2) {
		this.sortByDemobDateRequestNumber2 = sortByDemobDateRequestNumber2;
	}


	public Boolean getSortByDemobDateResourceName2() {
		return sortByDemobDateResourceName2;
	}


	public void setSortByDemobDateResourceName2(Boolean sortByDemobDateResourceName2) {
		this.sortByDemobDateResourceName2 = sortByDemobDateResourceName2;
	}


	public Boolean getSortByRequestNumberDemobDate2() {
		return sortByRequestNumberDemobDate2;
	}


	public void setSortByRequestNumberDemobDate2(
			Boolean sortByRequestNumberDemobDate2) {
		this.sortByRequestNumberDemobDate2 = sortByRequestNumberDemobDate2;
	}

}
