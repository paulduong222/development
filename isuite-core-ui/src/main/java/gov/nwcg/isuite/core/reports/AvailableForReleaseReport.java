package gov.nwcg.isuite.core.reports;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.data.AvailableForReleaseReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

public class AvailableForReleaseReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="AvailableForReleaseReport";
	
	public AvailableForReleaseReport(Collection<AvailableForReleaseReportData> data) {
		super();
		super.setReportData(data);
	}
	
	public String getReportName() {
		return AvailableForReleaseReport.REPORT_NAME;
	}
}
