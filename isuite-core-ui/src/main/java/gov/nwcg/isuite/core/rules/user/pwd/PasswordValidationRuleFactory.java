package gov.nwcg.isuite.core.rules.user.pwd;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class PasswordValidationRuleFactory {

	public enum ObjectTypeEnum {
		CURRENT_PASSWORD
		,NEW_PASSWORD
		,CONFIRM_PASSWORD
		,USER_VO
		,USER_ENTITY
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(2)
		,CHECK_FORMAT(5)
		,CHECK_HISTORY(10)
		,CHECK_DICTIONARY(15)
		,CHECK_NEW_AND_CONFIRM_MATCH(20)
		,CHECK_IF_CURRENT_PASSWORD_IS_VALID(25)
		,PASSWORD_RESET_EVENT(100)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
		
	}	
	
	/**
	 * @param rule
	 * @param ctx
	 * @param newPassword
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(
			RuleEnum rule, 
			ApplicationContext ctx, 
			String currentPassword,
			String newPassword,
			String confirmPassword,
			UserVo userVo,
			User user) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 2:
				ruleInstance = new ChangePasswordValidateDataRules(ctx);
				break;
			case 5:
				ruleInstance = new CheckFormatRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckHistoryRules(ctx);
				break;
			case 15:
				ruleInstance = new CheckDictionaryRules(ctx);
				break;
			case 20:
				ruleInstance = new CheckNewAndConfirmMatchRules(ctx);
				break;
			case 25:
				ruleInstance = new CheckIfCurrentPasswordIsValidRules(ctx);
				break;
			case 100:
				ruleInstance = new PasswordResetEventRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(currentPassword, ObjectTypeEnum.CURRENT_PASSWORD.name());
			ruleInstance.setObject(newPassword, ObjectTypeEnum.NEW_PASSWORD.name());
			ruleInstance.setObject(confirmPassword, ObjectTypeEnum.CONFIRM_PASSWORD.name());
			ruleInstance.setObject(userVo, ObjectTypeEnum.USER_VO.name());
			ruleInstance.setObject(user, ObjectTypeEnum.USER_ENTITY.name());
		}
		
		return ruleInstance;
	}
	

	
}
