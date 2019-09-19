package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswContractorAgreementNumberHistory", table = "isw_contracotr_agree_nbr_hist")
public class IswContractorAgreementNumberHistory {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_CONTRACTOR_AGREE_NBR_HIST", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "ContractorAgreement", type = "COMPLEX", target = IswContractorAgreement.class, lookupname = "ID", sourcename = "ContractorAgreementId")
	private ContractorAgreement contractorAgreement;

	@XmlTransferField(name = "ContractorAgreementId", sqlname = "CONTRACTOR_AGREEMENT_ID", type = "LONG", derived = true, derivedfield = "ContractorAgreement")
	private Long contractorAgreementId;

	@XmlTransferField(name = "ReasonText", sqlname = "REASON_TEXT", type="STRING")
	private String reasonText;

	@XmlTransferField(name = "NewAgreementNumber", sqlname = "NEW_AGREEMENT_NUMBER", type="STRING")
	private String newAgreementNumber;

	@XmlTransferField(name = "OldAgreementNumber", sqlname = "OLD_AGREEMENT_NUMBER", type="STRING")
	private String oldAgreementNumber;

	public IswContractorAgreementNumberHistory() {

	}

	/**
	 * Returns the id.
	 * 
	 * @return the id to return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the contractorAgreement
	 * 
	 * @return the contractorAgreement to return
	 */
	public ContractorAgreement getContractorAgreement() {
		return contractorAgreement;
	}

	/**
	 * Returns the contractorAgreementId
	 * 
	 * @return the contractorAgreeementId to return
	 */
	public Long getContractorAgreementId() {
		return contractorAgreementId;
	}

	/**
	 * Returns the newAgreementNumber
	 * 
	 * @return the newAgreementNumber to return
	 */
	public String getNewAgreementNumber() {
		return newAgreementNumber;
	}

	/**
	 * Returns the oldAgreementNumber
	 * 
	 * @return the oldAgreementNumber to return
	 */
	public String getOldAgreementNumber() {
		return oldAgreementNumber;
	}

	/**
	 * Returns the reasonText
	 * 
	 * @return the reasonText to return
	 */
	public String getReasonText() {
		return reasonText;
	}

	/**
	 * Sets the contractorAgreement
	 * 
	 * @param contractorAgreement
	 *            the contractorAgreement to set
	 */
	public void setContractorAgreement(ContractorAgreement contractorAgreement) {
		this.contractorAgreement = contractorAgreement;
	}

	/**
	 * Sets the contractorAgreementId
	 * 
	 * @param contractorAgreementId
	 *            the contractorAgreementId to set
	 */
	public void setContractorAgreementId(Long contractorAgreementId) {
		this.contractorAgreementId = contractorAgreementId;
	}

	/**
	 * Sets the newAgreementNumber
	 * 
	 * @param newAgreementNumber
	 *            the newAgreementNumber to set
	 */
	public void setNewAgreementNumber(String newAgreementNumber) {
		this.newAgreementNumber = newAgreementNumber;
	}

	/**
	 * Sets the oldAgreementNumber
	 * 
	 * @param oldAgreementNumber
	 *            the oldAgreementNumber to set
	 */
	public void setOldAgreementNumber(String oldAgreementNumber) {
		this.oldAgreementNumber = oldAgreementNumber;
	}

	/**
	 * Sets the reasonText
	 * 
	 * @param reasonText
	 *            the reasonText to set
	 */
	public void setReasonText(String reasonText) {
		this.reasonText = reasonText;
	}

}
