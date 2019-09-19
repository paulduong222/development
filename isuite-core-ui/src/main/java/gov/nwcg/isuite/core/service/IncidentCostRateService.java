package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.filter.CostRateFilter;
import gov.nwcg.isuite.core.vo.IncidentCostRateKindVo;
import gov.nwcg.isuite.core.vo.IncidentCostRateOvhdVo;
import gov.nwcg.isuite.core.vo.IncidentCostRateStateKindVo;
import gov.nwcg.isuite.core.vo.IncidentCostRateStateVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public interface IncidentCostRateService extends TransactionService {

	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	public DialogueVo getDefaultRatesGrid(CostRateFilter filter, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * @param filter
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getIncidentCostRate(CostRateFilter filter, DialogueVo dialogueVo) throws ServiceException ;

	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveIncidentCostRateKind(IncidentCostRateKindVo vo, String category, DialogueVo dialogueVo) throws ServiceException ;

	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveIncidentCostRateOvhd(IncidentCostRateOvhdVo vo, String category, DialogueVo dialogueVo) throws ServiceException ;
	
	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveIncidentCostRateState(IncidentCostRateStateVo vo, String category, DialogueVo dialogueVo) throws ServiceException ;
	
	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveIncidentCostRateStateKind(IncidentCostRateStateKindVo vo, String category, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getIncidentCostRateOvhd(CostRateFilter filter, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getIncidentCostRateState(CostRateFilter filter, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getIncidentCostRateStateKinds(CostRateFilter filter, DialogueVo dialogueVo) throws ServiceException;	
}
