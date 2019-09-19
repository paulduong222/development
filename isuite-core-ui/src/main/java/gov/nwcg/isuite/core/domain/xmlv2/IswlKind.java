package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.math.BigDecimal;

@XmlTransferTable(name = "IswlKind", table="iswl_kind"
	, filterincident=" is_standard = :FALSE and incident_id in (:INCIDENTID) " 
	, filtergroup=" is_standard = :FALSE and incident_group_id = :INCIDENTGROUPID ")
public class IswlKind {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_KIND", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="CODE", type="STRING")
	private String a;

	@XmlTransferField(name = "B",sqlname="DESCRIPTION", type="STRING")
	private String b;

	@XmlTransferField(name = "C",sqlname="IS_STANDARD", type="BOOLEAN")
	private Boolean c;

	@XmlTransferField(name = "D", sqlname="IS_DIRECT", type="BOOLEAN")
	private Boolean d;

	@XmlTransferField(name = "e", sqlname="IS_INDIRECT", type="BOOLEAN")
	private Boolean e;

	@XmlTransferField(name = "F", sqlname="UNITS", type="INTEGER")
	private Integer f;

	@XmlTransferField(name = "G", sqlname="PEOPLE", type="INTEGER")
	private Integer g;

	@XmlTransferField(name = "H", sqlname="IS_LINE_OVERHEAD", type="BOOLEAN")
	private Boolean h;

	@XmlTransferField(name = "I", sqlname="IS_SUBORDINATE", type="BOOLEAN")
	private Boolean i;

	@XmlTransferField(name = "J", sqlname="IS_STRIKE_TEAM", type="BOOLEAN")
	private Boolean j;

	@XmlTransferField(name = "K", sqlname="IS_AIRCRAFT", type="STRING")
	private String k;

	@XmlTransferField(name = "L", sqlname="INCIDENT_ID", type="STRING",updateable=false
			,derivedtable="isw_incident")
	private String l;

	@XmlTransferField(name = "M", sqlname="INCIDENT_GROUP_ID", type="STRING",updateable=false
			,derivedtable="isw_incident_group")
	private String m;

	@XmlTransferField(name = "N", sqlname="IS_ACTIVE", type="STRING")
	private String n;

	@XmlTransferField(name = "O", sqlname="STANDARD_COST", type="BIGDECIMAL")
	private BigDecimal o;

	@XmlTransferField(name = "P", sqlname="REQUEST_CATEGORY_ID", type="STRING"		
				  	,derivedtable="iswl_request_category")
	private String p;
	
	@XmlTransferField(name = "Q", sqlname="DEPARTMENT_ID", type="STRING"		
		  ,derivedtable="iswl_department")
	private String q;
	
	@XmlTransferField(name = "R", sqlname="DAILY_FORM_ID", type="STRING"		
		  ,derivedtable="iswl_daily_form")
	private String r;
	
	@XmlTransferField(name = "S", sqlname="SIT_209_ID", type="STRING"		
		  ,derivedtable="iswl_sit_209")
	private String s;
	
	@XmlTransferField(name = "T", sqlname="SUB_GROUP_CATEGORY_ID", type="STRING"		
		  ,derivedtable="iswl_sub_group_category")
	private String t;

	@XmlTransferField(name = "U", sqlname="GROUP_CATEGORY_ID", type="STRING"		
		  ,derivedtable="iswl_group_category")
	private String u;

	@XmlTransferField(name = "V", sqlname = "OBSOLETE", type="STRING")
	private String v;
	
	public IswlKind() {
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

	public Boolean getD() {
		return d;
	}

	public void setD(Boolean d) {
		this.d = d;
	}

	public Boolean getE() {
		return e;
	}

	public void setE(Boolean e) {
		this.e = e;
	}

	public Integer getF() {
		return f;
	}

	public void setF(Integer f) {
		this.f = f;
	}

	public Integer getG() {
		return g;
	}

	public void setG(Integer g) {
		this.g = g;
	}

	public Boolean getH() {
		return h;
	}

	public void setH(Boolean h) {
		this.h = h;
	}

	public Boolean getI() {
		return i;
	}

	public void setI(Boolean i) {
		this.i = i;
	}

	public Boolean getJ() {
		return j;
	}

	public void setJ(Boolean j) {
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

	public BigDecimal getO() {
		return o;
	}

	public void setO(BigDecimal o) {
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

}
