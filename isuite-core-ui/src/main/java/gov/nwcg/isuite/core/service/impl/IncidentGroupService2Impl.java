package gov.nwcg.isuite.core.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import gov.nwcg.isuite.core.controllers.restdata.DropdownData;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentGroupPrefs;
import gov.nwcg.isuite.core.domain.IncidentGroupQSKind;
import gov.nwcg.isuite.core.domain.IncidentGroupQuestion;
import gov.nwcg.isuite.core.domain.IncidentPrefsOtherFields;
import gov.nwcg.isuite.core.domain.UserGroup;
import gov.nwcg.isuite.core.domain.UserGroupUser;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentPrefsOtherFieldsImpl;
import gov.nwcg.isuite.core.domain.impl.UserGroupImpl;
import gov.nwcg.isuite.core.filter.IncidentGroupFilter;
import gov.nwcg.isuite.core.persistence.ContractorDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupQuestionDao;
import gov.nwcg.isuite.core.persistence.QuestionDao;
import gov.nwcg.isuite.core.persistence.UserGroupDao;
import gov.nwcg.isuite.core.reports.IncGroupConflictReport;
import gov.nwcg.isuite.core.reports.data.IncGroupConflictReportData;
import gov.nwcg.isuite.core.rules.AddIncidentsCheckRulesHandler;
import gov.nwcg.isuite.core.rules.IncidentGroupAddIncidentRulesHandler;
import gov.nwcg.isuite.core.rules.IncidentGroupAddUsersRulesHandler;
import gov.nwcg.isuite.core.rules.IncidentGroupDeleteRulesHandler;
import gov.nwcg.isuite.core.rules.IncidentGroupSavePrefsRulesHandler;
import gov.nwcg.isuite.core.rules.IncidentGroupSaveQSKindsRulesHandler;
import gov.nwcg.isuite.core.rules.IncidentGroupSaveQuestionsRulesHandler;
import gov.nwcg.isuite.core.rules.IncidentGroupSaveRulesHandler;
import gov.nwcg.isuite.core.rules.IncidentSyncSupplimentalRulesHandler;
import gov.nwcg.isuite.core.rules.IsLockedRulesHandler;
import gov.nwcg.isuite.core.service.IncidentGroupService2;
import gov.nwcg.isuite.core.service.IncidentSelectorService;
import gov.nwcg.isuite.core.vo.CostSettingsVo;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.IncidentGroupConflictVo;
import gov.nwcg.isuite.core.vo.IncidentGroupGridVo;
import gov.nwcg.isuite.core.vo.IncidentGroupPrefsVo;
import gov.nwcg.isuite.core.vo.IncidentGroupQSKindVo;
import gov.nwcg.isuite.core.vo.IncidentGroupQuestionVo;
import gov.nwcg.isuite.core.vo.IncidentGroupVo;
import gov.nwcg.isuite.core.vo.IncidentPrefsOtherFieldsVo;
import gov.nwcg.isuite.core.vo.IncidentSelector2Vo;
import gov.nwcg.isuite.core.vo.IncidentSupplimentalDataVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.QuestionVo;
import gov.nwcg.isuite.core.vo.RestrictedIncidentUserVo;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.core.vo.UserGroupVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.service.BaseReportService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.report.ReportBuilder2;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.QuestionTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.VoValidator;

public class IncidentGroupService2Impl extends BaseReportService implements IncidentGroupService2 {
	private IncidentGroupDao incidentGroupDao=null;

	public IncidentGroupService2Impl(){
		super();
	}

	public void initialization(){
		incidentGroupDao = (IncidentGroupDao)super.context.getBean("incidentGroupDao");
	}

	public DialogueVo getIncidentDropdownList(Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();		
		
		try{
			Collection<DropdownData> list = new ArrayList<DropdownData>();

			Collection<IncidentGroupGridVo> vos = new ArrayList<IncidentGroupGridVo>();

			IncidentGroup entity = incidentGroupDao.getById(incidentGroupId);
			if( null != entity && CollectionUtility.hasValue(entity.getIncidents())){
				list = DropdownData.convertFromIncidents(entity.getIncidents());
			}

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_INCIDENT_DROPDOWN_LIST");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);

			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(list);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentGroup2Service#getGrid(gov.nwcg.isuite.core.filter.IncidentGroupFilter, java.lang.Boolean, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getGrid(IncidentGroupFilter filter, DialogueVo dialogueVo) throws ServiceException {

		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try{

			Collection<IncidentGroupGridVo> vos = new ArrayList<IncidentGroupGridVo>();

			vos = incidentGroupDao.getGrid(filter);

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_INCIDENT_GROUP_GRID");
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
	 * @see gov.nwcg.isuite.core.service.IncidentGroupService2#deleteIncidentGroup(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteIncidentGroup(Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try{
			if(!LongUtility.hasValue(incidentGroupId))
				super.handleException(ErrorEnum._90000_ERROR,"incidentGroupId is required to delete an incident group.");

			IncidentGroup entity = incidentGroupDao.getById(incidentGroupId, IncidentGroupImpl.class);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"IncidentGroup["+incidentGroupId+"]");

			IncidentGroupDeleteRulesHandler ruleHandler = new IncidentGroupDeleteRulesHandler(context);
			if(ruleHandler.execute(incidentGroupId, entity, dialogueVo)==AbstractRuleHandler._OK){
				
//				Collection<Long> incidentIds = new ArrayList<Long>();
//				for(Incident inc : entity.getIncidents()){
//					incidentIds.add(inc.getId());
//				}
				incidentGroupDao.preDeleteIncidentGroup(incidentGroupId);
				
				incidentGroupDao.delete(entity);
				incidentGroupDao.flushAndEvict(entity);

				// delete from custom excluded view
				incidentGroupDao.deleteFromUserCustomView(incidentGroupId);
				
//				IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
//				Collection<Incident> incidents = incidentDao.getAllByIds(incidentIds);
//				Collection<IncidentVo> incidentVos = IncidentVo.getInstances(incidents, true);
				
				/*
				 * Build and set the affirmDeleteIncidentGroup message
				 */
				CourseOfActionVo coaVo = new CourseOfActionVo();
			    coaVo.setCoaName("DELETE IG COMPLETE");
			    coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setIsDialogueEnding(Boolean.TRUE);
			    coaVo.setMessageVo(
						   new MessageVo(
								   "text.incidentGroup", 
								   "text.affirmDeleteIncidentGroup" , new String[]{entity.getGroupName()}, 
								   MessageTypeEnum.INFO));

				dialogueVo.setCourseOfActionVo(coaVo);
//				dialogueVo.setResultObject(incidentVos);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	public DialogueVo getAvailableIncidents(Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		Long loggedInUserId = null;
		
		/*
		 * If the user has a Data Steward role, 
		 * only retrieve restricted Incidents to which they have access.
		 */
		if(!getUserSessionVo().getPrivilegedUser()) 
			loggedInUserId = getUserSessionVo().getUserId();
		
		try{
			Collection<IncidentGridVo> vos = 
				incidentGroupDao.getAvailableIncidents(incidentGroupId, loggedInUserId, getUserSessionVo().getPrivilegedUser());

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_IG_AVAILABLE_INCIDENTS");
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
	 * @see gov.nwcg.isuite.core.service.IncidentGroupService2#getAvailableIncidents(java.lang.Long, java.lang.Long, gov.nwcg.isuite.core.filter.IncidentFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
//	public DialogueVo getAvailableIncidents(Long incidentGroupId, Long workAreaId, IncidentFilter filter, DialogueVo dialogueVo) throws ServiceException {
//		if(null==dialogueVo)dialogueVo = new DialogueVo();
//
//		Long loggedInUserId = null;
//		
//		/*
//		 * If the user has a Data Steward role, 
//		 * only retrieve restricted Incidents to which they have access.
//		 */
//		if(!getUserSessionVo().getPrivilegedUser()) 
//			loggedInUserId = getUserSessionVo().getUserId();
//		
//		try{
//
//			Collection<IncidentGridVo> vos = 
//				incidentGroupDao.getAvailableIncidents(incidentGroupId
//														, workAreaId
//														, filter
//														, loggedInUserId
//														, getUserSessionVo().getPrivilegedUser());
//
//			CourseOfActionVo coaVo = new CourseOfActionVo();
//			coaVo.setCoaName("GET_IG_AVAILABLE_INCIDENTS");
//			coaVo.setIsDialogueEnding(Boolean.TRUE);
//			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
//			dialogueVo.setCourseOfActionVo(coaVo);
//			dialogueVo.setRecordset(vos);
//			
//		}catch(Exception e){
//			super.dialogueException(dialogueVo, e);
//		}
//
//		return dialogueVo;
//	}

	public DialogueVo getAvailableUserGroups(DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try{

			//Collection<UserGroupGridVo> vos = incidentGroupDao.getAvailableUserGroups();
			UserGroupDao ugDao = (UserGroupDao)super.context.getBean("userGroupDao");
			
			Collection<UserGroup> entities = ugDao.getUserGroups(super.getUserVo().getId());
			Collection<UserGroupVo> vos = UserGroupVo.getInstances(entities, true);

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_IG_AVAILABLE_USER_GROUPS");
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
	 * @see gov.nwcg.isuite.core.service.IncidentGroupService2#getAvailableUserGroups(gov.nwcg.isuite.core.filter.UserGroupFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
//	public DialogueVo getAvailableUserGroups(UserGroupFilter filter, DialogueVo dialogueVo) throws ServiceException {
//		if(null==dialogueVo)dialogueVo = new DialogueVo();
//
//		try{
//
//			Collection<UserGroupGridVo> vos = incidentGroupDao.getAvailableUserGroups(filter);
//
//			CourseOfActionVo coaVo = new CourseOfActionVo();
//			coaVo.setCoaName("GET_IG_AVAILABLE_USER_GROUPS");
//			coaVo.setIsDialogueEnding(Boolean.TRUE);
//			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
//			dialogueVo.setCourseOfActionVo(coaVo);
//			dialogueVo.setRecordset(vos);
//				
//		}catch(Exception e){
//			super.dialogueException(dialogueVo, e);
//		}
//
//		return dialogueVo;
//	}

	public DialogueVo getUserGroupUsers(ArrayList<Integer> incidentGroupIds, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();
		
		Set<UserVo> users = new HashSet<UserVo>();
		
		try{
			/*
			 * Validate the id's
			 */
			if(incidentGroupIds.size() == 0)
				super.handleException(ErrorEnum._90000_ERROR,"incidentGroupIds are required to get user group users to add to incident group.");

			/*
			 * Get the userGroup Entity
			 */
			UserGroupDao userGroupDao = (UserGroupDao)context.getBean("userGroupDao");
			
			for(Integer id : incidentGroupIds) {
				UserGroup userGroupEntity = userGroupDao.getById(id.longValue(), UserGroupImpl.class);
				
				if(null == userGroupEntity) 
					super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"UserGroup["+id+"]");
				
				for(UserGroupUser ugu : userGroupEntity.getUserGroupUsers()) {
					users.add(UserVo.getInstance(ugu.getUser(), true));
				}
			}
		    
		} catch (Exception e) {
			super.handleException(e);
		}
		
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName("GET_USER_GROUP_USERS");
		coaVo.setIsDialogueEnding(Boolean.TRUE);
		coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dialogueVo.setRecordset(users);
		dialogueVo.setCourseOfActionVo(coaVo);
			
		return dialogueVo;
	}
	
	public DialogueVo getAvailableUsers(DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try{
			Collection<UserGridVo> vos = incidentGroupDao.getAvailableUsers();

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_IG_AVAILABLE_USERS");
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
	 * @see gov.nwcg.isuite.core.service.IncidentGroupService2#getAvailableUsers(java.lang.Long, java.lang.Long, gov.nwcg.isuite.core.filter.UserFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
//	public DialogueVo getAvailableUsers(Long groupId, Long workAreaId, UserFilter filter, DialogueVo dialogueVo) throws ServiceException {
//		if(null==dialogueVo)dialogueVo = new DialogueVo();
//
//		try{
//			if(!LongUtility.hasValue(groupId))
//				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentGroupId is required to retrieve available users.");
////			if( !LongUtility.hasValue(workAreaId))
////				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"workAreaId is required to retrieve available users.");
//			
//			if(filter == null) {
//				filter = new UserFilterImpl();
//			}
//			
//			Collection<UserGridVo> vos = incidentGroupDao.getAvailableUsers(workAreaId,groupId, filter);
//
//			CourseOfActionVo coaVo = new CourseOfActionVo();
//			coaVo.setCoaName("GET_IG_AVAILABLE_USERS");
//			coaVo.setIsDialogueEnding(Boolean.TRUE);
//			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
//			dialogueVo.setCourseOfActionVo(coaVo);
//			dialogueVo.setRecordset(vos);
//			
//		}catch(Exception e){
//			super.dialogueException(dialogueVo, e);
//		}
//
//		return dialogueVo;
//	}

	public DialogueVo getDefaultQuestions(DialogueVo dialogueVo) {
		if(dialogueVo == null)
			dialogueVo = new DialogueVo();
		
		try{
			QuestionDao questionDao = (QuestionDao)super.context.getBean("questionDao");
			Collection<QuestionVo> vos = questionDao.getDefaultQuestions();
			Collection<IncidentGroupQuestionVo> igVos = new ArrayList<IncidentGroupQuestionVo>();
			
			for(QuestionVo vo : vos) {
				IncidentGroupQuestionVo iqvo = new IncidentGroupQuestionVo();
				iqvo.setQuestionVo(vo);
				iqvo.setVisible(true);
				igVos.add(iqvo);
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_DEFAULT_QUESTIONS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(igVos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentGroupService2#getById(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getById(Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try{
			if(!LongUtility.hasValue(incidentGroupId)) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Incident Group with null id");

			/*
			 * clean up duplicate resource questions
			 */
			incidentGroupDao.cleanUpDuplicateQuestionIssue(incidentGroupId);
			
			IncidentGroup entity = incidentGroupDao.getById(incidentGroupId, IncidentGroupImpl.class);
			
			if(null==entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"IncidentGroup[id="+incidentGroupId+"]");
			
			IncidentGroupVo vo = IncidentGroupVo.getInstance(entity, true);
			
			// load supplimental data
			IncidentSupplimentalDataVo isdVo = new IncidentSupplimentalDataVo();
			// get contractor list
			ContractorDao contractorDao = (ContractorDao)context.getBean("contractorDao");
			
// dan 8/13/2019			isdVo.setContractorVos(contractorDao.getLightList(null, vo.getId()));
			dialogueVo.setResultObjectAlternate4(isdVo);
			
			// set default incident checkin setting
			for (IncidentVo incVo : vo.getIncidentVos()){
				for (RestrictedIncidentUserVo riuVo : incVo.getRestrictedIncidentUserVos()){
					if(riuVo.getUserVo().getId().compareTo(super.getUserVo().getId())==0){
						incVo.setUserDefaultCheckinDate(riuVo.getDefaultCheckinDate());
						incVo.setUserDefaultCheckinType(riuVo.getDefaultCheckinType());
					}
				}
			}

			/*
			 * Sync any incident supplimental information
			 */
			try{
				if(CollectionUtility.hasValue(entity.getIncidents())){
					for(Incident incident : entity.getIncidents()){
						if(!DateUtil.hasValue(incident.getIncidentEndDate())){
							IncidentSyncSupplimentalRulesHandler syncRuleHandler = new IncidentSyncSupplimentalRulesHandler(super.context);
							syncRuleHandler.executeProcessedActions(incident, dialogueVo);
						}
							
						incidentGroupDao.flushAndEvict(incident);
					}
				}
			}catch(Exception eee){
				
			}
			
			
			/*
			 * Build course of action for HANDLE_RECORDSET and set resultObject
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_INCIDENT_GROUP_BY_ID");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(vo);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	public DialogueVo save(IncidentGroupVo newVo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) 
			dialogueVo = new DialogueVo();
		
		IncidentGroupVo originalVo = null;
		
		try {
			IncidentGroup entity = null;
			
			if(VoValidator.isValidAbstractVo(newVo))
				entity=incidentGroupDao.getById(newVo.getId(),IncidentGroupImpl.class);
			
			if(entity != null) {
				originalVo = IncidentGroupVo.getInstance(entity, true);
				//incidentGroupDao.flushAndEvict(entity);
			}
			
			Boolean isNew=(null==entity ? true : false);

			if(isNew==false){
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(entity.getId(), "INCIDENTGROUP", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			
			/*
			 * Run the save incident group rules 
			 */
			IncidentGroupSaveRulesHandler rulesHandler = new IncidentGroupSaveRulesHandler(context);
			if(rulesHandler.execute(originalVo, newVo, entity,dialogueVo)==IncidentGroupSaveRulesHandler._OK){
				
				// incidents are not getting removed automatically
				// so get the removeList prior to persisting
				Collection<Incident> removeList = new ArrayList<Incident>();

				if(null != entity){
					removeList=IncidentVo.toEntityRemoveList(newVo.getIncidentVos(),entity.getIncidents());
				}
				
				entity = IncidentGroupVo.toEntity(entity, newVo, true );
				
				incidentGroupDao.save(entity);
				incidentGroupDao.flushAndEvict(entity);
				
				if(CollectionUtility.hasValue(removeList)){
					for(Incident i : removeList){
						incidentGroupDao.removeIncidentFromGroup(i.getId());
					}
				}

				
				if(super.getRunMode().equals("ENTERPRISE")){
					// add any incident users to incident group user list if not already added
					incidentGroupDao.addIncidentUsers(entity.getId());
					
					if(isNew==true){
						// if new incident group, delete incident group settings
						// and rebuild them based on primary incident's settings
						incidentGroupDao.createDefaultGroupSettings(entity.getId(), newVo.getPrimaryIncidentId());
						
						IncidentGroupQuestionDao qdao = (IncidentGroupQuestionDao)context.getBean("incidentGroupQuestionDao");
						qdao.createDefaultGroupQuestions(entity.getId(), newVo.getPrimaryIncidentId());
					}
				}


				rulesHandler.executeProcessedActions(originalVo, newVo, entity, dialogueVo);
				
				entity = incidentGroupDao.getById(entity.getId(), IncidentGroupImpl.class);
				
				newVo = IncidentGroupVo.getInstance(entity, true);

				CourseOfActionVo coa = new CourseOfActionVo();
				coa.setCoaName("SAVE IG COMPLETE");
				coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coa.setIsDialogueEnding(true);
				
				MessageVo msgVo = new MessageVo(
						   "text.incidentGroup", 
						   "info.0030" , null, 
						   MessageTypeEnum.INFO);
				coa.setMessageVo(msgVo);
				
				dialogueVo.setCourseOfActionVo(coa);
				dialogueVo.setResultObject(newVo);

				// get update grid item
				IncidentSelectorService iss = (IncidentSelectorService)context.getBean("incidentSelectorService");
				iss.setIncidentGroupId(entity.getId());
				DialogueVo dvo2 = iss.getGrid(super.getUserSessionVo().getUserId(), null);
				if(CollectionUtility.hasValue(dvo2.getRecordset())){
					IncidentSelector2Vo is2Vo = (IncidentSelector2Vo)dvo2.getRecordset().iterator().next();	
					dialogueVo.setResultObjectAlternate(is2Vo);
				}

				// get list of grid incidents only
				iss.setIncidentGroupId(0L);
				iss.setIncidentsOnly(true);
				dvo2 = iss.getGrid(super.getUserSessionVo().getUserId(), null);
				if(CollectionUtility.hasValue(dvo2.getRecordset())){
					dialogueVo.setResultObjectAlternate2(dvo2.getRecordset());
				}
				
			}
		} catch (Exception e) {
			super.handleException(e);
		}
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentGroupService2#save(gov.nwcg.isuite.core.vo.IncidentGroupVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo save_alt(IncidentGroupVo newVo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) 
			dialogueVo = new DialogueVo();
		
		IncidentGroupVo originalVo = null;
		
		try {
			IncidentGroup entity = null;
			
			if(VoValidator.isValidAbstractVo(newVo))
				entity=incidentGroupDao.getById(newVo.getId(),IncidentGroupImpl.class);
			
			if(entity != null) {
				originalVo = IncidentGroupVo.getInstance(entity, true);
				//incidentGroupDao.flushAndEvict(entity);
			}
			
			/*
			 * Run the save incident group rules 
			 */
			IncidentGroupSaveRulesHandler rulesHandler = new IncidentGroupSaveRulesHandler(context);
			if(rulesHandler.execute(originalVo, newVo, entity, dialogueVo)==IncidentGroupSaveRulesHandler._OK){
				/*
				 * Run the add incidents rules 
				 */
				IncidentGroupAddIncidentRulesHandler addIncidentsRulesHandler = new IncidentGroupAddIncidentRulesHandler(super.context);
				if(addIncidentsRulesHandler.execute(newVo, originalVo, dialogueVo)==AbstractRuleHandler._OK) {
					
					/*
					 * Run the add users rules 
					 */
					IncidentGroupAddUsersRulesHandler addUserRuleHandler = new IncidentGroupAddUsersRulesHandler(context);
					if(addUserRuleHandler.execute(newVo, originalVo, dialogueVo)==IncidentGroupAddUsersRulesHandler._OK){
						// incidents are not getting removed automatically
						// so get the removeList prior to persisting
						Collection<Incident> removeList = new ArrayList<Incident>();
						if(null != entity){
							removeList=IncidentVo.toEntityRemoveList(newVo.getIncidentVos(),entity.getIncidents());
						}
						
						//save entity
						entity = IncidentGroupVo.toEntity(entity, newVo, true );
												
						incidentGroupDao.save(entity);
						if(newVo.getId() == null || newVo.getId() == 0) {
							newVo.setId(entity.getId());
						}
						
						incidentGroupDao.flushAndEvict(entity);
						if(CollectionUtility.hasValue(removeList)){
							for(Incident i : removeList){
								incidentGroupDao.removeIncidentFromGroup(i.getId());
							}
						}
						
						newVo = IncidentGroupVo.getInstance(incidentGroupDao.getById(newVo.getId(), IncidentGroupImpl.class),true);
						
						CourseOfActionVo coa = new CourseOfActionVo();
						coa.setCoaName("SAVE IG COMPLETE");
						coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
						coa.setIsDialogueEnding(true);
						
						MessageVo msgVo = new MessageVo(
								   "text.incidentGroup", 
								   "info.itemDataSaved" , new String[] {newVo.getGroupName() + " Incident Group"}, 
								   MessageTypeEnum.INFO);
						coa.setMessageVo(msgVo);
						
						dialogueVo.setCourseOfActionVo(coa);
						
						addIncidentsRulesHandler.executeProcessedActions(newVo, originalVo, dialogueVo);
						addUserRuleHandler.executeProcessedActions(newVo, originalVo, dialogueVo);
						//rulesHandler.executeProcessedActions(newVo, originalVo, entity, dialogueVo);
						
						/*
						 * handle all additional messages
						 */			
						addUserRuleHandler.addAdditionalMessages(dialogueVo);
						addIncidentsRulesHandler.addAdditionalMessages(dialogueVo);
						rulesHandler.addAdditionalMessages(dialogueVo);
						
						dialogueVo.setResultObject(IncidentGroupVo.getInstance(
								incidentGroupDao.getById(newVo.getId(),IncidentGroupImpl.class), true));
						
						// get updated grid item
						IncidentSelectorService iss = (IncidentSelectorService)context.getBean("incidentSelectorService");
						iss.setIncidentGroupId(entity.getId());
						DialogueVo dvo2 = iss.getGrid(super.getUserSessionVo().getUserId(), null);
						if(CollectionUtility.hasValue(dvo2.getRecordset())){
							IncidentSelector2Vo is2Vo = (IncidentSelector2Vo)dvo2.getRecordset().iterator().next();	
							dialogueVo.setResultObjectAlternate(is2Vo);
						}
						
						// get list of grid incidents only
						iss.setIncidentGroupId(0L);
						iss.setIncidentsOnly(true);
						dvo2 = iss.getGrid(super.getUserSessionVo().getUserId(), null);
						if(CollectionUtility.hasValue(dvo2.getRecordset())){
							dialogueVo.setResultObjectAlternate2(dvo2.getRecordset());
						}
					}
				}
			}
		} catch (Exception e) {
			super.handleException(e);
		}
		return dialogueVo;
	}

	public DialogueVo getQSKinds(Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();

		try{
			if(!LongUtility.hasValue(incidentGroupId)) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Incident Group with null id");
			
			Collection<IncidentGroupQSKindVo> vos = new ArrayList<IncidentGroupQSKindVo>();
			
			IncidentGroup entity = this.incidentGroupDao.getById(incidentGroupId);
			Collection<KindVo> kindVos = new ArrayList<KindVo>();
			if (null != entity ) {
				vos = IncidentGroupQSKindVo.getInstances(entity.getIncidentGroupQSKinds(), true);
				for(IncidentGroupQSKindVo v : vos ) {
					kindVos.add(v.getKindVo());
				}
			}

			CourseOfActionVo coaVoRecordset = new CourseOfActionVo();
			coaVoRecordset.setCoaName("GET_INCIDENT_GROUP_QS_KINDS");
			coaVoRecordset.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVoRecordset.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVoRecordset);
			
			dialogueVo.setRecordset(kindVos);				
			dialogueVo.setResultObjectAlternate(super.getGlobalCacheVo().getKindVos());
		} catch (Exception e) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentGroupService2#saveQSKinds(java.lang.Long, java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveQSKinds(Long groupId, Collection<KindVo> kindVos, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo=new DialogueVo();
		
		try{
			IncidentGroupSaveQSKindsRulesHandler rulesHandler = new IncidentGroupSaveQSKindsRulesHandler(context);
			
			IncidentGroup entity = incidentGroupDao.getById(groupId, IncidentGroupImpl.class);
			
			if(rulesHandler.execute(entity, kindVos, dialogueVo)==IncidentGroupSaveQSKindsRulesHandler._OK){
				/*
				 * Get list of IncidentGroupQSKinds to remove and add
				 */
				Collection<IncidentGroupQSKind> removeList = IncidentGroupQSKindVo.toRemoveList(kindVos,entity.getIncidentGroupQSKinds());
				Collection<IncidentGroupQSKind> addList = IncidentGroupQSKindVo.toAddList(kindVos,entity.getIncidentGroupQSKinds(),entity);

				if(CollectionUtility.hasValue(removeList))
					entity.getIncidentGroupQSKinds().removeAll(removeList);

				if(CollectionUtility.hasValue(addList))
					entity.getIncidentGroupQSKinds().addAll(addList);

				incidentGroupDao.save(entity);
				
				incidentGroupDao.flushAndEvict(entity);
				
				entity = incidentGroupDao.getById(groupId, IncidentGroupImpl.class);
				IncidentGroupVo vo = IncidentGroupVo.getInstance(entity, true);

				/*
				 * Build course of action for 
				 */
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_IG_QS_KINDS");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.incidentGroup", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coaVo);
				
				dialogueVo.setResultObject(vo); 
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo getIncidentGroupCheckOutQuestions(Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {
		try {
			if(dialogueVo == null) {
				dialogueVo = new DialogueVo();
			}

			if(!LongUtility.hasValue(incidentGroupId))
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentGroupId in IncidentGroupPrefsVo is required.");

			IncidentGroup entity = this.incidentGroupDao.getById(incidentGroupId);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"unable to retrieve incident group with id " + incidentGroupId + ".");

			Collection<IncidentGroupPrefsVo> vos = IncidentGroupPrefsVo.getInstances(entity.getIncidentGroupPrefs(), true);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_INCIDENT_GROUP_PREFS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
			
		} catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentGroupService2#saveIncidentGroupCheckOutQuestions(java.lang.Long, java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIncidentGroupCheckOutQuestions(Long incidentGroupId, Collection<IncidentGroupPrefsVo> vos, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) 
			dialogueVo=new DialogueVo();
		
		try{
			IncidentGroupSavePrefsRulesHandler rulesHandler = new IncidentGroupSavePrefsRulesHandler(context);
			
			IncidentGroup entity = incidentGroupDao.getById(incidentGroupId, IncidentGroupImpl.class);
			
			if(rulesHandler.execute(entity, vos, dialogueVo)==IncidentGroupSavePrefsRulesHandler._OK){
				Collection<IncidentGroupPrefs> prefs = new ArrayList<IncidentGroupPrefs>();
				
				for(IncidentGroupPrefsVo vo : vos){
					/*
					 * Add IncidentGroupQuestion to collection
					 */
					prefs.add(IncidentGroupPrefsVo.toEntity(null, vo, true));
				}
				
				entity.setIncidentGroupPrefs(prefs);
					
				incidentGroupDao.flushAndEvict(entity);

				// for each incident in group
				// propagate the group values to each incident
				// we want to sync all group/incident prefs to be the same
				incidentGroupDao.propagatePrefs(entity.getId());
				
				
				entity = incidentGroupDao.getById(incidentGroupId, IncidentGroupImpl.class);
				IncidentGroupVo igv = IncidentGroupVo.getInstance(entity, true);
				
				/*
				 * Build course of action for 
				 */
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_INCIDENT_GROUP_CHECKOUT_QUESTIONS");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.incidentGroup", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(false);
				
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(igv);
			}
				
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo getIncidentGroupAirTravelQuestions(Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();

		try {
			/*
			 * clean up duplicate resource questions
			 */
			incidentGroupDao.cleanUpDuplicateQuestionIssue(incidentGroupId);
			
			/*
			 * Query for the entity
			 */
			IncidentGroup entity = incidentGroupDao.getById(incidentGroupId);


			Collection<IncidentGroupQuestionVo> vos = new ArrayList<IncidentGroupQuestionVo>();
			
			if ( null != entity) {
				for(IncidentGroupQuestion iq : entity.getIncidentGroupQuestions()){
					if(iq.getQuestion().getQuestionType() == QuestionTypeEnum.AIRTRAVEL ) {
						vos.add(IncidentGroupQuestionVo.getInstance(iq, true));
					}
				}
			}

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_INCIDENT_GROUP_AIR_TRAVEL_QUESTIONS");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setIsDialogueEnding(true);

			dialogueVo.setRecordset(vos);
			dialogueVo.setCourseOfActionVo(coaVo);
		} catch(Exception e) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentGroupService2#saveIncidentGroupQuestions(java.lang.Long, java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIncidentGroupQuestions(Long incidentGroupId,Collection<IncidentGroupQuestionVo> vos, String questionType, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo=new DialogueVo();
		
		try{
			if(!LongUtility.hasValue(incidentGroupId)) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"IncidentGroup with null id");
			
			IncidentGroup entity = incidentGroupDao.getById(incidentGroupId, IncidentGroupImpl.class);
			if(null==entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"IncidentGroup[id="+incidentGroupId+"]");
			
			/*
			 * Run rule checks
			 */
			IncidentGroupSaveQuestionsRulesHandler ruleHandler = new IncidentGroupSaveQuestionsRulesHandler(context);
			if(ruleHandler.execute(entity,vos,dialogueVo)==AbstractRuleHandler._OK){

				// get list of removed questions
				IncidentGroupVo entityVo = IncidentGroupVo.getInstance(entity, true);
				
				Collection<IncidentGroupQuestionVo> originalQuestions;
				if(questionType.equalsIgnoreCase("AirTravel"))
					originalQuestions = entityVo.getAirTravelQuestions();
				else
					originalQuestions = entityVo.getCheckInQuestions();
				
				Collection<IncidentGroupQuestionVo> removedQuestions = new ArrayList<IncidentGroupQuestionVo>();
				Boolean found = false;
				
				for(IncidentGroupQuestionVo orig : originalQuestions) {
					found = false;
					for(IncidentGroupQuestionVo vo : vos) {
						if(LongUtility.hasValue(vo.getId()) && vo.getId().equals(orig.getId()) ) {
							found = true;
							break;
						}
					}
					if(!found) {
						removedQuestions.add(orig);
					}
				}
				
				if(removedQuestions.size()>0){
					Collection<String> questions = new ArrayList<String>();
					
					/*
					 * Remove the incident group question from the
					 * incident group entity. 
					 * Let hibernate delete the incidentGroupQuestion
					 */
					for(IncidentGroupQuestionVo rmVo : removedQuestions) {
						questions.add(rmVo.getQuestionVo().getQuestion());
						for(IncidentGroupQuestion igq : entity.getIncidentGroupQuestions()) {
							if(igq.getId().equals(rmVo.getId())) {
								entity.getIncidentGroupQuestions().remove(igq);
								break;
							}
						}
					}
					incidentGroupDao.save(entity);
					
					// remove questions from all incidents in group
					//this.incidentGroupDao.removeQuestionFromGroup(incidentGroupId,questions);

				}
				
				
				// save updated question list
				Collection<IncidentGroupQuestion> incidentGroupQuestions = new ArrayList<IncidentGroupQuestion>();
				
				for(IncidentGroupQuestionVo vo : vos){
					if(!vo.getQuestionVo().getQuestion().endsWith("?")){
						vo.getQuestionVo().setQuestion(vo.getQuestionVo().getQuestion()+"?");
					}
					incidentGroupQuestions.add(IncidentGroupQuestionVo.toEntity(null, vo, true, entity));
				}
				
				incidentGroupDao.flushAndEvict(entity);
				
				/*
				 * Save the collection.
				 * Let hibernate manage the inserts/updates.
				 */
				IncidentGroupQuestionDao iqDao = (IncidentGroupQuestionDao)context.getBean("incidentGroupQuestionDao");
				iqDao.saveAll(incidentGroupQuestions);

				ruleHandler.executeProcessedActions(entity,vos,dialogueVo);
				
				entity = incidentGroupDao.getById(incidentGroupId, IncidentGroupImpl.class);
				IncidentGroupVo vo = IncidentGroupVo.getInstance(entity, true);
				
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_INCIDENT_GROUP_QUESTIONS");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.incidentGroup", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(vo);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService2#saveIncidentCostDefaults(gov.nwcg.isuite.core.vo.IncidentCostDefaultsVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIncidentGroupCostDefaults(IncidentGroupVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(dialogueVo == null) dialogueVo = new DialogueVo();
		
		try{
			
			if(!LongUtility.hasValue(vo.getId()))
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentId in IncidentPrefsVo is required.");

			IncidentGroup entity = this.incidentGroupDao.getById(vo.getId(), IncidentGroupImpl.class);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"unable to retrieve incident with id " + vo.getId() + ".");//vo.getIncidentVo().getId() + ".");

			/*
			 * Save the entity
			 */
			entity.setIncidentGroupCostDefaultHours(vo.getIncidentGroupCostDefaultHours());
			entity.setCostAutoRun(vo.getCostAutoRun()== true ? StringBooleanEnum.Y : StringBooleanEnum.N);
			incidentGroupDao.save(entity);
			incidentGroupDao.flushAndEvict(entity);
			
			//vo = IncidentGroupVo.getInstance(this.incidentGroupDao.getById(vo.getId(),IncidentGroupImpl.class), true);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_INCIDENT_COST_DEFAULTS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.incidentGroup", "info.0030" , null, MessageTypeEnum.INFO));
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(vo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentGroupService2#preCheckAddIncidentsToGroup(gov.nwcg.isuite.core.vo.IncidentGroupVo, java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo preCheckAddIncidentsToGroup(IncidentGroupVo igVo, Collection<IncidentGridVo> incGridVos,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			
			AddIncidentsCheckRulesHandler rulesHandler = new AddIncidentsCheckRulesHandler(context);
			if(rulesHandler.execute(igVo, incGridVos,dialogueVo)==AbstractRule._OK){
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("PRE_CHECK_ADD_INCIDENTS");
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentGroupService2#printConflictReport(gov.nwcg.isuite.core.vo.IncidentGroupVo, gov.nwcg.isuite.core.vo.IncidentGroupConflictVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo printConflictReport(IncidentGroupVo igVo, IncidentGroupConflictVo conflictVo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();
		
		try{

			Collection<IncGroupConflictReportData> reportData = new ArrayList<IncGroupConflictReportData>();
			for(String s : conflictVo.getTextLines()){
				IncGroupConflictReportData data = new IncGroupConflictReportData();
				data.setTextline(s);
				data.setGroupName(igVo.getGroupName());				
				reportData.add(data);
			}
			
			// init new report object
			IReport report = new IncGroupConflictReport(reportData);
			ReportBuilder2 reportBuilder = new ReportBuilder2(servletContext);

			// Generate Report
			String reportName=getDestinationFileName();
			String outputFile=super.getOutputFile(reportName);
			reportBuilder.applicationContext=super.context;
			String reportFile=reportBuilder.createPdfReport(report, outputFile);
			String reportUrl=getOutputUrl(reportName);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("PRINT_CONFLICT_REPORT");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(reportUrl);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	private String getDestinationFileName() throws Exception {
		String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
		return "rpt" + timestamp + ".pdf";
	}

	public DialogueVo getIncidentGroupPrefsOtherFields(Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();

		try{
			if(!LongUtility.hasValue(incidentGroupId)) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Incident Group with null id");
			
			IncidentGroup entity = this.incidentGroupDao.getById(incidentGroupId);
			IncidentPrefsOtherFieldsVo otherFieldsVo = null;
			if (null != entity ) {
				if ( null == entity.getIncidentPrefsOtherFields()) {
					IncidentGroup incGroupEntity = new IncidentGroupImpl();
					incGroupEntity.setId(entity.getId());

					IncidentPrefsOtherFields newPrefImpl = new IncidentPrefsOtherFieldsImpl();
					newPrefImpl.setIncidentGroup(incGroupEntity);
					newPrefImpl.setOther1Label("");
					newPrefImpl.setOther2Label("");
					newPrefImpl.setOther3Label("");

					entity.setIncidentPrefsOtherFields(newPrefImpl);
					
					incidentGroupDao.save(entity);
					incidentGroupDao.flushAndEvict(entity);
					entity = this.incidentGroupDao.getById(incidentGroupId);

				} 

				otherFieldsVo = IncidentPrefsOtherFieldsVo.getInstance(entity.getIncidentPrefsOtherFields(), true);
				
			}

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_INCIDENT_GROUP_PREFS_OTHER_FIELDS");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			
			dialogueVo.setResultObject(otherFieldsVo);				
		} catch (Exception e) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo saveIncidentPrefsOtherFields(IncidentPrefsOtherFieldsVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(dialogueVo == null) dialogueVo = new DialogueVo();
		
		try{
			
			IncidentGroup entity = this.incidentGroupDao.getById(vo.getIncidentGroupId());
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"unable to retrieve incidentgroup with id " + vo.getIncidentGroupId() + ".");

			/*
			 * Rebuild incidentgroup entity with new incidentprefsotherfieldsvo data
			 */
			entity.setIncidentPrefsOtherFields(IncidentPrefsOtherFieldsVo.toEntity(entity.getIncidentPrefsOtherFields(), vo, true, entity));

			/*
			 * Save the entity
			 */
			incidentGroupDao.save(entity);
			incidentGroupDao.flushAndEvict(entity);
			entity=incidentGroupDao.getById(entity.getId(),IncidentGroupImpl.class);
			
			vo = IncidentPrefsOtherFieldsVo.getInstance(entity.getIncidentPrefsOtherFields(), true);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_INCGROUP_PREFS_OTHER_FIELDS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(
					new MessageVo(
							"text.incidentSettings",
							"info.0030.01",
							new String[]{"Changes to Other Field labels (Incident Group Settings)"},
							MessageTypeEnum.INFO));
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(vo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	@Override
	public DialogueVo saveIncidentGroupPrefsIap204(Long incidentGroupId, Collection<IncidentGroupPrefsVo> incidentGroupPrefsVos, DialogueVo dialogueVo) throws ServiceException {
		try {
			if(dialogueVo == null) {
				dialogueVo = new DialogueVo();
			}

			if(!LongUtility.hasValue(incidentGroupId))
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentGroupId is required.");
			
			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(incidentGroupId, "INCIDENTGROUP", dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}
			
			IncidentGroup incidentGroup = this.incidentGroupDao.getById(incidentGroupId);
			if(null == incidentGroup)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"unable to retrieve incident group with id " + incidentGroupId + ".");
			
			incidentGroup.setIncidentGroupPrefs(IncidentGroupPrefsVo.toEntityList(incidentGroupPrefsVos, true, incidentGroup));
			incidentGroupDao.save(incidentGroup);
			incidentGroupDao.flushAndEvict(incidentGroup);
			
			Collection<IncidentGroupPrefsVo> vos = IncidentGroupPrefsVo.getInstances(incidentGroup.getIncidentGroupPrefs(), true);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_INCIDENT_GROUP_PREFS_IAP_204"); 
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(
					new MessageVo(
							"text.iapSettings",
							"info.0030.01",
							new String[]{"changes to IAP 204 Block 5 preferences"},
							MessageTypeEnum.INFO));
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
		} catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	public DialogueVo isLocked(Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
			String isLocked="N";
			
			if(LongUtility.hasValue(incidentGroupId)){
				Boolean rslt=incidentDao.isIncidentLocked(incidentGroupId, "INCIDENTGROUP");
				if(rslt==true)
					isLocked="Y";
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("IS_LOCKED");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(isLocked);
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo getIncidentGroupCostSettings(Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {

		try{
			if(null == dialogueVo)dialogueVo=new DialogueVo();
			
			if(!LongUtility.hasValue(incidentGroupId))
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentGroupId in getIncidentGroupCostSettings is required.");

			IncidentGroup entity = this.incidentGroupDao.getById(incidentGroupId);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"unable to retrieve incident group with id " + incidentGroupId + ".");//vo.getIncidentVo().getId() + ".");
			
			CostSettingsVo vo = new CostSettingsVo();
			
			if (null != entity.getCostAutoRun() && entity.getCostAutoRun().getValue() == true) {
				vo.setCostAutoRun(true);
				vo.setCostAutoRunString("Run Cost Automatically");
			} else {
				vo.setCostAutoRun(false);
				vo.setCostAutoRunString("Run Cost Manually");
			}
			
			if ( entity.getIncidentGroupCostDefaultHours() == null ){
				vo.setCostDefaultHours(0);
				vo.setCostDefaultHoursString(String.valueOf(0));
			} else {
				vo.setCostDefaultHours(entity.getIncidentGroupCostDefaultHours());
				vo.setCostDefaultHoursString(String.valueOf(entity.getIncidentGroupCostDefaultHours()));
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_INCIDENT_GROUP_COST_SETTINGS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(vo);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;

	}

	public DialogueVo saveIncidentGroupCostSettings(Long incidentGroupId, CostSettingsVo costSettingsVo, DialogueVo dialogueVo) throws ServiceException {

		try{
			if(null == dialogueVo)dialogueVo=new DialogueVo();
			
			if(!LongUtility.hasValue(incidentGroupId))
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentGroupId in saveIncidentGroupCostSettings is required.");

			IncidentGroup entity = this.incidentGroupDao.getById(incidentGroupId);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"unable to retrieve incident group with id " + incidentGroupId + ".");//vo.getIncidentVo().getId() + ".");


			if (StringUtility.hasValue(costSettingsVo.getCostDefaultHoursString())) {
				entity.setIncidentGroupCostDefaultHours(Integer.valueOf(costSettingsVo.getCostDefaultHoursString()));
			} else {
				entity.setIncidentGroupCostDefaultHours(0);
			}

			if (StringUtility.hasValue(costSettingsVo.getCostAutoRunString()) 
					&& costSettingsVo.getCostAutoRunString().equalsIgnoreCase(("Run Cost Automatically"))) {
				entity.setCostAutoRun(StringBooleanEnum.Y);
			} else {
				entity.setCostAutoRun(StringBooleanEnum.N);
			}

			incidentGroupDao.save(entity);
			incidentGroupDao.flushAndEvict(entity);

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_INCIDENT_GROUP_COST_SETTINGS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.incidentGroup", "info.0030" , null, MessageTypeEnum.INFO));
			
			dialogueVo.setCourseOfActionVo(coaVo);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;

	}
	
}
