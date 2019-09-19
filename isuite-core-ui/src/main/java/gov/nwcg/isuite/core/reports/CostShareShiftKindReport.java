package gov.nwcg.isuite.core.reports;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.data.CostShareMainReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;


public class CostShareShiftKindReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="CostShareShiftItemCodeReport";
	private static final String SUB_REPORT="CostShareShiftItemCodeSubReport"; 

	public CostShareShiftKindReport(Collection<CostShareMainReportData> data) {
		super();
		super.setReportData(data);
		setSubreport();
	}
	
	public String getReportName() {
			return CostShareShiftKindReport.REPORT_NAME;
	}
	
	public void setSubreport() {
			super.addSubReportName(SUB_REPORT);
	}
}
