package gov.nwcg.isuite.framework.types;

import gov.nwcg.isuite.core.vo.TaskTypeVo;
import gov.nwcg.isuite.framework.core.task.EISuiteTask;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;


public enum TaskTypeEnum {
	   
	CHECK_USER_PASSWORD_EXPIRATION(
			1L,
			"CHECK_USER_PASSWORD_EXPIRATION",
			"gov.nwcg.isuite.core.task.CheckUserPasswordExpirationTask.class",
		   	"checkUserPasswordExpirationTask")
	,CHECK_LOCKED_ACCOUNTS(
			2L,
			"CHECK_LOCKED_ACCOUNTS", 
			"gov.nwcg.isuite.core.task.CheckLockedAccountsTask.class", 
			"checkLockedAccountsTask")
	,RUN_DAILY_COSTS(
			3L,
			"RUN_DAILY_COSTS", 
			"gov.nwcg.isuite.core.task.RunDailyCostTask.class", 
			"runDailyCostTask")
	,IMPORT_ROSS_RESOURCE_INV(
			4L,
			"IMPORT_ROSS_RESOURCE_INV", 
			"gov.nwcg.isuite.core.task.ImportRossResourceInventoryTask3.class", 
			"importRossResInvTask")
	,SITE_AUTO_DB_BACKUP(
			5L,
			"SITE_AUTO_DB_BACKUP", 
			"gov.nwcg.isuite.core.task.SiteAutoDbBackupTask.class", 
			"siteAutoDbBackupTask")
    ,IRWIN(
			6L,
			"IRWIN", 
			"gov.nwcg.isuite.core.task.IrwinTask.class", 
			"irwinTask")						
    ,PURGE_ENTERPRISE_REPORTS(
			7L,
			"PURGE_ENTERPRISE_REPORTS", 
			"gov.nwcg.isuite.core.task.PurgeReportsEnterpriseTask.class", 
			"purgeReportsEnterprise")						
    ,PURGE_SITE_REPORTS(
			8L,
			"PURGE_SITE_REPORTS", 
			"gov.nwcg.isuite.core.task.PurgeReportsSiteTask.class", 
			"purgeReportsSite")						
   ;
	
	
   private Long id;
   private String description="";
   private String className="";
   private String beanName="";
   
   TaskTypeEnum(Long id,String desc, String className, String beanName){
	   this.id=id;
	   this.description=desc;
	   this.className=className;
	   this.beanName=beanName;
   }

   public static TaskTypeVo getVo(TaskTypeEnum val){
	   if(null==val)
		   return null;
	   
	   for(TaskTypeVo vo : getTaskTypeVos()){
		   if(vo.getTaskType().equals(val.description)){
			   return vo;
		   }
	   }
	   return null;
   }
   
   public static TaskTypeEnum toEnumValue(TaskTypeVo vo){
	   if(null==vo || !StringUtility.hasValue(vo.getTaskType()))
		   return null;
	   else{
		   for(TaskTypeEnum task : TaskTypeEnum.values()){
			   if(vo.getTaskType().equals(task.description))
				   return task;
		   }
	   }
	   return null;
   }
   
   public static Collection<TaskTypeVo> getTaskTypeVos(){
	   Collection<TaskTypeVo> list = new ArrayList<TaskTypeVo>();

	   for(TaskTypeEnum task : TaskTypeEnum.values()){
		   list.add(TaskTypeVo.getInstance(task));
	   }
	   
	   return list;
   }
   
   public Long getId(){
	   return id;
   }
   
   /**
    * Returns the description of the task queue type.
    * 
    * @return
    * 	the description of the task queue type
    */
   public String getDescription(){
	   return this.description;
   }

   public String getBeanName(){
	   return this.beanName;
   }

   public EISuiteTask getInstance() throws Exception{
	   EISuiteTask task = null;

	   task = (EISuiteTask)Class.forName(className).newInstance();
	   
	   return task;
   }
}
