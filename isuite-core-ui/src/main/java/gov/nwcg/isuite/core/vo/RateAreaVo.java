/**
 * 
 */
package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.nwcg.isuite.core.domain.RateArea;
import gov.nwcg.isuite.core.domain.impl.RateAreaImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.Validator;

public class RateAreaVo extends AbstractVo implements PersistableVo {
   private String code;
   private String description;
   private Boolean standard = false;
   
   private Collection<RateClassRateVo> rateClassRateVos = new ArrayList<RateClassRateVo>();
   
   public RateAreaVo() {
      super();
   }

   public RateAreaVo(String name){
	   super();
	   code=name;
   }
   
   /**
    * Returns a RateAreaVo from a RateArea entity.
    * 
    * @param entity
    * 			the source RateArea entity
    * @param cascadable
    * 			flag indicating whether the vo instance should created as a cascadable vo
    * @return
    * 			instance of RateAreaVo
    * @throws Exception
    */
   public static RateAreaVo getInstance(RateArea entity,boolean cascadable) throws Exception{
	   RateAreaVo vo = new RateAreaVo();
	
	   if(null == entity)
		   throw new Exception("Unable to create RateAreaVo from null RateArea entity.");
	
	   vo.setId(entity.getId());

	   /*
	    * Only populate fields outside of the entity Id if needed
	    */
	   if(cascadable){
		   vo.setCode(entity.getCode());
		   vo.setDescription(entity.getDescription());
		   vo.setStandard(entity.isStandard());
	   }
	   
	   return vo;
   }

   /**
    * Returns a collection of RateAreaVos from a collection of RateArea entities.
    * 
    * @param entities
    * 			the source collection of RateArea entities
    * @param cascadable
    * 			flag indicating whether the vo instances should created as a cascadable vos
    * @return
    * 			collection of RateArea vos
    * @throws Exception
    */
   public static Collection<RateAreaVo> getInstances(Collection<RateArea> entities, boolean cascadable) throws Exception {
	   Collection<RateAreaVo> vos = new ArrayList<RateAreaVo>();
	   
	   for(RateArea entity : entities){
		   vos.add(RateAreaVo.getInstance(entity, cascadable));
	   }
	   
	   return vos;
   }
   
   /**
    * Returns a RateArea entity from a RateArea vo.
    * 
    * @param vo
    * 			the source RateArea vo
    * @param cascadable
    * 			flag indicating whether the entity instance should created as a cascadable entity
    * @return
    * 			instance of RateArea entity
    * @throws Exception
    */
   public static RateArea toEntity(RateAreaVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
	   RateArea entity = new RateAreaImpl();
		
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
    * Returns a collection of RateArea entities from a collection of RateArea vos.
    * 
    * @param vos
    * 			the source collection of RateArea vos
    * @param cascadable
    * 			flag indicating whether the entity instances should created as a cascadable entities
    * @return
    * 			collection of RateArea entities
    * @throws Exception
    */
   public static Collection<RateArea> toEntityList(Collection<RateAreaVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
	   Collection<RateArea> entities = new ArrayList<RateArea>();
	   
	   for(RateAreaVo vo : vos){
		   entities.add(RateAreaVo.toEntity(vo, cascadable, persistables));
	   }
	   
	   return entities;
   }
   
   /**
    * Perform some validation on the RateArea field values against the
    * entity field definitions.
    * 
    * @param entity
    * 			the source RateArea entity
    * @throws ValidationException
    */
   private static void validateEntity(RateArea entity) throws ValidationException {
	   Validator.validateStringField("code", entity.getCode(), 10, true);
	   Validator.validateStringField("description", entity.getDescription(), 75,true);
	   Validator.validateBooleanField("isStandard", entity.isStandard(), true);
   }
   
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.vo.PersistableVo#toEntity()
    */
   public RateArea toEntity(Persistable entity) throws Exception {
	   // use RateAreaVo.toEntity()
	   return null;
   }
   
   /**
    * Sets the code.
    * 
    * @param code 
    * 		the code to set
    */
   public void setCode(String code) {
      this.code = code;
   }
   
   /**
    * Returns the code.
    * 
    * @return 
    * 		the code
    */
   public String getCode() {
      return this.code;
   }

   /**
    * Sets the description.
    * 
    * @param description 
    * 		the description to set
    */
   public void setDescription(String description) {
      this.description = description;
   }
   
   /**
    * Returns the description.
    * 
    * @return 
    * 		the description to return
    */
   public String getDescription() {
      return this.description;
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
 * @return the rateClassRateVos
 */
public Collection<RateClassRateVo> getRateClassRateVos() {
	return rateClassRateVos;
}

/**
 * @param rateClassRateVos the rateClassRateVos to set
 */
public void setRateClassRateVos(Collection<RateClassRateVo> rateClassRateVos) {
	this.rateClassRateVos = rateClassRateVos;
}

}
