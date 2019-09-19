package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.IapCopyVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public interface IapCopyService extends TransactionService {
	
	public DialogueVo copyPlan(IapCopyVo iapCopyVo, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo copyForm(IapCopyVo iapCopyVo, DialogueVo dialogueVo) throws ServiceException;
	
}
