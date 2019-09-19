package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.DuplicateItemException;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;
import java.util.Date;

public interface UserDao extends TransactionSupport, CrudDao<User>{

   public Long getUserId(String loginName) throws PersistenceException;
   
	public void save(User user) throws PersistenceException,DuplicateItemException;

	/**
	 * Saves or updates a {@link Collection} of {@link User} objects.
	 * @param users {@link Collection} of {@link User} objects to be saved or updated.
	 * @throws PersistenceException
	 */
	public void saveAll(Collection<User> users) throws PersistenceException;

	/**
	 * Retrieve a {@link Collection} of {@link UserGridVo} objects based on the
	 * provided filter.
	 * @param filter {@link UserFilter}
	 * @return A {@link Collection} of {@link UserGridVo} objects
	 * @throws PersistenceException
	 */
	public Collection<UserGridVo> getGrid(UserFilter filter) throws PersistenceException;

	/**
	 * Retrieve a single {@link UserVo}
	 * @param id
	 * @return a single {@link UserVo}
	 * @throws PersistenceException
	 */
	public UserVo getUserById(Long id) throws PersistenceException;

	/**
	 * @param userIds
	 * @throws PersistenceException
	 */
	public void enableUsers(Collection<Long> userIds) throws PersistenceException;

	/**
	 * @param userIds
	 * @throws PersistenceException
	 */
	public void disableUsers(Collection<Long> userIds) throws PersistenceException;

	/**
	 * @param userIds
	 * @throws PersistenceException
	 */
	public void deleteUsers(Collection<Long> userIds) throws PersistenceException;

	/**
	 * @param id
	 * @throws PersistenceException
	 */
	public void updateLastLogin(Long id) throws PersistenceException;

	/**
	 * @param loginName
	 * @return
	 * @throws PersistenceException
	 */
	public User getByLoginName(String loginName) throws PersistenceException;

	/**
	 * @param entity
	 * @throws PersistenceException
	 */
	public void merge(User entity) throws PersistenceException;

	/**
	 * Returns list of permission keys that the user has authorizations to execute.
	 * 
	 * @param userId
	 * 			the user id
	 * @return
	 * 			collection of permission keys
	 * @throws PersistenceException
	 */
	public Collection<String> getPermissionsForUser(Long userId) throws PersistenceException;

	/**
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<User> getUsers(UserFilter filter) throws PersistenceException;
	
	/**
	 * @param ids
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<User> getUsersByIds(Collection<Long> ids) throws PersistenceException;
	
	public Collection<User> getUserPasswordExpirations(int pwdDays, Date dt) throws PersistenceException;

	public void resetSkipAuditInfo();

	public void createWorkAreaUserAssoc1(Long userId,Long pdcId) throws PersistenceException;

	public void createWorkAreaUserAssoc2(Long userId) throws PersistenceException;

	public void removeUserFromAccessLists(Long userId) throws PersistenceException;

	public Collection<UserVo> getDataStewardUsers() throws PersistenceException;
	
}
