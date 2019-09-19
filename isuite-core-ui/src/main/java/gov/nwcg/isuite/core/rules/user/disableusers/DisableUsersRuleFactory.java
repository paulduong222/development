package gov.nwcg.isuite.core.rules.user.disableusers;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class DisableUsersRuleFactory {

	public enum ObjectTypeEnum {
		USER_VOS,
		USER_ENTITIES,
		USER_DAO
	}

	public enum RuleEnum {
		CANNOT_DISABLE_OWN_USER_ACCT_RULE(5),
		IMMEDIATELY_LOG_OUT_DISABLED_USER_RULE(10)
		;

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
			Collection<UserVo> userVos,
			Collection<User> userEntities) throws Exception {
		
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new CannotDisableOwnUserAcctRules(ctx);
				break;
			case 10:
				ruleInstance = new ImmediatelyLogOutDisabledUserRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(userVos, ObjectTypeEnum.USER_VOS.name());
			ruleInstance.setObject(userEntities, ObjectTypeEnum.USER_ENTITIES.name());
		}

		return ruleInstance;
	}
}
