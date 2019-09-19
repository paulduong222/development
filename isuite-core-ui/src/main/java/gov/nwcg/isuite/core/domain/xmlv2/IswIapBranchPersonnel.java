package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswIapBranchPersonnel", table = "isw_iap_branch_personnel"
	,filterincident=" iap_branch_id in ( select id from isw_iap_branch where iap_plan_id in ( select id from isw_iap_plan where incident_id in (:INCIDENTID ) ) ) "
	,filtergroup=" iap_branch_id in ( select id from isw_iap_branch where iap_plan_id in ( select id from isw_iap_plan where incident_group_id in (:INCIDENTGROUPID ) ) ) "
)
public class IswIapBranchPersonnel {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_BRANCH_PERSONNEL", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="IAP_BRANCH_ID", type="STRING"
		,derivedtable="isw_iap_branch")
	private String a;
	
	@XmlTransferField(name = "B", sqlname="ROLE", type="STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname="ROLE_TYPE", type="STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname="RESOURCE_NAME", type="STRING")
	private String d;
	
	@XmlTransferField(name = "E", sqlname="PHONE1", type="STRING")
	private String e;
	
	@XmlTransferField(name = "F", sqlname="PHONE2", type="STRING")
	private String f;

	@XmlTransferField(name = "G", sqlname="IS_TRAINEE", type="STRING")
	private String g;

	@XmlTransferField(name = "H", sqlname="POSITION_NUM", type="INTEGER")
	private Integer h;
	
	/**
	 * Default constructor.
	 * 
	 */
	public IswIapBranchPersonnel() {
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

	public Integer getH() {
		return h;
	}

	public void setH(Integer h) {
		this.h = h;
	}

}
