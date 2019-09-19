package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.filter.UserImportFailureFilter;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.core.vo.UserImportFailureVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.WorkAreaVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

import java.util.Collection;

public interface UserService  {

	/**
	 * Deletes the users by setting the deleted_date to the current date.
	 * 
	 * @param userGridVos
	 * @throws ServiceException
	 */
	public void deleteUsers(Collection<UserGridVo> userGridVos) throws ServiceException;

	/**
	 * 
	 * @param userGridVos
	 * @return {@link Collection} of {@link UserGridVo} objects representing {@link User} accounts that were enabled.
	 * @throws ServiceException
	 */
	public Collection<UserGridVo> enableUsers(Collection<UserGridVo> userGridVos) throws ServiceException;

	/**
	 * 
	 * @param userGridVos
	 * @return {@link Collection} of {@link UserGridVo} objects representing {@link User} accounts that were disabled.
	 * @throws ServiceException
	 */
	public Collection<UserGridVo> disableUsers(Collection<UserGridVo> userGridVos) throws ServiceException;

	/**
	 * Saves the user.
	 * 
	 * @param userVo
	 * 			the userVo to save
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public UserVo save(UserVo userVo) throws ServiceException, ValidationException;

	/**
	 * Retrieve users to be displayed in UI grids.
	 * @param userFilter {@link UserFilter}
	 * @return {@link Collection} of {@link UserGridVo}s
	 * @throws ServiceException
	 */
	public Collection<UserGridVo> getGrid(UserFilter userFilter) throws ServiceException;

	/**
	 * Retrieve a single {@link UserVo}
	 * @param id
	 * @return a single {@link UserVo}
	 * @throws ServiceException
	 */
	public UserVo getUserById(Long id) throws ServiceException;

	/**
	 * Returns a collection of UserVo's based on the filter.
	 * 
	 * @param filter
	 * 			the userFilter to filter by
	 * @return
	 * 		collection of UserVo's
	 * @throws ServiceException
	 */
	public Collection<UserVo> getUsers(UserFilter filter) throws ServiceException;

	/**
	 * @param vos
	 * @param userId
	 * @throws ServiceException
	 */
	public Collection<OrganizationVo> addOrganizations(Collection<OrganizationVo> vos, Long userId) throws ServiceException;

	/**
	 * @param vos
	 * @param userId
	 * @throws ServiceException
	 */
	public Collection<OrganizationVo> removeOrganizations(Collection<OrganizationVo> vos, Long userId) throws ServiceException;

	/**
	 * @param vos
	 * @return
	 * @throws ServiceException
	 */
//	public String generateUserTransferData(Collection<UserGridVo> vos) throws ServiceException;
	public byte[] generateUserTransferData(Collection<UserGridVo> vos) throws ServiceException;

	/**
	 * @param xml
	 * @throws ServiceException
	 */
//	public void consumeUserTransferData(String xml, String defaultPassword) throws ServiceException;
	public void consumeUserTransferData(byte[] xmlByteArray, String defaultPassword) throws ServiceException;

	/**
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	public Collection<UserImportFailureVo> getLastImportFailures(UserImportFailureFilter filter) throws ServiceException ;	

	/**
	 * @param vo
	 * @param validUserVo
	 * @return
	 * @throws ServiceException
	 */
	public UserImportFailureVo resolveImportFailure(UserImportFailureVo vo,UserVo validUserVo) throws ServiceException;

	/**
	 * @param vo
	 * @throws ServiceException
	 */
	public void deleteImportFailure(UserImportFailureVo vo) throws ServiceException;
	
	/**
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public String checkPasswordStatus(Long userId) throws ServiceException;
	
	/**
	 * @param userId
	 * @param newPassword
	 * @throws ServiceException
	 */
	public void changePassword(Long userId, String newPassword) throws ServiceException;

	public void updateROBAgreementDate(Long userId, String robType) throws ServiceException;
	
	public void validateOldPassword(Long userId, String newPassword) throws ServiceException;

	public UserVo createSiteAdminUserAccount(UserVo userVo) throws ServiceException;

	/**
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public UserSessionVo refreshUserSessionVo(Long userId) throws ServiceException;
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public Collection<WorkAreaVo> getUpdatedUserWorkAreas(Long userId) throws ServiceException;
	
}
