package gov.nwcg.isuite.core.reports.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class LastWorkDayReportFilter {
	public static final int LAST_WORK_DAY_DATE_RESOURCE_CATEGORY_REPORT=0;
	public static final int LAST_WORK_DAY_SECTION_DATE_REPORT=1;
	public static final int LAST_WORK_DAY_SECTION_RESOURCE_CATEGORY_DATE_REPORT=2;
	
	private Long incidentId = null;
	private Long incidentGroupId=null;
	private Boolean includeSTTFComponents = false;
	private Boolean includeAllDates = false;
	private Date reportStartDate = null;
	private Date reportEndDate = null;
	private Collection<String> sectionsToInclude = null;
	private Collection<String> sortBy = new ArrayList<String>();
	private Integer grouping = null;
	
	private Boolean groupByDateResourceCategory=false;
	private Boolean groupBySectionDate=false;
	private Boolean groupBySectionResourceCategoryDate=false;
	
	private Boolean allSections=true;
	private Boolean command=false;
	private Boolean externalResources=false;
	private Boolean finance=false;
	private Boolean logistics=false;
	private Boolean operations=false;
	private Boolean plans=false;

	
	/**
	 * Sets the incidentId
	 * 
	 * @param incidentId
	 * 		the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}
	
	/**
	 * Returns the incidentId
	 * 
	 * @return
	 * 		the incidentId to return
	 */
	public Long getIncidentId() {
		return incidentId;
	}
	
	/**
	 * Sets include Strike Team Task Force Components
	 * 
	 * @param includeSTTFComponents
	 * 		the includeSTTFComponents to set
	 */
	public void setIncludeSTTFComponents(Boolean includeSTTFComponents) {
		this.includeSTTFComponents = includeSTTFComponents;
	}

	/**
	 * Returns include Strike Team Task Force Components
	 * 
	 * @return
	 * 		the includeSTTFComponent to return
	 */
	public Boolean getIncludeSTTFComponents() {
		return includeSTTFComponents;
	}

	/**
	 * Sets the reportStartDate
	 * 
	 * @param reportStartDate
	 * 		the reportStartDate to set
	 */
	public void setReportStartDate(Date reportStartDate) {
		this.reportStartDate = reportStartDate;
	}

	/**
	 * Returns the reportStartDate
	 * 
	 * @return
	 * 		the reportStartDate to return
	 */
	public Date getReportStartDate() {
		return reportStartDate;
	}

	/**
	 * Sets the reportEndDate
	 * 
	 * @param reportEndDate
	 * 		the reportEndDate to set
	 */
	public void setReportEndDate(Date reportEndDate) {
		this.reportEndDate = reportEndDate;
	}

	/**
	 * Returns the reportEndDate
	 * 
	 * @return
	 * 		the reportEndDate to return
	 */
	public Date getReportEndDate() {
		return reportEndDate;
	}

	/**
	 * Sets the sectionsToInclude
	 * 
	 * @param sectionsToInclude
	 * 		the sectionsToInclude to set
	 */
	public void setSectionsToInclude(Collection<String> sectionsToInclude) {
		this.sectionsToInclude = sectionsToInclude;
	}

	/**
	 * Returns the sectionsToInclude
	 * 
	 * @return
	 * 		the sectionsToInclude to return
	 */
	public Collection<String> getSectionsToInclude() {
		return sectionsToInclude;
	}

	public static String getSections(LastWorkDayReportFilter filter){
		String rtn="";
		
		if(filter.allSections){
			rtn="'C','E','F','L','O','P'";
		}else{
			if(filter.command){
				rtn=rtn + (rtn.length() < 1 ? "'C'" : ",'C'" );
			}
	
			if(filter.externalResources){
				rtn=rtn + (rtn.length() < 1 ? "'E'" : ",'E'" );
			}
			
			if(filter.finance){
				rtn=rtn + (rtn.length() < 1 ? "'F'" : ",'F'" );
			}
			
			if(filter.logistics){
				rtn=rtn + (rtn.length() < 1 ? "'L'" : ",'L'" );
			}
			
			if(filter.operations){
				rtn=rtn + (rtn.length() < 1 ? "'O'" : ",'O'" );
			}
			
			if(filter.plans){
				rtn=rtn + (rtn.length() < 1 ? "'P'" : ",'P'" );
			}
		}
		
		return rtn;
	}
	
	/**
	 * Sets the sortBy
	 * 
	 * @param sortBy
	 * 		the sortBy to set
	 */
	public void setSortBy(Collection<String> sortBy) {
		this.sortBy = sortBy;
	}

	/**
	 * Returns the sortBy
	 * 
	 * @return
	 * 		the sortBy to return
	 */
	public Collection<String> getSortBy() {
		return sortBy;
	}

	/**
	 * Sets the grouping
	 * 
	 * @param grouping
	 * 		the grouping to set
	 */
	public void setGrouping(Integer grouping) {
		this.grouping = grouping;
	}

	/**
	 * Returns the grouping
	 * 
	 * @return
	 * 		the grouping to return
	 */
	public Integer getGrouping() {
		if(this.groupByDateResourceCategory)
			return LAST_WORK_DAY_DATE_RESOURCE_CATEGORY_REPORT;
		if(this.groupBySectionDate)
			return LAST_WORK_DAY_SECTION_DATE_REPORT;
		if(this.groupBySectionResourceCategoryDate)
			return LAST_WORK_DAY_SECTION_RESOURCE_CATEGORY_DATE_REPORT;
		return 0;
	}

	/**
	 * Sets include all dates
	 * 
	 * @param includeAllDates
	 * 		includes all dates to set
	 */
	public void setIncludeAllDates(Boolean includeAllDates) {
		this.includeAllDates = includeAllDates;
	}

	/**
	 * Returns include all dates
	 * 
	 * @return
	 * 		include all dates to return
	 */
	public Boolean getIncludeAllDates() {
		return includeAllDates;
	}

	public Boolean getGroupByDateResourceCategory() {
		return groupByDateResourceCategory;
	}

	public void setGroupByDateResourceCategory(Boolean groupByDateResourceCategory) {
		this.groupByDateResourceCategory = groupByDateResourceCategory;
	}

	public Boolean getGroupBySectionDate() {
		return groupBySectionDate;
	}

	public void setGroupBySectionDate(Boolean groupBySectionDate) {
		this.groupBySectionDate = groupBySectionDate;
	}

	public Boolean getGroupBySectionResourceCategoryDate() {
		return groupBySectionResourceCategoryDate;
	}

	public void setGroupBySectionResourceCategoryDate(
			Boolean groupBySectionResourceCategoryDate) {
		this.groupBySectionResourceCategoryDate = groupBySectionResourceCategoryDate;
	}

	public Boolean getCommand() {
		return command;
	}

	public void setCommand(Boolean command) {
		this.command = command;
	}

	public Boolean getExternalResources() {
		return externalResources;
	}

	public void setExternalResources(Boolean externalResources) {
		this.externalResources = externalResources;
	}

	public Boolean getFinance() {
		return finance;
	}

	public void setFinance(Boolean finance) {
		this.finance = finance;
	}

	public Boolean getLogistics() {
		return logistics;
	}

	public void setLogistics(Boolean logistics) {
		this.logistics = logistics;
	}

	public Boolean getOperations() {
		return operations;
	}

	public void setOperations(Boolean operations) {
		this.operations = operations;
	}

	public Boolean getPlans() {
		return plans;
	}

	public void setPlans(Boolean plans) {
		this.plans = plans;
	}

	public Boolean getAllSections() {
		return allSections;
	}

	public void setAllSections(Boolean allSections) {
		this.allSections = allSections;
	}

	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

}
