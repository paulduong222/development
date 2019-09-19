package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.CostAccrualAllDetailReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class CostAccrualAllDetailReport extends BaseReport implements IReport{
	private static final String REPORT_NAME="CostAccrualAllDetailReportNew";
   // private static final String SUB_REPORT_NAME="CostAccrualAllDetailReport_subreport1";

	public CostAccrualAllDetailReport(Collection<CostAccrualAllDetailReportData> data){
		super();
		super.setHeaderTitle("");
		super.setReportData(data);
		//super.addSubReportName(SUB_REPORT_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.report.IReport#getReportName()
	 */
	public String getReportName(){
		return CostAccrualAllDetailReport.REPORT_NAME;
	}
	
	
}
