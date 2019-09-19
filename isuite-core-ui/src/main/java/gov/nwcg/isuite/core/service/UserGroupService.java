/**
 * 
 */
package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.domain.UserGroup;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.filter.UserGroupFilter;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.core.vo.UserGroupGridVo;
import gov.nwcg.isuite.core.vo.UserGroupPicklistVo;
import gov.nwcg.isuite.core.vo.UserGroupUserVo;
import gov.nwcg.isuite.core.vo.UserGroupVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

/**
 * This is the interface for the {@link EnterpriseUserGroupServiceImpl} class and allows a user
 * to add/edit/and otherwise modify a {@link UserGroup} in the system
 * 
 * @author mpoll
 */
public interface UserGroupService extends TransactionService {

	/**
	 * Get the User Group grid populated based on the provided filter set.
	 * 
	 * @param filter {@link UserGroupFilter}
	 * @return {@link Collection} of {@link UserGroupGridVo} objects.
	 * @throws ServiceException
	 */
	public Collection<UserGroupGridVo> getGrid(UserGroupFilter filter) throws ServiceException;

	/**
	 * @param vo
	 * @return
	 * @throws ServiceException
	 */
	public UserGroupVo save(UserGroupVo vo) throws ServiceException;

	/**
	 * @param userGroupId
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	public Collection<UserGridVo> getAvailableUsersGrid(Long userGroupId, UserFilter filter) throws ServiceException;

	/**
	 * @param userGroupId
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	public Collection<UserGroupUserVo> getUsersInGroupGrid(Long userGroupId, UserFilter filter) throws ServiceException;
	
	/**
	 * @param userGroupId
	 * @param vos
	 * @throws ServiceException
	 */
	public void addUsers(Long userGroupId, Collection<UserVo> vos) throws ServiceException;

	/**
	 * @param userGroupUserId
	 * @param vos
	 * @throws ServiceException
	 */
	public void saveRolesForUser(Long userGroupUserId, Collection<SystemRoleVo> vos) throws ServiceException;

	/**
	 * @param vos
	 * @throws ServiceException
	 */
	public void removeUsers(Long userGroupId,Collection<UserGroupUserVo> vos) throws ServiceException ;

	/**
	 * Remove the specified User Group
	 * 
	 * @param userGroupId The id of the {@link UserGroup} we are retrieving
	 * @throws ServiceException
	 */
	public void deleteUserGroup(Long userGroupId) throws ServiceException;

	/**
	 * Retrieve UserGroupVo data
	 * 
	 * @param id The id of the {@link UserGroup} we are retrieving
	 * @return The {@link UserGroupVo} for the id specified
	 * @throws ServiceException
	 */
	public UserGroupVo getById(Long id) throws ServiceException;

	/**
	 * Returns a Collection of UserGroupPicklistVo objects based upon the current user id
	 * @return Collection of {@link UserGroupPicklistVo} objects assigned to the current user
	 * @throws ServiceException
	 */
	public Collection<UserGroupPicklistVo> getUserGroupPicklist() throws ServiceException;

}
