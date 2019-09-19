package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.filter.UserGroupFilter;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.UserGroupUserVo;
import gov.nwcg.isuite.core.vo.UserGroupVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

public interface UserGroupService2 extends TransactionService {

	public DialogueVo getGrid(UserGroupFilter filter, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo save(UserGroupVo vo,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getAvailableUsersGrid(Long userGroupId, UserFilter filter,DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getUsersInGroupGrid(Long userGroupId, UserFilter filter, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo deleteUserGroup(Long userGroupId, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getById(Long id,DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getUserGroupPicklist(DialogueVo dialogueVo) throws ServiceException;
	
}
