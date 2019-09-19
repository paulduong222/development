package gov.nwcg.isuite.core.task;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.reports.TaskLockedAccountsReport;
import gov.nwcg.isuite.core.reports.data.TaskLockedAccountsReportData;
import gov.nwcg.isuite.framework.core.task.BaseTask;
import gov.nwcg.isuite.framework.core.task.EISuiteTask;
import gov.nwcg.isuite.framework.exceptions.TaskException;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.report.ReportBuilder2;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.StringTokenizer;

import org.apache.commons.mail.EmailAttachment;

public class CheckedLockedAccountsTask extends BaseTask implements EISuiteTask {

	private Collection<User> users;
	
	@Override
	public int runScheduledTask() throws TaskException {
		
		System.out.println("in task");
		
		try {
		
			UserDao userDao = (UserDao)super.context.getBean("userDao");
//			users = userDao.getLockedAccounts();
		
			processTaskComplete();
			
		} catch(Exception e){
			super.handleException(e);
		}
		
		return 1;
	}
	
	@Override
	public void postTask() throws TaskException {

		try {

			if(users.size() > 0) {
				
				Collection<String> mailToEmails = new ArrayList<String>();
				String mailToString = super.getSystemParamValue(SystemParameterTypeEnum.TASK_CHECK_LOCKED_ACCTS_EMAIL_LIST);
				
				if(null != mailToString && !mailToString.equals("")) {
					
					StringTokenizer tokenizer = new StringTokenizer(mailToString,"|");
					while(tokenizer.hasMoreTokens()){
						mailToEmails.add((String)tokenizer.nextToken());
					}
					
					if(mailToEmails.size()>0) {
						
						Collection<TaskLockedAccountsReportData> data = new ArrayList<TaskLockedAccountsReportData>();
						for(User user : this.users) {
							
							TaskLockedAccountsReportData datum = new TaskLockedAccountsReportData();
							datum.setFirstName(user.getFirstName());
							datum.setLastName(user.getLastName());
							datum.setLoginName(user.getLoginName());
							datum.setEmail(user.getEmail());
							
							data.add(datum);
						}
						
						IReport report = new TaskLockedAccountsReport(data);
						ReportBuilder2 reportBuilder = new ReportBuilder2(super.servletContext);
						
						String urlContext = super.getSystemParamValue(SystemParameterTypeEnum.CONTEXT_ROOT);
						if(null != urlContext && !urlContext.isEmpty())
							reportBuilder.setUrlContext(urlContext);
						
						String fileName = "TaskLockedAccountsReport_" + Calendar.getInstance().getTimeInMillis()+".pdf";

						String outputFile = this.getOutputFile(fileName);

						// Create the pdf report. Set the name of the output file.
						reportBuilder.createPdfReport(report, outputFile);
						
						EmailAttachment attach = new EmailAttachment();
						attach.setPath(outputFile);
						
						super.emailTaskResults(
								mailToEmails
								, TaskLockedAccountsReport.REPORT_TITLE
								, "Attached is the " + TaskLockedAccountsReport.REPORT_TITLE + " processed on " + Calendar.getInstance().getTime() + "."
								, attach
								, "qa");
					}
				}
			}
			
		} catch(Exception e){
			super.handleException(e);
		}
		
	}		
}