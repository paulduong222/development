package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.WorkAreaVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.AuthenticationException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.security.ldap.LdapProperties;

public interface AuthenticationService extends TransactionService {

	public void setLdapProperties(LdapProperties ldapProperties);
	
	public UserSessionVo simulateLogin(Long userId) throws ServiceException,AuthenticationException;

	public UserSessionVo loginLdap(String userName, String password) throws ServiceException,AuthenticationException;
	
	/**
	 * 
	 * @param userName
	 * @param password
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 * @throws AuthenticationException
	 */
	public DialogueVo login(String userName, String password, DialogueVo dialogueVo) throws ServiceException,AuthenticationException ;
	
	public DialogueVo login2(String userName, String password, DialogueVo dialogueVo) throws ServiceException,AuthenticationException ;
	
	public UserSessionVo loginOverride(String userName, String password) throws ServiceException,AuthenticationException ;
		
	/**
	 * 
	 * @param currentWorkAreaVo
	 * @param userId
	 * @param isConcurrentSessionLogout
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo endSession(WorkAreaVo currentWorkAreaVo, Integer userId, Boolean isConcurrentSessionLogout, DialogueVo dialogueVo) throws ServiceException;
   
}