package gov.nwcg.isuite.core.reports;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.data.GroupCategoryTotalReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;


public class GroupCategoryTotalReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="GroupCategoryTotalReport";
	//private static final String SUB_REPORT="GroupCategoryTotalSubReport";
	private static final String SUB_REPORT="GroupCategoryTotalSubReportCrosstab";
	private static final String SUB_REPORT_GRAPHIC_NAME="GroupCategoryTotalSubChartReport";
	private static final String SUB_REPORT_ALL_INCIDENTS="GroupCategoryTotalAllIncidentsSubReportCrosstab";
	                                                     
	public GroupCategoryTotalReport(Collection<GroupCategoryTotalReportData> data) {
		super();
		super.setReportData(data);
		setSubreport();
	}
	
	public String getReportName() {
			return GroupCategoryTotalReport.REPORT_NAME;
	}
	
	public void setSubreport() {
			super.addSubReportName(SUB_REPORT);
			super.addSubReportName(SUB_REPORT_GRAPHIC_NAME);
			super.addSubReportName(SUB_REPORT_ALL_INCIDENTS);
	}
}
