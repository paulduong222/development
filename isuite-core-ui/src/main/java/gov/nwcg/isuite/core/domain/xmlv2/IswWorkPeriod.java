package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswWorkPeriod", table="isw_work_period"
	,filterincident=" incident_resource_id in ( select id from isw_incident_resource where incident_id in (:INCIDENTID) ) "
)
public class IswWorkPeriod {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_WORK_PERIOD", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="INCIDENT_RESOURCE_ID", type="STRING",updateable=false
			,derivedtable="isw_incident_resource")
	private String a;

	@XmlTransferField(name = "B", sqlname = "CI_TRAVEL_METHOD", type="STRING",nullwhenempty=true)
	private String b;

	@XmlTransferField(name = "C", sqlname="CI_RENTAL_LOCATION", type="STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname="CI_CHECK_IN_DATE", type="DATE")
	private Date d;

	@XmlTransferField(name = "E", sqlname="CI_FIRST_WORK_DATE", type="DATE")
	private Date e;

	@XmlTransferField(name = "F", sqlname="CI_PRE_PLANNING_REMARKS", type="STRING")
	private String f;

	@XmlTransferField(name = "G", sqlname="CI_LENGTH_AT_ASSIGNMENT", type="LONG")
	private Long g;

	@XmlTransferField(name = "H", sqlname="DM_TENTATIVE_DEMOB_CITY", type="STRING")
	private String h;

	@XmlTransferField(name = "I", sqlname="DM_TENTATIVE_ARRIVAL_DATE", type="DATE")
	private Date i;

	@XmlTransferField(name = "J", sqlname="DM_RELEASE_DATE", type="DATE")
	private Date j;

	@XmlTransferField(name = "K", sqlname="DM_TENTATIVE_RELEASE_DATE", type="DATE")
	private Date k;

	@XmlTransferField(name = "L", sqlname="DM_IS_REASSIGNABLE", type="BOOLEAN")
	private Boolean l=true;

	@XmlTransferField(name = "M", sqlname="DM_IS_REST_OVERNIGHT", type="BOOLEAN")
	private Boolean m=false;

	@XmlTransferField(name = "N", sqlname="DM_IS_RELEASE_DISPATCH_NOTIF", type="BOOLEAN")
	private Boolean n=false;

	@XmlTransferField(name = "O", sqlname="DM_IS_PLANNING_DISPATCH_NOTIF", type="BOOLEAN")
	private Boolean o=false;

	@XmlTransferField(name = "P", sqlname="DM_IS_CHECKOUT_FORM_PRINTED", type="BOOLEAN")
	private Boolean p=false;

	@XmlTransferField(name = "Q", sqlname="DM_RELEASE_REMARKS", type="STRING")
	private String q;

	@XmlTransferField(name = "R", sqlname="DM_PLANNING_REMARKS", type="STRING")
	private String r;

	@XmlTransferField(name = "S", sqlname = "DM_TRAVEL_METHOD", type="STRING",nullwhenempty=true)
	private String s;

	@XmlTransferField(name="T", sqlname="CI_TENTATIVE_ARRIVAL_DATE", type = "DATE")
	private Date t;

	@XmlTransferField(name = "U", sqlname="IS_GROUND_SUPPORT", type = "BOOLEAN")
	private Boolean u;

	@XmlTransferField(name = "V", sqlname="CI_MOBILIZATION_ID", type="STRING"
		  ,derivedtable="isw_resource_mobilization")
	private String v;
	
	@XmlTransferField(name = "W", sqlname="DEF_INCIDENT_ACCOUNT_CODE_ID", type="STRING"
		  ,derivedtable="isw_incident_account_code")
	private String w;

	@XmlTransferField(name = "X", sqlname="CI_ARRIVAL_JET_PORT_ID", type="STRING"
		  ,derivedtable="iswl_jet_port")
	private String x;

	@XmlTransferField(name = "Y", sqlname="DM_TENTATIVE_DEMOB_STATE_ID", type="STRING"
		  ,derivedtable="iswl_country_subdivision")
	private String y;

	@XmlTransferField(name = "Z", sqlname="DM_AIR_TRAVEL_ID", type="STRING"
		  ,derivedtable="isw_air_travel")
	private String z;

	public IswWorkPeriod(){
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

	public Date getD() {
		return d;
	}

	public void setD(Date d) {
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

	public Long getG() {
		return g;
	}

	public void setG(Long g) {
		this.g = g;
	}

	public String getH() {
		return h;
	}

	public void setH(String h) {
		this.h = h;
	}

	public Date getI() {
		return i;
	}

	public void setI(Date i) {
		this.i = i;
	}

	public Date getJ() {
		return j;
	}

	public void setJ(Date j) {
		this.j = j;
	}

	public Date getK() {
		return k;
	}

	public void setK(Date k) {
		this.k = k;
	}

	public Boolean getL() {
		return l;
	}

	public void setL(Boolean l) {
		this.l = l;
	}

	public Boolean getM() {
		return m;
	}

	public void setM(Boolean m) {
		this.m = m;
	}

	public Boolean getN() {
		return n;
	}

	public void setN(Boolean n) {
		this.n = n;
	}

	public Boolean getO() {
		return o;
	}

	public void setO(Boolean o) {
		this.o = o;
	}

	public Boolean getP() {
		return p;
	}

	public void setP(Boolean p) {
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

	public Date getT() {
		return t;
	}

	public void setT(Date t) {
		this.t = t;
	}

	public Boolean getU() {
		return u;
	}

	public void setU(Boolean u) {
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

}
