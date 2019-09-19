/**
 * 
 */
package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.GraphGroup;
import gov.nwcg.isuite.core.domain.impl.GraphGroupImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class GraphGroupVo extends AbstractVo implements PersistableVo {
   private String code;
   private String description;
   private Boolean standard = false;
   
   public GraphGroupVo() {
      super();
   }

   /**
    * Returns a GraphGroupVo from a Kind entity.
    * 
    * @param entity
    * 			the source GraphGroup entity
    * @param cascadable
    * 			flag indicating whether the vo instance should created as a cascadable vo
    * @return
    * 			instance of GraphGroupVo
    * @throws Exception
    */
   public static GraphGroupVo getInstance(GraphGroup entity,boolean cascadable) throws Exception{
	   GraphGroupVo vo = new GraphGroupVo();
	
	   if(null == entity)
		   throw new Exception("Unable to create GraphGroupVo from null GraphGroup entity.");
	
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
    * Returns a collection of GraphGroupVos from a collection of GraphGroup entities.
    * 
    * @param entities
    * 			the source collection of GraphGroup entities
    * @param cascadable
    * 			flag indicating whether the vo instances should created as a cascadable vos
    * @return
    * 			collection of GraphGroup vos
    * @throws Exception
    */
   public static Collection<GraphGroupVo> getInstances(Collection<GraphGroup> entities, boolean cascadable) throws Exception {
	   Collection<GraphGroupVo> vos = new ArrayList<GraphGroupVo>();
	   
	   for(GraphGroup entity : entities){
		   vos.add(GraphGroupVo.getInstance(entity, cascadable));
	   }
	   
	   return vos;
   }
   
   /**
    * Returns a GraphGroup entity from a GraphGroup vo.
    * 
    * @param vo
    * 			the source GraphGroup vo
    * @param cascadable
    * 			flag indicating whether the entity instance should created as a cascadable entity
    * @return
    * 			instance of GraphGroup entity
    * @throws Exception
    */
   public static GraphGroup toEntity(GraphGroupVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
	   GraphGroup entity = new GraphGroupImpl();
		
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
    * Returns a collection of GraphGroup entities from a collection of GraphGroup vos.
    * 
    * @param vos
    * 			the source collection of GraphGroup vos
    * @param cascadable
    * 			flag indicating whether the entity instances should created as a cascadable entities
    * @return
    * 			collection of GraphGroup entities
    * @throws Exception
    */
   public static Collection<GraphGroup> toEntityList(Collection<GraphGroupVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
	   Collection<GraphGroup> entities = new ArrayList<GraphGroup>();
	   
	   for(GraphGroupVo vo : vos){
		   entities.add(GraphGroupVo.toEntity(vo, cascadable, persistables));
	   }
	   
	   return entities;
   }
   
   /**
    * Perform some validation on the GraphGroup field values against the
    * entity field definitions.
    * 
    * @param entity
    * 			the source GraphGroup entity
    * @throws ValidationException
    */
   private static void validateEntity(GraphGroup entity) throws ValidationException {
	   Validator.validateStringField("code", entity.getCode(), 10, true);
	   Validator.validateStringField("description", entity.getDescription(), 75,true);
	   Validator.validateBooleanField("isStandard", entity.isStandard(), true);
   }
   
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.vo.PersistableVo#toEntity()
    */
   public GraphGroup toEntity(Persistable entity) throws Exception {
	   // use GraphGroupVo.toEntity()
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
