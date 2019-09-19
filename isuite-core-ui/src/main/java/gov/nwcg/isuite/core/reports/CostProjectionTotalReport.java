package gov.nwcg.isuite.core.reports;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.data.CostProjectionReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;


public class CostProjectionTotalReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="CostProjectionTotalReport";
	private static final String SUB_REPORT="CostProjectionTotalSubReport"; 
	private static final String SUB_REPORT1="CostProjectionChartReport"; 
	private static final String SUB_REPORT2="CostProjectionChartReportToDate"; 
	//private static final String SUB_REPORT1="CostProjectionBarChartTotal";

	public CostProjectionTotalReport(Collection<CostProjectionReportData> data) {
		super();
		super.setReportData(data);
		setSubreport();
	}
	
	public String getReportName() {
			return CostProjectionTotalReport.REPORT_NAME;
	}
	
	public void setSubreport() {
			super.addSubReportName(SUB_REPORT);
			super.addSubReportName(SUB_REPORT1);
			super.addSubReportName(SUB_REPORT2);
	}
}
