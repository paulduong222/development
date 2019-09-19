package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.DemobPlanningReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class DemobPlanningReport extends BaseReport implements IReport{
	/*
	 * for now hardcode the report name.
	 * we may want to discuss having a isw_report table
	 * that defines the supported reports.
	 */
	private static final String REPORT_NAME="DemobPlanningReport";
	
  public DemobPlanningReport(Collection<DemobPlanningReportData> data){
		super();
		super.setHeaderTitle("Demob Planning Report");
		//super.setHeaderSubTitle(subTitle);
		super.setReportData(data);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.report.IReport#getReportName()
	 */
	public String getReportName(){
		return DemobPlanningReport.REPORT_NAME;
	}
	
}
