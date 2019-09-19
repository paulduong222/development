package gov.nwcg.isuite.core.rules.usergroup.adduser;

import java.util.ArrayList;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.UserGroupUser;
import gov.nwcg.isuite.core.vo.UserGroupUserVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import org.springframework.context.ApplicationContext;

public class UserGroupAddNonPrivilegedUserAccountRule extends AbstractUserGroupAddUserRule implements IRule {
	/* 3.	The system must allow the user to add any non-privileged User Account 
	 *      in the e-ISuite System to a User Account Group.
	 * 
	 * 6.	The system must prevent the user from adding privileged User Accounts to a User Group.
	 */
	
	public UserGroupAddNonPrivilegedUserAccountRule(ApplicationContext ctx, String rname) {
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
		/*
		 * Remove any privileged users from the UserGroupUsers collection.
		 * Show message that the privileged users can not be added to UserGroup
		 * Allow the valid users to be saved. (aka. always return _OK)
		 */
		
		for(UserGroupUserVo vo : vos) {
			User user = userDao.getById(vo.getUserVo().getId(), User.class);
			UserVo uvo = UserVo.getInstance(user, true);
			if(uvo.getAdminUser()) {
				// remove userGroupUser
				for(int i=entity.getUserGroupUsers().size()-1; i>-1; i--) {
					if(uvo.getId().equals(((ArrayList<UserGroupUser>)entity.getUserGroupUsers()).get(i).getUser().getId())){
						((ArrayList<UserGroupUser>)entity.getUserGroupUsers()).remove(i);
						break;
					}
				}
				
				// create message that privilaged user can be be added
				CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName("Save Violation");
				MessageVo msgVo;
				if(coaVo == null) {
					coaVo = new CourseOfActionVo();
					coaVo.setCoaName("Save Violation");
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE );
					coaVo.setIsDialogueEnding(true);
					
					dialogueVo.setCourseOfActionVo(coaVo);
					
					msgVo = new MessageVo();
					msgVo.setTitleProperty("User Save Violation");
					msgVo.setMessageType(MessageTypeEnum.URGENT);
					msgVo.setMessageProperty("The privileged user " + user.getLoginName() + " can not be added to this User Group");
					coaVo.setMessageVo(msgVo);
				} else {
					msgVo = coaVo.getMessageVo();
					String msg = msgVo.getMessageProperty();
					msg = msg + " \n " + "The privileged user " + user.getLoginName() + " can not be added to this User Group";
					msgVo.setMessageProperty(msg);
				}
			}
		}
		return _OK;
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

	
}
