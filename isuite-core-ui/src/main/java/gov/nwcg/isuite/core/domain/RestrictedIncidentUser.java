package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.RestrictedIncidentUserTypeEnum;

import java.util.Date;

/**
 * Domain object used to represent a Restricted Incident User association.
 * <br />The unique key for this data object is: 
 * <ul>
 *   <li>userId</li>
 *   <li>incidentId</li>
 *   <li>accessEndDate</li>
 * </ul> 
 *
 * @author bsteiner
 */
public interface RestrictedIncidentUser extends Persistable  {

   /**
    * Retrieve the User being associated to the Restricted Incident.
    * 
    * @return the user
    */
   public User getUser();
   
   /**
    * Set the User being associated to the User Group.
    * 
    * @param user the user
    */
   public void setUser(User user);
   
   /**
    * Retrieve the User id.
    * 
    * @return the user id
    */
   public Long getUserId();
   
   /**
    * Set the User id.
    * 
    * @param userId the user id
    */
   public void setUserId(Long userId);
   
   /**
    * Retrieve the Restricted Incident.
    * 
    * @return the incident
    */
   public Incident getIncident();
   
   /**
    * Set the Incident.
    * 
    * @param incident the incident
    */
   public void setIncident(Incident incident);
   
   /**
    * Retrieve the Incident id.
    * 
    * @return the incident id
    */
   public Long getIncidentId();
   
   /**
    * Set the Restricted Incident's id.
    * 
    * @param incidentId the incident id
    */
   public void setIncidentId(Long incidentId);
   
   /**
    * Retrieve the user type.
    * 
    * @return the user type
    */
   public RestrictedIncidentUserTypeEnum getUserType();
   
   /**
    * Set the user type.
    * 
    * @param type the type
    */
   public void setUserType(RestrictedIncidentUserTypeEnum type);
   
   /**
    * Gets the access end date.
    * 
    * @return the access end date
    */
   public Date getAccessEndDate();
   
   /**
    * Sets the access end date.
    * 
    * @param endDate the new access end date
    */
   public void setAccessEndDate(Date endDate);
   
   /**
    * Access granted by.
    * 
    * @param loginName the login name
    * 
    * @return the string
    */
   public void setAccessGrantedBy(String loginName);
   
   /**
    * Gets the access granted by.
    * 
    * @return the access granted by
    */
   public String getAccessGrantedBy();
   
//   public Collection<SystemRole> getUserRoles();
//   
//   public void setUserRoles(Collection<SystemRole> userRoles);
   
	public Date getDefaultCheckinDate();
	
	public void setDefaultCheckinDate(Date defaultCheckinDate) ;
	
	public String getDefaultCheckinType() ;
	
	public void setDefaultCheckinType(String defaultCheckinType);
}
