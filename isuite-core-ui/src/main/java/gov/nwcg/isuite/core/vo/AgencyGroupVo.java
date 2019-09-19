package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.nwcg.isuite.core.domain.AgencyGroup;
import gov.nwcg.isuite.core.domain.impl.AgencyGroupImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.Validator;

@JsonAutoDetect(fieldVisibility = Visibility.PUBLIC_ONLY)
public class AgencyGroupVo extends AbstractVo implements PersistableVo {
   private String code;
   private String description;
   private Boolean standard;
   private Boolean active;
   private IncidentVo incidentVo;
   private IncidentGroupVo incidentGroupVo;

   /**
    * Default Constructor
    */
   public AgencyGroupVo() {
      
   }
   
   /**
    * Returns a AgencyGroupVo instance from a AgencyGroup entity.
    * 
    * @param entity
    *          the source entity
    * @param cascadable
    *          flag indicating whether the instance should created as a cascadable vo
    * @return
    *       instance of AgencyGroupVo
    * @throws Exception
    */
   public static AgencyGroupVo getInstance(AgencyGroup entity,boolean cascadable) throws Exception {
      AgencyGroupVo vo = new AgencyGroupVo();

      if(null == entity)
         throw new Exception("Unable to create AgencyGroupVo from null AgencyGroup entity.");

      vo.setId(entity.getId());

      if(cascadable){
         vo.setCode(entity.getCode());
         vo.setDescription(entity.getDescription());
         vo.setStandard(entity.isStandard());
         vo.setActive(StringBooleanEnum.toBooleanValue(entity.isActive()));
         
         if(null != entity.getIncident()) {
        	 vo.setIncidentVo(IncidentVo.getInstance(entity.getIncident(), true));
         }
         
         if(null != entity.getIncidentGroup()) {
        	 vo.setIncidentGroupVo(IncidentGroupVo.getInstance(entity.getIncidentGroup(), true));
         }
      }
      return vo;
   }

   /**
    * Returns a Collection of AgencyGroupVo instances from a Collection of AgencyGroup entities.
    * 
    * @param entities
    *          the source entities
    * @param cascadable
    *          flag indicating whether the instances should created as a cascadable Collection of vo's
    * @return
    *       Collection of AgencyGroupVo objects
    * @throws Exception
    */
   public static Collection<AgencyGroupVo> getInstances(Collection<AgencyGroup> entities, boolean cascadable) throws Exception {
      Collection<AgencyGroupVo> vos = new ArrayList<AgencyGroupVo>();

      for(AgencyGroup entity : entities){
         vos.add(AgencyGroupVo.getInstance(entity, cascadable));
      }
      return vos;
   }

   /**
    * Returns a AgencyGroup entity from a vo.
    * 
    * @param vo
    *          the source vo
    * @param cascadable
    *          flag indicating whether the entity instance should created as a cascadable entity
    * @return
    *          instance of AgencyGroup entity
    * @throws Exception
    */
   public static AgencyGroup toEntity(AgencyGroup entity, AgencyGroupVo vo, boolean cascadable) throws Exception {
      if(null == entity)
         entity=new AgencyGroupImpl();

      entity.setId(vo.getId());

      if(cascadable){
         entity.setCode(vo.getCode());
         entity.setDescription(vo.getDescription());
         entity.setStandard(vo.isStandard());
         entity.setActive(StringBooleanEnum.toEnumValue(vo.isActive()));
         
         if (null != vo.getIncidentVo()) {
			entity.setIncident(IncidentVo.toEntity(null, vo.getIncidentVo(), false));
         }
         
         if (null != vo.getIncidentGroupVo()) {
        	 entity.setIncidentGroup(IncidentGroupVo.toEntity(null, vo.getIncidentGroupVo(), false));
         }
         
         /*
          * Validate the entity
          */
          //validateEntity(entity);

      }

      return entity;
   }

   /**
    * Perform some validation on the incident entity field values against the
    * entity field definitions.
    * 
    * @param entity
    *          the source AgencyGroup entity
    * @throws ValidationException
    */
   private static void validateEntity(AgencyGroup entity) throws ValidationException {
      Validator.validateStringField("code", entity.getCode(), 20, true);
      Validator.validateStringField("description", entity.getDescription(), 1024, true);
      Validator.validateBooleanField("standard", entity.isStandard(), true);
   }   

   /**
    * @return the code
    */
   public String getCode() {
      return code;
   }

   /**
    * @param code the code to set
    */
   public void setCode(String code) {
      this.code = code;
   }

   /**
    * @return the description
    */
   public String getDescription() {
      return description;
   }

   /**
    * @param description the description to set
    */
   public void setDescription(String description) {
      this.description = description;
   }

   /**
    * @return the standard
    */
	@JsonProperty("standard")
   public Boolean getStandard() {
      return standard;
   }

   /**
    * @return the standard
    */
	@JsonIgnore
   public Boolean isStandard() {
      return standard;
   }

   /**
    * @param standard the standard to set
    */
	@JsonProperty("standard")
   public void setStandard(Boolean standard) {
      this.standard = standard;
   }
   
   /**
    * @return the active
    */
	@JsonProperty("active")
   public Boolean getActive() {
      return active;
   }

   /**
    * @return the active
    */
	@JsonIgnore
   public Boolean isActive() {
      return active;
   }

   /**
    * @param active the active to set
    */
	@JsonProperty("active")
   public void setActive(Boolean active) {
      this.active = active;
   }

	/**
	 * @param incidentVo the incidentVo to set
	 */
	@JsonIgnore
	public void setIncidentVo(IncidentVo incidentVo) {
		this.incidentVo = incidentVo;
	}
	
	/**
	 * @return the incidentVo
	 */
	@JsonIgnore
	public IncidentVo getIncidentVo() {
		return incidentVo;
	}
	
	/**
	 * @param incidentGroupVo the incidentGroupVo to set
	 */
	@JsonIgnore
	public void setIncidentGroupVo(IncidentGroupVo incidentGroupVo) {
		this.incidentGroupVo = incidentGroupVo;
	}
	
	/**
	 * @return the incidentGroupVo
	 */
	@JsonIgnore
	public IncidentGroupVo getIncidentGroupVo() {
		return incidentGroupVo;
	}
   
}
