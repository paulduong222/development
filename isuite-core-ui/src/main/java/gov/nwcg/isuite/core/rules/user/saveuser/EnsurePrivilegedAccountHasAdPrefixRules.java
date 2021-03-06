package gov.nwcg.isuite.core.rules.user.saveuser;

import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;

import org.springframework.context.ApplicationContext;

public class EnsurePrivilegedAccountHasAdPrefixRules extends AbstractSaveUserRule implements IRule {

	public static final String _RULE_NAME=SaveUserRuleFactory.RuleEnum.ENSURE_PRIV_ACCOUNT_HAS_AD_PREFIX_RULE.name();

	public EnsurePrivilegedAccountHasAdPrefixRules(ApplicationContext ctx) {
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
		/*A.	2.0000 � Manage User Accounts on Enterprise

			Validation on Save
			
			5.	If the user attempts to save a privileged 
				User Account that does not have an "ad." 
				prefix for their User Account Name, the 
				system must not save the User Account and 
				must display an error message. [Message 0228]
				
				0228	The user attempted to save a privileged 
						User Account that does not have an "ad" 
						prefix in the User Name.
		*/
		
		for(SystemRoleVo systemRoleVo : userVo.getUserRoleVos()) {
			//if(systemRoleVo.getRoleName().equals("ROLE_ACCOUNT_MANAGER")) {
			if(BooleanUtility.isTrue(systemRoleVo.getPrivilegedRole())){
				if(!userVo.getLoginName().startsWith("ad.")) {
					CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
					courseOfActionVo.setCoaName(_MSG_FINISHED);
					courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					courseOfActionVo.setMessageVo(
							new MessageVo(
									"text.userAccounts",
									"error.0228",
									null,
									MessageTypeEnum.CRITICAL));
					courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
					dialogueVo.setCourseOfActionVo(courseOfActionVo);
					return _FAIL;
				}
			}
		}
		
		return _OK;
	}

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		// TODO Auto-generated method stub

	}

}
