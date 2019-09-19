/**
 * 
 */
package gov.nwcg.isuite.core.reports;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.data.TaskLockedAccountsReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

/**
 * @author mgreen
 *
 */
public class TaskLockedAccountsReport extends BaseReport implements IReport {

	private static final String REPORT_NAME = "TaskLockedAccountsReport";
	public static final String REPORT_TITLE = "Locked Accounts";
	
	/**
	 * 
	 */
	public TaskLockedAccountsReport(Collection<TaskLockedAccountsReportData> data) {

		super();
		super.setHeaderTitle(TaskLockedAccountsReport.REPORT_TITLE);
		super.setReportData(data);
	}
	
	public String getReportName() {
		return TaskLockedAccountsReport.REPORT_NAME;
	}

}
