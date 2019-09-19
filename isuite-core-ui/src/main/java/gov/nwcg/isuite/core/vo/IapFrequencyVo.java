package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IapForm205;
import gov.nwcg.isuite.core.domain.IapFrequency;
import gov.nwcg.isuite.core.domain.impl.IapForm205Impl;
import gov.nwcg.isuite.core.domain.impl.IapFrequencyImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class IapFrequencyVo extends AbstractVo implements PersistableVo {
	private Long iapForm205Id;
	private String radioType;
	private String channel;
	private String sfunction;
	private String frequencyRx;
	private String toneRx;
	private String frequencyTx;
	private String toneTx;
	private String assignment;
	private String remarks;
	private Date preparedDate;
	private String zoneGroup;
	private String channelName;
	private String modeType;
	private Long masterFreqId;
	private Boolean isBlankLine=false;
	private Integer positionNum;
	
	/** 
	 * Constructor 
	 */
	public IapFrequencyVo() {
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static IapFrequencyVo getInstance(IapFrequency entity, boolean cascadable) throws Exception {
		IapFrequencyVo vo = new IapFrequencyVo();
		
		if(null == entity)
			throw new Exception("Unable to create IapFrequencyVo from null IapFrequency entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			vo.setIapForm205Id(entity.getIapForm205Id());
			vo.setRadioType(entity.getRadioType());
			vo.setChannel(entity.getChannel());
			vo.setSfunction(entity.getFunction());
			vo.setFrequencyRx(entity.getFrequencyRx());
			vo.setToneRx(entity.getToneRx());
			vo.setFrequencyTx(entity.getFrequencyTx());
			vo.setToneTx(entity.getToneTx());
			vo.setAssignment(entity.getAssignment());
			vo.setRemarks(entity.getRemarks());
			vo.setPreparedDate(entity.getPreparedDate());
			vo.setZoneGroup(entity.getZoneGroup());
			vo.setChannelName(entity.getChannelName());
			vo.setModeType(entity.getModeType());
			vo.setMasterFreqId(entity.getMasterFreqId());
			
			if(IntegerUtility.hasValue(entity.getPositionNum()))
				vo.setPositionNum(entity.getPositionNum());
			else
				vo.setPositionNum(new Integer(0));

			if(null != entity.getIsBlankLine())
				vo.setIsBlankLine(entity.getIsBlankLine().getValue());
			else
				vo.setIsBlankLine(false);
			
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<IapFrequencyVo> getInstances(Collection<IapFrequency> entities, boolean cascadable) throws Exception {
		Collection<IapFrequencyVo> vos = new ArrayList<IapFrequencyVo>();
		
		for(IapFrequency entity : entities) {
			vos.add(IapFrequencyVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	/**
	 * @param entity
	 * @param vo
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static IapFrequency toEntity(IapFrequency entity, IapFrequencyVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) entity = new IapFrequencyImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			
			IapForm205 form205=(IapForm205Impl)AbstractVo.getPersistableObject(persistables, IapForm205Impl.class);
			if(null != form205){
				entity.setIapForm205(form205);
//				IapForm205 form205Entity = new IapForm205Impl();
//				form205Entity.setId(form205.getId());
//				entity.setIapForm205(form205Entity);
			}
						
			entity.setRadioType(StringUtility.toUpper(vo.getRadioType()));
			entity.setChannel(StringUtility.toUpper(vo.getChannel()));
			entity.setFunction(StringUtility.toUpper(vo.getSfunction()));
			entity.setFrequencyRx(StringUtility.toUpper(vo.getFrequencyRx()));
			entity.setToneRx(StringUtility.toUpper(vo.getToneRx()));
			entity.setFrequencyTx(StringUtility.toUpper(vo.getFrequencyTx()));
			entity.setToneTx(StringUtility.toUpper(vo.getToneTx()));
			entity.setAssignment(StringUtility.toUpper(vo.getAssignment()));
			entity.setRemarks(vo.getRemarks());
			//entity.setRemarks(StringUtility.toUpper(vo.getRemarks()));
			entity.setPreparedDate(vo.getPreparedDate());
			entity.setZoneGroup(StringUtility.toUpper(vo.getZoneGroup()));
			entity.setChannelName(StringUtility.toUpper(vo.getChannelName()));
			entity.setModeType(StringUtility.toUpper(vo.getModeType()));
			entity.setMasterFreqId(vo.getMasterFreqId());
			
			if(IntegerUtility.hasValue(vo.getPositionNum()))
				entity.setPositionNum(vo.getPositionNum());
			else
				entity.setPositionNum(new Integer(0));

			entity.setIsBlankLine(StringBooleanEnum.toEnumValue(vo.getIsBlankLine()));
			if ( BooleanUtility.isTrue(vo.getIsBlankLine()))
				entity.setZoneGroup("");
		}
		
		return entity;
	}
	
	/**
	 * @param vos
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static Collection<IapFrequency> toEntityList(Collection<IapFrequencyVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IapFrequency> entities = new ArrayList<IapFrequency>();
		
		for(IapFrequencyVo vo : vos) {
			entities.add(IapFrequencyVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
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
	public void setSfunction(String function) {
		this.sfunction = function;
	}

	/**
	 * @return the function
	 */
	public String getSfunction() {
		return sfunction;
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
	 * @return the isBlankLine
	 */
	public Boolean getIsBlankLine() {
		return isBlankLine;
	}

	/**
	 * @param isBlankLine the isBlankLine to set
	 */
	public void setIsBlankLine(Boolean isBlankLine) {
		this.isBlankLine = isBlankLine;
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

}
