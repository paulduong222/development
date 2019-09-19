package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.Tnsp1ReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class Tnsp1Report extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="TNSP_1_Dataform";
	
	public Tnsp1Report(Collection<Tnsp1ReportData> data) {
		super();
		super.setReportData(data);
	}
	
	public String getReportName() {
		return Tnsp1Report.REPORT_NAME;
	}

}
