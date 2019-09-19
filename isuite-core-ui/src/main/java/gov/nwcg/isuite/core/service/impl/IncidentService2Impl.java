package gov.nwcg.isuite.core.service.impl;
import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.controllers.restdata.DropdownData;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentQSKind;
import gov.nwcg.isuite.core.domain.IncidentQuestion;
import gov.nwcg.isuite.core.domain.RestrictedIncidentUser;
import gov.nwcg.isuite.core.domain.UserGroup;
import gov.nwcg.isuite.core.domain.UserGroupUser;
import gov.nwcg.isuite.core.domain.impl.IncidentAccountCodeImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentQuestionImpl;
import gov.nwcg.isuite.core.domain.impl.RestrictedIncidentUserImpl;
import gov.nwcg.isuite.core.domain.impl.UserGroupImpl;
import gov.nwcg.isuite.core.filter.IncidentAccountCodeFilter;
import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.filter.impl.IncidentFilterImpl;
import gov.nwcg.isuite.core.irwin.IRWINUtil;
import gov.nwcg.isuite.core.persistence.AccountCodeDao;
import gov.nwcg.isuite.core.persistence.AgencyDao;
import gov.nwcg.isuite.core.persistence.ContractorDao;
import gov.nwcg.isuite.core.persistence.IncidentAccountCodeDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.IncidentQuestionDao;
import gov.nwcg.isuite.core.persistence.JetPortDao;
import gov.nwcg.isuite.core.persistence.KindDao;
import gov.nwcg.isuite.core.persistence.OrganizationDao;
import gov.nwcg.isuite.core.persistence.QuestionDao;
import gov.nwcg.isuite.core.persistence.RestrictedIncidentUserDao;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.persistence.UserGroupDao;
import gov.nwcg.isuite.core.persistence.WorkAreaDao;
import gov.nwcg.isuite.core.rules.IncidentAddRestrictedUserGroupUserRulesHandler;
import gov.nwcg.isuite.core.rules.IncidentAddRestrictedUserRulesHandler;
import gov.nwcg.isuite.core.rules.IncidentDeleteIncAcctCodeRulesHandler;
import gov.nwcg.isuite.core.rules.IncidentDeleteQuestionRulesHandler;
import gov.nwcg.isuite.core.rules.IncidentDeleteRestrictedUsersRulesHandler;
import gov.nwcg.isuite.core.rules.IncidentDeleteRulesHandler;
import gov.nwcg.isuite.core.rules.IncidentRestrictRulesHandler;
import gov.nwcg.isuite.core.rules.IncidentSaveIncAcctCodeRulesHandler;
import gov.nwcg.isuite.core.rules.IncidentSaveQuestionsRulesHandler;
import gov.nwcg.isuite.core.rules.IncidentSaveRulesHandler;
import gov.nwcg.isuite.core.rules.IncidentSyncSupplimentalRulesHandler;
import gov.nwcg.isuite.core.rules.IncidentUnrestrictRulesHandler;
import gov.nwcg.isuite.core.rules.IsLockedRulesHandler;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.service.IncidentSelectorService;
import gov.nwcg.isuite.core.service.IncidentService2;
import gov.nwcg.isuite.core.vo.CostSettingsVo;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.IncidentPrefsOtherFieldsVo;
import gov.nwcg.isuite.core.vo.IncidentPrefsVo;
import gov.nwcg.isuite.core.vo.IncidentQSKindVo;
import gov.nwcg.isuite.core.vo.IncidentQuestionVo;
import gov.nwcg.isuite.core.vo.IncidentSelector2Vo;
import gov.nwcg.isuite.core.vo.IncidentSupplimentalDataVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.QuestionVo;
import gov.nwcg.isuite.core.vo.RestrictedIncidentUserVo;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.core.vo.UserGroupVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.WorkAreaVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.QuestionTypeEnum;
import gov.nwcg.isuite.framework.types.RestrictedIncidentUserTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

public class IncidentService2Impl extends BaseService implements IncidentService2 {
	private AccountCodeDao accountCodeDao;
	private IncidentAccountCodeDao incidentAccountCodeDao;
	private IncidentDao incidentDao;
	private IncidentGroupDao incidentGroupDao;
	private RestrictedIncidentUserDao restrictedIncidentUserDao;

	public IncidentService2Impl() {
		super();
	}

	public void initialization() {
		accountCodeDao = (AccountCodeDao)super.context.getBean("accountCodeDao");
		incidentAccountCodeDao = (IncidentAccountCodeDao)super.context.getBean("incidentAccountCodeDao");
		incidentDao = (IncidentDao)super.context.getBean("incidentDao");
		incidentGroupDao = (IncidentGroupDao)super.context.getBean("incidentGroupDao");
		restrictedIncidentUserDao = (RestrictedIncidentUserDao)super.context.getBean("restrictedIncidentUserDao");
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService2#getGrid(gov.nwcg.isuite.core.filter.IncidentFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getGrid(IncidentFilter filter, DialogueVo dialogueVo) throws ServiceException {

		try{
			if(null == dialogueVo)dialogueVo=new DialogueVo();

			filter.setCurrentUserId(super.getUserVo().getId());

			Collection<IncidentGridVo> vos = incidentDao.getGrid(filter, super.getUserSessionVo().getPrivilegedUser());

			Collection<IncidentGridVo> returnVos = new ArrayList<IncidentGridVo>();

			for(IncidentGridVo vo : vos){
				if(vo.getSharedUser()){
					// get the shared user perms
					Collection<String> perms = incidentDao.getPermissionsForSharedUser(vo.getId(), super.getUserSessionVo().getUserId());

					vo.setRestrictedAccessPermissions(perms);
				}

				returnVos.add(vo);
			}

			//Filter on incidentNumber
			Collection<IncidentGridVo> filteredIncidents = new ArrayList<IncidentGridVo>();
			for(IncidentGridVo igvo : returnVos) {
				if(StringUtility.hasValue(filter.getIncidentNumber())) {
					String incidentTag = "US-" + igvo.getUnitCode() + "-" + igvo.getIncidentNumber();
					if(!incidentTag.startsWith(filter.getIncidentNumber().toUpperCase())) {
						filteredIncidents.add(igvo);
					}
				}
			}
			
			returnVos.removeAll(filteredIncidents);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_INCIDENT_GRID");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);

			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(returnVos);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;

	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService2#getById(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getById(Long id,DialogueVo dialogueVo) throws ServiceException {

		try{
			if(null == dialogueVo)dialogueVo=new DialogueVo();

			/*
			 * clean up duplicate resource questions
			 */
			incidentDao.cleanUpDuplicateQuestionIssue(id);
			
			/*
			 * Query for the entity
			 */
			Incident entity = incidentDao.getById(id);
			IncidentVo vo = null;

			if(null != entity)
				vo = IncidentVo.getInstance(entity, true);

			// set default user checkin settings
			for (RestrictedIncidentUserVo riuVo : vo.getRestrictedIncidentUserVos()){
				if(riuVo.getUserVo().getId().compareTo(super.getUserVo().getId())==0){
					vo.setUserDefaultCheckinDate(riuVo.getDefaultCheckinDate());
					vo.setUserDefaultCheckinType(riuVo.getDefaultCheckinType());
				}
			}
			
			/*
			 * Sync any incident supplimental information
			 */
			if(!DateUtil.hasValue(entity.getIncidentEndDate())){
				IncidentSyncSupplimentalRulesHandler syncRuleHandler = new IncidentSyncSupplimentalRulesHandler(super.context);
				syncRuleHandler.executeProcessedActions(entity, dialogueVo);
			}
			
			//check IRWIN status
			SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");
			IRWINUtil irwinUtil = new IRWINUtil(this.getRunMode(),spDao);
			irwinUtil.getIRWINStatus(vo, entity);
			
			// load supplimental data
			IncidentSupplimentalDataVo isdVo = new IncidentSupplimentalDataVo();
			// get contractor list
			ContractorDao contractorDao = (ContractorDao)context.getBean("contractorDao");
			
			Long groupId=incidentDao.getIncidentGroupId(vo.getId());
			/* dan 8/13/2019
			if(LongUtility.hasValue(groupId))
				isdVo.setContractorVos(contractorDao.getLightList(null, groupId));
			else
				isdVo.setContractorVos(contractorDao.getLightList(vo.getId(), null));
			// get kinds by incidentId
			KindDao kindDao = (KindDao)context.getBean("kindDao");
			isdVo.setKindVos(kindDao.getByIncidentId(vo.getId()));
			// get jetports by incidentId
			JetPortDao jpDao = (JetPortDao)context.getBean("jetPortDao");
			isdVo.setJetPortVos(jpDao.getNonStdJetports(null, vo.getId()));
			// get agencies by incidentId
			AgencyDao aDao = (AgencyDao)context.getBean("agencyDao");
			isdVo.setAgencyVos(aDao.getByIncidentId(vo.getId()));
			// get unit codes by incidentId
			OrganizationDao orgDao = (OrganizationDao)context.getBean("organizationDao");
			isdVo.setOrgVos(orgDao.getByIncidentId(vo.getId()));
			*/
			
			/*
			 * Build course of action for HANDLE_RECORDSET and set resultObject
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_INCIDENT_BY_ID");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(vo);
			dialogueVo.setResultObjectAlternate4(isdVo);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;

	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService2#save(gov.nwcg.isuite.core.vo.IncidentVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo save(IncidentVo incidentVo, WorkAreaVo workAreaVo, DialogueVo dialogueVo) throws ServiceException {
		try{
			if(null == dialogueVo)dialogueVo=new DialogueVo();
			
			Boolean isNew=false;
			if(!LongUtility.hasValue(incidentVo.getId()))
				isNew=true;
						
			IncidentSaveRulesHandler rulesHandler = new IncidentSaveRulesHandler(context);

			Incident incidentEntity = null;
			if(LongUtility.hasValue(incidentVo.getId())){
				incidentEntity = incidentDao.getById(incidentVo.getId());
			}

			OrganizationVo originalPdc = null;
			OrganizationVo newPdc = null;
			
			if(rulesHandler.execute(incidentEntity, incidentVo, workAreaVo, dialogueVo,originalPdc, newPdc)==IncidentSaveRulesHandler._OK){		
				//start: find match in the IRWIN, pass in incidentVo, if found, populate the IRWIN data.
				SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");
				IRWINUtil irwinUtil = new IRWINUtil(this.getRunMode(),spDao);
				irwinUtil.findMatches(incidentVo, incidentVo.getHomeUnitVo().getUnitCode(), incidentVo.getIncidentNumber());
				//end: find match in the IRWIN	
				
				incidentEntity = IncidentVo.toEntity(incidentEntity, incidentVo, true);
				incidentDao.save(incidentEntity);

				incidentVo.setId(incidentEntity.getId());
				incidentDao.flushAndEvict(incidentEntity);

				// if saving new incident , add to all users (site)
				if(super.getRunMode().equals("SITE") && isNew==true){
					incidentDao.executeInsertIncidentUser(incidentEntity.getId());

					// add to site group
					IncidentGroupDao incidentGroupDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
					incidentGroupDao.executeInsertIncidentGroupNewIncident(incidentEntity.getId());
				}
				
				// execute follow up actions
				rulesHandler.executeProcessedActions(incidentEntity, incidentVo,workAreaVo,dialogueVo,originalPdc, newPdc);

				incidentDao.flushAndEvict(incidentEntity);
				incidentEntity = incidentDao.getById(incidentEntity.getId());
				incidentVo=IncidentVo.getInstance(incidentDao.getById(incidentEntity.getId()), true);

				// set default user checkin settings
				for (RestrictedIncidentUserVo riuVo : incidentVo.getRestrictedIncidentUserVos()){
					if(riuVo.getUserVo().getId().compareTo(super.getUserVo().getId())==0){
						incidentVo.setUserDefaultCheckinDate(riuVo.getDefaultCheckinDate());
						incidentVo.setUserDefaultCheckinType(riuVo.getDefaultCheckinType());
					}
				}
				
				// build coa
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_INCIDENT");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.incident", "info.0030.01" , new String[]{"incident "+incidentVo.getIncidentName()+" "}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);

				dialogueVo.setResultObject(incidentVo);
				
				// get updated grid item
				IncidentSelectorService iss = (IncidentSelectorService)context.getBean("incidentSelectorService");
				if(CollectionUtility.hasValue(incidentEntity.getIncidentGroups())){
					// get parent incidentgroup grid item
					IncidentGroup ig = (IncidentGroup)incidentEntity.getIncidentGroups().iterator().next();
					iss.setIncidentGroupId(ig.getId());
					DialogueVo dvo2 = iss.getGrid(super.getUserSessionVo().getUserId(), null);
					if(CollectionUtility.hasValue(dvo2.getRecordset())){
						IncidentSelector2Vo is2Vo = (IncidentSelector2Vo)dvo2.getRecordset().iterator().next();	
						dialogueVo.setResultObjectAlternate(is2Vo);
					}
				}else{
					// get incident grid item
					iss.setIncidentId(incidentEntity.getId());
					DialogueVo dvo2 = iss.getGrid(super.getUserSessionVo().getUserId(), null);
					if(CollectionUtility.hasValue(dvo2.getRecordset())){
						IncidentSelector2Vo is2Vo = (IncidentSelector2Vo)dvo2.getRecordset().iterator().next();	
						dialogueVo.setResultObjectAlternate(is2Vo);
					}
				}
				
			}


		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;

	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService2#deleteIncident(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteIncident(Long incidentId, DialogueVo dialogueVo) throws ServiceException {
		try{
			if(null == dialogueVo)dialogueVo=new DialogueVo();

			Long igEntityId=0L;
			
			IncidentDeleteRulesHandler rulesHandler = new IncidentDeleteRulesHandler(context);

			Incident incidentEntity = null;
			incidentEntity = incidentDao.getById(incidentId);

			if(CollectionUtility.hasValue(incidentEntity.getIncidentGroups())){
				IncidentGroup ig = (IncidentGroup)incidentEntity.getIncidentGroups().iterator().next();
				igEntityId=ig.getId();
			}

			IncidentVo incidentVo = IncidentVo.getInstance(incidentEntity, true);

			if(rulesHandler.execute(incidentEntity, incidentVo, dialogueVo) == IncidentDeleteRulesHandler._OK) {
				// delete cost records first
				// delete iap stuff
				incidentDao.preDeleteIncident(incidentEntity.getId());
				
				incidentDao.delete(incidentEntity);

				// execute follow up actions
				rulesHandler.executeProcessedActions(incidentEntity, incidentVo, dialogueVo);

				// delete from custom excluded view
				incidentDao.deleteFromUserCustomView(incidentId);
				
				// build coa
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_INCIDENT");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.incident", "text.affirmDeleteIncident", new String[]{incidentVo.getIncidentName()}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				
				dialogueVo.setCourseOfActionVo(coaVo);


				IncidentSelector2Vo is2Vo = new IncidentSelector2Vo();
				is2Vo.setType("INCIDENT");
				is2Vo.setIncidentId(incidentVo.getId());
				
				if(LongUtility.hasValue(igEntityId)){
					// get new incident group grid item
					IncidentSelectorService iss = (IncidentSelectorService)context.getBean("incidentSelectorService");
					iss.setIncidentGroupId(igEntityId);	
					DialogueVo dvo2 = iss.getGrid(super.getUserSessionVo().getUserId(), null);
					if(CollectionUtility.hasValue(dvo2.getRecordset())){
						is2Vo = (IncidentSelector2Vo)dvo2.getRecordset().iterator().next();	
					}
				}
				
				dialogueVo.setResultObject(is2Vo);
				
			}

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;

	}

	@Override
	public DialogueVo restrictIncident(Long incidentId, DialogueVo dialogueVo) throws ServiceException {
		try {
			if(dialogueVo == null) {
				dialogueVo = new DialogueVo();
			}

			IncidentRestrictRulesHandler rulesHandler = new IncidentRestrictRulesHandler(context);

			Incident incidentEntity = null;
			incidentEntity = incidentDao.getById(incidentId);

			if(incidentEntity == null) {
				throw new ServiceException("Unable to retrieve the incident.");
			}

			IncidentVo incidentVo = IncidentVo.getInstance(incidentEntity, true);

			if(rulesHandler.execute(incidentEntity, incidentVo, dialogueVo) == AbstractRule._OK) {
				
				incidentEntity.setRestricted(true);

				RestrictedIncidentUser riuEntity = new RestrictedIncidentUserImpl();
				riuEntity.setIncident(incidentEntity);
				riuEntity.setUser(UserVo.toEntity(null, getUserVo(), false));
				//							riuEntity.setUserRoles(SystemRoleVo.toEntityList(getUserVo().getUserRoleVos(), true));
				riuEntity.setUserType(RestrictedIncidentUserTypeEnum.OWNER);
				if(null == incidentEntity.getRestrictedIncidentUsers())
					incidentEntity.setRestrictedIncidentUsers(new ArrayList<RestrictedIncidentUser>());
				incidentEntity.getRestrictedIncidentUsers().add(riuEntity);

				incidentDao.save(incidentEntity);

				// execute follow up actions
				rulesHandler.executeProcessedActions(incidentEntity, incidentVo, dialogueVo);

				// build coa
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("COMPLETE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(
						new MessageVo(
								"text.incident", 
								"text.affirmRestrictIncident" ,
								new String[]{incidentVo.getIncidentNumber(), incidentVo.getIncidentName()}, 
								MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);

				dialogueVo.setResultObject(IncidentVo.getInstance(incidentEntity, true));
			}

		} catch(Exception e) {
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	@Override
	public DialogueVo unrestrictIncident(Long incidentId, DialogueVo dialogueVo) throws ServiceException {
		try {
			if(dialogueVo == null) {
				dialogueVo = new DialogueVo();
			}

			IncidentUnrestrictRulesHandler rulesHandler = new IncidentUnrestrictRulesHandler(context);

			Incident incidentEntity = null;
			incidentEntity = incidentDao.getById(incidentId);

			if(incidentEntity == null) {
				throw new ServiceException("Unable to retrieve the incident.");
			}
			
			IncidentVo incidentVo = IncidentVo.getInstance(incidentEntity, true);

			if(rulesHandler.execute(incidentEntity, incidentVo, dialogueVo) == IncidentUnrestrictRulesHandler._OK) {
				
				incidentEntity.setRestricted(false);				
				incidentDao.save(incidentEntity);				
				restrictedIncidentUserDao.unRestrictIncidentUsersAndOwners(incidentEntity.getId());
				
				IncidentGroupDao incidentGroupDao = (IncidentGroupDao)super.context.getBean("incidentGroupDao");
				//Remove the now unrestricted Incident from its Incident Group.
				incidentGroupDao.removeIncidentFromGroup(incidentEntity.getId());

				// execute follow up actions
				rulesHandler.executeProcessedActions(incidentEntity, incidentVo, dialogueVo);

				// build coa
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("COMPLETE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(
						new MessageVo(
								"text.incident", 
								"text.affirmUnrestrictIncident" , 
								new String[]{incidentVo.getIncidentNumber(), incidentVo.getIncidentName()}, 
								MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);

				dialogueVo.setResultObject(IncidentVo.getInstance(incidentEntity, true));
			}

		} catch(Exception e) {
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService2#getRestrictedIncidentUsers(java.lang.Long, gov.nwcg.isuite.core.filter.UserFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getRestrictedIncidentUsers(Long restrictedIncidentId, UserFilter filter, DialogueVo dialogueVo) throws ServiceException {

		if(dialogueVo == null) dialogueVo = new DialogueVo();

		try{
			
			if (!LongUtility.hasValue(restrictedIncidentId))
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"restrictedIncidentId is required to retrieve incident restricted users.");

			Incident entity = incidentDao.getById(restrictedIncidentId);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"unable to retrieve restricted incident with id " + restrictedIncidentId + ".");

			if(!entity.getRestricted()){
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
			    coaVo.setCoaName("MSG_FINISHED");
			    coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				ErrorObject errorObj = new ErrorObject(ErrorEnum._90000_ERROR , new String[]{"Unable to retrieve restricted incident users, the incident is not restricted."});
			    coaVo.setMessageVo(
						   new MessageVo(
								   "text.incidentGroup", 
								   errorObj.getErrorProperty(),
								   errorObj.getParameters(),
								   MessageTypeEnum.CRITICAL));

				dialogueVo.setCourseOfActionVo(coaVo);
				
			}else{
			
				/*
				 * Get the restricted users for the incident and return in the dialogue.
				 */
				Collection<RestrictedIncidentUserVo> vos = restrictedIncidentUserDao.getUsersInRestrictedIncident(restrictedIncidentId, filter);
					
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("GET_INCIDENT_RESTRICTED_USERS");
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setRecordset(vos);
			}

			incidentDao.flushAndEvict(entity);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService2#getAvailableRestrictedIncidentUsers(java.lang.Long, gov.nwcg.isuite.core.filter.UserFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getAvailableRestrictedIncidentUsers(Long restrictedIncidentId, UserFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(dialogueVo == null) dialogueVo = new DialogueVo();
		
		try{
			if (!LongUtility.hasValue(restrictedIncidentId))
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"restrictedIncidentId is required to retrieve available users.");
			
			Collection<UserVo> vos = restrictedIncidentUserDao.getAvailableRestrictedIncidentUsers(restrictedIncidentId, filter);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_AVAILABLE_RESTRICTED_USERS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo getAvailableIncidentUsers(Long restrictedIncidentId, DialogueVo dialogueVo) throws ServiceException {
		if(dialogueVo == null) dialogueVo = new DialogueVo();
		
		try{
			
			Collection<UserGridVo> vos = restrictedIncidentUserDao.getAvailableIncidentUsers(restrictedIncidentId);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_AVAILABLE_INCIDENT_USERS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo getDefaultQuestions(DialogueVo dialogueVo) {
		if(dialogueVo == null)
			dialogueVo = new DialogueVo();
		
		try{
			QuestionDao questionDao = (QuestionDao)super.context.getBean("questionDao");
			Collection<QuestionVo> vos = questionDao.getDefaultQuestions();
			Collection<IncidentQuestionVo> iVos = new ArrayList<IncidentQuestionVo>();
			
			for(QuestionVo vo : vos) {
				IncidentQuestionVo iqvo = new IncidentQuestionVo();
				iqvo.setQuestionVo(vo);
				iqvo.setVisible(true);
				iVos.add(iqvo);
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_DEFAULT_QUESTIONS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(iVos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo getIncidentPrefsOtherFields(Long incidentId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();

		try{
			if(!LongUtility.hasValue(incidentId)) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Incident with null id");
			
			Incident entity = this.incidentDao.getById(incidentId);
			IncidentPrefsOtherFieldsVo otherFieldsVo = null;
			if (null != entity ) {
				otherFieldsVo = IncidentPrefsOtherFieldsVo.getInstance(entity.getIncidentPrefsOtherFields(), true);
			}

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_INCIDENT_PREFS_OTHER_FIELDS");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			
			dialogueVo.setResultObject(otherFieldsVo);				
		} catch (Exception e) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService2#saveIncidentPrefsOtherFields(gov.nwcg.isuite.core.vo.IncidentPrefsOtherFieldsVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIncidentPrefsOtherFields(IncidentPrefsOtherFieldsVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(dialogueVo == null) dialogueVo = new DialogueVo();
		
		try{
			
			if(!LongUtility.hasValue(vo.getIncidentId()))
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentId in IncidentPrefsVo is required.");

			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, vo.getIncidentId(), "INCIDENT");
				
				if(null != rule){
					if(AbstractRule._OK != rule.executeRules(dialogueVo)){
						return dialogueVo;
					}
				}
			}
			
			Incident entity = this.incidentDao.getById(vo.getIncidentId());
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"unable to retrieve incident with id " + vo.getIncidentId() + ".");//vo.getIncidentVo().getId() + ".");

			/*
			 * Rebuild incident entity with new incidentprefsotherfieldsvo data
			 */
			entity.setIncidentPrefsOtherFields(IncidentPrefsOtherFieldsVo.toEntity(entity.getIncidentPrefsOtherFields(), vo, true, entity));

			/*
			 * Save the entity
			 */
			incidentDao.save(entity);
			incidentDao.flushAndEvict(entity);
			entity=incidentDao.getById(entity.getId(),IncidentImpl.class);
			
			vo = IncidentPrefsOtherFieldsVo.getInstance(entity.getIncidentPrefsOtherFields(), true);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_INCIDENT_PREFS_OTHER_FIELDS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(
					new MessageVo(
							"text.incidentSettings",
							"info.0030.01",
							new String[]{"Changes to Other Field labels (Incident Settings)"},
							MessageTypeEnum.INFO));
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(vo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService2#addRestrictedUsers(java.lang.Long, java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo addRestrictedUsers(Long incidentId,Collection<RestrictedIncidentUserVo> vos ,DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{

			if (!LongUtility.hasValue(incidentId))
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentId is required to add users to a restricted incident.");

			Incident entity = this.incidentDao.getById(incidentId);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"unable to retrieve incident with id " + incidentId + ".");
			
			IncidentAddRestrictedUserRulesHandler ruleHandler = new IncidentAddRestrictedUserRulesHandler(context);

			/*
			 * Run rule checks
			 */
			if(ruleHandler.execute(entity, vos, dialogueVo )==AbstractRuleHandler._OK ){

				Collection<RestrictedIncidentUser> entities = RestrictedIncidentUserVo.toEntityList(null, vos, true,entity);
				
				restrictedIncidentUserDao.saveAll(entities);
				
				WorkAreaDao waDao = (WorkAreaDao)context.getBean("workAreaDao");
				if(super.getRunMode().equals("ENTERPRISE")){
					for(RestrictedIncidentUserVo riuvo : vos){
						if(!riuvo.getUserVo().getLoginName().contains("ad.user1")) {
							waDao.syncUserRestrictedWorkArea(riuvo.getUserVo().getId());	
						}
					}
				}
				
				incidentDao.flushAndEvict(entity);
				entity = incidentDao.getById(incidentId, IncidentImpl.class);				
				
				Collection<RestrictedIncidentUserVo> riuVos = RestrictedIncidentUserVo.getInstances(entity.getRestrictedIncidentUsers(), true);
				
				/*
				 * Build course of action for 
				 */
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("COMPLETE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.incident", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.FALSE);				
				dialogueVo.setCourseOfActionVo(coaVo);
				
				CourseOfActionVo coaVoRecordset = new CourseOfActionVo();
				coaVoRecordset.setCoaName("REMOVE_USERS_FROM_RESTRICTED_INCIDENT_AVAILABLE_LIST");
				coaVoRecordset.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
				coaVoRecordset.setIsDialogueEnding(Boolean.TRUE);				
				dialogueVo.addSupplementalCourseOfActionVo(coaVoRecordset);
				dialogueVo.setRecordset(riuVos);	
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService2#removeRestrictedUsers(java.lang.Long, java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo removeRestrictedUsers(Long incidentId, Collection<RestrictedIncidentUserVo> vos, DialogueVo dialogueVo) throws ServiceException {
		
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{		
			if (!LongUtility.hasValue(incidentId))
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentId is required to remove users from a restricted incident.");
		
			Incident entity = this.incidentDao.getById(incidentId);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"unable to retrieve incident with id " + incidentId + ".");
			
			IncidentDeleteRestrictedUsersRulesHandler ruleHandler = new IncidentDeleteRestrictedUsersRulesHandler(context);

			/*
			 * Run rule checks
			 */
			if(ruleHandler.execute(entity, vos, dialogueVo )==AbstractRuleHandler._OK ){
				WorkAreaDao waDao = (WorkAreaDao)context.getBean("workAreaDao");
				for(RestrictedIncidentUserVo riuvo : vos){
					RestrictedIncidentUser riuEntity = restrictedIncidentUserDao.getById(riuvo.getId());
					if(null != riuEntity){
						restrictedIncidentUserDao.delete(riuEntity);
						if(!riuvo.getUserVo().getLoginName().contains("ad.user1")) {
							waDao.syncUserRestrictedWorkArea(riuvo.getUserVo().getId());	
						}
					}
				}
				
				/*
				 * Build course of action for 
				 */
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("COMPLETE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.incident", "text.affirmDeleteRestrictedIncidentUsers" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.FALSE);				
				dialogueVo.setCourseOfActionVo(coaVo);
				
				CourseOfActionVo coaVoRecordset = new CourseOfActionVo();
				coaVoRecordset.setCoaName("REMOVE_USERS_FROM_RESTRICTED_INCIDENT");
				coaVoRecordset.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
				coaVoRecordset.setIsDialogueEnding(Boolean.TRUE);				
				dialogueVo.addSupplementalCourseOfActionVo(coaVoRecordset);
				dialogueVo.setRecordset(vos);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService2#addRestrictedUserGroupUsers(java.lang.Long, java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */	
	public DialogueVo addRestrictedUserGroupUsers(Long incidentId, Long userGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			if(!LongUtility.hasValue(incidentId)) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Incident with null id");
			
			if(!LongUtility.hasValue(userGroupId)) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"UserGroup with null id");
			
			Incident entity = incidentDao.getById(incidentId, IncidentImpl.class);
			if(null==entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Incident[id="+incidentId+"]");
			
			UserGroupDao ugDao = (UserGroupDao)context.getBean("userGroupDao");
			UserGroup ugEntity = ugDao.getById(userGroupId, UserGroupImpl.class);
			if(null==ugEntity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"UserGroup[id="+userGroupId+"]");
			
			IncidentAddRestrictedUserGroupUserRulesHandler ruleHandler = new IncidentAddRestrictedUserGroupUserRulesHandler(context);
			if(ruleHandler.execute(entity, ugEntity, dialogueVo)==AbstractRule._OK){
				
				/*
				 * Add the user group users to the restricted incident
				 */
				Collection<UserGroupUser> userGroupUsers = ugEntity.getUserGroupUsers();
				Collection<RestrictedIncidentUser> riuEntities = new ArrayList<RestrictedIncidentUser>();
				
			    for(UserGroupUser ugu : userGroupUsers) {

			    	/*
			    	 * Create the restricted incident user entity
			    	 */
			    	RestrictedIncidentUser riuEntity = new RestrictedIncidentUserImpl();
					
					UserVo userVo = new UserVo();
					userVo.setId(ugu.getUserId());
					
					riuEntity.setId(restrictedIncidentUserDao.getRestrictedIncidentUserId(ugu.getUserId(), incidentId));
					riuEntity.setUserType(RestrictedIncidentUserTypeEnum.USER);
					riuEntity.setUser(UserVo.toEntity(null, userVo, false, riuEntity));
					riuEntity.setIncident(entity);
					
					riuEntities.add(riuEntity);
			    }
				
				restrictedIncidentUserDao.saveAll(riuEntities);

				incidentDao.flushAndEvict(entity);
				entity = incidentDao.getById(incidentId, IncidentImpl.class);
				
				Collection<RestrictedIncidentUserVo> riuVos = RestrictedIncidentUserVo.getInstances(entity.getRestrictedIncidentUsers(), true);
				
				/*
				 * Build and set the info.0030 message
				 */
				CourseOfActionVo coaVo = new CourseOfActionVo();
			    coaVo.setCoaName("ADD_RESTR_USER_GROUP_USER");
			    coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setIsDialogueEnding(Boolean.FALSE);
			    coaVo.setMessageVo(
						   new MessageVo(
								   "text.restrictedIncident", 
								   "info.0030" , null, 
								   MessageTypeEnum.INFO));
				dialogueVo.setCourseOfActionVo(coaVo);
				
				CourseOfActionVo coaVoRecordset = new CourseOfActionVo();
				coaVoRecordset.setCoaName("UPDATE_USERS_ON_SELECTED_RESTRICTED_INCIDENT_VO");
				coaVoRecordset.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
				coaVoRecordset.setIsDialogueEnding(Boolean.TRUE);				
				dialogueVo.addSupplementalCourseOfActionVo(coaVoRecordset);
				dialogueVo.setRecordset(riuVos);				
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo getQSKinds(Long incidentId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();

		try{
			if(!LongUtility.hasValue(incidentId)) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Incident with null id");
			
			Collection<IncidentQSKindVo> vos = new ArrayList<IncidentQSKindVo>();
			
			Incident entity = this.incidentDao.getById(incidentId);
			Collection<KindVo> kindVos = new ArrayList<KindVo>();
			if (null != entity ) {
				vos = IncidentQSKindVo.getInstances(entity.getIncidentQSKinds(), true);
				for(IncidentQSKindVo v : vos ) {
					kindVos.add(v.getKindVo());
				}
			}

			CourseOfActionVo coaVoRecordset = new CourseOfActionVo();
			coaVoRecordset.setCoaName("GET_INCIDENT_QS_KINDS");
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
	 * @see gov.nwcg.isuite.core.service.IncidentService2#saveQSKinds(java.lang.Long, java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveQSKinds(Long incidentId, Collection<KindVo> kindVos, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{
	
			if(!LongUtility.hasValue(incidentId)) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Incident with null id");
			
			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, incidentId, "INCIDENT");
				
				if(null != rule){
					if(AbstractRule._OK != rule.executeRules(dialogueVo)){
						return dialogueVo;
					}
				}
			}
			
			Incident entity = incidentDao.getById(incidentId, IncidentImpl.class);
			if(null==entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Incident[id="+incidentId+"]");

			/*
			 * Get list of IncidentQSKinds to remove and add
			 */
			Collection<IncidentQSKind> removeList = IncidentQSKindVo.toRemoveList(kindVos,entity.getIncidentQSKinds());
			Collection<IncidentQSKind> addList = IncidentQSKindVo.toAddList(kindVos,entity.getIncidentQSKinds(),entity);

			if(CollectionUtility.hasValue(removeList))
				entity.getIncidentQSKinds().removeAll(removeList);

			if(CollectionUtility.hasValue(addList))
				entity.getIncidentQSKinds().addAll(addList);

			incidentDao.save(entity);
			
			incidentDao.flushAndEvict(entity);
			entity = incidentDao.getById(incidentId, IncidentImpl.class);
			
			IncidentVo incidentVo = IncidentVo.getInstance(entity, true);
			
			/*
			 * Build course of action for 
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_INCIDENT_QS_KINDS");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.incident", "info.0030" , null, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(true);

			dialogueVo.setCourseOfActionVo(coaVo);
			
			dialogueVo.setRecordset(incidentVo.getIncidentQSKindVos());
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo getIncidentAirTravelQuestions(Long incidentId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();

		try {
			/*
			 * clean up duplicate resource questions
			 */
			incidentDao.cleanUpDuplicateQuestionIssue(incidentId);
			
			/*
			 * Query for the entity
			 */
			Incident entity = incidentDao.getById(incidentId);


			Collection<IncidentQuestionVo> vos = new ArrayList<IncidentQuestionVo>();
			
			if ( null != entity) {
				for(IncidentQuestion iq : entity.getIncidentQuestions()){
					if(iq.getQuestion().getQuestionType() == QuestionTypeEnum.AIRTRAVEL ) {
						vos.add(IncidentQuestionVo.getInstance(iq, true));
					}
				}
			}

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_INCIDENT_AIR_TRAVEL_QUESTIONS");
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
	 * @see gov.nwcg.isuite.core.service.IncidentService2#saveIncidentQuestions(java.lang.Long, java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIncidentQuestions(Long incidentId,Collection<IncidentQuestionVo> vos, String questionType, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{
			if(!LongUtility.hasValue(incidentId)) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Incident with null id");
			
			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, incidentId, "INCIDENT");
				
				if(null != rule){
					if(AbstractRule._OK != rule.executeRules(dialogueVo)){
						return dialogueVo;
					}
				}
			}
			
			Incident entity = incidentDao.getById(incidentId, IncidentImpl.class);
			if(null==entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Incident[id="+incidentId+"]");

			Long incidentGroupId=this.incidentDao.getIncidentGroupId(incidentId);
			
			/*
			 * Run rule checks
			 */
			IncidentSaveQuestionsRulesHandler ruleHandler = new IncidentSaveQuestionsRulesHandler(context);
			if(ruleHandler.execute(entity,vos,dialogueVo)==AbstractRuleHandler._OK){

				// get list of removed questions
				IncidentVo entityVo = IncidentVo.getInstance(entity, true);
				
				Collection<IncidentQuestionVo> originalQuestions;
				if(questionType.equalsIgnoreCase("AirTravel"))
					originalQuestions = entityVo.getAirTravelQuestions();
				else
					originalQuestions = entityVo.getCheckInQuestions();
				
				Collection<IncidentQuestionVo> removedQuestions = new ArrayList<IncidentQuestionVo>();
				Boolean found = false;
				
				for(IncidentQuestionVo orig : originalQuestions) {
					found = false;
					for(IncidentQuestionVo vo : vos) {
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
					/*
					 * Remove the incident question from the
					 * incident entity. 
					 * Let hibernate delete the incidentQuestion
					 */
					Collection<String> questions=new ArrayList<String>();
					for(IncidentQuestionVo rmVo : removedQuestions) {
						for(IncidentQuestion igq : entity.getIncidentQuestions()) {
							if(igq.getId().equals(rmVo.getId())) {
								questions.add(igq.getQuestion().getQuestion());
								entity.getIncidentQuestions().remove(igq);
								break;
							}
						}
					}
					incidentDao.save(entity);
					
					if(LongUtility.hasValue(incidentGroupId)){
						// remove questions from group and all incidents in group
						//this.incidentGroupDao.removeQuestionFromGroup(incidentGroupId,questions);
					}
				}
				
				
				// save updated question list
				Collection<IncidentQuestion> incidentQuestions = new ArrayList<IncidentQuestion>();
				
				for(IncidentQuestionVo vo : vos){
					if(!vo.getQuestionVo().getQuestion().endsWith("?")){
						vo.getQuestionVo().setQuestion(vo.getQuestionVo().getQuestion()+"?");
					}
					incidentQuestions.add(IncidentQuestionVo.toEntity(null, vo, true, entity));
				}
				
				incidentDao.flushAndEvict(entity);
				
				/*
				 * Save the collection.
				 * Let hibernate manage the inserts/updates.
				 */
				IncidentQuestionDao iqDao = (IncidentQuestionDao)context.getBean("incidentQuestionDao");
				iqDao.saveAll(incidentQuestions);

				ruleHandler.executeProcessedActions(entity, vos, dialogueVo);
				
				entity = incidentDao.getById(incidentId, IncidentImpl.class);
				IncidentVo vo = IncidentVo.getInstance(entity, true);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_INCIDENT_QUESTIONS");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.incident", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setResultObject(vo);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService2#deleteIncidentQuestion(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteIncidentQuestion(Long incidentQuestionId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();

		try{
			if(!LongUtility.hasValue(incidentQuestionId)) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"IncidentQuestion with null id");
			
			IncidentQuestionDao iqDao = (IncidentQuestionDao)context.getBean("incidentQuestionDao");
			
			IncidentQuestion entity = iqDao.getById(incidentQuestionId, IncidentQuestionImpl.class);
			if(null==entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"IncidentQuestion[id="+incidentQuestionId+"]");
			
			/*
			 * Run rule checks
			 */
			IncidentDeleteQuestionRulesHandler ruleHandler = new IncidentDeleteQuestionRulesHandler(context);
			if(ruleHandler.execute(entity,dialogueVo)==AbstractRuleHandler._OK){
				
				Incident incidentEntity = entity.getIncident();
				
				/*
				 * Remove the incident question from the
				 * incident entity. 
				 * Let hibernate delete the incidentQuestion
				 */
				incidentEntity.getIncidentQuestions().remove(entity);
				incidentDao.save(incidentEntity);
				
				/*
				 * Build course of action for 
				 */
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_INCIDENT_QUESTION");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.incident", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setResultObject(incidentQuestionId);
				dialogueVo.setCourseOfActionVo(coaVo);
				
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	@Override
	public DialogueVo getIncidentAccountCodes(Long incidentId, IncidentAccountCodeFilter filter, DialogueVo dialogueVo) throws ServiceException {
		try {
			if(null == dialogueVo) {
				dialogueVo = new DialogueVo();
			}
			
			Collection<IncidentAccountCode> entities = this.incidentAccountCodeDao.getGrid(incidentId, filter);
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setIsDialogueEnding(true);
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(IncidentAccountCodeVo.getInstances(entities, true));
			
		} catch(Exception e) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService2#saveIncidentAccountCode(java.lang.Long, gov.nwcg.isuite.core.vo.IncidentAccountCodeVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIncidentAccountCode(Long incidentId, IncidentAccountCodeVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			if(!LongUtility.hasValue(incidentId)) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Incident with null id");

			Incident incidentEntity = this.incidentDao.getById(incidentId);
			if(null == incidentEntity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"unable to retrieve incident with id " + incidentId + ".");

			IncidentAccountCode entity = null;
			if(LongUtility.hasValue(vo.getId())) 
				entity=incidentAccountCodeDao.getById(vo.getId(), IncidentAccountCodeImpl.class);
			
			IncidentSaveIncAcctCodeRulesHandler ruleHandler = new IncidentSaveIncAcctCodeRulesHandler(context);
			if(ruleHandler.execute(entity, vo, incidentEntity, dialogueVo)==AbstractRule._OK){

				incidentDao.flushAndEvict(incidentEntity);
				incidentAccountCodeDao.flushAndEvict(entity);
				
				boolean isAccountCodeChanged=false;
				if(null != entity && LongUtility.hasValue(entity.getId()) 
						&& !entity.getAccrualAccountCode().equalsIgnoreCase(vo.getAccrualAccountCode())){
					isAccountCodeChanged=true;
				}
				vo.setIncidentId(incidentId);
				entity = IncidentAccountCodeVo.toEntity(entity, vo, true);
				if(isAccountCodeChanged==true){
					entity.setAccrualAccountCode(vo.getAccrualAccountCode().toUpperCase());
				}
				
				incidentAccountCodeDao.save(entity);
				incidentAccountCodeDao.flushAndEvict(entity);
				
				entity=incidentAccountCodeDao.getById(entity.getId(), IncidentAccountCodeImpl.class);
				
				ruleHandler.executeProcessedActions(entity, vo, incidentEntity, dialogueVo);
				
				vo=IncidentAccountCodeVo.getInstance(entity,true);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_INCIDENT_ACCOUNT_CODE");
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(
						new MessageVo(
								"text.incidentAccountingCodes",
								"info.0030.01",
								new String[]{"Incident Accounting Code " + entity.getAccountCode().getAccountCode()},
								MessageTypeEnum.INFO));
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(vo);
				
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService2#deleteIncidentAccountCode(gov.nwcg.isuite.core.vo.IncidentAccountCodeVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteIncidentAccountCode(IncidentAccountCodeVo vo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			if(!LongUtility.hasValue(vo.getId())) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Unable to retrieve incident account code to delete with id " + vo.getId() + ".");
		
			IncidentAccountCode entity=incidentAccountCodeDao.getById(vo.getId(), IncidentAccountCodeImpl.class);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"unable to retrieve incident account code with id " + vo.getId() + ".");

			Incident incidentEntity = incidentDao.getById(entity.getIncidentId());
			
			IncidentDeleteIncAcctCodeRulesHandler ruleHandler = new IncidentDeleteIncAcctCodeRulesHandler(context);
			if(ruleHandler.execute(entity, incidentEntity,dialogueVo)==AbstractRule._OK){

				String accountCode = entity.getAccountCode().getAccountCode();
				incidentAccountCodeDao.delete(entity);
				incidentAccountCodeDao.flushAndEvict(entity);
				
				// 6/3/2019 fix issue with the accrualAccountCode
				Collection<IncidentAccountCode> iacs = this.incidentAccountCodeDao.getByIncidentId(incidentEntity.getId());
				if(CollectionUtility.hasValue(iacs)){
					for(IncidentAccountCode iac : iacs ) {
						if(StringUtility.hasValue(iac.getAccrualAccountCode()) 
								&& iac.getAccrualAccountCode().equalsIgnoreCase(accountCode)) {
							// reset back to original since the one that is assigned is getting deleted
							iac.setAccrualAccountCode(iac.getAccountCode().getAccountCode());
							this.incidentAccountCodeDao.save(iac);
							this.incidentAccountCodeDao.flushAndEvict(iac);
						}
					}
				}

				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_INCIDENT_ACCOUNT_CODE");
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(
						new MessageVo(
								"text.incidentAccountingCodes",
								"info.0028",
								new String[]{"The Incident Accounting Code " + entity.getAccountCode().getAccountCode()},
								MessageTypeEnum.INFO));
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(vo.getId());
			}
			
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo getIncidentPrefs(Long incidentId, DialogueVo dialogueVo) throws ServiceException {
		try {
			if(dialogueVo == null) {
				dialogueVo = new DialogueVo();
			}

			if(!LongUtility.hasValue(incidentId))
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentId in IncidentPrefsVo is required.");

			Incident entity = this.incidentDao.getById(incidentId);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"unable to retrieve incident with id " + incidentId + ".");

			Collection<IncidentPrefsVo> vos = IncidentPrefsVo.getInstances(entity.getIncidentPrefs(), true);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_INCIDENT_PREFS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
			
		} catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	@Override
	public DialogueVo saveIncidentPrefs(Long incidentId, Collection<IncidentPrefsVo> incidentPrefsVos, DialogueVo dialogueVo) throws ServiceException {
		try {
			if(dialogueVo == null) {
				dialogueVo = new DialogueVo();
			}

			if(!LongUtility.hasValue(incidentId))
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentId in IncidentPrefsVo is required.");

			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, incidentId, "INCIDENT");
				
				if(null != rule){
					if(AbstractRule._OK != rule.executeRules(dialogueVo)){
						return dialogueVo;
					}
				}
			}
			
			Incident entity = this.incidentDao.getById(incidentId);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"unable to retrieve incident with id " + incidentId + ".");

			entity.setIncidentPrefs(IncidentPrefsVo.toEntityList(incidentPrefsVos, true, entity));

			/*
			 * Save the entity
			 */
			incidentDao.save(entity);
			incidentDao.flushAndEvict(entity);
			
			Long groupId = incidentDao.getIncidentGroupId(entity.getId());

			if(LongUtility.hasValue(groupId)){
				// propagate the incident prefs to group 
				incidentGroupDao.propagatePrefsFromIncident(entity.getId(),groupId);
				
				// for each incident in group
				// propagate group values to each incident
				// we want to sync all group/incident prefs to be the same
				incidentGroupDao.propagatePrefs(groupId);
			}

			Collection<IncidentPrefsVo> vos = IncidentPrefsVo.getInstances(entity.getIncidentPrefs(), true);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
//			coaVo.setCoaName("");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(
					new MessageVo(
							"text.incidentSettings",
							"info.0030.01",
							new String[]{"changes to Check-Out Form"},
							MessageTypeEnum.INFO));
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
		} catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	@Override
	public DialogueVo saveIncidentPrefsIap204(Long incidentId, Collection<IncidentPrefsVo> incidentPrefsVos, DialogueVo dialogueVo) throws ServiceException {
		try {
			if(dialogueVo == null) {
				dialogueVo = new DialogueVo();
			}

			if(!LongUtility.hasValue(incidentId))
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentId in IncidentPrefsVo is required.");

			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(incidentId, "INCIDENT", dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}
			
			Incident entity = this.incidentDao.getById(incidentId);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"unable to retrieve incident with id " + incidentId + ".");

			entity.setIncidentPrefs(IncidentPrefsVo.toEntityList(incidentPrefsVos, true, entity));

			/*
			 * Save the entity
			 */
			incidentDao.save(entity);
			incidentDao.flushAndEvict(entity);
			
			Collection<IncidentPrefsVo> vos = IncidentPrefsVo.getInstances(entity.getIncidentPrefs(), true);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_INCIDENT_PREFS_IAP_204"); 
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

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService2#getAvailableUserGroups(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getAvailableUserGroups(DialogueVo dialogueVo) throws ServiceException {
		if(dialogueVo == null) dialogueVo = new DialogueVo();

		try{
			UserGroupDao ugDao = (UserGroupDao)super.context.getBean("userGroupDao");
			
			Collection<UserGroup> entities = ugDao.getUserGroups(super.getUserVo().getId());
			
			Collection<UserGroupVo> vos = UserGroupVo.getInstances(entities, true);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_AVAILABLE_USER_GROUPS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo getIncidentUserDefaultCheckin(Long userId, Long incidentId, DialogueVo dialogueVo) throws ServiceException {
		if(dialogueVo == null) dialogueVo = new DialogueVo();
		
		try{
			Collection<Long> incIds = new ArrayList<Long>();
			incIds.add(incidentId);
			Collection<RestrictedIncidentUser> users = this.restrictedIncidentUserDao.getRIUsersByUserIdAndIncidentIds(userId, incIds);
			
			String defaultType = "";
			
			if(CollectionUtility.hasValue(users)) {
				RestrictedIncidentUser riu = users.iterator().next();
				defaultType = riu.getDefaultCheckinType();
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_INCIDENT_USER_DEFAULT_CHECKIN");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			dialogueVo.setResultObject(defaultType);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService2#saveIncidentUserCheckin(gov.nwcg.isuite.core.vo.RestrictedIncidentUserVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIncidentUserCheckin(RestrictedIncidentUserVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(dialogueVo == null) dialogueVo = new DialogueVo();

		try{
			Long incidentId=0L;
			if(null != vo && null != vo.getIncidentVo() && LongUtility.hasValue(vo.getIncidentVo().getId())){
				incidentId=vo.getIncidentVo().getId();
			}
			
			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, incidentId, "INCIDENT");
				
				if(null != rule){
					if(AbstractRule._OK != rule.executeRules(dialogueVo)){
						return dialogueVo;
					}
				}
			}
			
			this.restrictedIncidentUserDao.updateUserDefaultCheckIn(vo);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_INCIDENT_USER_CHECKIN");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(
					new MessageVo(
							"text.incidentSettings",
							"info.0030.01",
							new String[]{"user default Check-In settings for this incident"},
							MessageTypeEnum.INFO));
			//coaVo.setMessageVo(new MessageVo("text.incident", "info.generic" , new String[]{"The user default Check-In settings for this incident were saved."}, MessageTypeEnum.INFO));
			
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
		
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService2#saveIncidentCostDefaults(gov.nwcg.isuite.core.vo.IncidentCostDefaultsVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIncidentCostDefaults(IncidentVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(dialogueVo == null) dialogueVo = new DialogueVo();
		
		try{
			
			if(!LongUtility.hasValue(vo.getId()))
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentId in IncidentPrefsVo is required.");

			Incident entity = this.incidentDao.getById(vo.getId());
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"unable to retrieve incident with id " + vo.getId() + ".");//vo.getIncidentVo().getId() + ".");

			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, entity.getId(), "INCIDENT");
				
				if(null != rule){
					if(AbstractRule._OK != rule.executeRules(dialogueVo)){
						incidentDao.flushAndEvict(entity);
						return dialogueVo;
					}
				}
			}
			
			/*
			 * Save the entity
			 */
			entity.setIncidentCostDefaultHours(vo.getIncidentCostDefaultHours());
			entity.setCostAutoRun(vo.getCostAutoRun()==true ? StringBooleanEnum.Y : StringBooleanEnum.N);
			incidentDao.save(entity);
			//vo = IncidentVo.getInstance(entity, true);
			incidentDao.flushAndEvict(entity);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_INCIDENT_COST_DEFAULTS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(
					new MessageVo(
							"text.incidentSettings",
							"info.0030.01",
							new String[]{"changes to Incident Cost Defaults"},
							MessageTypeEnum.INFO));
			/*
			coaVo.setMessageVo(
					new MessageVo(
							"text.incidentSettings",
							"text.affirmSaveIncidentCostDefaults",
							null,
							MessageTypeEnum.INFO));
			*/
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(vo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo getAllIncidents(DialogueVo dialogueVo) throws ServiceException {

		try{
			if(null == dialogueVo)dialogueVo=new DialogueVo();
			
			IncidentFilter filter = new IncidentFilterImpl();
			
			filter.setCurrentUserId(super.getUserVo().getId());

			Collection<IncidentGridVo> vos = incidentDao.getGrid(filter, super.getUserSessionVo().getPrivilegedUser());

			Collection<IncidentGridVo> returnVos = new ArrayList<IncidentGridVo>();

			for(IncidentGridVo vo : vos){
				if(vo.getSharedUser()){
					// get the shared user perms
					Collection<String> perms = incidentDao.getPermissionsForSharedUser(vo.getId(), super.getUserSessionVo().getUserId());

					vo.setRestrictedAccessPermissions(perms);
				}

				returnVos.add(vo);
			}

			//Filter on incidentNumber
			Collection<IncidentGridVo> filteredIncidents = new ArrayList<IncidentGridVo>();
			for(IncidentGridVo igvo : returnVos) {
				if(StringUtility.hasValue(filter.getIncidentNumber())) {
					String incidentTag = "US-" + igvo.getUnitCode() + "-" + igvo.getIncidentNumber();
					if(!incidentTag.startsWith(filter.getIncidentNumber().toUpperCase())) {
						filteredIncidents.add(igvo);
					}
				}
			}
			
			returnVos.removeAll(filteredIncidents);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_INCIDENT_GRID");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);

			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(returnVos);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;

	}

	public DialogueVo getAllIncidentsForUser(DialogueVo dialogueVo) throws ServiceException {

		try{
			if(null == dialogueVo)dialogueVo=new DialogueVo();
			
			Long userId=super.getUserVo().getId();

			Collection<IncidentGridVo> vos = incidentDao.getIncidentsForUser(userId);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_ALL_INCIDENTS_FOR_USER");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);

			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;

	}

	public DialogueVo getIncidentCostSettings(Long incidentId, DialogueVo dialogueVo) throws ServiceException {

		try{
			if(null == dialogueVo)dialogueVo=new DialogueVo();
			
			if(!LongUtility.hasValue(incidentId))
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentId in getIncidentCostSettings is required.");

			Incident entity = this.incidentDao.getById(incidentId);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"unable to retrieve incident with id " + incidentId + ".");//vo.getIncidentVo().getId() + ".");
			
			CostSettingsVo vo = new CostSettingsVo();
			
			if (null != entity.getCostAutoRun() && entity.getCostAutoRun().getValue() == true) {
				vo.setCostAutoRun(true);
				vo.setCostAutoRunString("Run Cost Automatically");
			} else {
				vo.setCostAutoRun(false);
				vo.setCostAutoRunString("Run Cost Manually");
			}
			
			if ( entity.getIncidentCostDefaultHours() == null ){
				vo.setCostDefaultHours(14);
				vo.setCostDefaultHoursString("14");
			} else {
				vo.setCostDefaultHours(entity.getIncidentCostDefaultHours());
				vo.setCostDefaultHoursString(String.valueOf(entity.getIncidentCostDefaultHours()));
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_INCIDENT_COST_SETTINGS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(vo);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;

	}

	public DialogueVo saveIncidentCostSettings(Long incidentId, CostSettingsVo costSettingsVo, DialogueVo dialogueVo) throws ServiceException {

		try{
			if(null == dialogueVo)dialogueVo=new DialogueVo();
			
			if(!LongUtility.hasValue(incidentId))
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentId in saveIncidentCostSettings is required.");

			Incident entity = this.incidentDao.getById(incidentId);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"unable to retrieve incident with id " + incidentId + ".");//vo.getIncidentVo().getId() + ".");


			if (StringUtility.hasValue(costSettingsVo.getCostDefaultHoursString())) {
				entity.setIncidentCostDefaultHours(Integer.valueOf(costSettingsVo.getCostDefaultHoursString()));
			} else {
				entity.setIncidentCostDefaultHours(0);
			}

			if (StringUtility.hasValue(costSettingsVo.getCostAutoRunString()) 
					&& costSettingsVo.getCostAutoRunString().equalsIgnoreCase(("Run Cost Automatically"))) {
				entity.setCostAutoRun(StringBooleanEnum.Y);
			} else {
				entity.setCostAutoRun(StringBooleanEnum.N);
			}

			incidentDao.save(entity);
			incidentDao.flushAndEvict(entity);

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_INCIDENT_COST_SETTINGS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.incident", "info.0030" , null, MessageTypeEnum.INFO));
			
			dialogueVo.setCourseOfActionVo(coaVo);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;

	}
	
	public DialogueVo getIncidentAccountCodeDropdownList(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {

		try{
			if(null == dialogueVo)dialogueVo=new DialogueVo();

			Collection<DropdownData> list = new ArrayList<DropdownData>();
			Collection<IncidentAccountCode> iacs = new ArrayList<IncidentAccountCode>();
			
			if ( LongUtility.hasValue(incidentId)) {
				iacs = this.incidentAccountCodeDao.getByIncidentId(incidentId);
			}
			
			if (LongUtility.hasValue(incidentGroupId)) {
				iacs = this.incidentAccountCodeDao.getByIncidentGroupId(incidentGroupId);
			}

			if( CollectionUtility.hasValue(iacs)){
				list = DropdownData.convertFromIncidentAccountCode(iacs);
			}

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_INC_ACCT_CODE_DROPDOWN_LIST");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setRecordset(list);
			dialogueVo.setCourseOfActionVo(coaVo);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
		
	}

	public DialogueVo isLocked(Long incidentId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
			String isLocked="N";
			
			if(LongUtility.hasValue(incidentId)){
				Boolean rslt=incidentDao.isIncidentLocked(incidentId, "INCIDENT");
				if(rslt==true)
					isLocked="Y";
				/*
				if(super.getRunMode().equals("SITE") && rslt==true)
					isLocked= "N";
				else if(super.getRunMode().equals("SITE") && rslt==false)
					isLocked="Y";
				else if(super.getRunMode().equals("ENTERPRISE") && rslt==true)
					isLocked="Y";
				else if(super.getRunMode().equals("ENTERPRISE") && rslt==false)
					isLocked="N";
				else
					isLocked="N";
					*/
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
	
}
