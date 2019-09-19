package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.UserGroup;
import gov.nwcg.isuite.core.domain.UserGroupUser;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.vo.UserGroupUserGridVo;
import gov.nwcg.isuite.core.vo.UserGroupUserVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

/**
 * @author bsteiner
 *
 */
public interface UserGroupUserDao extends TransactionSupport, CrudDao<UserGroupUser> {

   /**
    * Retrieves All Users Assigned to a specific UserGroup
    * @param userGroupId id of the {@link UserGroup}, cannot be null
    * @param filter {@link UserFilter}, cannot be null
    * @return a {@link Collection} of {@link UserGroupUserGridVo} objects
    * @throws PersistenceException
    */
   public Collection<UserGroupUserGridVo> getAssignedUsersGrid(Long userGroupId, 
            UserFilter filter) throws PersistenceException;
   
   /**
    * Retrieves All Users not already associated with a specific UserGroup
    * @param userGroupId id of the {@link UserGroup}, cannot be null
    * @param filter {@link UserFilter}, cannot be null
    * @return a {@link Collection} of {@link UserGroupUserGridVo} objects
    */
   public Collection<UserGroupUserGridVo> getAvailableUsersByUserGroupId(Long userGroupId, 
            UserFilter filter) throws PersistenceException;

   /**
    * Returns a collection of user group users for the specified userGroupIds.
    * 
    * @param userGroupIds
    * 		collection of userGroupIds to filter by
    * @return
    * 		collection of userGroupUserImpl's
    * @throws PersistenceException
    */
   public Collection<UserGroupUser> getUserGroupUsers(Collection<Long> userGroupIds) throws PersistenceException;
   
   /**
    * Returns a collection of user group users vos for a specific userGroupId
    * and set of search criterion
    * 
    * @param userGroupId
    * @param filter
    * @return
    * @throws PersistenceException
    */
   public Collection<UserGroupUserVo> getUsersInGroupGrid(Long userGroupId, UserFilter filter) throws PersistenceException;
   
   public void deleteUsersById(Collection<Long> ids) throws PersistenceException;

}
