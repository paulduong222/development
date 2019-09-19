package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswIapPersonnel", table="isw_iap_personnel", alias="r"
	,filterincident=" IAP_FORM_203_ID in (select id from isw_iap_form_203 where iap_plan_id in ( select id from isw_iap_plan where incident_id in (:INCIDENTID) ) ) " +
				    " or " +
				    " IAP_FORM_220_ID in (select id from isw_iap_form_220 where iap_plan_id in ( select id from isw_iap_plan where incident_id in (:INCIDENTID) ) ) "
	,filtergroup=" IAP_FORM_203_ID in (select id from isw_iap_form_203 where iap_plan_id in ( select id from isw_iap_plan where incident_group_id = :INCIDENTGROUPID ) ) " +
				 " or " +
				 " IAP_FORM_220_ID in (select id from isw_iap_form_220 where iap_plan_id in ( select id from isw_iap_plan where incident_group_id = :INCIDENTGROUPID ) ) "
	,orderby=" order by id asc "
)
public class IswIapPersonnel {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_PERSONNEL", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="AGENCY_NAME", type="STRING")
	private String a;
	
	@XmlTransferField(name = "B", sqlname="ROLE", type="STRING")
	private String b;
	
	@XmlTransferField(name = "C", sqlname="ROLE_TYPE", type="STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname="NAME", type="STRING")
	private String d;
	
	@XmlTransferField(name = "E", sqlname="PHONE", type="STRING")
	private String e;
	
	@XmlTransferField(name = "F", sqlname="FORM", type="STRING")
	private String f;
	
	@XmlTransferField(name = "G", sqlname="SECTION", type="STRING")
	private String g;
	
	@XmlTransferField(name = "H", sqlname="POSITION_NUM", type="INTEGER")
	private Integer h;

	@XmlTransferField(name = "I", sqlname="IS_BLANK_LINE", type="STRING")
	private String i;

	@XmlTransferField(name = "J", sqlname="IS_BLANK_BRANCH", type="STRING")
	private String j;

	@XmlTransferField(name = "K", sqlname="DIVISION_GROUP_NAME", type="STRING")
	private String k;
	
	@XmlTransferField(name = "L", sqlname="IS_TRAINEE", type="STRING")
	private String l;
	
	@XmlTransferField(name = "M", sqlname="IS_BRANCH_NAME", type="STRING")
	private String m;
	
	@XmlTransferField(name = "N", sqlname="IAP_FORM_203_ID", type="STRING"
		,derivedtable="isw_iap_form_203")
	private String n;

	@XmlTransferField(name = "O", sqlname="IAP_FORM_220_ID", type="STRING"
		,derivedtable="isw_iap_form_220")
	private String o;

	@XmlTransferField(name = "P", sqlname="IAP_PERSONNEL_BRANCH_ID", type="STRING"
		,derivedtable="isw_iap_personnel")
	private String p;
	
	/*
	@XmlTransferField(name = "IapBrPsPar", type="COMPLEX", target=IswIapPersonnel.class
			, lookupname="Id", sourcename="IapBrPsParId")
	private IswIapPersonnel iapBrPsPar;

	@XmlTransferField(name = "IapBrPsParId", sqlname="IAP_PERSONNEL_BRANCH_ID", type="LONG"
			,derived=true, derivedfield="IapBrPsPar")
	private Long iapBrPsParId;
	
	@XmlTransferField(name = "IapPsRes", type="COMPLEX", target=IswIapPersonnelRes.class
			, lookupname="IapPsId", sourcename="Id"
			, cascade=true)
	private Collection<IswIapPersonnelRes> iapPrRess = new ArrayList<IswIapPersonnelRes>();
	*/
	
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

	public Integer getH() {
		return h;
	}

	public void setH(Integer h) {
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

}
