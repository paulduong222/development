package gov.nwcg.isuite.core.rules.support.pwd;

import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class PasswordValidationRuleFactory {

	public enum ObjectTypeEnum {
		NEW_PASSWORD
		,CONFIRM_PASSWORD
		,USER_VO
		,USER_ENTITY
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(2)
		,CHECK_FORMAT(5)
		,CHECK_NEW_AND_CONFIRM_MATCH(20)
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
			String newPassword,
			String confirmPassword) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 2:
				ruleInstance = new ChangePasswordValidateDataRules(ctx);
				break;
			case 5:
				ruleInstance = new CheckFormatRules(ctx);
				break;

			case 20:
				ruleInstance = new CheckNewAndConfirmMatchRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(newPassword, ObjectTypeEnum.NEW_PASSWORD.name());
			ruleInstance.setObject(confirmPassword, ObjectTypeEnum.CONFIRM_PASSWORD.name());
		}
		
		return ruleInstance;
	}
	

	
}
