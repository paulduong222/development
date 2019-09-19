package gov.nwcg.isuite.core.reports;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.data.CostProjectionReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;


public class CostProjectionCategoryReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="CostProjectionCategoryReport";
	private static final String SUB_REPORT="CostProjectionCategorySubReport"; 
	private static final String SUB_REPORT1="CostProjectionCategoryDetailTotalSubReport"; 
	private static final String SUB_REPORT2="CostProjectionBarChart";
	
	public CostProjectionCategoryReport(Collection<CostProjectionReportData> data) {
		super();
		super.setReportData(data);
		setSubreport();
	}
	
	public String getReportName() {
			return CostProjectionCategoryReport.REPORT_NAME;
	}
	
	public void setSubreport() {
			super.addSubReportName(SUB_REPORT);
			super.addSubReportName(SUB_REPORT1);
			super.addSubReportName(SUB_REPORT2);
	}
}
