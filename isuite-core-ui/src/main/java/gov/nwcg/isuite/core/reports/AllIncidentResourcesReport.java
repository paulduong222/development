package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.AllIncidentResourcesReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class AllIncidentResourcesReport extends BaseReport implements IReport{
	/*
	 * for now hardcode the report name.
	 * we may want to discuss having a isw_report table
	 * that defines the supported reports.
	 */
	private static final String REPORT_NAME="AllIncidentResourcesReport";

	public AllIncidentResourcesReport(Collection<AllIncidentResourcesReportData> data){
		super();
		super.setHeaderTitle("ALL RESOURCES REPORT");
		super.setReportData(data);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.report.IReport#getReportName()
	 */
	public String getReportName(){
		return AllIncidentResourcesReport.REPORT_NAME;
	}
	
	
}
