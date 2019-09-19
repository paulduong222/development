package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

public interface TimePostService extends TransactionService {

	/**
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public AssignmentTimePostVo getById(Long id) throws ServiceException;
	
	/**
	 * @param incResAssignmentId
	 * @return
	 * @throws ServiceException
	 */
	public Collection<AssignmentTimePostVo> getGrid(Integer incResAssignmentId) throws ServiceException;
	
	/**
	 * @param vo
	 * @param crewAssignmentTimeIds
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveTimePost(AssignmentTimePostVo vo, Collection<Integer> crewAssignmentTimeIds, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo saveTimePostContractor(String postType, AssignmentTimePostVo primaryVo, AssignmentTimePostVo specialVo , DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param vos
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public void deleteAssignmentTimePosts(Collection<AssignmentTimePostVo> vos) throws ServiceException;

	/**
	 * @param assignmentTimeIds
	 * @return
	 * @throws ServiceException
	 */
	public Collection<AssignmentTimePostVo> getGridCrew(Collection<Integer> assignmentTimeIds) throws ServiceException;

	public DialogueVo deleteAssignmentTimePostsCrew(Collection<IncidentResourceVo> irVos, Collection<AssignmentTimePostVo> vos,DialogueVo dialogueVo) throws ServiceException;
	
}
