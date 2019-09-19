package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswIapMasterFrequency", table="isw_iap_master_frequency"
	, filterincident=" incident_id in (:INCIDENTID) ", filtergroup=" incident_group_id = :INCIDENTGROUPID ")
public class IswIapMasterFrequency {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_MASTER_FREQUENCY", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "I", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="INCIDENT_ID", type="STRING",derivedtable="isw_incident")
	private String a;
	
	@XmlTransferField(name = "B", sqlname="INCIDENT_GROUP_ID", type="STRING",derivedtable="isw_incident_group")
	private String b;

	@XmlTransferField(name = "C", sqlname="SHOW", type="STRING")
	private String c;
	
	@XmlTransferField(name = "D", sqlname="SYSTEM", type="STRING",nullwhenempty=true)
	private String d;
	
	@XmlTransferField(name = "E", sqlname="GROUP_NAME", type="STRING")
	private String e;
	
	@XmlTransferField(name = "F", sqlname="CHANNEL", type="STRING")
	private String f;
	
	@XmlTransferField(name = "G", sqlname="RFUNCTION", type="STRING")
	private String g;
	
	@XmlTransferField(name = "H", sqlname="RX", type="STRING")
	private String h;
	
	@XmlTransferField(name = "I", sqlname="TX", type="STRING")
	private String i;
	
	@XmlTransferField(name = "J", sqlname="TONE", type="STRING")
	private String j;
	
	@XmlTransferField(name = "K", sqlname="ASSIGNMENT", type="STRING")
	private String k;
	
	@XmlTransferField(name = "L", sqlname="REMARKS", type="STRING")
	private String l;
	
	@XmlTransferField(name = "M", sqlname="CHANNEL_NAME_RADIO_TALKGROUP", type="STRING")
	private String m;
	
	@XmlTransferField(name = "N", sqlname="RX_FREQ_N_W", type="STRING")
	private String n;
	
	@XmlTransferField(name = "O", sqlname="RX_TONE_NAC", type="STRING")
	private String o;
	
	@XmlTransferField(name = "P", sqlname="TX_FREQ_N_W", type="STRING")
	private String p;
	
	@XmlTransferField(name = "Q", sqlname="TX_TONE_NAC", type="STRING")
	private String q;
	
	@XmlTransferField(name = "R", sqlname="MODE_A_D_M", type="STRING")
	private String r;

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

	public String getO() {
		return o;
	}

	public void setO(String o) {
		this.o = o;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

	
}
