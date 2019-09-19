package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswIapForm202", table="isw_iap_form_202"
	,filterincident=" iap_plan_id in (select id from isw_iap_plan where incident_id in (:INCIDENTID) ) "
	,filtergroup=" iap_plan_id in (select id from isw_iap_plan where incident_group_id in (:INCIDENTGROUPID) ) "
)
public class IswIapForm202 {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_FORM_202", type="LONG")
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

	@XmlTransferField(name = "D", sqlname="APPROVED_BY", type="STRING")
	private String d;
	
	@XmlTransferField(name = "E", sqlname="APPROVED_DATE", type="DATE")
	private Date e;

	@XmlTransferField(name = "F", sqlname="OBJECTIVES", type="STRING", ischardata=true)
	private String f;
	
	@XmlTransferField(name = "G", sqlname="COMMAND_EMPHASIS", type="STRING", ischardata=true)
	private String g;

	@XmlTransferField(name = "H", sqlname="GEN_SIT_AWARENESS", type="STRING", ischardata=true)
	private String h;

	@XmlTransferField(name = "I", sqlname="SITE_SAFETY_PLAN_RQRD", type="STRING")
	private String i;
	
	@XmlTransferField(name = "J", sqlname="SITE_SAFETY_PLAN_LOC", type="STRING")
	private String j;

	@XmlTransferField(name = "K", sqlname="IS_FORM_202_ATTACHED", type="STRING")
	private String k;
	
	@XmlTransferField(name = "L", sqlname="IS_FORM_203_ATTACHED", type="STRING")
	private String l;
	
	@XmlTransferField(name = "M", sqlname="IS_FORM_204_ATTACHED", type="STRING")
	private String m;
	
	@XmlTransferField(name = "N", sqlname="IS_FORM_205_ATTACHED", type="STRING")
	private String n;
	
	@XmlTransferField(name = "O", sqlname="IS_FORM_205A_ATTACHED", type="STRING")
	private String o;
	
	@XmlTransferField(name = "P", sqlname="IS_FORM_206_ATTACHED", type="STRING")
	private String p;
	
	@XmlTransferField(name = "Q", sqlname="IS_FORM_207_ATTACHED", type="STRING")
	private String q;
	
	@XmlTransferField(name = "R", sqlname="IS_FORM_208_ATTACHED", type="STRING")
	private String r;
	
	@XmlTransferField(name = "S", sqlname="IS_INCIDENT_MAP_ATTACHED", type="STRING")
	private String s;
	
	@XmlTransferField(name = "T", sqlname="IS_WEATHER_FORECAST_ATTACHED", type="STRING")
	private String t;

	@XmlTransferField(name = "U", sqlname="IS_OTHER_FORM_ATTACHED_1", type="STRING")
	private String u;
	
	@XmlTransferField(name = "V", sqlname="IS_OTHER_FORM_ATTACHED_2", type="STRING")
	private String v;

	@XmlTransferField(name = "W", sqlname="IS_OTHER_FORM_ATTACHED_3", type="STRING")
	private String w;

	@XmlTransferField(name = "X", sqlname="IS_OTHER_FORM_ATTACHED_4", type="STRING")
	private String x;

	@XmlTransferField(name = "Y", sqlname="OTHER_FORM_NAME_1", type="STRING")
	private String y;
	
	@XmlTransferField(name = "Z", sqlname="OTHER_FORM_NAME_2", type="STRING")
	private String z;

	@XmlTransferField(name = "AA", sqlname="OTHER_FORM_NAME_3", type="STRING")
	private String aA;
	
	@XmlTransferField(name = "AB", sqlname="OTHER_FORM_NAME_4", type="STRING")
	private String aB;
	
	@XmlTransferField(name = "AC", sqlname="IS_FORM_LOCKED", type="STRING")
	private String aC;

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

	public Date getE() {
		return e;
	}

	public void setE(Date e) {
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

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getU() {
		return u;
	}

	public void setU(String u) {
		this.u = u;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public String getW() {
		return w;
	}

	public void setW(String w) {
		this.w = w;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getZ() {
		return z;
	}

	public void setZ(String z) {
		this.z = z;
	}

	public String getAA() {
		return aA;
	}

	public void setAA(String aa) {
		aA = aa;
	}

	public String getAB() {
		return aB;
	}

	public void setAB(String ab) {
		aB = ab;
	}

	public String getAC() {
		return aC;
	}

	public void setAC(String ac) {
		aC = ac;
	}

}
