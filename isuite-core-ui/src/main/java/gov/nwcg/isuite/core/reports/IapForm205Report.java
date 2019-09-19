package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.IapForm205ReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class IapForm205Report extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="IAP_FORM_205";
    private static final String SUB_REPORT_NAME="IAP_FORM_205_subreport1";
	
	public IapForm205Report(Collection<IapForm205ReportData> data) {
		super();
		super.setReportData(data);
		super.addSubReportName(SUB_REPORT_NAME);
	}
	
	public String getReportName() {
		return IapForm205Report.REPORT_NAME;
	}

}
