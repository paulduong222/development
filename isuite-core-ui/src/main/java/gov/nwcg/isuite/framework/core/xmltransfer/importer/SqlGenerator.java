package gov.nwcg.isuite.framework.core.xmltransfer.importer;

import gov.nwcg.isuite.core.persistence.DataTransferDao;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.xmltransfer.AbstractXmlTransfer;
import gov.nwcg.isuite.framework.core.xmltransfer.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransfer.data.XmlTable;
import gov.nwcg.isuite.framework.core.xmltransfer.importer.handlers.TableHandler;
import gov.nwcg.isuite.framework.core.xmltransfer.importer.handlers.TableHandlerRuleFactory;
import gov.nwcg.isuite.framework.core.xmltransfer.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Level;
import org.springframework.context.ApplicationContext;

public class SqlGenerator extends AbstractXmlTransfer{
	protected ApplicationContext context=null;
	private DataTransferDao dao=null;
	private TableHandlerRuleFactory tableHandlerRuleFactory = new TableHandlerRuleFactory();
	private TransferableIdentityMap tiMappings = new TransferableIdentityMap();
	private TablesUpdatedMap tablesUpdatedMappings = new TablesUpdatedMap();
	public Boolean hasIncidentGroup=false;
	public Boolean fromWebServlet=false;
	public Long fromWebServletUserId=0L;
	public Long dataStewardUserId=0L;
	public DialogueVo dialogueVo=null;
	
	// list of incident resources to postpone processing until the very end 
	// only needed if we are importing an incident group to ensure that
	// we can process cross-incident data
	public Collection<Object> incidentResourceXmlObjects = new ArrayList<Object>();
	public Collection<Object> incidentResourceOtherXmlObjects = new ArrayList<Object>();
	public Collection<Object> otherXmlObjects = new ArrayList<Object>();
	
	public int queryCount=0;
	public int insertCount=0;
	public int updateCount=0;
	
	public SqlGenerator(DataTransferDao dtDao){
		dao=dtDao;
	}

	public void generateTableSql(Object xmlObject,XmlTable xt,Collection<String> sqls) throws Exception {
		log(Level.DEBUG,"SqlGenerator.generateTableSql() "+xt.tableName);

		if(hasIncidentGroup==true && xt.tableName.equals("isw_incident_resource")){
			log(Level.DEBUG,"SqlGenerator.generateTableSql() Postponing IncidentResource import.");
			this.incidentResourceXmlObjects.add(xmlObject);
			return;
		}

		if(hasIncidentGroup==true && xt.tableName.equals("isw_incident_resource_other")){
			log(Level.DEBUG,"SqlGenerator.generateTableSql() Postponing IncidentResourceOther import.");
			this.incidentResourceOtherXmlObjects.add(xmlObject);
			return;
		}

		if(hasIncidentGroup==true && xt.tableName.equals("isw_cost_accrual_ext_rsc")){
			log(Level.DEBUG,"SqlGenerator.generateTableSql() Postponing CostAccrualExtRes import.");
			this.otherXmlObjects.add(xmlObject);
			return;
		}
		
		//System.out.println(xt.tableName);
		if(xt.tableName.equals("isw_incident_group_incident")){
			//System.out.println();
		}
		
		// process derived tables first
		if(CollectionUtility.hasValue(xt.getDerivedTables())){
			for(XmlField xf : xt.getDerivedTables()){
				Object derivedObject = XmlTransferUtil.invokeGetMethod(xmlObject, xf.derivedfield);
				
				if(null != derivedObject && !(derivedObject instanceof String)){
					// load xmltable definition
					XmlTable xtDerivedObject = XmlTransferUtil.getXmlTableDefinition(derivedObject.getClass());
					xtDerivedObject.xmlFields = XmlTransferUtil.getXmlFields(derivedObject.getClass());

					generateTableSql(derivedObject,xtDerivedObject,sqls);
					
					// update xmlObject with derived value
					XmlField df = xt.getXmlFieldByName(xf.derivedfield);
					if(null != df){
						Object derivedVal=XmlTransferUtil.invokeGetMethod(derivedObject, df.lookupname);
						XmlTransferUtil.invokeSetMethod(xmlObject, xf.name, derivedVal, xf.type);
					}
				}
			}
		}
		
		if(xt.tableName.equals("isw_incident_group_incident")){
			//System.out.println();
		}

		// determine if this table has special processing 
		if(tableHandlerRuleFactory.hasPreProcessRule(xt)==true){
			TableHandler tableHandler=tableHandlerRuleFactory.getTableHandler(dao, xmlObject, xt, this.dialogueVo);
			tableHandler.setRunMode(super.runMode);
			tableHandler.setContext(this.context);
			tableHandler.setFromWebServlet(this.fromWebServlet);
			tableHandler.setFromWebServletUserId(this.fromWebServletUserId);
			tableHandler.setDataStewardUserId(this.dataStewardUserId);
			Boolean bContinue=tableHandler.doPreProcess();
			xmlObject=tableHandler.getXmlObject();
			
			if(bContinue==false)
				return;
		}
		
		// process this table 
		if(xt.jointable==true){
			// join table have no primary key ID field, so handle these a little differently
			generateCurrentTableJoinSql(xmlObject,xt,sqls);
		}else{
			generateCurrentTableSql(xmlObject,xt,sqls);
		}
		
		// process cascade tables
		if(CollectionUtility.hasValue(xt.getCascadeTables())){
			for(XmlField f : xt.getCascadeTables()){
				Object cascadeObject=XmlTransferUtil.invokeGetMethod(xmlObject, f.name);
				if(null==cascadeObject){
					// try and see if its a collection getter method
					cascadeObject=XmlTransferUtil.invokeGetMethod(xmlObject, f.name+"s");
				}
				
				if(null != cascadeObject){
					if(cascadeObject instanceof Collection){
						for(Object co : (Collection)cascadeObject){
							XmlTable xtCascadeObject = XmlTransferUtil.getXmlTableDefinition(co.getClass());
							xtCascadeObject.xmlFields = XmlTransferUtil.getXmlFields(co.getClass());

							XmlField targetField=xtCascadeObject.getXmlFieldByName(f.lookupname);
							
							// get the source value
							Object val = XmlTransferUtil.invokeGetMethod(xmlObject,f.sourcename);

							// set the lookupname field value with the source value
							XmlTransferUtil.invokeSetMethod(co, f.lookupname, val, targetField.type);
							
							this.generateTableSql(co, xtCascadeObject, sqls);
						}
					}else{
						XmlTable xtCascadeObject = XmlTransferUtil.getXmlTableDefinition(cascadeObject.getClass());
						xtCascadeObject.xmlFields = XmlTransferUtil.getXmlFields(cascadeObject.getClass());

						XmlField targetField=xtCascadeObject.getXmlFieldByName(f.lookupname);
						
						// get the source value
						Object val = XmlTransferUtil.invokeGetMethod(xmlObject,f.sourcename);
						
						// set the lookupname field value with the source value
						XmlTransferUtil.invokeSetMethod(cascadeObject, f.lookupname, val, targetField.type);
						
						this.generateTableSql(cascadeObject, xtCascadeObject, sqls);
					}
				}
			}
			
		}
		
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
			super.log(Level.DEBUG, "getNextSequence("+seqName+") :"+sql);
			idResult=dao.executeQuery(sql);				
			queryCount++;
		}catch(Exception e){
			this.log(Level.ERROR,"SqlGenerator.getNextSequence() Exception on query:"+sql);
			this.log(Level.ERROR,e.getMessage());
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
	
	public void generateCurrentTableSql(Object xmlObject,XmlTable xt,Collection<String> sqls) throws Exception {
		log(Level.DEBUG,"SqlGenerator.generateCurrentTableSql() "+xt.tableName);

		if(xt.tableName.equals("isw_resource_invoice")){
			//System.out.println("");
		}
		
		// resolve disjoined field first
		if(CollectionUtility.hasValue(xt.getDisjoinedFields())){
			for(XmlField xf : xt.getDisjoinedFields()){
				
				Object disjoinedValue=XmlTransferUtil.invokeGetMethod(xmlObject, xf.name);
				Long disjoinedVal=0L;
				XmlField targetField=xt.getXmlFieldByName(xf.sourcename);
				
				if(null==targetField){
					log(Level.DEBUG,"SqlGenerator.generateCurrentTableSql() Unable to get TargetField: "+xf.sourcename);
				}
				
				if(null != disjoinedValue && StringUtility.hasValue(String.valueOf(disjoinedValue))){
					String dsjQuery="select id from "+xf.disjoinedtable+
									 " where "+xf.disjoinedfield+"='"+(String)disjoinedValue+"'";
					Object dsjResult=null;
					super.log(Level.DEBUG, "generateCurrentTableSql() :"+dsjQuery);
					try{
						dsjResult=dao.executeQuery(dsjQuery);
						queryCount++;
					}catch(Exception e){
						this.log(Level.ERROR,"SqlGenerator.generateCurrentTableSql() Exception on query:"+dsjQuery);
						this.log(Level.ERROR,e.getMessage());
						throw new RuntimeException(e);
					}
					if(null != dsjResult){
						Collection dsjResults = (Collection)dsjResult;
						if(CollectionUtility.hasValue(dsjResults)){
							for(Object object : dsjResults){
								disjoinedVal=TypeConverter.convertToLong(object);
							}
						}
					}
			}
				
				if(LongUtility.hasValue(disjoinedVal)){
					XmlTransferUtil.invokeSetMethod(xmlObject, targetField.name, disjoinedVal, targetField.type);
				}
			}
		}
		
		// check timappings to see if we have already resolved the id for the ti field
		String ti = (String)XmlTransferUtil.invokeGetMethod(xmlObject,"TransferableIdentity");
		if(null==ti || (StringUtility.hasValue(ti) && ti.equals("null"))){
			//System.out.println("");
		}
		
		Long idValue=0L;
		
		if(xt.finaltable==true){
			for(XmlField xf : xt.xmlFields){
				if(xf.joinkeyprimary==true){
					Long idval = (Long)XmlTransferUtil.invokeGetMethod(xmlObject,xf.name);
					if(LongUtility.hasValue(idval)){
						// check if we have the record by id
						String sql="select "+xf.sqlname+" from "+xt.tableName+" where " + xf.sqlname + " = " + idval ;
						Object idResult=null;
						log(Level.DEBUG,"SqlGenerator.generateCurrentTableSql() query: "+sql);
						
						try{
							idResult=dao.executeQuery(sql);
							queryCount++;
						}catch(Exception e){
							this.log(Level.ERROR,"SqlGenerator.generateCurrentTableSql() Exception on query:"+sql);
							this.log(Level.ERROR,e.getMessage());
							throw new RuntimeException(e);
						}

						if(null != idResult){
							Collection results = (Collection)idResult;
							if(CollectionUtility.hasValue(results)){
								for(Object object : results){
									idValue=TypeConverter.convertToLong(object);
								}
							}
						}
						
					}
					break;
				}
			}

			if(!LongUtility.hasValue(idValue)){
				// do an insert on the final join table
				// create the table record
				String insertSql=XmlTransferUtil.createInsertSql(xt, xmlObject, dao.isOracleDialect());
				
				// add insert sql to queue
				sqls.add(insertSql);
				
				try{
					this.log(Level.DEBUG,"SqlGenerator.generateCurrentTableSql() insert query: "+insertSql);
					dao.executeUpdate(insertSql);
					insertCount++;
					this.tablesUpdatedMappings.addTableId(xt.tableName, idValue);
				}catch(Exception e){
					this.log(Level.ERROR,"SqlGenerator.generateCurrentTableSql() Exception on insert:"+insertSql);
					this.log(Level.ERROR,e.getMessage());
					throw new RuntimeException(e);
				}
				
				// after insert, determine if there is some manual cleanup
				if(tableHandlerRuleFactory.hasPostInsertRule(xt)==true){
					TableHandler tableHandler=tableHandlerRuleFactory.getTableHandler(dao, xmlObject, xt, this.dialogueVo);
					tableHandler.setRunMode(super.runMode);
					tableHandler.doPostInsertProcesses();
				}
				
			}
		}else{
			idValue=tiMappings.getIdByTransferableIdentity(xt.tableName, ti);
			
			if(!LongUtility.hasValue(idValue)){
				
				// if we havn't already resolved the id for the i
				// , then query and get id value from the table by reading for transferableIdentity value 
				
				String sql = "SELECT ID FROM "+xt.tableName+" WHERE TRANSFERABLE_IDENTITY = '"+ti+"' ";

				Object idResult=null;
				log(Level.DEBUG,"SqlGenerator.generateCurrentTableSql() query: "+sql);
				
				try{
					idResult=dao.executeQuery(sql);
					queryCount++;
				}catch(Exception e){
					this.log(Level.ERROR,"SqlGenerator.generateCurrentTableSql() Exception on query:"+sql);
					this.log(Level.ERROR,e.getMessage());
					throw new RuntimeException(e);
				}

				// extract idValue from query result
				if(null != idResult){
					Collection results = (Collection)idResult;
					if(CollectionUtility.hasValue(results)){
						for(Object object : results){
							idValue=TypeConverter.convertToLong(object);
							tiMappings.addTransferableIdentityId(xt.tableName, ti, idValue);
						}
					}
				}
			}
			
			/*
			 * If we have the id value for the table
			 * then use the id value.
			 * If there is no id value for the table
			 * then create the id and the table record
			 */
			if(LongUtility.hasValue(idValue)){
				
				// check if this table record has already been updated
				if(this.tablesUpdatedMappings.hasTableBeenUpdated(xt.tableName, idValue)==false){
					if(xt.tableName.equals("isw_assign_time_post")){
						//System.out.println("");
					}
					
					
					// do an update stmt
					String updateSql = XmlTransferUtil.createUpdateSql(xt, xmlObject, dao.isOracleDialect());

					if(StringUtility.hasValue(updateSql)){
						sqls.add(updateSql);
						
						try{
							this.log(Level.DEBUG,"SqlGenerator.generateCurrentTableSql() update query: "+updateSql);
							dao.executeUpdate(updateSql);
							updateCount++;
							this.tablesUpdatedMappings.addTableId(xt.tableName, idValue);
						}catch(Exception e){
							this.log(Level.ERROR,"SqlGenerator.generateCurrentTableSql() Exception on update:"+updateSql);
							this.log(Level.ERROR,e.getMessage());
							throw new RuntimeException(e);
						}
					}
					
				}
				
				// set the field setter method with the idValue
				XmlTransferUtil.invokeSetMethod(xmlObject, "Id", idValue, "LONG");
				
				// after update, determine if there is some manual cleanup
				if(tableHandlerRuleFactory.hasPostUpdateRule(xt)==true){
					TableHandler tableHandler=tableHandlerRuleFactory.getTableHandler(dao, xmlObject, xt, this.dialogueVo);
					tableHandler.setRunMode(super.runMode);
					tableHandler.setContext(this.context);
					tableHandler.setFromWebServlet(this.fromWebServlet);
					tableHandler.setFromWebServletUserId(this.fromWebServletUserId);
					tableHandler.setDataStewardUserId(this.dataStewardUserId);
					tableHandler.doPostUpdateProcesses();
				}
				
			}else{
				// get next id sequence
				idValue=this.getNextSequence(XmlTransferUtil.getSequenceName(xt));
				
				// set the new idValue in the xmlObject
				XmlTransferUtil.invokeSetMethod(xmlObject, "Id", idValue, "LONG");
				
				// create the table record
				String insertSql=XmlTransferUtil.createInsertSql(xt, xmlObject, dao.isOracleDialect());
				
				// add insert sql to queue
				sqls.add(insertSql);
				
				try{
					this.log(Level.DEBUG,"SqlGenerator.generateCurrentTableSql() insert query: "+insertSql);
					dao.executeUpdate(insertSql);
					insertCount++;
					this.tablesUpdatedMappings.addTableId(xt.tableName, idValue);
				}catch(Exception e){
					this.log(Level.ERROR,"SqlGenerator.generateCurrentTableSql() Exception on insert:"+insertSql);
					this.log(Level.ERROR,e.getMessage());
					throw new RuntimeException(e);
				}
				
				// after insert, determine if there is some manual cleanup
				if(tableHandlerRuleFactory.hasPostInsertRule(xt)==true){
					TableHandler tableHandler=tableHandlerRuleFactory.getTableHandler(dao, xmlObject, xt, this.dialogueVo);
					tableHandler.setRunMode(super.runMode);
					tableHandler.setContext(this.context);
					tableHandler.setFromWebServlet(this.fromWebServlet);
					tableHandler.setFromWebServletUserId(this.fromWebServletUserId);
					tableHandler.setDataStewardUserId(this.dataStewardUserId);
					tableHandler.doPostInsertProcesses();
				}
			}

		}
		
	}
	
	public void generateCurrentTableJoinSql(Object xmlObject,XmlTable xt,Collection<String> sqls) throws Exception {
		log(Level.DEBUG,"SqlGenerator.generateCurrentTableJoinSql() "+xt.tableName);

		XmlField xfJoinKeyPrimary=xt.getXmlFieldJoinKeyPrimary();
		XmlField xfJoinKeySecondary=xt.getXmlFieldJoinKeySecondary();
		
		Long primaryKey=(Long)XmlTransferUtil.invokeGetMethod(xmlObject, xfJoinKeyPrimary.name);
		Long secondaryKey=(Long)XmlTransferUtil.invokeGetMethod(xmlObject, xfJoinKeySecondary.name);
		
		// query and get id value from the table by reading for both primary/secondary values 
		String sql = "SELECT "+xfJoinKeyPrimary.sqlname+","+xfJoinKeySecondary.sqlname+
					 " FROM "+xt.tableName+
					 " WHERE "+xfJoinKeyPrimary.sqlname+" = "+primaryKey+" " +
					 " AND "+xfJoinKeySecondary.sqlname+" = "+secondaryKey+" ";
		
		Object idResult=null;
		log(Level.DEBUG,"SqlGenerator.generateCurrentTableJoinSql() query : "+sql);
		
		try{
			idResult=dao.executeQuery(sql);
			queryCount++;
		}catch(Exception e){
			this.log(Level.ERROR,"SqlGenerator.generateCurrentTableJoinSql() Exception on query:"+sql);
			this.log(Level.ERROR,e.getMessage());
			throw new RuntimeException(e);
		}
		
		// extract idValue from query result
		Long primaryFieldValue=0L;
		Long secondaryFieldValue=0L;
		if(null != idResult){
			Collection results = (Collection)idResult;
			if(CollectionUtility.hasValue(results)){
				for(Object object : results){
					Object[] list = (Object[])object;
					int i=0;
					for(Object o : list){
						switch(i)
						{
							case 0:
								primaryFieldValue=TypeConverter.convertToLong(o);
								break;
							case 1:
								secondaryFieldValue=TypeConverter.convertToLong(o);
								break;
						}
						i++;
					}
				}
			}
		}
		
		/*
		 * If we have the id value for the table
		 * then use the id value.
		 * If there is no id value for the table
		 * then create the id and the table record
		 */
		if(LongUtility.hasValue(primaryFieldValue)){
			// do an update stmt
			
		}else{
			
			// create the table record
			String insertSql=XmlTransferUtil.createInsertSql(xt, xmlObject, dao.isOracleDialect());
			
			// add insert sql to queue
			sqls.add(insertSql);
			try{
				log(Level.DEBUG,"SqlGenerator.generateCurrentTableJoinSql() insert query : "+insertSql);
				dao.executeUpdate(insertSql);
				insertCount++;
				//System.out.println(insertCount);
			}catch(Exception e){
				this.log(Level.ERROR,"SqlGenerator.generateCurrentTableJoinSql() Exception on insert:"+insertSql);
				this.log(Level.ERROR,e.getMessage());
				throw new RuntimeException(e);
			}
		}
		
	}

}
