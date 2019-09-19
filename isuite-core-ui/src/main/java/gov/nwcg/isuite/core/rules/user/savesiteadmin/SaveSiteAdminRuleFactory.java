package gov.nwcg.isuite.core.rules.user.savesiteadmin;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.rules.user.pwd.CheckFormatRules;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveSiteAdminRuleFactory {

	public enum ObjectTypeEnum {
		USER_VO,
		USER_ENTITY,
		USER_DAO
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(5),
		CHECK_FOR_AD_ONLY_RULE(8),
		CHECK_PASSWORD_RULE(10),
		ENSURE_USER_NAME_IS_UNIQUE_RULE(15);
		
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
	                                UserDao userDao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx()) {
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 8:
				ruleInstance = new CheckForAdOnlyRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckPasswordRules(ctx);
				break;
			case 15:
				ruleInstance = new EnsureUserNameIsUniqueRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(userVo, ObjectTypeEnum.USER_VO.name());
			ruleInstance.setObject(userEntity, ObjectTypeEnum.USER_ENTITY.name());
			ruleInstance.setObject(userDao, ObjectTypeEnum.USER_DAO.name());
		}
		
		return ruleInstance;
	}
	
}
