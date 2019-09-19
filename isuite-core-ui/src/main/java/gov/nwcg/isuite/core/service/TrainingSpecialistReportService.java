package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.reports.filter.HomeUnitContactLabelsReportFilter;
import gov.nwcg.isuite.core.reports.filter.IncidentTrainingSummaryReportFilter;
import gov.nwcg.isuite.core.reports.filter.TrainingAssignmentsListReportFilter;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public interface TrainingSpecialistReportService {
	
	public DialogueVo generateTrainingAssignmentsListReport(DialogueVo dialogueVo, TrainingAssignmentsListReportFilter filter) throws ServiceException;
	
	public DialogueVo generateIncidentTrainingSummaryReport(DialogueVo dialogueVo, IncidentTrainingSummaryReportFilter filter) throws ServiceException;
	
	public DialogueVo generateDataFormReport(DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo generateEvaluatorFormReport(DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo generatePerformanceEvaluationReport(DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo generateHomeUnitLetterReport(DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo generateExitInterviewReport(DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo generateHomeUnitContactLabelsReport(DialogueVo dialogueVo, HomeUnitContactLabelsReportFilter filter) throws ServiceException;
	
	public DialogueVo getHomeUnitContactGrid(DialogueVo dialogueVo, Long incidentId, Long incidentGroupId) throws ServiceException;

	public DialogueVo getTrainingSpecialistList(DialogueVo dialogueVo, Long incidentId, Long incidentGroupId, Long rtId) throws ServiceException ;	

	public DialogueVo getEarliestStartDate(DialogueVo dialogueVo, Long incidentId, Long incidentGroupId) throws ServiceException;

}
