package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.IapForm206ReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class IapForm206Report extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="IAP_FORM_206";
    private static final String SUB_REPORT_NAME="IAP_FORM_206_subreport1";
    private static final String SUB_REPORT_NAME_2="IAP_FORM_206_subreport2";
    private static final String SUB_REPORT_NAME_3="IAP_FORM_206_subreport3";
    private static final String SUB_REPORT_NAME_4="IAP_FORM_206_AreaLocCap";
    private static final String SUB_REPORT_NAME_5="IAP_FORM_206_RemoteCampLoc";
	
	public IapForm206Report(Collection<IapForm206ReportData> data) {
		super();
		super.setReportData(data);
		super.addSubReportName(SUB_REPORT_NAME);
		super.addSubReportName(SUB_REPORT_NAME_2);
		super.addSubReportName(SUB_REPORT_NAME_3);
		super.addSubReportName(SUB_REPORT_NAME_4);
		super.addSubReportName(SUB_REPORT_NAME_5);
	}
	
	public String getReportName() {
		return IapForm206Report.REPORT_NAME;
	}

}
