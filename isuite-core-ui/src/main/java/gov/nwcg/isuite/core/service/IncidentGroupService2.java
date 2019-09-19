package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.filter.IncidentGroupFilter;
import gov.nwcg.isuite.core.vo.CostSettingsVo;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.IncidentGroupConflictVo;
import gov.nwcg.isuite.core.vo.IncidentGroupPrefsVo;
import gov.nwcg.isuite.core.vo.IncidentGroupQuestionVo;
import gov.nwcg.isuite.core.vo.IncidentGroupVo;
import gov.nwcg.isuite.core.vo.IncidentPrefsOtherFieldsVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.Collection;

public interface IncidentGroupService2 {

  public DialogueVo getIncidentDropdownList(Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;	
  public DialogueVo getIncidentGroupCostSettings(Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
  public DialogueVo saveIncidentGroupCostSettings(Long incidentGroupId, CostSettingsVo costSettingsVo, DialogueVo dialogueVo) throws ServiceException;
	
  /**
   * 
   * @param filter
   * @param dialogueVo
   * @return dialogueVo
   * @throws ServiceException
   */
	public DialogueVo getGrid(IncidentGroupFilter filter, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * Save or update any changes made to an Incident Group.  This includes any additions
	 * or removal of associated Incidents to the group.
	 * 
	 * @param vo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo save(IncidentGroupVo vo, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * @param incidentGroupId
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getById(Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
 	
	/**
	 * Retrieve all incidents that are available to be added to the specified Incident Group.  
	 * 
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getAvailableIncidents(Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;

//	/**
//	 * Retrieve all incidents that are available to be added to the specified 
//	 * Incident Group.  These will come from the current work area (which should only 
//	 * be a standard work area) to find available incidents.
//	 * 
//	 * @throws ServiceException
//	 */
//	public DialogueVo getAvailableIncidents(Long incidentGroupId, Long workAreaId, IncidentFilter filter, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getAvailableUsers(DialogueVo dialogueVo) throws ServiceException;
	
//	/**
//	 * @param groupId
//	 * @param workAreaId
//	 * @param filter
//	 * @return
//	 * @throws ServiceException
//	 */
//	public DialogueVo getAvailableUsers(Long groupId, Long workAreaId, UserFilter filter, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getAvailableUserGroups(DialogueVo dialogueVo) throws ServiceException;
	
//	/**
//	 * @param filter
//	 * @param dialogueVo
//	 * @return
//	 * @throws ServiceException
//	 */
//	public DialogueVo getAvailableUserGroups(UserGroupFilter filter,DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param incidentGroupIds
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getUserGroupUsers(ArrayList<Integer> incidentGroupIds, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getQSKinds(Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param groupId
	 * @param kindVos
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveQSKinds(Long groupId, Collection<KindVo> kindVos, DialogueVo dialogueVo) throws ServiceException;

	/**
	 * 
	 * @param incidentGroupId
	 * @param vos - Collection<IncidentGroupPrefsVo>
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveIncidentGroupCheckOutQuestions(Long incidentGroupId, Collection<IncidentGroupPrefsVo> vos, DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo getIncidentGroupCheckOutQuestions(Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
	
	/**
	 * @param incidentGroupId
	 * @param vos
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo saveIncidentGroupQuestions(Long incidentGroupId,Collection<IncidentGroupQuestionVo> vos, String questionType, DialogueVo dialogueVo) throws ServiceException ;
	public DialogueVo getIncidentGroupAirTravelQuestions(Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo preCheckAddIncidentsToGroup(IncidentGroupVo igVo, Collection<IncidentGridVo> igGridVos,DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo printConflictReport(IncidentGroupVo igVo, IncidentGroupConflictVo conflictVo, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getIncidentGroupPrefsOtherFields(Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo saveIncidentPrefsOtherFields(IncidentPrefsOtherFieldsVo vo, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo saveIncidentGroupPrefsIap204(Long incidentGroupId, Collection<IncidentGroupPrefsVo> vo, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo isLocked(Long incidentId, DialogueVo dialogueVo) throws ServiceException;
	
}

