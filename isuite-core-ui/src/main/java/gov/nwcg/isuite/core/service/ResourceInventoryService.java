package gov.nwcg.isuite.core.service;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.ResourceInventoryGridVo;
import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

public interface ResourceInventoryService {

	/**
	 * 
	 * @param dispatchCenterId
	 * @param userId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getGrid(Long dispatchCenterId, Long userId, DialogueVo dialogueVo) throws ServiceException ;

	/**
	 * 
	 * @param id
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getById(Long id, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * 
	 * @param resourceId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo deleteResource(Long resourceId, DialogueVo dialogueVo) throws ServiceException ;
	
	/**
	 * 
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public DialogueVo save(ResourceVo vo, DialogueVo dialogueVo) throws ServiceException,ValidationException;
	
	/**
	 * 
	 * @param organizationId
	 * @param resourceInventoryGridVo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo removeNonStandardResource(Long organizationId, ResourceVo resourceVo, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * 
	 * @param organizationVo
	 * @param gridVos
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo addNonStandardResources(OrganizationVo organizationVo, Collection<ResourceInventoryGridVo> gridVos, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * 
	 * @param dispatchCenterId
	 * @param nonStdId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getNonStdGrid(Long dispatchCenterId, Long nonStdId, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * 
	 * @param userId
	 * @param dispatchCenterId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getCustomUserDataView(Long userId, Long dispatchCenterId, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * 
	 * @param userId
	 * @param excludedVos
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveCustomView(Long userId, Collection<ResourceInventoryGridVo> excludedVos, DialogueVo dialogueVo) throws ServiceException;
	
}
