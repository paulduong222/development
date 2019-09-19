package gov.nwcg.isuite.core.rules.usergroup.delete;

import gov.nwcg.isuite.core.domain.UserGroup;
import gov.nwcg.isuite.core.persistence.UserGroupDao;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class UserGroupDeleteRuleFactory {

	public enum ObjectTypeEnum {
		USER_GROUP
		,USER_GROUP_DAO
	}
	
	public enum RuleEnum {
		ENTITY_EXISTS(5)
		,GROUP_OWNER(10)
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
									UserGroup entity,  
									UserGroupDao dao) throws Exception {
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new UserGroupEntityExistsRule(ctx, rule.name());
				break;
			case 10:
				// any data steward can now delete any user group
				//ruleInstance = new UserGroupIsGroupOwnerRule(ctx, rule.name());
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(entity, ObjectTypeEnum.USER_GROUP.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.USER_GROUP_DAO.name());
		}
		
		return ruleInstance;
	}
	
	
}
