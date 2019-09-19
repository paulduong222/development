package gov.nwcg.isuite.core.reports.filter;


import gov.nwcg.isuite.framework.filter.ReportFilter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CostProjectionReportFilter extends ReportFilter{
	
	final int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
	public SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	public SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM-dd");
	
	// report name 
	public boolean isByDayReport;
	public boolean isByWeekReport;
	public boolean isByTotalReport;
	public boolean isByWeekChartReport;
	
	// report type
	public String selectedReportType;
	
	// report data type
	public boolean isSuppourtingData = false;
	
	public String incidentName;
	public String incidentNumber;
	public String incidentGroupName;
	public String projectionName;
	public Long projectionId;
	public int days;
		
	
	public List<String> getReportHeaderDate() {
		// make it times of 7 a full week
		int stopDay = days;
		
		while(days%7 != 0) 
			days++;
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		
		// create the list of days for report header and query 
		List<String> daysStr = new ArrayList<String>();
		for(int i=0; i < days; i++) {
			daysStr.add(dateFormat.format(calendar.getTime()));
			calendar.add(Calendar.DAY_OF_YEAR, 1);
		}
		
		return daysStr;
	}
	
	public int getNumOfWeeks() {
		int numberOfweeks = days/7;
		if(days%7 == 0)
			return numberOfweeks;
		else
			return numberOfweeks+1;
	}
	
	public List<String> getDaysByWeek(int theWeek) {
		List<String> subs = getReportHeaderDate().subList(theWeek*7,theWeek*7+7);
		return subs;
	}
	
	public Date getEndDate() {
		Calendar c = Calendar.getInstance();
	    c.setTime(startDate);
	    c.add(Calendar.DATE, days);
	    Date endDate = c.getTime();
	    return endDate;
	}
	
	public String getStartDateString() {
		return dateFormat.format(startDate.getTime());
	}

	public String getIncidentNumber() {
		return incidentNumber;
	}

	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}
	
	
	public Date getAfterDate(Date date, int days) {
		Calendar c = Calendar.getInstance();
	    c.setTime(date);
	    c.add(Calendar.DATE, days);
	    return c.getTime();
	}
	
	public String getAfterDateInString1(Date date, int days) {
		Calendar c = Calendar.getInstance();
	    c.setTime(date);
	    c.add(Calendar.DATE, days);
	    return dateFormat.format(c.getTime());
	}

	public String getAfterDateInString2(Date date, int days) {
		Calendar c = Calendar.getInstance();
	    c.setTime(date);
	    c.add(Calendar.DATE, days);
	    return dateFormat1.format(c.getTime());
	}
	
	public boolean isByWeekChartReport() {
		return days > 30;
	}
}
