package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswIapMasterFrequency", table="isw_iap_master_frequency")
public class IswIapMasterFrequency {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_MASTER_FREQUENCY", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "IncidentId", sqlname="INCIDENT_ID", type="LONG")
	private Long incidentId;
	
	@XmlTransferField(name = "IncidentGroupId", sqlname="INCIDENT_GROUP_ID", type="LONG")
	private Long incidentGroupId;

	@XmlTransferField(name = "Show", sqlname="SHOW", type="STRING")
	private String show;
	
	@XmlTransferField(name = "System", sqlname="SYSTEM", type="STRING",nullwhenempty=true)
	private String system;
	
	@XmlTransferField(name = "GroupName", sqlname="GROUP_NAME", type="STRING")
	private String group;
	
	@XmlTransferField(name = "Channel", sqlname="CHANNEL", type="STRING")
	private String channel;
	
	@XmlTransferField(name = "Rfunction", sqlname="RFUNCTION", type="STRING")
	private String rfunction;
	
	@XmlTransferField(name = "Rx", sqlname="RX", type="STRING")
	private String rx;
	
	@XmlTransferField(name = "Tx", sqlname="TX", type="STRING")
	private String tx;
	
	@XmlTransferField(name = "Tone", sqlname="TONE", type="STRING")
	private String tone;
	
	@XmlTransferField(name = "Assignment", sqlname="ASSIGNMENT", type="STRING")
	private String assignment;
	
	@XmlTransferField(name = "Remarks", sqlname="REMARKS", type="STRING")
	private String remarks;
	
	@XmlTransferField(name = "ChannelName", sqlname="CHANNEL_NAME_RADIO_TALKGROUP", type="STRING")
	private String channelName;
	
	@XmlTransferField(name = "RxFreq", sqlname="RX_FREQ_N_W", type="STRING")
	private String rxFreq;
	
	@XmlTransferField(name = "RxTone", sqlname="RX_TONE_NAC", type="STRING")
	private String rxTone;
	
	@XmlTransferField(name = "TxFreq", sqlname="TX_FREQ_N_W", type="STRING")
	private String txFreq;
	
	@XmlTransferField(name = "TxTone", sqlname="TX_TONE_NAC", type="STRING")
	private String txTone;
	
	@XmlTransferField(name = "Mode", sqlname="MODE_A_D_M", type="STRING")
	private String mode;

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
	 * @return the show
	 */
	public String getShow() {
		return show;
	}

	/**
	 * @param show the show to set
	 */
	public void setShow(String show) {
		this.show = show;
	}

	/**
	 * @return the system
	 */
	public String getSystem() {
		return system;
	}

	/**
	 * @param system the system to set
	 */
	public void setSystem(String system) {
		this.system = system;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @param channel the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * @return the rfunction
	 */
	public String getRfunction() {
		return rfunction;
	}

	/**
	 * @param rfunction the rfunction to set
	 */
	public void setRfunction(String rfunction) {
		this.rfunction = rfunction;
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
	 * @return the assignment
	 */
	public String getAssignment() {
		return assignment;
	}

	/**
	 * @param assignment the assignment to set
	 */
	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the channelName
	 */
	public String getChannelName() {
		return channelName;
	}

	/**
	 * @param channelName the channelName to set
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**
	 * @return the rxFreq
	 */
	public String getRxFreq() {
		return rxFreq;
	}

	/**
	 * @param rxFreq the rxFreq to set
	 */
	public void setRxFreq(String rxFreq) {
		this.rxFreq = rxFreq;
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
	 * @return the txFreq
	 */
	public String getTxFreq() {
		return txFreq;
	}

	/**
	 * @param txFreq the txFreq to set
	 */
	public void setTxFreq(String txFreq) {
		this.txFreq = txFreq;
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
