package gov.nwcg.isuite.core.domain;

import java.util.Collection;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public interface JetPort extends Persistable{

   /**
    * Accessor for code
    * @return the jet port code, will not be null
    * @see #setCode(String)
    */
   public abstract String getCode();
   
   /**
    * Mutator for code
    * @param code the jet port code, cannot be null
    * @see #getCode()
    */
   public abstract void setCode(String code);
   
   /**
    * Accessor for description
    * @return the jet port description, will not be null
    * @see #setDescription(String)
    */
   public abstract String getDescription();
   
   /**
    * Mutator for description
    * @param description the jet port description, cannot be null
    * @see #getDescription()
    */
   public abstract void setDescription(String description);

   
   /**
    * Accessor for standard.
    * @return the standard flag, will not be null
    * @see #setStandard(Boolean)
    */
   public abstract Boolean isStandard();

   /**
    * Mutator for standard.
    * @param standard the flag to set if the item comes standard with the base application or not, can not be null
    * @see #isStandard()
    */
   public abstract void setStandard(Boolean standard);

	public CountrySubdivision getCountrySubdivision() ;

	public void setCountrySubdivision(CountrySubdivision countrySubdivision) ;

	public Long getCountrySubdivisionId() ;
	
	public void setCountrySubdivisionId(Long countrySubdivisionId) ;
	
	/**
	 * @param workPeriods the workPeriods to set
	 */
	public void setWorkPeriods(Collection<WorkPeriod> workPeriods);
	
	/**
	 * @return the workPeriods
	 */
	public Collection<WorkPeriod> getWorkPeriods();
	
	/**
	 * @param airTravels the airTravels to set
	 */
	public void setAirTravels(Collection<AirTravel> airTravels);
	
	/**
	 * @return the airTravels
	 */
	public Collection<AirTravel> getAirTravels();
	
	/**
	 * @param incident the incident to set
	 */
	public void setIncident(Incident incident);
	
	/**
	 * @return the incident
	 */
	public Incident getIncident();
	
	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId);
	
	/**
	 * @return the incidentId
	 */
	public Long getIncidentId();
	
	/**
	 * @param incidentGroup the incidentGroup to set
	 */
	public void setIncidentGroup(IncidentGroup incidentGroup);
	
	/**
	 * @return the incidentGroup
	 */
	public IncidentGroup getIncidentGroup();
	
	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId);
	
	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId();
	
   /**
	 * @param active the active to set
	 */
	public void setActive(StringBooleanEnum active);
	
	/**
	 * @return the active
	 */
	public StringBooleanEnum isActive();
	
}
