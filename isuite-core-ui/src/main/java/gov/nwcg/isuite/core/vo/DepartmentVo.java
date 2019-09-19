/**
 * 
 */
package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.nwcg.isuite.core.domain.Department;
import gov.nwcg.isuite.core.domain.impl.DepartmentImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.Validator;

public class DepartmentVo extends AbstractVo implements PersistableVo {
   private String code;
   private String description;
   private Boolean standard = false;
   
   public DepartmentVo() {
      super();
   }

   /**
    * Returns a DepartmentVo from a Kind entity.
    * 
    * @param entity
    * 			the source Department entity
    * @param cascadable
    * 			flag indicating whether the vo instance should created as a cascadable vo
    * @return
    * 			instance of DepartmentVo
    * @throws Exception
    */
   public static DepartmentVo getInstance(Department entity,boolean cascadable) throws Exception{
	   DepartmentVo vo = new DepartmentVo();
	
	   if(null == entity)
		   throw new Exception("Unable to create DepartmentVo from null Department entity.");
	
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
    * Returns a collection of DepartmentVos from a collection of Department entities.
    * 
    * @param entities
    * 			the source collection of Department entities
    * @param cascadable
    * 			flag indicating whether the vo instances should created as a cascadable vos
    * @return
    * 			collection of Department vos
    * @throws Exception
    */
   public static Collection<DepartmentVo> getInstances(Collection<Department> entities, boolean cascadable) throws Exception {
	   Collection<DepartmentVo> vos = new ArrayList<DepartmentVo>();
	   
	   for(Department entity : entities){
		   vos.add(DepartmentVo.getInstance(entity, cascadable));
	   }
	   
	   return vos;
   }
   
   /**
    * Returns a Department entity from a Department vo.
    * 
    * @param vo
    * 			the source Department vo
    * @param cascadable
    * 			flag indicating whether the entity instance should created as a cascadable entity
    * @return
    * 			instance of Department entity
    * @throws Exception
    */
   public static Department toEntity(DepartmentVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
	   Department entity = new DepartmentImpl();
		
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
    * Returns a collection of Department entities from a collection of Department vos.
    * 
    * @param vos
    * 			the source collection of Department vos
    * @param cascadable
    * 			flag indicating whether the entity instances should created as a cascadable entities
    * @return
    * 			collection of Department entities
    * @throws Exception
    */
   public static Collection<Department> toEntityList(Collection<DepartmentVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
	   Collection<Department> entities = new ArrayList<Department>();
	   
	   for(DepartmentVo vo : vos){
		   entities.add(DepartmentVo.toEntity(vo, cascadable, persistables));
	   }
	   
	   return entities;
   }
   
   /**
    * Perform some validation on the Department field values against the
    * entity field definitions.
    * 
    * @param entity
    * 			the source Department entity
    * @throws ValidationException
    */
   private static void validateEntity(Department entity) throws ValidationException {
	   Validator.validateStringField("code", entity.getCode(), 10, true);
	   Validator.validateStringField("description", entity.getDescription(), 75,true);
	   Validator.validateBooleanField("isStandard", entity.isStandard(), true);
   }
   
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.vo.PersistableVo#toEntity()
    */
   public Department toEntity(Persistable entity) throws Exception {
	   // use DepartmentVo.toEntity()
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

}
