package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

public interface UserSessionManagementService2 extends TransactionService {

	/**
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo startSession(UserSessionVo userSessionVo, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * @param userVo
	 * @param databaseName
	 * @param actionType
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo endSession(UserVo userVo, String databaseName, String actionType, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * @param userId
	 * @param databaseName
	 * @return
	 * @throws ServiceException
	 */
	public Boolean isUserLoggedIn(Long userId, String databaseName)  throws ServiceException;
	
	/**
	 * @param userId
	 * @param databaseName
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo userSessionPing(Long userId, String databaseName, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param userId
	 * @param databaseName
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getAllUserSessions(Long userId, String databaseName, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param userIds
	 * @param databaseName
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo disconnectUserSessions(Collection<Integer> userIds, String databaseName, DialogueVo dialogueVo) throws ServiceException ;

	
	/**
	 * @param oldUserId
	 * @param oldDatabase
	 * @param newUserSessionVo
	 * @param newDatabase
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo switchDatabaseSession(Long oldUserId, String oldDatabase, UserSessionVo newUserSessionVo, String newDatabase, DialogueVo dialogueVo) throws ServiceException ;

	public DialogueVo clearSession(DialogueVo dialogueVo) throws ServiceException;
	
}