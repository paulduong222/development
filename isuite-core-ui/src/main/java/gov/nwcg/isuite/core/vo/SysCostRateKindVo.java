package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.SysCostRate;
import gov.nwcg.isuite.core.domain.SysCostRateKind;
import gov.nwcg.isuite.core.domain.impl.SysCostRateImpl;
import gov.nwcg.isuite.core.domain.impl.SysCostRateKindImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class SysCostRateKindVo extends AbstractVo {
	private String rateType;
	private BigDecimal rateAmount;
	private KindVo kindVo;
	private Long sysCostRateId;
	
	public SysCostRateKindVo(){
		super();
	}

	/**
	 * Returns a SysCostRateKindVo from a SysCostRateKind entity.
	 * 
	 * @param entity
	 * 			the source SysCostRateKind entity
	 * @param cascadable
	 * 			flag indicating whether the vo instance should created as a cascadable vo
	 * @return
	 * 			instance of SysCostRateKindVo
	 * @throws Exception
	 */
	public static SysCostRateKindVo getInstance(SysCostRateKind entity,boolean cascadable) throws Exception{
		SysCostRateKindVo vo = new SysCostRateKindVo();

		if(null == entity)
			throw new Exception("Unable to create SysCostRateKindVo from null SysCostRateKind entity.");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setRateType(entity.getRateType());
			vo.setRateAmount(entity.getRateAmount());
			vo.setKindVo(KindVo.getInstance(entity.getKind(),true));
			vo.setSysCostRateId(entity.getSysCostRate().getId());
		}

		return vo;
	}

	/**
	 * Returns a collection of SysCostRateKindVos from a collection of SysCostRateKind entities.
	 * 
	 * @param entities
	 * 			the source collection of SysCostRateKind entities
	 * @param cascadable
	 * 			flag indicating whether the vo instances should created as a cascadable vos
	 * @return
	 * 			collection of SysCostRateKind vos
	 * @throws Exception
	 */
	public static Collection<SysCostRateKindVo> getInstances(Collection<SysCostRateKind> entities, boolean cascadable) throws Exception {
		Collection<SysCostRateKindVo> vos = new ArrayList<SysCostRateKindVo>();

		for(SysCostRateKind entity : entities){
			vos.add(SysCostRateKindVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	/**
	 * Returns a SysCostRateKind entity from a SysCostRateKind vo.
	 * 
	 * @param vo
	 * 			the source SysCostRateKind vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of SysCostRateKind entity
	 * @throws Exception
	 */
	public static SysCostRateKind toEntity(SysCostRateKindVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		SysCostRateKind entity = new SysCostRateKindImpl();

		entity.setId(vo.getId());

		if(cascadable){
			entity.setRateType(vo.getRateType());
			entity.setRateAmount(vo.getRateAmount());

			SysCostRate sysCostRate = new SysCostRateImpl();
			sysCostRate.setId(vo.getSysCostRateId());
			entity.setSysCostRate(sysCostRate);
			
			if(null != vo.getKindVo()){
				entity.setKind(KindVo.toEntity(null, vo.getKindVo(), false,entity));
			}

			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

	/**
	 * Returns a collection of SysCostRateKind entities from a collection of SysCostRateKind vos.
	 * 
	 * @param vos
	 * 			the source collection of SysCostRateKind vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a cascadable entities
	 * @return
	 * 			collection of SysCostRateKind entities
	 * @throws Exception
	 */
	public static Collection<SysCostRateKind> toEntityList(Collection<SysCostRateKindVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<SysCostRateKind> entities = new ArrayList<SysCostRateKind>();

		for(SysCostRateKindVo vo : vos){
			entities.add(SysCostRateKindVo.toEntity(vo, cascadable, persistables));
		}

		return entities;
	}

	/**
	 * Perform some validation on the SysCostRateKind field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source SysCostRateKind entity
	 * @throws ValidationException
	 */
	private static void validateEntity(SysCostRateKind entity) throws ValidationException {
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
	 * @return the sysCostRateId
	 */
	public Long getSysCostRateId() {
		return sysCostRateId;
	}

	/**
	 * @param sysCostRateId the sysCostRateId to set
	 */
	public void setSysCostRateId(Long sysCostRateId) {
		this.sysCostRateId = sysCostRateId;
	}

}
