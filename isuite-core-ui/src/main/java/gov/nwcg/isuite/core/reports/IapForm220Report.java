package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.IapForm220ReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class IapForm220Report extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="IAP_FORM_220_1";
	
	public IapForm220Report(Collection<IapForm220ReportData> data) {
		super();
		super.setReportData(data);
	}
	
	public String getReportName() {
		return IapForm220Report.REPORT_NAME;
	}

}
