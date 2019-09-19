package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.IapForm205;
import gov.nwcg.isuite.core.domain.IapFrequency;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * IapFrequency entity.
 */
@Entity
@Table(name = "isw_iap_frequency")
@SequenceGenerator(name="SEQ_IAP_FREQUENCY", sequenceName="SEQ_IAP_FREQUENCY")
public class IapFrequencyImpl extends PersistableImpl implements IapFrequency {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_IAP_FREQUENCY")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=IapForm205Impl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IAP_FORM_205_ID", insertable = true, updatable = true, nullable = false)
	private IapForm205 iapForm205;
	
	@Column(name = "IAP_FORM_205_ID", insertable = false, updatable = false, unique=false, nullable = false)
	private Long iapForm205Id;
	
	@Column(name = "RADIO_TYPE", length = 50)
	private String radioType;
	
	@Column(name = "CHANNEL", length = 50)
	private String channel;
	
	@Column(name = "FUNCTION", length = 50)
	private String function;
	
	@Column(name = "FREQUENCY_RX", length = 50)
	private String frequencyRx;
	
	@Column(name = "TONE_RX", length = 50)
	private String toneRx;
	
	@Column(name = "FREQUENCY_TX", length = 50)
	private String frequencyTx;
	
	@Column(name = "TONE_TX", length = 50)
	private String toneTx;
	
	@Column(name = "ASSIGNMENT", length = 50)
	private String assignment;
	
	@Column(name = "REMARKS", length = 50)
	private String remarks;
	
	@Column(name = "PREPARED_DATE")
	private Date preparedDate;
	
	@Column(name = "ZONE_GROUP", length = 50)
	private String zoneGroup;
	
	@Column(name = "CHANNEL_NAME", length = 50)
	private String channelName;
	
	@Column(name = "MODE_TYPE", length = 1)
	private String modeType;
	
	@Column(name = "MASTER_FREQ_ID")
	private Long masterFreqId;
	
    @Column(name = "POSITION_NUM", nullable = true)
	private Integer positionNum;

	@Column(name = "IS_BLANK_LINE", nullable = true)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isBlankLine;
	
	/** 
	 * Default constructor 
	 */
	public IapFrequencyImpl() {
		super();
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
	 * @param radioType the radioType to set
	 */
	public void setRadioType(String radioType) {
		this.radioType = radioType;
	}

	/**
	 * @return the radioType
	 */
	public String getRadioType() {
		return radioType;
	}

	/**
	 * @param channel the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @param function the function to set
	 */
	public void setFunction(String function) {
		this.function = function;
	}

	/**
	 * @return the function
	 */
	public String getFunction() {
		return function;
	}

	/**
	 * @param frequencyRx the frequencyRx to set
	 */
	public void setFrequencyRx(String frequencyRx) {
		this.frequencyRx = frequencyRx;
	}

	/**
	 * @return the frequencyRx
	 */
	public String getFrequencyRx() {
		return frequencyRx;
	}

	/**
	 * @param toneRx the toneRx to set
	 */
	public void setToneRx(String toneRx) {
		this.toneRx = toneRx;
	}

	/**
	 * @return the toneRx
	 */
	public String getToneRx() {
		return toneRx;
	}

	/**
	 * @param frequencyTx the frequencyTx to set
	 */
	public void setFrequencyTx(String frequencyTx) {
		this.frequencyTx = frequencyTx;
	}

	/**
	 * @return the frequencyTx
	 */
	public String getFrequencyTx() {
		return frequencyTx;
	}

	/**
	 * @param toneTx the toneTx to set
	 */
	public void setToneTx(String toneTx) {
		this.toneTx = toneTx;
	}

	/**
	 * @return the toneTx
	 */
	public String getToneTx() {
		return toneTx;
	}

	/**
	 * @param assignment the assignment to set
	 */
	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}

	/**
	 * @return the assignment
	 */
	public String getAssignment() {
		return assignment;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param preparedDate the preparedDate to set
	 */
	public void setPreparedDate(Date preparedDate) {
		this.preparedDate = preparedDate;
	}

	/**
	 * @return the preparedDate
	 */
	public Date getPreparedDate() {
		return preparedDate;
	}

	/**
	 * @param zoneGroup the zoneGroup to set
	 */
	public void setZoneGroup(String zoneGroup) {
		this.zoneGroup = zoneGroup;
	}

	/**
	 * @return the zoneGroup
	 */
	public String getZoneGroup() {
		return zoneGroup;
	}

	/**
	 * @param channelName the channelName to set
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**
	 * @return the channelName
	 */
	public String getChannelName() {
		return channelName;
	}

	/**
	 * @param modeType the modeType to set
	 */
	public void setModeType(String modeType) {
		this.modeType = modeType;
	}

	/**
	 * @return the modeType
	 */
	public String getModeType() {
		return modeType;
	}

	/**
	 * @return the iapForm205
	 */
	public IapForm205 getIapForm205() {
		return iapForm205;
	}

	/**
	 * @param iapForm205 the iapForm205 to set
	 */
	public void setIapForm205(IapForm205 iapForm205) {
		this.iapForm205 = iapForm205;
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
	 * @return the masterFreqId
	 */
	public Long getMasterFreqId() {
		return masterFreqId;
	}

	/**
	 * @param masterFreqId the masterFreqId to set
	 */
	public void setMasterFreqId(Long masterFreqId) {
		this.masterFreqId = masterFreqId;
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
	public StringBooleanEnum getIsBlankLine() {
		return isBlankLine;
	}

	/**
	 * @param isBlankLine the isBlankLine to set
	 */
	public void setIsBlankLine(StringBooleanEnum isBlankLine) {
		this.isBlankLine = isBlankLine;
	}

}
