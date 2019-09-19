package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IncidentCostRate;
import gov.nwcg.isuite.core.domain.IncidentCostRateOvhd;
import gov.nwcg.isuite.core.domain.impl.IncidentCostRateImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentCostRateOvhdImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class IncidentCostRateOvhdVo extends AbstractVo {
	private BigDecimal directRate;
	private BigDecimal indirectRate;
	private BigDecimal subordinateRate;
	private BigDecimal singleRate;
	private Long incidentCostRateId;
	
	public IncidentCostRateOvhdVo(){
		super();
	}

	/**
	 * Returns a IncidentCostRateOvhdVo from a IncidentCostRateOvhd entity.
	 * 
	 * @param entity
	 * 			the source IncidentCostRateOvhd entity
	 * @param cascadable
	 * 			flag indicating whether the vo instance should created as a cascadable vo
	 * @return
	 * 			instance of IncidentCostRateOvhdVo
	 * @throws Exception
	 */
	public static IncidentCostRateOvhdVo getInstance(IncidentCostRateOvhd entity,boolean cascadable) throws Exception{
		IncidentCostRateOvhdVo vo = new IncidentCostRateOvhdVo();

		if(null == entity)
			throw new Exception("Unable to create IncidentCostRateOvhdVo from null IncidentCostRateOvhd entity.");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setDirectRate(entity.getDirectRate());
			vo.setIndirectRate(entity.getIndirectRate());
			vo.setSingleRate(entity.getSingleRate());
			vo.setSubordinateRate(entity.getSubordinateRate());
			vo.setIncidentCostRateId(entity.getIncidentCostRate().getId());
		}

		return vo;
	}

	/**
	 * Returns a collection of IncidentCostRateOvhdVos from a collection of IncidentCostRateOvhd entities.
	 * 
	 * @param entities
	 * 			the source collection of IncidentCostRateOvhd entities
	 * @param cascadable
	 * 			flag indicating whether the vo instances should created as a cascadable vos
	 * @return
	 * 			collection of IncidentCostRateOvhd vos
	 * @throws Exception
	 */
	public static Collection<IncidentCostRateOvhdVo> getInstances(Collection<IncidentCostRateOvhd> entities, boolean cascadable) throws Exception {
		Collection<IncidentCostRateOvhdVo> vos = new ArrayList<IncidentCostRateOvhdVo>();

		for(IncidentCostRateOvhd entity : entities){
			vos.add(IncidentCostRateOvhdVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	/**
	 * Returns a IncidentCostRateOvhd entity from a IncidentCostRateOvhd vo.
	 * 
	 * @param vo
	 * 			the source IncidentCostRateOvhd vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of IncidentCostRateOvhd entity
	 * @throws Exception
	 */
	public static IncidentCostRateOvhd toEntity(IncidentCostRateOvhdVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		IncidentCostRateOvhd entity = new IncidentCostRateOvhdImpl();

		entity.setId(vo.getId());

		if(cascadable){
			entity.setDirectRate(vo.getDirectRate());
			entity.setIndirectRate(vo.getIndirectRate());
			entity.setSubordinateRate(vo.getSubordinateRate());
			entity.setSingleRate(vo.getSingleRate());
			
			IncidentCostRate incidentCostRate = new IncidentCostRateImpl();
			incidentCostRate.setId(vo.getIncidentCostRateId());
			entity.setIncidentCostRate(incidentCostRate);

			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

	/**
	 * Returns a collection of IncidentCostRateOvhd entities from a collection of IncidentCostRateOvhd vos.
	 * 
	 * @param vos
	 * 			the source collection of IncidentCostRateOvhd vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a cascadable entities
	 * @return
	 * 			collection of IncidentCostRateOvhd entities
	 * @throws Exception
	 */
	public static Collection<IncidentCostRateOvhd> toEntityList(Collection<IncidentCostRateOvhdVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IncidentCostRateOvhd> entities = new ArrayList<IncidentCostRateOvhd>();

		for(IncidentCostRateOvhdVo vo : vos){
			entities.add(IncidentCostRateOvhdVo.toEntity(vo, cascadable, persistables));
		}

		return entities;
	}

	/**
	 * Perform some validation on the IncidentCostRateOvhd field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source IncidentCostRateOvhd entity
	 * @throws ValidationException
	 */
	private static void validateEntity(IncidentCostRateOvhd entity) throws ValidationException {
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
