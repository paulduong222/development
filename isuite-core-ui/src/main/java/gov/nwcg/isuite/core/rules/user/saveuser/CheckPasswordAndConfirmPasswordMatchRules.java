package gov.nwcg.isuite.core.rules.user.saveuser;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckPasswordAndConfirmPasswordMatchRules extends AbstractSaveUserRule implements IRule {

	public static final String _RULE_NAME=SaveUserRuleFactory.RuleEnum.CHECK_PASSWORD_AND_CONFIRM_PASSWORD_MATCH_RULE.name();

	public CheckPasswordAndConfirmPasswordMatchRules(ApplicationContext ctx) {
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
		/*
		 * only execute this rule if runmode==site or (runmode==enterprise && use_nap_auth==0)
		 */
		try{
			if(super.getRunMode().equals("ENTERPRISE") && super.getSystemParamValue(SystemParameterTypeEnum.USE_NAP_AUTH).equals("1"))
				return _OK;
		}catch(Exception e){
		}
		
		if(!userVo.getPassword().equals(userVo.getConfirmPassword())) {
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName(_RULE_NAME);
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			courseOfActionVo.setMessageVo(
					new MessageVo(
							"text.userAccounts",
							"error.0023",
							null,
							MessageTypeEnum.CRITICAL));
			courseOfActionVo.setIsDialogueEnding(true);
			
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
