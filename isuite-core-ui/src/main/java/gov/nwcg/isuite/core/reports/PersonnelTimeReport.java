package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.PersonnelTimeReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class PersonnelTimeReport extends BaseReport implements IReport{
	/*
	 * for now hardcode the report name.
	 * we may want to discuss having a isw_report table
	 * that defines the supported reports.
	 */
	private static final String REPORT_NAME="PersonnelTimeReport";
	private static final String SUB_REPORT_NAME="PersonnelTimeReport_subreport1";

	public PersonnelTimeReport(String subTitle,Collection<PersonnelTimeReportData> data){
		super();
		super.setHeaderTitle("Report of Personnel Time For");
		super.setHeaderSubTitle(subTitle);
		super.setReportData(data);
		super.addSubReportName(SUB_REPORT_NAME);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.report.BaseReport#getReportName()
	 */
	public String getReportName(){
		return getReportNameWithSuffix(REPORT_NAME);
	}
	
	
}
