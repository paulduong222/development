package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;


@XmlTransferTable(name = "IswWorkPeriodQuestionValue", table="isw_work_period_question_value"
	,filterincident=" incident_question_id in (" +
					"     select id " +
					"     from isw_incident_question " +
					"     where incident_id in (:INCIDENTID) " +
					") "
)
public class IswWorkPeriodQuestionValue {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_WORK_PERIOD_QUESTION_VALUE", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname="WORK_PERIOD_ID", type="STRING"
		,derivedtable="isw_work_period")
	private String a;
	
	@XmlTransferField(name = "B", sqlname = "QUESTION_VALUE", type="BOOLEAN")
	private Boolean b;

	@XmlTransferField(name = "C", sqlname="INCIDENT_QUESTION_ID", type="STRING"
		  ,derivedtable="isw_incident_question")
	private String c;
	
	public IswWorkPeriodQuestionValue() {
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

	public Boolean getB() {
		return b;
	}

	public void setB(Boolean b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

}
