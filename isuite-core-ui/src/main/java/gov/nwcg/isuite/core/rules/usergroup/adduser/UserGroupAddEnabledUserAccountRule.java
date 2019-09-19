package gov.nwcg.isuite.core.rules.usergroup.adduser;

import java.util.ArrayList;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.UserGroupUser;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class UserGroupAddEnabledUserAccountRule extends AbstractUserGroupAddUserRule implements IRule {
	/* 	
	 * The User account must be enabled to be added to a UserGroup 
	 */
	
	public UserGroupAddEnabledUserAccountRule(ApplicationContext ctx, String rname) {
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
		
		for(UserGroupUser ugu : users) {
			User user = userDao.getById(ugu.getUser().getId(), User.class);
			if(!user.isEnabled()) {
				for(int i=entity.getUserGroupUsers().size()-1; i>-1; i--) {
					if(ugu.getUser().getId().equals(((ArrayList<UserGroupUser>)entity.getUserGroupUsers()).get(i).getUser().getId())){
						((ArrayList<UserGroupUser>)entity.getUserGroupUsers()).remove(i);
						
						// create message that privilaged user can be be added
						CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName("Save Violation");
						MessageVo msgVo;
						if(coaVo == null) {
							coaVo = new CourseOfActionVo();
							coaVo.setCoaName("Save Violation");
							coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE );
							coaVo.setIsDialogueEnding(false);
							
							dialogueVo.setCourseOfActionVo(coaVo);
							
							msgVo = new MessageVo();
							msgVo.setTitleProperty("User Save Violation");
							msgVo.setMessageType(MessageTypeEnum.URGENT);
							msgVo.setMessageProperty("The user " + user.getLoginName() + " is disabled and can not be added to this User Group");
							coaVo.setMessageVo(msgVo);
						} else {
							msgVo = coaVo.getMessageVo();
							String msg = msgVo.getMessageProperty();
							msg = msg + " \n " + "The user " + user.getLoginName() + " is disabled and can not be added to this User Group";
							msgVo.setMessageProperty(msg);
						}
						
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
