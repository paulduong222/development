package gov.nwcg.isuite.core.reports;

import java.util.Collection;
import gov.nwcg.isuite.core.reports.data.DailyCostComparisonRESRReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;


public class DailyCostComparisonRESRReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="DailyCostComparisonRESRReport";
	private static final String SUB_REPORT="DailyCostComparisonRESRSubReport";
		                                                     
	public DailyCostComparisonRESRReport(Collection<DailyCostComparisonRESRReportData> data) {
		super();
		super.setReportData(data);
		setSubreport();
	}
	
	public String getReportName() {
			return DailyCostComparisonRESRReport.REPORT_NAME;
	}
	
	public void setSubreport() {
			super.addSubReportName(SUB_REPORT);
	}
}

