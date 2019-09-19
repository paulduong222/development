package gov.nwcg.isuite.core.reports;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.data.GroundSupportReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

public class GroundSupportReport extends BaseReport implements IReport{
	
	private static final String REPORT_NAME="GroundSupportReport";
	
	public GroundSupportReport(Collection<GroundSupportReportData> data) {
		super();
		super.setReportData(data);
	}
	
	public String getReportName() {
		return GroundSupportReport.REPORT_NAME;
	}

}
