package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.IapForm205ReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class IapForm205_16F_1LReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="IAP_FORM_205_16F_1L";
    private static final String SUB_REPORT_NAME="IAP_FORM_205_subreport1";
	
	public IapForm205_16F_1LReport(Collection<IapForm205ReportData> data) {
		super();
		super.setReportData(data);
		super.addSubReportName(SUB_REPORT_NAME);
	}
	
	public String getReportName() {
		return IapForm205_16F_1LReport.REPORT_NAME;
	}

}

