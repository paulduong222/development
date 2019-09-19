package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.OF288TimeInvoice;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class OF288TimeInvoiceReport extends BaseReport implements IReport {

	private static final String REPORT_NAME="OF288TimeInvoiceReport";
	private static final String SUB_REPORT_NAME="OF288TimeInvoiceSubReport";
	
	public OF288TimeInvoiceReport(Collection<OF288TimeInvoice> reportData) {
		super();
		super.setHeaderTitle("Emergency FireFighter Time Report, OF-288");
		//super.setHeaderSubTitle(subTitle);
		super.setReportData(reportData);
		super.addSubReportName(SUB_REPORT_NAME);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.report.BaseReport#getReportName()
	 */
	public String getReportName() {
		return OF288TimeInvoiceReport.REPORT_NAME;
	}
}
