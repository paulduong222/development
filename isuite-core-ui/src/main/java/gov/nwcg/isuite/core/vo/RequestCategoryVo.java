package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.nwcg.isuite.core.domain.RequestCategory;
import gov.nwcg.isuite.core.domain.impl.RequestCategoryImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.Validator;

/**
 *
 */
public class RequestCategoryVo extends AbstractVo implements PersistableVo {
   private String code;
   private String description;
   private Boolean standard = false;
   
   public RequestCategoryVo() {
      super();
   }

   public RequestCategoryVo(RequestCategory kind) {
      super();
      if(kind != null) {
         setCode(kind.getCode());
         setDescription(kind.getDescription());
         setStandard(kind.isStandard());
         setId(kind.getId());
      }
   }

   /**
    * Returns a ResourceCategoryVo from a ResourceCategory entity.
    * 
    * @param entity
    * 			the source ResourceCategory entity
    * @param cascadable
    * 			flag indicating whether the vo instance should created as a cascadable vo
    * @return
    * 			instance of RequestCategory vo
    * @throws Exception
    */
   public static RequestCategoryVo getInstance(RequestCategory entity,boolean cascadable) throws Exception{
	   RequestCategoryVo vo = new RequestCategoryVo();
	
	   if(null == entity)
		   throw new Exception("Unable to create RequestCategoryVo from null RequestCategory entity.");
	
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
    * Returns a collection of ResourceCategoryVos from a collection of ResourceCategory entities.
    * 
    * @param entities
    * 			the source collection of ResourceCategory entities
    * @param cascadable
    * 			flag indicating whether the vo instances should created as a cascadable vos
    * @return
    * 			collection of RequestCategory vos
    * @throws Exception
    */
   public static Collection<RequestCategoryVo> getInstances(Collection<RequestCategory> entities, boolean cascadable) throws Exception {
	   Collection<RequestCategoryVo> vos = new ArrayList<RequestCategoryVo>();
	   
	   for(RequestCategory entity : entities){
		   vos.add(RequestCategoryVo.getInstance(entity, cascadable));
	   }
	   
	   return vos;
   }
   
   /**
    * Returns a ResourceCategory entity from a ResourceCategoryVo.
    * 
    * @param vo
    * 			the source ResourceCategoryVo
    * @param cascadable
    * 			flag indicating whether the entity instance should created as a cascadable entity
    * @return
    * 			instance of RequestCategory entity
    * @throws Exception
    */
   public static RequestCategory toEntity(RequestCategoryVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
	   RequestCategory entity = new RequestCategoryImpl();
		
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
    * Returns a collection of ResourceCategory entities from a collection of ResourceCategoryVos.
    * 
    * @param vos
    * 			the source collection of ResourceCategoryVos
    * @param cascadable
    * 			flag indicating whether the entity instances should created as a cascadable entities
    * @return
    * 			collection of RequestCategory entities
    * @throws Exception
    */
   public static Collection<RequestCategory> toEntityList(Collection<RequestCategoryVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
	   Collection<RequestCategory> entities = new ArrayList<RequestCategory>();
	   
	   for(RequestCategoryVo vo : vos){
		   entities.add(RequestCategoryVo.toEntity(vo, cascadable, persistables));
	   }
	   
	   return entities;
   }
   
   /**
    * Perform some validation on the RequestCategory entity field values against the
    * entity field definitions.
    * 
    * @param entity
    * 			the source RequestCategory entity
    * @throws ValidationException
    */
   private static void validateEntity(RequestCategory entity) throws ValidationException {
	   Validator.validateStringField("code", entity.getCode(), 10, true);
	   Validator.validateStringField("description", entity.getDescription(), 75,true);
	   Validator.validateBooleanField("isStandard", entity.isStandard(), true);
   }
   
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.vo.PersistableVo#toEntity()
    */
   public RequestCategory toEntity(Persistable entity) throws Exception {
	   // use RequestCategoryVo.toEntity()
	   return null; //populateEntity(this, ((RequestCategory)entity));
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
    * Returns whether or not this is a standard kind.
    * 
    * @return 
    * 		the standard status to return
    */
	@JsonIgnore
   public Boolean isStandard() {
      return this.standard;
   }

   /**
    * Returns whether or not this is a standard kind.
    * 
    * @return 
    * 		the standard status to return
    */
	@JsonProperty("standard")
	public Boolean getStandard() {
	   return isStandard();
   }
   
   /**
    * Sets whether or not this is a standard kind.
    * 
    * @param standard 
    * 		the standard status to set
    */
	@JsonProperty("standard")
   public void setStandard(Boolean standard) {
      this.standard = standard;
   }          
}
