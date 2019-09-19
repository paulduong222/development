package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.domain.CostGroupAgencyDayShare;
import gov.nwcg.isuite.core.domain.CostGroupAgencyDaySharePercentage;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.ResourceOther;
import gov.nwcg.isuite.core.domain.impl.CostGroupAgencyDayShareImpl;
import gov.nwcg.isuite.core.domain.impl.CostGroupAgencyDaySharePercentageImpl;
import gov.nwcg.isuite.core.domain.impl.CostGroupImpl;
import gov.nwcg.isuite.core.filter.CostGroupAgencyDayShareFilter;
import gov.nwcg.isuite.core.filter.CostGroupFilter;
import gov.nwcg.isuite.core.filter.impl.CostGroupAgencyDayShareFilterImpl;
import gov.nwcg.isuite.core.persistence.CostGroupAgencyDayShareDao;
import gov.nwcg.isuite.core.persistence.CostGroupAgencyDaySharePercentageDao;
import gov.nwcg.isuite.core.persistence.CostGroupDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceOtherDao;
import gov.nwcg.isuite.core.persistence.ResourceDao;
import gov.nwcg.isuite.core.persistence.ResourceOtherDao;
import gov.nwcg.isuite.core.rules.CostGroupsDeleteAllocationDateRulesHandler;
import gov.nwcg.isuite.core.rules.CostGroupsDeleteAllocationPctRulesHandler;
import gov.nwcg.isuite.core.rules.CostGroupsDeleteRulesHandler;
import gov.nwcg.isuite.core.rules.CostGroupsSaveAgencyDayShareRulesHandler;
import gov.nwcg.isuite.core.rules.CostGroupsSaveAgencyPercentageRulesHandler;
import gov.nwcg.isuite.core.rules.CostGroupsSaveRulesHandler;
import gov.nwcg.isuite.core.service.CostGroupService;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.CostGroupAgencyDayShareGridVo;
import gov.nwcg.isuite.core.vo.CostGroupAgencyDaySharePercentageVo;
import gov.nwcg.isuite.core.vo.CostGroupAgencyDayShareVo;
import gov.nwcg.isuite.core.vo.CostGroupGridVo;
import gov.nwcg.isuite.core.vo.CostGroupResourceGridVo;
import gov.nwcg.isuite.core.vo.CostGroupVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

public class CostGroupServiceImpl extends BaseService implements CostGroupService {
	private CostGroupDao costGroupDao;
	private ResourceOtherDao resourceOtherDao;
	private ResourceDao resourceDao;
	private CostGroupAgencyDayShareDao costGroupAgencyDayShareDao;
	private CostGroupAgencyDaySharePercentageDao costGroupAgencyDaySharePercentageDao;

	public CostGroupServiceImpl() {
		super();
	}

	public void initialization() {
		costGroupDao = (CostGroupDao)super.context.getBean("costGroupDao");
		resourceOtherDao = (ResourceOtherDao)super.context.getBean("resourceOtherDao");
		resourceDao = (ResourceDao)super.context.getBean("resourceDao");
		costGroupAgencyDayShareDao = (CostGroupAgencyDayShareDao)super.context.getBean("costGroupAgencyDayShareDao");
		costGroupAgencyDaySharePercentageDao = (CostGroupAgencyDaySharePercentageDao)super.context.getBean("costGroupAgencyDaySharePercentageDao");
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostGroupService#getGrid(gov.nwcg.isuite.core.filter.CostGroupFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getGrid(CostGroupFilter costGroupFilter, DialogueVo dialogueVo) throws ServiceException {

		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try {
			Collection<CostGroupGridVo> vos = costGroupDao.getGrid(costGroupFilter);

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_COST_GROUP_GRID");
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
	 * @see gov.nwcg.isuite.core.service.CostGroupService#getById(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getById(Long costGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try {
			CostGroup entity = costGroupDao.getById(costGroupId, CostGroupImpl.class);
			CostGroupVo costGroupVo = null;
			if(null!=entity) {
				costGroupVo = CostGroupVo.getInstance(entity, true);
			}

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_BY_ID_COST_GROUP");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(costGroupVo);

		} catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostGroupService#save(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, gov.nwcg.isuite.core.vo.CostGroupVo)
	 */
	public DialogueVo save(DialogueVo dialogueVo, CostGroupVo costGroupVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try{
			CostGroup entity = null;

			if(LongUtility.hasValue(costGroupVo.getId())){
				entity = costGroupDao.getById(costGroupVo.getId(), CostGroupImpl.class);
			}

			CostGroupsSaveRulesHandler rulesHandler = new CostGroupsSaveRulesHandler(context);

			if(rulesHandler.execute(entity, costGroupVo, dialogueVo)==CostGroupsSaveRulesHandler._OK) {
				entity = CostGroupVo.toEntity(entity,costGroupVo,true);

				costGroupDao.save(entity);
				costGroupDao.flushAndEvict(entity);

				rulesHandler.executeProcessedActions(entity, costGroupVo, dialogueVo);

				entity = costGroupDao.getById(entity.getId(), CostGroupImpl.class);
				costGroupVo = CostGroupVo.getInstance(entity, true);

				CostGroupGridVo gridVo = CostGroupGridVo.getInstance(entity, true);

				//Build course of action
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_COST_GROUP");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.costGroup", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);

				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(costGroupVo);
				dialogueVo.setResultObjectAlternate(gridVo);

			}

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostGroupService#deleteCostGroup(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteCostGroup(Long costGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try {
			CostGroup entity = costGroupDao.getById(costGroupId, CostGroupImpl.class);

			if (null == entity) {
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"CostGroup["+costGroupId+"]");
			}

			CostGroupsDeleteRulesHandler ruleHandler = new CostGroupsDeleteRulesHandler(context);

			if(ruleHandler.execute(entity, null, dialogueVo)==AbstractRuleHandler._OK){
				//Mark the entity as deleted by setting deletedDate
				entity.setDeletedDate(Calendar.getInstance().getTime());
				costGroupDao.save(entity);
				costGroupDao.flushAndEvict(entity);

				ruleHandler.executeProcessedActions(entity, null, dialogueVo);
				
				//Build and set the affirm delete message
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_COST_GROUP_COMPLETE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(
						new MessageVo(
								"Cost Groups", 
								"text.affirmDelete", 
								new String[]{entity.getCostGroupName()}, 
								MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setCourseOfActionVo(coaVo);
				//Return deleted cost group
				dialogueVo.setResultObject(entity.getId());
			}

		} catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostGroupService#getCostGroups(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, gov.nwcg.isuite.core.filter.CostGroupFilter)
	 */
	public DialogueVo getCostGroups(DialogueVo dialogueVo, CostGroupFilter costGroupFilter) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try {
			Collection<CostGroupVo> vos = costGroupDao.getAll(costGroupFilter);

			CourseOfActionVo coaVo = new CourseOfActionVo();

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
	 * @see gov.nwcg.isuite.core.service.CostGroupService#getCostGroupResourceGrids(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, java.lang.Long)
	 */
	public DialogueVo getCostGroupResourceGrids(DialogueVo dialogueVo, Long costGroupId) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try {
			Collection<CostGroupResourceGridVo> availableVos = costGroupDao.getCostGroupAvailableResourceGrid(costGroupId);
			Collection<CostGroupResourceGridVo> selectedVos = costGroupDao.getCostGroupSelectedResourceGrid(costGroupId);
			Collection<CostGroupResourceGridVo> otherAvailableVos = costGroupDao.getCostGroupAvailableOtherResourceGrid(costGroupId);
			Collection<CostGroupResourceGridVo> otherSelectedVos = costGroupDao.getCostGroupSelectedOtherResourceGrid(costGroupId);

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_RESOURCE_GRIDS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(availableVos);
			dialogueVo.setResultObjectAlternate(otherAvailableVos);
			dialogueVo.setResultObjectAlternate2(selectedVos);
			dialogueVo.setResultObjectAlternate3(otherSelectedVos);

		} catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostGroupService#saveCostGroupResources(java.lang.Long, java.util.Collection, java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveCostGroupResources(Long costGroupId, Collection<CostGroupResourceGridVo> resources, Collection<CostGroupResourceGridVo> otherResources, DialogueVo dialogueVo) {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try {
			CostGroup costGroup = costGroupDao.getById(costGroupId, CostGroupImpl.class);
			if(costGroup == null) {
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"CostGroup[id="+costGroupId+"]");
			}

			Collection<Long> resourceIds = CostGroupResourceGridVo.toResourceIds(resources);
			Collection<Long> otherResourceIds = CostGroupResourceGridVo.toResourceIds(otherResources);

			Collection<Resource> resourcesToAdd = resourceDao.getAllByIds(resourceIds);
			Collection<ResourceOther> otherResourcesToAdd = resourceOtherDao.getResourceOthersByIds(otherResourceIds);

			costGroup.getResources().clear();
			costGroup.getResources().addAll(resourcesToAdd);
			costGroup.getResourceOthers().clear();
			costGroup.getResourceOthers().addAll(otherResourcesToAdd);

			costGroupDao.save(costGroup);

			costGroupDao.removeResourcesFromPriorCostGroups(costGroupId, resourceIds);
			costGroupDao.removeOtherResourcesFromPriorCostGroups(costGroupId, otherResourceIds);

			/*
			 * Update the resources default costgroup in the resource.costData table
			 */
			IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
			IncidentResourceOtherDao iroDao=(IncidentResourceOtherDao)context.getBean("incidentResourceOtherDao");

			for(Resource r : resourcesToAdd){
				IncidentResource ir = r.getIncidentResources().iterator().next();
				irDao.updateDefaultCostGroup(ir.getCostDataId(), costGroup.getId());
			}
			for(ResourceOther ro : otherResourcesToAdd){
				IncidentResourceOther iro = ro.getIncidentResourceOthers().iterator().next();
				iroDao.updateDefaultCostGroup(iro.getCostDataId(), costGroup.getId());
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_RESOURCES");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setMessageVo(new MessageVo("text.costGroup", "info.0030" , null, MessageTypeEnum.INFO));
			dialogueVo.setCourseOfActionVo(coaVo);

		} catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostGroupService#deleteCostGroupAgencyDayShare(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, java.lang.Long)
	 */
	public DialogueVo deleteCostGroupAgencyDayShare(DialogueVo dialogueVo, Long costGroupAgencyDayShareId) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try {
			CostGroupAgencyDayShare entity = costGroupAgencyDayShareDao.getById(costGroupAgencyDayShareId, CostGroupAgencyDayShareImpl.class);

			if (null == entity) {
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"CostGroupAgencyDayShare["+costGroupAgencyDayShareId+"]");
			}

			CostGroupsDeleteAllocationDateRulesHandler ruleHandler = new CostGroupsDeleteAllocationDateRulesHandler(context);

			if(ruleHandler.execute(entity, dialogueVo)==AbstractRuleHandler._OK){
				//Mark the entity as deleted by setting deletedDate
				entity.setDeletedDate(Calendar.getInstance().getTime());
				costGroupAgencyDayShareDao.save(entity);
				costGroupAgencyDayShareDao.flushAndEvict(entity);

				//Build and set the affirm delete message
				String dt = DateUtil.toDateString(entity.getAgencyShareDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY);
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_CG_SHARE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.delete", "info.0028" , new String[]{dt}, MessageTypeEnum.INFO));
				dialogueVo.setCourseOfActionVo(coaVo);
				//Return deleted cost group
				dialogueVo.setResultObject(costGroupAgencyDayShareId);
				coaVo.setIsDialogueEnding(Boolean.TRUE);
			}

		} catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	public DialogueVo deleteCostGroupAgencyDaySharePct(DialogueVo dialogueVo, Long costGroupAgencyDaySharePctId) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try {
			CostGroupAgencyDaySharePercentage entity = costGroupAgencyDaySharePercentageDao.getById(costGroupAgencyDaySharePctId, CostGroupAgencyDaySharePercentageImpl.class);

			if (null == entity) {
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"CostGroupAgencyDaySharePercentage["+costGroupAgencyDaySharePctId+"]");
			}

			CostGroupsDeleteAllocationPctRulesHandler ruleHandler = new CostGroupsDeleteAllocationPctRulesHandler(context);

			if(ruleHandler.execute(entity, dialogueVo)==AbstractRuleHandler._OK){
				costGroupAgencyDaySharePercentageDao.delete(entity);

				//Build and set the affirm delete message
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_CG_SHAREPCT");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.delete", "info.0028" , new String[]{"Cost Group Agency Percentage"}, MessageTypeEnum.INFO));
				dialogueVo.setCourseOfActionVo(coaVo);
				//Return deleted cost group
				dialogueVo.setResultObject(costGroupAgencyDaySharePctId);
				coaVo.setIsDialogueEnding(Boolean.TRUE);
			}

		} catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostGroupService#getAgencyDayShareById(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, java.lang.Long)
	 */
	//	public DialogueVo getAgencyDayShareById(DialogueVo dialogueVo, Long id) throws ServiceException {
	//		if(null==dialogueVo)dialogueVo = new DialogueVo();
	//		
	//		try {
	//			CostGroupAgencyDayShare entity = costGroupAgencyDayShareDao.getById(id, CostGroupAgencyDayShareImpl.class);
	//			if(null==entity) {
	//				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"CostGroupAgencyDayShare["+id+"]");
	//			}
	//			
	//			CourseOfActionVo coaVo = new CourseOfActionVo();
	//			
	//			coaVo.setIsDialogueEnding(Boolean.TRUE);
	//			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
	//			
	//			dialogueVo.setCourseOfActionVo(coaVo);
	//			dialogueVo.setResultObject(CostGroupAgencyDayShareVo.getInstance(entity, true));
	//			
	//		} catch(Exception e){
	//			super.dialogueException(dialogueVo, e);
	//		}
	//		
	//		return dialogueVo;
	//	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostGroupService#getAgencyDayShareGrid(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, java.lang.Long)
	 */
	public DialogueVo getAgencyDayShareGrid(DialogueVo dialogueVo, Long costGroupId) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try {
			Collection<CostGroupAgencyDayShare> entities = costGroupAgencyDayShareDao.getGrid(costGroupId);

			CostGroupAgencyDayShareFilter filter = new CostGroupAgencyDayShareFilterImpl();
			filter.setCostGroupId(costGroupId);
			Collection<CostGroupAgencyDayShareGridVo> vos = CostGroupAgencyDayShareGridVo.getHierarchicalInstances(entities, filter, true);

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_AGENCY_SHARE_GRID");
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
	 * @see gov.nwcg.isuite.core.service.CostGroupService#getAgencyDaySharePercentageById(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, java.lang.Long)
	 */
	//	public DialogueVo getAgencyDaySharePercentageById(DialogueVo dialogueVo, Long id) throws ServiceException {
	//		if(null==dialogueVo)dialogueVo = new DialogueVo();
	//		
	//		try {
	//			CostGroupAgencyDaySharePercentage entity = costGroupAgencyDaySharePercentageDao.getById(id, CostGroupAgencyDaySharePercentageImpl.class);
	//			if(null==entity) {
	//				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"CostGroupAgencyDaySharePercent["+id+"]");
	//			}
	//			
	//			CourseOfActionVo coaVo = new CourseOfActionVo();
	//			
	//			coaVo.setIsDialogueEnding(Boolean.TRUE);
	//			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
	//			
	//			dialogueVo.setCourseOfActionVo(coaVo);
	//			dialogueVo.setResultObject(CostGroupAgencyDaySharePercentageVo.getInstance(entity, true));
	//			
	//		} catch(Exception e){
	//			super.dialogueException(dialogueVo, e);
	//		}
	//		
	//		return dialogueVo;
	//	}

	public DialogueVo getAgencyDaySharePercentageGrid(DialogueVo dialogueVo, CostGroupAgencyDayShareFilter costGroupAgencyDayShareFilter) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try {
			Collection<CostGroupAgencyDaySharePercentageVo> vos = costGroupAgencyDaySharePercentageDao.getGrid(costGroupAgencyDayShareFilter);

			CourseOfActionVo coaVo = new CourseOfActionVo();

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
	 * @see gov.nwcg.isuite.core.service.CostGroupService#saveAgencyDaySharePercentages(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, java.util.Collection)
	 */
	public DialogueVo saveAgencyDaySharePercentages(DialogueVo dialogueVo, Collection<CostGroupAgencyDaySharePercentageVo> costGroupAgencyDaySharePercentageVos) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try {

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostGroupService#saveAgencyDaySharePercentage(gov.nwcg.isuite.core.vo.CostGroupAgencyDaySharePercentageVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveAgencyDaySharePercentage(CostGroupAgencyDaySharePercentageVo vo, DialogueVo dialogueVo ) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try {
			CostGroupAgencyDayShare dayShare = null;
			CostGroupAgencyDaySharePercentage daySharePct=null;

			// get costGroupDayShare
			if(null != vo.getCostGroupAgencyDayShareVo()){
				if(LongUtility.hasValue(vo.getCostGroupAgencyDayShareVo().getId())){
					dayShare=this.costGroupAgencyDayShareDao.getById( 
							vo.getCostGroupAgencyDayShareVo().getId(),CostGroupAgencyDayShareImpl.class);
				}else{
					if(DateUtil.hasValue(vo.getCostGroupAgencyDayShareVo().getAgencyShareDate())){
						// try and get it by date
						dayShare=this.costGroupAgencyDayShareDao.getByShareDate(vo.getCostGroupAgencyDayShareVo().getAgencyShareDate(),vo.getCostGroupAgencyDayShareVo().getCostGroupVo().getId());
					}

					if(null==dayShare){
						// init a new one for this date?
						dayShare = new CostGroupAgencyDayShareImpl();
						dayShare.setAgencyShareDate(vo.getCostGroupAgencyDayShareVo().getAgencyShareDate());
						dayShare.setCostGroup(CostGroupVo.toEntity(null, vo.getCostGroupAgencyDayShareVo().getCostGroupVo(), false));
					}

					vo.setCostGroupAgencyDayShareVo(CostGroupAgencyDayShareVo.getInstance(dayShare,false));
				}

			}

			// get costGroupDaySharePct
			if(LongUtility.hasValue(vo.getId())){
				daySharePct=this.costGroupAgencyDaySharePercentageDao.getById(
						vo.getId(),CostGroupAgencyDaySharePercentageImpl.class);
			}

			// init rulehandler, run rule checks
			CostGroupsSaveAgencyPercentageRulesHandler ruleHandler = new CostGroupsSaveAgencyPercentageRulesHandler(super.context);
			if(ruleHandler.execute(vo, dayShare, daySharePct, dialogueVo)==AbstractRule._OK){

				if(!LongUtility.hasValue(vo.getId())){
					CostGroupAgencyDaySharePercentage pctEntity = CostGroupAgencyDaySharePercentageVo.toEntity(vo, true, dayShare);
					dayShare.getCostGroupAgencyDaySharePercentages().add(pctEntity);
					this.costGroupAgencyDayShareDao.save(dayShare);
					this.costGroupAgencyDayShareDao.flushAndEvict(pctEntity.getAgency());
					this.costGroupAgencyDayShareDao.flushAndEvict(pctEntity);
					this.costGroupAgencyDayShareDao.flushAndEvict(dayShare);
				}else{
					CostGroupAgencyDaySharePercentage pctEntity = null;
					for(CostGroupAgencyDaySharePercentage pct : dayShare.getCostGroupAgencyDaySharePercentages()){
						if(pct.getId().compareTo(vo.getId())==0){
							pctEntity=pct;
							break;
						}
					}
					pctEntity.setAgency(AgencyVo.toEntity(null, vo.getAgencyVo(), false));
					pctEntity.setPercentage(vo.getPercentage());
					this.costGroupAgencyDaySharePercentageDao.save(pctEntity);
					this.costGroupAgencyDaySharePercentageDao.flushAndEvict(pctEntity.getAgency());
					this.costGroupAgencyDaySharePercentageDao.flushAndEvict(pctEntity);
					this.costGroupAgencyDayShareDao.flushAndEvict(dayShare);
				}

				// get update grid object for this date
				Long costGroupId=dayShare.getCostGroupId();
				if(!LongUtility.hasValue(costGroupId))
					costGroupId=dayShare.getCostGroup().getId();
				CostGroupAgencyDayShare ds = costGroupAgencyDayShareDao.getByShareDate(dayShare.getAgencyShareDate(), costGroupId);
				Collection<CostGroupAgencyDayShare> entities = new ArrayList<CostGroupAgencyDayShare>();
				entities.add(ds);
				CostGroupAgencyDayShareFilter filter = new CostGroupAgencyDayShareFilterImpl();
				filter.setCostGroupId(ds.getCostGroupId());
				Collection<CostGroupAgencyDayShareGridVo> vos = CostGroupAgencyDayShareGridVo.getHierarchicalInstances(entities, filter, true);
				CostGroupAgencyDayShareGridVo gridVo = null;
				if(CollectionUtility.hasValue(vos))
					gridVo=vos.iterator().next();

				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_AGENCY_SHARE_PERCENTAGE");
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.costGroup", "info.0030" , null, MessageTypeEnum.INFO));
				dialogueVo.setResultObject(gridVo);
				dialogueVo.setCourseOfActionVo(coaVo);

			}

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	public DialogueVo saveAgencyDayShare(CostGroupAgencyDayShareVo vo, DialogueVo dialogueVo ) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try {
			CostGroupAgencyDayShare entity = null;

			// get costGroupDayShare
			if(LongUtility.hasValue(vo.getId()))
				entity=this.costGroupAgencyDayShareDao.getById(vo.getId(),CostGroupAgencyDayShareImpl.class);

			// init rulehandler, run rule checks
			CostGroupsSaveAgencyDayShareRulesHandler ruleHandler = new CostGroupsSaveAgencyDayShareRulesHandler(context);
			if(ruleHandler.execute(vo, entity, dialogueVo)==AbstractRule._OK){	
				
				entity=CostGroupAgencyDayShareVo.toEntity(entity, vo, true);
				this.costGroupAgencyDayShareDao.save(entity);
				for(CostGroupAgencyDaySharePercentage dsp : entity.getCostGroupAgencyDaySharePercentages()){
					this.costGroupAgencyDayShareDao.flushAndEvict(dsp);
				}
				this.costGroupAgencyDayShareDao.flushAndEvict(entity);
				
				vo=CostGroupAgencyDayShareVo.getInstance(entity, true);
				
				DialogueVo dvo2 = new DialogueVo();
				dvo2=this.getAgencyDayShareGrid(dvo2, vo.getCostGroupVo().getId());
				CostGroupAgencyDayShareGridVo gridVo=null;
				if(null != dvo2){
					Collection<CostGroupAgencyDayShareGridVo> gvos = (Collection<CostGroupAgencyDayShareGridVo>)dvo2.getRecordset();
					for(CostGroupAgencyDayShareGridVo gvo : gvos){
						if(gvo.getId().compareTo(entity.getId())==0){
							gridVo=gvo;
							break;
						}
					}
				}
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_AGENCY_SHARE");
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.costGroup", "info.0030" , null, MessageTypeEnum.INFO));
				dialogueVo.setResultObject(vo);
				if(null != gridVo)
					dialogueVo.setResultObjectAlternate(gridVo);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	public DialogueVo getAgencyDayShareById(Long id, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		CostGroupAgencyDayShare entity = null;
		
		try{
			CostGroupAgencyDayShareVo vo = null;
			
			if(LongUtility.hasValue(id)){
				entity = this.costGroupAgencyDayShareDao.getById(id, CostGroupAgencyDayShareImpl.class);
				
				if(null != entity){
					vo = CostGroupAgencyDayShareVo.getInstance(entity, true);
				}
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_AGENCY_SHARE");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			dialogueVo.setResultObject(vo);
			dialogueVo.setCourseOfActionVo(coaVo);
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CostGroupService#getAgencyDaySharePercentageById(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getAgencyDaySharePercentageById(Long id, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		CostGroupAgencyDaySharePercentage entity = null;
		
		try{
			CostGroupAgencyDaySharePercentageVo vo = null;
			
			if(LongUtility.hasValue(id)){
				entity = this.costGroupAgencyDaySharePercentageDao.getById(id, CostGroupAgencyDaySharePercentageImpl.class);
				
				if(null != entity){
					vo = CostGroupAgencyDaySharePercentageVo.getInstance(entity, true);
					CostGroupAgencyDayShareVo dsVo = new CostGroupAgencyDayShareVo();
					dsVo.setId(entity.getCostGroupAgencyDayShare().getId());
					dsVo.setCostGroupVo(CostGroupVo.getInstance(entity.getCostGroupAgencyDayShare().getCostGroup(), false));
					dsVo.setAgencyShareDate(entity.getCostGroupAgencyDayShare().getAgencyShareDate());
					
					vo.setCostGroupAgencyDayShareVo(dsVo);
				}
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_AGENCY_SHARE_PERCENTAGE");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			dialogueVo.setResultObject(vo);
			dialogueVo.setCourseOfActionVo(coaVo);
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
}
