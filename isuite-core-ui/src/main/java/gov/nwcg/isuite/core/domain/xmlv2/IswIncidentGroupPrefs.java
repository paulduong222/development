package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswIncidentGroupPrefs", table="isw_incident_group_prefs"
	, filtergroup=" incident_group_id = :INCIDENTGROUPID ")
public class IswIncidentGroupPrefs {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCIDENT_GROUP_PREFS", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="INCIDENT_GROUP_ID", type="STRING",updateable=false
			,derivedtable="isw_incident_group")
	private String a;

	@XmlTransferField(name = "B", sqlname="SECTION_NAME", type="STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname="FIELD_LABEL", type="STRING")
	private String c;

	@XmlTransferField(name = "D", sqlname="POSITION", type="INTEGER")
	private Integer d;

	@XmlTransferField(name = "E", sqlname="IS_SELECTED", type="BOOLEAN")
	private Boolean e;

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

	public Integer getD() {
		return d;
	}

	public void setD(Integer d) {
		this.d = d;
	}

	public Boolean getE() {
		return e;
	}

	public void setE(Boolean e) {
		this.e = e;
	}

}
