package gov.nwcg.isuite.core.task;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.reports.TaskUserPwdExpirationReport;
import gov.nwcg.isuite.core.reports.data.TaskUserPwdExpirationReportData;
import gov.nwcg.isuite.framework.core.task.BaseTask;
import gov.nwcg.isuite.framework.core.task.EISuiteTask;
import gov.nwcg.isuite.framework.exceptions.TaskException;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.report.ReportBuilder2;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

/**
 * @author dprice
 *
 */
public class CheckUserPasswordExpirationTask extends BaseTask implements EISuiteTask {
	private Collection<User> users = new ArrayList<User>();
	private int passwordExpireDays=0;
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.tasks.EISuiteTask#runScheduledTask()
	 */
	public int runScheduledTask() throws TaskException {
		try{
			UserDao userDao = (UserDao)super.context.getBean("userDao");
			
			/*
			 * Get the password expiration days from the database
			 */
			int pwdDays=Integer.parseInt(super.getSystemParamValue(SystemParameterTypeEnum.PWD_EXPIRE_TIME));
			passwordExpireDays=pwdDays;
			
			Collection<User> entities=userDao.getUserPasswordExpirations(pwdDays,Calendar.getInstance().getTime());
			
			for(User entity : entities){
				entity.setEnabled(false);
				users.add(entity);
			}
			
			if(users.size()>0)
				userDao.saveAll(users);

			processTaskComplete();
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return 1;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.task.EISuiteTask#postTask()
	 */
	public void postTask() throws TaskException{

		try{
			
			Collection<TaskUserPwdExpirationReportData> data = new ArrayList<TaskUserPwdExpirationReportData>();
			for(User user : users){
				TaskUserPwdExpirationReportData rd = new TaskUserPwdExpirationReportData();
				rd.setLoginName(user.getLoginName());
				rd.setUserName(user.getFirstName()+" " + user.getLastName());
				rd.setExpirationDate(DateUtil.toDateString(DateUtil.addDays(user.getPasswordCreatedDate(), this.passwordExpireDays),DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS));
							
				data.add(rd);
			}
						
			if(data.size()<1){
				TaskUserPwdExpirationReportData rd = new TaskUserPwdExpirationReportData();
				rd.setLoginName("No report data available");
				rd.setUserName("N/A");
				rd.setExpirationDate("N/A");
							
				data.add(rd);
			}
						
			IReport report = new TaskUserPwdExpirationReport("",data);
						
			ReportBuilder2 reportBuilder = new ReportBuilder2(super.servletContext);
						
			String urlContext = super.getSystemParamValue(SystemParameterTypeEnum.CONTEXT_ROOT);
			if(null != urlContext && !urlContext.isEmpty())
				reportBuilder.setUrlContext(urlContext);
						
			String fileName = "CheckUserPwdExpirationReport_" + Calendar.getInstance().getTimeInMillis()+".pdf";
	
			String outputFile = this.getOutputFile(fileName);
	
			// Create the pdf report. Set the name of the output file.
			reportBuilder.createPdfReport(report, outputFile);

			super.taskReportFile=outputFile;

			//super.processTaskSystemNotifications();
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw new TaskException(e.getMessage());
		}
		
	}
	
}
