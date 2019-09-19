package gov.nwcg.isuite.core.datatransfer;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.framework.cryptfile.ISWFileEncryption;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;

public class BaseDataTransfer {
	protected ApplicationContext context;
	protected String runMode;
	protected Collection<String> tmpLogEntries = new ArrayList<String>();
	protected String dataTransFilesFldr="";
	
	protected String getUserSessionDbName(){
		return ((UserSessionVo)context.getBean("userSessionVo")).getSiteDatabaseName();
	}
	
	protected String getReportsFolder() throws Exception {
		String folder="";
		SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");

		try{
			SystemParameter spEntity = spDao.getByParameterName(SystemParameterTypeEnum.REPORT_OUTPUT_FOLDER.name());

			if( (null != spEntity) && ( null != spEntity.getParameterValue()) && (!spEntity.getParameterValue().isEmpty()) ){
				folder=spEntity.getParameterValue();
				
				if(!FileUtil.isDir(folder)){
					FileUtil.makeDir(folder);
				}
				
			}else{
				return "";
			}

		}catch(Exception e){
			throw e;
		}
		
		return folder;
	}
	
	protected Boolean getLogging() throws Exception {
		SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");

		try{
			SystemParameter spEntity = spDao.getByParameterName(SystemParameterTypeEnum.DATA_TRANSFER_LOGGING.name());

			if( (null != spEntity) && ( null != spEntity.getParameterValue()) && (!spEntity.getParameterValue().isEmpty()) ){
				String doLogging=spEntity.getParameterValue();

				if(doLogging.equals("1"))
					return true;
			}else{
			}

		}catch(Exception e){
			throw e;
		}
		
		return false;
	}
	
	protected void writeLog(String exportFileLog, Collection<String> logEntries) throws Exception {
		BufferedWriter appender = null;
		String timestamp=String.valueOf(Calendar.getInstance().getTimeInMillis());
		
		if(getLogging()==false)
			return;
		
		try{
			FileUtil.createFile(exportFileLog);
			appender = FileUtil.getFileAppender(exportFileLog);
			
			for(String s : logEntries){
				appender.write(s);
				appender.newLine();
			}
		}catch(Exception ignore){
			
		}finally{
			if(null != appender)
				appender.close();
		}
		
	}
	
	protected String packageFiles(String xmlFilename, String outputFolder,String timestamp, Collection<String> pdfFilenames) throws Exception {
		String tmpDir=outputFolder+"tmpexp"+timestamp;
		String dataTransferFile=outputFolder+xmlFilename+"_"+timestamp+".isw";

		try{
			char sepChar = java.io.File.separatorChar;
			String xmlFile=outputFolder+xmlFilename+"_"+timestamp+".xml";

			FileUtil.makeDir(tmpDir);
			
			ISWFileEncryption encFexp=new ISWFileEncryption();
			encFexp.tmpDir = new File(tmpDir);
			encFexp.aesKey = encFexp.genKey(null);
			encFexp.saveAESKey(encFexp.aesKey, encFexp.tmpDir,ISWFileEncryption.ISWPRIVKEY);
			FileUtil.makeDir(encFexp.tmpDir+File.separator+"work");
			
			String workXmlFile=tmpDir+File.separator+"work"+File.separator+xmlFilename+"_"+timestamp+".xml";
			FileUtil.copyFile(xmlFile, workXmlFile);
			encFexp.encrypt(encFexp.aesKey, encFexp.tmpDir, encFexp.tmpDir+File.separator+"work"+File.separator+xmlFilename+"_"+timestamp+".xml");

			String reportsFolder=this.getReportsFolder();
			
			for(String pdffile : pdfFilenames){
				if(BooleanUtility.isTrue(FileUtil.fileExists(reportsFolder+pdffile))){
					FileUtil.copyFile(reportsFolder+pdffile
									 , tmpDir+File.separator+"work"+File.separator+pdffile);
					encFexp.encrypt(encFexp.aesKey, encFexp.tmpDir, encFexp.tmpDir+File.separator+"work"+File.separator+pdffile);
				}
			}
			

			try{
				FileUtils.deleteQuietly(new File(tmpDir+File.separator+"work"));
				FileUtils.deleteQuietly(new File(xmlFile));
			}catch(Exception ignore){
				//System.out.println("");
			}
			
			encFexp.iswPackage(encFexp.tmpDir, dataTransferFile);
		}catch(Exception e){
			throw e;
		}finally{
			
			try{
				FileUtils.deleteQuietly(new File(tmpDir));
			}catch(Exception ignore){
				//System.out.println("");
			}
			
		}
		
		return dataTransferFile;
	}

	protected String packageFiles2(String tmpDir,String xmlFilename, String outputFolder,String timestamp, Collection<String> pdfFilenames) throws Exception {
		String dataTransferFile=dataTransFilesFldr+xmlFilename+"_"+timestamp+".isw";

		try{

			ISWFileEncryption encFexp=new ISWFileEncryption();
			encFexp.tmpDir = new File(tmpDir);
			encFexp.aesKey = encFexp.genKey(null);
			encFexp.saveAESKey(encFexp.aesKey, encFexp.tmpDir,ISWFileEncryption.ISWPRIVKEY);
			FileUtil.makeDir(encFexp.tmpDir+File.separator+"work");

			Collection<String> xmlFileList=FileUtil.getDirFilenames(tmpDir);
			if(CollectionUtility.hasValue(xmlFileList)){
				for(String s : xmlFileList){
					String workXmlFile=tmpDir+File.separator+"work"+File.separator+s;
					if(s.endsWith(".xml") || s.endsWith(".file")){
						FileUtil.copyFile(tmpDir+File.separator+s, workXmlFile);
						encFexp.encrypt(encFexp.aesKey, encFexp.tmpDir, encFexp.tmpDir+File.separator+"work"+File.separator+s);
					}
				}
			}

			// get and copy all report files (invoices)
			String reportsFolder=this.getReportsFolder();
			for(String pdffile : pdfFilenames){
				if(BooleanUtility.isTrue(FileUtil.fileExists(reportsFolder+pdffile))){
					FileUtil.copyFile(reportsFolder+pdffile
									 , tmpDir+File.separator+"work"+File.separator+pdffile);
					encFexp.encrypt(encFexp.aesKey, encFexp.tmpDir, encFexp.tmpDir+File.separator+"work"+File.separator+pdffile);
				}
			}
			

			try{
				FileUtils.deleteQuietly(new File(tmpDir+File.separator+"work"));
			}catch(Exception ignore){
				//System.out.println("");
			}
			
			encFexp.iswPackage(encFexp.tmpDir, dataTransferFile);
		}catch(Exception e){
			throw e;
		}finally{
			
			try{
				FileUtils.deleteQuietly(new File(tmpDir));
			}catch(Exception ignore){
				//System.out.println("");
			}
			
		}
		
		return dataTransferFile;
	}

	protected void unpackageFiles(String outputFolder,String iswFileName, String timestamp) throws Exception {
		ISWFileEncryption encFexp = new ISWFileEncryption();
		String tmpDir=outputFolder+"tmpexp"+timestamp;
		String iswFile=outputFolder+iswFileName+timestamp+".isw";
		
		tmpLogEntries.add("BaseDataTransfer.unpackageFiles()  tmpDir="+tmpDir);
		tmpLogEntries.add("BaseDataTransfer.unpackageFiles()  iswFile="+iswFile);
		
		try{
			tmpLogEntries.add("BaseDataTransfer.unpackageFiles()  Before call FileUtil.makeDir()");
			FileUtil.makeDir(tmpDir);
			tmpLogEntries.add("BaseDataTransfer.unpackageFiles()  After call FileUtil.makeDir()");
			
			Boolean dirExists=FileUtil.isDir(tmpDir);
			if(dirExists==true)
				tmpLogEntries.add("BaseDataTransfer.unpackageFiles() "+tmpDir+" exists");
			else
				tmpLogEntries.add("BaseDataTransfer.unpackageFiles() "+tmpDir+" does not exist");
				
			// unpack the backup file
			encFexp.tmpDir = new File(tmpDir);
			FileUtil.makeDir(encFexp.tmpDir+File.separator+"work");
			
			tmpLogEntries.add("BaseDataTransfer.unpackageFiles() Before call encFexp.unArchive()");
			encFexp.unArchive(encFexp.tmpDir, iswFile);
			tmpLogEntries.add("BaseDataTransfer.unpackageFiles() After call encFexp.unArchive()");
			encFexp.loadKey(encFexp.tmpDir, ISWFileEncryption.ISWPUBKEY);
			tmpLogEntries.add("BaseDataTransfer.unpackageFiles() After call encFexp.loadKey()");
			
			String reportsFolder=this.getReportsFolder();
			
			File fileTmpDir=new File(tmpDir);
			for(String filename : fileTmpDir.list()){
				//System.out.println(filename);
				if(!filename.equals("metadata.0") && !filename.equals("work") && !filename.equals("Version2.file")){
					tmpLogEntries.add("BaseDataTransfer.unpackageFiles() Before call encFexp.decrypt() " + filename);
					encFexp.decrypt(encFexp.tmpDir, filename, encFexp.tmpDir+File.separator+"work"+File.separator);
					tmpLogEntries.add("BaseDataTransfer.unpackageFiles() After call encFexp.decrypt() " + filename);
					
					if(filename.endsWith(".xml")){
						FileUtil.copyFile(encFexp.tmpDir+File.separator+"work"+File.separator+filename
								 , outputFolder+"dtimport_"+timestamp+".xml");
					}else{
						FileUtil.copyFile(encFexp.tmpDir+File.separator+"work"+File.separator+filename
								 , reportsFolder+filename);
					}
				}
			}
		}catch(GeneralSecurityException gse){
			tmpLogEntries.add("BaseDataTransfer.unpackageFiles() GeneralSecurityException: " + gse.getMessage());
			throw gse;
		}catch(IOException ioe){
			tmpLogEntries.add("BaseDataTransfer.unpackageFiles() IOException: " + ioe.getMessage());
			throw ioe;
		}catch(ClassNotFoundException cnfe){
			tmpLogEntries.add("BaseDataTransfer.unpackageFiles() ClassNotFoundException: " + cnfe.getMessage());
			throw cnfe;
		}catch(Exception e){
			tmpLogEntries.add("BaseDataTransfer.unpackageFiles() Exception: " + e.getMessage());
			throw e;
		}finally{
			try{
				FileUtils.deleteQuietly(new File(tmpDir));
			}catch(Exception ignore){
				//System.out.println("");
			}
		}
		
	}
	
}

