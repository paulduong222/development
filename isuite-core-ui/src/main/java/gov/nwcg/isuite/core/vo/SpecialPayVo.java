/**
 * 
 */
package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.nwcg.isuite.core.domain.SpecialPay;
import gov.nwcg.isuite.core.domain.impl.SpecialPayImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.Validator;

public class SpecialPayVo extends AbstractVo implements PersistableVo {
   private String code;
   private String description;
   private Boolean standard = false;
   private Boolean availableToAd;
   private Boolean availableToFed;
   private Boolean availableToOther;
   private Boolean availableToFedAd;
   private Boolean availableToFedAdOther;
   private Boolean availableToFedOther;
   private Boolean availableToAdOther;
   
   private Integer ordinalValue;
   
   public SpecialPayVo() {
      super();
   }

   /**
    * Returns a SpecialPayVo from a SpecialPay entity.
    * 
    * @param entity
    * 			the source SpecialPay entity
    * @param cascadable
    * 			flag indicating whether the vo instance should created as a cascadable vo
    * @return
    * 			instance of SpecialPayVo
    * @throws Exception
    */
   public static SpecialPayVo getInstance(SpecialPay entity,boolean cascadable) throws Exception{
	   SpecialPayVo vo = new SpecialPayVo();
	
	   if(null == entity)
		   throw new Exception("Unable to create SpecialPayVo from null SpecialPay entity.");
	
	   vo.setId(entity.getId());

	   /*
	    * Only populate fields outside of the entity Id if needed
	    */
	   if(cascadable){
		   vo.setCode(entity.getCode());
		   vo.setDescription(entity.getDescription());
		   vo.setStandard(entity.isStandard());
		   vo.setAvailableToAd(entity.getAvailableToAd());
		   vo.setAvailableToFed(entity.getAvailableToFed());
		   vo.setAvailableToOther(entity.getAvailableToOther());
		   
		   if(BooleanUtility.isTrue(vo.getAvailableToFed()) 
				   	|| BooleanUtility.isTrue(vo.getAvailableToAd())){
			   vo.setAvailableToFedAd(true);
		   }else
			   vo.setAvailableToFedAd(false);
		   
		   if(BooleanUtility.isTrue(vo.getAvailableToFed()) 
				   	|| BooleanUtility.isTrue(vo.getAvailableToOther())){
			   vo.setAvailableToFedOther(true);
		   }else
			   vo.setAvailableToFedOther(false);
		   
		   if(BooleanUtility.isTrue(vo.getAvailableToFed()) 
				   	|| BooleanUtility.isTrue(vo.getAvailableToAd())
				   	|| BooleanUtility.isTrue(vo.getAvailableToOther())){
			   vo.setAvailableToFedAdOther(true);
		   }else
			   vo.setAvailableToFedAdOther(false);

		   if(BooleanUtility.isTrue(vo.getAvailableToAd()) 
				   	|| BooleanUtility.isTrue(vo.getAvailableToOther())){
			   vo.setAvailableToAdOther(true);
		   }else
			   vo.setAvailableToAdOther(false);
		   
		   vo.setOrdinalValue(entity.getOrdinalValue());
	   }
	   
	   return vo;
   }

   /**
    * Returns a collection of SpecialPayVos from a collection of SpecialPay entities.
    * 
    * @param entities
    * 			the source collection of SpecialPay entities
    * @param cascadable
    * 			flag indicating whether the vo instances should created as a cascadable vos
    * @return
    * 			collection of SpecialPay vos
    * @throws Exception
    */
   public static Collection<SpecialPayVo> getInstances(Collection<SpecialPay> entities, boolean cascadable) throws Exception {
	   Collection<SpecialPayVo> vos = new ArrayList<SpecialPayVo>();
	   
	   for(SpecialPay entity : entities){
		   vos.add(SpecialPayVo.getInstance(entity, cascadable));
	   }
	   
	   return vos;
   }
   
   /**
    * Returns a SpecialPay entity from a SpecialPay vo.
    * 
    * @param vo
    * 			the source SpecialPay vo
    * @param cascadable
    * 			flag indicating whether the entity instance should created as a cascadable entity
    * @return
    * 			instance of SpecialPay entity
    * @throws Exception
    */
   public static SpecialPay toEntity(SpecialPayVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
	   SpecialPay entity = new SpecialPayImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable){
			
    		entity.setCode(vo.getCode());
			entity.setDescription(vo.getDescription());
			entity.setStandard(vo.getStandard());
			entity.setAvailableToAd(vo.getAvailableToAd());
			entity.setAvailableToFed(vo.getAvailableToFed());
			entity.setAvailableToOther(vo.getAvailableToOther());
			entity.setOrdinalValue(vo.getOrdinalValue());

			/*
			 * Validate the entity
			 */
    		validateEntity(entity);
		}
		
		return entity;
   }
   
   /**
    * Returns a collection of SpecialPay entities from a collection of SpecialPay vos.
    * 
    * @param vos
    * 			the source collection of SpecialPay vos
    * @param cascadable
    * 			flag indicating whether the entity instances should created as a cascadable entities
    * @return
    * 			collection of SpecialPay entities
    * @throws Exception
    */
   public static Collection<SpecialPay> toEntityList(Collection<SpecialPayVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
	   Collection<SpecialPay> entities = new ArrayList<SpecialPay>();
	   
	   for(SpecialPayVo vo : vos){
		   entities.add(SpecialPayVo.toEntity(vo, cascadable, persistables));
	   }
	   
	   return entities;
   }
   
   /**
    * Perform some validation on the SpecialPay field values against the
    * entity field definitions.
    * 
    * @param entity
    * 			the source SpecialPay entity
    * @throws ValidationException
    */
   private static void validateEntity(SpecialPay entity) throws ValidationException {
	   Validator.validateStringField("code", entity.getCode(), 10, true);
	   Validator.validateStringField("description", entity.getDescription(), 75,true);
	   Validator.validateBooleanField("isStandard", entity.isStandard(), true);
   }
   
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.vo.PersistableVo#toEntity()
    */
   public SpecialPay toEntity(Persistable entity) throws Exception {
	   // use SpecialPayVo.toEntity()
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

public Boolean getAvailableToAd() {
	return availableToAd;
}

public void setAvailableToAd(Boolean availableToAd) {
	this.availableToAd = availableToAd;
}

public Boolean getAvailableToFed() {
	return availableToFed;
}

public void setAvailableToFed(Boolean availableToFed) {
	this.availableToFed = availableToFed;
}

public Boolean getAvailableToOther() {
	return availableToOther;
}

public void setAvailableToOther(Boolean availableToOther) {
	this.availableToOther = availableToOther;
}

public void setOrdinalValue(Integer ordinalValue) {
	this.ordinalValue = ordinalValue;
}

public Integer getOrdinalValue() {
	return ordinalValue;
}

/**
 * @return the availableToFedAd
 */
public Boolean getAvailableToFedAd() {
	return availableToFedAd;
}

/**
 * @param availableToFedAd the availableToFedAd to set
 */
public void setAvailableToFedAd(Boolean availableToFedAd) {
	this.availableToFedAd = availableToFedAd;
}

/**
 * @return the availableToFedAdOther
 */
public Boolean getAvailableToFedAdOther() {
	return availableToFedAdOther;
}

/**
 * @param availableToFedAdOther the availableToFedAdOther to set
 */
public void setAvailableToFedAdOther(Boolean availableToFedAdOther) {
	this.availableToFedAdOther = availableToFedAdOther;
}

/**
 * @return the availableToFedOther
 */
public Boolean getAvailableToFedOther() {
	return availableToFedOther;
}

/**
 * @param availableToFedOther the availableToFedOther to set
 */
public void setAvailableToFedOther(Boolean availableToFedOther) {
	this.availableToFedOther = availableToFedOther;
}

/**
 * @return the availableToAdOther
 */
public Boolean getAvailableToAdOther() {
	return availableToAdOther;
}

/**
 * @param availableToAdOther the availableToAdOther to set
 */
public void setAvailableToAdOther(Boolean availableToAdOther) {
	this.availableToAdOther = availableToAdOther;
}

}
