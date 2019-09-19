package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswIncidentQuestion", table="isw_incident_question"
	, filterincident=" incident_id in (:INCIDENTID) ")
public class IswIncidentQuestion {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCIDENT_QUESTION", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="INCIDENT_ID", type="STRING"
		,derivedtable="isw_incident")
	private String a;

	@XmlTransferField(name = "B", sqlname="POSITION", type="INTEGER")
	private Integer b;

	@XmlTransferField(name = "C", sqlname="IS_VISIBLE", type="BOOLEAN")
	private Boolean c;

	@XmlTransferField(name = "D", sqlname="QUESTION_ID", type="STRING"		
					  ,derivedtable="isw_question")
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

	public Integer getB() {
		return b;
	}

	public void setB(Integer b) {
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

}
