package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswTrainingSettings", table = "isw_training_settings"
	,filterincident=" incident_id in (:INCIDENTID) "
	,filtergroup=" incident_group_id in (:INCIDENTGROUPID) "
)
public class IswTrainingSettings {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_TRAINING_SETTINGS", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="INCIDENT_ID", type="STRING",	
			  			derivedtable="isw_incident")
	private String a;

	@XmlTransferField(name = "B", sqlname="INCIDENT_GROUP_ID", type="STRING",	
  						derivedtable="isw_incident_group")
	private String b;

	@XmlTransferField(name = "C", sqlname="COMPLEXITY_ID", type="STRING",	
  						derivedtable="iswl_complexity")
	private String c;

	@XmlTransferField(name = "D", sqlname="NUMBER_OF_ACRES", type="LONG")
	private String d;


	/**
	 * Default constructor.
	 * 
	 */
	public IswTrainingSettings() {
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



}
