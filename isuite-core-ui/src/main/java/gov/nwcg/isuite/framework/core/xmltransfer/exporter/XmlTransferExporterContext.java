package gov.nwcg.isuite.framework.core.xmltransfer.exporter;

import gov.nwcg.isuite.core.persistence.DataTransferDao;
import gov.nwcg.isuite.framework.core.xmltransfer.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransfer.data.XmlTable;
import gov.nwcg.isuite.framework.core.xmltransfer.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Level;
import org.springframework.context.ApplicationContext;

public class XmlTransferExporterContext extends AbstractXmlTransferExporterContext {
	private ApplicationContext context;
	private DataTransferDao dao = null;
	private TableObjectMap tableObjectMap = new TableObjectMap();
	public Collection<String> pdfFilenames=new ArrayList<String>();
	
	public int queryCount=0;
	public int updateCount=0;
	
	public XmlTransferExporterContext(ApplicationContext ctx){
		this.context=ctx;
		dao = (DataTransferDao)context.getBean("dataTransferDao");
	}

	
	/**
	 * 
	 * @param cls
	 * @param lookupField
	 * @param lookupValue
	 * @return
	 * @throws Exception
	 */
	public Collection<XmlTable> getXmlTableData(Class cls, String lookupField, Object lookupValue) throws Exception {
		log(Level.DEBUG,"XmlTransferExporterContext.getXmlTableData("+cls+","+lookupField+","+lookupValue+")");
		Collection<XmlTable> data = new ArrayList<XmlTable>();
	
		if(cls.getSimpleName().equals("IswAssignmentTimePost")){
			//System.out.println("");
		}
		
		XmlTable xmlTable = XmlTransferUtil.getXmlTableDefinition(cls);
		
		if(null==xmlTable){
			log(Level.ERROR,"Unable to create XmlTable instance for class " + cls);
			throw new RuntimeException("Unable to create XmlTable instance for class " + cls);
		}
		
		// load collection of xmlfields
		xmlTable.xmlFields = XmlTransferUtil.getXmlFields(cls);

		// check transferable identity field
		if(xmlTable.jointable==true){
			//this.checkTransferableIdentityJoinTable(xmlTable, lookupField, lookupValue);
		}else{
			this.checkTransferableIdentity(xmlTable, lookupField, lookupValue);
		}

		// generate sql
		Object bufferedObject=null;
		String ti=xmlTable.getTransferableIdentity();
		if(StringUtility.hasValue(ti)){
			bufferedObject=this.tableObjectMap.getObjectByTransferableIdentity(xmlTable.tableName, ti);
		}
		
		if(null==bufferedObject){
			if(lookupValue != null && !String.valueOf(lookupValue).equals("0")){
				String sql = super.generateSql(xmlTable,lookupField, lookupValue);

				log(Level.DEBUG,sql);
				
				// execute query on single fields and build results
				Object queryResults =null;
				try{
					queryResults = dao.executeQuery(sql);
					queryCount++;
				}catch(Exception e){
					this.log(Level.ERROR,"XmlTransferExporterContext.getXmlTableData() Exception on query:"+sql);
					this.log(Level.ERROR,e.getMessage());
					throw new RuntimeException(e);
				}
				
				if(null != queryResults){
					Collection results = (Collection)queryResults;
					if(CollectionUtility.hasValue(results)){
						for(Object object : results){
							XmlTable xt = XmlTable.copy(xmlTable);
							Object[] list = (Object[])object;
							Collection<XmlField> newXmlFields = populateXmlTableFieldValues(xt,list);
							xt.xmlFields=newXmlFields;
							data.add(xt);
							
							if(xt.tableName.equals("isw_report")){
								for(XmlField xmlfield : newXmlFields){
									if(xmlfield.name.equals("FileName"))
										this.pdfFilenames.add((String)xmlfield.value);
								}
							}
						}
					}
				}
				
				// for each result from above, determine if there are nested queries to execute
				Collection<XmlTable> newData = new ArrayList<XmlTable>();
				for(XmlTable xt : data){
					XmlTable newXt = XmlTable.copy(xt);
					if(xt.hasJoinTables()==true){
						for(XmlField f : xt.xmlFields){
							if(f.isComplex()==true && f.disjoined==false){
								log(Level.DEBUG,f.name);
								Object nestedLookupValue = super.getLookupValue(f,xt.xmlFields);
								if(null != nestedLookupValue){
									Collection<XmlTable> nestedTableData = this.getXmlTableData(f.target, f.lookupname, nestedLookupValue);
									for(XmlTable nt : nestedTableData){
										nt.nodeName=f.name;
										newXt.nestedTables.add(nt);
									}
								}
							}
						}
						newData.add(newXt);
					}else{
						if(StringUtility.hasValue(ti))
							this.tableObjectMap.addTableObject(xt.tableName, ti, newXt);
						newData.add(newXt);
					}
				}
				
				data = newData;

				for(XmlTable xt : newData){
					String xtTi=xt.getTransferableIdentity();
					if(StringUtility.hasValue(xtTi))
						this.tableObjectMap.addTableObject(xt.tableName, xtTi, xt);
				}
				
			}
		}else{
			XmlTable xtBufferedObject = XmlTable.copy((XmlTable)bufferedObject);
			xtBufferedObject.nodeName=xmlTable.nodeName;
			data.add((XmlTable)xtBufferedObject);
		}
		
		return data;
	}
	
	/*
	 * When exporting data, this method is called before each table is queried.
	 * We want to make sure to create/persist a transferableidentity value before populating the xml.
	 */
	private void checkTransferableIdentity(XmlTable xmlTable, String lookupField, Object lookupValue) throws Exception {
		
		// check if table has transferable identity field
		// if yes, check if transferable identity is null
		// if yes, generate it
		//if(xmlTable.hasTransferableIdentityField()==true && xmlTable.hasPrimaryKeyField()==true){
		if(xmlTable.hasTransferableIdentityField()==true ){
			// query for transferable identity
			String sqlUuid = super.generateSqlQueryUuid(xmlTable,lookupField, lookupValue);
			Object queryUuidResults = null;
			this.log(Level.DEBUG,"XmlTransferExporterContext.checkTransferableIdentity() query:"+sqlUuid);
			
			try{
				queryUuidResults=dao.executeQuery(sqlUuid);
				queryCount++;
			}catch(Exception e){
				this.log(Level.ERROR,"XmlTransferExporterContext.checkTransferableIdentity() Exception on query:"+sqlUuid);
				this.log(Level.ERROR,e.getMessage());
				throw new RuntimeException(e);
			}
			
			if(null != queryUuidResults){
				Collection uuidResults = (Collection)queryUuidResults;
				if(CollectionUtility.hasValue(uuidResults)){
					for(Object object : uuidResults){
						Object[] list = (Object[])object;
						int i=0;
						Long primaryKeyValue=null;
						String ti="";
						for(Object o : list){
							if(i==0){
								primaryKeyValue=TypeConverter.convertToLong(o);
							}else if(i==1){
								ti=TypeConverter.convertToString(o);
							}
							i++;
						}
						
						if(!StringUtility.hasValue(ti)){
							String uuid=super.generateUuid(xmlTable.tableName);
							String sqlUpdate=super.generateSqlUpdateUuid(xmlTable, "ID", primaryKeyValue, uuid);
							this.log(Level.DEBUG,"XmlTransferExporterContext.checkTransferableIdentity() query2:"+sqlUpdate);
							try{
								dao.executeUpdate(sqlUpdate);
								
								updateCount++;
								
								// update xmltable field value
								xmlTable.setFieldStringValue("TransferableIdentity", uuid);
							}catch(Exception e){
								this.log(Level.ERROR,"XmlTransferExporterContext.checkTransferableIdentity() Exception on update:"+sqlUpdate);
								this.log(Level.ERROR,e.getMessage());
								throw new RuntimeException(e);
							}
						}else{
							// update xmltable field value
							xmlTable.setFieldStringValue("TransferableIdentity", ti);
						}
					}
				}
			}
			
		}
		
	}
	
	private void checkTransferableIdentityJoinTable(XmlTable xmlTable, String lookupField, Object lookupValue) throws Exception {
		
		XmlField xfPrimary=xmlTable.getXmlFieldJoinKeyPrimary();
		XmlField xfSecondary=xmlTable.getXmlFieldJoinKeySecondary();
		
		// check if table has transferable identity field
		// if yes, check if transferable identity is null
		// if yes, generate it
		//if(xmlTable.hasTransferableIdentityField()==true && xmlTable.hasPrimaryKeyField()==true){
		if(xmlTable.hasTransferableIdentityField()==true ){
			// query for transferable identity
			String sqlUuid = super.generateSqlQueryUuid(xmlTable,lookupField, lookupValue);
			Object queryUuidResults =null;
			this.log(Level.DEBUG,"checkTransferableIdentityJoinTable() query: "+sqlUuid);
			
			try{
				queryUuidResults = dao.executeQuery(sqlUuid);
			}catch(Exception e){
				this.log(Level.ERROR,"checkTransferableIdentityJoinTable() Exception on query:"+sqlUuid);
				this.log(Level.ERROR,e.getMessage());
				throw new RuntimeException(e);
			}
			if(null != queryUuidResults){
				Collection uuidResults = (Collection)queryUuidResults;
				if(CollectionUtility.hasValue(uuidResults)){
					/* join table records could have multiple results
					 * process each independently
					 */
					for(Object object : uuidResults){
						Object[] list = (Object[])object;
						int i=0;
						Long primaryKeyValue=null;
						Long primaryFieldValue=null;
						Long secondaryFieldValue=null;
						String ti="";
						for(Object o : list){
							switch(i)
							{
								case 0:
									primaryKeyValue=TypeConverter.convertToLong(o);
									break;
								case 1:
									ti=TypeConverter.convertToString(o);
									break;
								case 2:
									primaryFieldValue=TypeConverter.convertToLong(o);
									break;
								case 3:
									secondaryFieldValue=TypeConverter.convertToLong(o);
									break;
							}
							i++;
						}
						
						if(!StringUtility.hasValue(ti)){
							String uuid=super.generateUuid(xmlTable.tableName);
							StringBuffer sql = new StringBuffer();
							sql.append("UPDATE " + xmlTable.tableName + " " )
								.append("SET transferable_identity = '"+uuid +"' ")
								.append("WHERE "+xfPrimary.sqlname+" = " + primaryFieldValue+" ")
								.append("AND "+xfSecondary.sqlname+" = " + secondaryFieldValue+" ");
							
							String sqlUpdate=sql.toString();
							this.log(Level.DEBUG,"checkTransferableIdentityJoinTable() query2: "+sqlUpdate);
							
							try{
								dao.executeUpdate(sqlUpdate);
							}catch(Exception e){
								this.log(Level.ERROR,"XmlTransferExporterContext.checkTransferableIdentityJoinTable() Exception on update:"+sqlUpdate);
								this.log(Level.ERROR,e.getMessage());
								throw new RuntimeException(e);
							}
						}
					}
				}
			}
			
		}
		
	}

	public String toXml(Collection<XmlTable> xmlTableData, int level) {
		StringBuffer xml = new StringBuffer();
		
		for(XmlTable xt : xmlTableData){
			xml.append(indent(level)+"<"+xt.nodeName+">"+"\n");
			// add single fields
			for(XmlField f : xt.xmlFields){
				if(f.isComplex()==false){
					if(f.ischardata==true){
						xml.append(indent(level+1)+"<"+f.name+"><![CDATA["+f.value+"]]></"+f.name+">"+"\n");
					}else{
						xml.append(indent(level+1)+"<"+f.name+">"+f.value+"</"+f.name+">"+"\n");
					}
				}
			}
			// add nested tables
			if(CollectionUtility.hasValue(xt.nestedTables)){
				xml.append(toXml(xt.nestedTables,level+1));
			}
			
			xml.append(indent(level)+"</"+xt.nodeName+">"+"\n");
		}
		
		return xml.toString();
	}
}
