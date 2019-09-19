package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswIncidentGroupQuestion", table="isw_incident_group_question"
	, filtergroup=" incident_group_id = :INCIDENTGROUPID ")
public class IswIncidentGroupQuestion {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCIDENT_GROUP_QUESTION", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="QUESTION_ID", type="STRING"
					 ,derivedtable="isw_question")
	private String a;

	@XmlTransferField(name = "B", sqlname="INCIDENT_GROUP_ID", type="STRING"
		,derivedtable="isw_incident_group")
	private String b;

	@XmlTransferField(name = "C", sqlname="POSITION", type="INTEGER")
	private Integer c;

	@XmlTransferField(name = "D", sqlname="IS_VISIBLE", type="BOOLEAN")
	private Boolean d;

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

	public Integer getC() {
		return c;
	}

	public void setC(Integer c) {
		this.c = c;
	}

	public Boolean getD() {
		return d;
	}

	public void setD(Boolean d) {
		this.d = d;
	}

}
