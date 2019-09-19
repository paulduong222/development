package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswlJetPort", table = "iswl_jet_port"
	, filterincident=" is_standard = :FALSE and incident_id in (:INCIDENTID) " 
	, filtergroup=" is_standard = :FALSE and incident_group_id = :INCIDENTGROUPID "
)
public class IswlJetPort {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_JETPORT", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="CODE", type="STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname="DESCRIPTION", type="STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname="IS_STANDARD", type="BOOLEAN")
	private Boolean c;

	@XmlTransferField(name = "D", sqlname="INCIDENT_ID", type="STRING",updateable=false
			,derivedtable="isw_incident")
	private String d;

	@XmlTransferField(name = "E", sqlname = "INCIDENT_GROUP_ID", type="STRING",updateable=false
			,derivedtable="isw_incident_group")
	private String e;

	@XmlTransferField(name = "F", sqlname = "IS_ACTIVE", type="STRING")
	private String f;

	@XmlTransferField(name = "G", sqlname = "OBSOLETE", type="STRING")
	private String g;
	
	/**
	 * Default Constructor
	 */
	public IswlJetPort() {
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

	public Boolean getC() {
		return c;
	}

	public void setC(Boolean c) {
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

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}

	public String getG() {
		return g;
	}

	public void setG(String g) {
		this.g = g;
	}

}
