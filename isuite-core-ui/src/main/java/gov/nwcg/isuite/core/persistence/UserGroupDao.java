package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.UserGroup;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.filter.UserGroupFilter;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.core.vo.UserGroupGridVo;
import gov.nwcg.isuite.core.vo.UserGroupPicklistVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

/**
 * @author bsteiner
 *
 */
public interface UserGroupDao extends TransactionSupport, CrudDao<UserGroup> {

   /**
    * Retrieve a set of User Group Grid VOs based on a set of search criterion
    * 
    * @param filter {@link UserGroupFilter} with selected criterion
    * @return {@link Collection} of {@link UserGroupGridVo} objects.
    * @throws PersistenceException
    */
   public Collection<UserGroupGridVo> getGrid(UserGroupFilter filter) throws PersistenceException;

   public Collection<UserGridVo> getAvailableUsers(Long userGroupId, UserFilter filter) throws PersistenceException;
   
   /**
    * Determine if The Group Name is Unique for the given User.
    * 
    * @param userId
    * @param groupName
    * @return true if the name is unique.
    * @throws PersistenceException
    */
   public boolean isGroupNameUniqueToUser(Long userId, String groupName, Long groupId) throws PersistenceException;
   
   /**
    * Determine if The Group Name is Unique for the system.
    * 
    * @param groupName
    * @return true if the name is unique.
    * @throws PersistenceException
    */
   public boolean isGroupNameUniqueToSystem(String groupName, Long groupId) throws PersistenceException;
   
   
   
   /**
    * Retrieve a set of UserGroupPicklist VOs based on a set of search criterion
    * 
    * @param filter {@link UserGroupFilter} with selected criterion
    * @return {@link Collection} of {@link UserGroupPicklistVo} objects.
    * @throws PersistenceException
    */
   public Collection<UserGroupPicklistVo> getPicklist(UserGroupFilter filter) throws PersistenceException;
   
   /**
    * @param userId
    * @return
    * @throws PersistenceException
    */
   public Collection<UserGroup> getUserGroups(Long userId) throws PersistenceException;
   
}
