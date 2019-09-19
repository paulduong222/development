package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.CrewRosterReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class CrewRosterReport extends BaseReport implements IReport{

	private static final String REPORT_NAME="CrewRosterReport";

	public CrewRosterReport(String subTitle,Collection<CrewRosterReportData> data){
		super();
		super.setHeaderTitle("Crew Roster Report");
		super.setHeaderSubTitle(subTitle);
		super.setReportData(data);
	}
	
	public String getReportName(){
		return REPORT_NAME;
	}
}
