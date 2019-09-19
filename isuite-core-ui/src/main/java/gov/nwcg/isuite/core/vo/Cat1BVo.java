/**
 * 
 */
package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Cat1B;
import gov.nwcg.isuite.core.domain.impl.Cat1BImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Cat1BVo extends AbstractVo implements PersistableVo {
   private String code;
   private String description;
   private Boolean standard = false;
   
   public Cat1BVo() {
      super();
   }

   /**
    * Returns a Cat1BVo from a Kind entity.
    * 
    * @param entity
    * 			the source Cat1B entity
    * @param cascadable
    * 			flag indicating whether the vo instance should created as a cascadable vo
    * @return
    * 			instance of Cat1BVo
    * @throws Exception
    */
   public static Cat1BVo getInstance(Cat1B entity,boolean cascadable) throws Exception{
	   Cat1BVo vo = new Cat1BVo();
	
	   if(null == entity)
		   throw new Exception("Unable to create Cat1BVo from null Cat1B entity.");
	
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
    * Returns a collection of Cat1BVos from a collection of Cat1B entities.
    * 
    * @param entities
    * 			the source collection of Cat1B entities
    * @param cascadable
    * 			flag indicating whether the vo instances should created as a cascadable vos
    * @return
    * 			collection of Cat1B vos
    * @throws Exception
    */
   public static Collection<Cat1BVo> getInstances(Collection<Cat1B> entities, boolean cascadable) throws Exception {
	   Collection<Cat1BVo> vos = new ArrayList<Cat1BVo>();
	   
	   for(Cat1B entity : entities){
		   vos.add(Cat1BVo.getInstance(entity, cascadable));
	   }
	   
	   return vos;
   }
   
   /**
    * Returns a Cat1B entity from a Cat1B vo.
    * 
    * @param vo
    * 			the source Cat1B vo
    * @param cascadable
    * 			flag indicating whether the entity instance should created as a cascadable entity
    * @return
    * 			instance of Cat1B entity
    * @throws Exception
    */
   public static Cat1B toEntity(Cat1BVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
	   Cat1B entity = new Cat1BImpl();
		
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
    * Returns a collection of Cat1B entities from a collection of Cat1B vos.
    * 
    * @param vos
    * 			the source collection of Cat1B vos
    * @param cascadable
    * 			flag indicating whether the entity instances should created as a cascadable entities
    * @return
    * 			collection of Cat1B entities
    * @throws Exception
    */
   public static Collection<Cat1B> toEntityList(Collection<Cat1BVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
	   Collection<Cat1B> entities = new ArrayList<Cat1B>();
	   
	   for(Cat1BVo vo : vos){
		   entities.add(Cat1BVo.toEntity(vo, cascadable, persistables));
	   }
	   
	   return entities;
   }
   
   /**
    * Perform some validation on the Cat1B field values against the
    * entity field definitions.
    * 
    * @param entity
    * 			the source Cat1B entity
    * @throws ValidationException
    */
   private static void validateEntity(Cat1B entity) throws ValidationException {
	   Validator.validateStringField("code", entity.getCode(), 10, true);
	   Validator.validateStringField("description", entity.getDescription(), 75,true);
	   Validator.validateBooleanField("isStandard", entity.isStandard(), true);
   }
   
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.vo.PersistableVo#toEntity()
    */
   public Cat1B toEntity(Persistable entity) throws Exception {
	   // use Cat1BVo.toEntity()
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
   public Boolean getStandard() {
	   return isStandard();
   }
   
   /**
    * Sets whether or not this is a standard type.
    * 
    * @param standard 
    * 		the standard status to set
    */
   public void setStandard(Boolean standard) {
      this.standard = standard;
   }

}
