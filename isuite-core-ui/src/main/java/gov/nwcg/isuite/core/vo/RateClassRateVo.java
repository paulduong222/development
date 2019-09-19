/**
 * 
 */
package gov.nwcg.isuite.core.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.nwcg.isuite.core.domain.RateClassRate;
import gov.nwcg.isuite.core.domain.impl.RateClassRateImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public class RateClassRateVo extends AbstractVo implements PersistableVo {
	private static final String CONUS = "CONUS";
	
	private Long rateClassId;
	private String area;
	private BigDecimal rate;
	private Integer rateYear;
	private Boolean standard;
	private String rateClassName;
	private String rateClassCode;
	private RateClassRateVo trainingRateClassRateVo;
	private Boolean active;
	
	public RateClassRateVo() {
		super();
	}

	public static Collection<RateClassRateVo> getByArea(String area, Collection<RateClassRateVo> vos){
		Collection<RateClassRateVo> rtnVos = new ArrayList<RateClassRateVo>();
		
		for(RateClassRateVo vo : vos){
			if(vo.getArea().equalsIgnoreCase(area)){
				rtnVos.add(vo);
			}
		}
		
		return rtnVos;
	}
	
	/**
	 * Returns a RateClassRateVo from a RateClass entity.
	 * 
	 * @param entity
	 * 			the source RateClassRate entity
	 * @param cascadable
	 * 			flag indicating whether the vo instance should created as a cascadable vo
	 * @return
	 * 			instance of RateClassRateVo
	 * @throws Exception
	 */
	public static RateClassRateVo getInstance(RateClassRate entity,boolean cascadable) throws Exception{
		RateClassRateVo vo = new RateClassRateVo();

		if(null == entity)
			throw new Exception("Unable to create RateClassRateVo from null RateClassRate entity.");

		vo.setId(entity.getId());

		/*
		 * Only populate fields outside of the entity Id if needed
		 */
		if(cascadable){
			vo.setRateClassId(entity.getRateClassId());
			vo.setArea(entity.getArea());
			vo.setRate(entity.getRate());
			vo.setRateYear(entity.getRateYear());
			vo.setRateClassName(entity.getRateClassName());
			vo.setRateClassCode(entity.getRateClassCode());
			vo.setStandard(entity.isStandard());
			vo.setActive(StringBooleanEnum.toBooleanValue(entity.isActive()));
			
			if(null != entity.getTrainingRateClassRate()){
				RateClassRateVo trcrVo = new RateClassRateVo();
				trcrVo.setId(entity.getTrainingRateClassRate().getId());
				trcrVo.setArea(entity.getTrainingRateClassRate().getArea());
				trcrVo.setRate(entity.getTrainingRateClassRate().getRate());
				trcrVo.setRateYear(entity.getTrainingRateClassRate().getRateYear());
				trcrVo.setRateClassName(entity.getTrainingRateClassRate().getRateClassName());
				trcrVo.setRateClassCode(entity.getTrainingRateClassRate().getRateClassCode());
				vo.setTrainingRateClassRateVo(trcrVo);
			}
		}

		return vo;
	}

	/**
	 * Returns a collection of RateClassRateVos from a collection of RateClassRate entities.
	 * 
	 * @param entities
	 * 			the source collection of RateClassRate entities
	 * @param cascadable
	 * 			flag indicating whether the vo instances should created as a cascadable vos
	 * @return
	 * 			collection of RateClassRate vos
	 * @throws Exception
	 */
	public static Collection<RateClassRateVo> getInstances(Collection<RateClassRate> entities, boolean cascadable) throws Exception {
		Collection<RateClassRateVo> vos = new ArrayList<RateClassRateVo>();

		for(RateClassRate entity : entities){
			// only include conus rates now
			if(entity.getArea() == null || entity.getArea().equals("CONUS"))	
				vos.add(RateClassRateVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	/**
	 * Returns a RateClassRate entity from a RateClassRate vo.
	 * 
	 * @param vo
	 * 			the source RateClassRate vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of RateClassRate entity
	 * @throws Exception
	 */
	public static RateClassRate toEntity(RateClassRate entity, RateClassRateVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) {
			entity = new RateClassRateImpl();
		}

		entity.setId(vo.getId());

		if(cascadable){
			entity.setRateClassId(vo.getRateClassId());
			if(null != vo.getRateClassId()) {
				RateClassVo rateClassVo = new RateClassVo();
				rateClassVo.setId(vo.getRateClassId());
				entity.setRateClass(RateClassVo.toEntity(rateClassVo, false));
			}
			entity.setStandard(vo.getStandard());
			entity.setRateClassName(vo.getRateClassName());
			entity.setRate(vo.getRate());
			entity.setRateYear(vo.getRateYear());
			entity.setArea(CONUS);
			entity.setActive(StringBooleanEnum.toEnumValue(vo.isActive()));

			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

	/**
	 * Returns a collection of RateClassRate entities from a collection of RateClassRate vos.
	 * 
	 * @param vos
	 * 			the source collection of RateClassRate vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a cascadable entities
	 * @return
	 * 			collection of RateClassRate entities
	 * @throws Exception
	 */
	public static Collection<RateClassRate> toEntityList(Collection<RateClassRateVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<RateClassRate> entities = new ArrayList<RateClassRate>();

		for(RateClassRateVo vo : vos){
			entities.add(RateClassRateVo.toEntity(null, vo, cascadable, persistables));
		}

		return entities;
	}

	/**
	 * Perform some validation on the RateClassRate field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source RateClassRate entity
	 * @throws ValidationException
	 */
	private static void validateEntity(RateClassRate entity) throws ValidationException {
	}


	/**
	 * Returns whether or not this is a standard type.
	 * 
	 * @return 
	 * 		the standard status to return
	 */
	@JsonIgnore
	public Boolean isStandard() {
		return this.standard;
	}

	/**
	 * Returns whether or not this is a standard type.
	 * 
	 * @return 
	 * 		the standard status to return
	 */
	@JsonProperty("standard")
	public Boolean getStandard() {
		return isStandard();
	}

	/**
	 * Sets whether or not this is a standard type.
	 * 
	 * @param standard 
	 * 		the standard status to set
	 */
	@JsonProperty("standard")
	public void setStandard(Boolean standard) {
		this.standard = standard;
	}

	/**
	 * @return the rateYear
	 */
	public Integer getRateYear() {
		return rateYear;
	}

	/**
	 * @param rateYear the rateYear to set
	 */
	public void setRateYear(Integer rateYear) {
		this.rateYear = rateYear;
	}

	public Long getRateClassId() {
		return rateClassId;
	}

	public void setRateClassId(Long rateClassId) {
		this.rateClassId = rateClassId;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getRateClassName() {
		return rateClassName;
	}

	public void setRateClassName(String rateClassName) {
		this.rateClassName = rateClassName;
	}

	public String getRateClassCode() {
		return rateClassCode;
	}

	public void setRateClassCode(String rateClassCode) {
		this.rateClassCode = rateClassCode;
	}

	public RateClassRateVo getTrainingRateClassRateVo() {
		return trainingRateClassRateVo;
	}

	public void setTrainingRateClassRateVo(RateClassRateVo trainingRateClassRateVo) {
		this.trainingRateClassRateVo = trainingRateClassRateVo;
	}
	
	/**
	 * @return the active
	 */
	@JsonProperty("active")
	public Boolean getActive() {
		return active;
	}

	/**
	 * @return the active
	 */
	@JsonIgnore
	public Boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	@JsonProperty("active")
	public void setActive(Boolean active) {
		this.active = active;
	}

}
