package gov.nwcg.isuite.core.reports;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.data.ActualDemobReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

public class ActualDemobReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="ActualDemobReport";
    private static final String SUB_REPORT_NAME="ActualDemob_subreport1";
	
	public ActualDemobReport(Collection<ActualDemobReportData> data) {
		super();
		super.setReportData(data);
		super.addSubReportName(SUB_REPORT_NAME);
	}
	
	public String getReportName() {
		return ActualDemobReport.REPORT_NAME;
	}

}
