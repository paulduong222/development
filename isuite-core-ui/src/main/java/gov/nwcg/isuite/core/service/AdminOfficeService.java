package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.filter.AdminOfficeFilter;
import gov.nwcg.isuite.core.vo.AdminOfficeGridVo;
import gov.nwcg.isuite.core.vo.AdminOfficeVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

import java.util.Collection;

/**
 * @author Geoff Dyer
 *
 */
public interface AdminOfficeService extends TransactionService {

	/**
	 * Saves the resource.
	 * 
	 * @param persistableVo
	 * 				the resourceVo to save
	 * @throws ServiceException
	 */
	public AdminOfficeVo save(AdminOfficeVo persistableVo) throws ServiceException,ValidationException;
	

    /**
     * @param vo
     * @param dialogueVo
     * @return
     * @throws ServiceException
     */
    public DialogueVo saveAdminOffice(AdminOfficeVo vo , DialogueVo dialogueVo) throws ServiceException;
	
    /**
     * @param vo
     * @param dialogueVo
     * @return
     * @throws ServiceException
     */
    public DialogueVo deleteAdminOffice2(AdminOfficeVo vo , DialogueVo dialogueVo) throws ServiceException ;
   
	/**
	 * Deletes the resource.
	 * 
	 * @param persistableVo
	 * 			the resourceVo to delete
	 * @throws ServiceException
	 */
	public void deleteAdminOffice(AdminOfficeVo persistableVo) throws ServiceException;
	
	/**
	 * Saves the collection of resources.
	 * 
	 * @param persistableList
	 * 			the collection of resourceVos to save
	 * @throws ServiceException
	 */
	public void saveAll(Collection<AdminOfficeVo> persistableList) throws ServiceException,ValidationException;
	
	/**
	 * Returns the resource with the supplied id.
	 * 
	 * @param id
	 * 			the id to find
	 * @return
	 * 		the resourceVo with the supplied id
	 * @throws ServiceException
	 */
	public AdminOfficeVo getById(Long id) throws ServiceException;
	
	/**
	 * Returns collection of resources for a resource grid.
	 * 
	 * @param filter
	 * 			the filter values
	 * @return
	 * 		collection of adminOfficeGridVos
	 * @throws ServiceException
	 */
	public Collection<AdminOfficeGridVo> getGrid(AdminOfficeFilter filter) throws ServiceException;
	
	/**
	 * @return collection of adminOfficeVos
	 * @throws ServiceException
	 */
	public Collection<AdminOfficeVo> getPicklist() throws ServiceException;
	
}
