package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.nwcg.isuite.core.domain.IncidentGroupPrefs;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupPrefsImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.IncidentPrefsSectionNameEnum;
import gov.nwcg.isuite.framework.util.Validator;

public class IncidentGroupPrefsVo extends AbstractVo implements PersistableVo {
   private IncidentGroupVo incidentGroupVo;
   private Long incidentGroupId;
   private IncidentPrefsSectionNameEnum sectionName;
   private String fieldLabel;
   private Integer position;
   private Boolean selected;
   
   /**
    * Default Constructor
    */
   public IncidentGroupPrefsVo() {
   }

   /**
    * Returns a IncidentGroupPrefsVo instance from a IncidentGroupPrefs entity.
    * 
    * @param entity
    *          the source IncidentGroupPrefs entity
    * @param cascadable
    *          flag indicating whether the instance should created as a cascadable vo
    * @return
    *       instance of IncidentGroupPrefsVo
    * @throws Exception
    */
   public static IncidentGroupPrefsVo getInstance(IncidentGroupPrefs entity, boolean cascadable) throws Exception {
      IncidentGroupPrefsVo vo = new IncidentGroupPrefsVo();

      if(null == entity)
         throw new Exception("Unable to create IncidentGroupPrefsVo from null IncidentGroupPrefs entity.");

      vo.setId(entity.getId());

      /*
       * Only populate fields outside of the entity Id if needed
       */
      if(cascadable){
         vo.setSectionName(entity.getSectionName());
         vo.setFieldLabel(entity.getFieldLabel());
         vo.setPosition(entity.getPosition());
         vo.setSelected(entity.isSelected());         
         
         if( (null != entity.getIncidentGroupId()) && (entity.getIncidentGroupId()>0L) ){
        	vo.setIncidentGroupId(entity.getIncidentGroupId());
        	vo.setIncidentGroupVo(IncidentGroupVo.getInstance(entity.getIncidentGroup(), false));
         }

      }

      return vo;
   }

   /**
    * Returns a Collection of IncidentGroupPrefsVo instances from a Collection of IncidentGroupPrefs entities.
    * 
    * @param entities
    *          the source entities
    * @param cascadable
    *          flag indicating whether the instances should created as a cascadable Collection of vo's
    * @return
    *       Collection of IncidentGroupPrefsVo objects
    * @throws Exception
    */
   public static Collection<IncidentGroupPrefsVo> getInstances(Collection<IncidentGroupPrefs> entities, boolean cascadable) throws Exception {
      Collection<IncidentGroupPrefsVo> vos = new ArrayList<IncidentGroupPrefsVo>();

      for(IncidentGroupPrefs entity : entities){
         vos.add(IncidentGroupPrefsVo.getInstance(entity, cascadable));
      }
      return vos;
   }

   /**
    * Returns a IncidentGroupPrefs entity from a IncidentGroupPrefsVo.
    * 
    * @param vo
    *          the source IncidentGroupPrefsVo
    * @param cascadable
    *          flag indicating whether the entity instance should created as a cascadable entity
    * @param persistables
    *          Optional array of referenced persistable entities 
    * @return
    *          IncidentGroupPrefs entity
    * @throws Exception
    */
   public static IncidentGroupPrefs toEntity(IncidentGroupPrefs entity, IncidentGroupPrefsVo vo,boolean cascadable,Persistable...persistables) throws Exception {
      if (null == entity) {
         entity = new IncidentGroupPrefsImpl();
      }

      entity.setId(vo.getId());

      if(cascadable){
         entity.setSectionName(vo.getSectionName());
         entity.setFieldLabel(vo.getFieldLabel());
         entity.setPosition(vo.getPosition());
         entity.setSelected(vo.isSelected());
         entity.setIncidentGroupId(vo.getIncidentGroupId());
         entity.setIncidentGroup(IncidentGroupVo.toEntity(null, vo.getIncidentGroupVo(), false, null));
         

         /*
          * Validate the entity
          */
         validateEntity(entity);
      }

      return entity;
   }

   public static Collection<IncidentGroupPrefs> toEntityList(Collection<IncidentGroupPrefsVo> vos, boolean cascadable, Persistable...persistables) throws Exception {
	   Collection<IncidentGroupPrefs> entities = new ArrayList<IncidentGroupPrefs>();
	   
	   for(IncidentGroupPrefsVo vo : vos) {
		   entities.add(IncidentGroupPrefsVo.toEntity(null, vo, cascadable, persistables));
	   }
	   return entities;
   }

   /**
    * Perform some validation on the IncidentGroupPrefs field values against the
    * entity field definitions.
    * 
    * @param entity
    * @throws ValidationException
    */
   private static void validateEntity(IncidentGroupPrefs entity) throws ValidationException {
      Validator.validateStringField("sectionName", entity.getSectionName().getDescription(), 30, true);
      Validator.validateStringField("fieldLabel", entity.getFieldLabel(), 300, false);//true);//TODO:  Verify the null requirement for the fieldLabel. -dbudge
      Validator.validateIntegerField("position", entity.getPosition(), true);
      Validator.validateBooleanField("selected", entity.isSelected(), true);
   }

   /**
    * Returns the sectionName.
    *
    * @return 
    *    the sectionName to return
    */
   public IncidentPrefsSectionNameEnum getSectionName() {
      return sectionName;
   }

   /**
    * Sets the sectionName.
    *
    * @param sectionName 
    *       the sectionName to set
    */
   public void setSectionName(IncidentPrefsSectionNameEnum sectionName) {
      this.sectionName = sectionName;
   }

   /**
    * Returns the fieldLabel.
    *
    * @return 
    *    the fieldLabel to return
    */
   public String getFieldLabel() {
      return fieldLabel;
   }

   /**
    * Sets the fieldLabel.
    *
    * @param fieldLabel 
    *       the fieldLabel to set
    */
   public void setFieldLabel(String fieldLabel) {
      this.fieldLabel = fieldLabel;
   }

   /**
    * Returns the position.
    *
    * @return 
    *    the position to return
    */
   public Integer getPosition() {
      return position;
   }

   /**
    * Sets the position.
    *
    * @param position 
    *       the position to set
    */
   public void setPosition(Integer position) {
      this.position = position;
   }

   /**
    * Returns the selected value.
    *
    * @return 
    *    the selected value to return
    */
	@JsonProperty("selected")
   public Boolean getSelected() {
      return selected;
   }

   /**
    * Returns the selected value.
    *
    * @return 
    *    the selected value to return
    */
	@JsonIgnore
   public Boolean isSelected() {
      return selected;
   }

   /**
    * Sets the selected value.
    *
    * @param selected 
    *       the selected value to set
    */
	@JsonProperty("selected")
   public void setSelected(Boolean selected) {
      this.selected = selected;
   }

/**
 * @return the incidentGroupId
 */
public Long getIncidentGroupId() {
	return incidentGroupId;
}

/**
 * @param incidentGroupId the incidentGroupId to set
 */
public void setIncidentGroupId(Long incidentGroupId) {
	this.incidentGroupId = incidentGroupId;
}

public IncidentGroupVo getIncidentGroupVo() {
	return incidentGroupVo;
}

public void setIncidentGroupVo(IncidentGroupVo incidentGroupVo) {
	this.incidentGroupVo = incidentGroupVo;
}

}
