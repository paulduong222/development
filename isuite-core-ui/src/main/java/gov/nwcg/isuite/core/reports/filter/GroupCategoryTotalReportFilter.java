package gov.nwcg.isuite.core.reports.filter;

import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.framework.filter.ReportFilter;

import java.util.ArrayList;
import java.util.List;

public class GroupCategoryTotalReportFilter extends ReportFilter{
	
	public List<String> additionalFilters = new ArrayList<String>();
	public String selectedReportGroup;
	public String selectedGraphGroup;
	public String additionalFilterType;
	
	public boolean isReportOnly;
	public boolean isGraphOnly;
	public boolean isReportAndGraph;
	public boolean isDateRangeSelected;
	public boolean isIncidentGroup;
	public boolean isOverhead;
	public boolean isNonOverhead;

	public boolean includeAllAccountingCode = true;
	public boolean selectiveAccountingCode;

	public boolean includeAllAgency = true;
	public boolean selectiveAgency;

	public boolean includeAllPaymentAgency = true;
	public boolean resourcesWherePaymentAgencyIsBlank;
	public boolean resourcesWherePaymentAgencyasEntry;
	public boolean selectivePaymentAgencies;

	public boolean includeAllCostGroup = true;
	public boolean resourcesWhereCostGroupIsBlank;
	public boolean resourcesWhereCostGroupHasEntry;
	public boolean selectiveCostGroups;

	public boolean includeAllUnitID = true;
	public boolean selectiveHomeUnits;

	public boolean includeAllItemCode = true;
	public boolean selectiveItemCode;

	public String groupingName;
	public String reportGroupForQuery;
		
	private DateTransferVo startDateVo=new DateTransferVo();
	private DateTransferVo endDateVo=new DateTransferVo();
	
	public DateTransferVo getStartDateVo() {
		return startDateVo;
	}

	public void setStartDateVo(DateTransferVo startDateVo) {
		this.startDateVo = startDateVo;
	}

	public DateTransferVo getEndDateVo() {
		return endDateVo;
	}

	public void setEndDateVo(DateTransferVo endDateVo) {
		this.endDateVo = endDateVo;
	}

	public List<String> getAdditionalFilters() {
		return additionalFilters;
	}
	
	public void setAdditionalFilters(List<String> additionalFilters) {
		this.additionalFilters = additionalFilters;
	}
	
	public String getSelectedReportGroup() {
		return selectedReportGroup;
	}
	
	public void setSelectedReportGroup(String selectedReportGroup) {
		this.selectedReportGroup = selectedReportGroup;
		this.additionalFilterType = selectedReportGroup;
	}
	
	public String getSelectedGraphGroup() {
		return selectedGraphGroup;
	}
	
	public void setSelectedGraphGroup(String selectedGraphGroup) {
		this.selectedGraphGroup = selectedGraphGroup;
	}
	
	public boolean isIncludeAllAccountingCode() {
		return includeAllAccountingCode;
	}
	
	public void setIncludeAllAccountingCode(boolean includeAllAccountingCode) {
		this.includeAllAccountingCode = includeAllAccountingCode;
	}
	
	public boolean isSelectiveAccountingCode() {
		return selectiveAccountingCode;
	}
	
	public void setSelectiveAccountingCode(boolean selectiveAccountingCode) {
		this.selectiveAccountingCode = selectiveAccountingCode;
	}
	
	public boolean isIncludeAllAgency() {
		return includeAllAgency;
	}
	
	public void setIncludeAllAgency(boolean includeAllAgency) {
		this.includeAllAgency = includeAllAgency;
	}
	
	public boolean isSelectiveAgency() {
		return selectiveAgency;
	}
	
	public void setSelectiveAgency(boolean selectiveAgency) {
		this.selectiveAgency = selectiveAgency;
	}
	
	public boolean isIncludeAllPaymentAgency() {
		return includeAllPaymentAgency;
	}
	
	public void setIncludeAllPaymentAgency(boolean includeAllPaymentAgency) {
		this.includeAllPaymentAgency = includeAllPaymentAgency;
	}
	
	public boolean isResourcesWherePaymentAgencyIsBlank() {
		return resourcesWherePaymentAgencyIsBlank;
	}
	
	public void setResourcesWherePaymentAgencyIsBlank(
			boolean resourcesWherePaymentAgencyIsBlank) {
		this.resourcesWherePaymentAgencyIsBlank = resourcesWherePaymentAgencyIsBlank;
	}
	
	public boolean isResourcesWherePaymentAgencyasEntry() {
		return resourcesWherePaymentAgencyasEntry;
	}
	
	public void setResourcesWherePaymentAgencyasEntry(
			boolean resourcesWherePaymentAgencyasEntry) {
		this.resourcesWherePaymentAgencyasEntry = resourcesWherePaymentAgencyasEntry;
	}
	
	public boolean isSelectivePaymentAgencies() {
		return selectivePaymentAgencies;
	}
	
	public void setSelectivePaymentAgencies(boolean selectivePaymentAgencies) {
		this.selectivePaymentAgencies = selectivePaymentAgencies;
	}
	
	public boolean isIncludeAllCostGroup() {
		return includeAllCostGroup;
	}
	
	public void setIncludeAllCostGroup(boolean includeAllCostGroup) {
		this.includeAllCostGroup = includeAllCostGroup;
	}
	
	public boolean isResourcesWhereCostGroupIsBlank() {
		return resourcesWhereCostGroupIsBlank;
	}
	
	public void setResourcesWhereCostGroupIsBlank(
			boolean resourcesWhereCostGroupIsBlank) {
		this.resourcesWhereCostGroupIsBlank = resourcesWhereCostGroupIsBlank;
	}
	
	public boolean isResourcesWhereCostGroupHasEntry() {
		return resourcesWhereCostGroupHasEntry;
	}
	
	public void setResourcesWhereCostGroupHasEntry(
			boolean resourcesWhereCostGroupHasEntry) {
		this.resourcesWhereCostGroupHasEntry = resourcesWhereCostGroupHasEntry;
	}
	
	public boolean isSelectiveCostGroups() {
		return selectiveCostGroups;
	}
	
	public void setSelectiveCostGroups(boolean selectiveCostGroups) {
		this.selectiveCostGroups = selectiveCostGroups;
	}
	
	public boolean isIncludeAllUnitID() {
		return includeAllUnitID;
	}
	
	public void setIncludeAllUnitID(boolean includeAllUnitID) {
		this.includeAllUnitID = includeAllUnitID;
	}
	
	public boolean isSelectiveHomeUnits() {
		return selectiveHomeUnits;
	}
	
	public void setSelectiveHomeUnits(boolean selectiveHomeUnits) {
		this.selectiveHomeUnits = selectiveHomeUnits;
	}
	
	public boolean isIncludeAllItemCode() {
		return includeAllItemCode;
	}
	
	public void setIncludeAllItemCode(boolean includeAllItemCode) {
		this.includeAllItemCode = includeAllItemCode;
	}
	
	public boolean isSelectiveItemCode() {
		return selectiveItemCode;
	}
	
	public void setSelectiveItemCode(boolean selectiveItemCode) {
		this.selectiveItemCode = selectiveItemCode;
	}
	
	public String getAdditionalFilterString() {
		String additionalFilters = "";
		for(String s :this.additionalFilters) 
			additionalFilters += "'" + s + "',";
		
		if(!additionalFilters.isEmpty())
			additionalFilters = additionalFilters.substring(0, additionalFilters.length()-1);
		
		return additionalFilters;
	}
	
	public boolean isReportOnly() {
		return isReportOnly;
	}

	public void setReportOnly(boolean isReportOnly) {
		this.isReportOnly = isReportOnly;
	}

	public boolean isGraphOnly() {
		return isGraphOnly;
	}

	public void setGraphOnly(boolean isGraphOnly) {
		this.isGraphOnly = isGraphOnly;
	}

	public boolean isReportAndGraph() {
		return isReportAndGraph;
	}

	public boolean isOverhead() {
		return isOverhead;
	}

	public void setOverhead(boolean isOverhead) {
		this.isOverhead = isOverhead;
	}

	public boolean isNonoverhead() {
		return isNonOverhead;
	}

	public void setNonOverhead(boolean isNonOverhead) {
		this.isNonOverhead = isNonOverhead;
	}
	
	public void setReportAndGraph(boolean isReportAndGraph) {
		this.isReportAndGraph = isReportAndGraph;
	}

	public boolean isDateRangeSelected() {
		return isDateRangeSelected;
	}

	public void setDateRangeSelected(boolean isDateRangeSelected) {
		this.isDateRangeSelected = isDateRangeSelected;
	}

	public boolean isIncidentGroup() {
		return isIncidentGroup;
	}

	public void setIncidentGroup(boolean isIncidentGroup) {
		this.isIncidentGroup = isIncidentGroup;
	}

	public String getReportType() {
		if(isReportOnly)  
			return "reportsOnly";
		else if(isReportAndGraph)
			return "reportsAndGraph";
		else if(isGraphOnly)
			return "graphOnly";
		else
			return "";
	}

	public String getAdditionalFilterType() {
		return additionalFilterType;
	}

	public void setAdditionalFilterType(String additionalFilterType) {
		this.additionalFilterType = additionalFilterType;
	}

	public String getGroupingName() {
		return groupingName;
	}

	public void setGroupingName(String groupingName) {
		this.groupingName = groupingName;
	}

	public String getReportGroupForQuery() {
		return reportGroupForQuery;
	}

	public void setReportGroupForQuery(String reportGroupForQuery) {
		this.reportGroupForQuery = reportGroupForQuery;
	}
}
