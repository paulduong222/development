package gov.nwcg.isuite.core.rules.usergroup.adduser;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.UserGroupUser;
import gov.nwcg.isuite.core.vo.UserGroupUserVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import org.springframework.context.ApplicationContext;

public class UserGroupAddUniqueUserAccountRule extends AbstractUserGroupAddUserRule implements IRule {
	/* 7.	The system must not allow the user to add duplicate 
	 *      User Accounts to a User Account Group. 
	 */
	
	public UserGroupAddUniqueUserAccountRule(ApplicationContext ctx, String rname) {
		super(ctx, rname);
	}
	
	@Override
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try {
			/*
		     * if rule check has been completed, return
		     */
			if(isCourseOfActionComplete(dialogueVo, ruleName))
				return _OK;
		
			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo) == _FAIL) 
				return _FAIL;
			
			/*
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
				.add(super.buildNoActionCoaVo(ruleName,true));
			 
		} catch (Exception e) {
			throw new ServiceException(e);
		}
				
		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		ArrayList<UserGroupUser> users = new ArrayList<UserGroupUser>();
		users.addAll(entity.getUserGroupUsers());
		int count = 0;
		
		for(UserGroupUser ugu : users) {
			count = 0;
			for(int i=entity.getUserGroupUsers().size()-1; i>-1; i--) {
				if(ugu.getUser().getId().equals(((ArrayList<UserGroupUser>)entity.getUserGroupUsers()).get(i).getUser().getId())){
					count++;
					if(count > 1) {
						((ArrayList<UserGroupUser>)entity.getUserGroupUsers()).remove(i);
					}
				}
			}
		}
		
		return _OK;
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
	
}
