package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswQuestion", table = "isw_question"
	,filterincident=" id in (select question_id from isw_incident_question where incident_id in ( :INCIDENTID ) ) "
	, filtergroup=" id in (select question_id from isw_incident_group_question where incident_group_id = :INCIDENTGROUPID ) ")
public class IswQuestion {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_QUESTION", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname = "QUESTION_TYPE", type="STRING")
	private String a;

	@XmlTransferField(name = "B", sqlname = "QUESTION", type="STRING")
	private String b;
	
	@XmlTransferField(name = "C", sqlname = "IS_STANDARD", type="BOOLEAN")
	private Boolean c;

	public IswQuestion() {
		super();
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

}
