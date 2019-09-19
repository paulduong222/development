package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswIncidentGroupQSKind", table="isw_incident_group_qs_kind"
	, filtergroup=" incident_group_id = :INCIDENTGROUPID ")
public class IswIncidentGroupQSKind {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCIDENT_GROUP_QS_KIND", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="INCIDENT_GROUP_ID", type="STRING", updateable=false
			,derivedtable="isw_incident_group")
	private String a;

	@XmlTransferField(name = "B", sqlname="INCIDENT_GROUP_KIND_ID", type="STRING"
		, derivedtable="isw_incident_group")
	private String b;

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
	


	
}
