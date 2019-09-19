package gov.nwcg.isuite.core.reports;

import java.util.Collection;
import gov.nwcg.isuite.core.reports.data.ResourcesWithDailyCostExceeds10000ReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;


public class ResourcesWithDailyCostExceeds10000Report extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="ResourcesWithDailyCostExceeds10000Report";
	private static final String SUB_REPORT="ResourcesWithDailyCostExceeds10000SubReport";
		                                                     
	public ResourcesWithDailyCostExceeds10000Report(Collection<ResourcesWithDailyCostExceeds10000ReportData> data) {
		super();
		super.setReportData(data);
		setSubreport();
	}
	
	public String getReportName() {
			return ResourcesWithDailyCostExceeds10000Report.REPORT_NAME;
	}
	
	public void setSubreport() {
			super.addSubReportName(SUB_REPORT);
	}
}

