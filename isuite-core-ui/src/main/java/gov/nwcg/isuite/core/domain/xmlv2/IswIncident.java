package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswIncident", table="isw_incident" 
					, filterincident=" id in (:INCIDENTID) ")
public class IswIncident{

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCIDENT", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="INCIDENT_NAME", type="STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname="INCIDENT_DESCRIPTION", type="STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname="NBR", type="STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname="IRWIN_IRWINID", type="STRING")
	private String d;

	@XmlTransferField(name = "E", sqlname="LOCATION", type="STRING")
	private String e;

	@XmlTransferField(name = "F", sqlname="LATITUDE", type="STRING")
	private String f;

	@XmlTransferField(name = "G", sqlname="LONGITUDE", type="STRING")
	private String g;

	@XmlTransferField(name = "H", sqlname="INCIDENT_START_DATE", type="DATE")
	private Date h;

	@XmlTransferField(name = "I", sqlname="INCIDENT_END_DATE", type="DATE")
	private Date i;

	@XmlTransferField(name = "J", sqlname="INCIDENT_YEAR", type="INTEGER")
	private Integer j;

	@XmlTransferField(name = "K", sqlname="RESTRICTED_FLG", type="BOOLEAN")
	private Boolean k;

	@XmlTransferField(name = "L", sqlname="ROSS_INCIDENT_ID", type="STRING")
	private String l;

	@XmlTransferField(name = "M", sqlname="IAP_PERSON_NAME_ORDER", type="SHORT", defaultvalue="0")
	private Short m;

	@XmlTransferField(name = "N", sqlname="INCLUDE_FILLED", type="STRING")
	private String n;

	@XmlTransferField(name = "O", sqlname="IAP_RESOURCE_TO_DISPLAY_FROM", type="SHORT", defaultvalue="0")
	private Short o;

	@XmlTransferField(name = "P", sqlname="IAP_TREEVIEW_DISPLAY", type="SHORT", defaultvalue="0")
	private Short p;

	@XmlTransferField(name = "Q", sqlname="BY_DATE", type="DATE")
	private Date q;

	@XmlTransferField(name = "R", sqlname="NBR_OF_DAYS_PRIOR", type="SHORT")
	private Short r;

	@XmlTransferField(name = "S", sqlname="COST_DEFAULT_HOURS", type="INTEGER")
	private Integer s;

	@XmlTransferField(name = "T", sqlname="COST_AUTORUN", type="STRING")
	private String t;

	@XmlTransferField(name = "U", sqlname="ACCRUAL_EXTRACT_NUMBER", type="INTEGER")
	private Integer u;

	@XmlTransferField(name = "V", sqlname="AGENCY_ID", type="STRING",		
					  derivedtable="iswl_agency")
	private String v;

	@XmlTransferField(name = "W", sqlname="EVENT_TYPE_ID", type="STRING"		
		  ,derivedtable="iswl_event_type")
    private String w;

	@XmlTransferField(name = "X", sqlname="COUNTRY_SUBDIVISION_ID", type="STRING"
			,derivedtable="iswl_country_subdivision")
	private String x;

	@XmlTransferField(name = "Y", sqlname="RATE_CLASS_ID", type="STRING"
		,derivedtable="iswl_rate_class")
	private String y;

	@XmlTransferField(name = "Z", sqlname="UNIT_ID", type="STRING"
		,derivedtable="isw_organization")
	private String z;
	
	@XmlTransferField(name = "aA", sqlname="PDC_ID", type="STRING"
		,derivedtable="isw_organization")
	private String aA;
	
	public IswIncident(){
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

	public Date getH() {
		return h;
	}

	public void setH(Date h) {
		this.h = h;
	}

	public Date getI() {
		return i;
	}

	public void setI(Date i) {
		this.i = i;
	}

	public Integer getJ() {
		return j;
	}

	public void setJ(Integer j) {
		this.j = j;
	}

	public Boolean getK() {
		return k;
	}

	public void setK(Boolean k) {
		this.k = k;
	}

	public String getL() {
		return l;
	}

	public void setL(String l) {
		this.l = l;
	}

	public Short getM() {
		return m;
	}

	public void setM(Short m) {
		this.m = m;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public Short getO() {
		return o;
	}

	public void setO(Short o) {
		this.o = o;
	}

	public Short getP() {
		return p;
	}

	public void setP(Short p) {
		this.p = p;
	}

	public Date getQ() {
		return q;
	}

	public void setQ(Date q) {
		this.q = q;
	}

	public Short getR() {
		return r;
	}

	public void setR(Short r) {
		this.r = r;
	}

	public Integer getS() {
		return s;
	}

	public void setS(Integer s) {
		this.s = s;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public Integer getU() {
		return u;
	}

	public void setU(Integer u) {
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

}
