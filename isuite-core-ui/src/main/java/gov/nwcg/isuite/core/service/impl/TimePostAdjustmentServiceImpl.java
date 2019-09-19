package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.TimeAssignAdjust;
import gov.nwcg.isuite.core.domain.impl.AssignmentImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentAccountCodeImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.TimeAssignAdjustImpl;
import gov.nwcg.isuite.core.persistence.AssignmentDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.TimeAssignAdjustDao;
import gov.nwcg.isuite.core.rules.TimeAssignAdjustDeleteBatchRulesHandler;
import gov.nwcg.isuite.core.rules.TimeAssignAdjustDeleteRulesHandler;
import gov.nwcg.isuite.core.rules.TimeAssignAdjustSaveBatchRulesHandler;
import gov.nwcg.isuite.core.rules.TimeAssignAdjustSaveRulesHandler;
import gov.nwcg.isuite.core.service.TimePostAdjustmentService;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.TimeAssignAdjustVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.AdjustmentTypeEnum;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class TimePostAdjustmentServiceImpl extends BaseService implements TimePostAdjustmentService {
	private TimeAssignAdjustDao dao = null;
	
	public TimePostAdjustmentServiceImpl(){
		super();
	}
	
	public void initialization(){
		dao = (TimeAssignAdjustDao)super.context.getBean("timeAssignAdjustDao");
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.TimePostAdjustmentService#delete(gov.nwcg.isuite.core.vo.TimeAssignAdjustVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteAdjustment(Long id, DialogueVo dialogueVo) throws ServiceException {

		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{
		
			if(!LongUtility.hasValue(id))
				super.handleException(ErrorEnum._90000_ERROR,"timeAssignAdjustId is required to delete a time post adjustment.");

			TimeAssignAdjust entity = dao.getById(id, TimeAssignAdjustImpl.class);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"TimeAssignAdjust["+id+"]");

			TimeAssignAdjustDeleteRulesHandler ruleHandler = new TimeAssignAdjustDeleteRulesHandler(context);

			/*
			 * Run the deletion rules
			 */
			if(ruleHandler.execute(entity, null, dialogueVo)==AbstractRuleHandler._OK){

				/*
				 * B.R. 6.0011 Delete an Adjustment
				 *  3.	The system must retain deleted Time and Adjustment postings 
				 *      for historical and auditing purposes. 
				 *      The system must not allow the typical user to access deleted data 
				 *      in any lists, unless a filter for deleted Time postings is selected 
				 *      and the user has rights to access deleted data. 
				 *      Accessing deleted data will be strictly controlled and 
				 *      is not an objective of the e-ISuite System.
				 *  
				 *   Mark the entity as deleted by setting deletedDate
				 */
				entity.setDeletedDate(Calendar.getInstance().getTime());
				dao.save(entity);
				dao.flushAndEvict(entity);

				// run post process actions
				ruleHandler.executeProcessedActions(entity, null, dialogueVo);
				
				/*
				 * Build and set the affirm delete message
				 */
				CourseOfActionVo coaVo = new CourseOfActionVo();
			    coaVo.setCoaName("DELETE_ADJUSTMENT");
			    coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setIsDialogueEnding(Boolean.TRUE);
			    coaVo.setMessageVo(
						   new MessageVo(
								   "text.delete" 
								   ,"info.0028" 
								   , new String[]{"The Adjustment"}, 
								   MessageTypeEnum.INFO));
			    dialogueVo.setResultObject(id);
				dialogueVo.setCourseOfActionVo(coaVo);
				
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo deleteAdjustmentBatch(Long id, Collection<Integer> crewIds, DialogueVo dialogueVo) throws ServiceException {

		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{
			Collection<Long> crewIrIds= new ArrayList<Long>();
			if(CollectionUtility.hasValue(crewIds)){
				crewIrIds=IntegerUtility.convertToLongs(crewIds);
			}
			
			if(!LongUtility.hasValue(id))
				super.handleException(ErrorEnum._90000_ERROR,"timeAssignAdjustId is required to delete a time post adjustment.");

			TimeAssignAdjust entity = dao.getById(id, TimeAssignAdjustImpl.class);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"TimeAssignAdjust["+id+"]");

			TimeAssignAdjustDeleteBatchRulesHandler ruleHandler = new TimeAssignAdjustDeleteBatchRulesHandler(context);

			/*
			 * Run the deletion rules
			 */
			if(ruleHandler.execute(entity, null, crewIrIds,dialogueVo)==AbstractRuleHandler._OK){

				/*
				 * B.R. 6.0011 Delete an Adjustment
				 *  3.	The system must retain deleted Time and Adjustment postings 
				 *      for historical and auditing purposes. 
				 *      The system must not allow the typical user to access deleted data 
				 *      in any lists, unless a filter for deleted Time postings is selected 
				 *      and the user has rights to access deleted data. 
				 *      Accessing deleted data will be strictly controlled and 
				 *      is not an objective of the e-ISuite System.
				 *  
				 *   Mark the entity as deleted by setting deletedDate
				 */
				Date dt=Calendar.getInstance().getTime();
				entity.setDeletedDate(dt);
				dao.save(entity);
				dao.flushAndEvict(entity);

				// try and match same posting for crew ids
				if(CollectionUtility.hasValue(crewIrIds) && crewIrIds.size()>1){
					Collection<Long> taaIds = dao.getAdjustIds(entity, crewIrIds);
					if(CollectionUtility.hasValue(taaIds)){
						dao.deleteAdjustments(taaIds, dt);
					}
				}

				// get list of records deleted
				
				
				// run post process actions
				ruleHandler.executeProcessedActions(entity, null, crewIrIds,dialogueVo);
				
				/*
				 * Build and set the affirm delete message
				 */
				CourseOfActionVo coaVo = new CourseOfActionVo();
			    coaVo.setCoaName("DELETE_ADJUSTMENT_BATCH");
			    coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setMessageVo(new MessageVo("text.adjustments", "info.generic" , new String[]{"The Adjustments were deleted."}, MessageTypeEnum.INFO));
			    dialogueVo.setResultObject(id);
				dialogueVo.setCourseOfActionVo(coaVo);
				
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.TimePostAdjustmentService#getById(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getById(Long timeAssignAdjustId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{
			if(!LongUtility.hasValue(timeAssignAdjustId))
				super.handleException(ErrorEnum._90000_ERROR,"timeAssignAdjustId is required to retrive a time post adjustment.");

			TimeAssignAdjust entity = dao.getById(timeAssignAdjustId, TimeAssignAdjustImpl.class);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"TimeAssignAdjust["+timeAssignAdjustId+"]");
			
			TimeAssignAdjustVo vo = TimeAssignAdjustVo.getInstance(entity, true);
			
			/*
			 * Build course of action for HANDLE_RECORDSET and set resultObject
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_TIME_ASSIGN_ADJUST_BY_ID");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(vo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo getIncidentResourceAndTimeAdjustVos(Long incidentResourceId, Long assignmentId, DialogueVo dialogueVo) throws ServiceException {
	
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			if(LongUtility.hasValue(incidentResourceId)){
				IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
				IncidentResource irEntity = irDao.getById(incidentResourceId, IncidentResourceImpl.class);
				IncidentResourceVo vo = null;
				
				if(null != irEntity)
					vo = IncidentResourceVo.getInstance(irEntity, true);
				
				dialogueVo.setResultObject(vo);
			}
			
			if(!LongUtility.hasValue(assignmentId))
				super.handleException(ErrorEnum._90000_ERROR,"assignmentId is required to retrive a time post adjustment vos.");

			/*
			 * Get the vos
			 */
			Collection<TimeAssignAdjustVo> vos = dao.getGrid(assignmentId);

			/*
			 * Build course of action for HANDLE_RECORDSET and set recordset
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_INCIDENT_RESOURCE_AND_ADJUSTMENTS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);

			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.TimePostAdjustmentService#getTimeAdjustVos(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getTimeAdjustVos(Long assignmentId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{
			
			if(!LongUtility.hasValue(assignmentId))
				super.handleException(ErrorEnum._90000_ERROR,"assignmentId is required to retrive a time post adjustment vos.");

			/*
			 * Get the vos
			 */
			Collection<TimeAssignAdjustVo> vos = dao.getGrid(assignmentId);

			/*
			 * Build course of action for HANDLE_RECORDSET and set recordset
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_TIME_ASSIGN_ADJUST_VOS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);

			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.TimePostAdjustmentService#save(gov.nwcg.isuite.core.vo.TimeAssignAdjustVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo save(IncidentResourceVo incidentResourceVo,TimeAssignAdjustVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{
			
			TimeAssignAdjust entity = null;
			Assignment assignmentEntity = null;
			
			AssignmentDao assignmentDao = (AssignmentDao)context.getBean("assignmentDao");
			
			if(LongUtility.hasValue(vo.getId())){
				entity = dao.getById(vo.getId(), TimeAssignAdjustImpl.class);
				assignmentEntity = entity.getAssignment();
			}else{
				if(LongUtility.hasValue(vo.getAssignmentVo().getId())){
					assignmentEntity = assignmentDao.getById(vo.getAssignmentVo().getId(), AssignmentImpl.class);
				}
			}
			
			TimeAssignAdjustSaveRulesHandler ruleHandler = new TimeAssignAdjustSaveRulesHandler(context);
			
			/*
			 * Run the rule checks
			 */
			if(ruleHandler.execute(entity, incidentResourceVo,assignmentEntity,vo, dialogueVo)==AbstractRuleHandler._OK){

				entity = TimeAssignAdjustVo.toEntity(entity,vo,true);
				dao.save(entity);
				dao.flushAndEvict(entity);			
				
				// execute postprocess actions
				ruleHandler.executeProcessedActions(entity, incidentResourceVo, assignmentEntity , vo, dialogueVo);
				
				vo = TimeAssignAdjustVo.getInstance(dao.getById(entity.getId(), TimeAssignAdjustImpl.class), true);
				
				/*
				 * Build course of action for HANDLE_RECORDSET and set resultObject
				 */
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("AFFIRM_SAVE_ADJUSTMENT");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.adjustments", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);
				
				dialogueVo.setResultObject(vo);
				
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo saveBatch(IncidentResourceVo incidentResourceVo,TimeAssignAdjustVo vo, Collection<Integer> crewIds,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{
			Collection<Long> crewIrIds= new ArrayList<Long>();
			if(CollectionUtility.hasValue(crewIds)){
				crewIrIds=IntegerUtility.convertToLongs(crewIds);
			}
			
			TimeAssignAdjust entity = null;
			Assignment assignmentEntity = null;
			
			AssignmentDao assignmentDao = (AssignmentDao)context.getBean("assignmentDao");
			
			if(LongUtility.hasValue(vo.getId())){
				entity = dao.getById(vo.getId(), TimeAssignAdjustImpl.class);
				assignmentEntity = entity.getAssignment();
			}else{
				if(LongUtility.hasValue(vo.getAssignmentVo().getId())){
					assignmentEntity = assignmentDao.getById(vo.getAssignmentVo().getId(), AssignmentImpl.class);
				}
			}
			
			TimeAssignAdjustSaveBatchRulesHandler ruleHandler = new TimeAssignAdjustSaveBatchRulesHandler(context);
			
			/*
			 * Run the rule checks
			 */
			if(ruleHandler.execute(entity, incidentResourceVo,assignmentEntity,vo, crewIrIds,dialogueVo)==AbstractRuleHandler._OK){
				Collection<TimeAssignAdjustVo> finalVos = new ArrayList<TimeAssignAdjustVo>();

				if(null != entity && LongUtility.hasValue(entity.getId())){
					
					Collection<Long> adjIds=dao.getMatchingBatchAdjIds(entity, crewIrIds);
					if(CollectionUtility.hasValue(adjIds)){
						entity=TimeAssignAdjustVo.toEntity(entity, vo, true);
						dao.updateBatch(entity, adjIds);
						dao.flushAndEvict(entity);
						
						Collection<TimeAssignAdjustVo> updatedVos = new ArrayList<TimeAssignAdjustVo>();
						for(Long adjid : adjIds){
							TimeAssignAdjust entity2=dao.getById(adjid, TimeAssignAdjustImpl.class);
							TimeAssignAdjustVo vo2=TimeAssignAdjustVo.getInstance(entity2, true);
							updatedVos.add(vo2);
						}
						Collection<Long> assignmentIds=new ArrayList<Long>();
						for(TimeAssignAdjustVo v : updatedVos){
							assignmentIds.add(v.getAssignmentId());
						}
						Collection<TimeAssignAdjustVo> resInfoVos = dao.getResourceInfo(assignmentIds);
						for(TimeAssignAdjustVo resVo : resInfoVos){
							for(TimeAssignAdjustVo fvo : updatedVos){
								if(fvo.getAssignmentId().compareTo(resVo.getAssignmentId())==0){
									fvo.setRequestNumber(resVo.getRequestNumber());
									fvo.setLastName(resVo.getLastName());
									fvo.setFirstName(resVo.getFirstName());
									finalVos.add(fvo);
								}
							}
						}
					}
					
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName("AFFIRM_SAVE_ADJUSTMENT_BATCH");
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(new MessageVo("text.adjustments", "info.generic" , new String[]{"No adjustments created."}, MessageTypeEnum.INFO));
					coaVo.setIsDialogueEnding(true);

					dialogueVo.setCourseOfActionVo(coaVo);
					
					dialogueVo.setResultObject(vo);
					dialogueVo.setResultObjectAlternate(finalVos);
					
					return dialogueVo;
				}
				
				
				Collection<TimeAssignAdjustVo> excludeVos=dao.getLastInvoiceDateConflicts(vo.getActivityDate(), crewIrIds);
				Collection<TimeAssignAdjustVo> taaVosCreated=new ArrayList<TimeAssignAdjustVo>();

				AdjustmentTypeEnum adjType=null;
				boolean bexcludePrimary=false;
				for(TimeAssignAdjustVo taavo : excludeVos){
					if(taavo.getAssignmentId().compareTo(vo.getAssignmentId())==0)
						bexcludePrimary=true;
				}
				if(bexcludePrimary==false){
					entity = TimeAssignAdjustVo.toEntity(entity,vo,true);
					adjType=entity.getAdjustmentType();
					dao.save(entity);
					dao.flushAndEvict(entity);			
				}

				vo.setAssignmentId(vo.getAssignmentVo().getId());
				
				// create same posting for crew ids
				if(CollectionUtility.hasValue(crewIrIds) && crewIrIds.size()>1){
					Collection<Long> assignmentIds = dao.getAssignmentIds(crewIrIds);
					for(Long aid : assignmentIds){
						boolean bcontinue=true;
						for(TimeAssignAdjustVo taavo2 : excludeVos){
							if(taavo2.getAssignmentId().compareTo(aid)==0
									|| taavo2.getAssignmentId().compareTo(vo.getAssignmentId())==0){
								//bcontinue=false;
							}
						}
						if(bexcludePrimary==false){
							if(aid.compareTo(vo.getAssignmentId())==0)
								bcontinue=false;
						}
						if(bcontinue==true){
							TimeAssignAdjust taaEntity = new TimeAssignAdjustImpl();
							TimeAssignAdjustVo taaVo3  = new TimeAssignAdjustVo();
							if(DateTransferVo.hasDateString(vo.getActivityDateVo())){
								Date dt=DateTransferVo.getTransferDate(vo.getActivityDateVo());
								taaEntity.setActivityDate(dt);
							}
							taaVo3.setActivityDateVo(vo.getActivityDateVo());
							taaEntity.setAdjustmentAmount(vo.getAdjustmentAmount());
							taaVo3.setAdjustmentAmount(vo.getAdjustmentAmount());
							Assignment assignment = new AssignmentImpl();
							assignment.setId(aid);
							taaVo3.setAssignmentId(aid);
							taaEntity.setAssignment(assignment);
							taaEntity.setCommodity(vo.getCommodity());
							taaVo3.setCommodity(vo.getCommodity());
							taaEntity.setAdjustCategory(entity.getAdjustCategory());
							taaVo3.setAdjustCategoryVo(vo.getAdjustCategoryVo());
							taaEntity.setAdjustmentType(adjType);
							taaVo3.setAdjustmentType(adjType);
							IncidentAccountCode iac = new IncidentAccountCodeImpl();
							iac.setId(vo.getIncidentAccountCodeVo().getId());
							taaEntity.setIncidentAccountCode(iac);
							taaVo3.setIncidentAccountCodeVo(vo.getIncidentAccountCodeVo());
							dao.save(taaEntity);
							dao.flushAndEvict(taaEntity);
							if(vo.getAssignmentId().compareTo(aid)==0){
								entity=taaEntity;
							}
							taaVosCreated.add(taaVo3);							
						}
					}
				}
				
				// execute postprocess actions
				if(CollectionUtility.hasValue(taaVosCreated)){
					ruleHandler.executeProcessedActions(entity, incidentResourceVo, assignmentEntity , vo, crewIrIds,dialogueVo);
				}

				if(null != entity){
					vo = TimeAssignAdjustVo.getInstance(dao.getById(entity.getId(), TimeAssignAdjustImpl.class), true);
					taaVosCreated.add(vo);							
				}

				if(CollectionUtility.hasValue(taaVosCreated)){
					Collection<Long> assignmentIds=new ArrayList<Long>();
					for(TimeAssignAdjustVo v : taaVosCreated){
						assignmentIds.add(v.getAssignmentId());
					}
					Collection<TimeAssignAdjustVo> resInfoVos = dao.getResourceInfo(assignmentIds);
					for(TimeAssignAdjustVo resVo : resInfoVos){
						for(TimeAssignAdjustVo fvo : taaVosCreated){
							if(fvo.getAssignmentId().compareTo(resVo.getAssignmentId())==0){
								fvo.setRequestNumber(resVo.getRequestNumber());
								fvo.setLastName(resVo.getLastName());
								fvo.setFirstName(resVo.getFirstName());
								finalVos.add(fvo);
							}
						}
					}
				}
				
				/*
				 * Build course of action for HANDLE_RECORDSET and set resultObject
				 */
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("AFFIRM_SAVE_ADJUSTMENT_BATCH");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				if(CollectionUtility.hasValue(taaVosCreated))
					coaVo.setMessageVo(new MessageVo("text.adjustments", "info.0030" , null, MessageTypeEnum.INFO));
				else
					coaVo.setMessageVo(new MessageVo("text.adjustments", "info.generic" , new String[]{"No adjustments created."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);
				
				dialogueVo.setResultObject(vo);
				dialogueVo.setResultObjectAlternate(finalVos);
				
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

}
