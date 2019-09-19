package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswIncidentQuestion", table="isw_incident_question")
public class IswIncidentQuestion {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_INCIDENT_QUESTION", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	//@XmlTransferField(name = "Question", type="COMPLEX", target=IswQuestion.class
	//				, lookupname="Id", sourcename="QuestionId")
	//private IswQuestion question;

	@XmlTransferField(name = "QuestionTransferableIdentity", alias="qti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="QuestionId"
		, disjoined=true, disjoinedtable="isw_question", disjoinedfield="transferable_identity",disjoinedsource="QUESTION_ID")
	private String questionTransferableIdentity;

	@XmlTransferField(name = "QuestionId", sqlname="QUESTION_ID", type="LONG"
						,derived=true, derivedfield="QuestionTransferableIdentity")
	private Long questionId;
	
	@XmlTransferField(name = "IncidentTransferableIdentity", alias="iti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IncidentId"
		, disjoined=true, disjoinedtable="isw_incident", disjoinedfield="transferable_identity",disjoinedsource="incident_id")
	private String incidentTransferableIdentity;

	@XmlTransferField(name = "IncidentId", sqlname="INCIDENT_ID", type="LONG"
		,joinkeyprimary=true)
	private Long incidentId;

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
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
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
	 * @return the incidentTransferableIdentity
	 */
	public String getIncidentTransferableIdentity() {
		return incidentTransferableIdentity;
	}

	/**
	 * @param incidentTransferableIdentity the incidentTransferableIdentity to set
	 */
	public void setIncidentTransferableIdentity(String incidentTransferableIdentity) {
		this.incidentTransferableIdentity = incidentTransferableIdentity;
	}

	/**
	 * @return the questionTransferableIdentity
	 */
	public String getQuestionTransferableIdentity() {
		return questionTransferableIdentity;
	}

	/**
	 * @param questionTransferableIdentity the questionTransferableIdentity to set
	 */
	public void setQuestionTransferableIdentity(String questionTransferableIdentity) {
		this.questionTransferableIdentity = questionTransferableIdentity;
	}

}
