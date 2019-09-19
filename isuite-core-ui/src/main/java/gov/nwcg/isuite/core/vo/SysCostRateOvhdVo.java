package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.SysCostRate;
import gov.nwcg.isuite.core.domain.SysCostRateOvhd;
import gov.nwcg.isuite.core.domain.impl.SysCostRateImpl;
import gov.nwcg.isuite.core.domain.impl.SysCostRateOvhdImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class SysCostRateOvhdVo extends AbstractVo {
	private BigDecimal directRate;
	private BigDecimal indirectRate;
	private BigDecimal subordinateRate;
	private BigDecimal singleRate;
	private Long sysCostRateId;
	
	public SysCostRateOvhdVo(){
		super();
	}

	/**
	 * Returns a SysCostRateOvhdVo from a SysCostRateOvhd entity.
	 * 
	 * @param entity
	 * 			the source SysCostRateOvhd entity
	 * @param cascadable
	 * 			flag indicating whether the vo instance should created as a cascadable vo
	 * @return
	 * 			instance of SysCostRateOvhdVo
	 * @throws Exception
	 */
	public static SysCostRateOvhdVo getInstance(SysCostRateOvhd entity,boolean cascadable) throws Exception{
		SysCostRateOvhdVo vo = new SysCostRateOvhdVo();

		if(null == entity)
			throw new Exception("Unable to create SysCostRateOvhdVo from null SysCostRateOvhd entity.");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setDirectRate(entity.getDirectRate());
			vo.setIndirectRate(entity.getIndirectRate());
			vo.setSingleRate(entity.getSingleRate());
			vo.setSubordinateRate(entity.getSubordinateRate());
			vo.setSysCostRateId(entity.getSysCostRate().getId());
		}

		return vo;
	}

	/**
	 * Returns a collection of SysCostRateOvhdVos from a collection of SysCostRateOvhd entities.
	 * 
	 * @param entities
	 * 			the source collection of SysCostRateOvhd entities
	 * @param cascadable
	 * 			flag indicating whether the vo instances should created as a cascadable vos
	 * @return
	 * 			collection of SysCostRateOvhd vos
	 * @throws Exception
	 */
	public static Collection<SysCostRateOvhdVo> getInstances(Collection<SysCostRateOvhd> entities, boolean cascadable) throws Exception {
		Collection<SysCostRateOvhdVo> vos = new ArrayList<SysCostRateOvhdVo>();

		for(SysCostRateOvhd entity : entities){
			vos.add(SysCostRateOvhdVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	/**
	 * Returns a SysCostRateOvhd entity from a SysCostRateOvhd vo.
	 * 
	 * @param vo
	 * 			the source SysCostRateOvhd vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of SysCostRateOvhd entity
	 * @throws Exception
	 */
	public static SysCostRateOvhd toEntity(SysCostRateOvhdVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		SysCostRateOvhd entity = new SysCostRateOvhdImpl();

		entity.setId(vo.getId());

		if(cascadable){
			entity.setDirectRate(vo.getDirectRate());
			entity.setIndirectRate(vo.getIndirectRate());
			entity.setSubordinateRate(vo.getSubordinateRate());
			entity.setSingleRate(vo.getSingleRate());
			
			SysCostRate sysCostRate = new SysCostRateImpl();
			sysCostRate.setId(vo.getSysCostRateId());
			entity.setSysCostRate(sysCostRate);

			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

	/**
	 * Returns a collection of SysCostRateOvhd entities from a collection of SysCostRateOvhd vos.
	 * 
	 * @param vos
	 * 			the source collection of SysCostRateOvhd vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a cascadable entities
	 * @return
	 * 			collection of SysCostRateOvhd entities
	 * @throws Exception
	 */
	public static Collection<SysCostRateOvhd> toEntityList(Collection<SysCostRateOvhdVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<SysCostRateOvhd> entities = new ArrayList<SysCostRateOvhd>();

		for(SysCostRateOvhdVo vo : vos){
			entities.add(SysCostRateOvhdVo.toEntity(vo, cascadable, persistables));
		}

		return entities;
	}

	/**
	 * Perform some validation on the SysCostRateOvhd field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source SysCostRateOvhd entity
	 * @throws ValidationException
	 */
	private static void validateEntity(SysCostRateOvhd entity) throws ValidationException {
	}
	
	/**
	 * @return the directRate
	 */
	public BigDecimal getDirectRate() {
		return directRate;
	}

	/**
	 * @param directRate the directRate to set
	 */
	public void setDirectRate(BigDecimal directRate) {
		this.directRate = directRate;
	}

	/**
	 * @return the indirectRate
	 */
	public BigDecimal getIndirectRate() {
		return indirectRate;
	}

	/**
	 * @param indirectRate the indirectRate to set
	 */
	public void setIndirectRate(BigDecimal indirectRate) {
		this.indirectRate = indirectRate;
	}

	/**
	 * @return the subordinateRate
	 */
	public BigDecimal getSubordinateRate() {
		return subordinateRate;
	}

	/**
	 * @param subordinateRate the subordinateRate to set
	 */
	public void setSubordinateRate(BigDecimal subordinateRate) {
		this.subordinateRate = subordinateRate;
	}

	/**
	 * @return the singleRate
	 */
	public BigDecimal getSingleRate() {
		return singleRate;
	}

	/**
	 * @param singleRate the singleRate to set
	 */
	public void setSingleRate(BigDecimal singleRate) {
		this.singleRate = singleRate;
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
