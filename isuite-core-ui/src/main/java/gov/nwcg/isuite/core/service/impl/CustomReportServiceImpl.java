package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.CustomReport;
import gov.nwcg.isuite.core.domain.CustomReportFilter;
import gov.nwcg.isuite.core.domain.impl.CustomReportFilterImpl;
import gov.nwcg.isuite.core.domain.impl.CustomReportImpl;
import gov.nwcg.isuite.core.persistence.CustomReportDao;
import gov.nwcg.isuite.core.persistence.CustomReportFilterDao;
import gov.nwcg.isuite.core.reports.CustomReportsReport;
import gov.nwcg.isuite.core.reports.data.CustomReportData;
import gov.nwcg.isuite.core.rules.CustomReportDeleteRulesHandler;
import gov.nwcg.isuite.core.rules.CustomReportExportRulesHandler;
import gov.nwcg.isuite.core.rules.CustomReportGeneratorRulesHandler;
import gov.nwcg.isuite.core.rules.CustomReportImportRulesHandler;
import gov.nwcg.isuite.core.rules.CustomReportSQLBuilderRulesHandler;
import gov.nwcg.isuite.core.rules.CustomReportSaveRulesHandler;
import gov.nwcg.isuite.core.service.CustomReportService;
import gov.nwcg.isuite.core.vo.CustomReportColumnAggregateVo;
import gov.nwcg.isuite.core.vo.CustomReportColumnFormatVo;
import gov.nwcg.isuite.core.vo.CustomReportCriteriaEvaluatorVo;
import gov.nwcg.isuite.core.vo.CustomReportCriteriaOperatorVo;
import gov.nwcg.isuite.core.vo.CustomReportCriteriaVo;
import gov.nwcg.isuite.core.vo.CustomReportViewVo;
import gov.nwcg.isuite.core.vo.CustomReportVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseReportService;
import gov.nwcg.isuite.framework.customreports.designer.DesignModifierDelegate;
import gov.nwcg.isuite.framework.customreports.enumerators.ComparisonOperatorEnum;
import gov.nwcg.isuite.framework.customreports.enumerators.CriteriaEvaluatorEnum;
import gov.nwcg.isuite.framework.customreports.enumerators.FieldAggregatorTypeEnum;
import gov.nwcg.isuite.framework.customreports.enumerators.FieldFormatterTypeEnum;
import gov.nwcg.isuite.framework.customreports.exportimport.CustomReportExportGenerator;
import gov.nwcg.isuite.framework.customreports.exportimport.CustomReportImportGenerator;
import gov.nwcg.isuite.framework.customreports.sql.SQLBuilder;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.xml.XmlHandler;
import gov.nwcg.isuite.framework.xml.XmlSchemaTypeEnum;
import gov.nwcg.isuite.xml.customreport.CustomReportExport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

//import local.TestCustomReportExport;

//import local.TestCustomReportExport;
import net.sf.jasperreports.engine.design.JasperDesign;

public class CustomReportServiceImpl extends BaseReportService  implements CustomReportService {

	private static final String EXPORT_FILE_EXTENSION_XML = ".xml";
	private static final String EXPORT_FILE_PREFIX = "cr";
	private static final Charset EXPORT_FILE_CHARSET =  Charset.forName("UTF-8");
	
	private CustomReportDao dao = null;
	
	public CustomReportServiceImpl() {
		super();
	}
	
	public void initialization(){
		dao = (CustomReportDao)super.context.getBean("customReportDao");
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CustomReportService#getAvailableViews(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getAvailableViews(DialogueVo dialogueVo) throws ServiceException {

		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try {
			Collection<CustomReportViewVo> vos = dao.getCustomReportViewVos(super.getUserSessionVo().getUserRoleVos());
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_AVAILABLE_VIEWS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CustomReportService#buildSQL(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, gov.nwcg.isuite.core.vo.CustomReportVo, java.util.Collection)
	 */
	public DialogueVo buildSQL(DialogueVo dialogueVo, CustomReportVo vo, Collection<Long> incidentIds) throws ServiceException {
		
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try {
			CustomReportSQLBuilderRulesHandler rulesHandler = new CustomReportSQLBuilderRulesHandler(context);
//			if(rulesHandler.execute(vo, dialogueVo)==CustomReportSQLBuilderRulesHandler._OK) {
				String sql = SQLBuilder.buildSql(vo, getRunMode(), incidentIds);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("BUILD_SQL");
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(sql);
//			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CustomReportService#getOperators(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getOperators(DialogueVo dialogueVo) throws ServiceException {
		
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try {
			Collection<CustomReportCriteriaOperatorVo> vos = ComparisonOperatorEnum.getCriteriaOperatorVos();
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_OPERATORS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
		
	}
	
	public DialogueVo getEvaluators(DialogueVo dialogueVo) throws ServiceException {
		
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try {
			Collection<CustomReportCriteriaEvaluatorVo> vos = CriteriaEvaluatorEnum.getCriteriaEvaluatorVos();
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_EVALUATORS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo getAggregators(DialogueVo dialogueVo) throws ServiceException {
		
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try {
			Collection<CustomReportColumnAggregateVo> vos = FieldAggregatorTypeEnum.getCRColumnAggregateVos();
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_AGGREGATORS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo getFormatters(DialogueVo dialogueVo) throws ServiceException {
		
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try {
			Collection<CustomReportColumnFormatVo> vos = FieldFormatterTypeEnum.getCRColumnFormatVos();
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_FORMATTERS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CustomReportService#generateCustomReport(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, gov.nwcg.isuite.core.vo.CustomReportVo, java.util.Collection, java.lang.String)
	 */
	public DialogueVo generateCustomReport(DialogueVo dialogueVo, CustomReportVo customReportVo, Collection<Long> incidentIds, String incidents) throws ServiceException {
		if (dialogueVo == null) { dialogueVo = new DialogueVo(); }
		
		try {
			CustomReportGeneratorRulesHandler ruleHandler = new CustomReportGeneratorRulesHandler(context);
			if(ruleHandler.execute(customReportVo,dialogueVo)==AbstractRule._OK){
				
				Collection<CustomReportData> customReportDataList = new ArrayList<CustomReportData>();
			
				String sql = SQLBuilder.buildSql(customReportVo, getRunMode(), incidentIds);
				sql = sql.replaceAll("'A/R'", "'AR'");
				
				if(sql.toUpperCase().indexOf("MOB_TRAVEL_METHOD")>0){
					sql = sql.replaceAll("MOB_TRAVEL_METHOD AS column", 
					"CASE WHEN MOB_TRAVEL_METHOD = 'AR' THEN 'A/R' ELSE MOB_TRAVEL_METHOD END AS column");
				}else{
					//sql = sql.replaceAll("TRAVEL_METHOD AS column", 
					//		"CASE WHEN TRAVEL_METHOD = 'AR' THEN 'A/R' ELSE TRAVEL_METHOD END AS column");
					sql = sql.replaceAll("RETURN_METHOD_OF_TRAVEL AS column", 
					"CASE WHEN RETURN_METHOD_OF_TRAVEL = 'AR' THEN 'A/R' ELSE RETURN_METHOD_OF_TRAVEL END AS column");
				}
				if(dao.isOracleDialect()){
					if(sql.toUpperCase().indexOf("SUBSTRING")>0){
						sql = sql.toUpperCase().replaceAll("SUBSTRING","SUBSTR"); 
					}
				}
				
				customReportDataList = dao.getReportResults(sql, customReportVo.getCustomReportColumnVos());

				if(customReportDataList.size() < 1){
					dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Custom"));
					return dialogueVo;
				}
				
				IReport customReport = new CustomReportsReport(customReportVo.getReportTitle(), customReportVo.getSubTitle(), customReportDataList, incidents);
				
				if(customReportVo.getExportToExcel()){
					customReport.enableExportToExcel();
				}
				
				// Create and modify a JasperDesign object based on user choices
				JasperDesign jasperDesign = this.modifyJasperDesign(customReport, customReportVo);
				
				// Send the IReport and JasperDesign object to create a report
				String reportFileURL = generateReportFromJasperDesign(customReport, jasperDesign);
				
				List<String> reportsList = new ArrayList<String>();
				reportsList.add(reportFileURL);
				
				CourseOfActionVo coa = new CourseOfActionVo();
				coa.setCoaName("GENERATE_CUSTOM_REPORT"); 
				coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
				coa.setIsDialogueEnding(true);
				
				dialogueVo.setCourseOfActionVo(coa);
				dialogueVo.setResultObject(reportsList);
			}
		
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	private JasperDesign modifyJasperDesign(IReport customReport, CustomReportVo customReportVo) throws Exception {
		String templateName = customReport.getReportName();
		JasperDesign jasperDesign = getJasperDesignFromTemplate(templateName);
		
		// Delegate to custom delegate class 
		return DesignModifierDelegate.modifyCustomReportDesign(customReport, customReportVo, jasperDesign);
	}
	
	/**
	 * 
	 */
	public DialogueVo exportCustomReport(DialogueVo dialogueVo, CustomReportVo customReportVo) throws ServiceException {
		if(null==dialogueVo) { dialogueVo = new DialogueVo(); }
		
		try{
			CustomReportExportRulesHandler exportRulesHandler = new CustomReportExportRulesHandler(context);
			if(exportRulesHandler.execute(customReportVo, dialogueVo, getUserVo())==CustomReportExportRulesHandler._OK){
				//Generate xml & encrypt
				XmlHandler xmlHandler = new XmlHandler();
				xmlHandler.setFormatXml(true);
				xmlHandler.setXmlSchemaType(XmlSchemaTypeEnum.CUSTOM_REPORT);
				
				CustomReportExport crExport = new CustomReportExport();
				CustomReportExportGenerator crExportGenerator = new CustomReportExportGenerator(super.context);
				crExport = crExportGenerator.generateCustomReportExport(customReportVo);
				StringBuffer sb = xmlHandler.marshall(crExport);
//				String destinationFileURL = writeExportFile(sb.toString()); 
				
				//Build course of action
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("CUSTOM_REPORT_EXPORT");
				coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setCourseOfActionVo(coaVo);
//				dialogueVo.setResultObject(destinationFileURL);
				dialogueVo.setResultObject(sb.toString());
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		return dialogueVo;
	}
	
	/**
	 * Writes the contents of a previously created Custom Report Export to disk.
	 * @param fileContents String containing the contents of the export file. 
	 * @return URL by which the file written to the disk can be accessed. 
	 * @throws Exception
	 */
	private String writeExportFile(String fileContents) throws Exception{
		try{
			String exportFileName = this.generateExportFileName();
			String destinationFilePath = super.getOutputFile(exportFileName);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			os.write(fileContents.getBytes(EXPORT_FILE_CHARSET));
			FileUtil.writeFile(os,destinationFilePath);
			
			String destinationFileURL = super.getOutputUrl(exportFileName);
			return destinationFileURL;
		} catch(IOException e){
			throw new Exception("Could not write the export file to disk.");
		} 
	}
	
	private synchronized String generateExportFileName() {
		String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
		String fileName = EXPORT_FILE_PREFIX + timestamp + EXPORT_FILE_EXTENSION_XML;
		return fileName;
	}
	
	public DialogueVo importCustomReport(DialogueVo dialogueVo, byte[] xmlByteArray) throws ServiceException {
		if(null==dialogueVo) { dialogueVo = new DialogueVo(); }
		try {
			// Import XML File and generate XMLExport object from the imported file
			//StringBuffer crImportBuffer = new StringBuffer((new String(xmlByteArray)).replaceAll("&quot;", "\""));
			StringBuffer crImportBuffer = new StringBuffer((new String(xmlByteArray))); //).replaceAll("&", "&amp;"));
			String xsdBasePath = super.getSystemParamValue(SystemParameterTypeEnum.XSD_BASE_PATH);
			XmlHandler xmlHandler = new XmlHandler(XmlSchemaTypeEnum.CUSTOM_REPORT, xsdBasePath);
			xmlHandler.setXmlSchema(XmlSchemaTypeEnum.CUSTOM_REPORT.getFileName());
			
			CustomReportExport crExport = (CustomReportExport)xmlHandler.unmarshall(crImportBuffer);
			
			CustomReportVo customReportVo = null;
			
			Collection<CustomReportViewVo> customReportViewVos = dao.getCustomReportViewVos(null);
			
			// Pass the generated XML to converter, which will return CustomReportVo object
			CustomReportImportGenerator crImportGenerator = new CustomReportImportGenerator(super.context);
			
			customReportVo = crImportGenerator.generateCustomReportImport(crExport, customReportViewVos);
			
			CustomReportImportRulesHandler importRulesHandler = new CustomReportImportRulesHandler(context);
			
			if(importRulesHandler.execute(customReportVo, dialogueVo, getUserVo())==CustomReportImportRulesHandler._OK){
				// Add userVo object to returned crVo
				customReportVo.setUserVo(getUserVo());
				customReportVo.setUserLoginName(getUserVo().getLoginName());
				
				customReportVo.setCreatedBy(getUserVo().getLoginName());
				
				//Other information that needs to be added to the newly imported
				// customReportVo comes here.... 
				
				// If all good, return the dialogue VO 
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("CUSTOM_REPORT_IMPORT");
				coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
				coaVo.setIsDialogueEnding(Boolean.TRUE);

				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(customReportVo);
			} 
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CustomReportService#getGrid(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, java.lang.Long)
	 */
	public DialogueVo getGrid(DialogueVo dialogueVo, Long userId) throws ServiceException {

		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try {
			Collection<CustomReportVo> vos = dao.getGrid(userId, super.getUserSessionVo().getUserRoleVos());
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_CUSTOM_REPORT_GRID");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);

		} catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CustomReportService#deleteCustomReport(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, gov.nwcg.isuite.core.vo.CustomReportVo)
	 */
	public DialogueVo deleteCustomReport(DialogueVo dialogueVo, CustomReportVo customReportVo) throws ServiceException {
		if(null==dialogueVo) { dialogueVo = new DialogueVo(); }
		
		try{
			if((null == customReportVo) || !LongUtility.hasValue(customReportVo.getId()))
				throw new ServiceException("Unable to delete unknown custom report.");
			
			CustomReport entity = null;
			if(LongUtility.hasValue(customReportVo.getId())) {
				entity = dao.getById(customReportVo.getId(), CustomReportImpl.class);
			}
			
			CustomReportDeleteRulesHandler deleteRulesHandler = new CustomReportDeleteRulesHandler(context);
			if(deleteRulesHandler.execute(customReportVo, dialogueVo, getUserVo())==CustomReportDeleteRulesHandler._OK){

				dao.delete(entity);
				
				//Build course of action
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("CUSTOM_REPORT_DELETE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.customReports", "info.0028" , new String[]{"Custom Reports"}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(customReportVo);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CustomReportService#saveCustomReport(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, gov.nwcg.isuite.core.vo.CustomReportVo)
	 */
	public DialogueVo saveCustomReport(DialogueVo dialogueVo, CustomReportVo crVo) throws ServiceException {
		if(null==dialogueVo) { dialogueVo = new DialogueVo(); }
		
		try{
			CustomReport entity = null;
			if(LongUtility.hasValue(crVo.getId())){
				entity = dao.getById(crVo.getId(), CustomReportImpl.class);
			}
			
			CustomReportSaveRulesHandler saveRulesHandler = new CustomReportSaveRulesHandler(context);
			if(saveRulesHandler.execute(crVo, dialogueVo, getUserVo())==CustomReportSaveRulesHandler._OK){
				
				entity = CustomReportVo.toEntity(entity, crVo, true);
				dao.save(entity);
				dao.flushAndEvict(entity);
				
				crVo = CustomReportVo.getInstance(dao.getById(entity.getId(), CustomReportImpl.class), true);
				
				//Build course of action
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("CUSTOM_REPORT_SAVE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.customReports", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(crVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CustomReportService#deleteCustomReport(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, gov.nwcg.isuite.core.vo.CustomReportVo)
	 */
	public DialogueVo deleteCriteria(DialogueVo dialogueVo, CustomReportCriteriaVo criteriaVo) throws ServiceException {
		if(null==dialogueVo) { dialogueVo = new DialogueVo(); }
		
		try{
			if((null == criteriaVo) || !LongUtility.hasValue(criteriaVo.getId()))
				throw new ServiceException("Unable to delete unknown custom report criteria.");
			
			CustomReportFilter entity = null;
			
			CustomReportFilterDao filterDao = (CustomReportFilterDao)context.getBean("customReportFilterDao");
			
			if(LongUtility.hasValue(criteriaVo.getId())) {
				entity = filterDao.getById(criteriaVo.getId(), CustomReportFilterImpl.class);
			}

			filterDao.delete(entity);
			
			//Build course of action
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("CRITERIA_DELETE");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.customReports", "info.0028" , new String[]{"The " + criteriaVo.getType().toLowerCase()}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setResultObject(criteriaVo);
			dialogueVo.setCourseOfActionVo(coaVo);
		
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		return dialogueVo;
	}
}
