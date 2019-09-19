package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.PriorityProgram;
import gov.nwcg.isuite.core.domain.TrainingContact;
import gov.nwcg.isuite.core.domain.TrainingSettings;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.PriorityProgramImpl;
import gov.nwcg.isuite.core.domain.impl.TrainingContactImpl;
import gov.nwcg.isuite.core.domain.impl.TrainingSettingsImpl;
import gov.nwcg.isuite.core.persistence.FuelTypeDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.PriorityProgramDao;
import gov.nwcg.isuite.core.persistence.TrainingContactDao;
import gov.nwcg.isuite.core.persistence.TrainingSettingsDao;
import gov.nwcg.isuite.core.rules.IsLockedRulesHandler;
import gov.nwcg.isuite.core.rules.TnspDeletePriorityProgramRulesHandler;
import gov.nwcg.isuite.core.rules.TnspSavePriorityProgramRulesHandler;
import gov.nwcg.isuite.core.rules.TnspSaveTrainingSettingsRulesHandler;
import gov.nwcg.isuite.core.service.TrainingSpecialistSettingsService;
import gov.nwcg.isuite.core.vo.FuelTypeVo;
import gov.nwcg.isuite.core.vo.PriorityProgramVo;
import gov.nwcg.isuite.core.vo.TrainingContactVo;
import gov.nwcg.isuite.core.vo.TrainingSettingsVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

public class TrainingSpecialistSettingsServiceImpl extends BaseService implements
		TrainingSpecialistSettingsService {
	
	public void initialization(){
		
	}
	
	public DialogueVo savePriorityProgram(PriorityProgramVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			PriorityProgramDao ppDao = (PriorityProgramDao)super.context.getBean("priorityProgramDao");
			String originalCode="";
			
			PriorityProgram entity = null;
			if(LongUtility.hasValue(vo.getId())){
				entity = ppDao.getById(vo.getId(), PriorityProgramImpl.class);
				originalCode=entity.getCode();
			}

			if(null != vo
					&& LongUtility.hasValue(vo.getIncidentId()) ){
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(vo.getIncidentId(), "INCIDENT", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			if(null != vo
					&& LongUtility.hasValue(vo.getIncidentGroupId()) ){
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(vo.getIncidentGroupId(), "INCIDENTGROUP", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}

			
			
			TnspSavePriorityProgramRulesHandler ruleHandler = new TnspSavePriorityProgramRulesHandler(context);
			if(ruleHandler.execute(vo, entity, dialogueVo)==AbstractRule._OK){
				
				entity = PriorityProgramVo.toEntity(entity, vo, true);
				ppDao.save(entity);
				ppDao.flushAndEvict(entity);
				
				// sync new priority program across group/incidents if applicable
				if(!LongUtility.hasValue(vo.getId()) && LongUtility.hasValue(vo.getIncidentGroupId())){
					ppDao.syncNewWithGroup(vo.getCode().toUpperCase(), vo.getIncidentGroupId());
				}

				// sync update priority program across group/incidents if applicable
				if(LongUtility.hasValue(vo.getId()) && LongUtility.hasValue(vo.getIncidentGroupId())){
					ppDao.syncUpdateWithGroup(originalCode,vo.getCode().toUpperCase(), vo.getIncidentGroupId());
				}
				
				entity = ppDao.getById(entity.getId(), PriorityProgramImpl.class);
				
				vo = PriorityProgramVo.getInstance(entity, true);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_PRIORITY_PROGRAM");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.priorityProgram", "info.0030" , null, MessageTypeEnum.INFO));
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
	
	public DialogueVo saveTrainingSettings(TrainingSettingsVo vo, Long igId, Collection<Integer> incIds,  DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			TrainingSettingsDao tsDao = (TrainingSettingsDao)super.context.getBean("trainingSettingsDao");
			vo.setId(null);

			if(null != vo
					&& LongUtility.hasValue(vo.getIncidentId()) ){
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(vo.getIncidentId(), "INCIDENT", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			if(null != vo
					&& LongUtility.hasValue(vo.getIncidentGroupId()) ){
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(vo.getIncidentGroupId(), "INCIDENTGROUP", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			
			TrainingSettings igEntity = null;
			if(LongUtility.hasValue(igId)) {
				igEntity = tsDao.getByIncidentGroupId(igId);
				
				if(null != igEntity){
					igEntity = TrainingSettingsVo.toCreateEntity(igEntity, vo, igEntity.getId(), null, igId, true);
				}else{
					igEntity = TrainingSettingsVo.toCreateEntity(igEntity, vo, null, null, igId, true);
				}
				
				tsDao.save(igEntity);
				tsDao.flushAndEvict(igEntity);
			}
			
			Collection<Long> incidentIds = new ArrayList<Long>();
			
			for(Integer iId : incIds) {
				incidentIds.add(iId.longValue());
			}
			
			Collection<TrainingSettings> tsEntities = tsDao.getByIncidentIds(incidentIds);
			Collection<TrainingSettings> entitiesToSave = new ArrayList<TrainingSettings>();
			
			for(Long incId : incidentIds) {
				Boolean bMatch = false;
				for(TrainingSettings entity : tsEntities) {
					if (entity.getIncidentId().equals(incId)) {
						entitiesToSave.add(TrainingSettingsVo.toCreateEntity(entity, vo, entity.getId(), incId, null, true));
						bMatch = true;
					}
				}
				if(!bMatch){
					entitiesToSave.add(TrainingSettingsVo.toCreateEntity(null, vo, null, incId, null, true));
				}
			}
			
//			TnspSaveTrainingSettingsRulesHandler ruleHandler = new TnspSaveTrainingSettingsRulesHandler(context);
//			if(ruleHandler.execute(vo, entitiesToSave, dialogueVo)==AbstractRule._OK){
				tsDao.saveAll(entitiesToSave);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_TRAINING_SETTINGS");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.trainingSettings", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);

				// set the result object with updated vo
				dialogueVo.setResultObject(vo);
//			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
//	public DialogueVo saveTrainingSettings(TrainingSettingsVo vo, DialogueVo dialogueVo) throws ServiceException {
//		if(null == dialogueVo) dialogueVo = new DialogueVo();
//		
//		try{
//			TrainingSettingsDao tsDao = (TrainingSettingsDao)super.context.getBean("trainingSettingsDao");
//			
//			TrainingSettings entity = null;
//			if(LongUtility.hasValue(vo.getId())){
//				entity = tsDao.getById(vo.getId(), TrainingSettingsImpl.class);
//			}
//			
//			TnspSaveTrainingSettingsRulesHandler ruleHandler = new TnspSaveTrainingSettingsRulesHandler(context);
//			if(ruleHandler.execute(vo, dialogueVo)==AbstractRule._OK){
//				entity = TrainingSettingsVo.toEntity(entity, vo, true);
//				tsDao.save(entity);
//				tsDao.flushAndEvict(entity);
//				entity = tsDao.getById(entity.getId(), TrainingSettingsImpl.class);
//				
//				vo = TrainingSettingsVo.getInstance(entity, true);
//				
//				CourseOfActionVo coaVo = new CourseOfActionVo();
//				coaVo.setCoaName("SAVE_TRAINING_SETTINGS");
//				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
//				coaVo.setMessageVo(new MessageVo("text.trainingSettings", "info.0030" , null, MessageTypeEnum.INFO));
//				coaVo.setIsDialogueEnding(true);
//
//				dialogueVo.setCourseOfActionVo(coaVo);
//
//				// set the result object with updated vo
//				dialogueVo.setResultObject(vo);
//			}
//			
//		}catch(Exception e){
//			super.dialogueException(dialogueVo, e);
//		}
//		
//		return dialogueVo;
//	}
	
	public DialogueVo deletePriorityProgram(PriorityProgramVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			if(null != vo
					&& LongUtility.hasValue(vo.getIncidentId()) ){
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(vo.getIncidentId(), "INCIDENT", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			if(null != vo
					&& LongUtility.hasValue(vo.getIncidentGroupId()) ){
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(vo.getIncidentGroupId(), "INCIDENTGROUP", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			
			PriorityProgramDao ppDao = (PriorityProgramDao)super.context.getBean("priorityProgramDao");
		
			if((null == vo) || !LongUtility.hasValue(vo.getId()))
				throw new ServiceException("Unable to delete unknown priority program.");
			
			PriorityProgram entity = null;
			if(LongUtility.hasValue(vo.getId()))
				entity = ppDao.getById(vo.getId(), PriorityProgramImpl.class);
			
			TnspDeletePriorityProgramRulesHandler ruleHandler = new TnspDeletePriorityProgramRulesHandler(context);
			if(ruleHandler.execute(vo, entity, dialogueVo)==AbstractRule._OK){
				ppDao.delete(entity);
				
				if(LongUtility.hasValue(vo.getIncidentGroupId())){
					// delete same code for incidents in group
					ppDao.deleteCodeForGroupIncidents(vo.getCode(), vo.getIncidentGroupId());
				}
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_PRIORITY_PROGRAM");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.priorityProgram", "info.0028" , new String[]{"Priority Program"}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(vo);
				
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo getPriorityProgramGrid(DialogueVo dialogueVo, Long incidentId, Long incidentGroupId) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			PriorityProgramDao ppDao = (PriorityProgramDao)super.context.getBean("priorityProgramDao");
			
			Collection<PriorityProgramVo> vos = new ArrayList<PriorityProgramVo>();
			
			vos = ppDao.getGrid(incidentId, incidentGroupId);
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_PRIORITY_PROGRAM_GRID");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	public DialogueVo getTrainingSettings(DialogueVo dialogueVo, Long incidentId, Long incidentGroupId) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			TrainingSettingsDao tsDao = (TrainingSettingsDao)super.context.getBean("trainingSettingsDao");
			
			//Training Settings
			Collection<TrainingSettingsVo> vos = new ArrayList<TrainingSettingsVo>();

			// create initial record if it does not exist
			if(LongUtility.hasValue(incidentId)){
				vos = tsDao.getTrainingSettings(incidentId, incidentGroupId);
				if(!CollectionUtility.hasValue(vos)){
					TrainingSettings entity = new TrainingSettingsImpl();
					Incident inc = new IncidentImpl();
					inc.setId(incidentId);
					entity.setIncident(inc);
					tsDao.save(entity);
				}
			}
			if(LongUtility.hasValue(incidentGroupId)){
				IncidentGroupDao igDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				Collection<Long> incIds=igDao.getIncidentIdsInGroup(incidentGroupId);
				if(CollectionUtility.hasValue(incIds)){
					for(Long incId : incIds){
						vos = tsDao.getTrainingSettings(incId, 0L);
						if(!CollectionUtility.hasValue(vos)){
							TrainingSettings entity = new TrainingSettingsImpl();
							Incident inc = new IncidentImpl();
							inc.setId(incId);
							entity.setIncident(inc);
							tsDao.save(entity);
						}
					}
				}
			}

			vos = tsDao.getTrainingSettings(incidentId, incidentGroupId);
			
			//Fuel Types
			FuelTypeDao ftDao = (FuelTypeDao)super.context.getBean("fuelTypeDao");
			Collection<FuelTypeVo> ftVos = ftDao.getFuelTypes();
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_TRAINING_SETTINGS");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setResultObject(vos);
			dialogueVo.setResultObjectAlternate(ftVos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
//	public DialogueVo getTrainingSettings(DialogueVo dialogueVo, Long incidentId, Long incidentGroupId) throws ServiceException {
//		if(null == dialogueVo) dialogueVo = new DialogueVo();
//		
//		try{
//			TrainingSettingsDao tsDao = (TrainingSettingsDao)super.context.getBean("trainingSettingsDao");
//			
//			//Training Settings
//			TrainingSettings trainingSettings = null;
//			
//			if(LongUtility.hasValue(incidentId)) {
//				trainingSettings = tsDao.getTrainingSettings(incidentId, incidentGroupId);
//			}
//			
//			TrainingSettingsVo vo = new TrainingSettingsVo();
//			
//			if(null != trainingSettings)
//				vo = TrainingSettingsVo.getInstance(trainingSettings, true);
//			
//			//Fuel Types
//			FuelTypeDao ftDao = (FuelTypeDao)super.context.getBean("fuelTypeDao");
//			Collection<FuelTypeVo> ftVos = ftDao.getFuelTypes();
//			
//			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
//			courseOfActionVo.setCoaName("GET_TRAINING_SETTINGS");
//			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
//			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
//			
//			dialogueVo.setCourseOfActionVo(courseOfActionVo);
//			dialogueVo.setResultObject(vo);
//			dialogueVo.setResultObjectAlternate(ftVos);
//			
//		}catch(Exception e){
//			super.dialogueException(dialogueVo, e);
//		}
//		
//		return dialogueVo;
//	}
	
//	public DialogueVo getContactResourcesGrid(DialogueVo dialogueVo, Long incidentId, Long incidentGroupId) throws ServiceException {
//		if(null == dialogueVo) dialogueVo = new DialogueVo();
//		
//		try{
//			TrainingContactDao tcDao = (TrainingContactDao)super.context.getBean("trainingContactDao");
//			
//			Collection<TrainingContactVo> vos = new ArrayList<TrainingContactVo>();
//			vos = tcDao.getContactResourcesGrid(incidentId, incidentGroupId);
//			
//			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
//			courseOfActionVo.setCoaName("GET_CONTACT_RES_GRID");
//			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
//			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
//			
//			dialogueVo.setCourseOfActionVo(courseOfActionVo);
//			dialogueVo.setRecordset(vos);
//			
//		}catch(Exception e){
//			super.dialogueException(dialogueVo, e);
//		}
//		
//		return dialogueVo;
//		
//	}
	
	public DialogueVo getTrainingContactGrids(DialogueVo dialogueVo, Long incidentId, Long incidentGroupId) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			TrainingContactDao tcDao = (TrainingContactDao)super.context.getBean("trainingContactDao");
			
			Collection<TrainingContactVo> resVos = new ArrayList<TrainingContactVo>();
			resVos = tcDao.getContactResourcesGrid(incidentId, incidentGroupId);
			
			Collection<TrainingContactVo> conVos = new ArrayList<TrainingContactVo>();
			conVos = tcDao.getTrainingContactGrid(incidentId, incidentGroupId);
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_TRAINING_CONTACT_GRIDS");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setResultObject(resVos);
			dialogueVo.setResultObjectAlternate(conVos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
		
	}
	
	public DialogueVo saveTrainingContact(TrainingContactVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			TrainingContactDao tcDao = (TrainingContactDao)super.context.getBean("trainingContactDao");
			
			if(null != vo 
					&& LongUtility.hasValue(vo.getIncidentResourceId()) ){
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(vo.getIncidentResourceId(), "INCIDENTRESOURCE", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			
			TrainingContact entity = null;
			if(LongUtility.hasValue(vo.getId())){
				entity = tcDao.getById(vo.getId(), TrainingContactImpl.class);
			}
			
			//TnspSaveTrainingContactRulesHandler
			IncidentResourceDao irDao = (IncidentResourceDao)super.context.getBean("incidentResourceDao");
			IncidentResource irEntity = irDao.getById(vo.getIncidentResourceId(), IncidentResourceImpl.class);
			
			entity = TrainingContactVo.toEntity(entity, vo, true, irEntity);
			tcDao.save(entity);
			tcDao.flushAndEvict(entity);
			entity = tcDao.getById(entity.getId(), TrainingContactImpl.class);
			
			vo = TrainingContactVo.getInstance(entity, true);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_TRAINING_CONTACT");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.contactInformation", "info.0030" , null, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(true);

			dialogueVo.setCourseOfActionVo(coaVo);

			// set the result object with updated vo
			dialogueVo.setResultObject(vo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo deleteTrainingContact(TrainingContactVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			TrainingContactDao tcDao = (TrainingContactDao)super.context.getBean("trainingContactDao");
		
			if((null == vo) || !LongUtility.hasValue(vo.getId()))
				throw new ServiceException("Unable to delete unknown training contact.");

			if(null != vo 
					&& LongUtility.hasValue(vo.getIncidentResourceId()) ){
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(vo.getIncidentResourceId(), "INCIDENTRESOURCE", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			
			TrainingContact entity = null;
			if(LongUtility.hasValue(vo.getId()))
				entity = tcDao.getById(vo.getId(), TrainingContactImpl.class);
			
			//TnspDeleteTrainingContactRulesHandler
			tcDao.delete(entity);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("DELETE_TRAINING_CONTACT");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.trainingContact", "info.0028" , new String[]{"Training Contact"}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(vo);
				
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo saveAcres(TrainingSettingsVo vo, DialogueVo dialogueVo) throws ServiceException {
		
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			TrainingSettingsDao tsDao = (TrainingSettingsDao)super.context.getBean("trainingSettingsDao");
			
			if(null != vo
					&& LongUtility.hasValue(vo.getIncidentId()) ){
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(vo.getIncidentId(), "INCIDENT", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			if(null != vo
					&& LongUtility.hasValue(vo.getIncidentGroupId()) ){
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(vo.getIncidentGroupId(), "INCIDENTGROUP", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			
			TrainingSettings entity = null;
			if(LongUtility.hasValue(vo.getId())){
				entity = tsDao.getById(vo.getId(), TrainingSettingsImpl.class);
			}
			
			TnspSaveTrainingSettingsRulesHandler ruleHandler = new TnspSaveTrainingSettingsRulesHandler(context);
			if(ruleHandler.execute(vo, dialogueVo)==AbstractRule._OK){
				entity = TrainingSettingsVo.toEntity(entity, vo, true);
				tsDao.save(entity);
				tsDao.flushAndEvict(entity);
				entity = tsDao.getById(entity.getId(), TrainingSettingsImpl.class);
				
				vo = TrainingSettingsVo.getInstance(entity, true);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_ACRES");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.trainingSettings", "info.0030" , null, MessageTypeEnum.INFO));
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
	

}
