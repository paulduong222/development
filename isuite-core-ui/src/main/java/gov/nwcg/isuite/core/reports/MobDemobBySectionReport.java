package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.MobDemobBySectionReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class MobDemobBySectionReport extends BaseReport implements IReport{
	/*
	 * for now hardcode the report name.
	 * we may want to discuss having a isw_report table
	 * that defines the supported reports.
	 */
	private static final String REPORT_NAME="MobDemobBySectionReport";

	public MobDemobBySectionReport(String subTitle,Collection<MobDemobBySectionReportData> data){
		super();
		super.setHeaderTitle("OPERATIONS SECTIONS - DIVISIONS");
		super.setHeaderSubTitle(subTitle);
		super.setReportData(data);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.report.IReport#getReportName()
	 */
	public String getReportName(){
		return MobDemobBySectionReport.REPORT_NAME;
	}
	
	
}
