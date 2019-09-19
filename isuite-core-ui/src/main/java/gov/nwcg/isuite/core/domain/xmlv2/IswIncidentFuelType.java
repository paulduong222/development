package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswIncidentFuelType", table = "isw_incident_fuel_type"
	,filterincident=" incident_id in (:INCIDENTID) "
)
public class IswIncidentFuelType {

	@XmlTransferField(name = "A", sqlname="INCIDENT_ID", type="STRING"
		,derivedtable="isw_incident")
	private String a;
	
	@XmlTransferField(name = "B", sqlname="FUEL_TYPE_ID", type="STRING"
		  ,derivedtable="iswl_fuel_type")
	private String b;

	/**
	 * Default constructor.
	 * 
	 */
	public IswIncidentFuelType() {
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
