package gov.nwcg.isuite.core.task;

import gov.nwcg.isuite.core.persistence.ReportDao;
import gov.nwcg.isuite.framework.core.task.BaseTask;
import gov.nwcg.isuite.framework.core.task.EISuiteTask;
import gov.nwcg.isuite.framework.exceptions.TaskException;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.FileUtil;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.transaction.annotation.Transactional;


@Transactional
public class PurgeReportsEnterpriseTask extends BaseTask implements EISuiteTask {

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.tasks.EISuiteTask#runScheduledTask()
	 */
	public int runScheduledTask() throws TaskException {

		/*
		 * task record
			insert into isw_task_queue (id, user_id, task_type, frequency, frequency_term, initial_run_date, next_scheduled_date)
			values (seq_taskqueue.nextVal,null,'PURGE_ENTERPRISE_REPORTS','DAILY',360, to_timestamp('7/17/2015 01:00','MM/DD/YYYY HH24:MI'),to_timestamp('7/17/2015 01:00','MM/DD/YYYY HH24:MI'));
			
			commit;
		 */
		try{
			
			ReportDao dao = (ReportDao)context.getBean("reportDao");
			Date dt = Calendar.getInstance().getTime();
			dt=DateUtil.subtractDaysFromDate(dt, 2);
			String beforeDate=DateUtil.toDateString(dt, DateUtil.MM_SLASH_DD_SLASH_YYYY);

			// get all report records that are non invoiced records
			Collection<String> fileNames=dao.getObsoleteReportFilenames(beforeDate);
			
			if(CollectionUtility.hasValue(fileNames)){
				String reportsFolder=super.getSystemParamValue(SystemParameterTypeEnum.REPORT_OUTPUT_FOLDER);
				
				for(String s : fileNames){
					if(FileUtil.fileExists(reportsFolder+s)){
						FileUtil.deleteFile(reportsFolder+s);
					}
				}
				
				// delete these records from the report table
				dao.deleteObsoleteReports(beforeDate);
			}

			
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
			
		}catch(Exception e){
			throw new TaskException(e.getMessage());
		}
		
	}

}
