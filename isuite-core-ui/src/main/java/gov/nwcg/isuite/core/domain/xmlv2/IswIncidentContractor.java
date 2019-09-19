package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswIncidentContractor", table="isw_incident_contractor"
	,filterincident=" incident_id in ( :INCIDENTID) "
)
public class IswIncidentContractor {

	@XmlTransferField(name = "A", sqlname="INCIDENT_ID", type="STRING"
						,derivedtable="isw_incident")
	private String a;

	@XmlTransferField(name = "B", sqlname="CONTRACTOR_ID", type="STRING"		
					  ,derivedtable="isw_contractor")
	private String b;
	
    public IswIncidentContractor(){
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
