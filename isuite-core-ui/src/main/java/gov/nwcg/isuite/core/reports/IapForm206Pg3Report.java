package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.IapForm206Pg3ReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class IapForm206Pg3Report extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="IAP_FORM_206_3";
	
	public IapForm206Pg3Report(Collection<IapForm206Pg3ReportData> data) {
		super();
		super.setReportData(data);
	}
	
	public String getReportName() {
		return IapForm206Pg3Report.REPORT_NAME;
	}

}
