package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.ResourceKind;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.UserGroupUser;
import gov.nwcg.isuite.core.domain.UserNotification;
import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.core.domain.WorkAreaUser;
import gov.nwcg.isuite.core.domain.impl.KindImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceKindImpl;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.core.domain.impl.UserNotificationImpl;
import gov.nwcg.isuite.core.domain.impl.WorkAreaImpl;
import gov.nwcg.isuite.core.domain.impl.WorkAreaUserImpl;
import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.filter.SharedWorkAreaFilter;
import gov.nwcg.isuite.core.filter.WorkAreaFilter;
import gov.nwcg.isuite.core.filter.WorkAreaResourcesFilter;
import gov.nwcg.isuite.core.filter.impl.SharedWorkAreaFilterImpl;
import gov.nwcg.isuite.core.filter.impl.WorkAreaFilterImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.OrganizationDao;
import gov.nwcg.isuite.core.persistence.ResourceDao;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.persistence.UserGroupUserDao;
import gov.nwcg.isuite.core.persistence.UserNotificationDao;
import gov.nwcg.isuite.core.persistence.WorkAreaDao;
import gov.nwcg.isuite.core.persistence.WorkAreaUserDao;
import gov.nwcg.isuite.core.rules.WorkAreaSaveRulesHandler;
import gov.nwcg.isuite.core.service.WorkAreaService;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.IncidentSelectorVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.OrganizationPicklistVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.core.vo.UserGroupUserVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.WorkAreaGridVo;
import gov.nwcg.isuite.core.vo.WorkAreaInfoVo;
import gov.nwcg.isuite.core.vo.WorkAreaPicklistVo;
import gov.nwcg.isuite.core.vo.WorkAreaResourceGridVo;
import gov.nwcg.isuite.core.vo.WorkAreaUserVo;
import gov.nwcg.isuite.core.vo.WorkAreaVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.WorkAreaUserTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * Service access for the Work Area data objects.
 * 
 */
public class WorkAreaServiceImpl extends BaseService implements WorkAreaService {

	protected WorkAreaDao workAreaDao;
	protected WorkAreaUserDao workAreaUserDao;
	protected OrganizationDao organizationDao;
	protected IncidentDao incidentDao;
	protected ResourceDao resourceDao;
	protected UserDao userDao;
	protected UserGroupUserDao userGroupUserDao;

	public WorkAreaServiceImpl() {
		super();
	}

	public WorkAreaServiceImpl(final WorkAreaDao waDao, final WorkAreaUserDao wauDao, final OrganizationDao orgDao, final IncidentDao incDao, 
			final ResourceDao resDao, final UserDao userDao, final UserGroupUserDao userGroupUserDao) {
		super();
		if (waDao == null) {
			throw new IllegalArgumentException("workAreaDao cannot be null.  Check Spring config.");
		}
		this.workAreaDao = waDao;

		if (wauDao == null) {
			throw new IllegalArgumentException("workAreaUserDao cannot be null.  Check Spring config.");
		}
		this.workAreaUserDao = wauDao;

		if (orgDao == null) {
			throw new IllegalArgumentException("organizationDao cannot be null.  Check Spring config.");
		}
		this.organizationDao = orgDao;

		if (incDao == null) {
			throw new IllegalArgumentException("incidentDao cannot be null.  Check Spring config.");
		}
		this.incidentDao = incDao;

		if (resDao == null) {
			throw new IllegalArgumentException("resourceDao cannot be null.  Check Spring config.");
		}
		this.resourceDao = resDao;

		if (userDao == null) {
			throw new IllegalArgumentException("userDao cannot be null.  Check Spring config.");
		}
		this.userDao = userDao;

		if (userGroupUserDao == null) {
			throw new IllegalArgumentException("userGroupUserDao cannot be null.  Check Spring config.");
		}
		this.userGroupUserDao = userGroupUserDao;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.WorkAreaService#getAllOrganizationsByUserId(java.lang.Long)
	 */
	public Collection<OrganizationPicklistVo> getAllOrganizationsByUserId(Long userId)throws ServiceException {
		if (userId == null || userId == 0) {
			throw new ServiceException("UserId is required.");
		}

		try {
			return organizationDao.getAllOrganizationsByUserId(userId);
		}
		catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.service.access.WorkAreaService#getGrid(gov.nwcg.isuite.domain.access.WorkAreaFilter)
	 */
	public Collection<WorkAreaGridVo> getManageWorkAreaGrid(WorkAreaFilter filter) throws ServiceException {
		try {
			if (filter == null) {
				filter = new WorkAreaFilterImpl();
			}
			if (filter.getCurrentUserId() == null) {
				filter.setCurrentUserId(super.getUserSessionVo().getUserId());
			}
			Collection<WorkAreaGridVo> vos = workAreaDao.getGrid(filter);
			for(WorkAreaGridVo waVo : vos) {
			   if(waVo.getStandardOrgId() == null) {
			      waVo.setDeletable(true);
			   } else {
			      waVo.setDeletable(false);
			   }
			}
			return vos;
		}
		catch ( Exception e ) {
			super.handleException(e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.WorkAreaService#getManageWorkAreaGridItem(java.lang.Long, java.lang.Long)
	 */
	public WorkAreaGridVo getManageWorkAreaGridItem(Long userId,Long workAreaId) throws ServiceException {
		try{
			return workAreaDao.getGridItem(userId, workAreaId);
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.service.access.WorkAreaService#getPicklist(gov.nwcg.isuite.domain.access.WorkAreaFilter)
	 */
	public Collection<WorkAreaPicklistVo> getPicklist(WorkAreaFilter filter) throws ServiceException {
		try {
			if (filter == null) {
				filter = new WorkAreaFilterImpl();
			}
			if (filter.getCurrentUserId() == null) {
				filter.setCurrentUserId(super.getUserSessionVo().getUserId());
			}
			return workAreaDao.getPicklist(filter);
		}
		catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.service.access.WorkAreaService#addIncidentsToWorkArea(java.util.Collection, java.lang.Long)
	 */
	public void addIncidentsToWorkArea(Collection<Integer> incidentIds, Long workAreaId) throws ServiceException {
		try {
			WorkArea wa = getWorkArea(workAreaId);
			if (wa == null) {
				throw new ServiceException("Cannot retrieve work area for adding incidents.");
			}

			Collection<Incident> incidents = incidentDao.getAllByIds(IntegerUtility.convertToLongs(incidentIds));
			wa.getWorkAreaIncidents().addAll(incidents);
			workAreaDao.save(wa);
		}
		catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.service.access.WorkAreaService#addResourcesToWorkArea(java.util.Collection, java.lang.Long)
	 */
	public void addResourcesToWorkArea(Collection<Integer> resourceIds, Long workAreaId) throws ServiceException {
		if (resourceIds == null || workAreaId == null || workAreaId == 0) {
			throw new ServiceException("ResourceIds and workAreaId is required.");
		}
		try {
			WorkArea wa = getWorkArea(workAreaId);
			if (wa == null) {
				throw new ServiceException("Cannot retrieve work area for adding resources.");
			}
			Collection<Resource> resources = resourceDao.getAllByIds(IntegerUtility.convertToLongs(resourceIds));
			
			Collection<Resource> resourcesToSave = new ArrayList<Resource>();
			
			for (Resource resource : resources) {
				Resource cpyEntity;
				//if resource is permanent, than it is a work area resource, otherwise it is an incident resource
				if (resource.isPermanent()) {
					cpyEntity = this.copyResource(resource, resource);
				} 
				else {
					//create permanent resource
					Resource permEntity = this.copyResource(resource, null);
					//create a copy to associate with the work area
					cpyEntity = this.copyResource(resource, permEntity);
					//associate the incident resource with the permanent resource
					resource.setPermanentResource(cpyEntity);
					resourcesToSave.add(resource);
					
				}
				
				resourcesToSave.add(cpyEntity);
			}
			
			resourceDao.saveAll(resourcesToSave);
		}
		catch ( Exception e ) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * Makes copy of a resource
	 * 
	 * @param resource
	 * @return Resource
	 * @throws Exception
	 */
	public Resource copyResource(Resource resourceToCopy, Resource permanentResource) throws Exception {
		Resource copy = new ResourceImpl();
		copy.setAgency(resourceToCopy.getAgency());
		copy.setContracted(resourceToCopy.isContracted());
		copy.setPerson(resourceToCopy.isPerson());
		copy.setEnabled(resourceToCopy.isEnabled());
		copy.setPermanent(permanentResource == null);
		copy.setLeader(resourceToCopy.isLeader());
		copy.setContactName(resourceToCopy.getContactName());
		copy.setEmail(resourceToCopy.getEmail());
		copy.setFirstName(resourceToCopy.getFirstName());
		copy.setLastName(resourceToCopy.getLastName());
		copy.setResourceName(resourceToCopy.getResourceName());
		copy.setOrganization(resourceToCopy.getOrganization());
		copy.setPrimaryDispatchCenter(resourceToCopy.getPrimaryDispatchCenter());
		copy.setNameOnPictureId(resourceToCopy.getNameOnPictureId());
		copy.setOther1(resourceToCopy.getOther1());
		copy.setOther2(resourceToCopy.getOther2());
		copy.setOther3(resourceToCopy.getOther3());
		if (null != permanentResource) {
			//copy.setPermanentResourceId(permanentResource.getId());
			copy.setPermanentResource(permanentResource);
		}
		
		copy.setParentResourceId(resourceToCopy.getParentResourceId());
		copy.setPhone(resourceToCopy.getPhone());
		copy.setResourceClassification(resourceToCopy.getResourceClassification());
		copy.setResourceStatus(resourceToCopy.getResourceStatus());
		
		for (ResourceKind rk : resourceToCopy.getResourceKinds()) {
			ResourceKind resourceKind = new ResourceKindImpl();
			Kind kind = new KindImpl();
			kind.setId(rk.getKind().getId());
			resourceKind.setKind(kind);
			resourceKind.setPrimary(rk.getPrimary());
			resourceKind.setTraining(rk.getTraining());
			resourceKind.setResource(copy);
			copy.getResourceKinds().add(resourceKind);
		}
		return copy;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.service.access.WorkAreaService#addUsersToSharedWorkArea(java.util.Collection, java.lang.Long)
	 */
	public void addUsersToSharedWorkArea(Collection<UserGridVo> userGridVos, Collection<SystemRoleVo> roleVos,Long workAreaId) throws ServiceException {
		if (workAreaId == null || workAreaId == 0 || userGridVos == null) {
			throw new ServiceException("workAreaId are required");
		}
		try {
			WorkArea waEntity = getWorkArea(workAreaId);

			for (UserGridVo userGridVo : userGridVos) {
				User userEntity = userDao.getById(userGridVo.getId(), UserImpl.class);
				UserVo uvo = UserVo.getInstance(userEntity, true);

				WorkAreaUser wauEntity = new WorkAreaUserImpl();
				wauEntity.setUserType(WorkAreaUserTypeEnum.SHARED);
				wauEntity.setUser(UserVo.toEntity(null,uvo, true));
				wauEntity.setSharedByUser(UserVo.toEntity(null, super.getUserVo(), false));
//				wauEntity.setWorkAreaUserRoles(SystemRoleVo.toEntityList(roleVos,false));
				
				wauEntity.setWorkArea(waEntity);

				// set shared user default roles to the users roles
				/*
			   for (SystemRoleVo roleVo : uvo.getUserRoleVos()) {
				   wauEntity.getWorkAreaUserRoles().add(SystemRoleVo.toEntity(roleVo, false));
			   }
				 */

				workAreaUserDao.save(wauEntity);
			}

			setWorkAreaSharedOutFlag(waEntity);

		} catch ( Exception e ) {
			super.handleException(e);
		} 
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.service.access.WorkAreaService#getAvailableIncidentsForWorkArea(gov.nwcg.isuite.domain.incident.IncidentFilter)
	 */
	public Collection<IncidentGridVo> getAvailableWorkAreaIncidents(IncidentFilter filter) throws ServiceException {
		if (filter == null || filter.getWorkAreaId() == null) {
			throw new ServiceException("Required field: workAreaId not provided.");
		}
		try {
			filter.setCurrentUserId(super.getUserVo().getId());
			return workAreaDao.getAvailableWorkAreaIncidents(filter, super.getUserSessionVo().getPrivilegedUser());
		}
		catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}

	public Collection<WorkAreaResourceGridVo> getAvailableWorkAreaResources(WorkAreaResourcesFilter filter) throws ServiceException {
		if (filter == null || filter.getWorkAreaId() == null || filter.getWorkAreaId() == 0) {
			throw new ServiceException("Required field: workAreaId not provided.");
		}
		try {
			filter.setCurrentUserId(super.getUserVo().getId());
			return workAreaDao.getAvailableWorkAreaResources(filter);
		}
		catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.service.access.WorkAreaService#getUsersAvailableForSharedWorkArea(gov.nwcg.isuite.domain.access.UserFilter)
	 */
	public Collection<UserGridVo> getUsersAvailableForSharedWorkArea(SharedWorkAreaFilter filter, Long workAreaId) throws ServiceException {
		if (filter == null || filter.getCurrentUserId() == 0 || filter.getCurrentUserId() == null || workAreaId == 0) {
			throw new ServiceException("Work area id and current user id required to call this method.");
		}

		Collection<UserGridVo> workAreaUsers = new ArrayList<UserGridVo>();

		try {
			workAreaUsers = workAreaUserDao.getUsersAvailableForSharedWorkArea(filter, workAreaId);
		} catch (PersistenceException pe) {
			throw new ServiceException(pe);
		}
		return workAreaUsers;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.service.access.WorkAreaService#getUsersFromSharedWorkArea(gov.nwcg.isuite.domain.access.UserFilter)
	 */
	public Collection<UserGridVo> getUsersFromSharedWorkArea(SharedWorkAreaFilter filter, Long workAreaId) throws ServiceException {
		Collection<UserGridVo> userGridVos = new ArrayList<UserGridVo>();
		try {
			filter.setSharedUsers(true);
			userGridVos = workAreaUserDao.getUsersByWorkAreaId(filter, workAreaId);

		} catch (PersistenceException e) {
			throw new ServiceException(e);
		}
		return userGridVos;
	}

	public void addUserGroupToWorkArea(Long workAreaId, Long userGroupId) throws ServiceException {
		if (null == workAreaId || null == userGroupId || workAreaId == 0 || userGroupId == 0) {
			throw new ServiceException("WorkAreaId and UserGroupId are required.");
		}

		try {
			WorkArea waEntity = workAreaDao.getById(workAreaId, WorkAreaImpl.class);

			Collection<Long> uguids = new ArrayList<Long>();
			uguids.add(userGroupId);

			// get users in user group
			Collection<UserGroupUser> uguEntities = userGroupUserDao.getUserGroupUsers(uguids);

			//Get users already assigned to the work area
			SharedWorkAreaFilter sharedWorkAreaFilter = new SharedWorkAreaFilterImpl();
			sharedWorkAreaFilter.setSharedUsers(false);
			Collection<UserGridVo> waUserGridVos = workAreaUserDao.getUsersByWorkAreaId(sharedWorkAreaFilter, workAreaId);

			// collection of workareausers to add
			Collection<WorkAreaUser> wauEntities = new ArrayList<WorkAreaUser>();

			for(UserGroupUser uguEntity : uguEntities){
				Boolean userAdded=false;

				UserGroupUserVo uguVo = null;

				try{
					uguVo=UserGroupUserVo.getInstance(uguEntity, true);
				}catch(Exception e){
					super.handleException(e);
				}

				for(UserGridVo ugvo : waUserGridVos){
					if(ugvo.getId().compareTo(uguVo.getUserVo().getId())==0){
						userAdded=true;
						break;
					}
				}

				if(!userAdded){
					WorkAreaUser wauEntity = new WorkAreaUserImpl();
					wauEntity.setWorkArea(waEntity);

					try{
						wauEntity.setUser(UserVo.toEntity(null, uguVo.getUserVo(), false));
						wauEntity.setUserType(WorkAreaUserTypeEnum.SHARED);
						wauEntity.setSharedByUser(UserVo.toEntity(null, super.getUserVo(), false));
//						wauEntity.setWorkAreaUserRoles(SystemRoleVo.toEntityList(uguVo.getUserGroupUserRoles(),false));
					}catch(Exception e){
						super.handleException(e);
					}

					wauEntities.add(wauEntity);
				}

			}

			if(wauEntities.size()>0)
				workAreaUserDao.saveAll(wauEntities);

			setWorkAreaSharedOutFlag(waEntity);

		}
		catch ( PersistenceException e ) {
			super.handleException(e);
		}



	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.service.access.WorkAreaService#getWorkAreaIncidents(gov.nwcg.isuite.domain.incident.IncidentFilter)
	 */
	public Collection<IncidentGridVo> getWorkAreaIncidents(IncidentFilter filter) throws ServiceException {
		if (filter == null || filter.getWorkAreaId() == null) {
			throw new ServiceException("Required field: workAreaId not provided.");
		}

		try {
			return workAreaDao.getWorkAreaIncidents(filter, ((UserSessionVo)super.context.getBean("userSessionVo")).getUserId());
		}
		catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}

	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.WorkAreaService#getWorkAreaIncidentSelectorVos(gov.nwcg.isuite.core.filter.IncidentFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getWorkAreaIncidentSelectorVos(IncidentFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			
			if(null == filter)
				super.handleException(ErrorEnum._90000_ERROR,"null IncidentFilter");

			if(!LongUtility.hasValue(filter.getWorkAreaId())) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"WorkArea with null id");

			Collection<IncidentSelectorVo> vos = new ArrayList<IncidentSelectorVo>();

	        vos = workAreaDao.getWorkAreaIncidentSelectorData(filter);
	        
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_WORK_AREA_INCIDENT_SELECTOR_VOS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo getUserWorkAreaVos(WorkAreaFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			
			if(null == filter)
				super.handleException(ErrorEnum._90000_ERROR,"null WorkAreaFilter");

			if(!LongUtility.hasValue(filter.getCurrentUserId())) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"User WorkAreas with null currentUserId");

			Collection<WorkAreaVo> vos = new ArrayList<WorkAreaVo>();

			Collection<WorkArea> waEntities = this.workAreaDao.getWorkAreasForUser(filter);
			
			if(CollectionUtility.hasValue(waEntities))
				vos = WorkAreaVo.getInstances(waEntities, true);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_USER_WORK_AREA_VOS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.WorkAreaService#getWorkAreaIncidentSelectorData(java.lang.Long)
	 */
	public Collection<IncidentSelectorVo> getWorkAreaIncidentSelectorData(Long workAreaId) throws ServiceException {
      IncidentGroupDao incidentGroupDao = (IncidentGroupDao)super.context.getBean("incidentGroupDao");
      if (workAreaId == null) {
         throw new ServiceException("Required field: workAreaId not provided.");
      }
      
      Collection<IncidentSelectorVo> isIncVos = new ArrayList<IncidentSelectorVo>();
      Collection<IncidentSelectorVo> isIncGroupVos = new ArrayList<IncidentSelectorVo>();
      try {
         isIncVos = workAreaDao.getWorkAreaIncidentSelectorData(workAreaId);
         isIncGroupVos = incidentGroupDao.getWorkAreaIncidentGroupSelectorData(workAreaId);
         
         //Add the incident groups to display in the WorkAreaIncidentSelector.incidentSelector drop down.
         Collection<IncidentSelectorVo> incidentSelectorVos = new ArrayList<IncidentSelectorVo>();
         for(IncidentSelectorVo vo : isIncGroupVos) {
            IncidentSelectorVo isVo = new IncidentSelectorVo();
            isVo.setIncidentName(vo.getIncidentGroupName());
            isVo.setIncidentGroupId(vo.getIncidentGroupId());
            isVo.setIsIncidentGroup(true);
            incidentSelectorVos.add(isVo);
         }
         
         isIncVos.addAll(incidentSelectorVos);
         return isIncVos;
      }
      catch ( PersistenceException e ) {
         throw new ServiceException(e);
      }
   }

	public WorkAreaVo getById(Long workAreaId) throws ServiceException {

		WorkAreaVo vo = null;

		try{
			if ( null == workAreaId || workAreaId == 0 ) {
				throw new ServiceException("Cannot retrieve work area for editing or copying.");
			}

			WorkArea entity = workAreaDao.getById(workAreaId, WorkAreaImpl.class);

			if (null == entity) {
				throw new ServiceException("Cannot retrieve work area for editing or copying.");
			}

			vo = WorkAreaVo.getInstance(entity, true);

		}catch(Exception e){
			super.handleException(e);
		}

		return vo;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.service.access.WorkAreaService#getWorkAreaInfo(java.lang.Long, java.lang.Boolean)
	 */
	public WorkAreaInfoVo getWorkAreaInfo(Long workAreaId, Boolean copy) throws ServiceException {

		if ( null == workAreaId || workAreaId == 0 ) {
			throw new ServiceException("Cannot retrieve work area for editing or copying.");
		}

		try {
			WorkArea wa = workAreaDao.getById(workAreaId, WorkAreaImpl.class);
			if (wa == null) {
				throw new ServiceException("Cannot retrieve work area for editing or copying.");
			}
			WorkAreaInfoVo vo = new WorkAreaInfoVo(wa);

			List<OrganizationPicklistVo> orgPicklist = new ArrayList<OrganizationPicklistVo>();

			for (Organization org : wa.getFilteredOrganizations()) {
				orgPicklist.add(new OrganizationPicklistVo(org));
			}   

			vo.setSelectedOrganizations(orgPicklist);

			if ( copy ) {
				vo.setWaCreatedDt(null);
				vo.setCreatedByWhom(null);
				vo.setWorkAreaIdToBeCopied(vo.getId());
				vo.setId(null);
			}
			return vo;
		}
		catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}

	public Collection<WorkAreaResourceGridVo> getWorkAreaResources(WorkAreaResourcesFilter filter) throws ServiceException {
		if (filter == null || filter.getWorkAreaId() == null) {
			throw new ServiceException("Required field: workAreaId not provided.");
		}

		try {
			return workAreaDao.getWorkAreaResources(filter);
		}
		catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.service.access.WorkAreaService#removeUsersFromSharedWorkArea(java.util.Collection, java.lang.Long)
	 */
	public void removeUsersFromSharedWorkArea(Collection<Integer> workAreaUserIds, Long workAreaId) throws ServiceException {
		try {
			for(Long workAreaUserId : IntegerUtility.convertToLongs(workAreaUserIds)) {
				WorkAreaUser workAreaUser = workAreaUserDao.getById(workAreaUserId, WorkAreaUserImpl.class);
				if(workAreaUser != null) {
					//If this workAreaUser is the currently logged in user, do not delete.
					if(!workAreaUser.getUserId().equals(super.getUserSessionVo().getUserId())) {
						workAreaUserDao.delete(workAreaUser);
					}else{
						throw new ServiceException(super.getIsuiteProperty("error.workAreaOwnersCannotRemoveThemselves"));
					}
				}
			}
			setWorkAreaSharedOutFlag(workAreaId);

		} catch (PersistenceException e) {
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.service.access.WorkAreaService#save(gov.nwcg.isuite.domain.access.WorkAreaInfoVo)
	 */
	public WorkAreaVo save(WorkAreaVo vo) throws ServiceException {
		if (vo != null) {
			try {
				Long userId = super.getUserSessionVo().getUserId();

				WorkArea entity = null;	

				// if this is a work area being edited, get work area 
				if (null != vo.getId() && vo.getId() != 0) {
					entity = workAreaDao.getById(vo.getId(), WorkAreaImpl.class);

					// clear the orgs associated to ensure only the ones selected are included.
					entity.getFilteredOrganizations().clear();
					
					if(!entity.getName().equals(vo.getName())){
						// verify new name is unique
						if(!workAreaDao.isWorkAreaNameUniqueToUser(entity.getId(), vo.getName(), super.getUserVo().getId()))
							throw new ServiceException(ErrorEnum._0058_WORK_AREA_NAME_ALREADY_ASSIGNED);
						
						if(!workAreaDao.isWorkAreaNameUniqueStandard(vo.getName()))
							throw new ServiceException(ErrorEnum._0058_WORK_AREA_NAME_ALREADY_ASSIGNED);
					}
						
				}else{
					// new work area, check the name uniqueness
					if(!workAreaDao.isWorkAreaNameUniqueToUser(null, vo.getName(), super.getUserVo().getId()))
						throw new ServiceException(ErrorEnum._0058_WORK_AREA_NAME_ALREADY_ASSIGNED);
					
					if(!workAreaDao.isWorkAreaNameUniqueStandard(vo.getName()))
						throw new ServiceException(ErrorEnum._0058_WORK_AREA_NAME_ALREADY_ASSIGNED);
				}

				vo.setUserVo(super.getUserVo());

				if(null == entity){
					/*
					 * User is adding a new work area
					 */
					WorkAreaUserVo wauVo = new WorkAreaUserVo();
					wauVo.setUserVo(super.getUserVo());
					wauVo.setUserType(WorkAreaUserTypeEnum.OWNER);

					for (SystemRoleVo systemRoleVo : super.getUserVo().getUserRoleVos()) {
						SystemRoleVo srVo = new SystemRoleVo();
						srVo.setId(systemRoleVo.getId());
						wauVo.getWorkAreaUserRoleVos().add(srVo);
					}

					vo.getWorkAreaUserVos().add(wauVo);
				}else{
					vo.setWorkAreaUserVos(WorkAreaUserVo.getInstances(entity.getWorkAreaUsers(), true));
				}

				entity = WorkAreaVo.toEntity(entity,vo,true);

				workAreaDao.save(entity);

				Long id = entity.getId();
				workAreaDao.getHibernateSession().flush();
				workAreaDao.getHibernateSession().evict(entity);

				return this.getById(id);
			} 
			catch ( Exception e ) {
				super.handleException(e);
			}
			
			return null;
		}else{
			throw new ServiceException("Unable to save null object");
		}
	
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.WorkAreaService#saveWorkArea(gov.nwcg.isuite.core.vo.WorkAreaVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveWorkArea(WorkAreaVo vo, DialogueVo dialogueVo) throws ServiceException {
		
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			
			WorkArea entity = null;	
			if(LongUtility.hasValue(vo.getId()))
				entity = workAreaDao.getById(vo.getId(), WorkAreaImpl.class);
				
			WorkAreaSaveRulesHandler ruleHandler = new WorkAreaSaveRulesHandler(context);
			if(ruleHandler.execute(vo, entity, dialogueVo)==AbstractRule._OK){

				vo.setUserVo(super.getUserVo());

				if(null == entity){
					/*
					 * User is adding a new work area
					 */
					WorkAreaUserVo wauVo = new WorkAreaUserVo();
					wauVo.setUserVo(super.getUserVo());
					wauVo.setUserType(WorkAreaUserTypeEnum.OWNER);

					for (SystemRoleVo systemRoleVo : super.getUserVo().getUserRoleVos()) {
						SystemRoleVo srVo = new SystemRoleVo();
						srVo.setId(systemRoleVo.getId());
						wauVo.getWorkAreaUserRoleVos().add(srVo);
					}

					vo.getWorkAreaUserVos().add(wauVo);
					
				}else{
					// clear the orgs associated to ensure only the ones selected are included.
					entity.getFilteredOrganizations().clear();
					vo.setWorkAreaUserVos(WorkAreaUserVo.getInstances(entity.getWorkAreaUsers(), true));
				}
				
				entity = WorkAreaVo.toEntity(entity,vo,true);
				workAreaDao.save(entity);

				Long id = entity.getId();
				workAreaDao.flushAndEvict(entity);
				vo=this.getById(id);
				
				// build coa
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("COMPLETE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.workArea", "info.0030" , null, MessageTypeEnum.INFO));
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
	 * @see gov.nwcg.isuite.service.access.WorkAreaService#updateSharedWorkAreaUserRoles(gov.nwcg.isuite.domain.access.UserGridVo)
	 */
	public void updateSharedWorkAreaUserRoles(UserGridVo userGridVo) throws ServiceException {
		/*
	   if (userGridVo.getWorkAreaUserId() == null  || userGridVo.getWorkAreaUserId() == 0) {
		   throw new ServiceException("Work area user ID is required for this method.");
	   }

	   try {
		   WorkAreaUser waUser = workAreaUserDao.getById(userGridVo.getWorkAreaUserId(), WorkAreaUserImpl.class);

		   //remove existing authorities in for a work area user before saving new authorities
		   workAreaUserAuthorityDao.deleteAllWithWorkAreaUserId(userGridVo.getWorkAreaUserId());

		   Collection<WorkAreaUserAuthority> workAreaUserAuthorities = new ArrayList<WorkAreaUserAuthority>();
		   for (RoleVo roleVo : userGridVo.getUserRoles()) {
			   WorkAreaUserAuthority workAreaUserAuthority = new WorkAreaUserAuthorityImpl();
			   try {
				   workAreaUserAuthority.setWorkAreaUser(waUser);
        		 	workAreaUserAuthority.setRole(roleVo.toEntity(null));
			   } catch(Exception e) {
				   throw new ServiceException(e);
			   }
			   workAreaUserAuthorities.add(workAreaUserAuthority);
		   }

		   waUser.setWorkAreaUserAuthorities(workAreaUserAuthorities);
		   workAreaUserDao.save(waUser);
	   }
	   catch ( PersistenceException e ) {
		   throw new ServiceException(e);
	   }
		 */
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.service.access.WorkAreaService#removeIncidentsFromWorkArea(java.util.Collection, java.lang.Long)
	 */
	public void removeIncidentsFromWorkArea(Collection<Integer> incidentIds, Long workAreaId) throws ServiceException {
		if (workAreaId == null) {
			throw new ServiceException("Work Area ID is required for this method.");
		}

		try {
			workAreaDao.removeIncidentsFromWorkArea(IntegerUtility.convertToLongs(incidentIds), workAreaId);
		}
		catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.service.access.WorkAreaService#removeResourcesFromWorkArea(java.util.Collection, java.lang.Long)
	 */
	public void removeResourcesFromWorkArea(Collection<Integer> resourceIds, Long workAreaId) throws ServiceException {
		if (workAreaId == null) {
			throw new ServiceException("Work Area ID is required for this method.");
		}

		try {
			workAreaDao.removeResourcesFromWorkArea(IntegerUtility.convertToLongs(resourceIds), workAreaId);
		}
		catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.WorkAreaService#removeCustomWorkArea(java.lang.Long, java.lang.Long)
	 */
	public Long removeCustomWorkArea(Long workAreaId, Long userId) throws ServiceException {
		try {
			WorkArea wa = workAreaDao.getById(workAreaId, WorkAreaImpl.class);

			if(wa.getName().equalsIgnoreCase("MY RESTRICTED WORK AREA")){
				throw new Exception("Cannot delete the system generated 'MY RESTRICTED WORK AREA'.");
			}
			
			
			if (wa.getUserId().longValue() == userId.longValue()) { //if this is a custom work area created by the user.. 
				wa.getFilteredOrganizations().clear();
				wa.getWorkAreaIncidents().clear();
				wa.getWorkAreaResources().clear();

				Collection<WorkAreaUser> waUsers=wa.getWorkAreaUsers();
				// notify all work area shared users
				Collection<UserNotification> notifications = new ArrayList<UserNotification>();
				for(WorkAreaUser wauEntity : waUsers){
					if(wauEntity.getUserId().compareTo(super.getUserVo().getId())!=0){
						UserNotification notif = new UserNotificationImpl();
						notif.setUser(wauEntity.getUser());
						notif.setSource("System");
						notif.setPostedDate(Calendar.getInstance().getTime());
						notif.setMessage(getIsuiteProperty("info.0068",wa.getName()));
						notifications.add(notif);
					}
				}

				wa.getWorkAreaUsers().clear();
				wa.getIncidents().clear();
				workAreaDao.delete(wa);

				if(notifications.size()>0){
					UserNotificationDao notifDao = (UserNotificationDao)super.context.getBean("userNotificationDao");
					notifDao.saveAll(notifications);
				}
			}
			else { //if this is a work area shared to you.. 
				WorkAreaUser wau = workAreaUserDao.getByWorkAreaIdAndUserId(workAreaId, userId);
				workAreaUserDao.delete(wau);
				setWorkAreaSharedOutFlag(wa);
			}
			
			return workAreaId;
			
		}
		catch ( Exception e ) {
			super.handleException(e);
		}
		
		return null;
	}

	private WorkArea getWorkArea(Long id) throws PersistenceException{
		return workAreaDao.getById(id, WorkAreaImpl.class);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.service.access.WorkAreaService#setWorkAreaSharedOutFlag(java.lang.Long)
	 */
	private void setWorkAreaSharedOutFlag(Long workAreaId) throws ServiceException {
		try {
			setWorkAreaSharedOutFlag(getWorkArea(workAreaId));
		}
		catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}

	/**
	 * If the {@link WorkArea} has been shared, set the sharedOutFlg to true.
	 * <br>
	 * If the {@link WorkArea} has NOT been shared, set the sharedOutFlg to false.
	 * @param workArea
	 * @param workAreaId
	 * @throws ServiceException
	 */
	private void setWorkAreaSharedOutFlag(WorkArea workArea) throws ServiceException {
		try {
			Boolean shared = workArea.isSharedOut();
			int workAreaUserCount = workAreaUserDao.getWorkAreaUserCountByWorkAreaId(workArea.getId());
			if(workAreaUserCount <= 1) {
				workArea.setSharedOut(false);
			} else {
				workArea.setSharedOut(true);
			}
			if (shared != workArea.isSharedOut()) {
				workAreaDao.save(workArea);
			}
		}
		catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.WorkAreaService#getWorkAreaPickListByUserId(java.lang.Long)
	 */
	public Collection<WorkAreaPicklistVo> getWorkAreaPickListByUserId(Long userId) throws ServiceException {
		Collection<WorkAreaPicklistVo> workAreaPickListVos = null;
		try {
			workAreaPickListVos = this.workAreaDao.getWorkAreaPickListByUserId(userId);
		} catch (PersistenceException pe) {
			throw new ServiceException(pe);
		}
		return workAreaPickListVos;
	}

	public Collection<OrganizationVo> getAssociatedUserOrganizations(Long userId) throws ServiceException {	
		Collection<OrganizationVo> vos = new ArrayList<OrganizationVo>();

		try{
			vos = workAreaDao.getAssociatedUserOrganizations(userId);

		}catch(Exception e){
			super.handleException(e);
		}

		return vos;
	}

	public WorkAreaUserVo getWorkAreaUserById(Long workAreaUserId) throws ServiceException {
		try{
			WorkAreaUser entity = workAreaUserDao.getById(workAreaUserId, WorkAreaUserImpl.class);

			if(null==entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"WorkAreaUser");

			return WorkAreaUserVo.getInstance(entity, true);
		}catch(Exception e){
			super.handleException(e);
		}

		return null;
	}

	public void saveWorkAreaUserRoles(WorkAreaUserVo vo) throws ServiceException {

		try{

			WorkAreaUser entity = workAreaUserDao.getById(vo.getId(), WorkAreaUserImpl.class);

			if(null==entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"WorkAreaUser");

//			entity.getWorkAreaUserRoles().clear();

//			entity.setWorkAreaUserRoles(SystemRoleVo.toEntityList(vo.getWorkAreaUserRoleVos(), false));

			workAreaUserDao.save(entity);

		}catch(Exception e){
			super.handleException(e);
		}

	}

	public WorkAreaVo getCopyById(Long workAreaId) throws ServiceException {

		try{
			WorkArea entity = workAreaDao.getById(workAreaId, WorkAreaImpl.class);

			if(null==entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"WorkArea");

			WorkAreaVo vo = new WorkAreaVo();
			vo.setId(null);
			vo.setSharedOut(false);
			vo.setName("");
			vo.setDescription("");

			if(null != entity.getFilteredOrganizations())
				vo.setFilteredOrganizations(OrganizationVo.getInstances(entity.getFilteredOrganizations(), true));

			if(null != entity.getWorkAreaIncidents())
				vo.setWorkAreaIncidentVos(IncidentVo.getInstances(entity.getWorkAreaIncidents(), false));

			if(null != entity.getWorkAreaResources())
				vo.setWorkAreaResourceVos(ResourceVo.getInstances(entity.getWorkAreaResources(), false));

			return vo;

		}catch(Exception e){
			super.handleException(e);
		}

		return null;
	}

}
