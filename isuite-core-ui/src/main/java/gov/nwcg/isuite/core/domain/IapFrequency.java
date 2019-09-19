package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.Date;

public interface IapFrequency extends Persistable {
	
	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);


	/**
	 * @param radioType the radioType to set
	 */
	public void setRadioType(String radioType);

	/**
	 * @return the radioType
	 */
	public String getRadioType();

	/**
	 * @param channel the channel to set
	 */
	public void setChannel(String channel);

	/**
	 * @return the channel
	 */
	public String getChannel();

	/**
	 * @param function the function to set
	 */
	public void setFunction(String function);

	/**
	 * @return the function
	 */
	public String getFunction();

	/**
	 * @param frequencyRx the frequencyRx to set
	 */
	public void setFrequencyRx(String frequencyRx);

	/**
	 * @return the frequencyRx
	 */
	public String getFrequencyRx();

	/**
	 * @param toneRx the toneRx to set
	 */
	public void setToneRx(String toneRx);

	/**
	 * @return the toneRx
	 */
	public String getToneRx();

	/**
	 * @param frequencyTx the frequencyTx to set
	 */
	public void setFrequencyTx(String frequencyTx);

	/**
	 * @return the frequencyTx
	 */
	public String getFrequencyTx();

	/**
	 * @param toneTx the toneTx to set
	 */
	public void setToneTx(String toneTx);

	/**
	 * @return the toneTx
	 */
	public String getToneTx();

	/**
	 * @param assignment the assignment to set
	 */
	public void setAssignment(String assignment);

	/**
	 * @return the assignment
	 */
	public String getAssignment();

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks);

	/**
	 * @return the remarks
	 */
	public String getRemarks();

	/**
	 * @param preparedDate the preparedDate to set
	 */
	public void setPreparedDate(Date preparedDate);

	/**
	 * @return the preparedDate
	 */
	public Date getPreparedDate();

	/**
	 * @param zoneGroup the zoneGroup to set
	 */
	public void setZoneGroup(String zoneGroup);

	/**
	 * @return the zoneGroup
	 */
	public String getZoneGroup();

	/**
	 * @param channelName the channelName to set
	 */
	public void setChannelName(String channelName);

	/**
	 * @return the channelName
	 */
	public String getChannelName();

	/**
	 * @param modeType the modeType to set
	 */
	public void setModeType(String modeType);

	/**
	 * @return the modeType
	 */
	public String getModeType();

	/**
	 * @return the iapForm205
	 */
	public IapForm205 getIapForm205() ;

	/**
	 * @param iapForm205 the iapForm205 to set
	 */
	public void setIapForm205(IapForm205 iapForm205) ;

	/**
	 * @return the iapForm205Id
	 */
	public Long getIapForm205Id() ;

	/**
	 * @param iapForm205Id the iapForm205Id to set
	 */
	public void setIapForm205Id(Long iapForm205Id);	

	/**
	 * @return the masterFreqId
	 */
	public Long getMasterFreqId();

	/**
	 * @param masterFreqId the masterFreqId to set
	 */
	public void setMasterFreqId(Long masterFreqId);

	/**
	 * @return the positionNum
	 */
	public Integer getPositionNum();

	/**
	 * @param positionNum the positionNum to set
	 */
	public void setPositionNum(Integer positionNum);

	/**
	 * @return the isBlankLine
	 */
	public StringBooleanEnum getIsBlankLine();

	/**
	 * @param isBlankLine the isBlankLine to set
	 */
	public void setIsBlankLine(StringBooleanEnum isBlankLine);
	
}
