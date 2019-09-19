package gov.nwcg.isuite.core.reports;

import java.util.Collection;
import gov.nwcg.isuite.core.reports.data.DailyCostComparisonICReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;


public class DailyCostComparisonICReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="DailyCostComparisonICReport";
	private static final String SUB_REPORT="DailyCostComparisonICSubReport";
		                                                     
	public DailyCostComparisonICReport(Collection<DailyCostComparisonICReportData> data) {
		super();
		super.setReportData(data);
		setSubreport();
	}
	
	public String getReportName() {
			return DailyCostComparisonICReport.REPORT_NAME;
	}
	
	public void setSubreport() {
			super.addSubReportName(SUB_REPORT);
	}
}

