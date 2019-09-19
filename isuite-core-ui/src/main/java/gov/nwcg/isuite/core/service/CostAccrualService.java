package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.CostAccrualExtractVo;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public interface CostAccrualService extends TransactionService {

	public DialogueVo getGrid(Long incidentId, Long incidentGroupId,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo runExtract(String extractDate, Long incidentId, Long incidentGroupId,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo runFinalize(CostAccrualExtractVo vo,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo saveAccrualCode(IncidentAccountCodeVo vo , DialogueVo dialogueVo) throws ServiceException ;	

	public DialogueVo generateAccrualSummaryReport(Long extractId, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo generateAccrualDetailReport(Long extractId, DialogueVo dialogueVo) throws ServiceException ;	

	public DialogueVo generateAccrualAllDetailReport(Long incidentId, Long incidentGroupId,DialogueVo dialogueVo) throws ServiceException;

}
