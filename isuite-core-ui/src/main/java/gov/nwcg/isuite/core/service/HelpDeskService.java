package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public interface HelpDeskService extends TransactionService {


	/**
	 * @param code
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo generateSiteAccessKey(String code, DialogueVo dialogueVo) throws ServiceException;
	

	/**
	 * @param code
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo recoverSiteDatabasePassword(String code, DialogueVo dialogueVo) throws ServiceException;

}
