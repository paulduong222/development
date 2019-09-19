package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswIncidentAccountCode", table="isw_incident_account_code"
	, filterincident=" incident_id in (:INCIDENTID) ")
public class IswIncidentAccountCode  {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCIDENT_ACCOUNT_CODE", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="DEFAULT_FLG", type="BOOLEAN")
	private Boolean a;

	@XmlTransferField(name = "B", sqlname="ACCRUAL_ACCOUNT_CODE", type="STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname="INCIDENT_ID", type="STRING"
		, derivedtable="isw_incident")
	private String c;
	
	@XmlTransferField(name = "D", sqlname="ACCOUNT_CODE_ID", type="STRING",derivedtable="isw_account_code")
	private String d;
	
	@XmlTransferField(name = "E", sqlname="OVERRIDE_ACCOUNT_CODE_ID", type="STRING",derivedtable="isw_account_code")
	private String e;

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

	public Boolean getA() {
		return a;
	}

	public void setA(Boolean a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
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

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

}
