package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

public interface KindService2 extends TransactionService {

	public DialogueVo save(KindVo vo, DialogueVo dialogueVo) throws ServiceException,ValidationException;
	
    public DialogueVo deleteKind(KindVo vo , DialogueVo dialogueVo) throws ServiceException ;
   
	public DialogueVo getGrid(DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getById(Long id, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getGridIncidentorGroup(Long incidentId, Long incidentGroupId, Boolean incidentOnly, DialogueVo dialogueVo) throws ServiceException ;
	
}
