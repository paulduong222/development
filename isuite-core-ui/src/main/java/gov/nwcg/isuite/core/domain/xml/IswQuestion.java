package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswQuestion", table = "isw_question")
public class IswQuestion {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_QUESTION", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "QuestionType", sqlname = "QUESTION_TYPE", type="STRING")
	private String questionType;

	@XmlTransferField(name = "Question", sqlname = "QUESTION", type="STRING")
	private String question;
	
	@XmlTransferField(name = "Standard", sqlname = "IS_STANDARD", type="BOOLEAN")
	private Boolean standard;

	
	public IswQuestion() {
		super();
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
	public String getQuestionType() {
		return questionType;
	}

	/**
	 * @param questionType
	 */
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	/**
	 * @return
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return
	 */
	public Boolean isStandard() {
		return this.standard;
	}

	/**
	 * @param isStandard
	 */
	public void setStandard(Boolean isStandard) {
		this.standard = isStandard;
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
	 * @return the standard
	 */
	public Boolean getStandard() {
		return standard;
	}    

}
