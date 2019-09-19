package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswIncidentResource", table="isw_incident_resource"
	,filterincident=" incident_id in (:INCIDENTID) "
)
public class IswIncidentResource {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCIDENT_RESOURCE", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="INCIDENT_ID", type="STRING",updateable=false
			,derivedtable="isw_incident")
	private String a;

	@XmlTransferField(name = "B", sqlname="RES_NUM_ID", type="LONG")
	private Long b;
	
	@XmlTransferField(name = "C", sqlname="NAME_AT_INCIDENT", type="STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname="ROSS_RES_REQ_ID", type="LONG")
	private Long d;

	@XmlTransferField(name = "E", sqlname="DAILY_COST_EXCEPTION", type="STRING")
	private String e;

	@XmlTransferField(name = "F", sqlname="ACTIVE", type="BOOLEAN")
	private Boolean f=true;

	@XmlTransferField(name = "G", sqlname="RESOURCE_ID", type="STRING"
		,derivedtable="isw_resource")
	private String g;
	
	@XmlTransferField(name = "H", sqlname="COST_DATA_ID", type="STRING"
		,derivedtable="isw_cost_data")
	private String h;

	/* Cascade Tables 
	@XmlTransferField(name = "C1", target=IswIncidentResourceDailyCost.class
			, targettable="isw_inc_res_daily_cost", targetfield="INCIDENT_RESOURCE_ID", sourcename="Id"
			, cascade=true)
	private Collection<IswIncidentResourceDailyCost> c1s = new ArrayList<IswIncidentResourceDailyCost>();

	@XmlTransferField(name = "C2", target=IswWorkPeriod.class
			, targettable="isw_work_period", targetfield="INCIDENT_RESOURCE_ID", sourcename="Id"
			, cascade=true)
	private IswWorkPeriod c2;
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

	public Long getB() {
		return b;
	}

	public void setB(Long b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public Long getD() {
		return d;
	}

	public void setD(Long d) {
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
	
	
}

