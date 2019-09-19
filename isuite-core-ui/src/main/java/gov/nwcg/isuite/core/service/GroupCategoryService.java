package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.GroupCategoryVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

public interface GroupCategoryService extends TransactionService {
	
	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public DialogueVo save(GroupCategoryVo vo, DialogueVo dialogueVo) throws ServiceException, ValidationException;
	
	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo deleteGroupCategory(GroupCategoryVo vo, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getGrid(DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param id
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo geById(Long id, DialogueVo dialogueVo) throws ServiceException;

}
