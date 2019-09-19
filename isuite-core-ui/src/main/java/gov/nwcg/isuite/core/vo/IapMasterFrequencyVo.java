package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.IapMasterFrequency;
import gov.nwcg.isuite.core.domain.impl.IapMasterFrequencyImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.types.SystemTypeEnum;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

public class IapMasterFrequencyVo extends AbstractVo implements PersistableVo {
	private IncidentVo incidentVo;
	private IncidentGroupVo incidentGroupVo;
	private Boolean show;
	private SystemTypeVo systemTypeVo;
	private String group;
	private String channel;
	private String rfunction;
	private String rx;
	private String tx;
	private String tone;
	private String assignment;
	private String remarks;
	private String channelName;
	private String rxFreq;
	private String rxTone;
	private String txFreq;
	private String txTone;
	private String mode;
	private Integer positionNum;
	
	/**
	 * Constructor
	 */
	public IapMasterFrequencyVo() {
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static IapMasterFrequencyVo getInstance(IapMasterFrequency entity, boolean cascadable) throws Exception {
		IapMasterFrequencyVo vo = new IapMasterFrequencyVo();
		
		if(null == entity)
			throw new Exception("Unable to create IapMasterFrequencyVo from null IapMasterFrequency entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			if(null != entity.getIncident()) {
				vo.setIncidentVo(IncidentVo.getInstance(entity.getIncident(), false));
			}
			if(null != entity.getIncidentGroup()){
				vo.setIncidentGroupVo(IncidentGroupVo.getInstance(entity.getIncidentGroup(), false));
			}
			
			vo.setShow(entity.getShow().getValue());
			
			if(null != entity.getSystem()) {
				vo.setSystemTypeVo(SystemTypeEnum.getSystemTypeVoByCode(entity.getSystem().name()));
			}
			
			vo.setGroup(entity.getGroup());
			vo.setChannel(entity.getChannel());
			vo.setRfunction(entity.getRfunction());
			vo.setRx(entity.getRx());
			vo.setTx(entity.getTx());
			vo.setTone(entity.getTone());
			vo.setAssignment(entity.getAssignment());
			vo.setRemarks(entity.getRemarks());
			vo.setChannelName(entity.getChannelName());
			vo.setRxFreq(entity.getRxFreq());
			vo.setRxTone(entity.getRxTone());
			vo.setTxFreq(entity.getTxFreq());
			vo.setTxTone(entity.getTxTone());
			vo.setMode(entity.getMode());
			
			if(IntegerUtility.hasValue(entity.getPositionNum()))
				vo.setPositionNum(entity.getPositionNum());
			else
				vo.setPositionNum(new Integer(0));
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<IapMasterFrequencyVo> getInstances(Collection<IapMasterFrequency> entities, boolean cascadable) throws Exception {
		Collection<IapMasterFrequencyVo> vos = new ArrayList<IapMasterFrequencyVo>();
		
		for(IapMasterFrequency entity : entities) {
			vos.add(IapMasterFrequencyVo.getInstance(entity, cascadable));
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
	public static IapMasterFrequency toEntity(IapMasterFrequency entity, IapMasterFrequencyVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		
		if(null == entity) entity = new IapMasterFrequencyImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			if (null != vo.getIncidentVo()) {
				entity.setIncident(IncidentVo.toEntity(null, vo.getIncidentVo(), false));
			}
			if(null != vo.getIncidentGroupVo() ){
				entity.setIncidentGroup(IncidentGroupVo.toEntity(null, vo.getIncidentGroupVo(), false));
			}
			
			entity.setShow(StringBooleanEnum.toEnumValue(vo.getShow()));
			
			if(null != vo.getSystemTypeVo()) {
				entity.setSystem(SystemTypeEnum.valueOf(vo.getSystemTypeVo().getCode()));
			}
			
			entity.setGroup(StringUtility.toUpper(vo.getGroup()));
			entity.setChannel(StringUtility.toUpper(vo.getChannel()));
			entity.setRfunction(StringUtility.toUpper(vo.getRfunction()));
			entity.setRx(StringUtility.toUpper(vo.getRx()));
			entity.setTx(StringUtility.toUpper(vo.getTx()));
			entity.setTone(StringUtility.toUpper(vo.getTone()));
			entity.setAssignment(StringUtility.toUpper(vo.getAssignment()));
			entity.setRemarks(StringUtility.toUpper(vo.getRemarks()));
    		entity.setChannelName(StringUtility.toUpper(vo.getChannelName()));
			entity.setRxFreq(StringUtility.toUpper(vo.getRxFreq()));
			entity.setRxTone(StringUtility.toUpper(vo.getRxTone()));
			entity.setTxFreq(StringUtility.toUpper(vo.getTxFreq()));
			entity.setTxTone(StringUtility.toUpper(vo.getTxTone()));
			entity.setMode(StringUtility.toUpper(vo.getMode()));

			if(IntegerUtility.hasValue(vo.getPositionNum()))
				entity.setPositionNum(vo.getPositionNum());
			else
				entity.setPositionNum(new Integer(0));
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
	public static Collection<IapMasterFrequency> toEntityList(Collection<IapMasterFrequencyVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IapMasterFrequency> entities = new ArrayList<IapMasterFrequency>();
		
		for(IapMasterFrequencyVo vo : vos) {
			entities.add(IapMasterFrequencyVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}

	/**
	 * @param incidentVo the incidentVo to set
	 */
	public void setIncidentVo(IncidentVo incidentVo) {
		this.incidentVo = incidentVo;
	}

	/**
	 * @return the incidentVo
	 */
	public IncidentVo getIncidentVo() {
		return incidentVo;
	}
	
	/**
	 * @param incidentGroupVo the incidentGroupVo to set
	 */
	public void setIncidentGroupVo(IncidentGroupVo incidentGroupVo) {
		this.incidentGroupVo = incidentGroupVo;
	}

	/**
	 * @return the incidentGroupVo
	 */
	public IncidentGroupVo getIncidentGroupVo() {
		return incidentGroupVo;
	}	

	/**
	 * @param show the show to set
	 */
	public void setShow(Boolean show) {
		this.show = show;
	}

	/**
	 * @return the show
	 */
	public Boolean getShow() {
		return show;
	}

	/**
	 * @param systemTypeVo the systemTypeVo to set
	 */
	public void setSystemTypeVo(SystemTypeVo systemTypeVo) {
		this.systemTypeVo = systemTypeVo;
	}

	/**
	 * @return the systemTypeVo
	 */
	public SystemTypeVo getSystemTypeVo() {
		return systemTypeVo;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
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
	 * @param rfunction the rfunction to set
	 */
	public void setRfunction(String rfunction) {
		this.rfunction = rfunction;
	}

	/**
	 * @return the rfunction
	 */
	public String getRfunction() {
		return rfunction;
	}

	/**
	 * @param rx the rx to set
	 */
	public void setRx(String rx) {
		this.rx = rx;
	}

	/**
	 * @return the rx
	 */
	public String getRx() {
		return rx;
	}

	/**
	 * @param tx the tx to set
	 */
	public void setTx(String tx) {
		this.tx = tx;
	}

	/**
	 * @return the tx
	 */
	public String getTx() {
		return tx;
	}

	/**
	 * @param tone the tone to set
	 */
	public void setTone(String tone) {
		this.tone = tone;
	}

	/**
	 * @return the tone
	 */
	public String getTone() {
		return tone;
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
	 * @return the channelName
	 */
	public String getChannelName() {
		return channelName;
	}
	
	/**
	 * @param channelName
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
	 * @param rxFreq
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
	 * @param rxTone
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
	 * @param txFreq
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
	 * @param txTone
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
	 * @param mode
	 */
	public void setMode(String mode) {
		this.mode = mode;
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
