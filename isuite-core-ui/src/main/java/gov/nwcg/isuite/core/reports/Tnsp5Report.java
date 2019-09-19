package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.Tnsp5ReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class Tnsp5Report extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="TNSP_5_HU_Letter";
	
	public Tnsp5Report(Collection<Tnsp5ReportData> data) {
		super();
		super.setReportData(data);
	}
	
	public String getReportName() {
		return Tnsp5Report.REPORT_NAME;
	}

}
