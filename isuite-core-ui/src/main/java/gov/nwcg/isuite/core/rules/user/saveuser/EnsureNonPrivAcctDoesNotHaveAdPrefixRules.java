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

public class EnsureNonPrivAcctDoesNotHaveAdPrefixRules extends AbstractSaveUserRule implements IRule {

	public static final String _RULE_NAME=SaveUserRuleFactory.RuleEnum.ENSURE_NON_PRIV_ACCT_DOES_NOT_HAVE_AD_PREFIX_RULE.name();

	public EnsureNonPrivAcctDoesNotHaveAdPrefixRules(ApplicationContext ctx) {
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
			
				6.	If the user attempts to save a non-privileged 
					User Account that has an "ad." prefix for the 
					User Account Name, the system must not save the 
					User Account and must display a message. [Message 0229]
				
				0229	The user attempted to save a non-privileged 
						User Account with an "ad." prefix in the User Name.
		*/
		
		for(SystemRoleVo systemRoleVo : userVo.getUserRoleVos()) {
			if(BooleanUtility.isFalse(systemRoleVo.getPrivilegedRole())) {
			//if(!systemRoleVo.getRoleName().equals("ROLE_ACCOUNT_MANAGER")) {
				if(userVo.getLoginName().startsWith("ad.")) {
					CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
					courseOfActionVo.setCoaName(_MSG_FINISHED);
					courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					courseOfActionVo.setMessageVo(
							new MessageVo(
									"text.userAccounts",
									"error.0229",
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
