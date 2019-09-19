package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswIncidentCostRate", table="isw_inccost_rate"
	,filterincident=" incident_id in ( :INCIDENTID ) "
	,filtergroup=" incident_group_id = :INCIDENTGROUPID ")
public class IswIncidentCostRate {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCCOST_RATE", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="COST_RATE_CATEGORY", type="STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname="INCIDENT_ID", type="STRING"
		,derivedtable="isw_incident")
	private String b;

	@XmlTransferField(name = "C", sqlname="INCIDENT_GROUP_ID", type="STRING"
		,derivedtable="isw_incident_group")
	private String c;

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
	
}
