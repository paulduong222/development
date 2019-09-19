package gov.nwcg.isuite.core.reports;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.data.CostProjectionReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;


public class CostProjectionDetailReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="CostProjectionDetailReport1";
	private static final String SUB_REPORT="CostProjectionDetailSubReport"; 
	private static final String SUB_REPORT1="CostProjectionCategoryDetailTotalSubReport"; 
	private static final String SUB_REPORT2="CostProjectionBarChart";
	
	public CostProjectionDetailReport(Collection<CostProjectionReportData> data) {
		super();
		super.setReportData(data);
		setSubreport();
	}
	
	public String getReportName() {
			return CostProjectionDetailReport.REPORT_NAME;
	}
	
	public void setSubreport() {
			super.addSubReportName(SUB_REPORT);
			super.addSubReportName(SUB_REPORT1);
			super.addSubReportName(SUB_REPORT2);
	}
}
