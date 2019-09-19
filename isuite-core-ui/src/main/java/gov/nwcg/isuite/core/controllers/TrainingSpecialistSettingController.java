package gov.nwcg.isuite.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.PriorityProgramData;
import gov.nwcg.isuite.core.controllers.restdata.TrainingContactData;
import gov.nwcg.isuite.core.controllers.restdata.TrainingSettingsData;
import gov.nwcg.isuite.core.service.TrainingSpecialistSettingsService;
import gov.nwcg.isuite.core.vo.PriorityProgramVo;
import gov.nwcg.isuite.core.vo.TrainingContactVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

@Controller
@RequestMapping("/training-specialists/settings")
public class TrainingSpecialistSettingController extends BaseRestController {

	@Autowired
	private TrainingSpecialistSettingsService service;

	@RequestMapping(value = "/priority-programs", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo savePriorityProgram(@RequestBody PriorityProgramData data) throws ServiceException {
		return resolveMessaging(service.savePriorityProgram(data.getPriorityProgramVo(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/priority-programs/{id}", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deletePriorityProgram(@PathVariable("id") long id,
			@RequestParam(value = "incidentId", required = false) Long incidentId,
			@RequestParam(value = "incidentGroupId", required = false) Long incidentGroupId,
			@RequestParam(value = "code", required = false) String code) throws ServiceException {
		PriorityProgramVo vo = new PriorityProgramVo();
		vo.setId(id);
		vo.setIncidentId(incidentId);
		vo.setIncidentGroupId(incidentGroupId);
		vo.setCode(code);

		return resolveMessaging(service.deletePriorityProgram(vo, null));
	}

	@RequestMapping(value = "/priority-programs/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getPriorityProgramGrid(
			@RequestParam(value = "incidentId", required = false) Long incidentId,
			@RequestParam(value = "incidentGroupId", required = false) Long incidentGroupId) throws ServiceException {
		return resolveMessaging(service.getPriorityProgramGrid(null, incidentId, incidentGroupId));
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody DialogueVo getTrainingSettings(
			@RequestParam(value = "incidentId", required = false) Long incidentId,
			@RequestParam(value = "incidentGroupId", required = false) Long incidentGroupId) throws ServiceException {
		return resolveMessaging(service.getTrainingSettings(null, incidentId, incidentGroupId));
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveTrainingSettings(
			@RequestParam(value = "incidentId", required = false) List<Integer> incidentIds,
			@RequestParam(value = "incidentGroupId", required = false) Long incidentGroupId,
			@RequestBody TrainingSettingsData data) throws ServiceException {
		return resolveMessaging(
				service.saveTrainingSettings(data.getTrainingSettingsVo(), incidentGroupId, incidentIds, data.getDialogueVo()));
	}
	
//	@RequestMapping(value = "/contact-resources/grid", method = RequestMethod.GET)
//	public @ResponseBody DialogueVo getContactResourcesGrid(
//			@RequestParam(value = "incidentId", required = false) Long incidentId,
//			@RequestParam(value = "incidentGroupId", required = false) Long incidentGroupId) throws ServiceException {
//		return resolveMessaging(service.getContactResourcesGrid(null, incidentId, incidentGroupId));
//	}
	
	@RequestMapping(value = "/training-contacts", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveTrainingContact(@RequestBody TrainingContactData data) throws ServiceException {
		return resolveMessaging(service.saveTrainingContact(data.getTrainingContactVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/training-contacts/{id}", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteTrainingContact(
			@PathVariable("id") long id,
			@RequestParam(value = "incidentResourceId", required = false) Long incidentResourceId) throws ServiceException {
		TrainingContactVo vo = new TrainingContactVo();
		vo.setId(id);
		vo.setIncidentResourceId(incidentResourceId);

		return resolveMessaging(service.deleteTrainingContact(vo, null));
	}
	
	@RequestMapping(value = "/training-contacts/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getTrainingContactGrids(
			@RequestParam(value = "incidentId", required = false) Long incidentId,
			@RequestParam(value = "incidentGroupId", required = false) Long incidentGroupId) throws ServiceException {
		return resolveMessaging(service.getTrainingContactGrids(null, incidentId, incidentGroupId));
	}
	
	@RequestMapping(value = "/acres", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveAcres(@RequestBody TrainingSettingsData data) throws ServiceException {
		return resolveMessaging(service.saveAcres(data.getTrainingSettingsVo(), data.getDialogueVo()));
	}

}
