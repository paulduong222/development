package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.nwcg.isuite.core.domain.JetPort;
import gov.nwcg.isuite.core.domain.impl.JetPortImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.Validator;

/**
 * 
 * @author mgreen
 */
public class JetPortVo extends AbstractVo implements PersistableVo {
	private String code;
	private String description;
	private Boolean standard;
	private CountryCodeSubdivisionVo countryCodeSubdivisionVo;
	private Boolean active;
	private IncidentVo incidentVo;
	private IncidentGroupVo incidentGroupVo;

   /**
    * Default Constructor
    */
	public JetPortVo() {
	}

	/**
	 * Returns a JetPortVo instance from a JetPort entity.
	 * 
	 * @param entity
	 * 			the source JetPort entity
	 * @param cascadable
	 * 			flag indicating whether the instance should created as a cascadable vo
	 * @return
	 * 		instance of JetPortVo
	 * @throws Exception
	 */
	public static JetPortVo getInstance(JetPort entity,boolean cascadable) throws Exception {
		JetPortVo vo = new JetPortVo();

		if(null == entity)
			throw new Exception("Unable to create JetPortVo from null JetPort entity.");

		vo.setId(entity.getId());

		/*
		 * Only populate fields outside of the entity Id if needed
		 */
		if(cascadable){
			vo.setCode(entity.getCode());
			vo.setDescription(entity.getDescription());
			vo.setStandard(entity.isStandard());

			if(null != entity.getCountrySubdivision()){
				vo.setCountryCodeSubdivisionVo(CountryCodeSubdivisionVo.getInstance(entity.getCountrySubdivision(), true));
			}
			
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
    * Returns a Collection of JetPortVo instances from a Collection of JetPort entities.
    * 
    * @param entities
    *          the source entities
    * @param cascadable
    *          flag indicating whether the instances should created as a cascadable Collection of vo's
    * @return
    *       Collection of JetPortVo objects
    * @throws Exception
    */
   public static Collection<JetPortVo> getInstances(Collection<JetPort> entities, boolean cascadable) throws Exception {
      Collection<JetPortVo> vos = new ArrayList<JetPortVo>();

      for(JetPort entity : entities){
         vos.add(JetPortVo.getInstance(entity, cascadable));
      }
      return vos;
   }

	/**
	 * Returns a JetPort entity from a JetPortVo.
	 * 
	 * @param vo
	 * 			the source JetPortVo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @param persistables
	 * 			Optional array of referenced persistable entities 
	 * @return
	 * 			JetPort entity
	 * @throws Exception
	 */
	public static JetPort toEntity(JetPort entity, JetPortVo vo,boolean cascadable,Persistable...persistables) throws Exception {
	   if (null == entity) {
	      entity = new JetPortImpl();
	   }

		entity.setId(vo.getId());

		if(cascadable){
			entity.setCode(vo.getCode());
			entity.setDescription(vo.getDescription());
			entity.setStandard(vo.getStandard());

			if(null != vo.getCountryCodeSubdivisionVo()){
				entity.setCountrySubdivision(CountryCodeSubdivisionVo.toEntity(vo.getCountryCodeSubdivisionVo(), false));
			}
			
			entity.setActive(StringBooleanEnum.toEnumValue(vo.isActive()));
	         
	        if (null != vo.getIncidentVo() && LongUtility.hasValue(vo.getIncidentVo().getId())) {
	        	entity.setIncident(IncidentVo.toEntity(null, vo.getIncidentVo(), false));
	        }
	         
	        if (null != vo.getIncidentGroupVo() && LongUtility.hasValue(vo.getIncidentGroupVo().getId())) {
	        	entity.setIncidentGroup(IncidentGroupVo.toEntity(null, vo.getIncidentGroupVo(), false));
	        }

			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

	/**
	 * Perform some validation on the JetPort field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source jetPort entity
	 * @throws ValidationException
	 */
	private static void validateEntity(JetPort entity) throws ValidationException {
    	Validator.validateStringField("code", entity.getCode(), 4, true);
    	Validator.validateStringField("description", entity.getDescription(), 100,true);
      Validator.validateBooleanField("standard", entity.isStandard(), true);
	}

	/**
	 * Returns the code.
	 *
	 * @return 
	 *		the code to return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code 
	 *			the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Returns the description.
	 *
	 * @return 
	 *		the description to return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description 
	 *			the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

   /**
	 * Returns the standard.
	 *
	 * @return 
	 *		the standard to return
	 */
	@JsonProperty("standard")
	public Boolean getStandard() {
		return standard;
	}

   /**
    * Returns the standard.
    *
    * @return 
    *    the standard to return
    */
	@JsonIgnore
   public Boolean isStandard() {
      return standard;
   }

	/**
	 * Sets the standard.
	 *
	 * @param standard 
	 *			the standard to set
	 */
	@JsonProperty("standard")
	public void setStandard(Boolean standard) {
		this.standard = standard;
	}

	public CountryCodeSubdivisionVo getCountryCodeSubdivisionVo() {
		return countryCodeSubdivisionVo;
	}

	public void setCountryCodeSubdivisionVo(
			CountryCodeSubdivisionVo countryCodeSubdivisionVo) {
		this.countryCodeSubdivisionVo = countryCodeSubdivisionVo;
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
	public void setIncidentVo(IncidentVo incidentVo) {
		this.incidentVo = incidentVo;
	}
	
	/**
	 * @return the incidentVo
	 */
	public IncidentVo getIncidentVo() {
		return incidentVo;
	}
	
	/**
	 * @param incidentGroupVo the incidentGroupVo to set
	 */
	public void setIncidentGroupVo(IncidentGroupVo incidentGroupVo) {
		this.incidentGroupVo = incidentGroupVo;
	}
	
	/**
	 * @return the incidentGroupVo
	 */
	public IncidentGroupVo getIncidentGroupVo() {
		return incidentGroupVo;
	}

}
