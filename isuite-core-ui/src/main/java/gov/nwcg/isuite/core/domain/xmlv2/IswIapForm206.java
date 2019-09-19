package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswIapForm206", table="isw_iap_form_206"
	,filterincident=" iap_plan_id in (select id from isw_iap_plan where incident_id in (:INCIDENTID) ) "
	,filtergroup=" iap_plan_id in (select id from isw_iap_plan where incident_group_id in (:INCIDENTGROUPID) ) "
)
public class IswIapForm206 {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_FORM_206", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="IAP_PLAN_ID", type="STRING"
		,derivedtable="isw_iap_plan")
	private String a;

	@XmlTransferField(name = "B", sqlname="PREPARED_BY", type="STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname="PREPARED_BY_POS", type="STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname="PREPARED_DATE", type="DATE")
	private Date d;

	@XmlTransferField(name = "E", sqlname="REVIEWED_BY", type="STRING")
	private String e;

	@XmlTransferField(name = "F", sqlname="REVIEWED_DATE", type="DATE")
	private Date f;

	@XmlTransferField(name = "G", sqlname="APPROVED_BY", type="STRING")
	private String g;
	
	@XmlTransferField(name = "H", sqlname="IS_FORM_LOCKED", type="STRING")
	private String h;
	
	@XmlTransferField(name = "I", sqlname="IS_AVIATION_UTILIZED", type="STRING", defaultvalue="N")
	private String j;

	@XmlTransferField(name = "K", sqlname="MEDICAL_EMERGENCY_PROC", type="STRING")
	private String k;

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

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public Date getD() {
		return d;
	}

	public void setD(Date d) {
		this.d = d;
	}

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}

	public Date getF() {
		return f;
	}

	public void setF(Date f) {
		this.f = f;
	}

	public String getG() {
		return g;
	}

	public void setG(String g) {
		this.g = g;
	}

	public String getH() {
		return h;
	}

	public void setH(String h) {
		this.h = h;
	}

	public String getJ() {
		return j;
	}

	public void setJ(String j) {
		this.j = j;
	}

	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}

}
