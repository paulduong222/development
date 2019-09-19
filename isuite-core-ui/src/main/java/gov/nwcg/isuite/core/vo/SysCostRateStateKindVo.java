package gov.nwcg.isuite.core.vo;

import edu.emory.mathcs.backport.java.util.Collections;
import gov.nwcg.isuite.core.domain.SysCostRateState;
import gov.nwcg.isuite.core.domain.SysCostRateStateKind;
import gov.nwcg.isuite.core.domain.impl.SysCostRateStateImpl;
import gov.nwcg.isuite.core.domain.impl.SysCostRateStateKindImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class SysCostRateStateKindVo extends AbstractVo {
	private KindVo kindVo;
	private String rateType;
	private BigDecimal rateAmount;
	private Long sysCostRateStateId;
	
	public SysCostRateStateKindVo(){
		super();
	}

	/**
	 * Returns a SysCostRateStateKindVo from a SysCostRateState entity.
	 * 
	 * @param entity
	 * 			the source SysCostRateStateKind entity
	 * @param cascadable
	 * 			flag indicating whether the vo instance should created as a cascadable vo
	 * @return
	 * 			instance of SysCostRateStateKindVo
	 * @throws Exception
	 */
	public static SysCostRateStateKindVo getInstance(SysCostRateStateKind entity,boolean cascadable) throws Exception{
		SysCostRateStateKindVo vo = new SysCostRateStateKindVo();

		if(null == entity)
			throw new Exception("Unable to create SysCostRateStateKindVo from null SysCostRateStateKind entity.");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setRateAmount(entity.getRateAmount());
			vo.setRateType(entity.getRateType());
			vo.setSysCostRateStateId(entity.getSysCostRateState().getId());
			
			if(null != entity.getKind()){
				vo.setKindVo(KindVo.getInstance(entity.getKind(),true));
			}
			
		}

		return vo;
	}

	/**
	 * Returns a collection of SysCostRateStateKindVos from a collection of SysCostRateStateKind entities.
	 * 
	 * @param entities
	 * 			the source collection of SysCostRateStateKind entities
	 * @param cascadable
	 * 			flag indicating whether the vo instances should created as a cascadable vos
	 * @return
	 * 			collection of SysCostRateStateKind vos
	 * @throws Exception
	 */
	public static Collection<SysCostRateStateKindVo> getInstances(Collection<SysCostRateStateKind> entities, boolean cascadable) throws Exception {
		Collection<SysCostRateStateKindVo> vos = new ArrayList<SysCostRateStateKindVo>();

		for(SysCostRateStateKind entity : entities){
			vos.add(SysCostRateStateKindVo.getInstance(entity, cascadable));
		}
		
		Collections.sort((List)vos, new KindVoComparator());
		
		return vos;
	}

	static class KindVoComparator implements Comparator{

		public int compare(Object data1, Object data2){

			SysCostRateStateKindVo vo1=(SysCostRateStateKindVo)data1;
			SysCostRateStateKindVo vo2=(SysCostRateStateKindVo)data2;
			
			return vo1.getKindVo().getCode().compareTo(vo2.getKindVo().getCode());
		}
	}
   
	
	/**
	 * Returns a SysCostRateStateKind entity from a SysCostRateStateKind vo.
	 * 
	 * @param vo
	 * 			the source SysCostRateStateKind vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of SysCostRateStateKind entity
	 * @throws Exception
	 */
	public static SysCostRateStateKind toEntity(SysCostRateStateKindVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		SysCostRateStateKind entity = new SysCostRateStateKindImpl();

		entity.setId(vo.getId());

		if(cascadable){

			entity.setRateAmount(vo.getRateAmount());
			entity.setRateType(vo.getRateType());

			SysCostRateState sysCostRateState = new SysCostRateStateImpl();
			sysCostRateState.setId(vo.getSysCostRateStateId());
			entity.setSysCostRateState(sysCostRateState);
			
			if(null != vo.getKindVo()){
				entity.setKind(KindVo.toEntity(null, vo.getKindVo(), false));
			}
			
			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

	/**
	 * Returns a collection of SysCostRateStateKind entities from a collection of SysCostRateStateKind vos.
	 * 
	 * @param vos
	 * 			the source collection of SysCostRateStateKind vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a cascadable entities
	 * @return
	 * 			collection of SysCostRateStateKind entities
	 * @throws Exception
	 */
	public static Collection<SysCostRateStateKind> toEntityList(Collection<SysCostRateStateKindVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<SysCostRateStateKind> entities = new ArrayList<SysCostRateStateKind>();

		for(SysCostRateStateKindVo vo : vos){
			entities.add(SysCostRateStateKindVo.toEntity(vo, cascadable, persistables));
		}

		return entities;
	}

	/**
	 * Perform some validation on the SysCostRateStateKind field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source SysCostRateStateKind entity
	 * @throws ValidationException
	 */
	private static void validateEntity(SysCostRateStateKind entity) throws ValidationException {
	}

	/**
	 * @return the kindVo
	 */
	public KindVo getKindVo() {
		return kindVo;
	}

	/**
	 * @param kindVo the kindVo to set
	 */
	public void setKindVo(KindVo kindVo) {
		this.kindVo = kindVo;
	}

	/**
	 * @return the rateType
	 */
	public String getRateType() {
		return rateType;
	}

	/**
	 * @param rateType the rateType to set
	 */
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	/**
	 * @return the rateAmount
	 */
	public BigDecimal getRateAmount() {
		return rateAmount;
	}

	/**
	 * @param rateAmount the rateAmount to set
	 */
	public void setRateAmount(BigDecimal rateAmount) {
		this.rateAmount = rateAmount;
	}

	/**
	 * @return the sysCostRateStateId
	 */
	public Long getSysCostRateStateId() {
		return sysCostRateStateId;
	}

	/**
	 * @param sysCostRateStateId the sysCostRateStateId to set
	 */
	public void setSysCostRateStateId(Long sysCostRateStateId) {
		this.sysCostRateStateId = sysCostRateStateId;
	}


}
