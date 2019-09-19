package gov.nwcg.isuite.core.reports;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.data.AircraftDetailReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;


public class AircraftDetailReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="AircraftDetailReport";
	private static final String SUB_REPORT="AircraftDetailSubReport";
	private static final String SUB_REPORT_GRAPHIC_NAME="AircraftDetailSubChartReport";

	
	                                                     
	public AircraftDetailReport(Collection<AircraftDetailReportData> data) {
		super();
		super.setReportData(data);
		setSubreport();
	}
	
	public String getReportName() {
			return AircraftDetailReport.REPORT_NAME;
	}
	
	public void setSubreport() {
			super.addSubReportName(SUB_REPORT);
			super.addSubReportName(SUB_REPORT_GRAPHIC_NAME);
	}
}
