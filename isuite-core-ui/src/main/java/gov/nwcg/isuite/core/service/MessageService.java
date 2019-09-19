package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.MessageBoardVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public interface MessageService extends TransactionService {
	
	public DialogueVo save(MessageBoardVo vo, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getStaticMessage(DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getPopUpGrid(DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo savePopUp(MessageBoardVo vo, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getPopUpMessages(DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo deletePopUpMessage(MessageBoardVo vo, DialogueVo dialogueVo) throws ServiceException;

}
