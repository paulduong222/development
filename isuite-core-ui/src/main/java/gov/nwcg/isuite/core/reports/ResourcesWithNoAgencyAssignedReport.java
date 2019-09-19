package gov.nwcg.isuite.core.reports;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.data.ResourcesWithNoAgencyAssignedReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;


public class ResourcesWithNoAgencyAssignedReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="ResourcesWithNoAgencyAssignedReport";
	private static final String SUB_REPORT="ResourcesWithNoAgencyAssignedSubReport";
		                                                     
	public ResourcesWithNoAgencyAssignedReport(Collection<ResourcesWithNoAgencyAssignedReportData> data) {
		super();
		super.setReportData(data);
		setSubreport();
	}
	
	public String getReportName() {
			return ResourcesWithNoAgencyAssignedReport.REPORT_NAME;
	}
	
	public void setSubreport() {
			super.addSubReportName(SUB_REPORT);
	}
}
