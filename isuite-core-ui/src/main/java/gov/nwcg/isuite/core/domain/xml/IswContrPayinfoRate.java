package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswContrPayinfoRate", table="isw_contr_payinfo_rate", jointable=true)
public class IswContrPayinfoRate {

	@XmlTransferField(name = "ContractorPayInfoTransferableIdentity", alias="cpiti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="ContractorPayInfoId"
						, disjoined=true, disjoinedtable="isw_contr_payment_info", disjoinedfield="transferable_identity",disjoinedsource="contractor_pay_info_id")
    private String contractorPayInfoTransferableIdentity;

	@XmlTransferField(name = "ContractorPayInfoId", sqlname="CONTRACTOR_PAY_INFO_ID", type="LONG"
						,joinkeyprimary=true)
	private Long contractorPayInfoId;
	
	@XmlTransferField(name = "ContractorRateTransferableIdentity", alias="crti", type="STRING"
					, lookupname="TransferableIdentity", sourcename="ContractorRateId"
					, disjoined=true, disjoinedtable="isw_contractor_rate", disjoinedfield="transferable_identity",disjoinedsource="contractor_rate_id")
	private String contractorRateTransferableIdentity;

	@XmlTransferField(name = "ContractorRate", type="COMPLEX", target=IswContractorRate.class
						, lookupname="Id", sourcename="ContractorRateId")
    private IswContractorRate contractorRate;

	@XmlTransferField(name = "ContractorRateId", sqlname="CONTRACTOR_RATE_ID", type="LONG"
						,derived=true, derivedfield="ContractorRate"
						,joinkeysecondary=true)
    private Long contractorRateId;
	

    public IswContrPayinfoRate(){
    }


	/**
	 * @return the contractorPayInfoId
	 */
	public Long getContractorPayInfoId() {
		return contractorPayInfoId;
	}


	/**
	 * @param contractorPayInfoId the contractorPayInfoId to set
	 */
	public void setContractorPayInfoId(Long contractorPayInfoId) {
		this.contractorPayInfoId = contractorPayInfoId;
	}


	/**
	 * @return the contractorRate
	 */
	public IswContractorRate getContractorRate() {
		return contractorRate;
	}


	/**
	 * @param contractorRate the contractorRate to set
	 */
	public void setContractorRate(IswContractorRate contractorRate) {
		this.contractorRate = contractorRate;
	}


	/**
	 * @return the contractorRateId
	 */
	public Long getContractorRateId() {
		return contractorRateId;
	}


	/**
	 * @param contractorRateId the contractorRateId to set
	 */
	public void setContractorRateId(Long contractorRateId) {
		this.contractorRateId = contractorRateId;
	}


	/**
	 * @return the contractorPayInfoTransferableIdentity
	 */
	public String getContractorPayInfoTransferableIdentity() {
		return contractorPayInfoTransferableIdentity;
	}


	/**
	 * @param contractorPayInfoTransferableIdentity the contractorPayInfoTransferableIdentity to set
	 */
	public void setContractorPayInfoTransferableIdentity(
			String contractorPayInfoTransferableIdentity) {
		this.contractorPayInfoTransferableIdentity = contractorPayInfoTransferableIdentity;
	}


	/**
	 * @return the contractorRateTransferableIdentity
	 */
	public String getContractorRateTransferableIdentity() {
		return contractorRateTransferableIdentity;
	}


	/**
	 * @param contractorRateTransferableIdentity the contractorRateTransferableIdentity to set
	 */
	public void setContractorRateTransferableIdentity(
			String contractorRateTransferableIdentity) {
		this.contractorRateTransferableIdentity = contractorRateTransferableIdentity;
	}

}
