package gov.nwcg.isuite.core.reports;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.data.ResourceItemCodeByCostReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;


public class ResourceItemCodeByCostReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="ResourceItemCodeByCostReport";
	private static final String SUB_REPORT="ResourceItemCodeByCostSubReport";
		                                                     
	public ResourceItemCodeByCostReport(Collection<ResourceItemCodeByCostReportData> data) {
		super();
		super.setReportData(data);
		setSubreport();
	}
	
	public String getReportName() {
			return ResourceItemCodeByCostReport.REPORT_NAME;
	}
	
	public void setSubreport() {
			super.addSubReportName(SUB_REPORT);
	}
}


