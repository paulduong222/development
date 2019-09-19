package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.TaskUserPwdExpirationReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class TaskUserPwdExpirationReport extends BaseReport implements IReport{
	private static final String REPORT_NAME="TaskCheckUserPwdExpirationReport";

	public TaskUserPwdExpirationReport(String subTitle,Collection<TaskUserPwdExpirationReportData> data){
		super();
		super.setHeaderTitle("Daily User Password Expiration Report");
		super.setHeaderSubTitle(subTitle);
		super.setReportData(data);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.report.IReport#getReportName()
	 */
	public String getReportName(){
		return TaskUserPwdExpirationReport.REPORT_NAME;
	}
	
	
}
