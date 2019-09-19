package gov.nwcg.isuite.framework.core.xmltransferv2.data;

import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;



public class XmlTable {
	public String nodeName;
	public String tableName;
	public String orderby="";
	public String filter="";
	public String filterincident="";
	public String filtergroup="";
	public String alias="";
	public Class cls;
	public Collection<XmlField> xmlFields = new ArrayList<XmlField>();
	public Collection<XmlTable> nestedTables = new ArrayList<XmlTable>();
	
	public static XmlTable copy(XmlTable source){
		XmlTable xt = new XmlTable();
		xt.nodeName=source.nodeName;
		xt.tableName=source.tableName;
		xt.orderby=source.orderby;
		xt.cls=source.cls;
		xt.filter=source.filter;
		xt.filterincident=source.filterincident;
		xt.filtergroup=source.filtergroup;
		xt.alias=source.alias;
		xt.xmlFields = new ArrayList<XmlField>();
		for(XmlField f : source.xmlFields){
			xt.xmlFields.add(XmlField.copy(f));
		}
		xt.nestedTables=new ArrayList<XmlTable>();
		for(XmlTable nt : source.nestedTables){
			xt.nestedTables.add(XmlTable.copy(nt));
		}
		return xt;
	}

	public String getTransferableIdentity(){
		String ti="";
		
		if(this.hasTransferableIdentityField()==true){
			XmlField tiField=this.getXmlFieldByName("TI");
			if(null != tiField && null != tiField.value)
				ti=(String)tiField.value;
		}
		
		return ti;
	}

	public Long getId(){
		for(XmlField f : this.xmlFields){
			if(f.sqlname.equalsIgnoreCase("ID") && f.value != null){
				try{
					Long val=TypeConverter.convertToLong(f.value);
					return val;
				}catch(Exception e){}
			}
		}
		
		return 0L;
	}
	
	public Boolean hasPrimaryKeyField() {
		Boolean rtn=false;
		
		for(XmlField f : this.xmlFields){
			if(BooleanUtility.isTrue(f.primaryKey)){
				rtn=true;
				break;
			}
		}
		return rtn;
	}
	
	public Boolean hasTransferableIdentityField() {
		Boolean rtn=false;
		
		for(XmlField f : this.xmlFields){
			if(StringUtility.hasValue(f.sqlname) && f.sqlname.equalsIgnoreCase("TRANSFERABLE_IDENTITY")){
				rtn=true;
				break;
			}
		}
		return rtn;
	}
	
	public Boolean hasIncidentGroupIdField() {
		Boolean rtn=false;
		
		for(XmlField f : this.xmlFields){
			if(StringUtility.hasValue(f.sqlname) && f.sqlname.equalsIgnoreCase("INCIDENT_GROUP_ID")){
				rtn=true;
				break;
			}
		}
		return rtn;
	}
	
	public Boolean hasJoinTables(){
		Boolean rtn=false;
		
		for(XmlField f : this.xmlFields){
			if(StringUtility.hasValue(f.type) && f.type.equals("COMPLEX")){
				rtn=true;
				break;
			}
		}
		return rtn;
	}
	
	public XmlField getXmlFieldByName(String name){
		for(XmlField f : this.xmlFields){
			if(f.name.equals(name)){
				return f;
			}
		}
		return null;
	}
	
	public XmlField getXmlFieldBySqlName(String name){
		for(XmlField f : this.xmlFields){
			if(f.sqlname.equalsIgnoreCase(name)){
				return f;
			}
		}
		return null;
	}

	public Object getXmlFieldValueBySqlName(String name){
		for(XmlField f : this.xmlFields){
			if(f.sqlname.equalsIgnoreCase(name)){
				return f.value;
			}
		}
		return null;
	}

	public Boolean hasUpdateableFields(){
		Boolean rtn=false;
		
		for(XmlField f : this.xmlFields){
			if(f.updateable==true){
				rtn=true;
				break;
			}
		}
		return rtn;
	}
	
	public void setFieldStringValue(String fieldname, String val){
		Collection<XmlField> newFields = new ArrayList<XmlField>();
		
		for(XmlField f : this.xmlFields){
			if(f.name.equalsIgnoreCase(fieldname)){
				f.value=val;
			}
			newFields.add(f);
		}
		
		this.xmlFields=newFields;
	}
	
}
