package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.datatransfer.DataExporterV2;
import gov.nwcg.isuite.core.datatransfer.DataExporterV3;
import gov.nwcg.isuite.core.datatransfer.DataImporterV2;
import gov.nwcg.isuite.core.domain.DataTransfer;
import gov.nwcg.isuite.core.domain.TransferControl;
import gov.nwcg.isuite.core.domain.impl.DataTransferImpl;
import gov.nwcg.isuite.core.domain.impl.TransferControlImpl;
import gov.nwcg.isuite.core.persistence.DataTransferDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.TransferControlDao;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.rules.DataTransferExportDataRulesHandler;
import gov.nwcg.isuite.core.rules.DataTransferGetEntFileRulesHandler;
import gov.nwcg.isuite.core.rules.DataTransferUnlockDataRulesHandler;
import gov.nwcg.isuite.core.service.DataTransferService;
import gov.nwcg.isuite.core.vo.DataTransferVo;
import gov.nwcg.isuite.core.vo.DbAvailVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.MultipartUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibm.xml.crypto.util.Base64;

public class DataTransferServiceImpl extends BaseService implements DataTransferService {
	private DataTransferDao dataTransferDao=null;
	
	public DataTransferServiceImpl(){

	}

	public void initialization(){
		dataTransferDao=(DataTransferDao)context.getBean("dataTransferDao");
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.DataTransferService#getExportHistory(java.lang.Long, java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getExportHistory(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			
			Collection<DataTransfer> entities = dataTransferDao.getExportHistory(incidentId, incidentGroupId);
			
			Collection<DataTransferVo> vos = new ArrayList<DataTransferVo>();
			
			if(CollectionUtility.hasValue(entities)){
				int size=entities.size();
				int x=0;
				for(DataTransfer entity : entities){
					x++;
					if(x==size){
						vos.add(DataTransferVo.getInstance(entity, true));
					}
				}
				//vos=DataTransferVo.getInstances(entities, true);
			}

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_EXPORT_HISTORY");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.DataTransferService#exportData(gov.nwcg.isuite.core.vo.DataTransferVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo exportData(DataTransferVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			String incidentOrGroupName=vo.getIncidentOrGroupName();
			
			DataTransferExportDataRulesHandler ruleHandler = new DataTransferExportDataRulesHandler(context);
			if(ruleHandler.execute(vo, dialogueVo)==AbstractRule._OK){
				Boolean processAsGroup=false;
				Long processAsGroupId=0L;
				IncidentDao idao = (IncidentDao)context.getBean("incidentDao");
				IncidentGroupDao igdao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				if(LongUtility.hasValue(vo.getIncidentId())){
					idao.updateSiteManaged(vo.getIncidentId(), true);
					processAsGroupId=idao.getIncidentGroupId(vo.getIncidentId());
					if(LongUtility.hasValue(processAsGroupId)){
						Collection<Long> incidentIds=igdao.getIncidentIdsInGroup(processAsGroupId);
						if(CollectionUtility.hasValue(incidentIds) && incidentIds.size()>1){
							processAsGroup=true;	
						}
					}
				}
				if(LongUtility.hasValue(vo.getIncidentGroupId())){
					igdao.updateSiteManaged(vo.getIncidentGroupId(), true);
					igdao.updateGroupIncidentsSiteManaged(vo.getIncidentGroupId(), true);
				}
				
				String strDtVersion=super.getSystemParamValue(SystemParameterTypeEnum.DT_VERSION);
				boolean dtVersion3=false;
				if(StringUtility.hasValue(strDtVersion)&& strDtVersion.equals("3"))
					dtVersion3=true;
				
				if(super.getRunMode().equalsIgnoreCase("SITE") 
						&& dtVersion3==true 
						&& BooleanUtility.isFalse(vo.getGenerateVersion2File())
						){
					
					TransferControlDao tcDao = (TransferControlDao)context.getBean("transferControlDao");
					
					// call function to gen uuid
					tcDao.generateTI();
					
					String groupTi="";
					String incidentTi="";
					
					if(LongUtility.hasValue(vo.getIncidentGroupId())){
						groupTi=tcDao.getGroupTi(vo.getIncidentGroupId());
						
						tcDao.createRecord(true,groupTi,"",0L);
					}else if(LongUtility.hasValue(vo.getIncidentGroupId()) && processAsGroup==true){
							groupTi=tcDao.getGroupTi(processAsGroupId);
							tcDao.createRecord(true,groupTi,"",0L);
					}else if(LongUtility.hasValue(vo.getIncidentId())){
						incidentTi=tcDao.getIncidentTi(vo.getIncidentId());
						tcDao.createRecord(false,"",incidentTi,0L);
					}else{
						tcDao.createRecord(false,"","",0L);
					}
					
			    	// init data exporter
					DataExporterV3 exporter = new DataExporterV3(super.context,super.getRunMode(),super.logger);
					DbAvailVo dbVo = new DbAvailVo();
					String dbName=((UserSessionVo)context.getBean("userSessionVo")).getSiteDatabaseName();
					
					dbVo.setDatabaseName(dbName.toLowerCase());
					String fname=vo.getFilename();
					fname=fname+"_Site-To-Ent";
					vo.setFilename(fname);
					
					String outputFile="";
					
					outputFile=exporter.exportData(vo, dbVo, groupTi,incidentTi);
					
					vo.setStoredFilepath(outputFile);
				}else{
			    	// init data exporter
					DataExporterV2 exporter = new DataExporterV2(super.context,super.getRunMode(),super.logger);

					String outputFile="";
					
					// export data
					String fname=vo.getFilename();
					if(super.getRunMode().equalsIgnoreCase("ENTERPRISE")){
						fname=fname+"_Ent-To-Site";
					}else{
						fname=fname+"_Site-To-Site";
					}
					vo.setFilename(fname);
					outputFile=exporter.exportData(vo);
					vo.setStoredFilepath(outputFile);
					
				}
				
				// create data transfer record
				DataTransfer entity = DataTransferVo.toEntity(null, vo, true);
				dataTransferDao.save(entity);
				dataTransferDao.flushAndEvict(entity);
				entity=dataTransferDao.getById(entity.getId(), DataTransferImpl.class);
				vo=DataTransferVo.getInstance(entity, true);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("EXPORT_DATA");
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.dataTransfer", "info.generic" , new String[]{"A transition file was created."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(vo);
				//dialogueVo.setResultObjectAlternate(exporter.xmlString.toString().getBytes());
				
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo getDataStewardListFromDb(DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();

		try{
			UserDao userDao=(UserDao)context.getBean("userDao");
			
			Collection<UserVo> vos = userDao.getDataStewardUsers();
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_DATASTEWARD_LIST_FROM_DB");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.DataTransferService#getDataStewardList(gov.nwcg.isuite.core.vo.DataTransferVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getDataStewardList(DataTransferVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();
		String charset = "UTF-8";
		
		try{
			String enterpriseUrl=super.getSystemParamValue(SystemParameterTypeEnum.ENT_DATA_TRANSFER_DSUSERS_URL);
			FIPSEncryptor enc = FIPSEncryptorFactory.getDefault();
			Base64 base64 = new Base64();
			
			byte[] bytes1 = enc.encrypt(vo.getEnterpriseLoginName().getBytes());
			String strCred1=base64.encode(bytes1);
			
			byte[] bytes2 = enc.encrypt(vo.getEnterprisePassword().getBytes());
			String strCred2=base64.encode(bytes2);
			
			String cred=strCred1+"|"+strCred2;
			
			MultipartUtility multipart = new MultipartUtility(enterpriseUrl, charset);

			multipart.addHeaderField("User-Agent", "e-ISuite");
			multipart.addFormField("cred", cred);
			multipart.addFormField("fillerfield", "");

			List<String> response = multipart.finish();
			String result="";
			Collection<UserVo> userVos = new ArrayList<UserVo>();
			
			for (String line : response) {
				result=result+line;
			}

			if(!result.startsWith("NO_USERS")){
				userVos=UserVo.buildList(result);
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("GET_DATASTEWARD_LIST");
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(userVos);
			}else{
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("GET_DATASTEWARD_LIST_NOUSERS");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.dataTransfer", "info.0030.01" , new String[]{"Enterprise System has no Data Steward Users setup."}, MessageTypeEnum.URGENT));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);
			
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
		
	}
	
	public DialogueVo exportDataToEnterprise(DataTransferVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();
		
		try{

			String enterpriseUrl=super.getSystemParamValue(SystemParameterTypeEnum.ENT_DATA_TRANSFER_IMPORT_URL);
			
			DataTransferExportDataRulesHandler ruleHandler = new DataTransferExportDataRulesHandler(context);
			if(ruleHandler.execute(vo, dialogueVo)==AbstractRule._OK){
				String mode=vo.getMode();
				
				if(mode.equals("precheck")){
			    	// init data exporter
					DataExporterV2 exporter = new DataExporterV2(super.context,super.getRunMode(),super.logger);

					// export data
					if(!StringUtility.hasValue(vo.getExportFile())){
						String outputFile=exporter.exportData(vo);
						vo.setStoredFilepath(outputFile);
						vo.setExportFile(outputFile);
						vo.setIncidentGroupTransferableIdentity(exporter.incidentGroupTi);
					}
					
					// create data transfer record
					DataTransfer entity = DataTransferVo.toEntity(null, vo, true);
					dataTransferDao.save(entity);
					dataTransferDao.flushAndEvict(entity);
					vo.setId(entity.getId());
					//entity=dataTransferDao.getById(entity.getId(), DataTransferImpl.class);
					//vo=DataTransferVo.getInstance(entity, true);
					
					// do prechecks first (authentication, validation, etc..)
					String precheckStatus=this.doEnterpriseTransfer(vo,mode,enterpriseUrl);
					if(precheckStatus.startsWith("SUCCESS")){
						// ok
						CourseOfActionVo coaVo = new CourseOfActionVo();
						coaVo.setCoaName("EXPORT_DATA_TO_ENTERPRISE_PRECHECK_OK");
						coaVo.setIsDialogueEnding(Boolean.TRUE);
						coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
						dialogueVo.setCourseOfActionVo(coaVo);
						dialogueVo.setResultObject(vo);
						
					}else if(precheckStatus.startsWith("PROMPTNAME")){
						// prompt user for group name
						CourseOfActionVo coaVo = new CourseOfActionVo();
						coaVo.setCoaName("EXPORT_DATA_TO_ENTERPRISE_PROMPTNAME");
						coaVo.setIsDialogueEnding(Boolean.TRUE);
						coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
						dialogueVo.setCourseOfActionVo(coaVo);
						dialogueVo.setResultObject(vo);
					}else{
						// error
						CourseOfActionVo coaVo = new CourseOfActionVo();
						coaVo.setCoaName("EXPORT_DATA_TO_ENTERPRISE");
						coaVo.setIsDialogueEnding(Boolean.TRUE);
						coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
						coaVo.setMessageVo(new MessageVo("text.dataTransfer", "info.generic" , new String[]{precheckStatus}, MessageTypeEnum.CRITICAL));
						coaVo.setIsDialogueEnding(true);
						dialogueVo.setCourseOfActionVo(coaVo);
						dialogueVo.setResultObject(vo);
					}
					
				}else{
					// send file and process
					String finalStatus=this.doEnterpriseTransfer(vo,"final",enterpriseUrl);
					
					if(finalStatus.startsWith("SUCCESS")){
						// ok
						CourseOfActionVo coaVo = new CourseOfActionVo();
						coaVo.setCoaName("EXPORT_DATA_TO_ENTERPRISE");
						coaVo.setIsDialogueEnding(Boolean.TRUE);
						coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
						coaVo.setMessageVo(new MessageVo("text.dataTransfer", "info.generic" , new String[]{"The Incident/Incident Group was transitioned to Enterprise."}, MessageTypeEnum.INFO));
						coaVo.setIsDialogueEnding(true);
						dialogueVo.setCourseOfActionVo(coaVo);
						dialogueVo.setResultObject(vo);
					}else{
						CourseOfActionVo coaVo = new CourseOfActionVo();
						coaVo.setCoaName("EXPORT_DATA_TO_ENTERPRISE");
						coaVo.setIsDialogueEnding(Boolean.TRUE);
						coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
						coaVo.setMessageVo(new MessageVo("text.dataTransfer", "info.generic" , new String[]{finalStatus}, MessageTypeEnum.CRITICAL));
						coaVo.setIsDialogueEnding(true);
						dialogueVo.setCourseOfActionVo(coaVo);
						dialogueVo.setResultObject(vo);
					}
				}

			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.DataTransferService#getExportData(gov.nwcg.isuite.core.vo.DataTransferVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getExportData(DataTransferVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			
			String file=vo.getStoredFilepath();
			
			byte[] fileBytes=FileUtil.getFileContentsBytes(file);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_EXPORT_DATA");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObjectAlternate(fileBytes);
				
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.DataTransferService#importDataFromFile(byte[], java.lang.String, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo importDataFromFile(byte[] xmlByteArray, String password, UserVo dsUserVo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			// init data importer
			DataImporterV2 importer = new DataImporterV2(super.context,super.getRunMode(),super.logger);
			importer.xmlByteArray=xmlByteArray;
			importer.filePassword=password;
			importer.fromWebServlet=false;

			try{
				String val=super.getSystemParamValue(SystemParameterTypeEnum.DATA_TRANSFER_LOGGING);
				if(StringUtility.hasValue(val) && val.equalsIgnoreCase("1"))
					importer.setDoLogging("1");
			}catch(Exception ignore){
			}
			
			if(null != dsUserVo && LongUtility.hasValue(dsUserVo.getId())){
				importer.dataStewardUserId=dsUserVo.getId();
			}

			String strDtVersion=super.getSystemParamValue(SystemParameterTypeEnum.DT_VERSION);
			boolean dtVersion3=false;
			if(StringUtility.hasValue(strDtVersion)&& strDtVersion.equals("3"))
				dtVersion3=true;
			
			importer.importData(dialogueVo, dtVersion3);
			
			if(importer.ruleHandlerFailure==false){
				if(super.getRunMode().equalsIgnoreCase("ENTERPRISE") 
						&& dtVersion3==true){
					IncidentGroupDao igDao=(IncidentGroupDao)context.getBean("incidentGroupDao");
					// make sure site managed is false
					if(StringUtility.hasValue(importer.incidentGroupTi)){
						igDao.updateSiteManaged(importer.incidentGroupTi, false);
						igDao.updateGroupIncidentsSiteManaged(importer.incidentGroupTi, false);
						// add user to group
						if(LongUtility.hasValue(importer.dataStewardUserId)){
							this.dataTransferDao.addGroupUser(importer.incidentGroupTi,importer.dataStewardUserId);
						}

						Collection<Long> incidentIds = igDao.getGroupIncidentIds(importer.incidentGroupTi);
						if(CollectionUtility.hasValue(incidentIds)){
							for(Long id : incidentIds){
								this.dataTransferDao.addIncidentUser(id, importer.dataStewardUserId);
							}
						}
					}
					if(StringUtility.hasValue(importer.incidentTi)){
						this.dataTransferDao.doIncidentSiteManagedFalse(importer.incidentTi);
						if(LongUtility.hasValue(importer.dataStewardUserId)){
							this.dataTransferDao.addIncidentUser(importer.incidentTi, importer.dataStewardUserId);
						}else{
							// default to logged in user
							if(null != super.getUserSessionVo()){
								Long userid=super.getUserSessionVo().getUserId();
								this.dataTransferDao.addIncidentUser(importer.incidentTi, userid);
							}
						}
					}

					// if is group and group_name is still SITE_GROUP_xxxxxxxx
					// need to prompt user for a group name
					if(StringUtility.hasValue(importer.incidentGroupTi)){
						String groupName=igDao.getGroupNameByTi(importer.incidentGroupTi);
						if(StringUtility.hasValue(groupName) && groupName.startsWith("SITE_GROUP_")){
							dialogueVo.setResultObjectAlternate4("PROMPT_GROUP_NAME");
							dialogueVo.setResultObjectAlternate3(importer.incidentGroupTi);
						}
					}
				}
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("IMPORT_DATA_FROM_FILE");
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				if(BooleanUtility.isTrue(importer.hasException)){
					coaVo.setCoaType(CourseOfActionTypeEnum.ERROR);
					ErrorObject errorObject = new ErrorObject(ErrorEnum._90000_ERROR,new String[]{importer.importException});
					coaVo.getErrorObjectVos().add(errorObject);
				}else{
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(new MessageVo("text.dataTransfer", "info.generic" , new String[]{"The transition file was imported."}, MessageTypeEnum.INFO));
				}
				dialogueVo.setCourseOfActionVo(coaVo);
			}else{
				// do nothing dialogueVo should already have the coa
			}

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	private String doEnterpriseTransfer(DataTransferVo vo
											, String mode
											,String enterpriseUrl) throws ServiceException {
		String charset = "UTF-8";
		String result="";
		
		try{
			String revisionLevel=super.getRevisionLevel();
			String groupName="";
			String groupTi="";
			
			if(LongUtility.hasValue(vo.getIncidentGroupId())){
				groupTi=vo.getIncidentGroupTransferableIdentity();
				
				if(StringUtility.hasValue(vo.getIncidentGroupName()))
					groupName=vo.getIncidentGroupName();
			}
			
			FIPSEncryptor enc = FIPSEncryptorFactory.getDefault();
			Base64 base64 = new Base64();
			
			byte[] bytes1 = enc.encrypt(vo.getEnterpriseLoginName().getBytes());
			String strCred1=base64.encode(bytes1);
			
			byte[] bytes2 = enc.encrypt(vo.getEnterprisePassword().getBytes());
			String strCred2=base64.encode(bytes2);
			
			String cred=strCred1+"|"+strCred2;
			
			MultipartUtility multipart = new MultipartUtility(enterpriseUrl, charset);

			multipart.addHeaderField("User-Agent", "e-ISuite");
			multipart.addFormField("mode", mode);
			multipart.addFormField("cred", cred);
			multipart.addFormField("incidentsti", "");
			multipart.addFormField("incidentgroupti", groupTi);
			multipart.addFormField("revisionlevel", revisionLevel);
			multipart.addFormField("incidentgroupname", groupName);
			if(!LongUtility.hasValue(vo.getEnterpriseDSUserId())){
				multipart.addFormField("dsuserid", "0");
			}else{
				multipart.addFormField("dsuserid", String.valueOf(vo.getEnterpriseDSUserId()));
			}
			multipart.addFormField("fillerfield", "");

			if(mode.equals("final")){
				String file=vo.getExportFile();
				File uploadFile1 = new File(file);
				multipart.addFilePart("fileUpload", uploadFile1);
			}
			
			List<String> response = multipart.finish();

			for (String line : response) {
				result=result+line;
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.DataTransferService#getEnterpriseFile(gov.nwcg.isuite.core.vo.DataTransferVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getEnterpriseFile(DataTransferVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			DataTransferGetEntFileRulesHandler ruleHandler = new DataTransferGetEntFileRulesHandler(context);
			if(ruleHandler.execute(vo, super.logger,dialogueVo)==AbstractRule._OK){
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("GET_ENTERPRISE_FILE");
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.dataTransfer", "info.generic" , new String[]{"The Incident/Incident Group was transitioned from Enterprise."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
		
	}

	public DialogueVo unlock(DataTransferVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			DataTransferUnlockDataRulesHandler ruleHandler = new DataTransferUnlockDataRulesHandler(context);
			if(ruleHandler.execute(vo, dialogueVo)==AbstractRule._OK){
				IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
				IncidentGroupDao incidentGroupDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				Long groupId=0L;
				
				if(LongUtility.hasValue(vo.getIncidentId())){
					incidentDao.updateSiteManaged(vo.getIncidentId(), false);
					
					groupId=incidentDao.getIncidentGroupId(vo.getIncidentId());
				}else
					groupId=vo.getIncidentGroupId();

				if(LongUtility.hasValue(groupId)){
					incidentGroupDao.updateSiteManaged(groupId, false);
					Collection<Long> incidentIds = incidentGroupDao.getIncidentIdsInGroup(groupId);
					for(Long id : incidentIds){
						incidentDao.updateSiteManaged(id, false);
					}
				}
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("UNLOCK");
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.dataTransfer", "info.generic" , new String[]{"The Incident/Incident Group was unlocked."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo isLocked(DataTransferVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
			String isLocked="N";
			
			if(LongUtility.hasValue(vo.getIncidentId())){
				Boolean rslt=incidentDao.isIncidentLocked(vo.getIncidentId(), "INCIDENT");
				if(rslt==true)
					isLocked="Y";
			}
			if(LongUtility.hasValue(vo.getIncidentGroupId())){
				Boolean rslt=incidentDao.isIncidentLocked(vo.getIncidentGroupId(), "INCIDENTGROUP");
				if(rslt==true)
					isLocked="Y";
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("IS_LOCKED");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(isLocked);
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo saveGroupName(String name,String ti, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();

		try{
			IncidentGroupDao igDao=(IncidentGroupDao)context.getBean("incidentGroupDao");
			int cnt=igDao.getGroupNameCountByTi(name.toUpperCase(),ti);
			if(cnt>0){
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_GROUP_NAME_ERROR");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(
						new MessageVo(
							"text.incidentGroup", 
							ErrorEnum._0200_DUPLICATE_INCIDENT_GROUP_NAME.getErrorName(), 
							new String[] { name.toUpperCase() },
							MessageTypeEnum.CRITICAL));

				dialogueVo.setCourseOfActionVo(coaVo);
			}else{
				igDao.saveGroupName(name.toUpperCase(),ti);
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_GROUP_NAME");
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.dataTransfer", "info.generic" , new String[]{"The transition file was imported."}, MessageTypeEnum.INFO));
				dialogueVo.setCourseOfActionVo(coaVo);
			}
			
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		return dialogueVo;
	}
	
}
