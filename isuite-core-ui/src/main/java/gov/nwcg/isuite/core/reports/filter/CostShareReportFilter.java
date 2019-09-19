package gov.nwcg.isuite.core.reports.filter;

import gov.nwcg.isuite.framework.filter.ReportFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CostShareReportFilter extends ReportFilter{
	static final Map<String,Integer> numOfColumnMap = new HashMap<String, Integer>();
	static {
		numOfColumnMap.put("summary", 5);
		numOfColumnMap.put("worksheet", 3);
		numOfColumnMap.put("shiftkind", 3);
		numOfColumnMap.put("detail", 3);
	}
	
	private static final String SUMMARY ="summary";
	private static final String WORKSHEET ="worksheet";
	private static final String SHIFTKIND ="shiftkind";
	private static final String DETAIL ="detail";
	
	public List<String> selectedAgencies = new ArrayList<String>();
	public List<String> categoryList = new ArrayList<String>();
	
	public boolean isSummaryReport;
	public boolean isDetailReport;
	public boolean isShiftKindReport;
	public boolean isWorkSheetReport;
	public boolean isIncidentGroup;
	
	public boolean isIncidentGroup() {
		return isIncidentGroup;
	}

	public void setIncidentGroup(boolean isIncidentGroup) {
		this.isIncidentGroup = isIncidentGroup;
	}

	public void setAgencyList(List<String> agencyNames) {
		this.selectedAgencies = agencyNames;
	}
	
	public List<String> getAgencyNames(int order) {
		
		List<String> list = new ArrayList<String>();
		int size = numOfColumnMap.get(getReportType());
		for(int i=size*order; i< (size*order+size); i++) {
			if(i<selectedAgencies.size())
				list.add(selectedAgencies.get(i));
		}
		return list;
		
	}
	
	public List<String> getCategoryList() {
		if(categoryList.isEmpty()) {
		categoryList.add("Aircraft");
		categoryList.add("Support & Overhead");
		}
		
		return categoryList;
		
	}
	
	public void setCategoryList(List<String> categoryList) {
//		if(categoryList.isEmpty()) {
//		categoryList.add("Aircraft");
//		categoryList.add("Support & Overhead");
//		}
		this.categoryList = categoryList;
	}
	
	public int getNumOfAgencyGroup() {
	
		int columnSize = numOfColumnMap.get(getReportType());
		if(selectedAgencies == null || selectedAgencies.isEmpty())
			return 0;
		
		int numOfgroups =  selectedAgencies.size()/columnSize;
		
		if(selectedAgencies.size()%columnSize == 0)
			return numOfgroups;
		else
			return numOfgroups+1;
	}
	
	public String getReportType(){
		if(isSummaryReport)
			return SUMMARY;
		
		if(isDetailReport)
			return DETAIL;
		
		if(isShiftKindReport)
			return SHIFTKIND;
		
		if(isWorkSheetReport)
			return WORKSHEET;
		
		return "";
	}
}
