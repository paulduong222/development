package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.filter.IncidentAccountCodeFilter;
import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.vo.CostSettingsVo;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.core.vo.IncidentCostDefaultsVo;
import gov.nwcg.isuite.core.vo.IncidentPrefsOtherFieldsVo;
import gov.nwcg.isuite.core.vo.IncidentPrefsVo;
import gov.nwcg.isuite.core.vo.IncidentQuestionVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.RestrictedIncidentUserVo;
import gov.nwcg.isuite.core.vo.WorkAreaVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import java.util.Collection;

public interface IncidentService2 {

	public DialogueVo getIncidentCostSettings(Long incidentId, DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo saveIncidentCostSettings(Long incidentId, CostSettingsVo costSettingsVo, DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo getIncidentAccountCodeDropdownList(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * @param filter
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getGrid(IncidentFilter filter, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param id
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getById(Long id,DialogueVo dialogueVo) throws ServiceException;

	/**
	 * @param incidentVo
	 * @param workAreaVo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo save(IncidentVo incidentVo, WorkAreaVo workAreaVo, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param incidentId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo deleteIncident(Long incidentId, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * 
	 * @param incidentId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo restrictIncident(Long incidentId, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * 
	 * @param incidentId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo unrestrictIncident(Long incidentId ,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getIncidentUserDefaultCheckin(Long userId, Long incidentId, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * Returns list of users assigned to the restricted incident.
	 * 
	 * @param restrictedIncidentId
	 * @param filter
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getRestrictedIncidentUsers(Long restrictedIncidentId, UserFilter filter, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * Returns list of available users that can be assigned to the restricted incident.
	 * 
	 * @param restrictedIncidentId
	 * @param filter
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getAvailableRestrictedIncidentUsers(Long restrictedIncidentId, UserFilter filter, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveIncidentPrefsOtherFields(IncidentPrefsOtherFieldsVo vo, DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo getIncidentPrefsOtherFields(Long incidentId, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getQSKinds(Long incidentId, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * @param incidentId
	 * @param kindVos
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveQSKinds(Long incidentId, Collection<KindVo> kindVos, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveIncidentCostDefaults(IncidentVo vo, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * @param incidentId
	 * @param vos
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo addRestrictedUsers(Long incidentId,Collection<RestrictedIncidentUserVo> vos ,DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param vos
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveIncidentQuestions(Long incidentId, Collection<IncidentQuestionVo> vos, String questionType, DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo getIncidentAirTravelQuestions(Long incidentId, DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo deleteIncidentQuestion(Long incidentQuestionId, DialogueVo dialogueVo) throws ServiceException ;

	/**
	 * @param incidentId
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveIncidentAccountCode(Long incidentId, IncidentAccountCodeVo vo, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo deleteIncidentAccountCode(IncidentAccountCodeVo vo,DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param incidentId
	 * @param vos
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo removeRestrictedUsers(Long incidentId, Collection<RestrictedIncidentUserVo> vos, DialogueVo dialogueVo) throws ServiceException ;

	/**
	 * @param incidentId
	 * @param userGroupId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo addRestrictedUserGroupUsers(Long incidentId, Long userGroupId, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getIncidentPrefs(Long incidentId, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * 
	 * @param incidentId
	 * @param incidentPrefsVos
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveIncidentPrefs(Long incidentId, Collection<IncidentPrefsVo> incidentPrefsVos, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * 
	 * @param incidentId
	 * @param incidentPrefsVos
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveIncidentPrefsIap204(Long incidentId, Collection<IncidentPrefsVo> incidentPrefsVos, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * 
	 * @param incidentId
	 * @param filter
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getIncidentAccountCodes(Long incidentId, IncidentAccountCodeFilter filter, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * Return the default set of incidentQuestions to be used when creating a new incident.
	 * 
	 * @param dialogueVo
	 * @return
	 */
	public DialogueVo getDefaultQuestions(DialogueVo dialogueVo);
	
	public DialogueVo getAvailableIncidentUsers(Long restrictedIncidentId, DialogueVo dialogueVo) throws ServiceException ;
	
	public DialogueVo getAvailableUserGroups(DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo saveIncidentUserCheckin(RestrictedIncidentUserVo vo, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getAllIncidentsForUser(DialogueVo dialogueVo) throws ServiceException ;

	public DialogueVo isLocked(Long incidentId, DialogueVo dialogueVo) throws ServiceException;
	
}
