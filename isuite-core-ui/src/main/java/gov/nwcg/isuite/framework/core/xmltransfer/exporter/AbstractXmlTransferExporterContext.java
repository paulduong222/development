package gov.nwcg.isuite.framework.core.xmltransfer.exporter;

import gov.nwcg.isuite.framework.core.xmltransfer.AbstractXmlTransfer;
import gov.nwcg.isuite.framework.core.xmltransfer.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransfer.data.XmlTable;
import gov.nwcg.isuite.framework.types.DataTransferTypeEnum;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.safehaus.uuid.UUID;
import org.safehaus.uuid.UUIDGenerator;

public class AbstractXmlTransferExporterContext extends AbstractXmlTransfer{

	/*
	 * NOTE: Important to make sure each table does not have duplicate uuid's.
	 * 		 The same uuid can exist in different tables, but not in the same table.
	 */
	protected String generateUuid(String table){
		UUID uuid=UUIDGenerator.getInstance().generateNameBasedUUID(
					UUIDGenerator.getInstance().generateTimeBasedUUID(), table+Calendar.getInstance().getTimeInMillis());
		return uuid.toString();
	}
	
	protected String generateSqlQueryUuid(XmlTable xmlTable, String lookupname, Object lookupvalue) {
		StringBuffer sql = new StringBuffer();

		XmlField xfLookup=xmlTable.getXmlFieldByName(lookupname);
		
		if(xmlTable.hasPrimaryKeyField()==true){
			sql.append("SELECT id, transferable_identity ")
				.append("FROM "+xmlTable.tableName + " ")
				.append("WHERE "+xfLookup.sqlname+" = " + lookupvalue);
		}else{
			XmlField primaryField=xmlTable.getXmlFieldJoinKeyPrimary();
			XmlField secondaryField=xmlTable.getXmlFieldJoinKeySecondary();
			sql.append("SELECT 0 as column1,transferable_identity,"+primaryField.sqlname+","+secondaryField.sqlname+" ")
				.append("FROM "+xmlTable.tableName + " ")
				.append("WHERE "+xfLookup.sqlname+" = " + lookupvalue);
		}
		
		return sql.toString();
	}
	
	protected String generateSqlUpdateUuid(XmlTable xmlTable, String primaryKeyField, Object primaryKeyValue, String uuid) {
		StringBuffer sql = new StringBuffer();

		sql.append("UPDATE " + xmlTable.tableName + " " )
			.append("SET transferable_identity = '"+uuid +"' ")
			.append("WHERE "+primaryKeyField+" = " + primaryKeyValue);
		
		return sql.toString();
	}

	/**
	 * @param cls
	 * @param xmlFields
	 * @return
	 */
	protected String generateSql(XmlTable xmlTable, String lookupfield, Object lookupvalue) {
		StringBuffer sql = new StringBuffer();

		if(xmlTable.tableName.equals("isw_incident_account_code")){
			System.out.println("");
		}
		
		sql.append("SELECT ");
		
		// loop through each field and add to query if type is not complex
		int cnt=0;
		
		for(XmlField xmlField : xmlTable.xmlFields){
			if(StringUtility.hasValue(xmlField.type) && !xmlField.type.equals("COMPLEX") && xmlField.disjoined==false){
				if(xmlField.type.equals("DATE")){
					String sqlfield="to_char("+xmlField.sqlname+",'MM/DD/YYYY HH24:MI:SS') as "+xmlField.sqlname;
					if(cnt==0){
						sql.append(sqlfield);
						cnt++;
					}else{
						sql.append(", " + sqlfield);
					}
				}else{
					if(cnt==0){
						sql.append(xmlField.sqlname);
						cnt++;
					}else{
						sql.append(", " + xmlField.sqlname);
					}
				}
			}
			if(StringUtility.hasValue(xmlField.type) && xmlField.disjoined==true){
				String subSql="("+
								"select "+xmlField.disjoinedfield+" as "+xmlField.alias+"  from "+xmlField.disjoinedtable+" where "+
								"id="+xmlField.disjoinedsource;
				
				subSql=subSql+")";
				if(cnt==0){
					sql.append(subSql);
					cnt++;
				}else{
					sql.append(", " + subSql);
				}
			}
		}
		
		sql.append(" ");
		sql.append("FROM " + xmlTable.tableName.toUpperCase() + " ");
		
		XmlField xfLookupField=xmlTable.getXmlFieldByName(lookupfield);
		
		sql.append("WHERE " + xfLookupField.sqlname + " = " );
		
		if(lookupvalue instanceof String){
			// todo
		}else if(lookupvalue instanceof Long){
			sql.append(lookupvalue);
		}

		if(StringUtility.hasValue(xmlTable.filter)){
			sql.append(xmlTable.filter);
		}
			
		if(StringUtility.hasValue(xmlTable.orderby)){
			sql.append(" "+xmlTable.orderby);
		}
		
		return sql.toString();
	}
	
	protected Collection<XmlField> populateXmlTableFieldValues(XmlTable xt, Object[] list) throws Exception {
		int cnt=0;
		
		Collection<XmlField> newXmlFields = new ArrayList<XmlField>();

		try{
			for(XmlField xf : xt.xmlFields){
				if(StringUtility.hasValue(xf.type) && !xf.type.equals("COMPLEX")){
					XmlField newXmlField = xf;
					int i=0;
					for(Object o : list){
						if(i==cnt){
							DataTransferTypeEnum dtte=DataTransferTypeEnum.getByName(xf.type);
							switch(dtte.type)
							{
								case 0:  // string
									String sval=TypeConverter.convertToString(o);
									if(StringUtility.hasValue(sval)){
										sval=sval.replaceAll("&", "&amp;");
										sval=sval.replaceAll("'", "&apos;");
										sval=sval.replaceAll("<", "&lt;");
										sval=sval.replaceAll(">", "&gt;");
									}else
										sval="";
									newXmlField.value=sval;
									break;
								case 1:  // integer
									newXmlField.value=TypeConverter.convertToInteger(o);
									break;
								case 2:  // bigdecimal
									newXmlField.value=TypeConverter.convertToBigDecimal(o);
									break;
								case 3:  // long
									newXmlField.value=TypeConverter.convertToLong(o);
									break;
								case 4:  // date
									newXmlField.value=o;
									break;
								case 5:  // short
									String s = TypeConverter.convertToString(o);
									if(StringUtility.hasValue(s))
										newXmlField.value=Short.valueOf(s);
									break;
								case 6:  // boolean
									newXmlField.value=TypeConverter.convertToBoolean(o);
									break;
								case 7:  // timestamp
									newXmlField.value=TypeConverter.convertToTimestamp(o);
									break;
							}
							break;
						}
						i++;
					}
					newXmlFields.add(newXmlField);
					cnt++;
				}else{
					newXmlFields.add(xf);
				}
			}
		}catch(Exception e){
			throw e;
		}
		
		return newXmlFields;
	}

	protected Object getLookupValue(XmlField xf, Collection<XmlField> xmlFields){
		Object object = null;
		
		for(XmlField f : xmlFields){
			if(f.name.equals(xf.sourcename))
				object=f.value;
		}
		
		return object;
	}
	
	protected String indent(int level) {
		String rtn="";
		
		for(int i=0;i<level;i++){
			rtn=rtn+" ";
		}
		
		return rtn;
	}
	
	
}
