package gov.nwcg.isuite.core.reports.filter;

import gov.nwcg.isuite.framework.filter.TimeReportFilter;

public class WorkRestRatioReportFilter extends TimeReportFilter {
	
	private boolean allResources = true;
	private boolean specificResources;

	private boolean groupByNone = true;
	private boolean groupBySection;
	private boolean groupByDate;

	private boolean sectionTypeAll = true;
	private boolean sectionTypeCommand;
	private boolean sectionTypeOperations;
	private boolean sectionTypeFinance;
	private boolean sectionTypePlanning;
	private boolean sectionTypeLogistics;
	private boolean sectionTypeExternal;

	private boolean sectionSortByShiftStartDate = true;
	private boolean sectionSortByRequestNumber;
	private boolean sectionSortByName;

	private boolean dateTypeAscending = true;
	private boolean dateTypeDescending;

	private boolean dateSortByRequestNumber = true;
	private boolean dateSortByName;
	
	public WorkRestRatioReportFilter() {
	}

	public boolean isAllResources() {
		return allResources;
	}

	public void setAllResources(boolean allResources) {
		this.allResources = allResources;
	}

	public boolean isSpecificResources() {
		return specificResources;
	}

	public void setSpecificResources(boolean specificResources) {
		this.specificResources = specificResources;
	}

	public boolean isGroupByNone() {
		return groupByNone;
	}

	public void setGroupByNone(boolean groupByNone) {
		this.groupByNone = groupByNone;
	}

	public boolean isGroupBySection() {
		return groupBySection;
	}

	public void setGroupBySection(boolean groupBySection) {
		this.groupBySection = groupBySection;
	}

	public boolean isGroupByDate() {
		return groupByDate;
	}

	public void setGroupByDate(boolean groupByDate) {
		this.groupByDate = groupByDate;
	}

	public boolean isSectionTypeAll() {
		return sectionTypeAll;
	}

	public void setSectionTypeAll(boolean sectionTypeAll) {
		this.sectionTypeAll = sectionTypeAll;
	}

	public boolean isSectionTypeCommand() {
		return sectionTypeCommand;
	}

	public void setSectionTypeCommand(boolean sectionTypeCommand) {
		this.sectionTypeCommand = sectionTypeCommand;
	}

	public boolean isSectionTypeOperations() {
		return sectionTypeOperations;
	}

	public void setSectionTypeOperations(boolean sectionTypeOperations) {
		this.sectionTypeOperations = sectionTypeOperations;
	}

	public boolean isSectionTypeFinance() {
		return sectionTypeFinance;
	}

	public void setSectionTypeFinance(boolean sectionTypeFinance) {
		this.sectionTypeFinance = sectionTypeFinance;
	}

	public boolean isSectionTypePlanning() {
		return sectionTypePlanning;
	}

	public void setSectionTypePlanning(boolean sectionTypePlanning) {
		this.sectionTypePlanning = sectionTypePlanning;
	}

	public boolean isSectionTypeLogistics() {
		return sectionTypeLogistics;
	}

	public void setSectionTypeLogistics(boolean sectionTypeLogistics) {
		this.sectionTypeLogistics = sectionTypeLogistics;
	}

	public boolean isSectionTypeExternal() {
		return sectionTypeExternal;
	}

	public void setSectionTypeExternal(boolean sectionTypeExternal) {
		this.sectionTypeExternal = sectionTypeExternal;
	}

	public boolean isSectionSortByShiftStartDate() {
		return sectionSortByShiftStartDate;
	}

	public void setSectionSortByShiftStartDate(boolean sectionSortByShiftStartDate) {
		this.sectionSortByShiftStartDate = sectionSortByShiftStartDate;
	}

	public boolean isSectionSortByRequestNumber() {
		return sectionSortByRequestNumber;
	}

	public void setSectionSortByRequestNumber(boolean sectionSortByRequestNumber) {
		this.sectionSortByRequestNumber = sectionSortByRequestNumber;
	}

	public boolean isSectionSortByName() {
		return sectionSortByName;
	}

	public void setSectionSortByName(boolean sectionSortByName) {
		this.sectionSortByName = sectionSortByName;
	}

	public boolean isDateTypeAscending() {
		return dateTypeAscending;
	}

	public void setDateTypeAscending(boolean dateTypeAscending) {
		this.dateTypeAscending = dateTypeAscending;
	}

	public boolean isDateTypeDescending() {
		return dateTypeDescending;
	}

	public void setDateTypeDescending(boolean dateTypeDescending) {
		this.dateTypeDescending = dateTypeDescending;
	}

	public boolean isDateSortByRequestNumber() {
		return dateSortByRequestNumber;
	}

	public void setDateSortByRequestNumber(boolean dateSortByRequestNumber) {
		this.dateSortByRequestNumber = dateSortByRequestNumber;
	}

	public boolean isDateSortByName() {
		return dateSortByName;
	}

	public void setDateSortByName(boolean dateSortByName) {
		this.dateSortByName = dateSortByName;
	}
	
	/**
	 * Function to return a comma separated list of section codes are selected by the user.
	 * If all sections are selected, return null.
	 * @return
	 */
	public String getFilteredSectionString() {
		if(!groupBySection) return null;
		if(sectionTypeAll) return null;
		
		int count = 0;
		int TOTAL_NUMBER_OF_SECTIONS = 6;
		String sectionString = null;
		
		if(sectionTypeCommand){
			count++;
			sectionString = addSectionToString(sectionString, "C");
		}
		
		if(sectionTypeOperations){
			count++;
			sectionString = addSectionToString(sectionString, "O");
		}
		
		if(sectionTypeFinance){
			count++;
			sectionString = addSectionToString(sectionString, "F");
		}
		
		if(sectionTypePlanning){
			count++;
			sectionString = addSectionToString(sectionString, "P");
		}
		
		if(sectionTypeLogistics){
			count++;
			sectionString = addSectionToString(sectionString, "L");
		}
		
		if(sectionTypeExternal){
			count++;
			sectionString = addSectionToString(sectionString, "E");
		}
		
		// If all sections are selected by the user, return null.
		if(count==TOTAL_NUMBER_OF_SECTIONS) {
			sectionString = null;
		}
		
		return sectionString;
	}
	
	/**
	 * Private helper method called by getFilteredSectionString( ) to return the updated
	 * sectionString, which contains the newly appended section code.
	 * @param sectionString
	 * @param sectionCode
	 * @return
	 */
	private String addSectionToString(String sectionString, String sectionCode){
		if(sectionString != null && !"".equals(sectionString)){
			sectionString += "," + sectionCode;
		} else{
			sectionString = sectionCode;
		}
		return sectionString;
	}
}
