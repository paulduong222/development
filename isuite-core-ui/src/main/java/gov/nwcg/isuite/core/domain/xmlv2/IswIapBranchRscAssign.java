package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswIapBranchRscAssign", table = "isw_iap_branch_rsc_assign"
	,filterincident=" iap_branch_id in ( select id from isw_iap_branch where iap_plan_id in ( select id from isw_iap_plan where incident_id in (:INCIDENTID ) ) ) "
	,filtergroup=" iap_branch_id in ( select id from isw_iap_branch where iap_plan_id in ( select id from isw_iap_plan where incident_group_id in (:INCIDENTGROUPID ) ) ) "
)
public class IswIapBranchRscAssign {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_BRANCH_RSC_ASSIGN", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="IAP_BRANCH_ID", type="STRING"
		,derivedtable="isw_iap_branch")
	private String a;
	
	@XmlTransferField(name = "B", sqlname="RESOURCE_NAME", type="STRING")
	private String b;
	
	@XmlTransferField(name = "C", sqlname="LEADER_NAME", type="STRING")
	private String c;
	
	@XmlTransferField(name = "D", sqlname="NBR_OF_PERSONNEL", type="INTEGER")
	private Integer d;
	
	@XmlTransferField(name = "E", sqlname="TRANSPORTATION", type="STRING")
	private String e;
	
	@XmlTransferField(name = "F", sqlname="DROP_OFF_POINT", type="STRING")
	private String f;
	
	@XmlTransferField(name = "G", sqlname="DROP_OFF_TIME", type="STRING")
	private String g;
	
	@XmlTransferField(name = "H", sqlname="PICK_UP_POINT", type="STRING")
	private String h;
	
	@XmlTransferField(name = "I", sqlname="PICK_UP_TIME", type="STRING")
	private String i;
	
	@XmlTransferField(name = "J", sqlname="CONTACT_INFO", type="STRING")
	private String j;
	
	@XmlTransferField(name = "K", sqlname="ADDITIONAL_INFO", type="STRING")
	private String k;
	
	@XmlTransferField(name = "L", sqlname="LAST_DAY_TO_WORK_DATE", type="DATE")
	private Date l;
	
	@XmlTransferField(name = "M", sqlname="POSITION_NUM", type="INTEGER")
	private Integer m;

	@XmlTransferField(name = "N", sqlname="IS_BLANK_LINE", type="STRING")
	private String n;

	/**
	 * Default constructor.
	 * 
	 */
	public IswIapBranchRscAssign() {
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

	public Integer getD() {
		return d;
	}

	public void setD(Integer d) {
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

	public Date getL() {
		return l;
	}

	public void setL(Date l) {
		this.l = l;
	}

	public Integer getM() {
		return m;
	}

	public void setM(Integer m) {
		this.m = m;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

}
