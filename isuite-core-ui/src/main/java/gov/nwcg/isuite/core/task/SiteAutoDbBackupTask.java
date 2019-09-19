package gov.nwcg.isuite.core.task;

import gov.nwcg.isuite.core.domain.DbAvail;
import gov.nwcg.isuite.core.persistence.DbAvailDao;
import gov.nwcg.isuite.core.persistence.SystemDao;
import gov.nwcg.isuite.core.persistence.TimeInvoiceDao;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.DbAvailVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.framework.core.persistence.DatasourceConfiguration;
import gov.nwcg.isuite.framework.core.task.BaseTask;
import gov.nwcg.isuite.framework.core.task.EISuiteTask;
import gov.nwcg.isuite.framework.dbutil.pg.BackupDatabaseUtil;
import gov.nwcg.isuite.framework.exceptions.TaskException;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Stack;

import org.springframework.transaction.annotation.Transactional;


@Transactional
public class SiteAutoDbBackupTask extends BaseTask implements EISuiteTask {
	private Collection<DbAvailVo> dbAvailVos = new ArrayList<DbAvailVo>();
	private String pgBaseDir="";
	private String nwcgFolder="";
	private String nwcgBackupFolder="";
	private String nwcgPatchFolder="";

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.tasks.EISuiteTask#runScheduledTask()
	 */
	public int runScheduledTask() throws TaskException {
		
		try{
			dbAvailVos = new ArrayList<DbAvailVo>();
			
			DbAvailDao dao = (DbAvailDao)context.getBean("dbAvailDao");
			Collection<DbAvail> dbAvailEntities = dao.getAll();
			
			if(CollectionUtility.hasValue(dbAvailEntities)){
				for(DbAvail entity : dbAvailEntities){
					if(BooleanUtility.isTrue(entity.getActive().getValue())){

						Boolean doBackup=false;
						if(null != entity.getIsBackup())
							doBackup=BooleanUtility.isTrue(entity.getIsBackup().getValue());

						if(doBackup==true){
							// need to auto back up this db
							DbAvailVo vo = DbAvailVo.getInstance(entity, true);
							vo.setPasswordHash(entity.getPasswordHash());
							dbAvailVos.add(vo);
						}
					}
					
					dao.flushAndEvict(entity);
				}
			}

			BackupDatabaseUtil util = new BackupDatabaseUtil(context);

			// load nwcg folder paths from system parameter table
			this.loadNwcgFolderPaths();
			util.pgBaseDir=this.pgBaseDir;
			util.nwcgFolder=this.nwcgFolder;
			util.nwcgBackupFolder=this.nwcgBackupFolder;
			util.nwcgPatchFolder=this.nwcgPatchFolder;
			
			SystemDao systemDao = (SystemDao)context.getBean("systemDao");
			String systemPatchLevel=systemDao.getRevisionLevel();

			Date now=Calendar.getInstance().getTime();

			Collection<String> autoBackupLog = new ArrayList<String>();
			
			// for each db , do auto backup if last backup over the backup interval 
			for(DbAvailVo vo : dbAvailVos){

				if(BooleanUtility.isTrue(vo.getIsAutoBackup())){
					autoBackupLog.add("DbAvail.name: " + vo.getDatabaseName());
					autoBackupLog.add("DbAvail.isAutoBackup: " + vo.getIsAutoBackup());
					autoBackupLog.add("DbAvail.interval: " + vo.getBackupInterval());
					
					Date dtnow=Calendar.getInstance().getTime();
					String fname=DateUtil.toDateString(dtnow);
					fname=fname.replaceAll("/", "");
					fname=vo.getDatabaseName()+"_"+fname+"_";
					int hour=DateUtil.getHourAsInt(dtnow);
					String shour="";
					if(hour < 10)
						shour="0"+String.valueOf(hour);
					else
						shour=String.valueOf(hour);
					int min=DateUtil.getDayAsInt(dtnow);
					String smin="";
					if(min < 10)
						smin="0"+String.valueOf(min);
					else
						smin=String.valueOf(min);
					String sec="00";
					fname=fname+shour+smin+sec;
					String destFileName=fname;
					autoBackupLog.add("destFileName: " + destFileName);
					
					// check if last backup exists
					Boolean lastBackupExists=DateTransferVo.hasDateString(vo.getLastAutoBackupDateVo());
					autoBackupLog.add("lastBackupExists: " + (lastBackupExists == true ? "YES" : "NO"));
					Boolean doBackup=false;
					
					if(lastBackupExists==true){
						Date fileCreationDate=null;
						fileCreationDate=DateTransferVo.getTransferDate(vo.getLastAutoBackupDateVo());
						
						if(fileCreationDate==null)
							autoBackupLog.add("fileCreationDate is null.");
						else
							autoBackupLog.add("fileCreationDate: " + DateUtil.toDateString(fileCreationDate,DateUtil.MM_SLASH_DD_SLASH_YYYY));

						if(null != fileCreationDate){
							if(StringUtility.hasValue(vo.getLastAutoBackupDateVo().getTimeString())){
								autoBackupLog.add("TimeString is: " + vo.getLastAutoBackupDateVo().getTimeString());
								fileCreationDate=DateUtil.addMilitaryTimeToDate(fileCreationDate, vo.getLastAutoBackupDateVo().getTimeString());
							}else
								autoBackupLog.add("TimeString is null.");
						}
							
						long hoursPassed=DateUtil.diffHours(now, fileCreationDate);
						autoBackupLog.add("Hours Passed: "+hoursPassed);
						autoBackupLog.add("Backup Interval: "+vo.getBackupInterval());
						
						if(hoursPassed >= vo.getBackupInterval())
							doBackup=true;
					}else
						doBackup=true;

					if(doBackup==true){
						autoBackupLog.add("Doing backup for this db.");
						autoBackupLog.add("===========");
						autoBackupLog.add("===========");
						autoBackupLog.add("");
						
						// get all invoice pdf filenames
						TimeInvoiceDao tiDao = (TimeInvoiceDao)context.getBean("timeInvoiceDao");
						Collection<String> invoiceFilenames = this.getAllInvoiceFileNames(vo.getDatabaseName()); //tiDao.getAllInvoiceFileNames();
						Collection<String> iapAttachFilenames = this.getIapAttachments(vo.getDatabaseName()); //tiDao.getAllInvoiceFileNames();

						invoiceFilenames.addAll(iapAttachFilenames);
						
						String reportOutputFolder = super.getSystemParamValue(SystemParameterTypeEnum.REPORT_OUTPUT_FOLDER);
						
						util.reportOutputFolder = reportOutputFolder;
						util.pdfFilenames=invoiceFilenames;
						
						util.backupDatabase(vo, vo.getPasswordHash(),destFileName, systemPatchLevel);
						
						dao.updateLastAutoBackupDate(vo.getId(), Calendar.getInstance().getTime());

						if(StringUtility.hasValue(vo.getAdditionalBackupDestination())){
							try{
								// copy file to additionalbackupdestination
								FileUtil.copyFile(this.nwcgBackupFolder+destFileName+".bak"
													, vo.getAdditionalBackupDestination()+destFileName+".bak");
							}catch(Exception ignoreForNow){}
							
						}
					}
				}
			}

			/* turn off logging
			BufferedWriter appender = null;
			try{
				if(CollectionUtility.hasValue(autoBackupLog)){
					long milli=Calendar.getInstance().getTimeInMillis();
					String logFile=this.nwcgBackupFolder+"AutoBackLog_"+milli+".log";
					FileUtil.createFile(logFile);
					appender = FileUtil.getFileAppender(logFile);
					
					for(String s : autoBackupLog){
						appender.write(s);
						appender.newLine();
					}
				}
			}catch(Exception smother){
			}finally{
				if(null != appender)
					appender.close();
			}
			 */
			
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

	private Date getFileModifiedDate(String filePath){
		
		Date modifiedDate=null;
		
		try{
			File file=new File(filePath);
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			
			String lastModified=sdf.format(file.lastModified());
			
			modifiedDate=DateUtil.toDate(lastModified, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS);
			
		}catch(Exception e){
			// smother
		}
		
		return modifiedDate;
	}
	
	private Collection<String> getAllInvoiceFileNames(String dbname) {
		Collection<String> list=new ArrayList<String>();

		try{
			String outFile=nwcgBackupFolder+"sqlout.file";
			if(FileUtil.fileExists(outFile)){
				FileUtil.deleteFile(outFile);
			}
			String pgBinDir=pgBaseDir+"bin"+File.separator;
	  		String sql = "select rpt.file_name " +
				 "from isw_time_invoice ti "+
				 "     , isw_report rpt " +
				 "where ti.deleted_date is null " +
				 "and ti.is_draft = false " +
				 "and rpt.id = ti.invoice_report_id " ;
			
			String[] command3 = { pgBinDir + "psql"
					,"--username=isw" 
					,"--host=127.0.0.1"
					,"--quiet"
					,"--dbname=" + dbname.trim().toLowerCase() 
					,"--output="+outFile
					,"-t"
					,"--command="+sql};
			
			Stack<String> errStack3 = new Stack<String>();

			ProcessBuilder procbuilder = new ProcessBuilder(command3);

			procbuilder.directory(new File(pgBinDir));

			Process process = procbuilder.start();
			InputStream is = process.getErrorStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String stdErr;
			while ((stdErr = br.readLine()) != null) {
				errStack3.push(new String(stdErr));
			}
			Object ret = process.waitFor();
			process.destroy();
			
			if(FileUtil.fileExists(outFile)){
				Collection<String> pdfs=FileUtil.getContentsByLine(outFile);
				FileUtil.deleteFile(outFile);
				for(String s : pdfs){
					if(StringUtility.hasValue(s))
						list.add(s.trim());
				}
				
			}
		}catch(Exception e){
			//System.out.println(e.getMessage());
		}
		
		
		return list;
	}

	private Collection<String> getIapAttachments(String dbname) {
		Collection<String> list=new ArrayList<String>();

		try{
			String outFile=nwcgBackupFolder+"sqlout.file";
			if(FileUtil.fileExists(outFile)){
				FileUtil.deleteFile(outFile);
			}
			String pgBinDir=pgBaseDir+"bin"+File.separator;
	  		String sql = "select filename from isw_iap_attachment ";
			
			String[] command3 = { pgBinDir + "psql"
					,"--username=isw" 
					,"--host=127.0.0.1"
					,"--quiet"
					,"--dbname=" + dbname.trim().toLowerCase() 
					,"--output="+outFile
					,"-t"
					,"--command="+sql};
			
			Stack<String> errStack3 = new Stack<String>();

			ProcessBuilder procbuilder = new ProcessBuilder(command3);

			procbuilder.directory(new File(pgBinDir));

			Process process = procbuilder.start();
			InputStream is = process.getErrorStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String stdErr;
			while ((stdErr = br.readLine()) != null) {
				errStack3.push(new String(stdErr));
			}
			Object ret = process.waitFor();
			process.destroy();
			
			if(FileUtil.fileExists(outFile)){
				Collection<String> pdfs=FileUtil.getContentsByLine(outFile);
				FileUtil.deleteFile(outFile);
				for(String s : pdfs){
					if(StringUtility.hasValue(s))
						list.add(s.trim());
				}
				
			}
		}catch(Exception e){
			//System.out.println(e.getMessage());
		}
		
		
		return list;
	}
	
}
