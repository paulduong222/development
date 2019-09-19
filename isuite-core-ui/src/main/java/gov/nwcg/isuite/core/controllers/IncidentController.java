package gov.nwcg.isuite.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.CostSettingsData;
import gov.nwcg.isuite.core.controllers.restdata.IncidentAccountCodeData;
import gov.nwcg.isuite.core.controllers.restdata.IncidentData;
import gov.nwcg.isuite.core.controllers.restdata.IncidentPrefsData;
import gov.nwcg.isuite.core.controllers.restdata.IncidentPrefsOtherFieldsData;
import gov.nwcg.isuite.core.controllers.restdata.IncidentQuestionData;
import gov.nwcg.isuite.core.controllers.restdata.KindData;
import gov.nwcg.isuite.core.controllers.restdata.RestrictedIncidentUserData;
import gov.nwcg.isuite.core.controllers.restdata.UserFilterData;
import gov.nwcg.isuite.core.filter.impl.IncidentAccountCodeFilterImpl;
import gov.nwcg.isuite.core.filter.impl.IncidentFilterImpl;
import gov.nwcg.isuite.core.service.IncidentService2;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

@Controller
@RequestMapping("/incidents")
public class IncidentController extends BaseRestController {

	@Autowired
	private IncidentService2 service;

	/**
	 * Find all incidents matching the provided (if any) criteria.
	 * 
	 * NOTE: This is needed to be done as a POST due to the complexity of the
	 * IncidentFilter. It would be very difficult to create the GET without a body
	 * using Query Params to map it all into a GET since you can't use a body.
	 * 
	 * @param queryParams
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/grid", method = RequestMethod.POST)
	public @ResponseBody DialogueVo getGrid(@RequestBody IncidentFilterImpl filter) throws ServiceException {

		return super.resolveMessaging(service.getGrid(filter, null));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getById(@PathVariable("id") Long id) throws ServiceException {
		return super.resolveMessaging(service.getById(id, null));
	}

	@RequestMapping(value = "/{id}/iap-position-item-codes", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getIapPositionItemCodes(@PathVariable("id") Long id) throws ServiceException {
		DialogueVo vo = super.resolveMessaging(service.getById(id, null));
		if ( null != vo.getResultObject()) {
			IncidentVo incVo = (IncidentVo)vo.getResultObject();
			vo.setResultObject(null);
			vo.setRecordset(incVo.getIapPositionItemCodeVos());
			vo.getCourseOfActionVo().setCoaName("GET_INCIDENT_IAP_POSITION_ITEM_CODES");
		}

		return vo;
	}

	
	/**
	 * The "upsert" REST endpoint to create or modify an Incident.
	 * 
	 * Suggested usage is to use a POST when creating and a PUT when updating.
	 * 
	 * @param data
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo save(@RequestBody IncidentData data) throws ServiceException {
		return resolveMessaging(service.save(data.getIncidentVo(), data.getWorkAreaVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteIncident(@PathVariable("id") long id) throws ServiceException {
		return resolveMessaging(service.deleteIncident(id, null));
	}
	
	@RequestMapping(value = "/{id}/restrict", method = RequestMethod.PUT)
	public @ResponseBody DialogueVo restrictIncident(@PathVariable("id") long id) throws ServiceException {
		return resolveMessaging(service.restrictIncident(id, null));
	}
	
	@RequestMapping(value = "/{id}/unrestrict", method = RequestMethod.PUT)
	public @ResponseBody DialogueVo unrestrictIncident(@PathVariable("id") long id) throws ServiceException {
		return resolveMessaging(service.unrestrictIncident(id, null));
	}
	
	/**
	 * Look up restricted users associated to the specified incident.
	 * 
	 * NOTE: This is a POST, not a GET due to the need of a body for filtering.
	 * 
	 * @param id
	 * @param data
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/{id}/restricted/users/lookup", method = RequestMethod.POST)
	public @ResponseBody DialogueVo getRestrictedIncidentUsers(
			@PathVariable("id") long id,
			@RequestBody UserFilterData data) throws ServiceException {
		return resolveMessaging(service.getRestrictedIncidentUsers(id, data.getUserFilter(), data.getDialogueVo()));
	}
	
	/**
	 * Look up available restricted users for an incident
	 * 
	 * NOTE: This is a POST, not a GET due to the need of a body for filtering.
	 * 
	 * @param id
	 * @param data
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/{id}/restricted/users/available", method = RequestMethod.POST)
	public @ResponseBody DialogueVo getAvailableRestrictedIncidentUsers(
			@PathVariable("id") long id,
			@RequestBody UserFilterData data) throws ServiceException {
		return resolveMessaging(service.getAvailableRestrictedIncidentUsers(id, data.getUserFilter(), data.getDialogueVo()));
	}
	
	/**
	 * Look up available restricted users for an incident
	 * 
	 * NOTE: This is a POST, not a GET due to the need of a body for filtering.
	 * 
	 * @param id
	 * @param data
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/{id}/users/available", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getAvailableIncidentUsers(
			@PathVariable("id") long id) throws ServiceException {
		return resolveMessaging(service.getAvailableIncidentUsers(id, null));
	}
	
	
	@RequestMapping(value = "{id}/users/{userId}/defcheckindate", method = { RequestMethod.GET })
	public @ResponseBody DialogueVo getIncidentUserDefaultCheckin(
			@PathVariable("id") Long incidentId, @PathVariable("userId") Long userId) throws ServiceException {
		return resolveMessaging(service.getIncidentUserDefaultCheckin(userId, incidentId, null));
	}

	@RequestMapping(value = "/users/checkin", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIncidentUserCheckin(@RequestBody RestrictedIncidentUserData data) throws ServiceException {
		return resolveMessaging(service.saveIncidentUserCheckin(data.getRestrictedIncidentUserVo(), data.getDialogueVo()));
	}
	
	/**
	 * Look up available restricted users for an incident
	 * 
	 * NOTE: This is a POST, not a GET due to the need of a body for filtering.
	 * 
	 * @param id
	 * @param data
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/{id}/restricted/users", method = RequestMethod.POST)
	public @ResponseBody DialogueVo addRestrictedUsers(
			@PathVariable("id") long id,
			@RequestBody RestrictedIncidentUserData data) throws ServiceException {
		return resolveMessaging(service.addRestrictedUsers(id, data.getRestrictedIncidentUserVos(), data.getDialogueVo()));
	}
	
	/**
	 * NOTE: This is a PUT, not a GET due to the need of a body appropriate removal of users
	 * .
	 * @param id
	 * @param data
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/{id}/restricted/users/remove", method = RequestMethod.PUT)
	public @ResponseBody DialogueVo removeRestrictedUsers(@PathVariable("id") long id,
			@RequestBody RestrictedIncidentUserData data) throws ServiceException {

		return resolveMessaging(service.removeRestrictedUsers(id, data.getRestrictedIncidentUserVos(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/{id}/prefs-others", method = { RequestMethod.GET })
	public @ResponseBody DialogueVo getIncidentPrefsOtherFields(
			@PathVariable("id") long incidentId) throws ServiceException {
		return resolveMessaging(service.getIncidentPrefsOtherFields(incidentId, null));
	}
	
	@RequestMapping(value = "/{id}/prefs-others", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIncidentPrefsOtherFields(
			@PathVariable("id") long id,
			@RequestBody IncidentPrefsOtherFieldsData data) throws ServiceException {
		return resolveMessaging(service.saveIncidentPrefsOtherFields(data.getIncidentPrefsOtherFieldsVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/{id}/qskinds", method = { RequestMethod.GET })
	public @ResponseBody DialogueVo getQSKinds(@PathVariable("id") long id) throws ServiceException {
		return resolveMessaging(service.getQSKinds(id,null));
	}
	
	@RequestMapping(value = "/{id}/qskinds", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveQSKinds(
			@PathVariable("id") long id,
			@RequestBody KindData data) throws ServiceException {
		return resolveMessaging(service.saveQSKinds(id, data.getKindVos(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/{id}/costs/defaults", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIncidentCostDefaults(
			@PathVariable("id") long id,
			@RequestBody IncidentData data) throws ServiceException {
		return resolveMessaging(service.saveIncidentCostDefaults(data.getIncidentVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/questions/default", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getDefaultQuestions() throws ServiceException {
		return resolveMessaging(service.getDefaultQuestions(null));
	}

	@RequestMapping(value = "/{id}/questions/airtravel", method = { RequestMethod.GET })
	public @ResponseBody DialogueVo getIncidentAirTravelQuestions(@PathVariable("id") long id) throws ServiceException {
		return resolveMessaging(service.getIncidentAirTravelQuestions(id, null));
	}
	
	@RequestMapping(value = "/{id}/questions", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIncidentQuestions(
			@PathVariable("id") long id,
			@RequestBody IncidentQuestionData data) throws ServiceException {
		return resolveMessaging(service.saveIncidentQuestions(id, data.getIncidentQuestionVos(), data.getQuestionType(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/questions/{id}", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteIncidentQuestion(@PathVariable("id") long id) throws ServiceException {

		return resolveMessaging(service.deleteIncidentQuestion(id, null));
	}
	
	@RequestMapping(value = "/{id}/account-codes", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIncidentAccountCode(
			@PathVariable("id") long id,
			@RequestBody IncidentAccountCodeData data) throws ServiceException {
		return resolveMessaging(service.saveIncidentAccountCode(id, data.getIncidentAccountCodeVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/account-codes/{id}", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteIncidentAccountCode(@PathVariable("id") long id) throws ServiceException {
		IncidentAccountCodeVo vo = new IncidentAccountCodeVo();
		vo.setId(id);

		return resolveMessaging(service.deleteIncidentAccountCode(vo, null));
	}
	
	@RequestMapping(value = "/{id}/account-codes", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getIncidentAccountCodes(
			@PathVariable("id") Long id,
			IncidentAccountCodeFilterImpl queryParams) throws ServiceException {
		return resolveMessaging(service.getIncidentAccountCodes(id, queryParams, null));
	}
	
	@RequestMapping(value = "/user-groups", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getAvailableUserGroups() throws ServiceException {
		return resolveMessaging(service.getAvailableUserGroups(null));
	}
	
	@RequestMapping(value = "/{id}/user-groups/{userGroupId}", method = RequestMethod.POST)
	public @ResponseBody DialogueVo addRestrictedUserGroupUsers(@PathVariable("id") long id,
			@PathVariable("userGroupId") long userGroupId) throws ServiceException {
		return resolveMessaging(service.addRestrictedUserGroupUsers(id, userGroupId, null));
	}

	@RequestMapping(value = "/{id}/prefs", method = { RequestMethod.GET })
	public @ResponseBody DialogueVo getIncidentPrefs(
			@PathVariable("id") long incidentId) throws ServiceException {
		return resolveMessaging(service.getIncidentPrefs(incidentId, null));
	}
	
	@RequestMapping(value = "/{id}/prefs", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIncidentPrefs(
			@PathVariable("id") long id,
			@RequestBody IncidentPrefsData data) throws ServiceException {
		return resolveMessaging(service.saveIncidentPrefs(id, data.getIncidentPrefsVos(), null));
	}
	
	@RequestMapping(value = "/{id}/prefs/iap-204", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIncidentPrefsIap204(
			@PathVariable("id") long id,
			@RequestBody IncidentPrefsData data) throws ServiceException {
		return resolveMessaging(service.saveIncidentPrefsIap204(id, data.getIncidentPrefsVos(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/current-user", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getAllIncidentsForUser() throws ServiceException {
		return resolveMessaging(service.getAllIncidentsForUser(null));
	}
	
	@RequestMapping(value = "/{id}/locked", method = RequestMethod.GET)
	public @ResponseBody DialogueVo isLocked(@PathVariable("id") Long id) throws ServiceException {
		return resolveMessaging(service.isLocked(id, null));
	}

	@RequestMapping(value = "/{id}/cost-settings", method = { RequestMethod.GET })
	public @ResponseBody DialogueVo getIncidentCostSettings(@PathVariable("id") long id) throws ServiceException {
		return resolveMessaging(service.getIncidentCostSettings(id, null));
	}

	@RequestMapping(value = "/{id}/cost-settings", method = { RequestMethod.PUT, RequestMethod.POST })
	public @ResponseBody DialogueVo saveIncidentCostSettings(@PathVariable("id") long id
			, @RequestBody CostSettingsData data) throws ServiceException {
		return resolveMessaging(service.saveIncidentCostSettings(id, data.getCostSettingsVo(), null));
	}	
}
