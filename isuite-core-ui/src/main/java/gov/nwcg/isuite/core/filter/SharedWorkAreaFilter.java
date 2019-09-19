package gov.nwcg.isuite.core.filter;

import java.util.Collection;

import gov.nwcg.isuite.framework.core.filter.Filter;

/**
 * Filter for Manage Shared Work Area Page
 * @author dbudge
 */

public interface SharedWorkAreaFilter extends Filter {

   /**
    * @param userName
    */
   public void setUserName(String userName);
   
   /**
    * @return userName
    */
   public String getUserName();
   
   /**
    * @param firstName
    */
   public void setFirstName(String firstName);
   
   /**
    * @return First Name
    */
   public String getFirstName();
   
   /**
    * @param lastName
    */
   public void setLastName(String lastName);
   
   /**
    * @return Last Name
    */
   public String getLastName();
   
   /**
    * @param primaryDispatchCenter
    */
   public void setPrimaryDispatchCenter(String primaryDispatchCenter);
   
   /**
    * @return Primary Dispatch Center
    */
   public String getPrimaryDispatchCenter();
   
   /**
    * @param primaryOrganization
    */
   public void setPrimaryOrganization(String primaryOrganization);
   
   /**
    * @return Primary Organization
    */
   public String getPrimaryOrganization();
   
   /**
    * @return the {@link Collection} of roleVoIds
    */
   public Collection<Integer> getRoleVoIds();

   /**
    * @param roleVoIds the {@link Collection} of roleVoIds to set
    */
   public void setRoleVoIds(Collection<Integer> roleVoIds);
   
   public Collection<Long> getRoleVoIdsFromIntegers() throws Exception;
   
   /**
	 * @param sharedUsers the sharedUsers to set
	 */
	public void setSharedUsers(Boolean sharedUsers);
	
	/**
	 * @return the sharedUsers
	 */
	public Boolean getSharedUsers();
}
