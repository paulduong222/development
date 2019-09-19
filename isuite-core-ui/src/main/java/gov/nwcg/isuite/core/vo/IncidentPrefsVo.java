package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentPrefs;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentPrefsImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.IncidentPrefsSectionNameEnum;
import gov.nwcg.isuite.framework.util.Validator;

public class IncidentPrefsVo extends AbstractVo implements PersistableVo {
   private IncidentVo incidentVo = new IncidentVo();
   private IncidentPrefsSectionNameEnum sectionName;
   private String fieldLabel = new String();
   private Integer position; 
   private Boolean selected;
   
   /**
    * Default Constructor
    */
   public IncidentPrefsVo() {
   }

   /**
    * Returns a IncidentPrefsVo instance from a IncidentPrefs entity.
    * 
    * @param entity
    *          the source IncidentPrefs entity
    * @param cascadable
    *          flag indicating whether the instance should created as a cascadable vo
    * @return
    *       instance of IncidentPrefsVo
    * @throws Exception
    */
   public static IncidentPrefsVo getInstance(IncidentPrefs entity, boolean cascadable) throws Exception {
      IncidentPrefsVo vo = new IncidentPrefsVo();

      if(null == entity)
         throw new Exception("Unable to create IncidentPrefsVo from null IncidentPrefs entity.");

      vo.setId(entity.getId());

      /*
       * Only populate fields outside of the entity Id if needed
       */
      if(cascadable){
         vo.setSectionName(entity.getSectionName());
         vo.setFieldLabel(entity.getFieldLabel());
         vo.setPosition(entity.getPosition());
         vo.setSelected(entity.isSelected());
         
         if( (null != entity.getIncidentId()) && (entity.getIncidentId()>0L) ){
        	 vo.setIncidentVo(IncidentVo.getInstance(entity.getIncident(),false));
         }
      }

      return vo;
   }

   /**
    * Returns a Collection of IncidentPrefsVo instances from a Collection of IncidentPrefs entities.
    * 
    * @param entities
    *          the source entities
    * @param cascadable
    *          flag indicating whether the instances should created as a cascadable Collection of vo's
    * @return
    *       Collection of IncidentPrefsVo objects
    * @throws Exception
    */
   public static Collection<IncidentPrefsVo> getInstances(Collection<IncidentPrefs> entities, boolean cascadable) throws Exception {
      Collection<IncidentPrefsVo> vos = new ArrayList<IncidentPrefsVo>();

      for(IncidentPrefs entity : entities){
         vos.add(IncidentPrefsVo.getInstance(entity, cascadable));
      }
      return vos;
   }

   /**
    * Returns a IncidentPrefs entity from a IncidentPrefsVo.
    * 
    * @param vo
    *          the source IncidentPrefsVo
    * @param cascadable
    *          flag indicating whether the entity instance should created as a cascadable entity
    * @param persistables
    *          Optional array of referenced persistable entities 
    * @return
    *          IncidentPrefs entity
    * @throws Exception
    */
   public static IncidentPrefs toEntity(IncidentPrefs entity, IncidentPrefsVo vo,boolean cascadable,Persistable...persistables) throws Exception {
      if (null == entity) {
         entity = new IncidentPrefsImpl();
      }

      entity.setId(vo.getId());

      if(cascadable){
         entity.setSectionName(vo.getSectionName());
         entity.setFieldLabel(vo.getFieldLabel());
         entity.setPosition(vo.getPosition());
         entity.setSelected(vo.isSelected());
         
         if(AbstractVo.hasValue(vo.getIncidentVo())){
            entity.setIncident(IncidentVo.toEntity(null, vo.getIncidentVo(),false));
         }
         
         Incident incident = (Incident)AbstractVo.getPersistableObject(persistables, IncidentImpl.class);
         if(null != incident) {
        	 entity.setIncident(incident);
         }

         /*
          * Validate the entity
          */
         validateEntity(entity);
      }

      return entity;
   }

   public static Collection<IncidentPrefs> toEntityList(Collection<IncidentPrefsVo> vos, boolean cascadable, Persistable...persistables) throws Exception {
	   Collection<IncidentPrefs> entities = new ArrayList<IncidentPrefs>();
	   
	   for(IncidentPrefsVo vo : vos) {
		   entities.add(IncidentPrefsVo.toEntity(null, vo, cascadable, persistables));
	   }
	   
	   return entities;
   }
   
   /**
    * Perform some validation on the IncidentPrefs field values against the
    * entity field definitions.
    * 
    * @param entity
    *          the source jetPort entity
    * @throws ValidationException
    */
   private static void validateEntity(IncidentPrefs entity) throws ValidationException {
      Validator.validateStringField("sectionName", entity.getSectionName().getDescription(), 30, true);
      Validator.validateStringField("fieldLabel", entity.getFieldLabel(), 300, false);//true);//TODO:  Verify the null requirement for the fieldLabel. -dbudge
      Validator.validateIntegerField("position", entity.getPosition(), true);
      Validator.validateBooleanField("selected", entity.isSelected(), true);
   }


   /**
	 * Public static comparator used to sort IncidentPrefsVo based on their positions.
	 */
   public static Comparator<IncidentPrefsVo> preferencesPositionComparator  = new Comparator<IncidentPrefsVo>() {
		public int compare(IncidentPrefsVo pref1, IncidentPrefsVo pref2) {
			Integer pos1 = pref1.getPosition();
			Integer pos2 = pref2.getPosition();
			
			if(pos2==null) return 1;
			if(pos1==null) return -1;
			
			return pos1.compareTo(pos2);
		}
   };
   
   /**
    * @return the incidentVo
    */
   public IncidentVo getIncidentVo() {
      return incidentVo;
   }

   /**
    * @param incidentVo the incidentVo to set
    */
   public void setIncidentVo(IncidentVo incidentVo) {
      this.incidentVo = incidentVo;
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

}
