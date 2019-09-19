package gov.nwcg.isuite.core.reports;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.data.ResourcesWithActualTimePostingButThreeOrMoreDaysReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;


public class ResourcesWithActualTimePostingButThreeOrMoreDaysReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="ResourcesWithActualTimePostingButThreeOrMoreDaysReport";
	private static final String SUB_REPORT="ResourcesWithActualTimePostingButThreeOrMoreDaysSubReport";
		                                                     
	public ResourcesWithActualTimePostingButThreeOrMoreDaysReport(Collection<ResourcesWithActualTimePostingButThreeOrMoreDaysReportData> data) {
		super();
		super.setReportData(data);
		setSubreport();
	}
	
	public String getReportName() {
			return ResourcesWithActualTimePostingButThreeOrMoreDaysReport.REPORT_NAME;
	}
	
	public void setSubreport() {
			super.addSubReportName(SUB_REPORT);
	}
}


