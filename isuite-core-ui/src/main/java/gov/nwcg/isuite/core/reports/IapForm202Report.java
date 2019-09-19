package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.IapForm202ReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class IapForm202Report extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="IAP_FORM_202";
	
	public IapForm202Report(Collection<IapForm202ReportData> data) {
		super();
		super.setReportData(data);
	}
	
	public String getReportName() {
		return IapForm202Report.REPORT_NAME;
	}

}
