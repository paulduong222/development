package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IncidentCostRate;
import gov.nwcg.isuite.core.domain.IncidentCostRateKind;
import gov.nwcg.isuite.core.domain.impl.IncidentCostRateImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentCostRateKindImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class IncidentCostRateKindVo extends AbstractVo {
	private String rateType;
	private BigDecimal rateAmount;
	private KindVo kindVo;
	private Long incidentCostRateId;
	
	public IncidentCostRateKindVo(){
		super();
	}

	/**
	 * Returns a IncidentCostRateKindVo from a IncidentCostRateKind entity.
	 * 
	 * @param entity
	 * 			the source IncidentCostRateKind entity
	 * @param cascadable
	 * 			flag indicating whether the vo instance should created as a cascadable vo
	 * @return
	 * 			instance of IncidentCostRateKindVo
	 * @throws Exception
	 */
	public static IncidentCostRateKindVo getInstance(IncidentCostRateKind entity,boolean cascadable) throws Exception{
		IncidentCostRateKindVo vo = new IncidentCostRateKindVo();

		if(null == entity)
			throw new Exception("Unable to create IncidentCostRateKindVo from null IncidentCostRateKind entity.");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setRateType(entity.getRateType());
			vo.setRateAmount(entity.getRateAmount());
			vo.setKindVo(KindVo.getInstance(entity.getKind(),true));
			vo.setIncidentCostRateId(entity.getIncidentCostRate().getId());
		}

		return vo;
	}

	/**
	 * Returns a collection of IncidentCostRateKindVos from a collection of IncidentCostRateKind entities.
	 * 
	 * @param entities
	 * 			the source collection of IncidentCostRateKind entities
	 * @param cascadable
	 * 			flag indicating whether the vo instances should created as a cascadable vos
	 * @return
	 * 			collection of IncidentCostRateKind vos
	 * @throws Exception
	 */
	public static Collection<IncidentCostRateKindVo> getInstances(Collection<IncidentCostRateKind> entities, boolean cascadable) throws Exception {
		Collection<IncidentCostRateKindVo> vos = new ArrayList<IncidentCostRateKindVo>();

		for(IncidentCostRateKind entity : entities){
			vos.add(IncidentCostRateKindVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	/**
	 * Returns a IncidentCostRateKind entity from a IncidentCostRateKind vo.
	 * 
	 * @param vo
	 * 			the source IncidentCostRateKind vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of IncidentCostRateKind entity
	 * @throws Exception
	 */
	public static IncidentCostRateKind toEntity(IncidentCostRateKindVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		IncidentCostRateKind entity = new IncidentCostRateKindImpl();

		entity.setId(vo.getId());

		if(cascadable){
			entity.setRateType(vo.getRateType());
			entity.setRateAmount(vo.getRateAmount());

			IncidentCostRate incidentCostRate = new IncidentCostRateImpl();
			incidentCostRate.setId(vo.getIncidentCostRateId());
			entity.setIncidentCostRate(incidentCostRate);
			
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
	 * Returns a collection of IncidentCostRateKind entities from a collection of IncidentCostRateKind vos.
	 * 
	 * @param vos
	 * 			the source collection of IncidentCostRateKind vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a cascadable entities
	 * @return
	 * 			collection of IncidentCostRateKind entities
	 * @throws Exception
	 */
	public static Collection<IncidentCostRateKind> toEntityList(Collection<IncidentCostRateKindVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IncidentCostRateKind> entities = new ArrayList<IncidentCostRateKind>();

		for(IncidentCostRateKindVo vo : vos){
			entities.add(IncidentCostRateKindVo.toEntity(vo, cascadable, persistables));
		}

		return entities;
	}

	/**
	 * Perform some validation on the IncidentCostRateKind field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source IncidentCostRateKind entity
	 * @throws ValidationException
	 */
	private static void validateEntity(IncidentCostRateKind entity) throws ValidationException {
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
