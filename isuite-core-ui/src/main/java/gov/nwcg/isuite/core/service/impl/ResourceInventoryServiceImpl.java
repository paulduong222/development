package gov.nwcg.isuite.core.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.persistence.ResourceInventoryDao;
import gov.nwcg.isuite.core.rules.ResourceInventoryDeleteRulesHandler;
import gov.nwcg.isuite.core.rules.ResourceInventorySaveRulesHandler;
import gov.nwcg.isuite.core.service.ResourceInventoryService;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.ResourceInventoryGridVo;
import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

public class ResourceInventoryServiceImpl extends BaseService implements ResourceInventoryService {
	protected ResourceInventoryDao resourceInventoryDao;
	
	public ResourceInventoryServiceImpl() {
		super();
	}

    public void initialization(){
    	resourceInventoryDao = (ResourceInventoryDao)super.context.getBean("resourceInventoryDao");
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ResourceInventoryService#getGrid(java.lang.Long, java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
    public DialogueVo getGrid(Long dispatchCenterId, Long userId, DialogueVo dialogueVo) throws ServiceException {
		
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		Collection<ResourceInventoryGridVo> resourceInventoryGridVos = new ArrayList<ResourceInventoryGridVo>();
		
		try{
			resourceInventoryGridVos = resourceInventoryDao.getGrid(dispatchCenterId, userId);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_RES_INVENTORY_GRID");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(resourceInventoryGridVos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ResourceInventoryService#deleteResource(gov.nwcg.isuite.core.vo.ResourceVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteResource(Long resourceId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try {
			ResourceInventoryDeleteRulesHandler rulesHandler = new ResourceInventoryDeleteRulesHandler(context);
			
			Resource resourceEntity = resourceInventoryDao.getById(resourceId, ResourceImpl.class);
			
			if(rulesHandler.execute(resourceEntity, dialogueVo) == ResourceInventoryDeleteRulesHandler._OK) {
				resourceEntity.setDeletedDate(Calendar.getInstance().getTime());
				resourceInventoryDao.save(resourceEntity);
				resourceInventoryDao.flushAndEvict(resourceEntity);
				
				// execute follow up actions
				rulesHandler.executeProcessedActions(resourceEntity, dialogueVo);
				
				// build coa
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_RES_INVENTORY_COMPLETE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(
						new MessageVo(
								"Resource Inventory", 
								"text.affirmDelete", 
								new String[]{resourceEntity.getResourceName()}, 
								MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(resourceEntity.getId());
			}
			
		}catch(Exception e) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ResourceInventoryService#getById(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getById(Long id, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try {
			Resource resourceEntity = resourceInventoryDao.getById(id, ResourceImpl.class);
			ResourceVo resourceVo = null;
			
			if(null != resourceEntity) {
				resourceVo = ResourceVo.getInstance(resourceEntity, true);
			}
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_BY_ID_RES_INVENTORY");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setResultObject(resourceVo);
			
		} catch(Exception e) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ResourceInventoryService#save(gov.nwcg.isuite.core.vo.ResourceVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo save(ResourceVo resourceVo, DialogueVo dialogueVo) throws ServiceException, ValidationException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try {
			ResourceInventorySaveRulesHandler rulesHandler = new ResourceInventorySaveRulesHandler(context);
			if(rulesHandler.execute(resourceVo, dialogueVo) == ResourceInventorySaveRulesHandler._OK) {
				
				Resource resourceEntity = null;
				if(LongUtility.hasValue(resourceVo.getId())){
					resourceEntity = resourceInventoryDao.getById(resourceVo.getId(), ResourceImpl.class);
				}
				
				resourceEntity = ResourceVo.toResourceInventoryEntity(resourceEntity, resourceVo);
				resourceInventoryDao.save(resourceEntity);
				
				resourceInventoryDao.flushAndEvict(resourceEntity);
				
				//rulesHandler.executeProcessedActions(resourceVo, dialogueVo);
				
				resourceEntity = resourceInventoryDao.getById(resourceEntity.getId(), ResourceImpl.class);
				resourceVo=ResourceVo.getInstance(resourceEntity, true);
				
				//get the gridVo
				ResourceInventoryGridVo gridVo = ResourceInventoryGridVo.getInstance(resourceEntity);
				
				// build coa
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_INVENTORY_RESOURCE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.resourceInventory", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);

				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(resourceVo);
				dialogueVo.setResultObjectAlternate(gridVo);
					
			}
			
		} catch(Exception e) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ResourceInventoryService#removeNonStandardResource(java.lang.Long, gov.nwcg.isuite.core.vo.ResourceInventoryGridVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo removeNonStandardResource(Long organizationId, ResourceVo resourceVo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try {
			resourceInventoryDao.removeNonStandardResource(organizationId, resourceVo.getId());
			
			// build coa
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("REMOVE_NON_STANDARD_RESOURCE");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(
					new MessageVo(
							"text.resourceInventory", 
							"text.affirmDelete", 
							new String[]{resourceVo.getResourceName()}, 
							MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(resourceVo.getId());
			
		}catch(Exception e) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ResourceInventoryService#addNonStandardResource(gov.nwcg.isuite.core.vo.OrganizationVo, java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo addNonStandardResources(OrganizationVo organizationVo, Collection<ResourceInventoryGridVo> gridVos, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try {
			Collection<Long> resourceIds = new ArrayList<Long>();
			
			for(ResourceInventoryGridVo gridVo : gridVos) {
				resourceIds.add(gridVo.getId());
			}
			
			Collection<Resource> resources = resourceInventoryDao.getAllByIds(resourceIds);
			
			Organization organization = OrganizationVo.toEntity(null, organizationVo, true);
			
			
			for(Resource resource : resources) {
				resource.getNonStandardOrganizations().add(organization);
			}
			
			resourceInventoryDao.saveAll(resources);
			
			//get the gridVo
			Collection<ResourceInventoryGridVo> resourceInventoryGridVos = ResourceInventoryGridVo.getInstances(resources);
			
			// build coa
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_NON_STD_RESOURCES");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.resourceInventory", "info.0030" , null, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);

			dialogueVo.setCourseOfActionVo(coaVo);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setRecordset(resourceInventoryGridVos);
			
		}catch(Exception e) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ResourceInventoryService#getNonStdGrid(java.lang.Long, java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getNonStdGrid(Long dispatchCenterId, Long nonStdId, DialogueVo dialogueVo) throws ServiceException {
		
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		Collection<ResourceInventoryGridVo> resourceInventoryGridVos = new ArrayList<ResourceInventoryGridVo>();
		
		try{
			resourceInventoryGridVos = resourceInventoryDao.getNonStdGrid(dispatchCenterId, nonStdId);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_NON_STD_RES_GRID");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(resourceInventoryGridVos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ResourceInventoryService#getCustomUserDataView(java.lang.Long, java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getCustomUserDataView(Long userId, Long dispatchCenterId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			Collection<ResourceInventoryGridVo> vos = new ArrayList<ResourceInventoryGridVo>();
			vos = resourceInventoryDao.getGrid(dispatchCenterId, userId);
		
			Collection<ResourceInventoryGridVo> excludedVos = new ArrayList<ResourceInventoryGridVo>();
			excludedVos = resourceInventoryDao.getExcludedResources(userId);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_CUSTOM_USER_RES_VIEW");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			dialogueVo.setResultObjectAlternate(excludedVos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ResourceInventoryService#saveCustomView(java.lang.Long, java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveCustomView(Long userId, Collection<ResourceInventoryGridVo> excludedVos, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			
			// remove any excluded items that are now included
			Collection<Long> resourceIds = ResourceInventoryGridVo.toResourceIds(excludedVos);

			int cnt=resourceInventoryDao.removeExcludedItems(userId, resourceIds);		
			
			Collection<ResourceInventoryGridVo> excludedGridVos = resourceInventoryDao.getExcludedResources(userId);
			
			Collection<Long> resourceIdsToExclude = ResourceInventoryGridVo.toResourceIds(excludedGridVos);
			
			resourceIds.removeAll(resourceIdsToExclude);
			
			// add new excluded items
			cnt+=resourceInventoryDao.createExcludedItems(userId, resourceIds);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_CUSTOM_RESOURCE_VIEW");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
		    coaVo.setMessageVo(
					   new MessageVo(
							   "text.resourceInventory", 
							   "info.generic" , new String[]{"The custom data view was saved"}, 
							   MessageTypeEnum.INFO));
			
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(cnt);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
			
	}
}
