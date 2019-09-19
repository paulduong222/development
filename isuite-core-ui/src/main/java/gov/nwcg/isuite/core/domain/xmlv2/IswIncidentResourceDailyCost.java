package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.math.BigDecimal;
import java.util.Date;

@XmlTransferTable(name = "IswIncidentResourceDailyCost", table="isw_inc_res_daily_cost"
	,filterincident=" incident_resource_id in ( select id from isw_incident_resource where incident_id in (:INCIDENTID) ) " +
	 			    " or " +
	 			    " incident_resource_other_id in (select id from isw_incident_resource_other where incident_id in (:INCIDENTID) ) "
)
public class IswIncidentResourceDailyCost {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INC_RES_DAILY_COST", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "B", sqlname="INCIDENT_RESOURCE_ID", type="STRING",updateable=false
			,derivedtable="isw_incident_resource")
	private String b;

	@XmlTransferField(name = "C", sqlname="INCIDENT_RESOURCE_OTHER_ID", type="STRING",updateable=false
			,derivedtable="isw_incident_resource_other")
	private String c;

	@XmlTransferField(name = "D", sqlname="RESOURCE_COST_TYPE", type="STRING")
	private String d;

	@XmlTransferField(name = "E", sqlname="ACTIVITY_DATE", type="DATE")
	private Date e;

	@XmlTransferField(name = "F", sqlname="RATE_TYPE", type="STRING")
	private String f;

	@XmlTransferField(name = "G", sqlname="UNITS", type="BIGDECIMAL")
	private BigDecimal g;

	@XmlTransferField(name = "H", sqlname="UNIT_COST_AMOUNT", type="BIGDECIMAL")
	private BigDecimal h;

	@XmlTransferField(name = "I", sqlname="ADJUSTMENT_AMOUNT", type="BIGDECIMAL")
	private BigDecimal i;

	@XmlTransferField(name = "J", sqlname="COST_LEVEL", type="STRING", nullwhenempty=true)
	private String j;

	@XmlTransferField(name = "K", sqlname="IS_LOCKED", type="BOOLEAN")
	private Boolean k=false;

	@XmlTransferField(name = "L", sqlname="IS_FLOWDOWN", type="BOOLEAN")
	private Boolean l=false;

	@XmlTransferField(name = "M", sqlname="PRIMARY_TOTAL_AMOUNT", type="BIGDECIMAL")
	private BigDecimal m;

	@XmlTransferField(name = "N", sqlname="SUBORDINATE_TOTAL_AMOUNT", type="BIGDECIMAL")
	private BigDecimal n;

	@XmlTransferField(name = "O", sqlname="TOTAL_COST_AMOUNT", type="BIGDECIMAL")
	private BigDecimal o;

	@XmlTransferField(name = "P", sqlname="AIRCRAFT_COST_AMOUNT", type="BIGDECIMAL")
	private BigDecimal p;

	@XmlTransferField(name = "Q", sqlname="FLIGHT_HOURS", type="BIGDECIMAL")
	private BigDecimal q;

	@XmlTransferField(name = "R", sqlname="NUMBER_OF_LOADS", type="INTEGER")
	private Integer r;

	@XmlTransferField(name = "S", sqlname="WATER_GALLONS", type="BIGDECIMAL")
	private BigDecimal s;

	@XmlTransferField(name = "T", sqlname="RETARDANT_GALLONS", type="BIGDECIMAL")
	private BigDecimal t;

	@XmlTransferField(name = "U", sqlname="CARGO_POUNDS", type="BIGDECIMAL")
	private BigDecimal u;

	@XmlTransferField(name = "V", sqlname="NUMBER_OF_TRIPS", type="INTEGER")
	private Integer v;

	@XmlTransferField(name = "W", sqlname="NUMBER_OF_PASSENGERS", type="INTEGER")
	private Integer w;

	@XmlTransferField(name = "X", sqlname="INCIDENT_ACCOUNT_CODE_ID", type="STRING"
		  ,derivedtable="isw_incident_account_code")
	private String x;
	
	@XmlTransferField(name = "Y", sqlname="ACCRUAL_CODE_ID", type="STRING"		
					  ,derivedtable="iswl_accrual_code")
	private String y;
	
	@XmlTransferField(name = "Z", sqlname="INCIDENT_SHIFT_ID", type="STRING"		
		  ,derivedtable="isw_incident_shift")
	private String z;
	
	@XmlTransferField(name = "AA", sqlname="COST_GROUP_ID", type="STRING"		
		  ,derivedtable="isw_cost_group")
	private String aA;

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

	public BigDecimal getG() {
		return g;
	}

	public void setG(BigDecimal g) {
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

	public Boolean getL() {
		return l;
	}

	public void setL(Boolean l) {
		this.l = l;
	}

	public BigDecimal getM() {
		return m;
	}

	public void setM(BigDecimal m) {
		this.m = m;
	}

	public BigDecimal getN() {
		return n;
	}

	public void setN(BigDecimal n) {
		this.n = n;
	}

	public BigDecimal getO() {
		return o;
	}

	public void setO(BigDecimal o) {
		this.o = o;
	}

	public BigDecimal getP() {
		return p;
	}

	public void setP(BigDecimal p) {
		this.p = p;
	}

	public BigDecimal getQ() {
		return q;
	}

	public void setQ(BigDecimal q) {
		this.q = q;
	}

	public Integer getR() {
		return r;
	}

	public void setR(Integer r) {
		this.r = r;
	}

	public BigDecimal getS() {
		return s;
	}

	public void setS(BigDecimal s) {
		this.s = s;
	}

	public BigDecimal getT() {
		return t;
	}

	public void setT(BigDecimal t) {
		this.t = t;
	}

	public BigDecimal getU() {
		return u;
	}

	public void setU(BigDecimal u) {
		this.u = u;
	}

	public Integer getV() {
		return v;
	}

	public void setV(Integer v) {
		this.v = v;
	}

	public Integer getW() {
		return w;
	}

	public void setW(Integer w) {
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
