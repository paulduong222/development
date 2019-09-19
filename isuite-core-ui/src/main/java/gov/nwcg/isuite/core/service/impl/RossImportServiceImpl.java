package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.RossXmlFile;
import gov.nwcg.isuite.core.domain.impl.RossXmlFileImpl;
import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.filter.RossIncidentFilter;
import gov.nwcg.isuite.core.filter.impl.IncidentFilterImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.RossIncDataBlacklistDao;
import gov.nwcg.isuite.core.persistence.RossXmlFileDao;
import gov.nwcg.isuite.core.rules.RossImportProcessBeginRulesHandler;
import gov.nwcg.isuite.core.rules.RossImportProcessEndRulesHandler;
import gov.nwcg.isuite.core.rules.RossImportProcessRulesHandler;
import gov.nwcg.isuite.core.service.RossImportService;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.RestrictedIncidentUserVo;
import gov.nwcg.isuite.core.vo.RossIncDataBlacklistGridVo;
import gov.nwcg.isuite.core.vo.RossUploadVo;
import gov.nwcg.isuite.core.vo.RossXmlFileDataVo;
import gov.nwcg.isuite.core.vo.RossXmlFileGridVo;
import gov.nwcg.isuite.core.vo.RossXmlFileVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.rossimport2.RossImportVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.IsuiteException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;
import gov.nwcg.isuite.framework.xml.XmlHandler;
import gov.nwcg.isuite.framework.xml.XmlSchemaTypeEnum;
import gov.nwcg.isuite.xml.ross.Dataset;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

public class RossImportServiceImpl extends BaseService implements RossImportService {
	private RossXmlFileDao rxfDao = null;
	
	public RossImportServiceImpl(){
		
	}

	public void initialization(){
		this.rxfDao =  (RossXmlFileDao)context.getBean("rossXmlFileDao");
	}

	/**
	 * @param uploadId
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getUploadStatus(String uploadId,DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			System.out.println("checking status");
			String status = "";
			
			RossUploadVo ruv = (RossUploadVo)context.getBean("rossUploadVo");
			if(ruv.isComplete(uploadId))
				status="COMPLETE";
			
			CourseOfActionVo coa = new CourseOfActionVo();
			dialogueVo.setCourseOfActionVo(coa);
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coa.setCoaName("GET_UPLOAD_STATUS");
			coa.setIsDialogueEnding(true);
			dialogueVo.setResultObject(status);
			
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.RossImportService#getRossImportedList(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, gov.nwcg.isuite.core.filter.RossIncidentFilter)
	 */
	public DialogueVo getRossImportedList(DialogueVo dialogueVo, RossIncidentFilter filter) throws ServiceException {
	
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			filter.setImported(Boolean.TRUE);
			filter.setCurrentUserId(super.getUserSessionVo().getUserId());
			Collection<RossXmlFileGridVo> vos = this.rxfDao.getGrid(filter);

			dialogueVo.setRecordset(vos);
			CourseOfActionVo coa = new CourseOfActionVo();
			dialogueVo.setCourseOfActionVo(coa);
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coa.setCoaName("DISPLAY_ROSS_IMPORTED_INCIDENT_LIST");
			coa.setIsDialogueEnding(true);
			
			return dialogueVo;
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.RossImportService#uploadFileComplete(java.lang.String)
	 */
	public DialogueVo uploadFileComplete(String filename, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			if(!StringUtility.hasValue(filename))
            	super.handleException(ErrorEnum.ERROR_NULL,"Filename");
			
			/*
			 * Get the xsd_base_path and ross folder location from the system param table
			 */
			String rossFileFolder = super.getSystemParamValue(SystemParameterTypeEnum.ROSS_XML_FOLDER );
			String xsdBasePath = super.getSystemParamValue(SystemParameterTypeEnum.XSD_BASE_PATH);
			
			
			//rossFileFolder = "c:/workspaces/isuite-core/Webroot/rossFiles/";
			//xsdBasePath = "c:/workspaces/isuite-core/src/main/resources/xsd/";
			
			/*
			 * construct the ross xml file path
			 * and verify file exists
			 */
			String rossXmlFile = rossFileFolder + filename;
			StringBuffer rossBuffer = new StringBuffer();
			
			if(!FileUtil.fileExists(rossXmlFile))
            	super.handleException(ErrorEnum._90000_ERROR, "Unable to find file: " + rossXmlFile);
			else{
				rossBuffer=FileUtil.getFileContents(rossXmlFile);
				rossBuffer=new StringBuffer(rossBuffer.toString().replaceAll("&", "&amp;"));
			}
			
			/*
			 * Parse the xml file
			 */
			Boolean useOldFormat=true;
			try{
				String val=super.getSystemParamValue(SystemParameterTypeEnum.ROSS_FORMAT_VERSION);
				if(StringUtility.hasValue(val) && val.equals("2"))
					useOldFormat=false;
			}catch(Exception ee){
				// smother
			}
			
			if(useOldFormat==true){
				XmlHandler xmlHandler = new XmlHandler(XmlSchemaTypeEnum.ROSS_XML_DATA, xsdBasePath);
				Dataset rootNode = (Dataset)xmlHandler.unmarshall(rossBuffer);
				
				/*
				 * Convert xml data to RossXmlFileDataVos
				 */
				Collection<RossXmlFileDataVo> rxfDataVos = 
						RossXmlFileDataVo.getInstances(rootNode.getMetadata(), 
													   rootNode.getData().getRow());

				
				Collection<RossXmlFileVo> rxfVos = RossXmlFileDataVo.getRossXmlFileVos(rxfDataVos);
				
				String newFileName =  "_" + Calendar.getInstance().getTimeInMillis();
				FileUtil.renameFile(rossXmlFile, rossFileFolder + newFileName );
				
				/*
				 * Create the isw_ross_xml_file(s) and isw_ross_xml_file_data record(s).
				 */
				for(RossXmlFileVo rxfVo : rxfVos){
					RossXmlFile rossXmlFileEntity = new RossXmlFileImpl();
					rossXmlFileEntity.setFileName(newFileName);
					rossXmlFileEntity.setIncidentName(rxfVo.getIncidentName());
					rossXmlFileEntity.setIncidentNumber(rxfVo.getIncidentNumber());
					rossXmlFileEntity.setIncidentEventType(rxfVo.getIncidentEventType());
					rossXmlFileEntity.setIncidentStartDate(rxfVo.getIncidentStartDate());
					rossXmlFileEntity.setImportStatus("NA");
					rossXmlFileEntity.setRossIncId(rxfVo.getRossIncidentId());
					rossXmlFileEntity.setUnitCode(RossXmlFileVo.extractUnitCode(rxfVo.getIncidentNumber()));
					
					Collection<RossXmlFileDataVo> dataVos = RossXmlFileDataVo.getByInc(rxfVo.getRossIncidentId(),rxfDataVos);
					
					rossXmlFileEntity.setRossXmlFileDatas(RossXmlFileDataVo.toEntities(dataVos, true, rossXmlFileEntity));

					rxfDao.save(rossXmlFileEntity);
					
					// delete any old ross incident files previous to this one that were not completed or deleted
					Collection<RossXmlFile> oldRxfs = rxfDao.getByIncidentNumber(rossXmlFileEntity.getIncidentNumber(), rossXmlFileEntity.getId());
					if(CollectionUtility.hasValue(oldRxfs)){
						for(RossXmlFile r : oldRxfs){
							if(!r.getImportStatus().equals("DELETED") && !r.getImportStatus().equals("IMPORTED") )
								this.deleteRossImportFile(r.getId(), new DialogueVo());
						}
					}
				}
				
			}else{
				XmlHandler xmlHandler = new XmlHandler(XmlSchemaTypeEnum.ROSS_XML_DATA2, xsdBasePath);
				gov.nwcg.isuite.xml.ross2.DataSet rootNode = (gov.nwcg.isuite.xml.ross2.DataSet)xmlHandler.unmarshall(new File(rossXmlFile));
				
				/*
				 * Convert xml data to RossXmlFileDataVos
				 */
				Collection<RossXmlFileDataVo> rxfDataVos = new ArrayList<RossXmlFileDataVo>();
				try{
					rxfDataVos = RossXmlFileDataVo.getInstances2(rootNode);
				}catch(IsuiteException ie){
					super.dialogueException(dialogueVo, ie);
					return dialogueVo;
				}catch(Exception ee){
					super.dialogueException(dialogueVo, ee);
					return dialogueVo;
				}

				
				Collection<RossXmlFileVo> rxfVos = RossXmlFileDataVo.getRossXmlFileVos(rxfDataVos);
				
				String newFileName =  "_" + Calendar.getInstance().getTimeInMillis();
				FileUtil.renameFile(rossXmlFile, rossFileFolder + newFileName );
				
				/*
				 * Create the isw_ross_xml_file(s) and isw_ross_xml_file_data record(s).
				 */
				for(RossXmlFileVo rxfVo : rxfVos){
					RossXmlFile rossXmlFileEntity = new RossXmlFileImpl();
					rossXmlFileEntity.setFileName(newFileName);
					rossXmlFileEntity.setIncidentName(rxfVo.getIncidentName());
					rossXmlFileEntity.setIncidentNumber(rxfVo.getIncidentNumber());
					rossXmlFileEntity.setIncidentEventType(rxfVo.getIncidentEventType());
					rossXmlFileEntity.setIncidentStartDate(rxfVo.getIncidentStartDate());
					rossXmlFileEntity.setImportStatus("NA");
					rossXmlFileEntity.setRossIncId(rxfVo.getRossIncidentId());
					rossXmlFileEntity.setUnitCode(RossXmlFileVo.extractUnitCode(rxfVo.getIncidentNumber()));
					
					Collection<RossXmlFileDataVo> dataVos = RossXmlFileDataVo.getByInc(rxfVo.getRossIncidentId(),rxfDataVos);
					
					rossXmlFileEntity.setRossXmlFileDatas(RossXmlFileDataVo.toEntities(dataVos, true, rossXmlFileEntity));

					rxfDao.save(rossXmlFileEntity);
					
					// delete any old ross incident files previous to this one that were not completed or deleted
					Collection<RossXmlFile> oldRxfs = rxfDao.getByIncidentNumber(rossXmlFileEntity.getIncidentNumber(), rossXmlFileEntity.getId());
					if(CollectionUtility.hasValue(oldRxfs)){
						for(RossXmlFile r : oldRxfs){
							if(!r.getImportStatus().equals("DELETED") && !r.getImportStatus().equals("IMPORTED") )
								this.deleteRossImportFile(r.getId(), new DialogueVo());
						}
					}
				}
			}

			
//			CourseOfActionVo coa = new CourseOfActionVo();
//			coa.setCoaType(CourseOfActionTypeEnum.NOACTION);
//			coa.setCoaName("COMPLETE");
//			coa.setIsDialogueEnding(true);
//			dialogueVo.setCourseOfActionVo(coa);
			
			CourseOfActionVo coa = new CourseOfActionVo();
			
			coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coa.setCoaName("XML_DATA_IMPORT_COMPLETE");
			coa.setMessageVo(new MessageVo("text.rossImport", "text.rossXmlImportCompleted", null, MessageTypeEnum.INFO));
			coa.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coa);
			
			return dialogueVo;
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		//System.out.println("upload complete");
		
		//((RossUploadVo)context.getBean("rossUploadVo")).updateUploadData(uploadId, "COMPLETE");
		
		return dialogueVo;
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.RossImportService#getRossIncidentList(gov.nwcg.isuite.core.filter.RossIncidentFilter)
	 */
	public DialogueVo getRossIncidentList(DialogueVo dialogueVo, RossIncidentFilter filter) throws ServiceException {

		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			filter.setCurrentUserId(super.getUserSessionVo().getUserId());
			Collection<RossXmlFileGridVo> vos = this.rxfDao.getGrid(filter);
			
			dialogueVo.setRecordset(vos);
			CourseOfActionVo coa = new CourseOfActionVo();
			dialogueVo.setCourseOfActionVo(coa);
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coa.setCoaName("DISPLAY_ROSS_INCIDENT_LIST");
			coa.setIsDialogueEnding(true);
			
			return dialogueVo;
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.RossImportService#deleteRossIncidentFile(java.lang.Long)
	 */
	public void deleteRossIncidentFile(Long id) throws ServiceException {
		try{
			
			RossXmlFile entity = rxfDao.getById(id, RossXmlFileImpl.class);

			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"RossXmlFile[id=+"+id+"]");

			entity.setImportStatus("DELETED");
			this.rxfDao.save(entity);
			this.rxfDao.flushAndEvict(entity);
			
			this.rxfDao.purgeDeletedFileData(entity.getId());
			
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			super.handleException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.RossImportService#deleteRossImportFile(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteRossImportFile(Long rossXmlFileId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			this.deleteRossIncidentFile(rossXmlFileId);
			
			if(null == dialogueVo)dialogueVo = new DialogueVo();

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaName("ROSS_FILE_DELETED");
			coaVo.setMessageVo(new MessageVo("text.rossImport", "text.rossFileDeleted", null, MessageTypeEnum.INFO));
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
		
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.RossImportService#processRossImport(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo processRossImport(String action,Long rossXmlFileId, DialogueVo dialogueVo) throws ServiceException {
		
		try{
			RossXmlFile entity = rxfDao.getById(rossXmlFileId, RossXmlFileImpl.class);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"RossXmlFile[id=+"+rossXmlFileId+"]");

			RossXmlFileVo rxfVo = RossXmlFileVo.getInstance(entity, true);

			if(null == dialogueVo)dialogueVo = new DialogueVo();
			
			/*
			 * Check if ross incident user is trying to import is already in e-isuite.
			 * If yes, verify user has access to the incident before allowing to import
			 */
			if(null == dialogueVo.getCourseOfActionVo()){
				Collection<IncidentVo> eisuiteIncidentVos=this.buildEISuiteIncidentsList(rxfVo);
				if(CollectionUtility.hasValue(eisuiteIncidentVos)){
					Boolean hasPerm=false;
					IncidentVo incVo = eisuiteIncidentVos.iterator().next();
					Long userId = super.getUserSessionVo().getUserId();
					
					for(RestrictedIncidentUserVo riuVo : incVo.getRestrictedIncidentUserVos()){
						if(riuVo.getUserVo().getId().compareTo(userId)==0){
							hasPerm=true;
						}
					}
					
					if(hasPerm==false){
						String msg="This user account ("+ super.getUserSessionVo().getUserLoginName() +") does not have access to e-ISuite Incident (" + incVo.getIncidentName() + " " + incVo.getHomeUnitVo().getUnitCode() + "-"+incVo.getIncidentNumber() + ").  For further assistance, contact your Account Manager or the e-ISuite Helpdesk.";
						CourseOfActionVo coaVo = new CourseOfActionVo();
						coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
						coaVo.setIsDialogueEnding(Boolean.TRUE);
						coaVo.setCoaName("NO USER ACCESS");
						//coaVo.setMessageVo(new MessageVo("text.rossImport", "info.generic" , new String[]{"The ROSS file you are trying to import is already in the e-ISuite system.  You must have access to the e-ISuite incident before you can import the ROSS file."}, MessageTypeEnum.CRITICAL));
						coaVo.setMessageVo(new MessageVo("text.rossImport", "info.generic" , new String[]{msg}, MessageTypeEnum.CRITICAL));
						dialogueVo.setCourseOfActionVo(coaVo);
						return dialogueVo;
					}
				}
			}
			
			RossImportProcessRulesHandler rulesHandler = new RossImportProcessRulesHandler(super.context);

			if(rulesHandler.execute(action,rxfVo,super.getUserVo(),dialogueVo) == AbstractRule._OK){
			
				/*
				 * Verify import completed instead of cancel or error
				 */
				CourseOfActionVo coaVo = dialogueVo.getCourseOfActionVo();
				if(null != coaVo && coaVo.getCoaName().equals("COMPLETE")){
					rxfDao.flushAndEvict(entity);
					entity = rxfDao.getById(entity.getId(), RossXmlFileImpl.class);

					// update isw_ross_xml_file status to IMPORTED
					entity.setImportStatus("IMPORTED");
					entity.setImportedDate(Calendar.getInstance().getTime());
					rxfDao.save(entity);
				}
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
			//super.handleException(e);
		}
		
		return dialogueVo;
	}

	public DialogueVo processRossImportBegin(Long rossXmlFileId, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			
			RossXmlFile entity = rxfDao.getById(rossXmlFileId, RossXmlFileImpl.class);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"RossXmlFile[id=+"+rossXmlFileId+"]");

			RossXmlFileVo rxfVo = RossXmlFileVo.getInstance(entity, true);
			
			RossImportProcessBeginRulesHandler ruleHandler = new RossImportProcessBeginRulesHandler(super.context);
			if(ruleHandler.execute(rxfVo, super.getUserVo(), null, dialogueVo)==AbstractRule._OK){

				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("ROSS_IMPORT_BEGIN");
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
				
				if(null != dialogueVo.getResultObjectAlternate4()){
					RossImportVo riVo=(RossImportVo)dialogueVo.getResultObjectAlternate4();
					if(LongUtility.hasValue(riVo.getEisuiteIncidentId())){
						riVo.setHasIncidentMatch(true);
						dialogueVo.setResultObjectAlternate4(riVo);
						dialogueVo.setResultObject(riVo);
					}else{
						dialogueVo.setResultObject(dialogueVo.getResultObjectAlternate4());
					}
				}else{
					dialogueVo.setResultObject(dialogueVo.getResultObjectAlternate4());
				}
				
				dialogueVo.setCourseOfActionVo(coaVo);
				
			}else{
				// todo
			}

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo processRossImportEnd(RossImportVo rossImportVo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			RossImportProcessEndRulesHandler ruleHandler = new RossImportProcessEndRulesHandler(super.context);
			if(ruleHandler.execute(rossImportVo, super.getUserVo(), dialogueVo)==AbstractRule._OK){

				RossXmlFile entity = rxfDao.getById(rossImportVo.getRossXmlFileId(), RossXmlFileImpl.class);

				// update isw_ross_xml_file status to IMPORTED
				entity.setImportStatus("IMPORTED");
				entity.setImportedDate(Calendar.getInstance().getTime());
				rxfDao.save(entity);
				rxfDao.flushAndEvict(entity);

				// purge imported records
				rxfDao.purgeImportedResources(entity.getId());
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("ROSS_IMPORT_END");
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.rossImport", "text.rossXmlImportCompleted", null, MessageTypeEnum.INFO));
				
				dialogueVo.setResultObject(rossImportVo);
				dialogueVo.setCourseOfActionVo(coaVo);
				
			}else{
				// todo
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		dialogueVo.setResultObjectAlternate4(null);
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.RossImportService#getRossIncidents(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, gov.nwcg.isuite.core.filter.IncidentFilter)
	 */
	public DialogueVo getRossIncidents(DialogueVo dialogueVo, IncidentFilter filter) throws ServiceException {
		
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			RossIncDataBlacklistDao dao = (RossIncDataBlacklistDao)context.getBean("rossIncDataBlacklistDao");
			
			Collection<IncidentGridVo> vos = dao.getRossIncidents(filter);
			
			dialogueVo.setRecordset(vos);
			CourseOfActionVo coa = new CourseOfActionVo();
			dialogueVo.setCourseOfActionVo(coa);
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coa.setCoaName("DISPLAY_ROSS_INCIDENT_LIST");
			coa.setIsDialogueEnding(true);
			
			return dialogueVo;
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}

	public DialogueVo getRossIncidentExcludedResources(DialogueVo dialogueVo, IncidentFilter filter) throws ServiceException {
		
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			RossIncDataBlacklistDao dao = (RossIncDataBlacklistDao)context.getBean("rossIncDataBlacklistDao");
			
			Collection<RossIncDataBlacklistGridVo> vos = dao.getByRossIncId(filter.getRossIncidentId());
			if(null == vos)
				vos = new ArrayList<RossIncDataBlacklistGridVo>();
			
			CourseOfActionVo coa = new CourseOfActionVo();
			dialogueVo.setCourseOfActionVo(coa);
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coa.setCoaName("DISPLAY_ROSS_INC_RESOURCE_LIST");
			coa.setIsDialogueEnding(true);
			dialogueVo.setRecordset(vos);

			return dialogueVo;
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}

	/**
	 * @param ids
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo reimportProcess(String action,Long rossXmlFileId, Collection<Object> ids, DialogueVo dialogueVo) throws ServiceException {

		try{
			if(null == dialogueVo)dialogueVo = new DialogueVo();
			
			RossXmlFile entity = rxfDao.getById(rossXmlFileId, RossXmlFileImpl.class);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"RossXmlFile[id=+"+rossXmlFileId+"]");

			if(CollectionUtility.hasValue(ids)){
				this.rxfDao.updateAllExcluded();
			}
			
			RossXmlFileVo rxfVo = RossXmlFileVo.getInstance(entity, true);
			Collection<Long> rossResReqIds = new ArrayList<Long>();
			
			for(Object id : ids){
				rossResReqIds.add(TypeConverter.convertToLong(id));
			}

			if(CollectionUtility.hasValue(rossResReqIds)){
				for(Long resReqId : rossResReqIds){
					rxfDao.updateIdByReqId(rossXmlFileId, resReqId);
				}
			}
			
			RossImportProcessBeginRulesHandler ruleHandler = new RossImportProcessBeginRulesHandler(super.context);
			if(ruleHandler.execute(rxfVo, super.getUserVo(), rossResReqIds, dialogueVo)==AbstractRule._OK){
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("ROSS_REIMPORT_BEGIN");
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);

				if(null != dialogueVo.getResultObjectAlternate4()){
					RossImportVo riVo=(RossImportVo)dialogueVo.getResultObjectAlternate4();
					if(LongUtility.hasValue(riVo.getEisuiteIncidentId())){
						riVo.setHasIncidentMatch(true);
						dialogueVo.setResultObjectAlternate4(riVo);
						dialogueVo.setResultObject(riVo);
					}else{
						dialogueVo.setResultObject(dialogueVo.getResultObjectAlternate4());
					}
				}else{
					dialogueVo.setResultObject(dialogueVo.getResultObjectAlternate4());
				}
				
				dialogueVo.setCourseOfActionVo(coaVo);
				
			}else{
				// todo
			}

			/*
			RossImportProcessDao ripDao = (RossImportProcessDao)context.getBean("rossImportProcessDao");
			
			// get the incident(s)
			IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
			ResourceDao resourceDao = (ResourceDao)context.getBean("resourceDao");
			
			Collection<Incident> incidents = incidentDao.getByRossIncidentId(TypeConverter.convertToLong(entity.getRossIncId()));
			
			for(Incident inc : incidents){
				Collection<RossImportProcessResourceVo> ripVos = ripDao.getRossResourcesByResIds(entity.getId(), rossResourceIds);
				IncidentResourceBuilder builder = new IncidentResourceBuilder(context,rxfVo,inc);
				for(RossImportProcessResourceVo ripResVo : ripVos){
					Resource resourceEntity = builder.buildResource(ripResVo,dialogueVo);
					resourceDao.save(resourceEntity);
				}
				
			}
			*/
			/*
			 * Set the blacklisted resource statuses to reimport
			 */
			/*
			RossIncDataBlacklistDao ridbDao = (RossIncDataBlacklistDao)context.getBean("rossIncDataBlacklistDao");

			ridbDao.updateStatuses(rossResourceIds, entity.getId(), "IMPORTED");
			
			CourseOfActionVo coa = new CourseOfActionVo();
			dialogueVo.setCourseOfActionVo(coa);
			coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coa.setCoaName("REPROCESS_RESOURCE_IMPORT");
			coa.setMessageVo(new MessageVo("text.rossImport", "info.generic", new String[]{"Resources were imported."}, MessageTypeEnum.INFO));
			coa.setIsDialogueEnding(true);
			dialogueVo.setResultObject(rossResourceIds);
			*/
		}catch(Exception e){
			super.handleException(e);
		}
		
		return dialogueVo;
	}
	
	private Collection<IncidentVo> buildEISuiteIncidentsList(RossXmlFileVo rxfVo) throws Exception{
		Collection<IncidentVo> vos = new ArrayList<IncidentVo>();
		Collection<IncidentVo> rtnVos = new ArrayList<IncidentVo>();

		IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
		
		IncidentFilter filter = new IncidentFilterImpl();

		/*
		 * do a look up by ross incident id
		 */
		filter.setRossIncId(TypeConverter.convertToLong(rxfVo.getRossIncidentId()));
		vos=incidentDao.getPossibleRossMatches(filter);
		
		if(null == vos || vos.size() < 1){
			// do a look up by other info
			filter.setRossIncId(null);
			String nbr="";
			String unitCode="";
			if(StringUtility.hasValue(rxfVo.getIncidentNumber())){
				filter.setIncidentTagNumber(rxfVo.getIncidentNumber());
				//unitCode=StringUtility.getTokenValue(rxfVo.getIncidentNumber(), "-", 2);
				//nbr=StringUtility.getTokenValue(rxfVo.getIncidentNumber(), "-", 3);
				//StringUtility.removeChar(nbr,'0');
			}

			if(DateUtil.hasValue(rxfVo.getIncidentStartDate())){
				Integer year=DateUtil.getYearAsInteger(rxfVo.getIncidentStartDate());
				filter.setIncidentYear(year);
			}
			
			//filter.setIncidentName(this.rxfVo.getIncidentName());
			//filter.setIncidentNumber(nbr);
			
			/*
			 * Query the incidents table and find best possible matches
			 */
			vos = incidentDao.getPossibleRossMatches(filter);
			
			return vos;
		}
		
		// reset rossincidentid to null
		for(IncidentVo vo : vos){
			vo.setRossIncidentId("");
			rtnVos.add(vo);
		}
		
		return rtnVos;
	}

	
	public static void main(String[] args){
		
		try{
			//String filename="UT_MILL_FLAT.xml";
			String filename="OR-MHF-000728_09222014_1311.xml";
			
			/*
			 * Get the xsd_base_path and ross folder location from the system param table
			 */
			//String rossFileFolder = "c:/workspace3/isuite-core/Webroot/rossFiles/";
			String rossFileFolder = "c:/development/e-isuite/ROSS Files/NewStructure/";
			String xsdBasePath = "c:/workspace/isuite-core/src/main/resources/common/xsd/";
			
			/*
			 * construct the ross xml file path
			 * and verify file exists
			 */
			String rossXmlFile = rossFileFolder + filename;
			boolean val = FileUtil.fileExists(rossXmlFile);
			//if(!FileUtil.fileExists(rossXmlFile))
			//	System.out.println("file does not exist");
			
			/*
			 * Parse the xml file
			 */
			XmlHandler xmlHandler = new XmlHandler(XmlSchemaTypeEnum.ROSS_XML_DATA2, xsdBasePath);
			gov.nwcg.isuite.xml.ross2.DataSet rootNode = (gov.nwcg.isuite.xml.ross2.DataSet)xmlHandler.unmarshall(new File(rossXmlFile));
			
			/*
			 * Convert xml data to RossXmlFileDataVos
			 */
			//Collection<RossXmlFileDataVo> rxfDataVos = 
			//		RossXmlFileDataVo.getInstances(rootNode.getMetadata(), 
			//									   rootNode.getData().getRow());
			Collection<RossXmlFileDataVo> rxfDataVos = 
					RossXmlFileDataVo.getInstances2(rootNode);
			
			//System.out.println(rxfDataVos.size());
			
			/*
			 * Create the isw_ross_xml_file and isw_ross_xml_file_data record(s).
			 */
			RossXmlFile rossXmlFileEntity = new RossXmlFileImpl();
			rossXmlFileEntity.setFileName(filename);
			rossXmlFileEntity.setImportStatus("NA");
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
}
