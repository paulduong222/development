package gov.nwcg.isuite.core.service;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.IncidentResourceDailyCostVo;
import gov.nwcg.isuite.core.vo.IncidentResourceGridVo;
import gov.nwcg.isuite.core.vo.IncidentResourceOtherGridVo;
import gov.nwcg.isuite.core.vo.IncidentResourceOtherVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public interface IncidentResourceDailyCostService extends TransactionService {

	/**
	 * @param irGridVo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo runManualResourceDailyCosts(IncidentResourceGridVo irGridVo, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo runManualResourcesDailyCosts(IncidentResourceGridVo primarySelectedGridVo,Collection<IncidentResourceGridVo> irGridVos, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo runManualResourceOtherDailyCosts(IncidentResourceOtherGridVo iroGridVo, DialogueVo dialogueVo) throws ServiceException ;	
	
	public DialogueVo runManualResourcesOtherDailyCosts(IncidentResourceOtherGridVo primVo, Collection<IncidentResourceOtherGridVo> gridVos,
						Long incidentId, Long incidentGroupId,DialogueVo dialogueVo) throws ServiceException ;	
	
	/**
	 * @param incidentId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo runManualIncidentDailyCosts(Long incidentId, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * @param incidentGroupId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo runManualIncidentGroupDailyCosts(Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException ;
	
	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo deleteDailyCost(IncidentResourceDailyCostVo vo, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * Saves the Incident Resource Daily Cost Record.
	 * 
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveDailyCost(IncidentResourceDailyCostVo vo, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * @param incidentResourceId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getResourceDailyCosts(Long incidentResourceId, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getResourceOtherDailyCosts(Long incidentResourceOtherId, DialogueVo dialogueVo) throws ServiceException ;

	/**
	 * @param incidentId
	 * @param incidentGroupId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getIncidentTotalCost(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException ;

	public DialogueVo runUpdateRatesResource(IncidentResourceVo irVo, DialogueVo dialogueVo) throws ServiceException ;

	public DialogueVo runUpdateRatesResourceOther(IncidentResourceOtherVo irVo, DialogueVo dialogueVo) throws ServiceException ;
	
	public DialogueVo runUpdateRates(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException ;

	public DialogueVo runDailyCostsNextSequence(DialogueVo dialogueVo ) throws ServiceException;
}
