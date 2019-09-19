package gov.nwcg.isuite.core.rules.usergroup.save;

import gov.nwcg.isuite.core.domain.UserGroup;
import gov.nwcg.isuite.core.persistence.UserGroupDao;
import gov.nwcg.isuite.core.vo.UserGroupVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class UserGroupSaveRuleFactory {

	public enum ObjectTypeEnum {
		USER_GROUP
		,USER_GROUP_VO
		,USER_GROUP_DAO
	}
	
	public enum RuleEnum {
		GROUP_OWNER(5)
		,VALIDATION(10)
		,UNIQUE_NAME(15)
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
									UserGroupVo vo,
									UserGroup entity,
									UserGroupDao dao) throws Exception {
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 5:
				// any data steward can now save any user group
				//ruleInstance = new UserGroupSaveIsGroupOwnerRule(ctx, rule.name());
				break;
			case 10:
				ruleInstance = new UserGroupValidationRule(ctx, rule.name());
				break;	
			case 15:
				ruleInstance = new UserGroupUniqueNameRule(ctx, rule.name());
				break;	
		}

		if(null != ruleInstance){
			ruleInstance.setObject(entity, ObjectTypeEnum.USER_GROUP.name());
			ruleInstance.setObject(vo, ObjectTypeEnum.USER_GROUP_VO.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.USER_GROUP_DAO.name());
		}
		
		return ruleInstance;
	}
	
}
