package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.filter.IncidentResourceFilter;
import gov.nwcg.isuite.core.vo.IncidentResourceGridVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.ResourceInventoryGridVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;


public interface IncidentResource2Service extends TransactionService{

	/**
	 * @param dialogVo
	 * @param incidentResourceFilter
	 * @param sortFields
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getGrid(IncidentResourceFilter incidentResourceFilter, Collection<String> sortFields,DialogueVo dialogVo) throws ServiceException;
	
	public DialogueVo getById(Long incidentResourceId,DialogueVo dialogVo) throws ServiceException;

	public DialogueVo deleteResource(IncidentResourceGridVo gridVo,DialogueVo dialogVo) throws ServiceException;

	public DialogueVo saveResource(IncidentResourceVo incidentResourceVo, Boolean propagateToChildren,DialogueVo dialogVo) throws ServiceException;
	
	public DialogueVo addPermanentResource(Long incidentId,Resource resource,DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getUnassignedInventoryResources(String dispatchCenter,DialogueVo dialogueVo) throws ServiceException ;

	public DialogueVo getPrepInventoryResources(Collection<ResourceInventoryGridVo> gridVos,DialogueVo dialogueVo) throws ServiceException ;

	public DialogueVo saveAndRoster(Long parentResourceId, IncidentResourceVo vo, DialogueVo dialogueVo) throws ServiceException ;

	public DialogueVo loadParentAndSupplimentals(Long rosterParentId,DialogueVo dialogueVo) throws ServiceException ;	

	public DialogueVo unroster(IncidentResourceGridVo vo, DialogueVo dialogueVo) throws ServiceException ;

	public DialogueVo rosterExisting(Long parentResourceId,Collection<IncidentResourceGridVo> vos, DialogueVo dialogueVo) throws ServiceException ;	

	public DialogueVo getStrikeTeams(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException ;

	public DialogueVo propagateCrewEmploymentType(String employmentType,Long incidentResourceId,DialogueVo dialogueVo) throws ServiceException ;

	public DialogueVo propagateCrewAddress(Long incidentResourceId, DialogueVo dialogueVo) throws ServiceException ;	

	public DialogueVo clearAllCrewAddress(Long incidentResourceId,DialogueVo dialogueVo) throws ServiceException ;

	public DialogueVo getAllCrewMembers(Collection<IncidentResourceGridVo> vos, DialogueVo dialogueVo) throws ServiceException ;

	public DialogueVo getMaxPostingDate(Long irId, Boolean isCrew, DialogueVo dialogueVo) throws ServiceException ;	
	
	public DialogueVo getIncidentGroupAccountCodes(DialogueVo dialogueVo, Long incidentGroupId) throws ServiceException ;

	public DialogueVo getIapResources(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo clearAllCrewHireInfo(Long incidentResourceId,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo propagateCrewHireInfo(Long incidentResourceId,DialogueVo dialogueVo) throws ServiceException;
}
