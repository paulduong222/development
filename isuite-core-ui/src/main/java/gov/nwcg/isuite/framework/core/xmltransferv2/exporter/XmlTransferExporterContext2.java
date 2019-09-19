package gov.nwcg.isuite.framework.core.xmltransferv2.exporter;

import gov.nwcg.isuite.core.domain.xmlv2.DataTransferInc;
import gov.nwcg.isuite.core.domain.xmlv2.DataTransferIncGroup;
import gov.nwcg.isuite.core.persistence.DataTransferDao;
import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlTable;
import gov.nwcg.isuite.framework.core.xmltransferv2.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import org.apache.log4j.Level;
import org.springframework.context.ApplicationContext;

public class XmlTransferExporterContext2 extends AbstractXmlTransferExporterContext {
	private ApplicationContext context;

	public String tempFolder="";

	public Long incidentGroupId=0L;
	public Collection<Long> incidentIds=null;
	public String incidentIdList="";
	public String incidentGroupIdList="";

	private FileWriter fwriter = null;
	private BufferedWriter writer=null;
	private PrintWriter out=null;
	private String currentTableFile="";
	private boolean isNewFile=false;

	public Collection<String> pdfFilenames=new ArrayList<String>();
	private Collection<DataTransferInc> dtIncidents=new ArrayList<DataTransferInc>();
	private Collection<DataTransferIncGroup> dtIncidentGroups=new ArrayList<DataTransferIncGroup>();
	
	public XmlTransferExporterContext2(ApplicationContext ctx){
		this.context=ctx;
		dao = (DataTransferDao)context.getBean("dataTransferDao");
	}

	private void setIncidentIdList(){
		if(LongUtility.hasValue(this.incidentGroupId))
			this.incidentGroupIdList=String.valueOf(this.incidentGroupId);

		if(CollectionUtility.hasValue(this.incidentIds)){
			int cnt=0;
			for(Long id : this.incidentIds){
				if(cnt==0)
					this.incidentIdList=String.valueOf(id);
				else
					this.incidentIdList=this.incidentIdList+","+String.valueOf(id);

				cnt++;
			}
		}
	}

	public void exportData() throws Exception {

		setIncidentIdList();

		if(StringUtility.hasValue(this.incidentIdList)){
			// verify ti for all incidentIds
			for(Long incId : this.incidentIds){
				String sql="select transferable_identity from isw_incident where id = " + incId;
				Object queryResults =null;
				try{
					queryResults = dao.executeQuery(sql);
					if(null != queryResults && !String.valueOf(queryResults).contains("null")){
						
					}else{
						sql="";
						String ti=super.generateUuid("isw_incident");
						sql="update isw_incident set transferable_identity='"+ti+"' where id="+incId+" ";
						dao.executeUpdate(sql);
					}
				}catch(Exception e){
					this.log(Level.ERROR,e.getMessage());
					throw new RuntimeException(e);
				}
				
			}
		}
		if(StringUtility.hasValue(this.incidentGroupIdList)){
			// verify ti for all incidentGroupId
			String sql="select transferable_identity from isw_incident_group where id = " + this.incidentGroupId;
			Object queryResults =null;
			try{
				queryResults = dao.executeQuery(sql);
				if(null != queryResults && !String.valueOf(queryResults).contains("null")){
				}else{
					sql="";
					String ti=super.generateUuid("isw_incident_group");
					sql="update isw_incident_group set transferable_identity='"+ti+"' where id="+this.incidentGroupId+" ";
					dao.executeUpdate(sql);
				}
			}catch(Exception e){
				this.log(Level.ERROR,e.getMessage());
				throw new RuntimeException(e);
			}
		}

		// address sub group category record C4
		String sql2="update iswl_sub_group_category set transferable_identity='ab5a4b5f-42bf-3e36-b546-328e395eafad' where code='C4'";
		dao.executeUpdate(sql2);
		
		for (TableExportOrderEnum toe : TableExportOrderEnum.values()){
			//System.out.println(toe.name());
			if(toe.name().equals("IswIncidentGroupIncident")){
				//System.out.println(toe.name());
			}
			boolean bContinue=true;

			// do not continue if toe is filterbyincident and we have no incident id's
			if(BooleanUtility.isTrue(toe.getFilterIncident())
					&& !StringUtility.hasValue(this.incidentIdList))
				bContinue=false;
			
			// do not continue if toe is filterbygroup and we have no incident group id
			if(BooleanUtility.isTrue(toe.getFilterGroup())
					&& !StringUtility.hasValue(this.incidentGroupIdList))
				bContinue=false;
			
			if(toe.name().equals("IswTimeInvoice")){
				//System.out.println("");
			}
			if(toe.name().equals("IswResource")){
				System.out.println("");
			}
			
			if(bContinue==true){
				XmlTable xmlTable = XmlTransferUtil.getXmlTableDefinition(toe.getTblClass());
				if(null==xmlTable){
					log(Level.ERROR,"Unable to create XmlTable instance for class " + toe.getTblClass());
					throw new RuntimeException("Unable to create XmlTable instance for class " + toe.getTblClass());
				}
	
				// load collection of xmlfields
				xmlTable.xmlFields = XmlTransferUtil.getXmlFields(toe.getTblClass());
	
				// get all records for this lookup
				Collection<XmlTable> data = this.readTable(xmlTable,toe);

				if(toe.name().equals("IswResource") ||
						toe.name().equals("IswIapPersonnel1")||
						toe.name().equals("IswIapPersonnel2")){
					// read table again, to ensure we have parent_ti 
					data = this.readTable(xmlTable,toe);
				}
				if(toe.name().equals("IswIncident")){
					bufferIncidentData(xmlTable,data);
				}else if(toe.name().equals("IswIncidentGroup")){
					bufferIncidentGroupData(xmlTable,data);
				}
				
				// write this data to file
				if(CollectionUtility.hasValue(data))
					writeToFile(xmlTable,data);
	
				data=null;
			}
		}
		
		this.addEndingTag();
	}

	private Collection<XmlTable> readTable(XmlTable xmlTable,TableExportOrderEnum toe) throws Exception{
		Collection<XmlTable> data = new ArrayList<XmlTable>();

		String sql = super.generateSql(xmlTable,this.incidentIdList,this.incidentGroupIdList,toe);

		// execute query on single fields and build results
		Object queryResults =null;
		try{
			queryResults = dao.executeQuery(sql);

			if(null != queryResults){
				Collection results = (Collection)queryResults;
				if(CollectionUtility.hasValue(results)){
					for(Object object : results){
						XmlTable xt = XmlTable.copy(xmlTable);
						Object[] list = (Object[])object;
						Collection<XmlField> newXmlFields = populateXmlTableFieldValues(xt,list);
						xt.xmlFields=newXmlFields;
						
						// check if have ti, if not , create and update
						if(xt.hasTransferableIdentityField()){
							String ti=xt.getTransferableIdentity();
							if(!StringUtility.hasValue(ti)){
								Long primaryId=xt.getId();
								// generate and update one
								ti=super.generateUuid(xt.tableName);
								sql="update "+xt.tableName+" set transferable_identity='"+ti+"' where id="+primaryId+" ";
								try{
									dao.executeUpdate(sql);
									xt.setFieldStringValue("TI", ti);
									//dao.executeUpdate("commit");
								}catch(Exception e){
									this.log(Level.ERROR,e.getMessage());
									throw new RuntimeException(e);
								}
							}
						}
						data.add(xt);

						if(xt.tableName.equals("isw_report")){
							XmlField fn=xt.getXmlFieldBySqlName("FILE_NAME");
							if(null != fn && null != fn.value){
								String filename=String.valueOf(fn.value);
								this.pdfFilenames.add(filename);
							}
						}
						if(xt.tableName.equals("isw_iap_attachment")){
							XmlField fn=xt.getXmlFieldBySqlName("FILENAME");
							if(null != fn && null != fn.value){
								String filename=String.valueOf(fn.value);
								this.pdfFilenames.add(filename);
							}
						}
					}
				}
			}
		}catch(Exception e){
			this.log(Level.ERROR,"XmlTransferExporterContext.readTable() Exception on query:"+sql);
			this.log(Level.ERROR,e.getMessage());
			throw new RuntimeException(e);
		}

		return data;
	}

	private void writeToFile(XmlTable xt, Collection<XmlTable> data) throws Exception {
		if(!FileUtil.isDir(tempFolder)){
			FileUtil.makeDir(tempFolder);
		}

		if(xt.tableName.equals("isw_inc_res_daily_cost")){
			//System.out.println("");
		}
		
		Hashtable ht = new Hashtable();
		if(CollectionUtility.hasValue(data) && data.size()>5000){
			ht=CollectionUtility.splitCollection(data, 5000);
		}else{
			ht.put(1, data);
		}
		
		//System.out.println(ht.size());
		for(int x = 1;x<=ht.size();x++){
			Collection<XmlTable> htData = (Collection<XmlTable>)ht.get(x);
			String tableFile=tempFolder+File.separator+xt.tableName+x+".xml";

			initWriter(tableFile);

			try{
				if(null != out){
					for(XmlTable t : htData){
						StringBuffer xml = new StringBuffer();
						Long id=0L;

						xml.append("<"+xt.nodeName+">"+"");

						// add single fields
						for(XmlField f : t.xmlFields){
							if(f.sqlname.equals("ID"))
								id=(Long)f.value;

							if(f.ischardata==true){
								xml.append("<"+f.name+"><![CDATA["+f.value+"]]></"+f.name+">"+"");
							}else{
								xml.append("<"+f.name+">"+f.value+"</"+f.name+">"+"");
							}
						}
						xml.append("</"+xt.nodeName+">");

						String s=(isNewFile==true?"<Data>":"")+xml.toString();

						// write data
						out.write(s);
						this.isNewFile=false;
					}
				}
			}catch(Exception e){
				throw e;
			}finally{
				this.closeFileWriter();
			}
		}
	}

	private void initWriter(String tableFile) throws Exception{
		try{
			if(FileUtil.fileExists(tableFile)){
				if(!this.currentTableFile.equals(tableFile)){
					this.closeFileWriter();
				}
				if(null == writer){
					fwriter = new FileWriter(tableFile,true);
					writer = new BufferedWriter(fwriter);
					out = new PrintWriter(writer);
					this.currentTableFile=tableFile;
				}
			}else{
				this.closeFileWriter();
				FileUtil.createFile(tableFile);
				this.isNewFile=true;
				fwriter = new FileWriter(tableFile,true);
				writer = new BufferedWriter(fwriter);
				out = new PrintWriter(writer);
				this.currentTableFile=tableFile;
			}
		}catch(Exception e){
			this.closeFileWriter();
			throw e;
		}
	}

	public void closeFileWriter(){
		this.currentTableFile="";

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

	public void addEndingTag() throws Exception {
		this.closeFileWriter();
		Collection<String> files = FileUtil.getDirFilenames(tempFolder);
		if(CollectionUtility.hasValue(files)){
			for(String s : files){
				if(s.endsWith(".xml")){
					this.initWriter(tempFolder+File.separator+s);
					if(null != out)
						out.write("</Data>");
					this.closeFileWriter();
				}
			}
		}
	}

	private void bufferIncidentData(XmlTable xt, Collection<XmlTable> data){
		for(XmlTable xtData : data){
			DataTransferInc inc = new DataTransferInc();
			try{
				inc.setId(TypeConverter.convertToLong(xtData.getXmlFieldValueBySqlName("ID")));
				inc.setTI(TypeConverter.convertToString(xtData.getXmlFieldValueBySqlName("TRANSFERABLE_IDENTITY")));
				inc.setName(TypeConverter.convertToString(xtData.getXmlFieldValueBySqlName("INCIDENT_NAME")));
				inc.setNbr(TypeConverter.convertToString(xtData.getXmlFieldValueBySqlName("NBR")));
				inc.setYear(TypeConverter.convertToInteger(xtData.getXmlFieldValueBySqlName("INCIDENT_YEAR")));
				inc.setHomeUnitTI(TypeConverter.convertToString(xtData.getXmlFieldValueBySqlName("UNIT_ID")));
			}catch(Exception e){
			}
			
			this.dtIncidents.add(inc);
		}
	}
	
	private void bufferIncidentGroupData(XmlTable xt, Collection<XmlTable> data){
		for(XmlTable xtData : data){
			DataTransferIncGroup incgroup = new DataTransferIncGroup();
			try{
				incgroup.setId(TypeConverter.convertToLong(xtData.getXmlFieldValueBySqlName("ID")));
				incgroup.setTI(TypeConverter.convertToString(xtData.getXmlFieldValueBySqlName("TRANSFERABLE_IDENTITY")));
				incgroup.setName(TypeConverter.convertToString(xtData.getXmlFieldValueBySqlName("GROUP_NAME")));
			}catch(Exception e){
			}
			
			this.dtIncidentGroups.add(incgroup);
		}
	}
	
	public String getBufferIncData(){
		String xml="";
		for(DataTransferInc inc : this.dtIncidents){
			xml=xml+
			  "<DataTransferInc>"+
			  " <Id>"+inc.getId()+"</Id>"+
			  " <Name>"+inc.getName()+"</Name>"+
			  " <Nbr>"+inc.getNbr()+"</Nbr>"+
			  " <TI>"+inc.getTI()+"</TI>"+
			  " <HomeUnitTI>"+inc.getHomeUnitTI()+"</HomeUnitTI>" +
			  " <UnitCode>"+""+"</UnitCode>" +
			  " <Year>"+inc.getYear()+"</Year>"+
			  "</DataTransferInc>";
		}
		return xml;
	}
	
	public String getBufferIncGroupData(){
		String xml="";
		for(DataTransferIncGroup incgroup : this.dtIncidentGroups){
			xml=xml+
			  "<DataTransferIncGroup>"+
			  " <Id>"+incgroup.getId()+"</Id>"+
			  " <TI>"+incgroup.getTI()+"</TI>"+
			  " <Name>"+incgroup.getName()+"</Name>"+
			  "</DataTransferIncGroup>";
		}
		return xml;
	}
}
