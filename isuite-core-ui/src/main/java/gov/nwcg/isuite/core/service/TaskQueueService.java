package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.TaskQueueVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public interface TaskQueueService extends TransactionService {

	public DialogueVo getSupportedTaskQueueList(DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getCurrentTaskQueues(DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo saveTaskQueue(TaskQueueVo vo, DialogueVo dialogueVo) throws ServiceException ;
	
}
