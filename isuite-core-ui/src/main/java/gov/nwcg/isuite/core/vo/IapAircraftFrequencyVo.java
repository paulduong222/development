package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.IapAircraftFrequency;
import gov.nwcg.isuite.core.domain.IapForm220;
import gov.nwcg.isuite.core.domain.impl.IapAircraftFrequencyImpl;
import gov.nwcg.isuite.core.domain.impl.IapForm220Impl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.util.StringUtility;

public class IapAircraftFrequencyVo extends AbstractVo implements PersistableVo {
	private Long iapForm220Id;
	private String frequency;
	private String amRxTx;
	private String fmRxTx;
	private String amTone;
	private String fmTone;
	
	/**
	 * Constructor
	 */
	public IapAircraftFrequencyVo() {
	}
	
	public static Collection<IapAircraftFrequencyVo> buildDefaultVos(IapForm220Vo vo) throws Exception {
		Collection<IapAircraftFrequencyVo> frequencyVos = new ArrayList<IapAircraftFrequencyVo>();
		frequencyVos.add(IapAircraftFrequencyVo.buildEmptyVo("Air/Air Fixed-Wing", vo));
		frequencyVos.add(IapAircraftFrequencyVo.buildEmptyVo("Air/Air Rotary-Wing - Flight Following", vo));
		frequencyVos.add(IapAircraftFrequencyVo.buildEmptyVo("Air/Ground", vo));
		frequencyVos.add(IapAircraftFrequencyVo.buildEmptyVo("Command", vo));
		frequencyVos.add(IapAircraftFrequencyVo.buildEmptyVo("Deck Coordinator", vo));
		frequencyVos.add(IapAircraftFrequencyVo.buildEmptyVo("Take-Off & Landing Coordinator", vo));
		frequencyVos.add(IapAircraftFrequencyVo.buildEmptyVo("Air Guard", vo));
		frequencyVos.add(IapAircraftFrequencyVo.buildEmptyVo("", vo));
		frequencyVos.add(IapAircraftFrequencyVo.buildEmptyVo("", vo));
		frequencyVos.add(IapAircraftFrequencyVo.buildEmptyVo("", vo));
		frequencyVos.add(IapAircraftFrequencyVo.buildEmptyVo("", vo));
		frequencyVos.add(IapAircraftFrequencyVo.buildEmptyVo("", vo));

		return frequencyVos;
	}
	
	public static IapAircraftFrequencyVo buildEmptyVo(String freq, IapForm220Vo form220Vo) throws Exception {
		IapAircraftFrequencyVo vo = new IapAircraftFrequencyVo();
		vo.setFrequency(freq);
		vo.setIapForm220Id(form220Vo.getId());
		
		return vo;
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static IapAircraftFrequencyVo getInstance(IapAircraftFrequency entity, boolean cascadable) throws Exception {
		IapAircraftFrequencyVo vo = new IapAircraftFrequencyVo();
		
		if(null == entity)
			throw new Exception("Unable to create IapAircraftFrequencyVo from null IapAircraftFrequency entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			vo.setIapForm220Id(entity.getIapForm220Id());
			vo.setFrequency(entity.getFrequency());
			vo.setAmRxTx(entity.getAmRxTx());
			vo.setFmRxTx(entity.getFmRxTx());
			vo.setAmTone(entity.getAmTone());
			vo.setFmTone(entity.getFmTone());
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<IapAircraftFrequencyVo> getInstances(Collection<IapAircraftFrequency> entities, boolean cascadable) throws Exception {
		Collection<IapAircraftFrequencyVo> vos = new ArrayList<IapAircraftFrequencyVo>();
		
		for(IapAircraftFrequency entity : entities) {
			vos.add(IapAircraftFrequencyVo.getInstance(entity, cascadable));
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
	public static IapAircraftFrequency toEntity(IapAircraftFrequency entity, IapAircraftFrequencyVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) entity = new IapAircraftFrequencyImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			entity.setFrequency(vo.getFrequency());
			entity.setAmRxTx(StringUtility.toUpper(vo.getAmRxTx()));
			entity.setFmRxTx(StringUtility.toUpper(vo.getFmRxTx()));
			entity.setAmTone(StringUtility.toUpper(vo.getAmTone()));
			entity.setFmTone(StringUtility.toUpper(vo.getFmTone()));
			
			IapForm220 form220=(IapForm220)AbstractVo.getPersistableObject(persistables, IapForm220Impl.class);
			if(null != form220){
				entity.setIapForm220(form220);
			}
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
	public static Collection<IapAircraftFrequency> toEntityList(Collection<IapAircraftFrequencyVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IapAircraftFrequency> entities = new ArrayList<IapAircraftFrequency>();
		
		for(IapAircraftFrequencyVo vo : vos) {
			entities.add(IapAircraftFrequencyVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return the frequency
	 */
	public String getFrequency() {
		return frequency;
	}

	/**
	 * @param amRxTx the amRxTx to set
	 */
	public void setAmRxTx(String amRxTx) {
		this.amRxTx = amRxTx;
	}

	/**
	 * @return the amRxTx
	 */
	public String getAmRxTx() {
		return amRxTx;
	}

	/**
	 * @param fmRxTx the fmRxTx to set
	 */
	public void setFmRxTx(String fmRxTx) {
		this.fmRxTx = fmRxTx;
	}

	/**
	 * @return the fmRxTx
	 */
	public String getFmRxTx() {
		return fmRxTx;
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
