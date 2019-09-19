package gov.nwcg.isuite.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.ResourceHomeUnitContact;
import gov.nwcg.isuite.core.domain.ResourceTraining;
import gov.nwcg.isuite.core.domain.RscTrainingObjective;
import gov.nwcg.isuite.core.domain.RscTrainingTrainer;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceHomeUnitContactImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceTrainingImpl;
import gov.nwcg.isuite.core.domain.impl.RscTrainingObjectiveImpl;
import gov.nwcg.isuite.core.domain.impl.RscTrainingTrainerImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.ResourceTrainingDao;
import gov.nwcg.isuite.core.persistence.ResourceTrainingObjectiveDao;
import gov.nwcg.isuite.core.persistence.ResourceTrainingTrainerDao;
import gov.nwcg.isuite.core.persistence.hibernate.ResourceHomeUnitContactDao;
import gov.nwcg.isuite.core.rules.IsLockedRulesHandler;
import gov.nwcg.isuite.core.rules.TnspSaveResourceHomeUnitContactRulesHandler;
import gov.nwcg.isuite.core.rules.TnspSaveResourceTrainingRulesHandler;
import gov.nwcg.isuite.core.rules.TnspSaveRscTrainingTrainorRulersHandler;
import gov.nwcg.isuite.core.service.TrainingSpecialistService;
import gov.nwcg.isuite.core.vo.ResourceHomeUnitContactVo;
import gov.nwcg.isuite.core.vo.ResourceTrainingVo;
import gov.nwcg.isuite.core.vo.RscTrainingObjectiveVo;
import gov.nwcg.isuite.core.vo.RscTrainingTrainerVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

public class TrainingSpecialistServiceImpl extends BaseService implements TrainingSpecialistService {
	
	public void initialization(){
	}
	
	public DialogueVo saveResourceTraining(ResourceTrainingVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
			
		try{
			ResourceTrainingDao rtDao = (ResourceTrainingDao)super.context.getBean("resourceTrainingDao");

			if(null !=vo.getIncidentResourceVo()
					&& LongUtility.hasValue(vo.getIncidentResourceVo().getId())){
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(vo.getIncidentResourceVo().getId(), "INCIDENTRESOURCE", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			
			ResourceTraining entity = null;
			if(LongUtility.hasValue(vo.getId())){
				entity = rtDao.getById(vo.getId(), ResourceTrainingImpl.class);
			}
			
			TnspSaveResourceTrainingRulesHandler ruleHandler = new TnspSaveResourceTrainingRulesHandler(context);
			if(ruleHandler.execute(vo, dialogueVo)==AbstractRule._OK){
				
				IncidentResourceDao irDao = (IncidentResourceDao)super.context.getBean("incidentResourceDao");
				IncidentResource irEntity = irDao.getById(vo.getIncidentResourceVo().getId(), IncidentResourceImpl.class);
				
				entity = ResourceTrainingVo.toEntity(entity, vo, true, irEntity);
				rtDao.save(entity);
				rtDao.flushAndEvict(entity);
				entity = rtDao.getById(entity.getId(), ResourceTrainingImpl.class);
				
				vo = ResourceTrainingVo.getInstance(entity, true);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_RESOURCE_TRAINING");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.traineeAssignment", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);

				// set the result object with updated vo
				dialogueVo.setResultObject(vo);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo deleteResourceTraining(ResourceTrainingVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			ResourceTrainingDao rtDao = (ResourceTrainingDao)super.context.getBean("resourceTrainingDao");
			
			if(null !=vo.getIncidentResourceVo()
					&& LongUtility.hasValue(vo.getIncidentResourceVo().getId())){
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(vo.getIncidentResourceVo().getId(), "INCIDENTRESOURCE", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			
			if((null == vo) || !LongUtility.hasValue(vo.getId()))
				throw new ServiceException("Unable to delete unknown trainee assignment.");
			
			ResourceTraining entity = null;
			if(LongUtility.hasValue(vo.getId()))
				entity = rtDao.getById(vo.getId(), ResourceTrainingImpl.class);
			
			//ruleHandler	
//			TnspDeleteResourceTrainingRulesHandler ruleHandler = new TnspDeleteResourceTrainingRulesHandler(context);
			
			rtDao.delete(entity);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_RESOURCE_TRAINING");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.traineeAssignment", "info.0028" , new String[]{"Trainee Assignment"}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(vo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo getResourceTrainings(Long incidentResourceId, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
	
		try{
			ResourceTrainingDao rtDao = (ResourceTrainingDao)super.context.getBean("resourceTrainingDao");
			
			Collection<ResourceTrainingVo> vos = new ArrayList<ResourceTrainingVo>();
			
			vos = rtDao.getResourceTrainings(incidentResourceId);
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_RESOURCE_TRAININGS");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo saveHomeUnitContact(ResourceHomeUnitContactVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			ResourceHomeUnitContactDao hucDao = (ResourceHomeUnitContactDao)super.context.getBean("resourceHomeUnitContactDao");
			
			ResourceHomeUnitContact entity = null;
			
			if(LongUtility.hasValue(vo.getId())) {
				entity = hucDao.getById(vo.getId(), ResourceHomeUnitContactImpl.class);
			}
			
			if(null !=vo.getIncidentResourceVo()
					&& LongUtility.hasValue(vo.getIncidentResourceVo().getId()) ){
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(vo.getIncidentResourceVo().getId(), "INCIDENTRESOURCE", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			
			TnspSaveResourceHomeUnitContactRulesHandler ruleHandler = new TnspSaveResourceHomeUnitContactRulesHandler(context);
			if(ruleHandler.execute(vo, dialogueVo)==AbstractRule._OK){
				
				IncidentResourceDao irDao = (IncidentResourceDao)super.context.getBean("incidentResourceDao");
				IncidentResource irEntity = irDao.getById(vo.getIncidentResourceVo().getId(), IncidentResourceImpl.class);
				
				entity = ResourceHomeUnitContactVo.toEntity(entity, vo, true, irEntity);
				hucDao.save(entity);
				hucDao.flushAndEvict(entity);
				entity = hucDao.getById(entity.getId(), ResourceHomeUnitContactImpl.class);
				
				vo = ResourceHomeUnitContactVo.getInstance(entity, true);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_HOME_UNIT_CONTACT");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.homeUnitContact", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);

				// set the result object with updated vo
				dialogueVo.setResultObject(vo);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo saveObjectives(DialogueVo dialogueVo, Collection<RscTrainingObjectiveVo> vos) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
			
		try{
			ResourceTrainingObjectiveDao rtoDao = (ResourceTrainingObjectiveDao)super.context.getBean("resourceTrainingObjectiveDao");
			
			Collection<RscTrainingObjectiveVo> oVos = new ArrayList<RscTrainingObjectiveVo>();

			for(RscTrainingObjectiveVo v : vos){
				if(null !=v.getResourceTrainingVo()
						&& null != v.getResourceTrainingVo().getIncidentResourceVo()
						&& LongUtility.hasValue(v.getResourceTrainingVo().getIncidentResourceVo().getId()) ){
					IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
					if(lockedRuleHandler.execute(v.getResourceTrainingVo().getIncidentResourceVo().getId(), "INCIDENTRESOURCE", dialogueVo)!=AbstractRule._OK){
						return dialogueVo;
					}
				}
			}
			
			for(RscTrainingObjectiveVo vo : vos){
				RscTrainingObjective entity = null;
				
				if(LongUtility.hasValue(vo.getId())){
					entity = rtoDao.getById(vo.getId(), RscTrainingObjectiveImpl.class);
				}
				
				entity = RscTrainingObjectiveVo.toEntity(entity, vo, true);
				rtoDao.save(entity);
				rtoDao.flushAndEvict(entity);
				entity = rtoDao.getById(entity.getId(), RscTrainingObjectiveImpl.class);
				
				vo = RscTrainingObjectiveVo.getInstance(entity, true);
				
				oVos.add(vo);
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_OBJECTIVES");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.objectives", "info.0030" , null, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(oVos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo getHomeUnitContact(DialogueVo dialogueVo, Long incidentResourceId) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			ResourceHomeUnitContactDao hucDao = (ResourceHomeUnitContactDao)super.context.getBean("resourceHomeUnitContactDao");
			
			ResourceHomeUnitContactVo vo = ResourceHomeUnitContactVo.getInstance(hucDao.getResourceHomeUnitContact(incidentResourceId), true);
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_HOME_UNIT_CONTACT");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setResultObject(vo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	@SuppressWarnings("unchecked")
	public DialogueVo getIncidentEvaluatorsGrid(DialogueVo dialogueVo, Long incidentResourceId, Long incidentId, Long incidentGroupId) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			ResourceTrainingTrainerDao rtDao = (ResourceTrainingTrainerDao)super.context.getBean("resourceTrainingTrainerDao");
			
			Collection<RscTrainingTrainerVo> vos = new ArrayList<RscTrainingTrainerVo>();
			
			vos = rtDao.getEvalIncResGrid(incidentResourceId, incidentId, incidentGroupId);
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_INCIDENT_EVALUATORS_GRID");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo getTraineeTotal(DialogueVo dialogueVo, Long incidentId, Long incidentGroupId) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		ResourceTrainingDao rtDao = (ResourceTrainingDao)super.context.getBean("resourceTrainingDao");
		
		try{
			String traineeTotal = rtDao.getTraineeTotal(incidentId, incidentGroupId);
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_TRAINEE_TOTAL");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setResultObject(traineeTotal);
			
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo getPriorityTrainees(DialogueVo dialogueVo, Long incidentId, Long incidentGroupId) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		ResourceTrainingDao rtDao = (ResourceTrainingDao)super.context.getBean("resourceTrainingDao");
		
		try{
			String priorityTrainees = rtDao.getPriorityTrainees(incidentId, incidentGroupId);
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_PRIORITY_TRAINEES");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setResultObject(priorityTrainees);
			
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo getObjectives(DialogueVo dialogueVo, Long resourceTrainingId) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			ResourceTrainingObjectiveDao rtoDao = (ResourceTrainingObjectiveDao)super.context.getBean("resourceTrainingObjectiveDao");
			
			Collection<RscTrainingObjectiveVo> vos = new ArrayList<RscTrainingObjectiveVo>();
			
			vos = rtoDao.getObjectives(resourceTrainingId);
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_OBJECTIVES");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo saveTrainingTrainer(RscTrainingTrainerVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			ResourceTrainingTrainerDao rttDao = (ResourceTrainingTrainerDao)super.context.getBean("resourceTrainingTrainerDao");
			
			if(null !=vo.getResourceTrainingVo()
					&& LongUtility.hasValue(vo.getResourceTrainingVo().getId()) ){
				ResourceTrainingDao rtDao = (ResourceTrainingDao)context.getBean("resourceTrainingDao");
				ResourceTraining rt = rtDao.getById(vo.getResourceTrainingVo().getId(), ResourceTrainingImpl.class);
				if(null != rt){
					IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
					if(lockedRuleHandler.execute(rt.getIncidentResourceId(), "INCIDENTRESOURCE", dialogueVo)!=AbstractRule._OK){
						return dialogueVo;
					}
					rtDao.flushAndEvict(rt);
				}
			}
			
			RscTrainingTrainer entity = null;
			if(LongUtility.hasValue(vo.getId())){
				entity = rttDao.getById(vo.getId(), RscTrainingTrainerImpl.class);
			}
			
			TnspSaveRscTrainingTrainorRulersHandler ruleHandler = new TnspSaveRscTrainingTrainorRulersHandler(context);
			if(ruleHandler.execute(vo, dialogueVo)==AbstractRule._OK){
				
				if(null != vo.getIncidentResourceVo() && LongUtility.hasValue(vo.getIncidentResourceVo().getId())){
					IncidentResourceDao irDao = (IncidentResourceDao)super.context.getBean("incidentResourceDao");
					IncidentResource irEntity = irDao.getById(vo.getIncidentResourceVo().getId(), IncidentResourceImpl.class);
					entity = RscTrainingTrainerVo.toEntity(entity, vo, true, irEntity);
				}else{
					entity = RscTrainingTrainerVo.toEntity(entity, vo, true);
				}
				
				rttDao.save(entity);
				rttDao.flushAndEvict(entity);
				entity = rttDao.getById(entity.getId(), RscTrainingTrainerImpl.class);
				
				vo = RscTrainingTrainerVo.getInstance(entity, true);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_TRAINER");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.evaluator", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);

				// set the result object with updated vo
				dialogueVo.setResultObject(vo);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo deleteTrainingTrainer(RscTrainingTrainerVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			ResourceTrainingTrainerDao rttDao = (ResourceTrainingTrainerDao)super.context.getBean("resourceTrainingTrainerDao");
			
			if((null == vo) || !LongUtility.hasValue(vo.getId()))
				throw new ServiceException("Unable to delete unknown evaluator.");
			
			if(null !=vo.getResourceTrainingVo()
					&& null !=vo.getResourceTrainingVo().getIncidentResourceVo()
					&& LongUtility.hasValue(vo.getResourceTrainingVo().getIncidentResourceVo().getId()) ){
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(vo.getResourceTrainingVo().getIncidentResourceVo().getId(), "INCIDENTRESOURCE", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}

			RscTrainingTrainer entity = null;
			if(LongUtility.hasValue(vo.getId()))
				entity = rttDao.getById(vo.getId(), RscTrainingTrainerImpl.class);
			
			//ruleHandler	
			
			rttDao.delete(entity);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("DELETE_TRAINER");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.evaluator", "info.0028" , new String[]{"Evaluator"}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(vo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	
	}

}
