package gov.nwcg.isuite.framework.core.xmltransfer.data;

import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;



public class XmlTable {
	public String nodeName;
	public String tableName;
	public boolean jointable=false;
	public boolean finaltable=false;
	public String orderby="";
	public String filter="";
	public Class cls;
	public Collection<XmlField> xmlFields = new ArrayList<XmlField>();
	public Collection<XmlTable> nestedTables = new ArrayList<XmlTable>();
	
	public static XmlTable copy(XmlTable source){
		XmlTable xt = new XmlTable();
		xt.nodeName=source.nodeName;
		xt.tableName=source.tableName;
		xt.jointable=source.jointable;
		xt.finaltable=source.finaltable;
		xt.orderby=source.orderby;
		xt.cls=source.cls;
		xt.filter=source.filter;
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
			XmlField tiField=this.getXmlFieldByName("TransferableIdentity");
			if(null != tiField && null != tiField.value)
				ti=(String)tiField.value;
		}
		
		return ti;
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
	
	public Boolean hasJoinTables(){
		Boolean rtn=false;
		
		for(XmlField f : this.xmlFields){
			if(StringUtility.hasValue(f.type) && f.type.equals("COMPLEX") && f.disjoined==false){
				rtn=true;
				break;
			}
		}
		return rtn;
	}
	
	public Collection<XmlField> getDerivedTables(){
		Collection<XmlField> list = new ArrayList<XmlField>();

		for(XmlField f : this.xmlFields){
			if(f.derived==true){
				list.add(f);
			}
		}
		
		return list;
	}

	public Collection<XmlField> getCascadeTables(){
		Collection<XmlField> list = new ArrayList<XmlField>();

		for(XmlField f : this.xmlFields){
			if(f.cascade==true){
				list.add(f);
			}
		}
		
		return list;
	}

	public Collection<XmlField> getDisjoinedFields(){
		Collection<XmlField> list = new ArrayList<XmlField>();

		for(XmlField f : this.xmlFields){
			if(f.disjoined==true){
				list.add(f);
			}
		}
		
		return list;
	}
	
	public XmlField getXmlFieldByName(String name){
		for(XmlField f : this.xmlFields){
			if(f.name.equals(name)){
				return f;
			}
		}
		return null;
	}
	
	public XmlField getXmlFieldJoinKeyPrimary(){
		for(XmlField f : this.xmlFields){
			if(f.joinkeyprimary==true)
				return f;
		}
		return null;
	}
	
	public XmlField getXmlFieldJoinKeySecondary(){
		for(XmlField f : this.xmlFields){
			if(f.joinkeysecondary==true)
				return f;
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
			if(f.name.equals(fieldname)){
				f.value=val;
			}
			newFields.add(f);
		}
		
		this.xmlFields=newFields;
	}
	
}
