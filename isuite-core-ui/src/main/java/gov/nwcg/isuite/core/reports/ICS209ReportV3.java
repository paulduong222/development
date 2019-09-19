package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.ICS209ReportV3Data;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class ICS209ReportV3 extends BaseReport implements IReport{

	private static final String REPORT_NAME="Form-ICS209v3";
    private static final String SUB_REPORT_NAME_1="Form-ICS209v3_subreport1";

	public ICS209ReportV3(String subTitle,Collection<ICS209ReportV3Data> data){
		super();
		super.setHeaderTitle("ICS-209 RESOURCE SUMMARY");
		super.setHeaderSubTitle(subTitle);
		super.setReportData(data);
		super.addSubReportName(SUB_REPORT_NAME_1);
}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.report.IReport#getReportName()
	 */
	public String getReportName(){
		return ICS209ReportV3.REPORT_NAME;
	}
	
	
}
