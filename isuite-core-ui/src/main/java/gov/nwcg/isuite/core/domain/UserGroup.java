
package gov.nwcg.isuite.core.domain;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.framework.core.domain.Persistable;

/**
 * Container object representing a user group
 * 
 * @author bsteiner
 *
 */
public interface UserGroup extends Persistable {
   
   /**
    * Retrieve the name of the group.
    * 
    * @return group name.
    */
   public String getGroupName();
   
   /**
    * Set the name of the group.
    * 
    * @param groupName
    */
   public void setGroupName(String groupName);
   
   /**
    * Retrieve the User ID of the owner of the group.
    * 
    * @return group name.
    */
   public Long getGroupOwnerId();
   
   /**
    * Set the User ID of the owner of the group.
    * 
    * @param groupName
    */
   public void setGroupOwnerId(Long groupName);
   
   /**
    * Retrieve the {@link User} who created this group.
    * 
    * @return {@link User}
    */
   public User getGroupOwner();
   
   /**
    * Set the {@link User} who created the group.
    * 
    * @param user {@link User}
    */
   public void setGroupOwner(User user);

   /**
    * Returns the userGroupUsers.
    *
    * @return 
    *		the userGroupUsers to return
    */
   public Collection<UserGroupUser> getUserGroupUsers();	

   /**
    * Sets the userGroupUsers.
    *
    * @param userGroupUsers 
    *			the userGroupUsers to set
    */
   public void setUserGroupUsers(Collection<UserGroupUser> userGroupUsers);
}
