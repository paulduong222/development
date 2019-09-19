package gov.nwcg.isuite.core.datatransfer;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.domain.xml.DataTransferXml;
import gov.nwcg.isuite.core.domain.xml.IswIncident;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.rules.DataTransferImportDataRulesHandler;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.xmltransfer.importer.XmlTransferImporterContext;
import gov.nwcg.isuite.framework.logging.LoggingInterceptor;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.FileUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class DataImporter extends BaseDataTransfer {
	public StringBuffer xmlString;
	public byte[] xmlByteArray;
	public String filePassword;
	private LoggingInterceptor logger=null;
	public Collection<String> logEntries = new ArrayList<String>();
	public Boolean hasException=false;
	public Boolean fromWebServlet=false;
	public Long fromWebServletUserId=0L;
	public String importException="";
	public Boolean ruleHandlerFailure=false;
	public String incidentGroupTi="";
	public String incidentTi="";
	public Long dataStewardUserId=0L;
	
	public DataImporter(ApplicationContext ctx, String runmode,LoggingInterceptor loger){
		super.context=ctx;
		super.runMode=runmode;
		this.logger=loger;
	}

	public DialogueVo importData(DialogueVo dialogueVo) throws Exception {
		String timestamp=String.valueOf(Calendar.getInstance().getTimeInMillis());
		String logFile=this.getOutputFile("dtimport_"+timestamp+".log");

		try{
			String xmlFile=this.getOutputFile("dtimport_"+timestamp+".xml");
			String packagedFile=this.getOutputFile("dtimport_"+timestamp+".isw");
			
			//FileUtil.writeFile(xmlByteArray, encFile);
			logEntries.add("DataImporter.importData() Before FileUtil.writeFile2(xmlByteArray, packagedFile)");
			FileUtil.writeFile2(xmlByteArray, packagedFile);
			logEntries.add("DataImporter.importData() After FileUtil.writeFile2(xmlByteArray, packagedFile)");
			
			if(FileUtil.fileExists(packagedFile)){
				logEntries.add("DataImporter.importData() " + packagedFile + " exists.");
			}else{
				logEntries.add("DataImporter.importData() " + packagedFile + " does not exist.");
			}
			
			logEntries.add("DataImporter.importData() Before super.unpackageFiles()");
			super.unpackageFiles(this.getOutputFile(""), "dtimport_", timestamp);
			logEntries.addAll(super.tmpLogEntries);
			logEntries.add("DataImporter.importData() After super.unpackageFiles()");
			
			xmlString = FileUtil.getFileContents(xmlFile);
			
		}catch(Exception ee){
			logEntries.addAll(super.tmpLogEntries);
			logEntries.add("DataImporter.importData() Unable to unpackage file.");
			logEntries.add(ee.getMessage());
			super.writeLog(logFile,this.logEntries);
			this.importException=ee.getMessage();
			throw new Exception("The file is invalid.  The system was unable to unpack the data transfer file.");
		}
		
		XmlTransferImporterContext xtc = new XmlTransferImporterContext(context,xmlString);
		
		try{
			
			//System.out.println(xmlString);
			xtc.setLogger(this.logger);
			xtc.setRunMode(this.runMode);
			xtc.fromWebServlet=this.fromWebServlet;
			xtc.fromWebServletUserId=this.fromWebServletUserId;
			xtc.dataStewardUserId=this.dataStewardUserId;
			
			xtc.dialogueVo=dialogueVo;
			
			DataTransferXml dataTransferXml = xtc.getRootObject();
			
			if(null != dataTransferXml){
				
				DataTransferImportDataRulesHandler rulesHandler = new DataTransferImportDataRulesHandler(context);
				if(rulesHandler.execute(dataTransferXml, filePassword, this.fromWebServlet,dialogueVo)==AbstractRule._OK){

					if(null != dataTransferXml.getIncidentGroup()){
						xtc.hasIncidentGroup=true;
						this.incidentGroupTi=dataTransferXml.getIncidentGroup().getTransferableIdentity();
					}else{
						if(CollectionUtility.hasValue(dataTransferXml.getIncidents())){
							IswIncident inc = dataTransferXml.getIncidents().iterator().next();
							if(null != inc)
								this.incidentTi=inc.getTransferableIdentity();
						}
					}
					
					if(CollectionUtility.hasValue(dataTransferXml.getIncidents())){
						long milliStart=Calendar.getInstance().getTimeInMillis();
						for(IswIncident incident : dataTransferXml.getIncidents()){
							Collection<String> sqlStatements = xtc.generateSqlStatements(incident);
						}
						
						if(null != dataTransferXml.getIncidentGroup()){
							Collection<String> sqlStatements = xtc.generateSqlStatements(dataTransferXml.getIncidentGroup());
							
							Collection<String> sqlStatements2 = xtc.processIncidentResources();
							
							Collection<String> sqlStatements3 = xtc.processIncidentResourceOthers();
							
							Collection<String> sqlStatements4 = xtc.processOther();
						}
						
						this.logEntries=xtc.logEntries;
						long milliStop=Calendar.getInstance().getTimeInMillis();
						long milliDiff=milliStop-milliStart;
						String seconds = String.valueOf(milliDiff/1000);
						//System.out.println("DataImporter.importData() Seconds:"+seconds);
						//System.out.println("DataImporter.importData() QueryCount:"+xtc.queryCount);
						//System.out.println("DataImporter.importData() UpdateCount:"+xtc.updateCount);
						//System.out.println("DataImporter.importData() InsertCount:"+xtc.insertCount);
						
						this.logEntries.add("DataImporter.importData() Seconds:"+seconds);
						this.logEntries.add("DataImporter.importData() QueryCount:"+xtc.queryCount);
						this.logEntries.add("DataImporter.importData() UpdateCount:"+xtc.updateCount);
						this.logEntries.add("DataImporter.importData() InsertCount:"+xtc.insertCount);
					}
					
					super.writeLog(logFile,this.logEntries);
					
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName("IMPORT_DATA_FROM_FILE");
					coaVo.setIsDialogueEnding(Boolean.TRUE);
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(new MessageVo("text.dataTransfer", "info.generic" , new String[]{"The transition file was imported."}, MessageTypeEnum.INFO));
					coaVo.setIsDialogueEnding(true);
					dialogueVo.setCourseOfActionVo(coaVo);
	
				}else{
					this.ruleHandlerFailure=true;
				}
			}
		}catch(Exception ee){
			this.hasException=true;
			if(null != xtc){
				this.logEntries=xtc.logEntries;
				this.logEntries.add(ee.getMessage());
				super.writeLog(logFile,this.logEntries);
				this.importException=ee.getMessage();
				throw ee;
			}
		}finally{
			try{
				FileUtil.deleteFile(this.getOutputFile("dtimport_"+timestamp+".xml"));
			}catch(Exception eee){
			}
		}
		
		return dialogueVo;
	}

	protected String getOutputFile(String fileName) throws Exception {
		SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");

		try{
			SystemParameter spEntity = spDao.getByParameterName(SystemParameterTypeEnum.DATA_TRANSFER_FOLDER.name());

			if( (null != spEntity) && ( null != spEntity.getParameterValue()) && (!spEntity.getParameterValue().isEmpty()) ){
				String dirName=spEntity.getParameterValue();
				
				if(!FileUtil.isDir(dirName)){
					FileUtil.makeDir(dirName);
				}
				
				return spEntity.getParameterValue() + fileName;
			}else{
				return fileName;
			}

		}catch(Exception e){
			throw e;
		}
	}

}
