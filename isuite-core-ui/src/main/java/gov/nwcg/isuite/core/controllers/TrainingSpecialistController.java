package gov.nwcg.isuite.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.ResourceHomeUnitContactData;
import gov.nwcg.isuite.core.controllers.restdata.ResourceTrainingData;
import gov.nwcg.isuite.core.controllers.restdata.RscTrainingObjectiveData;
import gov.nwcg.isuite.core.controllers.restdata.RscTrainingTrainerData;
import gov.nwcg.isuite.core.service.TrainingSpecialistService;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.ResourceTrainingVo;
import gov.nwcg.isuite.core.vo.RscTrainingTrainerVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

@Controller
@RequestMapping("/training-specialists")
public class TrainingSpecialistController extends BaseRestController {

	@Autowired
	private TrainingSpecialistService service;

	@RequestMapping(value = "/resource-trainings", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveResourceTraining(@RequestBody ResourceTrainingData data)
			throws ServiceException {
		return resolveMessaging(service.saveResourceTraining(data.getResourceTrainingVo(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/resource-trainings/{resourceTrainingId}/incident-resources/{incidentResourceId}", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteResourceTraining(@PathVariable("resourceTrainingId") long resourceTrainingId,
			@RequestParam(value = "incidentResourceId", required = false) Long incidentResourceId)
			throws ServiceException {
		ResourceTrainingVo vo = new ResourceTrainingVo();
		vo.setId(resourceTrainingId);

		if (incidentResourceId != null) {
			IncidentResourceVo irVo = new IncidentResourceVo();
			irVo.setId(incidentResourceId);
			vo.setIncidentResourceVo(irVo);
		}

		return resolveMessaging(service.deleteResourceTraining(vo, null));
	}

	@RequestMapping(value = "/resource-trainings/{id}", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getResourceTrainings(@PathVariable("id") long id) throws ServiceException {
		return resolveMessaging(service.getResourceTrainings(id, null));
	}

	@RequestMapping(value = "/home-unit-contacts", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveHomeUnitContact(@RequestBody ResourceHomeUnitContactData data)
			throws ServiceException {
		return resolveMessaging(service.saveHomeUnitContact(data.getResourceHomeUnitContactVo(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/incident-resources/{incidentResourceId}/incident-evaluators/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getIncidentEvaluatorsGrid(
			@PathVariable("incidentResourceId") long incidentResourceId,
			@RequestParam(value = "incidentId", required = false) Long incidentId,
			@RequestParam(value = "incidentGroupId", required = false) Long incidentGroupId) throws ServiceException {
		return resolveMessaging(
				service.getIncidentEvaluatorsGrid(null, incidentResourceId, incidentId, incidentGroupId));
	}

	@RequestMapping(value = "/trainees/totals", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getTraineeTotal(
			@RequestParam(value = "incidentId", required = false) Long incidentId,
			@RequestParam(value = "incidentGroupId", required = false) Long incidentGroupId) throws ServiceException {
		return resolveMessaging(service.getTraineeTotal(null, incidentId, incidentGroupId));
	}

	@RequestMapping(value = "/trainees/totals/priority", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getPriorityTrainees(
			@RequestParam(value = "incidentId", required = false) Long incidentId,
			@RequestParam(value = "incidentGroupId", required = false) Long incidentGroupId) throws ServiceException {
		return resolveMessaging(service.getPriorityTrainees(null, incidentId, incidentGroupId));
	}

	@RequestMapping(value = "/incident-resources/{incidentResourceId}/home-unit-contacts", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getHomeUnitContact(@PathVariable("incidentResourceId") long incidentResourceId)
			throws ServiceException {
		return resolveMessaging(service.getHomeUnitContact(null, incidentResourceId));
	}

	@RequestMapping(value = "/resource-trainings/{resourceTrainingId}/objectives", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getObjectives(@PathVariable("resourceTrainingId") long resourceTrainingId)
			throws ServiceException {
		return resolveMessaging(service.getObjectives(null, resourceTrainingId));
	}

	@RequestMapping(value = "/resource-trainings/objectives", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveObjectives(@RequestBody RscTrainingObjectiveData data) throws ServiceException {
		return resolveMessaging(service.saveObjectives(data.getDialogueVo(), data.getRscTrainingObjectiveVos()));
	}

	@RequestMapping(value = "/trainers", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveTrainingTrainer(@RequestBody RscTrainingTrainerData data)
			throws ServiceException {
		return resolveMessaging(service.saveTrainingTrainer(data.getRscTrainingTrainerVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/trainers/{trainerId}", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteTrainingTrainer(
			@PathVariable("trainerId") long trainerId,
			@RequestParam(value= "incidentResourceId", required = false) Long incidentResourceId) throws ServiceException {
		RscTrainingTrainerVo vo = new RscTrainingTrainerVo();
		vo.setId(trainerId);
		if (incidentResourceId != null) {
			ResourceTrainingVo rtVo = new ResourceTrainingVo();
			IncidentResourceVo irVo = new IncidentResourceVo();
			irVo.setId(incidentResourceId);
			rtVo.setIncidentResourceVo(irVo);
			vo.setResourceTrainingVo(rtVo);
		}

		return resolveMessaging(service.deleteTrainingTrainer(vo, null));
	}
}
