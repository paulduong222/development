package gov.nwcg.isuite.core.reports;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.data.GroupCategorySummaryReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

public class GroupCategorySummaryReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="GroupCategorySummaryReport";
	private static final String SUB_REPORT1="GroupCategorySummarySubReport";
	private static final String SUB_REPORT2="GroupCategorySummarySubReportByDateCrosstab";
	private static final String SUB_REPORT3="GroupCategorySummarySubReportByGroupCrosstab";
	private static final String SUB_REPORT_GRAPHIC_NAME="GroupCategorySummarySubChartReport";
	
	                                                     
	public GroupCategorySummaryReport(Collection<GroupCategorySummaryReportData> data) {
		super();
		super.setReportData(data);
		setSubreport();
	}
	
	public String getReportName() {
			return GroupCategorySummaryReport.REPORT_NAME;
	}
	
	public void setSubreport() {
			super.addSubReportName(SUB_REPORT1);
			super.addSubReportName(SUB_REPORT2);
			super.addSubReportName(SUB_REPORT3);
			super.addSubReportName(SUB_REPORT_GRAPHIC_NAME);
	}
}
