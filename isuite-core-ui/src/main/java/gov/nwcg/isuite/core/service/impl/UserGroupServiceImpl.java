package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.UserGroup;
import gov.nwcg.isuite.core.domain.UserGroupUser;
import gov.nwcg.isuite.core.domain.impl.UserGroupImpl;
import gov.nwcg.isuite.core.domain.impl.UserGroupUserImpl;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.filter.UserGroupFilter;
import gov.nwcg.isuite.core.filter.impl.UserFilterImpl;
import gov.nwcg.isuite.core.filter.impl.UserGroupFilterImpl;
import gov.nwcg.isuite.core.persistence.UserGroupDao;
import gov.nwcg.isuite.core.persistence.UserGroupUserDao;
import gov.nwcg.isuite.core.service.UserGroupService;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.core.vo.UserGroupGridVo;
import gov.nwcg.isuite.core.vo.UserGroupPicklistVo;
import gov.nwcg.isuite.core.vo.UserGroupUserVo;
import gov.nwcg.isuite.core.vo.UserGroupVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class allows a user
 * to add/edit/and otherwise modify a UserGroup in the system
 * 
 * @author mpoll
 */
public class UserGroupServiceImpl extends BaseService implements UserGroupService {
	private UserGroupDao userGroupDao;
	private UserGroupUserDao userGroupUserDao;
//	private UserDao userDao;

	/*
	 * Default Constructor
	 */
	public UserGroupServiceImpl() {}

	public void initialization(){
		userGroupDao = (UserGroupDao)super.context.getBean("userGroupDao");
		userGroupUserDao = (UserGroupUserDao)super.context.getBean("userGroupUserDao");
//		userDao = (UserDao)super.context.getBean("userDao");
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.service.access.enterprise.EnterpriseUserGroupService#getById(java.lang.Long)
	 */
	public UserGroupVo getById(Long id) throws ServiceException {
		UserGroup entity =null;
		try {
			if (id == null) {
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"UserGoup[id=null]");
			}

			entity = userGroupDao.getById(id, UserGroupImpl.class);
			
			if (null == entity) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"UserGroup");
			
			return UserGroupVo.getInstance(entity, true);
			
		}catch ( Exception e ) {
			super.handleException(e);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.service.access.enterprise.EnterpriseUserGroupService#getGrid(gov.nwcg.isuite.domain.access.enterprise.UserGroupFilter)
	 */
	public Collection<UserGroupGridVo> getGrid(UserGroupFilter filter) throws ServiceException {
		try {
			if (filter == null) {
				filter = new UserGroupFilterImpl();
			}
			filter.setCurrentUserId(super.getUserSessionVo().getUserId());
			filter.setCreatedDate(DateUtil.setTimeStampToZero(filter.getCreatedDate()));

			Collection<UserGroupGridVo> uggVos = userGroupDao.getGrid(filter);
			for(UserGroupGridVo uggVo : uggVos) {
			   if(uggVo.getGroupOwnerId().equals(super.getUserSessionVo().getUserId())) {
			      uggVo.setDeletable(true);
			   } else {
			      uggVo.setDeletable(false);
			   }
			}
			return uggVos;
		}
		catch ( Exception e ) {
			super.handleException(e);
		}
		return null;
	}

	public UserGroupVo save(UserGroupVo vo) throws ServiceException {
		UserGroup entity = null;
		
		try{
			if( (null==vo.getId()) || (vo.getId() < 1)) {
				
//		         if (!userGroupDao.isGroupNameUniqueToUser(super.getUserSessionVo().getUserId(), vo.getGroupName().toUpperCase(),null)) 
//		             super.handleException(ErrorEnum._0174_DUPLICATE_USER_GROUP_NAME);
				 if (!userGroupDao.isGroupNameUniqueToSystem(vo.getGroupName().toUpperCase(),null)) 
		             super.handleException(ErrorEnum._0175_DUPLICATE_USER_GROUP_NAME_IN_SYSTEM);
		         

			}else {
//		         if (!userGroupDao.isGroupNameUniqueToUser(super.getUserSessionVo().getUserId(), vo.getGroupName().toUpperCase(),vo.getId())) 
//		             super.handleException(ErrorEnum._0174_DUPLICATE_USER_GROUP_NAME);
		         if (!userGroupDao.isGroupNameUniqueToSystem(vo.getGroupName().toUpperCase(),vo.getId())) 
		             super.handleException(ErrorEnum._0175_DUPLICATE_USER_GROUP_NAME_IN_SYSTEM);
		         
				entity = userGroupDao.getById(vo.getId(), UserGroupImpl.class);
				
				if (null == entity) 
					super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"UserGroup");
				
	            if (super.getUserSessionVo().getUserId().compareTo(entity.getGroupOwnerId()) != 0) 
	            	super.handleException(ErrorEnum._90000_ERROR,"Cannot save or change a User Group for a different Group Owner.");
	            
			}

			vo.setGroupOwnerVo(super.getUserVo());
			
			entity = UserGroupVo.toEntity(vo, true);
			
			for (UserGroupUser userGroupUser : entity.getUserGroupUsers()) {
				userGroupUser.setUserGroup(entity);
			}
			
			userGroupDao.save(entity);

			return UserGroupVo.getInstance(entity, true);
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}
	
	public Collection<UserGridVo> getAvailableUsersGrid(Long userGroupId, UserFilter filter) throws ServiceException {
		try {
			if (userGroupId == null) {
				throw new ServiceException("userGroupId cannot be null");
			}

			//Set Current User Id to logged in user
			if (filter == null) 
				filter = new UserFilterImpl();
			
			filter.setCurrentUserId(super.getUserSessionVo().getUserId());

			Collection<UserGridVo> vos = userGroupDao.getAvailableUsers(userGroupId, filter);

			return vos;
		}
		catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}
	
	public Collection<UserGroupUserVo> getUsersInGroupGrid(Long userGroupId, UserFilter filter) throws ServiceException {
		try {
			if (userGroupId == null) {
				throw new ServiceException("userGroupId cannot be null");
			}
			
			Collection<UserGroupUserVo> vos = userGroupUserDao.getUsersInGroupGrid(userGroupId, filter);
			
			return vos;
		} catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}

	public void addUsers(Long userGroupId,Collection<UserVo> vos) throws ServiceException {
		try{
			Collection<UserGroupUser> uguEntities = new ArrayList<UserGroupUser>();
			
			UserGroup entity = userGroupDao.getById(userGroupId, UserGroupImpl.class);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"UserGroup");
			
			for(UserVo vo : vos){
				UserGroupUser uguEntity = new UserGroupUserImpl();
				uguEntity.setUser(UserVo.toEntity(null, vo, false,uguEntity));
				uguEntity.setUserGroup(entity);
//				uguEntity.setUserGroupUserRoles(SystemRoleVo.toEntityList(vo.getUserRoleVos(), false));
				
				//entity.getUserGroupUsers().add(uguEntity);
				uguEntities.add(uguEntity);
			}
			
			userGroupUserDao.saveAll(uguEntities);
			
			//userGroupDao.save(entity);
			
		}catch(Exception e){
			super.handleException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserGroupService#removeUsers(java.lang.Long, java.util.Collection)
	 */
	public void removeUsers(Long userGroupId,Collection<UserGroupUserVo> vos) throws ServiceException {
		
		try{
				
			UserGroup entity = userGroupDao.getById(userGroupId, UserGroupImpl.class);

			Collection<UserGroupUser> removeEntities = new ArrayList<UserGroupUser>();
			for(UserGroupUser uguEntity : entity.getUserGroupUsers()){
				for(UserGroupUserVo uguVo : vos){
					if(uguEntity.getId().compareTo(uguVo.getId())==0)
						removeEntities.add(uguEntity);
				}
			}
			
			entity.getUserGroupUsers().removeAll(removeEntities);
			for(UserGroupUser uguEntity : removeEntities){
				userGroupUserDao.delete(uguEntity);
			}
			
		}catch(Exception e){
			super.handleException(e);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.service.access.enterprise.EnterpriseUserGroupService#deleteUserGroup(java.lang.Long)
	 */
	public void deleteUserGroup(Long userGroupId) throws ServiceException {
		if (userGroupId == null) 
			throw new ServiceException("userGroupId cannot be null");

		try {
			UserGroup entity = userGroupDao.getById(userGroupId, UserGroupImpl.class);
			
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"UserGroup");

			if (super.getUserSessionVo().getUserId().compareTo(entity.getGroupOwnerId()) != 0) 
				throw new ServiceException("Cannot delete a User Group for a different Group Owner.");
			else
				userGroupDao.delete(entity);
		}
		catch ( Exception e ) {
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserGroupService#saveRolesForUser(java.lang.Long, java.util.Collection)
	 */
	public void saveRolesForUser(Long userGroupUserId, Collection<SystemRoleVo> vos) throws ServiceException {
		try {
			if (vos == null) 
				super.handleException(ErrorEnum._0021_NO_ROLES_DEFINED);


			UserGroupUser entity = userGroupUserDao.getById(userGroupUserId, UserGroupUserImpl.class);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"UserGroupUser");
			
			UserGroup ugEntity = userGroupDao.getById(entity.getUserGroupId(), UserGroupImpl.class);
			if (ugEntity == null) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"UserGroup");

			if (super.getUserSessionVo().getUserId().compareTo(ugEntity.getGroupOwnerId()) != 0) 
				throw new ServiceException("Cannot save roles for a User Group User with a different Group Owner.");
			
//			entity.getUserGroupUserRoles().clear();
			
//			entity.getUserGroupUserRoles().addAll(SystemRoleVo.toEntityList(vos, false));

			userGroupUserDao.save(entity);
		}
		catch ( Exception e ) {
			super.handleException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.service.incident.enterprise.EnterpriseIncidentService#getUserGroupPicklist(java.lang.String)
	 */
	public Collection<UserGroupPicklistVo> getUserGroupPicklist() throws ServiceException {
		// Note: current user id is used to populate the UserGroupFilter (leave rest of filter empty)
		//       when calling the getPicklist method.
		UserGroupFilter userGroupFilter = new UserGroupFilterImpl();
		userGroupFilter.setCurrentUserId(super.getUserSessionVo().getUserId());
		try {
			return userGroupDao.getPicklist(userGroupFilter);
		} catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}

}