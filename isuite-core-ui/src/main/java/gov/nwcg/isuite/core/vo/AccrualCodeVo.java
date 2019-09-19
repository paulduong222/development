package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.AccrualCode;
import gov.nwcg.isuite.core.domain.impl.AccrualCodeImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.Collection;

public class AccrualCodeVo extends AbstractVo {
	private String code;
	private String description;
	private Boolean standard;

	public AccrualCodeVo(){
		super();
	}

	/**
	 * Returns a AccrualCodeVo from a AccrualCode entity.
	 * 
	 * @param entity
	 * 			the source AccrualCode entity
	 * @param cascadable
	 * 			flag indicating whether the vo instance should created as a cascadable vo
	 * @return
	 * 			instance of AccrualCodeVo
	 * @throws Exception
	 */
	public static AccrualCodeVo getInstance(AccrualCode entity,boolean cascadable) throws Exception{
		AccrualCodeVo vo = new AccrualCodeVo();

		if(null == entity)
			throw new Exception("Unable to create AccrualCodeVo from null AccrualCode entity.");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setCode(entity.getCode());
			vo.setDescription(entity.getDescription());
			vo.setStandard(entity.getStandard());
		}

		return vo;
	}

	/**
	 * Returns a collection of AccrualCodeVos from a collection of AccrualCode entities.
	 * 
	 * @param entities
	 * 			the source collection of AccrualCode entities
	 * @param cascadable
	 * 			flag indicating whether the vo instances should created as a cascadable vos
	 * @return
	 * 			collection of AccrualCode vos
	 * @throws Exception
	 */
	public static Collection<AccrualCodeVo> getInstances(Collection<AccrualCode> entities, boolean cascadable) throws Exception {
		Collection<AccrualCodeVo> vos = new ArrayList<AccrualCodeVo>();

		for(AccrualCode entity : entities){
			vos.add(AccrualCodeVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	/**
	 * Returns a AccrualCode entity from a AccrualCode vo.
	 * 
	 * @param vo
	 * 			the source AccrualCode vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of AccrualCode entity
	 * @throws Exception
	 */
	public static AccrualCode toEntity(AccrualCodeVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		AccrualCode entity = new AccrualCodeImpl();

		entity.setId(vo.getId());

		if(cascadable){
			entity.setCode(vo.getCode());
			entity.setDescription(vo.getDescription());
			entity.setStandard(vo.getStandard());
			
			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

	/**
	 * Returns a collection of AccrualCode entities from a collection of AccrualCode vos.
	 * 
	 * @param vos
	 * 			the source collection of AccrualCode vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a cascadable entities
	 * @return
	 * 			collection of AccrualCode entities
	 * @throws Exception
	 */
	public static Collection<AccrualCode> toEntityList(Collection<AccrualCodeVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<AccrualCode> entities = new ArrayList<AccrualCode>();

		for(AccrualCodeVo vo : vos){
			entities.add(AccrualCodeVo.toEntity(vo, cascadable, persistables));
		}

		return entities;
	}

	/**
	 * Perform some validation on the AccrualCode field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source AccrualCode entity
	 * @throws ValidationException
	 */
	private static void validateEntity(AccrualCode entity) throws ValidationException {
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the standard
	 */
	public Boolean getStandard() {
		return standard;
	}

	/**
	 * @param standard the standard to set
	 */
	public void setStandard(Boolean standard) {
		this.standard = standard;
	}


}
