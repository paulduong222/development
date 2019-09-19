package gov.nwcg.isuite.core.datatransfer;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.domain.xml.DataTransferXml;
import gov.nwcg.isuite.core.domain.xml.IswIncident;
import gov.nwcg.isuite.core.domain.xml.IswIncidentGroup;
import gov.nwcg.isuite.core.persistence.DataTransferDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.vo.DataTransferVo;
import gov.nwcg.isuite.framework.core.xmltransfer.data.XmlTable;
import gov.nwcg.isuite.framework.core.xmltransfer.exporter.XmlTransferExporterContext;
import gov.nwcg.isuite.framework.core.xmltransfer.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.logging.LoggingInterceptor;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.EISuiteCalendar;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.apache.log4j.Level;
import org.springframework.context.ApplicationContext;

public class DataExporter extends BaseDataTransfer {
	public StringBuffer xmlString;
	public byte[] xmlStringEncrypted;
	public String incidentName;
	private LoggingInterceptor logger=null;
	public Collection<String> logEntries = new ArrayList<String>();
	public String incidentGroupTi="";
	
	public DataExporter(ApplicationContext ctx, String runmode,LoggingInterceptor loger){
		super.context=ctx;
		super.runMode=runmode;
		this.logger=loger;
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
	
	public String exportData(DataTransferVo vo) throws Exception {
		String dataTransferFile="";
		String xmlFile="";
		
		XmlTransferExporterContext xtc = new XmlTransferExporterContext(context);
		xtc.setLogger(this.logger);
		xtc.setRunMode(this.runMode);
		
		try{
			
			Collection<XmlTable> xmlTableData = new ArrayList<XmlTable>();
			XmlTable xtXmlTable = XmlTransferUtil.getXmlTableDefinition(DataTransferXml.class);
			xtXmlTable.xmlFields=XmlTransferUtil.getXmlFields(DataTransferXml.class);
			xtXmlTable.setFieldStringValue("Password", vo.getFilePassword());
			xtXmlTable.setFieldStringValue("Source", this.runMode);
			xtXmlTable.setFieldStringValue("ExportDate", EISuiteCalendar.getCalendarDateAsFullString(getUserSessionDbName()));
			//xtXmlTable.setFieldStringValue("SourceRevisionLevel", "221");
			xtXmlTable.setFieldStringValue("SourceRevisionLevel", getRevisionLevel());
			
			// export incident data
			if(LongUtility.hasValue(vo.getIncidentId())){
				
				// load xml data for incident
				long milliStart=Calendar.getInstance().getTimeInMillis();
				
				Collection<XmlTable> xmlTableDataIncident = xtc.getXmlTableData(IswIncident.class, "Id", vo.getIncidentId());
				if(CollectionUtility.hasValue(xmlTableDataIncident)){
					XmlTable incTable=xmlTableDataIncident.iterator().next();
					xtXmlTable.nestedTables.add(incTable);
				}
				
				long milliStop=Calendar.getInstance().getTimeInMillis();
				
				long milliDiff=milliStop-milliStart;

				this.logEntries = xtc.logEntries;
				
				//System.out.println(milliDiff/1000);
				//System.out.println("Query Count:"+xtc.queryCount);
				//System.out.println("UPdate Count:"+xtc.updateCount);
				this.logEntries.add("DataExporter.exportData() xtc.getXmlTableData() queryCount: " + xtc.queryCount+"\n");
				this.logEntries.add("DataExporter.exportData() xtc.getXmlTableData() updateCount: " + xtc.updateCount+"\n");
				this.logEntries.add("DataExporter.exportData() xtc.getXmlTableData() seconds: " + (milliDiff/1000)+"\n");
				if(null != logger){
					this.logger.addLog("DataExporter.exportData() xtc.getXmlTableData() queryCount: " + xtc.queryCount+"", Level.DEBUG);
					this.logger.addLog("DataExporter.exportData() xtc.getXmlTableData() updateCount: " + xtc.updateCount+"", Level.DEBUG);
					this.logger.addLog("DataExporter.exportData() xtc.getXmlTableData() time: " + (milliDiff/1000/60)+"", Level.DEBUG);
				}
				
				xmlTableData.add(xtXmlTable);
			}

			// export incident group data
			if(LongUtility.hasValue(vo.getIncidentGroupId())){
				// load xml data for incident
				long milliStart=Calendar.getInstance().getTimeInMillis();

				IncidentGroupDao igDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				Collection<Long> incidentIds = igDao.getIncidentIdsInGroup(vo.getIncidentGroupId());
				
				for(Long iid : incidentIds){
					Collection<XmlTable> xmlTableDataIncident = xtc.getXmlTableData(IswIncident.class, "Id", iid);
					if(CollectionUtility.hasValue(xmlTableDataIncident)){
						XmlTable incTable=xmlTableDataIncident.iterator().next();
						xtXmlTable.nestedTables.add(incTable);
					}
				}
					
				Collection<XmlTable> xmlTableDataIncidentGroup = xtc.getXmlTableData(IswIncidentGroup.class, "Id", vo.getIncidentGroupId());
				if(CollectionUtility.hasValue(xmlTableDataIncidentGroup)){
					XmlTable incGroupTable=xmlTableDataIncidentGroup.iterator().next();
					this.incidentGroupTi=incGroupTable.getTransferableIdentity();
					xtXmlTable.nestedTables.add(incGroupTable);
				}
				
				long milliStop=Calendar.getInstance().getTimeInMillis();
				
				long milliDiff=milliStop-milliStart;

				this.logEntries = xtc.logEntries;
				
				//System.out.println(milliDiff/1000);
				//System.out.println("Query Count:"+xtc.queryCount);
				//System.out.println("UPdate Count:"+xtc.updateCount);
				this.logEntries.add("DataExporter.exportData() xtc.getXmlTableData() queryCount: " + xtc.queryCount+"\n");
				this.logEntries.add("DataExporter.exportData() xtc.getXmlTableData() updateCount: " + xtc.updateCount+"\n");
				this.logEntries.add("DataExporter.exportData() xtc.getXmlTableData() seconds: " + (milliDiff/1000)+"\n");
				if(null != logger){
					this.logger.addLog("DataExporter.exportData() xtc.getXmlTableData() queryCount: " + xtc.queryCount+"", Level.DEBUG);
					this.logger.addLog("DataExporter.exportData() xtc.getXmlTableData() updateCount: " + xtc.updateCount+"", Level.DEBUG);
					this.logger.addLog("DataExporter.exportData() xtc.getXmlTableData() time: " + (milliDiff/1000/60)+"", Level.DEBUG);
				}
				
				xmlTableData.add(xtXmlTable);
			}
			
			String xmlString="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
								xtc.toXml(xmlTableData, 0);
			
			// write xml string to file
			String timestamp=String.valueOf(Calendar.getInstance().getTimeInMillis());
			xmlFile=this.getOutputFile(vo.getFilename()+"_"+timestamp+".xml");
			
			FileUtil.writeFile(xmlString.toString().getBytes(), xmlFile);

			try{
				// pacakge the files
				dataTransferFile=super.packageFiles(vo.getFilename()
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
			}
			
			throw e;
		}

		return dataTransferFile;
	}

}
