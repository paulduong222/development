package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswIncidentGroupQuestion", table="isw_incident_group_question")
public class IswIncidentGroupQuestion {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCIDENT_GROUP_QUESTION", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;

	@XmlTransferField(name = "Question", type="COMPLEX", target=IswQuestion.class
					, lookupname="Id", sourcename="QuestionId")
	private IswQuestion question;

	@XmlTransferField(name = "QuestionId", sqlname="QUESTION_ID", type="LONG"
					,derived=true, derivedfield="Question")
	private Long questionId;

	@XmlTransferField(name = "IncidentGroupTransferableIdentity", alias="iti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IncidentGroupId"
		, disjoined=true, disjoinedtable="isw_incident_group", disjoinedfield="transferable_identity",disjoinedsource="incident_group_id")
	private String incidentGroupTransferableIdentity;

	@XmlTransferField(name = "IncidentGroupId", sqlname="INCIDENT_GROUP_ID", type="LONG"
		,joinkeyprimary=true)
	private Long incidentGroupId;

	@XmlTransferField(name = "Position", sqlname="POSITION", type="INTEGER")
	private Integer position;

	@XmlTransferField(name = "Visible", sqlname="IS_VISIBLE", type="BOOLEAN")
	private Boolean visible;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the question
	 */
	public IswQuestion getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(IswQuestion question) {
		this.question = question;
	}

	/**
	 * @return the questionId
	 */
	public Long getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}

	/**
	 * @return the visible
	 */
	public Boolean getVisible() {
		return visible;
	}

	/**
	 * @param visible the visible to set
	 */
	public void setVisible(Boolean visible) {
		this.visible = visible;
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

	/**
	 * @return the incidentGroupTransferableIdentity
	 */
	public String getIncidentGroupTransferableIdentity() {
		return incidentGroupTransferableIdentity;
	}

	/**
	 * @param incidentGroupTransferableIdentity the incidentGroupTransferableIdentity to set
	 */
	public void setIncidentGroupTransferableIdentity(
			String incidentGroupTransferableIdentity) {
		this.incidentGroupTransferableIdentity = incidentGroupTransferableIdentity;
	}

}
