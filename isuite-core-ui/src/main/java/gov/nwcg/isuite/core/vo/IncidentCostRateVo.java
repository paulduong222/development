package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentCostRate;
import gov.nwcg.isuite.core.domain.impl.IncidentCostRateImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

public class IncidentCostRateVo extends AbstractVo {
	private Long incidentId;
	private String costRateCategory;
	private Collection<IncidentCostRateStateVo> costRateStateVos = new ArrayList<IncidentCostRateStateVo>();
	private Collection<IncidentCostRateKindVo> costRateKindVos = new ArrayList<IncidentCostRateKindVo>();
	private Collection<IncidentCostRateOvhdVo> costRateOvhdVos = new ArrayList<IncidentCostRateOvhdVo>();

	public IncidentCostRateVo(){
		super();
	}

	/**
	 * Returns a IncidentCostRateVo from a IncidentCostRate entity.
	 * 
	 * @param entity
	 * 			the source IncidentCostRate entity
	 * @param cascadable
	 * 			flag indicating whether the vo instance should created as a cascadable vo
	 * @return
	 * 			instance of IncidentCostRateVo
	 * @throws Exception
	 */
	public static IncidentCostRateVo getInstance(IncidentCostRate entity,boolean cascadable) throws Exception{
		IncidentCostRateVo vo = new IncidentCostRateVo();

		if(null == entity)
			throw new Exception("Unable to create IncidentCostRateVo from null IncidentCostRate entity.");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setIncidentId(entity.getIncident().getId());
			vo.setCostRateCategory(entity.getCostRateCategory());
			
			if(null != entity.getIncidentCostRateKinds() && entity.getIncidentCostRateKinds().size()>0){
				vo.setCostRateKindVos(IncidentCostRateKindVo.getInstances(entity.getIncidentCostRateKinds(), true));
			}

			if(null != entity.getIncidentCostRateOvhds() && entity.getIncidentCostRateOvhds().size()>0){
				vo.setCostRateOvhdVos(IncidentCostRateOvhdVo.getInstances(entity.getIncidentCostRateOvhds(), true));
			}

			if(null != entity.getIncidentCostRateStates() && entity.getIncidentCostRateStates().size()>0){
				vo.setCostRateStateVos(IncidentCostRateStateVo.getInstances(entity.getIncidentCostRateStates(), true));
			}
		}

		return vo;
	}

	/**
	 * Returns a collection of IncidentCostRateVos from a collection of IncidentCostRate entities.
	 * 
	 * @param entities
	 * 			the source collection of IncidentCostRate entities
	 * @param cascadable
	 * 			flag indicating whether the vo instances should created as a cascadable vos
	 * @return
	 * 			collection of IncidentCostRate vos
	 * @throws Exception
	 */
	public static Collection<IncidentCostRateVo> getInstances(Collection<IncidentCostRate> entities, boolean cascadable) throws Exception {
		Collection<IncidentCostRateVo> vos = new ArrayList<IncidentCostRateVo>();

		for(IncidentCostRate entity : entities){
			vos.add(IncidentCostRateVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	/**
	 * Returns a IncidentCostRate entity from a IncidentCostRate vo.
	 * 
	 * @param vo
	 * 			the source IncidentCostRate vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of IncidentCostRate entity
	 * @throws Exception
	 */
	public static IncidentCostRate toEntity(IncidentCostRateVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		IncidentCostRate entity = new IncidentCostRateImpl();

		entity.setId(vo.getId());

		if(cascadable){
			if(LongUtility.hasValue(vo.getIncidentId())){
				Incident incidentEntity = new IncidentImpl();
				incidentEntity.setId(vo.getIncidentId());
				entity.setIncident(incidentEntity);
			}
			
			entity.setCostRateCategory(vo.getCostRateCategory());
			
			if(null != vo.getCostRateKindVos() && vo.getCostRateKindVos().size() > 0){
				entity.setIncidentCostRateKinds(IncidentCostRateKindVo.toEntityList(vo.getCostRateKindVos(),true,entity));
			}

			if(null != vo.getCostRateOvhdVos() && vo.getCostRateOvhdVos().size() > 0){
				entity.setIncidentCostRateOvhds(IncidentCostRateOvhdVo.toEntityList(vo.getCostRateOvhdVos(),true,entity));
			}
			
			if(null != vo.getCostRateStateVos() && vo.getCostRateStateVos().size() > 0){
				entity.setIncidentCostRateStates(IncidentCostRateStateVo.toEntityList(vo.getCostRateStateVos(),true,entity));
			}

			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

	/**
	 * Returns a collection of IncidentCostRate entities from a collection of IncidentCostRate vos.
	 * 
	 * @param vos
	 * 			the source collection of IncidentCostRate vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a cascadable entities
	 * @return
	 * 			collection of IncidentCostRate entities
	 * @throws Exception
	 */
	public static Collection<IncidentCostRate> toEntityList(Collection<IncidentCostRateVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IncidentCostRate> entities = new ArrayList<IncidentCostRate>();

		for(IncidentCostRateVo vo : vos){
			entities.add(IncidentCostRateVo.toEntity(vo, cascadable, persistables));
		}

		return entities;
	}

	/**
	 * Perform some validation on the IncidentCostRate field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source IncidentCostRate entity
	 * @throws ValidationException
	 */
	private static void validateEntity(IncidentCostRate entity) throws ValidationException {
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
	 * @return the costRateStateVos
	 */
	public Collection<IncidentCostRateStateVo> getCostRateStateVos() {
		return costRateStateVos;
	}

	/**
	 * @param costRateStateVos the costRateStateVos to set
	 */
	public void setCostRateStateVos(
			Collection<IncidentCostRateStateVo> costRateStateVos) {
		this.costRateStateVos = costRateStateVos;
	}

	/**
	 * @return the costRateKindVos
	 */
	public Collection<IncidentCostRateKindVo> getCostRateKindVos() {
		return costRateKindVos;
	}

	/**
	 * @param costRateKindVos the costRateKindVos to set
	 */
	public void setCostRateKindVos(
			Collection<IncidentCostRateKindVo> costRateKindVos) {
		this.costRateKindVos = costRateKindVos;
	}

	/**
	 * @return the costRateOvhdVos
	 */
	public Collection<IncidentCostRateOvhdVo> getCostRateOvhdVos() {
		return costRateOvhdVos;
	}

	/**
	 * @param costRateOvhdVos the costRateOvhdVos to set
	 */
	public void setCostRateOvhdVos(
			Collection<IncidentCostRateOvhdVo> costRateOvhdVos) {
		this.costRateOvhdVos = costRateOvhdVos;
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}
}
