package gov.nwcg.isuite.core.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.CostSettingsData;
import gov.nwcg.isuite.core.controllers.restdata.IncidentGroupData;
import gov.nwcg.isuite.core.controllers.restdata.IncidentGroupPrefsData;
import gov.nwcg.isuite.core.controllers.restdata.IncidentGroupQuestionData;
import gov.nwcg.isuite.core.controllers.restdata.IncidentPrefsOtherFieldsData;
import gov.nwcg.isuite.core.controllers.restdata.KindData;
import gov.nwcg.isuite.core.filter.impl.IncidentGroupFilterImpl;
import gov.nwcg.isuite.core.service.IncidentGroupService2;
import gov.nwcg.isuite.core.vo.IncidentGroupVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.IsuiteException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

@Controller
@RequestMapping("/incident-groups")
public class IncidentGroupController2 extends BaseRestController {

	@Autowired
	private IncidentGroupService2 service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getById(@PathVariable("id") Long id) throws ServiceException {
		DialogueVo dvo = service.getById(id, null);
		return super.resolveMessaging(dvo);
	}

	@RequestMapping(value = "/{id}/iap-position-item-codes", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getIapPositionItemCodes(@PathVariable("id") Long id) throws ServiceException {
		DialogueVo vo = super.resolveMessaging(service.getById(id, null));
		if ( null != vo.getResultObject()) {
			IncidentGroupVo incGroupVo = (IncidentGroupVo)vo.getResultObject();
			vo.setResultObject(null);
			vo.setRecordset(incGroupVo.getIapPositionItemCodeVos());
			vo.getCourseOfActionVo().setCoaName("GET_INCGROUP_IAP_POSITION_ITEM_CODES");
		}

		return vo;
	}
	
	@RequestMapping(value = "/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getGrid(IncidentGroupFilterImpl queryParams) throws ServiceException {
		return resolveMessaging(service.getGrid(queryParams, null));
	}

	@RequestMapping(value = "/{id}/inc-dropdown-list", method = RequestMethod.GET)
	@ResponseBody
	public DialogueVo getIncidentDropdownList(
			@PathVariable("id") Long incidentGroupId) throws IsuiteException {
		return super.resolveMessaging(service.getIncidentDropdownList(incidentGroupId, null));
	}	

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo save(@RequestBody IncidentGroupData data) throws ServiceException {
		return resolveMessaging(service.save(data.getIncidentGroupVo(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/{id}/incidents", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getAvailableIncidents(@PathVariable("id") Long id) throws ServiceException {
		return resolveMessaging(service.getAvailableIncidents(id, null));
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getAvailableUsers(@PathVariable("id") Long id) throws ServiceException {
		return resolveMessaging(service.getAvailableUsers(null));
	}

	@RequestMapping(value = "/user-groups", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getAvailableUserGroups(@PathVariable("id") Long id) throws ServiceException {
		return resolveMessaging(service.getAvailableUserGroups(null));
	}

	@RequestMapping(value = "/user-groups/users", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getUserGroupUsers(
			@RequestParam("incidentGroupId") ArrayList<Integer> incidentGroupIds) throws ServiceException {
		return resolveMessaging(service.getUserGroupUsers(incidentGroupIds, null));
	}

	@RequestMapping(value = "/{id}/qs-kinds", method = { RequestMethod.GET })
	public @ResponseBody DialogueVo getQSKinds(@PathVariable("id") long id) throws ServiceException {
		return resolveMessaging(service.getQSKinds(id,null));
	}

	@RequestMapping(value = "/{id}/qs-kinds", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveQSKinds(@PathVariable("id") long id, @RequestBody KindData data)
			throws ServiceException {
		return resolveMessaging(service.saveQSKinds(id, data.getKindVos(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/{id}/questions/checkout", method = { RequestMethod.GET })
	public @ResponseBody DialogueVo getIncidentGroupCheckOutQuestions(@PathVariable("id") long id) throws ServiceException {
		return resolveMessaging(
				service.getIncidentGroupCheckOutQuestions(id, null));
	}
	
	@RequestMapping(value = "/{id}/questions/checkout", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIncidentGroupCheckOutQuestions(@PathVariable("id") long id,
			@RequestBody IncidentGroupPrefsData data) throws ServiceException {
		return resolveMessaging(
				service.saveIncidentGroupCheckOutQuestions(id, data.getIncidentGroupPrefsVos(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/{id}/questions/airtravel", method = { RequestMethod.GET })
	public @ResponseBody DialogueVo getIncidentGroupAirTravelQuestions(@PathVariable("id") long id) throws ServiceException {
		return resolveMessaging(service.getIncidentGroupAirTravelQuestions(id, null));
	}
	
	@RequestMapping(value = "/{id}/questions", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIncidentGroupQuestions(
			@PathVariable("id") long id, @RequestBody IncidentGroupQuestionData data) throws ServiceException {
		return resolveMessaging(
				service.saveIncidentGroupQuestions(id, data.getIncidentGroupQuestionVos(), data.getQuestionType(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/incidents/pre-check", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo preCheckAddIncidentsToGroup(
			@RequestBody IncidentGroupData data) throws ServiceException, ValidationException {
		return resolveMessaging(service.preCheckAddIncidentsToGroup(data.getIncidentGroupVo(), data.getIncidentGridVos(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "reports/conflict/print", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo printConflictReport(@RequestBody IncidentGroupData data) throws ServiceException, ValidationException {
		return resolveMessaging(service.printConflictReport(data.getIncidentGroupVo(), data.getIncidentGroupConflictVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/{id}/prefs/other", method = { RequestMethod.GET })
	public @ResponseBody DialogueVo getIncidentGroupPrefsOtherFields(
			@PathVariable("id") long incidentGroupId) throws ServiceException {
		return resolveMessaging(service.getIncidentGroupPrefsOtherFields(incidentGroupId, null));
	}
	
	@RequestMapping(value = "/prefs/other", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIncidentPrefsOtherFields(@RequestBody IncidentPrefsOtherFieldsData data) throws ServiceException, ValidationException {
		return resolveMessaging(service.saveIncidentPrefsOtherFields(data.getIncidentPrefsOtherFieldsVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/{id}/prefs/iap-204", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIncidentGroupPrefsIap204(
			@PathVariable("id") long id,
			@RequestBody IncidentGroupPrefsData data) throws ServiceException, ValidationException {
		return resolveMessaging(service.saveIncidentGroupPrefsIap204(id, data.getIncidentGroupPrefsVos(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/{id}/locked", method = RequestMethod.GET)
	public @ResponseBody DialogueVo isLocked(@PathVariable("id") Long id) throws ServiceException {
		return resolveMessaging(service.isLocked(id, null));
	}

	@RequestMapping(value = "/{id}/cost-settings", method = { RequestMethod.GET })
	public @ResponseBody DialogueVo getIncidentGroupCostSettings(@PathVariable("id") long id) throws ServiceException {
		return resolveMessaging(service.getIncidentGroupCostSettings(id, null));
	}

	@RequestMapping(value = "/{id}/cost-settings", method = { RequestMethod.PUT, RequestMethod.POST })
	public @ResponseBody DialogueVo saveIncidentGroupCostSettings(@PathVariable("id") long id
			, @RequestBody CostSettingsData data) throws ServiceException {
		return resolveMessaging(service.saveIncidentGroupCostSettings(id, data.getCostSettingsVo(), null));
	}	
}
