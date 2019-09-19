package gov.nwcg.isuite.core.rules.usergroup.adduser;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.UserGroup;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.persistence.UserGroupDao;
import gov.nwcg.isuite.core.vo.UserGroupUserVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class UserGroupAddUserRuleFactory {

	public enum ObjectTypeEnum {
		USER_GROUP_USER_VOS
		,USER_GROUP
		,USER_DAO
		,USER_GROUP_DAO
	}
	
	public enum RuleEnum {
		UNIQUE_USER(5)
		,NON_PRIVELEGED(10)
		,IS_ENABLED(15)
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
									Collection<UserGroupUserVo> vos, 
									UserGroupDao dao,
									UserDao userDao) throws Exception {
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new UserGroupAddUniqueUserAccountRule(ctx, rule.name());
				break;
			case 10:
				ruleInstance = new UserGroupAddNonPrivilegedUserAccountRule(ctx, rule.name());
				break;
			case 15:
				ruleInstance = new UserGroupAddEnabledUserAccountRule(ctx, rule.name());
				break;	
		}

		if(null != ruleInstance){
			ruleInstance.setObject(entity, ObjectTypeEnum.USER_GROUP.name());
			ruleInstance.setObject(vos, ObjectTypeEnum.USER_GROUP_USER_VOS.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.USER_GROUP_DAO.name());
			ruleInstance.setObject(userDao, ObjectTypeEnum.USER_DAO.name());
		}
		
		return ruleInstance;
	}
	
	
}
