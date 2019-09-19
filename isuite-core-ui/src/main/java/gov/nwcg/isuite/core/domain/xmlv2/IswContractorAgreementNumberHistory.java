package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswContractorAgreementNumberHistory", table = "isw_contracotr_agree_nbr_hist")
public class IswContractorAgreementNumberHistory {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_CONTRACTOR_AGREE_NBR_HIST", type = "LONG")
	private Long id = 0L;

	/*
	@XmlTransferField(name = "ContractorAgreement", type = "COMPLEX", target = IswContractorAgreement.class, lookupname = "ID", sourcename = "ContractorAgreementId")
	private ContractorAgreement contractorAgreement;

	@XmlTransferField(name = "ContractorAgreementId", sqlname = "CONTRACTOR_AGREEMENT_ID", type = "LONG", derived = true, derivedfield = "ContractorAgreement")
	private Long contractorAgreementId;
	*/
	
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

}
