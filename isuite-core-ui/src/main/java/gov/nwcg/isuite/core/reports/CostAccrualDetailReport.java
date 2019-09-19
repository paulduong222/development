package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.CostAccrualDetailReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class CostAccrualDetailReport extends BaseReport implements IReport{
	private static final String REPORT_NAME="CostAccrualDetailReport";

	public CostAccrualDetailReport(Collection<CostAccrualDetailReportData> data){
		super();
		super.setHeaderTitle("");
		super.setReportData(data);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.report.IReport#getReportName()
	 */
	public String getReportName(){
		return CostAccrualDetailReport.REPORT_NAME;
	}
	
	
}
