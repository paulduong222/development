package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswIapAircraftFrequency", table = "isw_iap_aircraft_frequency")
public class IswIapAircraftFrequency {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_AIRCRAFT_FREQUENCY", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;

	@XmlTransferField(name = "IapForm220TransferableIdentity", alias="fti2", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IapForm220Id"
		, disjoined=true, disjoinedtable="isw_iap_form_220", disjoinedfield="transferable_identity",disjoinedsource="iap_form_220_id")
	private String iapForm220TransferableIdentity;

	@XmlTransferField(name = "IapForm220Id", sqlname="IAP_FORM_220_ID", type="LONG"
		,derived=true,derivedfield="IapForm220TransferableIdentity")
	private Long iapForm220Id;
	
	@XmlTransferField(name = "Frequency", sqlname="FREQUENCY", type="STRING")
	private String frequency;
	
	@XmlTransferField(name = "AmRxTx", sqlname="AM_RX_TX", type="STRING")
	private String amRxTx;
	
	@XmlTransferField(name = "FmRxTx", sqlname="FM_RX_TX", type="STRING")
	private String fmRxTx;
	
	@XmlTransferField(name = "AmTone", sqlname="AM_TONE", type="STRING")
	private String amTone;
	
	@XmlTransferField(name = "FmTone", sqlname="FM_TONE", type="STRING")
	private String fmTone;

	/**
	 * Default constructor.
	 * 
	 */
	public IswIapAircraftFrequency() {
	}

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
	 * @return the iapForm220TransferableIdentity
	 */
	public String getIapForm220TransferableIdentity() {
		return iapForm220TransferableIdentity;
	}

	/**
	 * @param iapForm220TransferableIdentity the iapForm220TransferableIdentity to set
	 */
	public void setIapForm220TransferableIdentity(
			String iapForm220TransferableIdentity) {
		this.iapForm220TransferableIdentity = iapForm220TransferableIdentity;
	}

	/**
	 * @return the iapForm220Id
	 */
	public Long getIapForm220Id() {
		return iapForm220Id;
	}

	/**
	 * @param iapForm220Id the iapForm220Id to set
	 */
	public void setIapForm220Id(Long iapForm220Id) {
		this.iapForm220Id = iapForm220Id;
	}

	/**
	 * @return the frequency
	 */
	public String getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return the amRxTx
	 */
	public String getAmRxTx() {
		return amRxTx;
	}

	/**
	 * @param amRxTx the amRxTx to set
	 */
	public void setAmRxTx(String amRxTx) {
		this.amRxTx = amRxTx;
	}

	/**
	 * @return the fmRxTx
	 */
	public String getFmRxTx() {
		return fmRxTx;
	}

	/**
	 * @param fmRxTx the fmRxTx to set
	 */
	public void setFmRxTx(String fmRxTx) {
		this.fmRxTx = fmRxTx;
	}

	/**
	 * @return the amTone
	 */
	public String getAmTone() {
		return amTone;
	}

	/**
	 * @param amTone the amTone to set
	 */
	public void setAmTone(String amTone) {
		this.amTone = amTone;
	}

	/**
	 * @return the fmTone
	 */
	public String getFmTone() {
		return fmTone;
	}

	/**
	 * @param fmTone the fmTone to set
	 */
	public void setFmTone(String fmTone) {
		this.fmTone = fmTone;
	}


}
