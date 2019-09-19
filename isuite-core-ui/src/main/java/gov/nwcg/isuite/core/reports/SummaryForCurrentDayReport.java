package gov.nwcg.isuite.core.reports;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.data.SummaryForCurrentDayReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

public class SummaryForCurrentDayReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="DetailSummaryReport";
	private static final String SUB_REPORT1="SummaryForCurrentDaySubReportByDate";
	private static final String SUB_REPORT2="SummaryForCurrentDaySubReportByGroup";
	                                                     
	public SummaryForCurrentDayReport(Collection<SummaryForCurrentDayReportData> data) {
		super();
		super.setReportData(data);
		setSubreport();
	}
	
	public String getReportName() {
			return SummaryForCurrentDayReport.REPORT_NAME;
	}
	
	public void setSubreport() {
			super.addSubReportName(SUB_REPORT1);
			super.addSubReportName(SUB_REPORT2);
	}
}
