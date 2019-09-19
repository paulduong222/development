package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.Tnsp3ReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class Tnsp3Report extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="TNSP3_TrainingSummary";
	private static final String SUB_REPORT_NAME="TNSP3_TS_subreport";
	private static final String SUB_REPORT_NAME2="TNSP3_TS_Tnspsubreport";
	private static final String SUB_REPORT_NAME3="TNSP3_TS_PPsubreport";
	
	public Tnsp3Report(Collection<Tnsp3ReportData> data) {
		super();
		super.setReportData(data);
		super.addSubReportName(SUB_REPORT_NAME);
		super.addSubReportName(SUB_REPORT_NAME2);
		super.addSubReportName(SUB_REPORT_NAME3);
	}
	
	public String getReportName() {
		return Tnsp3Report.REPORT_NAME;
	}

}
