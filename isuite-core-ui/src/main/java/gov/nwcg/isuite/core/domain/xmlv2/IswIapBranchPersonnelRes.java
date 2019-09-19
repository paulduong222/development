package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswIapBranchPersonnelRes", table = "isw_iap_branch_personnel_res"
	,filterincident=" iap_branch_personnel_id in (select id from isw_iap_branch_personnel where iap_branch_id in ( select id from isw_iap_branch where iap_plan_id in ( select id from isw_iap_plan where incident_id in (:INCIDENTID ) ) ) ) "
	,filtergroup=" iap_branch_personnel_id in (select id from isw_iap_branch_personnel where iap_branch_id in ( select id from isw_iap_branch where iap_plan_id in ( select id from isw_iap_plan where incident_group_id in (:INCIDENTGROUPID ) ) ) ) "
)
public class IswIapBranchPersonnelRes {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_BRANCH_PERSONNEL_RES", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="IAP_BRANCH_PERSONNEL_ID", type="STRING"
		,derivedtable="isw_iap_branch_personnel")
	private String a;
	
	@XmlTransferField(name = "B", sqlname="NAME", type="STRING")
	private String b;
	
	@XmlTransferField(name = "C", sqlname="POSITION_NUM", type="INTEGER")
	private Integer c;

	@XmlTransferField(name = "D", sqlname="IS_TRAINEE", type="STRING")
	private String d;

	/**
	 * Default constructor.
	 * 
	 */
	public IswIapBranchPersonnelRes() {
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

	public Integer getC() {
		return c;
	}

	public void setC(Integer c) {
		this.c = c;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}


}
