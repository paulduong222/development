package gov.nwcg.isuite.core.datatransfer;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.domain.xml.IswIncident;
import gov.nwcg.isuite.core.domain.xmlv2.DataTransferXml;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.persistence.TransferControlDao;
import gov.nwcg.isuite.core.rules.DataTransferImportDataRulesHandler;
import gov.nwcg.isuite.core.rules.DataTransferV2ImportDataRulesHandler;
import gov.nwcg.isuite.core.rules.DataTransferV3ImportDataRulesHandler;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.xmltransfer.importer.XmlTransferImporterContext;
import gov.nwcg.isuite.framework.core.xmltransferv2.importer.XmlTransferImporterContextV2;
import gov.nwcg.isuite.framework.cryptfile.ISWFileEncryption;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.logging.LoggingInterceptor;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Stack;

import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;

public class DataImporterV2 extends BaseDataTransfer {
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
	private Boolean doLogging=false;

	private String dtFilesFolder="";
	private String currentTimestamp="";
	private String logFile="";
	private String packagedFile="";
	private String xmlFile="";
	private String tmpImportDir="";

	private FileWriter fwriter = null;
	private BufferedWriter writer=null;
	private PrintWriter out=null;
	
	public DataImporterV2(ApplicationContext ctx, String runmode,LoggingInterceptor loger){
		super.context=ctx;
		super.runMode=runmode;
		this.logger=loger;
	}

	public void setDoLogging(String s){
		if(s.equals("1"))
			doLogging=true;
	}

	protected void unpackFile(String packagedFile) throws Exception {
		ISWFileEncryption decryptFexp = new ISWFileEncryption();
		logEntries.add("DataImporterV2.unpackFile() start");
		
		try{
			FileUtil.makeDir(tmpImportDir);
			logEntries.add("DataImporterV2.unpackFile() makeDir "+tmpImportDir+" completed.");
			Boolean dirExists=FileUtil.isDir(tmpImportDir);
			if(!dirExists==true){
				logEntries.add("DataImporterV2.unpackFile() dir "+tmpImportDir+" does not exists.");
				throw new Exception("Unable to create "+tmpImportDir);
			}
				
			logEntries.add("DataImporterV2.unpackFile() dir "+tmpImportDir+" exists.");
			
			// unpack the backup file
			decryptFexp.tmpDir = new File(tmpImportDir);
			FileUtil.makeDir(decryptFexp.tmpDir+File.separator+"work");
			logEntries.add("DataImporterV2.unpackFile() after create tmpDir work");
			
			decryptFexp.unArchive(decryptFexp.tmpDir, packagedFile);
			logEntries.add("DataImporterV2.unpackFile() after decryptFexp.unArchive");
			decryptFexp.loadKey(decryptFexp.tmpDir, ISWFileEncryption.ISWPUBKEY);
			logEntries.add("DataImporterV2.unpackFile() after loadKey");
			
			String reportsFolder=this.getReportsFolder();
			
			File fileTmpDir=new File(tmpImportDir);
			for(String filename : fileTmpDir.list()){
				//System.out.println(filename);
				if(!filename.equals("metadata.0") && !filename.equals("work")){
					decryptFexp.decrypt(decryptFexp.tmpDir, filename, decryptFexp.tmpDir+File.separator+"work"+File.separator);
					
					if(filename.endsWith(".xml") || filename.endsWith(".file")){
						FileUtil.copyFile(decryptFexp.tmpDir+File.separator+"work"+File.separator+filename
								 , decryptFexp.tmpDir+File.separator+filename);
					}else if(filename.endsWith(".pdf")){
						FileUtil.copyFile(decryptFexp.tmpDir+File.separator+"work"+File.separator+filename
								 , reportsFolder+filename);
					}
				}
			}
		}catch(GeneralSecurityException gse){
			throw gse;
		}catch(IOException ioe){
			throw ioe;
		}catch(ClassNotFoundException cnfe){
			throw cnfe;
		}catch(Exception e){
			throw e;
		}finally{
			try{
				//FileUtils.deleteQuietly(new File(tmpImportDir+File.separator+"work"));
			}catch(Exception ignore){
				//System.out.println("");
			}
		}
		
	}
	
	public DialogueVo importData(DialogueVo dialogueVo, Boolean dtVersion3) throws Exception {
		currentTimestamp=String.valueOf(Calendar.getInstance().getTimeInMillis());
		logFile=this.getOutputFile("dtimport_"+currentTimestamp+".log");

		logEntries.add("DataImporterV2.importData() start");
		
		// load datatransferfiles file
		dtFilesFolder=this.getDataTransferFilesFolder();		

		logEntries.add("DataImporterV2.importData() dtfolder is: " + dtFilesFolder);
		
		packagedFile=dtFilesFolder+"dtimport_"+currentTimestamp+".isw";

		tmpImportDir=dtFilesFolder+File.separator+"tmpimp"+currentTimestamp+File.separator;
		
		try{
			FileUtil.writeFile2(xmlByteArray, packagedFile);
			logEntries.add("DataImporterV2.importData() write dtFile.isw complete");

			if(!FileUtil.fileExists(packagedFile)){
				logEntries.add("DataImporterV2.importData() unable to write byte data to file");
				throw new Exception("DataImporterV2 unable to write byte data to file -" + packagedFile + ".");
			}
			
			logEntries.add("DataImporterV2.importData() before call to unpackFile()");
			this.unpackFile(packagedFile);
			logEntries.add("DataImporterV2.importData() after call to unpackFile()");
			
			if(FileUtil.fileExists(tmpImportDir+"Version2.file")){
				logEntries.add("DataImporterV2.importData() version file is 2");
				if(this.runMode.equalsIgnoreCase("ENTERPRISE") && BooleanUtility.isTrue(dtVersion3) ){
					// version 2 on enterprise is no longer supported
					String m="You are attempting to import a Site to Site transition file into Enterprise.  To use the Data Transfer process to import data from one Site Database to Enterprise, you must create a Site to Enterprise Transition File.";
					this.importException=m;//"Enterprise no longer supports importing a data transfer version 2 file.";
					this.hasException=true;
					return dialogueVo;
				}
				dialogueVo=this.importDataV2(dialogueVo);
			}else if(FileUtil.fileExists(tmpImportDir+"Version3.file")){
				if(this.runMode.equalsIgnoreCase("SITE") ){
					String m="You are attempting to import a Site to Enterprise transition file into a Site Database.  To use the Data Transfer process to import data from one Site Database to another Site Database, you must create a Site to Site Transition File.";
					this.importException=m;//"Enterprise no longer supports importing a data transfer version 2 file.";
					this.hasException=true;
					return dialogueVo;
				}
				logEntries.add("DataImporterV2.importData() version file is 3");
				dialogueVo=this.importDataV3(dialogueVo);
			}else{
				logEntries.add("DataImporterV2.importData() version file is 1");
				dialogueVo=this.importDataV1(dialogueVo);
			}
		}catch(Exception e){
			logEntries.add("DataImporterV2.importData() Exception:"+e.getMessage());			
			this.importException=e.getMessage();
		}finally{
			try{
				if(CollectionUtility.hasValue(this.logEntries)){
					// create log file
					super.writeLog(logFile,this.logEntries);
				}
				FileUtils.deleteQuietly(new File(tmpImportDir+File.separator+"work"));
				FileUtils.deleteQuietly(new File(tmpImportDir));
			}catch(Exception e){
			}
		}
		
		return dialogueVo;
	}

	public DialogueVo importDataV3(DialogueVo dialogueVo) throws Exception {
		logEntries.add("DataImporterV2.importDataV3() start");

		// make no records in isw_transfer_control record exists with null status
		TransferControlDao tcDao=(TransferControlDao)context.getBean("transferControlDao");
		int cnt=tcDao.getStatusEmptyCount();
		logEntries.add("DataImporterV2.importDataV3() getStatusEmptyCount:"+cnt);
		if(cnt>0){
			// only 1 can be in process at a time
			Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
			ErrorObject error2 = 
				new ErrorObject("error.800000"
									,"There is already an import in process.  A new import cannot be started until current one completes.");
			errorObjects.add(error2);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("ValidationError");
			coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
			coaVo.setIsDialogueEnding(true);
			coaVo.setErrorObjectVos(errorObjects);
			dialogueVo.setCourseOfActionVo(coaVo);

			this.ruleHandlerFailure=true;
			return dialogueVo;
		}
		
		if(FileUtil.fileExists(tmpImportDir+"dt.xml")){
			logEntries.add("DataImporterV2.importDataV3() file "+tmpImportDir+"dt.xml"+" exists.");
			xmlString=FileUtil.getFileContents(tmpImportDir+"dt.xml");
		}else{
			logEntries.add("DataImporterV2.importDataV3() file does not exist:"+tmpImportDir+"dt.xml");
		}
		
		XmlTransferImporterContextV2 xtc = new XmlTransferImporterContextV2(context);
		xtc.doLogging=true;
		 
		try{
			xtc.setLogger(this.logger);
			xtc.setRunMode(this.runMode);
			xtc.fromWebServlet=this.fromWebServlet;
			xtc.fromWebServletUserId=this.fromWebServletUserId;
			xtc.dataStewardUserId=this.dataStewardUserId;
			xtc.dialogueVo=dialogueVo;

			logEntries.add("XmlString: " +xmlString.toString());
			
			gov.nwcg.isuite.core.domain.xmlv3.DataTransferXml dataTransferXml = xtc.getRootObject2(xmlString);
			
			if(null != dataTransferXml){
				DataTransferV3ImportDataRulesHandler rulesHandler = new DataTransferV3ImportDataRulesHandler(context);
				if(rulesHandler.execute(dataTransferXml, filePassword, this.fromWebServlet,dialogueVo)==AbstractRule._OK){
					logEntries.add("DataImporterV2.importDataV3() after ruleHandler check");
					
					// create transfer control record with empty status 
					if(StringUtility.hasValue(dataTransferXml.getIncidentGroupTI())){
						this.incidentGroupTi=dataTransferXml.getIncidentGroupTI();
						logEntries.add("DataImporterV2.importDataV3() creating transfer control record for group");
						tcDao.createRecord(true, dataTransferXml.getIncidentGroupTI(),"",this.dataStewardUserId);
						
					}else{
						this.incidentTi=dataTransferXml.getIncidentTI();
						logEntries.add("DataImporterV2.importDataV3() creating transfer control record for incident");
						tcDao.createRecord(false, "",incidentTi,this.dataStewardUserId);
					}

					String currentTimestamp=String.valueOf(Calendar.getInstance().getTimeInMillis());
					
					// copy db.tar to datatransferfolder/tmp/currentTimestamp.dump
					String targetDumpFile="";
					if(FileUtil.fileExists(tmpImportDir+"work"+File.separator+"db.tar")){
						targetDumpFile=dtFilesFolder+"tmp"+File.separator+"import.dump";
						if(!FileUtil.isDir(dtFilesFolder+"tmp")){
							FileUtil.makeDir(dtFilesFolder+"tmp");
						}
						if(FileUtil.fileExists(targetDumpFile)){
							// rename it
							String renameFile=dtFilesFolder+"tmp"+File.separator+currentTimestamp+".dump";
							FileUtil.copyFile(targetDumpFile, renameFile);
							FileUtil.deleteFile(targetDumpFile);
						}
						
						FileUtil.copyFile(tmpImportDir+"work"+File.separator+"db.tar", targetDumpFile);
						if(!FileUtil.fileExists(targetDumpFile)){
							logEntries.add("DataImporterV2.importDataV3() file does not exist:"+targetDumpFile);
						}
					}else{
						logEntries.add("DataImporterV2.importDataV3() file does not exist:"+tmpImportDir+"work"+File.separator+"db.tar");
					}
					
					// call DT_ORA_IMPORT_SCRIPT_PATH -f currentTimestamp.dump
					String cmdImport=getSystemParamValue(SystemParameterTypeEnum.DT_ORA_IMPORT_SCRIPT_PATH);
					logEntries.add("DataImporterV2.importDataV3() dt_ora_import_script_path:"+cmdImport);
					logEntries.add("DataImporterV2.importDataV3() target dump file:"+targetDumpFile);
					///opt/NWCG/tomcat/webapps/eisuite/datatransferfiles/tmp/import.dump
					String[] command3 = { 
							"/bin/sh"
							,"-c"
							,cmdImport};
					
					Stack<String> errStack3 = new Stack<String>();

					// add /tmp to datatransferfiles folder
					logEntries.add("DataImporterV2.importDataV3() before import script call");
					if (runProc(command3, errStack3, "/opt/NWCG/datatransfer/bin") == 0) {
						logEntries.add("DataImporterV2.importDataV3() after import script call result is not 0");
						CourseOfActionVo coaVo = new CourseOfActionVo();
						coaVo.setCoaName("IMPORT_DATA_FROM_FILE");
						coaVo.setIsDialogueEnding(Boolean.TRUE);
						coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
						coaVo.setMessageVo(new MessageVo("text.dataTransfer", "info.generic" , new String[]{errStack3.toString()}, MessageTypeEnum.INFO));
						coaVo.setIsDialogueEnding(true);
						dialogueVo.setCourseOfActionVo(coaVo);
						return dialogueVo;
					} else {
						// success
						logEntries.add("DataImporterV2.importDataV3() after import script call result is 0");
					}
					
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName("IMPORT_DATA_FROM_FILE");
					coaVo.setIsDialogueEnding(Boolean.TRUE);
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(new MessageVo("text.dataTransfer", "info.generic" , new String[]{"The transition file was queued."}, MessageTypeEnum.INFO));
					coaVo.setIsDialogueEnding(true);
					dialogueVo.setCourseOfActionVo(coaVo);
	
					logEntries.add("DataImporterV2.importDataV3() end method");
				}else{
					this.ruleHandlerFailure=true;
				}
			}
		}catch(Exception ee){
			this.hasException=true;
		}finally{
			try{
				this.closeFileWriter();
			}catch(Exception eff){
			}
			
			try{
				FileUtil.deleteFile(xmlFile);
			}catch(Exception eee){
			}
		}
		
		return dialogueVo;
	}
	
	public DialogueVo importDataV2(DialogueVo dialogueVo) throws Exception {
		
		if(FileUtil.fileExists(tmpImportDir+"DataTransfer.xml")){
			xmlString=FileUtil.getFileContents(tmpImportDir+"DataTransfer.xml");
		}
		
		XmlTransferImporterContextV2 xtc = new XmlTransferImporterContextV2(context);
		xtc.doLogging=true;
		 
		try{
			xtc.setLogger(this.logger);
			xtc.setRunMode(this.runMode);
			xtc.fromWebServlet=this.fromWebServlet;
			xtc.fromWebServletUserId=this.fromWebServletUserId;
			xtc.dataStewardUserId=this.dataStewardUserId;
			xtc.dialogueVo=dialogueVo;
			
			DataTransferXml dataTransferXml = xtc.getRootObject(xmlString);
			
			if(null != dataTransferXml){
				DataTransferV2ImportDataRulesHandler rulesHandler = new DataTransferV2ImportDataRulesHandler(context);
				if(rulesHandler.execute(dataTransferXml, filePassword, this.fromWebServlet,dialogueVo)==AbstractRule._OK){

					this.initLogWriter();
					xtc.out=this.out;
					
					// import the data
					xtc.tmpImportPath=tmpImportDir;
					xtc.importXmlData(dataTransferXml);
					
					//super.writeLog(logFile,this.logEntries);
					
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
				//this.logEntries=xtc.logEntries;
				//this.logEntries.add(ee.getMessage());
				//super.writeLog(logFile,this.logEntries);
				this.importException=ee.getMessage();
				throw ee;
			}
		}finally{
			try{
				this.closeFileWriter();
			}catch(Exception eff){
			}
			
			try{
				FileUtil.deleteFile(xmlFile);
			}catch(Exception eee){
			}
		}
		
		return dialogueVo;
	}

	public DialogueVo importDataV1(DialogueVo dialogueVo) throws Exception {
		String timestamp=String.valueOf(Calendar.getInstance().getTimeInMillis());
		String logFile=this.getOutputFile("dtimport_"+timestamp+".log");

		try{
			String xmlFile="";
			Collection<String> filenames=FileUtil.getDirFilenames(tmpImportDir);
			for(String s: filenames){
				if(s.endsWith(".xml")){
					xmlFile=tmpImportDir+File.separator+s;
					break;
				}
			}
			xmlString = FileUtil.getFileContents(xmlFile);
		}catch(Exception ee){
			this.importException=ee.getMessage();
			throw new Exception("The file is invalid.  The system was unable to unpack the data transfer file.");
		}
		
		XmlTransferImporterContext xtc = new XmlTransferImporterContext(context,xmlString);
		
		try{
			xtc.setLogger(this.logger);
			xtc.setRunMode(this.runMode);
			xtc.fromWebServlet=this.fromWebServlet;
			xtc.fromWebServletUserId=this.fromWebServletUserId;
			xtc.dataStewardUserId=this.dataStewardUserId;
			xtc.dialogueVo=dialogueVo;
			
			gov.nwcg.isuite.core.domain.xml.DataTransferXml dataTransferXml 
				= xtc.getRootObject();
			
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

	protected String getDataTransferFilesFolder() throws Exception {
		SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");

		try{
			SystemParameter spEntity = spDao.getByParameterName(SystemParameterTypeEnum.DATA_TRANSFER_FOLDER.name());

			if( (null != spEntity) && ( null != spEntity.getParameterValue()) && (!spEntity.getParameterValue().isEmpty()) ){
				String dirName=spEntity.getParameterValue();
				
				if(!FileUtil.isDir(dirName)){
					FileUtil.makeDir(dirName);
				}

				// the spentity.parameter_value  should end with file.separator
				// ex:  c:\\temp\\datatransferfiles\\"
				//      /temp/datatransferfiles/"
				String path=spEntity.getParameterValue();
				
				spDao.flushAndEvict(spEntity);
				
				return path;
			}else{
				return "";
			}

		}catch(Exception e){
			throw e;
		}
	}

	private void initLogWriter() throws Exception{
		try{
			fwriter = new FileWriter(logFile,true);
			writer = new BufferedWriter(fwriter);
			out = new PrintWriter(writer);
		}catch(Exception e){
			this.closeFileWriter();
			throw e;
		}
	}

	public void closeFileWriter(){

		try{
			if(null != this.out)
				this.out.close();
		}catch(Exception e){
		}finally{
			this.out=null;
		}

		try{
			if(null != this.writer)
				this.writer.close();
		}catch(Exception e){
		}finally{
			this.writer=null;
		}

		try{
			if(null != this.fwriter)
				this.fwriter.close();
		}catch(Exception e){
		}finally{
			this.fwriter=null;
		}
	}

	protected String getSystemParamValue(SystemParameterTypeEnum paramName) throws Exception {
		
		SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");
		
		SystemParameter entity = spDao.getByParameterName(paramName.name());
		
		if(null == entity)
			throw new Exception("SystemParameter["+paramName.name()+"] not found");
		
		return entity.getParameterValue();
	}

	protected int runProc(final String[] command, Stack<String> errStack, String pwd) throws Exception, IOException {

		int ret = 1;

		try {
			ProcessBuilder procbuilder = new ProcessBuilder(command);

			procbuilder.directory(new File(pwd));

			Process process = procbuilder.start();
			InputStream is = process.getErrorStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String stdErr;
			while ((stdErr = br.readLine()) != null) {
				//System.out.println(stdErr);
				errStack.push(new String(stdErr));
			}
			ret = process.waitFor();
			process.destroy();

		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		return (ret);
	}
	
}
