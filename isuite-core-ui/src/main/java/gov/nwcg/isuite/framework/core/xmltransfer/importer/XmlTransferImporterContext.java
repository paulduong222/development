package gov.nwcg.isuite.framework.core.xmltransfer.importer;

import gov.nwcg.isuite.core.domain.xml.DataTransferXml;
import gov.nwcg.isuite.core.domain.xml.IswIncident;
import gov.nwcg.isuite.core.domain.xml.IswIncidentGroup;
import gov.nwcg.isuite.core.persistence.DataTransferDao;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.xmltransfer.data.XmlTable;
import gov.nwcg.isuite.framework.core.xmltransfer.utility.XmlTransferUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Level;
import org.springframework.context.ApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlTransferImporterContext extends AbstractXmlTransferImporterContext {
	private ApplicationContext context;
	private StringBuffer xml;
	private SqlGenerator sqlGenerator = null;
	public int queryCount=0;
	public int updateCount=0;
	public int insertCount=0;
	public Boolean hasIncidentGroup=false;
	public Boolean fromWebServlet=false;
	public Long fromWebServletUserId=0L;
	public Long dataStewardUserId=0L;
	public DialogueVo dialogueVo=null;
	
	public XmlTransferImporterContext(ApplicationContext ctx, StringBuffer xmlString){
		super();
		this.context=ctx;
		dao = (DataTransferDao)context.getBean("dataTransferDao");
		sqlGenerator = new SqlGenerator(super.dao);
		sqlGenerator.logEntries=super.logEntries;
		sqlGenerator.context=this.context;
		xml=xmlString;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public DataTransferXml getRootObject() throws Exception {
		DataTransferXml dataTransferXml= null;
		IswIncident incidentObject = null;
		IswIncidentGroup incidentGroupObject = null;
		InputStream in=null;
		
		try{
			in = IOUtils.toInputStream(xml.toString(), "UTF-8");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = dbf.newDocumentBuilder();
			Document doc = parser.parse(in);
			doc.getDocumentElement().normalize();
			
			String rootNodeName=doc.getDocumentElement().getNodeName();
			
			if(rootNodeName.equals("DataTransfer")){
				dataTransferXml = new DataTransferXml();
				
				NodeList childNodes = doc.getDocumentElement().getChildNodes();
				for(int i=0;i<childNodes.getLength();i++){
					Node cn = childNodes.item(i);
					
					//System.out.println(cn.getNodeName());
					
					if(cn.getNodeType()==1){
						if(cn.getNodeName().equals("IswIncident")){
							incidentObject=(IswIncident)super.populateDataObjects("Incident",(Element)cn,"Incident", "IswIncident");
							dataTransferXml.getIncidents().add(incidentObject);
							//dataTransferXml.setIncident(incidentObject);
						}else if(cn.getNodeName().equals("IswIncidentGroup")){
							incidentGroupObject=(IswIncidentGroup)super.populateDataObjects("IncidentGroup",(Element)cn,"IncidentGroup", "IswIncidentGroup");
							dataTransferXml.setIncidentGroup(incidentGroupObject);
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
				if(null != in)
					in.close();
			}catch(Exception ee){}
		}

		return dataTransferXml;
	}

	/**
	 * @param rootObject
	 * @return
	 * @throws Exception
	 */
	public Collection<String> generateSqlStatements(Object rootObject) throws Exception {
		Collection<String> sqls = new ArrayList<String>();

		XmlTable xt = XmlTransferUtil.getXmlTableDefinition(rootObject.getClass());
		xt.xmlFields = XmlTransferUtil.getXmlFields(rootObject.getClass());
		super.log(Level.DEBUG,"XmlTransferImporterContext.generateSqlStatements() Processing "+xt.nodeName);

		sqlGenerator.hasIncidentGroup=this.hasIncidentGroup;
		sqlGenerator.setRunMode(super.runMode);
		sqlGenerator.fromWebServlet=this.fromWebServlet;
		sqlGenerator.fromWebServletUserId=this.fromWebServletUserId;
		sqlGenerator.dataStewardUserId=this.dataStewardUserId;
		sqlGenerator.dialogueVo=this.dialogueVo;
		
		// generate and process sql's
		sqlGenerator.generateTableSql(rootObject,xt,sqls);

		this.queryCount=sqlGenerator.queryCount;
		this.insertCount=sqlGenerator.insertCount;
		this.updateCount=sqlGenerator.updateCount;
		
		return sqls;
	}
	
	public Collection<String> processIncidentResources() throws Exception {
		Collection<String> sqls = new ArrayList<String>();
		sqlGenerator.hasIncidentGroup=false;

		for(Object rootObject : sqlGenerator.incidentResourceXmlObjects){
			XmlTable xt = XmlTransferUtil.getXmlTableDefinition(rootObject.getClass());
			xt.xmlFields = XmlTransferUtil.getXmlFields(rootObject.getClass());
			super.log(Level.DEBUG,"XmlTransferImporterContext.generateSqlStatements() Processing "+xt.nodeName);

			sqlGenerator.setRunMode(super.runMode);
			
			// generate and process sql's
			sqlGenerator.generateTableSql(rootObject,xt,sqls);

			this.queryCount=sqlGenerator.queryCount;
			this.insertCount=sqlGenerator.insertCount;
			this.updateCount=sqlGenerator.updateCount;
		}
		
		return sqls;
	}

	public Collection<String> processIncidentResourceOthers() throws Exception {
		Collection<String> sqls = new ArrayList<String>();
		sqlGenerator.hasIncidentGroup=false;

		for(Object rootObject : sqlGenerator.incidentResourceOtherXmlObjects){
			XmlTable xt = XmlTransferUtil.getXmlTableDefinition(rootObject.getClass());
			xt.xmlFields = XmlTransferUtil.getXmlFields(rootObject.getClass());
			super.log(Level.DEBUG,"XmlTransferImporterContext.generateSqlStatements() Processing "+xt.nodeName);

			sqlGenerator.setRunMode(super.runMode);
			
			// generate and process sql's
			sqlGenerator.generateTableSql(rootObject,xt,sqls);

			this.queryCount=sqlGenerator.queryCount;
			this.insertCount=sqlGenerator.insertCount;
			this.updateCount=sqlGenerator.updateCount;
		}
		
		return sqls;
	}

	public Collection<String> processOther() throws Exception {
		Collection<String> sqls = new ArrayList<String>();
		sqlGenerator.hasIncidentGroup=false;

		for(Object rootObject : sqlGenerator.otherXmlObjects){
			XmlTable xt = XmlTransferUtil.getXmlTableDefinition(rootObject.getClass());
			xt.xmlFields = XmlTransferUtil.getXmlFields(rootObject.getClass());
			super.log(Level.DEBUG,"XmlTransferImporterContext.generateSqlStatements() Processing "+xt.nodeName);

			sqlGenerator.setRunMode(super.runMode);
			
			// generate and process sql's
			sqlGenerator.generateTableSql(rootObject,xt,sqls);

			this.queryCount=sqlGenerator.queryCount;
			this.insertCount=sqlGenerator.insertCount;
			this.updateCount=sqlGenerator.updateCount;
		}
		
		return sqls;
	}
	
}
