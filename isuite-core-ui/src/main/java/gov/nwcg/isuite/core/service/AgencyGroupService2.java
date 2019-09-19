package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.AgencyGroupVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

public interface AgencyGroupService2 extends TransactionService {

	public DialogueVo save(AgencyGroupVo vo, DialogueVo dialogueVo) throws ServiceException,ValidationException;
	
    public DialogueVo deleteAgencyGroup(AgencyGroupVo vo , DialogueVo dialogueVo) throws ServiceException ;
   
	public DialogueVo getGrid(DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getById(Long id, DialogueVo dialogueVo) throws ServiceException;
	
}
