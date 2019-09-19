package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.filter.OtherResourceCostFilter;
import gov.nwcg.isuite.core.vo.IncidentResourceOtherVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public interface OtherResourceCostService extends TransactionService{

	public DialogueVo getGrid(OtherResourceCostFilter otherResourceCostFilter, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo saveOtherResource(IncidentResourceOtherVo incidentResourceOtherVo, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getOtherResourceById(Long otherResourceId, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo deleteOtherResource(Long otherResourceId, DialogueVo dialogueVo) throws ServiceException;

}
