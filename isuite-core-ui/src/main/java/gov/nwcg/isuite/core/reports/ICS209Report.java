package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.ICS209ReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class ICS209Report extends BaseReport implements IReport{
	/*
	 * for now hardcode the report name.
	 * we may want to discuss having a isw_report table
	 * that defines the supported reports.
	 */
	private static final String REPORT_NAME="Form-ICS209";

	public ICS209Report(String subTitle,Collection<ICS209ReportData> data){
		super();
		super.setHeaderTitle("ICS-209 INFORMATION FOR BLOCK 29");
		super.setHeaderSubTitle(subTitle);
		super.setReportData(data);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.report.IReport#getReportName()
	 */
	public String getReportName(){
		return ICS209Report.REPORT_NAME;
	}
	
	
}
