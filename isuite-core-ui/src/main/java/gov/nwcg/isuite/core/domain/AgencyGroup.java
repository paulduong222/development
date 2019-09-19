package gov.nwcg.isuite.core.domain;

import java.util.Collection;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public interface AgencyGroup extends Persistable {

   /**
    * Sets the code.
    * 
    * @param code 
    * 		the agencyGroup code to set
    */
   public void setCode(String code);
   
   /**
    * Returns the agencyGroup code.
    * 
    * @return 
    * 		the agencyGroup code to return
    */
   public String getCode();

   /**
    * Sets the description.
    * 
    * @param description 
    * 		the agencyGroup code description to set
    */
   public void setDescription(String description);
   
   /**
    * Returns the agencyGroup code description.
    * 
    * @return 
    * 		the agencyGroup code description to return
    */
   public String getDescription();
   
   /**
    * Returns whether or not the agencyGroup code is a standard system code.
    * 
    * @return 
    * 		the agencyGroup code standard status
    */
   public Boolean isStandard();
   
   /**
    * Sets whether or not the agencyGroup code is a standard system code.
    * 
    * @param isStandard
    * 			the agencyGroup code standard status
    */
   public void setStandard(Boolean isStandard);
   
   /**
    * @param agencies the agencies to set
	*/
   public void setAgencies(Collection<Agency> agencies);

   /**
    * @return the agencies
    */
   public Collection<Agency> getAgencies();
   
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