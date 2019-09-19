package gov.nwcg.isuite.core.reports;

import java.util.Collection;
import gov.nwcg.isuite.core.reports.data.ResourcesWithNoActualTimePostedReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;


public class ResourcesWithNoActualTimePostedReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="ResourcesWithNoActualTimePostedReport";
	private static final String SUB_REPORT="ResourcesWithNoActualTimePostedSubReport";
		                                                     
	public ResourcesWithNoActualTimePostedReport(Collection<ResourcesWithNoActualTimePostedReportData> data) {
		super();
		super.setReportData(data);
		setSubreport();
	}
	
	public String getReportName() {
			return ResourcesWithNoActualTimePostedReport.REPORT_NAME;
	}
	
	public void setSubreport() {
			super.addSubReportName(SUB_REPORT);
	}
}
