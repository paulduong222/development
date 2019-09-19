package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;


/**
 * This is the interface for the {@link SitePasswordResetServiceImpl} class and allows a user
 * to reset their password.
 * 
 * @author bniebauer
 */
public interface SitePasswordResetService {
	
	   /**
	    * Resets the Site Admin Password
	    * 
	    * @param newPwd The password we are resetting to.
	    * @param dialogueVo
	    * @throws ServiceException
	    */
public DialogueVo resetSiteAdminPassword(String newPwd, DialogueVo dialogueVo) throws ServiceException;

/**
 * Generates a new password key
 * 
 * @param pwd
 * @param confirmPwd
 * @param dialogueVo
 * @throws ServiceException
 */
public DialogueVo generateNewPasswordKey(String pwd, String confirmPwd, DialogueVo dialogueVo) throws ServiceException;
}
