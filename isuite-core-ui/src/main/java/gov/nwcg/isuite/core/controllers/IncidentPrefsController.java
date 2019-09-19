package gov.nwcg.isuite.core.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.IncidentQuestionData;
import gov.nwcg.isuite.core.controllers.restdata.KindData;
import gov.nwcg.isuite.core.filter.impl.KindFilterImpl;
import gov.nwcg.isuite.core.service.IncidentPrefsService;
import gov.nwcg.isuite.core.vo.IncidentGroupPrefsVo;
import gov.nwcg.isuite.core.vo.IncidentPrefsOtherFieldsVo;
import gov.nwcg.isuite.core.vo.IncidentPrefsVo;
import gov.nwcg.isuite.core.vo.IncidentQuestionVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

@Controller
@RequestMapping("/prefs")
@Deprecated
public class IncidentPrefsController extends BaseRestController {
	
	public enum PreferenceType {
		INCIDENT, GROUP
	}

	@Autowired
	private IncidentPrefsService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody IncidentPrefsVo getById(@PathVariable("id") long id) throws ServiceException {
		return service.getById(id);
	}

	@RequestMapping(value = "/incidents/{id}", method = RequestMethod.GET)
	public @ResponseBody IncidentPrefsVo getByIncidentId(@PathVariable("incidentId") long incidentId)
			throws ServiceException {
		return service.getById(incidentId);
	}
	
	@RequestMapping(value = "/labels/other/{type}/{id}", method = RequestMethod.GET)
	public @ResponseBody Collection<IncidentPrefsVo> getOtherLabels(
			@PathVariable("type") PreferenceType type,
			@PathVariable("id") long id) throws ServiceException {
		return service.getOtherLabels(id, isGroup(type));
	}
	
	@RequestMapping(value = "/fields/logistics/{type}/{id}", method = RequestMethod.GET)
	public @ResponseBody Collection<IncidentPrefsVo> getLogisticsFields(
			@PathVariable("type") PreferenceType type,
			@PathVariable("id") long id) throws ServiceException {
		return service.getLogisticsFields(id, isGroup(type));
	}
	
	@RequestMapping(value = "/fields/planning/{type}/{id}", method = RequestMethod.GET)
	public @ResponseBody Collection<IncidentPrefsVo> getPlanningFields(
			@PathVariable("type") PreferenceType type,
			@PathVariable("id") long id) throws ServiceException {
		return service.getPlanningFields(id, isGroup(type));
	}
	
	@RequestMapping(value = "/fields/finance/{type}/{id}", method = RequestMethod.GET)
	public @ResponseBody Collection<IncidentPrefsVo> getFinanceFields(
			@PathVariable("type") PreferenceType type,
			@PathVariable("id") long id) throws ServiceException {
		return service.getFinanceFields(id, isGroup(type));
	}
	
	@RequestMapping(value = "/fields/ics-2210-other/{type}/{id}", method = RequestMethod.GET)
	public @ResponseBody Collection<IncidentPrefsVo> getICS221OtherFields(
			@PathVariable("type") PreferenceType type,
			@PathVariable("id") long id) throws ServiceException {
		return service.getICS221OtherFields(id, isGroup(type));
	}
	
	@RequestMapping(value = "/fields/ics-2210/{type}/{id}", method = RequestMethod.GET)
	public @ResponseBody Collection<IncidentPrefsVo> getICS221Fields(
			@PathVariable("type") PreferenceType type,
			@PathVariable("id") long id) throws ServiceException {
		return service.getICS221Fields(id, isGroup(type));
	}
	
	@RequestMapping(value = "/questions/air-travel/{type}/{id}", method = RequestMethod.GET)
	public @ResponseBody Collection<IncidentQuestionVo> getAirTravelQuestions(
			@PathVariable("type") PreferenceType type,
			@PathVariable("id") long id) throws ServiceException {
		return service.getAirTravelQuestions(id, isGroup(type));
	}
	
	@RequestMapping(value = "/questions/check-in/{type}/{id}", method = RequestMethod.GET)
	public @ResponseBody Collection<IncidentQuestionVo> getCheckInQuestions(
			@PathVariable("type") PreferenceType type,
			@PathVariable("id") long id) throws ServiceException {
		return service.getCheckInQuestions(id, isGroup(type));
	}
	
	@RequestMapping(value = "/incident-questions", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody Collection<IncidentQuestionVo> saveQuestions(@RequestBody IncidentQuestionData data) 
			throws ServiceException {
		if (data.getIncidentQuestionVos() != null) {
			return service.saveQuestions(data.getIncidentQuestionVos());
		} else {
			return service.saveQuestion(data.getIncidentQuestionVo());
		}
	}
	
	@RequestMapping(value = "/incident-questions/positions", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody void saveQuestionPositions(@RequestBody IncidentQuestionData data) 
			throws ServiceException {
		service.saveQuestionPositions(data.getIncidentQuestionVos());
	}
	
	@RequestMapping(value = "/incident-questions/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteIncidentQuestion(@PathVariable("id") long id) throws ServiceException {
		service.deleteQuestion(id);
	}
	
	@RequestMapping(value = "/incident-questions/{id}", method = RequestMethod.GET)
	public @ResponseBody IncidentQuestionVo getIncidentQuestionById(@PathVariable("id") Long id) throws ServiceException {
		return service.getIncidentQuestionById(id);
	}
	
	@RequestMapping(value = "/kind-codes/available/{type}/{id}", method = RequestMethod.GET)
	public @ResponseBody Collection<KindVo> getAvailablePrefKindCodes(
			@PathVariable("type") PreferenceType type,
			@PathVariable("id") long id,
			KindFilterImpl itemFilter) throws ServiceException {
		return service.getAvailablePrefKindCodes(id, isGroup(type), itemFilter);
	}
	
	@RequestMapping(value = "/kind-codes/selected/{type}/{id}", method = RequestMethod.GET)
	public @ResponseBody Collection<KindVo> getSelectedPrefKindCodes(
			@PathVariable("type") PreferenceType type,
			@PathVariable("id") long id) throws ServiceException {
		return service.getSelectedPrefKindCodes(id, isGroup(type));
	}
	
	@RequestMapping(value = "/kind-codes", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody void saveKindCodePrefs(
			@PathVariable("type") PreferenceType type,
			@PathVariable("id") long id,
			@RequestBody KindData data) throws ServiceException {
		service.saveKindCodePrefs(id, data.getKindVos(), isGroup(type));
	}
	
	@RequestMapping(value = "/incidents/{id}/questions/non-standard/travel", method = RequestMethod.GET)
	public @ResponseBody boolean checkForNonStandardTravelQuestions(
			@PathVariable("id") long id) throws ServiceException {
		return service.checkForNonStandardTravelQuestions(id);
	}
	
	@RequestMapping(value = "/incidents/{id}/questions/non-standard/check-in", method = RequestMethod.GET)
	public @ResponseBody boolean checkForNonStandardCheckInQuestions(
			@PathVariable("id") long id) throws ServiceException {
		return service.checkForNonStandardCheckinQuestions(id);
	}
	
	@RequestMapping(value = "/incidents/{id}/fields/other", method = RequestMethod.GET)
	public @ResponseBody IncidentPrefsOtherFieldsVo getIncidentPrefsOtherFields(
			@PathVariable("id") long id) throws ServiceException {
		return service.getIncidentPrefsOtherFields(id);
	}
	
	@RequestMapping(value = "incidentsfields/other", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody IncidentPrefsOtherFieldsVo saveIncidentPrefsOtherFields(@RequestBody IncidentPrefsOtherFieldsVo data) throws ServiceException {
		return service.saveIncidentPrefsOtherFields(data);
	}
	
	@RequestMapping(value = "/incidents/{id}/in-group", method = RequestMethod.GET)
	public @ResponseBody boolean doesIncidentBelongToAGroup(
			@PathVariable("id") long id) throws ServiceException {
		return service.doesIncidentBelongToAGroup(id);
	}
	
	@RequestMapping(value = "/fields/ics-204-block-5/incidents/{id}", method = RequestMethod.GET)
	public @ResponseBody Collection<IncidentPrefsVo> getICS204Block5FieldsForIncident(
			@PathVariable("id") long id,
			@RequestParam(value = "selected", required = false) Boolean selected) throws ServiceException {
			return service.getICS204Block5FieldsForIncident(id, selected);
	}
	
	@RequestMapping(value = "/fields/ics-204-block-5/incident-groups/{id}", method = RequestMethod.GET)
	public @ResponseBody Collection<IncidentGroupPrefsVo> getICS204Block5FieldsForIncidentGroup(
			@PathVariable("id") long id,
			@RequestParam(value = "selected", required = false) Boolean selected) throws ServiceException {
			return service.getICS204Block5FieldsForIncidentGroup(id, selected);
	}
	
	private boolean isGroup(PreferenceType type) {
		return type == PreferenceType.GROUP;
	}

}
