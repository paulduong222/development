package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswIapBranchCommSummary", table = "isw_iap_branch_comm_summary"
	,filterincident=" iap_branch_id in ( select id from isw_iap_branch where iap_plan_id in ( select id from isw_iap_plan where incident_id in (:INCIDENTID ) ) ) "
	,filtergroup=" iap_branch_id in ( select id from isw_iap_branch where iap_plan_id in ( select id from isw_iap_plan where incident_group_id in (:INCIDENTGROUPID ) ) ) "
)
public class IswIapBranchCommSummary {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_BRANCH_COMM_SUMMARY", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="IAP_BRANCH_ID", type="STRING"
		,derivedtable="isw_iap_branch")
	private String a;
	
	@XmlTransferField(name = "B", sqlname="FUNCTION", type="STRING")
	private String b;
	
	@XmlTransferField(name = "C", sqlname="RX", type="STRING")
	private String c;
	
	@XmlTransferField(name = "D", sqlname="TX", type="STRING")
	private String d;
	
	@XmlTransferField(name = "E", sqlname="TONE", type="STRING")
	private String e;
	
	@XmlTransferField(name = "F", sqlname="RX_TONE", type="STRING")
	private String f;
	
	@XmlTransferField(name = "G", sqlname="TX_TONE", type="STRING")
	private String g;
	
	@XmlTransferField(name = "H", sqlname="SMODE", type="STRING")
	private String h;

	@XmlTransferField(name = "I", sqlname="SYSTEM_1", type="STRING")
	private String i;
	
	@XmlTransferField(name = "J", sqlname="CHANNEL_1", type="STRING")
	private String j;
	
	@XmlTransferField(name = "K", sqlname="SYSTEM_2", type="STRING")
	private String k;
	
	@XmlTransferField(name = "L", sqlname="CHANNEL_2", type="STRING")
	private String l;
	
	@XmlTransferField(name = "M", sqlname="PRIMARY_CONTACT", type="STRING")
	private String m;
	
	@XmlTransferField(name = "N", sqlname="CELL_NBR", type="STRING")
	private String n;
	
	@XmlTransferField(name = "O", sqlname="PAGER_NBR", type="STRING")
	private String o;

	@XmlTransferField(name = "P", sqlname="POSITION_NUM", type="INTEGER")
	private Integer p;

	@XmlTransferField(name = "Q", sqlname="IS_BLANK_LINE", type="STRING")
	private String q;

	/**
	 * Default constructor.
	 * 
	 */
	public IswIapBranchCommSummary() {
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

	public Integer getP() {
		return p;
	}

	public void setP(Integer p) {
		this.p = p;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

}
