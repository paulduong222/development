package gov.nwcg.isuite.core.task;

import gov.nwcg.isuite.core.domain.ResInvImport;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.persistence.ResInvImportDao;
import gov.nwcg.isuite.core.persistence.ResourceDao;
import gov.nwcg.isuite.core.reports.TaskLockedAccountsReport;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.ResInvImportConflictVo;
import gov.nwcg.isuite.core.vo.ResInvImportVo;
import gov.nwcg.isuite.core.vo.RossResourceXmlFileDataVo;
import gov.nwcg.isuite.framework.core.task.BaseTask;
import gov.nwcg.isuite.framework.core.task.EISuiteTask;
import gov.nwcg.isuite.framework.exceptions.TaskException;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.xml.XmlHandler;
import gov.nwcg.isuite.framework.xml.XmlSchemaTypeEnum;
import gov.nwcg.isuite.xml.ross.Dataset;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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
import org.apache.commons.mail.EmailAttachment;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class ImportRossResourceInventoryTask extends BaseTask implements EISuiteTask {
	ResourceDao resourceDao = null;
	private Collection<String> logEntries = new ArrayList<String>();
	private String logFile="";
	private String hostaccount="";
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.tasks.EISuiteTask#runScheduledTask()
	 */
	public int runScheduledTask() throws TaskException {
		
		try{
			logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() begin");
			
			Collection<RossEmail> rossEmails = this.getRossEmails();
			//Collection<RossEmail> rossEmails = this.getTestRossEmails();

			logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() after getRossEmails()");
			
			logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() rossEmails size:"+rossEmails.size());
			
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
					logEntries.add("");
					logEntries.add("");
					logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() ================================");
					logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() ================================");
					logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() Processing email "+cnt+" of " + size);
					
					try{
						if(StringUtility.hasValue(rossEmail.fullPathFilename)){
							if(!FileUtil.fileExists(rossEmail.fullPathFilename)){
								logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() Exception:" + "Unable to find file: " + rossEmail.fullPathFilename);
				            	//super.handleException(ErrorEnum._90000_ERROR, "Unable to find file: " + rossEmail.fullPathFilename);
							}else{
							
								logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() processing: " + rossEmail.fullPathFilename);
								
								logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() before unmarshall");
								
								Dataset rootNode = (Dataset)xmlHandler.unmarshall(new File(rossEmail.fullPathFilename));
		
								logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() after unmarshall");
								
								Collection<RossResourceXmlFileDataVo> dataVos
												=RossResourceXmlFileDataVo.getInstances(rossEmail.isAceFile
																						,rootNode.getMetadata()
																						,rootNode.getData().getRow());
								
								
								logEntries.add("ImportRossResourceInventoryTask.runScheduledTask() dataVos.size:"+ dataVos.size());
								
								ResInvImportVo resInvImportVo = new ResInvImportVo();
								resInvImportVo.setDispatchCenterCode("UNK");
								resInvImportVo.setGaccUnitCode("UNK");
								resInvImportVo.setFileName(rossEmail.filename);
								resInvImportVo.setStatus("IMPORTING");
								
								this.processRossResourcesImport(!rossEmail.isAceFile, dataVos,resInvImportVo);
	
								ResInvImport resInvImport = ResInvImportVo.toEntity(null, resInvImportVo, true);
								resInvImportDao.save(resInvImport);
								resInvImportDao.flushAndEvict(resInvImport);
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
			super.handleException(e);
		}finally{
			if(CollectionUtility.hasValue(logEntries)){
				try{
					String timestamp=String.valueOf(Calendar.getInstance().getTimeInMillis());
					logFile=this.getOutputFile("importResInv_"+timestamp+".log");
					super.writeLog(logFile, logEntries);
				}catch(Exception eee){
				}
			}
			
		}
		
		return 1;
	}
	
	private void processRossResourcesImport(Boolean isOverheadFile,Collection<RossResourceXmlFileDataVo> vos,ResInvImportVo resInvImportVo) throws Exception {
		logEntries.add("ImportRossResourceInventoryTask.processRossResourcesImport() begin");
		
		try{
			Collection<OrganizationVo> orgVos = super.getGlobalCacheVo().getOrganizationVos();
			int updateCount=0;
			int insertCount=0;
			int deletedCount=0;
			int errorCount=0;
			
			// cognos sends duplicates, keep track of what was already processed
			HashMap<Long,Long> map = new HashMap<Long,Long>();
			
			int i=0;
			int size=vos.size();
			
			for(RossResourceXmlFileDataVo vo : vos){
				logEntries.add("ImportRossResourceInventoryTask.processRossResourcesImport() processing resource: "
								+ i + " of " + size);
				if(i==0){
					// set gacc file we are processing
					resInvImportVo.setDispatchCenterCode(vo.getGaccUnitCode());
					resInvImportVo.setGaccUnitCode(vo.getGaccUnitCode());
				}
				i++;
				
				// only process ones not already processed, skip duplicates
				if(!map.containsKey((Long)vo.getResId())){
					//System.out.println(vo.getResId()+" " + vo.getResourceName());
					//if(vo.getResId().compareTo(867921L)==0)
					//	System.out.println(vo.getResId()+" " + vo.getResourceName());

					Boolean isNew=false;
					Resource resource = resourceDao.getPermanentResourceByResId(vo.getResId());
					if(null == resource){
						resource = new ResourceImpl();
						isNew=true;
					}

					// validate and fix vo before processing
					ResInvImportConflictVo conflictVo=this.validateAndFixIncomingVo(isOverheadFile, vo,orgVos);
		
					if(conflictVo==null){
						// proceed
						OrganizationVo orgVo = OrganizationVo.getOrgByUnitCode(vo.getResProvUnitCode(), orgVos);
						OrganizationVo pdcVo = OrganizationVo.getDispatchCenterByUnitCode(vo.getPdcCode(), orgVos);
						resource.setOrganization(OrganizationVo.toEntity(null,orgVo, false));
						resource.setPrimaryDispatchCenter(OrganizationVo.toEntity(null,pdcVo, false));
						resource.setPermanent(true);
						resource.setRossResId(vo.getResId());
						resource.setEnabled(true);
						resource.setContracted(false);
						resource.setLeader(false);
						if(isOverheadFile==true){
							resource.setRossFirstName(vo.getFirstName().toUpperCase().trim());
							resource.setRossLastName(vo.getLastName().toUpperCase().trim());
							
							// only set first,last name if not already defined.  user may have assigned an eisuite first,last name
							if(!StringUtility.hasValue(resource.getFirstName()))
								resource.setFirstName(resource.getRossFirstName());
							if(!StringUtility.hasValue(resource.getLastName()))
								resource.setLastName(resource.getRossLastName());
							
							resource.setPerson(true);
						}else{
							if(StringUtility.hasValue(vo.getResourceName())){
								resource.setRossResourceName(vo.getResourceName().toUpperCase().trim());
								if(!StringUtility.hasValue(resource.getResourceName()))
									resource.setResourceName(resource.getRossResourceName());
							}
							resource.setPerson(false);
						}
						
						if(!isNew && vo.getRemovedFlag()==true){
							resource.setDeletedDate(Calendar.getInstance().getTime());
							deletedCount++;
						}
						resourceDao.save(resource);
						resourceDao.flushAndEvict(resource);
						if(isNew==true)
							insertCount++;
						else
							updateCount++;
					}else{
						errorCount++;
						resInvImportVo.getResInvImportConflictVos().add(conflictVo);
					}
					
					map.put(vo.getResId(), vo.getResId());
				}
				
			}
			
			resInvImportVo.setNewImportCount(new Integer(insertCount));
			resInvImportVo.setDeletedCount(new Integer(deletedCount));
			resInvImportVo.setUpdatedImportCount(new Integer(updateCount));
			resInvImportVo.setStatus("COMPLETE");
			resInvImportVo.setProcessedDate(Calendar.getInstance().getTime());
			logEntries.add("ImportRossResourceInventoryTask.processRossResourcesImport() New Import Count: " + insertCount);
			logEntries.add("ImportRossResourceInventoryTask.processRossResourcesImport() Deleted Count: " + deletedCount);
			logEntries.add("ImportRossResourceInventoryTask.processRossResourcesImport() Updated Count: " + updateCount);
			
			//System.out.println("INSERT COUNT: " + insertCount);
			//System.out.println("UPDATE COUNT: " + updateCount);
			//System.out.println("ERROR COUNT: " + errorCount);
		}catch(Exception e){
			logEntries.add("ImportRossResourceInventoryTask.processRossResourcesImport() Exception: " + e.getMessage());
			resInvImportVo.setStatus("ERROR");
			String error = e.getMessage();
			resInvImportVo.setErrorDescription(StringUtility.fixString(error, 300));
		}
	}

	private ResInvImportConflictVo validateAndFixIncomingVo(Boolean isOverhead,RossResourceXmlFileDataVo vo,Collection<OrganizationVo> orgVos) throws Exception {
		ResInvImportConflictVo conflictVo = null;
		Boolean hasError=false;
		
		// validate and fix person information
		if(isOverhead==true){
			hasError=!vo.hasValidPersonInformation();
			if(hasError==true){
				conflictVo = this.initConflictVo(vo);
				conflictVo.setDescription("Error with First and/or Last Name. ");
			}
		}
		
		// validate and fix nonperson information
		if(isOverhead==false){
			hasError=!vo.hasValidNonPersonInformation();
			if(hasError==true){
				conflictVo = this.initConflictVo(vo);
				conflictVo.setDescription("Error with Resource Name.");
			}
		}

		if(hasError==false){
			OrganizationVo orgVo = OrganizationVo.getOrgByUnitCode(vo.getResProvUnitCode(), orgVos);
			OrganizationVo pdcVo = OrganizationVo.getDispatchCenterByUnitCode(vo.getPdcCode(), orgVos);

			if(null==orgVo){
				conflictVo = this.initConflictVo(vo);
				conflictVo.setDescription("Unable to identify Unit Code: " + vo.getResProvUnitCode());
			}
			if(null==pdcVo){
				conflictVo = this.initConflictVo(vo);
				conflictVo.setDescription("Unable to identify Dispatch Center: " + vo.getPdcCode());
			}
			
		}
		
		return conflictVo;
	}

	private ResInvImportConflictVo initConflictVo(RossResourceXmlFileDataVo vo) throws Exception{
		ResInvImportConflictVo conflictVo = new ResInvImportConflictVo();
		conflictVo.setRossResId(String.valueOf(vo.getResId()));
		conflictVo.setFirstName(StringUtility.fixString(vo.getFirstName(),75));
		conflictVo.setLastName(StringUtility.fixString(vo.getLastName(),75));
		conflictVo.setResourceName(StringUtility.fixString(vo.getResourceName(),75));
		conflictVo.setResProvUnitCodeName(vo.getResProvUnitCode());
		conflictVo.setGaccDispUnitCodeName(vo.getPdcCode());
		conflictVo.setIsResolved(false);
		
		return conflictVo;
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

								if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
									DataHandler handler = bodyPart.getDataHandler();
									String detachName = FilenameUtils.getName(handler.getName());
									//String currentDate=DateUtil.toDateString(Calendar.getInstance().getTime(), DateUtil.YYYYMMDD);
									Date dt=Calendar.getInstance().getTime();
									String currentDate=DateUtil.getMonthAsString(dt)+DateUtil.getDayAsString(dt)+DateUtil.getYearAsString(dt);
									String timestamp=String.valueOf(Calendar.getInstance().getTimeInMillis());
									currentDate=currentDate+"_"+timestamp;
									currentDate=currentDate.replaceAll(" ", "");
									if(detachName.indexOf("Overhead")>0){
										detachName="ResInvOH"+currentDate+".xml";
										rossEmail.isAceFile=false;
									}else{
										detachName="ResInvACE"+currentDate+".xml";
										rossEmail.isAceFile=true;
									}
									rossEmail.fullPathFilename=rossResFileFolder+detachName;
									rossEmail.filename=detachName;
									
									rossEmails.add(rossEmail);
									
									// save the file to drive
							        bodyPart.saveFile(rossResFileFolder+detachName);
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
	
	
	private Collection<RossEmail> getTestRossEmails() throws Exception {
		Collection<RossEmail> rossEmails = new ArrayList<RossEmail>();

		RossEmail rossEmail = new RossEmail();
		rossEmail.isAceFile=false;
		rossEmail.fullPathFilename="c:\\development\\e-isuite\\ross_resources\\DecemberTest.xml";
		rossEmail.filename="DecemberTest.xml";
		rossEmails.add(rossEmail);
		
		return rossEmails;
	}
	
}
