package gov.nwcg.isuite.framework.core.xmltransfer.data;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.util.StringUtility;

public class XmlField {
	public boolean primaryKey=false;
	public String sequence="";
	public String name="";
	public String sqlname="";
	public String type="";
	public Class target=null;
	public boolean derived=false;
	public String derivedfield="";
	public String lookupname="";
	public String sourcename="";
	public boolean cascade=false;
	public boolean disjoined=false;
	public String disjoinedtable="";
	public String disjoinedfield="";
	public String disjoinedsource="";
	public boolean nullwhenempty=false;
	public boolean joinkeyprimary=false;
	public boolean joinkeysecondary=false;
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
		xf.derived=source.derived;
		xf.derivedfield=source.derivedfield;
		xf.lookupname=source.lookupname;
		xf.sourcename=source.sourcename;
		xf.cascade=source.cascade;
		xf.disjoined=source.disjoined;
		xf.disjoinedtable=source.disjoinedtable;
		xf.disjoinedfield=source.disjoinedfield;
		xf.disjoinedsource=source.disjoinedsource;
		xf.value=source.value;
		xf.nullwhenempty=source.nullwhenempty;
		xf.joinkeyprimary=source.joinkeyprimary;
		xf.joinkeysecondary=source.joinkeysecondary;
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
		f.derived=xtf.derived();
		f.derivedfield=xtf.derivedfield();
		f.lookupname=xtf.lookupname();
		f.sourcename=xtf.sourcename();
		f.cascade=xtf.cascade();
		f.disjoined=xtf.disjoined();
		f.disjoinedtable=xtf.disjoinedtable();
		f.disjoinedfield=xtf.disjoinedfield();
		f.disjoinedsource=xtf.disjoinedsource();
		f.nullwhenempty=xtf.nullwhenempty();
		f.joinkeyprimary=xtf.joinkeyprimary();
		f.joinkeysecondary=xtf.joinkeysecondary();
		f.updateable=xtf.updateable();
		f.alias=xtf.alias();
		f.defaultvalue=xtf.defaultvalue();
		f.ischardata=xtf.ischardata();
		f.filter=xtf.filter();
		
		if(f.name.equals("Id") || f.name.equals("TransferableIdentity") 
				|| f.type.equals("COMPLEX") || f.disjoined==true)
			f.updateable=false;
		
		return f;
	}
	
	public Boolean isComplex(){
		if(StringUtility.hasValue(type) && type.equals("COMPLEX"))
			return true;
		else
			return false;
	}
}
