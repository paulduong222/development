package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.OF286TimeInvoiceReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class OF286TimeInvoiceReport extends BaseReport implements IReport {

	private static final String REPORT_NAME="OF286TimeInvoiceReport";
	private static final String SUB_REPORT_NAME="OF286TimeInvoice_subreport1";
	
//	private OF286TimeInvoiceReportData reportData = new OF286TimeInvoiceReportData();
	
	public OF286TimeInvoiceReport(Collection<OF286TimeInvoiceReportData> data) {
		super();
		super.setHeaderTitle("Emergency Equipment - Use Invoice");
		super.setReportData(data);
		super.addSubReportName(SUB_REPORT_NAME);
	}
	
	public String getReportName() {
		return OF286TimeInvoiceReport.REPORT_NAME;
	}
}
