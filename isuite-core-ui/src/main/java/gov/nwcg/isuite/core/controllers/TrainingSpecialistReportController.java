package gov.nwcg.isuite.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.reports.filter.HomeUnitContactLabelsReportFilter;
import gov.nwcg.isuite.core.reports.filter.IncidentTrainingSummaryReportFilter;
import gov.nwcg.isuite.core.reports.filter.TrainingAssignmentsListReportFilter;
import gov.nwcg.isuite.core.reports.filter.TrainingSpecialistReportFilter;
import gov.nwcg.isuite.core.service.TrainingSpecialistReportService;
import gov.nwcg.isuite.core.vo.TrainingSpecialistVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

@Controller
@RequestMapping("/training-specialists/reports")
public class TrainingSpecialistReportController extends BaseRestController {

	@Autowired
	private TrainingSpecialistReportService service;
	
	@RequestMapping(value = "/training-assignments", method = RequestMethod.POST)
	public @ResponseBody DialogueVo generateTrainingAssignmentsListReport(
			@RequestBody TrainingAssignmentsListReportFilter trainingAssignmentsListReportFilter) throws ServiceException {
		
		return resolveMessaging(service.generateTrainingAssignmentsListReport(null, trainingAssignmentsListReportFilter));
	}
	
	@RequestMapping(value = "/incident-training-summary", method = RequestMethod.POST)
	public @ResponseBody DialogueVo generateIncidentTrainingSummaryReport(
			@RequestBody IncidentTrainingSummaryReportFilter incidentTrainingSummaryReportFilter) throws ServiceException {
		
		return resolveMessaging(service.generateIncidentTrainingSummaryReport(null, incidentTrainingSummaryReportFilter));
	}
	
	@RequestMapping(value = "/data-form", method = RequestMethod.POST)
	public @ResponseBody DialogueVo generateDataFormReport(
			@RequestBody TrainingSpecialistReportFilter trainingSpecialistReportFilter) throws ServiceException {
		DialogueVo dialogueVo = new DialogueVo();
		TrainingSpecialistVo tsVo = new TrainingSpecialistVo();
		tsVo.setId(trainingSpecialistReportFilter.getTrainingSpecialistId());
		dialogueVo.setResultObject(tsVo);
		dialogueVo.setResultObjectAlternate(trainingSpecialistReportFilter.getResourceTrainingId());
		dialogueVo.setResultObjectAlternate2(trainingSpecialistReportFilter.getTrainerId());
		dialogueVo.setResultObjectAlternate4(trainingSpecialistReportFilter.getBlankForm());
		
		return resolveMessaging(service.generateDataFormReport(dialogueVo));
	}
	
	@RequestMapping(value = "/evaluator-form", method = RequestMethod.POST)
	public @ResponseBody DialogueVo generateEvaluatorFormReport(
			@RequestBody TrainingSpecialistReportFilter trainingSpecialistReportFilter) throws ServiceException {
		DialogueVo dialogueVo = new DialogueVo();
		dialogueVo.setResultObjectAlternate(trainingSpecialistReportFilter.getResourceTrainingId());
		dialogueVo.setResultObjectAlternate2(trainingSpecialistReportFilter.getTrainerId());
		dialogueVo.setResultObjectAlternate3(trainingSpecialistReportFilter.getEvaluationRecordNumber());
		dialogueVo.setResultObjectAlternate4(trainingSpecialistReportFilter.getBlankForm());
		
		return resolveMessaging(service.generateEvaluatorFormReport(dialogueVo));
	}
	
	@RequestMapping(value = "/performance-evaluation", method = RequestMethod.POST)
	public @ResponseBody DialogueVo generatePerformanceEvaluationReport(
			@RequestBody TrainingSpecialistReportFilter trainingSpecialistReportFilter) throws ServiceException {
		DialogueVo dialogueVo = new DialogueVo();
		dialogueVo.setResultObjectAlternate(trainingSpecialistReportFilter.getResourceTrainingId());
		dialogueVo.setResultObjectAlternate2(trainingSpecialistReportFilter.getTrainerId());
		dialogueVo.setResultObjectAlternate4(trainingSpecialistReportFilter.getBlankForm());
		
		return resolveMessaging(service.generatePerformanceEvaluationReport(dialogueVo));
	}
	
	@RequestMapping(value = "/home-unit-letter", method = RequestMethod.POST)
	public @ResponseBody DialogueVo generateHomeUnitLetterReport(
			@RequestBody TrainingSpecialistReportFilter trainingSpecialistReportFilter) throws ServiceException {
		DialogueVo dialogueVo = new DialogueVo();
		TrainingSpecialistVo tsVo = new TrainingSpecialistVo();
		tsVo.setId(trainingSpecialistReportFilter.getTrainingSpecialistId());
		dialogueVo.setResultObject(tsVo);
		dialogueVo.setResultObjectAlternate(trainingSpecialistReportFilter.getResourceTrainingId());
		dialogueVo.setResultObjectAlternate2(trainingSpecialistReportFilter.getTrainerId());
		dialogueVo.setResultObjectAlternate4(trainingSpecialistReportFilter.getBlankForm());
		
		return resolveMessaging(service.generateHomeUnitLetterReport(dialogueVo));
	}
	
	@RequestMapping(value = "/exit-interview", method = RequestMethod.POST)
	public @ResponseBody DialogueVo generateExitInterviewReport(
			@RequestBody TrainingSpecialistReportFilter trainingSpecialistReportFilter) throws ServiceException {
		DialogueVo dialogueVo = new DialogueVo();
		TrainingSpecialistVo tsVo = new TrainingSpecialistVo();
		tsVo.setId(trainingSpecialistReportFilter.getTrainingSpecialistId());
		dialogueVo.setResultObject(tsVo);
		dialogueVo.setResultObjectAlternate(trainingSpecialistReportFilter.getResourceTrainingId());
		dialogueVo.setResultObjectAlternate2(trainingSpecialistReportFilter.getTrainerId());
		dialogueVo.setResultObjectAlternate4(trainingSpecialistReportFilter.getBlankForm());
		
		return resolveMessaging(service.generateExitInterviewReport(dialogueVo));
	}
	
	@RequestMapping(value = "/home-unit-contact-labels", method = RequestMethod.POST)
	public @ResponseBody DialogueVo generateHomeUnitContactLabelsReport(
			@RequestBody HomeUnitContactLabelsReportFilter homeUnitContactLabelsReportFilter) throws ServiceException {
		
		return resolveMessaging(service.generateHomeUnitContactLabelsReport(null, homeUnitContactLabelsReportFilter));
	}
	
	@RequestMapping(value = "/home-unit-contacts/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getHomeUnitContactGrid(
			@RequestParam(value = "incidentId", required = false) Long incidentId,
			@RequestParam(value = "incidentGroupId", required = false) Long incidentGroupId) throws ServiceException {
		DialogueVo vo = service.getHomeUnitContactGrid(null, incidentId, incidentGroupId);
		return resolveMessaging(vo);
	}
	
	@RequestMapping(value = "/training-specialist-list", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getTrainingSpecialistList(
			@RequestParam(value = "incidentId", required = false) Long incidentId,
			@RequestParam(value = "incidentGroupId", required = false) Long incidentGroupId,
			@RequestParam(value = "rtId", required = false) Long rtId) throws ServiceException {
		DialogueVo vo = service.getTrainingSpecialistList(null, incidentId, incidentGroupId, rtId);
		return resolveMessaging(vo);
	}
	
	@RequestMapping(value = "/earliest-start-date", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getEarliestStartDate(
			@RequestParam(value = "incidentId", required = false) Long incidentId,
			@RequestParam(value = "incidentGroupId", required = false) Long incidentGroupId) throws ServiceException {
		DialogueVo vo = service.getEarliestStartDate(null, incidentId, incidentGroupId);
		return resolveMessaging(vo);
	}
}
