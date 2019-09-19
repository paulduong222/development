package gov.nwcg.isuite.core.service;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.CustomReportCriteriaVo;
import gov.nwcg.isuite.core.vo.CustomReportVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public interface CustomReportService extends TransactionService {
	
	/**
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getAvailableViews(DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param vo
	 * @param incidentIds
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo buildSQL(DialogueVo dialogueVo, CustomReportVo vo, Collection<Long> incidentIds) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getOperators(DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getEvaluators(DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param customReportVo
	 * @param incidentIds
	 * @param incidents
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo generateCustomReport(DialogueVo dialogueVo, CustomReportVo customReportVo, Collection<Long> incidentIds, String incidents) throws ServiceException;

	/**
	 * @param dialogueVo
	 * @param customReportVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo exportCustomReport(DialogueVo dialogueVo, CustomReportVo customReportVo) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param xmlByteArray
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo importCustomReport(DialogueVo dialogueVo, byte[] xmlByteArray) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param customReportVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveCustomReport(DialogueVo dialogueVo, CustomReportVo customReportVo) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param customReportVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo deleteCustomReport(DialogueVo dialogueVo, CustomReportVo customReportVo) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getGrid(DialogueVo dialogueVo, Long userId) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param criteriaVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo deleteCriteria(DialogueVo dialogueVo, CustomReportCriteriaVo criteriaVo) throws ServiceException;
}
