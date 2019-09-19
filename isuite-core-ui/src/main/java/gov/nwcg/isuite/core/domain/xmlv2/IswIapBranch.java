package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswIapBranch", table = "isw_iap_branch"
	,filterincident=" iap_plan_id in (select id from isw_iap_plan where incident_id in (:INCIDENTID) ) "
	,filtergroup=" iap_plan_id in (select id from isw_iap_plan where incident_group_id in (:INCIDENTGROUPID) ) "
)
public class IswIapBranch {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_BRANCH", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="IAP_PLAN_ID", type="STRING"
		,derivedtable="isw_iap_plan")
	private String a;
	
	@XmlTransferField(name = "B", sqlname="BRANCH_NAME", type="STRING")
	private String b;
	
	@XmlTransferField(name = "C", sqlname="DIVISION_NAME", type="STRING")
	private String c;
	
	@XmlTransferField(name = "D", sqlname="GROUP_NAME", type="STRING")
	private String d;
	
	@XmlTransferField(name = "E", sqlname="CONTROL_OPERATIONS", type="STRING", ischardata=true)
	private String e;
	
	@XmlTransferField(name = "F", sqlname="SPECIAL_INSTRUCTIONS", type="STRING", ischardata=true)
	private String f;
	
	@XmlTransferField(name = "G", sqlname="STAGING_AREA", type="STRING")
	private String g;
	
	@XmlTransferField(name = "H", sqlname="WORK_ASSIGNMENT", type="STRING", ischardata=true)
	private String h;
	
	@XmlTransferField(name = "I", sqlname="PREPARED_BY", type="STRING")
	private String i;
	
	@XmlTransferField(name = "J", sqlname="PREPARED_BY_POSITION", type="STRING")
	private String j;

	@XmlTransferField(name = "K", sqlname="APPROVED_BY", type="STRING")
	private String k;
	
	@XmlTransferField(name = "L", sqlname="PREPARED_DATE", type="DATE")
	private Date l;

	@XmlTransferField(name = "M", sqlname="IS_FORM_204_LOCKED", type="STRING")
	private String m;


	/**
	 * Default constructor.
	 * 
	 */
	public IswIapBranch() {
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


	public Date getL() {
		return l;
	}


	public void setL(Date l) {
		this.l = l;
	}


	public String getM() {
		return m;
	}


	public void setM(String m) {
		this.m = m;
	}

}
