package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswIapForm220", table="isw_iap_form_220"
	,filterincident=" iap_plan_id in (select id from isw_iap_plan where incident_id in (:INCIDENTID) ) "
	,filtergroup=" iap_plan_id in (select id from isw_iap_plan where incident_group_id in (:INCIDENTGROUPID) ) "
)
public class IswIapForm220 {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_FORM_220", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="PREPARED_BY", type="STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname="PREPARED_BY_POS", type="STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname="PREPARED_DATE", type="DATE")
	private Date c;
	
	@XmlTransferField(name = "D", sqlname="IS_FORM_LOCKED", type="STRING")
	private String d;

	@XmlTransferField(name = "E", sqlname="SUNSET", type="STRING")
	private String e;

	@XmlTransferField(name = "F", sqlname="SUNRISE", type="STRING")
	private String f;
	
	@XmlTransferField(name = "G", sqlname="MEDIVAC", type="STRING")
	private String g;
	
	@XmlTransferField(name = "H", sqlname="NEW_INCIDENT", type="STRING")
	private String h;

	@XmlTransferField(name = "I", sqlname="READY_ALERT_AIRCRAFT", type="STRING")
	private String i;

	@XmlTransferField(name = "J", sqlname="TFR_NBR", type="STRING")
	private String j;
	
	@XmlTransferField(name = "K", sqlname="ALTITUDE", type="STRING")
	private String k;
	
	@XmlTransferField(name = "L", sqlname="CENTRAL_POINT", type="STRING")
	private String l;

	@XmlTransferField(name = "M", sqlname="REMARKS", type="STRING")
	private String m;

	@XmlTransferField(name = "N", sqlname="IAP_PLAN_ID", type="STRING"
		,derivedtable="isw_iap_plan")
	private String n;

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

	public Date getC() {
		return c;
	}

	public void setC(Date c) {
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

	public String getH() {
		return h;
	}

	public void setH(String h) {
		this.h = h;
	}

	public String getI() {
		return i;
	}

	public void setI(String i) {
		this.i = i;
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

	public String getL() {
		return l;
	}

	public void setL(String l) {
		this.l = l;
	}

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}
	

}
