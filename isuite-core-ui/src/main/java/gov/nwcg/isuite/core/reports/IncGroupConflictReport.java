package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.IncGroupConflictReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class IncGroupConflictReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="IncidentGroupConflictReport";
	
	public IncGroupConflictReport(Collection<IncGroupConflictReportData> data) {
		super();
		super.setReportData(data);
	}
	
	public String getReportName() {
		return IncGroupConflictReport.REPORT_NAME;
	}

}
