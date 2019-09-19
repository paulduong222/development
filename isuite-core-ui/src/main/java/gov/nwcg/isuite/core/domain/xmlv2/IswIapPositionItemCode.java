package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswIapPositionItemCode", table="isw_iap_position_item_code"
	, filterincident=" incident_id in (:INCIDENTID) ", filtergroup=" incident_group_id = :INCIDENTGROUPID ")
public class IswIapPositionItemCode {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_POSITION_ITEM_CODE", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="INCIDENT_ID", type="STRING",updateable=false
			,derivedtable="isw_incident")
	private String a;
	
	@XmlTransferField(name = "B", sqlname="INCIDENT_GROUP_ID", type="STRING",updateable=false
			,derivedtable="isw_incident_group")
	private String b;

	@XmlTransferField(name = "C", sqlname="POSITION", type="STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname="FORM", type="STRING")
	private String d;
	
	@XmlTransferField(name = "E", sqlname="SECTION", type="STRING")
	private String e;

	@XmlTransferField(name = "F", sqlname="KIND_ID", type="STRING",updateable=false
			,derivedtable="iswl_kind")
	private String f;
	
	@XmlTransferField(name = "G", sqlname="AGENCY_ID", type="STRING",updateable=false
			,derivedtable="iswl_agency")
	private String g;
	
	public Long getId() {
		return id;
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

	public String getC() {
		return c;
	}

	public void setC(String c) {
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

	public String getG() {
		return g;
	}

	public void setG(String g) {
		this.g = g;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
