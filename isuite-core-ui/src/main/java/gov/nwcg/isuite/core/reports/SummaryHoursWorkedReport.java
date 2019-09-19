package gov.nwcg.isuite.core.reports;

import java.util.Collection;
import gov.nwcg.isuite.core.reports.data.SummaryOfHoursWorkedReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;


public class SummaryHoursWorkedReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="SummaryOfHoursWorkedReport";
	private static final String SUB_REPORT_1="SummaryOfHoursWorked_subreport";
	private static final String SUB_REPORT_2="SummaryOfHoursWorked_sub_subreport";
	                                                     
	public SummaryHoursWorkedReport(Collection<SummaryOfHoursWorkedReportData> data) {
		super();
		super.setReportData(data);
		setSubreport();
	}
	
	public String getReportName() {
		return SummaryHoursWorkedReport.REPORT_NAME;
	}
	
	public void setSubreport() {
		super.addSubReportName(SUB_REPORT_1);
		super.addSubReportName(SUB_REPORT_2);
	}
}
