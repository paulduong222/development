package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswIncidentGroupIncident", table="isw_incident_group_incident"
	,filtergroup=" incident_group_id = :INCIDENTGROUPID "
)
public class IswIncidentGroupIncident {

	@XmlTransferField(name = "A", sqlname="INCIDENT_ID", type="STRING"
		,derivedtable="isw_incident")
	private String a;
	
	@XmlTransferField(name = "B", sqlname="INCIDENT_GROUP_ID", type="STRING"
		,derivedtable="isw_incident_group")
	private String b;
	
    public IswIncidentGroupIncident(){
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
