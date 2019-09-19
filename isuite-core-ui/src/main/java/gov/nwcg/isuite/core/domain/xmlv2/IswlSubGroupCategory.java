package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswlSubGroupCategory", table="iswl_sub_group_category")
public class IswlSubGroupCategory {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_SUB_GROUP_CATEGORY", type="LONG")
	private Long id = 0L;
   
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="CODE", type="STRING")
	private String a;
   
	@XmlTransferField(name = "B", sqlname="DESCRIPTION", type="STRING")
	private String b;
   
	@XmlTransferField(name = "C", sqlname="IS_STANDARD", type="BOOLEAN")
	private Boolean c;

   
	@XmlTransferField(name = "D", sqlname = "IS_ACTIVE", type="STRING")
	private String d;

	@XmlTransferField(name = "E", sqlname="INCIDENT_ID", type="STRING",updateable=false
			,derivedtable="isw_incident")
	private String e;

	@XmlTransferField(name = "F", sqlname="INCIDENT_GROUP_ID", type="STRING",updateable=false
			,derivedtable="isw_incident_group")
	private String f;
	
	public IswlSubGroupCategory() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTI() {
		return tI;
	}

	public void setTI(String ti) {
		tI = ti;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public Boolean getC() {
		return c;
	}

	public void setC(Boolean c) {
		this.c = c;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}

}
