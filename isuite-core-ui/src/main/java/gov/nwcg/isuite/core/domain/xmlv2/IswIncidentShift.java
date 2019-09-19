package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswIncidentShift", table="isw_incident_shift"
	, filterincident=" incident_id in (:INCIDENTID) ")
public class IswIncidentShift {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCIDENT_SHIFT", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="SHIFT_NAME", type="STRING")
	private String a;
		
	@XmlTransferField(name = "B", sqlname="INCIDENT_ID", type="STRING",updateable=false
			,derivedtable="isw_incident")
	private String b;	
			
	@XmlTransferField(name = "C", sqlname="START_TIME", type="STRING")
	private String c;
	
	@XmlTransferField(name = "D", sqlname="END_TIME", type="STRING")
	private String d;
	
	public IswIncidentShift() {
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

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	
}