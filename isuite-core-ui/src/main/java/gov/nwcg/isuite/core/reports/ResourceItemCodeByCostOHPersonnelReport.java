package gov.nwcg.isuite.core.reports;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.data.ResourceItemCodeByCostOHPersonnelReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;


public class ResourceItemCodeByCostOHPersonnelReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="ResourceItemCodeByCostOHPersonnelReport";
	private static final String SUB_REPORT="ResourceItemCodeByCostOHPersonnelSubReport";
		                                                     
	public ResourceItemCodeByCostOHPersonnelReport(Collection<ResourceItemCodeByCostOHPersonnelReportData> data) {
		super();
		super.setReportData(data);
		setSubreport();
	}
	
	public String getReportName() {
			return ResourceItemCodeByCostOHPersonnelReport.REPORT_NAME;
	}
	
	public void setSubreport() {
			super.addSubReportName(SUB_REPORT);
	}
}
