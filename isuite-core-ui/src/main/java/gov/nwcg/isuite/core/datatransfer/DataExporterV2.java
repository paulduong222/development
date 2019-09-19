package gov.nwcg.isuite.core.datatransfer;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.persistence.DataTransferDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.vo.DataTransferVo;
import gov.nwcg.isuite.framework.core.xmltransferv2.exporter.XmlTransferExporterContext2;
import gov.nwcg.isuite.framework.logging.LoggingInterceptor;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class DataExporterV2 extends BaseDataTransfer {
	public StringBuffer xmlString;
	public byte[] xmlStringEncrypted;
	public String incidentName;
	private LoggingInterceptor logger=null;
	public Collection<String> logEntries = new ArrayList<String>();
	public String incidentGroupTi="";
	private Boolean doLogging=false;
	
	public DataExporterV2(ApplicationContext ctx, String runmode,LoggingInterceptor loger){
		super.context=ctx;
		super.runMode=runmode;
		this.logger=loger;
	}

	public String exportData(DataTransferVo vo) throws Exception {
		String dataTransferFile="";
		String xmlFile="";
		String ts=String.valueOf(Calendar.getInstance().getTimeInMillis());
		super.dataTransFilesFldr=this.getDataTransferFilesFolder();
		String tmpDir=super.dataTransFilesFldr+"tmpexp"+ts;
		
		XmlTransferExporterContext2 xtc = new XmlTransferExporterContext2(context);
		xtc.setLogger(this.logger);
		xtc.setRunMode(this.runMode);
		xtc.doLogging=this.doLogging;
		xtc.tempFolder=tmpDir;
		
		try{
			Collection<Long> incidentIds = new ArrayList<Long>();
			Long incidentGroupId=null;
			
			// export incident data
			if(LongUtility.hasValue(vo.getIncidentId())){
				incidentIds.add(vo.getIncidentId());
			}

			// export incident group data
			if(LongUtility.hasValue(vo.getIncidentGroupId())){
				IncidentGroupDao igDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				incidentIds = igDao.getIncidentIdsInGroup(vo.getIncidentGroupId());
				incidentGroupId=vo.getIncidentGroupId();
			}

			xtc.incidentGroupId=incidentGroupId;
			xtc.incidentIds=incidentIds;
			
			xtc.exportData();
	
			String dtXml="<DataTransfer>"+
				"<Password>"+vo.getFilePassword()+"</Password>"+
				"<Source>"+this.runMode+"</Source>"+
				"<ExportDate>"+""+"</ExportDate>"+
				"<SourceRevisionLevel>"+getRevisionLevel()+"</SourceRevisionLevel>"+
				xtc.getBufferIncData()+
				xtc.getBufferIncGroupData()+
				"</DataTransfer>";
			
			// write xml string to file
			String timestamp=String.valueOf(Calendar.getInstance().getTimeInMillis());
			xmlFile=tmpDir+File.separator+"DataTransfer.xml";
			
			FileUtil.writeFile(dtXml.getBytes(), xmlFile);

			String versionFile=tmpDir+File.separator+"Version2.file";
			FileUtil.createFile(versionFile);
			
			try{
				// pacakge the files
				dataTransferFile=super.packageFiles2(tmpDir
													,vo.getFilename()
													, this.getOutputFile("")
													, timestamp
													, xtc.pdfFilenames);
				
			}catch(Exception ee){
				logEntries.add("Unable to encrypt and compress file.");
				logEntries.add(ee.getMessage());
			}
			
			String outputFileLog=xmlFile+"exp.log";
			super.writeLog(outputFileLog,logEntries);
			
			
		}catch(Exception e){
			if(null != xtc){
				this.logEntries=xtc.logEntries;
				this.logEntries.add(e.getMessage());
				String timestamp=String.valueOf(Calendar.getInstance().getTimeInMillis());
				xmlFile=this.getOutputFile(vo.getFilename()+"_"+timestamp);
				String outputFileLog=xmlFile+"exp.log";
				super.writeLog(outputFileLog,logEntries);
				xtc=null;
			}
			
			throw e;
		}finally{
			if(null != xtc)
				xtc=null;
		}

		return dataTransferFile;
	}

	public void addLog(String s) {
		
	}

	public void setDoLogging(String s){
		if(s.equals("1"))
			doLogging=true;
	}
	
	protected String getRevisionLevel() throws Exception {
		String level="";
		DataTransferDao dao = (DataTransferDao)context.getBean("dataTransferDao");
		
		String sql="SELECT revisionlevel from revision";
		ArrayList rslt=(ArrayList)dao.executeQuery(sql);
		if(null != rslt){
			level=TypeConverter.convertToString(rslt.get(0));
		}
		
		return level;
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

	protected String getOutputFolder() throws Exception {
		SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");

		try{
			SystemParameter spEntity = spDao.getByParameterName(SystemParameterTypeEnum.DATA_TRANSFER_FOLDER.name());
			
			if( (null != spEntity) && ( null != spEntity.getParameterValue()) && (!spEntity.getParameterValue().isEmpty()) ){
				String dirName=spEntity.getParameterValue();
				
				if(!FileUtil.isDir(dirName)){
					FileUtil.makeDir(dirName);
				}
				
				return dirName;
			}else{
				return "";
			}

		}catch(Exception e){
			throw e;
		}
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
	
	
	public static void main(String[] args){
		String s="<IswIncident><Id>10000</Id><ti>bc88e455-ef72-33fe-90b9-16796492d44c</ti><A>LAKE</A><B></B><C>007894</C><D></D><E></E><F></F><G></G><H>06/17/2015 00:00:00</H><I>null</I><J>2015</J><K>true</K><L>200815</L><M>1</M><N>Y</N><O>0</O><P>0</P><Q>null</Q><R>0</R><S>14</S><T>N</T><U>8</U><D1>81</D1></IswIncident>";
		System.out.println(s.length());
		
	}
}
