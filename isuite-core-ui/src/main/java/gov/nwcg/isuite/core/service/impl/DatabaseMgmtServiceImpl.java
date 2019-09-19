package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.DataAuditConfig;
import gov.nwcg.isuite.core.domain.DataAuditTracking;
import gov.nwcg.isuite.core.domain.DbAvail;
import gov.nwcg.isuite.core.domain.PasswordHistory;
import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.impl.DataAuditTrackingImpl;
import gov.nwcg.isuite.core.domain.impl.DbAvailImpl;
import gov.nwcg.isuite.core.domain.impl.PasswordHistoryImpl;
import gov.nwcg.isuite.core.domain.impl.SystemParameterImpl;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.core.persistence.DataAuditConfigDao;
import gov.nwcg.isuite.core.persistence.DataAuditTrackingDao;
import gov.nwcg.isuite.core.persistence.DbAvailDao;
import gov.nwcg.isuite.core.persistence.IapAttachmentDao;
import gov.nwcg.isuite.core.persistence.PasswordHistoryDao;
import gov.nwcg.isuite.core.persistence.SystemDao;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.persistence.TimeInvoiceDao;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.rules.DbManagementBackupRulesHandler;
import gov.nwcg.isuite.core.rules.DbManagementCopyRulesHandler;
import gov.nwcg.isuite.core.rules.DbManagementDeleteRulesHandler;
import gov.nwcg.isuite.core.rules.DbManagementRestoreRulesHandler;
import gov.nwcg.isuite.core.rules.DbManagementSaveRulesHandler;
import gov.nwcg.isuite.core.service.DatabaseMgmtService;
import gov.nwcg.isuite.core.service.UserSessionManagementService2;
import gov.nwcg.isuite.core.vo.DbAvailVo;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;
import gov.nwcg.isuite.framework.dbutil.RecoverDbPasswordUtil;
import gov.nwcg.isuite.framework.dbutil.pg.BackupDatabaseUtil;
import gov.nwcg.isuite.framework.dbutil.pg.CopyDatabaseUtil;
import gov.nwcg.isuite.framework.dbutil.pg.DeleteDatabaseUtil;
import gov.nwcg.isuite.framework.dbutil.pg.RestoreDatabaseUtil;
import gov.nwcg.isuite.framework.dbutil.postprocess.RestoreDbPostProcess;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.DataAuditEvent;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.DatasourceUsageUtil;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.io.BufferedWriter;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.ibm.xml.crypto.util.Base64;

public class DatabaseMgmtServiceImpl extends BaseService implements DatabaseMgmtService {
	private DbAvailDao dbAvailDao=null;
	private String pgBaseDir="";
	private String nwcgFolder="";
	private String nwcgBackupFolder="";
	private String nwcgPatchFolder="";
	
	public enum DataSourceEnum {
		default0DataSource
		,default1DataSource
		,default2DataSource
		,default3DataSource
		,default4DataSource
		,default5DataSource
		,default6DataSource
		,default7DataSource
		,default8DataSource
		,default9DataSource
		,default10DataSource
		,default11DataSource
		,default12DataSource
		,default13DataSource
		,default14DataSource
		,default15DataSource
		,default16DataSource
		,default17DataSource
		,default18DataSource
		,default19DataSource
		,default20DataSource
	}
	
	public DatabaseMgmtServiceImpl() {

	}

	public void initialization() {
		dbAvailDao = (DbAvailDao)context.getBean("dbAvailDao");
	}

	private void loadNwcgFolderPaths() throws Exception{
		try{
			//String appPath=super.servletContext.getRealPath(File.separator);
			//System.out.println(appPath);
			this.pgBaseDir=super.getSystemParamValue(SystemParameterTypeEnum.PG_BASE_DIR);
			this.nwcgFolder=super.getSystemParamValue(SystemParameterTypeEnum.NWCG_FOLDER);
			this.nwcgBackupFolder=super.getSystemParamValue(SystemParameterTypeEnum.NWCG_BACKUP_FOLDER);
			this.nwcgPatchFolder=super.getSystemParamValue(SystemParameterTypeEnum.NWCG_PATCH_FOLDER);
			
		}catch(Exception e){
			throw e;
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.DatabaseMgmtService#saveDb(gov.nwcg.isuite.core.vo.DbAvailVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveDb(DbAvailVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		DbAvail entity =null;
		
		try{
			Boolean isNew=false;
			String originalDatabaseName="";
			String newDatabaseName="";
			
			if(LongUtility.hasValue(vo.getId())){
				((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("YES");
				entity=dbAvailDao.getById(vo.getId(), DbAvailImpl.class);
				originalDatabaseName=entity.getName();
				//this.dbAvailDao.flushAndEvict(entity);
				((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
			}else{
				isNew=true;
			}
			
			// init rulehandler and check rules
			DbManagementSaveRulesHandler ruleHandler = new DbManagementSaveRulesHandler(context);
			if(ruleHandler.execute(entity,vo, dialogueVo)==AbstractRule._OK){
				String ds="";
				
				newDatabaseName=vo.getDatabaseName().toLowerCase();
				
				if(!LongUtility.hasValue(vo.getId())){
					isNew=true;
					
					// get next datasource
					ds=this.getNextDatasource();
					if(!StringUtility.hasValue(ds)){
						// handle this
					}else
						vo.setDatasource(ds);

					if(!DatasourceUsageUtil.datasourcesUsed.contains(ds)){
						DatasourceUsageUtil.datasourcesUsed.add(ds);
					}
					
					// load nwcg folder paths from system parameter table
					this.loadNwcgFolderPaths();
					
					// create new database based on isuite_site_master
					CopyDatabaseUtil utility = new CopyDatabaseUtil(super.context);

					utility.pgBaseDir=this.pgBaseDir;
					utility.nwcgFolder=this.nwcgFolder;
					utility.nwcgBackupFolder=this.nwcgBackupFolder;
					utility.nwcgPatchFolder=this.nwcgPatchFolder;
					
					DbAvailVo sourceVo = new DbAvailVo();
					sourceVo.setDatabaseName("isuite_site_master");
					DbAvailVo targetVo = new DbAvailVo();
					targetVo.setDatabaseName(vo.getDatabaseName().toLowerCase());
					int rtnval=utility.copyDatabase(sourceVo, targetVo);
					Thread.sleep(3000);			
					
					//CreateDatabaseUtil utility = new CreateDatabaseUtil(super.context);
					//utility.createDatabase(vo);
				}
				
				// save the dbavail entity
				entity = DbAvailVo.toEntity(entity, vo, true);
				((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("YES");

				if(isNew==true){
					entity.setLastAutoBackupDate(Calendar.getInstance().getTime());
				}
				
				this.dbAvailDao.save(entity);
				//this.dbAvailDao.flushAndEvict(entity);
				((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
				DbAvailVo updatedVo = DbAvailVo.getInstance(entity, true);

				// update datasource usage
				GlobalCacheVo gcVo = (GlobalCacheVo)context.getBean("globalCacheVo");
				Collection<String> dataSourceConnectionsUsed = gcVo.getDataSourceConnectionsUsed();
				dataSourceConnectionsUsed.add(vo.getDatasource());
				((GlobalCacheVo)context.getBean("globalCacheVo")).setDataSourceConnectionsUsed(dataSourceConnectionsUsed);
				
				Long newUserId=0L;
				
				if(isNew==true){
					Thread.sleep(1000);			
					// update datasource map
					((BasicDataSource)context.getBean(vo.getDatasource())).setUrl("jdbc:postgresql://127.0.0.1/"+vo.getDatabaseName());
					Thread.sleep(2000);			
				
					// create initial ad user in new database
					Long userId=super.getUserVo().getId();
			    	UserSessionManagementService2 usms2 = (UserSessionManagementService2)context.getBean("userSessionManagementService2");
					
					if(LongUtility.hasValue(userId)){
						// end current session for this user
				    	UserVo oldUserVo = new UserVo();
				    	oldUserVo.setId(((UserSessionVo)context.getBean("userSessionVo")).getUserId());
				    	
						DialogueVo dvo2 = usms2.endSession(oldUserVo
															, ((UserSessionVo)context.getBean("userSessionVo")).getSiteDatabaseName()
															, "USER_LOGOUT"
															, new DialogueVo());

						UserDao userDao=(UserDao)context.getBean("userDao");
						User userEntity=userDao.getById(userId, UserImpl.class);
						UserVo userVo = UserVo.getInstance(userEntity, true);
						userDao.flushAndEvict(userEntity);
						UserVo newUserVo=this.createDefaultUser(userVo, userEntity.getPassword(),vo.getDatasource());
						newUserId=newUserVo.getId();
						// prepare new db for site mode
						this.prepareNewDb(newUserVo,vo.getDatasource());
						((UserSessionVo)context.getBean("userSessionVo")).setUserId(newUserId);
					}
					((UserSessionVo)context.getBean("userSessionVo")).setSiteDatasourceName(ds);
					((UserSessionVo)context.getBean("userSessionVo")).setSiteDatabaseName(vo.getDatabaseName().toLowerCase());
					Thread.sleep(1000);

					if(LongUtility.hasValue(userId)){
						// start new session for restored database
						UserSessionVo usvo = (UserSessionVo)this.context.getBean("userSessionVo");
						usvo.setUserId(newUserId);
						DialogueVo dvo3=usms2.startSession(usvo, new DialogueVo());
					}
					
					((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
				}else if(isNew==false && !originalDatabaseName.equals(newDatabaseName)){
					((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("YES");
					this.dbAvailDao.updateDatabaseName(originalDatabaseName, newDatabaseName);
					((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
					
					Thread.sleep(1000);			
					// user changed the database name
					((BasicDataSource)context.getBean(vo.getDatasource())).setUrl("jdbc:postgresql://127.0.0.1/"+vo.getDatabaseName().toLowerCase());
					Thread.sleep(2000);			
				}
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_DB");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.database", "info.0030.01" , new String[]{"database "}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(updatedVo);
				dialogueVo.setResultObjectAlternate4(newUserId);
			}
			
		} catch (Exception e) {
			super.dialogueException(dialogueVo, e);
		} finally{
			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
			if(null != entity){
				try{
					this.dbAvailDao.flushAndEvict(entity);
				}catch(Exception smother){}
			}
		}

		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nwcg.isuite.core.service.DatabaseMgmtService#backupDb(gov.nwcg.isuite
	 * .core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo backupDb(DbAvailVo vo, String destFileName, String altDestination,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			String backupDir = "\\NWCG-Backups\\";

			// init rulehandler and check rules
			DbManagementBackupRulesHandler ruleHandler = new DbManagementBackupRulesHandler(context);
			if(ruleHandler.execute(vo,destFileName,altDestination,dialogueVo)==AbstractRule._OK){

				String systemPatchLevel="";
				
				((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("YES");
				Thread.sleep(1000);			
				
				// get db credential
				DbAvail entity = this.dbAvailDao.getById(vo.getId(), DbAvailImpl.class);
				String credential=entity.getPasswordHash();
				
				SystemDao systemDao = (SystemDao)context.getBean("systemDao");
				systemPatchLevel=systemDao.getRevisionLevel();
				
				((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
				Thread.sleep(1000);			

			
				BackupDatabaseUtil util = new BackupDatabaseUtil(context);

				// load nwcg folder paths from system parameter table
				this.loadNwcgFolderPaths();
				util.pgBaseDir=this.pgBaseDir;
				util.nwcgFolder=this.nwcgFolder;
				util.nwcgBackupFolder=this.nwcgBackupFolder;
				util.nwcgPatchFolder=this.nwcgPatchFolder;
				
				
				String bdsOrig="jdbc:postgresql://127.0.0.1/"+super.getUserSessionVo().getSiteDatabaseName().toLowerCase();
				//((BasicDataSource)context.getBean(super.getUserSessionVo().getSiteDatasourceName())).getUrl();
				// get all invoice pdf filenames
				((BasicDataSource)context.getBean(vo.getDatasource())).setUrl("jdbc:postgresql://127.0.0.1/"+vo.getDatabaseName().toLowerCase());
				TimeInvoiceDao tiDao = (TimeInvoiceDao)context.getBean("timeInvoiceDao");
				IapAttachmentDao iapAttachDao = (IapAttachmentDao)context.getBean("iapAttachmentDao");
				
				Collection<String> invoiceFilenames = util.getAllInvoiceFileNames(vo.getDatabaseName().toLowerCase());
				Collection<String> iapAttachFilenames = util.getIapAttachments(vo.getDatabaseName().toLowerCase());
				
				Collection<String> pdfFileNames=new ArrayList<String>();
				pdfFileNames.addAll(iapAttachFilenames);
				pdfFileNames.addAll(invoiceFilenames);

				//Collection<String> invoiceFilenames = tiDao.getAllInvoiceFileNames();
				//Collection<String> iapAttachments = iapAttachDao.getIapAttachmentFilenames();
				//Collection<String> pdfList = new ArrayList<String>();
				//pdfList.addAll(invoiceFilenames);
				//pdfList.addAll(iapAttachments);
				((BasicDataSource)context.getBean(vo.getDatasource())).setUrl(bdsOrig);
				
				String reportOutputFolder = super.getSystemParamValue(SystemParameterTypeEnum.REPORT_OUTPUT_FOLDER);
				
				util.reportOutputFolder = reportOutputFolder;
				util.pdfFilenames=pdfFileNames;
				util.backupDatabase(vo, credential,destFileName, systemPatchLevel);
				
				Collection<String> altDestLog = new ArrayList<String>();
				if(StringUtility.hasValue(altDestination)){
					altDestLog.add("AltDestination:" + altDestination);
					
					// copy the backup file to backup dest folder
					String source=nwcgBackupFolder+destFileName.trim()+".bak";

					altDestLog.add("SourceFile:" + nwcgBackupFolder+destFileName.trim()+".bak");
					
					String backupDir2=altDestination; //vo.getAdditionalBackupDestination();
					if(!backupDir2.endsWith("\\")){
						backupDir2=backupDir2+File.separator;
					}
					
					altDestLog.add("AdjustedBackupDir2:" + backupDir2);
					
					String target=backupDir2+destFileName.trim()+".bak";

					altDestLog.add("Target:" + target);
					
					try{
						FileUtil.copyFile(source, target);
						altDestLog.add("Copy Completed");
					}catch(Exception ee){
						altDestLog.add("Exception: " + ee.getMessage());
						//System.out.println(ee.getMessage());
					}
					
					BufferedWriter appender = null;
					String timestamp=String.valueOf(Calendar.getInstance().getTimeInMillis());
					String outputFileLog=backupDir+"AltBackup_"+timestamp+".log";
					
					try{
						FileUtil.createFile(outputFileLog);
						appender = FileUtil.getFileAppender(outputFileLog);
						
						for(String s : altDestLog){
							appender.write(s);
							appender.newLine();
						}
					}catch(Exception ignore){
						
					}finally{
						if(null != appender)
							appender.close();
					}
				}
				
				// Create the backup audit data record (only if same db as login session)
				if(super.getUserSessionVo().getSiteDatabaseName().equalsIgnoreCase(entity.getName())){
					DataAuditConfigDao dacDao = (DataAuditConfigDao)context.getBean("dataAuditConfigDao");
					DataAuditTrackingDao datDao = (DataAuditTrackingDao)context.getBean("dataAuditTrackingDao");
					DataAuditConfig dac = null;
					try{
						dac=dacDao.getByEventName("", DataAuditEvent.BACKUP_COMPLETED.name());
					}catch(Exception e){
						//smother
					}
					DataAuditTracking dataAuditTracking = new DataAuditTrackingImpl();
					dataAuditTracking.setDataAuditConfig(dac);
					dataAuditTracking.setTablePrimaryKeyId(entity.getId());
					dataAuditTracking.setChangeDate(Calendar.getInstance().getTime());
					dataAuditTracking.setAuditField1(backupDir); // backup file path
					dataAuditTracking.setAuditField2(destFileName.trim()); // backup file name
					dataAuditTracking.setAuditField3("MANUAL BACKUP"); // backup type
					dataAuditTracking.setAuditField4(super.getUserSessionVo().getUserLoginName()); // user name
					dataAuditTracking.setAuditField5("");
					dataAuditTracking.setUserName(super.getUserSessionVo().getUserLoginName());
					dataAuditTracking.setOldValue("");
					dataAuditTracking.setNewValue("BACKUP COMPLETED");
					
					datDao.save(dataAuditTracking);
					datDao.flushAndEvict(dataAuditTracking);
				}
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("BACKUP_DB");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.database", "info.0030.02" , new String[]{"The database backup was completed."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
			
			
		} catch (Exception e) {
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.DatabaseMgmtService#copyDb(gov.nwcg.isuite.core.vo.DbAvailVo, gov.nwcg.isuite.core.vo.DbAvailVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo copyDb(DbAvailVo sourceVo,DbAvailVo targetVo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{

			// init rulehandler and check rules
			DbManagementCopyRulesHandler ruleHandler = new DbManagementCopyRulesHandler(context);
			if(ruleHandler.execute(sourceVo,targetVo,dialogueVo)==AbstractRule._OK){
				
				// get next datasource
				String ds=this.getNextDatasource();
				if(!StringUtility.hasValue(ds)){
					// handle this
				}else
					targetVo.setDatasource(ds);

				if(!DatasourceUsageUtil.datasourcesUsed.contains(ds)){
					DatasourceUsageUtil.datasourcesUsed.add(ds);
				}
				
				// run the copy database utility 
				CopyDatabaseUtil utility = new CopyDatabaseUtil(super.context);
				
				// load nwcg folder paths from system parameter table
				this.loadNwcgFolderPaths();
				utility.pgBaseDir=this.pgBaseDir;
				utility.nwcgFolder=this.nwcgFolder;
				utility.nwcgBackupFolder=this.nwcgBackupFolder;
				utility.nwcgPatchFolder=this.nwcgPatchFolder;
				
				utility.copyDatabase(sourceVo, targetVo);
	
				((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("YES");
				Thread.sleep(1000);			
				DbAvail entity = this.dbAvailDao.getById(sourceVo.getId(), DbAvailImpl.class);
				this.dbAvailDao.flushAndEvict(entity);
				DbAvail entity2=DbAvailVo.toEntity(null,targetVo, true);
				entity2.setMasterDb(sourceVo.getDatabaseName().toLowerCase());
				entity2.setDatasource(ds);
				this.dbAvailDao.save(entity2);
				this.dbAvailDao.flushAndEvict(entity2);
				((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
				Thread.sleep(1000);			
				DbAvailVo updatedVo = DbAvailVo.getInstance(entity2, true);

				// update datasource map
				((BasicDataSource)context.getBean(updatedVo.getDatasource())).setUrl("jdbc:postgresql://127.0.0.1/"+updatedVo.getDatabaseName().toLowerCase());
				Thread.sleep(2000);			

				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("COPY_DB");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.database", "info.generic" , new String[]{"The database was copied."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(updatedVo);
				
			}
			
			
		} catch (Exception e) {
			super.dialogueException(dialogueVo, e);
		}finally{
			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
		}

		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nwcg.isuite.core.service.DatabaseMgmtService#getGrid(gov.nwcg.isuite
	 * .core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getGrid(DialogueVo dialogueVo) throws ServiceException {
		if (null == dialogueVo)
			dialogueVo = new DialogueVo();

		try {
			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("YES");
			Thread.sleep(1000);			
			Collection<DbAvail> entities = dbAvailDao.getAll();
			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
			Thread.sleep(1000);			
			
			Collection<DbAvailVo> vos = DbAvailVo.getInstances(entities, true);
			
			for(DbAvail dba : entities){
				dbAvailDao.flushAndEvict(dba);
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_DATABASE_LIST");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setIsDialogueEnding(true);
			
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);

		} catch (final Exception e) {
			super.dialogueException(dialogueVo, e);
		} finally{
			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
		}

		return dialogueVo;
	}

	public DialogueVo restoreDb(String filename, String targetDbName, String pwd, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{

			// init rulehandler and check rules
			DbManagementRestoreRulesHandler ruleHandler = new DbManagementRestoreRulesHandler(context);
			if(ruleHandler.execute(filename,targetDbName, pwd,dialogueVo)==AbstractRule._OK){
				String dbpwd="";

				String reportOutputFolder = super.getSystemParamValue(SystemParameterTypeEnum.REPORT_OUTPUT_FOLDER);
				
				// restore the db
				RestoreDatabaseUtil util = new RestoreDatabaseUtil(context);
				
				// load nwcg folder paths from system parameter table
				this.loadNwcgFolderPaths();
				util.pgBaseDir=this.pgBaseDir;
				util.nwcgFolder=this.nwcgFolder;
				util.nwcgBackupFolder=this.nwcgBackupFolder;
				util.nwcgPatchFolder=this.nwcgPatchFolder;
				
				util.reportOutputFolder=reportOutputFolder;
				util.restoreDb(targetDbName.trim().toLowerCase(), filename , dbpwd);

				int originalPatchLevel=util.originalPatchLevel;
				
				// get next datasource
				String ds=this.getNextDatasource();
				
				if(!DatasourceUsageUtil.datasourcesUsed.contains(ds)){
					DatasourceUsageUtil.datasourcesUsed.add(ds);
				}
				
				String pwdhash="";
				FIPSEncryptor enc = FIPSEncryptorFactory.getDefault();
				byte[] bytes = enc.encrypt(pwd.getBytes());
				Base64 base64 = new Base64();
				pwdhash=base64.encode(bytes);
				
				// create the db avail record
				((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("YES");
				Thread.sleep(1000);			
				DbAvail entity = new DbAvailImpl();
				entity.setDatasource(ds);
				entity.setMasterDb("master");
				entity.setActive(StringBooleanEnum.Y);
				entity.setInitialVersion(1L);
				entity.setName(targetDbName.trim().toLowerCase());
				entity.setPasswordHash(pwdhash);
				entity.setIsBackup(StringBooleanEnum.Y);
				entity.setBackupInterval(Short.valueOf("6"));
				entity.setLastAutoBackupDate(Calendar.getInstance().getTime());
				this.dbAvailDao.save(entity);
				this.dbAvailDao.flushAndEvict(entity);
				((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
				Thread.sleep(1000);			
				
				DbAvailVo vo = DbAvailVo.getInstance(entity, true);
				
				// update datasource map
				((BasicDataSource)context.getBean(vo.getDatasource())).setUrl("jdbc:postgresql://127.0.0.1/"+vo.getDatabaseName().toLowerCase());
				Thread.sleep(2000);			

				// remove uploaded file
				SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");
				SystemParameter spEntity = spDao.getByParameterName(SystemParameterTypeEnum.ROSS_XML_FOLDER.name());
				String path=spEntity.getParameterValue();
				FileUtil.deleteFile(path+filename);

				// end current session for this user
		    	UserSessionManagementService2 usms2 = (UserSessionManagementService2)context.getBean("userSessionManagementService2");
		    	UserVo oldUserVo = new UserVo();
		    	oldUserVo.setId(((UserSessionVo)context.getBean("userSessionVo")).getUserId());
		    	
				DialogueVo dvo2 = usms2.endSession(oldUserVo
													, ((UserSessionVo)context.getBean("userSessionVo")).getSiteDatabaseName()
													, "USER_LOGOUT"
													, new DialogueVo());
				
				// check if current user is in the restored database
				Long currentUserId=super.getUserSessionVo().getUserId();
				String currentDatabase=super.getUserSessionVo().getSiteDatabaseName();
				
				UserDao userDao = (UserDao)context.getBean("userDao");
				User currentUser=userDao.getById(currentUserId, UserImpl.class);
				UserVo userVo = UserVo.getInstance(currentUser, true);
				String currentUserPwd=currentUser.getPassword();
				userDao.flushAndEvict(currentUser);
				String currentDs=((UserSessionVo)context.getBean("userSessionVo")).getSiteDatasourceName();
				((UserSessionVo)context.getBean("userSessionVo")).setSiteDatasourceName(ds);
				User restoredDbUser=userDao.getByLoginName(userVo.getLoginName());
				Long restoredUserId=0L;
				
				if(null == restoredDbUser){
					// create it
					((UserSessionVo)context.getBean("userSessionVo")).setSiteDatasourceName(currentDs);
					UserVo newUserVo=this.createDefaultUser(userVo, currentUserPwd,ds);
					((UserSessionVo)context.getBean("userSessionVo")).setId(newUserVo.getId());
					((UserSessionVo)context.getBean("userSessionVo")).setUserId(newUserVo.getId());
					restoredUserId=newUserVo.getId();
				}else{
					restoredUserId=restoredDbUser.getId();
					
					// check if pwd matches
					if(!restoredDbUser.getPassword().equals(currentUserPwd)){
						// update it
						((UserSessionVo)context.getBean("userSessionVo")).setSiteDatasourceName(ds);
						restoredDbUser.setPassword(currentUserPwd);
						userDao.save(restoredDbUser);
						userDao.flushAndEvict(restoredDbUser);
						((UserSessionVo)context.getBean("userSessionVo")).setId(restoredDbUser.getId());
						((UserSessionVo)context.getBean("userSessionVo")).setUserId(restoredDbUser.getId());
					}
				}

				// update current UsersessionVo
				((UserSessionVo)context.getBean("userSessionVo")).setSiteDatasourceName(ds);
				((UserSessionVo)context.getBean("userSessionVo")).setSiteDatabaseName(targetDbName.trim().toLowerCase());
				Thread.sleep(1000);
				
				// apply post restore data cleanup
				RestoreDbPostProcess postProcessor = new RestoreDbPostProcess(context);				
				postProcessor.executePostProcesses(ds,originalPatchLevel);

				// start new session for restored database
				UserSessionVo usvo = (UserSessionVo)this.context.getBean("userSessionVo");
				usvo.setUserId(restoredUserId);
				DialogueVo dvo3=usms2.startSession(usvo, new DialogueVo());

				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("RESTORE_DB");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.database", "info.0030.02" , new String[]{"The backup file "+filename+" was successfully restored."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(vo);
				dialogueVo.setResultObjectAlternate3(targetDbName.toUpperCase());
				dialogueVo.setResultObjectAlternate4(restoredUserId);
			}
			
		} catch (Exception e) {
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.DatabaseMgmtService#removeDb(gov.nwcg.isuite.core.vo.DbAvailVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo removeDb(DbAvailVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{

			// init rulehandler and check rules
			DbManagementDeleteRulesHandler ruleHandler = new DbManagementDeleteRulesHandler(context);
			if(ruleHandler.execute(vo,dialogueVo)==AbstractRule._OK){

				this.syncDatasourceUsage();
				
				String systemPatchLevel="";
				
				// get db credential
				((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("YES");
				Thread.sleep(1000);			
				DbAvail entity = this.dbAvailDao.getById(vo.getId(), DbAvailImpl.class);
				String credential=entity.getPasswordHash();
				dbAvailDao.flushAndEvict(entity);

				SystemDao systemDao = (SystemDao)context.getBean("systemDao");
				systemPatchLevel=systemDao.getRevisionLevel();
				
				((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
				Thread.sleep(1000);			
				
				// create a backup copy
				BackupDatabaseUtil util = new BackupDatabaseUtil(context);
				
				// load nwcg folder paths from system parameter table
				this.loadNwcgFolderPaths();
				util.pgBaseDir=this.pgBaseDir;
				util.nwcgFolder=this.nwcgFolder;
				util.nwcgBackupFolder=this.nwcgBackupFolder;
				util.nwcgPatchFolder=this.nwcgPatchFolder;
				
				// get all invoice pdf filenames
				TimeInvoiceDao tiDao = (TimeInvoiceDao)context.getBean("timeInvoiceDao");
				//Collection<String> invoiceFilenames = tiDao.getAllInvoiceFileNames();
				Collection<String> invoiceFilenames = util.getAllInvoiceFileNames(vo.getDatabaseName().toLowerCase());
				Collection<String> iapAttachFilenames = util.getIapAttachments(vo.getDatabaseName().toLowerCase());
				
				Collection<String> pdfFileNames=new ArrayList<String>();
				pdfFileNames.addAll(iapAttachFilenames);
				pdfFileNames.addAll(invoiceFilenames);
				
				String reportOutputFolder = super.getSystemParamValue(SystemParameterTypeEnum.REPORT_OUTPUT_FOLDER);
				
				util.reportOutputFolder = reportOutputFolder;
				util.pdfFilenames=pdfFileNames;
				
				util.backupDatabase(vo, credential,vo.getDatabaseName().toLowerCase()+".del.bak",systemPatchLevel);

				// close datasource connection
				Connection conn=null;
				try{
					conn = DataSourceUtils.getConnection((BasicDataSource)context.getBean(vo.getDatasource()));
					if(null != conn)
						conn.close();
					DataSourceUtils.getConnection((BasicDataSource)context.getBean(vo.getDatasource())).close();
				}catch(Exception e){
					//System.out.println(e.getMessage());
				}
				
				// remove from datasource map
				// update datasource map
				//((BasicDataSource)context.getBean(vo.getDatasource())).setUrl("jdbc:postgresql://localhost/"+"nothing");
				((BasicDataSource)context.getBean(vo.getDatasource())).setUrl(null);

				// wait over 30 seconds to allow connection pool to release
				Thread.sleep(33000);			
				
				// remove from dbavail
				((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("YES");
				Thread.sleep(1000);			
				this.dbAvailDao.delete(entity);
				((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
				Thread.sleep(1000);			
				
				// remove physical db from pg
				DeleteDatabaseUtil deleteUtil = new DeleteDatabaseUtil(context);
				deleteUtil.pgBaseDir=this.pgBaseDir;
				deleteUtil.nwcgFolder=this.nwcgFolder;
				deleteUtil.nwcgBackupFolder=this.nwcgBackupFolder;
				deleteUtil.nwcgPatchFolder=this.nwcgPatchFolder;
				
				deleteUtil.deleteDatabase(vo);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("REMOVE_DB");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.database", "info.generic" , new String[]{"The database was removed."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(vo);
				
			}
			
			
		} catch (Exception e) {
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	private String getNextDatasource() throws Exception {
		String ds="";
		
		try{
			/*
			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("YES");
			Collection<DbAvail> entities = dbAvailDao.getAll();
			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");

			for(DataSourceEnum dsEnum : DataSourceEnum.values()){
				Boolean bFound=false;
				for(DbAvail ent : entities){
					if(ent.getDatasource().equals(dsEnum.name())){
						bFound=true;
					}
				}
				if(bFound==false)
					ds=dsEnum.name();
			}
			*/
			this.syncDatasourceUsage();
			
			for(DataSourceEnum dsEnum : DataSourceEnum.values()){
				if(!dsEnum.name().equals("default0DataSource")){
					Boolean bFound=false;
					for(String dsused : DatasourceUsageUtil.datasourcesUsed){
						if(dsused.equals(dsEnum.name())){
							bFound=true;
						}
					}
					
					if(bFound==false){
						ds=dsEnum.name();
						break;
					}
				}
			}
			
		}catch(Exception e){
			throw e;
		}finally{
			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
		}
		
		return ds;
		
	}
	
	private UserVo createDefaultUser(UserVo userVo, String pwd,String newDbDs) throws Exception {
		UserVo newUserVo = null;
		
		String currentDs=((UserSessionVo)context.getBean("userSessionVo")).getSiteDatasourceName();
		try{
			userVo.setId(null);
			userVo.setCreatedDate(Calendar.getInstance().getTime());
			userVo.setAccountExpirationDate(null);
			userVo.setLastLoginDate(Calendar.getInstance().getTime());
			userVo.setPasswordCreatedDate(Calendar.getInstance().getTime());
			User entity = UserVo.toEntity(null, userVo, true);
			entity.setPasswordCreatedDate(Calendar.getInstance().getTime());
			entity.setRobAgreementDate(Calendar.getInstance().getTime());
			entity.setLastLoginDate(Calendar.getInstance().getTime());
			entity.setPassword(pwd);
			
			String expireTime=super.getSystemParamValue(SystemParameterTypeEnum.PWD_EXPIRE_TIME);
			if(null==expireTime || expireTime.isEmpty())
				expireTime="60";
			
			Date now = Calendar.getInstance().getTime();
			entity.setAccountExpirationDate(DateUtil.addDaysToDate(now,Integer.parseInt(expireTime)));
			
			UserDao userDao=(UserDao)context.getBean("userDao");
			((UserSessionVo)context.getBean("userSessionVo")).setSiteDatasourceName(newDbDs);
			Thread.sleep(2000);			
			entity.setCreatedById(null);
			entity.setLastModifiedById(null);
			userDao.setSkipSetAuditInfo(true);
			userDao.save(entity);
			userDao.flushAndEvict(entity);

			userDao.setSkipSetAuditInfo(false);
			
			PasswordHistoryDao phDao=(PasswordHistoryDao)context.getBean("passwordHistoryDao");
			PasswordHistory phEntity = new PasswordHistoryImpl();
			phEntity.setUser(entity);
			phEntity.setUserPassword(entity.getPassword());
			phEntity.setUserPasswordCreatedDate(entity.getPasswordCreatedDate());
			
			phDao.save(phEntity);

			newUserVo = UserVo.getInstance(userDao.getById(entity.getId(), UserImpl.class), true);
		}catch(Exception e){
			throw e;
		}finally{
			((UserSessionVo)context.getBean("userSessionVo")).setSiteDatasourceName(currentDs);
			Thread.sleep(2000);			
			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
			Thread.sleep(2000);			
		}

		return newUserVo;
	}

	private void prepareNewDb(UserVo userVo,String newDbDs) throws Exception {
		String currentDs=((UserSessionVo)context.getBean("userSessionVo")).getSiteDatasourceName();
		try{
			((UserSessionVo)context.getBean("userSessionVo")).setSiteDatasourceName(newDbDs);
			Thread.sleep(2000);			
			SystemParameterDao spDao = (SystemParameterDao)super.context.getBean("systemParameterDao");
			SystemParameter spEntity = spDao.getByParameterName("SITE_ADMIN_USER");
			if(null==spEntity)
				spEntity = new SystemParameterImpl();
			spEntity.setParameterName(SystemParameterTypeEnum.SITE_ADMIN_USER.toString());
			spEntity.setParameterValue(userVo.getLoginName());
			
			spDao.save(spEntity);
			spDao.flushAndEvict(spEntity);
		}catch(Exception e){
			throw e;
		}finally{
			((UserSessionVo)context.getBean("userSessionVo")).setSiteDatasourceName(currentDs);
			Thread.sleep(2000);			
			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
			Thread.sleep(2000);			
		}
		
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.DatabaseMgmtService#generateRecoverCode(java.lang.String, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo generateRecoverCode(String filename, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			String code="";

			RecoverDbPasswordUtil util = new RecoverDbPasswordUtil(context);
			code=util.generateCode(filename);

			// delete the uploaded file
			SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");
			SystemParameter spEntity = spDao.getByParameterName(SystemParameterTypeEnum.ROSS_XML_FOLDER.name());
			String path=spEntity.getParameterValue();
			FileUtil.deleteFile(path+filename);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GENERATE_RECOVER_CODE");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(code);
			
			
		} catch (Exception e) {
			super.dialogueException(dialogueVo, e);
		} finally{
			
		}

		return dialogueVo;
	}
	
	public DialogueVo getDefaultBackupDir(DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			this.loadNwcgFolderPaths();
			String path=this.nwcgBackupFolder;
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_DEFAULT_BACKUP_DIR");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(path);
			
			
		} catch (Exception e) {
			super.dialogueException(dialogueVo, e);
		} finally{
			
		}

		return dialogueVo;
	}

	private void syncDatasourceUsage() throws Exception {

		((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("YES");
		Collection<DbAvail> entities = dbAvailDao.getAll();
		((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");

		Collection<DbAvailVo> vos = DbAvailVo.getInstances(entities, true);
		
		// sync datasource usage
		for(DbAvailVo dbvo : vos){
			Boolean bfound=false;
			for(String ds : DatasourceUsageUtil.datasourcesUsed){
				if(ds.equals(dbvo.getDatasource()))
					bfound=true;
			}
			// if not found, add to datasource usage map
			if(bfound==false){
				DatasourceUsageUtil.datasourcesUsed.add(dbvo.getDatasource());
			}
		}
		
	}

	public static void main(String[] args) throws Exception{
		String path1="c:\\temp\\testfile1.txt";
		String path2="c:\\temp"+File.separator+"testfile2.txt";
		FileUtil.copyFile(path1, path2);
	}
}
