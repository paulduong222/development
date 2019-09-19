package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;


@XmlTransferTable(name = "IswWorkPeriodQuestionValue", table="isw_work_period_question_value")
public class IswWorkPeriodQuestionValue {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_WORK_PERIOD_QUESTION_VALUE", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "WorkPeriod", type="COMPLEX", target=IswWorkPeriod.class
				, lookupname="ID", sourcename="WorkPeriodId")
	private IswWorkPeriod workPeriod;
	
	@XmlTransferField(name = "WorkPeriodId", sqlname="WORK_PERIOD_ID", type="LONG"
			,derived=true, derivedfield="WorkPeriod")
	private Long workPeriodId;
	
//	@XmlTransferField(name = "IncidentQuestion", type="COMPLEX", target=IswIncidentQuestion.class
//				, lookupname="ID", sourcename="IncidentQuestionId")
//	private IswIncidentQuestion incidentQuestion;
	
	@XmlTransferField(name = "IncidentQuestionId", sqlname="INCIDENT_QUESTION_ID", type="LONG"
			,derived=true, derivedfield="IncidentQuestion")
	private Long incidentQuestionId;
	
	@XmlTransferField(name = "QuestionValue", sqlname = "QUESTION_VALUE", type="BOOLEAN")
	private Boolean questionValue;

	public IswWorkPeriodQuestionValue() {
	}

	/**
	 * @return
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	} 

	/**
	 * @return
	 */
	public IswWorkPeriod getWorkPeriod() {
		return workPeriod;
	}

	/**
	 * @param workPeriod
	 */
	public void setWorkPeriod(IswWorkPeriod workPeriod) {
		this.workPeriod = workPeriod;
	}

	/**
	 * @return
	 */
	public Long getWorkPeriodId() {
		return workPeriodId;
	}

	/**
	 * @param workPeriodId
	 */
	public void setWorkPeriodId(Long workPeriodId) {
		this.workPeriodId = workPeriodId;
	}

	/**
	 * @return
	 */
	public Long getIncidentQuestionId() {
		return incidentQuestionId;
	}

	/**
	 * @param incidentQuestionId
	 */
	public void setIncidentQuestionId(Long incidentQuestionId) {
		this.incidentQuestionId = incidentQuestionId;
	}

	/**
	 * @return
	 */
	public Boolean getQuestionValue() {
		return questionValue;
	}

	/**
	 * @param questionValue
	 */
	public void setQuestionValue(Boolean questionValue) {
		this.questionValue = questionValue;
	}

	/**
	 * @return the transferableIdentity
	 */
	public String getTransferableIdentity() {
		return transferableIdentity;
	}

	/**
	 * @param transferableIdentity the transferableIdentity to set
	 */
	public void setTransferableIdentity(String transferableIdentity) {
		this.transferableIdentity = transferableIdentity;
	}

}
