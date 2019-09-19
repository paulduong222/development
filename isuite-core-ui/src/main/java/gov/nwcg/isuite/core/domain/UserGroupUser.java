package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

/**
 * Container object representing a user group user
 * 
 * @author bsteiner
 *
 */

public interface UserGroupUser extends Persistable {
   
   /**
    * Retrieve the {@link User} being associated to the {@link UserGroup}.
    * @return {@link User}
    */
   public User getUser();
   
   /**
    * Set the {@link User} being associated to the {@link UserGroup}.
    * @param user {@link User}
    */
   public void setUser(User user);
   
   /**
    * Retrieve the User id.
    * 
    * @return userId
    */
   public Long getUserId();
   
   /**
    * Set the User id
    * 
    * @param userId
    */
   public void setUserId(Long userId);
   
   /**
    * Retrieve the {@link UserGroup}
    * 
    * @return {@link UserGroup}
    */
   public UserGroup getUserGroup();
   
   /**
    * Set the {@link UserGroup}
    * 
    * @param {@link UserGroup}
    */
   public void setUserGroup(UserGroup group);
   
   /**
    * Retrieve the UserGroup id.
    * 
    * @return userGroupId
    */
   public Long getUserGroupId();
   
   /**
    * Set the UserGroup id
    * 
    * @param userGroupId
    */
   public void setUserGroupId(Long groupId);
   
//   public Collection<SystemRole> getUserGroupUserRoles();
//   
//   public void setUserGroupUserRoles(Collection<SystemRole> roles);
   
}
