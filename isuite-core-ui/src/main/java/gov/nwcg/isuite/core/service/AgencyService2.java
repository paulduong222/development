package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

public interface AgencyService2 extends TransactionService {

	public DialogueVo save(AgencyVo vo, DialogueVo dialogueVo) throws ServiceException,ValidationException;
	
    public DialogueVo deleteAgency(AgencyVo vo , DialogueVo dialogueVo) throws ServiceException ;
   
	public DialogueVo getGrid(DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getGridIncidentorGroup(Long incidentId, Long incidentGroupId,Boolean incidentOnly, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getById(Long id, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getStandardAgencies(DialogueVo dialogueVo) throws ServiceException;
	
}
