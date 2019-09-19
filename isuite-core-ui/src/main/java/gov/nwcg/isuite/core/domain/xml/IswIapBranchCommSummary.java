package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswIapBranchCommSummary", table = "isw_iap_branch_comm_summary")
public class IswIapBranchCommSummary {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_BRANCH_COMM_SUMMARY", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;

	@XmlTransferField(name = "IapBranchTransferableIdentity", alias="fti2", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IapBranchId"
		, disjoined=true, disjoinedtable="isw_iap_branch", disjoinedfield="transferable_identity",disjoinedsource="iap_branch_id")
	private String iapBranchTransferableIdentity;

	@XmlTransferField(name = "IapBranchId", sqlname="IAP_BRANCH_ID", type="LONG"
		,derived=true,derivedfield="IapBranchTransferableIdentity")
	private Long iapBranchId;
	
	@XmlTransferField(name = "Function", sqlname="FUNCTION", type="STRING")
	private String function;
	
	@XmlTransferField(name = "Rx", sqlname="RX", type="STRING")
	private String rx;
	
	@XmlTransferField(name = "Tx", sqlname="TX", type="STRING")
	private String tx;
	
	@XmlTransferField(name = "Tone", sqlname="TONE", type="STRING")
	private String tone;
	
	@XmlTransferField(name = "RxTone", sqlname="RX_TONE", type="STRING")
	private String rxTone;
	
	@XmlTransferField(name = "TxTone", sqlname="TX_TONE", type="STRING")
	private String txTone;
	
	@XmlTransferField(name = "mode", sqlname="SMODE", type="STRING")
	private String mode;

	@XmlTransferField(name = "System1", sqlname="SYSTEM_1", type="STRING")
	private String system1;
	
	@XmlTransferField(name = "Channel1", sqlname="CHANNEL_1", type="STRING")
	private String channel1;
	
	@XmlTransferField(name = "System2", sqlname="SYSTEM_2", type="STRING")
	private String system2;
	
	@XmlTransferField(name = "Channel2", sqlname="CHANNEL_2", type="STRING")
	private String channel2;
	
	@XmlTransferField(name = "PrimaryContact", sqlname="PRIMARY_CONTACT", type="STRING")
	private String primaryContact;
	
	@XmlTransferField(name = "CellNbr", sqlname="CELL_NBR", type="STRING")
	private String cellNbr;
	
	@XmlTransferField(name = "PagerNbr", sqlname="PAGER_NBR", type="STRING")
	private String pagerNbr;

//	/@Column(name = "MASTER_FREQ_ID")
	//private Long masterFreqId;
	
	@XmlTransferField(name = "PositionNum", sqlname="POSITION_NUM", type="INTEGER")
	private Integer positionNum;

	@XmlTransferField(name = "IsBlankLine", sqlname="IS_BLANK_LINE", type="STRING")
	private String isBlankLine;

	/**
	 * Default constructor.
	 * 
	 */
	public IswIapBranchCommSummary() {
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
	 * @return the iapBranchTransferableIdentity
	 */
	public String getIapBranchTransferableIdentity() {
		return iapBranchTransferableIdentity;
	}

	/**
	 * @param iapBranchTransferableIdentity the iapBranchTransferableIdentity to set
	 */
	public void setIapBranchTransferableIdentity(
			String iapBranchTransferableIdentity) {
		this.iapBranchTransferableIdentity = iapBranchTransferableIdentity;
	}

	/**
	 * @return the iapBranchId
	 */
	public Long getIapBranchId() {
		return iapBranchId;
	}

	/**
	 * @param iapBranchId the iapBranchId to set
	 */
	public void setIapBranchId(Long iapBranchId) {
		this.iapBranchId = iapBranchId;
	}

	/**
	 * @return the function
	 */
	public String getFunction() {
		return function;
	}

	/**
	 * @param function the function to set
	 */
	public void setFunction(String function) {
		this.function = function;
	}

	/**
	 * @return the rx
	 */
	public String getRx() {
		return rx;
	}

	/**
	 * @param rx the rx to set
	 */
	public void setRx(String rx) {
		this.rx = rx;
	}

	/**
	 * @return the tx
	 */
	public String getTx() {
		return tx;
	}

	/**
	 * @param tx the tx to set
	 */
	public void setTx(String tx) {
		this.tx = tx;
	}

	/**
	 * @return the tone
	 */
	public String getTone() {
		return tone;
	}

	/**
	 * @param tone the tone to set
	 */
	public void setTone(String tone) {
		this.tone = tone;
	}

	/**
	 * @return the rxTone
	 */
	public String getRxTone() {
		return rxTone;
	}

	/**
	 * @param rxTone the rxTone to set
	 */
	public void setRxTone(String rxTone) {
		this.rxTone = rxTone;
	}

	/**
	 * @return the txTone
	 */
	public String getTxTone() {
		return txTone;
	}

	/**
	 * @param txTone the txTone to set
	 */
	public void setTxTone(String txTone) {
		this.txTone = txTone;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the system1
	 */
	public String getSystem1() {
		return system1;
	}

	/**
	 * @param system1 the system1 to set
	 */
	public void setSystem1(String system1) {
		this.system1 = system1;
	}

	/**
	 * @return the channel1
	 */
	public String getChannel1() {
		return channel1;
	}

	/**
	 * @param channel1 the channel1 to set
	 */
	public void setChannel1(String channel1) {
		this.channel1 = channel1;
	}

	/**
	 * @return the system2
	 */
	public String getSystem2() {
		return system2;
	}

	/**
	 * @param system2 the system2 to set
	 */
	public void setSystem2(String system2) {
		this.system2 = system2;
	}

	/**
	 * @return the channel2
	 */
	public String getChannel2() {
		return channel2;
	}

	/**
	 * @param channel2 the channel2 to set
	 */
	public void setChannel2(String channel2) {
		this.channel2 = channel2;
	}

	/**
	 * @return the primaryContact
	 */
	public String getPrimaryContact() {
		return primaryContact;
	}

	/**
	 * @param primaryContact the primaryContact to set
	 */
	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}

	/**
	 * @return the cellNbr
	 */
	public String getCellNbr() {
		return cellNbr;
	}

	/**
	 * @param cellNbr the cellNbr to set
	 */
	public void setCellNbr(String cellNbr) {
		this.cellNbr = cellNbr;
	}

	/**
	 * @return the pagerNbr
	 */
	public String getPagerNbr() {
		return pagerNbr;
	}

	/**
	 * @param pagerNbr the pagerNbr to set
	 */
	public void setPagerNbr(String pagerNbr) {
		this.pagerNbr = pagerNbr;
	}

	/**
	 * @return the positionNum
	 */
	public Integer getPositionNum() {
		return positionNum;
	}

	/**
	 * @param positionNum the positionNum to set
	 */
	public void setPositionNum(Integer positionNum) {
		this.positionNum = positionNum;
	}

	/**
	 * @return the isBlankLine
	 */
	public String getIsBlankLine() {
		return isBlankLine;
	}

	/**
	 * @param isBlankLine the isBlankLine to set
	 */
	public void setIsBlankLine(String isBlankLine) {
		this.isBlankLine = isBlankLine;
	}



}
