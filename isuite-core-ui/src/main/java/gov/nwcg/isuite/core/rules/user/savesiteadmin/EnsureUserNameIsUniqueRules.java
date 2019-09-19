package gov.nwcg.isuite.core.rules.user.savesiteadmin;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class EnsureUserNameIsUniqueRules extends AbstractSaveSiteAdminRule implements IRule {

	public static final String _RULE_NAME=SaveSiteAdminRuleFactory.RuleEnum.ENSURE_USER_NAME_IS_UNIQUE_RULE.name();

	public EnsureUserNameIsUniqueRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}

	@Override
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo)==_FAIL)
				return _FAIL;

			/*
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
			.add(super.buildNoActionCoaVo(_RULE_NAME,true));

		}catch(Exception e){
			throw new ServiceException(e);
		}

		return _OK;
	}

	/**
	 * 
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*A.	2.0000 – Manage User Accounts on Enterprise

			Validation on Save
			
			4.	If the User Name for the User Account is 
				identified as already being used in the 
				system when the user attempts to add it, 
				the User Account is not added and a message 
				displays. [Message 0045]
				
				0045	User Name <username> is already 
						defined in the system for 
						<First Name> <Last Name>.
		*/
		
		User user = userDao.getByLoginName(userVo.getLoginName());
		if(user != null && !user.getId().equals(userVo.getId())) {
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName(_RULE_NAME);
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			courseOfActionVo.setMessageVo(
					new MessageVo(
							"text.userAccounts",
							"error.0045",
							new String[]{
									userVo.getLoginName(), 
									userVo.getFirstName(), 
									userVo.getLastName()},
							MessageTypeEnum.CRITICAL));
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		// TODO Auto-generated method stub

	}

}
