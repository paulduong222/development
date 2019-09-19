package gov.nwcg.isuite.core.rules.user.saveuser;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class CheckForAdOnlyRules extends AbstractSaveUserRule implements IRule {
	public static final String _RULE_NAME=SaveUserRuleFactory.RuleEnum.CHECK_FOR_AD_ONLY_RULE.name();

	public CheckForAdOnlyRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
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
	
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {

			/*
			 * Defect #4644.
			 * The user can create an Account Manager user account named "ad.".  
			 * The system should prevent the creation of an account named "ad."
			 */
		
			if(userVo.getLoginName().equalsIgnoreCase("AD.")) {
				CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
				courseOfActionVo.setCoaName(_MSG_FINISHED);
				courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				courseOfActionVo.setMessageVo(
						new MessageVo(
								"text.userAccounts",
								"info.generic",
								new String[]{"The User Name 'ad.' is not permitted."},
								MessageTypeEnum.CRITICAL));
				courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setCourseOfActionVo(courseOfActionVo);
				return _FAIL;
			}
		
		return _OK;
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
