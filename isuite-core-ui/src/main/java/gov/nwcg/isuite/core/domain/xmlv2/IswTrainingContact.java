package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswTrainingContact", table = "isw_training_contact"
	,filterincident=" incident_resource_id in ( "+
				    "   select id "+
				    "   from isw_incident_resource ir2"+
				    "   where ir2.incident_id in (:INCIDENTID) " +
				    ")"
)
public class IswTrainingContact {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_TRAINING_CONTACT", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="INCIDENT_RESOURCE_ID", type="STRING",	
			  			derivedtable="isw_incident_resource")
	private String a;

	@XmlTransferField(name = "B", sqlname="ADDRESS_ID", type="STRING",	
					  derivedtable="isw_address")
	private String b;

	@XmlTransferField(name = "C", sqlname="PHONE", type="STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname="EMAIL", type="STRING")
	private String d;

	@XmlTransferField(name = "E", sqlname="IS_ACTIVE", type="STRING")
	private String e;

	/**
	 * Default constructor.
	 * 
	 */
	public IswTrainingContact() {
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

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}



}
