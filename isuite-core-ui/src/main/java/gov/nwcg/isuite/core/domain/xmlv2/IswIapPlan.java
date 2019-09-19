package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswIapPlan", table="isw_iap_plan"
	,filterincident=" incident_id in (:INCIDENTID) "
	,filtergroup=" incident_group_id = :INCIDENTGROUPID "
)
public class IswIapPlan {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_PLAN", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="INCIDENT_GROUP_ID", type="STRING"
		,derivedtable="isw_incident_group")
	private String a;

	@XmlTransferField(name = "B", sqlname="INCIDENT_ID", type="STRING"
		,derivedtable="isw_incident")
	private String b;

	@XmlTransferField(name = "C", sqlname="INCIDENT_NAME", type="STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname="OPERATION_PERIOD", type="STRING")
	private String d;

	@XmlTransferField(name = "E", sqlname="FROM_DATE", type="DATE")
	private Date e;

	@XmlTransferField(name = "F", sqlname="TO_DATE", type="DATE")
	private Date f;

	@XmlTransferField(name = "G", sqlname="IS_PLAN_LOCKED", type="STRING", defaultvalue="N")
	private String g;

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

	public Date getE() {
		return e;
	}

	public void setE(Date e) {
		this.e = e;
	}

	public Date getF() {
		return f;
	}

	public void setF(Date f) {
		this.f = f;
	}

	public String getG() {
		return g;
	}

	public void setG(String g) {
		this.g = g;
	}


}
