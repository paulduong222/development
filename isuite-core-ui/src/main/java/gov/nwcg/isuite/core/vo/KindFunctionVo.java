/**
 * 
 */
package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.KindFunction;
import gov.nwcg.isuite.core.domain.impl.KindFunctionImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class KindFunctionVo extends AbstractVo implements PersistableVo {
   private String code;
   private String description;
   private Boolean standard = false;
   
   public KindFunctionVo() {
      super();
   }

   /**
    * Returns a KindFunctionVo from a Kind entity.
    * 
    * @param entity
    * 			the source KindFunction entity
    * @param cascadable
    * 			flag indicating whether the vo instance should created as a cascadable vo
    * @return
    * 			instance of KindFunctionVo
    * @throws Exception
    */
   public static KindFunctionVo getInstance(KindFunction entity,boolean cascadable) throws Exception{
	   KindFunctionVo vo = new KindFunctionVo();
	
	   if(null == entity)
		   throw new Exception("Unable to create KindFunctionVo from null KindFunction entity.");
	
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
    * Returns a collection of KindFunctionVos from a collection of KindFunction entities.
    * 
    * @param entities
    * 			the source collection of KindFunction entities
    * @param cascadable
    * 			flag indicating whether the vo instances should created as a cascadable vos
    * @return
    * 			collection of KindFunction vos
    * @throws Exception
    */
   public static Collection<KindFunctionVo> getInstances(Collection<KindFunction> entities, boolean cascadable) throws Exception {
	   Collection<KindFunctionVo> vos = new ArrayList<KindFunctionVo>();
	   
	   for(KindFunction entity : entities){
		   vos.add(KindFunctionVo.getInstance(entity, cascadable));
	   }
	   
	   return vos;
   }
   
   /**
    * Returns a KindFunction entity from a KindFunction vo.
    * 
    * @param vo
    * 			the source KindFunction vo
    * @param cascadable
    * 			flag indicating whether the entity instance should created as a cascadable entity
    * @return
    * 			instance of KindFunction entity
    * @throws Exception
    */
   public static KindFunction toEntity(KindFunctionVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
	   KindFunction entity = new KindFunctionImpl();
		
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
    * Returns a collection of KindFunction entities from a collection of KindFunction vos.
    * 
    * @param vos
    * 			the source collection of KindFunction vos
    * @param cascadable
    * 			flag indicating whether the entity instances should created as a cascadable entities
    * @return
    * 			collection of KindFunction entities
    * @throws Exception
    */
   public static Collection<KindFunction> toEntityList(Collection<KindFunctionVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
	   Collection<KindFunction> entities = new ArrayList<KindFunction>();
	   
	   for(KindFunctionVo vo : vos){
		   entities.add(KindFunctionVo.toEntity(vo, cascadable, persistables));
	   }
	   
	   return entities;
   }
   
   /**
    * Perform some validation on the KindFunction field values against the
    * entity field definitions.
    * 
    * @param entity
    * 			the source KindFunction entity
    * @throws ValidationException
    */
   private static void validateEntity(KindFunction entity) throws ValidationException {
	   Validator.validateStringField("code", entity.getCode(), 10, true);
	   Validator.validateStringField("description", entity.getDescription(), 75,true);
	   Validator.validateBooleanField("isStandard", entity.isStandard(), true);
   }
   
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.vo.PersistableVo#toEntity()
    */
   public KindFunction toEntity(Persistable entity) throws Exception {
	   // use KindFunctionVo.toEntity()
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
