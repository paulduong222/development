package gov.nwcg.isuite.core.rules.user.saveuser;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveUserRuleFactory {

	public enum ObjectTypeEnum {
		USER_VO,
		USER_ENTITY,
		NEW_PASSWORD,
		USER_DAO,
		DB_NAME
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(1),
		CHECK_PASSWORD_AND_CONFIRM_PASSWORD_MATCH_RULE(2),
		CHECK_PASSWORD_FORMAT(3),
		CHECK_PWD_HISTORY(4),		
		ENSURE_USER_NAME_IS_UNIQUE_RULE(5),
		CHECK_VAlID_NAMING_RULE(7),
		ASSIGN_AT_LEAST_ONE_ROLE_RULE(10),
		ENSURE_PRIV_ACCOUNT_HAS_AD_PREFIX_RULE(15),
		USER_ACCT_IS_DISABLED_WHEN_SAVED_RULE(20),
		USER_ACCT_DISABLED_WHEN_SAVED_RULE(23),
		USER_ACCT_CHANGES_FROM_DISABLED_TO_ENABLED_RULE(25),
		ENSURE_NON_PRIV_ACCT_DOES_NOT_HAVE_AD_PREFIX_RULE(30),
		CHECK_FOR_AD_ONLY_RULE(40),
		CHECK_NAP_ACCOUNT_MANAGER_RULE(50),
		CHECK_AUDIT_ROLE_CHANGE(100),
		CHECK_AUDIT_ENABLED_CHANGE(110),
		USER_CREATED_EVENT(120),
		PASSWORD_RESET_EVENT(130);
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
		
	}	
	
	public static IRule getInstance(RuleEnum rule, 
	                                ApplicationContext ctx, 
	                                UserVo userVo,
	                                User userEntity,
	                                UserDao userDao,
	                                String dbName) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx()) {
			case 1:
				ruleInstance = new ValidateDataRules(ctx, rule.name());
				break;
			case 2:
				ruleInstance = new CheckPasswordAndConfirmPasswordMatchRules(ctx);
				break;
			case 3:
				ruleInstance = new CheckFormatRules(ctx);
				break;
			case 4:
				ruleInstance = new CheckPasswordHistoryRules(ctx);
				break;
			case 5:
				ruleInstance = new EnsureUserNameIsUniqueRules(ctx);
				break;
			case 7:
				ruleInstance = new CheckValidNamingRules(ctx);
				break;
			case 10:
				ruleInstance = new AssignAtLeastOneRoleRules(ctx);
				break;
			case 15:
				ruleInstance = new EnsurePrivilegedAccountHasAdPrefixRules(ctx);
				break;
//			case 20:
//				ruleInstance = new UserAcctIsDisabledWhenSavedRules(ctx);
//				break;
			case 23:
				ruleInstance = new UserAcctDisabledWhenSavedRules(ctx);
				break;
			case 25:
				ruleInstance = new UserAcctChangesFromDisabledToEnabledRules(ctx);
				break;
			case 30:
				ruleInstance = new EnsureNonPrivAcctDoesNotHaveAdPrefixRules(ctx);
				break;
			case 40:
				ruleInstance = new CheckForAdOnlyRules(ctx);
				break;
			case 50:
				ruleInstance = new CheckNapAccountManagerEditRules(ctx);
				break;
			case 100:
				ruleInstance = new CheckAuditRoleEventRules(ctx);
				break;
			case 110:
				ruleInstance = new CheckAuditEnabledEventRules(ctx);
				break;
			case 120:
				ruleInstance = new UserCreatedEventRules(ctx);
				break;
			case 130:
				ruleInstance = new PasswordResetEventRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(userVo, ObjectTypeEnum.USER_VO.name());
			ruleInstance.setObject(userEntity, ObjectTypeEnum.USER_ENTITY.name());
			ruleInstance.setObject(userDao, ObjectTypeEnum.USER_DAO.name());
			ruleInstance.setObject(dbName, ObjectTypeEnum.DB_NAME.name());
		}
		
		return ruleInstance;
	}
	
}
