package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

public interface TestDateService extends TransactionService {

	public DialogueVo getVo(DialogueVo dialogueVo) throws ServiceException,ValidationException;
	
	
}
