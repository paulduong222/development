package gov.nwcg.isuite.core.rules.user.deleteuser;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class DeleteUsersRuleFactory {

	public enum ObjectTypeEnum {
		USER_VO,
		USER_ENTITY,
		USER_DAO
	}
	
	public enum RuleEnum {
		DELETE_ONE_USER_ACCT_RULE(5),
		CANNOT_DELETE_OWN_ACCT_RULE(10),
		CANNOT_DELETE_IF_TRANSACTIONS_EXIST_RULE(15),
		REMOVE_USER_FROM_RESTRICTED_INC_LIST_RULE(20),
		REMOVE_USER_FROM_USER_GROUP_RULE(25),
		DELETE_USER_EVENT(50),
		CHECK_NAP_ACCOUNT_MANAGER_DELETE_RULE(60)
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
	                                UserVo userVo,
	                                User userEntity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new DeleteOneUserAcctRules(ctx);
				break;
			case 10:
				ruleInstance = new CannotDeleteOwnAcctRules(ctx);
				break;
			case 15:
				ruleInstance = new CannotDeleteIfTransactionsExistRules(ctx);
				break;
			case 20:
				ruleInstance = new RemoveUserFromRestrictedIncidentListRules(ctx);
				break;
			case 25:
				ruleInstance = new RemoveUserFromUserGroupRules(ctx);
				break;
			case 50:
				ruleInstance = new DeleteUserEventRules(ctx);
				break;
			case 60:
				ruleInstance = new CheckNapAccountManagerDeleteRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(userVo, ObjectTypeEnum.USER_VO.name());
			ruleInstance.setObject(userEntity, ObjectTypeEnum.USER_ENTITY.name());
		}
		
		return ruleInstance;
	}
	
}
