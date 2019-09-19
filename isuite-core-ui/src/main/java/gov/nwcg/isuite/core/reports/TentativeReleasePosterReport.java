package gov.nwcg.isuite.core.reports;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.data.TentativeReleasePosterReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

public class TentativeReleasePosterReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="TentativeReleasePoster";
	
	public TentativeReleasePosterReport(Collection<TentativeReleasePosterReportData> data) {
		super();
		super.setReportData(data);
	}
	
	public String getReportName() {
		return TentativeReleasePosterReport.REPORT_NAME;
	}
}
