package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.filter.impl.RossResourceImportFilterImpl;
import gov.nwcg.isuite.core.persistence.ResInvImportDao;
import gov.nwcg.isuite.core.persistence.ResourceDao;
import gov.nwcg.isuite.core.service.RossResourceImportService;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.ResInvImportVo;
import gov.nwcg.isuite.core.vo.RossResourceXmlFileDataVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.ErrorTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.xml.XmlHandler;
import gov.nwcg.isuite.framework.xml.XmlSchemaTypeEnum;
import gov.nwcg.isuite.xml.ross.Dataset;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

public class RossResourceImportServiceImpl extends BaseService implements RossResourceImportService {
	private ResourceDao resourceDao = null;
	private ResInvImportDao resInvImportDao=null;
	
	public RossResourceImportServiceImpl(){
		
	}

	public void initialization(){
		this.resourceDao =  (ResourceDao)context.getBean("resourceDao");
		this.resInvImportDao =  (ResInvImportDao)context.getBean("resInvImportDao");
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.RossResourceImportService#getImportHistory(gov.nwcg.isuite.core.filter.impl.RossResourceImportFilterImpl, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getImportHistory(RossResourceImportFilterImpl filter,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			Date fromDate=null;
			Date toDate=null;
			
			if(StringUtility.hasValue(filter.getFromDate())){
				fromDate=DateUtil.toDate(filter.getFromDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
				fromDate=DateUtil.subtractDaysFromDate(fromDate, 1);
			}else{
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("ValidationError");
				coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
				coaVo.setIsDialogueEnding(true);
				Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
				ErrorObject errorObject = new ErrorObject("info.generic", "From Date is required.");
				errorObject.setErrorType(ErrorTypeEnum.VALIDATION_ERROR);
				errorObjects.add(errorObject);
				coaVo.setErrorObjectVos(errorObjects);
				dialogueVo.setCourseOfActionVo(coaVo);
				return dialogueVo;
			}
			if(StringUtility.hasValue(filter.getToDate())){
				toDate=DateUtil.toDate(filter.getToDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
				toDate=DateUtil.addDaysToDate(toDate, 1);
			}

			if(DateUtil.hasValue(fromDate) && DateUtil.hasValue(toDate) && toDate.before(fromDate))
				throw new Exception("To Date cannot be before From Date");
			

			Collection<ResInvImportVo> vos = this.resInvImportDao.getByDates(fromDate, toDate);

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_RES_INV_IMPORT_HISTORY");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
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
			
			/*
			 * construct the ross xml file path
			 * and verify file exists
			 */
			String rossXmlFile = rossFileFolder + filename;
			if(!FileUtil.fileExists(rossXmlFile))
            	super.handleException(ErrorEnum._90000_ERROR, "Unable to find file: " + rossXmlFile);
			
			/*
			 * Parse the xml file
			 */
			XmlHandler xmlHandler = new XmlHandler(XmlSchemaTypeEnum.ROSS_XML_DATA, xsdBasePath);
			Dataset rootNode = (Dataset)xmlHandler.unmarshall(new File(rossXmlFile));

			Boolean isACEFile=true;
			if(filename.indexOf("Overhead")>0)
				isACEFile=false;
			
			/*
			 * Convert xml data to RossResourceXmlFileDataVos
			 */
			Collection<RossResourceXmlFileDataVo> dataVos=RossResourceXmlFileDataVo.getInstances(
															 isACEFile
															  ,rootNode.getMetadata() 
												   			  ,rootNode.getData().getRow());
			
			// Create/Update Resource table
			this.createNewResources(dataVos);
			this.updateExistingResources(dataVos);
			
			CourseOfActionVo coa = new CourseOfActionVo();
			
			coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coa.setCoaName("XML_DATA_IMPORT_COMPLETE");
			coa.setMessageVo(new MessageVo("text.eISuite", "info.generic", new String[]{"ROSS Resource File imported."}, MessageTypeEnum.INFO));
			coa.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coa);
			
			return dialogueVo;
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.RossResourceImportService#processRossResourcesImport(java.lang.Boolean, java.util.Collection)
	 */
	public void processRossResourcesImport(Boolean isOverheadFile,Collection<RossResourceXmlFileDataVo> vos) throws ServiceException {
	
		try{
			Collection<OrganizationVo> orgVos = super.getGlobalCacheVo().getOrganizationVos();
			
			// cognos sends duplicates, keep track of what was already processed
			HashMap<Long,Long> map = new HashMap<Long,Long>();
			
			for(RossResourceXmlFileDataVo vo : vos){

				// only process ones not already processed, skip duplicates
				if(!map.containsKey((Long)vo.getResId())){
					Resource resource = resourceDao.getPermanentResourceByResId(vo.getResId());
					if(null != resource){
						
					}else{
						// create new
						
						// todo: validate vo before processing
						Boolean hasError=false;
						
						resource = new ResourceImpl();
						resource.setPermanent(true);
						resource.setRossResId(vo.getResId());
						resource.setEnabled(true);
						resource.setContracted(false);
						resource.setLeader(false);
						
						OrganizationVo orgVo = OrganizationVo.getOrgByUnitCode(vo.getResProvUnitCode(), orgVos);
						OrganizationVo pdcVo = OrganizationVo.getDispatchCenterByUnitCode(vo.getPdcCode(), orgVos);
						if(null != orgVo)
							resource.setOrganization(OrganizationVo.toEntity(null,orgVo, false));
						else{
							// add to conflict
							hasError=true;
						}
						if(null != pdcVo)
							resource.setPrimaryDispatchCenter(OrganizationVo.toEntity(null,pdcVo, false));
						else{
							// add to conflict
							hasError=true;
						}
						
						if(isOverheadFile==true){
							resource.setFirstName(vo.getFirstName().toUpperCase().trim());
							resource.setLastName(vo.getLastName().toUpperCase().trim());
							resource.setPerson(true);
						}else{
							resource.setResourceName(vo.getResourceName().toUpperCase().trim());
							resource.setPerson(false);
						}
						
						if(!hasError){
							resourceDao.save(resource);
							resourceDao.flushAndEvict(resource);
						}
					}
					map.put(vo.getResId(), vo.getResId());
				}
				
			}
		}catch(Exception e){
			
		}
	}
	
	private void createNewResources(Collection<RossResourceXmlFileDataVo> vos){
		// get list of resources that need to created
	}
	
	private void updateExistingResources(Collection<RossResourceXmlFileDataVo> vos){
		// get list of resources that need to be updated
	}

	public static void main(String[] args){
		
		try{
			String filename="eisuiteACE.xml";
			//String filename="eisuiteOverhead.xml";
			
			/*
			 * Get the xsd_base_path and ross folder location from the system param table
			 */
			String rossFileFolder = "c:/workspace/isuite-core/Webroot/rossFiles/";
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
			XmlHandler xmlHandler = new XmlHandler(XmlSchemaTypeEnum.ROSS_XML_DATA, xsdBasePath);
			Dataset rootNode = (Dataset)xmlHandler.unmarshall(new File(rossXmlFile));

			Boolean isACEFile=true;
			if(filename.indexOf("Overhead")>0)
				isACEFile=false;
			
			/*
			 * Convert xml data to RossResourceXmlFileDataVos
			 */
			Collection<RossResourceXmlFileDataVo> dataVos = new ArrayList<RossResourceXmlFileDataVo>();
			dataVos=RossResourceXmlFileDataVo.getInstances(isACEFile,rootNode.getMetadata(), 
												   				  rootNode.getData().getRow());
			
			//System.out.println(dataVos.size());
			
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
}
