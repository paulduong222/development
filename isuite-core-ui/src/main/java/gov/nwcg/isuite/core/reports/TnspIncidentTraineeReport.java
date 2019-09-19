package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.TnspIncidentTraineeReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class TnspIncidentTraineeReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="TNSP_IncidentTrainee";
	private static final String SUB_REPORT_NAME="TNSP_IncidentTrainee_subreport";
	
	public TnspIncidentTraineeReport(Collection<TnspIncidentTraineeReportData> data) {
		super();
		super.setReportData(data);
		super.addSubReportName(SUB_REPORT_NAME);
	}
	
	public String getReportName() {
		return TnspIncidentTraineeReport.REPORT_NAME;
	}

}
