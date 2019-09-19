package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentCostDefaults;
import gov.nwcg.isuite.core.domain.impl.IncidentCostDefaultsImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.math.BigDecimal;

public class IncidentCostDefaultsVo extends AbstractVo implements PersistableVo {
	private Long incidentId;
	private BigDecimal airTravelAmount;
	private BigDecimal rentalVehicleAmount;
	private BigDecimal otherAmount;
	private String otherDescription;
	private Integer defaultHours;
	
	/**
	 * Default Constructor
	 */
	public IncidentCostDefaultsVo() {
	}

	/**
	 * Returns a IncidentCostDefaultsVo instance from a IncidentCostDefaults entity.
	 * 
	 * @param entity
	 *          the source IncidentCostDefaults entity
	 * @param cascadable
	 *          flag indicating whether the instance should created as a cascadable vo
	 * @return
	 *       instance of IncidentCostDefaultsVo
	 * @throws Exception
	 */
	public static IncidentCostDefaultsVo getInstance(IncidentCostDefaults entity, boolean cascadable) throws Exception {
		IncidentCostDefaultsVo vo = new IncidentCostDefaultsVo();

		if(null == entity)
			throw new Exception("Unable to create IncidentCostDefaultsVo from null IncidentCostDefaults entity.");

		vo.setId(entity.getId());

		/*
		 * Only populate fields outside of the entity Id if needed
		 */
		if(cascadable){
			vo.setIncidentId(entity.getIncidentId());
			vo.setAirTravelAmount(entity.getAirTravelAmount());
			vo.setDefaultHours(entity.getDefaultHours());
			vo.setOtherAmount(entity.getOtherAmount());
			vo.setOtherDescription(entity.getOtherDescription());
			vo.setRentalVehicleAmount(entity.getRentalVehicleAmount());
		}

		return vo;
	}

	/**
	 * Returns a IncidentCostDefaults entity from a IncidentCostDefaultsVo.
	 * 
	 * @param vo
	 *          the source IncidentCostDefaultsVo
	 * @param cascadable
	 *          flag indicating whether the entity instance should created as a cascadable entity
	 * @param persistables
	 *          Optional array of referenced persistable entities 
	 * @return
	 *          IncidentCostDefaults entity
	 * @throws Exception
	 */
	public static IncidentCostDefaults toEntity(IncidentCostDefaults entity, IncidentCostDefaultsVo vo,boolean cascadable,Persistable...persistables) throws Exception {
		if (null == entity) {
			entity = new IncidentCostDefaultsImpl();
			entity.setId(vo.getId());
		}

		if(cascadable){
			entity.setAirTravelAmount(vo.getAirTravelAmount());
			entity.setDefaultHours(vo.getDefaultHours());
			entity.setOtherAmount(vo.getOtherAmount());
			entity.setOtherDescription(vo.getOtherDescription());
			entity.setRentalVehicleAmount(vo.getRentalVehicleAmount());
			
			Incident incEntity = (Incident)getPersistableObject(persistables, IncidentImpl.class);
			if(null != incEntity){
				entity.setIncident(incEntity);
			} else {
				if(LongUtility.hasValue(vo.getIncidentId())) {
					incEntity = new IncidentImpl();
					incEntity.setId(vo.getIncidentId());
					entity.setIncident(incEntity);
				}
			}

			
			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

	/**
	 * Perform some validation on the IncidentCostDefaults field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * @throws ValidationException
	 */
	private static void validateEntity(IncidentCostDefaults entity) throws ValidationException {
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

	/**
	 * @return the airTravelAmount
	 */
	public BigDecimal getAirTravelAmount() {
		return airTravelAmount;
	}

	/**
	 * @param airTravelAmount the airTravelAmount to set
	 */
	public void setAirTravelAmount(BigDecimal airTravelAmount) {
		this.airTravelAmount = airTravelAmount;
	}

	/**
	 * @return the rentalVehicleAmount
	 */
	public BigDecimal getRentalVehicleAmount() {
		return rentalVehicleAmount;
	}

	/**
	 * @param rentalVehicleAmount the rentalVehicleAmount to set
	 */
	public void setRentalVehicleAmount(BigDecimal rentalVehicleAmount) {
		this.rentalVehicleAmount = rentalVehicleAmount;
	}

	/**
	 * @return the otherAmount
	 */
	public BigDecimal getOtherAmount() {
		return otherAmount;
	}

	/**
	 * @param otherAmount the otherAmount to set
	 */
	public void setOtherAmount(BigDecimal otherAmount) {
		this.otherAmount = otherAmount;
	}

	/**
	 * @return the otherDescription
	 */
	public String getOtherDescription() {
		return otherDescription;
	}

	/**
	 * @param otherDescription the otherDescription to set
	 */
	public void setOtherDescription(String otherDescription) {
		this.otherDescription = otherDescription;
	}

	/**
	 * @return the defaultHours
	 */
	public Integer getDefaultHours() {
		return defaultHours;
	}

	/**
	 * @param defaultHours the defaultHours to set
	 */
	public void setDefaultHours(Integer defaultHours) {
		this.defaultHours = defaultHours;
	}


}
