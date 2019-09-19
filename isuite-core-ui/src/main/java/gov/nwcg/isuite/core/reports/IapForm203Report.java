package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.IapForm203ReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class IapForm203Report extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="IAP_FORM_203";
    private static final String SUB_REPORT_NAME_1="IAP_FORM_203_Positions";
	
	public IapForm203Report(Collection<IapForm203ReportData> data) {
		super();
		super.setReportData(data);
		super.addSubReportName(SUB_REPORT_NAME_1);
	}
	
	public String getReportName() {
		return IapForm203Report.REPORT_NAME;
	}

}
