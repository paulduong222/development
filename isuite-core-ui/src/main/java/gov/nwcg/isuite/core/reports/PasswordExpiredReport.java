package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.PasswordExpiredReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class PasswordExpiredReport extends BaseReport implements IReport{
	/*
	 * for now hardcode the report name.
	 * we may want to discuss having a isw_report table
	 * that defines the supported reports.
	 */
	private static final String REPORT_NAME="Users Who's Password Expired";

	public PasswordExpiredReport(String subTitle,Collection<PasswordExpiredReportData> data){
		super();
		super.setHeaderTitle("Users Who's Password Expired");
		super.setHeaderSubTitle(subTitle);
		super.setReportData(data);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.report.IReport#getReportName()
	 */
	public String getReportName(){
		return PasswordExpiredReport.REPORT_NAME;
	}
	
	
}
