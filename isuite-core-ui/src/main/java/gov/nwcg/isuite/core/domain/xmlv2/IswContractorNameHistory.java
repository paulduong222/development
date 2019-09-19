package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;


@XmlTransferTable(name = "IswContractorNameHistory", table = "isw_contractor_name_history")
public class IswContractorNameHistory {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_CONTRACTOR_NAME_HISTORY", type = "LONG")
	private Long id = 0L;

	/*
	@XmlTransferField(name = "Contractor", type = "COMPLEX", target = IswContractor.class, lookupname = "ID", sourcename = "ContractorId")
	private Contractor contractor;
	*/
	
	/*
	@XmlTransferField(name = "ContractorId", sqlname = "CONTRACTOR_ID", type = "LONG"
		, derived = true, derivedfield = "Contractor")
	private Long contractorId;
	*/
	
	@XmlTransferField(name = "ReasonText", sqlname = "REASON_TEXT", type = "STRING")
	private String reasonText;

	@XmlTransferField(name = "NewContractorName", sqlname = "NEW_CONTRACTOR_NAME", type = "STRING")
	private String newContractorName;

	@XmlTransferField(name = "OldContractorName", sqlname = "OLD_CONTRACTOR_NAME", type = "STRING")
	private String oldContractorName;

	public IswContractorNameHistory() {

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
	 * Returns the newContractorName
	 * 
	 * @return the newContractorName to return
	 */
	public String getNewContractorName() {
		return newContractorName;
	}

	/**
	 * Returns the oldContractorName
	 * 
	 * @return the oldContractorName to return
	 */
	public String getOldContractorName() {
		return oldContractorName;
	}

	/**
	 * Returns the reasonText
	 * 
	 * @return the reastonText to return
	 */
	public String getReasonText() {
		return reasonText;
	}

	/**
	 * Sets the newContractorName
	 * 
	 * @param newContractorName
	 *            the newContractorName to set
	 */
	public void setNewContractorName(String newContractorName) {
		this.newContractorName = newContractorName;
	}

	/**
	 * Sets the oldContractorName
	 * 
	 * @param oldContractorName
	 *            the oldContractorName to set
	 */
	public void setOldContractorName(String oldContractorName) {
		this.oldContractorName = oldContractorName;
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
