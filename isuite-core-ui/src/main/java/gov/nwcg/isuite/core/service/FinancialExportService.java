package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.FinancialExportVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public interface FinancialExportService extends TransactionService {
	
	/**
	 * @param dialogueVo
	 * @param incidentId
	 * @param incidentGroupId
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getGrid(DialogueVo dialogueVo, Long incidentId, Long incidentGroupId) throws ServiceException ;

	/**
	 * @param dialogueVo
	 * @param financialExportVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo export(DialogueVo dialogueVo, FinancialExportVo financialExportVo) throws ServiceException ;
	
	/**
	 * @param dialogueVo
	 * @param financialExportVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo save(DialogueVo dialogueVo, FinancialExportVo financialExportVo) throws ServiceException ;
	
}