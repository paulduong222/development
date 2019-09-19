package gov.nwcg.isuite.core.reports;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.data.DetailSummaryReportData;
import gov.nwcg.isuite.core.filter.impl.CostReportFilterImpl;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;


public class DetailSummaryReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="DetailSummaryReport";
	private static final String SUB_REPORT="DetailSummarySubDetailReport";
	
	                                                     
	public DetailSummaryReport(Collection<DetailSummaryReportData> data) {
		super();
		super.setReportData(data);
		setSubreport();
	}
	
	public String getReportName() {
			return DetailSummaryReport.REPORT_NAME;
	}
	
	public void setSubreport() {
			super.addSubReportName(SUB_REPORT);
	}
}
