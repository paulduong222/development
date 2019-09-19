package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.WorkRestRatioReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class WorkRestRatioReport extends BaseReport implements IReport{

	private static final String REPORT_NAME="WorkRestRatioReport";
	private static final String SUB_REPORT_NAME="WorkRestRatioReport_subreport";

	public WorkRestRatioReport(String subTitle,Collection<WorkRestRatioReportData> data){
		super();
		super.setHeaderTitle("Work-Rest Ratio Report");
		super.setHeaderSubTitle(subTitle);
		super.setReportData(data);
		super.addSubReportName(SUB_REPORT_NAME);
	}
	
	public String getReportName(){
		return REPORT_NAME;
	}
}
