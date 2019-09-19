package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.math.BigDecimal;
import java.util.Date;

@XmlTransferTable(name = "IswAssignmentTimePost", table="isw_assign_time_post"
	,filterincident=" assignment_time_id in ("+
					"   select at3.id "+
					"   from isw_assignment_time at3 "+
					"        ,isw_assignment a3 "+
					"		 , isw_work_period_assignment wpa3 " +
					"		 , isw_work_period wp3 " +
					"        , isw_incident_resource ir3 " +
					"   where at3.assignment_id = a3.id " +
					"   and a3.id = wpa3.assignment_id " + 
					"   and wpa3.work_period_id = wp3.id " +
					"   and wp3.incident_resource_id = ir3.id " +
					"   and ir3.incident_id in (:INCIDENTID) " +
				    ") "
)
public class IswAssignmentTimePost {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_ASSIGN_TIME_POST", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="ASSIGNMENT_TIME_ID", type="STRING",updateable=false
			,derivedtable="isw_assignment_time")
	private String a;

	@XmlTransferField(name = "B", sqlname="EMPLOYMENT_TYPE", type="STRING")
    private String b;
    
	@XmlTransferField(name = "C", sqlname="POST_START_DATE", type="DATE")
	private Date c;
	
	@XmlTransferField(name = "D", sqlname="POST_STOP_DATE", type="DATE")
	private Date d;
	
	@XmlTransferField(name = "E", sqlname="OTHER_RATE", type="BIGDECIMAL")
	private BigDecimal e;
	
	@XmlTransferField(name = "F", sqlname="RATE_TYPE", type="STRING")
	private String f;
	
	@XmlTransferField(name = "G", sqlname="UNIT_OF_MEASURE", type="STRING")
	private String g;

	@XmlTransferField(name = "H", sqlname="RATE_AMOUNT", type="BIGDECIMAL")
	private BigDecimal h;
	
	@XmlTransferField(name = "I", sqlname="GUARANTEE_AMOUNT", type="BIGDECIMAL")
	private BigDecimal i;
	
	@XmlTransferField(name = "J", sqlname="DESCRIPTION", type="STRING")
	private String j;
	
	@XmlTransferField(name = "K", sqlname="IS_HALF_RATE", type="BOOLEAN")
	private Boolean k;
	
	@XmlTransferField(name = "L", sqlname="QUANTITY", type="BIGDECIMAL")
	private BigDecimal l;
	
	@XmlTransferField(name = "M", sqlname="IS_TRAINING", type="BOOLEAN")
	private Boolean m;

	@XmlTransferField(name = "N", sqlname="RTN_TRAVEL_START_ONLY", type="BOOLEAN")
	private Boolean n;
	
	@XmlTransferField(name = "O", sqlname="IS_PRIMARY_POSTING", type="BOOLEAN")
	private Boolean o;

	@XmlTransferField(name = "P", sqlname="IS_SPECIAL_POSTING", type="BOOLEAN")
	private Boolean p;

	@XmlTransferField(name = "Q", sqlname="IS_GUARANTEE_POSTING", type="BOOLEAN")
	private Boolean q;

	@XmlTransferField(name = "R", sqlname="INVOICED_AMOUNT", type="BIGDECIMAL")
	private BigDecimal r;
	
	@XmlTransferField(name = "S", sqlname="CONTRACTOR_POST_TYPE", type="STRING")
	private String s;

	@XmlTransferField(name = "T", sqlname="RATE_CLASS_RATE_ID", type="STRING"
		  ,derivedtable="iswl_rate_class_rate")
	private String t;
	
	@XmlTransferField(name = "U", sqlname="INCIDENT_ACCOUNT_CODE_ID", type="STRING"
		  ,derivedtable="isw_incident_account_code")
	private String u;

	@XmlTransferField(name = "V", sqlname = "KIND_ID", type = "STRING"
		  ,derivedtable="iswl_kind")
	private String v;
	
	@XmlTransferField(name = "W", sqlname="REF_CONTRACTOR_RATE_ID", type="STRING"
		  ,derivedtable="isw_contractor_rate")
	private String w;

	@XmlTransferField(name = "X", sqlname="SPECIAL_PAY_ID", type="STRING"
		  ,derivedtable="iswl_special_pay")
	private String x;

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

	public Date getD() {
		return d;
	}

	public void setD(Date d) {
		this.d = d;
	}

	public BigDecimal getE() {
		return e;
	}

	public void setE(BigDecimal e) {
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

	public BigDecimal getH() {
		return h;
	}

	public void setH(BigDecimal h) {
		this.h = h;
	}

	public BigDecimal getI() {
		return i;
	}

	public void setI(BigDecimal i) {
		this.i = i;
	}

	public String getJ() {
		return j;
	}

	public void setJ(String j) {
		this.j = j;
	}

	public Boolean getK() {
		return k;
	}

	public void setK(Boolean k) {
		this.k = k;
	}

	public BigDecimal getL() {
		return l;
	}

	public void setL(BigDecimal l) {
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

	public Boolean getQ() {
		return q;
	}

	public void setQ(Boolean q) {
		this.q = q;
	}

	public BigDecimal getR() {
		return r;
	}

	public void setR(BigDecimal r) {
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


}