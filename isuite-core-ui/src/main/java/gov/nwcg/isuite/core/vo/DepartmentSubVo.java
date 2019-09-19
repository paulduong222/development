/**
 * 
 */
package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.DepartmentSub;
import gov.nwcg.isuite.core.domain.impl.DepartmentSubImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DepartmentSubVo extends AbstractVo implements PersistableVo {
   private String code;
   private String description;
   private Boolean standard = false;
   private Long departmentId;
   
   public DepartmentSubVo() {
      super();
   }

   /**
    * Returns a DepartmentSubVo from a Kind entity.
    * 
    * @param entity
    * 			the source DepartmentSub entity
    * @param cascadable
    * 			flag indicating whether the vo instance should created as a cascadable vo
    * @return
    * 			instance of DepartmentSubVo
    * @throws Exception
    */
   public static DepartmentSubVo getInstance(DepartmentSub entity,boolean cascadable) throws Exception{
	   DepartmentSubVo vo = new DepartmentSubVo();
	
	   if(null == entity)
		   throw new Exception("Unable to create DepartmentSubVo from null DepartmentSub entity.");
	
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
    * Returns a collection of DepartmentSubVos from a collection of DepartmentSub entities.
    * 
    * @param entities
    * 			the source collection of DepartmentSub entities
    * @param cascadable
    * 			flag indicating whether the vo instances should created as a cascadable vos
    * @return
    * 			collection of DepartmentSub vos
    * @throws Exception
    */
   public static Collection<DepartmentSubVo> getInstances(Collection<DepartmentSub> entities, boolean cascadable) throws Exception {
	   Collection<DepartmentSubVo> vos = new ArrayList<DepartmentSubVo>();
	   
	   for(DepartmentSub entity : entities){
		   vos.add(DepartmentSubVo.getInstance(entity, cascadable));
	   }
	   
	   return vos;
   }
   
   /**
    * Returns a DepartmentSub entity from a DepartmentSub vo.
    * 
    * @param vo
    * 			the source DepartmentSub vo
    * @param cascadable
    * 			flag indicating whether the entity instance should created as a cascadable entity
    * @return
    * 			instance of DepartmentSub entity
    * @throws Exception
    */
   public static DepartmentSub toEntity(DepartmentSubVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
	   DepartmentSub entity = new DepartmentSubImpl();
		
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
    * Returns a collection of DepartmentSub entities from a collection of DepartmentSub vos.
    * 
    * @param vos
    * 			the source collection of DepartmentSub vos
    * @param cascadable
    * 			flag indicating whether the entity instances should created as a cascadable entities
    * @return
    * 			collection of DepartmentSub entities
    * @throws Exception
    */
   public static Collection<DepartmentSub> toEntityList(Collection<DepartmentSubVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
	   Collection<DepartmentSub> entities = new ArrayList<DepartmentSub>();
	   
	   for(DepartmentSubVo vo : vos){
		   entities.add(DepartmentSubVo.toEntity(vo, cascadable, persistables));
	   }
	   
	   return entities;
   }
   
   /**
    * Perform some validation on the DepartmentSub field values against the
    * entity field definitions.
    * 
    * @param entity
    * 			the source DepartmentSub entity
    * @throws ValidationException
    */
   private static void validateEntity(DepartmentSub entity) throws ValidationException {
	   Validator.validateStringField("code", entity.getCode(), 10, true);
	   Validator.validateStringField("description", entity.getDescription(), 75,true);
	   Validator.validateBooleanField("isStandard", entity.isStandard(), true);
   }
   
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.vo.PersistableVo#toEntity()
    */
   public DepartmentSub toEntity(Persistable entity) throws Exception {
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

/**
 * @return the departmentId
 */
public Long getDepartmentId() {
	return departmentId;
}

/**
 * @param departmentId the departmentId to set
 */
public void setDepartmentId(Long departmentId) {
	this.departmentId = departmentId;
}

}
