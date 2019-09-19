package gov.nwcg.isuite.core.reports;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.data.AirTravelRequestReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

public class AirTravelRequestReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="AirTravelReport";
	private static final String SUB_REPORT_NAME="AirTravelReport_subreport1";
	
	public AirTravelRequestReport(Collection<AirTravelRequestReportData> data) {
		super();
		super.setReportData(data);
		super.addSubReportName(SUB_REPORT_NAME);
	}
	
	public String getReportName() {
		return AirTravelRequestReport.REPORT_NAME;
	}
}
