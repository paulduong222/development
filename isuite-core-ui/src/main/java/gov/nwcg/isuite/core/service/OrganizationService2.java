package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

public interface OrganizationService2 extends TransactionService {

	public DialogueVo save(OrganizationVo vo, DialogueVo dialogueVo, IncidentVo ivo) throws ServiceException,ValidationException;
	
    public DialogueVo deleteOrganization(OrganizationVo vo , DialogueVo dialogueVo) throws ServiceException ;
   
	public DialogueVo getGrid(DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getById(Long id, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getGridIncidentorGroup2(Long incidentId, Long incidentGroupId,DialogueVo dialogueVo) throws ServiceException ;
	public DialogueVo getGridIncidentorGroup(Long incidentId, Long incidentGroupId, Boolean incidentOnly, DialogueVo dialogueVo) throws ServiceException;
	
}
