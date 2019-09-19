package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IncidentCostRate;
import gov.nwcg.isuite.core.domain.IncidentCostRateState;
import gov.nwcg.isuite.core.domain.impl.IncidentCostRateImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentCostRateStateImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class IncidentCostRateStateVo extends AbstractVo {
	private AgencyVo agencyVo;
	private BigDecimal directRate;
	private BigDecimal inDirectRate;
	private BigDecimal singleRate;
	private BigDecimal subordinateRate;
	private Collection<IncidentCostRateStateKindVo> incidentCostRateStateKindVos = new ArrayList<IncidentCostRateStateKindVo>();
	private Long incidentCostRateId;
	
	public IncidentCostRateStateVo(){
		super();
	}

	/**
	 * Returns a IncidentCostRateStateVo from a IncidentCostRateState entity.
	 * 
	 * @param entity
	 * 			the source IncidentCostRateState entity
	 * @param cascadable
	 * 			flag indicating whether the vo instance should created as a cascadable vo
	 * @return
	 * 			instance of IncidentCostRateStateVo
	 * @throws Exception
	 */
	public static IncidentCostRateStateVo getInstance(IncidentCostRateState entity,boolean cascadable) throws Exception{
		IncidentCostRateStateVo vo = new IncidentCostRateStateVo();

		if(null == entity)
			throw new Exception("Unable to create IncidentCostRateStateVo from null IncidentCostRateState entity.");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setDirectRate(entity.getDirectRate());
			vo.setSingleRate(entity.getSingleRate());
			vo.setSubordinateRate(entity.getSubordinateRate());
			vo.setInDirectRate(entity.getIndirectRate());
			vo.setIncidentCostRateId(entity.getIncidentCostRate().getId());
			
			if(null != entity.getAgency()){
				vo.setAgencyVo(AgencyVo.getInstance(entity.getAgency(),true));
			}
			
			if(null != entity.getIncidentCostStateKinds() && entity.getIncidentCostStateKinds().size() > 0){
				vo.setIncidentCostRateStateKindVos(IncidentCostRateStateKindVo.getInstances(entity.getIncidentCostStateKinds(), true));
			}
		}

		return vo;
	}

	/**
	 * Returns a collection of IncidentCostRateStateVos from a collection of IncidentCostRateState entities.
	 * 
	 * @param entities
	 * 			the source collection of IncidentCostRateState entities
	 * @param cascadable
	 * 			flag indicating whether the vo instances should created as a cascadable vos
	 * @return
	 * 			collection of IncidentCostRateState vos
	 * @throws Exception
	 */
	public static Collection<IncidentCostRateStateVo> getInstances(Collection<IncidentCostRateState> entities, boolean cascadable) throws Exception {
		Collection<IncidentCostRateStateVo> vos = new ArrayList<IncidentCostRateStateVo>();

		for(IncidentCostRateState entity : entities){
			vos.add(IncidentCostRateStateVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	/**
	 * Returns a IncidentCostRateState entity from a IncidentCostRateState vo.
	 * 
	 * @param vo
	 * 			the source IncidentCostRateState vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of IncidentCostRateState entity
	 * @throws Exception
	 */
	public static IncidentCostRateState toEntity(IncidentCostRateStateVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		IncidentCostRateState entity = new IncidentCostRateStateImpl();

		entity.setId(vo.getId());

		if(cascadable){
			entity.setDirectRate(vo.getDirectRate());
			entity.setIndirectRate(vo.getInDirectRate());
			entity.setSingleRate(vo.getSingleRate());
			entity.setSubordinateRate(vo.getSubordinateRate());
			
			IncidentCostRate incidentCostRate = new IncidentCostRateImpl();
			incidentCostRate.setId(vo.getIncidentCostRateId());
			entity.setIncidentCostRate(incidentCostRate);

			if(null != vo.getAgencyVo()){
				entity.setAgency(AgencyVo.toEntity(null, vo.getAgencyVo(), false));
			}
			
//			if(null != vo.getIncidentCostRateStateKindVos() && vo.getIncidentCostRateStateKindVos().size() > 0){
//				entity.setIncidentCostStateKinds(IncidentCostRateStateKindVo.toEntityList(vo.getIncidentCostRateStateKindVos(), true));
//			}
			
			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

	/**
	 * Returns a collection of IncidentCostRateState entities from a collection of IncidentCostRateState vos.
	 * 
	 * @param vos
	 * 			the source collection of IncidentCostRateState vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a cascadable entities
	 * @return
	 * 			collection of IncidentCostRateState entities
	 * @throws Exception
	 */
	public static Collection<IncidentCostRateState> toEntityList(Collection<IncidentCostRateStateVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IncidentCostRateState> entities = new ArrayList<IncidentCostRateState>();

		for(IncidentCostRateStateVo vo : vos){
			entities.add(IncidentCostRateStateVo.toEntity(vo, cascadable, persistables));
		}

		return entities;
	}

	/**
	 * Perform some validation on the IncidentCostRateState field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source IncidentCostRateState entity
	 * @throws ValidationException
	 */
	private static void validateEntity(IncidentCostRateState entity) throws ValidationException {
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
	 * @return the incidentCostRateStateKindVos
	 */
	public Collection<IncidentCostRateStateKindVo> getIncidentCostRateStateKindVos() {
		return incidentCostRateStateKindVos;
	}

	/**
	 * @param incidentCostRateStateKindVos the incidentCostRateStateKindVos to set
	 */
	public void setIncidentCostRateStateKindVos(
			Collection<IncidentCostRateStateKindVo> incidentCostRateStateKindVos) {
		this.incidentCostRateStateKindVos = incidentCostRateStateKindVos;
	}

	/**
	 * @return the incidentCostRateId
	 */
	public Long getIncidentCostRateId() {
		return incidentCostRateId;
	}

	/**
	 * @param incidentCostRateId the incidentCostRateId to set
	 */
	public void setIncidentCostRateId(Long incidentCostRateId) {
		this.incidentCostRateId = incidentCostRateId;
	}

	

}
