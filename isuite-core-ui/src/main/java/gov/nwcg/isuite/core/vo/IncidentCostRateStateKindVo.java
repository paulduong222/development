package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IncidentCostRateState;
import gov.nwcg.isuite.core.domain.IncidentCostRateStateKind;
import gov.nwcg.isuite.core.domain.impl.IncidentCostRateStateImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentCostRateStateKindImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class IncidentCostRateStateKindVo extends AbstractVo {
	private KindVo kindVo;
	private String rateType;
	private BigDecimal rateAmount;
	private Long incidentCostRateStateId;
	
	public IncidentCostRateStateKindVo(){
		super();
	}

	/**
	 * Returns a IncidentCostRateStateKindVo from a IncidentCostRateState entity.
	 * 
	 * @param entity
	 * 			the source IncidentCostRateStateKind entity
	 * @param cascadable
	 * 			flag indicating whether the vo instance should created as a cascadable vo
	 * @return
	 * 			instance of IncidentCostRateStateKindVo
	 * @throws Exception
	 */
	public static IncidentCostRateStateKindVo getInstance(IncidentCostRateStateKind entity,boolean cascadable) throws Exception{
		IncidentCostRateStateKindVo vo = new IncidentCostRateStateKindVo();

		if(null == entity)
			throw new Exception("Unable to create IncidentCostRateStateKindVo from null IncidentCostRateStateKind entity.");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setRateAmount(entity.getRateAmount());
			vo.setRateType(entity.getRateType());
			vo.setIncidentCostRateStateId(entity.getIncidentCostRateState().getId());
			
			if(null != entity.getKind()){
				vo.setKindVo(KindVo.getInstance(entity.getKind(),true));
			}
			
		}

		return vo;
	}

	/**
	 * Returns a collection of IncidentCostRateStateKindVos from a collection of IncidentCostRateStateKind entities.
	 * 
	 * @param entities
	 * 			the source collection of IncidentCostRateStateKind entities
	 * @param cascadable
	 * 			flag indicating whether the vo instances should created as a cascadable vos
	 * @return
	 * 			collection of IncidentCostRateStateKind vos
	 * @throws Exception
	 */
	public static Collection<IncidentCostRateStateKindVo> getInstances(Collection<IncidentCostRateStateKind> entities, boolean cascadable) throws Exception {
		Collection<IncidentCostRateStateKindVo> vos = new ArrayList<IncidentCostRateStateKindVo>();

		for(IncidentCostRateStateKind entity : entities){
			vos.add(IncidentCostRateStateKindVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	/**
	 * Returns a IncidentCostRateStateKind entity from a IncidentCostRateStateKind vo.
	 * 
	 * @param vo
	 * 			the source IncidentCostRateStateKind vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of IncidentCostRateStateKind entity
	 * @throws Exception
	 */
	public static IncidentCostRateStateKind toEntity(IncidentCostRateStateKindVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		IncidentCostRateStateKind entity = new IncidentCostRateStateKindImpl();

		entity.setId(vo.getId());

		if(cascadable){

			entity.setRateAmount(vo.getRateAmount());
			entity.setRateType(vo.getRateType());

			IncidentCostRateState incidentCostRateState = new IncidentCostRateStateImpl();
			incidentCostRateState.setId(vo.getIncidentCostRateStateId());
			entity.setIncidentCostRateState(incidentCostRateState);
			
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
	 * Returns a collection of IncidentCostRateStateKind entities from a collection of IncidentCostRateStateKind vos.
	 * 
	 * @param vos
	 * 			the source collection of IncidentCostRateStateKind vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a cascadable entities
	 * @return
	 * 			collection of IncidentCostRateStateKind entities
	 * @throws Exception
	 */
	public static Collection<IncidentCostRateStateKind> toEntityList(Collection<IncidentCostRateStateKindVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IncidentCostRateStateKind> entities = new ArrayList<IncidentCostRateStateKind>();

		for(IncidentCostRateStateKindVo vo : vos){
			entities.add(IncidentCostRateStateKindVo.toEntity(vo, cascadable, persistables));
		}

		return entities;
	}

	/**
	 * Perform some validation on the IncidentCostRateStateKind field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source IncidentCostRateStateKind entity
	 * @throws ValidationException
	 */
	private static void validateEntity(IncidentCostRateStateKind entity) throws ValidationException {
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
	 * @return the incidentCostRateStateId
	 */
	public Long getIncidentCostRateStateId() {
		return incidentCostRateStateId;
	}

	/**
	 * @param incidentCostRateStateId the incidentCostRateStateId to set
	 */
	public void setIncidentCostRateStateId(Long incidentCostRateStateId) {
		this.incidentCostRateStateId = incidentCostRateStateId;
	}


}
