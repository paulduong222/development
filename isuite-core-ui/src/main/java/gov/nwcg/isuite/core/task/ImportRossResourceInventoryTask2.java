package gov.nwcg.isuite.core.task;

import gov.nwcg.isuite.core.domain.ResInvImport;
import gov.nwcg.isuite.core.persistence.ResInvImportDao;
import gov.nwcg.isuite.core.persistence.ResourceDao;
import gov.nwcg.isuite.core.task.util.ResInvParser;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.ResInvImportConflictVo;
import gov.nwcg.isuite.core.vo.ResInvImportVo;
import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.framework.core.task.BaseTask;
import gov.nwcg.isuite.framework.core.task.EISuiteTask;
import gov.nwcg.isuite.framework.exceptions.TaskException;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.xml.XmlHandler;
import gov.nwcg.isuite.framework.xml.XmlSchemaTypeEnum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;

import org.apache.commons.io.FilenameUtils;
import org.safehaus.uuid.UUID;
import org.safehaus.uuid.UUIDGenerator;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class ImportRossResourceInventoryTask2 extends BaseTask implements EISuiteTask {
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
		
		try{
			milliStart=Calendar.getInstance().getTimeInMillis();
			logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() begin");
			
			//Collection<RossEmail> rossEmails = this.getRossEmails();
			Collection<RossEmail> rossEmails = this.getRossFilesFromFile();
			//Collection<RossEmail> rossEmails = this.getTestRossEmails();

			//logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() after getRossEmails()");
			
			//logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() rossEmails size:"+rossEmails.size());
			
			if(CollectionUtility.hasValue(rossEmails)){
				resourceDao = (ResourceDao)context.getBean("resourceDao");
				ResInvImportDao resInvImportDao = (ResInvImportDao)context.getBean("resInvImportDao");
				
				String xsdBasePath = super.getSystemParamValue(SystemParameterTypeEnum.XSD_BASE_PATH);
				XmlHandler xmlHandler = new XmlHandler(XmlSchemaTypeEnum.ROSS_XML_DATA, xsdBasePath);
				
				// Process each incoming res inv file
				int cnt=0;
				int size=rossEmails.size();
				
				for(RossEmail rossEmail : rossEmails){
					cnt++;
					//logEntries.add("");
					//logEntries.add("");
					//logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() ================================");
					//logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() ================================");
					//logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() Processing email "+cnt+" of " + size);
					
					try{
						if(StringUtility.hasValue(rossEmail.fullPathFilename)){
							if(!FileUtil.fileExists(rossEmail.fullPathFilename)){
								logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() Exception:" + "Unable to find file: " + rossEmail.fullPathFilename);
				            	//super.handleException(ErrorEnum._90000_ERROR, "Unable to find file: " + rossEmail.fullPathFilename);
							}else{
							
								//logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() processing: " + rossEmail.fullPathFilename);
								
								//logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() before ResInvParser.getResourceVos");
								
								ResInvParser resInvParser = new ResInvParser();
								
								resInvParser.getResourceVos(rossEmail.fullPathFilename, xsdBasePath);
								
								Collection<String> xmlPdcNames = resInvParser.pdcNames;

								resourceVoCount = resInvParser.resourceCount;
								
								if(resInvParser.resourceCount > 0){
									//logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() ResInvParser.getResourceVos returned "+ resInvParser.resourceCount + " records.");

									this.dbOrgVos = super.getGlobalCacheVo().getOrganizationVos();
									this.dbPdcVos = super.getGlobalCacheVo().getPdcVos();
									this.dbKindVos = super.getGlobalCacheVo().getKindVos();
									
									this.resourceSqls = new ArrayList<String>();
									this.resourceKindSqls = new ArrayList<String>();
		
									for(String xmlPdcName : xmlPdcNames){
										this.resourceSqls = new ArrayList<String>();
										this.resourceKindSqls = new ArrayList<String>();
										ResInvImportVo resInvImportVo = null;

										logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() Processing PDC: "+xmlPdcName);
										
										Collection<ResourceVo> pdcResourceVos = resInvParser.getResourceVosForPdc(xmlPdcName);

										if(CollectionUtility.hasValue(pdcResourceVos)){
											resInvImportVo = processVos(pdcResourceVos,xmlPdcName);
											resInvImportVo.setDispatchCenterCode(xmlPdcName);
											resInvImportVo.setGaccUnitCode(xmlPdcName);
											resInvImportVo.setProcessedDate(Calendar.getInstance().getTime());
											resInvImportVo.setStatus("COMPLETE");
											resInvImportVo.setFileName(StringUtility.fixString(rossEmail.filename,75));
											
											if(CollectionUtility.hasValue(resourceSqls)){
												resourceDao.persistSqls(resourceSqls);
											}
											if(CollectionUtility.hasValue(resourceKindSqls)){
												resourceDao.persistSqls(resourceKindSqls);
											}

											if(null != resInvImportVo){
												ResInvImport resInvImport = ResInvImportVo.toEntity(null,resInvImportVo, true);
												resInvImportDao.save(resInvImport);
												resInvImportDao.flushAndEvict(resInvImport);
											}
											
											// remove from parser hashmap to free memory
											resInvParser.removeFromMap(xmlPdcName);
										}
										
									}
									
								}else{
									logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() ResInvParser.getResourceVos returned 0 records.");
								}
		
								// delete the file
								try{
									FileUtil.deleteFile(rossEmail.fullPathFilename);
								}catch(Exception fe){
									logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() Delete File Exception: " + fe.getMessage());
								}
							}
							
						}else{
							// log no attachment
							logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() no attachment: ");
						}
					}catch(Exception re){
						// catch for each rossEmail processing
						// todo: log it
						logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() Exception: " + re.getMessage());
					}
				}
			}
			
			processTaskComplete();
			
		}catch(Exception e){
			logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() Exception: " + e.getMessage());
			super.handleException(e);
		}finally{
			if(CollectionUtility.hasValue(logEntries)){
				try{
					String timestamp=String.valueOf(Calendar.getInstance().getTimeInMillis());
					logFile=this.getOutputFile("importResInv_"+timestamp+".log");
					milliStop=Calendar.getInstance().getTimeInMillis();
					logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() TimeLapse Seconds: " + ((milliStop-milliStart/1000)));
					super.writeLog(logFile, logEntries);
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

	private Collection<RossEmail> getRossEmails() throws Exception {
		logEntries.add("ImportRossResourceInventoryTask.getRossEmails() begin");
		Collection<RossEmail> rossEmails = new ArrayList<RossEmail>();
		Store store=null;
		Folder folder=null;

		try{
			Properties properties = System.getProperties();
			Session session = Session.getDefaultInstance(properties);

			String host = super.getSystemParamValue(SystemParameterTypeEnum.POP_SERVER_HOSTNAME);
			hostaccount = super.getSystemParamValue(SystemParameterTypeEnum.MAIL_SERVER_ACCOUNT);;
			
			store = session.getStore("pop3");
			logEntries.add("ImportRossResourceInventoryTask.getRossEmails() before store.connect("+host+","+hostaccount+")");

			store.connect(host, hostaccount, "ISSCPassword");
			logEntries.add("ImportRossResourceInventoryTask.getRossEmails() after store.connect()");
			
			logEntries.add("ImportRossResourceInventoryTask.getRossEmails() before store.getFolder");
			folder = store.getFolder("INBOX");
			
			logEntries.add("ImportRossResourceInventoryTask.getRossEmails() before folder.open");
			folder.open(Folder.READ_WRITE);
			
			logEntries.add("ImportRossResourceInventoryTask.getRossEmails() before folder.getMessages");
			Message[] messages = folder.getMessages();

			logEntries.add("ImportRossResourceInventoryTask.getRossEmails() after folder.getMessages");

			logEntries.add("ImportRossResourceInventoryTask.getRossEmails() messages.length = " + messages.length);

			if(messages.length > 0){
				String rossResFileFolder=super.getSystemParamValue(SystemParameterTypeEnum.ROSS_XML_FOLDER);
				
				for(int i=0;i<messages.length;i++){
					RossEmail rossEmail = new RossEmail();
					
					rossEmail.from = InternetAddress.toString(messages[i].getFrom());
					rossEmail.to = InternetAddress.toString(messages[i].getRecipients(Message.RecipientType.TO));
					//rossEmail.cc = InternetAddress.toString(messages[i].getRecipients(Message.RecipientType.CC));
					//rossEmail.bcc = InternetAddress.toString(messages[i].getRecipients(Message.RecipientType.BCC));
					rossEmail.subject = messages[i].getSubject();
					rossEmail.sent = messages[i].getSentDate();
				
					//if(StringUtility.hasValue(rossEmail.subject) && rossEmail.subject.toUpperCase().indexOf("EISUITE")>-1){
					if(StringUtility.hasValue(rossEmail.subject)){
						if(null != messages[i] && null != messages[i].getContent()){
							Multipart multipart = (Multipart) messages[i].getContent();

							for (int x = 0; x < multipart.getCount(); x++) {
								MimeBodyPart bodyPart = (MimeBodyPart) multipart.getBodyPart(x);

								String detachFileName="resinv_"+x+".zip";
								try{
									FileUtil.deleteFile(rossResFileFolder+detachFileName);									
									if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
										DataHandler handler = bodyPart.getDataHandler();
										String detachEmailName = FilenameUtils.getName(handler.getName());
										rossEmail.fullPathFilename=rossResFileFolder+detachFileName;
										rossEmail.filename=detachFileName;
										
										rossEmails.add(rossEmail);
										
										// save the file to drive
								        bodyPart.saveFile(rossResFileFolder+detachFileName);
								    }
									
								}catch(Exception eee){
									logEntries.add("ImportRossResourceInventoryTask.getRossEmails() Unable to delete file "+ rossResFileFolder+detachFileName +" Exception:"+eee.getMessage());
								}
							}
						}
						
					}else{
						//log invalid incoming email
						logEntries.add("ImportRossResourceInventoryTask.getRossEmails() Invalid Email");
					}

					// mark for delete
					messages[i].setFlag(Flags.Flag.DELETED, true);
				}
			}
			
		} catch (MessagingException me) {
			logEntries.add("ImportRossResourceInventoryTask.getRossEmails() MessaginException: "+me.getMessage());
			super.handleException(me);
		} catch (IOException ioe) {
			logEntries.add("ImportRossResourceInventoryTask.getRossEmails() IOException: "+ioe.getMessage());
			super.handleException(ioe);
		}catch(Exception e){
			logEntries.add("ImportRossResourceInventoryTask.getRossEmails() Exception: "+e.getMessage());
			super.handleException(e);
		}finally{
			try{
				// process marks and close
				if(null != folder)
					folder.close(true);
				if(null != store)
					store.close();
				
			}catch(Exception se){
				//smother
			}
		}
		
		logEntries.add("ImportRossResourceInventoryTask.getRossEmails() end");

		return rossEmails;
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

	class RossEmail {
		String from = "";
		String to = "";
		String cc = "";
		String bcc = "";
		String subject = "";
		Date sent = null;
		
		String fullPathFilename="";
		String filename="";
		Boolean isAceFile=true;
	}

	private Collection<RossEmail> getRossFilesFromFile() throws Exception {
		Collection<RossEmail> rossEmails = new ArrayList<RossEmail>();
		
		String rossXmlFolder=this.getRossXmlFolder("");
		Collection<String> files=FileUtil.getDirFilenames(rossXmlFolder);
		if(CollectionUtility.hasValue(files)){
			for(String f : files){
				if(StringUtility.hasValue(f) 
						&& f.toUpperCase().startsWith("RESOURCEIMPORT")
						&& f.toUpperCase().endsWith(".XML")){

					RossEmail rossEmail = new RossEmail();
					rossEmail.fullPathFilename=rossXmlFolder+f;
					rossEmail.filename=f;
					rossEmails.add(rossEmail);
				}
			}
		}
		
		return rossEmails;
	}
	
	private Collection<RossEmail> getTestRossEmails() throws Exception {
		Collection<RossEmail> rossEmails = new ArrayList<RossEmail>();

		RossEmail rossEmail = new RossEmail();
		rossEmail.fullPathFilename="c:\\workspace\\isuite-core\\ResourceImportNonOH.xml";
		rossEmail.filename="ResourceImportNonOH.xml";
		rossEmails.add(rossEmail);
		
		return rossEmails;
	}
	
}
