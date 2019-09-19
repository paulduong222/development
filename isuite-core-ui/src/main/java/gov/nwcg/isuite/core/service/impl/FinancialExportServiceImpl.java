/**
 * 
 */
package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.FinancialExport;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.domain.impl.FinancialExportImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.financialexport.FinancialExportGenerator;
import gov.nwcg.isuite.core.persistence.FinancialExportDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.rules.FinancialExportExportRulesHandler;
import gov.nwcg.isuite.core.rules.IsLockedRulesHandler;
import gov.nwcg.isuite.core.service.FinancialExportService;
import gov.nwcg.isuite.core.vo.FinancialExportVo;
import gov.nwcg.isuite.core.vo.IncidentGroupVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.cryptfile.FinExpEncryption;
import gov.nwcg.isuite.framework.cryptfile.ISWFileEncryption;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.xml.XmlHandler;
import gov.nwcg.isuite.framework.xml.XmlSchemaTypeEnum;
import gov.nwcg.isuite.xml.financialexport.ISuiteExport;

import java.math.BigDecimal;
import java.util.Collection;

/**
 *
 */
public class FinancialExportServiceImpl extends BaseService implements
		FinancialExportService {
	private FinancialExportDao financialExportDao;

	public FinancialExportServiceImpl(){
		super();
	}
	
	public void initialization(){
		financialExportDao = (FinancialExportDao)super.context.getBean("financialExportDao");
		
	}
	
	public DialogueVo getGrid(DialogueVo dialogueVo, Long incidentId, Long incidentGroupId) throws ServiceException {

		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try {
			Collection<FinancialExportVo> vos = financialExportDao.getGrid(incidentId, incidentGroupId);

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_FINANCIAL_EXPORT_GRID");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);

		} catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	public DialogueVo export(DialogueVo dialogueVo, FinancialExportVo financialExportVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try{
			Long id=0L;
			String type="";
			if(null != financialExportVo.getIncidentVo() && LongUtility.hasValue(financialExportVo.getIncidentVo().getId())){
				id=financialExportVo.getIncidentVo().getId();
				type="INCIDENT";
			}
			if(null != financialExportVo.getIncidentGroupVo() && LongUtility.hasValue(financialExportVo.getIncidentGroupVo().getId())){
				id=financialExportVo.getIncidentGroupVo().getId();
				type="INCIDENTGROUP";
			}
			if(LongUtility.hasValue(id)){
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(id, type, dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			
			FinancialExport entity = null;

			FinancialExportExportRulesHandler rulesHandler = new FinancialExportExportRulesHandler(context);

			if(rulesHandler.execute(entity, financialExportVo, dialogueVo)==FinancialExportExportRulesHandler._OK) {
				
				//Generate xml & encrypt
				XmlHandler xh = new XmlHandler();
				xh.setFormatXml(true);
				xh.setXmlSchemaType(XmlSchemaTypeEnum.FINANCIAL_EXPORT);
				
				ISuiteExport ie = new ISuiteExport();
				
				FinancialExportGenerator feg = new FinancialExportGenerator(super.context);
				
				ie = feg.generateFinancialExport(financialExportVo);
				
				StringBuffer sb = xh.marshall(ie);
				
//				System.out.println(sb.toString());
				
				//encrypt file
				String encFilPath = this.encryptFinancialExport(sb.toString(), financialExportVo.getFileName());
				
				byte[] bs = FileUtil.getFileContentsBytes(encFilPath);
				
				//Build course of action
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("EXPORT_FINANCIAL_EXPORT");
				coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
				coaVo.setIsDialogueEnding(Boolean.TRUE);

				dialogueVo.setCourseOfActionVo(coaVo);
				//comment out the following line for encryption
//				dialogueVo.setResultObject(sb.toString());
				//uncomment out the following line for encryption
				dialogueVo.setResultObject(bs);
				
			}

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	private String encryptFinancialExport(String financialExport, String fName) throws Exception {
		
		String filLocation = this.getOutputFileLocation();
		String clearTextFilPath = filLocation + fName + ".xml";
		
		FileUtil.writeFile(financialExport.getBytes(), clearTextFilPath);
		
		String encrFilPath = filLocation + fName + ".fex";
		
		ISWFileEncryption encFexp = new ISWFileEncryption();
		
		try {
			encFexp.tmpDir = FileUtil.iswCreateTmpPath();
			encFexp.aesKey = encFexp.genKey(null);
			encFexp.saveAESKey(encFexp.aesKey, encFexp.tmpDir,
					ISWFileEncryption.ISWPRIVKEY);
			encFexp.encrypt(encFexp.aesKey, encFexp.tmpDir, clearTextFilPath);
			encFexp.iswPackage(encFexp.tmpDir, encrFilPath);
		}catch (Exception e) {
			throw e;
		} finally {
			try {
				if (encFexp.tmpDir.exists()) {
					FileUtil.rDelete(encFexp.tmpDir);
				}
			} catch (Exception fe) {
				// smother
			}
		}
		
		return encrFilPath;
		
	}
	
	private String getOutputFileLocation() throws Exception {
		String location = null;
		SystemParameterDao spDao = (SystemParameterDao)super.context.getBean("systemParameterDao");

		try{
			SystemParameter spEntity = spDao.getByParameterName(SystemParameterTypeEnum.REPORT_OUTPUT_FOLDER.name());
			if( (null != spEntity) && ( null != spEntity.getParameterValue()) && (!spEntity.getParameterValue().isEmpty()) ){
				location = spEntity.getParameterValue();
			}
		}catch(Exception e){
			throw e;
		}
		
		return location;
	}
	
	
	public DialogueVo save(DialogueVo dialogueVo, FinancialExportVo financialExportVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		String sdebug="";
		
		try{
			
			FinancialExport entity = null;

			//save financial export record
			entity = FinancialExportVo.toEntity(entity, financialExportVo, true);
			sdebug="1";
			financialExportDao.save(entity);
			sdebug="2";
			financialExportDao.flushAndEvict(entity);
			sdebug="3";
			entity = financialExportDao.getById(entity.getId(), FinancialExportImpl.class);
			sdebug="4";
			financialExportVo=FinancialExportVo.getInstance(entity, true);
			sdebug="5";
			
			//save export record for each incident in incident group
			if (null != financialExportVo.getIncidentGroupVo()) {
				sdebug="6";
				IncidentGroupDao igDao = (IncidentGroupDao)super.context.getBean("incidentGroupDao");
				Collection<Long> incidentIdsInGroup = igDao.getIncidentIdsInGroup(financialExportVo.getIncidentGroupVo().getId());
				sdebug="7";
				
				for(Long incidentId : incidentIdsInGroup){
					sdebug="8";
					FinancialExport feEntity = new FinancialExportImpl();
					try{
						feEntity = FinancialExportVo.toEntity(feEntity, financialExportVo, true);
					}catch(Exception ee){
						sdebug=ee.getMessage();
						super.dialogueException(dialogueVo, ee);
						return dialogueVo;
					}
					sdebug="9";
					feEntity.setId(null);
					feEntity.setIncidentGroup(null);
					
					Incident incident = new IncidentImpl();
					incident.setId(incidentId);
					feEntity.setIncident(incident);
					
					feEntity.setIncidentReferenceId(null);
					feEntity.setIncidentName(null);
					financialExportDao.save(feEntity);
					sdebug="10";
					financialExportDao.flushAndEvict(feEntity);
				}
			}
			
			//update records to exported
			FinancialExportGenerator feg = new FinancialExportGenerator(super.context);
			feg.updateRecordsToExported(financialExportVo);
			
			//Build course of action
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_FINANCIAL_EXPORT");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setMessageVo(new MessageVo("text.financialExport", "info.0030" , null, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);

			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(financialExportVo);
				
		}catch(Exception e){
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("ServiceException");
			coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
			coaVo.setIsDialogueEnding(true);
			ErrorObject errorObject = new ErrorObject(ErrorEnum._90000_ERROR,new String[]{sdebug+" " + e.getMessage()});
			coaVo.getErrorObjectVos().add(errorObject);
			dialogueVo.setCourseOfActionVo(coaVo);
		}

		return dialogueVo;
	}
	
	public DialogueVo getIncidentGroup(DialogueVo dialogueVo, Long incidentGroupId) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IncidentGroupDao igDao = (IncidentGroupDao)super.context.getBean("incidentGroupDao");
			IncidentGroupVo igVo = IncidentGroupVo.getInstance(igDao.getById(incidentGroupId), true);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_INCIDENT_GROUP");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(igVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public static void main(String[] args) throws Exception {
		try {
			
			//System.out.println(new BigDecimal(1.0).multiply(new BigDecimal(.535)).setScale(2,2));

			ISWFileEncryption encFexp = new ISWFileEncryption();
			FinExpEncryption.decryptFinExp(encFexp, "c:\\dan\\UT-USO-28349292--20180812-1615.fex",
							"c:\\dan\\");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
