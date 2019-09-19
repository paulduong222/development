package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.SysCostRate;
import gov.nwcg.isuite.core.domain.SysCostRateState;
import gov.nwcg.isuite.core.domain.impl.SysCostRateImpl;
import gov.nwcg.isuite.core.domain.impl.SysCostRateStateImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class SysCostRateStateVo extends AbstractVo {
	private AgencyVo agencyVo;
	private BigDecimal directRate;
	private BigDecimal inDirectRate;
	private BigDecimal singleRate;
	private BigDecimal subordinateRate;
	private Collection<SysCostRateStateKindVo> sysCostRateStateKindVos = new ArrayList<SysCostRateStateKindVo>();
	private Long sysCostRateId;
	
	public SysCostRateStateVo(){
		super();
	}

	/**
	 * Returns a SysCostRateStateVo from a SysCostRateState entity.
	 * 
	 * @param entity
	 * 			the source SysCostRateState entity
	 * @param cascadable
	 * 			flag indicating whether the vo instance should created as a cascadable vo
	 * @return
	 * 			instance of SysCostRateStateVo
	 * @throws Exception
	 */
	public static SysCostRateStateVo getInstance(SysCostRateState entity,boolean cascadable) throws Exception{
		SysCostRateStateVo vo = new SysCostRateStateVo();

		if(null == entity)
			throw new Exception("Unable to create SysCostRateStateVo from null SysCostRateState entity.");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setDirectRate(entity.getDirectRate());
			vo.setSingleRate(entity.getSingleRate());
			vo.setSubordinateRate(entity.getSubordinateRate());
			vo.setInDirectRate(entity.getIndirectRate());
			vo.setSysCostRateId(entity.getSysCostRate().getId());
			
			if(null != entity.getAgency()){
				vo.setAgencyVo(AgencyVo.getInstance(entity.getAgency(),true));
			}
			
			if(null != entity.getSysCostStateKinds() && entity.getSysCostStateKinds().size() > 0){
				vo.setSysCostRateStateKindVos(SysCostRateStateKindVo.getInstances(entity.getSysCostStateKinds(), true));
			}
		}

		return vo;
	}

	/**
	 * Returns a collection of SysCostRateStateVos from a collection of SysCostRateState entities.
	 * 
	 * @param entities
	 * 			the source collection of SysCostRateState entities
	 * @param cascadable
	 * 			flag indicating whether the vo instances should created as a cascadable vos
	 * @return
	 * 			collection of SysCostRateState vos
	 * @throws Exception
	 */
	public static Collection<SysCostRateStateVo> getInstances(Collection<SysCostRateState> entities, boolean cascadable) throws Exception {
		Collection<SysCostRateStateVo> vos = new ArrayList<SysCostRateStateVo>();

		for(SysCostRateState entity : entities){
			vos.add(SysCostRateStateVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	/**
	 * Returns a SysCostRateState entity from a SysCostRateState vo.
	 * 
	 * @param vo
	 * 			the source SysCostRateState vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of SysCostRateState entity
	 * @throws Exception
	 */
	public static SysCostRateState toEntity(SysCostRateStateVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		SysCostRateState entity = new SysCostRateStateImpl();

		entity.setId(vo.getId());

		if(cascadable){
			entity.setDirectRate(vo.getDirectRate());
			entity.setIndirectRate(vo.getInDirectRate());
			entity.setSingleRate(vo.getSingleRate());
			entity.setSubordinateRate(vo.getSubordinateRate());
			
			SysCostRate sysCostRate = new SysCostRateImpl();
			sysCostRate.setId(vo.getSysCostRateId());
			entity.setSysCostRate(sysCostRate);

			if(null != vo.getAgencyVo()){
				entity.setAgency(AgencyVo.toEntity(null, vo.getAgencyVo(), false));
			}
			
//			if(null != vo.getSysCostRateStateKindVos() && vo.getSysCostRateStateKindVos().size() > 0){
//				entity.setSysCostStateKinds(SysCostRateStateKindVo.toEntityList(vo.getSysCostRateStateKindVos(), true));
//			}
			
			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

	/**
	 * Returns a collection of SysCostRateState entities from a collection of SysCostRateState vos.
	 * 
	 * @param vos
	 * 			the source collection of SysCostRateState vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a cascadable entities
	 * @return
	 * 			collection of SysCostRateState entities
	 * @throws Exception
	 */
	public static Collection<SysCostRateState> toEntityList(Collection<SysCostRateStateVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<SysCostRateState> entities = new ArrayList<SysCostRateState>();

		for(SysCostRateStateVo vo : vos){
			entities.add(SysCostRateStateVo.toEntity(vo, cascadable, persistables));
		}

		return entities;
	}

	/**
	 * Perform some validation on the SysCostRateState field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source SysCostRateState entity
	 * @throws ValidationException
	 */
	private static void validateEntity(SysCostRateState entity) throws ValidationException {
	}

	/**
	 * @return the agencyVo
	 */
	public AgencyVo getAgencyVo() {
		return agencyVo;
	}

	/**
	 * @param agencyVo the agencyVo to set
	 */
	public void setAgencyVo(AgencyVo agencyVo) {
		this.agencyVo = agencyVo;
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
	 * @return the inDirectRate
	 */
	public BigDecimal getInDirectRate() {
		return inDirectRate;
	}

	/**
	 * @param inDirectRate the inDirectRate to set
	 */
	public void setInDirectRate(BigDecimal inDirectRate) {
		this.inDirectRate = inDirectRate;
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
	 * @return the sysCostRateStateKindVos
	 */
	public Collection<SysCostRateStateKindVo> getSysCostRateStateKindVos() {
		return sysCostRateStateKindVos;
	}

	/**
	 * @param sysCostRateStateKindVos the sysCostRateStateKindVos to set
	 */
	public void setSysCostRateStateKindVos(
			Collection<SysCostRateStateKindVo> sysCostRateStateKindVos) {
		this.sysCostRateStateKindVos = sysCostRateStateKindVos;
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
