package gov.nwcg.isuite.core.reports.filter;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import gov.nwcg.isuite.framework.filter.TimeReportFilter;

public class SummaryHoursWorkedReportFilter extends TimeReportFilter {
	
	final int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
	public SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	public List<String> sections = new ArrayList<String>();
	public List<Long> incidentIds = new ArrayList<Long>();
	public Long transactionId;
	
	public boolean isNonGroupBy = true;
	public boolean isSection = false;
	public boolean isAllResources = true;
	public boolean isSpecificResources = false;
	public boolean isSortByShifStartDate = false;
	public boolean isSortByRequestNum = false;
	public boolean isSortByResourceName = false;
	public boolean isExcludeDemob = false;
		
	public List<Long> getIncidentIds() {
		
		return incidentIds;
	}

	public void setIncidentIds(List<Long> incidentIds) {
		this.incidentIds = incidentIds;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public List<String> getSections() {
		return sections;
	}
	
	public void setSections(List<String> sections) {
		this.sections = sections;
	}
		
	public boolean isNonGroupBy() {
		return isNonGroupBy;
	}
	
	public void setNonGroupBy(boolean isNonGroupBy) {
		this.isNonGroupBy = isNonGroupBy;
	}
	
	public boolean isSection() {
		return isSection;
	}
	
	public void setSection(boolean isSection) {
		this.isSection = isSection;
	}
	
	public boolean isAllResources() {
		return isAllResources;
	}
	
	public void setAllResources(boolean isAllResources) {
		this.isAllResources = isAllResources;
	}
	
	public boolean isSpecificResources() {
		return isSpecificResources;
	}
	
	public void setSpecificResources(boolean isSpecificResources) {
		this.isSpecificResources = isSpecificResources;
	}
	
	public boolean isSortByShifStartDate() {
		return isSortByShifStartDate;
	}
	
	public void setSortByShifStartDate(boolean isSortByShifStartDate) {
		this.isSortByShifStartDate = isSortByShifStartDate;
	}
	
	public boolean isSortByRequestNum() {
		return isSortByRequestNum;
	}
	
	public void setSortByRequestNum(boolean isSortByRequestNum) {
		this.isSortByRequestNum = isSortByRequestNum;
	}
	
	public boolean isSortByResourceName() {
		return isSortByResourceName;
	}
	
	public void setSortByResourceName(boolean isSortByResName) {
		this.isSortByResourceName = isSortByResName;
	}
	
	public boolean isExcludeDemob() {
		return isExcludeDemob;
	}
	
	public void setExcludeDemob(boolean isExcludeDemob) {
		this.isExcludeDemob = isExcludeDemob;
	}
	
	public String getSectionsString() {
		String strSection = null;
		
		for(String s : sections) {
			if(strSection == null)
				strSection = s;
			else
				strSection += "," + s;
		}
		
		return strSection;
	}
	
	public int getDaysBetweenStartAndEndDate() {
		int numberOfDays = (int)((endDate.getTime() - startDate.getTime())/MILLIS_IN_DAY) + 1;
		return numberOfDays;	
	}
	
	public List<String> getReportDays() {
		// get number of days between endDate and startDate 
		int numberOfDays = getDaysBetweenStartAndEndDate();	
		// make it times of 7 a full week
		while(numberOfDays%7 != 0) 
			numberOfDays++;
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		
		// create the list of days for report header and query 
		List<String> days = new ArrayList<String>();
		for(int i=0; i < numberOfDays; i++) {
			days.add(dateFormat.format(calendar.getTime()));
			calendar.add(Calendar.DAY_OF_YEAR, 1);
		}
	
		return days;
	}
	
	public int getNumOfWeeks() {
		// get number of weeks for endDate and startDate 
		int numberOfDays = getDaysBetweenStartAndEndDate();
		
		int numberOfweeks = numberOfDays/7;
		if(numberOfDays%7 == 0)
			return numberOfweeks;
		else
			return numberOfweeks+1;
	}
	
	public List<String> getDaysByWeek(int theWeek) {
		List<String> subs = getReportDays().subList(theWeek*7,theWeek*7+7);
		return subs;
	}
	
	public String getStartDateString() {
		return dateFormat.format(startDate.getTime());
	}
	
	public String getEndDateString() {
		return dateFormat.format(endDate);
	}
	
	
}
