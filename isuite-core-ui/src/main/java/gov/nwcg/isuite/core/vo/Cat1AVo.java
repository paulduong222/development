/**
 * 
 */
package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Cat1A;
import gov.nwcg.isuite.core.domain.impl.Cat1AImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Cat1AVo extends AbstractVo implements PersistableVo {
   private String code;
   private String description;
   private Boolean standard = false;
   
   public Cat1AVo() {
      super();
   }

   /**
    * Returns a Cat1AVo from a Kind entity.
    * 
    * @param entity
    * 			the source Cat1A entity
    * @param cascadable
    * 			flag indicating whether the vo instance should created as a cascadable vo
    * @return
    * 			instance of Cat1AVo
    * @throws Exception
    */
   public static Cat1AVo getInstance(Cat1A entity,boolean cascadable) throws Exception{
	   Cat1AVo vo = new Cat1AVo();
	
	   if(null == entity)
		   throw new Exception("Unable to create Cat1AVo from null Cat1A entity.");
	
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
    * Returns a collection of Cat1AVos from a collection of Cat1A entities.
    * 
    * @param entities
    * 			the source collection of Cat1A entities
    * @param cascadable
    * 			flag indicating whether the vo instances should created as a cascadable vos
    * @return
    * 			collection of Cat1A vos
    * @throws Exception
    */
   public static Collection<Cat1AVo> getInstances(Collection<Cat1A> entities, boolean cascadable) throws Exception {
	   Collection<Cat1AVo> vos = new ArrayList<Cat1AVo>();
	   
	   for(Cat1A entity : entities){
		   vos.add(Cat1AVo.getInstance(entity, cascadable));
	   }
	   
	   return vos;
   }
   
   /**
    * Returns a Cat1A entity from a Cat1A vo.
    * 
    * @param vo
    * 			the source Cat1A vo
    * @param cascadable
    * 			flag indicating whether the entity instance should created as a cascadable entity
    * @return
    * 			instance of Cat1A entity
    * @throws Exception
    */
   public static Cat1A toEntity(Cat1AVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
	   Cat1A entity = new Cat1AImpl();
		
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
    * Returns a collection of Cat1A entities from a collection of Cat1A vos.
    * 
    * @param vos
    * 			the source collection of Cat1A vos
    * @param cascadable
    * 			flag indicating whether the entity instances should created as a cascadable entities
    * @return
    * 			collection of Cat1A entities
    * @throws Exception
    */
   public static Collection<Cat1A> toEntityList(Collection<Cat1AVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
	   Collection<Cat1A> entities = new ArrayList<Cat1A>();
	   
	   for(Cat1AVo vo : vos){
		   entities.add(Cat1AVo.toEntity(vo, cascadable, persistables));
	   }
	   
	   return entities;
   }
   
   /**
    * Perform some validation on the Cat1A field values against the
    * entity field definitions.
    * 
    * @param entity
    * 			the source Cat1A entity
    * @throws ValidationException
    */
   private static void validateEntity(Cat1A entity) throws ValidationException {
	   Validator.validateStringField("code", entity.getCode(), 10, true);
	   Validator.validateStringField("description", entity.getDescription(), 75,true);
	   Validator.validateBooleanField("isStandard", entity.isStandard(), true);
   }
   
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.vo.PersistableVo#toEntity()
    */
   public Cat1A toEntity(Persistable entity) throws Exception {
	   // use Cat1AVo.toEntity()
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
