package gov.nwcg.isuite.core.reports;

import java.util.Collection;
import gov.nwcg.isuite.core.reports.data.ResourcesWithNoDailyCostRecordsReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;


public class ResourcesWithNoDailyCostRecordsReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="ResourcesWithNoDailyCostRecordsReport";
	private static final String SUB_REPORT="ResourcesWithNoDailyCostRecordsSubReport";
		                                                     
	public ResourcesWithNoDailyCostRecordsReport(Collection<ResourcesWithNoDailyCostRecordsReportData> data) {
		super();
		super.setReportData(data);
		setSubreport();
	}
	
	public String getReportName() {
			return ResourcesWithNoDailyCostRecordsReport.REPORT_NAME;
	}
	
	public void setSubreport() {
			super.addSubReportName(SUB_REPORT);
	}
}
