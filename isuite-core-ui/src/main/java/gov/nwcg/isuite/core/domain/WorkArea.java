package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.core.vo.WorkAreaInfoVo;
import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.util.Collection;

/**
 * <p>
 * WorkAreas are used to manage access to incidents and resources.
 * </p>
 * 
 * @author doug
 * 
 */
public interface WorkArea extends Persistable {

	/**
	 * Accessor for the name of the workarea
	 * 
	 * @return name of work area, will not be null or empty
	 * @see #setName(String)
	 */
	public String getName();

	/**
	 * Accessor for name of the workArea
	 * 
	 * @param name
	 *            name of custom workArea, can not be null, or empty
	 */
	public void setName(String name);
   
   /**
    * 
    * @return
    */
   public String getDescription();
   
   /**
    *
    */
   public void setDescription(String description);
   
   /**
    * 
    * @return
    */
   public Collection<Organization> getFilteredOrganizations();
   
   /**
    * 
    * @param organizations
    */
   public void setFilteredOrganizations(Collection<Organization> organizations);
   
   /**
    * 
    * @return
    */
   public Boolean isSharedOut();

   /**
    * 
    * @param sharedOut
    */
   public void setSharedOut(Boolean sharedOut);

   /**
    * 
    * @return
    */
   public Collection<Incident> getWorkAreaIncidents();

   /**
    * 
    * @param workAreaIncidents
    */
   public void setWorkAreaIncidents(Collection<Incident> workAreaIncidents);

   /**
    * 
    * @return
    */
   public Collection<Resource> getWorkAreaResources();

   /**
    * 
    * @param workAreaResources
    */
   public void setWorkAreaResources(Collection<Resource> workAreaResources);
   
   /**
    * 
    * @return
    */
   public Organization getStandardOrganization();

   /**
    * @param standardOrganization the standardOrganization to set
    */
   public void setStandardOrganization(Organization standardOrganization);

   /**
    * @return the standardOrganizationId
    */
   public Long getStandardOrganizationId();

   /**
    * @param standardOrganizationId the standardOrganizationId to set
    */
   public void setStandardOrganizationId(Long standardOrganizationId);
   
   /**
    * Retreive a list of Users that area associated to the user
    * @return
    */
   public Collection<WorkAreaUser> getWorkAreaUsers();

   /**
    * Retreive a list of Users that area associated to the user
    * @return
    */
   public void setWorkAreaUsers(Collection<WorkAreaUser> waUsers);
   
   /**
    * Set the user object
    * @param user
    */
   public void setUser(User user);
   
   /**
    * Retrieve the User object.
    * @return
    */
   public User getUser();
   
   /**
    * Set the user object
    * @param user
    */
   public void setUserId(Long userId);
   
   /**
    * Retrieve the User object.
    * @return
    */
   public Long getUserId();

	/**
	 * Returns the incidents.
	 *
	 * @return 
	 *		the incidents to return
	 */
	public Collection<Incident> getIncidents();

	
	/**
	 * Sets the incidents.
	 *
	 * @param incidents 
	 *			the incidents to set
	 */
	public void setIncidents(Collection<Incident> incidents);
}
