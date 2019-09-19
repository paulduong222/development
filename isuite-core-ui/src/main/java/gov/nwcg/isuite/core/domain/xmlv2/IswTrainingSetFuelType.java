package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswTrainingSetFuelType", table = "isw_training_set_fuel_type"
	,filterincident=" training_settings_id in ( "+
				    "   select id "+
				    "   from isw_training_settings ts "+
				    "   where ts.incident_id in (:INCIDENTID) " +
				    ")"
	,filtergroup=" training_settings_id in ( "+
				    "   select id "+
				    "   from isw_training_settings ts "+
				    "   where ts.incident_group_id in (:INCIDENTGROUPID) " +
				    ")"
)
public class IswTrainingSetFuelType {

	@XmlTransferField(name = "A", sqlname="TRAINING_SETTINGS_ID", type="STRING",	
			  			derivedtable="isw_training_settings")
	private String a;

	@XmlTransferField(name = "B", sqlname="FUEL_TYPE_ID", type="STRING",	
					  derivedtable="iswl_fuel_type")
	private String b;


	/**
	 * Default constructor.
	 * 
	 */
	public IswTrainingSetFuelType() {
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
