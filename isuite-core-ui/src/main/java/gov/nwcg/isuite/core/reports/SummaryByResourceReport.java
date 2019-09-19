package gov.nwcg.isuite.core.reports;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.data.SummaryByResourceReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;


public class SummaryByResourceReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="SummaryByResourceReport";
	private static final String SUB_REPORT2="SummaryByResourceByDateSubReport2";
	private static final String SUB_REPORT="SummaryByResourceByDateSubReport1"; 
	private static final String SUB_REPORT3="SummaryByResourceByGroupSubReport";
	
	
	public SummaryByResourceReport(Collection<SummaryByResourceReportData> data) {
		super();
		super.setReportData(data);
		setSubreport();
	}
	
	public String getReportName() {
			return SummaryByResourceReport.REPORT_NAME;
	}
	
	public void setSubreport() {
			super.addSubReportName(SUB_REPORT);
			super.addSubReportName(SUB_REPORT2);
			super.addSubReportName(SUB_REPORT3);
	}
}
