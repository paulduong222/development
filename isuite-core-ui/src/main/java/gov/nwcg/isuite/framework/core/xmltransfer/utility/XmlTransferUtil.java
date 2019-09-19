package gov.nwcg.isuite.framework.core.xmltransfer.utility;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;
import gov.nwcg.isuite.framework.core.xmltransfer.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransfer.data.XmlTable;
import gov.nwcg.isuite.framework.types.DataTransferTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class XmlTransferUtil {

	public static XmlTable getXmlTableDefinition(Class cls) {
		XmlTable xmlTable = new XmlTable();
		
		for(Annotation annotation : cls.getDeclaredAnnotations()){
			if(annotation instanceof XmlTransferTable){
				XmlTransferTable xtt = (XmlTransferTable)annotation;
				xmlTable.cls=cls;
				xmlTable.nodeName=xtt.name();
				xmlTable.tableName=xtt.table();
				xmlTable.jointable=xtt.jointable();
				xmlTable.finaltable=xtt.finaltable();
				xmlTable.orderby=xtt.orderby();
				xmlTable.filter=xtt.filter();
				return xmlTable;
			}
		}
		
		return null;
	}
	
	public static Collection<XmlField> getXmlFields(Class cls) {
		Collection<XmlField> fields = new ArrayList<XmlField>();
		
		for(Field field : cls.getDeclaredFields()){
			for(Annotation fa : field.getAnnotations()){
				if(fa instanceof XmlTransferField){
					XmlTransferField xtf = (XmlTransferField)fa;
					XmlField xmlField = XmlField.getInstance(xtf);
					fields.add(xmlField);
				}
			}
		}
		
		return fields;
	}

	public static String getSequenceName(XmlTable xmlTable){
		String name="";
		
		for(XmlField xf : xmlTable.xmlFields){
			if(xf.primaryKey==true){
				name=xf.sequence;
			}
		}
		
		return name;
	}
	
	public static Object invokeGetMethod(Object object, String propertyName) throws Exception {
		Object rtnObject=null;
		
		Method methods[]=object.getClass().getDeclaredMethods();
		for(Method m : methods){
			if(m.getName().equals("get"+propertyName)){
				rtnObject=m.invoke(object);
				break;
			}
		}
		return rtnObject;
	}
	
	public static void invokeSetMethod(Object object, String propertyName, Object value, String type) throws Exception {
		
		Method methods[]=object.getClass().getDeclaredMethods();
		for(Method m : methods){
			if(m.getName().equals("set"+propertyName)){
				if(type.equals("LONG"))
					m.invoke(object, (Long)value);
				if(type.equals("STRING"))
					m.invoke(object,(String)value);
				break;
			}
		}

	}
	
	public static String createInsertSql(XmlTable xtTable, Object xmlObject, Boolean isOracle) throws Exception {
		String sql="INSERT INTO " + xtTable.tableName + " ";
		String fieldNames="";
		String fieldValues="";
		
		int i=0;
		for(XmlField xf : xtTable.xmlFields){
			if(!xf.type.equals("COMPLEX") 
					&& xf.disjoined==false
						&& xf.ischardata==false){
				if(i==0)
					fieldNames=xf.sqlname;
				else
					fieldNames=fieldNames+","+xf.sqlname;
				
				Object o=invokeGetMethod(xmlObject,xf.name);

				if(xf.name.equals("IapResourceToDisplayFrom")){
					//System.out.println("");
				}
				DataTransferTypeEnum dtte=DataTransferTypeEnum.getByName(xf.type);
				switch(dtte.type)
				{
					case 0:  // string
						String val=TypeConverter.convertToString(o);
						if(!StringUtility.hasValue(val))
							val="";
						
						if(xf.nullwhenempty==true){
							if(!StringUtility.hasValue(val)){
								val=null;
							}
						}
						
						if(StringUtility.hasValue(xf.defaultvalue)){
							if(!StringUtility.hasValue(val)){
								val=xf.defaultvalue;
							}
						}
						
						if(StringUtility.hasValue(val)){
							// need to use double single quote to save ' in string value
							val=val.replaceAll("&apos;", "''");
							val=val.replaceAll("'", "''");
							val=val.replaceAll("\"", "''");
							val=val.replaceAll("&lt;", "<");
							val=val.replaceAll("&gt;", ">");
							val=val.replaceAll("&amp;", "&");
						}
						
						if(i==0){
							if(val!=null)
								fieldValues="'"+val+"'";
							else
								fieldValues=""+val+"";
						}else{
							if(val!=null)
								fieldValues=fieldValues+",'"+val+"'";
							else
								fieldValues=fieldValues+","+val+"";
						}
						break;
					case 1:  // integer
						if(i==0)
							fieldValues=fieldValues+TypeConverter.convertToInteger(o);
						else
							fieldValues=fieldValues+","+TypeConverter.convertToInteger(o)+"";
						break;
					case 2:  // bigdecimal
						if(i==0)
							fieldValues=fieldValues+TypeConverter.convertToBigDecimal(o);
						else
							fieldValues=fieldValues+","+TypeConverter.convertToBigDecimal(o)+"";
						break;
					case 3:  // long
						if(i==0){
							Long lng=TypeConverter.convertToLong(o);
							if(LongUtility.hasValue(lng))
								fieldValues=fieldValues+lng;
							else
								fieldValues=fieldValues+null;
						}else{
							Long lng=TypeConverter.convertToLong(o);
							if(LongUtility.hasValue(lng))
								fieldValues=fieldValues+","+lng+"";
							else
								fieldValues=fieldValues+","+null+"";
						}
						break;
					case 4:  // date
						String sdt="";
						if(null != o){
							Date dt=(Date)o;
							sdt=DateUtil.toDateString(dt, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS);
							if(i==0)
								fieldValues=fieldValues+"to_timestamp('"+sdt+"','MM/DD/YYYY HH24:MI:SS')";
							else
								fieldValues=fieldValues+","+"to_timestamp('"+sdt+"','MM/DD/YYYY HH24:MI:SS')";
						}else{
							if(i==0)
								fieldValues=fieldValues+null;
							else
								fieldValues=fieldValues+","+null+"";
						}
						break;
					case 5:  // short
						String s = TypeConverter.convertToString(o);
						Short sh=null;
						if(StringUtility.hasValue(s))
							sh=Short.valueOf(s);
						else{
							if(StringUtility.hasValue(xf.defaultvalue)){
								sh=Short.valueOf(xf.defaultvalue);
							}
						}
						
						if(i==0)
							fieldValues=fieldValues+sh;
						else
							fieldValues=fieldValues+","+sh+"";
						break;
					case 6:  // boolean
						Boolean bval=false;
						if(null != o){
							String b=String.valueOf(o);
							if(b.equals("1") || b.equalsIgnoreCase("TRUE"))
								bval=true;
						}
						if(bval==true){
							if(i==0)
								fieldValues=fieldValues+(isOracle==true?1:true)+"";
							else
								fieldValues=fieldValues+","+(isOracle==true?1:true)+"";
						}else{
							if(i==0)
								fieldValues=fieldValues+(isOracle==true?0:false)+"";
							else
								fieldValues=fieldValues+","+(isOracle==true?0:false)+"";
							
						}
						break;
					case 7:  // timestamp
						if(i==0)
							fieldValues=fieldValues+null+"";
						else
							fieldValues=fieldValues+","+null+"";
						break;
				}
				
				i++;
			}
		}
		
		// fields
		sql=sql+"("+fieldNames+") ";
		
		// values
		sql=sql+" VALUES ("+fieldValues+") ";
		
		return sql;
	}
	
	public static String createUpdateSql(XmlTable xtTable, Object xmlObject, Boolean isOracle) throws Exception {
		if(xtTable.hasUpdateableFields()==false){
			return "";
		}
		
		String sql="UPDATE " + xtTable.tableName + " ";
		sql=sql+"SET ";
		String fieldUpdates="";

		String tiValue="";
		
		int i=0;
		for(XmlField xf : xtTable.xmlFields){
			if(xf.name.equals("TransferableIdentity")){
				Object ti=invokeGetMethod(xmlObject,xf.name);
				tiValue=TypeConverter.convertToString(ti);
			}
			
			/* only uupdate fields not linked to other tables */
			if(xf.updateable==true && xf.ischardata==false){
				
				if(i==0)
					fieldUpdates=xf.sqlname+"=";
				else
					fieldUpdates=fieldUpdates+","+xf.sqlname+"=";
				
				Object o=invokeGetMethod(xmlObject,xf.name);

				DataTransferTypeEnum dtte=DataTransferTypeEnum.getByName(xf.type);
				switch(dtte.type)
				{
					case 0:  // string
						String val=TypeConverter.convertToString(o);
						
						if(StringUtility.hasValue(val)){
							// need to use double single quote to save ' in string value
							val=val.replaceAll("&apos;", "''");
							val=val.replaceAll("'", "''");
							val=val.replaceAll("\"", "''");
							val=val.replaceAll("&lt;", "<");
							val=val.replaceAll("&gt;", ">");
							val=val.replaceAll("&amp;", "&");
						}

						if(StringUtility.hasValue(xf.defaultvalue)){
							if(!StringUtility.hasValue(val)){
								val=xf.defaultvalue;
							}
						}
						
						if(!StringUtility.hasValue(val))
							val="";

						
						if(xf.nullwhenempty==true){
							if(!StringUtility.hasValue(val)){
								val=null;
							}
						}
						if(val!=null)
							fieldUpdates=fieldUpdates+"'"+val+"'";
						else
							fieldUpdates=fieldUpdates+""+val+"";
						
						if(xf.sqlname.equals("TRANSFERABLE_IDENTITY"))
							tiValue=val;
						break;
					case 1:  // integer
						Integer ival=TypeConverter.convertToInteger(o);
						fieldUpdates=fieldUpdates+ival;
						break;
					case 2:  // bigdecimal
						BigDecimal bd=TypeConverter.convertToBigDecimal(o);
						fieldUpdates=fieldUpdates+bd;
						break;
					case 3:  // long
						Long lng=TypeConverter.convertToLong(o);
						if(!LongUtility.hasValue(lng)){
							fieldUpdates=fieldUpdates+null;
						}else
							fieldUpdates=fieldUpdates+lng;
						break;
					case 4:  // date
						String sdt="";
						if(null != o){
							Date dt=(Date)o;
							sdt=DateUtil.toDateString(dt, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS);
							fieldUpdates=fieldUpdates+"to_timestamp('"+sdt+"','MM/DD/YYYY HH24:MI:SS')";
						}else{
							fieldUpdates=fieldUpdates+null;
						}
						break;
					case 5:  // short
						String s = TypeConverter.convertToString(o);
						Short sh=null;
						if(StringUtility.hasValue(s))
							sh=Short.valueOf(s);
						else{
							if(StringUtility.hasValue(xf.defaultvalue)){
								sh=Short.valueOf(xf.defaultvalue);
							}
						}
						fieldUpdates=fieldUpdates+sh;
						
						break;
					case 6:  // boolean
						Boolean bval=false;
						if(null != o){
							String b=String.valueOf(o);
							if(b.equals("1") || b.equalsIgnoreCase("TRUE"))
								bval=true;
						}
						if(bval==true){
							fieldUpdates=fieldUpdates+(isOracle==true?1:true)+"";
						}else{
							fieldUpdates=fieldUpdates+(isOracle==true?0:false)+"";
						}
						break;
					case 7:  // timestamp
						fieldUpdates=fieldUpdates+null+"";
						break;
				}
				
				i++;
			}
		}
		
		// fields
		sql=sql+fieldUpdates;
		sql=sql+" WHERE transferable_identity = '"+tiValue+"'";
		
		return sql;
	}
}
