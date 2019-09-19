package gov.nwcg.isuite.framework.core.task;

import gov.nwcg.isuite.core.domain.SystemNotification;
import gov.nwcg.isuite.core.domain.SystemNotifyUser;
import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.domain.TaskQueue;
import gov.nwcg.isuite.core.domain.TaskQueueLog;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.impl.TaskQueueLogImpl;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.core.persistence.SystemNotificationDao;
import gov.nwcg.isuite.core.persistence.SystemNotifyUserDao;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.persistence.TaskQueueDao;
import gov.nwcg.isuite.core.persistence.TaskQueueLogDao;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.IsuiteException;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.TaskException;
import gov.nwcg.isuite.framework.mail.MailProperties;
import gov.nwcg.isuite.framework.mail.Mailer;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.types.TaskFrequencyTypeEnum;
import gov.nwcg.isuite.framework.types.TaskStatusTypeEnum;
import gov.nwcg.isuite.framework.types.TaskTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletContext;

import org.apache.commons.mail.EmailAttachment;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;

public class BaseTask implements ApplicationContextAware, ServletContextAware {
	protected ApplicationContext context=null;
	protected TaskQueue taskQueueEntity=null;
	protected ServletContext servletContext;
	protected String taskReportFile=null;
	protected boolean skipScheduleNext=false;
	
	public void setApplicationContext(ApplicationContext ctx){
		this.context=ctx;
	}

	public void setTaskQueue(TaskQueue taskQueue){
		this.taskQueueEntity=taskQueue;
	}

	public TaskQueue getTaskQueue(){
		return this.taskQueueEntity;
	}

	protected GlobalCacheVo getGlobalCacheVo() throws ServiceException {
		return (GlobalCacheVo)context.getBean("globalCacheVo");
	}
	
	protected void handleException(Exception e) throws TaskException {
		TaskException te = new TaskException();

		if(e instanceof PersistenceException){
			// handle accordingly
			te = new TaskException(new ErrorObject(ErrorEnum._900006_PERSISTENCE_ERROR,e.getMessage()));
		}else if (e instanceof IsuiteException){
			te.setErrorObjects(((IsuiteException)e).getErrorObjects());
		}else {
			// Generic exception message
			te = new TaskException(new ErrorObject(ErrorEnum._90000_ERROR,e.getMessage()));
		}

		throw te;
	}

	protected void handleException(ErrorEnum error,String... params) throws TaskException {
		throw new TaskException(new ErrorObject(error,params));
	}

	protected void handleException(ErrorObject errorObject) throws TaskException {
		throw new TaskException(errorObject);
	}
	
	/**
	 * Returns the system parameter value if found.
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	protected String getSystemParamValue(SystemParameterTypeEnum paramName) throws Exception {
		
		SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");
		
		SystemParameter entity = spDao.getByParameterName(paramName.name());
		
		if(null == entity)
			this.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"SystemParameter["+paramName.name()+"]");
		
		return entity.getParameterValue();
	}

	/**
	 * 
	 * Need to determine the action to take regarding the end of the task run.
	 * Some tasks are only ran onetime, while others may need to be rescheduled
	 * for another run.
	 * 
	 * @throws Exception
	 */
	protected void processTaskComplete() throws Exception {
		TaskQueueDao taskQueueDao = (TaskQueueDao)this.context.getBean("taskQueueDao");
		TaskQueueLogDao taskQueueLogDao = (TaskQueueLogDao)this.context.getBean("taskQueueLogDao");
		
		// create the tq log for this task run
		if (taskQueueEntity.getTaskType()!=TaskTypeEnum.IRWIN) {
			TaskQueueLog taskQueueLogEntity = buildTaskQueueLog(taskQueueEntity);
			taskQueueLogDao.save(taskQueueLogEntity);
		}
		
		/*
		 * determine what to do with the completed task
		 */
		if(taskQueueEntity.getFrequency()==TaskFrequencyTypeEnum.ONETIME){
			/*
			 * if onetime, the task is now complete
			 */
			
			if (taskQueueEntity.getTaskType()==TaskTypeEnum.IRWIN
					|| taskQueueEntity.getTaskType()==TaskTypeEnum.SITE_AUTO_DB_BACKUP) {
				taskQueueEntity.setStatus(null);
			}
			else {
				taskQueueEntity.setStatus(TaskStatusTypeEnum.COMPLETED);
				taskQueueEntity.setDeactivatedDate(Calendar.getInstance().getTime());
			}
		}else if(taskQueueEntity.getFrequency()==TaskFrequencyTypeEnum.DAILY){
			/*
			 * schedule task for next day, if we have not hit the frequency term task runs
			 */
			long daysSince = DateUtil.diffDays(taskQueueEntity.getInitialRunDate(), Calendar.getInstance().getTime());
			if(daysSince < taskQueueEntity.getFrequencyTerm().longValue()){
				taskQueueEntity.setStatus(null);
				/*
				 * add 1 day to either initialrundate or nextscheduleddate
				 */
				if(skipScheduleNext==false){
					Date nextRunDate = (null != taskQueueEntity.getNextScheduledDate() ? DateUtil.addDays(taskQueueEntity.getNextScheduledDate(),1) : DateUtil.addDays(taskQueueEntity.getInitialRunDate(),1));
					taskQueueEntity.setNextScheduledDate(nextRunDate);
				}
			}else{
				/*
				 * task has ran the amount in the frequency term 
				 */
				taskQueueEntity.setStatus(TaskStatusTypeEnum.COMPLETED);
				taskQueueEntity.setDeactivatedDate(Calendar.getInstance().getTime());
			}
		}

		if(skipScheduleNext==false){
			taskQueueEntity.setLastRunDate(Calendar.getInstance().getTime());
		}		
		taskQueueDao.save(taskQueueEntity);
	}
	
    private TaskQueueLog buildTaskQueueLog(TaskQueue tq){
    	TaskQueueLog entity = new TaskQueueLogImpl();
    	entity.setStartDate(Calendar.getInstance().getTime());
    	entity.setStatus("COMPLETED");
    	entity.setStatusMessage("");
    	entity.setTaskQueue(tq);
    	return entity;
    }
	
	protected MailProperties getMailProperties(String acct) throws Exception {
		MailProperties props = new MailProperties();

		props.setHostname(getSystemParamValue(SystemParameterTypeEnum.MAIL_SERVER_HOSTNAME ));
		
		MailProperties.MailAddress mailFrom = props.getMailAddressInstance();
		mailFrom.emailAddress=acct+"@nn350ap002.nwcg.gov";
		mailFrom.firstName="E-ISuite";
		mailFrom.lastName=acct+" System";
		props.setMailFrom(mailFrom);
		return props;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
	 */
	@Override
	public void setServletContext(ServletContext ctx) {
		servletContext=ctx;
	}

	protected String getDestinationFileName() throws Exception {

		String timestamp=String.valueOf(Calendar.getInstance().getTimeInMillis());

		return "rpt"+timestamp+".pdf";
	}

	protected String getOutputFile(String fileName) throws Exception {
		SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");

		try{
			SystemParameter spEntity = spDao.getByParameterName("REPORT_OUTPUT_FOLDER");

			if( (null != spEntity) && ( null != spEntity.getParameterValue()) && (!spEntity.getParameterValue().isEmpty()) ){
				return spEntity.getParameterValue() + fileName;
			}else{
				return fileName;
			}

		}catch(Exception e){
			throw e;
		}
	}

	protected String getRossXmlFolder(String fileName) throws Exception {
		SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");

		try{
			SystemParameter spEntity = spDao.getByParameterName("ROSS_XML_FOLDER");

			if( (null != spEntity) && ( null != spEntity.getParameterValue()) && (!spEntity.getParameterValue().isEmpty()) ){
				return spEntity.getParameterValue() + fileName;
			}else{
				return fileName;
			}

		}catch(Exception e){
			throw e;
		}
	}

	public void emailTaskResults(Collection<String> emailAddresses
						, String subject, String message, String acct) throws TaskException {
	
		try {
		
			MailProperties props = getMailProperties(acct);
			props.setMailToList(getMailToList(emailAddresses));
			props.setSubject(subject);
			props.setMessage(message);
			Mailer.sendEmail(props);
			
		} catch (Exception ex) {
			throw new TaskException(ex.getMessage());
		}
	}
	
	public void emailTaskResults(Collection<String> emailAddresses
			, String subject, String message, EmailAttachment attachment, String acct) throws TaskException {

		try {
			
			MailProperties props = getMailProperties(acct);
			props.setMailToList(getMailToList(emailAddresses));
			props.setSubject(subject);
			props.setMessage(message);
			props.addAttachment(attachment);
			Mailer.sendEmail(props);
			
		} catch (Exception ex) {
			throw new TaskException(ex.getMessage());
		}
	}
	
	private Collection<MailProperties.MailAddress> getMailToList(Collection<String> emailAddresses) {
		
		Collection<MailProperties.MailAddress> mailToList = new ArrayList();
		
		for(String s : emailAddresses) {
			
			MailProperties.MailAddress mailTo = new MailProperties().getMailAddressInstance();
			mailTo.emailAddress = s;
			mailToList.add(mailTo);
		}
		
		return mailToList;
	}
	
	/**
	 * @param taskQueueId
	 * @return
	 * @throws Exception
	 */
	protected Collection<SystemNotification> getSystemNotificationForTask(Long taskQueueId) throws Exception {
		
		SystemNotificationDao systemNotificationDao = (SystemNotificationDao)context.getBean("systemNotificationDao");
		
		Collection<SystemNotification> sysNotifications = systemNotificationDao.getByTaskQueueId(taskQueueId);
		
		return sysNotifications;
	}
	
	/**
	 * @param systemNotificationId
	 * @return
	 * @throws Exception
	 */
	protected Collection<SystemNotifyUser> getSystemNotifyUsers(Long systemNotificationId) throws Exception {
		SystemNotifyUserDao systemNotifyUserDao = (SystemNotifyUserDao)context.getBean("systemNotifyUserDao");
		
		Collection<SystemNotifyUser> sysNotifyUsers= systemNotifyUserDao.getBySystemNotificationId(systemNotificationId);
		
		return sysNotifyUsers;
		
	}
	
	/**
	 * @throws Exception
	 */
	protected void tmpprocessTaskSystemNotifications() throws Exception {
		
		Collection<SystemNotification> systemNotifications = getSystemNotificationForTask(taskQueueEntity.getId());
		
		for(SystemNotification sysNotification : systemNotifications){
			
			if(sysNotification.getType().equals("EMAIL")){
		
				// get list of notify users
				Collection<SystemNotifyUser> systemNotifyUsers = getSystemNotifyUsers(sysNotification.getId());
				
				UserDao userDao = (UserDao)context.getBean("userDao");

				MailProperties props = getMailProperties("todo");
				Collection<MailProperties.MailAddress> mailToList = new ArrayList();
				
				for(SystemNotifyUser snu : systemNotifyUsers){
					User user=userDao.getById(snu.getUserId(), UserImpl.class);
					
					if(null != user && (StringUtility.hasValue(user.getEmail()))){
						MailProperties.MailAddress mailTo = props.getMailAddressInstance();
						mailTo.emailAddress=user.getEmail();
						mailToList.add(mailTo);
					}
				}
				if(mailToList.size()>0){
					props.setMailToList(mailToList);
					
					props.setSubject(sysNotification.getSubject());
					props.setMessage(sysNotification.getMessage());

					if(null != taskReportFile){
						EmailAttachment attach = new EmailAttachment();
						attach.setPath( taskReportFile);
						props.addAttachment(attach);
					}
					
					Mailer.sendEmail(props);
				}
				
			}
		}
	}

	protected void writeLog(String exportFileLog, Collection<String> logEntries) throws Exception {
		BufferedWriter appender = null;
		String timestamp=String.valueOf(Calendar.getInstance().getTimeInMillis());
		
		try{
			FileUtil.createFile(exportFileLog);
			appender = FileUtil.getFileAppender(exportFileLog);
			
			for(String s : logEntries){
				appender.write(s);
				appender.newLine();
			}
		}catch(Exception ignore){
			
		}finally{
			if(null != appender)
				appender.close();
		}
		
	}
	
}
