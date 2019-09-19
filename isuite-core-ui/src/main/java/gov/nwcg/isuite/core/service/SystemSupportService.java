package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.IncidentResourceGridVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

public interface SystemSupportService extends TransactionService {
	
	public DialogueVo getHello(String secondsDelay, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo sendRetrieveVos(Collection<IncidentResourceGridVo> vos, DialogueVo dialogueVo) throws ServiceException;
}
