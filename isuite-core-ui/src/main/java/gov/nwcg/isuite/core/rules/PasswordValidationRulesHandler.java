package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.rules.common.password.PasswordValidationRuleFactory;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class PasswordValidationRulesHandler extends AbstractRuleHandler {
	
	public PasswordValidationRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	/**
	 * @param rule
	 * @param ctx
	 * @param currentRequired  Boolean - is currentPassword required, submit false to skip checkIsCurrentPasswordValid
	 * @param currentPassword  String - user entered current password
	 * @param newPassword      String - user entered new password
	 * @param confirmNewPassword  String - user entered new password
	 * @param userId           Long - password user's userId, submit 0L to skip checkPasswordHistory
	 * @param userPassword     String - encrypted password saved in the database
	 * @return
	 * @throws Exception
	 */
	public int execute(Boolean currentRequired,
			           String currentPassword, 
			           String newPassword, 
			           String confirmNewPassword, 
			           Long userId, 
			           String userPassword, 
			           DialogueVo dialogueVo) throws Exception {
		
		try{
			for(PasswordValidationRuleFactory.RuleEnum ruleEnum : PasswordValidationRuleFactory.RuleEnum.values()){
				IRule rule = PasswordValidationRuleFactory.getInstance(ruleEnum, context, currentRequired, currentPassword, newPassword, confirmNewPassword, userId, userPassword);
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}
			
		}catch(Exception e){
			// handle exception
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(
					new MessageVo(
							"text.userAccounts", 
							"error.900000" , 
							new String[]{e.getMessage()}, 
							MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	public void executeProcessedActions(Boolean currentRequired,
	           							String currentPassword, 
								        String newPassword, 
								        String confirmNewPassword, 
								        Long userId, 
								        String userPassword, 
								        DialogueVo dialogueVo) throws Exception {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			/*
			 * Execute any needed follow up actions 
			 */
			for(PasswordValidationRuleFactory.RuleEnum ruleEnum : PasswordValidationRuleFactory.RuleEnum.values()){
				IRule rule = PasswordValidationRuleFactory.getInstance(ruleEnum, context, 
																		currentRequired, currentPassword, 
																		newPassword, confirmNewPassword, 
																		userId, userPassword);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	public void addAdditionalMessages(DialogueVo dialogueVo) throws Exception {
		for(PasswordValidationRuleFactory.RuleEnum ruleEnum : PasswordValidationRuleFactory.RuleEnum.values()){
			IRule rule = PasswordValidationRuleFactory.getInstance(ruleEnum, context, null, null, null, null, null, null);
			
			if(null != rule){
				rule.addAdditionalMessages(dialogueVo);
			}
		}
	}
}
