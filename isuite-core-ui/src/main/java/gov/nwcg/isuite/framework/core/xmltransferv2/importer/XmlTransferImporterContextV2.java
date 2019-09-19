package gov.nwcg.isuite.framework.core.xmltransferv2.importer;

import gov.nwcg.isuite.core.domain.xmlv2.DataTransferInc;
import gov.nwcg.isuite.core.domain.xmlv2.DataTransferIncGroup;
import gov.nwcg.isuite.core.domain.xmlv2.DataTransferXml;
import gov.nwcg.isuite.core.persistence.DataTransferDao;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlTable;
import gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers.TableHandler;
import gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers.TableHandlerRuleFactory;
import gov.nwcg.isuite.framework.core.xmltransferv2.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Level;
import org.springframework.context.ApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlTransferImporterContextV2 extends AbstractXmlTransferImporterContext {
	private ApplicationContext context;
	public String tmpImportPath="";
	private TableHandlerRuleFactory tableHandlerRuleFactory = new TableHandlerRuleFactory();
	
	//private StringBuffer xml;
	public Boolean hasIncidentGroup=false;
	public Boolean fromWebServlet=false;
	public Long fromWebServletUserId=0L;
	public Long dataStewardUserId=0L;
	public int incidentCount=0;
	public int incidentGroupCount=0;
	public DialogueVo dialogueVo=null;
	private HashMap<String,String> resourceMap=new HashMap<String,String>();
	
	public XmlTransferImporterContextV2(ApplicationContext ctx){
		super();
		this.context=ctx;
		dao = (DataTransferDao)context.getBean("dataTransferDao");
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public DataTransferXml getRootObject(StringBuffer xml) throws Exception {
		DataTransferXml dataTransferXml= null;
		InputStream in=null;
		DocumentBuilderFactory dbf = null;
		DocumentBuilder parser=null;
		Document doc = null;
		
		try{
			in = IOUtils.toInputStream(xml.toString(), "UTF-8");
			dbf = DocumentBuilderFactory.newInstance();
			parser = dbf.newDocumentBuilder();
			doc = parser.parse(in);
			doc.getDocumentElement().normalize();
			
			String rootNodeName=doc.getDocumentElement().getNodeName();
			
			if(rootNodeName.equals("DataTransfer")){
				dataTransferXml = new DataTransferXml();
				
				NodeList childNodes = doc.getDocumentElement().getChildNodes();
				for(int i=0;i<childNodes.getLength();i++){
					Node cn = childNodes.item(i);
					
					//System.out.println(cn.getNodeName());
					
					if(cn.getNodeType()==1){
						if(cn.getNodeName().equals("DataTransferInc")){
							DataTransferInc incObject=(DataTransferInc)super.populateDataObjects("DataTransferInc",(Element)cn,"DataTransferInc");
							dataTransferXml.getDataTransferIncs().add(incObject);	
							this.incidentCount++;
						}else if(cn.getNodeName().equals("DataTransferIncGroup")){
							DataTransferIncGroup incGroupObject=(DataTransferIncGroup)super.populateDataObjects("DataTransferIncGroup",(Element)cn, "DataTransferIncGroup");
							dataTransferXml.getDataTransferIncGroups().add(incGroupObject);	
							this.incidentGroupCount++;
						}else{
							String nn=cn.getNodeName();
							String val = cn.getTextContent();
							if(cn.getNodeName().equals("Password")){
								dataTransferXml.setPassword(val);
							}else if(cn.getNodeName().equals("ExportDate")){
								dataTransferXml.setExportDate(val);
							}else if(cn.getNodeName().equals("Source")){
								dataTransferXml.setSource(val);
							}else if(cn.getNodeName().equals("SourceRevisionLevel")){
								dataTransferXml.setSourceRevisionLevel(val);
							}
						}
					}
				}
			}
			
		}catch(Exception e){
			throw e;
		}finally{
			try{
				dbf=null;
				parser=null;
				doc=null;
				if(null != in)
					in.close();
			}catch(Exception ee){}
		}

		return dataTransferXml;
	}

	public gov.nwcg.isuite.core.domain.xmlv3.DataTransferXml getRootObject2(StringBuffer xml) throws Exception {
		gov.nwcg.isuite.core.domain.xmlv3.DataTransferXml dataTransferXml= null;
		InputStream in=null;
		DocumentBuilderFactory dbf = null;
		DocumentBuilder parser=null;
		Document doc = null;
		
		try{
			in = IOUtils.toInputStream(xml.toString(), "UTF-8");
			dbf = DocumentBuilderFactory.newInstance();
			parser = dbf.newDocumentBuilder();
			doc = parser.parse(in);
			doc.getDocumentElement().normalize();
			
			String rootNodeName=doc.getDocumentElement().getNodeName();
			
			if(rootNodeName.equals("DataTransfer")){
				dataTransferXml = new gov.nwcg.isuite.core.domain.xmlv3.DataTransferXml();
				
				NodeList childNodes = doc.getDocumentElement().getChildNodes();
				for(int i=0;i<childNodes.getLength();i++){
					Node cn = childNodes.item(i);
					
					//System.out.println(cn.getNodeName());
					
					if(cn.getNodeType()==1){
							String nn=cn.getNodeName();
							String val = cn.getTextContent();
							if(cn.getNodeName().equals("Password")){
								dataTransferXml.setPassword(val);
							}else if(cn.getNodeName().equals("ExportDate")){
								dataTransferXml.setExportDate(val);
							}else if(cn.getNodeName().equals("Source")){
								dataTransferXml.setSource(val);
							}else if(cn.getNodeName().equals("SourceRevisionLevel")){
								dataTransferXml.setSourceRevisionLevel(val);
							}else if(cn.getNodeName().equals("IncidentGroupTI")){
								dataTransferXml.setIncidentGroupTI(val);
							}else if(cn.getNodeName().equals("IncidentTI")){
								dataTransferXml.setIncidentTI(val);
							}
					}
				}
			}
			
		}catch(Exception e){
			throw e;
		}finally{
			try{
				dbf=null;
				parser=null;
				doc=null;
				if(null != in)
					in.close();
			}catch(Exception ee){}
		}

		return dataTransferXml;
	}

	public void importXmlData(DataTransferXml dtXml) throws Exception {
		//System.out.println("Temp folder for unencrypted xml files: " + this.tmpImportPath);
		String tableName="";
		try{
			// for each enum in TableImportOrderEnum
			// check for corresponding .xml file
			// if found process the file
			for(TableImportOrderEnum tie : TableImportOrderEnum.values()){
				log(Level.DEBUG,"xtc.importXmlData() " + tie.name());
				tableName=tie.name();
				//this.logEntries.add(".importXmlData() "+tie.name());
				XmlTable xmlTable = XmlTransferUtil.getXmlTableDefinition(tie.getTblClass());
				if(null==xmlTable){
					log(Level.ERROR,"Unable to create XmlTable instance for class " + tie.getTblClass());
					throw new RuntimeException("Unable to create XmlTable instance for class " + tie.getTblClass());
				}

				if(tie.name().equals("IswIncidentQuestion")){
					//System.out.println(tie.name());
				}
				if(tie.name().equals("IswQuestion")){
					//System.out.println(tie.name());
				}
				System.out.println(tie.name());
				
				// check for .xml file
				int cnt=1;
				Boolean bmorefiles=true;
				while(bmorefiles==true){
					String xmlFile=this.tmpImportPath+xmlTable.tableName+cnt+".xml";
					if(FileUtil.fileExists(xmlFile)){
						log(Level.DEBUG,".importXmlData() "+tie.name()+" File Exists: " + xmlFile);
						// load collection of xmlfields
						xmlTable.xmlFields = XmlTransferUtil.getXmlFields(tie.getTblClass());
						
						// import file
						StringBuffer xmlString=FileUtil.getFileContents(xmlFile);
						
						//Collection<Object> dataObjects =
						parseAndImportXmlDataObjects(tie,xmlTable,xmlString);

						cnt++;
						/*
						if(CollectionUtility.hasValue(dataObjects)){
							//System.out.println(tie.name());
							if(tie.name().equals("IswCostAccrualExtractRsc")){
								//System.out.println(tie.name());
							}
							importDataObjects(tie,xmlTable,dataObjects);
						}
						*/
					}else{
						// file not found, move to next tableimportenum
						log(Level.DEBUG,".importXmlData() "+tie.name()+" File Does Not Exist: " + xmlFile+cnt);
						bmorefiles=false;
					}
				}
			}
			
			if(this.resourceMap.size()>0){
				// update all parentresourceids
				Set<String> keySet = resourceMap.keySet();
				for(String s : keySet){
					String parentTi=resourceMap.get(s);
					String sql="update isw_resource set parent_resource_id = (select id from isw_resource where transferable_identity='"+parentTi+"') "+
							   "where transferable_identity='"+s+"'";
					try{	
						dao.executeUpdate(sql);
					}catch(Exception e4){
						log(Level.DEBUG,"xtc.updateParentResource() RowSql Error:"+ sql);
						log(Level.DEBUG,"xtc.updateParentResource() Exception:"+ e4.getMessage());
						throw e4;
					}
				}
			}
			
		}catch(Exception e){
			throw new Exception("Exception processing table " + tableName + " Exception: " + e.getMessage());
			//System.out.println(e.getMessage());
		}
	}
	
	public void parseAndImportXmlDataObjects(TableImportOrderEnum tie,XmlTable xmlTable,StringBuffer xml) throws Exception {
		InputStream in=null;
		DocumentBuilderFactory dbf = null;
		DocumentBuilder parser=null;
		Document doc = null;
		
		long milliStart=Calendar.getInstance().getTimeInMillis();
		try{
			log(Level.DEBUG,"xtc.parseAndImportXmlDataObjects() Start:" + tie.name() + " [milliStart="+milliStart+"]");
			in = IOUtils.toInputStream(xml.toString(), "UTF-8");
			dbf = DocumentBuilderFactory.newInstance();
			parser = dbf.newDocumentBuilder();
			doc = parser.parse(in);
			doc.getDocumentElement().normalize();
			
			String rootNodeName=doc.getDocumentElement().getNodeName();
			
			if(rootNodeName.equals("Data")){
				long cnt=0;
				
				NodeList childNodes = doc.getDocumentElement().getChildNodes();
				for(int i=0;i<childNodes.getLength();i++){
					cnt++;
					//log(Level.DEBUG,"xtc.parseAndImportXmlDataObjects() "+tie.name()+" Processing Row: " + cnt);
					Node cn = childNodes.item(i);
					
					//System.out.println(cn.getNodeName());
					
					if(cn.getNodeType()==1){
						if(cn.getNodeName().equals(tie.name())){
							Element childElement=(Element)cn;
							
							Object childObject
									= super.populateDataObjects(cn.getNodeName()
																, childElement
																, xmlTable.cls.getSimpleName());
							this.importDataObject(tie, xmlTable, childObject);
						}
					}
				}
			}
			
		}catch(Exception e){
			log(Level.DEBUG,"xtc.parseAndImportXmlDataObjects() Exception:" + e.getMessage());
			throw e;
		}finally{
			try{
				dbf=null;
				parser=null;
				doc=null;
				if(null != in)
					in.close();
			}catch(Exception ee){}
		}

		long milliStop=Calendar.getInstance().getTimeInMillis();
		long milliDiff=milliStop-milliStart;
		String seconds = String.valueOf(milliDiff/1000);

		log(Level.DEBUG,"xtc.parseAndImportXmlDataObjects() Finish:"+ tie.name() + " [milliStop="+milliStop+"]");
		log(Level.DEBUG,"xtc.parseAndImportXmlDataObjects() Finish Seconds:"+ seconds);
	}

	private void importDataObject(TableImportOrderEnum tie,XmlTable xmlTable, Object dataObject ) throws Exception{
		int otherLabelFieldCount=1;
		
		//log(Level.DEBUG,"xtc.importDataObject() Start:"+ tie.name());
		//for(Object dataObj : dataObjects){
			Boolean bContinue=true;
			Boolean isUpdate=false;
			
			if(tie.name().equalsIgnoreCase("IswResource")){
				XmlField xf1=xmlTable.getXmlFieldBySqlName("TRANSFERABLE_IDENTITY");
				XmlField xf2=xmlTable.getXmlFieldBySqlName("PARENT_RESOURCE_ID");
				if(null != xf2){
					String xml1Value=String.valueOf(XmlTransferUtil.invokeGetMethod(dataObject, xf1.name));
					String xml2Value=String.valueOf(XmlTransferUtil.invokeGetMethod(dataObject, xf2.name));
					if(StringUtility.hasValue(xml1Value) 
							&& StringUtility.hasValue(xml2Value)
							&& !xml2Value.equalsIgnoreCase("NULL"))
						this.resourceMap.put(xml1Value, xml2Value);
				}
				
			}
			
			// do any pre-processing rules
			if(tableHandlerRuleFactory.hasPreProcessRule(xmlTable)==true){
				try{
					TableHandler tableHandler = this.initTableHandler(dataObject, xmlTable);
					tableHandler.setOtherLabelFieldCount(otherLabelFieldCount);
					bContinue=tableHandler.doPreProcess();
					dataObject=tableHandler.getXmlObject();
					otherLabelFieldCount=otherLabelFieldCount+(tableHandler.getIsOtherLabelField()==true?1:0);
				}catch(Exception e1){
					log(Level.DEBUG,"xtc.importDataObjects() PreProcessRule Exception:"+ e1.getMessage());
					throw e1;
				}
			}

			if(bContinue==true){
				// does this table have a transferable identity field?
				if(xmlTable.hasTransferableIdentityField()){
					Long insertIdValue=0L;
					
					// do a lookup by ti and see if record already exists
					String ti = (String)XmlTransferUtil.invokeGetMethod(dataObject,"TI");
					String tiSql="select id from " + xmlTable.tableName+" where transferable_identity='"+ti+"' ";
					Long existingTableId=(Long)dao.executeQueryUniqueId(tiSql);
					
					String rowSql="";
					if(LongUtility.hasValue(existingTableId)){
						// set the idValue in the xmlObject
						XmlTransferUtil.invokeSetMethod(dataObject, "Id", existingTableId, "LONG");
						
						// get update sql
						rowSql=XmlTransferUtil.createUpdateSql(xmlTable, dataObject, dao.isOracleDialect());
						isUpdate=true;
					}else{
						// get next id sequence
						insertIdValue=this.getNextSequence(XmlTransferUtil.getSequenceName(xmlTable));
						
						// set the new idValue in the xmlObject
						XmlTransferUtil.invokeSetMethod(dataObject, "Id", insertIdValue, "LONG");
						
						// get insert sql
						rowSql=XmlTransferUtil.createInsertSql(xmlTable, dataObject, dao.isOracleDialect());
					}
					
					//System.out.println(rowSql);
					//log(Level.DEBUG,"xtc.importDataObjects() RowSql:"+ rowSql);
					try{	
						dao.executeUpdate(rowSql);
					}catch(Exception e4){
						log(Level.DEBUG,"xtc.importDataObjects() RowSql Error:"+ rowSql);
						log(Level.DEBUG,"xtc.importDataObjects() Exception:"+ e4.getMessage());
						throw e4;
					}
					
					if(isUpdate==true){
						if(tableHandlerRuleFactory.hasPostUpdateRule(xmlTable)==true){
							try{
								TableHandler tableHandler = this.initTableHandler(dataObject, xmlTable);
								tableHandler.doPostUpdateProcesses();
							}catch(Exception e2){
								log(Level.DEBUG,"xtc.importDataObjects() PostUpdateRule Exception:"+ e2.getMessage());
								throw e2;
							}
						}
					}else{
						if(tableHandlerRuleFactory.hasPostInsertRule(xmlTable)==true){
							try{
								TableHandler tableHandler = this.initTableHandler(dataObject, xmlTable);
								tableHandler.doPostInsertProcesses();
							}catch(Exception e3){
								log(Level.DEBUG,"xtc.importDataObjects() PostUpdateRule Exception:"+ e3.getMessage());
								throw e3;
							}
						}
					}
					
				}
				
			}
			
			if(tie.name().equalsIgnoreCase("IswlSubGroupCategory")){
				// address sub group category record C4
				String sql2="update iswl_sub_group_category set transferable_identity='ab5a4b5f-42bf-3e36-b546-328e395eafad' where code='C4'";
				dao.executeUpdate(sql2);
			}
		//}
		//log(Level.DEBUG,"xtc.importDataObjects() Finish:"+ tie.name());
	}
	
	private TableHandler initTableHandler(Object dataObj, XmlTable xmlTable) throws Exception {
		TableHandler tableHandler=tableHandlerRuleFactory.getTableHandler(dao, dataObj, xmlTable, this.dialogueVo);
		tableHandler.setRunMode(super.runMode);
		tableHandler.setContext(this.context);
		tableHandler.setFromWebServlet(this.fromWebServlet);
		tableHandler.setFromWebServletUserId(this.fromWebServletUserId);
		tableHandler.setDataStewardUserId(this.dataStewardUserId);
		tableHandler.setIncidentCount(this.incidentCount);
		tableHandler.setIncidentGroupCount(this.incidentGroupCount);
		return tableHandler;
	}
	
	private Long getNextSequence(String seqName) throws Exception {
		Long seq=0L;
		
		String sql="";
		if(dao.isOracleDialect()){
			sql="Select "+seqName+".nextVal from dual";
		}else{
			sql="Select nextVal('"+seqName+"') ";
		}

		Object idResult=null;
		
		try{
			idResult=dao.executeQuery(sql);				
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		if(null != idResult){
			Collection results = (Collection)idResult;
			if(CollectionUtility.hasValue(results)){
				for(Object object : results){
					seq=TypeConverter.convertToLong(object);
				}
			}
		}
		
		return seq;
	}
}
