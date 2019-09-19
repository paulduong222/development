package gov.nwcg.isuite.core.service;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.filter.impl.DisconnectUsersFilterImpl;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.UserSessionActionCauseEnum;
import gov.nwcg.isuite.framework.types.UserSessionActionTypeEnum;

public interface UserSessionManagementService extends TransactionService {
  
  /**
   * Get a list of the userSessions that have not been updated/active in the last x minutes, 
   * create a userSessionLogActivity record, and close the sessions.
   * 
   * This method will be called by a scheduled task.
   * @throws ServiceException 
   */
  void closeStaleUserSessions() throws ServiceException;
  
  /**
   * close user sessions for newly disabled accounts
   * 
   * @param ids
   * @param adminUserId
   */
  void closeDisabledSessions(Collection<Long> ids, Long adminUserId) throws ServiceException;
  
  /**
   * Accept a list of userIds, and close the sessions for those users
   * @param dialogueVo
   * @return {@link DialogueVo} for client use.
   * @throws ServiceException
   */
  public DialogueVo closeUserSessions(DialogueVo dialogueVo) throws ServiceException;
  
  /**
   * 
   * @param dialogueVo
   * @param disconnectUsersFilter
   * @return
   * @throws ServiceException
   */
  public DialogueVo getAllUserSessions(DialogueVo dialogueVo, Long userId) throws ServiceException;
  
  /**
   * Check to see if a userSession is already stored in the sessionsMap based on the given userId
   * 
   * @param userId
   * 
   * @return
   */
  boolean isUserLoggedIn(Long userId);
  
  /**
   * 
   * @param userId
   * @param sessionId
   * @return
   */
  public boolean isUserLoggedIn(Long userId, String sessionId);
  
  /**
   * Record userSession status changes
   * 
   * @param User user
   * @param UserSessionActionTypeEnum
   * @param UserSessionActionCauseEnum
   * @param User adminUser
   * 
   * @throws ServiceException
   */
  Boolean manageUserSession(User user, UserSessionActionTypeEnum actionType, UserSessionActionCauseEnum actionCause, User adminUser) throws ServiceException;
     
  /**
   * Client checking with server to get current status.
   * @param dialogueVo {@link DialogueVo}
   * @param userId id of {@link UserSessionVo} that sent the request.
   * @return {@link DialogueVo}
   * @throws ServiceException
   */
  public DialogueVo userSessionPing(DialogueVo dialogueVo, Integer userId) throws ServiceException;
  
  /**
   * Return the sessionId for the logged in user.
   * 
   * @param userId
   * @return
   */
  public String getUserSessionId(Long userId);
  
  /**
   * 
   * @param userId
   * @throws ServiceException
   */
  public void sendMessageToClient(String sessionId) throws ServiceException;
  
  
}