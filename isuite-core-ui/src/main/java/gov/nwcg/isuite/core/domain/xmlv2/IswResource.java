package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswResource", table = "isw_resource", alias="r"
	,filterincident=" id in (select resource_id from isw_incident_resource where incident_id in (:INCIDENTID) ) "
    ,orderby=" order by get_resource_level(id) asc "
    	//case when parent_resource_id is null then 0 else parent_resource_id end, id asc "
)
public class IswResource {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_RESOURCE", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="RESOURCE_NAME", type="STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname="FIRST_NAME", type="STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname="LAST_NAME", type="STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname="RESOURCE_CLASSIFICATION", type="STRING",nullwhenempty=true)
	private String d;

	@XmlTransferField(name = "E", sqlname="RESOURCE_STATUS", type="STRING",nullwhenempty=true)
	private String e;

	@XmlTransferField(name = "F", sqlname="IS_PERSON", type = "BOOLEAN")
	private Boolean f;

	@XmlTransferField(name = "G", sqlname="IS_CONTRACTED", type = "BOOLEAN")
	private Boolean g;

	@XmlTransferField(name = "H", sqlname = "IS_LEADER", type = "BOOLEAN")
	private Boolean h;

	@XmlTransferField(name = "I", sqlname="IS_COMPONENT", type = "BOOLEAN")
	private Boolean i=false;

	@XmlTransferField(name = "J", sqlname="NAME_ON_PICTURE_ID", type="STRING")
	private String j;

	@XmlTransferField(name = "K", sqlname="CONTACT_NAME", type="STRING")
	private String k;

	@XmlTransferField(name = "L", sqlname="PHONE", type="STRING")
	private String l;

	@XmlTransferField(name = "M", sqlname="EMAIL", type="STRING")
	private String m;

	@XmlTransferField(name = "N", sqlname="OTHER_1", type="STRING")
	private String n;

	@XmlTransferField(name = "O", sqlname="OTHER_2", type="STRING")
	private String o;

	@XmlTransferField(name = "P", sqlname="OTHER_3", type="STRING")
	private String p;

	@XmlTransferField(name = "Q", sqlname="IS_ENABLED", type = "BOOLEAN")
	private Boolean q;

	@XmlTransferField(name = "R", sqlname="IS_PERMANENT", type = "BOOLEAN")
	private Boolean r;

	@XmlTransferField(name = "S", sqlname="LEADER_TYPE", type = "INTEGER")
	private Integer s;

	@XmlTransferField(name = "T", sqlname = "EMPLOYMENT_TYPE", type="STRING",nullwhenempty=true)
	private String t;

	@XmlTransferField(name = "U", sqlname="NUMBER_OF_PERSONNEL", type = "LONG")
	private Long u;

	@XmlTransferField(name = "V", sqlname="DELETED_DATE", type="DATE")
	private Date v;
	
	@XmlTransferField(name = "W", sqlname="ROSS_RES_ID", type = "LONG")
	private Long w;

	@XmlTransferField(name = "X", sqlname="ROSS_RESOURCE_NAME", type="STRING")
	private String x;

	@XmlTransferField(name = "Y", sqlname="ROSS_FIRST_NAME", type="STRING")
	private String y;

	@XmlTransferField(name = "Z", sqlname="ROSS_LAST_NAME", type="STRING")
	private String z;
	
	@XmlTransferField(name = "AA", sqlname="LAST_ROSS_UPDATED_DATE", type="DATE")
	private Date aA;
	
	@XmlTransferField(name = "AB", sqlname="ROSS_GROUP_ASSIGNMENT", type="STRING")
	private String aB;
	
	@XmlTransferField(name = "AC", sqlname="PARENT_RESOURCE_ID", type="STRING"		
					  ,derivedtable="isw_resource")
	private String aC;
	
	@XmlTransferField(name = "AD", sqlname="PERMANENT_RESOURCE_ID", type="STRING"		
		  ,derivedtable="isw_resource")
	private String aD;
	
	@XmlTransferField(name = "AE", sqlname="ORGANIZATION_ID", type="STRING"		
		  ,derivedtable="isw_organization")
	private String aE;
	
	@XmlTransferField(name = "AF", sqlname="PRIMARY_DISP_CTR_ID", type="STRING"		
		  ,derivedtable="isw_organization")
	private String aF;
	
	@XmlTransferField(name = "AG", sqlname="AGENCY_ID", type="STRING"		
		  ,derivedtable="iswl_agency")
	private String aG;
	
	@XmlTransferField(name = "AH", sqlname="DEFAULT_CONTRACTOR_ID", type="STRING"		
		  ,derivedtable="isw_contractor")
	private String aH;
	
	@XmlTransferField(name = "AI", sqlname="DEFAULT_AGREEMENT_ID", type="STRING"		
		  ,derivedtable="isw_contractor_agreement")
	private String aI;
	
	public IswResource(){

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

	public Boolean getF() {
		return f;
	}

	public void setF(Boolean f) {
		this.f = f;
	}

	public Boolean getG() {
		return g;
	}

	public void setG(Boolean g) {
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

	public Boolean getQ() {
		return q;
	}

	public void setQ(Boolean q) {
		this.q = q;
	}

	public Boolean getR() {
		return r;
	}

	public void setR(Boolean r) {
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

	public Long getU() {
		return u;
	}

	public void setU(Long u) {
		this.u = u;
	}

	public Date getV() {
		return v;
	}

	public void setV(Date v) {
		this.v = v;
	}

	public Long getW() {
		return w;
	}

	public void setW(Long w) {
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

	public Date getAA() {
		return aA;
	}

	public void setAA(Date aa) {
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

	public String getAD() {
		return aD;
	}

	public void setAD(String ad) {
		aD = ad;
	}

	public String getAE() {
		return aE;
	}

	public void setAE(String ae) {
		aE = ae;
	}

	public String getAF() {
		return aF;
	}

	public void setAF(String af) {
		aF = af;
	}

	public String getAG() {
		return aG;
	}

	public void setAG(String ag) {
		aG = ag;
	}

	public String getAH() {
		return aH;
	}

	public void setAH(String ah) {
		aH = ah;
	}

	public String getAI() {
		return aI;
	}

	public void setAI(String ai) {
		aI = ai;
	}

}
