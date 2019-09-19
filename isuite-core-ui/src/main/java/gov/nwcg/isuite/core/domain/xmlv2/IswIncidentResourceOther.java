package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswIncidentResourceOther", table="isw_incident_resource_other"
	,filterincident=" incident_id in (:INCIDENTID) "
)
public class IswIncidentResourceOther {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCIDENT_RESOURCE_OTHER", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="INCIDENT_ID", type="STRING",updateable=false
			,derivedtable="isw_incident")
	private String a;

	@XmlTransferField(name = "B", sqlname = "ASSIGNMENT_STATUS", type="STRING")
	private String b;
	
	@XmlTransferField(name = "C", sqlname="RESOURCE_OTHER_ID", type="STRING"		
					  ,derivedtable="isw_resource_other")
	private String c;
	
	@XmlTransferField(name = "D", sqlname="COST_DATA_ID", type="STRING"		
		  ,derivedtable="isw_cost_data")	
	private String d;

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
