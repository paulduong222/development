package gov.nwcg.isuite.framework.core.xmltransferv2.exporter;

import gov.nwcg.isuite.core.persistence.DataTransferDao;
import gov.nwcg.isuite.framework.core.xmltransferv2.AbstractXmlTransfer;
import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlTable;
import gov.nwcg.isuite.framework.types.DataTransferTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.safehaus.uuid.UUID;
import org.safehaus.uuid.UUIDGenerator;

public class AbstractXmlTransferExporterContext extends AbstractXmlTransfer{
	protected DataTransferDao dao = null;

	/*
	 * NOTE: Important to make sure each table does not have duplicate uuid's.
	 * 		 The same uuid can exist in different tables, but not in the same table.
	 */
	protected String generateUuid(String table){
		UUID uuid=UUIDGenerator.getInstance().generateNameBasedUUID(
					UUIDGenerator.getInstance().generateTimeBasedUUID(), table+Calendar.getInstance().getTimeInMillis());
		return uuid.toString();
	}

	public static String generateUuid2(String table){
		UUID uuid=UUIDGenerator.getInstance().generateNameBasedUUID(
					UUIDGenerator.getInstance().generateTimeBasedUUID(), table+Calendar.getInstance().getTimeInMillis());
		return uuid.toString();
	}

	public static void main(String[] args){
		int x=0;
		for(x=0;x<16;x++){
			System.out.println(AbstractXmlTransferExporterContext.generateUuid2("iswl_sub_group_category"));
		}
	}
	
	/**
	 * @param cls
	 * @param xmlFields
	 * @return
	 */
	protected String generateSql(XmlTable xmlTable, String incidentIdList, String incidentGroupIdList,TableExportOrderEnum toe) {
		StringBuffer sql = new StringBuffer();

		if(xmlTable.tableName.equals("isw_iap_personnel_res")){
			System.out.println("");
		}
		String alias=xmlTable.alias;
		
		sql.append("SELECT ");
		
		// loop through each field and add to query
		int cnt=0;
		
		for(XmlField xmlField : xmlTable.xmlFields){
			if(StringUtility.hasValue(xmlField.type)){
					if(xmlField.type.equals("DATE")){
						String sqlfield="to_char("+xmlField.sqlname+",'MM/DD/YYYY HH24:MI:SS') ";
						if(cnt==0){
							sql.append(sqlfield +" as "+xmlField.name);
							cnt++;
						}else{
							sql.append(", " + sqlfield+" as "+xmlField.name);
						}
					}else{
						if(StringUtility.hasValue(xmlField.derivedtable)){
							if(cnt==0){
								sql.append("(select distinct(transferable_identity) from "+xmlField.derivedtable+" where id = "+(StringUtility.hasValue(alias)?alias+".":"")+xmlField.sqlname+") "+" as "+xmlField.name);
								cnt++;
							}else{
								sql.append(", (select distinct(transferable_identity) from "+xmlField.derivedtable+" where id = "+(StringUtility.hasValue(alias)?alias+".":"")+xmlField.sqlname+") "+" as "+xmlField.name);
							}
						}else{
							if(cnt==0){
								sql.append(xmlField.sqlname+" as "+xmlField.name);
								cnt++;
							}else{
								sql.append(", " + xmlField.sqlname+" as "+xmlField.name);
							}
						}
					}
			}
		}
		
		sql.append(" ");
		sql.append("FROM " + xmlTable.tableName.toUpperCase() + (StringUtility.hasValue(alias)?" "+alias:" ")+ " ");
		
		String whereClause="";
		if(StringUtility.hasValue(xmlTable.filter)){
			whereClause="where "+xmlTable.filter+ " ";
		}
		if(BooleanUtility.isTrue(toe.getFilterIncident()) 
				&& StringUtility.hasValue(xmlTable.filterincident)
				&& StringUtility.hasValue(incidentIdList)){
			String f=xmlTable.filterincident;
			if(f.indexOf(":INCIDENTID")>0){
				f=f.replaceAll(":INCIDENTID", incidentIdList);
			}
			
			if(whereClause.length()>0)
				whereClause=whereClause+" " + f + " ";
			else
				whereClause="where "+f+" ";
		}
		if(BooleanUtility.isTrue(toe.getFilterGroup()) 
				&& StringUtility.hasValue(xmlTable.filtergroup)
				&& StringUtility.hasValue(incidentGroupIdList)){
			String f=xmlTable.filtergroup;
			if(f.indexOf(":INCIDENTGROUPID")>0){
				f=f.replaceAll(":INCIDENTGROUPID", incidentGroupIdList);
			}
			if(whereClause.length()>0)
				whereClause=whereClause+" " + f + " ";
			else
				whereClause="where "+f+" ";
		}

		if(StringUtility.hasValue(whereClause)){
			whereClause=whereClause.replaceAll(":FALSE", (dao.isOracleDialect() ? "0" : "false"));
			whereClause=whereClause.replaceAll(":TRUE", (dao.isOracleDialect() ? "1" : "true"));
			whereClause=whereClause.replaceAll(":PARAM_STR_TO_NUM", (dao.isOracleDialect() ? "to_number(trim(parameter_value))" : "cast(parameter_value as bigint)"));
			sql.append(whereClause);
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

}
