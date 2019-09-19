package gov.nwcg.isuite.core.service;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.TimeAssignAdjustVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public interface TimePostAdjustmentService extends TransactionService {

	/**
	 * @param assignmentId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getTimeAdjustVos(Long assignmentId, DialogueVo dialogueVo) throws ServiceException;

	
	public DialogueVo getIncidentResourceAndTimeAdjustVos(Long incidentResourceId, Long assignmentId, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param timeAssignAdjustId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getById(Long timeAssignAdjustId, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo save(IncidentResourceVo incidentResourceVo,TimeAssignAdjustVo vo, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo saveBatch(IncidentResourceVo incidentResourceVo,TimeAssignAdjustVo vo, Collection<Integer> crewIds,DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param id
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo deleteAdjustment(Long id, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo deleteAdjustmentBatch(Long id, Collection<Integer> crewIds, DialogueVo dialogueVo) throws ServiceException;

}
