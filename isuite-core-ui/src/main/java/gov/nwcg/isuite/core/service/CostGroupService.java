package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.filter.CostGroupAgencyDayShareFilter;
import gov.nwcg.isuite.core.filter.CostGroupFilter;
import gov.nwcg.isuite.core.vo.CostGroupAgencyDaySharePercentageVo;
import gov.nwcg.isuite.core.vo.CostGroupAgencyDayShareVo;
import gov.nwcg.isuite.core.vo.CostGroupResourceGridVo;
import gov.nwcg.isuite.core.vo.CostGroupVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

public interface CostGroupService extends TransactionService {
	
	/**
	 * @param costGroupFilter
	 * @param dialogueVor
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getGrid(CostGroupFilter costGroupFilter, DialogueVo dialogueVor) throws ServiceException;
	
	/**	 * @param costGroupId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getById(Long costGroupId, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param costGroupVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo save(DialogueVo dialogueVo, CostGroupVo costGroupVo) throws ServiceException;
	
	/**
	 * @param costGroupId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo deleteCostGroup(Long costGroupId, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param costGroupFilter
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getCostGroups(DialogueVo dialogueVo, CostGroupFilter costGroupFilter) throws ServiceException;
	
	/**
	 * @param costGroupId
	 * @param resources
	 * @param otherResources
	 * @param dialogueVo
	 * @return
	 */
	public DialogueVo saveCostGroupResources(Long costGroupId, Collection<CostGroupResourceGridVo> resources, Collection<CostGroupResourceGridVo> otherResources, DialogueVo dialogueVo);
	
	/**
	 * @param dialogueVo
	 * @param costGroupId
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getCostGroupResourceGrids(DialogueVo dialogueVo, Long costGroupId) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param costGroupAgencyDayShareId
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo deleteCostGroupAgencyDayShare(DialogueVo dialogueVo, Long costGroupAgencyDayShareId) throws ServiceException;
	
	public DialogueVo deleteCostGroupAgencyDaySharePct(DialogueVo dialogueVo, Long costGroupAgencyDaySharePctId) throws ServiceException ;
	
	/**
	 * @param dialogueVo
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	//public DialogueVo getAgencyDayShareById(DialogueVo dialogueVo, Long id) throws ServiceException;
	
	/**
	 * 
	 * @param dialogueVo
	 * @param costGroupId
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getAgencyDayShareGrid(DialogueVo dialogueVo, Long costGroupId) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	//public DialogueVo getAgencyDaySharePercentageById(DialogueVo dialogueVo, Long id) throws ServiceException;
	
	/**
	 * @param dialogVo
	 * @param costGroupFilter
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getAgencyDaySharePercentageGrid(DialogueVo dialogueVo, CostGroupAgencyDayShareFilter costGroupAgencyDayShareFilter) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @param costGroupAgencyDaySharePercentageVos
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveAgencyDaySharePercentages(DialogueVo dialogueVo, Collection<CostGroupAgencyDaySharePercentageVo> costGroupAgencyDaySharePercentageVos) throws ServiceException;

	public DialogueVo saveAgencyDaySharePercentage(CostGroupAgencyDaySharePercentageVo costGroupAgencyDaySharePercentageVo, DialogueVo dialogueVo ) throws ServiceException ;

	public DialogueVo getAgencyDaySharePercentageById(Long id, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getAgencyDayShareById(Long id, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo saveAgencyDayShare(CostGroupAgencyDayShareVo vo, DialogueVo dialogueVo ) throws ServiceException;
	
}
