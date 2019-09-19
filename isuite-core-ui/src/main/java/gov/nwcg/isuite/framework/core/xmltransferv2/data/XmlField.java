package gov.nwcg.isuite.framework.core.xmltransferv2.data;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.util.StringUtility;

public class XmlField {
	public boolean primaryKey=false;
	public String sequence="";
	public String name="";
	public String sqlname="";
	public String type="";
	public Class target=null;
	public String derivedtable="";
	public boolean nullwhenempty=false;
	public boolean updateable=true;
	public String alias="";
	public String defaultvalue="";
	public boolean ischardata=false;
	public String filter="";
	
	public Object value;
	
	public static XmlField copy(XmlField source){
		XmlField xf = new XmlField();
		xf.primaryKey=source.primaryKey;
		xf.sequence=source.sequence;
		xf.name=source.name;
		xf.sqlname=source.sqlname;
		xf.type=source.type;
		xf.target=source.target;
		xf.derivedtable=source.derivedtable;
		xf.value=source.value;
		xf.nullwhenempty=source.nullwhenempty;
		xf.updateable=source.updateable;
		xf.alias=source.alias;
		xf.defaultvalue=source.defaultvalue;
		xf.ischardata=source.ischardata;
		xf.filter=source.filter;
		return xf;
	}
	
	public static XmlField getInstance(XmlTransferField xtf) {
		XmlField f = new XmlField();
		f.primaryKey=xtf.primarykey();
		f.sequence=xtf.sequence();
		f.name=xtf.name();
		f.sqlname=xtf.sqlname();
		f.type=xtf.type();
		f.target=xtf.target();
		f.derivedtable=xtf.derivedtable();
		f.nullwhenempty=xtf.nullwhenempty();
		f.updateable=xtf.updateable();
		f.alias=xtf.alias();
		f.defaultvalue=xtf.defaultvalue();
		f.ischardata=xtf.ischardata();
		f.filter=xtf.filter();
		
		if(f.name.equals("Id") || f.name.equals("TI"))
			f.updateable=false;
		
		return f;
	}
	
}
