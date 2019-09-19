package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimeImpl;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimePostImpl;
import gov.nwcg.isuite.core.filter.impl.TimePostQueryFilterImpl;
import gov.nwcg.isuite.core.persistence.AssignmentTimeDao;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.rules.TimePostDeleteRulesHandler;
import gov.nwcg.isuite.core.rules.TimePostSaveCrewRulesHandler;
import gov.nwcg.isuite.core.rules.TimePostSaveRulesHandler;
import gov.nwcg.isuite.core.service.TimePostService;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;

public class TimePostServiceImpl extends BaseService implements TimePostService {
	private String empType="";
	
	public TimePostServiceImpl(){
		super();
	}
	
	public void initialization(){
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.TimePostService#getGrid(java.lang.Integer)
	 */
	public Collection<AssignmentTimePostVo> getGrid(Integer assignmentTimeId) throws ServiceException{
		
		try{
			if(null == assignmentTimeId || assignmentTimeId < 1){
				//throw new ServiceException(new ErrorObject(ErrorEnum._90000_ERROR,"Unable to retrieve Time Postings for unknown employment type"));
				return new ArrayList<AssignmentTimePostVo>();
			}
			
			TimePostDao dao = (TimePostDao)context.getBean("timePostDao");
			
			Long lngAssignmentTimeId = TypeConverter.convertToLong(assignmentTimeId);
			
			return dao.getGrid(lngAssignmentTimeId);
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(new ErrorObject(ErrorEnum._90000_ERROR,e.getMessage()));
		}
		
	}

	public Collection<AssignmentTimePostVo> getGridCrew(Collection<Integer> assignmentTimeIds) throws ServiceException{
		Collection<AssignmentTimePostVo> vos = null;
		
		try{
			Collection<Long> ids = IntegerUtility.convertToLongs(assignmentTimeIds);
			
			if(!CollectionUtility.hasValue(ids))
				return new ArrayList<AssignmentTimePostVo>();
			
			TimePostDao dao = (TimePostDao)context.getBean("timePostDao");
			
			
			vos = dao.getGridCrew(ids);
			
		}catch(Exception e){
			throw new ServiceException(new ErrorObject(ErrorEnum._90000_ERROR,e.getMessage()));
		}
		
		return vos;
		
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.TimePostService#getById(java.lang.Long)
	 */
	public AssignmentTimePostVo getById(Long id) throws ServiceException {
		
		try{
			TimePostDao dao = (TimePostDao)context.getBean("timePostDao");
			
			AssignmentTimePost entity = dao.getById(id, AssignmentTimePostImpl.class);
			
			if(null != entity){
				AssignmentTimePostVo vo = AssignmentTimePostVo.getInstance(entity, true);
				dao.flushAndEvict(entity);
				return vo;
			}else
				return null;
			
		}catch(Exception e){
			throw new ServiceException(new ErrorObject(ErrorEnum._90000_ERROR,e.getMessage()));
		}
	}
	
	public DialogueVo saveTimePosts(Collection<Integer> assignmentTimeIds , AssignmentTimePostVo post, DialogueVo dialogueVo) throws ServiceException {
		
		try {
			
			if(null == dialogueVo) dialogueVo = new DialogueVo();
			
			TimePostSaveRulesHandler rulesHandler = new TimePostSaveRulesHandler(context);
			ArrayList<Long> failedAssignmentTimeIds = new ArrayList<Long>();
			
			AssignmentTimeDao atd = (AssignmentTimeDao)context.getBean("assignmentTimeDao");
			
			for(Integer id : assignmentTimeIds) {
				
//				AssignmentTime atEntity = atd.getById(IntegerUtility.convertToLong(id), AssignmentTime.class);
				Long assignmentTimeId = TypeConverter.convertToLong(id);
				post.setAssignmentTimeId(assignmentTimeId);
			
				//preserve the resource's employment type at time of posting
//				post.setEmploymentType(atEntity.getEmploymentType());
				
				if(rulesHandler.execute(null,post, dialogueVo,"") != TimePostSaveRulesHandler._OK){
					
					failedAssignmentTimeIds.add(assignmentTimeId);
					
				} 
			}
			
			//if there were any failures
			if(failedAssignmentTimeIds.size() > 0) {
			
				//notify the user that all transactions have been rolled back
			} else {
				
				//save all of the posts
				for(Integer id : assignmentTimeIds) {
					
					Long assignmentTimeId = TypeConverter.convertToLong(id);
					post.setAssignmentTimeId(assignmentTimeId);
					
					rulesHandler.executeProcessedActions(null,post, dialogueVo,"");
						
				}
			}
			
		} catch (Exception e) {
		
			throw new ServiceException(new ErrorObject(ErrorEnum._90000_ERROR,e.getMessage()));
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.TimePostService#saveTimePost(gov.nwcg.isuite.core.vo.AssignmentTimePostVo, java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveTimePost(AssignmentTimePostVo vo, Collection<Integer> crewAssignmentTimeIds, DialogueVo dialogueVo) throws ServiceException{
		
		try{
			if(null == dialogueVo)dialogueVo=new DialogueVo();

			AssignmentTimeDao assignmentTimeDao = (AssignmentTimeDao)context.getBean("assignmentTimeDao");
			if(!LongUtility.hasValue(vo.getAssignmentTimeId())){
				ErrorObject errorObject = new ErrorObject("info.generic","Cannot post time for resources with an unknown employment type.");
				Collection<ErrorObject> errors = new ArrayList<ErrorObject>();
				errors.add(errorObject);
					
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("ValidationError");
				coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
				coaVo.setIsDialogueEnding(true);
				
				coaVo.setErrorObjectVos(errors);
				dialogueVo.setCourseOfActionVo(coaVo);
				return dialogueVo;
			}
			
			AssignmentTime assignmentTimeEntity = assignmentTimeDao.getById(vo.getAssignmentTimeId(), AssignmentTimeImpl.class);
			if(null == assignmentTimeEntity.getEmploymentType()){
				ErrorObject errorObject = new ErrorObject("info.generic","Cannot post time for resources with an unknown employment type.");
				Collection<ErrorObject> errors = new ArrayList<ErrorObject>();
				errors.add(errorObject);
					
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("ValidationError");
				coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
				coaVo.setIsDialogueEnding(true);
				
				coaVo.setErrorObjectVos(errors);
				dialogueVo.setCourseOfActionVo(coaVo);
				return dialogueVo;
			}
			empType=assignmentTimeEntity.getEmploymentType().name();
			
			//once recorded, a posting's employment type should not be changed, even if the resource's employment type has changed
			if(null == vo.getEmploymentType()) vo.setEmploymentType(assignmentTimeEntity.getEmploymentType());

			if(assignmentTimeEntity.getEmploymentType()==EmploymentTypeEnum.CONTRACTOR){
				/*
				 * Save contractor time post
				 */
				this.saveContractorTimePost(assignmentTimeEntity,vo, dialogueVo);
			}else{
				
				if(CollectionUtility.hasValue(crewAssignmentTimeIds)){
					/*
					 * Save crew time post(s)
					 */
					Collection<Long> crwAssignmentTimeIds = IntegerUtility.convertToLongs(crewAssignmentTimeIds);
					this.saveCrewPersonnelTimePost(crwAssignmentTimeIds, vo, dialogueVo);
					
				}else{
					/*
					 * Save personnel time post
					 */
					this.savePersonnelTimePost(assignmentTimeEntity,vo, dialogueVo);
				}
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	public DialogueVo saveTimePostContractor(String postType, AssignmentTimePostVo primaryVo, AssignmentTimePostVo specialVo , DialogueVo dialogueVo) throws ServiceException{
		
		try{
			if(null == dialogueVo)dialogueVo=new DialogueVo();

			AssignmentTimeDao assignmentTimeDao = (AssignmentTimeDao)context.getBean("assignmentTimeDao");
			AssignmentTime assignmentTimeEntity = null;
			
			EmploymentTypeEnum empType=null;
			
			if(postType.equals("PRIMARY") || postType.equals("GUARANTEE") || postType.equals("BOTH")){
				if(LongUtility.hasValue(primaryVo.getAssignmentTimeId())){
					assignmentTimeEntity = assignmentTimeDao.getById(primaryVo.getAssignmentTimeId(), AssignmentTimeImpl.class);
				}
				// set post start/stop date
				if(StringUtility.hasValue(primaryVo.getPostStartDateString())){
					primaryVo.setPostStartDate(DateUtil.toDate(primaryVo.getPostStartDateString(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
				}
				if(StringUtility.hasValue(primaryVo.getPostStopDateString())){
					primaryVo.setPostStopDate(DateUtil.toDate(primaryVo.getPostStopDateString(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
				}
				if(StringUtility.hasValue(primaryVo.getPostStartTime()) && primaryVo.getPostStartTime().equals("2400")){
					primaryVo.setPostStartTime("2359");
				}
				if(StringUtility.hasValue(primaryVo.getPostStopTime()) && primaryVo.getPostStopTime().equals("2400")){
					primaryVo.setPostStopTime("2359");
				}
				
			}else{
				// special
				if(LongUtility.hasValue(specialVo.getAssignmentTimeId())){
					assignmentTimeEntity = assignmentTimeDao.getById(specialVo.getAssignmentTimeId(), AssignmentTimeImpl.class);
				}
				// set post start/stop date
				if(StringUtility.hasValue(specialVo.getPostStartDateString())){
					specialVo.setPostStartDate(DateUtil.toDate(specialVo.getPostStartDateString(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
				}
				if(StringUtility.hasValue(specialVo.getPostStopDateString())){
					specialVo.setPostStopDate(DateUtil.toDate(specialVo.getPostStopDateString(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
				}
				if(StringUtility.hasValue(specialVo.getPostStartTime()) && specialVo.getPostStartTime().equals("2400")){
					specialVo.setPostStartTime("2359");
				}
				if(StringUtility.hasValue(specialVo.getPostStopTime()) && specialVo.getPostStopTime().equals("2400")){
					specialVo.setPostStopTime("2359");
				}
			}
			
			if(null==assignmentTimeEntity || null == assignmentTimeEntity.getEmploymentType()){
				ErrorObject errorObject = new ErrorObject("info.generic","Cannot post time for resources with an unknown employment type.");
				Collection<ErrorObject> errors = new ArrayList<ErrorObject>();
				errors.add(errorObject);
					
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("ValidationError");
				coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
				coaVo.setIsDialogueEnding(true);
				
				coaVo.setErrorObjectVos(errors);
				dialogueVo.setCourseOfActionVo(coaVo);
				return dialogueVo;
			}else{
				empType=assignmentTimeEntity.getEmploymentType();
			}
			
			//once recorded, a posting's employment type should not be changed, even if the resource's employment type has changed
			if(null == primaryVo.getEmploymentType()) primaryVo.setEmploymentType(empType);
			if(null == specialVo.getEmploymentType()) specialVo.setEmploymentType(empType);

			/*
			 * Save contractor time post
			 */
			TimePostSaveRulesHandler rulesHandler = new TimePostSaveRulesHandler(context);

			AssignmentTimePostVo originalPrimaryVo = primaryVo.clone();
			AssignmentTimePostVo originalSpecialVo = specialVo.clone();
			
			if(rulesHandler.executeContractor(assignmentTimeEntity,primaryVo,specialVo, dialogueVo,empType.name(),postType)==TimePostSaveRulesHandler._OK){

				rulesHandler.executeProcessedActionsContractor(assignmentTimeEntity, primaryVo, specialVo, dialogueVo, empType.name(), postType);

				TimePostDao dao = (TimePostDao)context.getBean("timePostDao");

				/*dantodo
				if(LongUtility.hasValue(vo.getId())){
					// get updated entity
					AssignmentTimePost entity = dao.getById(vo.getId(), AssignmentTimePostImpl.class);
					vo = AssignmentTimePostVo.getInstance(entity, true);
					dialogueVo.setResultObject(vo);
				}else{
					// get last time posting
					AssignmentTimePost entity = dao.getLatestTimePosting(null, vo.getAssignmentTimeId(),true);
					vo = AssignmentTimePostVo.getInstance(entity, true);
					dialogueVo.setResultObject(vo);
				}
				*/
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_CONTRACTOR_TIME_POST");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.time", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);

			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/**
	 * @param assignmentTimeEntity
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	private void savePersonnelTimePost(AssignmentTime assignmentTimeEntity,AssignmentTimePostVo vo, DialogueVo dialogueVo) throws ServiceException {
		
		try{
			TimePostSaveRulesHandler rulesHandler = new TimePostSaveRulesHandler(context);

			// set post start/stop date
			if(StringUtility.hasValue(vo.getPostStartDateString())){
				vo.setPostStartDate(DateUtil.toDate(vo.getPostStartDateString(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
			}
			if(StringUtility.hasValue(vo.getPostStopDateString())){
				vo.setPostStopDate(DateUtil.toDate(vo.getPostStopDateString(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
			}
			if(StringUtility.hasValue(vo.getPostStartTime()) && vo.getPostStartTime().equals("2400")){
				vo.setPostStartTime("2359");
			}
			if(StringUtility.hasValue(vo.getPostStopTime()) && vo.getPostStopTime().equals("2400")){
				vo.setPostStopTime("2359");
			}
			
			
			if(rulesHandler.execute(assignmentTimeEntity,vo, dialogueVo,empType)==TimePostSaveRulesHandler._OK){
				rulesHandler.executeProcessedActions(assignmentTimeEntity,vo, dialogueVo,empType);
				
				TimePostDao dao = (TimePostDao)context.getBean("timePostDao");
				
				if(LongUtility.hasValue(vo.getId())){
					// get updated entity
					AssignmentTimePost entity = dao.getById(vo.getId(), AssignmentTimePostImpl.class);
					vo = AssignmentTimePostVo.getInstance(entity, true);
					dialogueVo.setResultObject(vo);
				}else{
					// get last time posting
					AssignmentTimePost entity = dao.getLatestTimePosting(null, vo.getAssignmentTimeId(),false);
					vo = AssignmentTimePostVo.getInstance(entity, true);
					dialogueVo.setResultObject(vo);
				}
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_TIME_POST");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.time", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);
				
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
	}

	/**
	 * @param crewAssignmentTimeIds
	 * @param vo
	 * @param dialogueVo
	 * @throws ServiceException
	 */
	private void saveCrewPersonnelTimePost(Collection<Long> crewAssignmentTimeIds,AssignmentTimePostVo vo, DialogueVo dialogueVo) throws ServiceException {
		try{
			TimePostSaveCrewRulesHandler rulesHandler = new TimePostSaveCrewRulesHandler(context);

			AssignmentTimeDao atDao = (AssignmentTimeDao)context.getBean("assignmentTimeDao");

			Collection<AssignmentTime> atEntities = atDao.getByIds(crewAssignmentTimeIds);
			
			if(rulesHandler.execute(atEntities,vo, dialogueVo)==AbstractRule._OK){
				
				rulesHandler.executeProcessedActions(atEntities,vo, dialogueVo);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_CREW_POSTINGS");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.time", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);
				
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
	}
	
	/**
	 * @param assignmentTimeEntity
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	private void saveContractorTimePost(AssignmentTime assignmentTimeEntity,AssignmentTimePostVo vo, DialogueVo dialogueVo) throws ServiceException {
		try{
			TimePostSaveRulesHandler rulesHandler = new TimePostSaveRulesHandler(context);

			AssignmentTimePostVo originalVo = vo.clone();
			
			// set post start/stop date
			if(StringUtility.hasValue(vo.getPostStartDateString())){
				vo.setPostStartDate(DateUtil.toDate(vo.getPostStartDateString(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
			}
			if(StringUtility.hasValue(vo.getPostStopDateString())){
				vo.setPostStopDate(DateUtil.toDate(vo.getPostStopDateString(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
			}
			
			if(rulesHandler.execute(assignmentTimeEntity,vo, dialogueVo,empType)==TimePostSaveRulesHandler._OK){
				
				rulesHandler.executeProcessedActions(assignmentTimeEntity,vo, dialogueVo,empType);
				
				TimePostDao dao = (TimePostDao)context.getBean("timePostDao");
				
				if(LongUtility.hasValue(vo.getId())){
					// get updated entity
					AssignmentTimePost entity = dao.getById(vo.getId(), AssignmentTimePostImpl.class);
					vo = AssignmentTimePostVo.getInstance(entity, true);
					dialogueVo.setResultObject(vo);
				}else{
					// get last time posting
					AssignmentTimePost entity = dao.getLatestTimePosting(null, vo.getAssignmentTimeId(),true);
					vo = AssignmentTimePostVo.getInstance(entity, true);
					dialogueVo.setResultObject(vo);
				}
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_CONTRACTOR_TIME_POST");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.time", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);

			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.TimePostService#deleteAssignmentTimePosts(java.util.Collection)
	 */
	public void deleteAssignmentTimePosts(Collection<AssignmentTimePostVo> vos) throws ServiceException{

		try{
			
			ArrayList<AssignmentTimePost> entities = new ArrayList<AssignmentTimePost>();
			
			for(AssignmentTimePostVo vo : vos) {
				AssignmentTimePost entity = AssignmentTimePostVo.toEntity(null, vo, true);
				entities.add(entity);
			}
			
			TimePostDao dao = (TimePostDao)context.getBean("timePostDao");
			
			dao.deleteAll(entities);
			
		}catch(Exception e){
			throw new ServiceException(new ErrorObject(ErrorEnum._90000_ERROR,e.getMessage()));
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.TimePostService#deleteAssignmentTimePosts(java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteAssignmentTimePostsNew(Collection<AssignmentTimePostVo> vos,DialogueVo dialogueVo) throws ServiceException{
		if(null == dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			
			TimePostDao dao = (TimePostDao)context.getBean("timePostDao");
			
			ArrayList<AssignmentTimePost> entities = new ArrayList<AssignmentTimePost>();
			
			for(AssignmentTimePostVo vo : vos) {
				AssignmentTimePost entity = AssignmentTimePostVo.toEntity(null, vo, true);
				entities.add(entity);
			}

			/*
			 * Run each entity through the rules.
			 */
			for(AssignmentTimePost entity : entities){
				TimePostDeleteRulesHandler ruleHandler = new TimePostDeleteRulesHandler(context);
				if(ruleHandler.execute(entity, dialogueVo)==AbstractRuleHandler._OK){
					ruleHandler.executeProcessedActions(entity, dialogueVo);
				}else
					return dialogueVo;
			}
			
			for(AssignmentTimePost atp : entities){
				// remove any time invoice records
				dao.deleteInvoicedRecords(atp.getId());
				
			}
			
			//reload them
			entities = new ArrayList<AssignmentTimePost>();
			
			for(AssignmentTimePostVo vo : vos) {
				AssignmentTimePost entity = AssignmentTimePostVo.toEntity(null, vo, true);
				Long assignmentTimeId = dao.getAssignmentTimeId(entity.getId());
				entity.setAssignmentTimeId(assignmentTimeId);
				entities.add(entity);
			}
			
			dao.deleteAll(entities);

			// go through rules and execute postprocessingactions
			for(AssignmentTimePost entity : entities){
				DialogueVo dvo2 = new DialogueVo();
				TimePostDeleteRulesHandler ruleHandler = new TimePostDeleteRulesHandler(context);
				if(ruleHandler.execute(entity, dvo2)==AbstractRuleHandler._OK)
					ruleHandler.executeProcessedActions(entity, dvo2);
			}
			
			/*
			 * Build and set the affirmDeleteIncidentGroup message
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
		    coaVo.setCoaName("DELETE_TIME_POST");
		    coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
		    coaVo.setMessageVo(
					   new MessageVo(
							   "text.time", 
							   "text.affirmDeleteTimePosts" , null, 
							   MessageTypeEnum.INFO));

			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
			throw new ServiceException(new ErrorObject(ErrorEnum._90000_ERROR,e.getMessage()));
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.TimePostService#deleteAssignmentTimePostsCrew(java.util.Collection, java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteAssignmentTimePostsCrew(Collection<IncidentResourceVo> irVos, Collection<AssignmentTimePostVo> vos,DialogueVo dialogueVo) throws ServiceException{
		if(null == dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			
			TimePostDao dao = (TimePostDao)context.getBean("timePostDao");
			
			Collection<AssignmentTimePost> entities = new ArrayList<AssignmentTimePost>();
			Collection<AssignmentTimePostVo> vosToDelete=new ArrayList<AssignmentTimePostVo>();
			
			Collection<Long> irIds = new ArrayList<Long>();
			for(IncidentResourceVo irVo : irVos){
				irIds.add(irVo.getId());
			}

			// get timeposts to delete
			for(AssignmentTimePostVo vo : vos) {
				AssignmentTimePost entity = AssignmentTimePostVo.toEntity(null, vo, true);
				entities.add(entity);
				vosToDelete.add(vo);
				
				TimePostQueryFilterImpl f = new TimePostQueryFilterImpl();
				f.setStartDate(vo.getPostStartDate());
				f.setStopDate(vo.getPostStopDate());
				//if(null != vo.getSpecialPayVo())
				//	f.setSpecialPay(vo.getSpecialPayVo().getCode());

				f.setExcludeAssignmentTimePostId(vo.getId());
				
				// get all timeposts for irVos where time post info matches
				// based on activity date, start time, stop time  - see Defect #3003
				//Collection<AssignmentTimePost> atps = dao.getCrewTimePostings(irIds, vo.getPostStartDate(), vo.getPostStopDate(), vo.getId());
				Collection<AssignmentTimePost> atps = dao.getCrewTimePostings(irIds, f);
				
				for(AssignmentTimePost atp : atps){
					entities.add(atp);
					vosToDelete.add(AssignmentTimePostVo.getInstance(atp, true));
					dao.flushAndEvict(atp);
				}
			}

			// build list of resources for the time postings being deleted
			Collection<IncidentResourceVo> irVosTimePostDeleted = new ArrayList<IncidentResourceVo>();
			for(IncidentResourceVo irvo : irVos){
				for(AssignmentTimePostVo v : vosToDelete){
					if(v.getAssignmentTimeId().compareTo(irvo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getId())==0){
						irVosTimePostDeleted.add(irvo);
					}
				}
			}
			
			/*
			 * Run each entity through the rules.
			 */
			for(AssignmentTimePost entity : entities){
				TimePostDeleteRulesHandler ruleHandler = new TimePostDeleteRulesHandler(context);
				if(ruleHandler.execute(entity, dialogueVo)==AbstractRuleHandler._OK){
					ruleHandler.executeProcessedActions(entity, dialogueVo);
				}else
					return dialogueVo;
			}
			
			for(AssignmentTimePost atp : entities){
				// remove any time invoice records
				dao.deleteInvoicedRecords(atp.getId());
			}
			
			//reload them
			entities = new ArrayList<AssignmentTimePost>();
			
			for(AssignmentTimePostVo vo : vosToDelete) {
				AssignmentTimePost entity = AssignmentTimePostVo.toEntity(null, vo, true);
				Long assignmentTimeId = dao.getAssignmentTimeId(entity.getId());
				entity.setAssignmentTimeId(assignmentTimeId);
				entities.add(entity);
			}
			
			dao.deleteAll(entities);

			// go through rules and execute postprocessingactions
			for(AssignmentTimePost entity : entities){
				DialogueVo dvo2 = new DialogueVo();
				TimePostDeleteRulesHandler ruleHandler = new TimePostDeleteRulesHandler(context);
				if(ruleHandler.execute(entity, dvo2)==AbstractRuleHandler._OK)
					ruleHandler.executeProcessedActions(entity, dvo2);
			}
			
			/*
			 * Build and set the affirmDeleteIncidentGroup message
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
		    coaVo.setCoaName("DELETE_TIME_POST_CREW");
		    coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
		    coaVo.setMessageVo(
					   new MessageVo(
							   "text.time", 
							   "text.affirmDeleteTimePosts" , null, 
							   MessageTypeEnum.INFO));

			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(vosToDelete);
			dialogueVo.setResultObjectAlternate(irVosTimePostDeleted);
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
			throw new ServiceException(new ErrorObject(ErrorEnum._90000_ERROR,e.getMessage()));
		}
		
		return dialogueVo;
	}

}
