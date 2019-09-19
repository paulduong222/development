package gov.nwcg.isuite.core.service;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.ResourceHomeUnitContactVo;
import gov.nwcg.isuite.core.vo.ResourceTrainingVo;
import gov.nwcg.isuite.core.vo.RscTrainingObjectiveVo;
import gov.nwcg.isuite.core.vo.RscTrainingTrainerVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public interface TrainingSpecialistService extends TransactionService {
	
	public DialogueVo saveResourceTraining(ResourceTrainingVo vo, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo deleteResourceTraining(ResourceTrainingVo vo, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getResourceTrainings(Long id, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo saveHomeUnitContact(ResourceHomeUnitContactVo vo, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getIncidentEvaluatorsGrid(DialogueVo dialogueVo, Long incidentResourceId, Long incidentId, Long incidentGroupId) throws ServiceException;
	
	public DialogueVo getTraineeTotal(DialogueVo dialogueVo, Long incidentId, Long incidentGroupId) throws ServiceException;
	
	public DialogueVo getPriorityTrainees(DialogueVo dialogueVo, Long incidentId, Long incidentGroupId) throws ServiceException;
	
	public DialogueVo getHomeUnitContact(DialogueVo dialogueVo, Long incidentResourceId) throws ServiceException;
	
	public DialogueVo getObjectives(DialogueVo dialogueVo, Long resourceTrainingId) throws ServiceException;
	
	public DialogueVo saveObjectives(DialogueVo dialogueVo, Collection<RscTrainingObjectiveVo> vos) throws ServiceException;
	
	public DialogueVo saveTrainingTrainer(RscTrainingTrainerVo vo, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo deleteTrainingTrainer(RscTrainingTrainerVo vo, DialogueVo dialogueVo) throws ServiceException;
	
	
	
}
