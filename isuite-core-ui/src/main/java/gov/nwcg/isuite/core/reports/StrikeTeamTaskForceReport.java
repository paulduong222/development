package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.StrikeTeamTaskForceReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class StrikeTeamTaskForceReport extends BaseReport implements IReport{
	/*
	 * for now hardcode the report name.
	 * we may want to discuss having a isw_report table
	 * that defines the supported reports.
	 */
	private static final String REPORT_NAME="StrikeTeamTaskForceReport";

	public StrikeTeamTaskForceReport(String subTitle,Collection<StrikeTeamTaskForceReportData> data){
		super();
		super.setHeaderTitle("STRIKE TEAM/TASK FORCE REPORT");
		super.setHeaderSubTitle(subTitle);
		super.setReportData(data);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.report.IReport#getReportName()
	 */
	public String getReportName(){
		return StrikeTeamTaskForceReport.REPORT_NAME;
	}
	
	
}
