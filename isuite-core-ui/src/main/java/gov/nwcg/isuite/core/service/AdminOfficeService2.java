package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.filter.AdminOfficeFilter;
import gov.nwcg.isuite.core.vo.AdminOfficeVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

public interface AdminOfficeService2 extends TransactionService {

	public DialogueVo getDropdownList(DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * Saves the resource.
	 * 
	 * @param persistableVo
	 * 				the resourceVo to save
	 * @throws ServiceException
	 */
	public DialogueVo save(AdminOfficeVo vo, DialogueVo dialogueVo) throws ServiceException,ValidationException;
	
    /**
     * @param vo
     * @param dialogueVo
     * @return
     * @throws ServiceException
     */
    public DialogueVo deleteAdminOffice(AdminOfficeVo vo , DialogueVo dialogueVo) throws ServiceException ;
   
	/**
	 * Returns collection of resources for a resource grid.
	 * 
	 * @param filter
	 * 			the filter values
	 * @return
	 * 		collection of adminOfficeGridVos
	 * @throws ServiceException
	 */
	public DialogueVo getGrid(AdminOfficeFilter filter, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * @param id
	 * @return
	 */
	public DialogueVo getById(Long id, DialogueVo dialogueVo) throws ServiceException;
	
}
