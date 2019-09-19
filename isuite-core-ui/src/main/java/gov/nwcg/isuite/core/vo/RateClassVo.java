/**
 * 
 */
package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.RateClass;
import gov.nwcg.isuite.core.domain.impl.RateClassImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RateClassVo extends AbstractVo implements PersistableVo {
	private String rateClassName;
	private Integer rateYear;
	private Boolean standard = false;
	private Collection<RateClassRateVo> rateClassRateVos = new ArrayList<RateClassRateVo>();
	
	public RateClassVo() {
		super();
	}

	/**
	 * Returns a RateClassVo from a RateClass entity.
	 * 
	 * @param entity
	 * 			the source RateClass entity
	 * @param cascadable
	 * 			flag indicating whether the vo instance should created as a cascadable vo
	 * @return
	 * 			instance of RateClassVo
	 * @throws Exception
	 */
	public static RateClassVo getInstance(RateClass entity,boolean cascadable) throws Exception{
		RateClassVo vo = new RateClassVo();

		if(null == entity)
			throw new Exception("Unable to create RateClassVo from null RateClass entity.");

		vo.setId(entity.getId());

		/*
		 * Only populate fields outside of the entity Id if needed
		 */
		if(cascadable){
			vo.setRateClassName(entity.getRateClassName());
			vo.setRateYear(entity.getRateYear());
			vo.setStandard(entity.isStandard());
			
			if(null != entity.getRateClassRates()){
				vo.setRateClassRateVos(RateClassRateVo.getInstances(entity.getRateClassRates(), true));
			}
		}

		return vo;
	}

	/**
	 * Returns a collection of RateClassVos from a collection of RateClass entities.
	 * 
	 * @param entities
	 * 			the source collection of RateClass entities
	 * @param cascadable
	 * 			flag indicating whether the vo instances should created as a cascadable vos
	 * @return
	 * 			collection of RateClass vos
	 * @throws Exception
	 */
	public static Collection<RateClassVo> getInstances(Collection<RateClass> entities, boolean cascadable) throws Exception {
		Collection<RateClassVo> vos = new ArrayList<RateClassVo>();

		for(RateClass entity : entities){
			vos.add(RateClassVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	/**
	 * Returns a RateClass entity from a RateClass vo.
	 * 
	 * @param vo
	 * 			the source RateClass vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of RateClass entity
	 * @throws Exception
	 */
	public static RateClass toEntity(RateClassVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		RateClass entity = new RateClassImpl();

		entity.setId(vo.getId());

		if(cascadable){

			entity.setStandard(vo.getStandard());

			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

	/**
	 * Returns a collection of RateClass entities from a collection of RateClass vos.
	 * 
	 * @param vos
	 * 			the source collection of RateClass vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a cascadable entities
	 * @return
	 * 			collection of RateClass entities
	 * @throws Exception
	 */
	public static Collection<RateClass> toEntityList(Collection<RateClassVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<RateClass> entities = new ArrayList<RateClass>();

		for(RateClassVo vo : vos){
			entities.add(RateClassVo.toEntity(vo, cascadable, persistables));
		}

		return entities;
	}

	/**
	 * Perform some validation on the RateClass field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source RateClass entity
	 * @throws ValidationException
	 */
	private static void validateEntity(RateClass entity) throws ValidationException {
	}


	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.vo.PersistableVo#toEntity()
	 */
	public RateClass toEntity(Persistable entity) throws Exception {
		// use RateClassVo.toEntity()
		return null;
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
	 * @return the rateClassName
	 */
	public String getRateClassName() {
		return rateClassName;
	}

	/**
	 * @param rateClassName the rateClassName to set
	 */
	public void setRateClassName(String rateClassName) {
		this.rateClassName = rateClassName;
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

	public Collection<RateClassRateVo> getRateClassRateVos() {
		return rateClassRateVos;
	}

	public void setRateClassRateVos(Collection<RateClassRateVo> rateClassRateVos) {
		this.rateClassRateVos = rateClassRateVos;
	}

}
