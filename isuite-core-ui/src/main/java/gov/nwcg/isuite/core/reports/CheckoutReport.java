package gov.nwcg.isuite.core.reports;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.data.CheckoutReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

public class CheckoutReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="DemobCheckOut";
	private static final String SUB_REPORT_NAME="DemobCheckOut_subreport1";
	
	public CheckoutReport(Collection<CheckoutReportData> data) {
		super();
		super.setReportData(data);
		super.addSubReportName(SUB_REPORT_NAME);
	}
	
	public String getReportName() {
		return CheckoutReport.REPORT_NAME;
	}

}
