package gov.nwcg.isuite.core.rules.common.password;

import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class PasswordValidationRuleFactory {

	public enum ObjectTypeEnum {
		CURRENT_REQUIRED
		,CURRENT_PASSWORD
		,NEW_PASSWORD
		,CONFIRM_NEW_PASSWORD
		,USER_ID
		,USER_PASSWORD
	}
	
	public enum RuleEnum {
		VALIDATE_PASSWORD_DATA(5)
		,CHECK_IF_CURRENT_PASSWORD_IS_VALID(10)
		,CHECK_NEW_AND_CONFIRM_MATCH(15)
		,CHECK_FORMAT(20)
		,CHECK_HISTORY(25)
		,CHECK_DICTIONARY(30)
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
	 * @param currentRequired  Boolean - is currentPassword required
	 * @param currentPassword  String - user entered current password
	 * @param newPassword      String - user entered new password
	 * @param confirmNewPassword  String - user entered new password
	 * @param userId           Long - password user's userId, submit 0L to skip checkPasswordHistory
	 * @param userPassword     String - encrypted password saved in the database
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(
			RuleEnum rule, 
			ApplicationContext ctx, 
			Boolean currentRequired,
			String currentPassword,
			String newPassword,
			String confirmNewPassword,
			Long userId,
			String userPassword) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new ValidatePasswordDataRules(ctx, rule.name());
				break;
			case 10:
				ruleInstance = new CheckIfCurrentPasswordIsValidRules(ctx, rule.name());
				break;
			case 15:
				ruleInstance = new CheckNewAndConfirmPasswordMatchRules(ctx, rule.name());
				break;
			case 20:
				ruleInstance = new CheckFormatRules(ctx, rule.name());
				break;
			case 25:
				ruleInstance = new CheckPasswordHistoryRules(ctx, rule.name());
				break;
			case 30:
				ruleInstance = new CheckDictionaryRules(ctx, rule.name());
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(currentRequired, ObjectTypeEnum.CURRENT_REQUIRED.name());
			ruleInstance.setObject(currentPassword, ObjectTypeEnum.CURRENT_PASSWORD.name());
			ruleInstance.setObject(newPassword, ObjectTypeEnum.NEW_PASSWORD.name());
			ruleInstance.setObject(confirmNewPassword, ObjectTypeEnum.CONFIRM_NEW_PASSWORD.name());
			ruleInstance.setObject(userId, ObjectTypeEnum.USER_ID.name());
			ruleInstance.setObject(userPassword, ObjectTypeEnum.USER_PASSWORD.name());
		}
		
		return ruleInstance;
	}
	

	
}
