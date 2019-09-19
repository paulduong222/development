package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.Tnsp2ReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class Tnsp2Report extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="TNSP2";
	private static final String SUB_REPORT_NAME="TNSP2_subreport";
	private static final String SUB_REPORT_NAME2="TNSP2_Tnspsubreport";
	
	public Tnsp2Report(Collection<Tnsp2ReportData> data) {
		super();
		super.setReportData(data);
		super.addSubReportName(SUB_REPORT_NAME);
		super.addSubReportName(SUB_REPORT_NAME2);
	}
	
	public String getReportName() {
		return Tnsp2Report.REPORT_NAME;
	}

}
