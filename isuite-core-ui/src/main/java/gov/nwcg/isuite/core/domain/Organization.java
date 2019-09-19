package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.OrganizationTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.Collection;

/**
 * Represents an organization in an organization tree.
 * <p>
 * Organizations are hierarchical, i.e., they can be represented as a tree with
 * nodes and terminals.
 * </p>
 * 
 */
public interface Organization extends Persistable{
   
   /**
    * 
    * @return
    */
   public OrganizationTypeEnum getOrganizationType();
   
   /**
    * 
    * @param type
    */
   public void setOrganizationType(OrganizationTypeEnum type);

	/**
	 * Accessor for name of organization.
	 * 
	 * @return name of organization, will not be null
	 * @see #setName(String)
	 */
	public String getName();

   /**
    * Accessor for name of organization.
    * @param name new name of organization, can't be null
    */
   public void setName(String name);

   /**
    * Retrieves the Unit Code for the Organization (if exists)
    * @return unitCode
    */
   public String getUnitCode();
   
   /**
    * Sets the Unit Code for the organization
    * 
    * @param unitCode 
    */
   public void setUnitCode(String unitCode);
   
   /**
    * @return the gaccOrganization
    */
   public Organization getGaccOrganization();

   /**
    * @param gaccOrganization the gaccOrganization to set
    */
   public void setGaccOrganization(Organization gaccOrganization);


   /**
    * @return the gaccOrganizationId
    */
   public Long getGaccOrganizationId();


   /**
    * @param gaccOrganizationId the gaccOrganizationId to set
    */
   public void setGaccOrganizationId(Long gaccOrganizationId);


   /**
    * @return the gaccOrganizations
    */
   public Collection<Organization> getGaccOrganizations();


   /**
    * @param gaccOrganizations the gaccOrganizations to set
    */
   public void setGaccOrganizations(Collection<Organization> gaccOrganizations);
   
   /**
    * 
    * @return
    */
   public Agency getAgency();
   
   /**
    * 
    * @param agency
    */
   public void setAgency(Agency agency);
   
   /**
    * @param countrySubAbbreviation the countrySubAbbreviation to set
    */
   public void setCountrySubAbbreviation(String countrySubAbbreviation);

   /**
    * @return the countrySubAbbreviation
    */
   public String getCountrySubAbbreviation();
   
   /**
    * @return A {@link Boolean} object indicating whether or not this {@link Organization} is a Dispatch Center.
    */
   public Boolean isDispatchCenter();
   
   /**
    * @param dispatchCenter Set a {@link Boolean} value indicating whether or not this {@link Organization} is a Dispatch Center.
    */
   public void setDispatchCenter(Boolean dispatchCenter);
   
   /**
    * @return supplyCache
    */
   public Boolean isSupplyCache();
   
   /**
    * @param supplyCache
    */
   public void setSupplyCache(Boolean supplyCache);
   
   /**
    * @return
    */
   public Boolean isStandard();
   
   /**
    * @param standard
    */
   public void setStandard(Boolean standard);
   
   /**
    * @return
    */
   public Boolean isLocal();
   
   /**
    * @param local
    */
   public void setLocal(Boolean local);
   
   /**
    * Returns the users.
    *
    * @return 
    *		the users to return
    */
   public Collection<User> getUsers();

   /**
    * Sets the users.
    *
    * @param users 
    *			the users to set
    */
   public void setUsers(Collection<User> users) ;

   /**
    * @return the agencyId
    */
   public Long getAgencyId();


   /**
    * @param agencyId the agencyId to set
    */
   public void setAgencyId(Long agencyId);

   /**
    * Returns the dispatchCenters.
    *
    * @return 
    *		the dispatchCenters to return
    */
   public Collection<Organization> getDispatchCenters();

   /**
    * Sets the dispatchCenters.
    *
    * @param dispatchCenters 
    *			the dispatchCenters to set
    */
   public void setDispatchCenters(Collection<Organization> dispatchCenters);
   
   /**
	* @param nonStandardResources the nonStandardResources to set
	*/
   public void setNonStandardResources(Collection<Resource> nonStandardResources);
	
   /**
	* @return the nonStandardResources
	*/
   public Collection<Resource> getNonStandardResources();
   
   /**
	 * @param resources the resources to set
	 */
	public void setResources(Collection<Resource> resources);

	/**
	 * @return the resources
	 */
	public Collection<Resource> getResources();
	
	/**
	 * @param incidents the incidents to set
	 */
	public void setIncidents(Collection<Incident> incidents);

	/**
	 * @return the incidents
	 */
	public Collection<Incident> getIncidents();
	
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
	
	/**
	 * @param incidentOrgs the incidentOrgs to set
	 */
	public void setIncidentOrgs(Collection<IncidentOrg> incidentOrgs);

	/**
	 * @return the incidentOrgs
	 */
	public Collection<IncidentOrg> getIncidentOrgs();
	
	/**
	 * @param resourceHomeUnitContacts the resourceHomeUnitContacts to set
	 */
	public void setResourceHomeUnitContacts(Collection<ResourceHomeUnitContact> resourceHomeUnitContacts);

	/**
	 * @return the resourceHomeUnitContacts
	 */
	public Collection<ResourceHomeUnitContact> getResourceHomeUnitContacts();
	
	/**
	 * @param rscTrainingTrainers the rscTrainingTrainers to set
	 */
	public void setRscTrainingTrainers(Collection<RscTrainingTrainer> rscTrainingTrainers);
	/**
	 * @return the rscTrainingTrainers
	 */
	public Collection<RscTrainingTrainer> getRscTrainingTrainers();
   
}
