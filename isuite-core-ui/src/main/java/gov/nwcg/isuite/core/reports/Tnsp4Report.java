package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.Tnsp4ReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class Tnsp4Report extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="TNSP_4_Interview";
	
	public Tnsp4Report(Collection<Tnsp4ReportData> data) {
		super();
		super.setReportData(data);
	}
	
	public String getReportName() {
		return Tnsp4Report.REPORT_NAME;
	}

}
