package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.filter.UserImportFailureFilter;
import gov.nwcg.isuite.core.filter.impl.NapSearchFilterImpl;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.core.vo.UserImportFailureVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

public interface UserService2 {
	public DialogueVo updateROBAgreementDate(Long userId, String robType) throws ServiceException;

	/**
	 * Get a single {@link UserVo} based on the id.
	 * @param id {@link User} id.
	 * @param dialogueVo {@link DialogueVo}
	 * @return a {@link DialogueVo} containing a single {@link UserVo}
	 * @throws ServiceException
	 */
	public DialogueVo getById(Long id, DialogueVo dialogueVo) throws ServiceException;

	public String checkPasswordStatus(Long userId) throws ServiceException;
	
	/**
	 * 
	 * @param userFilter
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getGrid(UserFilter userFilter, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param userVo
	 * @param dbName
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo save(UserVo userVo, String dbName, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * 
	 * @param userId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo deleteUser(Long userId, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * 
	 * @param userGridVos
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo disableUsers(Collection<UserGridVo> userGridVos, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * 
	 * @param userId
	 * @param currentPassword
	 * @param newPassword
	 * @param confirmPassword
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo changePassword(
			Long userId, 
			String currentPassword,
			String newPassword, 
			String confirmPassword,
			DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * 
	 * @param xmlByteArray
	 * @param defaultPassword
	 * @param confirmDefaultPassword
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo importUserAccounts(byte[] xmlByteArray, String defaultPassword, String confirmDefaultPassword, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * 
	 * @param vos
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public byte[] exportUserAccounts(Collection<UserGridVo> vos, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * 
	 * @param filter
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getLastImportFailures(UserImportFailureFilter filter, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * 
	 * @param uifIds {@link UserImportFailureVo} ids
	 * @param validUserVos
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo resolveImportFailure(Collection<UserVo> validUserVos, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * 
	 * @param uifId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo deleteImportFailure(Long uifId, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * @param userVo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo createSiteAdminUserAccount(UserVo userVo, DialogueVo dialogueVo) throws ServiceException ;

	/**
	 * @param filter
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo searchNap(NapSearchFilterImpl filter, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo saveNapUser(UserVo userVo, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo createNewAccountManager(UserVo userVo, DialogueVo dialogueVo) throws ServiceException;
	
}
