package gov.nwcg.isuite.core.rules.common.password;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckNewAndConfirmPasswordMatchRules extends AbstractPasswordValidationRule implements IRule {

	public CheckNewAndConfirmPasswordMatchRules(ApplicationContext ctx, String rName) {
		super(ctx, rName);
	}

	@Override
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			if(isCourseOfActionComplete(dialogueVo, ruleName))
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
			.add(super.buildNoActionCoaVo(ruleName,true));

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

		/*C.	2.0002 – Manage Password Change
		
		Validation on Save
		
			3.	If the new password and verify password do not 
				match when the user saves the User Account, 
				the system must remove the new password and 
				verify password. The system must not add or 
				update the User Account and must display a message. 
				[Message 0023]
				 
				 0023	The Password and Verify Password do not match.*/
		
		if(!newPassword.equals(confirmNewPassword)) {
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName(ruleName);
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
		
	}

}
