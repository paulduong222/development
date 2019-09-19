package gov.nwcg.isuite.core.task;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.persistence.ResInvImportDao;
import gov.nwcg.isuite.core.persistence.ResourceDao;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.task.util.ResInvParser3;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.ResInvImportConflictVo;
import gov.nwcg.isuite.core.vo.ResInvImportVo;
import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.framework.core.task.BaseTask;
import gov.nwcg.isuite.framework.core.task.EISuiteTask;
import gov.nwcg.isuite.framework.exceptions.TaskException;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class ImportRossResourceInventoryTask3 extends BaseTask implements EISuiteTask {
	private ResourceDao resourceDao = null;
	private Collection<String> logEntries = new ArrayList<String>();
	private String logFile="";
	private String hostaccount="";

	private Collection<OrganizationVo> dbOrgVos = new ArrayList<OrganizationVo>();
	private Collection<OrganizationVo> dbPdcVos = new ArrayList<OrganizationVo>();
	private Collection<KindVo> dbKindVos = new ArrayList<KindVo>();

	private Collection<String> resourceSqls = new ArrayList<String>();
	private Collection<String> resourceKindSqls = new ArrayList<String>();
	private Date currentDate=Calendar.getInstance().getTime();
	private int resourceVoCount=0;
	private int resourceX=0;
	private Collection<Long> processedRossIds = new ArrayList<Long>();
	private long milliStart=0;
	private long milliStop=0;
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.tasks.EISuiteTask#runScheduledTask()
	 */
	public int runScheduledTask() throws TaskException {
		int filecount=0;
		
		try{
			logEntries = new ArrayList<String>();
			milliStart=Calendar.getInstance().getTimeInMillis();
			logEntries.add("ImportRossResourceInventoryTask3.runScheduledTask() begin");
			
			Collection<String> rossFiles= this.getInventoryFiles();

			logEntries.add("ImportRossResourceInventoryTask3.runScheduledTask() after getInventoryFiles()");
			
			logEntries.add("ImportRossResourceInventoryTask3.runScheduledTask() ross files count:"+rossFiles.size());
			
			
			if(CollectionUtility.hasValue(rossFiles)){
				resourceDao = (ResourceDao)context.getBean("resourceDao");
				ResInvImportDao resInvImportDao = (ResInvImportDao)context.getBean("resInvImportDao");
				
				// Process each incoming res inv file
				int cnt=0;
				filecount=rossFiles.size();
				
				for(String f : rossFiles){
					cnt++;
					ResInvParser3 resInvParser = new ResInvParser3();
					
					if(!FileUtil.fileExists(f)){
						logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() Exception:" + "Unable to find file: " + f);
					}else{
						logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() processing: " + f);
						logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() before ResInvParser.getResourceVos");

						String filetype="OH";
						if(f.indexOf("NONOH")>0)
							filetype="NONOH";

						int x=resInvParser.getResourceVos(f,filetype,logEntries);

						logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() filetype: " + filetype);
						
						Collection<String> sqls=resInvParser.getDataInsertSqls();
						if(CollectionUtility.hasValue(sqls)){
							// create the isw_resinv_file_data records from xlsx file
							if(sqls.size() > 999){
								Hashtable ht=CollectionUtility.splitCollection(sqls, 995);
								for(int i=1;i<(ht.size()+1);i++){
									Collection<String> list = (Collection<String>)ht.get(i);
									resourceDao.persistSqls(list);
									logEntries.add("Finished sql set " + i);
								}
							}else{
								resourceDao.persistSqls(sqls);
							}
							
							// create isw_resinv_import record
							// update the insert count
							// update the update count
							resInvImportDao.createResInvImportRecord(filetype);

							// do updates for isw_resource
							if(filetype.equals("OH")){
								resInvImportDao.doUpdatesOH();
							}else{
								resInvImportDao.doUpdatesNonOH();
							}
							
							// do inserts for isw_resource
							if(filetype.equals("OH")){
								resInvImportDao.doInsertsOH();
							}else{
								resInvImportDao.doInsertsNonOH();
							}
						
							resInvImportDao.markCompleted();
							
							try{
								FileUtil.deleteFile(f);
							}catch(Exception ee){
								logEntries.add("ImportRossResourceInventoryTask3.runScheduledTask() Delete File Exception: " + ee.getMessage());
							}
						}
					}
					// only process 1 file in this thread
					break;
				}
				
			}

			if(filecount>1)
				super.skipScheduleNext=true;
			else
				super.skipScheduleNext=false;
			processTaskComplete();
						
		}catch(Exception e){
			logEntries.add("ImportRossResourceInventoryTask3.runScheduledTask() Exception: " + e.getMessage());
			super.handleException(e);
		}finally{
			if(CollectionUtility.hasValue(logEntries)){
				try{
					String timestamp=String.valueOf(Calendar.getInstance().getTimeInMillis());
					logFile=this.getOutputFile("importResInv_"+timestamp+".log");
					milliStop=Calendar.getInstance().getTimeInMillis();
					logEntries.add("ImportRossResourceInventoryTask3.runScheduledTask() TimeLapse Seconds: " + ((milliStop-milliStart/1000)));
					super.writeLog(logFile, logEntries);
					processTaskComplete();
				}catch(Exception eee){
				}
			}
			
		}
		
		return 1;
	}

	private ResInvImportVo processVos(Collection<ResourceVo> resourceVos, String xmlPdcName) throws Exception {
		ResInvImportVo resInvImportVo = new ResInvImportVo();

		
		for(ResourceVo vo : resourceVos){
			OrganizationVo pdc=getPdc(vo);
			OrganizationVo org=getOrg(vo);
			
			if(null != pdc 
					&& pdc.getUnitCode().equalsIgnoreCase(xmlPdcName)
					&& null != org && LongUtility.hasValue(vo.getRossResId())){
				
				// proceed with processing this res
				String resName=(StringUtility.hasValue(vo.getResourceName()) ? vo.getResourceName().trim() : "");
				resName=StringUtility.fixStringSpecialChars(resName, 55);
				
				String lastName=(StringUtility.hasValue(vo.getLastName()) ? vo.getLastName().trim() : "");
				lastName=StringUtility.fixStringSpecialChars(lastName, 35);
				String firstName=(StringUtility.hasValue(vo.getFirstName()) ? vo.getFirstName().trim() : "");
				firstName=StringUtility.fixStringSpecialChars(firstName, 35);

				if(!StringUtility.hasValue(resName)
						&& !StringUtility.hasValue(firstName)
						&& !StringUtility.hasValue(lastName)){

					ResInvImportConflictVo conflictVo = new ResInvImportConflictVo();
					conflictVo.setDescription("No Resource Name");
					conflictVo.setGaccDispUnitCodeName(xmlPdcName);
					
					//resInvImportVo.getResInvImportConflictVos().add(conflictVo);
				}else{
					resourceX++;
					//System.out.println("Processing " + resourceX + " of " + resourceVoCount);
					if(resourceX==647){
						//System.out.println("");
					}
					if(processedRossIds.contains(vo.getRossResId())){
						//System.out.println("Duplicate Resource Found");
						//throw new RuntimeException("ERROR ERROR");
					}else{
						processedRossIds.add(vo.getRossResId());
					}
					
					// check if res already exists
					Long existingId=resourceDao.getPermResourceByRossResId(vo.getRossResId());
	
					if(LongUtility.hasValue(existingId)){
						String sqlUpdate="update isw_resource " +
										 "set ross_resource_name='"+resName+"' " +
										 ", ross_last_name='"+lastName+"' " +
										 ", ross_first_name='" + firstName+"' " +
										 ", last_ross_updated_date="+
										 "    to_date('"+DateUtil.toDateString(currentDate, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS')  " +
										 "where id = " + existingId;

						resourceSqls.add(sqlUpdate);
						
						resInvImportVo.incrementUpdatedCount();
					}else{
						
						// insert
						String sql="insert into isw_resource (" +
						   "id, is_permanent, is_enabled, is_person, is_component, is_contracted, is_leader"+
						   ",resource_name,last_name, first_name, number_of_personnel, leader_type "+
						   ",ross_res_id, ross_resource_name,ross_last_name, ross_first_name "+
						   ",organization_id, primary_disp_ctr_id, transferable_identity, last_ross_updated_date ) " +
						   " values (" +
						   "SEQ_RESOURCE.nextVal " +
						   ",1,1,"+(BooleanUtility.isTrue(vo.getPerson()) ? 1 : 0)+",0,0,0"+
						   ",'"+resName+"', '"+lastName+"', '" +firstName+"' " +
						   ",0,99"+
						   ","+(LongUtility.hasValue(vo.getRossResId()) ? vo.getRossResId() : null)+
						   ",'"+resName+"', '"+lastName+"', '" +firstName+"' " +
						   ","+org.getId()+" "+
						   ","+pdc.getId()+" "+
						   ",(select sys_guid() from dual)" +
						   ", to_date('"+DateUtil.toDateString(currentDate, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS)+"','MM/DD/YYYY HH24:MI:SS')  " +
						   ")";
						
						resourceSqls.add(sql);
						
						if(null != vo.getKindVo() && StringUtility.hasValue(vo.getKindVo().getDescription())){
							KindVo kind = getKind(vo);
							if(null != kind && LongUtility.hasValue(kind.getId())){
								//UUID uuid2=UUIDGenerator.getInstance().generateNameBasedUUID(
								//		UUIDGenerator.getInstance().generateTimeBasedUUID(), "reskind"+Calendar.getInstance().getTimeInMillis());
								//String ti2=uuid2.toString();
								
								String sql2="insert into isw_resource_kind (" +
										    "id, resource_id, kind_id, is_training,is_primary,transferable_identity) " +
										    "values ("+
										    "seq_resource_kind.nextVal" +
										    ",(select id from isw_resource where ross_res_id="+vo.getRossResId()+" and is_permanent=1) " +
										    ","+kind.getId() + " " +
										    ",0,1"+
										    ",(select sys_guid() from dual) " +
										    ")";
								this.resourceKindSqls.add(sql2);
							}
						}
						
						resInvImportVo.incrementNewCount();
					}
				}
			}else{
			}
		}

		return resInvImportVo;
	}
	
	private OrganizationVo getPdc(ResourceVo vo){
		if(null != vo
				&& null != vo.getPrimaryDispatchCenterVo()
				&& StringUtility.hasValue(vo.getPrimaryDispatchCenterVo().getUnitCode())){

			for(OrganizationVo orgVo : this.dbPdcVos){
				if(orgVo.getUnitCode().equals(vo.getPrimaryDispatchCenterVo().getUnitCode()))
					return orgVo;
			}
		}
		
		return null;
	}

	private OrganizationVo getOrg(ResourceVo vo){
		if(null != vo
				&& null != vo.getOrganizationVo()
				&& StringUtility.hasValue(vo.getOrganizationVo().getUnitCode())){

			for(OrganizationVo orgVo : this.dbOrgVos){
				if(orgVo.getUnitCode().equals(vo.getOrganizationVo().getUnitCode()))
					return orgVo;
			}
		}
		
		return null;
	}

	private KindVo getKind(ResourceVo vo){
		if(null != vo
				&& null != vo.getKindVo()
				&& StringUtility.hasValue(vo.getKindVo().getDescription())){

			for(KindVo kindVo : this.dbKindVos){
				if(kindVo.getDescription().equalsIgnoreCase(vo.getKindVo().getDescription()))
					return kindVo;
			}
		}
		
		return null;
	}


	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.task.EISuiteTask#postTask()
	 */
	public void postTask() throws TaskException{

		try{
			if(StringUtility.hasValue(this.logFile) && FileUtil.fileExists(logFile)){
				Collection<String> mailToEmails = new ArrayList<String>();
				/*
				mailToEmails.add("dprice@issc-usa.com");
				//mailToEmails.add("dboswell@issc-usa.com");
				
				EmailAttachment attach = new EmailAttachment();
				attach.setPath(this.logFile);
				
				super.emailTaskResults(
						mailToEmails
						, "ResourceInventoryImportTask"
						, "Attached is the log file " + " created on " + Calendar.getInstance().getTime() + "."
						, attach
						, hostaccount);
				*/
			}
		}catch(Exception e){
			//throw new TaskException(e.getMessage());
		}
		
	}

	private Collection<String> getInventoryFiles() throws Exception {
		Collection<String> resFiles = new ArrayList<String>();
		
		//String sfile="c:\\Development\\e-ISuite\\ross_resources\\test\\";
		String fileFolder=this.getFileFolder("");
		Collection<String> files=FileUtil.getDirFilenames(fileFolder);
		if(CollectionUtility.hasValue(files)){
			for(String f : files){
				if(StringUtility.hasValue(f) 
						&& f.toUpperCase().startsWith("RESOURCEIMPORT")
						&& f.endsWith(".xlsx")){

					resFiles.add(fileFolder+f);
				}
			}
		}
		
		return resFiles;
	}

	protected String getFileFolder(String fileName) throws Exception {
		SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");

		try{
			SystemParameter spEntity = spDao.getByParameterName("RES_INV_FOLDER");

			if( (null != spEntity) && ( null != spEntity.getParameterValue()) && (!spEntity.getParameterValue().isEmpty()) ){
				return spEntity.getParameterValue() + fileName;
			}else{
				return fileName;
			}

		}catch(Exception e){
			throw e;
		}
	}

	
}
