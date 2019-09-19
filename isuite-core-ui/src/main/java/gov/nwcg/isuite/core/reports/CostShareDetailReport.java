package gov.nwcg.isuite.core.reports;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.data.CostShareMainReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;


public class CostShareDetailReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="CostShareDetailReport";
	private static final String SUB_REPORT="CostShareDetailSubReport"; 

	public CostShareDetailReport(Collection<CostShareMainReportData> data) {
		super();
		super.setReportData(data);
		setSubreport();
	}
	
	public String getReportName() {
			return CostShareDetailReport.REPORT_NAME;
	}
	
	public void setSubreport() {
			super.addSubReportName(SUB_REPORT);
	}
}
