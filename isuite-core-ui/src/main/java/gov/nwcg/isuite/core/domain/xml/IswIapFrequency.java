package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswIapFrequency", table = "isw_iap_frequency")
public class IswIapFrequency {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_FREQUENCY", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;

	@XmlTransferField(name = "IapForm205TransferableIdentity", alias="fti2", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IapForm205Id"
		, disjoined=true, disjoinedtable="isw_iap_form_205", disjoinedfield="transferable_identity",disjoinedsource="iap_form_205_id")
	private String iapForm205TransferableIdentity;

	@XmlTransferField(name = "IapForm205Id", sqlname="IAP_FORM_205_ID", type="LONG"
		,derived=true,derivedfield="IapForm205TransferableIdentity")
	private Long iapForm205Id;
	
	@XmlTransferField(name = "RadioType", sqlname="RADIO_TYPE", type="STRING")
	private String radioType;
	
	@XmlTransferField(name = "Channel", sqlname="CHANNEL", type="STRING")
	private String channel;
	
	@XmlTransferField(name = "Function", sqlname="FUNCTION", type="STRING")
	private String function;
	
	@XmlTransferField(name = "FrequencyRx", sqlname="FREQUENCY_RX", type="STRING")
	private String frequencyRx;
	
	@XmlTransferField(name = "ToneRx", sqlname="TONE_RX", type="STRING")
	private String toneRx;
	
	@XmlTransferField(name = "FrequencyTx", sqlname="FREQUENCY_TX", type="STRING")
	private String frequencyTx;
	
	@XmlTransferField(name = "ToneTx", sqlname="TONE_TX", type="STRING")
	private String toneTx;
	
	@XmlTransferField(name = "Assignment", sqlname="ASSIGNMENT", type="STRING")
	private String assignment;
	
	@XmlTransferField(name = "Remarks", sqlname="REMARKS", type="STRING")
	private String remarks;
	
	@XmlTransferField(name = "PreparedDate", sqlname="PREPARED_DATE", type="DATE")
	private Date preparedDate;
	
	@XmlTransferField(name = "ZoneGroup", sqlname="ZONE_GROUP", type="STRING")
	private String zoneGroup;
	
	@XmlTransferField(name = "ChannelName", sqlname="CHANNEL_NAME", type="STRING")
	private String channelName;
	
	@XmlTransferField(name = "ModeType", sqlname="MODE_TYPE", type="STRING")
	private String modeType;
	
	//@Column(name = "MASTER_FREQ_ID")
	//private Long masterFreqId;
	
	@XmlTransferField(name = "PositionNum", sqlname="POSITION_NUM", type="INTEGER")
	private Integer positionNum;

	@XmlTransferField(name = "IsBlankLine", sqlname="IS_BLANK_LINE", type="STRING")
	private String isBlankLine;

	/**
	 * Default constructor.
	 * 
	 */
	public IswIapFrequency() {
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
	 * @return the iapForm205TransferableIdentity
	 */
	public String getIapForm205TransferableIdentity() {
		return iapForm205TransferableIdentity;
	}

	/**
	 * @param iapForm205TransferableIdentity the iapForm205TransferableIdentity to set
	 */
	public void setIapForm205TransferableIdentity(
			String iapForm205TransferableIdentity) {
		this.iapForm205TransferableIdentity = iapForm205TransferableIdentity;
	}

	/**
	 * @return the iapForm205Id
	 */
	public Long getIapForm205Id() {
		return iapForm205Id;
	}

	/**
	 * @param iapForm205Id the iapForm205Id to set
	 */
	public void setIapForm205Id(Long iapForm205Id) {
		this.iapForm205Id = iapForm205Id;
	}

	/**
	 * @return the radioType
	 */
	public String getRadioType() {
		return radioType;
	}

	/**
	 * @param radioType the radioType to set
	 */
	public void setRadioType(String radioType) {
		this.radioType = radioType;
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
	 * @return the frequencyRx
	 */
	public String getFrequencyRx() {
		return frequencyRx;
	}

	/**
	 * @param frequencyRx the frequencyRx to set
	 */
	public void setFrequencyRx(String frequencyRx) {
		this.frequencyRx = frequencyRx;
	}

	/**
	 * @return the toneRx
	 */
	public String getToneRx() {
		return toneRx;
	}

	/**
	 * @param toneRx the toneRx to set
	 */
	public void setToneRx(String toneRx) {
		this.toneRx = toneRx;
	}

	/**
	 * @return the frequencyTx
	 */
	public String getFrequencyTx() {
		return frequencyTx;
	}

	/**
	 * @param frequencyTx the frequencyTx to set
	 */
	public void setFrequencyTx(String frequencyTx) {
		this.frequencyTx = frequencyTx;
	}

	/**
	 * @return the toneTx
	 */
	public String getToneTx() {
		return toneTx;
	}

	/**
	 * @param toneTx the toneTx to set
	 */
	public void setToneTx(String toneTx) {
		this.toneTx = toneTx;
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
	 * @return the preparedDate
	 */
	public Date getPreparedDate() {
		return preparedDate;
	}

	/**
	 * @param preparedDate the preparedDate to set
	 */
	public void setPreparedDate(Date preparedDate) {
		this.preparedDate = preparedDate;
	}

	/**
	 * @return the zoneGroup
	 */
	public String getZoneGroup() {
		return zoneGroup;
	}

	/**
	 * @param zoneGroup the zoneGroup to set
	 */
	public void setZoneGroup(String zoneGroup) {
		this.zoneGroup = zoneGroup;
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
	 * @return the modeType
	 */
	public String getModeType() {
		return modeType;
	}

	/**
	 * @param modeType the modeType to set
	 */
	public void setModeType(String modeType) {
		this.modeType = modeType;
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
