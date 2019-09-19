package gov.nwcg.isuite.core.service;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.PriorityProgramVo;
import gov.nwcg.isuite.core.vo.TrainingContactVo;
import gov.nwcg.isuite.core.vo.TrainingSettingsVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public interface TrainingSpecialistSettingsService extends TransactionService {
	
	public DialogueVo savePriorityProgram(PriorityProgramVo vo, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo deletePriorityProgram(PriorityProgramVo vo, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getPriorityProgramGrid(DialogueVo dialogueVo, Long incidentId, Long incidentGroupId) throws ServiceException;
	
	public DialogueVo getTrainingSettings(DialogueVo dialogueVo, Long incidentId, Long incidentGroupId) throws ServiceException;
	
//	public DialogueVo saveTrainingSettings(TrainingSettingsVo vo, DialogueVo dialogueVo) throws ServiceException;
	public DialogueVo saveTrainingSettings(TrainingSettingsVo vo, Long igId, Collection<Integer> incIds,  DialogueVo dialogueVo) throws ServiceException;
	
//	public DialogueVo getContactResourcesGrid(DialogueVo dialogueVo, Long incidentId, Long incidentGroupId) throws ServiceException;
	
	public DialogueVo saveTrainingContact(TrainingContactVo vo, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo deleteTrainingContact(TrainingContactVo vo, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getTrainingContactGrids(DialogueVo dialogueVo, Long incidentId, Long incidentGroupId) throws ServiceException;
	
	public DialogueVo saveAcres(TrainingSettingsVo vo, DialogueVo dialogueVo) throws ServiceException;

}
