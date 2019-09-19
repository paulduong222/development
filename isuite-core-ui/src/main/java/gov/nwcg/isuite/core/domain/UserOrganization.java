/**
 * 
 */
package gov.nwcg.isuite.core.domain;


/**
 * @author dbudge
 *
 */
public interface UserOrganization {

   /**
    * @return userId
    */
   public Long getUserId();
   
   /**
    * @param The userId to set.
    */
   public void setUserId(Long userId);
   
   /**
    * @return organizationId
    */
   public Long getOrganizationId();
   
   /**
    * @param organizationId
    */
   public void setOrganizationId(Long organizationId);
   
   /**
    * @return A {@link User} object.
    */
   public User getUser();
   
   /**
    * @param user The {@link User} object to set.
    */
   public void setUser(User user);
   
   /**
    * @return An {@link Organization} object.
    */
   public Organization getOrganization();
   
   /**
    * @param org The {@link Organization} object to set.
    */
   public void setOrganization(Organization organization);
   
   
}
