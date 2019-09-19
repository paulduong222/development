package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.CostAccrualSummaryReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class CostAccrualSummaryReport extends BaseReport implements IReport{
	private static final String REPORT_NAME="CostAccrualSummaryReport";

	public CostAccrualSummaryReport(Collection<CostAccrualSummaryReportData> data){
		super();
		super.setHeaderTitle("");
		super.setReportData(data);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.report.IReport#getReportName()
	 */
	public String getReportName(){
		return CostAccrualSummaryReport.REPORT_NAME;
	}
	
	
}
