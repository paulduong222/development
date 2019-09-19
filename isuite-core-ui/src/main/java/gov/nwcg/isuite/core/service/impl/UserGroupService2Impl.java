package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.UserGroup;
import gov.nwcg.isuite.core.domain.UserGroupUser;
import gov.nwcg.isuite.core.domain.impl.UserGroupImpl;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.filter.UserGroupFilter;
import gov.nwcg.isuite.core.filter.impl.UserFilterImpl;
import gov.nwcg.isuite.core.filter.impl.UserGroupFilterImpl;
import gov.nwcg.isuite.core.persistence.UserGroupDao;
import gov.nwcg.isuite.core.persistence.UserGroupUserDao;
import gov.nwcg.isuite.core.rules.UserGroupAddUserRulesHandler;
import gov.nwcg.isuite.core.rules.UserGroupDeleteRulesHandler;
import gov.nwcg.isuite.core.rules.UserGroupSaveRulesHandler;
import gov.nwcg.isuite.core.service.UserGroupService2;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.core.vo.UserGroupGridVo;
import gov.nwcg.isuite.core.vo.UserGroupPicklistVo;
import gov.nwcg.isuite.core.vo.UserGroupUserVo;
import gov.nwcg.isuite.core.vo.UserGroupVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserGroupService2Impl extends BaseService implements UserGroupService2 {

	private UserGroupDao userGroupDao;
	private UserGroupUserDao userGroupUserDao;
	
	public UserGroupService2Impl() {
	}

	public void initialization(){
		userGroupDao = (UserGroupDao)super.context.getBean("userGroupDao");
		userGroupUserDao = (UserGroupUserDao)super.context.getBean("userGroupUserDao");
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserGroupService2#deleteUserGroup(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteUserGroup(Long userGroupId,DialogueVo dialogueVo) throws ServiceException {
		
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		if (userGroupId == null) 
			throw new ServiceException("userGroupId cannot be null");
		
		UserGroupDeleteRulesHandler rulesHandler = new UserGroupDeleteRulesHandler(context);
		
		try {
			UserGroup entity = userGroupDao.getById(userGroupId, UserGroupImpl.class);
			
			if(rulesHandler.execute(entity, dialogueVo) == UserGroupDeleteRulesHandler._OK) {
				userGroupDao.delete(entity);
				
				CourseOfActionVo coa = new CourseOfActionVo();
				coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coa.setCoaName("Delete User Group");
				String[] params = {"User Group"};
				coa.setMessageVo(new MessageVo("text.userGroups", "info.0028", params, MessageTypeEnum.INFO));
				coa.setIsDialogueEnding(Boolean.TRUE);
				
				dialogueVo.setCourseOfActionVo(coa);
				dialogueVo.setResultObject(userGroupId);
			}
			
		}catch ( Exception e ) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserGroupService2#getGrid(gov.nwcg.isuite.core.filter.UserGroupFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getGrid(UserGroupFilter filter, DialogueVo dialogueVo) throws ServiceException {
		
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
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

			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("GET_GRID_USER_GROUPS");
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coa.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(coa);
			dialogueVo.setRecordset(uggVos);
			
		}catch ( Exception e ) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserGroupService2#getAvailableUsersGrid(java.lang.Long, gov.nwcg.isuite.core.filter.UserFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getAvailableUsersGrid(Long userGroupId, UserFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		if (userGroupId == null) {
			throw new ServiceException("userGroupId cannot be null");
		}
		
		try {
			//Set Current User Id to logged in user
			if (filter == null) 
				filter = new UserFilterImpl();
			
			filter.setCurrentUserId(super.getUserSessionVo().getUserId());

			Collection<UserGridVo> vos = userGroupDao.getAvailableUsers(userGroupId, filter);
			
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("GET_AVAILABLE_USERS");
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coa.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setRecordset(vos);
			dialogueVo.setCourseOfActionVo(coa);
			
		}catch ( Exception e ) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserGroupService2#getById(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getById(Long id, DialogueVo dialogueVo) throws ServiceException {

		UserGroup entity =null;
		try {
			if (id == null) {
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"UserGoup[id=null]");
			}

			entity = userGroupDao.getById(id, UserGroupImpl.class);
			
			if (null == entity) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"UserGroup");
			
			UserGroupVo vo = UserGroupVo.getInstance(entity, true);

			dialogueVo.setResultObject(vo);
			
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("GET_BY_ID");
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coa.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(coa);
			
		}catch ( Exception e ) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserGroupService2#getUserGroupPicklist(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getUserGroupPicklist(DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try {
			// Note: current user id is used to populate the UserGroupFilter (leave rest of filter empty)
			//       when calling the getPicklist method.
			UserGroupFilter userGroupFilter = new UserGroupFilterImpl();
			userGroupFilter.setCurrentUserId(super.getUserSessionVo().getUserId());
			
			Collection<UserGroupPicklistVo> vos = userGroupDao.getPicklist(userGroupFilter);
			
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coa.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setRecordset(vos);
			
		}catch ( Exception e ) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserGroupService2#getUsersInGroupGrid(java.lang.Long, gov.nwcg.isuite.core.filter.UserFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getUsersInGroupGrid(Long userGroupId, UserFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		if (userGroupId == null) {
			throw new ServiceException("userGroupId cannot be null");
		}
		
		try {
			Collection<UserGroupUserVo> vos = userGroupUserDao.getUsersInGroupGrid(userGroupId, filter);
			
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coa.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setRecordset(vos);
		}catch ( Exception e ) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserGroupService2#save(gov.nwcg.isuite.core.vo.UserGroupVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo save(UserGroupVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		UserGroupSaveRulesHandler rulesHandler = new UserGroupSaveRulesHandler(context);
		
		try {
			UserGroup entity = null;
			if(vo.getId() > 0) {
				entity = userGroupDao.getById(vo.getId(), UserGroupImpl.class);
				if(null == entity)
					super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"UserGroup");
			}
			
			if(rulesHandler.execute(vo, entity, dialogueVo) == UserGroupSaveRulesHandler._OK) {

	            vo.setGroupOwnerVo(super.getUserVo());
				
	            // add creator of group to list
	            if(!LongUtility.hasValue(vo.getId())){
	            	UserGroupUserVo uguVo = new UserGroupUserVo();
	            	uguVo.setUserVo(super.getUserVo());
	            	vo.getUserGroupUserVos().add(uguVo);
	            }
	            
	            // make list of added users
	            List<UserGroupUserVo> addedUsers = new ArrayList<UserGroupUserVo>();
	            if(entity != null) {
	            	Boolean found = false;
	            	for(UserGroupUserVo uguv : vo.getUserGroupUserVos()) {
	            		found = false;
		            	for(UserGroupUser ugu : entity.getUserGroupUsers()) {
		            		if(uguv.getId().equals(ugu.getId())) {
		            			found = true;
		            		}
		            	}
	            		if(!found)
	            			addedUsers.add(uguv);
	            	}	
	            } else {
	            	for(UserGroupUserVo uguVo : vo.getUserGroupUserVos()) {
	            		addedUsers.add(uguVo);
	            	}
	            }
	            
	            entity = UserGroupVo.toEntity(vo, true);
	            
	            // check add users rules
            	UserGroupAddUserRulesHandler addRulesHandler = new UserGroupAddUserRulesHandler(context);
            	addRulesHandler.execute(entity, addedUsers, dialogueVo);
	            
				for (UserGroupUser userGroupUser : entity.getUserGroupUsers()) {
					userGroupUser.setUserGroup(entity);
				}
				
				userGroupDao.save(entity);
				
				userGroupDao.flushAndEvict(entity);
				entity=userGroupDao.getById(entity.getId(), UserGroupImpl.class);
				
				vo = UserGroupVo.getInstance(entity, true);
				
				UserGroupGridVo ugg = new UserGroupGridVo();
				ugg.setGroupName(vo.getGroupName());
				ugg.setGroupOwnerId(vo.getGroupOwnerVo().getId());
				ugg.setAltCreatedDate(vo.getCreatedDate());
				if(ugg.getGroupOwnerId().equals(super.getUserSessionVo().getUserId())) {
			       ugg.setDeletable(true);
			    } else {
			       ugg.setDeletable(false);
			    }
				ugg.setId(vo.getId());
				
				CourseOfActionVo coa = new CourseOfActionVo();
				coa.setCoaName("SAVE_USER_GROUP");
				coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coa.setIsDialogueEnding(Boolean.TRUE);
				coa.setMessageVo(new MessageVo("text.userGroups", "info.0030" , null, MessageTypeEnum.INFO));

				dialogueVo.setResultObject(vo);
				dialogueVo.setResultObjectAlternate(ugg);
								
				dialogueVo.setCourseOfActionVo(coa);
			}
		}catch ( Exception e ) {
			super.dialogueException(dialogueVo, e);
		}
		
		
		return dialogueVo;
	}

}
