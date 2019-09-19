package gov.nwcg.isuite.core.domain.xmlv2;

import java.util.Date;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswRscTrainingFuelType", table = "isw_rsc_training_fuel_type"
	,filterincident=" resource_training_id in ( "+
				    "   select rt.id "+
				    "   from isw_resource_training rt "+
				    "   where rt.incident_resource_id in ("+
				    "      select id from isw_incident_resource where incident_id in (:INCIDENTID) " +
				    "   ) "+
				    ")"
)
public class IswRscTrainingFuelType {

	@XmlTransferField(name = "A", sqlname="RESOURCE_TRAINING_ID", type="STRING"
		,derivedtable="isw_resource_training")
	private String a;
	
	@XmlTransferField(name = "B", sqlname="FUEL_TYPE_ID", type="STRING"
		  ,derivedtable="iswl_fuel_type")
	private String b;

	/**
	 * Default constructor.
	 * 
	 */
	public IswRscTrainingFuelType() {
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
