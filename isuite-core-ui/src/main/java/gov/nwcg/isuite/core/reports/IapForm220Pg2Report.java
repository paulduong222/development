package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.IapForm220Pg2ReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class IapForm220Pg2Report extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="IAP_FORM_220_2";
    private static final String SUB_REPORT_NAME="IAP_FORM_220_2_Helicopters";
    private static final String SUB_REPORT_NAME_2="IAP_FORM_220_2_Tasks";
	
	public IapForm220Pg2Report(Collection<IapForm220Pg2ReportData> data) {
		super();
		super.setReportData(data);
		super.addSubReportName(SUB_REPORT_NAME);
		super.addSubReportName(SUB_REPORT_NAME_2);
	}
	
	public String getReportName() {
		return IapForm220Pg2Report.REPORT_NAME;
	}

}
