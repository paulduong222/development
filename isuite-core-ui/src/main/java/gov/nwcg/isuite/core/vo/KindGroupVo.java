/**
 * 
 */
package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.KindGroup;
import gov.nwcg.isuite.core.domain.impl.KindGroupImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.Validator;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class KindGroupVo extends AbstractVo implements PersistableVo {
   private String code;
   private String description;
   private Boolean standard = false;
   
   public KindGroupVo() {
      super();
   }

   /**
    * Returns a KindGroupVo from a Kind entity.
    * 
    * @param entity
    * 			the source KindGroup entity
    * @param cascadable
    * 			flag indicating whether the vo instance should created as a cascadable vo
    * @return
    * 			instance of KindGroupVo
    * @throws Exception
    */
   public static KindGroupVo getInstance(KindGroup entity,boolean cascadable) throws Exception{
	   KindGroupVo vo = new KindGroupVo();
	
	   if(null == entity)
		   throw new Exception("Unable to create KindGroupVo from null KindGroup entity.");
	
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
    * Returns a collection of KindGroupVos from a collection of KindGroup entities.
    * 
    * @param entities
    * 			the source collection of KindGroup entities
    * @param cascadable
    * 			flag indicating whether the vo instances should created as a cascadable vos
    * @return
    * 			collection of KindGroup vos
    * @throws Exception
    */
   public static Collection<KindGroupVo> getInstances(Collection<KindGroup> entities, boolean cascadable) throws Exception {
	   Collection<KindGroupVo> vos = new ArrayList<KindGroupVo>();
	   
	   for(KindGroup entity : entities){
		   vos.add(KindGroupVo.getInstance(entity, cascadable));
	   }
	   
	   return vos;
   }
   
   /**
    * Returns a KindGroup entity from a vo.
    * 
    * @param vo
    *          the source vo
    * @param cascadable
    *          flag indicating whether the entity instance should created as a cascadable entity
    * @return
    *          instance of KindGroup entity
    * @throws Exception
    */
   public static KindGroup toEntity(KindGroup entity, KindGroupVo vo, boolean cascadable) throws Exception {
      if(null == entity)
         entity=new KindGroupImpl();

      entity.setId(vo.getId());

      if(cascadable){
         entity.setCode(vo.getCode());
         entity.setDescription(vo.getDescription());
         entity.setStandard(vo.isStandard());
         
         /*
          * Validate the entity
          */
          validateEntity(entity);

      }

      return entity;
   }
   
   
   
   /**
    * Perform some validation on the kindGroup field values against the
    * entity field definitions.
    * 
    * @param entity
    * 			the source kindGroup entity
    * @throws ValidationException
    */
   private static void validateEntity(KindGroup entity) throws ValidationException {
	   Validator.validateStringField("code", entity.getCode(), 10, true);
	   Validator.validateStringField("description", entity.getDescription(), 75,true);
	   Validator.validateBooleanField("isStandard", entity.isStandard(), true);
   }
   
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.vo.PersistableVo#toEntity()
    */
   public KindGroup toEntity(Persistable entity) throws Exception {
	   // use KindGroupVo.toEntity()
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
