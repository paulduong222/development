package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.Tnsp225ReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class Tnsp225Report extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="TNSP_ICS_225";
	
	public Tnsp225Report(Collection<Tnsp225ReportData> data) {
		super();
		super.setReportData(data);
	}
	
	public String getReportName() {
		return Tnsp225Report.REPORT_NAME;
	}

}
