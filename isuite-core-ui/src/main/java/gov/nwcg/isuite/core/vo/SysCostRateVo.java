package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.SysCostRate;
import gov.nwcg.isuite.core.domain.impl.SysCostRateImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.Collection;

public class SysCostRateVo extends AbstractVo {
	private String costRateCategory;
	private Collection<SysCostRateStateVo> sysCostRateStateVos = new ArrayList<SysCostRateStateVo>();
	private Collection<SysCostRateKindVo> sysCostRateKindVos = new ArrayList<SysCostRateKindVo>();
	private Collection<SysCostRateOvhdVo> sysCostRateOvhdVos = new ArrayList<SysCostRateOvhdVo>();

	public SysCostRateVo(){
		super();
	}

	/**
	 * Returns a SysCostRateVo from a SysCostRate entity.
	 * 
	 * @param entity
	 * 			the source SysCostRate entity
	 * @param cascadable
	 * 			flag indicating whether the vo instance should created as a cascadable vo
	 * @return
	 * 			instance of SysCostRateVo
	 * @throws Exception
	 */
	public static SysCostRateVo getInstance(SysCostRate entity,boolean cascadable) throws Exception{
		SysCostRateVo vo = new SysCostRateVo();

		if(null == entity)
			throw new Exception("Unable to create SysCostRateVo from null SysCostRate entity.");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setCostRateCategory(entity.getCostRateCategory());
			
			if(null != entity.getSysCostRateKinds() && entity.getSysCostRateKinds().size()>0){
				vo.setSysCostRateKindVos(SysCostRateKindVo.getInstances(entity.getSysCostRateKinds(), true));
			}

			if(null != entity.getSysCostRateOvhds() && entity.getSysCostRateOvhds().size()>0){
				vo.setSysCostRateOvhdVos(SysCostRateOvhdVo.getInstances(entity.getSysCostRateOvhds(), true));
			}

			if(null != entity.getSysCostRateStates() && entity.getSysCostRateStates().size()>0){
				vo.setSysCostRateStateVos(SysCostRateStateVo.getInstances(entity.getSysCostRateStates(), true));
			}
		}

		return vo;
	}

	/**
	 * Returns a collection of SysCostRateVos from a collection of SysCostRate entities.
	 * 
	 * @param entities
	 * 			the source collection of SysCostRate entities
	 * @param cascadable
	 * 			flag indicating whether the vo instances should created as a cascadable vos
	 * @return
	 * 			collection of SysCostRate vos
	 * @throws Exception
	 */
	public static Collection<SysCostRateVo> getInstances(Collection<SysCostRate> entities, boolean cascadable) throws Exception {
		Collection<SysCostRateVo> vos = new ArrayList<SysCostRateVo>();

		for(SysCostRate entity : entities){
			vos.add(SysCostRateVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	/**
	 * Returns a SysCostRate entity from a SysCostRate vo.
	 * 
	 * @param vo
	 * 			the source SysCostRate vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of SysCostRate entity
	 * @throws Exception
	 */
	public static SysCostRate toEntity(SysCostRateVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		SysCostRate entity = new SysCostRateImpl();

		entity.setId(vo.getId());

		if(cascadable){

			entity.setCostRateCategory(vo.getCostRateCategory());
			
			if(null != vo.getSysCostRateKindVos() && vo.getSysCostRateKindVos().size() > 0){
				entity.setSysCostRateKinds(SysCostRateKindVo.toEntityList(vo.getSysCostRateKindVos(),true,entity));
			}

			if(null != vo.getSysCostRateOvhdVos() && vo.getSysCostRateOvhdVos().size() > 0){
				entity.setSysCostRateOvhds(SysCostRateOvhdVo.toEntityList(vo.getSysCostRateOvhdVos(),true,entity));
			}
			
			if(null != vo.getSysCostRateStateVos() && vo.getSysCostRateStateVos().size() > 0){
				entity.setSysCostRateStates(SysCostRateStateVo.toEntityList(vo.getSysCostRateStateVos(),true,entity));
			}

			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

	/**
	 * Returns a collection of SysCostRate entities from a collection of SysCostRate vos.
	 * 
	 * @param vos
	 * 			the source collection of SysCostRate vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a cascadable entities
	 * @return
	 * 			collection of SysCostRate entities
	 * @throws Exception
	 */
	public static Collection<SysCostRate> toEntityList(Collection<SysCostRateVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<SysCostRate> entities = new ArrayList<SysCostRate>();

		for(SysCostRateVo vo : vos){
			entities.add(SysCostRateVo.toEntity(vo, cascadable, persistables));
		}

		return entities;
	}

	/**
	 * Perform some validation on the SysCostRate field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source SysCostRate entity
	 * @throws ValidationException
	 */
	private static void validateEntity(SysCostRate entity) throws ValidationException {
	}


	/**
	 * @return the costRateCategory
	 */
	public String getCostRateCategory() {
		return costRateCategory;
	}

	/**
	 * @param costRateCategory the costRateCategory to set
	 */
	public void setCostRateCategory(String costRateCategory) {
		this.costRateCategory = costRateCategory;
	}

	/**
	 * @return the sysCostRateStateVos
	 */
	public Collection<SysCostRateStateVo> getSysCostRateStateVos() {
		return sysCostRateStateVos;
	}

	/**
	 * @param sysCostRateStateVos the sysCostRateStateVos to set
	 */
	public void setSysCostRateStateVos(
			Collection<SysCostRateStateVo> sysCostRateStateVos) {
		this.sysCostRateStateVos = sysCostRateStateVos;
	}

	/**
	 * @return the sysCostRateKindVos
	 */
	public Collection<SysCostRateKindVo> getSysCostRateKindVos() {
		return sysCostRateKindVos;
	}

	/**
	 * @param sysCostRateKindVos the sysCostRateKindVos to set
	 */
	public void setSysCostRateKindVos(
			Collection<SysCostRateKindVo> sysCostRateKindVos) {
		this.sysCostRateKindVos = sysCostRateKindVos;
	}

	/**
	 * @return the sysCostRateOvhdVos
	 */
	public Collection<SysCostRateOvhdVo> getSysCostRateOvhdVos() {
		return sysCostRateOvhdVos;
	}

	/**
	 * @param sysCostRateOvhdVos the sysCostRateOvhdVos to set
	 */
	public void setSysCostRateOvhdVos(
			Collection<SysCostRateOvhdVo> sysCostRateOvhdVos) {
		this.sysCostRateOvhdVos = sysCostRateOvhdVos;
	}
}
